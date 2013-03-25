package graph;

import graph.Node;
import java.awt.Graphics;
import java.awt.Color;

public class Edge
{
  private Node fromNode = null;
  private Node toNode = null;
  private Color color;

  public Edge(Node fromNode, Node toNode)
  {
    this.fromNode = fromNode;
    this.toNode = toNode;
    this.color = Color.BLACK;
  }

  public Node getFromNode()
  {
    return this.fromNode;
  }

  public Node getToNode()
  {
    return this.toNode;
  }

  public void draw(Graphics canvas)
  {
    canvas.setColor(this.color);
    canvas.drawLine(this.fromNode.getX(), this.fromNode.getY(),
                    this.toNode.getX(), this.toNode.getY());
  }

}
