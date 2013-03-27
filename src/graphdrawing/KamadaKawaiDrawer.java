package graphdrawing;

import graph.Graph;
import graph.Node;
import graph.Edge;

import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;

import java.util.Random;
import java.lang.Math;


public class KamadaKawaiDrawer
{
  private Vector<Node> nodes;
  private Vector<Edge> edges;
  private Random random;

  private boolean isStable;

  private Graph graph;

  // Force Directed Graph Algorithm Constants
  private int L0;
  private int d[][];
  private int l[][];
  private double k[][];
  private Node nodes[];
  private static final double K = 1;

  public KamadaKawaiDrawer(Graph graph)
  {
    this.graph = graph;
    this.random = new Random();
  }

  
  public void initialize()
  {
    int numNodes = graph.getNodes().size();

    // Keep the nodes in an array
    this.nodes = new Nodes[numNodes];
    this.nodes = this.graph.getNodes().toArray(nodes);

    // Set up our arrays
    this.d = new int[numNodes][numNodes];
    this.l = new int[numNodes][numNodes];
    this.k = new double[numNodes][numNodes];
    
    // Get the distances between nodes in the graph
    for(int i = 0; i < numNodes; i++)
    {
      for(int j = 0; i < numNodes; j++)
      {
        d[i][j] = 10000000000000;    // Start very big?
      }
    }


  }

  public boolean isStable()
  {
    return this.isStable; 
  }


  public void updateGraph()
  {

  }

}
