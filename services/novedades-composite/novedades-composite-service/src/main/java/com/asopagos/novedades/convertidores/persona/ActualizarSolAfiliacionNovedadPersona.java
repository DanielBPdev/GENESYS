package com.asopagos.novedades.convertidores.persona;
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliaciones.clients.ConsultarUltimaSolicitudAfiliacion;
import com.asopagos.afiliados.clients.ConsultarRolAfiliado;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.dto.modelo.SolicitudAfiliacionPersonaModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.novedades.composite.clients.EjecutarActualizacionSolicitud;
import com.asopagos.novedades.composite.clients.VerificarPersonaNovedadRegistrarAnalisisFovis;
import com.asopagos.novedades.composite.dto.SolicitudAfiliacionRolDTO;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar los datos de una Solicitud de Afiliación.
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarSolAfiliacionNovedadPersona implements NovedadCore{

	private List<TipoTransaccionEnum> cambioTipoPensionado;
	private List<TipoTransaccionEnum> cambioTipoIndependiente;

	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		
		/*Se transforma a un objeto de datos de la Persona*/
		DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
		
		ConsultarUltimaSolicitudAfiliacion consultarUltimaSolicitudAfiliacion = new ConsultarUltimaSolicitudAfiliacion(datosPersona.getIdRolAfiliado());
		consultarUltimaSolicitudAfiliacion.execute();
		SolicitudAfiliacionPersonaModeloDTO solicitudAfiliacionPersonaModeloDTO = consultarUltimaSolicitudAfiliacion.getResult();
		ConsultarRolAfiliado consultarRolAfiliadoService = new ConsultarRolAfiliado(solicitudAfiliacionPersonaModeloDTO.getIdRolAfiliado());
		consultarRolAfiliadoService.execute();
		RolAfiliadoModeloDTO rolAfiliadoModeloDTO = consultarRolAfiliadoService.getResult();
		/*Asocia los tipos de Novedad de Persona.*/
		this.llenarListaNovedades();
		
		if (cambioTipoPensionado.contains(novedad)) {
			solicitudAfiliacionPersonaModeloDTO.setClasificacion(datosPersona.getClasificacion());
			rolAfiliadoModeloDTO.setValorSalarioMesadaIngresos(datosPersona.getValorMesadaPensional());
		} else if (cambioTipoIndependiente.contains(novedad)) {
			solicitudAfiliacionPersonaModeloDTO.setClasificacion(datosPersona.getClasificacion());
			rolAfiliadoModeloDTO.setPorcentajePagoAportes(datosPersona.getTarifaPagoAportesIndependiente());
		}
		
		// Se verifica si se indico desde validaciones que la persona esta asociada a una postulacion FOVIS
        // Se crea la tarea para revisarla en la HU325-77
        // Se verifica si la persona esta asociada a una postulacion FOVIS
        List<PersonaDTO> listaPersonas = new ArrayList<>();
        PersonaDTO persona = new PersonaDTO();
        persona.setNumeroIdentificacion(rolAfiliadoModeloDTO.getAfiliado().getNumeroIdentificacion());
        persona.setTipoIdentificacion(rolAfiliadoModeloDTO.getAfiliado().getTipoIdentificacion());
        listaPersonas.add(persona);
        // Se crea la tarea para revisarla en la HU325-77
        VerificarPersonaNovedadRegistrarAnalisisFovis verificarPersonaNovedadRegistrarAnalisisFovis = new VerificarPersonaNovedadRegistrarAnalisisFovis(
                solicitudNovedadDTO.getIdSolicitudNovedad(), listaPersonas);
        verificarPersonaNovedadRegistrarAnalisisFovis.execute();
		
		SolicitudAfiliacionRolDTO solicitudRol = new SolicitudAfiliacionRolDTO();
		solicitudRol.setRol(rolAfiliadoModeloDTO);
		solicitudRol.setSolicitudAfiliacion(solicitudAfiliacionPersonaModeloDTO);
		EjecutarActualizacionSolicitud actualizarSolicitudService = new EjecutarActualizacionSolicitud(solicitudRol);
		return actualizarSolicitudService;
	}

	/**
	 * Asocia las novedades por cada tipo
	 * 
	 */
	private void llenarListaNovedades(){
		/*Novedad 159-164 back */
		cambioTipoPensionado = new ArrayList<>();
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_0_6_WEB);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_2_WEB);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_WEB);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_6_WEB);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_2_WEB);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSION_FAMILIAR_WEB);
		cambioTipoPensionado.add(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_25_ANIOS);
		/*Novedad 172 back*/
		cambioTipoIndependiente = new ArrayList<>();
		cambioTipoIndependiente.add(TipoTransaccionEnum.CAMBIO_TIPO_INDEPENDIENTE_SEGUN_VALOR_APORTES_PRESENCIAL);
		cambioTipoIndependiente.add(TipoTransaccionEnum.CAMBIO_TIPO_INDEPENDIENTE_SEGUN_VALOR_APORTES_WEB);
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO solicitudNovedadDTO, EntityManager entityManager, UserDTO userDTO)  {
        // TODO Auto-generated method stub
        
    }
}
