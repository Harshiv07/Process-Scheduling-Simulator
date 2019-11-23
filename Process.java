package cpuschedulingsimulator;
import java.awt.Color;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Process 
{
    //DECLARING THE ATTRIBUTES OF PROCESSES
    private String processID;
    private int burstTime;
    private int arrivalTime;
    private int currentBurstTime;
    private boolean isCompleted;
    private JLabel remainingBurstTime;
    private JProgressBar count;
    private int pRemainingBurstTime;
    private int waitTime;
    private int turnAroundTime;
    private Color color;

    //CONSTRUCTOR
    public Process(String processID, int burstTime, int arrivalTime, JLabel remainingBurstTime, JProgressBar count) {
		
        this.processID = processID;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.currentBurstTime = 0;
        this.isCompleted = false;
        this.remainingBurstTime = remainingBurstTime;
        this.count = count;
        this.pRemainingBurstTime = 0;
        this.waitTime = 0;
        this.turnAroundTime = 0;
        this.color = randomColorMaker();
    }
    
    //GETTERS AND SETTERS
    public int getID() {
        String getChar = "";
        for (char c: this.processID.toCharArray()) {
            if (Character.isDigit(c)) {
                getChar = getChar + c;
            }
        }
        return Integer.parseInt(getChar); //CONVERTING GUI COMPONENT'S PID(STRING) TO INTEGER
    }
    
    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getCurrentBurstTime() {
        return currentBurstTime;
    }

    public void setCurrentBurstTime(int currentBurstTime) {
        this.currentBurstTime = currentBurstTime;
        checkCompletion();
        updateGUI();
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public int getpRemainingBurstTime() {
        return pRemainingBurstTime;
    }

    public void setpRemainingBurstTime(int pRemainingBurstTime) {
        this.pRemainingBurstTime = pRemainingBurstTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public String getProcessID() {
        return processID;
    }
    
    public void setProcessID(String processID) {
        this.processID = processID;
    }
    
    public JLabel getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public void setRemainingBurstTime(JLabel remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }

    public JProgressBar getCount() {
        return this.count;
    }
    
    public void setCount(JProgressBar count) {
        this.count = count;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public boolean isCompleted() {
        return this.isCompleted;
    }
  
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    //GENERATE RANDOM COLOR FOR EACH PROCESS FOR GANTT CHART
    private static Color randomColorMaker() {
        Random rand = new Random();
        float red = rand.nextFloat();
        float green = rand.nextFloat();
        float blue = rand.nextFloat();
        return new Color(red, green, blue);
    }
   
    //EACH TIME A THREAD OF PROCESS RUNS, IT'S CURRENT BURTST (INITIALLY 0) INCREMENTS TILL IT IS LESS THAN BURST TIME OF PROCESS
    public void run() {
        if (this.currentBurstTime < this.burstTime) {
            this.currentBurstTime++;
        }
        checkCompletion();
        updateGUI();
    }
	
    //AS SOON AS CURRENT BURST TIME OF PROCESS BECOMES GREATER THAN BURST TIME OF PROCESS, IT INDICATES THAT PROCESS IS COMPLETED
    private void checkCompletion() {
        this.isCompleted = (this.currentBurstTime >= this.burstTime);
    }
	
    //UPDATE GUI FOR SIMULATION
    //FOR EACH STEP OF SIMULATION, REMAINING BURST TIME IS UPDATED
    private void updateGUI() {
        this.pRemainingBurstTime = this.burstTime - this.currentBurstTime;
        this.remainingBurstTime.setText(String.valueOf(this.pRemainingBurstTime));
        this.count.setValue(this.currentBurstTime);
    }
}