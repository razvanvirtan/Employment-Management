package com.tema;

import javax.swing.*;
import java.awt.*;

public class FieldLabel extends JLabel {
    FieldLabel(String text) {
        super(text);
        setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
    }
}
