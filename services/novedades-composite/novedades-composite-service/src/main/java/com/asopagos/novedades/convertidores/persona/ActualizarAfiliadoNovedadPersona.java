package com.asopagos.novedades.convertidores.persona;
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ActualizarDatosAfiliado;
import com.asopagos.afiliados.clients.ConsultarDatosAfiliado;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rutine.afiliadosrutines.actualizardatosafiliado.ActualizarDatosAfiliadoRutine;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar los datos de Afiliado.
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarAfiliadoNovedadPersona implements NovedadCore{

	private List<TipoTransaccionEnum> activacionDesactivacionRetencionSubsidio; 
	private List<TipoTransaccionEnum> activacionDesactivacionCesionSubsidio; 
	private List<TipoTransaccionEnum> activacionDesactivacionPignoracion; 
	private List<TipoTransaccionEnum> serviciosSinAfiliacion;
	
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {


		System.out.println("GLPI-45051 transformarServicio-> ActualizarDatosAfiliado");
		ActualizarDatosAfiliado actualizarDatosAfiliado = new ActualizarDatosAfiliado(obtenerObjetoServicio(solicitudNovedadDTO));
		return actualizarDatosAfiliado;
	}
	
   @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO solicitudNovedadDTO, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
       ActualizarDatosAfiliadoRutine a= new ActualizarDatosAfiliadoRutine();
        a.actualizarDatosAfiliado(obtenerObjetoServicio(solicitudNovedadDTO), entityManager);        
    }	
	
	
	private AfiliadoModeloDTO obtenerObjetoServicio(SolicitudNovedadDTO solicitudNovedadDTO){
	    /*se transforma a un objeto de datos del empleador*/
        DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
        
        /*Se Consulta la Persona*/
        ConsultarDatosAfiliado consultarDatosAfiliado = new ConsultarDatosAfiliado(datosPersona.getNumeroIdentificacion(), datosPersona.getTipoIdentificacion());
        consultarDatosAfiliado.execute();
        AfiliadoModeloDTO afiliadoModeloDTO = consultarDatosAfiliado.getResult();
        
        /*Asocia los tipos de Novedad de Persona.*/
        this.llenarListaNovedades();
        
        if (activacionDesactivacionRetencionSubsidio.contains(novedad)) {
            afiliadoModeloDTO.setRetencionSubsidio(datosPersona.getRentencionSubsidioActivaGF());
        } else if (activacionDesactivacionCesionSubsidio.contains(novedad)) {
            afiliadoModeloDTO.setCesionSubsidio(datosPersona.getCesionSubsidio());
        } else if (activacionDesactivacionPignoracion.contains(novedad)) {
            afiliadoModeloDTO.setPignoracionSubsidio(datosPersona.getPignoracionSubsidioTrabajador());
        } else if (serviciosSinAfiliacion.contains(novedad)) {
            afiliadoModeloDTO.setServicioSinAfiliacion(datosPersona.getServiciosSinAfiliacionTrabajador());
            afiliadoModeloDTO.setCausaSinAfiliacion(datosPersona.getCausaInactivacionServiciosSinAfiliarTrabajador());
			if(datosPersona.getServiciosSinAfiliacionTrabajador().equals(Boolean.FALSE)){
				afiliadoModeloDTO.setFechaFinServicioSinAfiliacion(datosPersona.getFechaNovedadActivacionServiciosSinAfiliarTrabajador());
			}
            afiliadoModeloDTO.setFechaInicioServiciosSinAfiliacion(datosPersona.getFechaNovedadActivacionServiciosSinAfiliarTrabajador());
        }
        return afiliadoModeloDTO;
	}

	/**
	 * Asocia las novedades por cada tipo
	 * 
	 */
	private void llenarListaNovedades(){
		
		/*Novedad 257-258 back*/
		activacionDesactivacionRetencionSubsidio = new ArrayList<>();
		activacionDesactivacionRetencionSubsidio.add(TipoTransaccionEnum.SOLICITUD_RETENCION_SUBSIDIO_TRABAJADOR_DEPENDIENTE);
		activacionDesactivacionRetencionSubsidio.add(TipoTransaccionEnum.DESACTIVACION_RETENCION_SUBSIDIO_TRABAJADOR_DEPENDIENTE);
		
		
		/*Novedad 259 - 260 back*/
		activacionDesactivacionCesionSubsidio = new ArrayList<>();
		activacionDesactivacionCesionSubsidio.add(TipoTransaccionEnum.ACTIVACION_CESION_SUBSIDIO_TRABAJADOR_DEPENDIENTE);
		activacionDesactivacionCesionSubsidio.add(TipoTransaccionEnum.DESACTIVACION_CESION_SUBSIDIO_TRABAJADOR_DEPENDIENTE);
		
		
		/*Novedad 261 back*/
		activacionDesactivacionPignoracion = new ArrayList<>();
		activacionDesactivacionPignoracion.add(TipoTransaccionEnum.PIGNORACION_DEL_SUBSIDIO_TRABAJADOR_DEPENDIENTE);
		
		/*Novedad 277 Front*/
		serviciosSinAfiliacion = new ArrayList<>();
		serviciosSinAfiliacion.add(TipoTransaccionEnum.SOLICITUD_SERVICIOS_SIN_AFILIACION_TRABAJADOR_DEPENDIENTE);
	}
  

 
	
}
