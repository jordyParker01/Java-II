package application;

import utils.prompts.ConsolePrompts;
import utils.menu.*;
import utils.settings.*;
import java.util.*;
public class PrimeNumberGenerator
{
	static Setting[] settings = new Setting[]
	{
		new Setting("Method for Displaying Prime Factorization", 1,
			new String[]
			{
				"Displays as Numbered List",
				"Displays in Exponential Form"
			}
		),

		new Setting("Method for Displaying Complete Factorization", 1,
			new String[]
			{
				"Displays as Numbered List",
				"Displays in Factor Pairs"
			}
		),

		new Setting("Method for Factoring Numbers Found Not to Be Prime", 1,
			new String[]
			{
				"Displays Prime Factorization",
				"Displays Complete Factorization"
			}
		)
	};

	static ApplicationSettings appSettings = new ApplicationSettings(settings, "C:\\Users\\Jordy\\Java Program Files\\Prime Number Generator\\settings.txt");

	static FunctionalMenu applicationMenu = new FunctionalMenu
	(
		new Utility[]
		{
			new Utility(PrimeNumberGenerator::run_primes, "Generate the first n primes"),
			new Utility(PrimeNumberGenerator::run_primesUpTo, "Generate all the primes up to n"),
			new Utility(PrimeNumberGenerator::run_primeFactors, "Generate the prime factorization of a given number"),
			new Utility(PrimeNumberGenerator::run_factors, "Generate all the factors of a given number"),
			new Utility(PrimeNumberGenerator::run_isPrime, "Check if a given number is prime"),
			new Utility(appSettings::viewSettings, "View settings")
		}
	);

	//Application
	public static void main(String[] args)
	{
		appSettings.load();
		applicationMenu.run();
	}
	
	/******************************************************************************************************************************************************
	                                                                  APPLICATION METHODS                                                                  
	                                                                  vvvvvvvvvvvvvvvvvvv                                                                  
	******************************************************************************************************************************************************/

	private static void run_primes()
	{
		while(true)
		{
			Menu.clearConsole();
			int n = ConsolePrompts.promptInt
			(
				"\nI will generate the first n primes. What shall n be?",
				"Please enter a value greater than 0 for n.",
				new Number[]{1, Double.POSITIVE_INFINITY}
			);
			ArrayList<Integer> primes = primes(n);
			int index = 1;
			System.out.println();
			displayList(primes);
			if(!ConsolePrompts.promptYesOrNo("\nDo you want to try again for a different value of n?"))
				break;
		}
	}

	private static void run_primesUpTo()
	{
		while(true)
		{
			Menu.clearConsole();
			int n = ConsolePrompts.promptInt
			(
				"\nI will generate all the primes up to n. What shall n be?",
				"Please enter a value greater than 0 for n.",
				new Number[]{1, Double.POSITIVE_INFINITY}
			);
			ArrayList<Integer> primes = primesUpTo(n);
			int index = 1;
			System.out.println();
			displayList(primes);
			if(!ConsolePrompts.promptYesOrNo("\nDo you want to try again for a different value of n?"))
				break;
		}
	}

	private static void run_primeFactors()
	{
		while(true)
		{
			Menu.clearConsole();
			int n = ConsolePrompts.promptInt
			(
				"\nI will find the prime factors for n. What shall n be?",
				"Please enter a value greater than 0 for n.",
				new Number[]{1, Double.POSITIVE_INFINITY}
			);

			if(settings[0].getCurrentOption() == 0)
			{
				ArrayList<Integer> factors = primeFactorsList(n);
				displayList(factors);
			}
			else
			{
				Map<Integer, Integer> factorsMap = primeFactorsHash(n);
				displayPrimeFactors(factorsMap);
			}
			if(!ConsolePrompts.promptYesOrNo("\nDo you want to try again for a different value of n?"))
				break;
		}
	}

	private static void run_factors()
	{
		while(true)
		{
			Menu.clearConsole();
			int n = ConsolePrompts.promptInt
			(
				"\nI will find all the factors of n. What shall n be?",
				"Please enter a value greater than 0 for n.",
				new Number[]{1, Double.POSITIVE_INFINITY}
			);

			if(settings[1].getCurrentOption() == 0)
			{
				ArrayList<Integer> factors = factorsList(n);
				displayList(factors);
			}
			else
			{
				Map<Integer, Integer> factorsMap = factorsHash(n);
				displayFactorsAsPairs(factorsMap);
			}
			if(!ConsolePrompts.promptYesOrNo("\nDo you want to try again for a different value of n?"))
				break;
		}
	}

	private static void run_isPrime()
	{
		while(true)
		{
			Menu.clearConsole();
			int n = ConsolePrompts.promptInt
			(
				"\nI will check if a given number, n, is prime. What shall n be?",
				"Please enter a value greater than 0 for n.",
				new Number[]{1, Double.POSITIVE_INFINITY}
			);
			boolean isPrime = isPrime(n);

			System.out.println();
			if(isPrime)
				System.out.println(n + " is indeed prime.");
			else
			{
				System.out.println(n + " is not prime, it is composite.");

				//METHOD FOR DISPLAYING NUMBERS FOUND NOT TO BE PRIME >> Displays prime factorization
				if(settings[2].getCurrentOption() == 0)
				{
					if(ConsolePrompts.promptYesOrNo("Would you like to see the prime factorization of " + n + "?"))
					{
						//METHOD FOR DISPLAYING PRIME FACTORIZATION >> Displays as numbered list
						if(settings[0].getCurrentOption() == 0)
							displayList(primeFactorsList(n));
						//METHOD FOR DISPLAYING PRIME FACTORIZATION >> Displays in exponential form
						else
							displayPrimeFactors(primeFactorsHash(n));
					}
				}
				//METHOD FOR DISPLAYING NUMBERS FOUND NOT TO BE PRIME >> Displays complete factorization
				else
				{
					if(ConsolePrompts.promptYesOrNo("Would you like to see the complete list of factors for " + n + "?"))
					{
						//METHOD FOR DISPLAYING COMPLETE FACTORIZATION >> Displays as numbered list
						if(settings[1].getCurrentOption() == 0)
							displayList(factorsList(n));
						//METHOD FOR DISPLAYING COMPLETE FACTORIZATION >> Displays in factor pairs
						else
							displayFactorsAsPairs(factorsHash(n));
					}
				}
			}
			if(!ConsolePrompts.promptYesOrNo("\nDo you want to try again for a different value of n?"))
				break;
		}
	}
	
	/******************************************************************************************************************************************************
	                                                                    DISPLAY METHODS                                                                    
	                                                                    vvvvvvvvvvvvvvv                                                                    
	******************************************************************************************************************************************************/

	private static void displayPrimeFactors(Map<Integer, Integer> factorsMap)
	{
		System.out.println();
		for(Map.Entry<Integer, Integer> entry : factorsMap.entrySet())
		{
			System.out.print(entry.getKey());
			if(entry.getValue() != 1)
				System.out.println("^" + entry.getValue());
			else
				System.out.println();
		}
	}

	private static void displayFactorsAsPairs(Map<Integer, Integer> factorsMap)
	{
		System.out.println();
		for(Map.Entry<Integer, Integer> entry : factorsMap.entrySet())
		{
			System.out.println(entry.getKey() + " * " + entry.getValue());
		}
	}

	private static void displayList(ArrayList<Integer> list)
	{
		int index = 1;
		System.out.println();
		for(int item: list)
		{
			System.out.println(index + ": " + item);
			index++;
		}
	}

	/******************************************************************************************************************************************************
	                                                                    PUBLIC METHODS                                                                     
	                                                                    vvvvvvvvvvvvvv                                                                     
	******************************************************************************************************************************************************/

	public static ArrayList<Integer> primes(int n)
	{
		ArrayList<Integer> primes = new ArrayList<>();
		primes.add(2);
		int currentNumber = 3;
		while(primes.size() < n)
		{
			boolean isPrime = true;
			for(int prime : primes)
			{
				if(currentNumber % prime == 0)
				{
					isPrime = false;
					break;
				}
			}
			if(isPrime)
			{
				primes.add(currentNumber);
			}
			currentNumber++;
		}

		return primes;
	}

	public static ArrayList<Integer> primesUpTo(int n)
	{
		ArrayList<Integer> primes = new ArrayList<Integer>();
		primes.add(2);
		int currentNumber = 2;
		while(currentNumber <= n)
		{
			boolean isPrime = true;
			for(int prime : primes)
			{
				if(currentNumber % prime == 0)
				{
					isPrime = false;
					break;
				}
			}
			if(isPrime)
			{
				primes.add(currentNumber);
			}
				currentNumber++;
		}

		return primes;
	}

	public static Map<Integer, Integer> primeFactorsHash(int n)
	{
		ArrayList<Integer> primesUpToN = primesUpTo(n);
		Map<Integer, Integer> primeFactors = new HashMap<>();
		for(int prime : primesUpToN)
		{
			while(n % prime == 0)
			{
				primeFactors.put(prime, primeFactors.getOrDefault(prime, 0) +1);
				n /= prime;
			}
		}

		return primeFactors;
	}
	
	public static ArrayList<Integer> primeFactorsList(int n)
	{
		ArrayList<Integer> primesUpToN = primesUpTo(n);
		ArrayList<Integer> primeFactors = new ArrayList<Integer>();
		for(int prime : primesUpToN)
		{
			while(n % prime == 0)
			{
				primeFactors.add(prime);
				n /= prime;
			}
		}

		return primeFactors;
	}

	public static Map<Integer, Integer> factorsHash(int n)
	{
		ArrayList<Integer> factors = new ArrayList<Integer>();
		Map<Integer, Integer> factorMap = new HashMap<>();
		for(int i = 1; i <= n; i++)
		{
			if(factors.contains(i))
			{
				break;
			}
			else if(n % i == 0)
			{
				factorMap.put(i, n / i);
				factors.add(i);
				factors.add(n / i);
			}
		}

		return factorMap;
	}

	public static ArrayList<Integer> factorsList(int n)
	{
		ArrayList<Integer> factors = new ArrayList<Integer>();

		for(int i = 1; i <= n; i++)
		{
			if(n % i == 0)
				factors.add(i);
		}

		return factors;
	}

	public static boolean isPrime(int n)
	{
		ArrayList<Integer> primes = primesUpTo(n);
		if(primes.contains(n))
			return true;
		else
			return false;
	}
}