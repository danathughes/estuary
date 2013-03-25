package graphdrawing;

import graph.Graph;
import graph.Node;
import graph.Edge;

import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;

import java.util.Random;
import java.lang.Math;


public class EadesSpringDrawer
{
  private Vector<Node> nodes;
  private Vector<Edge> edges;
  private Random random;

  private boolean isStable;

  private Graph graph;

  // Force Directed Graph Algorithm Constants
  private static final double c1 = 10;
  private static final double c2 = 40;
  private static final double c3 = 500;
  private static final double c4 = 1;

  public EadesSpringDrawer(Graph graph)
  {
    this.graph = graph;
    this.random = new Random();
  }


  public boolean isStable()
  {
    return this.isStable; 
  }


  public void updateGraph()
  {
    this.isStable = false;
    int totalMovement = 0;
    
    // Update the graph according to force-directed algorithm
    HashMap<Node, Force2D> forces = new HashMap<Node, Force2D>();
    Iterator nodeIterator, edgeIterator, otherNodeIterator;
    Force2D f;

    // Initialize Forces
    nodeIterator = this.graph.getNodes().iterator();   
    while(nodeIterator.hasNext())
    {
      Node n = (Node) nodeIterator.next();
      forces.put(n, new Force2D());
    }

    // Add repulsive force to each node
    nodeIterator = this.graph.getNodes().iterator();   
    while(nodeIterator.hasNext())
    {
      Node n = (Node) nodeIterator.next();

      f = forces.get(n);
      otherNodeIterator = this.graph.getNodes().iterator();
      while(otherNodeIterator.hasNext())
      {
        Node n2 = (Node) otherNodeIterator.next(); 
        if (n != n2)
        {
          int dx = n.getX() - n2.getX();
          int dy = n.getY() - n2.getY();
          double d = Math.sqrt((double) (dx*dx + dy*dy));
          double r_force = c3 / (d*d);
          double x_hat = (double) dx / d;
          double y_hat = (double) dy / d;
          f.x += r_force * x_hat;
          f.y += r_force * y_hat;
        } 
      }
    }

    // Add attractive forces to each node
    edgeIterator = this.graph.getEdges().iterator();
   
    while(edgeIterator.hasNext())
    {
      Edge e = (Edge) edgeIterator.next();
      Node n1 = e.getFromNode();
      Node n2 = e.getToNode();
      int x1 = n1.getX();
      int x2 = n2.getX();
      int y1 = n1.getY();
      int y2 = n2.getY();
      double d = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
      double f_att = c1 * Math.log(d / c2);
      double x_hat = (double) (x2 - x1) / d;
      double y_hat = (double) (y2 - y1) / d;
      Force2D f1 = forces.get(n1);
      Force2D f2 = forces.get(n2);
      f1.x += x_hat * f_att;
      f1.y += y_hat * f_att;
      f2.x -= x_hat * f_att;
      f2.y -= y_hat * f_att;
    }

    // And, update the positions according to forces
    nodeIterator = this.graph.getNodes().iterator();

    while(nodeIterator.hasNext())
    {
      Node n = (Node) nodeIterator.next();
      System.out.println("Position of Node " + n + ": " + n.getX() + ", " + n.getY());
      f = forces.get(n);
      System.out.println("Force x on Node " + n + ": " + f.x);
      System.out.println("Force y on Node " + n + ": " + f.y);
      
      n.setX(n.getX() + (int) (c4*f.x));
      n.setY(n.getY() + (int) (c4*f.y));

      totalMovement += Math.abs((int) (c4 * f.x));
      totalMovement += Math.abs((int) (c4 * f.y));

      System.out.println("New position of Node " + n + ": " + n.getX() + ", " + n.getY());

      System.out.println("-------------------------------------");
    }
    this.isStable = totalMovement == 0;
  }

}
