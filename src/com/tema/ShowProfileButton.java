package com.tema;

import javax.swing.*;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.*;

public class ShowProfileButton extends JButton implements Command {
    private Consumer consumer;

    ShowProfileButton(String title, ActionListener actionListener) {
        super(title);
        addActionListener(actionListener);
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void execute() {
        new UserProfile(consumer);
    }
}
