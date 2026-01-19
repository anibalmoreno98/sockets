package pgvsockets.echoServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Cliente para el servidor Echo. Permite enviar mensajes al servidor y recibir respuestas.
 * Finaliza cuando el usuario escribe "salir".
 */
public class EchoCliente {

    /**
     * Método principal del cliente. Establece conexión con el servidor Echo,
     * envía mensajes desde consola y muestra las respuestas.
     *
     * @param args No se utilizan argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 1234);
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Conectado al servidor Echo");
            String mensaje;

            while (true) {
                System.out.print("Escribe mensaje: ");
                mensaje = teclado.readLine();

                if (mensaje.equalsIgnoreCase("salir")) break;

                salida.println(mensaje);
                String respuesta = entrada.readLine();
                System.out.println("Servidor respondió: " + respuesta);
            }

        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
