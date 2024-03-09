package org.example.controller;

import org.example.model.domain.User;
import org.example.util.AppData;
import org.example.util.Utils;
import org.example.view.GUI;

import java.io.IOException;

public class Controller {
    public Controller() {

    }

    public void start() throws IOException, ClassNotFoundException {
        boolean isConnected = true;

        do {
            GUI.mainMenuShow();
            int choice = Utils.intInput("");
            AppData.getClient().getOut().writeInt(choice);

            switch (choice) {
                case 1:
                    Utils.showMessage(GUI.nameMsg);
                    String name = Utils.stringInput("");
                    AppData.getClient().getOut().writeObject((String)name);
                    AppData.getClient().getOut().flush();
                    if(AppData.getClient().getIn().readBoolean()){
                        Utils.showMessage(GUI.passMsg);
                        String pass = Utils.stringInput("");
                        AppData.getClient().getOut().writeObject((String)pass);
                        AppData.getClient().getOut().flush();
                        if(AppData.getClient().getIn().readBoolean()) {
                            AppData.setLoggedUser((User)AppData.getClient().getIn().readObject());
                            int opt;
                            do{
                                Utils.showMessage(GUI.welcomeMsg+AppData.getLoggedUser().getName());
                                GUI.atmMenuShow();
                                opt = Utils.intInput("");
                                AppData.getClient().getOut().writeInt(opt);
                                AppData.getClient().getOut().flush();
                                switch (opt) {
                                    case 1:
                                        Utils.showMessage(GUI.moneyMsg);
                                        double money = Utils.doubleInput("");
                                        AppData.getClient().getOut().writeDouble(money);
                                        AppData.getClient().getOut().flush();
                                        User user = (User)AppData.getClient().getIn().readObject();
                                        if(user != null) {
                                            AppData.setLoggedUser(user);
                                            Utils.showMessage(GUI.successMsg);
                                        }else{
                                            Utils.showMessage(GUI.errorMsg);
                                        }
                                        break;
                                    case 2:
                                        Utils.showMessage(GUI.moneyMsg);
                                        double money2 = Utils.doubleInput("");
                                        AppData.getClient().getOut().writeDouble(money2);
                                        AppData.getClient().getOut().flush();
                                        User user2 = (User)AppData.getClient().getIn().readObject();
                                        if(user2 != null) {
                                            AppData.setLoggedUser(user2);
                                            Utils.showMessage(GUI.successMsg);
                                        }else{
                                            Utils.showMessage(GUI.errorMsg);
                                        }
                                        break;
                                    case 3:
                                        Utils.showMessage(GUI.userMsg);
                                        String receptorName = Utils.stringInput("");
                                        AppData.getClient().getOut().writeObject((String)receptorName);
                                        AppData.getClient().getOut().flush();
                                        User user3 = (User)AppData.getClient().getIn().readObject();
                                        if(user3 != null) {
                                            Utils.showMessage(GUI.moneyMsg);
                                            double money3 = Utils.doubleInput("");
                                            AppData.getClient().getOut().writeDouble(money3);
                                            AppData.getClient().getOut().flush();
                                            User user4 = (User)AppData.getClient().getIn().readObject();
                                            if(user4 != null) {
                                                AppData.setLoggedUser(user4);
                                                Utils.showMessage(GUI.successMsg);
                                            }else{
                                                Utils.showMessage(GUI.errorMsg);
                                            }
                                        }else{
                                            Utils.showMessage(GUI.errorMsg);
                                        }
                                        break;
                                    case 4:
                                        AppData.setLoggedUser((User)AppData.getClient().getIn().readObject());
                                        Utils.showMessage("Tu saldo actual es de " + AppData.getLoggedUser().getMoney());
                                        break;
                                    case 0:
                                        Utils.showMessage(GUI.disconnectMsg);
                                        break;
                                    default:
                                        break;
                                }
                            } while (opt != 0);
                        }
                    }
                    break;
                case 0:
                    Utils.showMessage(GUI.disconnectMsg);
                    isConnected = false;
                    break;
                default:
                    Utils.showMessage("Introduce una opcion valida.");
                    break;
            }
        }while (isConnected);
    }
}
