package org.example.util;

import org.example.connection.Client;
import org.example.model.domain.User;

public class AppData {
    private static PasswordAuthentication pa = new PasswordAuthentication();
    private static User loggedUser;
    private static Client client = new Client();

    public static PasswordAuthentication getPa() {
        return pa;
    }

    public static void setPa(PasswordAuthentication pa) {
        AppData.pa = pa;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        AppData.loggedUser = loggedUser;
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        AppData.client = client;
    }
}
