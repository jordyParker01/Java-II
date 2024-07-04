package utils.settings;

import utils.menu.*;
public class Setting
{
	private String name;
	private String[] options;
	private int defaultOption;
	private int currentOption;

	private SelectionMenu menu;

	//CONSTRUCTORS
	public Setting(String n, int op, String[] descriptions)
	{
		if(descriptions.length > 1)
			options = descriptions;
		else
			throw new IllegalArgumentException("Number of options must be greater than one");
		name = n.toUpperCase();
		defaultOption = op;
		currentOption = op;
		options = descriptions;
		menu = new SelectionMenu(
			"Below are listed are the current options for '" + name + "'",
			"CURRENT OPTION: " + (currentOption + 1),
			options
		);
	}

	public Setting(String n, String[] descriptions)
	{
		this(n, 0, descriptions);
	}

	//ACCESSOR METHODS

	public String getName()
	{
		return name;
	}
	public String[] getOptions()
	{
		return options;
	}

	public int getDefaultOption()
	{
		return defaultOption;
	}

	public int getCurrentOption()
	{
		return currentOption;
	}

	public void setCurrentOption(int op)
	{
		currentOption = op;
		menu.setProcede("CURRENT OPTION: " + (op + 1));
	}

	//INSTATNCE METHODS
	public void setToDefault()
	{
		currentOption = defaultOption;
	}

	public void changeSetting()
	{
		while(true)
		{
			int result = menu.run();
			if(result != 0)
			{
				setCurrentOption(result - 1);
			}
			else
				break;
		}
	}

	@Override
	public String toString()
	{
		return name + "\n\t" + options[currentOption] + "\n";
	}
}