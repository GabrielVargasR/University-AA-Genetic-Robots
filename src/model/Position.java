package model;

class Position {

    private int x;
    private int y;

    public Position(int pX, int pY){
        x = pX;
        y = pY;
    }

    @Override 
    public boolean equals(Object obj) {
        if(obj == null) return false;
        Position toCompare = (Position)obj;
        return toCompare.x == this.x && toCompare.y == this.y;
    }

    @Override
    public int hashCode() {
        return y + (x * IConstants.MAP_SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
