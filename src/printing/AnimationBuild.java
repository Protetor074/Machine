package printing;

import errors.ErrorValue;
import frame.Board;

import java.awt.*;

public class AnimationBuild extends PrintElement {

    Line l1Arm; // ramię wewnętrzne
    Line l2Arm; // ramię zewnętrzne
    Point conectPoint; // łączenie między ramionami
    Point endingPoint; // zakończenie drugiego ramienia maszyny
    Point sPoint; // punkt zakpńczenia obniżania
    Circle slider; // suwak

    public AnimationBuild(int x, int y, int length1, int length2, int d, int h, int s) throws ErrorValue {

        //Warunki konstrukcyjne
        if(d==0) throw new ErrorValue("pozycja suwaka");
        if(s>length1) throw new ErrorValue("ramie 1");
        if((length1-h)*(length1-h)+(d*d)>length2*length2) throw new ErrorValue("ramie 2");

        sPoint = new Point(x,y-s);

        l1Arm = new Line(x,y,length1,Math.PI*1/2);

        slider = new Circle(d,h,20);

        l2Arm = new Line((int)Math.round(l1Arm.getX() + length1 * Math.cos(l1Arm.angle())),
                (int)Math.round(l1Arm.getY() + length1 * Math.sin(l1Arm.angle())),
                length2,
                calculateAngle()
        );

        conectPoint = new Point((int)Math.round(l1Arm.getX() + length1 * Math.cos(l1Arm.angle())), (int)Math.round(l1Arm.getY() + length1 * Math.sin(l1Arm.angle())));
        endingPoint = new Point((int)Math.round(l2Arm.getX() + length2 * Math.cos(l2Arm.angle())), (int)Math.round(l2Arm.getY() + length2 * Math.sin(l2Arm.angle())));
    }

    public double getEndingX(){
        return l2Arm.getX() + l2Arm.length() * Math.cos(l2Arm.angle());
    }

    public double getEndingY(){
        return l2Arm.getY() + l2Arm.length() * Math.sin(l2Arm.angle());
    }

    public double calculateAngle(){
        double innerArmEndingX = l1Arm.length() * Math.cos(l1Arm.angle()) + l1Arm.getX();
        double innerArmEndingY = l1Arm.length() * Math.sin(l1Arm.angle()) + l1Arm.getY();
        double angle = Math.atan((slider.getY() - innerArmEndingY)/(slider.getX()-innerArmEndingX));

        if(slider.getX() < innerArmEndingX) angle += Math.PI;
        else if(slider.getX() == innerArmEndingX) angle = Math.PI/2 * (slider.getY() > 0 ? 1 : -1);

        return angle;
    }

    @Override
    public void print(Graphics drawer, Board board) {
        sPoint.print(drawer, board);
        slider.print(drawer, board);
        l1Arm.print(drawer, board);
        conectPoint.print(drawer, board);
        l2Arm.print(drawer, board);
        endingPoint.print(drawer, board);
    }

    @Override
    public void rotate(double radians) {
        l1Arm.rotate(radians);
        conectPoint.translate((int) Math.round(l1Arm.getX() + l1Arm.length() * Math.cos(l1Arm.angle()) - conectPoint.getX()), (int)Math.round(l1Arm.getY() + l1Arm.length()  * Math.sin(l1Arm.angle()) - conectPoint.getY()));
        l2Arm.translate(conectPoint.getX() - l2Arm.getX(), conectPoint.getY() - l2Arm.getY());
        l2Arm.rotate(calculateAngle() - l2Arm.angle());
        endingPoint.translate((int) Math.round(l2Arm.getX() + l2Arm.length() * Math.cos(l2Arm.angle()) - endingPoint.getX()), (int) Math.round(l2Arm.getY() + l2Arm.length()  * Math.sin(l2Arm.angle()) - endingPoint.getY()));
    }

    @Override
    public void translate(double x, double y) {
        l1Arm.translate(x,y);
        conectPoint.translate((int) Math.round(l1Arm.getX() + l1Arm.length() * Math.cos(l1Arm.angle()) - conectPoint.getX()), (int)Math.round(l1Arm.getY() + l1Arm.length()  * Math.sin(l1Arm.angle()) - conectPoint.getY()));
        l2Arm.translate(conectPoint.getX() - l2Arm.getX(), conectPoint.getY() - l2Arm.getY());
        l2Arm.rotate(calculateAngle() - l2Arm.angle());
        endingPoint.translate((int) Math.round(l2Arm.getX() + l2Arm.length() * Math.cos(l2Arm.angle()) - endingPoint.getX()), (int) Math.round(l2Arm.getY() + l2Arm.length()  * Math.sin(l2Arm.angle()) - endingPoint.getY()));
    }

    @Override
    public double getX() {
        return l1Arm.getX();
    }

    @Override
    public double getY() {
        return l1Arm.getY();
    }
}
