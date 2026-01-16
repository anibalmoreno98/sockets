package pgvsockets.chatSimple;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;
    private PrintWriter salida;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

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

                // Enviar a todos menos al remitente
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

    public void enviarMensaje(String mensaje) {
        salida.println(mensaje);
    }
}
