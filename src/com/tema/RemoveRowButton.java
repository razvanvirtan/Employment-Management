package com.tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

public class RemoveRowButton extends JButton implements Command {
    private JTable table;

    RemoveRowButton(String title, JTable table, ActionListener actionListener) {
        super(title);
        addActionListener(actionListener);
        this.table = table;
    }

    @Override
    public void execute() {
        ((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRow());
    }
}
