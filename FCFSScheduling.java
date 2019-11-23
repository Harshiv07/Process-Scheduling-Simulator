package cpuschedulingsimulator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FCFSScheduling extends SchAlgorithm implements Runnable 
{	
    Controller controller;
    boolean isGanttChartDone = false;	
    void addtoGanttChart() {
        ComputeGanttChart ganttChartComp = new ComputeGanttChart(controller.selectProcess,
        controller.selectProcess.getCurrentBurstTime()- controller.selectProcess.getBurstTime(),(controller.clock));
        controller.ganttChart.add(ganttChartComp);
    }
   
    //START THREAD
    public void run() {
        if (controller.cont) {
        } 
        else {
            start();
        }	
    }
   
    //RUN THE CPU CLOCK TILL THE SUM OF BURST TIME OF ALL PROCESS
    void start() 
    {
        while (controller.clock <= controller.totalBurstTime) {
            this.clock();
        }
    }
	
    public void clock() {
        boolean gChart = false;
        int totalProcess = controller.processList.length;
        //FCFS LOGIC FOR SELECTING THE PROCESS
        //UNTIL THE SELECTED PROCESS IS NOT COMPLETED, CONTROLLER WILL KEEP RUNNING IT
        for (int i = 0; i < totalProcess; i++) {
            if (!controller.processList[i].isCompleted() && controller.processList[i].getArrivalTime()<= controller.clock) {
                if (!controller.processList[i].equals(controller.selectProcess)) {
                    addtoGanttChart();  //ADDING TO GANTT CHART AS SELECTED PROCESS IS COMPLETED
                    gChart = true;
                }
                controller.selectProcess = controller.processList[i];
                break;
            }
        }
        if (controller.selectProcess != null) {
            controller.selectProcess.run();
        }
		
        controller.update();
        System.out.println(controller.clock);
		
        if (controller.clock >= controller.totalBurstTime && !gChart && !isGanttChartDone) {
            addtoGanttChart();
            isGanttChartDone = true;
        }
        //AFTER COMPLETION OF SIMULATION, WE MAKE THE THREAD SLEEP
        sleep();
    }

    //CONSTRUCTOR
    FCFSScheduling(Controller controller) {
        this.controller = controller;
    }

    private void sleep() {
        try {
            Thread.sleep((long) (controller.delay * 1000));
        }
        catch (InterruptedException ex) {
            Logger.getLogger(FCFSScheduling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}