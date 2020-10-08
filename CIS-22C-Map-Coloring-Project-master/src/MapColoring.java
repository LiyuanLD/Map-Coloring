/*
Map Coloring Class
Author: Liyuan Lin
Current Date: March 22, 2018

Course: CIS 22C Winter 2018
Program: Map Coloring
Team #2

Description: This is the subclass of Graph class, which intends to do the coloring map problem.
*/

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class MapColoring<E> extends Graph<E>
{
	private HashMap <E,Integer> colorSet = new HashMap<E,Integer>(); // store key set and color set, generic type of E and Integer
	private final int UNCOLORED = 0; // default color 
	private final int DEFAULTEDGE = 0; //default distance
	int numOfColorUsed;

	/** an array of color strings, use to display the color */
	String [] color = {"", "Red", "Blue", "Green", "Yellow", "Orange","Purple","Navy","Pink"};

	/** LinkedStack to store the removed edges, 
	 * with a generic type of Pair(store the started vertex and end vertex) */
	private LinkedStack<Pair<E,E>> deletedVertex = new LinkedStack<Pair<E,E>>();
	
	/** Default Constructor. Call super class constructor. */
	public MapColoring()
	{ super(); }
	
	/** Initialize the color of each Key in vertexSet with 0.
	 * Then passing the iterator of vertexSet to colorMap method 
	 * with counter of 0. */
	public boolean graphColoring()
	{
		initializeColor(); // initialize hashMap<String, Integer> with the data in Graph.java super class
		Iterator<Entry<E, Vertex<E>>> iter;
		iter = vertexSet.entrySet().iterator();
		if(!colorMap(iter,0))
		{
			System.out.println("\nUnable to color this map.");
			return false; 
		}
		System.out.println("\n************ Map Coloring Solution: ************");
		displayMapColor(); // Display the colored map
		System.out.println("********** End Map Coloring Solution ***********");
		return true;
	}

	/**@param iter (Iterator) of vertexSet
	 * @param c (int) indicates the colored vertex */
	public boolean colorMap(Iterator<Entry<E, Vertex<E>>> iter, int c)
	{
		// Base Case:
		if (c == vertexSet.size()) // If all vertices are assigned a color
			return true;

		if (iter.hasNext())
		{
			Entry<E, Vertex<E>> entry = iter.next(); // Get the next entry from parameter
			E key = entry.getKey();
			for (int i = 1; i <= color.length; ++i)
			{
				// Check if assignment of color c to v is fine
				if (isSafe(entry, i) && colorSet.containsKey(key))
				{
					colorSet.put(key, i);

					if (colorMap(iter, c + 1)) // Recursive call to colorMap: pass the iterator of vertexSet with colored counter c
						return true;
					colorSet.put(key, UNCOLORED); // if color i doesn't lead to a solution then remove it
				}
			}
		}
		return false; // no solutions were found
	}

	/** Retrieve the Key from vertexSet, and initialize the color with 0(UNCOLORED). */
	private void initializeColor()
	{
		Iterator<Entry<E,Vertex<E>>> iterator = vertexSet.entrySet().iterator();
		while(iterator.hasNext())
		{
			Entry<E,Vertex<E>> entry = iterator.next();
			colorSet.put(entry.getKey(), UNCOLORED);
		}
	}

	/** Check if the adjacent Vertex has the same color
	 * @param entry (Entry<E,Vertex<E>>) an entry from vertexSet
	 * @param i (int) color*/
	private boolean isSafe(Entry<E, Vertex<E>> entry, int i)
	{
		Vertex<E> value = entry.getValue();
		Iterator<Entry<E, Pair<Vertex<E>, Double>>> iter 
		                                   = value.adjList.entrySet().iterator();
		while(iter.hasNext())
		{
			E adjVer = iter.next().getKey();
			if(!colorSet.containsKey(adjVer) || colorSet.get(adjVer) == i)
				return false;
		}
		return true;
	}

	/** Display the colored Map. */
	public void displayMapColor() // Modifications by Evan M: Made output format easier to read
	{
		Iterator<Entry<E,Integer>> iter = colorSet.entrySet().iterator();
		numOfColorUsed = 0;
		while(iter.hasNext())
		{
			Entry<E,Integer> entry = iter.next();
			int colorNum = entry.getValue();
			String space = "";
			for(int i=1; i<=(40-entry.toString().length()); i++)
				space += " ";
			System.out.println(entry.getKey() +space+ color[entry.getValue()]+"\n--------------------------------------------------");
			if(colorNum > numOfColorUsed)
				numOfColorUsed = colorNum;
		}
		System.out.println("\nThe minimum number of colors needed is " + numOfColorUsed + "\n");
	}

	/** Save the remove edge and vertex to LinkedStack<Pair<E,E>>
	 * where the elements in Pair store the edge sources or destinations. */
	public boolean _remove(E start, E end)
	{
		if(remove(start,end))
		{
			Pair<E,E> pair = new Pair<E,E>(start,end);
			deletedVertex.push(pair);
			return true;
		}
		return false;
	}

	/** Pop the last-in Pair, and insert Key and Value in Pair
	 * as source and destination to vertexSet. */
	public boolean undoRemovals()
	{
		Pair<E,E> undoVer = deletedVertex.pop();
		if(undoVer != null)
		{
			addEdge(undoVer.first, undoVer.second, DEFAULTEDGE);
			return true;
		}
		return false;
	}

	/** Write countries the color set to text file.
	 * @param PrintWriter (file) object
	 */
	public void writeColorResultToFile(PrintWriter file) // Modifications by Evan M: Made output format easier to read
	{
		Iterator<Entry<E, Integer>> iter;
		file.println("************ Map Coloring Solution: ************");
		iter = colorSet.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry<E,Integer> entry = iter.next();
			String space = "";
			for(int i=1; i<=(40-entry.toString().length()); i++)
				space += " ";

			file.println(entry.getKey() +space+ color[entry.getValue()]+"\n--------------------------------------------------");
		}
		file.println("\nThe minimum number of colors needed is " + numOfColorUsed+"\n");
		file.print("********** End Map Coloring Solution ***********");
	} //end writeToFile
}
