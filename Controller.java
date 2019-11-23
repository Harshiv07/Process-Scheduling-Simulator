package cpuschedulingsimulator;

import java.awt.Color;
import java.awt.Component;
import java.util.LinkedList;
import javax.swing.*;

public class Controller extends JFrame 
{
    //DECLARING VARIABLES USED BY CONTROLLER TO UPDATE SIMULATION
    double delay;
    int clock;  
    int totalBurstTime;
    boolean cont;
    Process[] processList;
    private boolean finishedSimulation;
    double sumtrTS = 0.0;
    double avgTrTs;
    //GUI COMPONENTS UPDATED BY CONTROLLER
    JPanel jPanel;
    JPanel controlButtonsPanel;
    JProgressBar clockCycle;
    
    Process selectProcess;
    
    Component[] miscellaneousComp;
    SchAlgorithm algo;
    LinkedList<ComputeGanttChart> ganttChart = new LinkedList<>();;
    //CONSTRUCTOR
    Controller(Process[] processList, JProgressBar clockCycle, int delay, JPanel jPanel,Component[] miscellaneousComp, JPanel controlButtonsPanel) {
        this.clock = 0;
        int totalProcess = processList.length;
        this.clockCycle = clockCycle;
        this.processList = processList;
        this.finishedSimulation = false;
        setDelay(delay);
        this.jPanel = jPanel;
        this.controlButtonsPanel = controlButtonsPanel;
    	
        selectProcess = processList[0];
        totalBurstTime = 0;
        for (int i = 0; i < totalProcess; i++) {
            totalBurstTime += processList[i].getBurstTime();
        }
        clockCycle.setMaximum(totalBurstTime);
        this.miscellaneousComp = miscellaneousComp;
        this.cont = false;
		
    }
    //DECLARING SETTERS AND GETTERS
    public void setDelay(int delay) {
        this.delay = (double) (delay / 50.0);
    }
    void setAlgorithm(SchAlgorithm algo) {
        this.algo = algo;
    }
	
    public void setClock(int clock) {
        this.clock = clock;
    }
    public void setCont(boolean cont) {
        this.cont = cont;
    }
	
    public void setFinishedSimulation(boolean finishedSimulation) {
        this.finishedSimulation = finishedSimulation;
        this.clock = 0;
    }
	
    public boolean isFinishedSimulation() {
        return finishedSimulation;
    }
    
    //CONTROLLER FUNCTION TO INCREMENT CPU CLOCK AND UPDATE GUI
    void update() {
        clock++;
        clockCycle.setValue(clock);
        JLabel cpuSelectedProcess = (JLabel) miscellaneousComp[2];
        cpuSelectedProcess.setText(this.selectProcess.getProcessID());
        if (clock >= totalBurstTime) {
    		
            if (!finishedSimulation) {

                for (Component comp: controlButtonsPanel.getComponents()) {
                    if (comp.isEnabled()) {
                        comp.setEnabled(false);
                    }
                    else {
                        comp.setEnabled(true);
                    }
                }
                miscellaneousComp[3].setEnabled(true);
                miscellaneousComp[4].setEnabled(true);
                cpuSelectedProcess.setText("Completed");
                cpuSelectedProcess.setForeground(Color.WHITE);
                finishedSimulation = true;
            }
            obtainResult();
        }
		
    }
    
    //GETTING RESULTS AFTER COMPLETION OF SIMULATION
    void obtainResult() {
        double avgTurnAroundTime = 0.0;
        double trTs = 0.0;
        int[] turnaround = new int[processList.length];
        int waiting[] = new int[processList.length];
        for (int i = 0; i < processList.length; i++) {
            for (ComputeGanttChart comp: ganttChart) {
                if (comp.getProcess() == processList[i]) {
                    turnaround[i] = comp.getEndTime() - comp.getProcess().getArrivalTime();
                    processList[i].setTurnAroundTime(turnaround[i]);
                }
            }
            waiting[i] = turnaround[i] - processList[i].getBurstTime();
            processList[i].setWaitTime(waiting[i]);
        }
        
        for(int i=0; i < processList.length; i++)
        {
            double trTsIndividual = (double)(turnaround[i]/processList[i].getBurstTime());
            sumtrTS += trTsIndividual;
        }
        
        avgTrTs = (double) (sumtrTS / processList.length);
        double avgWaitTime = 0.0;
        for (int i = 0; i < processList.length; i++) {
            avgTurnAroundTime += turnaround[i];
            avgWaitTime += waiting[i];
        }
        
        //LOGIC TO CALCULATE VARIOUS METRICS
        // TR/TS REMAINING
        avgTurnAroundTime = (double) (avgTurnAroundTime / processList.length);
        avgWaitTime = (double) (avgWaitTime / processList.length);
        JTextField turnAroundLabel = (JTextField) miscellaneousComp[1];
        turnAroundLabel.setText(String.valueOf(avgTurnAroundTime));

        JTextField waitingTime = (JTextField) miscellaneousComp[0];
        waitingTime.setText(String.valueOf(avgWaitTime));
        //System.out.println("Average TurnAround Time / Burst Time: " + avgTrTs);
//        JTextField avgTrByTs = (JTextField) miscellaneousComp[5];
//        avgTrByTs.setText(String.valueOf(avgTrTs));
    }   
}