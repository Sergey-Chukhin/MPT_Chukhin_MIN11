package org.jdbc.worker;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBWorker {
  protected Connection connection;

  public DBWorker(String login, String password, String host, String dbName) {
    try {
      Properties properties = new Properties();
      properties.setProperty("user", login);
      properties.setProperty("password", password);

      connection = DriverManager.getConnection(
          String.format("jdbc:postgresql://%s/%s", host, dbName),
          properties
      );
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private boolean tableExists(String name) throws SQLException {
    name = name.toLowerCase();

    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet resultSet = metaData.getTables(null, null, name, null);
    boolean found = false;

    while (resultSet.next()) {
      String tableName = resultSet.getString("TABLE_NAME");
      if (tableName != null && tableName.equals(name)) {
        found = true;
        break;
      }
    }

    return found;
  }

  public void createTableFromObject(String name, Loadable object, Boolean dropIfExists)
      throws SQLException {
    if (tableExists(name)) {
      if (dropIfExists) {
        Statement dropStatement = connection.createStatement();
        dropStatement.executeUpdate("DROP TABLE " + name.toLowerCase());
      } else {
        return;
      }
    }

    Statement statement = connection.createStatement();
    statement.executeUpdate(object.getCreateTableUpdate(name));
  }

  public void loadObjectToBase(Loadable object, String table) throws SQLException {
    PreparedStatement statement = object.getValuesStatement(table, connection);
    statement.executeUpdate();
  }

  public void loadObjectsToBase(List<? extends Loadable> objects, String table)
      throws SQLException {
    Statement statement = connection.createStatement();
    statement.executeUpdate(Loadable.getLoadObjectsUpdate(objects, table));
  }

  public Display getById(int id, int type) throws SQLException {
    PreparedStatement search = connection.prepareStatement(
        "SELECT * FROM "
            .concat(type == 1 ? "monitors" : "televises")
            .concat(" WHERE id = ?")
    );
    search.setInt(1, id);
    ResultSet result = search.executeQuery();
    if (!result.next()) {
      throw new RuntimeException("Object not found");
    }

    if (type == 1) {
      return new Monitor(result);
    } else {
      return new Televise(result);
    }
  }

  public ArrayList<Display> getAllObjects(String table, int type) throws SQLException {
    Statement search = connection.createStatement();
    ResultSet result = search.executeQuery("SELECT * FROM ".concat(table));
    if (!result.next()) {
      throw new RuntimeException("Nothing found");
    }

    ArrayList<Display> objects = new ArrayList<>();
    while (result.next()) {
      if (type == 1) {
        objects.add(new Televise(result));
      } else {
        objects.add(new Monitor(result));
      }
    }

    return objects;
  }
}
