package pgvsockets.echoServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("Cliente dijo: " + mensaje);
                salida.println(mensaje); // Echo
            }

        } catch (Exception e) {
            System.out.println("Error con cliente " + socket.getInetAddress() + ": " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Cliente desconectado");
            } catch (Exception e) {
                System.out.println("Error al cerrar socket: " + e.getMessage());
            }
        }
    }
}
