package printing;

import frame.Board;

import java.awt.*;
import java.awt.geom.AffineTransform;

// rysowalna linia
public class Line extends PrintElement {

    private double x; // współrzędne początku linii
    private double y;
    private double length;
    private double angle;

    protected Color color; // kolor linii

    public Line(double x0, double y0, double length, double angle, Color color){
        this(x0, y0, length, angle);
        this.color = color;
    }

    public Line(double x0, double y0, double length, double angle){
        x = x0;
        y = y0;
        this.length = length;
        this.angle = angle;
        color = new Color(0,0,0);
    }

    public double angle(){
        return angle;
    }

    @Override
    public void print(Graphics drawer, Board board1) {
        Graphics2D internalDrawer = (Graphics2D) drawer;

        AffineTransform initialTransform = internalDrawer.getTransform();
        transform = new AffineTransform();

        transform.translate(board1.getScreenCenter()[0], board1.getScreenCenter()[1]);
        transform.scale(1,-1);

        internalDrawer.setTransform(transform);
        internalDrawer.setColor(color);
        internalDrawer.drawLine(
                (int)Math.rint(x),(int)Math.rint(y),
                (int) Math.round(Math.cos(angle)*length + x), (int) Math.round(Math.sin(angle)*length + y)
        );

        internalDrawer.setColor(new Color(0,0,0));
        internalDrawer.setTransform(initialTransform);
    }

    public double length(){
        return length;
    }

    @Override
    public void rotate(double radians) {
        angle += radians;
    }

    @Override
    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
