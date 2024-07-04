package utils.settings;

import java.util.ArrayList;
import java.io.*;
import utils.menu.*;
import utils.settings.Setting;
public class ApplicationSettings
{
	private Setting[] settings;
	private String filePath;
	private ObjectMenu<Setting> menu;

	//CONSTRUCTORS
	public ApplicationSettings(Setting[] s, String path)
	{
		settings = s;
		filePath = path;

		menu = new ObjectMenu<>(
			"Below are listed the settings of the current application.",
			"Please select a setting to view all its available options.\n",
			"Save and Quit",
			settings,
			setting -> setting.changeSetting()
		);
	}

	//ACCESSOR METHODS
	public Setting[] getSettings()
	{
		return settings;
	}

	//INSTANCE METHODS
	public void viewSettings()
	{
		menu.run();
		save();
	}

	public void save()
	{
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
		{
			for(int i = 1; i <= settings.length; i++)
			{
				writer.write(String.valueOf(settings[i - 1].getCurrentOption()));
				if(i != settings.length)
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
		try(BufferedReader reader = new BufferedReader(new FileReader(filePath)))
		{
			for(int i = 0; i < settings.length; i++)
			{
				try
				{
					int input = Integer.parseInt(reader.readLine());
					settings[i].setCurrentOption(input);
				}
				catch(NumberFormatException e)
				{
					settings[i].setToDefault();
					System.out.println(e.getMessage());
				}
			}
		}
		catch(IOException e)
		{
			for(int i = 0; i < settings.length; i++)
				settings[i].setToDefault();
		}
	}
}