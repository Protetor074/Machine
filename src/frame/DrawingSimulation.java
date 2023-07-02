package frame;

import printing.PrintElement;
import printing.AnimationBuild;
import printing.Point;
import errors.ErrorPosition;

public class DrawingSimulation extends Screen {
    private double x0;
    private double y0;
    private double angle0;

    DrawingSimulation(String title, int x, int y, int w, int h, int windowX, int windowY, int s) throws Exception {
        super(title, x, y, w, h, windowX, windowY);

        //symulacja
        board.addPrintElement(new AnimationBuild(0,0,50,100, 25, 45,30));//wartosci początkowe
        AnimationBuild animationBuild = (AnimationBuild) board.getPrintElement().get(0);
        x0 = animationBuild.getEndingX();
        y0 = animationBuild.getEndingY();
        angle0 = animationBuild.calculateAngle();
    }

    //odświerzanie wyglądu symulacji
    public void updateSimulator(int x, int y, int length1, int length2, int s) throws Exception {
        if(board.getPrintElement().size() == 0) return;
        PrintElement oldMachine = board.getPrintElement().get(0);

        board.getPrintElement().clear();
        try {
            AnimationBuild newAnimationBuild = new AnimationBuild(0,0,length1,length2, x, y,s);
            board.addPrintElement(newAnimationBuild);
            x0 = newAnimationBuild.getEndingX();
            y0 = newAnimationBuild.getEndingY();
            angle0 = newAnimationBuild.calculateAngle();
        } catch (Exception e) {
            board.addPrintElement(oldMachine);
            throw e;
        }
    }

    //działanie symulacji
    public void moveMachine(boolean addDot,int machineMove) throws ErrorPosition {
        if(board.getPrintElement().size() == 0) return;
        AnimationBuild animationBuild = (AnimationBuild) board.getPrintElement().get(0);

        //przesunięcie maszyny o machineMove
        animationBuild.translate(0,machineMove);

        //Warunki na wyjście końcówki poza zakres
        if(animationBuild.getEndingX() > board.getWidth()/2 || animationBuild.getEndingX() < - board.getWidth()/2)
            throw new ErrorPosition("końcówka maszyny", (int)Math.rint(animationBuild.getEndingX()), (int)Math.rint(animationBuild.getEndingY()));
        if(animationBuild.getEndingY() > board.getHeight()/2 || animationBuild.getEndingY() < - board.getHeight()/2)
            throw new ErrorPosition("końcówka maszyny", (int)Math.rint(animationBuild.getEndingX()), (int)Math.rint(animationBuild.getEndingY()));

        //rysowanie punktów
        if(addDot) board.addPrintElement(new Point((int)Math.rint(animationBuild.getEndingX()), (int)Math.rint(animationBuild.getEndingY()),2));
        else board.addPrintElement(new Point((int)Math.rint(animationBuild.getEndingX()), (int)Math.rint(animationBuild.getEndingY()),0));
    }

    // pobranie prędkości poziomej
    public int getXSpeed(){

        AnimationBuild animationBuild = (AnimationBuild) board.getPrintElement().get(0);

        double x1 = animationBuild.getEndingX();
        double xSpeed = (x1 - x0)*10000;//Mnoże aby wykres był płynny

        x0 = x1;

        return (int)Math.rint(xSpeed);//Zaokrągli
    }

    // pobranie prędkości pionowej
    public int getYSpeed(){

        AnimationBuild animationBuild = (AnimationBuild) board.getPrintElement().get(0);

        double y1 = animationBuild.getEndingY();
        double ySpeed = (y1 - y0)*10000;

        y0 = y1;

        return (int)Math.rint(ySpeed);
    }
}
