package dtu.controller;

import dtu.database.*;
import dtu.Service.*;

public class ControlReg {

    private static InMemoryUserDatabase userDatabase;
    private static IUserService userService;

    public static InMemoryUserDatabase getUserDatabase() {
        if (userDatabase == null) userDatabase = new InMemoryUserDatabase();
        return userDatabase;
    }

    public static IUserService getUserService() {
        if (userService == null) userService = new UserService();
        return userService;
    }
}
