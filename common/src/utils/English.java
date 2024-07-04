package utils;

public class English
{
	public static String undoCamelCasing(String input) //19.06.2024 I don't know enough about regular expressions to know how this method works.
	{
		if(input == null || input.isEmpty())
			return input;
		return input.replaceAll("([a-z])([A-Z])", "$1 $2").replaceAll("([A-Z])([A-Z][a-z])", "$1 $2");
	}

	public static String plural(String input) //19.06.2024 not done with this.
	{
		String result = input;

		if
		(
			input.endsWith("s") ||
			input.endsWith("sh") ||
			input.endsWith("ch") ||
			input.endsWith("x") ||
			input.endsWith("o")
		)
			result += "es";
		else result += "s";
		
		return result;

	}
}