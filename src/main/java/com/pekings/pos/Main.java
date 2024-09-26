package com.pekings.pos;

import com.pekings.pos.object.MenuItem;
import com.pekings.pos.storage.PersistentRepository;
import com.pekings.pos.storage.Repository;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Repository repository;

    public static void main(String[] args) throws Exception {
        repository = new PersistentRepository();
        ((PersistentRepository) repository).initialize();

        Repository repository = new PersistentRepository();
        List<MenuItem> menuItems = repository.getMenuItems();
        for (MenuItem menuItem : menuItems) {
            System.out.println(menuItem.getName());
        }

        POSApp posApp = new POSApp();
        posApp.initialize();
    }

    public Repository getRepository() {
        return repository;
    }
}