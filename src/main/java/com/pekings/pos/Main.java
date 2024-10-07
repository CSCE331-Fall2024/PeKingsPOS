package com.pekings.pos;

import com.pekings.pos.storage.PersistentRepository;
import com.pekings.pos.storage.Repository;


import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class Main {

    private static Repository repository;

    public static void main(String[] args) throws SQLException, IOException, URISyntaxException {
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