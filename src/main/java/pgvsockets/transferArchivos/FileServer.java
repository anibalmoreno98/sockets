package pgvsockets.transferArchivos;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor para recibir archivos enviados por clientes.
 * <p>
 * Escucha en el puerto 5000 y guarda cada archivo recibido en la carpeta
 * "recibidos/" con un nombre fijo o configurable.
 * </p>
 */
public class FileServer {

    /**
     * Método principal del servidor. Acepta conexiones entrantes y recibe
     * archivos enviados por los clientes, guardándolos en disco.
     *
     * @param args No se utilizan argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000)) {
            System.out.println("Servidor de archivos escuchando en el puerto 5000...");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Cliente conectado. Recibiendo archivo...");

                InputStream entrada = socket.getInputStream();

                // Nombre fijo; se puede mejorar generando nombres únicos
                String nombreArchivo = "archivo_recibido";

                // Guardar archivo en la carpeta del servidor
                FileOutputStream fos = new FileOutputStream("recibidos/" + nombreArchivo);

                byte[] buffer = new byte[4096];
                int bytesLeidos;

                // Recepción del archivo en bloques
                while ((bytesLeidos = entrada.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesLeidos);
                }

                fos.close();
                socket.close();

                System.out.println("Archivo recibido y guardado correctamente.");
            }

        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
