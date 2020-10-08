/*
Driver Class
Author: Evan Muller
Current Date: March 21, 2018

Course: CIS 22C Winter 2018
Program: Map Coloring
Team #2

Description: Main program that facilitates execution
*/

import java.io.FileNotFoundException;

public class Driver
{
	public static void main(String[] args) throws FileNotFoundException
	{
		Utility teamTest = new Utility();
		MapColoring<String> testMap= teamTest.getGraphFromInputFile();
		teamTest.processUserRequest(testMap);
	}

}