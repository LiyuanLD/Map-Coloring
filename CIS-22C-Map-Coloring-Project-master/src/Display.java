/*
Display Class
Author: Liyuan Lin
Current Date: March 21, 2018

Course: CIS 22C Winter 2018
Program: Map Coloring
Team #2

Description: Display implements interface of Visitor.
*/

public class Display <T> implements Visitor<T>
{
	public void visit(T obj)
	{
		System.out.print("\n" + obj.toString());
	}
}
