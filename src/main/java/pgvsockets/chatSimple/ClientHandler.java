package pgvsockets.chatSimple;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Gestiona la comunicación con un cliente dentro del servidor de chat.
 * <p>
 * Cada cliente se atiende en un hilo independiente. Esta clase recibe los
 * mensajes del cliente y los reenvía al resto mediante el método
 * {@link ChatServer#broadcast(String, ClientHandler)}.
 * </p>
 */
public class ClientHandler extends Thread {

    private Socket socket;
    private PrintWriter salida;

    /**
     * Constructor que recibe el socket asociado al cliente.
     *
     * @param socket Socket conectado al cliente.
     */
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * Método principal del hilo. Escucha mensajes del cliente y los reenvía
     * al resto de clientes conectados. Si el cliente se desconecta, el hilo
     * finaliza y el socket se cierra.
     */
    @Override
    public void run() {
        try (
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            )
        ) {
            salida = new PrintWriter(socket.getOutputStream(), true);

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("Mensaje recibido: " + mensaje);

                // Reenviar mensaje a todos los demás clientes
                ChatServer.broadcast(mensaje, this);
            }

        } catch (Exception e) {
            System.out.println("Cliente desconectado");
        } finally {
            try {
                socket.close();
            } catch (Exception ignored) {}
        }
    }

    /**
     * Envía un mensaje al cliente asociado a este manejador.
     *
     * @param mensaje Texto que se enviará al cliente.
     */
    public void enviarMensaje(String mensaje) {
        salida.println(mensaje);
    }
}
