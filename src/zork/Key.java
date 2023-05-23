package zork;

public class Key extends Item {

  private String keyId;

  public Key(String keyId, String keyName, int weight, Room location) {
    super(weight, keyName, false, location);
    this.keyId = keyId;
  }

  public String getKeyId() {
    return keyId;
  }


}
