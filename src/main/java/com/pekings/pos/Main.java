package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import com.pekings.pos.storage.PersistentRepository;
import com.pekings.pos.storage.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {

    private static Repository repository;

    public static void main(String[] args) throws Exception {
        repository = new PersistentRepository();
        ((PersistentRepository) repository).initialize();

        long now = System.currentTimeMillis();
        Set<MenuItem> menuItems = repository.getMenuItems();
        long after = System.currentTimeMillis();

        System.out.println("Fetched! Took " + (after - now) + "ms");

        POSApp posApp = new POSApp();
        posApp.initialize();
    }

    public Repository getRepository() {
        return repository;
    }
}