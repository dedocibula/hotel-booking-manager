/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.hotelbookingmanagerdesktop;

import com.sun.jersey.api.client.ClientHandlerException;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ContactTO;
import cz.fi.muni.pa165.hotelbookingmanagerdesktop.rest.ClientRESTManager;
import cz.fi.muni.pa165.hotelbookingmanagerdesktop.tablemodels.ClientTableModel;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Inky Ashizuki
 */
public class ClientDialogue extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;
    private boolean createClient = true;
    private ClientRESTManager clientRESTManager = new ClientRESTManager();
    private ClientTableModel clientTableModel;
    private ClientTO client;

    /**
     * Creates new form clientDialogue
     */
    public ClientDialogue(ClientTableModel clientTableModel) {
        initComponents();
        initializeCountryPicker();
        this.clientTableModel = clientTableModel;

        client = new ClientTO();
    }

    public ClientDialogue(ClientTO client, ClientTableModel clientTableModel) {
        initComponents();
        initializeCountryPicker();
        this.clientTableModel = clientTableModel;
        this.client = client;


        firstNameField.setText(client.getFirstName());
        lastNameField.setText(client.getLastName());
        addressField.setText(client.getContact().getAddress());
        cityField.setText(client.getContact().getCity());
        countryComboBox.setSelectedItem(client.getContact().getCountry());
        telephoneField.setText(client.getContact().getPhone());
        emailField.setText(client.getContact().getEmail());
        okButton.setText("Save");
        clientDialogueDescriptionLabel.setText("Edit client information");

        createClient = false;
    }

    private void initializeCountryPicker() {
        Set<String> countries = new TreeSet<>();

        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            String countryName = locale.getDisplayCountry();
            if (!countryName.isEmpty()) {
                countries.add(countryName);
            }
        }
        for (String s : countries) {
            countryComboBox.addItem(s);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        clientDialogueDescriptionLabel = new javax.swing.JLabel();
        firstNameLabel = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        contantInformationLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        cityLabel = new javax.swing.JLabel();
        countryLabel = new javax.swing.JLabel();
        telephoneLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        firstNameField = new javax.swing.JTextField();
        lastNameField = new javax.swing.JTextField();
        emailField = new javax.swing.JTextField();
        addressField = new javax.swing.JTextField();
        cityField = new javax.swing.JTextField();
        telephoneField = new javax.swing.JTextField();
        countryComboBox = new javax.swing.JComboBox();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Client Dialogue");
        setAlwaysOnTop(true);

        clientDialogueDescriptionLabel.setText("Create new client");

        firstNameLabel.setText("First Name:");

        lastNameLabel.setText("Last Name:");

        contantInformationLabel.setText("Contact Information");

        addressLabel.setText("Address");

        cityLabel.setText("City");

        countryLabel.setText("Country");

        telephoneLabel.setText("Telephone");

        emailLabel.setText("Email");

        okButton.setText("Create");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clientDialogueDescriptionLabel)
                            .addComponent(contantInformationLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(firstNameLabel)
                                    .addComponent(lastNameLabel)
                                    .addComponent(addressLabel)
                                    .addComponent(cityLabel)
                                    .addComponent(countryLabel)
                                    .addComponent(emailLabel)
                                    .addComponent(telephoneLabel))
                                .addGap(57, 57, 57)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(telephoneField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(emailField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(cityField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(addressField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(lastNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(firstNameField)
                                    .addComponent(countryComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 118, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(okButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clientDialogueDescriptionLabel)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameLabel)
                    .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNameLabel)
                    .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(contantInformationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLabel)
                    .addComponent(addressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cityLabel)
                    .addComponent(cityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(countryLabel)
                    .addComponent(countryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telephoneLabel)
                    .addComponent(telephoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (validateInformation()) {
            ContactTO contact = new ContactTO();
            contact.setAddress(addressField.getText());
            contact.setCity(cityField.getText());
            contact.setCountry((String)countryComboBox.getSelectedItem());
            contact.setPhone(telephoneField.getText());
            contact.setEmail(emailField.getText());

            client.setFirstName(firstNameField.getText());
            client.setLastName(lastNameField.getText());
            client.setContact(contact);

            try {
                int status = createClient ? clientRESTManager.createClient(client).getStatus() : clientRESTManager.updateClient(client).getStatus();
                switch(status) {
                    case 400:
                        JOptionPane.showMessageDialog(this, "An invalid client was sent to the server. Please check the information and try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 500:
                        JOptionPane.showMessageDialog(this, "An error occured on the server side. Please contact the administrator for more information.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    default:
                        clientTableModel.setClients(clientRESTManager.findAllClients());
                }
            } catch (ClientHandlerException che) {
                JOptionPane.showMessageDialog(this, "Server connection was lost. Please check your connection, or contact the administrator for further information. The application will now close.", "Cannot connect to server.", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            dispose();
        }
    }//GEN-LAST:event_okButtonActionPerformed

    private boolean validateInformation() {
        String errors = "";
        if (firstNameField.getText().isEmpty()) {
            errors += "First name is a required field.\n";
        }
        if (lastNameField.getText().isEmpty()){
            errors += "Last name is a required field.\n";
        }
        if (addressField.getText().isEmpty()) {
            errors += "Address field is a required field.\n";
        }
        if (cityField.getText().isEmpty()) {
            errors += "City field is a required field.\n";
        }
        if (telephoneField.getText().isEmpty()) {
            errors += "Telephone field is a required field.\n";
        } else if (!telephoneField.getText().matches("\\d+")) {
            errors += "Invalid telephone number.\n";
        }
        if (emailField.getText().isEmpty()) {
            errors += "Email field is a required field.\n";
        } else if (!emailField.getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            errors += "Invalid email address.\n";
        }
        if (errors.isEmpty()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, errors, "Errors found", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientDialogue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientDialogue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientDialogue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientDialogue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientDialogue().setVisible(true);
            }
        });*/
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressField;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField cityField;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JLabel clientDialogueDescriptionLabel;
    private javax.swing.JLabel contantInformationLabel;
    private javax.swing.JComboBox countryComboBox;
    private javax.swing.JLabel countryLabel;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField telephoneField;
    private javax.swing.JLabel telephoneLabel;
    // End of variables declaration//GEN-END:variables
}
