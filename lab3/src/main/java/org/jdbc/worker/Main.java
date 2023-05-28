package org.jdbc.worker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws SQLException, IOException, InterruptedException {
    DBWorker worker = new DBWorker("worker", "C11m0LMjFbyd4fTK", "localhost", "lab3");
    DirtyDatabaseWorker dirtyDatabaseWorker = new DirtyDatabaseWorker("worker", "C11m0LMjFbyd4fTK", "localhost", "lab3");

    worker.createTableFromObject("monitors", new Monitor(), true);
    worker.createTableFromObject("televises", new Televise(), true);

    CSVDisplayReader<Televise> televiseCSVDisplayReader = new CSVDisplayReader<>(
        "televises.csv", ",", new Televise());
    CSVDisplayReader<Monitor> monitorCSVDisplayReader = new CSVDisplayReader<>(
        "monitors.csv", ",", new Monitor());

    ArrayList<Televise> televises = televiseCSVDisplayReader.readObjects();
    ArrayList<Monitor> monitors = monitorCSVDisplayReader.readObjects();

    worker.loadObjectsToBase(televises, "televises");
    worker.loadObjectsToBase(monitors, "monitors");

    dirtyDatabaseWorker.dirtyReadAndRollback("SELECT * FROM monitors WHERE id = 30823");
    Thread.sleep(150);
    worker.connection.createStatement().executeUpdate("DELETE FROM monitors WHERE id = 30823");

    System.out.println(worker.getById(103655, 1).toString().concat("\n"));

    for (Display display : worker.getAllObjects("monitors", 2)) {
      System.out.println(display);
    }
  }

  // C11m0LMjFbyd4fTK
}