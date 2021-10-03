package com.tema;

import javax.swing.*;
import java.awt.event.ActionListener;

public class BudgetButton extends JButton implements Command {
    JTextField budgetField;
    Department department;

    BudgetButton(JTextField budgetField, ActionListener actionListener) {
        super("Calculate Budget");
        addActionListener(actionListener);
        this.budgetField = budgetField;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public void execute() {
        budgetField.setText(String.format("%.1f", department.getTotalSalaryBudget()));
    }
}
