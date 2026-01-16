package pgvsockets.transferArchivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class FileCliente {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Ruta del archivo a enviar: ");
            String ruta = sc.nextLine();

            File archivo = new File(ruta);

            if (!archivo.exists()) {
                System.out.println("El archivo no existe.");
                return;
            }

            Socket socket = new Socket("localhost", 5000);
            System.out.println("Conectado al servidor. Enviando archivo...");

            FileInputStream fis = new FileInputStream(archivo);
            OutputStream salida = socket.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesLeidos;

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
