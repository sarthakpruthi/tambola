package org.example;

import org.example.service.Tambola;

public class Main {

    public static void main(String[] args) {
        Tambola tambola = new Tambola(6);
        tambola.generateTickets();
    }
}