package printing;

import frame.Board;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Point extends PrintElement {

    private double x;
    private double y;
    private double radius;

    public Point(double x, double y, double radius){
        this(x,y);
        this.radius = radius;
    }

    public Point(double x, double y){
        this.x = x;
        this.y = y;
        radius = 2;
    }

    @Override
    public void print(Graphics drawer, Board board) {
        Graphics2D internalDrawer = (Graphics2D) drawer;

        AffineTransform initialTransform = internalDrawer.getTransform();
        transform = new AffineTransform();

        transform.translate(board.getScreenCenter()[0], board.getScreenCenter()[1]);
        transform.scale(1,-1);

        internalDrawer.setTransform(transform);
        internalDrawer.drawOval((int)Math.rint(x-radius),(int) Math.rint(y-radius), (int)Math.rint(2*radius), (int)Math.rint(2*radius));
        internalDrawer.fillOval((int)Math.rint(x-radius),(int) Math.rint(y-radius), (int)Math.rint(2*radius), (int)Math.rint(2*radius));
    }

    @Override
    public void rotate(double radians) {

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
