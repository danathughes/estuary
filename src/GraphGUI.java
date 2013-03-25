import graph.Graph;
import graph.Node;
import graph.Edge;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.Container;
import java.lang.Thread;

import java.util.Random;

/** 
 * Currently a simple GUI to demonstrate the Graph drawing algorithms.
 *
 * @author Dana Hughes
 * @version 0.1a March 24, 2013.
 */
public class GraphGUI {


  public static void main(String[] args) {
    JFrame display = new JFrame("Graph");
    display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    display.setSize(600,400);
    Container canvas = display.getContentPane();

    Random random = new Random();

    Graph graph = new Graph(canvas);

    // Add about 20 nodes to the graph
    for(int i = 0; i < 20; i++)
    {
      Node n = new Node(random.nextInt(600), random.nextInt(400));
      graph.addNode(n);
    }
    // And add about 20 edges
    for(int i = 0; i < 20; i++)
    {
      Node n1 = graph.getRandomNode();
      Node n2 = graph.getRandomNode();   
      Edge e = new Edge(n1, n2);
      graph.addEdge(e);
    }

    display.setVisible(true);

    while(!graph.updateGraph()) 
    {
      graph.draw();
      try {
        Thread.sleep(100);
      } catch(Exception e) { System.out.println("Blerg"); }
    }
  }
}
