package utils.creator;

import java.util.*;
import java.io.*;
import java.util.Comparator;
import java.lang.reflect.Array;
import utils.prompts.*;
import utils.menu.*;
import utils.Serializer;
public class CreationMenu<T extends Creatable> extends FunctionalMenu
{
	private Utility[] clientUtilities;
	private ArrayList<T> items = new ArrayList<>();
	private Class<T> clazz;
	private String className;
	private String classNamePlural;
	private Comparator<T> comparator;
	private String saveDirectoryPath;
	private File saveDirectory;
	private File save;
	
	/*
		CONSTRUCTORS
	*/

	public CreationMenu(Class<T> clazz, String saveDirectoryPath)
	{
		super();
		this.clazz = clazz;
		this.saveDirectoryPath = saveDirectoryPath;
		saveDirectory = new File(saveDirectoryPath);
		className = className();
		classNamePlural = plural(className);
		utilities = defaultUtilities();
	}

	@SuppressWarnings("unchecked")
	public CreationMenu(Class<T> clazz, String saveDirectoryPath, Utility... utils)
	{
		super();
		this.clazz = clazz;
		className = className();
		this.saveDirectoryPath = saveDirectoryPath;
		this.saveDirectory = new File(saveDirectoryPath);
		classNamePlural = plural(className);
		clientUtilities = utils;
		utilities = compileUtilities();
	}

	/*
		MUTATOR METHODS
	*/

	public void setClassName(String className)
	{
		this.className = className;
		this.classNamePlural = plural(className);
		utilities = compileUtilities();
	}

	public void setClassNamePlural(String classNamePlural)
	{
		this.classNamePlural = classNamePlural;
		utilities = compileUtilities();
	}

	public void setComparator(Comparator<T> comparator)
	{
		this.comparator = comparator;
	}

	/*
		INSTANCE METHODS
		APPLICATION UTILITIES
	*/

	public void createItem()
	{
		do
		{
			Menu.clearConsole();
			try
			{
				T item = clazz.getDeclaredConstructor().newInstance();
				item.prompt();
				items.add(item);
				sort();
				Serializer.serialize(items, save.getPath());
				displayItemState(item, false);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Error creating item.");
			}
		}
		while(ConsolePrompts.promptYesOrNo("\n\nCreate another " + className + "?"));
	}

	public void removeItem()
	{
		String[] names = names(items.toArray());

		SelectionMenu itemMenu = new SelectionMenu
		(
			"Below are listed all " + classNamePlural + " created so far",
			"Enter the number beside a " + className + " to permanently delete it or enter 0 to go back.",
			"Go Back",
			names
		);

		if(items.size() == 0)
			itemMenu.setProcede("No " + classNamePlural + " have been created. Enter 0 to go back.");

		int index = itemMenu.run() - 1;
		if(index != -1)
		{
			Menu.clearConsole();
			System.out.println("\n\n" + items.get(index).toString() + " has been deleted.\n");
			items.remove(index);
			Serializer.serialize(items, save.getPath());
			Menu.pause();
		}
	}

	@SuppressWarnings("unchecked")
	public void displayAllItems()
	{
		ObjectMenu<T> itemMenu = new ObjectMenu<>
		(
			"Below are listed all " + classNamePlural + " created so far",
			"Enter the number beside a " + className + " to view its stats or enter 0 to go back.",
			"Go Back",
			items.toArray((T[])Array.newInstance(clazz, items.size())),
			item -> displayItemState(item, true)
		);

		if(items.size() == 0)
			itemMenu.setProcede("No " + classNamePlural + " have been created. Enter 0 to go back.");
		
		itemMenu.run();
	}

	public void displayItemState(T item, boolean pause)
	{
		Menu.clearConsole();
		System.out.print(item.displayState());
		if(pause) Menu.pause();
	}

	public void loadSave()
	{
		File[] saves = Serializer.searchDirectory(saveDirectoryPath);
		String[] options;

		if(saves.length != 0)
		{
			options = new String[saves.length + 2];

			for(int i = 0; i < saves.length; i++)
			{
				File file = saves[i];
				options[i] = file.getName().substring(0, file.getName().length() - 4);
			}

			options[options.length - 2] = "CREATE A NEW SAVE";
			options[options.length - 1] = "DELETE AN EXISTING SAVE";

			SelectionMenu saveMenu = new SelectionMenu
			(
				"Hello, this is a program for creating and manipulating objects.",
				"Please select a save file to load created objects from.",
				options
			);
			int index = saveMenu.run() - 1;

			if(index == options.length - 2)
				createNewSave();
			else if(index == options.length - 1)
			{
				deleteSave();
				loadSave();
			}
			else
			{
				save = saves[index];
				loadData(save);
			}
		}
		else
		{
			createNewSave();
		}
	}

	/*
		OVERRIDDEN METHODS
	*/

	@Override
	public void run()
	{
		loadSave();
		super.run();
		Serializer.serialize(items, save.getPath());
	}

	/*
		PRIVATE METHODS
	*/

	private void loadData(File save)
	{
		try
		{
			items = Serializer.deserialize(save.getPath());
		}
		catch(IOException | ClassNotFoundException e)
		{
			System.out.println("\n\nFailed to load save.");
			e.printStackTrace();
			Menu.pause();
		}
	}

	private void createNewSave()
	{
		File[] saves = Serializer.searchDirectory(saveDirectoryPath);
		boolean nameConflictFound;
		String saveName;

		Menu.clearConsole();
		do
		{
			nameConflictFound = false;
			saveName = ConsolePrompts.promptString("\n\nPlease enter a name for your new save") + ".ser";
			for(File save : saves)
			{
				if(save.getName().equals(saveName))
				{
					nameConflictFound = true;
					System.out.print(saveName + " already exists.");
					break;
				}
			}
		}
		while(nameConflictFound);

		File newSave = new File(saveDirectoryPath + "\\" + saveName);
		try
		{
			newSave.createNewFile();
			save = newSave;
			/*
			loadData(save);
			*/
		}
		catch(IOException e)
		{
			System.out.println("\nFailed to create save.");
			Menu.pause();
		}
	}

	private void deleteSave()
	{
		File[] saves = Serializer.searchDirectory(saveDirectoryPath);
		String[] fileNames = new String[saves.length];

		for(int i = 0; i < fileNames.length; i++)
		{
			fileNames[i] = saves[i].getName().substring(0, saves[i].getName().length() - 4);
		}

		SelectionMenu saveMenu = new SelectionMenu(
			"DELETE SAVE",
			"Please select the save file you would like to delete. This action is permanent and cannot be reversed.",
			"Go Back",
			fileNames
		);

		Menu.clearConsole();
		int index = saveMenu.run() - 1;
		Menu.clearConsole();
		System.out.println("\n\n" + saves[index].getName() + " has been successfully deleted.");
		saves[index].delete();
		Menu.pause();
	}

	private Utility[] defaultUtilities()
	{
		return new Utility[]
		{
			new Utility(this::createItem, "Create a new " + className),
			new Utility(this::removeItem, "Delete an existing " + className),
			new Utility(this::displayAllItems, "View all existing " + classNamePlural)
		};
	}

	private Utility[] compileUtilities()
	{
		List<Utility> list = new ArrayList<>(Arrays.asList(defaultUtilities()));

		if(clientUtilities != null && clientUtilities.length != 0)
			list.addAll(Arrays.asList(clientUtilities));
		return list.toArray(new Utility[list.size()]);
	}

	private void sort()
	{
		if(comparator != null)
			items.sort(comparator);
	}

	private String className()
	{
		String result;
		String name = clazz.getName();
		int index = name.lastIndexOf(".");

		if(index == -1)
			result = name;
		else
			result = name.substring(index + 1);
		return result;
	}

	private static String plural(String input)
	{
		String result = input;
		if
		(
			input.endsWith("s") ||
			input.endsWith("sh") ||
			input.endsWith("ch") ||
			input.endsWith("x") ||
			input.endsWith("o")
		)
			result += "es";
		else
			result += "s";
		return result;
	}
}