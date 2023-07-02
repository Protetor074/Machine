package printing;


import frame.Board;

import java.awt.*;
import java.awt.geom.AffineTransform;

// obiekt możliwy do narysowania
public abstract class PrintElement {

    protected AffineTransform transform; // transformacja afiniczna

    abstract public void print(Graphics drawer, Board board);
    abstract public void rotate(double radians);

    abstract public void translate(double x, double y);

    abstract public double getX();

    abstract public double getY();

}
