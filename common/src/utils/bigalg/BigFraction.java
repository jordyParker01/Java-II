package utils.bigalg;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.Objects;
import utils.bigalg.*;
import utils.prompts.*;
public class BigFraction implements Comparable<BigFraction>, Promptable
{
	//INSTANCE FIELDS
	private BigInteger numerator;
	private BigInteger denominator;

	//CLASS CONSTANTS
	static BigFraction ZERO = new BigFraction();
	static BigFraction ONE = new BigFraction(1);
	static BigFraction	NEGATIVE_ONE = new BigFraction(-1);

	/*
		CONSTRUCTORS
	*/

	public BigFraction(int n, int d)
	{
		setNumerator(n);
		setDenominator(d);
		reduce();
	}

	public BigFraction(BigInteger n, BigInteger d)
	{
		setNumerator(n);
		setDenominator(d);
		reduce();
	}

	public BigFraction(BigInteger n, int d)
	{
		setNumerator(n);
		setDenominator(d);
		reduce();
	}

	public BigFraction(int n, BigInteger d)
	{
		setNumerator(n);
		setDenominator(d);
		reduce();
	}

	public BigFraction(int n)
	{
		this(n, 1);
	}

	public BigFraction(BigInteger n)
	{
		this(n, 1);
	}

	public BigFraction()
	{
		this(0, 1);
	}

	/*
		ACCESSOR METHODS
	*/

	public BigInteger getNumerator()
	{
		return numerator;
	}

	public BigInteger getDenominator()
	{
		return denominator;
	}

	/*
		MUTATOR METHODS
	*/

	private void setNumerator(int numerator)
	{
		this.numerator = BigInteger.valueOf(numerator);
	}

	private void setNumerator(BigInteger numerator)
	{
		this.numerator = numerator;
	}

	private void setDenominator(int denominator) throws NumberFormatException
	{
		if(denominator != 0)
			this.denominator = BigInteger.valueOf(denominator);
		else
			throw new NumberFormatException("Zero Divisor");
	}

	private void setDenominator(BigInteger denominator)
	{
		if(denominator.compareTo(BigInteger.ZERO) != 0) //denominator != 0
			this.denominator = denominator;
		else
			throw new NumberFormatException("Zero Divisor");
	}

	/*
		CLASS METHODS
	*/

	public static BigFraction parseDecimal(String input) throws NumberFormatException
	{
		BigFraction result = ZERO;

		String[] parts = input.split(".");
		if(parts.length > 2)
			throw new NumberFormatException("Invalid number of elements found while parsing decimal");
		else if(parts.length == 1)
		{
			BigInteger n = new BigInteger(parts[0].trim());
			result = new BigFraction(n);
		}
		else
		{
			BigInteger n = new BigInteger(parts[0].trim());
			result = new BigFraction(n);
			int[] digits = new int[parts[1].length()];

			int valueOfDigit;
			BigFraction addend;
			for(int i = 0; i < parts[1].length(); i++)
			{
				valueOfDigit = Integer.parseInt(parts[1].charAt(i) + "");
				addend = new BigFraction(valueOfDigit, (int)Math.pow(10, i + 1));
				result = add(result, addend);
			}
			
		}

		return result;
	}

	public static BigFraction add(BigFraction a, BigFraction b)
	{
		BigInteger lcm = BigNumberTheory.LCM(a.denominator, b.denominator);
		BigFraction result;

		BigInteger numA = a.numerator.multiply(lcm).divide(a.denominator);
		BigInteger numB = b.numerator.multiply(lcm).divide(b.denominator);

		return new BigFraction(numA.add(numB), lcm);
	}

	public static BigFraction addMany(BigFraction... fractions)
	{
		BigFraction result = ZERO;

		for(BigFraction fraction : fractions)
		{
			result = add(result, fraction);
		}

		return result;
	}

	public static BigFraction multiply(BigFraction a, BigFraction b)
	{
		BigInteger n = a.numerator.multiply(b.numerator);
		BigInteger d = a.denominator.multiply(b.denominator);
		return new BigFraction(n, d);
	}

	public static BigFraction multiplyMany(BigFraction... fractions)
	{
		BigFraction result = ONE;

		for(BigFraction fraction : fractions)
		{
			result = multiply(result, fraction);
		}

		return result;
	}

	public static BigFraction max(BigFraction a, BigFraction b)
	{
		return a.compareTo(b) >= 0 ? a : b;
	}

	public static BigFraction min(BigFraction a, BigFraction b)
	{
		return a.compareTo(b) <= 0 ? a : b;
	}

	/*
		INSTANCE METHODS
		LOSSY TYPE CONVERSIONS
	*/

	public long toLong() throws ArithmeticException
	{
		return numerator.longValueExact() / denominator.longValueExact();
	}

	public int toInt() throws ArithmeticException
	{
		return numerator.intValueExact() / denominator.intValueExact();
	}

	public short toShort() throws ArithmeticException
	{
		return (short)(numerator.shortValueExact() / denominator.shortValueExact());
	}

	public byte toByte() throws ArithmeticException
	{
		return (byte)(numerator.byteValueExact() / denominator.byteValueExact());
	}

	public float toFloat()
	{
		return numerator.floatValue() / denominator.floatValue();
	}

	public double toDouble()
	{
		return numerator.doubleValue() / denominator.doubleValue();
	}

	/*
		INSTANCE METHODS
		LOSSLESS TYPE CONVERSIONS
	*/

	public long toLongExact() throws ArithmeticException
	{
		if(!isInt())
			throw new ArithmeticException("Lossless conversion not possible");
		else
			return numerator.longValueExact() / denominator.longValueExact();
	}

	public int toIntExact() throws ArithmeticException
	{
		if(!isInt())
			throw new ArithmeticException("Lossless conversion not possible");
		else
			return numerator.intValueExact() / denominator.intValueExact();
	}

	public short toShortExact() throws ArithmeticException
	{
		if(!isInt())
			throw new ArithmeticException("Lossless conversion not possible");
		else
			return (short)(numerator.shortValueExact() / denominator.shortValueExact());
	}

	public byte toByteExact() throws ArithmeticException
	{
		if(!isInt())
			throw new ArithmeticException("Lossless conversion not possible");
		else
			return (byte)(numerator.byteValueExact() / denominator.byteValueExact());
	}

	/*
		INSTANCE METHODS
		NUMBER THEORY
	*/

	public BigFraction abs()
	{
		return new BigFraction(numerator.abs(), denominator);
	}

	public BigFraction negative()
	{
		return new BigFraction(numerator.negate(), denominator);
	}

	public BigFraction inverse()
	{
		return new BigFraction(denominator, numerator);
	}

	public int signum()
	{
		return numerator.signum();
	}

	public boolean isInt()
	{
		return numerator.remainder(denominator) != BigInteger.ZERO;
	}

	public boolean isImproper()
	{
		return numerator.compareTo(denominator) == 1 && !isInt();
	}

	/*
		OVERRIDDEN METHODS
	*/

	@Override
	public int compareTo(BigFraction other)
	{
		BigInteger lcm = BigNumberTheory.LCM(this.denominator, other.denominator);

		BigInteger numThis = this.numerator.multiply(lcm).divide(this.denominator);
		BigInteger numOther = other.numerator.multiply(lcm).divide(other.denominator);

		return numThis.compareTo(numOther);
	}

	@Override
	public boolean equals(Object  other)
	{
		if(this == other) return true;
		if(other == null || getClass() != other.getClass()) return false;

		BigFraction fraction = (BigFraction)other;
		return numerator.equals(fraction.numerator) && denominator.equals(fraction.denominator);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(numerator, denominator);
	}

	@Override
	public String toString()
	{
		String result;
		if(!denominator.equals(BigInteger.ONE))
			result = numerator + "/" + denominator;
		else
			result = String.valueOf(this.numerator);
		return result;
	}

	@Override
	public void parse(String input) throws NumberFormatException
	{
		BigInteger n = null;
		BigInteger d = null;

		String[] values = input.split("/");

		if(values.length > 2)
			throw new NumberFormatException("Invalid number of elements found while parsing fraction");
		else if(values.length == 1)
		{
			n = new BigInteger(values[0].trim());
			d = BigInteger.ONE;
		}
		else
		{
			n = new BigInteger(values[0].trim());
			d = new BigInteger(values[1].trim());
		}

		numerator = n;
		denominator = n;
	}

	/*
		PRIVATE METHODS
	*/

	private void reduce()
	{
		BigInteger gcd = BigNumberTheory.GCD(numerator, denominator);
		numerator = numerator.divide(gcd);
		denominator = denominator.divide(gcd);

		if(denominator.signum() == -1)
		{
			numerator = numerator.negate();
			denominator = denominator.negate();
		}
	}
}