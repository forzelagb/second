package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 30);
        System.out.println("User with name — Ivan added to DB");

        userService.saveUser("Petr", "Petrov", (byte) 25);
        System.out.println("User with name — Petr added to DB");

        userService.saveUser("Anna", "Sidorova", (byte) 28);
        System.out.println("User with name — Anna added to DB");

        userService.saveUser("Maria", "Smirnova", (byte) 22);
        System.out.println("User with name — Maria added to DB");

        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
