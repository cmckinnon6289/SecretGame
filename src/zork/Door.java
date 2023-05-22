package zork;

public class Door {

    private boolean isOpen;
    private String keyToOpen;

    public Door(String keyname) {
        keyToOpen = keyname; 
        this.isOpen = false;
    }

    public void open(String keyname) {
        if (keyname.equals(keyToOpen)) {
            isOpen = true;
            System.out.println("Door is now open.");
        } else {
            System.out.println("Incorrect key. Door remains closed.");
        }
    }

    public void close() {
        isOpen = false;
        System.out.println("Door is now closed.");
    }

    public boolean isOpen() {
        return isOpen;
    }
}
 