package frame;

import printing.PrintElement;
import printing.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel {
    private int[] screenCenter; // współrzędne środka ekranu
    private List<PrintElement> printElements; // elementy do narysowania

    Board(int x, int y, int w, int h, int windowX, int windowY){
        //Pola do rysowania
        setBounds(x,y,w,h);
        setBackground(new Color(255,255,255));

        //centrum i elementy do narysowania
        foundScreenCenter(windowX, windowY);
        printElements = new ArrayList<>();
    }

    public int[] getScreenCenter(){
        return screenCenter;
    }

    public void translateScreenCenter(int xt, int yt){
        screenCenter[0] += xt;
        screenCenter[1] += yt;
    }

    public void addPrintElement(PrintElement newPrintElement){
        printElements.add(newPrintElement);
    }

    public void setPrintElement(List<PrintElement> printElements){
        this.printElements = printElements;
    }

    public List<PrintElement> getPrintElement(){
        return printElements;
    }

    private void foundScreenCenter(int windowX, int windowY){
        screenCenter = new int[2];
        screenCenter[0] = windowX + getWidth()/2;
        screenCenter[1] = windowY + getHeight()/2;
    }

    // rysowanie rzeczy
    @Override
    protected void paintComponent(Graphics arg0){
        super.paintComponent(arg0);

        Point p = new Point(0,0);
        p.print(arg0, this);

        for (int i = 0; i < printElements.size(); i++) printElements.get(i).print(arg0, this);
    }
}
