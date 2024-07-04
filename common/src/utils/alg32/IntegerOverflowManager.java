package utils.alg32;

import utils.alg32.IntegerOverflowException;

public class IntegerOverflowManager
{
	public static int add(int a, int b) throws IntegerOverflowException
	{
		if((a > 0 && b > 0 && a + b < 0) || (a < 0 && b < 0 && a + b > 0))
			throw new IntegerOverflowException("Magnitude of sum too large to be stored in a 32-bit signed integer");
		return a + b;
	}

	public static int multiply(int a, int b) throws IntegerOverflowException
	{
		if((a > 0 && a > Integer.MAX_VALUE / b) || (a < 0 && a < Integer.MIN_VALUE / b))
			throw new IntegerOverflowException("Magnitude of product too large to be stored in a 32-bit signed integer");
		return a * b;
	}

	public static int negate(int n)
	{
		if(n == Integer.MIN_VALUE)
			throw new IntegerOverflowException("Cannot negate minimum integer");
		return n * -1;
	}
}