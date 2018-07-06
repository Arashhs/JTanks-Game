package gameMap;

/**
 * Rectangle class with doubles and not integers
 */
public class DoubleRectangle {
    public double x, y, width, height;

    public DoubleRectangle(){
        setBounds(0,0,0,0);
    }

    public DoubleRectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setBounds(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
