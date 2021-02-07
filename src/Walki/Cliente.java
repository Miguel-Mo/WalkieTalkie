package Walki;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    /**
     * En em main le pasamos un syso de bienvenida y el mainmethod()
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Bienvenido Usuario, usted tiene el nombre de Cliente. ");
        mainMethod();
    }// fin de main


    /**
     * Aquí iniciamos algunas variables y llamamos a elegir puerto y a walki
     */
    public static void mainMethod() {
        int Puerto = 0;
        Puerto = elegirPuerto(Puerto);
        walki(Puerto);
    }

    /**
     *  El walki recibe el numero del puerto. Con este metodo decidimos
     * cuando habla y cuando escucha utilizando dos booleans, uno que nos sirve para saver cuando hablar y escuchar y otro
     *  que nos sirve para saber cuando hay que cortar la comunicación. Finalmente cerramos la comunicación una vez salimos del
     * bucle.
     * @param Puerto
     */

    public static void walki(int Puerto) {
        Scanner scanner = new Scanner(System.in);
        String mensaje;
        boolean cambio = false;
        boolean cambioYcorto = false;
        String Host = "localhost";

        try {
            Socket Cliente = new Socket(Host, Puerto);
            System.out.println("PROGRAMA CLIENTE INICIADO...." +
                    "Recuerda, para que el otro usuario pueda escribir tienes que usar la palabra CAMBIO en mayúscula, " +
                    "para cortar la comunicación CAMBIO Y CORTO en mayúscula, " +
                    "Buena suerte :D");
            // CREO FLUJO DE SALIDA AL SERVIDOR
            DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());

            // CREO FLUJO DE ENTRADA AL SERVIDOR
            DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());

            //ESCANEO EL MENSAJE
            System.out.println("Escribe un mensaje:");
            while (!cambioYcorto) {

                while (!cambio) {
                    mensaje = scanner.nextLine();
                    // ENVIO DEL MENSAJE AL SERVIDOR
                    flujoSalida.writeUTF(mensaje);
                    if (mensaje.contains("CAMBIO Y CORTO")) {
                        System.out.println("Has cortado la conexión");
                        cambioYcorto = true;
                    } else if (mensaje.contains("CAMBIO")) {
                        System.out.println("Has realizado el cambio ahora te toca escuchar al Servidor");
                        cambio = true;
                    }

                }

                while (cambio) {
                    // EL SERVIDOR ME ENVIA UN MENSAJE
                    String mensajeRecibe = flujoEntrada.readUTF();
                    System.out.println("Recibiendo del SERVIDOR: \n\t" + mensajeRecibe);
                    if (mensajeRecibe.contains("CAMBIO Y CORTO")) {
                        System.out.println("Se ha cortado la conexión");
                        cambioYcorto = true;
                    } else if (mensajeRecibe.contains("CAMBIO")) {
                        System.out.println("Han realizado el cambio ahora te toca escribir");
                        cambio = false;
                    }
                }
            }
            // CERRAR STREAMS Y SOCKETS
            flujoEntrada.close();
            flujoSalida.close();
            Cliente.close();

        } catch (Exception e) {
            System.out.println("Ese puerto no está disponible");
            mainMethod();
        }
    }

    /**
     * Metodo para elegir puerto en el que le pasamos por parametro el numero introducido. Comprobamos si es un numero
     * si lo es lo asignamos a puerto, si no lo es volvemos a empezar.
     * @param Puerto
     * @return
     * @throws IOException
     */


    public static int elegirPuerto(int Puerto) {
        System.out.println("Dame el numero de puerto");

        Scanner scanner = new Scanner(System.in);
        String puertoConsola = scanner.nextLine();
        boolean puertoAsignado = false;

        while (!puertoAsignado) {
            if (Util.isNumeric(puertoConsola)) {
                Puerto = Integer.parseInt(puertoConsola);
                puertoAsignado = true;
            } else {
                System.out.println("Introduce un numero");
                mainMethod();
            }
        }
        return Puerto;
    }

}