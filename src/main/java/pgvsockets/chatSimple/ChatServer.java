package pgvsockets.chatSimple;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Servidor de chat simple multicliente.
 * <p>
 * Escucha en el puerto 1234 y crea un hilo {@link ClientHandler} por cada
 * cliente que se conecta. Mantiene una lista de clientes para permitir
 * la difusión de mensajes.
 * </p>
 */
public class ChatServer {

    /** Lista de clientes conectados al servidor. */
    public static List<ClientHandler> clientes = new ArrayList<>();

    /**
     * Método principal del servidor. Acepta conexiones entrantes y crea un
     * hilo para cada cliente conectado.
     *
     * @param args No se utilizan argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(1234)) {                            // ifconfig -- mi compañero pone mi ip("172.30.131.37, 1234")
            System.out.println("Servidor de chat escuchando en el puerto 1234...");        // para conectarse a mi servidor

            while (true) {
                Socket socketCliente = server.accept();
                System.out.println("Nuevo cliente conectado");

                ClientHandler handler = new ClientHandler(socketCliente);
                clientes.add(handler);
                handler.start();
            }

        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }

    /**
     * Envía un mensaje a todos los clientes excepto al remitente.
     *
     * @param mensaje   Texto que se enviará.
     * @param remitente Cliente que envió el mensaje original.
     */
    public static void broadcast(String mensaje, ClientHandler remitente) {
        for (ClientHandler cliente : clientes) {
            if (cliente != remitente) {
                cliente.enviarMensaje(mensaje);
            }
        }
    }
}
