package pgvsockets.clienteServidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor simple que escucha en el puerto 5555 y atiende a un único cliente.
 * <p>
 * El servidor acepta una conexión entrante, recibe un mensaje del cliente
 * y responde con un saludo fijo. Después cierra la conexión.
 * </p>
 */
public class App {

    /**
     * Método principal del servidor. Crea un {@link ServerSocket} en el puerto 5555,
     * espera la conexión de un cliente y gestiona el intercambio básico de mensajes.
     *
     * @param args No se utilizan argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try {
            ServerSocket socketServidor = new ServerSocket(5555);        // java pide al SO abrir una estructura interna que representa un socket TCP
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

            // Responder al cliente
            salida.println("Hola cliente");

            socketCliente.close();
            socketServidor.close();

        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
