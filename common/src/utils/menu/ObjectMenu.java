package utils.menu;

/********************************************************
An ObjectMenu, when run, executes a given instance method
********************************************************/
import utils.menu.*;
import java.util.function.Consumer;
public class ObjectMenu<T> extends FunctionalMenu
{
	T[] instances;

	/*
		CONSTRUCTORS
	*/

	public ObjectMenu(String g, String p, String e, T[] objs, Consumer<T> method)
	{
		super(g, p, e, new Utility[objs.length]);
		setInstances(objs, method);
	}

	public ObjectMenu(String g, String p, T[] objs, Consumer<T> method)
	{
		super(g, p, new Utility[objs.length]);
		setInstances(objs, method);
	}

	public ObjectMenu(String g, T[] objs, Consumer<T> method)
	{
		super(g, new Utility[objs.length]);
		setInstances(objs, method);
	}

	public ObjectMenu(T[] objs, Consumer<T> method)
	{
		super(new Utility[objs.length]);
		setInstances(objs, method);
	}

	/*
		ACCESSOR METHODS
	*/

	public void setInstances(T[] objs, Consumer<T> method)
	{
		instances = objs;
		for(int i = 0; i < instances.length; i++)
		{
			int temp = i;
			utilities[i] = new Utility(() -> method.accept(instances[temp]), instances[temp].toString());
		}
	}

	public T[] getInstances()
	{
		return instances;
	}

	/*
		INSTANCE METHODS
	*/

	@Override
	String display()
	{
		resetDescriptions();
		return super.display();
	}

	//For if an object's name changes over the course of using the ObjectMenu.
	public void resetDescriptions()
	{
		for(int i = 0; i < instances.length; i++)
		{
			int temp = i;
			utilities[i].setDescription(instances[i].toString());
		}
	}
}