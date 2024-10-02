package com.pekings.pos;

import com.pekings.pos.storage.PersistentRepository;
import com.pekings.pos.storage.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class Main  {

    private static Repository repository;

    public static void main(String[] args) throws SQLException, IOException, URISyntaxException {
        repository = new PersistentRepository();
        ((PersistentRepository) repository).initialize();

        POSApp obj = new POSApp();
        obj.initialize();
    }

    public static Repository getRepository() {
        return repository;
    }
}