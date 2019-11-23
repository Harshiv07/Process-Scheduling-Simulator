package cpuschedulingsimulator;
import javax.swing.GroupLayout.Alignment;
import javax.swing.*;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectInfo extends JFrame 
{
    private GroupLayout layout = new GroupLayout(getContentPane());
    private JLabel projTitle = new JLabel();
    private JLabel className = new JLabel();
    private JLabel projID = new JLabel();
    private JLabel members = new JLabel();
    private JButton exit = new JButton();	
    // CONSTRUCTOR
    public ProjectInfo() 
    {
        setResizable(false);
	initialize_comp();
	setLocationRelativeTo(null);
    }
    
    private void initialize_comp() 
    {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setTitle("About Scheduling Algorithm Simulator");
	setLabels();
	setButtons();
	setLayout();
	getContentPane().setLayout(layout);
	pack();
    }	
    
    private void setLayout() 
    {
	layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(150).addComponent(projTitle).addGap(150))
            .addGroup(layout.createSequentialGroup().addGap(191).addComponent(className))
            .addGroup(layout.createSequentialGroup().addGap(257).addComponent(projID))
            .addGroup(layout.createSequentialGroup().addGap(174).addComponent(members))
            .addGroup(layout.createSequentialGroup().addGap(276).addComponent(exit)));
            layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(40).addComponent(projTitle).addGap(20)
		.addComponent(className).addGap(16)
		.addComponent(projID).addGap(30)
		.addComponent(members).addGap(20)
		.addComponent(exit).addGap(40)));
    }	
    private void setLabels() 
    {
	projTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
	projTitle.setText("CPU Scheduling Simulator");	
        className.setFont(new Font("SansSerif", Font.PLAIN, 20));
	className.setText("Operating Systems");	
	projID.setFont(new Font("SansSerif", Font.PLAIN, 18));
	projID.setText("Project");
	members.setFont(new Font("SansSerif", Font.BOLD, 20));
	members.setText("by Harshiv Patel & Charmil Gandhi");
    }	
    private void setButtons() 
    {
        exit.setFont(new Font("SansSerif", Font.PLAIN, 18));
	exit.setText("Exit");
	exit.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
                exitActionPerformed(evt);
            }
	});
    }	
    private void exitActionPerformed(ActionEvent evt) 
    {
	this.dispose();
    }	
    private static void setNimbusUI() 
    {
        try 
        {
            for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) 
            {
		if ("Nimbus".equals(info.getName())) 
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
	catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(ProjectInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) 
        {
            Logger.getLogger(ProjectInfo.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch (IllegalAccessException ex) 
        {
            Logger.getLogger(ProjectInfo.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch (UnsupportedLookAndFeelException ex) 
        {
            Logger.getLogger(ProjectInfo.class.getName()).log(Level.SEVERE, null, ex);
	}
    }	
    public static void main(String args[])
    {		
        setNimbusUI();
        new ProjectInfo().setVisible(true);
    }	
}
