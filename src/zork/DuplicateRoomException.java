package zork;

public class DuplicateRoomException extends Exception {
    public DuplicateRoomException() {
        super("room already exists with these coordinates");
        printStackTrace();
        System.exit(-1);
    }
    public DuplicateRoomException(CoordKey key) {
        super("room already exists with coordinates: "+key.exceptionPrint());
        printStackTrace();
        System.exit(-1);
    }
}