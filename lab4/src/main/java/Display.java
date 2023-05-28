import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.io.Serializable;

//interface IDisplayReader<T> {
//
//  void parseCSVLine(String[] data, String[] titles);
//  T create();
//}

@Entity
@Table(name = "displays")
public class Display extends CSVReadable<Display> implements Serializable {

  public int getId() {
    return id;
  }

  @Id
  @Column(name = "id")
  private int id;

  public String getResolution() {
    return resolution;
  }

  public void setResolution(String resolution) {
    this.resolution = resolution;
  }

  @Column(name = "resolution")
  private String resolution; // "1280*720"

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  @Column(name = "price")
  private float price;

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  @Column(name = "model")
  private String model;

  public float getDiagonal() {
    return diagonal;
  }

  public void setDiagonal(float diagonal) {
    this.diagonal = diagonal;
  }

  public void setVendor(Vendor vendor) {
    this.vendor = vendor;
  }

  @JoinColumn(name = "vendor")
  @ManyToOne(fetch = FetchType.EAGER)
  private Vendor vendor;

  @Column(name = "diagonal")
  private float diagonal; // inches


  public Vendor getVendor() {
    return vendor;
  }

  public void setStorage(Storage storage) {
    this.storage = storage;
  }

  public Storage getStorage() {
    return storage;
  }

  @JoinColumn(name = "storage")
  @ManyToOne(fetch = FetchType.EAGER)
  private Storage storage;


  Display(int id, String model, float price, String resolution, float diagonal) {
    this.id = id;
    this.resolution = resolution;
    this.model = model;
    this.price = price;
    this.diagonal = diagonal;
  }

  public Display() {
    id = 0;
    resolution = "";
    model = "";
    price = 0;
    diagonal = 0;
  }


  @Override
  void parseCSVLine(String[] data, String[] titles) {
    for (int i = 0; i < titles.length; i++) {
      final String field = titles[i];
      final String value = data[i];
      switch (field) {
        case "id" -> id = Integer.parseInt(value);
        case "resolution" -> resolution = value;
        case "model" -> model = value;
        case "price" -> price = Float.parseFloat(value);
        case "diagonal" -> diagonal = Float.parseFloat(value);
        case "vendor" -> vendor = new Vendor(Integer.parseInt(value));
        case "storage" -> storage = new Storage(Integer.parseInt(value));
      }
    }
  }

  @Override
  Display create() {
    return new Display();
  }

  @Override
  public String toString() {
    return String.format("%10d %8s %8s %10.2f %4.1f", id, resolution, model, price, diagonal);
  }
}