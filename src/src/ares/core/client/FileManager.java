package src.ares.core.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import src.ares.core.common.ExceptionManager;

public class FileManager
{
	private File file;

	public FileManager(File file)
	{
		this.file = file;
		createFile();
	}

	public FileManager(File location, String filename, String extension)
	{
		this.file = new File(location, filename + "." + extension);
		location.mkdir();
		createFile();
	}

	private void createFile()
	{
		try
		{
			if (!file.exists())
				file.createNewFile();
		}
		catch (IOException e)
		{
			ExceptionManager.handle(e);
		}
	}

	/**
	 * Returns the contents of a specific File.
	 * 
	 * @return String
	 * @throws IOException
	 */
	public String getContents() throws IOException
	{
		FileInputStream inputStream = new FileInputStream(file);
		int length = inputStream.available();
		byte[] bytes = new byte[length];
		inputStream.read(bytes);
		inputStream.close();

		return new String(bytes);
	}

	/**
	 * Returns the File.
	 * 
	 * @return File
	 */
	public File getFile()
	{
		return file;
	}

	/**
	 * Writes a String to the specific File.
	 * 
	 * @param content The content to be stored.
	 * @throws IOException
	 */
	public void writeContent(String content) throws IOException
	{
		FileOutputStream outputStream = new FileOutputStream(file, true);
		outputStream.write(content.getBytes());
		outputStream.close();
	}
}
