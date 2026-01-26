package pgvsockets.appRedSegura;

import javax.net.ssl.*;
import java.io.*;

public class SSLCliente {

    public static void main(String[] args) {
        try {
            // TrustManager permisivo (solo para pruebas)
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
            }}, new java.security.SecureRandom());

            // Crear socket SSL
            SSLSocketFactory sf = sc.getSocketFactory();
            SSLSocket socket = (SSLSocket) sf.createSocket("localhost", 6000);

            System.out.println("Conectado al servidor SSL");

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String mensaje;

            // Bucle interactivo
            while (true) {
                System.out.print("Tú: ");
                mensaje = teclado.readLine();

                salida.println(mensaje);

                if (mensaje.equalsIgnoreCase("salir")) {
                    System.out.println("Cerrando conexión...");
                    break;
                }

                String respuesta = entrada.readLine();
                System.out.println("Servidor: " + respuesta);
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
