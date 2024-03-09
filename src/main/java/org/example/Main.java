package org.example;

import org.example.connection.ClientHandler;
import org.example.controller.Controller;
import org.example.model.DAO.UserDAO;
import org.example.model.domain.User;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Controller c = new Controller();
        c.start();
        /*User user1 = new User("Juan","1234",5000);
        User user2 = new User("Javier","1234",5500);
        User user3 = new User("Francisco","1234",6000);
        User user4 = new User("Erich","1234",4000);

        try (UserDAO udao = new UserDAO()) {
            udao.save(user1);
            udao.save(user2);
            udao.save(user3);
            udao.save(user4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }
}