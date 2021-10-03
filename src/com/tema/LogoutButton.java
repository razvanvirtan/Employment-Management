package com.tema;

import javax.accessibility.AccessibleRelation;
import javax.swing.*;
import java.awt.event.ActionListener;

public class LogoutButton extends JButton implements Command {
    private JFrame frame;

    LogoutButton(JFrame frame, ActionListener actionListener) {
        super("Log Out");
        this.frame = frame;
        addActionListener(actionListener);
    }

    @Override
    public void execute() {
        new LogInPage();
        frame.dispose();
    }
}
