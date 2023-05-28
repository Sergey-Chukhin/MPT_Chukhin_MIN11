public class Monitor extends Display {

  public boolean nvidiaGSync;
  public int frameRate;

  Monitor() {
    super();

    this.nvidiaGSync = false;
    this.frameRate = 60;
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
  public Monitor create() {
    return new Monitor();
  }
}
