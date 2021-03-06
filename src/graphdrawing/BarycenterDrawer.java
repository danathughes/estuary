package graphdrawing;

import graph.Graph;
import graph.Node;
import graph.Edge;

import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;

import java.util.Random;
import java.lang.Math;


public class BarycenterDrawer
{
  private Vector<Node> nodes;
  private Vector<Edge> edges;
  private Random random;

  private boolean isStable;

  private Graph graph;

  // Force Directed Graph Algorithm Constants
  private Node n1;
  private Node n2;
  private Node n3;
  private Node n4;

  public BarycenterDrawer(Graph graph)
  {
    this.graph = graph;
    this.random = new Random();
  }

  
  public void initialize()
  {
    Iterator nodeIterator;

    // The Barycenter Drawing algorithm requires three nodes to act as
    // a strictly convex polygon.  Extract three random points, and fix
    // them at appropriate locations.
    System.out.println("Getting nodes--");
    n1 = this.graph.getRandomNode();

    System.out.println("Node 1 = " + n1);
    do {
      n2 = this.graph.getRandomNode();
    } while (n2 == n1);

    System.out.println("Node 2 = " + n2);
    do {
      n3 = this.graph.getRandomNode();
    } while (n3 == n1 || n3 == n2);

    System.out.println("Node 3 = " + n3);    
  
    do {
      n4 = this.graph.getRandomNode();
    } while (n4 == n1 || n4 == n2 || n4 == n3);

    nodeIterator = this.graph.getNodes().iterator();

    System.out.print("Initializing Nodes...");
    while(nodeIterator.hasNext())
    {
      Node n = (Node) nodeIterator.next();
      // Set the current position to the origin
     
      n.setX(300);
      n.setY(200); 
    }
    System.out.println("Done!");

    // Now, scatter the fixed nodes to three predefined locations
    n1.setX(20);
    n1.setY(350);
    n2.setX(550);
    n2.setY(350);
    n3.setX(20);
    n3.setY(20);
    n4.setX(550);
    n4.setY(20);

    System.out.println("Finished!");
  }

  public boolean isStable()
  {
    return this.isStable; 
  }


  public void updateGraph()
  {
    Iterator nodeIterator, otherNodeIterator, edgeIterator;

    this.isStable = false;
    int totalMovement = 0;
    
    // First, convert our graph to a list of node connections
    HashMap<Node, Vector<Node>> degrees = new HashMap<Node, Vector<Node>> ();
    
    nodeIterator = this.graph.getNodes().iterator();

    while(nodeIterator.hasNext())
    {
      Node n = (Node) nodeIterator.next();
      degrees.put(n, new Vector<Node>());
    }

    edgeIterator = this.graph.getEdges().iterator();

    while(edgeIterator.hasNext())
    {
      Edge e = (Edge) edgeIterator.next();

      Node u = e.getFromNode();
      Node v = e.getToNode();

      Vector<Node> u_node_vector = degrees.get(u);
      Vector<Node> v_node_vector = degrees.get(v);

      u_node_vector.add(v);
      v_node_vector.add(u);
    }

    nodeIterator = this.graph.getNodes().iterator();

    while(nodeIterator.hasNext())
    {
      Node n = (Node) nodeIterator.next();

      if(n != n1 && n != n2 && n != n3 && n != n4)
      {
        Vector<Node> n_vector = degrees.get(n);

        int n_x = 0;
        int n_y = 0;
        int size = 0;
        otherNodeIterator = n_vector.iterator();
        while(otherNodeIterator.hasNext())
        {
          Node v = (Node) otherNodeIterator.next();
          size++;
          n_x += v.getX();
          n_y += v.getY();
        }

        if (size != 0)
        {
          n_x /= size;
          n_y /= size;
        } 
        else
        {
          n_x = n.getX();
          n_y = n.getY();
        }

        totalMovement += Math.abs(n.getX() - n_x) + Math.abs(n.getY() - n_y);

        n.setX(n_x);
        n.setY(n_y);    
      }
    }
    // Add repulsive force to each node
    this.isStable = totalMovement == 0;
  }

}
