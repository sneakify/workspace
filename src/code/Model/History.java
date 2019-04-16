package stockify;

public class History {
  
  String date;
  int value;
  
  public History(String date, int value) {
    this.date = date;
    this.value = value;
  }
  
  public String getDate() {
    return date;
  }
  
  public int getValue() {
    return value;
  }
}

