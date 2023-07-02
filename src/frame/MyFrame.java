package frame;

import errors.ErrorPosition;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {

    //Główne okienko
    private JPanel applicationPanel;

    //Panele
    private JPanel configurationPanel;//panel do wprowadzania i zatwierdzania zmiennych
    private JPanel speedGraphPanel;//panel z wykresami

    //Spinery
    private JSpinner[] setValueSpiner;//spinery do ustawiania wartości

    //Labele
    private JLabel[] parametersLabels; // podpisy do pól z parametrami

    //Buttony
    private JButton startSimulationButton; // przycisk do odpalenia animacji

    //Panel Symulacji
    private DrawingSimulation simulation; // panel z wyświetlaną animacją maszyny

    private SpeedGraph xSpeedGraph; // panel z wykresem prędkości pionowej
    private SpeedGraph ySpeedGraph; // panel z wykresem prędkości poziomej

    //Wartości stałe
    private final int yDistance = 25;//odstęp w osi y
    private final int xDistance = 25;//odstęp w osi x
    private final int screenWidth = 1920;
    private final int screenHeight = 1040;
    final int textHight = 30;//Wielkość pól tekstowych

    private int sValue;
    private int animationSpeed;

    //Moje okienko
    public MyFrame(String name) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        guiCreate();
        setActionButton();
        setChangeParameters();
    }

    //geneowanie gównego GUI
    private void guiCreate(){

        final int border = 10;

        //generowanie gównego panelu
        setSize(screenWidth, screenHeight);
        applicationPanel = new JPanel();
        setContentPane(applicationPanel);
        setLayout(null);
        applicationPanel.setLayout(null);
        setResizable(false);

        //panel do wprowadzania ustawień
        configurationPanel = new JPanel();
        configurationPanel.setBorder(BorderFactory.createTitledBorder("Parametry"));
        applicationPanel.add(configurationPanel);
        configurationPanel.setBounds(xDistance, yDistance, (applicationPanel.getWidth()-3* xDistance)/5, (applicationPanel.getHeight() - 4* yDistance)*3/5);

        parametersLabels = new JLabel[]{
                new JLabel("Długość ramienia L1:"),
                new JLabel("Długość ramienia L2:"),
                new JLabel("Wartiść obniżenia ramienia L1:"),
                new JLabel("Odległość punktu przecięcia w osi X:"),
                new JLabel("Odległość punktu przecięcia w osi Y:"),
                new JLabel("Prędkość animacji [ms]:")
        };

        //ustawnienia spinerów
        SpinnerNumberModel valueModel1 = new SpinnerNumberModel(50, 1, 258,1);//Ramie 1
        SpinnerNumberModel valueModel2 = new SpinnerNumberModel(100, 1, 880,1);//Ramie 2
        SpinnerNumberModel valueModel3 = new SpinnerNumberModel(30, 1, 258,1);//Obniżenie
        SpinnerNumberModel valueModel4 = new SpinnerNumberModel(25, -500, 500,1);//Współżendna x suwaka
        SpinnerNumberModel valueModel5 = new SpinnerNumberModel(45, -250, 250,1);//Współżendna y suwaka
        SpinnerNumberModel valueModel6 = new SpinnerNumberModel(100,0 , 10000,20);//Prędkość animacji
        setValueSpiner = new JSpinner[]{
                new JSpinner(valueModel1),
                new JSpinner(valueModel2),
                new JSpinner(valueModel3),
                new JSpinner(valueModel4),
                new JSpinner(valueModel5),
                new JSpinner(valueModel6)
        };

        for(int i = 0; i < parametersLabels.length; i++){
            parametersLabels[i].setBounds(border, border + yDistance + i * 2 * textHight, configurationPanel.getWidth() - 2* border, textHight);
            setValueSpiner[i].setBounds(border, parametersLabels[i].getY() + textHight, configurationPanel.getWidth() - 2* border, textHight);
            configurationPanel.add(parametersLabels[i]);
            configurationPanel.add(setValueSpiner[i]);
        }

        //przycisk startu symulacji
        startSimulationButton = new JButton("Start Simulation");
        startSimulationButton.setBounds(border + configurationPanel.getWidth()/8, configurationPanel.getY() + configurationPanel.getHeight() - textHight * 2 - border - 2* yDistance, 3*(configurationPanel.getWidth() - 2* border)/4, textHight * 2);
        configurationPanel.add(startSimulationButton);
        configurationPanel.setLayout(null);

        //panel symulacji
        try {
            simulation = new DrawingSimulation("Symulator", configurationPanel.getX() + configurationPanel.getWidth() + xDistance, yDistance, (applicationPanel.getWidth()-3* xDistance)*4/5, (applicationPanel.getHeight() - 4* yDistance)*3/5, configurationPanel.getX() + configurationPanel.getWidth() + xDistance, yDistance,sValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        applicationPanel.add(simulation);

        //panel wykresów
        speedGraphPanel = new JPanel();
        applicationPanel.add(speedGraphPanel);
        speedGraphPanel.setBounds(xDistance, configurationPanel.getY() + configurationPanel.getHeight() + yDistance, applicationPanel.getWidth()-2* xDistance, (applicationPanel.getHeight() - 2* yDistance)*2/5);
        speedGraphPanel.setLayout(null);

        //wykres prędkości poziomej
        xSpeedGraph = new SpeedGraph("Prędkość w osi X",0,0, speedGraphPanel.getWidth(), (speedGraphPanel.getHeight()- yDistance)/2, speedGraphPanel.getX(), speedGraphPanel.getY());
        speedGraphPanel.add(xSpeedGraph);

        //wykres prędkości pionowej
        ySpeedGraph = new SpeedGraph("Prędkość w osi Y", 0, xSpeedGraph.getY() + xSpeedGraph.getHeight() + yDistance, speedGraphPanel.getWidth(), (speedGraphPanel.getHeight()- yDistance)/2, speedGraphPanel.getX(), speedGraphPanel.getY() + xSpeedGraph.getY() + xSpeedGraph.getHeight() + yDistance);
        speedGraphPanel.add(ySpeedGraph);
    }


    private void setActionButton(){
        startSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                animation();
            }
        });
    }

    private void setChangeParameters(){
        setValueSpiner[0].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rearrangeScreen();
            }
        });

        setValueSpiner[1].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rearrangeScreen();
            }
        });

        setValueSpiner[2].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rearrangeScreen();
            }
        });

        setValueSpiner[3].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rearrangeScreen();
            }
        });

        setValueSpiner[4].addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rearrangeScreen();
            }
        });
    }

    public void animation(){


        animationSpeed = ((int)setValueSpiner[5].getValue()/(int)setValueSpiner[3].getValue())/2;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(rearrangeScreen() != 0) return;
                ySpeedGraph.clear();//czyczczenie wykresów
                xSpeedGraph.clear();

                startSimulationButton.setEnabled(false);

                int verticalVelocity;
                int horizontalVelocity;


                for(int i = 0; i <= sValue*2; i ++) {

                    verticalVelocity = simulation.getYSpeed();//rysowanie wykresow
                    horizontalVelocity = simulation.getXSpeed();

                    //zbieranie punktów
                    ySpeedGraph.addPoint(i , verticalVelocity);
                    xSpeedGraph.addPoint(i, horizontalVelocity);

                    try {//rysowanie symulacji
                        if(i<=sValue){
                            simulation.moveMachine(i % sValue/2 == 0,-1);
                        }
                        else{
                            simulation.moveMachine(i % sValue/2 == 0,1);
                        }
                    } catch (ErrorPosition e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                        break;
                    }
                    repaint();

                    try {
                        Thread.sleep(animationSpeed);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                //rysowanie wykresów prędkości
                ySpeedGraph.drawPlot();
                xSpeedGraph.drawPlot();
                repaint();

                startSimulationButton.setEnabled(true);
            }
        });
        t.start();
    }

    private int rearrangeScreen(){
        int lengthArm1, lengthArm2, sValue, hValue, dValue;

        lengthArm1 = (int)setValueSpiner[0].getValue();
        lengthArm2 = (int)setValueSpiner[1].getValue();
        sValue = (int)setValueSpiner[2].getValue();
        hValue = (int)setValueSpiner[4].getValue();
        dValue = (int)setValueSpiner[3].getValue();
        this.sValue=sValue;

        //czyszczenie pola symulacji
        try {
            simulation.updateSimulator(dValue,hValue, lengthArm1, lengthArm2,sValue);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            return -1;
        }
        repaint();

        return 0;
    }
}
