package utils;

import java.math.BigInteger;
import static java.math.BigInteger.*;
public class NumberNamer
{
	static String[] unitsNames = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
	static String[] teensNames = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
	static String[] tensNames = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninty"};
	static String[] smallMagNames = {"m", "b", "tr", "quadr", "quint", "sext", "sept", "oct", "non"};
	static String[] unitMagNames = {"un", "duo", "tre", "quattuor", "quin", "se", "septe", "octo", "nove"};
	static String[] tensMagNames = {"dec", "vi", "tri", "quadra", "quinqua", "sexa", "septua", "octo", "nona"};
	static String[] hundredsMagNames = {"cent", "ducent", "trecent", "quadringent", "quingent", "sescent", "septingent", "octingent", "nongent"};

	public static String nameNumber(String number)
	{
		return nameNumber(new BigInteger(number));
	}

	public static String nameNumber(long number)
	{
		return nameNumber(new BigInteger(String.valueOf(number)));
	}

	public static String nameNumber(BigInteger number)
	{
		BigInteger currentNumber = number;
		int magValue;
		int numberMagnitude = magnitude(number);
		String result = "";
		
		//Unique condition for zero...
		if(number.signum() == 0)
		{
			return "zero";
		}

		//Breaks number into its composite magnitudes and names them based on the value at each magnitude...
		int cache;
		for(int i = numberMagnitude; i >= -1; i--)
		{
			cache = magnitudeValue(currentNumber);//Calculate the value of the current magnitude
			if(magnitude(currentNumber) == i && cache != 0)//Procede only if the current magnitude has a nonzero value...
			{
				magValue = cache;

				if(i != numberMagnitude)//Add a space for all but the first loop...
					result += " ";
				
				result += modThousandName(magValue);
				if(i > -1)//Assign magnitude name for all magnitudes with a value greater than -1 and for all but the last loop...
					result += " " + magnitudeName(i);
			
				//Subtract the current magnitude and start the next loop on the next lowest magnitude...
				currentNumber = currentNumber.subtract(TEN.pow(3 * (i + 1)).multiply(new BigInteger(String.valueOf(magValue))));
			}
		}
		
		return result;
	}

	//Names numbers up to but not including 100.
	static String modHundredName(int n)
	{
		if(n < 10)
		{
			return unitsNames[n - 1];
		}
		else if(n < 20)
		{
			return teensNames[n % 10];
		}
		else
		{
			String result = tensNames[(n / 10) - 2];
			if(n % 10 != 0)
			{
				result += "-" + unitsNames[(n % 10) -1];
			}
		
			return result;
		}
	}

	//Names numbers up to but not including 1,000.
	static String modThousandName(int n)
	{
		if(n < 100)
		{
			return modHundredName(n);
		}
		else
		{
			String result = unitsNames[(n / 100) -1] + " hundred";
			if(n % 100 != 0)
			{
				result += " " + modHundredName(n % 100);
			}
			
			return result;
		}
	}
	
	//Constructs name 
	public static String magnitudeName(int n)
	{
		if(n == 0)
			return "thousand";

		String result = "";

		if(n > -1 && n < 10)
			result += smallMagNames[n - 1];
		else
		{
			//divide magnitude into its constituent place values
			int units = n % 10;
			int tens = n % 100 / 10;
			int hundreds = n % 1000 / 100;

			if(units > 0)
			{
				//check for phonetic padding
				boolean bS = false, bX = false, bN = false, bM = false;

				if(hundreds > 0 && tens == 0) //padding between units and hundreds
				{
					if(hundreds <= 7) bN = true;
					if(hundreds >= 3 && hundreds <= 5) bS = true;
					if(hundreds == 1) bX = true;
					if(hundreds == 8) {bM = true; bX = true;}
				}
				else if(tens > 0) //padding between units and tens
				{
					if(tens <= 7 && tens != 2) bN = true;
					if(tens >= 2 && tens <= 5) bS = true;
					if(tens == 2) {bM = true; bS = true;}
					if(tens == 8) {bM = true; bX = true;}
				}

				result += unitMagNames[units - 1];

				//add padding.
				if(units == 3 && (bS || bX) || units == 6 && bS) result += "s";
				else if(units == 6 && bX) result += "x";
				else if((units == 7 || units == 9) && bN) result += "n";
				else if((units == 7 || units == 9) && bM) result += "m";
			}
			if(tens > 0)
			{
				result += tensMagNames[tens - 1];
				if(tens > 1) result += "gint";
			}
			if(hundreds > 0)
			{
				if(tens == 1 || tens == 2) result += "i"; else if(tens != 0) result += "a";
				result += hundredsMagNames[hundreds - 1];
			}
		}

		result += "illion";
		return result;
	}

	//Returns the magnitude of a number (thousands >> 0, millions >> 1, billions >> 2, trillions >> 3, etc...)
	public static int magnitude(BigInteger number)
	{
		int numberOfDigits = number.toString().length();
		return (numberOfDigits - 1) / 3 - 1; 
	}

	//Returns the value of a given number's magnitude (37 thousand >> 37, 961 trillion >> 961, etc...)
	public static int magnitudeValue(BigInteger number)
	{
		return number.divide(TEN.pow(3 * magnitude(number) + 3)).intValue();
	}
}