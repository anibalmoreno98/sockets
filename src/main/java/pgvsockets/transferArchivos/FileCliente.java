package pgvsockets.transferArchivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Cliente para transferencia de archivos.
 * <p>
 * Solicita al usuario la ruta de un archivo local y lo envía al servidor
 * mediante un socket TCP en el puerto 5000.
 * </p>
 */
public class FileCliente {

    /**
     * Método principal del cliente. Lee la ruta del archivo desde consola,
     * verifica su existencia y lo envía al servidor en bloques de bytes.
     *
     * @param args No se utilizan argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Ruta del archivo a enviar: ");             // enviar: servidor.jks
            String ruta = sc.nextLine();

            File archivo = new File(ruta);

            // Validación del archivo
            if (!archivo.exists()) {
                System.out.println("El archivo no existe.");
                return;
            }

            // Conexión con el servidor
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Conectado al servidor. Enviando archivo...");

            FileInputStream fis = new FileInputStream(archivo);
            OutputStream salida = socket.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesLeidos;

            // Envío del archivo en bloques
            while ((bytesLeidos = fis.read(buffer)) != -1) {
                salida.write(buffer, 0, bytesLeidos);
            }

            fis.close();
            socket.close();

            System.out.println("Archivo enviado correctamente.");

        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
