package org.jdbc.worker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVDisplayReader<T extends Display> {

  private final T displayInstance;
  private final String fileName;
  private final BufferedReader reader;
  private final String delimiter;

  CSVDisplayReader(String fileName, String delimiter, T displayInstance)
      throws FileNotFoundException {
    this.fileName = fileName;
    this.delimiter = delimiter;
    this.reader = new BufferedReader(new FileReader(fileName));
    this.displayInstance = displayInstance;
  }

  CSVDisplayReader(T displayInstance) throws FileNotFoundException {
    this.fileName = "displays.csv";
    this.delimiter = ",";
    this.reader = new BufferedReader(new FileReader(this.fileName));
    this.displayInstance = displayInstance;
  }

  public ArrayList<T> readObjects() throws IOException {
    final String[] titles = this.reader.readLine().split(this.delimiter);

    ArrayList<T> result = new ArrayList<>();
    String data;
    int counter = 1;
    while ((data = this.reader.readLine()) != null) {
      try {
        String[] splitData = data.split(this.delimiter);
        T display = (T) this.displayInstance.create();
        display.parseCSVLine(splitData, titles);
        result.add(display);
        Journal.log("Display data loaded - " + display.id + ":" + display.model);
      } catch (Exception e) {
        Journal.log("Parsing error, line " + counter + ". Message: " + e.getMessage());
      } finally {
        counter++;
      }
    }

    Journal.log(this.fileName + " successfully parsed. Got " + counter + " items");
    return result;
  }

  public String getFileName() {
    return this.fileName;
  }
}
