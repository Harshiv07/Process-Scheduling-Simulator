package cpuschedulingsimulator;
import java.awt.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
public class ShowResults extends javax.swing.JFrame 
{	
    Process processList[];
    Controller controller;
    Color[] processColors;
    public ShowResults(Controller controller, Process[] processList) 
    {
        initializeComp();
        this.processList = processList;
        this.controller = controller;
        processColors = new Color[processList.length];
        this.setSize(200 + (50 * controller.ganttChart.size()), 650);
        DefaultTableModel model = (DefaultTableModel) processTable.getModel();
        for (Process process: processList) 
        {
            model.addRow(new Object[] { process.getID(), process.getWaitTime(),
            process.getTurnAroundTime()});
        }
        setLocationRelativeTo(null);
    }
    public void paint(Graphics graph) 
    {
        super.paint(graph);
        graph.drawString("0", 53, 126);
        for (int i = 0; i < controller.ganttChart.size(); i++) 
        {
            graph.drawRect(50 + (50 * i), 130, 50, 50);
            graph.setColor(controller.ganttChart.get(i).getProcess().getColor());
            graph.fillRect(50 + (50 * i), 130, 50, 50);
            graph.setColor((Color.black));
            graph.drawString(controller.ganttChart.get(i).getProcess().getProcessID(), 65 + (50 * i), 150);
            graph.drawString(String.valueOf(controller.ganttChart.get(i).getEndTime()), 90 + (50 * i), 126);
        }
    }

    @SuppressWarnings("serial")
    private void initializeComp() {
        ganttChartPanel = new JPanel();
        processDetailsPanel = new JPanel();
        processScrollPane = new JScrollPane();
        processTable = new JTable();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Results");

        ganttChartPanel.setBorder(BorderFactory.createTitledBorder("Gantt Chart"));

        GroupLayout gl_panel_GanttChart = new GroupLayout(ganttChartPanel);
        ganttChartPanel.setLayout(gl_panel_GanttChart);

        gl_panel_GanttChart.setHorizontalGroup(
        gl_panel_GanttChart.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 646, Short.MAX_VALUE));
        gl_panel_GanttChart.setVerticalGroup(
        gl_panel_GanttChart.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 248, Short.MAX_VALUE));

        processDetailsPanel.setBorder(BorderFactory.createTitledBorder("Process Details"));

        processTable.setModel(new DefaultTableModel(new Object[][] {
        }, new String[] { "Process Name", "Waiting Time", "Turn Around Time" }) {

            boolean[] canEdit = new boolean[] { false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        processScrollPane.setViewportView(processTable); 	
        GroupLayout gl_panel_ProcessDetails = new GroupLayout(processDetailsPanel);
        processDetailsPanel.setLayout(gl_panel_ProcessDetails);
        gl_panel_ProcessDetails.setHorizontalGroup(gl_panel_ProcessDetails
            .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, gl_panel_ProcessDetails
                    .createSequentialGroup().addContainerGap().addComponent(processScrollPane,
                        GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                        .addContainerGap()));

        gl_panel_ProcessDetails.setVerticalGroup(gl_panel_ProcessDetails
            .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING,
                    gl_panel_ProcessDetails.createSequentialGroup().addContainerGap(20, Short.MAX_VALUE)
                        .addComponent(processScrollPane, GroupLayout.PREFERRED_SIZE,
                            210, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGroup(layout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(ganttChartPanel, GroupLayout.DEFAULT_SIZE,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(
                processDetailsPanel, GroupLayout.DEFAULT_SIZE,
                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addContainerGap()));
            layout.setVerticalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                    .addComponent(ganttChartPanel, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(processDetailsPanel, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));	
        pack();
    }

    private static void setNimbusUI() {
        try {
            for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
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
    }
    private JPanel ganttChartPanel;
    private JPanel processDetailsPanel;
    private JScrollPane processScrollPane;
    private JTable processTable;
}