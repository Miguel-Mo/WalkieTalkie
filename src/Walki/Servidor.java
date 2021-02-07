package Walki;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

    /**
     * En em main le pasamos un syso de bienvenida y el mainmethod()
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        System.out.println("Bienvenido Usuario, usted tiene el nombre de Servidor, \n"+
                "Hasta que no ELIJA PUERTO NO PODRÁ COMUNICARSE EL CLIENTE CON USTED");
        mainMethod();
    }// Fin de main

    /**
     * Aquí iniciamos algunas variables y llamamos a elegir puerto y a walki
     * @throws IOException
     */
    public static void mainMethod() throws IOException {
        ServerSocket servidor;
        int Puerto=0;
        Puerto = elegirPuerto(Puerto);
        servidor = new ServerSocket(Puerto);
        walki(servidor);
    }

    /**
     * El walki recibe el servidor que a su vez lleva el numero del puerto. Con este metodo decidimos
     * cuando habla y cuando escucha utilizando dos booleans, uno que nos sirve para saver cuando hablar y escuchar y otro
     * que nos sirve para saber cuando hay que cortar la comunicación. Finalmente cerramos la comunicación una vez salimos del
     * bucle.
     * @param servidor
     * @throws IOException
     */
    public static void walki(ServerSocket servidor) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String mensaje;
        boolean cambio=true;
        boolean cambioYcorto=false;
        System.out.println("Servidor iniciado... " +
                "Recuerda, para que el otro usuario pueda escribir tienes que usar la palabra CAMBIO en mayúscula," +
                "para cortar la comunicación CAMBIO Y CORTO en mayúscula," +
                "Buena suerte :D");

        System.out.println("Te toca escuchar");

        //CLIENTE
        Socket cliente=new Socket();
        cliente= servidor.accept();

        // CREO FLUJO DE SALIDA AL SERVIDOR
        DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());

        // CREO FLUJO DE ENTRADA AL SERVIDOR
        DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());

        while(!cambioYcorto){

            while(!cambio){
                mensaje=scanner.nextLine();
                // ENVIO DEL MENSAJE AL SERVIDOR
                flujoSalida.writeUTF(mensaje);
                if (mensaje.contains("CAMBIO Y CORTO")){
                    System.out.println("Has cortado la conexión");
                    cambioYcorto=true;
                }
                else if(mensaje.contains("CAMBIO")){
                    System.out.println("Has realizado el cambio ahora te toca escuchar al Cliente");
                    cambio=true;
                }

            }

            while(cambio){
                // EL SERVIDOR ME ENVIA UN MENSAJE
                String mensajeRecibe=flujoEntrada.readUTF();
                System.out.println("Recibiendo del CLIENTE: \n\t" + mensajeRecibe);
                if(mensajeRecibe.contains("CAMBIO Y CORTO")){
                    System.out.println("Se ha cortado la conexión");
                    cambioYcorto=true;
                }else if(mensajeRecibe.contains("CAMBIO")){
                    System.out.println("Han realizado el cambio ahora te toca escribir");
                    cambio=false;
                }

            }

        }

        // CERRAR STREAMS Y SOCKETS
        flujoEntrada.close();
        flujoSalida.close();
        cliente.close();


    }

    /**
     * Metodo para elegir puerto en el que le pasamos por parametro el numero introducido. Comprobamos si es un numero
     * si lo es lo asignamos a puerto, si no lo es volvemos a empezar.
     * @param Puerto
     * @return
     * @throws IOException
     */

        public static int elegirPuerto(int Puerto) throws IOException {
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


}// Fin de Servidor