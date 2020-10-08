/*
Graph Tester Class
Current Date: March 21, 2018

Course: CIS 22C Winter 2018
Program: Map Coloring
Team #2

Description: A class purely used for testing.
*/

public class GraphTester
{
	   // -------  main --------------
	   public static void main(String[] args)
	   {
	      // build graph
	      MapColoring<String> myGraph1 = new MapColoring<String>();
	      myGraph1.addEdge("A", "B", 0);   myGraph1.addEdge("A", "C", 0);  myGraph1.addEdge("A", "D", 0);
	      myGraph1.addEdge("B", "E", 0);   myGraph1.addEdge("B", "F", 0);
	      myGraph1.addEdge("C", "G", 0);
	      myGraph1.addEdge("D", "H", 0);   myGraph1.addEdge("D", "I", 0);
	      myGraph1.addEdge("F", "J", 0);
	      myGraph1.addEdge("G", "K", 0);   myGraph1.addEdge("G", "L", 0);
	      myGraph1.addEdge("H", "M", 0);   myGraph1.addEdge("H", "N", 0);
	      myGraph1.addEdge("I", "N", 0);

	      myGraph1.showAdjTable();
	      myGraph1.graphColoring();
	      System.out.println("\nRemove edge between A and C, and re-color the Map: "
	      		+ "\n(notice that C doesn't connect with A anymore, so the color of C could be red now)");
	      myGraph1._remove("A","C");
	      myGraph1.graphColoring();
	      System.out.println("\n\nUndo the previous remove, and re-color the Map: "
	    		  + "\n(C is reconnected with A, so the color of C couldn't be red, then blue is the choice)");
	      myGraph1.undoRemovals();
	      myGraph1.graphColoring();
	      // myGraph1.displayMapDFT("A", new Display<String>());
	   }
}
/*
[Output:]
------------------------ 
Adj List for A: B(0.0) C(0.0) D(0.0) 
Adj List for B: A(0.0) E(0.0) F(0.0) 
Adj List for C: A(0.0) G(0.0) 
Adj List for D: A(0.0) H(0.0) I(0.0) 
Adj List for E: B(0.0) 
Adj List for F: B(0.0) J(0.0) 
Adj List for G: C(0.0) K(0.0) L(0.0) 
Adj List for H: D(0.0) M(0.0) N(0.0) 
Adj List for I: D(0.0) N(0.0) 
Adj List for J: F(0.0) 
Adj List for K: G(0.0) 
Adj List for L: G(0.0) 
Adj List for M: H(0.0) 
Adj List for N: H(0.0) I(0.0) 


A Red

B Blue

C Blue

D Blue

E Red

F Red

G Red

H Red

I Red

J Blue

K Blue

L Blue

M Blue

N Blue

The minimum number of colors needed is 2


Remove edge between A and C, and re-color the Map: 
(notice that C doesn't connect with A anymore, so the color of C could be red now)

A Red

B Blue

C Red

D Blue

E Red

F Red

G Blue

H Red

I Red

J Blue

K Red

L Red

M Blue

N Blue

The minimum number of colors needed is 2



Undo the previous remove, and re-color the Map: 
(C is reconnected with A, so the color of C couldn't be red, then blue is the choice)

A Red

B Blue

C Blue

D Blue

E Red

F Red

G Red

H Red

I Red

J Blue

K Blue

L Blue

M Blue

N Blue

The minimum number of colors needed is 2

A B E F J C G K L D H M N I 
*/
