package cpuschedulingsimulator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RRScheduling extends SchAlgorithm implements Runnable 
{	
    Controller controller;	
    void addToGanttChart() 
    {
        ComputeGanttChart ganttChartComp = new ComputeGanttChart(controller.selectProcess,
        controller.selectProcess.getCurrentBurstTime()- controller.selectProcess.getBurstTime(),(controller.clock));
        controller.ganttChart.add(ganttChartComp);    
    }

    public void run() {
        if (controller.cont) {
        }
        else
            start();
    }
    //RUN THE RR SCHEDULING THREAD UNTIL IT'S CLOCK REACHES TOTAL BURST TIME OF ALL PROCESSES
    void start() {
        while (controller.clock < controller.totalBurstTime) {
            this.clock();
        }
    }
	
    
    public void clock() {
        int totalProcess = controller.processList.length;
        
        if (controller.selectProcess != null && !controller.selectProcess.isCompleted()) 
        {
            //EVERY PROCESS THAT IS NOT COMPLETE YET WILL BE EXECUTED FOR TIME QUANTUM
            for (int i = 0; i < timeQuantum; i++) {
                if (!controller.selectProcess.isCompleted()) {
                    controller.selectProcess.run();
                    controller.update();
                    sleep();
                }
            }
            addToGanttChart();
        }
        //AFTER EVERY TIME QUANTUM SELECT THE NEXT PROCESS AVAILABLE
        controller.selectProcess = controller.processList[(controller.selectProcess.getID()+ 1) % (totalProcess)];
        
    }
    //CUSTOMIZING PARAMETER OF RR
    public int timeQuantum;
    
    //CONSTRUCTOR FOR ROUND ROBIN SCHEDULER
    public RRScheduling(Controller controller, int timeQuantum) {
        this.controller = controller;
        this.timeQuantum = timeQuantum;
    }
    
    void sleep() {
        try {
            Thread.sleep((long) (controller.delay * 1000));
        } catch (InterruptedException ex) {
            Logger.getLogger(FCFSScheduling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}