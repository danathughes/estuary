package graph;

import graphdrawing.EadesSpringDrawer;

import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;

import java.util.Random;

import java.awt.Container;
import java.awt.Color;

/**
 * An abstraction of a undirected graph.  This abstraction consists of the
 * actual graph itself (represented by Node and Edge objects), a graph drawing
 * algorithm, and the capability to draw the nodes and edges on a Container.
 *
 * @author Dana Hughes
 * @version 0.1 March 23, 2013.
 * @see Node
 * @see Edge
 */
public class Graph
{
  private Vector<Node> nodes;
  private Vector<Edge> edges;
  private Random random;
  private Container parent;

  private Color bgColor;

  private EadesSpringDrawer graphDrawer;

  /**
   * Constructor for a new graph.
   *
   * @param parent           The Container object which the graph is drawn in.
   */
  public Graph(Container parent)
  {
    this.parent = parent;
    this.random = new Random();
    this.nodes = new Vector<Node>();
    this.edges = new Vector<Edge>();
    this.bgColor = Color.WHITE;

    // Should this be parameterized?
    this.graphDrawer = new EadesSpringDrawer(this);

    // Delete this, or send to a logger...
    System.out.println("My panel is: " + parent);
    System.out.println("My canvas is: " + parent.getGraphics());
  }

  /**
   * Adds a new node to the graph.
   *
   * @param node             The node to be added to the graph.
   */
  public void addNode(Node node)
  {
    this.nodes.add(node);
  }

  /**
   * Adds a new edge to the graph.
   *
   * @param edge             The edge to be added to the graph.
   */
  public void addEdge(Edge edge)
  {
    this.edges.add(edge);
  }

  /**
   * Gets all the nodes of the graph, in the form of a Vector.
   * NOTE:  This is public now, but may be more suitable to being protected.
   * TODO:  Add some testing to determine if the node already exists in the 
   *        graph, so it isn't added twice.
   *
   * @return                 The nodes of the graph, as a Vector.
   */
  public Vector<Node> getNodes()
  {
    return this.nodes;
  }

  /**
   * Gets all the edges of the graph, in the form of a Vector.
   * NOTE:  This is public now, but may be more suitable to being protected.
   * TODO:  Add some testing to determine if the edge already exists in the 
   *        graph, so it isn't added twice.  Note that this doesn't keep the
   *        user from having two edges connecting the same two nodes.  Rather,
   *        it forces the user to explicitly create the two edges.
   *
   * @return                 The edges of the graph, as a Vector.
   */
  public Vector<Edge> getEdges()
  {
    return this.edges;
  }

  /**
   * Get a node at random in the graph, if one exists.
   * NOTE:  This is public now, but may be more suitable to being protected.
   * TODO:  Determine a good way to handle if the graph is empty.  Should it 
   *        return null, or throw an exception?  (Probably return null...)
   *
   * @return                 A random node in the graph.
   */
  public Node getRandomNode()
  {
    int index;

    if(nodes.isEmpty())
    {
      return null;
    }
    else
    {
      index = random.nextInt(this.nodes.size());
      return this.nodes.get(index);
    }
  }

  /**
   * Have the graph drawing algorithm determine the positions of the nodes in
   * the graph.
   *
   * @return                 A boolean indicating if the generated graph is stable.
   */
  public boolean updateGraph()
  {
    this.graphDrawer.updateGraph();
    return this.graphDrawer.isStable();    
  }

  /**
   * Draw all the nodes and edges of the graph on the graphics context of the 
   * provided container.
   */
  public void draw()
  {
    this.parent.getGraphics().setColor(this.bgColor);
    this.parent.getGraphics().clearRect(0, 0, this.parent.getWidth(), this.parent.getHeight());
    Iterator edgeIterator = this.edges.iterator();
    Iterator nodeIterator = this.nodes.iterator();
   
    while(edgeIterator.hasNext())
    {
      Edge e = (Edge) edgeIterator.next();
      e.draw(this.parent.getGraphics());
    }
   
    while(nodeIterator.hasNext())
    {
      Node n = (Node) nodeIterator.next();
      n.draw(this.parent.getGraphics());
    }
  }
}
