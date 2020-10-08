/*
WriteToFileVisitor Class
Author: Liyuan Lin
Current Date: March 20, 2018

Course: CIS 22C Winter 2018
Program: Map Coloring
Team #2

Description: Helps with writing to text file


import java.io.*;
public class WriteToFileVisitor<T> implements Visitor<T>
{
	FileOutputStream outFile;
	PrintWriter writer;
	public WriteToFileVisitor(String name)
	{
		try
		{
			outFile = new FileOutputStream(name+".txt");
		}
		catch(IOException ex)
		{
			System.out.println("Unable to create file " + ex);
			return;
		}
		writer = new PrintWriter(outFile);
	}
	
	public void visit(T obj)
	{
		writer.write(obj.toString());
	}
}
*/
/* ignore this class, we don't need it*/
