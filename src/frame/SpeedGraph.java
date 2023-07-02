package frame;

import printing.PrintElement;
import printing.Line;
import printing.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SpeedGraph extends Screen {

    private int maxX = -1;
    private List<Integer> yValues = new ArrayList<>();
    private int maxY = 0;
    private int minY = 0;
    private JLabel max;
    private JLabel min;

    SpeedGraph(String title, int x, int y, int w, int h, int windowX, int windowY){
        super(title,x,y,w,h, windowX, windowY);

        //Tworzenie wykresu z punktami
        board.translateScreenCenter(-board.getWidth()/2,0);//translacja do prawej
        board.addPrintElement(new Line(0,0, board.getWidth(),0));

        board.addPrintElement(new Point((board.getWidth()-2)/4,0));
        board.addPrintElement(new Point((board.getWidth()-2)/2,0));
        board.addPrintElement(new Point((board.getWidth()-2)*3/4,0));
        board.addPrintElement(new Point((board.getWidth()-2),0));


        //Informacja o maksymalnym wychyleniu
        max = new JLabel("");
        max.setBounds(board.getX(), board.getY(),100,10);
        add(max, 0);

        min = new JLabel("");
        min.setBounds(board.getX(), board.getY()+ board.getHeight()-11,100,10);
        add(min, 1);

        this.setLayout(null);
    }

    // dodawanie punktu
    public void addPoint(int x, int y){
        if(maxX >= x) return;

        maxX = x;
        if(yValues.size() > 1){
            double previousChangeRate = yValues.get(yValues.size()-1) - yValues.get(yValues.size()-2);
            Integer lastY = yValues.get(yValues.size()-1);


            double difference = Math.abs(previousChangeRate + lastY - y);
            if( difference > 9000 && difference < 11000)
                y = lastY;

        }
        yValues.add(y);

        if(maxY < y) maxY = y;
        if(minY > y) minY = y;
    }

    // rysowanie grafu
    public void drawPlot(){
        int currX, prevX = 0;
        int currY, prevY = 0;

        int length;
        double angle;

        int maximum = Math.max(3*Math.abs(maxY), 3*Math.abs(minY));

        for(int x = 0; x < maxX; x++){
            currX = (int)((double)(board.getWidth()-2)/(maxX) * x);
            currY = (int)((double) board.getHeight()/(maximum) * yValues.get(x));

            length = (int)Math.sqrt(Math.pow(currX-prevX,2) + Math.pow(currY-prevY,2));
            angle = Math.atan((float)(currY-prevY)/(currX-prevX));

            board.addPrintElement(new Line(prevX, prevY, length,angle,new Color(38, 43, 233)));


            prevX = currX;
            prevY = currY;
        }

        //Wartości maksymalne
        max.setText(Double.toString((double)maximum/2000));
        min.setText(Double.toString(-(double)maximum/2000));
    }

    // usuwanie punktu
    public void clear(){
        List<PrintElement> printElements = new ArrayList<>();
        for(PrintElement d : board.getPrintElement())
            if(!(d instanceof Point) &&  !(d instanceof Line))
                printElements.add(d);
        board.setPrintElement(printElements);

        board.addPrintElement(new Line(0,0, board.getWidth(),0));

        board.addPrintElement(new Point((board.getWidth()-2)/4,0));
        board.addPrintElement(new Point((board.getWidth()-2)/2,0));
        board.addPrintElement(new Point((board.getWidth()-2)*3/4,0));
        board.addPrintElement(new Point((board.getWidth()-2),0));

        maxX = -1;
        minY = 0;
        maxY = 0;
        yValues.clear();
    }
}
