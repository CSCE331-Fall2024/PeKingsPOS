package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import com.pekings.pos.storage.PersistentRepository;
import com.pekings.pos.storage.Repository;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Map;

public class Main {

    private static Repository repository;

    public static void main(String[] args) throws Exception {
        repository = new PersistentRepository();
        ((PersistentRepository) repository).initialize();

        long initial = System.currentTimeMillis();

        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 1);
        Date date = new Date(cal.getTime().getTime());

        double income = repository.getWeeklyIncome(date);

        System.out.println("Fetched! Took " + (System.currentTimeMillis() - initial) + "ms");
        System.out.println("Income " + income);

        //POSApp posApp = new POSApp();
        //posApp.initialize();
    }

    public Repository getRepository() {
        return repository;
    }
}