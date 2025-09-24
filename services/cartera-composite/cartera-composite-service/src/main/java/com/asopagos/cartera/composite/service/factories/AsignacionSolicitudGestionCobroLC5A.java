package com.asopagos.cartera.composite.service.factories;

import java.util.List;
import java.util.Map;
import com.asopagos.cartera.composite.constants.ConstanteCartera;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoParametrizacionGestionCobroEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripción: </b> Clase que representa los atributos y acciones
 * específicos relacionados a la asignación de solicitudes de la acción de cobro
 * LC5A. Ver <code>AsignacionSolicitudGestionCobroFactory</code> <br/>
 * <b>Historia de Usuario: </b> HU-164
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class AsignacionSolicitudGestionCobroLC5A extends AsignacionSolicitudGestionCobro {

    /**
     * Logger
     */
    private final ILogger logger = LogManager.getLogger(AsignacionSolicitudGestionCobroLC5A.class);

    /**
     * Constructor
     * 
     * @param userDTO
     *        Información del usuario que realiza la asignación
     */
    public AsignacionSolicitudGestionCobroLC5A(UserDTO userDTO) {
        super(userDTO);
        this.setAccionCobro(TipoAccionCobroEnum.LC5A);
        this.setTipoTransaccionElectronica(TipoTransaccionEnum.ACCION_COBRO_LC5A_ELECTRONICO);
        this.setTipoTransaccionFisica(TipoTransaccionEnum.ACCION_COBRO_LC5A_FISICO);
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.cartera.composite.service.factories.AsignacionSolicitudGestionCobro#asignarComunicados()
     */
    @Override
    public void asignarComunicados() {
        logger.debug("Inicia método asignarComunicados");
        this.getPlantillasComunicado().add(EtiquetaPlantillaComunicadoEnum.AVI_INC_PER);
        logger.debug("Finaliza método asignarComunicados");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.cartera.composite.service.factories.AsignacionSolicitudGestionCobro#consultarParametrizacion(java.util.List)
     */
    @Override
    public void consultarParametrizacion(List<Object> lista) {
    }
}
