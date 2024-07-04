package application;

import java.util.*;
import java.io.*;
import utils.menu.*;
import utils.prompts.ConsolePrompts;
public class SpecialNamesSet
{
	private TreeMap<Integer, String> names = new TreeMap<>();
	private String filePath;

	/*
		CONSTRUCTORS
	*/

	public SpecialNamesSet(String fileName)
	{
		setFilePath(fileName);
		load();
	}

	/*
		ACCESSOR METHODS
	*/

	public String getFilePath()
	{
		return filePath;
	}

	/*
		MUTATOR METHODS
	*/

	public void setFilePath(String fileName)
	{
		filePath = "apps\\bases\\special_names_saves\\" + fileName;
	}

	/*
		INSTANCE METHODS
	*/

	public void save()
	{
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
		{
			int index = 1;
			for(Map.Entry<Integer, String> entry : names.entrySet())
			{
				writer.write(entry.getKey() + ": " + entry.getValue());
				index++;
				if(index != names.size())
					writer.newLine();
			}
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			System.out.println("Failure to save.");
			Menu.pause();
		}
	}

	public void load()
	{
		names.clear();
		try(BufferedReader reader = new BufferedReader(new FileReader(filePath)))
		{
			String[] values;

			while((values = reader.readLine().split(":")) != null)
			{
				names.put(Integer.parseInt(values[0].trim()), values[1].trim());
			}
		}
		catch(Exception e)
		{
			try(BufferedReader reader = new BufferedReader(new FileReader(filePath)))
			{
				String line;
				String[] values;

				while((line = reader.readLine()) != null)
				{
					values = line.split(":");
					names.put(Integer.parseInt(values[0].trim()), values[1].trim());
				}
			}
			catch(Exception f)
			{
				System.out.println(f.getMessage());
				System.out.println("\nDefault special names save was not found or was corrupted. Program will continue as normal without special names.");
				Menu.pause();
			}
		}
	}

	public void display()
	{
		System.out.println("\n\n");
		for(Map.Entry<Integer, String> entry : names.entrySet())
		{
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		System.out.println("\n");
		Menu.pause();
	}

	/*
		STATIC METHODS
	*/

	public static void createNewSave()
	{
		String fileName = ConsolePrompts.promptString("\nPlease enter the name of the new save");
		SpecialNamesSet newSave = new SpecialNamesSet(fileName);
	}
}