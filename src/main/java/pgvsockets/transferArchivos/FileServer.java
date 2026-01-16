package pgvsockets.transferArchivos;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000)) {
            System.out.println("Servidor de archivos escuchando en el puerto 5000...");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Cliente conectado. Recibiendo archivo...");

                InputStream entrada = socket.getInputStream();

                // Nombre fijo o puedes generar uno dinámico
                String nombreArchivo = "archivo_recibido";

                // Guardar archivo en la carpeta del servidor
                FileOutputStream fos = new FileOutputStream("recibidos/" + nombreArchivo);

                byte[] buffer = new byte[4096];
                int bytesLeidos;

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
