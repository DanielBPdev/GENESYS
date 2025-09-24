package com.asopagos.asignaciones.util;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.entidades.ccf.general.ParametrizacionMetodoAsignacion;
import com.asopagos.enumeraciones.usuarios.EstadoUsuarioEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.tareashumanas.clients.ObtenerTareasAsignadasUsuario;
import com.asopagos.tareashumanas.dto.TareaDTO;
import com.asopagos.usuarios.clients.ObtenerMiembrosGrupo;
import com.asopagos.usuarios.dto.UsuarioDTO;
import com.asopagos.util.Interpolator;

/**
 * <b>Descripción:</b> Representa el metodo de asignacion por numero de
 * solicitudes <b>Historia de Usuario:</b> HU-TRA-084 Administrar asignación de
 * solicitudes
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
public class AsignacionNumeroSolicitudes implements MetodoAsignacion {

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(AsignacionNumeroSolicitudes.class);

    @Override
    public String ejecutar(ParametrizacionMetodoAsignacion parametrizacionMetodoAsignacion, Long sede, EntityManager entityManager)
            throws Exception {

        logger.debug(Interpolator.interpolate("Inicia AsignacionNumeroSolicitudes.ejecutar(Long). Método: {0}, Sede: {1}, grupo {3}",
                parametrizacionMetodoAsignacion.getMetodo(), sede, parametrizacionMetodoAsignacion.getGrupo()));

        String funcionario = parametrizacionMetodoAsignacion.getGrupo();
        ObtenerMiembrosGrupo miembrosGrupo = new ObtenerMiembrosGrupo(funcionario,
                parametrizacionMetodoAsignacion.getSedeCajaDestino() != null
                        ? parametrizacionMetodoAsignacion.getSedeCajaDestino().toString() : null,
                EstadoUsuarioEnum.ACTIVO);
        miembrosGrupo.execute();
        List<UsuarioDTO> usuarios = miembrosGrupo.getResult();

        logger.debug("usuarios miembros del grupo a revisar: " + usuarios.toString());

        String nombreMenorAsignacion = null;
        if (usuarios != null && !usuarios.isEmpty()) {
            Integer menor = null;
            for (UsuarioDTO usuarioDTO : usuarios) {
                ObtenerTareasAsignadasUsuario asignadas = new ObtenerTareasAsignadasUsuario(usuarioDTO.getNombreUsuario());
                List<TareaDTO> tareas = new ArrayList<>();
                asignadas.execute();
                tareas = (List<TareaDTO>) asignadas.getResult();
                int valor = 0;
                if (tareas != null && !tareas.isEmpty()) {
                    valor = tareas.size();
                }
                if (menor != null) {
                    if (valor < menor) {
                        menor = valor;
                        nombreMenorAsignacion = usuarioDTO.getNombreUsuario();
                    }
                }
                else {
                    menor = valor;
                    nombreMenorAsignacion = usuarioDTO.getNombreUsuario();
                }
            }
        }
        logger.debug("Usuario para realizar asignación: " + nombreMenorAsignacion);
        return nombreMenorAsignacion;
    }

}
