package pgvsockets.appRedSegura;

import javax.net.ssl.*;
import java.io.*;

public class SSLCliente {

    public static void main(String[] args) {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
            }}, new java.security.SecureRandom());

            SSLSocketFactory sf = sc.getSocketFactory();
            SSLSocket socket = (SSLSocket) sf.createSocket("localhost", 6000);

            System.out.println("Conectado al servidor SSL");

            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            salida.println("Hola servidor seguro");
            String respuesta = entrada.readLine();

            System.out.println("Servidor respondió: " + respuesta);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
