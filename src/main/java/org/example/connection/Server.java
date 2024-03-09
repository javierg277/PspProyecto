package org.example.connection;

import org.example.model.DAO.UserDAO;
import org.example.model.domain.User;
import org.example.util.AppData;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 8080;
    private static final int MAX_CLIENTS = 100;

    static Map<User, ObjectOutputStream> clients = new HashMap<>();
    static ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);

    public static void main(String[] args) {
        System.out.println("Server is running...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                pool.execute(new ClientHandler(serverSocket.accept()));
                System.out.println("New client connected.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static synchronized boolean checkName(String name) {
        try (UserDAO udao = new UserDAO()) {
            if(udao.find(name) == null) {
                System.out.println("Incorrect name.");
                return false;
            }else{
                System.out.println("Correct name.");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    static synchronized boolean checkPass(String name, String pass) {
        try (UserDAO udao = new UserDAO()) {
            User user = udao.find(name);
            if(AppData.getPa().authenticate(pass,user.getPassword())){
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    static synchronized User getUser(String name) {
        try (UserDAO udao = new UserDAO()) {
            return udao.find(name);
        } catch (Exception e) {
            return null;
        }
    }

    static synchronized User addMoney(User user, double money) {
        try(UserDAO udao = new UserDAO()) {
            return udao.updateMoney(user.getId(),user.getMoney()+money);
        } catch (Exception e) {
            return null;
        }
    }

    static synchronized User withdrawMoney(User user, double money) {
        try(UserDAO udao = new UserDAO()) {
            if(user.getMoney()-money < 0){
                return null;
            }
            return udao.updateMoney(user.getId(),user.getMoney()-money);
        } catch (Exception e) {
            return null;
        }
    }

    static synchronized User giveMoney(User user, double money, User receptor) {
        try(UserDAO udao = new UserDAO()) {
            if(user.getMoney()-money < 0){
                return null;
            }
            udao.updateMoney(receptor.getId(), receptor.getMoney()+money);
            return udao.updateMoney(user.getId(),user.getMoney()-money);
        } catch (Exception e) {
            return null;
        }
    }

}
