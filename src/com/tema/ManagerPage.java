package com.tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;

public class ManagerPage extends JFrame implements ActionListener{
    private RemoveRowButton buttonAccept, buttonReject;
    private ShowProfileButton buttonShowProfile;
    private LogoutButton logoutButton;
    private LinkedList<Request<Job, Consumer>> requests;
    private JTable requestTable;
    Application application;

    public ManagerPage(String company) {
        super("Manager Page");

        application = Application.getInstance();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(750, 600));
        getContentPane().setBackground(Color.white);
        setLayout(new FlowLayout());

        JLabel title = new JLabel("Active employement requests");
        title.setBackground(Color.white);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(Font.SERIF, Font.BOLD, 20));

        requests = application.getCompany(company).getManager().getRequests();
        Vector<Vector<String>> tableData = new Vector<>();
        Vector<String> tableColumns = new Vector<>();
        tableColumns.add("Job");
        tableColumns.add("Candidate");
        tableColumns.add("Recruiter");
        tableColumns.add("Score");
        for (Request<Job, Consumer> request:
             requests) {
            Vector<String> data = new Vector<>();
            data.add(request.getKey().getJobName());
            data.add(request.getValue1().getName() + " " + request.getValue1().getSurname());
            data.add(request.getValue2().getName() + " " + request.getValue2().getSurname());
            data.add(String.format("%.1f", request.getScore()));
            tableData.add(data);
        }
        DefaultTableModel model = new DefaultTableModel(tableData, tableColumns);
        requestTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(requestTable);
        scrollPane.setLayout(new ScrollPaneLayout());
        scrollPane.setPreferredSize(new Dimension(700, 350));
        requestTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        buttonAccept = new RemoveRowButton("Accept", requestTable, this);
        buttonReject = new RemoveRowButton("Reject", requestTable, this);
        buttonShowProfile = new ShowProfileButton("Show Profile", this);

        JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                title, scrollPane);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buttonReject, buttonAccept);
        buttonReject.setMinimumSize(new Dimension(350, 20));
        buttonReject.setMinimumSize(new Dimension(350, 20));
        JSplitPane splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                splitPane1, splitPane2);
        JSplitPane splitPane4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                splitPane3, buttonShowProfile);
        logoutButton = new LogoutButton(this, this);
        JSplitPane splitPane5 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                splitPane4, logoutButton);
        add(splitPane5);

        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int index = requestTable.getSelectedRow();

        Command command = (Command) e.getSource();

        if (command == buttonShowProfile) {
            if (index != -1) {
                User user = (User) requests.get(index).getValue1();
                buttonShowProfile.setConsumer(user);
                command.execute();
                return;
            }
            else {
                JOptionPane.showMessageDialog(this, "Please select a request.");
                return;
            }
        }

        if (command == buttonAccept) {
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Please, select a request.");
                return;
            }
            Job job = requests.get(index).getKey();
            if (job.getNoPositions() == 0) {
                JOptionPane.showMessageDialog(this, "There are no more positions" +
                        " available for this job");
                return;
            }
            command.execute();

            User user = (User) requests.get(index).getValue1();
            Company company =  application.getCompany(job.getCompanyName());
            Manager manager = company.getManager();
            manager.hire(user, job);
            job.setNoPositions(job.getNoPositions() - 1);
            if (job.getNoPositions() == 0) {
                job.setOpenFlag(false);
                company.notifyAllObservers(job);
                requests.remove(requests.get(index));
            }
            return;
        }

        if (command == buttonReject) {
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Please, select a request.");
                return;
            }
            command.execute();
            Job job = requests.get(index).getKey();
            User user = (User) requests.get(index).getValue1();
            user.update(new Notification(job, "REJECTED"));
            requests.remove(requests.get(index));
        }

        if (command == logoutButton) {
            command.execute();
        }
    }
}
