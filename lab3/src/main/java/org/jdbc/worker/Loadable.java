package org.jdbc.worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Loadable {

  abstract PreparedStatement getValuesStatement(String table, Connection connection)
      throws SQLException;

  abstract String getCreateTableUpdate(String name);

  abstract String getValuesLine();

  abstract String getValuesHeaderLine();

  static String getLoadObjectsUpdate(List<? extends Loadable> objects, String table) {
    String headers = objects.get(0).getValuesHeaderLine();
    String update = "INSERT INTO ".concat(table).concat(" ").concat(headers).concat(" VALUES");

    ArrayList<String> values = new ArrayList<>();
    for (Loadable object : objects) {
      values.add(object.getValuesLine());
    }

    return update.concat(" ").concat(String.join(",", values));
  }
}
