/************************************************************
Contains only default methods. Implementing class
should override either parse() or prompt().
Although it is not intended, one my also override both.
************************************************************/
package utils.prompts;

import java.util.Scanner;
public interface Promptable
{
	/*
		Parse input and return object.
		For gathering all field values from a single prompt and input.
		Ideal for classes with few field values that can be represented
		with simple, intuitive String representations.
	*/
	default void parse(String input)
	{
	}

	/*
		Prompt input and return object.
		For prompting field values one at a time.
		Ideal for classes with many field values that cannot be
		represented with a simple, intuitive String representation.
	*/
	default void prompt()
	{
	}

	/*
		Prompt input and return object.
		Should only be used if parse() is overridden in implementing class,
		otherwise prompt() must be overridden and used instead.
	*/
	default void prompt(String prompt)
	{
		Scanner scanner = new Scanner(System.in);
		String input;

		System.out.print(prompt + " >> ");

		while(true)
		{
			input = scanner.nextLine().trim();

			try
			{
				parse(input);
				break;
			}
			catch(Exception e)
			{
				System.out.print("Invalid input >> ");
			}
		}
	}
}