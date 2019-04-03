package css241;
// A Point object represents a pair of (x, y) coordinates.

public class Point {
    private int x;
    private int y;
    
 // Constructs a new point with the (0,0) location.
    public Point() {
        setLocation(0,0);
    }

    // Constructs a new point with the given (x, y) location.
    public Point(int initialX, int initialY) {
        setLocation(initialX, initialY);
    }
    
    // Constructs a new Point from another Point object
    public Point(Point other) {
    	this(other.x,other.y);
    }

    // Returns the distance between this point and (0, 0).
    public double distanceFromOrigin() {
        return Math.sqrt(x * x + y * y);
    }

    // Returns the x-coordinate of this point.
    public int getX() {
        return x;
    }

    // Returns the y-coordinate of this point.
    public int getY() {
        return y;
    }

    // Sets this point's (x, y) location to the given values.
    public void setLocation(int newX, int newY) {
        x = newX;
        y = newY;
    }

    // Returns a String representation of this point.
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // Shifts this point's location by the given amount.
    public void translate(int dx, int dy) {
        setLocation(x + dx, y + dy);
    }
    
    public void flip() {
    	
    }
}
