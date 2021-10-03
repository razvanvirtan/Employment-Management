package com.tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class AdminPage extends JFrame implements ActionListener {
    private Application application;
    private TitleLabel usersTitle;
    private JTable usersTable;
    private JList companiesList;
    private ShowProfileButton showUserButton;
    private ShowCompanyButton showCompanyButton;
    private ArrayList<User> users;
    private Vector<Company> companies;
    private LogoutButton logoutButton;

    AdminPage() {
        super("Admin Page");

        application = Application.getInstance();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setMinimumSize(new Dimension(900, 600));
        getContentPane().setBackground(Color.white);

        usersTitle = new TitleLabel("Unemployed users");
        users = application.getUsers();
        Vector<Vector<String>> tableData = new Vector<>();
        Vector<String> tableColumns = new Vector<>();
        tableColumns.add("User Name");
        tableColumns.add("E-mail");
        tableColumns.add("Phone Number");
        for (User user:
                users) {
            Vector<String> data = new Vector<>();
            data.add(user.getName() + " " + user.getSurname());
            data.add(user.getEmail());
            data.add(user.getPhoneNumber());
            tableData.add(data);
        }
        DefaultTableModel model = new DefaultTableModel(tableData, tableColumns);
        usersTable = new JTable(model);
        JScrollPane scrollPane1 = new JScrollPane(usersTable);
        scrollPane1.setPreferredSize(new Dimension(600, 400));
        scrollPane1.setLayout(new ScrollPaneLayout());
        usersTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, usersTitle,
                scrollPane1);
        showUserButton = new ShowProfileButton("Show user profile", this);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane1,
                showUserButton);

        companies = new Vector<Company>(application.getCompanies());
        companiesList = new JList(companies);
        companiesList.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        TitleLabel companiesTitle = new TitleLabel("Companies");
        JScrollPane scrollPane2 = new JScrollPane(companiesList);
        scrollPane2.setPreferredSize(new Dimension(300, 400));
        scrollPane2.setLayout(new ScrollPaneLayout());
        JSplitPane splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, companiesTitle,
                scrollPane2);
        splitPane3.setLayout(new FlowLayout());
        showCompanyButton = new ShowCompanyButton("View company" +
                " details", this);
        JSplitPane splitPane4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane3,
                showCompanyButton);
        JSplitPane splitPane5 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane2,
                splitPane4);
        logoutButton = new LogoutButton(this, this);
        JSplitPane splitPane6 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane5,
                logoutButton);
        add(splitPane6);
        setVisible(true);
        pack();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Command command = (Command) e.getSource();

        if (command == showUserButton) {
            if (usersTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Please, select" +
                        " a user from the list.");
                return;
            }
            showUserButton.setConsumer(users.get(usersTable.getSelectedRow()));
            command.execute();
            return;
        }

        if (command == showCompanyButton) {
            if (companiesList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Please, select" +
                        " a company from the list.");
                return;
            }

            showCompanyButton.setCompany(application.getCompanies().get(
                    companiesList.getSelectedIndex()));
            command.execute();
            return;
        }

        if (command == logoutButton)
            command.execute();
    }
}
