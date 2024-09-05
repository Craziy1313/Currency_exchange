package org.example;

import org.example.util.DatabaseInitializer;
import org.example.util.SQLiteConnection;


public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();
    }
}