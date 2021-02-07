package Multihilo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

    public static void main(String args[]) throws IOException {

        Scanner scanner = new Scanner(System.in);
        String mensaje;
        boolean cambio=true;
        boolean cambioYcorto=false;

        //SERVIDOR
        ServerSocket servidor;
        servidor = new ServerSocket(6000);
        System.out.println("Servidor iniciado... " +
                "Recuerda, para que el otro usuario pueda escribir tienes que usar la palabra CAMBIO en mayúscula," +
                "para cortar la comunicación CAMBIO Y CORTO en mayúscula," +
                "Buena suerte :D");

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
                    System.out.println("Has realizado el cambio ahora te toca escribir");
                    cambio=false;
                }

            }

        }

        // CERRAR STREAMS Y SOCKETS
        flujoEntrada.close();
        flujoSalida.close();
        cliente.close();

    }// Fin de main


}// Fin de Servidor