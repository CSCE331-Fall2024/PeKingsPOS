package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import com.pekings.pos.storage.PersistentRepository;
import com.pekings.pos.storage.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

public class Main {

    private static Repository repository;

    public static void main(String[] args) throws Exception {
        repository = new PersistentRepository();
        ((PersistentRepository) repository).initialize();

        long now = System.currentTimeMillis();
        double income = repository.getWeeklyIncome(LocalDate.parse("2024-01-01"));
        long after = System.currentTimeMillis();

        System.out.println("Fetched! Took " + (after - now) + "ms");
        System.out.println("Income " + income);

        //POSApp posApp = new POSApp();
        //posApp.initialize();
    }

    public Repository getRepository() {
        return repository;
    }
}