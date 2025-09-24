package com.asopagos.usuarios.ejb;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserSessionRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.asopagos.constants.ParametrosSistemaConstants;
import com.asopagos.cache.CacheManager;
import com.asopagos.util.CalendarUtils;

public class MonitoreoSesionUsuario {

    private static final Logger logger = LoggerFactory.getLogger(MonitoreoSesionUsuario.class);
    private final Keycloak keycloak;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // Tiempo máximo de inactividad en milisegundos
    private static final long TIEMPO_INACTIVIDAD_MILIS = obtenerTiempoInactividad(
            (String) CacheManager.getParametro(ParametrosSistemaConstants.TIEMPO_INICIO_SESION_USUARIO_KEYCLOAK));

    public MonitoreoSesionUsuario(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    /**
     * Método para extraer el tiempo de inactividad en milisegundos desde el parámetro.
     */
    private static long obtenerTiempoInactividad(String parametro) {
        try {
            // Remover cualquier carácter no numérico del parámetro
            String tiempo = parametro.replaceAll("[^0-9]", "");
            long minutos = Integer.parseInt(tiempo); // Convertir a entero
            return minutos * 60 * 1000; // Convertir minutos a milisegundos
        } catch (NumberFormatException e) {
            logger.error("Error al obtener el tiempo de inactividad del parámetro: " + parametro, e);
            // Valor predeterminado en milisegundos en caso de error (ej. 15 minutos)
            return 15 * 60 * 1000; // 15 minutos en milisegundos
        }
    }

    /**
     * Monitorea la sesión del usuario y la cierra si excede el tiempo de inactividad permitido.
     */
    public void iniciarMonitoreoSesion(String userId, String realm) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // Obtener la última sesión activa del usuario
                List<UserSessionRepresentation> sesiones = keycloak.realm(realm)
                        .users().get(userId).getUserSessions();

                if (!sesiones.isEmpty()) {
                    UserSessionRepresentation sesion = sesiones.get(0); // Primera sesión
                    long ultimaActividad = sesion.getLastAccess(); // Tiempo de la última actividad en milisegundos
                    long ahora = System.currentTimeMillis(); // Tiempo actual en milisegundos

                    // Calcular la diferencia en minutos entre ahora y la última acción
                    long minutosInactividad = (ahora - ultimaActividad) / (60 * 1000);

                    // Registro para depuración
                    // logger.info("Minutos de inactividad del usuario " + userId + ": " + minutosInactividad);
                    // logger.info("Tiempo máximo de inactividad permitido: " + (TIEMPO_INACTIVIDAD_MILIS / (60 * 1000)) + " minutos");

                    // Si el tiempo de inactividad supera el límite, cerrar la sesión del usuario
                    if (minutosInactividad >= (TIEMPO_INACTIVIDAD_MILIS / (60 * 1000))) {
                        cerrarSesionUsuario(userId, realm);
                    }
                }
            } catch (Exception e) {
                logger.error("Error al monitorear la sesión del usuario: " + userId, e);
            }
        }, 0, 1, TimeUnit.MINUTES); // Revisión cada minuto
    }

    /**
     * Cierra la sesión del usuario en Keycloak.
     */
    private void cerrarSesionUsuario(String userId, String realm) {
        try {
            keycloak.realm(realm).users().get(userId).logout();
            logger.info("Sesión del usuario " + userId + " ha sido cerrada por inactividad.");
        } catch (Exception e) {
            logger.error("Error al cerrar la sesión del usuario: " + userId, e);
        }
    }

    /**
     * Detiene el monitoreo de sesiones.
     */
    public void detenerMonitoreo() {
        scheduler.shutdown();
        logger.info("Monitoreo de sesiones detenido.");
    }
}
