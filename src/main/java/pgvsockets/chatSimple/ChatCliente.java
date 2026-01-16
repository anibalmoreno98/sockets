package pgvsockets.chatSimple;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatCliente {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Conectado al chat");

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Hilo para escuchar mensajes del servidor
            Thread listener = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {
                        System.out.println("\n> " + mensaje);
                    }
                } catch (Exception ignored) {}
            });

            listener.start();

            // Enviar mensajes al servidor
            String texto;
            while ((texto = teclado.readLine()) != null) {
                salida.println(texto);
            }

        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
