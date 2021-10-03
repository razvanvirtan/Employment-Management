package com.tema;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

public class UserProfile extends JFrame {
    Consumer consumer;
    JTree tree;
    JScrollPane scrollPane;

    UserProfile(Consumer consumer) {
        super("User Profile");
        this.consumer = consumer;

        Resume resume = consumer.getResume();
        Information personalInfo = resume.getPersonalInfo();
        LinkedList<Education> educationList = resume.getPreviousEducation();
        LinkedList<Experience> experienceList = resume.getPreviousExperience();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(750, 700));
        getContentPane().setBackground(Color.white);
        setLayout(new FlowLayout());

        // add basic personal info to profile tree
        DefaultMutableTreeNode root =
                new DefaultMutableTreeNode(personalInfo.getName() +
                        " " + personalInfo.getSurname());

        DefaultMutableTreeNode phoneNumber =
                new DefaultMutableTreeNode("Phone: " + personalInfo.getPhoneNumber());
        DefaultMutableTreeNode email =
                new DefaultMutableTreeNode("Email: " + personalInfo.getEmail());
        DefaultMutableTreeNode birthDate =
                new DefaultMutableTreeNode("Birth Date: " + personalInfo.getBirthDate());
        DefaultMutableTreeNode sex =
                new DefaultMutableTreeNode("Sex: " + personalInfo.getSex());
        root.add(phoneNumber);
        root.add(email);
        root.add(birthDate);
        root.add(sex);

        // add languages to profile tree
        DefaultMutableTreeNode languages = new DefaultMutableTreeNode("Languages");
        for (Map.Entry<String, String> entry :
                personalInfo.getLanguages().entrySet()) {
            DefaultMutableTreeNode language = new DefaultMutableTreeNode(entry.getKey()
                    + ": " + entry.getValue());
            languages.add(language);
        }
        root.add(languages);

        // add education to profile tree
        DefaultMutableTreeNode education =
                new DefaultMutableTreeNode("Education");
        for (Education ed :
                educationList) {
            DefaultMutableTreeNode institution =
                    new DefaultMutableTreeNode("Institution: " + ed.getInstitution());
            DefaultMutableTreeNode level =
                    new DefaultMutableTreeNode("Level: " + ed.getLevel());
            DefaultMutableTreeNode grade =
                    new DefaultMutableTreeNode("Grade: " + ed.getGrade());
            DefaultMutableTreeNode startDate =
                    new DefaultMutableTreeNode("Start Date: " + ed.getStartDate());
            DefaultMutableTreeNode endDate;
            if (ed.getEndDate() != null)
                endDate =
                        new DefaultMutableTreeNode("End Date: " + ed.getEndDate());
            else
                endDate =
                        new DefaultMutableTreeNode("End Date: In progress");

            institution.add(level);
            institution.add(grade);
            institution.add(startDate);
            institution.add(endDate);
            education.add(institution);
        }
        root.add(education);

        // add experience to profile tree
        DefaultMutableTreeNode experience =
                new DefaultMutableTreeNode("Experience");
        for (Experience exp :
                experienceList) {
            DefaultMutableTreeNode company =
                    new DefaultMutableTreeNode("Work Place: " + exp.getCompany());
            DefaultMutableTreeNode level =
                    new DefaultMutableTreeNode("Position: " + exp.getPosition());
            DefaultMutableTreeNode grade =
                    new DefaultMutableTreeNode("Department: " + exp.getDepartment());
            DefaultMutableTreeNode startDate =
                    new DefaultMutableTreeNode("Start Date: " + exp.getStartDate());
            DefaultMutableTreeNode endDate;
            if (exp.getEndDate() != null)
                endDate =
                        new DefaultMutableTreeNode("End Date: " + exp.getEndDate());
            else
                endDate =
                        new DefaultMutableTreeNode("End Date: In progress");
            company.add(level);
            company.add(grade);
            company.add(startDate);
            company.add(endDate);
            experience.add(company);
        }
        root.add(experience);

        tree = new JTree(root);
        tree.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        ImageIcon leafIcon = new ImageIcon("resources/leafimage.png");
        Image img = leafIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        leafIcon = new ImageIcon(img);
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(leafIcon);
        tree.setCellRenderer(renderer);
        scrollPane = new JScrollPane(tree);
        scrollPane.setPreferredSize(new Dimension(700, 500));

        add(scrollPane);
        setVisible(true);
        pack();
    }
}
