package org.jdbc.worker;

import java.io.Serializable;

interface IDisplayReader<T> {

  void parseCSVLine(String[] data, String[] titles);
  T create();
}

public abstract class Display extends Loadable implements Serializable, IDisplayReader<Display> {

  public int id;
  public String resolution; // "1280*720"
  public float price;
  public String model;
  public float diagonal; // inches

  Display(int id, String model, float price, String resolution, float diagonal) {
    this.id = id;
    this.resolution = resolution;
    this.model = model;
    this.price = price;
    this.diagonal = diagonal;
  }

  Display() {
    id = 0;
    resolution = "";
    model = "";
    price = 0;
    diagonal = 0;
  }
}