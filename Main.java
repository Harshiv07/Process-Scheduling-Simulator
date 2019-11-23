package cpuschedulingsimulator;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Main extends JFrame 
{	
	Thread thread = null;
        private JPanel tabSimulator;
	private JPanel simDisplayPanel;
	private JPanel processInfoTab;
	private JPanel addProcessPanel;
	private JPanel optionsPanel;
	private JPanel simulatorControlPanel;
	private JPanel simulatorSpeedPanel;
	private JPanel simulatorResultsPanel;
	
        private JTabbedPane jTabbedPane;
	private JScrollPane processDisplayScrollPane;
	private JTable processDisplayTable;
	private JComboBox<String> selectScheduler;
	
	private JButton btnStartSimProcess;
	private JButton addProcessBtn;
	private JButton removeSelectedProcessOption;
	private JButton removeAllProcessOption;
	private JButton controlSimulate;
	private JButton controlStep;
	private JButton controlBackBtn;
	private JButton controlResetBtn;
	private JButton simResultsBtn;
	
        private JLabel labelSelectScheduler;
	private JLabel labelAddArrivalTime;
	private JLabel labelAddBurstTime;
	private JLabel simDisplayPanelProcess;
	private JLabel simDisplayPanelProgress;
	private JLabel simDisplayRemainingBT;
	private JLabel simDisplayTotalBT;
	private JLabel simulatorDisplayPanel_label_schedule;
	private JLabel simulatorDisplayPanel_label_scheduleSelected;
	private JLabel simulatorDisplayPanel_label_timeQuantum;
	private JLabel simCPUClock;
	private JLabel simulator_label_CPU_selectedProcess;
	private JLabel simulator_label_CPU_selectedProcess_selected;
	private JLabel simResultsAvgWaitingTime;
	private JLabel simulatorSpeed_label_slow;
	private JLabel simulatorSpeed_label_fast;
        private JLabel simResultsAvgTurnAroundTime;
	
        private JSpinner simDisplayTimeQuantum;
	private JSpinner addProcess_spinner_arrivalTime;
	private JSpinner addProcess_spinner_burstTime;
	
        private JProgressBar cpuPrograssBar;
	private JSlider simulatorSpeedSlider;
	
        private JTextField simulatorResultsLabelAvgWaitingTimeCalc;
	private JTextField simulatorResultsLabelAvgTurnAroundTimeCalc;
	private JTextField simulatorResults_label_avgTrByTs;
                
	private void setAll() 
        {
            setJTabbedPane();	
	}
	
	private void setJTabbedPane() 
        {   
            jTabbedPane = new JTabbedPane();
            jTabbedPane.setFont(new Font("SansSerif", 0, 18));
            jTabbedPane.addChangeListener(new ChangeListener() 
            {
		public void stateChanged(ChangeEvent evt) 
                {
                    onClickTabChange(evt);
                }
            });
            
            setTabProcessInfo();
            setTabSimulator();	
	}
	
	private void setTabProcessInfo() 
        {
            processInfoTab = new JPanel();
            processInfoTab.setBackground(Color.GRAY);
            processInfoTab.setPreferredSize(new Dimension(500, 500));
            setProcessInfoDisplay();
            setProcessInfoAddProcess();
            setProcessInfoOptions();
            setProcessInfoScheduler();
            setProcessInfoRun();
            processInfoTab.getAccessibleContext().setAccessibleName("");
	}
	
	private void setProcessInfoDisplay() 
        {
            processDisplayScrollPane = new JScrollPane();
            processDisplayTable = new JTable();
            processDisplayTable.setBackground(Color.LIGHT_GRAY);
            
            processDisplayTable.setModel(new DefaultTableModel(new Object[][] {
            }, new String[] { "PROCESS ID", "ARRIVAL TIME", "BURST TIME" }));
            
            processDisplayScrollPane.setViewportView(processDisplayTable);
	}
        
	private void setProcessInfoAddProcess() 
        {
            addProcessPanel = new JPanel();
            addProcessPanel.setBackground(Color.LIGHT_GRAY);
            labelAddArrivalTime = new JLabel();
            labelAddBurstTime = new JLabel();
            addProcess_spinner_arrivalTime = new JSpinner();
            addProcess_spinner_arrivalTime.setFont(new Font("SansSerif", 0, 18));	
            addProcess_spinner_burstTime = new JSpinner();
            addProcess_spinner_burstTime.setFont(new Font("SansSerif", 0, 18));	
            addProcessBtn = new JButton();
                
            addProcessPanel.setBorder(BorderFactory.createTitledBorder("Add Process"));
            labelAddArrivalTime.setFont(new Font("SansSerif", 0, 18)); 
            labelAddArrivalTime.setText("Enter Arrival Time :");
            labelAddBurstTime.setFont(new Font("SansSerif", 0, 18)); 
            labelAddBurstTime.setText("Enter Burst Time :");
            addProcessBtn.setFont(new Font("SansSerif", 0, 20));	
            addProcessBtn.setText("Add Process");
            addProcessBtn.addActionListener(new ActionListener() 
            {
		public void actionPerformed(ActionEvent evt) 
                {
                    onClickAddProcess(evt);
                }
            });
            addProcess_spinner_arrivalTime.setModel(new SpinnerNumberModel(0, 0, 500, 1));
            addProcess_spinner_burstTime.setModel(new SpinnerNumberModel(1, 1, 500, 1));
	}
	
	private void setProcessInfoOptions() 
        {
            optionsPanel = new JPanel();
            optionsPanel.setBackground(Color.LIGHT_GRAY);
            removeSelectedProcessOption = new JButton();
            removeAllProcessOption = new JButton();
	
            optionsPanel.setBorder(BorderFactory.createTitledBorder("Remove Options"));
		
            removeSelectedProcessOption.setFont(new Font("SansSerif", 0, 20));	
            removeSelectedProcessOption.setText("Remove Selected Process");
            removeSelectedProcessOption.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent evt) 
                {
                    onClickRemoveSelectedProcess(evt);
                }
            });
		
            removeAllProcessOption.setFont(new Font("SansSerif", 0, 20));	
            removeAllProcessOption.setText("Remove All Processes");
            removeAllProcessOption.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent evt) 
                {
                    onClickRemoveAllProcess(evt);
                }
            });
	}
	
	private void setProcessInfoScheduler() 
        {
            labelSelectScheduler = new JLabel();
            selectScheduler = new JComboBox<>();
            selectScheduler.setFont(new Font("SansSerif", 0, 16));	
		
            labelSelectScheduler.setFont(new Font("SansSerif", 0, 18));	
            labelSelectScheduler.setText("Select Algorithm :");
            labelSelectScheduler.setForeground(Color.WHITE);
		
            selectScheduler.setModel(new DefaultComboBoxModel<>(new String[] { "FCFS", "SJF (Preemptive)", "Round Robin" }));
            selectScheduler.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent evt) 
                {
                    onClickSelectScheduler(evt);
                }
            });
            selectScheduler.addPropertyChangeListener(new PropertyChangeListener() 
            {
                public void propertyChange(PropertyChangeEvent evt) 
                {
                    jComboBox1PropertyChange(evt);
                }
            });	
	}
	
	private void setProcessInfoRun() 
        {
            btnStartSimProcess = new JButton();
            btnStartSimProcess.setFont(new Font("SansSerif", 1, 18));	
            btnStartSimProcess.setIcon(new ImageIcon(Main.class.getResource("/cpuschedulingsimulator/playI.png")));
            btnStartSimProcess.setText("Simulate");
            
            btnStartSimProcess.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent evt)
                {
                    onClickRunSimulationProcessInfo(evt);
                }
            });
	}
	
	private void setTabSimulator() 
        {
            tabSimulator = new JPanel();
            setSimulatorDisplayPanel();
            tabSimulator.setBackground(Color.GRAY);
            cpuPrograssBar = new JProgressBar();
            UIManager.put("cpuPrograssBar.selectForeground",Color.BLUE);
            simCPUClock = new JLabel();
            simCPUClock.setFont(new Font("SansSerif", 0, 18));	
            simCPUClock.setText("CPU Clock");
            simCPUClock.setForeground(Color.WHITE);
            simulator_label_CPU_selectedProcess = new JLabel();
            simulator_label_CPU_selectedProcess.setFont(new Font("SansSerif", 0, 18));	
            simulator_label_CPU_selectedProcess.setText("CPU Selected Process:");
            simulator_label_CPU_selectedProcess.setForeground(Color.WHITE);
            simulator_label_CPU_selectedProcess_selected = new JLabel();
            simulator_label_CPU_selectedProcess_selected.setFont(new Font("SansSerif", 0, 16));	
            setSimulatorSpeed();
            setSimulatorControl();
            setSimulatorResults();
	}
	
	private void setSimulatorDisplayPanel() 
        {
            simDisplayPanel = new JPanel();
            simDisplayPanel.setBackground(Color.LIGHT_GRAY);
            simDisplayPanelProcess = new JLabel();
            simDisplayPanelProgress = new JLabel();
            simDisplayRemainingBT = new JLabel();
            simDisplayTotalBT = new JLabel();
            simulatorDisplayPanel_label_schedule = new JLabel();
            simulatorDisplayPanel_label_scheduleSelected = new JLabel();
            simulatorDisplayPanel_label_timeQuantum = new JLabel();
            simDisplayTimeQuantum = new JSpinner();
    	
            simDisplayPanel.setBorder(BorderFactory.createTitledBorder("Simulator"));
		
            simDisplayPanelProcess.setFont(new Font("SansSerif", 0, 16));	
            simDisplayPanelProcess.setText("Process");
		
            simDisplayPanelProgress.setFont(new Font("SansSerif", 0, 16));	
            simDisplayPanelProgress.setText("Progress");
    	
            simDisplayRemainingBT.setFont(new Font("SansSerif", 0, 16));	
            simDisplayRemainingBT.setText("Remaining Burst Time");
            
            simDisplayTotalBT.setFont(new Font("SansSerif", 0, 16));	
            simDisplayTotalBT.setText("Total Burst Time");
		
            simulatorDisplayPanel_label_schedule.setFont(new Font("SansSerif", 0, 16));	
            simulatorDisplayPanel_label_schedule.setText("Scheduling:");
		
            simulatorDisplayPanel_label_scheduleSelected.setFont(new Font("SansSerif", 0, 16));	
            simulatorDisplayPanel_label_scheduleSelected.setText("0.00");
		
            simulatorDisplayPanel_label_timeQuantum.setFont(new Font("SansSerif", 0, 16));	
            simulatorDisplayPanel_label_timeQuantum.setText("Time Quantum");
	    
            simDisplayTimeQuantum.setModel(new SpinnerNumberModel(1, 1, null, 1));
	}
	
	private void setSimulatorSpeed() {
            simulatorSpeedPanel = new JPanel();
            simulatorSpeedPanel.setBackground(Color.LIGHT_GRAY);
            simulatorSpeedSlider = new JSlider();
            simulatorSpeed_label_slow = new JLabel();
            simulatorSpeed_label_fast = new JLabel();
		
            simulatorSpeedPanel.setBorder(BorderFactory.createTitledBorder("Speed"));
            simulatorSpeedSlider.setValue(40);
            simulatorSpeedSlider.setInverted(true);
            simulatorSpeedPanel.setVisible(false);
            
	}
	
	private void setSimulatorControl() {
            simulatorControlPanel = new JPanel();
            simulatorControlPanel.setBackground(Color.LIGHT_GRAY);
            controlSimulate = new JButton();
            controlStep = new JButton();
            controlBackBtn = new JButton();
            controlResetBtn = new JButton();
		
            simulatorControlPanel.setBorder(BorderFactory.createTitledBorder("Control"));
		
            controlSimulate.setFont(new Font("SansSerif", 0, 16)); 
            controlSimulate.setText("Simulate");
            controlSimulate.setEnabled(false);
            controlSimulate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    onClick_control_Simulate(evt);
                }
            });
		
            controlStep.setFont(new Font("SansSerif", 0, 16)); 
            controlStep.setText("Step");
            controlStep.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    onClick_control_step(evt);
                }
            });
		
            controlBackBtn.setFont(new Font("SansSerif", 0, 16)); 
            controlBackBtn.setText("Go Back To Process Info");
            controlBackBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    onClick_control_goBack(evt);
                }
            });
		
            controlResetBtn.setFont(new Font("SansSerif", 0, 16)); 
            controlResetBtn.setText("Reset");
            controlResetBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    onClickControlReset(evt);
                }
            });
	}
	
	private void setSimulatorResults() {
            simulatorResultsPanel = new JPanel();
            simResultsBtn = new JButton();
            simResultsAvgWaitingTime = new JLabel();
            simResultsAvgTurnAroundTime = new JLabel();
            simulatorResultsLabelAvgWaitingTimeCalc = new JTextField();
            simulatorResultsLabelAvgTurnAroundTimeCalc = new JTextField();
	    simulatorResults_label_avgTrByTs = new JTextField();
            
            simulatorResultsPanel.setBorder(BorderFactory.createTitledBorder("Result"));

            simResultsAvgWaitingTime.setFont(new Font("SansSerif", 0, 16)); 
            simResultsAvgWaitingTime.setText("Average Waiting Time");
		
            simResultsAvgTurnAroundTime.setFont(new Font("SansSerif", 0, 16)); 
            simResultsAvgTurnAroundTime.setText("Average Turn Around Time");
            
            simulatorResultsLabelAvgWaitingTimeCalc.setEditable(false);
            simulatorResultsLabelAvgWaitingTimeCalc.setFont(new Font("SansSerif", 0, 16)); 
            simulatorResultsLabelAvgWaitingTimeCalc.setText("0.00");
		
            simulatorResults_label_avgTrByTs.setEditable(false);
            simulatorResults_label_avgTrByTs.setFont(new Font("SansSerif", 0, 16)); 
            simulatorResults_label_avgTrByTs.setText("0.00");
	
            simulatorResultsLabelAvgTurnAroundTimeCalc.setEditable(false);
            simulatorResultsLabelAvgTurnAroundTimeCalc.setFont(new Font("SansSerif", 0, 16)); 
            simulatorResultsLabelAvgTurnAroundTimeCalc.setText("0.00");
		
            simResultsBtn.setFont(new Font("SansSerif", 0, 18)); 
            simResultsBtn.setText("View Detailed Results");
            simResultsBtn.setEnabled(false);
            simResultsBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    onClickOpenResults(evt);
                }
            });
	}
	
	public Main() {
            setResizable(true);
            initComponents();
            setLocationRelativeTo(null);
            setSecondTabComponents(false);
            simDisplayTimeQuantum.setVisible(false);
            simulatorDisplayPanel_label_timeQuantum.setVisible(false);
            this.setSize(780, 570);
	}
	
	private void initComponents() {
		
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle("Process Scheduling Algorithms Simulator");
		
            // set labels, buttons, tables, etc
            setAll();
		
            // set layout
            getContentPane().setLayout(new AbsoluteLayout());
		
            GroupLayout gl_panel_addProcess = new GroupLayout(addProcessPanel);
            gl_panel_addProcess.setHorizontalGroup(
            gl_panel_addProcess.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_addProcess.createSequentialGroup()
                .addGroup(gl_panel_addProcess.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_addProcess.createSequentialGroup()
                    .addContainerGap()
                .addGroup(gl_panel_addProcess.createParallelGroup(Alignment.LEADING)
                    .addComponent(labelAddArrivalTime)
                    .addComponent(labelAddBurstTime))
                    .addGap(18)
                .addGroup(gl_panel_addProcess.createParallelGroup(Alignment.LEADING, false)
                    .addComponent(addProcess_spinner_arrivalTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(addProcess_spinner_burstTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addGroup(gl_panel_addProcess.createSequentialGroup()
                    .addGap(39)
                    .addComponent(addProcessBtn)))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            gl_panel_addProcess.setVerticalGroup(
                gl_panel_addProcess.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_panel_addProcess.createSequentialGroup()
                        .addContainerGap()
                    .addGroup(gl_panel_addProcess.createParallelGroup(Alignment.BASELINE)
                        .addComponent(labelAddArrivalTime)
                        .addComponent(addProcess_spinner_arrivalTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(gl_panel_addProcess.createParallelGroup(Alignment.BASELINE)
                        .addComponent(labelAddBurstTime)
                        .addComponent(addProcess_spinner_burstTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addComponent(addProcessBtn)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            
            addProcessPanel.setLayout(gl_panel_addProcess);

            GroupLayout gl_panel_options = new GroupLayout(optionsPanel);
            gl_panel_options.setHorizontalGroup(
                gl_panel_options.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_panel_options.createSequentialGroup()
                        .addContainerGap()
                    .addGroup(gl_panel_options.createParallelGroup(Alignment.LEADING, false))
                        .addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                    .addGroup(gl_panel_options.createParallelGroup(Alignment.TRAILING)
                            .addComponent(removeSelectedProcessOption, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(removeAllProcessOption, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(5))
            );
            
            gl_panel_options.setVerticalGroup(
                gl_panel_options.createParallelGroup(Alignment.TRAILING)
                    .addGroup(gl_panel_options.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(gl_panel_options.createParallelGroup(Alignment.TRAILING)
                            .addComponent(removeSelectedProcessOption))
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addGroup(gl_panel_options.createParallelGroup(Alignment.BASELINE)
                            .addComponent(removeAllProcessOption, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap())
            );
           
            optionsPanel.setLayout(gl_panel_options);
		
            GroupLayout gl_tab_processInfo = new GroupLayout(processInfoTab);
            gl_tab_processInfo.setHorizontalGroup(
                gl_tab_processInfo.createParallelGroup(Alignment.TRAILING)
                    .addGroup(gl_tab_processInfo.createSequentialGroup()
                    .addGroup(gl_tab_processInfo.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_tab_processInfo.createSequentialGroup()
                        .addGap(5)
                        .addComponent(addProcessPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(gl_tab_processInfo.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_tab_processInfo.createSequentialGroup()
                        .addGap(18)
                        .addComponent(labelSelectScheduler)
                        .addGap(18)
                        .addComponent(selectScheduler, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
                    .addGroup(gl_tab_processInfo.createSequentialGroup()
                        .addGap(12)
                        .addComponent(optionsPanel, GroupLayout.PREFERRED_SIZE, 447, Short.MAX_VALUE))))
                        .addComponent(processDisplayScrollPane, GroupLayout.PREFERRED_SIZE, 725, GroupLayout.PREFERRED_SIZE)
                    .addGroup(gl_tab_processInfo.createSequentialGroup()
                        .addGap(236)
                        .addComponent(btnStartSimProcess, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)))
                        .addGap(81))
            );
            
            gl_tab_processInfo.setVerticalGroup(
                gl_tab_processInfo.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_tab_processInfo.createSequentialGroup()
                        .addGap(13)
                        .addComponent(processDisplayScrollPane, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                        .addGap(18)
                    .addGroup(gl_tab_processInfo.createParallelGroup(Alignment.LEADING)
                        .addComponent(addProcessPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(gl_tab_processInfo.createSequentialGroup()
                        .addComponent(optionsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(4)
                    .addGroup(gl_tab_processInfo.createParallelGroup(Alignment.BASELINE)
                        .addComponent(labelSelectScheduler)
                        .addComponent(selectScheduler, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                        .addGap(40)
                        .addComponent(btnStartSimProcess, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                        .addGap(283))
            );
            
            processInfoTab.setLayout(gl_tab_processInfo);
		
            GroupLayout gl_simulatorDisplayPanel = new GroupLayout(simDisplayPanel);
            gl_simulatorDisplayPanel.setHorizontalGroup(
            gl_simulatorDisplayPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_simulatorDisplayPanel.createSequentialGroup()
                    .addGap(31)
                    .addComponent(simDisplayPanelProcess)
                    .addGap(94)
                    .addComponent(simDisplayPanelProgress)
                    .addGap(48)
                .addGroup(gl_simulatorDisplayPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_simulatorDisplayPanel.createSequentialGroup()
                    .addPreferredGap(ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
                .addGroup(gl_simulatorDisplayPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_simulatorDisplayPanel.createSequentialGroup()
                    .addComponent(simulatorDisplayPanel_label_timeQuantum)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(simDisplayTimeQuantum, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
                .addGroup(gl_simulatorDisplayPanel.createSequentialGroup()
                    .addComponent(simulatorDisplayPanel_label_schedule)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(simulatorDisplayPanel_label_scheduleSelected))))
                .addGroup(gl_simulatorDisplayPanel.createSequentialGroup()
                    .addGap(66)
                    .addComponent(simDisplayRemainingBT)
                    .addGap(97)
                    .addComponent(simDisplayTotalBT)
                    .addContainerGap(131, Short.MAX_VALUE))))
		);
            
            gl_simulatorDisplayPanel.setVerticalGroup(
                gl_simulatorDisplayPanel.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_simulatorDisplayPanel.createSequentialGroup()
                        .addContainerGap()
                    .addGroup(gl_simulatorDisplayPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(simDisplayPanelProcess)
                        .addComponent(simDisplayRemainingBT)
                        .addComponent(simDisplayTotalBT)
                        .addComponent(simDisplayPanelProgress))
                        .addPreferredGap(ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
                    .addGroup(gl_simulatorDisplayPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(simulatorDisplayPanel_label_schedule)
                        .addComponent(simulatorDisplayPanel_label_scheduleSelected))
                        .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_simulatorDisplayPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(simulatorDisplayPanel_label_timeQuantum)
                        .addComponent(simDisplayTimeQuantum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(3))
            );
	
            simDisplayPanel.setLayout(gl_simulatorDisplayPanel);
		
            GroupLayout gl_simulatorControlPanel = new GroupLayout(simulatorControlPanel);
                gl_simulatorControlPanel.setHorizontalGroup(
                    gl_simulatorControlPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_simulatorControlPanel.createSequentialGroup()
                            .addGap(25)
                            .addComponent(controlSimulate)
                            .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(gl_simulatorControlPanel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_simulatorControlPanel.createSequentialGroup()
                            .addComponent(controlStep, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(controlBackBtn))
                        .addGroup(gl_simulatorControlPanel.createSequentialGroup()
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(controlResetBtn, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)))
					.addGap(19))
		);
            gl_simulatorControlPanel.setVerticalGroup(
                gl_simulatorControlPanel.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_simulatorControlPanel.createSequentialGroup()
                    .addGroup(gl_simulatorControlPanel.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(controlSimulate, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
                    .addGroup(gl_simulatorControlPanel.createSequentialGroup()
                    .addGroup(gl_simulatorControlPanel.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(controlResetBtn))
                        .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(gl_simulatorControlPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(controlStep)
                        .addComponent(controlBackBtn))))
                        .addContainerGap(23, Short.MAX_VALUE))
            );
            
            simulatorControlPanel.setLayout(gl_simulatorControlPanel);	
            GroupLayout gl_simulatorSpeedPanel = new GroupLayout(simulatorSpeedPanel);
            gl_simulatorSpeedPanel.setHorizontalGroup(
                gl_simulatorSpeedPanel.createParallelGroup(Alignment.TRAILING)
                    .addGroup(gl_simulatorSpeedPanel.createSequentialGroup()
                        .addContainerGap()
                    .addGroup(gl_simulatorSpeedPanel.createParallelGroup(Alignment.TRAILING)
                        .addComponent(simulatorSpeedSlider, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addGroup(gl_simulatorSpeedPanel.createSequentialGroup()
                        .addComponent(simulatorSpeed_label_slow)
                        .addPreferredGap(ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                        .addComponent(simulatorSpeed_label_fast)))
                        .addGap(21))
            );
           
            gl_simulatorSpeedPanel.setVerticalGroup(
                gl_simulatorSpeedPanel.createParallelGroup(Alignment.TRAILING)
                    .addGroup(gl_simulatorSpeedPanel.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(simulatorSpeedSlider, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_simulatorSpeedPanel.createParallelGroup(Alignment.TRAILING)
                        .addComponent(simulatorSpeed_label_fast)
                        .addComponent(simulatorSpeed_label_slow))
                        .addContainerGap())
            );
            
            simulatorSpeedPanel.setLayout(gl_simulatorSpeedPanel);
		
            GroupLayout gl_simulatorResultsPanel = new GroupLayout(simulatorResultsPanel);
                gl_simulatorResultsPanel.setHorizontalGroup(
                    gl_simulatorResultsPanel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_simulatorResultsPanel.createSequentialGroup()
                            .addGap(23)
                        .addGroup(gl_simulatorResultsPanel.createParallelGroup(Alignment.TRAILING)
                            .addComponent(simResultsBtn, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 326, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_simulatorResultsPanel.createSequentialGroup()
                        .addGroup(gl_simulatorResultsPanel.createParallelGroup(Alignment.LEADING)
                            .addComponent(simResultsAvgTurnAroundTime)
                            .addComponent(simResultsAvgWaitingTime))
//                            .addComponent(simulatorResults_label_avgTrByTs)
                            .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(gl_simulatorResultsPanel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_simulatorResultsPanel.createSequentialGroup()
                            .addGap(74)
                            .addComponent(simulatorResultsLabelAvgWaitingTimeCalc, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                            .addComponent(simulatorResultsLabelAvgTurnAroundTimeCalc, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))))
//                            .addComponent(simulatorResults_label_avgTrByTs, GroupLayout.DEFAULT_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                .addGap(30))
            );
	
            gl_simulatorResultsPanel.setVerticalGroup(
                gl_simulatorResultsPanel.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_simulatorResultsPanel.createSequentialGroup()
                        .addContainerGap()
                    .addGroup(gl_simulatorResultsPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(simulatorResultsLabelAvgWaitingTimeCalc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(simResultsAvgWaitingTime))
                        .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_simulatorResultsPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(simulatorResultsLabelAvgTurnAroundTimeCalc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(simResultsAvgTurnAroundTime))
                        .addPreferredGap(ComponentPlacement.RELATED)
//                    .addGroup(gl_simulatorResultsPanel.createParallelGroup(Alignment.BASELINE)
//                        .addComponent(simulatorResults_label_avgTrByTs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//                        .addComponent(simulatorResults_label_avgTrByTs)
                            .addComponent(simResultsBtn)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
	
            simulatorResultsPanel.setLayout(gl_simulatorResultsPanel);
		
            GroupLayout gl_tab_simulator = new GroupLayout(tabSimulator);
            gl_tab_simulator.setHorizontalGroup(
            gl_tab_simulator.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_tab_simulator.createSequentialGroup()
                    .addContainerGap()
                .addGroup(gl_tab_simulator.createParallelGroup(Alignment.TRAILING)
                    .addComponent(simDisplayPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                .addGroup(gl_tab_simulator.createSequentialGroup()
                .addGroup(gl_tab_simulator.createParallelGroup(Alignment.LEADING)
                    .addComponent(simulatorSpeedPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(gl_tab_simulator.createSequentialGroup()
                    .addComponent(simulator_label_CPU_selectedProcess)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(simulator_label_CPU_selectedProcess_selected))
                    .addComponent(cpuPrograssBar, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(simCPUClock))
                    .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(gl_tab_simulator.createParallelGroup(Alignment.LEADING)
                    .addComponent(simulatorResultsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(simulatorControlPanel, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))))
                    .addGap(82))
            );
            
            gl_tab_simulator.setVerticalGroup(
                gl_tab_simulator.createParallelGroup(Alignment.LEADING)
                    .addGroup(gl_tab_simulator.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(simDisplayPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_tab_simulator.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(gl_tab_simulator.createSequentialGroup()
                        .addGap(9)
                        .addComponent(simCPUClock)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(cpuPrograssBar, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_tab_simulator.createParallelGroup(Alignment.BASELINE)
                        .addComponent(simulator_label_CPU_selectedProcess)
                        .addComponent(simulator_label_CPU_selectedProcess_selected))
                        .addGap(26)
                        .addComponent(simulatorSpeedPanel, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
                    .addGroup(gl_tab_simulator.createSequentialGroup()
                        .addComponent(simulatorControlPanel, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(simulatorResultsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
            );
            
            tabSimulator.setLayout(gl_tab_simulator);
            jTabbedPane.addTab("Process Information", processInfoTab);
            jTabbedPane.addTab("Simulator", tabSimulator);
		
            getContentPane().add(jTabbedPane, new AbsoluteConstraints(12, 0, 810, 731));
            pack();
	}
	
	JLabel labelForProcess[];
	JProgressBar progress[];
	JLabel remainBt[];
	JLabel lblBt[];
	Process processList[];
	
	void setEnableTabComponents(boolean enabled, JPanel jPanel) {
            for (Component component: jPanel.getComponents()) {
                    component.setEnabled(enabled);
            }
	}
	
	void setFirstTabComponents(boolean enabled) {
            setEnableTabComponents(enabled, processInfoTab);
            setEnableTabComponents(enabled, addProcessPanel);
            setEnableTabComponents(enabled, optionsPanel);
            processDisplayTable.setEnabled(enabled);
	}   
	
	void setSecondTabComponents(boolean enabled) {
            setEnableTabComponents(enabled, tabSimulator);
            setEnableTabComponents(enabled, simulatorControlPanel);
            setEnableTabComponents(enabled, simulatorSpeedPanel);
    	}
	
	Controller controller;
	private void onClick_simulatorSpeedSlider(ChangeEvent evt) {
            if (controller != null) {
                controller.setDelay(simulatorSpeedSlider.getValue());
            }
        }
	
	private void onClick_control_goBack(ActionEvent evt) {
            setFirstTabComponents(true);
            setSecondTabComponents(false);
            for (Component c: labelForProcess) {
                panelProcess.remove(c);
            }
           
            for (Component c: progress) {
                panelProcess.remove(c);
            }
            for (Component c: lblBt) {
                panelProcess.remove(c);
            }
            for (Component c: remainBt) {
                panelProcess.remove(c);
            }   
            scrollPane.remove(panelProcess);
            panelProcess.repaint();
            panelProcess.revalidate();
            scrollPane.repaint();
            scrollPane.revalidate();
            simDisplayPanel.remove(scrollPane);
            simDisplayPanel.repaint();
            simDisplayPanel.revalidate();
            jTabbedPane.setSelectedIndex(0);
	}
	
	void initSimulator() {
            Component miscellaneousComp[] = new Component[6];
            miscellaneousComp[0] = simulatorResultsLabelAvgWaitingTimeCalc;
            miscellaneousComp[1] = simulatorResultsLabelAvgTurnAroundTimeCalc;
            miscellaneousComp[2] = simulator_label_CPU_selectedProcess_selected;
            miscellaneousComp[3] = simResultsBtn;
            miscellaneousComp[4] = simDisplayTimeQuantum;
//            miscellaneousComp[5] = simulatorResults_label_avgTrByTs;
            simDisplayTimeQuantum.setEnabled(false);
		
            controller = new Controller(processList, cpuPrograssBar, simulatorSpeedSlider.getValue(), panelProcess,
            miscellaneousComp, simulatorControlPanel);
    	
            switch (selectScheduler.getSelectedIndex()) {
                case 0:
                    FCFSScheduling fcfs = new FCFSScheduling(controller);
                    controller.setAlgorithm(fcfs);
                    thread = new Thread(fcfs);
                    break;
                case 1:
                    SJFScheduling sjf = new SJFScheduling(controller);
                    controller.setAlgorithm(sjf);
                    thread = new Thread(sjf);
                    break;
                case 2:
                    RRScheduling rr = new RRScheduling(controller,
                    Integer.parseInt(simDisplayTimeQuantum.getValue().toString()));
                    controller.setAlgorithm(rr);
                    thread = new Thread(rr);
                    break;
            }
            resetAction();
            thread.start();
	}
	
	private void onClick_control_Simulate(ActionEvent evt) {
            initSimulator();
            controller.setCont(false);
            controlSimulate.setEnabled(false);
            controlStep.setEnabled(false);
            controlBackBtn.setEnabled(false);
            controlResetBtn.setEnabled(false);
            	}
	
	private void resetAction() {
            for (int i = 0; i < processList.length; i++) {
                processList[i].setCurrentBurstTime(0);
                processList[i].setCompleted(false);
            }
            
            cpuPrograssBar.setValue(0);
            simulatorResultsLabelAvgTurnAroundTimeCalc.setText("0.00");
            simulatorResultsLabelAvgWaitingTimeCalc.setText("0.00");
//            simulatorResults_label_avgTrByTs.setText("0.00");
            controlSimulate.setEnabled(true);
            controlStep.setEnabled(true);
            controlBackBtn.setEnabled(true);
            controller.setClock(0);
            controller.setFinishedSimulation(false);
            simResultsBtn.setEnabled(false);
	}   
	
	private void onClick_control_step(ActionEvent evt) {
            if (controller == null)
                initSimulator();
            else if (controller.isFinishedSimulation() && controlSimulate.isEnabled()) //jButton3 for verification bc that boolean was originally meant for use some other purpose
                initSimulator();
		
            controller.setCont(true);
            controller.algo.clock();
            if (!controller.isFinishedSimulation()) {
                controlSimulate.setEnabled(false);
                controlBackBtn.setEnabled(false);
                simDisplayTimeQuantum.setEnabled(false);
            }
            else {
                controlStep.setEnabled(true);
                controlResetBtn.setEnabled(true);
                simDisplayTimeQuantum.setEnabled(true);
            }
	}
	
	private void onClickControlReset(ActionEvent evt) {
            resetAction();
	}
	
	private void onClickOpenResults(ActionEvent evt) {
            new ShowResults(controller, processList).setVisible(true);

	}
	
	private void jComboBox1PropertyChange(PropertyChangeEvent evt) {
	}
	
	private void onClickSelectScheduler(ActionEvent evt) {
	}
	
	JPanel panelProcess;
	JScrollPane scrollPane;
	
	private void onClickRunSimulationProcessInfo(ActionEvent evt) {
            if (processDisplayTable.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "There are no process in the list.",
                "NO PROCESSES..!!!", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                setFirstTabComponents(false);
                controlSimulate.setEnabled(true);
                setSecondTabComponents(true);
                simDisplayPanel.repaint();
                simDisplayPanel.revalidate();
                panelProcess = new JPanel();
                GridLayout glayout = new GridLayout(processDisplayTable.getRowCount(), 3);
                glayout.setHgap(30);
                panelProcess.setLayout(glayout);
                panelProcess.setLocation(20, 56);
	    	
                int pCount = processDisplayTable.getModel().getRowCount();
                labelForProcess = new JLabel[pCount];
                progress = new JProgressBar[pCount];
                remainBt = new JLabel[pCount];
                lblBt = new JLabel[pCount];
                processList = new Process[pCount];
                final int DIF_COMP = 50;
                for (int i = 0; i < pCount; i++) {
                    int bt = Integer.parseInt(processDisplayTable.getModel().getValueAt(i, 2).toString());
                    int at = Integer.parseInt(processDisplayTable.getModel().getValueAt(i, 1).toString());
                    String name = processDisplayTable.getModel().getValueAt(i, 0).toString();
                    labelForProcess[i] = new JLabel(name);
                    labelForProcess[i].setLocation(simDisplayPanelProcess.getX(),
                    simDisplayPanelProcess.getY() + ((i + 1) * DIF_COMP));

                    panelProcess.add(labelForProcess[i]);   

                    progress[i] = new JProgressBar();
                    progress[i].setForeground(Color.BLUE);
                    progress[i].setBackground(Color.BLUE);
                    progress[i].setLocation(simDisplayPanelProgress.getX(), simDisplayPanelProgress.getY() + ((i + 1) * DIF_COMP));
                    progress[i].setSize(20, 30);
				
                    panelProcess.add(progress[i]);
				
                    remainBt[i] = new JLabel(String.valueOf(bt));
                    remainBt[i].setLocation(simDisplayRemainingBT.getX(), simDisplayRemainingBT.getY() + ((i + 1) * DIF_COMP));
                    panelProcess.add(remainBt[i]);
				
                    lblBt[i] = new JLabel(String.valueOf(bt));
                    lblBt[i].setLocation(simDisplayTotalBT.getX(), simDisplayTotalBT.getY() + ((i + 1) * DIF_COMP));
                    panelProcess.add(lblBt[i]);
                    progress[i].setMaximum(Integer.parseInt(lblBt[i].getText()));

                    processList[i] = new Process(name, bt, at, remainBt[i], progress[i]);
                }
			
                if (selectScheduler.getSelectedIndex() == 2) {
                    simDisplayTimeQuantum.setVisible(true);
                    simulatorDisplayPanel_label_timeQuantum.setVisible(true);
                }
			
                scrollPane = new JScrollPane(panelProcess);
                scrollPane.setLocation(20, 56);
                scrollPane.setSize(700, 250);
                simDisplayPanel.add(scrollPane);
                scrollPane.repaint();
                scrollPane.revalidate();
                simDisplayPanel.repaint();
                simDisplayPanel.revalidate();

                simulatorDisplayPanel_label_scheduleSelected.setText(selectScheduler.getSelectedItem().toString());
                jTabbedPane.setSelectedIndex(1);
            }
	}
	
	private void onClickAddProcess(ActionEvent evt) {
            DefaultTableModel tableModel = (DefaultTableModel) processDisplayTable.getModel();
            String processNumber = "P"
                + String.valueOf(tableModel.getRowCount()).replaceFirst("^0+(?!$)", "");
            tableModel.addRow(new String[] {processNumber, addProcess_spinner_arrivalTime.getValue().toString(),
            addProcess_spinner_burstTime.getValue().toString() });
	}
	
	private void onClickTabChange(ChangeEvent evt) {
            if (jTabbedPane.getSelectedIndex() == 0) {
                this.setSize(780, 570);
            } else {
                this.setSize(780, 780);
            }
           
            this.repaint();
            this.revalidate();
	}
	
	private void onClickRemoveSelectedProcess(ActionEvent evt) {
            DefaultTableModel tableModel = (DefaultTableModel) processDisplayTable.getModel();
            tableModel.removeRow(processDisplayTable.getSelectedRow());
	}
	
	private void onClickRemoveAllProcess(ActionEvent evt) {
            DefaultTableModel tableModel = (DefaultTableModel) processDisplayTable.getModel();
            tableModel.setRowCount(0);
	}
	private static void setNimbusUI() {
            try {
                for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) 
                    {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
             }
            catch (ClassNotFoundException ex) {
                Logger.getLogger(ProjectInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InstantiationException ex) {
                Logger.getLogger(ProjectInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalAccessException ex) {
                Logger.getLogger(ProjectInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(ProjectInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	
	public static void main(String args[]) 
        {
            setNimbusUI();
            new Main().setVisible(true);	
        }
}