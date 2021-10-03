package com.tema;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class CompanyPage extends JFrame implements ActionListener, ListSelectionListener {
    Application application;
    TitleLabel departmentsTitle, employeesTitle, recruitersTitle, jobsTitle;
    BudgetButton budgetButton;
    ShowProfileButton showProfileButton;
    JTextField budgetField;
    JList departmentsList;
    JTable employeesTable, recruitersTable, jobsTable;
    Vector<String> employeesColumns, jobsColumns;
    Vector<Vector<String>> employeesData, jobsData;
    DefaultTableModel employeesModel, jobsModel;
    Company company;
    Vector<Department> departments;

    CompanyPage(Company company) {
        this.company = company;
        application = Application.getInstance();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setMinimumSize(new Dimension(1050, 600));
        setTitle(company.getName());
        getContentPane().setBackground(Color.white);

        departmentsTitle = new TitleLabel("Departments");
        departments = new Vector<Department> (company.getDepartments());
        departmentsList = new JList(departments);
        departmentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        departmentsList.getSelectionModel().addListSelectionListener(this);

        JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, departmentsTitle,
                departmentsList);
        splitPane1.setPreferredSize(new Dimension(1050, 250));

        budgetField =  new JTextField();
        budgetButton = new BudgetButton(budgetField, this);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, budgetButton,
                budgetField);
        JSplitPane splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane1,
                splitPane2);

        employeesTitle = new TitleLabel("Employees");
        employeesColumns = new Vector<>();
        employeesColumns.add("Name");
        employeesColumns.add("Email");
        employeesData = new Vector<>();
        employeesModel = new DefaultTableModel(employeesData, employeesColumns);
        employeesTable = new JTable(employeesModel);
        JScrollPane scrollPane1 = new JScrollPane(employeesTable);
        scrollPane1.setPreferredSize(new Dimension(300, 200));
        scrollPane1.setLayout(new ScrollPaneLayout());
        employeesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JSplitPane splitPane4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, employeesTitle, scrollPane1);
        showProfileButton = new ShowProfileButton("Show profile", this);
        JSplitPane splitPane5 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane4, showProfileButton);

        recruitersTitle = new TitleLabel("Recruiters");
        Vector<String> recruitersColumns = new Vector<>();
        recruitersColumns.add("Name");
        recruitersColumns.add("Rating");
        Vector<Vector<String>> recruitersData = new Vector<>();
        for (Recruiter recruiter : company.getRecruiters()) {
            Vector<String> data = new Vector<>();
            data.add(recruiter.getName() + " " + recruiter.getSurname());
            data.add(String.format("%.1f", recruiter.getRating()));
            recruitersData.add(data);
        }
        DefaultTableModel recruitersModel = new DefaultTableModel(recruitersData, recruitersColumns);
        recruitersTable = new JTable(recruitersModel);
        JScrollPane scrollPane2 = new JScrollPane(recruitersTable);
        scrollPane2.setPreferredSize(new Dimension(300, 200));
        scrollPane2.setLayout(new ScrollPaneLayout());
        recruitersTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JSplitPane splitPane6 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, recruitersTitle, scrollPane2);
        JSplitPane splitPane7 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane5, splitPane6);

        jobsTitle = new TitleLabel("Jobs");
        jobsColumns = new Vector<>();
        jobsData = new Vector<>();
        jobsColumns.add("Job Name");
        jobsColumns.add("Salary");
        jobsColumns.add("Number of position");
        jobsModel = new DefaultTableModel(jobsData, jobsColumns);
        jobsTable = new JTable(jobsModel);
        JScrollPane scrollPane3 = new JScrollPane(jobsTable);
        scrollPane3.setPreferredSize(new Dimension(300, 200));
        scrollPane3.setLayout(new ScrollPaneLayout());
        jobsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JSplitPane splitPane8 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jobsTitle, scrollPane3);
        JSplitPane splitPane9 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane7, splitPane8);
        JSplitPane splitPane10 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane3, splitPane9);

        add(splitPane10);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Command command = (Command) e.getSource();

        if (command == budgetButton) {
            if (departmentsList.getSelectedIndex() != -1){
                budgetButton.setDepartment(departments.get(departmentsList.getSelectedIndex()));
                command.execute();
            }
            else {
                JOptionPane.showMessageDialog(this, "Please, select a department.");
            }
        }

        if (command == showProfileButton) {
            if (employeesTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Please, select" +
                        " a user from the list.");
                return;
            }
            showProfileButton.setConsumer(departments.get(
                    departmentsList.getSelectedIndex()).getEmployees().get(employeesTable.getSelectedRow()));
            command.execute();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        Department selectedDepartment = company.getDepartments().get(departmentsList.getSelectedIndex());

        employeesData.removeAll(employeesData);
        for(Employee employee: selectedDepartment.getEmployees()) {
            Vector<String> data =  new Vector<>();
            data.add(employee.getName() + " " + employee.getSurname());
            data.add(employee.getEmail());
            employeesData.add(data);
        }
        employeesTable.revalidate();

        jobsData.removeAll(jobsData);
        for(Job job: selectedDepartment.getJobs()) {
            Vector<String> data =  new Vector<>();
            data.add(job.getJobName());
            data.add(String.format("%d", job.getSalary()));
            data.add(String.format("%d", job.getNoPositions()));
            jobsData.add(data);
        }
        jobsTable.revalidate();
    }
}
