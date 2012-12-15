package cz.fi.muni.pa165.hotelbookingmanagerdesktop;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanagerdesktop.rest.ClientRESTManager;
import cz.fi.muni.pa165.hotelbookingmanagerdesktop.rest.HotelRESTManager;
import cz.fi.muni.pa165.hotelbookingmanagerdesktop.tablemodels.ClientTableModel;
import cz.fi.muni.pa165.hotelbookingmanagerdesktop.tablemodels.HotelTableModel;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Thanh Dang Hoang Minh
 */
public class Main extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;
    private ClientTableModel clientTableModel = new ClientTableModel();
    private HotelTableModel hotelTableModel= new HotelTableModel();
    private static ClientRESTManager clientRESTManager = new ClientRESTManager();
    private static HotelRESTManager hotelRESTManager = new HotelRESTManager();

    /**
     * Creates new form Main
     */
    public Main(java.awt.Frame parent, boolean modal) {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initTableModels() {
        clientTable.setModel(clientTableModel);
        hotelTable.setModel(hotelTableModel);
    }

    public void refreshClientTable() {
        try {
            clientTableModel.setClients(clientRESTManager.findAllClients());
        } catch (ClientHandlerException ex) {
            JOptionPane.showMessageDialog(this, "Server connection is unavailable. Please contact the administrator for further information. The application will now close.", "Cannot connect to server.", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (UniformInterfaceException uie) {
            if (uie.getResponse().getStatus() == 500) {
                JOptionPane.showMessageDialog(this, "Error on server side. Contact administrator for more information", "Error while getting client list.", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private ClientTO getSelectedClient(int row) {
        try {
            return clientRESTManager.findClient((Long) clientTable.getValueAt(row, 0));
        } catch (ClientHandlerException ex) {
            JOptionPane.showMessageDialog(this, "Server connection was lost. Please check your connection, or contact the administrator for further information. The application will now close.", "Cannot connect to server.", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (UniformInterfaceException uie) {
            int status = uie.getResponse().getStatus();
            switch(status) {
                case 500:
                    JOptionPane.showMessageDialog(this, "Error on server side. Contact administrator for more information", "Error while getting client list.", JOptionPane.ERROR_MESSAGE);
                    break;
                case 404:
                    JOptionPane.showMessageDialog(this, "Client does not exist anymore. The client might have been deleted already.", "Error while getting client info.", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    public void refreshHotelTable() {
        try {
            hotelTableModel.setHotels(hotelRESTManager.findAllHotels());
        } catch (ClientHandlerException ex) {
            JOptionPane.showMessageDialog(this, "Server connection is unavailable. Please contact the administrator for further information. The application will now close.", "Cannot connect to server.", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (UniformInterfaceException uie) {
            if (uie.getResponse().getStatus() == 500) {
                JOptionPane.showMessageDialog(this, "Error on server side. Contact administrator for more information", "Error while getting hotel list.", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private HotelTO getSelectedHotel(int row) {
        try {
            return hotelRESTManager.findHotel((Long) hotelTable.getValueAt(row, 0));
        } catch (ClientHandlerException ex) {
            JOptionPane.showMessageDialog(this, "Server connection was lost. Please check your connection, or contact the administrator for further information. The application will now close.", "Cannot connect to server.", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (UniformInterfaceException uie) {
            int status = uie.getResponse().getStatus();
            switch(status) {
                case 500:
                    JOptionPane.showMessageDialog(this, "Error on server side. Contact administrator for more information", "Error while getting hotel list.", JOptionPane.ERROR_MESSAGE);
                    break;
                case 404:
                    JOptionPane.showMessageDialog(this, "Hotel does not exist anymore. The hotel might have been deleted already.", "Error while getting hotel info.", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        tabbedPane = new javax.swing.JTabbedPane();
        clientPanel = new javax.swing.JPanel();
        clientLabelDescription = new javax.swing.JLabel();
        clientScrollPane = new javax.swing.JScrollPane();
        clientTable = new javax.swing.JTable();
        newClientButton = new javax.swing.JButton();
        deleteClientButton = new javax.swing.JButton();
        editClientButton = new javax.swing.JButton();
        searchClientLabel = new javax.swing.JLabel();
        clientSearchField = new javax.swing.JTextField();
        resetClientSearchButton = new javax.swing.JButton();
        searchClientButton = new javax.swing.JButton();
        hotelPanel = new javax.swing.JPanel();
        hotelScrollPane = new javax.swing.JScrollPane();
        hotelTable = new javax.swing.JTable();
        hotelLabelDescription = new javax.swing.JLabel();
        newHotelButton = new javax.swing.JButton();
        deleteHotelButton = new javax.swing.JButton();
        editHotelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        hotelSearchField = new javax.swing.JTextField();
        hotelSearchButton = new javax.swing.JButton();
        hotelResetSearchButton = new javax.swing.JButton();
        hotelSearchByComboBox = new javax.swing.JComboBox();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        quitMenuItem = new javax.swing.JMenuItem();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hotel Booking Manager Desktop Client");

        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });

        clientLabelDescription.setText("List of clients");

        clientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "First Name", "Last Name", "Address", "City", "Country", "Telephone", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        clientScrollPane.setViewportView(clientTable);
        clientTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        newClientButton.setText("New client");
        newClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newClientButtonActionPerformed(evt);
            }
        });

        deleteClientButton.setText("Delete client");
        deleteClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteClientButtonActionPerformed(evt);
            }
        });

        editClientButton.setText("Edit client");
        editClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editClientButtonActionPerformed(evt);
            }
        });

        searchClientLabel.setText("Search clients by name");

        clientSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                clientSearchFieldKeyReleased(evt);
            }
        });

        resetClientSearchButton.setText("Reset");
        resetClientSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetClientSearchButtonActionPerformed(evt);
            }
        });

        searchClientButton.setText("Search");
        searchClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchClientButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout clientPanelLayout = new javax.swing.GroupLayout(clientPanel);
        clientPanel.setLayout(clientPanelLayout);
        clientPanelLayout.setHorizontalGroup(
            clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clientScrollPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clientPanelLayout.createSequentialGroup()
                        .addComponent(deleteClientButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editClientButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newClientButton))
                    .addGroup(clientPanelLayout.createSequentialGroup()
                        .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clientLabelDescription)
                            .addComponent(searchClientLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(clientPanelLayout.createSequentialGroup()
                        .addComponent(clientSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addComponent(searchClientButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetClientSearchButton)))
                .addContainerGap())
        );
        clientPanelLayout.setVerticalGroup(
            clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchClientLabel)
                .addGap(7, 7, 7)
                .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetClientSearchButton)
                    .addComponent(searchClientButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientLabelDescription)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clientScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newClientButton)
                    .addComponent(deleteClientButton)
                    .addComponent(editClientButton))
                .addContainerGap())
        );

        tabbedPane.addTab("Client", clientPanel);

        hotelTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Address", "City", "Country", "Telephone", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        hotelScrollPane.setViewportView(hotelTable);

        hotelLabelDescription.setText("List of hotels");

        newHotelButton.setText("New hotel");
        newHotelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newHotelButtonActionPerformed(evt);
            }
        });

        deleteHotelButton.setText("Delete hotel");
        deleteHotelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteHotelButtonActionPerformed(evt);
            }
        });

        editHotelButton.setText("Edit hotel");
        editHotelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editHotelButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Search hotels by:");

        hotelSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hotelSearchFieldKeyReleased(evt);
            }
        });

        hotelSearchButton.setText("Search");
        hotelSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotelSearchButtonActionPerformed(evt);
            }
        });

        hotelResetSearchButton.setText("Reset");
        hotelResetSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotelResetSearchButtonActionPerformed(evt);
            }
        });

        hotelSearchByComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Name", "Address", "City", "Country" }));

        javax.swing.GroupLayout hotelPanelLayout = new javax.swing.GroupLayout(hotelPanel);
        hotelPanel.setLayout(hotelPanelLayout);
        hotelPanelLayout.setHorizontalGroup(
            hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hotelPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hotelScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hotelPanelLayout.createSequentialGroup()
                        .addComponent(deleteHotelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editHotelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(newHotelButton))
                    .addGroup(hotelPanelLayout.createSequentialGroup()
                        .addComponent(hotelSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hotelSearchButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hotelResetSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(hotelPanelLayout.createSequentialGroup()
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hotelLabelDescription)
                            .addGroup(hotelPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(hotelSearchByComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        hotelPanelLayout.setVerticalGroup(
            hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hotelPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(hotelSearchByComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hotelSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hotelSearchButton)
                    .addComponent(hotelResetSearchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hotelLabelDescription)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hotelScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newHotelButton)
                    .addComponent(deleteHotelButton)
                    .addComponent(editHotelButton))
                .addContainerGap())
        );

        tabbedPane.addTab("Hotel", hotelPanel);

        menuFile.setText("File");

        quitMenuItem.setText("Quit");
        quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitMenuItemActionPerformed(evt);
            }
        });
        menuFile.add(quitMenuItem);

        menuBar.add(menuFile);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newClientButtonActionPerformed
        new ClientDialog(clientTableModel).setVisible(true);
    }//GEN-LAST:event_newClientButtonActionPerformed

    private void deleteClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteClientButtonActionPerformed
        if (clientTable.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a client you wish to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            int reply = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this client?", "Confirm deletion", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {
                    int status = clientRESTManager.deleteClient(getSelectedClient(clientTable.getSelectedRow())).getStatus();
                    switch(status) {
                        case 404:
                            JOptionPane.showMessageDialog(this, "Selected client cannot be deleted. The client is not present in the databse anymore - The record might have been deleted by someone else.", "Error while deleting.", JOptionPane.ERROR_MESSAGE);
                            break;
                        case 417:
                            JOptionPane.showMessageDialog(this, "Selected client cannot be deleted. The client still has an active reservation.", "Error while deleting.", JOptionPane.ERROR_MESSAGE);
                            break;
                        case 500:
                            JOptionPane.showMessageDialog(this, "There was an error on the server side. Please contact the administrator for furhter information.", "Error while deleting.", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                    refreshClientTable();
                } catch (ClientHandlerException che){
                    JOptionPane.showMessageDialog(this, "Server connection was lost. Please check your connection, or contact the administrator for further information. The application will now close.", "Cannot connect to server.", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                } catch (IllegalArgumentException iae) {
                    JOptionPane.showMessageDialog(this, "Cannot delete a nonexistent client.", "Error while deleting.", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_deleteClientButtonActionPerformed

    private void newHotelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newHotelButtonActionPerformed
        new HotelDialog(hotelTableModel).setVisible(true);
    }//GEN-LAST:event_newHotelButtonActionPerformed

    private void deleteHotelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteHotelButtonActionPerformed
        if (hotelTable.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a hotel you wish to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }else{
            int reply = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this hotel?", "Confirm deletion", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {
                    int status = hotelRESTManager.deleteHotel(getSelectedHotel(hotelTable.getSelectedRow())).getStatus();
                    switch(status) {
                        case 404:
                            JOptionPane.showMessageDialog(this, "Selected hotel cannot be deleted. The hotel is not present in the databse anymore - The record might have been deleted by someone else.", "Error while deleting.", JOptionPane.ERROR_MESSAGE);
                            break;
                        case 417:
                            JOptionPane.showMessageDialog(this, "Selected hotel cannot be deleted. The hotel still has an existing room.", "Error while deleting.", JOptionPane.ERROR_MESSAGE);
                            break;
                        case 500:
                            JOptionPane.showMessageDialog(this, "There was an error on the server side. Please contact the administrator for furhter information.", "Error while deleting.", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                    refreshHotelTable();
                } catch (ClientHandlerException che){
                    JOptionPane.showMessageDialog(this, "Server connection was lost. Please check your connection, or contact the administrator for further information. The application will now close.", "Cannot connect to server.", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                } catch (IllegalArgumentException iae) {
                    JOptionPane.showMessageDialog(this, "Cannot delete a nonexistent hotel.", "Error while deleting.", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_deleteHotelButtonActionPerformed

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
        //try {
            switch(tabbedPane.getSelectedIndex()) {
            //Case ClientPane was selected
            case 0:
                if (!clientTable.getModel().equals(clientTableModel)) {
                    initTableModels();
                }
                //customerSearchField.setText(""); //NOI18N
                refreshClientTable();
                break;
            //Case HotelPane was selected
            case 1:
                 if (!hotelTable.getModel().equals(hotelTableModel)) {
                    initTableModels();
                }
                //customerSearchField.setText(""); //NOI18N
                refreshHotelTable();
                break;
        }
        //} catch (SomeException e) {

        //}
    }//GEN-LAST:event_tabbedPaneStateChanged

    private void editClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editClientButtonActionPerformed
        if (clientTable.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a client you wish to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            new ClientDialog(getSelectedClient(clientTable.getSelectedRow()), clientTableModel).setVisible(true);
        }
    }//GEN-LAST:event_editClientButtonActionPerformed

    private void quitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_quitMenuItemActionPerformed

    private void editHotelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editHotelButtonActionPerformed
        if(hotelTable.getSelectedRowCount() ==0){
            JOptionPane.showMessageDialog(this, "Please select a hotel you wish to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
        }else{
            new HotelDialog(getSelectedHotel(hotelTable.getSelectedRow()), hotelTableModel).setVisible(true);
        }
    }//GEN-LAST:event_editHotelButtonActionPerformed

    private void searchClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchClientButtonActionPerformed
        String searchString = clientSearchField.getText();
        try {
            clientTableModel.setClients(clientRESTManager.findClientsByName(searchString));
        } catch (ClientHandlerException ex) {
            JOptionPane.showMessageDialog(this, "Server connection is unavailable. Please contact the administrator for further information. The application will now close.", "Cannot connect to server.", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (UniformInterfaceException uie) {
            int status = uie.getResponse().getStatus();
            switch(status) {
                case 400:
                    refreshClientTable();
                    break;
                case 404:
                    clientTableModel.setClients(new ArrayList<ClientTO>());
                    break;
                case 500:
                    JOptionPane.showMessageDialog(this, "Error on server side. Contact administrator for more information", "Error while getting client list.", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }//GEN-LAST:event_searchClientButtonActionPerformed

    private void resetClientSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetClientSearchButtonActionPerformed
        clientSearchField.setText("");
        refreshClientTable();
    }//GEN-LAST:event_resetClientSearchButtonActionPerformed

    private void clientSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientSearchFieldKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchClientButtonActionPerformed(null);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            resetClientSearchButtonActionPerformed(null);
        }
    }//GEN-LAST:event_clientSearchFieldKeyReleased

    private void hotelSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotelSearchButtonActionPerformed
        String searchString = hotelSearchField.getText();
        try {
            switch(hotelSearchByComboBox.getSelectedIndex()){
                case 0: hotelTableModel.setHotels(hotelRESTManager.findHotelsByName(searchString));
                        break;
                case 1: hotelTableModel.setHotels(hotelRESTManager.findHotelsByAddress(searchString));
                        break;
                case 2: hotelTableModel.setHotels(hotelRESTManager.findHotelsByCity(searchString));
                        break;
                case 3: hotelTableModel.setHotels(hotelRESTManager.findHotelsByCountry(searchString));
                        break;
            }
            //hotelTableModel.setHotels(hotelRESTManager.findHotelsByName(searchString));
        } catch (ClientHandlerException ex) {
            JOptionPane.showMessageDialog(this, "Server connection is unavailable. Please contact the administrator for further information. The application will now close.", "Cannot connect to server.", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (UniformInterfaceException uie) {
            int status = uie.getResponse().getStatus();
            switch(status) {
                case 400:
                    refreshClientTable();
                    break;
                case 404:
                    hotelTableModel.setHotels(new ArrayList<HotelTO>());
                    break;
                case 500:
                    JOptionPane.showMessageDialog(this, "Error on server side. Contact administrator for more information", "Error while getting client list.", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }//GEN-LAST:event_hotelSearchButtonActionPerformed

    private void hotelResetSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotelResetSearchButtonActionPerformed
        hotelSearchField.setText("");
        refreshHotelTable();
    }//GEN-LAST:event_hotelResetSearchButtonActionPerformed

    private void hotelSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hotelSearchFieldKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            hotelSearchButtonActionPerformed(null);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            hotelResetSearchButtonActionPerformed(null);
        }
    }//GEN-LAST:event_hotelSearchFieldKeyReleased

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main dialog = new Main(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel clientLabelDescription;
    private javax.swing.JPanel clientPanel;
    private javax.swing.JScrollPane clientScrollPane;
    private javax.swing.JTextField clientSearchField;
    private javax.swing.JTable clientTable;
    private javax.swing.JButton deleteClientButton;
    private javax.swing.JButton deleteHotelButton;
    private javax.swing.JButton editClientButton;
    private javax.swing.JButton editHotelButton;
    private javax.swing.JLabel hotelLabelDescription;
    private javax.swing.JPanel hotelPanel;
    private javax.swing.JButton hotelResetSearchButton;
    private javax.swing.JScrollPane hotelScrollPane;
    private javax.swing.JButton hotelSearchButton;
    private javax.swing.JComboBox hotelSearchByComboBox;
    private javax.swing.JTextField hotelSearchField;
    private javax.swing.JTable hotelTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JButton newClientButton;
    private javax.swing.JButton newHotelButton;
    private javax.swing.JMenuItem quitMenuItem;
    private javax.swing.JButton resetClientSearchButton;
    private javax.swing.JButton searchClientButton;
    private javax.swing.JLabel searchClientLabel;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
}
