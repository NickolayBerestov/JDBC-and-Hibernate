package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class  Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
//        userService.dropUsersTable();
//        userService.saveUser("Николай", "Берестов", (byte) 27);
//        userService.saveUser("Илья", "Берестов", (byte) 39);
//        userService.saveUser("Дмитрий", "Берестов", (byte) 21);
//        userService.saveUser("Алексей", "Берестов", (byte) 25);
//        userService.removeUserById(2);
//        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
    }
}
