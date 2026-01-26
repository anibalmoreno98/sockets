package pgvsockets.appRedSegura;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SSLServer {

    public static void main(String[] args) {
        try {
            // Cargar keystore del servidor
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("servidor.jks"), "123456".toCharArray());

            // Inicializar KeyManager
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, "123456".toCharArray());

            // Crear contexto SSL
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(kmf.getKeyManagers(), null, null);

            // Crear socket seguro
            SSLServerSocketFactory ssf = sc.getServerSocketFactory();
            SSLServerSocket servidor = (SSLServerSocket) ssf.createServerSocket(6000);

            System.out.println("Servidor SSL escuchando en el puerto 6000...");

            // Aceptar cliente
            SSLSocket cliente = (SSLSocket) servidor.accept();
            System.out.println("Cliente conectado de forma segura");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

            String mensaje;

            // Bucle interactivo
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("Cliente dijo: " + mensaje);

                if (mensaje.equalsIgnoreCase("salir")) {
                    salida.println("Conexión cerrada.");
                    break;
                }

                salida.println("Servidor SSL responde: " + mensaje);
            }

            cliente.close();
            servidor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
