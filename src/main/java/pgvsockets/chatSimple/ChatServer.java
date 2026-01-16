package pgvsockets.chatSimple;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    // Lista de clientes conectados
    public static List<ClientHandler> clientes = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(1234)) {
            System.out.println("Servidor de chat escuchando en el puerto 1234...");

            while (true) {
                Socket socketCliente = server.accept();
                System.out.println("Nuevo cliente conectado");

                ClientHandler handler = new ClientHandler(socketCliente);
                clientes.add(handler);
                handler.start(); // Lanzar hilo
            }

        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }

    // Método para enviar un mensaje a todos los clientes
    public static void broadcast(String mensaje, ClientHandler remitente) {
        for (ClientHandler cliente : clientes) {
            if (cliente != remitente) {
                cliente.enviarMensaje(mensaje);
            }
        }
    }
}
