package org.circles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {

  private final List<Circle> circles;

  public DrawPanel(int width, int height, List<Circle> circles) {
    setBounds(0, 0, width, height);
    this.circles = circles;
    for (Circle circle : this.circles) {
      circle.runThread(this, this.circles);
    }
  }

  @Override
  public void paint(Graphics graphics) {
    super.paint(graphics);
    Graphics2D canvas = (Graphics2D) graphics;

    for (Circle circle : circles) {
      canvas.setColor(circle.getColor());
      canvas.draw(circle.getFigure());
      circle.unlock();
    }

    repaint();
  }
}
