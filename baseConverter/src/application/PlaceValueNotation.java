package application;

import java.util.ArrayList;
import utils.alg32.*;
public class PlaceValueNotation extends Fraction32
{
	private int base;

	private int[] wholeDigits;
	private int[] fractionalDigits;
	private int indexOfRepetition;

	//CONSTRUCTORS
	public PlaceValueNotation(int n, int d, int b)
	{
		super(n, d);
		if(base <= 2)
			base = b;
		else
			throw new IllegalArgumentException("Illegal base: base must be no less than 2.");
		populateDigits();
	}

	public PlaceValueNotation(int n, int d)
	{
		super(n, d);
		base = 10;
		populateDigits();
	}

	public PlaceValueNotation(int n)
	{
		super(n);
	}

	//ACCESSOR METHODS
	public int getBase()
	{
		return base;
	}

	public int[] getWholeDigits()
	{
		return wholeDigits;
	}

	public int[] getFractionalDigits()
	{
		return fractionalDigits;
	}

	public int getIndexOfRepetition()
	{
		return indexOfRepetition;
	}

	//CLASS METHODS
	public static Fraction32 rationalize(String str, int b) throws IllegalArgumentException
	{
		Fraction32 result = new Fraction32();
		String[] parts = str.split("\\.");
		char[] wholeDigits;
		char[] fractionalDigits;

		wholeDigits = parts[0].toCharArray();
		int exp = wholeDigits.length -1;
		for(int i = 0; i < wholeDigits.length; i++)
		{
			result = Fraction32.add(result, new Fraction32(parseAlphanumeric(wholeDigits[i], b) * (int)Math.pow(b, exp)));
			exp--;
		}
		if(parts.length == 2)
		{
			fractionalDigits = parts[1].toCharArray();
			for(int i = 1; i <= fractionalDigits.length; i++)
			{
				result = Fraction32.add(result, new Fraction32(parseAlphanumeric(fractionalDigits[i - 1], b), (int)Math.pow(b, i)));
			}
		}
		return result;
	}

	//INSTANCE METHODS
	public String alphanumeric()
	{
		String result = "";

		if(base <= 36)
		{
			for(int i = 0; i < wholeDigits.length; i++)
				result += toAlphanumeric(wholeDigits[i]);
			if(!(fractionalDigits.length == 1 && fractionalDigits[0] == 0))
			{
				result += ".";
				for(int i = 0; i < fractionalDigits.length; i++)
				{
					if(i == indexOfRepetition)
						result += "%";
					result += toAlphanumeric(fractionalDigits[i]);
				}
			}
		}
		else
			result = pseudodecimal();

		return result;
	}

	public String pseudodecimal()
	{
		String result = "";
		String formatSpecifier = "%0" + (int)(Math.log10(base) + 1) + "d";

		for(int i = 0; i < wholeDigits.length; i++)
		{
			if(i != 0)
				result += ":";
			result += String.format(formatSpecifier, wholeDigits[i]);
		}
		if(!(fractionalDigits.length == 1 && fractionalDigits[0] == 0))
		{
			for(int i = 0; i < fractionalDigits.length; i++)
			{
				if(i == 0)
					result += "|";
				if(i == indexOfRepetition)
					result += "%";
				else if(i != 0)
					result += ":";
				result += String.format(formatSpecifier, fractionalDigits[i]);
			}
		}

		return result;
	}

	//PRIVATE METHODS
	private void populateDigits()
	{
		int dividend, divisor, quotient;
		ArrayList<Integer> previousDividends = new ArrayList<>();
		ArrayList<Integer> leftDigits = new ArrayList<>();
		ArrayList<Integer> rightDigits = new ArrayList<>();
		int magnitude = 0;
		int repeatingIndex = -1;

		//initializing variables
		Fraction32 dividendRational = new Fraction32(getNumerator());
		divisor = getDenominator();

		while(dividendRational.compareTo(base).getNumerator() <= 0)
		{
			dividendRational.divideBy(base);
			magnitude++;
		}

		//collecting digits
		while(true)
		{
			dividend = dividendRational.toInt();
			quotient = dividend / divisor;

			if(magnitude < 0)
				previousDividends.add(dividend);

			dividendRational.decreaseBy(quotient * divisor);
			dividendRational.multiplyBy(base);
			dividend = dividendRational.toInt();
			if(magnitude >= 0)
				leftDigits.add(quotient);
			else if(previousDividends.indexOf(dividend) != -1)
			{
				rightDigits.add(quotient);
				repeatingIndex = previousDividends.indexOf(dividend);
				break;
			}
			else if(dividend != 0)
				rightDigits.add(quotient);
			else
			{
				rightDigits.add(quotient);
				break;
			}
			magnitude--;
		}

		while(leftDigits.get(0) == 0 && leftDigits.size() > 1)
			leftDigits.remove(0);

		wholeDigits = leftDigits.stream().mapToInt(Integer::intValue).toArray();
		fractionalDigits = rightDigits.stream().mapToInt(Integer::intValue).toArray();
		indexOfRepetition = repeatingIndex;
	}

	private static char toAlphanumeric(int n) throws IllegalArgumentException
	{
		char result;
		if(n < 0 || n > 36)
			throw new IllegalArgumentException("Integer out of bounds for alphanumeric conversion");

		if(n < 10)
			result = (char)(n + 48);
		else
			result = (char)(n + 55);

		return result;
	}

	private static int parseAlphanumeric(char c, int b) throws IllegalArgumentException
	{
		int result;

		c = Character.toUpperCase(c);

		if(Character.isDigit(c) && c - 48 < b)
			result = c - 48;
		else if(Character.isLetter(c) && c - 55 < b)
			result = c - 55;
		else
			throw new IllegalArgumentException("Character must be a digit 0-9 or a letter A-Z");
		return result;
	}
}