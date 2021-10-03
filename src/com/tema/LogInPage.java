package com.tema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInPage extends JFrame implements ActionListener {
    private JTextField emailField;
    private JPasswordField passwordField;
    private FieldLabel emailLabel, passwordLabel;
    private ButtonGroup roles;
    private JButton loginButton;
    Application application;

    LogInPage() {
        super("Log In");
        application = Application.getInstance();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        getContentPane().setBackground(Color.white);
        setLayout(new FlowLayout());


        RoleRadioButton userButton = new RoleRadioButton("Active User");
        RoleRadioButton employeeButton = new RoleRadioButton("Employee");
        RoleRadioButton managerButton = new RoleRadioButton("Manager");
        RoleRadioButton adminButton = new RoleRadioButton("Administrator");
        JPanel rolesPane = new JPanel();
        roles = new ButtonGroup();
        rolesPane.add(userButton); rolesPane.add(employeeButton);
        rolesPane.add(managerButton); rolesPane.add(adminButton);
        roles.add(userButton); roles.add(employeeButton);
        roles.add(managerButton); roles.add(adminButton);
        rolesPane.setLayout(new GridLayout(4, 1));
        rolesPane.setBackground(Color.WHITE);
        rolesPane.setPreferredSize(new Dimension(300, 150));

        emailLabel = new FieldLabel("Email");
        passwordLabel = new FieldLabel("Password");
        emailField = new JTextField();
        passwordField = new JPasswordField();

        JPanel loginPane = new JPanel();
        loginPane.add(emailLabel); loginPane.add(emailField);
        loginPane.add(passwordLabel); loginPane.add(passwordField);
        loginPane.setLayout(new GridLayout(2, 2));
        loginPane.setBackground(Color.WHITE);
        loginPane.setPreferredSize(new Dimension(300, 50));

        JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                rolesPane, loginPane);

        loginButton = new JButton("Log In");
        loginButton.addActionListener(this);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane1,
                loginButton);

        add(splitPane2);
        setVisible(true);
        pack();
    }

    public boolean validate(Consumer consumer) {
        if (consumer.getEmail().compareTo(emailField.getText()) == 0) {
            if (consumer.getPassword().compareTo(String.valueOf(
                    passwordField.getPassword())) == 0)
                return true;
            JOptionPane.showMessageDialog(this, "Wrong Password");
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (roles.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Please select a role.");
            return;
        }

        switch (roles.getSelection().getActionCommand()) {
            case "Active User": {
                for (User user: application.getUsers())
                    if (validate(user)) {
                        new UserPage(user);
                        this.dispose();
                        return;
                    }
                JOptionPane.showMessageDialog(this, "Wrong Email/Role");
                break;
            }
            case "Employee": {
                for (Employee employee : application.getEmployees())
                    if (validate(employee)) {
                        new EmployeePage(employee);
                        this.dispose();
                        return;
                    }
                for (Company company : application.getCompanies())
                    if (validate(company.getManager())) {
                        new EmployeePage(company.getManager());
                        this.dispose();
                        return;
                    }
                JOptionPane.showMessageDialog(this, "Wrong Email/Role");
                break;
            }
            case "Manager": {
                for (Company company : application.getCompanies())
                    if (validate(company.getManager())) {
                        new ManagerPage(company.getName());
                        this.dispose();
                        return;
                    }
                JOptionPane.showMessageDialog(this, "Wrong Email/Role");
                break;
            }
            case "Administrator": {
                for (Company company : application.getCompanies())
                    if (validate(company.getManager())) {
                        new AdminPage();
                        this.dispose();
                        return;
                    }
                JOptionPane.showMessageDialog(this, "Wrong Email/Role");
                break;
            }
        }
    }
}
