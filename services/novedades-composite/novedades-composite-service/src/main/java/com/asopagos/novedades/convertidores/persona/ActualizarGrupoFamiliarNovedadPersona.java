package com.asopagos.novedades.convertidores.persona;
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ActualizarGrupoFamiliarPersona;
import com.asopagos.afiliados.clients.ConsultarDatosGrupoFamiliar;
import com.asopagos.dto.modelo.GrupoFamiliarModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar los datos de Grupo Familiar
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarGrupoFamiliarNovedadPersona implements NovedadCore{

	List<TipoTransaccionEnum> cambioCorrespondenciaGrupoFamiliar;
	List<TipoTransaccionEnum> activacionInactivacionGrupoFamiliarInembargable;
	List<TipoTransaccionEnum> actualizaSectorUbicacion;
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		
		/*se transforma a un objeto de datos del empleador*/
		DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
		
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();

		/*Asocia los tipos de Novedad de Persona.*/
		this.llenarListaNovedades();
		ConsultarDatosGrupoFamiliar consultarGrupoFamiliar = new ConsultarDatosGrupoFamiliar(datosPersona.getIdGrupoFamiliar());
		consultarGrupoFamiliar.execute();
		GrupoFamiliarModeloDTO grupoFamiliarModeloDTO = consultarGrupoFamiliar.getResult();
		
		if (cambioCorrespondenciaGrupoFamiliar.contains(novedad)){
			UbicacionModeloDTO ubicacionDTO = new UbicacionModeloDTO();
			if (grupoFamiliarModeloDTO.getUbicacionGrupoFamiliar() != null && grupoFamiliarModeloDTO.getUbicacionGrupoFamiliar().getIdUbicacion() != null) {
				ubicacionDTO = grupoFamiliarModeloDTO.getUbicacionGrupoFamiliar();
			} else {
				grupoFamiliarModeloDTO.setUbicacionGrupoFamiliar(ubicacionDTO);
			}
			/*Se actualizan los datos de correspondencia asociados al grupo familiar*/
			this.actualizarCorrespondenciaGrupoFamiliar(ubicacionDTO, datosPersona);
			
		} else if (activacionInactivacionGrupoFamiliarInembargable.contains(novedad)) {
			grupoFamiliarModeloDTO.setInembargable(datosPersona.getGrupoFamiliarInembargable());
		} else if (actualizaSectorUbicacion.contains(novedad)) {
		    UbicacionModeloDTO ubicacionDTO = new UbicacionModeloDTO();
		    if (grupoFamiliarModeloDTO.getUbicacionGrupoFamiliar() != null && 
		            grupoFamiliarModeloDTO.getUbicacionGrupoFamiliar().getIdUbicacion() != null) {
		        grupoFamiliarModeloDTO.getUbicacionGrupoFamiliar().setSectorUbicacion(datosPersona.getSectorUbicacion());
		    } else {
		        ubicacionDTO.setSectorUbicacion(datosPersona.getSectorUbicacion());
		        grupoFamiliarModeloDTO.setUbicacionGrupoFamiliar(ubicacionDTO);
		    }
		}
		ActualizarGrupoFamiliarPersona actualizarGrupoFamiliar = new ActualizarGrupoFamiliarPersona(grupoFamiliarModeloDTO);
		return actualizarGrupoFamiliar;
	}

	/**
	 * Actualiza los datos de Correspondencia asociados al GrupoFamiliar - Novedad 117
	 * @param ubicacion
	 * @param datosPersona
	 */
	private void actualizarCorrespondenciaGrupoFamiliar(UbicacionModeloDTO ubicacionDTO, DatosPersonaNovedadDTO datosPersona){
		if (datosPersona.getMunicipioResidenciaGrupoFam() != null) {
			ubicacionDTO.setIdMunicipio(datosPersona.getMunicipioResidenciaGrupoFam().getIdMunicipio());
		}
		ubicacionDTO.setDireccionFisica(datosPersona.getDireccionResidenciaGrupoFam());
        ubicacionDTO.setDescripcionIndicacion(datosPersona.getDescripcionIndicacionGrupoFam());
		ubicacionDTO.setCodigoPostal(datosPersona.getCodigoPostalGrupoFam());
		if(datosPersona.getIndicativoTelFijoGrupoFam() != null){
			ubicacionDTO.setIndicativoTelFijo(datosPersona.getIndicativoTelFijoGrupoFam().toString());
		}
		
		ubicacionDTO.setTelefonoFijo(datosPersona.getTelefonoFijoGrupoFam());
		ubicacionDTO.setTelefonoCelular(datosPersona.getTelCelularGrupoFam());
		ubicacionDTO.setEmail(datosPersona.getEmailGrupoFam());
	}
	
	/**
	 * Asocia las novedades por cada tipo
	 * 
	 */
	private void llenarListaNovedades() {
		/*Novedad 117, 262*/
		cambioCorrespondenciaGrupoFamiliar = new ArrayList<>();
		cambioCorrespondenciaGrupoFamiliar.add(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS);
		cambioCorrespondenciaGrupoFamiliar.add(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_DEPWEB);
		cambioCorrespondenciaGrupoFamiliar.add(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_WEB);
		cambioCorrespondenciaGrupoFamiliar.add(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_PRESENCIAL);
		cambioCorrespondenciaGrupoFamiliar.add(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_DEPWEB);
		cambioCorrespondenciaGrupoFamiliar.add(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_WEB);
		
		/*Novedad 278 back*/
		activacionInactivacionGrupoFamiliarInembargable = new ArrayList<>();
		activacionInactivacionGrupoFamiliarInembargable.add(TipoTransaccionEnum.DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE_GRUPO_FAMILIAR);
		
		/*Novedad 470*/
		actualizaSectorUbicacion = new ArrayList<>();
		actualizaSectorUbicacion.add(TipoTransaccionEnum.ACTUALIZACION_SECTOR_UBICACION_GRUPOFAMILIAR_DEPWEB);
		actualizaSectorUbicacion.add(TipoTransaccionEnum.ACTUALIZACION_SECTOR_UBICACION_GRUPOFAMILIAR);
		actualizaSectorUbicacion.add(TipoTransaccionEnum.ACTUALIZACION_SECTOR_UBICACION_GRUPOFAMILIAR_WEB);
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }
}

