package zork;

public class CoordKey {
    private int x;
    private int y;

    /**
     * Contains x and y values of a room. Used to pass coordinates through a HashMap.
     * @param x coord
     * @param y coord
     */
    public CoordKey(int x,int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Used pretty much exclusively by {@code Room.nextRoom()} to break references to the current room CoordKey.
     * @param room
     */
    public CoordKey(Room room) {
        this.x = Integer.valueOf(room.getRoomCoords().x);
        this.y = Integer.valueOf(room.getRoomCoords().y);
    }

    /**
     * Updates coordinates.
     * @param val - value to update
     * @param inc - increment
    */

    public void addTo(String val, int inc) {
        if (val.equals("x"))
            this.x += inc;
        else if (val.equals("y"))
            this.y += inc;
        else {
            throw new IllegalArgumentException("invalid addTo value");
        }
    }

    public String exceptionPrint() {
        return Integer.toString(this.x)+","+Integer.toString(this.y);
    }

    @Override
    public int hashCode(){
        int tmp = (y+((x+1)/2));
        return x+(tmp*tmp);
    }

    /**
     * Compares coordinates of CoordKeys.
     * @return whether coordinates match
    */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CoordKey))
            return false;
        CoordKey ref = (CoordKey) obj;
        return ref.x == this.x && ref.y == this.y;
    }
}
