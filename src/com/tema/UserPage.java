package com.tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;

public class UserPage extends EmployeePage {
    JButton showNotifications, backToProfile;
    RemoveRowButton removeRowButton;
    JTable notificationsTable;
    JPanel cards;
    CardLayout cardLayout;

    UserPage(Consumer consumer) {
        super(consumer);
        // profile view
        showNotifications = new JButton("See notifications");
        showNotifications.addActionListener(this);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane1,
                showNotifications);

        // notifications view
        Vector<Vector<String>> tableData = new Vector<>();
        Vector<String> tableColumns = new Vector<>();
        tableColumns.add("Company");
        tableColumns.add("Job");
        tableColumns.add("Status");
        for (Notification notification:
                ((User) consumer).getNotifications()) {
            Vector<String> data = new Vector<>();
            data.add(notification.getJob().getCompanyName());
            data.add(notification.getJob().getJobName());
            data.add(notification.getStatus());
            tableData.add(data);
        }
        DefaultTableModel model = new DefaultTableModel(tableData, tableColumns);
        notificationsTable = new JTable(model);
        JScrollPane scrollPane2 = new JScrollPane(notificationsTable);
        scrollPane2.setPreferredSize(new Dimension(500, 500));
        scrollPane2.setLayout(new ScrollPaneLayout());
        notificationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        removeRowButton = new RemoveRowButton("Delete notification",
                notificationsTable, this);
        JSplitPane splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane2,
                removeRowButton);
        backToProfile = new JButton("Back to Profile");
        backToProfile.addActionListener(this);
        JSplitPane splitPane4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane3,
                backToProfile);

        /*
         * Card Layout implementation (helps at varying the content displayed on
         * this frame.
         */
        JPanel profileCard = new JPanel();
        profileCard.add(splitPane2);
        JPanel notificationsCard = new JPanel();
        notificationsCard.add(splitPane4);

        cards = new JPanel(new CardLayout());
        cards.add(profileCard, "Profile");
        cards.add(notificationsCard, "Notifications");
        this.getContentPane().add(cards, BorderLayout.CENTER);

        cardLayout = (CardLayout) (cards.getLayout());
        cardLayout.show(cards, "Profile");

        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showNotifications)
            cardLayout.show(cards, "Notifications");

        if (e.getSource() == backToProfile)
            cardLayout.show(cards, "Profile");

        if (e.getSource() == removeRowButton) {
            if (notificationsTable.getSelectedRow() != -1) {
                LinkedList<Notification> notifications = ((User) consumer).getNotifications();
                notifications.remove(notifications.get(notificationsTable.getSelectedRow()));
                Command command = (Command) e.getSource();
                command.execute();
            }
           else {
               JOptionPane.showMessageDialog(this, "Please select a notification.");
            }
        }

        if (e.getSource() == logoutButton) {
            Command command = (Command) e.getSource();
            command.execute();
        }

        if (e.getSource() == searchButton) {
           searchUser(); // inherited from EmployeePage parent class
        }
    }
}
