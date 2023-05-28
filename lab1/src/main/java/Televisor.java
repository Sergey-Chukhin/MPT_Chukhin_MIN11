public class Televisor extends Display {

  public String operationSystem;
  public boolean internetAccess;

  Televisor() {
    super();

    operationSystem = "";
    internetAccess = false;
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
  public Televisor create() {
    return new Televisor();
  }
}
