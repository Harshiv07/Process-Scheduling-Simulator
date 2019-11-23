package cpuschedulingsimulator;

public class ComputeGanttChart 
{	
    private int beginTime;
    private int endTime;	
    private Process process;
    
    //CONSTRUCTOR
    public ComputeGanttChart(Process process, int beginTime, int endTime) {
        this.process = process;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
    
    //GETTERS AND SETTERS
    public int getBeginTime() {
        return beginTime;
    }
    
    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }	
    
    public int getEndTime() {
        return endTime;
    }
    
    public void setEndTIme(int endTime) {
        this.endTime = endTime;
    }
    
    public Process getProcess() {
        return process;
    }	
}