package graphdrawing;

import graph.Graph;
import graph.Node;
import graph.Edge;

import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;

import java.util.Random;
import java.lang.Math;


public class FruchtermanReingoldDrawer
{
  private Vector<Node> nodes;
  private Vector<Edge> edges;
  private Random random;

  private boolean isStable;
  private double temp;

  private Graph graph;

  public FruchtermanReingoldDrawer(Graph graph)
  {
    this.graph = graph;
    this.random = new Random();
//    this.temp = 0.1*graph.getCanvasWidth()*graph.getCanvasHeight();
    this.temp = 2200.0;
  }


  public boolean isStable()
  {
    return this.isStable; 
  }


  public void updateGraph()
  {
    double width, height, area, k;
    System.out.println("Temp: " + this.temp);
    width = (double) this.graph.getCanvasWidth();
    height = (double) this.graph.getCanvasHeight();

    area = width*height;
    k = Math.sqrt(area / this.graph.getNodes().size());

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
      Node v = (Node) nodeIterator.next();
      int v_pos_x = v.getX();
      int v_pos_y = v.getY();
      double v_disp_x = 0.0;
      double v_disp_y = 0.0;

      otherNodeIterator = this.graph.getNodes().iterator();
      while(otherNodeIterator.hasNext())
      {
        Node u = (Node) otherNodeIterator.next();

        int u_pos_x = u.getX();
        int u_pos_y = u.getY();
        double u_disp_x = 0.0;
        double u_disp_y = 0.0;

        if (u != v)
        {
          double dx = (double) v_pos_x - (double) u_pos_x;
          double dy = (double) v_pos_y - (double) u_pos_y;
          double dmag = Math.sqrt(dx*dx + dy*dy);
          dmag = Math.max(dmag, 0.001);

          v_disp_x = v_disp_x + (dx/dmag) * k*k/dmag;
          v_disp_y = v_disp_y + (dy/dmag) * k*k/dmag;

          f = forces.get(v);
          f.x += v_disp_x;
          f.y += v_disp_y;
        }
      }
    }

    // Add attractive forces to each node

    edgeIterator = this.graph.getEdges().iterator();

    while(edgeIterator.hasNext())
    {
      Edge e = (Edge) edgeIterator.next();

      Node v = e.getFromNode();
      Node u = e.getToNode();

      double dx = v.getX() - u.getX();
      double dy = v.getY() - u.getY();

      double d_mag = Math.sqrt(dx*dx + dy*dy);
      d_mag = Math.max(d_mag, 0.001); 
     
      Force2D f_v = forces.get(v);
      Force2D f_u = forces.get(u);

      f_v.x -= (dx / d_mag) * (d_mag*d_mag / k);
      f_v.y -= (dy / d_mag) * (d_mag*d_mag / k);
      f_u.x += (dx / d_mag) * (d_mag*d_mag / k);
      f_u.y += (dy / d_mag) * (d_mag*d_mag / k);
    }

    // And, update the positions according to forces
    nodeIterator = this.graph.getNodes().iterator();
    while(nodeIterator.hasNext())
    {
      Node n = (Node) nodeIterator.next();

      f = forces.get(n);

      double f_mag = Math.sqrt(f.x*f.x + f.y*f.y);
      if (f_mag > this.temp)
      {
        f.x = f.x * this.temp/f_mag;
        f.y = f.y * this.temp/f_mag;
      }

      int n_x = n.getX();
      int n_y = n.getY();

      n_x += (int) f.x;
      n_y += (int) f.y;

      if (n_x < 0)
        n_x = 0;
      if (n_y < 0)
        n_y = 0;
      if (n_x > this.graph.getCanvasWidth())
        n_x = this.graph.getCanvasWidth() - 1;
      if (n_y > this.graph.getCanvasHeight())
        n_y = this.graph.getCanvasHeight() - 1;

      n.setX(n_x);
      n.setY(n_y);

      this.temp -= 1.0;
      this.temp = Math.max(0.0, this.temp);
      this.isStable = this.temp == 0.0;
    }
  }

}
