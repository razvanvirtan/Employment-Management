package com.tema;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ShowCompanyButton extends JButton implements Command {
    Company company;

    ShowCompanyButton(String name, ActionListener actionListener) {
        super(name);
        addActionListener(actionListener);
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public void execute() {
        new CompanyPage(company);
    }
}
