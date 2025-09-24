package co.gov.sed.sace.util;

public class responseUtil {

    public static String obtenerCodigoGlosa(String json) {
        try {
            String clave = "\"codigo\":\"";
            int inicio = json.indexOf(clave) + clave.length();
            int fin = json.indexOf("\"", inicio);
            return json.substring(inicio, fin);
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
    }

    public static String obtenerMensaje(String json) {
        try {
            String mensaje = "\"mensaje\":\"";
            int inicio = json.indexOf(mensaje) + mensaje.length();
            int fin = json.indexOf("\"", inicio);
            return json.substring(inicio, fin);
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }

    }
}
