package com.ragnar.main.Util.Seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeederMain implements CommandLineRunner {
    @Autowired
    private RoleSeeder roleSeeder;

    @Autowired
    private SuperadminSeeder saSeeder;

    @Autowired
    private AuthorSeeder authorSeeder;

    @Autowired
    private BookSeeder bookSeeder;

    @Override
    public void run(String... args) throws Exception {
        roleSeeder.SeedAllRoles();
        saSeeder.SeedSuperAdmin();
        authorSeeder.SeedAuthors();
        bookSeeder.SeedBooks();
    }
}
