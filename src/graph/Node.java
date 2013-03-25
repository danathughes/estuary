package graph;

import java.awt.Graphics;
import java.awt.Color;

public class Node
{
  private int x;
  private int y;
  private Color color;

  public Node()
  {
    this.x = 0;
    this.y = 0;
    this.color = Color.BLACK;
  }

  public Node(int x, int y)
  {
    this();
    this.x = x;
    this.y = y;
  }

  public void draw(Graphics canvas)
  {
    canvas.setColor(this.color);
    canvas.fillOval(this.x-5, this.y-5, 10, 10);
  }

  public void setX(int x)
  {
    this.x = x;
  }

  public void setY(int y)
  {
    this.y = y;
  }

  public int getX()
  {
    return this.x;
  }

  public int getY()
  {
    return this.y;
  }
}
