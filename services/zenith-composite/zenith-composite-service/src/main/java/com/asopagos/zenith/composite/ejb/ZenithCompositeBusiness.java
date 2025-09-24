package com.asopagos.zenith.composite.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.core.Response;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.zenith.clients.GenerarArchivoAfiliadosZenith;
import com.asopagos.zenith.clients.SolicitarDatosPostulanteZenith;
import com.asopagos.zenith.clients.SolicitarDatosSubsidioZenith;
import com.asopagos.zenith.composite.service.ZenithCompositeService;
import com.asopagos.zenith.dto.DatosPostulanteDTO;
import com.asopagos.zenith.dto.DatosSubsidioDTO;

@Stateless
public class ZenithCompositeBusiness implements ZenithCompositeService{

	/* (non-Javadoc)
	 * @see com.asopagos.zenith.composite.service.ZenithCompositeService#consultarArchivoMaestroTrabajadoresActivos()
	 */
	@Override
	public Response consultarArchivoMaestroTrabajadoresActivos() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.asopagos.zenith.composite.service.ZenithCompositeService#consultarTrabajadoresActivos(java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void consultarTrabajadoresActivos(String nombreArchivo, String rutaUbicacion, Date fechaDisponible) {
		GenerarArchivoAfiliadosZenith generarArchivoAfiliadosZenith = new GenerarArchivoAfiliadosZenith(fechaDisponible, nombreArchivo, rutaUbicacion);
		generarArchivoAfiliadosZenith.execute();
	}

	/* (non-Javadoc)
	 * @see com.asopagos.zenith.composite.service.ZenithCompositeService#solicitarDatosPostulante(java.lang.String, java.lang.String)
	 */
	@Override
	public DatosPostulanteDTO solicitarDatosPostulante(String tipoIdentificacionCotizante, String numeroIdentificacionCotizante) {

		TipoIdentificacionEnum tipoIdCotizante = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoIdentificacionCotizante);
		if(tipoIdCotizante != null){
			SolicitarDatosPostulanteZenith solicitarDatosPostulanteZenith = new SolicitarDatosPostulanteZenith(numeroIdentificacionCotizante, tipoIdCotizante);
			solicitarDatosPostulanteZenith.execute();
			return solicitarDatosPostulanteZenith.getResult();
		}
		else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.asopagos.zenith.composite.service.ZenithCompositeService#solicitarDatosSubsidio(java.lang.String, java.lang.String)
	 */
	@Override
	public List<DatosSubsidioDTO> solicitarDatosSubsidio(String tipoIdentificacionCotizante, String numeroIdentificacionCotizante) {

		TipoIdentificacionEnum tipoIdCotizante = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoIdentificacionCotizante);
		if(tipoIdCotizante != null){
			SolicitarDatosSubsidioZenith solicitarDatosSubsidioZenith = new SolicitarDatosSubsidioZenith(numeroIdentificacionCotizante, tipoIdCotizante);
			solicitarDatosSubsidioZenith.execute();
			return solicitarDatosSubsidioZenith.getResult();
		}
		else{
			return null;
		}
	}
}
