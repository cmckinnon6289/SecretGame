 package zork;

public class RoomStateException extends Exception {
    public RoomStateException() {
        super("problem with a room");
        printStackTrace();
        System.exit(-1);
    }
    public RoomStateException(CoordKey key) {
        super("room already exists with coordinates: "+key.exceptionPrint());
        printStackTrace();
        System.exit(-1);
    }
}

