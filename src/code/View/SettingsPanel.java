package code.View;

import code.Model.DBUtils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class SettingsPanel extends ContentPanel implements ActionListener {
  
    private JLabel fullName = new JLabel(this.mainFrame.user.getFullName());
    private JLabel userName = new JLabel(this.mainFrame.user.getUserName());
    private JLabel currEmail = new JLabel(this.mainFrame.user.getEmail());
    private JLabel updateEmail = new JLabel("Change email: ");
    private JLabel updatePassword = new JLabel("Change password: ");
    private JTextField textEmail = new JTextField(20);
    private JPasswordField fieldPassword = new JPasswordField(20);
    private JButton buttonSaveChanges = new JButton("Save Changes");
     
    public SettingsPanel(MainFrame mf) {
        super(mf);
        //super("Settings");
         
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
         
//        pack();
//        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.buttonSaveChanges) {
            this.mainFrame.model.update_user(this.mainFrame.user, this.textEmail.getText(), this.fieldPassword.getText());
        }
    }
}
