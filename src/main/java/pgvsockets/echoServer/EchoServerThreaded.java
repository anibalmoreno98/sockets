package pgvsockets.echoServer;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor Echo multihilo. Escucha en el puerto 1234 y crea un hilo por cada cliente conectado.
 */
public class EchoServerThreaded {

    /**
     * Método principal del servidor. Acepta conexiones entrantes y lanza un hilo
     * ClientHandler para cada cliente.
     *
     * @param args No se utilizan argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(1234)) {            // abres un serversocket
            System.out.println("Servidor Echo MULTIHILO escuchando en el puerto 1234...");

            while (true) {
                Socket cliente = server.accept();                           // bloquea hasta que un cliente se conecta
                System.out.println("Cliente conectado desde " + cliente.getInetAddress());

                // Crear y lanzar un hilo para atender al cliente
                ClientHandler handler = new ClientHandler(cliente);
                handler.start();
            }

        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
