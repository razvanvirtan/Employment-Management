package com.tema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeePage extends UserProfile implements ActionListener {
    TextField searchedConsumer;
    JButton searchButton;
    LogoutButton logoutButton;
    JSplitPane splitPane;
    JSplitPane splitPane1;
    Application application;

    EmployeePage(Consumer consumer) {
        super(consumer);
        application = Application.getInstance();

        logoutButton = new LogoutButton(this, this);
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, logoutButton,
                scrollPane);
        add(splitPane);

        JPanel searchConsumer = new JPanel();
        searchConsumer.setLayout(new GridLayout(1, 2));
        searchedConsumer = new TextField();
        searchButton = new JButton("Search other users");
        searchButton.addActionListener(this);
        searchConsumer.add(searchedConsumer);
        searchConsumer.add(searchButton);

        splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane,
                searchConsumer);

        add(splitPane1);
    }

    public void searchUser() {
        String[] name = searchedConsumer.getText().split(" ");
        boolean found = false;
        for (Employee employee : application.getEmployees()) {
            if (employee.getName().compareTo(name[0]) == 0
                    && employee.getSurname().compareTo(name[1]) == 0) {
                new UserProfile(employee);
                found = true;
            }
        }
        for (User user : application.getUsers()) {
            if (user.getName().compareTo(name[0]) == 0
                    && user.getSurname().compareTo(name[1]) == 0) {
                new UserProfile(user);
                found = true;
            }
        }
        for (Company company: application.getCompanies()) {
            if (company.getManager().getName().compareTo(name[0]) == 0
                    && company.getManager().getSurname().compareTo(name[1]) == 0) {
                new UserProfile(company.getManager());
                found = true;
            }
        }
        if (!found)
            JOptionPane.showMessageDialog(this, "No user with this name");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            Command command = (Command) e.getSource();
            command.execute();
        }
        else {
           searchUser();
        }
    }

}
