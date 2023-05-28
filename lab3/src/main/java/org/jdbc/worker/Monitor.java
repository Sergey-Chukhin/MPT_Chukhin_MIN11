package org.jdbc.worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class Monitor extends Display {

  public boolean nvidiaGSync;
  public int frameRate;

  public Monitor() {
    super();

    this.nvidiaGSync = false;
    this.frameRate = 60;
  }

  public Monitor(ResultSet resultSet) throws SQLException {
    id = resultSet.getInt("id");
    model = resultSet.getString("model");
    price = resultSet.getFloat("price");
    resolution = resultSet.getString("resolution");
    nvidiaGSync = resultSet.getBoolean("nvidiaGSync");
    frameRate = resultSet.getInt("frameRate");
  }

  @Override
  public void parseCSVLine(String[] data, String[] titles) {
    for (int i = 0; i < titles.length; i++) {
      final String field = titles[i];
      final String value = data[i];
      switch (field) {
        case "id" -> this.id = Integer.parseInt(value);
        case "model" -> this.model = value;
        case "price" -> this.price = Float.parseFloat(value);
        case "resolution" -> this.resolution = value;
        case "nvidiaGSync" -> this.nvidiaGSync = value.equalsIgnoreCase("true");
        case "frameRate" -> this.frameRate = Integer.parseInt(value);
      }
    }
  }

  @Override
  public Display create() {
    return new Monitor();
  }

  @Override
  PreparedStatement getValuesStatement(String table, Connection connection) throws SQLException {
    PreparedStatement statement = connection.prepareStatement("INSERT INTO " + table
        + " (id, model, price, resolution, nvidiaGSync, frameRate) VALUES (?, ?, ?, ?, ?, ?)");

    System.out.println(statement.toString());

    statement.setInt(1, id);
    statement.setString(2, model);
    statement.setFloat(3, price);
    statement.setString(4, resolution);
    statement.setBoolean(5, nvidiaGSync);
    statement.setInt(6, frameRate);

    return statement;
  }

  @Override
  String getCreateTableUpdate(String name) {
    return String.format(
        "CREATE TABLE %s (id int PRIMARY KEY, model varchar(255), price float8, resolution "
            + "varchar(255), "
            + "nvidiaGSync boolean, frameRate int)", name);
  }

  @Override
  String getValuesLine() {
    return String.format(Locale.US, "(%d, '%s', %.4f, '%s', %b, '%d')", id, model, price, resolution,
        nvidiaGSync, frameRate);
  }

  @Override
  String getValuesHeaderLine() {
    return "(id, model, price, resolution, nvidiaGSync, frameRate)";
  }
}
