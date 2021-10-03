package com.tema;

import javax.swing.*;
import java.awt.*;

public class TitleLabel extends JLabel {
    TitleLabel(String text) {
        super(text);
        setBackground(Color.white);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font(Font.SERIF, Font.BOLD, 20));
    }
}
