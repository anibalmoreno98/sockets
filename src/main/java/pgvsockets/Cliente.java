package pgvsockets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente 
{
    public static void main(String[] args)
    {
        try {
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Conectado al servidor");

            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            // Enviar mensaje al servidor
            salida.println("Hola servidor");

            // Leer respuesta
            String respuesta = entrada.readLine();
            System.out.println("Servidor dice: " + respuesta);

            socket.close();

        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
