package net.codejava.swing.jpanel;
package code.Model;
package code.Controller;
 
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
 
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
 
// allows a user to update their email or password
public class SettingsPanel extends JFrame {
  
    private JLabel fullName = new JLabel(this.getFirstName() + this.getLastName());
    private JLabel userName = new JLabel(this.getUserName());
    private JLabel currEmail = new JLabel(this.getEmail());
    private JLabel updateEmail = new JLabel("Change email: ");
    private JLabel updatePassword = new JLabel("Change password: ");
    private JTextField textEmail = new JTextField(20);
    private JPasswordField fieldPassword = new JPasswordField(20);
    private JButton buttonSaveChanges = new JButton("Save Changes");
     
    public SettingsPanel() {
        super("Settings");
         
        // create a new panel with GridBagLayout manager
        JPanel newPanel = new JPanel(new GridBagLayout());
         
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
         
        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        newPanel.add(updateEmail, constraints);
 
        constraints.gridx = 1;
        newPanel.add(textEmail, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(updatePassword, constraints);
         
        constraints.gridx = 1;
        newPanel.add(fieldPassword, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        newPanel.add(buttonSaveChanges, constraints);
         
        // set border for the panel
        newPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Settings"));
         
        // add the panel to this frame
        add(newPanel);
         
        pack();
        setLocationRelativeTo(null);
    }
    
    
    public void actionPerformed(event e) {
      if (e.getSource() == this.buttonSaveChanges) {
        // TODO
      }
    }
     
    public static void main(String[] args) {
        // set look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SettingsPanel().setVisible(true);
            }
        });
    }
}
