package pgvsockets.chatSimple;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Cliente para el servidor de chat simple.
 * <p>
 * Se conecta al servidor, envía mensajes escritos por el usuario y muestra
 * los mensajes recibidos del resto de clientes en tiempo real.
 * </p>
 */
public class ChatCliente {

    /**
     * Método principal del cliente. Establece conexión con el servidor,
     * lanza un hilo para escuchar mensajes entrantes y permite al usuario
     * enviar mensajes desde consola.
     *
     * @param args No se utilizan argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Conectado al chat");

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in)); // crea lector para leer por teclado
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true); // crea un escritor que envia texto al servidor a traves del socket
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream())); // crea un lector que escucha mensajes que el servidor envia

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
