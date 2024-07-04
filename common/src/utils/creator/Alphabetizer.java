/******************************************************************************
Name: Jordy Parker
Date: 16.06.2024
Module: 2
Lab: 1
Purpose: Implements Comparator to alphabetize teams by name.
*******************************************************************************/
package utils.creator;

import java.util.Comparator;
public class Alphabetizer<T> implements Comparator<T>
{
	@Override
	public int compare(T obj1, T obj2)
	{
		return obj1.toString().compareTo(obj2.toString());
	}
}