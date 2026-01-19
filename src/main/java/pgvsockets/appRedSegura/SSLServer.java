package pgvsockets.appRedSegura;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

/**
 * Servidor SSL que escucha en el puerto 6000 y acepta conexiones seguras.
 * <p>
 * El servidor carga un keystore en formato JKS que contiene su certificado
 * y clave privada. Una vez establecida la conexión, recibe un mensaje del
 * cliente y responde confirmando la recepción.
 * </p>
 */
public class SSLServer {

    /**
     * Método principal del servidor SSL. Configura el contexto TLS,
     * carga el keystore, crea un {@link SSLServerSocket} y espera
     * conexiones entrantes.
     *
     * @param args No se utilizan argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try {
            // Cargar el keystore con la clave privada del servidor
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("servidor.jks"), "123456".toCharArray());

            // Inicializar KeyManager con la clave del servidor
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, "123456".toCharArray());

            // Crear contexto SSL
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(kmf.getKeyManagers(), null, null);

            // Crear socket SSL seguro
            SSLServerSocketFactory ssf = sc.getServerSocketFactory();
            SSLServerSocket servidor = (SSLServerSocket) ssf.createServerSocket(6000);

            System.out.println("Servidor SSL escuchando en el puerto 6000...");

            // Esperar conexión del cliente
            SSLSocket cliente = (SSLSocket) servidor.accept();
            System.out.println("Cliente conectado de forma segura");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

            // Leer mensaje del cliente
            String mensaje = entrada.readLine();
            System.out.println("Cliente dijo: " + mensaje);

            // Responder al cliente
            salida.println("Mensaje recibido de forma segura: " + mensaje);

            cliente.close();
            servidor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
