package utils.menu;

import utils.menu.Procedure;
//Utility is a class which contains a Procedure (functional interface) and a description of the Procedure.
public class Utility
{
	private Procedure procedure;
	private String description;

	/*
		CONSTRUCTORS
	*/
	public Utility(Procedure p, String d)
	{
		procedure = p;
		description = d;
	}

	/*
		ACCESSOR METHODS
	*/
	public Procedure getProcedure()
	{
		return procedure;
	}

	public String getDescription()
	{
		return description;
	}

	/*
		MUTATOR METHODS
	*/

	public void setProcedure(Procedure p)
	{
		procedure = p;
	}

	public void setDescription(String d)
	{
		description = d;
	}

	/*
		OVERRIDDEN METHODS
	*/

	@Override
	public String toString()
	{
		return description;
	}

	/*
		INSTANCE METHODS
	*/
	public void execute()
	{
		procedure.execute();
	}
}