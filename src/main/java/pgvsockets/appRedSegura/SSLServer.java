package pgvsockets.appRedSegura;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SSLServer {

    public static void main(String[] args) {
        try {
            // Cargar el keystore
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("servidor.jks"), "123456".toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, "123456".toCharArray());

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(kmf.getKeyManagers(), null, null);

            SSLServerSocketFactory ssf = sc.getServerSocketFactory();
            SSLServerSocket servidor = (SSLServerSocket) ssf.createServerSocket(6000);

            System.out.println("Servidor SSL escuchando en el puerto 6000...");

            SSLSocket cliente = (SSLSocket) servidor.accept();
            System.out.println("Cliente conectado de forma segura");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

            String mensaje = entrada.readLine();
            System.out.println("Cliente dijo: " + mensaje);

            salida.println("Mensaje recibido de forma segura: " + mensaje);

            cliente.close();
            servidor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
