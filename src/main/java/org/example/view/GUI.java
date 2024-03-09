package org.example.view;

import org.example.util.AppData;
import org.example.util.Utils;

public class GUI {

    public static String nameMsg = "Introduzca su nombre:";
    public static String passMsg = "Introduzca su contraseña:";
    public static String userMsg = "Introduzca el nombre del receptor:";
    public static String moneyMsg = "Introduzca el importe:";
    public static String disconnectMsg = "Desconectando...";
    public static String successMsg = "Exito en la operacion.";
    public static String errorMsg = "Error en la operacion.";
    public static String welcomeMsg = "Bienvenido, ";

    public static void mainMenuShow(){
        Utils.showMessage("¿Usar el cajero?");
        Utils.showMessage("1. Usar.");
        Utils.showMessage("0. Salir.");
    }

    public static void atmMenuShow() {
        Utils.showMessage("Seleccione la operación a realizar:");
        Utils.showMessage("1. Ingresar dinero.");
        Utils.showMessage("2. Sacar dinero.");
        Utils.showMessage("3. Realizar transferencia.");
        Utils.showMessage("4. Consultar saldo.");
        Utils.showMessage("0. Desconectar.");
    }
}
