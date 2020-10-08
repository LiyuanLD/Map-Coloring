/*
Utility Class
Author: Zhengnan Guo
Co-author: Evan Muller
Current Date: March 22, 2018

Course: CIS 22C Winter 2018
Program: Map Coloring
Team #2

Description: Provides user interface for the project
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Utility
{
	private static final double DEFAULTCOST = 0;
	static Visitor<String> visitor = new Display<String>();
	static String filename = null;
	public static Scanner userScanner = new Scanner(System.in);

	public static Scanner openInputFile()
	{
		System.out.print("Input a text file (excluding extension): ");
		filename = userScanner.nextLine();
		File file = new File(filename+".txt");
		Scanner scanner = null;
		try
		{
			scanner = new Scanner(file);
		}
		catch (FileNotFoundException fe)
		{
			System.out.println("Can't open input file\n");
			return null;
		}
		return scanner;
	}

	// getGraphFromInputFile written by Evan M.
	public static MapColoring<String> getGraphFromInputFile()
	{
		while (true) // Will continue asking user for valid file until it is provided
		{
			Scanner file = null;
			file = openInputFile();
			if (file == null) // If the file provided was not valid
				System.out.print("Please Re-"); // Attaches to original prompt to display "Please Re-Input Text File"
			else
			{
				MapColoring<String> graph = new MapColoring<String>();

				while (file.hasNextLine())
				{
					String line = file.nextLine(); // Get next file line
					String source = ""; // Initialize string that will be the source location
					if (line.charAt(0) == '#') // If the # mark is present 
						source = line.substring(1); // The source is the string after 
					else // Text input was not written correctly (# tag missing)
					{
						System.out.print("Need # before each source location\n\nRe-");
						break; // go back to beginning of this method
					}
					if (file.hasNextLine()) // ensure that there is another line to possibly add edges from
					{
						line = file.nextLine(); // Get next file line
						String[] arr = line.split("/"); // Creates an array of each destination (edge) for each string in-between separators

						for (String elem : arr) // For all adjacent locations
							graph.addEdge(source, elem, DEFAULTCOST); // add to the graph (note: uses weight of 0 because graph is unweighted) 
						if (!file.hasNextLine()) // File traversed without issue
							return graph;
					}
					else // Text input was not written correctly (Source not paired with destination)
						System.out.print("Need adjacent locations for every source location\n\nRe-");
				}
			}
		}
	} // end getGraphFromInputFile

	static String sourceToRemove = null;
	static String destToRemove = null;

	// Modificatons by Evan M: Modified console output to make it easier to read + added more error handling
	public static void processUserRequest(MapColoring<String> graph) throws FileNotFoundException
	{
		boolean flag = true;
		while (flag)
		{
			displayUserOptions();
			String request = userScanner.nextLine();
			String lineBreak = "\n------------------------------------------------";
			switch (request)
			{
			case "1":
				System.out.println("Option 1: Input new text file"+lineBreak);
				graph = getGraphFromInputFile();
				break;

			case "2":
				System.out.println("Option 2: Add edge to graph"+lineBreak);
				System.out.println("Input source of the new edge: ");
				String sourceToAdd = userScanner.nextLine();
				System.out.println("Input destinaton of the new edge: ");
				String destToAdd = userScanner.nextLine();
				graph.addEdge(sourceToAdd, destToAdd, DEFAULTCOST);
				break;

			case "3":
				System.out.println("Option 3: Remove edge"+lineBreak);
				System.out.print("Input source to remove: ");
				sourceToRemove = userScanner.nextLine();
				System.out.print("Input destination to remove: ");
				destToRemove = userScanner.nextLine();
				if(graph._remove(sourceToRemove, destToRemove))
					System.out.println("\nRemoval completed successfully");
				else
					System.out.println("\nRemoval failed");
				break;

			case "4":
				System.out.println("Option 4: Undo Removal"+lineBreak);
				if (graph.undoRemovals())
					System.out.println("Undo was successful");
				else
					System.out.println("Undo failed: No edge removed");
				sourceToRemove = null;
				destToRemove = null;
				break;

			case "5":
				System.out.println("Option 5: Display graph"+lineBreak);
				System.out.print("How would you like to display the graph?"
						+ "\nBreadth First Traversal (BFT), Depth First Traversal (DFT), or Adjacency Lists (AJ)?"
						+ "\n\nInput choice: ");
				String choice = null;
				choice = userScanner.nextLine();
				switch(choice.toUpperCase())
				{
				case "BFT":
					System.out.print("Please enter the name of the root location (case sensitive): ");
					String root = userScanner.nextLine();
					
					/* If there was a graph contains method, I would use that instead to prevent
					using depthFirstTraversal when root doesn't exist in graph*/
					try
					{
						System.out.print("\n-----BFT for " + root + "-----");
						graph.breadthFirstTraversal(root, visitor);
						System.out.println();
					}
					catch(NullPointerException e)
					{
						System.out.println("\nError: "+root+ " does not exist in the graph");
					}
					break;
				case "DFT":
					System.out.print("Please enter the name of the root location (case sensitive): ");
					root = userScanner.nextLine();

					/* If there was a graph contains method, I would use that instead to prevent
					using depthFirstTraversal when root doesn't exist in graph*/
					try
					{
						System.out.print("\n-----DFT for " + root + "-----");
						graph.depthFirstTraversal(root, visitor);
						System.out.println();
					}
					catch(NullPointerException e)
					{
						System.out.println("\nError: "+root+ " does not exist in the graph");
					}
					break;
				case "AJ":
					System.out.println("\nAdjacency lists:");
					graph.showAdjTable();
					break;
				default:
					System.out.println("Invalid display option");
				}
				break;

			case "6":
				System.out.println("Option 6: Solve color mapping"+lineBreak);
				if (graph.graphColoring())
				{
					System.out.print("\nDo you want to save the solution to a file? (Y/N): ");
					String ans = userScanner.nextLine();
					if (ans.toUpperCase().equals("Y"))
					{
						System.out.print("Please enter file name (excluding extension): ");
						String solutionName = userScanner.nextLine();
						File file = new File(solutionName + ".txt");
						if (!file.exists())
						{
							System.out.println("File is not found. Creating "+file.getName());
							try
							{
								file.createNewFile();
							}
							catch (IOException e)
							{
								System.out.println("Cannot create file");
								e.printStackTrace();
							}
						}
						PrintWriter printWriter = new PrintWriter(file);
						graph.writeColorResultToFile(printWriter);
						printWriter.close();
						System.out.println("Map coloring solution saved to "+file.getAbsolutePath());
					}
					else
					{
						if(!ans.toUpperCase().equals("N"))
						{
							System.out.println("Answer was neither Y nor N.");
						}
						System.out.println("Solution not written to file");
					}
				}
				// else: graphColoring() will print "Unable to color this map."
				break;

			case "7":
				System.out.println("Option 7: Write graph to text file"+lineBreak);
				System.out.print("Please enter file name (excluding extension): ");
				String op = userScanner.nextLine();
				File file = new File(op + ".txt");
				if (!file.exists())
				{
					System.out.println("File is not found. Creating "+file.getName());
					try
					{
						file.createNewFile();
					}
					catch (IOException e)
					{
						System.out.println("Cannot create file");
						e.printStackTrace();
					}
				}
				PrintWriter printWriter = new PrintWriter(file);
				graph.writeToFile(printWriter);
				printWriter.close();
				System.out.println("Graph saved to "+file.getAbsolutePath());
				break;

			case "8":
				System.out.println("Option 8: End Program"+lineBreak);
				flag = false;
				System.out.println("Ending Program. Have a nice day");
				break;

			default:
				System.out.println("User must choose one number from the available options");
				break;
			}
		}
	}

	public static void displayUserOptions()
	{
		System.out.println(
				"\n--------------Map Coloring Options--------------\n"
				+ "1.	Input new text file\n"
				+ "2.	Add edge to graph\n"
				+ "3.	Remove edge\n"
				+ "4.	Undo removal\n"
				+ "5.	Display graph\n"
				+ "6.	Solve color mapping\n"
				+ "7.	Write graph to text file\n"
				+ "8.	End Program"
				+ "\n--------------Please enter a number-------------");
	}
}
