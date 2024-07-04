package utils;

import java.io.*;
import java.util.*;
public class Serializer
{
	public static <T> void serialize(T t, String path)
	{
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path)))
		{
			out.writeObject(t);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserialize(String path) throws IOException, ClassNotFoundException
	{
		T result = null;
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
		result = (T)in.readObject();

		return result;
	}

	public static File[] searchDirectory(String path)
	{
		List<File> files = Arrays.asList(new File(path).listFiles());

		for(File file : files)
		{
			if(!file.getName().endsWith(".ser"))
				files.remove(file);
		}

		return files.toArray(new File[files.size()]);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] deserializeInDirectory(String path)
	{
		File[] serializedFiles = searchDirectory(path);
		LinkedList<T> deserializedFiles = new LinkedList<>();

		for(int i = 0; i < serializedFiles.length; i++)
		{
			try
			{
				deserializedFiles.add(deserialize(serializedFiles[i].getPath()));
			}
			catch(IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}

		return (T[])deserializedFiles.toArray(new Object[deserializedFiles.size()]);
	}
}