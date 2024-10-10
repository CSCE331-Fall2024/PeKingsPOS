package com.pekings.pos;

import com.pekings.pos.storage.PersistentRepository;
import com.pekings.pos.storage.Repository;

import java.sql.SQLException;

public class Main {

    private static Repository repository;

    public static void main(String[] args) throws Exception {
        repository = new PersistentRepository();
        ((PersistentRepository) repository).initialize();

        POSApp posApp = new POSApp();
        posApp.initialize(args);

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