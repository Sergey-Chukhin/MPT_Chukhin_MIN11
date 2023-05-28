package org.circles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class Circle {

  private static enum movement {
    HORIZONTAL, VERTICAL, BOTH, NONE
  }

  private double x;

  private double y;

  private double dx;
  private double dy;
  private int diameter;
  private Color color;
  private boolean locked = false;

  public Circle(double x, double y, double dx, double dy, int diameter, Color color) {
    this.x = x;
    this.y = y;
    this.diameter = diameter;
    this.dx = dx;
    this.dy = dy;
    this.color = color;
  }

  public Ellipse2D.Double getFigure() {
    return new Double(getBoundsX(), getBoundsY(), diameter, diameter);
  }

  public Color getColor() {
    return color;
  }

  public double getBoundsX() {
    return x - (double) diameter / 2;
  }

  public double getBoundsY() {
    return y - (double) diameter / 2;
  }

  public double getY() {
    return y;
  }

  public double getX() {
    return x;
  }

  protected void reflectX() {
    dx *= -1;
  }

  protected void reflectY() {
    dy *= -1;
  }

  public void lock() {
    this.locked = true;
  }

  public void unlock() {
    this.locked = false;
  }

  private movement checkBorders(JPanel panel) {
    double radius = (double) diameter / 2;

    boolean checkX = x + radius >= panel.getWidth() || x - radius <= 0;
    boolean checkY = y + radius >= panel.getHeight() || y - radius <= 0;

    if (checkX && checkY) {
      return movement.BOTH;
    } else if (checkX) {
      return movement.HORIZONTAL;
    } else if (checkY) {
      return movement.VERTICAL;
    } else {
      return movement.NONE;
    }
  }

  private void calculateStep(JPanel panel, List<Circle> circles) {
    movement collisionCheck = checkBorders(panel);
    switch (collisionCheck) {
      case BOTH -> circles.forEach(circle -> {
        circle.reflectX();
        circle.reflectY();
      });
      case VERTICAL -> circles.forEach(Circle::reflectY);
      case HORIZONTAL -> circles.forEach(Circle::reflectX);
    }

    x += dx;
    y += dy;

    lock();
  }

  public void runThread(JPanel panel, List<Circle> circles) {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
//        System.out.println();
        if (!locked) {
          calculateStep(panel, circles);
        }
      }
    }, 0, 17);
  }
}
