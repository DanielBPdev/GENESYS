package com.asopagos.cartera.composite.service.factories;

import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.security.dto.UserDTO;
import java.util.List;
public class AsignacionSolicitudGestionCobroH2C6  extends AsignacionSolicitudGestionCobro {
    /**
     * Logger
     */
    private final ILogger logger = LogManager.getLogger(AsignacionSolicitudGestionCobroH2C6.class);

    /**
     * Constructor
     *
     * @param userDTO Información del usuario que realiza la asignación
     */
    public AsignacionSolicitudGestionCobroH2C6(UserDTO userDTO) {
        super(userDTO);
        this.setAccionCobro(TipoAccionCobroEnum.H2_C6);
        this.setTipoTransaccionElectronica(TipoTransaccionEnum.ACCION_COBRO_2H_ELECTRONICO);
        this.setTipoTransaccionFisica(TipoTransaccionEnum.ACCION_COBRO_2H_FISICO);
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.factories.AsignacionSolicitudGestionCobro#asignarComunicados()
     */
    @Override
    public void asignarComunicados() {
        logger.debug("Inicia método asignarComunicados");
        this.getPlantillasComunicado().add(EtiquetaPlantillaComunicadoEnum.NOTIFI_MORA_DESAF);
        logger.debug("Finaliza método asignarComunicados");
    }

    /**
     * (non-Javadoc)
     *
     * @see com.asopagos.cartera.composite.service.factories.AsignacionSolicitudGestionCobro#consultarParametrizacion(java.util.List)
     */
    @Override
    public void consultarParametrizacion(List<Object> lista) {
    }
}

