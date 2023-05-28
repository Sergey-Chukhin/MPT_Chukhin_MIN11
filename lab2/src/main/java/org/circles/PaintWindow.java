package org.circles;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PaintWindow extends JFrame {

  public PaintWindow(int width, int height, List<Circle> circles) {
    JPanel panel = new DrawPanel(width, height, circles);

    setTitle("Концентрические окружности");
    setSize(width, height);
    add(panel);

    setVisible(true);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(1);
      }
    });
  }
}
