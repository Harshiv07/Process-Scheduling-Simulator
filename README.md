------------------------------------------------------------------------------------------------------
Group 17
Harshiv Patel  : 1741005
Charmil Gandhi : 1741059

Project Title : Scheduling Algorithm Simulator
School of Engineering and Applied Science
5th Semester
------------------------------------------------------------------------------------------------------

Requirements : 
-> Netbeans IDE 8.2 or above
-> JDK 12.0.2
-> JRE 8

In Netbeans, you need to install certain libraries :
-> JAVA swing library
-> JAVA FX
-> Import Absolute Layout Library : You need to click on libraries in project panel of netbeans and then add library. Select Absolute Layout Library

Algorithms Implemented : 
-> First Come First Serve Algorithm (FCFS)
-> Round Robin Algorithm (RR)
-> Shortest Job First Algorithm (SJF)

Input Parameters : Arrival Time and Burst Time
Output Parameters : Average Turn Around Time, Average Waiting Time, Turn Around Time of Each Process and Waiting Time of each process  

Note : In some cases, we get waiting time as -1 which indicates that this is not the idle case and represents that the CPU remained unutilized in that period.

Limitations : 
-> We need to take the arrival time of the first process 0, else it doesnot result to successful simulation.
