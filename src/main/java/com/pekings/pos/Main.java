package com.pekings.pos;

import com.pekings.pos.storage.PersistentRepository;
import com.pekings.pos.storage.Repository;

public class Main {

    private static Repository repository;

    public static void main(String[] args) throws Exception {
        repository = new PersistentRepository();
        ((PersistentRepository) repository).initialize();

        long startTime = System.currentTimeMillis();

        System.out.println(repository.getMenuItem(5).getName());;

        System.out.println("Fetched! Took " + (System.currentTimeMillis() - startTime) + "ms");

        //POSApp posApp = new POSApp();
        //posApp.initialize();
    }

    public Repository getRepository() {
        return repository;
    }
}