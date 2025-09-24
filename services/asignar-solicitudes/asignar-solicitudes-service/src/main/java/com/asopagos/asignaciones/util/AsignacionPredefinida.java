package com.asopagos.asignaciones.util;

import javax.persistence.EntityManager;
import com.asopagos.entidades.ccf.general.ParametrizacionMetodoAsignacion;
import com.asopagos.usuarios.clients.EsMiembroGrupo;


/**
 * <b>Descripción:</b> Representa el metodo de asignacion predefinido
 * <b>Historia de Usuario:</b> HU-TRA-084 Administrar asignación de solicitudes
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
public class AsignacionPredefinida implements MetodoAsignacion {

    @Override
    public String ejecutar(ParametrizacionMetodoAsignacion parametrizacionMetodoAsignacion, Long sede, EntityManager entityManager)
            throws Exception {
        EsMiembroGrupo esMiembroGrupo = new EsMiembroGrupo(parametrizacionMetodoAsignacion.getGrupo(),
                parametrizacionMetodoAsignacion.getUsuario());
        esMiembroGrupo.execute();
        Boolean respuesta = esMiembroGrupo.getResult();
        if (respuesta) {
            return parametrizacionMetodoAsignacion.getUsuario();
        }
        return null;
    }

}
