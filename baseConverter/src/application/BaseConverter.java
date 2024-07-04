package application;

import java.util.*;
import utils.menu.*;
import utils.alg32.*;
import utils.settings.*;
import utils.prompts.ConsolePrompts;
public class BaseConverter
{
	static Setting[] settings = new Setting[]
	{
		new Setting("notation type for binary", 0,
			new String[]
			{
				"Alphanumeric",
				"Pseudodecimal"
			}
		),

		new Setting("notation type for octal", 0,
			new String[]
			{
				"Alphanumeric",
				"Pseudodecimal"
			}
		),

		new Setting("notation type for hexadecimal", 0,
			new String[]
			{
				"Alphanumeric",
				"Pseudodecimal"
			}
		),

		new Setting("notation type for all other radices from 11 to 36", 0,
			new String[]
			{
				"Alphanumeric",
				"Pseudodecimal"
			}
		),

		new Setting("notation type for all other radices less than 10", 0,
			new String[]
			{
				"Alphanumeric",
				"Pseudodecimal"
			}
		)
	};

	static ApplicationSettings appSettings = new ApplicationSettings(settings, "C:\\Users\\Jordy\\Java Program Files\\Base Converter\\settings.txt");
	static SpecialNamesSet namesSet = new SpecialNamesSet("C:\\Users\\Jordy\\Java Program Files\\Base Converter\\default.txt");

	static FunctionalMenu applicationMenu = new FunctionalMenu
	(
		new Utility[]
		{
			new Utility(BaseConverter::run_decimalToBase, "Convert a base-10 integer to working radix"),
			new Utility(BaseConverter::run_fractionToBase, "Convert a fraction to a place value numeral in working radix"),
			new Utility(BaseConverter::run_stringToFraction, "Convert a place value numeral in working radix to a fraction"),
			new Utility(BaseConverter::run_placeValueToBase, "Convert a base-10 place value numeral to working radix"),
			new Utility(RadixSettings::changeWorkingRadix, "Change Working Radix"),
			new Utility(appSettings::viewSettings, "View settings"),
			new Utility(namesSet::display, "View special names set (work in progress, shows default.txt)")
		}
	);
	
	/*
		SYSTEM FOR CHANGING WORKING RADIX
	*/

	private class RadixSettings
	{
		private static int workingRadix = 12;

		public static void changeWorkingRadix()
		{
			System.out.println("\n\nCurrent Working Radix: " + workingRadix + "\n");
			workingRadix = ConsolePrompts.promptInt("Please enter new working radix");
			applicationMenu.setProcede("Current Working Radix: " + RadixSettings.workingRadix);
		}

		public static String workingRadixToString()
		{
			return "Current Working Radix: " + workingRadix;
		}

		public static void displayWorkingRadix()
		{
			System.out.println("\n" + workingRadixToString());
		}
	}

	public static void main(String[] args)
	{
		appSettings.load();
		applicationMenu.setProcede(RadixSettings.workingRadixToString());
		applicationMenu.run();
	}


	/**********************************************************************************************************************************
	                                                           APPLICATION METHODS                                                     
	                                                           vvvvvvvvvvvvvvvvvvv                                                     
	**********************************************************************************************************************************/

	private static void run_decimalToBase()
	{
		int number;

		while(true)
		{
			Menu.clearConsole();
			RadixSettings.displayWorkingRadix();
			System.out.println("\nI will convert a positive integer from base-10 to the current working radix.");
			number = ConsolePrompts.promptInt("\nPlease enter the number you'd like to convert", "Please enter a positive integer", new Number[]{1, Double.POSITIVE_INFINITY});

			System.out.println("\n" + decimalToBase(number, RadixSettings.workingRadix) + "\n");
			if(!ConsolePrompts.promptYesOrNo("Would you like to try again for another number?"))
				break;
		}
	}

	private static void run_fractionToBase()
	{
		Fraction32 fraction;

		while(true)
		{
			Menu.clearConsole();
			RadixSettings.displayWorkingRadix();
			System.out.println("\nI will convert a fraction to its respective place value representation in the current working radix.");
			fraction = Fraction32.promptFraction("\nPlease enter the fraction you'd like to convert");

			System.out.println("\n" + fractionToBase(fraction, RadixSettings.workingRadix) + "\n");
			if(!ConsolePrompts.promptYesOrNo("Would you like to try again for another fraction?"))
				break;
		}
	}

	private static void run_stringToFraction()
	{
		String str;

		while(true)
		{
			Menu.clearConsole();
			RadixSettings.displayWorkingRadix();
			System.out.println("\nI will convert the place value representation of a number in the current working radix to a base-10 rational representation.");
			str = ConsolePrompts.promptString("Please enter the place value notation of the number you'd like to convert");
			
			while(true)
			{
				try
				{
					System.out.println("\n" + stringToFraction(str, RadixSettings.workingRadix) + "\n");
					break;
				}
				catch(IllegalArgumentException e)
				{
					str = ConsolePrompts.promptString("Invalid Input: failed to parse");
				}
			}

			if(!ConsolePrompts.promptYesOrNo("Would you like to try again for another place value notation?"))
				break;
		}
	}

	private static void run_placeValueToBase()
	{
		String str;

		while(true)
		{
			Menu.clearConsole();
			RadixSettings.displayWorkingRadix();
			System.out.println("\nI will convert a number from its place value representation in base-10 to its place value representation in the current working radix.");
			str = ConsolePrompts.promptString("\nPlease enter the place value notation of the number you'd like to convert");

			while(true)
			{
				try
				{
					System.out.println("\n" + placeValueToBase(str, RadixSettings.workingRadix) + "\n");
					break;
				}
				catch(IllegalArgumentException e)
				{
					str =ConsolePrompts.promptString("Invalid input: failed to parse");
				}
			}
			if(!ConsolePrompts.promptYesOrNo("Would you like to try again for another notation?"))
				break;
		}
	}

	private static String preferredNotation(PlaceValueNotation pvn)
	{
		String result;
		int b = pvn.getBase();

		if(b == 2 && settings[0].getCurrentOption() == 0) //NOTATION TYPE FOR BINARY >> Alphanumeric
			result = "0b" + pvn.alphanumeric();
		else if(b == 8 && settings[1].getCurrentOption() == 0) //NOTATION TYPE FOR OCTAL >> Alphanumeric
			result = "0o" + pvn.alphanumeric();
		else if(b == 16 && settings[2].getCurrentOption() == 0) //NOTATION TYPE FOR HEXADECIMAL >> Alphanumeric
			result = "0x" + pvn.alphanumeric();
		else if
		(
			b > 10 && settings[3].getCurrentOption() == 0 || //NOTATION TYPE FOR ALL OTHER RADICES FROM 11 TO 36 >> Alphanumeric
			b < 10 && settings[4].getCurrentOption() == 0 || //NOTATION TYPE FOR ALL OTHER RADICES LESS THAN 10 >> Alphanumeric
			b == 10
		)
			result = pvn.alphanumeric();
		else
			result = pvn.pseudodecimal();

		return result;
	}

	/**********************************************************************************************************************************
	                                                                PUBLIC METHODS                                                     
	                                                                vvvvvvvvvvvvvv                                                     
	**********************************************************************************************************************************/

	public static String decimalToBase(int n, int b)
	{
		PlaceValueNotation notation = new PlaceValueNotation(n, 1, b);
		return preferredNotation(notation);
	}
	 
	public static String fractionToBase(Fraction32 fraction, int b)
	{
		PlaceValueNotation notation = new PlaceValueNotation(fraction.getNumerator(), fraction.getDenominator(), b);
		return preferredNotation(notation);
	}

	public static String stringToFraction(String str, int b) throws IllegalArgumentException
	{
		return PlaceValueNotation.rationalize(str, b).toString();
	}

	public static String placeValueToBase(String str, int b) throws IllegalArgumentException
	{
		return fractionToBase(PlaceValueNotation.rationalize(str, 10), b);
	}
}