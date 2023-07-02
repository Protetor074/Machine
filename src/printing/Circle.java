package printing;

import frame.Board;

import java.awt.*;

// punkt ustawienia to górny wierzchołek
public class Circle extends PrintElement {

    Point point;

    public Circle(double x, double y, double sideLength){
        point = new Point(x,y);

    }

    @Override
    public void print(Graphics drawer, Board board) {
        point.print(drawer, board);

    }

    @Override
    public void rotate(double radians) {

    }

    @Override
    public void translate(double x, double y) {

    }

    @Override
    public double getX() {
        return point.getX();
    }

    @Override
    public double getY() {
        return point.getY();
    }
}
