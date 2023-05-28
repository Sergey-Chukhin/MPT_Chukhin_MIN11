import java.io.IOException;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

  public static void main(String[] args) throws IOException {
    SessionFactory sessionFactory = new Configuration().configure()
        .addAnnotatedClass(Vendor.class)
        .addAnnotatedClass(Storage.class)
        .addAnnotatedClass(Display.class)
        .buildSessionFactory();

    Session session = sessionFactory.openSession();
    BaseDOD worker = new BaseDOD(session);

    ArrayList<Object> data = new ArrayList<>();
    data.addAll(new CSVDataReader<>("vendors.csv", ";", new Vendor()).readFile());
    data.addAll(new CSVDataReader<Storage>("storages.csv", ";", new Storage()).readFile());
    data.addAll(new CSVDataReader<>("displays.csv", ",", new Display()).readFile());

    worker.loadObjects(data);

    for (Display display : worker.getAllDisplays()) {
      System.out.println(display);
    }

    System.out.println("\n\n");

    Storage targetStorage = new Storage(2);
    worker.getFramesFromOneStorageAndVendor(worker.findStorageById(2), worker.findVendorById(1))
        .forEach(display -> System.out.println(display));

    System.out.println("Все объекты в базе данных:\n");
    worker.getAllObjects().forEach(item -> {
      System.out.println(item);
    });

    session.close();
  }
}
