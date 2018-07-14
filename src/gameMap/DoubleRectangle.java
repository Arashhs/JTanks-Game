package gameMap;

/**
 * Rectangle class with doubles and not integers
 * Useless for now but it is needed for future add-ons and updates
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

    /**
     * Set bounds for the rectangle
     * @param x X parameter for the upper left side
     * @param y Y parameter for the upper left side
     * @param width width for the rectangle
     * @param height height for the rectangle
     */
    public void setBounds(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
