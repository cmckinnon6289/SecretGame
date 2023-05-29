package zork;

public class Key extends Item {
  private String keyID;
  
  public Key(String keyId, String keyName, int weight, Room location) {
    super(weight, keyName, false, location);
    this.keyID = keyId;
  }

  public String getKeyId() {
    return keyID;
  }


}
