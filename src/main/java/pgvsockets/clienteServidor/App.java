package pgvsockets.clienteServidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class App 
{
    public static void main(String[] args)
    {
        try {
            ServerSocket socketServidor = new ServerSocket(5555);
            System.out.println("Servidor escuchando en el puerto 5555...");

            Socket socketCliente = socketServidor.accept();
            System.out.println("Cliente conectado");

            PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socketCliente.getInputStream())
            );

            // Leer mensaje del cliente
            String mensaje = entrada.readLine();
            System.out.println("Cliente dice: " + mensaje);

            // Responder
            salida.println("Hola cliente");

            socketCliente.close();
            socketServidor.close();

        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
