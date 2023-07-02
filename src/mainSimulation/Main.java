/**
 * AUTOR:
 * Kamil Gondek
 *
 * SPOSÓB KOMPILACJI:
 * set path="C:\Program Files\Java\jdk-11.0.2\bin\";%path%
 * javac -encoding UTF8 -d tojar/classes @compile.txt
 *
 * SPOSÓB BUDOWANIA:
 * jar --create --file lab04_pop.jar --main-class MainSimulation.Main --module-version 1.0 -C tojar\classes module-info.class -C tojar classes -C tojar src
 *
 * SPOSÓB URUCHAMIANIA:
 * java -p lab04_pop.jar -m MainSimulation.Main
 * */

package mainSimulation;

import frame.MyFrame;

public class Main {
    // odpalenie aplikacji
    public static void main(String[] args) {

        MyFrame myFrame = new MyFrame("Symulacja");
        myFrame.setVisible(true);
    }
}