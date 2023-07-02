package frame;

import javax.swing.*;


public class Screen extends JLayeredPane {

    protected final Board board; // panel wewnętrzny
    protected int windowX; // pozycja X ekranu względem punktu (0,0) okna
    protected int windowY; // pozycja Y ekranu względem punktu (0,0) okna

    // konstruktor
    Screen(String title, int x, int y, int w, int h, int windowX, int windowY){
        //ekran symulacji i wykresów
        super();

        setBorder(BorderFactory.createTitledBorder(title));
        setBounds(x,y,w,h);
        this.windowX = windowX;
        this.windowY = windowY;

        final int border = 8;
        board = new Board(border,2*border,getWidth() - 2*border,getHeight() - 3*border, windowX + border, windowY + 2*border);
        add(board,2);
        setLayout(null);
    }
}
