package com.tema;

import javax.swing.*;
import java.awt.*;

public class RoleRadioButton extends JRadioButton {
    RoleRadioButton(String text) {
        super(text);
        this.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        this.setBackground(Color.WHITE);
        setActionCommand(text);
    }
}
