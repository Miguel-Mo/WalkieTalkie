package Walki;

import java.io.IOException;
import java.util.Scanner;

public class Util {

    /**
     * Metodo comprobación de si lo introducido es un numero
     * @param cadena
     * @return
     */
    public static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
