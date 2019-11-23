package cpuschedulingsimulator;
import java.util.logging.Level;
import java.util.logging.Logger;
public class SJFScheduling extends SchAlgorithm implements Runnable 
{
    Controller controller;
    boolean isGanttChartDone = false;	
    void addtoGanttChart() {
        ComputeGanttChart ganttChartComp = new ComputeGanttChart(controller.selectProcess,
        controller.selectProcess.getCurrentBurstTime()- controller.selectProcess.getBurstTime(),(controller.clock));
        controller.ganttChart.add(ganttChartComp);
    }
   
    public void run() {
        if (controller.cont) {
        }
        else {
            start();
        }	
    }
   
    //START THE THREAD TILL CLOCK IS LESS THAN THE TOTAL BURST TIME
    void start() {
        while (controller.clock <= controller.totalBurstTime) {
            this.clock();
        }
    }

    public void clock() {
        boolean gChart = false;
        int totalProcess = controller.processList.length;
        
        int lastShortestJob = controller.processList[0].getpRemainingBurstTime();
        
        for (Process process: controller.processList) {
            if (!process.isCompleted())
                lastShortestJob = process.getpRemainingBurstTime();
        }
		
        Process currentSelected = controller.selectProcess;
        //SELECT THE PROCESS WITH SHORTEST BURST TIME REMAINING OUT OF THE PROCESSES THAT ARE NOT COMPLETE
        
        for (int i = 0; i < totalProcess; i++) {
            if (!controller.processList[i].isCompleted()&& controller.processList[i].getArrivalTime()<= controller.clock
                && (controller.processList[i].getpRemainingBurstTime()<= lastShortestJob)) {
                currentSelected = controller.processList[i];
                lastShortestJob = controller.processList[i].getpRemainingBurstTime();
            }
        }
        if (!currentSelected.equals(controller.selectProcess)) {
            addtoGanttChart();
            gChart = true;
        }
        
        controller.selectProcess = currentSelected;
        if (controller.selectProcess == null) {
        }
        else {
            controller.selectProcess.run();
        }
		
        controller.update();
        System.out.println(controller.clock);
    	
        if (controller.clock >= controller.totalBurstTime && !gChart && !isGanttChartDone) {
            addtoGanttChart();
            isGanttChartDone = true;
        }
		
        sleep();
    }

    //CONSTRUCTOR
    public SJFScheduling(Controller controller) {
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