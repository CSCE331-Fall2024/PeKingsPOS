package com.pekings.pos;

import com.pekings.pos.storage.PersistentRepository;
import com.pekings.pos.storage.Repository;

import java.sql.SQLException;

public class Main {

    private static Repository repository;

    //Explain the runtime portion: Fabio
    /**
     * Initializes the database and calls the functions to open and launch the window.
     *
     * @param args arguments to give to the main on startup
     */
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

    /**
     * returns the repository when called
     */
    public static Repository getRepository() {
        return repository;
    }
}