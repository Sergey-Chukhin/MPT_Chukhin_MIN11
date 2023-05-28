package org.circles;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    double centerX = 250;
    double centerY = 350;
    double dx = 1.6;
    double dy = -0.4;

    new PaintWindow(1000, 1000, List.of(new Circle[]{
        new Circle(centerX, centerY, dx, dy, 300, Color.BLACK),
        new Circle(centerX, centerY, dx, dy, 250, Color.RED),
        new Circle(centerX, centerY, dx, dy, 200, Color.GREEN),
        new Circle(centerX, centerY, dx, dy, 150, Color.BLUE),
        new Circle(centerX, centerY, dx, dy, 100, Color.PINK),
        new Circle(centerX, centerY, dx, dy, 50, Color.YELLOW),
    }));
  }
}