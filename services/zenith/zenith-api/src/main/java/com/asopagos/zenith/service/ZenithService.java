package com.asopagos.zenith.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.zenith.dto.DatosPostulanteDTO;
import com.asopagos.zenith.dto.DatosSubsidioDTO;
/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la integración Zenith - Genesys
 * 
 * @author Steven Quintero <squintero@heinsohn.com.co>
 */
@Path("externalAPI/zenith")
@Consumes({ "application/json; charset=UTF-8"})
@Produces({ "application/json; charset=UTF-8"})
public interface ZenithService {

	/**
	 * consulta, genera y publica el archivo en el FTP de zenith
	 * para los datos suministrados
	 * 
	 * @param nombreArchivo
	 * 			nombre del archivo de entrada que se buscará en el FTP.
	 * 
	 * @param rutaUbicacion
	 * 			ruta en la que está ubicada el archivo.
	 * 
	 * @param fechaDisponible
	 * 			fecha en la que se dispuso el archivo en el FTP.
	 */
	@GET
	@Path("/generarArchivoAfiliadosZenith")
	public void generarArchivoAfiliadosZenith(@QueryParam("nombreArchivo") String nombreArchivo, 
			@QueryParam("rutaUbicacion") String rutaUbicacion, 
			@QueryParam("fechaDisponible") Date fechaDisponible);
	
	/**
	 * Consulta la información del postulante y su grupo familiar
	 * 
	 * @param tipoIdentificacionCotizante
	 * 			tipo de identificación del postulante.
	 * 
	 * @param numeroIdentificacionCotizante
	 * 			número de identificación del postulante.
	 * 
	 * @return DatosPostulanteDTO con los datos del postulante y su grupo familiar.
	 */
	@GET
	@Path("/solicitarDatosPostulanteZenith")
	public DatosPostulanteDTO solicitarDatosPostulanteZenith(@QueryParam("tipoIdCotizante") TipoIdentificacionEnum tipoIdentificacionCotizante,
			@QueryParam("numeroIdCotizante") String numeroIdentificacionCotizante);

	/**
	 * Consulta los datos de los subsidios activos
	 * 
	 * @param tipoIdCotizante
	 * 			tipo deidenficiación del cotizante.
	 * 
	 * @param numeroIdCotizante
	 * 			número de identificación del cotizante.
	 * 
	 * @return List<DatosSubsidioDTO> con la información obtenida.
	 */
	@GET
	@Path("/solicitarDatosSubsidioZenith")
	public List<DatosSubsidioDTO> solicitarDatosSubsidioZenith(@QueryParam("tipoIdCotizante") TipoIdentificacionEnum tipoIdCotizante,
			@QueryParam("numeroIdCotizante") String numeroIdCotizante
			);

}
