package com.asopagos.cartera.composite.service.factories;

import java.util.List;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Clase que representa los atributos y acciones específicos
 * relacionados a la asignación de solicitudes de la acción de cobro 2E <br/>
 * <b>Módulo:</b> Asopagos - HU-164 <br/>
 *
 * @author <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */
public class AsignacionSolicitudGestionCobro2E extends AsignacionSolicitudGestionCobro {

    /**
     * Constructor
     * 
     * @param userDTO
     *        Información del usuario que realiza la asignación
     */
    public AsignacionSolicitudGestionCobro2E(UserDTO userDTO) {
        super(userDTO);
        this.setAccionCobro(TipoAccionCobroEnum.E2);
        this.setTipoTransaccionFisica(TipoTransaccionEnum.ACCION_COBRO_2E);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.cartera.composite.service.factories.AsignacionSolicitudGestionCobro#asignarComunicados()
     */
    @Override
    public void asignarComunicados() {
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.cartera.composite.service.factories.AsignacionSolicitudGestionCobro#consultarParametrizacion(java.util.List)
     */
    @Override
    public void consultarParametrizacion(List<Object> lista) {
    }
}
