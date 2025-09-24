package com.asopagos.fovis.composite.ejb;

import java.util.List;

import com.asopagos.afiliados.clients.ConsultarClasificacionesAfiliadoFovis;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.fovis.InformacionSubsidioFOVISDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.fovis.clients.ConsultarInformacionSubsidioFovis;
import com.asopagos.fovis.composite.service.IntegracionFovisService;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.TechnicalException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericType;
import java.util.List;



public class IntegracionFovisBusiness implements IntegracionFovisService{

	@Override
    public InformacionSubsidioFOVISDTO obtenerInfoFovis(String numeroRadicado, TipoIdentificacionEnum tipoIdentificacion,
            String numeroIdentificacion) {
        try {
            ConsultarInformacionSubsidioFovis consultarInformacionSubsidioFovis = new ConsultarInformacionSubsidioFovis(numeroRadicado, numeroIdentificacion, tipoIdentificacion);
            consultarInformacionSubsidioFovis.execute();
            InformacionSubsidioFOVISDTO infoSubsidio = consultarInformacionSubsidioFovis.getResult();
            if (infoSubsidio != null && infoSubsidio.getIdentificacion() != null && infoSubsidio.getTipoId() != null) {
                ConsultarClasificacionesAfiliadoFovis clasificacionesAfiliado = new ConsultarClasificacionesAfiliadoFovis(infoSubsidio.getIdentificacion(), infoSubsidio.getTipoId());
                clasificacionesAfiliado.execute();
                List<ClasificacionEnum> listClasificacion = (List<ClasificacionEnum>) clasificacionesAfiliado.getResult().readEntity(new GenericType<List<ClasificacionEnum>>(){});
                if (listClasificacion != null && !listClasificacion.isEmpty()) {
                    ClasificacionEnum clasificacion = listClasificacion.get(0);
                    infoSubsidio.setTipoAportante((TipoAfiliadoEnum) clasificacion.getSujetoTramite());
                }
            }
            return infoSubsidio;
        } catch (ParametroInvalidoExcepcion pe) {
            throw pe;
        } catch (Exception e) {
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
}