package pgvsockets.clienteServidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Cliente simple que se conecta al servidor en el puerto 5555.
 * <p>
 * Envía un mensaje inicial al servidor y muestra la respuesta recibida.
 * </p>
 */
public class Cliente {

    /**
     * Método principal del cliente. Establece conexión con el servidor,
     * envía un mensaje y espera la respuesta.
     *
     * @param args No se utilizan argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5555); // crea el socket y se conecta
            System.out.println("Conectado al servidor");

            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true); // crea un flujo de salida envía a través del socket
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream())); // crea un lector para recibir el texto

            // Enviar mensaje al servidor
            salida.println("Hola servidor");

            // Leer respuesta del servidor
            String respuesta = entrada.readLine();
            System.out.println("Servidor dice: " + respuesta);

            socket.close();

        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
