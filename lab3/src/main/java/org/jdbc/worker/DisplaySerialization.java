package org.jdbc.worker;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DisplaySerialization {

  public static void main(String[] args) throws IOException {
    try {
      final CSVDisplayReader<Televise> televisorCSVDisplayReader = new CSVDisplayReader<>(
          "televises.csv", ",", new Televise());
      final CSVDisplayReader<Monitor> monitorCSVDisplayReader = new CSVDisplayReader<>(
          "monitors.csv", ",", new Monitor());

      ArrayList<Display> displays = new ArrayList<>();
      displays.addAll(televisorCSVDisplayReader.readObjects());
      displays.addAll(monitorCSVDisplayReader.readObjects());

      ObjectOutputStream objectOutputStream = new ObjectOutputStream(
          new FileOutputStream("displays.dat"));
      for (Display display : displays) {
        objectOutputStream.writeObject(display);
      }
    } catch (Exception e) {
      final String message = "Runtime error: " + e.getMessage() + " " + e.getCause();
      System.out.println(message);
      Journal.log(message);
    }
  }
}
