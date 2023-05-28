package org.jdbc.worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class Televise extends Display {

  public String operationSystem;
  public boolean internetAccess;

  Televise() {
    super();

    operationSystem = "";
    internetAccess = false;
  }

  public Televise(ResultSet resultSet) throws SQLException {
    id = resultSet.getInt("id");
    model = resultSet.getString("model");
    price = resultSet.getFloat("price");
    resolution = resultSet.getString("resolution");
    operationSystem = resultSet.getString("operationSystem");
    internetAccess = resultSet.getBoolean("internetAccess");
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
        case "operationSystem" -> this.operationSystem = value;
        case "internetAccess" -> this.internetAccess = value.equalsIgnoreCase("true");
      }
    }
  }

  @Override
  public Televise create() {
    return new Televise();
  }

  @Override
  PreparedStatement getValuesStatement(String table, Connection connection) throws SQLException {
    PreparedStatement statement = connection.prepareStatement("INSERT INTO " + table
        + " (id, model, price, resolution, operationSystem, internetAccess) VALUES (?, ?, ?, ?, ?, ?)");

    statement.setInt(1, id);
    statement.setString(2, model);
    statement.setFloat(3, price);
    statement.setString(4, resolution);
    statement.setString(5, operationSystem);
    statement.setBoolean(6, internetAccess);

    return statement;
  }

  @Override
  String getCreateTableUpdate(String name) {
    return String.format(
        "CREATE TABLE %s (id int PRIMARY KEY, model varchar(255), price float8, resolution "
            + "varchar(255), "
            + "operationSystem varchar(255), internetAccess boolean)", name);
  }

  @Override
  String getValuesLine() {
    return String.format(Locale.US, "(%d, '%s', %.4f, '%s', '%s', '%b')", id, model, price, resolution,
        operationSystem, internetAccess);
  }

  @Override
  String getValuesHeaderLine() {
    return "(id, model, price, resolution, operationSystem, internetAccess)";
  }
}
