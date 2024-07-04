/*************************************************************
Contains static methods for handling data input and validation
including exception handling and range restriction
*************************************************************/
package utils.prompts;
import java.util.Scanner;
public class ConsolePrompts
{
	static Scanner scanner = new Scanner(System.in);

	/*
	To be called at the end of a do while loop. Return value of false should terminate the program.
	*/
	public static boolean promptRestart(String prompt, String exitMessage)
	{
		String response;
		boolean result;
		
		System.out.print(prompt + " (y/n)>> ");

		while(true)
		{
			response = scanner.nextLine().trim().toLowerCase();

			if(response.equals("y"))
			{
				result = true;
				System.out.print("\n\n");
				break;
			}
			else if(response.equals("n"))
			{
				result = false;
				System.out.print("\n" + exitMessage + "\n\n");
				break;
			}
			else
			{
				System.out.print("Please enter a valid response. (y/n) >> ");
			}
		}

		return result;
	}

	public static boolean promptRestart(String prompt)
	{
		return promptRestart(prompt, "OK, Goodbye!");
	}

	/*
	Prompts and returns a boolean.
	*/
	public static boolean promptYesOrNo(String prompt)
	{
		String response;
		boolean isYes;
	
		System.out.print(prompt + " (y/n)>> ");
		
		while(true)
		{
			response = scanner.nextLine().trim().toLowerCase();
			
			if(response.equals("y"))
			{
				isYes = true;
				break;
			}
			else if(response.equals("n"))
			{
				isYes = false;
				break;
			}
			else
			{
				System.out.print("Please enter a valid response. (y/n) >> ");
			}
		}
		
		return isYes;
	}

	/*
	Prompts and returns an 8-bit signed integer.
	*/
	public static byte promptByte(String prompt)
	{
		String response;
		byte result;
		System.out.print(prompt + " >> ");
		
		while(true)
		{
			response = scanner.nextLine().trim();
			
			try
			{
				result= Byte.parseByte(response);
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.print("Please enter a valid 8-bit signed integer >> ");
			}
		}
		
		return result;
	}

	public static byte promptByte(String prompt, String conditionErrorMessage, Number[]...ranges)
	{
		String response;
		byte result;
		System.out.print(prompt + " >> ");
		
		while(true)
		{
			response = scanner.nextLine().trim();
			
			try
			{
				result = Byte.parseByte(response);
				
				if(condition(result, ranges))
				{
					break;
				}
				else
				{
					System.out.print(conditionErrorMessage + " >> ");
				}
			}
			catch(NumberFormatException e)
			{
				System.out.print("Please enter a valid 8-bit signed integer >> ");
			}
		}
		
		return result;
	}

	/*
	Prompts and returns a 16-bit signed integer.
	*/
	public static short promptShort(String prompt)
	{
		String response;
		short result;
		System.out.print(prompt + " >> ");
		
		while(true)
		{
			response = scanner.nextLine().trim();
			
			try
			{
				result = Short.parseShort(response);
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.print("Please enter a valid 16-bit signed integer >> ");
			}
		}
		
		return result;
	}

	public static short promptShort(String prompt, String conditionErrorMessage, Number[]...ranges)
	{
		String response;
		short result;
		System.out.print(prompt + " >> ");
		
		while(true)
		{
			response = scanner.nextLine().trim();
			
			try
			{
				result = Short.parseShort(response);
				
				if(condition(result, ranges))
				{
					break;
				}
				else
				{
					System.out.print(conditionErrorMessage + " >> ");
				}
			}
			catch(NumberFormatException e)
			{
				System.out.print("Please enter a valid 16-bit signed integer >> ");
			}
		}
		
		return result;
	}

	/*
	Prompts and returns a 32-bit signed integer.
	*/
	public static int promptInt(String prompt)
	{
		String response;
		int result;
		System.out.print(prompt + " >> ");
		
		while(true)
		{
			response = scanner.nextLine().trim();
			
			try
			{
				result = Integer.parseInt(response);
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.print("Please enter a valid 32-bit signed integer >> ");
			}
		}
		
		return result;
	}

	public static int promptInt(String prompt, String conditionErrorMessage, Number[]...ranges)
	{
		String response;
		int result;
		System.out.print(prompt + " >> ");
		
		while(true)
		{
			response = scanner.nextLine().trim();
			
			try
			{
				result = Integer.parseInt(response);
				
				if(condition(result, ranges))
				{
					break;
				}
				else
				{
					System.out.print(conditionErrorMessage + " >> ");
				}
			}
			catch(NumberFormatException e)
			{
				System.out.print("Please enter a valid 32-bit signed integer >> ");
			}
		}
		
		return result;
	}

	/*
	Prompts and returns a 64-bit signed integer.
	*/
	public static long promptLong(String prompt)
	{
		String response;
		long result;
		System.out.print(prompt + " >> ");
		
		while(true)
		{
			response = scanner.nextLine().trim();
			
			try
			{
				result = Long.parseLong(response);
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.print("Please enter a valid 64-bit signed integer >> ");
			}
		}
		
		return result;
	}

	public static long promptLong(String prompt, String conditionErrorMessage, Number[]...ranges)
	{
		String response;
		long result;
		System.out.print(prompt + " >> ");
		
		while(true)
		{
			response = scanner.nextLine().trim();
			
			try
			{
				result = Long.parseLong(response);
				
				if(condition(result, ranges))
				{
					break;
				}
				else
				{
					System.out.print(conditionErrorMessage + " >> ");
				}
			}
			catch(NumberFormatException e)
			{
				System.out.print("Please enter a valid 64-bit signed integer >> ");
			}
		}
		
		return result;
	}

	/*
	Prompts and returns a 32-bit floating point number.
	*/
	public static float promptFloat(String prompt)
	{
		String response;
		float result;
		System.out.print(prompt + " >> ");
		
		while(true)
		{
			response = scanner.nextLine().trim();
			
			try
			{
				result = Float.parseFloat(response);
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.print("Please enter a valid 32-bit floating point number >> ");
			}
		}
		
		return result;
	}

	public static float promptFloat(String prompt, String conditionErrorMessage, Number[]...ranges)
	{
		String response;
		float result;
		System.out.print(prompt + " >> ");
		
		while(true)
		{
			response = scanner.nextLine().trim();
			
			try
			{
				result = Float.parseFloat(response);
				
				if(condition(result, ranges))
				{
					break;
				}
				else
				{
					System.out.print(conditionErrorMessage + " >> ");
				}
			}
			catch(NumberFormatException e)
			{
				System.out.print("Please enter a valid 32-bit floating point number >> ");
			}
		}
		
		return result;
	}

	/*
	Prompts and returns a 64-bit floating point number.
	*/
	public static double promptDouble(String prompt)
	{
		String response;
		double result;
		System.out.print(prompt + " >> ");
		
		while(true)
		{
			response = scanner.nextLine().trim();
			
			try
			{
				result = Double.parseDouble(response);
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.print("Please enter a valid 64-bit floating point number >> ");
			}
		}
		
		return result;
	}

	public static double promptDouble(String prompt, String conditionErrorMessage, Number[]...ranges)
	{
		String response;
		double result;
		System.out.print(prompt + " >> ");
		
		while(true)
		{
			response = scanner.nextLine().trim();
			
			try
			{
				result = Double.parseDouble(response);
				
				if(condition(result, ranges))
				{
					break;
				}
				else
				{
					System.out.print(conditionErrorMessage + " >> ");
				}
			}
			catch(NumberFormatException e)
			{
				System.out.print("Please enter a valid 64-bit floating point number >> ");
			}
		}
		
		return result;
	}
	
	/*
	Prompts and returns a character of integral data type.
	*/
	public static char promptChar(String prompt)
	{
		String response;
		char result;
		System.out.print(prompt + " >> ");

		while(true)
		{
			response = scanner.nextLine().trim();
			
			if(response.length() != 1)
			{
				System.out.print("Please enter a single character >> ");
			}
			else
			{
				result = response.charAt(0);
				break;
			}
		}

		return result;
	}

	public static String promptString(String prompt)
	{
		System.out.print(prompt + " >> ");
		return scanner.nextLine();
	}

	/*
	Prompts and returns several values formatted as an
	array of strings, useful for prompting several fields
	of data on a single console line. Parsing and exception
	handling must be handled by the method or class calling
	promptStringArray().

	I've used this only once before and found the need
	for exception handling on the front end tedious and
	frustrating, and entirely counterproductive to the
	goal I was trying to achieve by writing this package;
	to handle all exceptions and input errors automatically.

	I've read about hash maps recently and may return to this
	method in the future when I understand them better, as
	I believe they may improve the usability of this method.
	*/
	public static String[] promptStringArray(String prompt)
	{
		String[] result;
		System.out.print(prompt + " >> ");
		result = scanner.nextLine().split(",\\s*");
		return result;
	}

	public static String[] promptStringArray(String prompt, int arrayLength)
	{
		String[] result;
		System.out.print(prompt + " >> ");

		while(true)
		{
			result = scanner.nextLine().split(",\\s*");

			if(result.length != arrayLength)
			{
				System.out.print("Please enter the correct number of items >> ");
			}
			else
			{
				break;
			}
		}
		
		return result;
	}

	/*
	Checks if a given numerical input falls within a set of specified ranges
	*/
	private static boolean condition(Number numericalInput, Number[]... ranges) throws IllegalArgumentException
	{
		boolean result = false;
		
		double input = numericalInput.doubleValue();
		
		for(int i = 0; i < ranges.length; i++)
		{
			//Checks for illegal bounds.
			if(ranges[i][0].equals(Double.POSITIVE_INFINITY))
				throw new IllegalArgumentException("Invalid lower bound.");
			else if(ranges[i][0].equals(Double.NEGATIVE_INFINITY))
				throw new IllegalArgumentException("Invalid upper bound.");
			else if(ranges[i][0].doubleValue() > ranges[i][1].doubleValue())
				throw new IllegalArgumentException("Ranges out of order: lower bound greater than upper bound.");

			//Evaluates condition.
			else if(ranges[i][0].equals(Double.NEGATIVE_INFINITY))
				result |= input <= ranges[i][1].doubleValue();
			else if(ranges[i][1].equals(Double.POSITIVE_INFINITY))
				result |= input >= ranges[i][0].doubleValue();
			else
				result |= input >= ranges[i][0].doubleValue() && input <= ranges[i][1].doubleValue();
		}
		
		return result;
	}
}