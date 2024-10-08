package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import com.pekings.pos.object.Order;
import com.pekings.pos.storage.PersistentRepository;
import com.pekings.pos.storage.Repository;
import com.pekings.pos.util.DateGenerator;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Main {

    private static Repository repository;

    public static void main(String[] args) throws Exception {
        repository = new PersistentRepository();
        ((PersistentRepository) repository).initialize();

        POSApp posApp = new POSApp();
        posApp.initialize();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ((PersistentRepository) repository).shutdown();
            } catch (SQLException ignored) {}
        }));
    }

    public static Repository getRepository() {
        return repository;
    }
}