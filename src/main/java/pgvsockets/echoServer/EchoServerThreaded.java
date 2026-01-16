package pgvsockets.echoServer;

import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerThreaded {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(1234)) {
            System.out.println("Servidor Echo MULTIHILO escuchando en el puerto 1234...");

            while (true) {
                Socket cliente = server.accept();
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
