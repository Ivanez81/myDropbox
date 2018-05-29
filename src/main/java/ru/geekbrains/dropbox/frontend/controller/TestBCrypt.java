package ru.geekbrains.dropbox.frontend.controller;

public class TestBCrypt {

    public static void main(String[] args) {
        // spring 4.0.0
        org.springframework.security.crypto.password.PasswordEncoder encoder
                = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

        for (int i = 0; i < 5; i++) {
            // "123456" - plain text - user input from user interface
            String passwd = encoder.encode("123456");

            // passwd - password from database
            System.out.println(passwd); // print hash

            // true for all 5 iteration
            System.out.println(encoder.matches("123456", passwd));
        }


    }


}
