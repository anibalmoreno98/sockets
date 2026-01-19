package pgvsockets.appRedSegura;

import javax.net.ssl.*;
import java.io.*;

/**
 * Cliente SSL simple que se conecta a un servidor seguro en el puerto 6000.
 * <p>
 * Este cliente utiliza un {@link TrustManager} permisivo que acepta cualquier
 * certificado sin validación. Esto facilita las pruebas locales, pero no debe
 * usarse en entornos reales por motivos de seguridad.
 * </p>
 */
public class SSLCliente {

    /**
     * Método principal del cliente SSL. Establece un contexto TLS,
     * crea un socket seguro y envía un mensaje al servidor.
     *
     * @param args No se utilizan argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try {
            // Crear contexto SSL con TrustManager que acepta cualquier certificado
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

            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar mensaje al servidor
            salida.println("Hola servidor seguro");

            // Leer respuesta
            String respuesta = entrada.readLine();
            System.out.println("Servidor respondió: " + respuesta);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
