package com.pekings.pos;

import com.pekings.pos.storage.PersistentRepository;
import com.pekings.pos.storage.Repository;

public class Main {

    private static Repository repository;

    public static void main(String[] args) throws Exception {
        POSApp posApp = new POSApp();
        posApp.initialize();

        repository = new PersistentRepository();
        ((PersistentRepository) repository).initialize();
    }

    public Repository getRepository() {
        return repository;
    }
}