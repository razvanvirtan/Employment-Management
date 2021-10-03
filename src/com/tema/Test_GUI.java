package com.tema;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Test_GUI {
    public static void main(String []args) throws IOException, ParseException {
        Application application = Application.getInstance();
        Test.parseJSON();
        LogInPage logInPage = new LogInPage();
    }
}
