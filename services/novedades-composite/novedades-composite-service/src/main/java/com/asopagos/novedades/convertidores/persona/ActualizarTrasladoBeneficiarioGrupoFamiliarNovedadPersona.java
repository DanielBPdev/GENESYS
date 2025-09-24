package com.asopagos.novedades.convertidores.persona;
/**
 * 
 */

import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ConsultarBeneficiario;
import com.asopagos.afiliados.clients.ConsultarDatosAfiliado;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.GrupoFamiliarModeloDTO;
import com.asopagos.novedades.composite.clients.EjecutarActualizacionBeneficiario;
import com.asopagos.novedades.composite.clients.EjecutarTrasladoBeneficiariosGrupoFamiliar;
import com.asopagos.novedades.composite.dto.BeneficiarioGrupoAfiliadoDTO;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;
//*
import com.asopagos.afiliados.clients.ActualizarGrupoFamiliarPersona;
import com.asopagos.dto.DatosBasicosIdentificacionDTO;
import com.asopagos.afiliados.clients.AsociarBeneficiarioAGrupoFamiliar;
import com.asopagos.rutine.afiliadosrutines.actualizarmediodepagogrupofamiliar.ActualizarMedioDePagoGrupoFamiliarRutine;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.enumeraciones.core.TipoNovedadEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import javax.persistence.PersistenceContext;
import com.asopagos.afiliados.clients.ActualizarMedioDePagoGrupoFamiliar;
import com.asopagos.solicitudes.clients.ConsultarDatosTemporales;
import com.asopagos.solicitudes.clients.GuardarDatosTemporales;
/**
 * Clase que contiene la lógica para actualizar el traslado de beneficiarios a
 * un Grupo Familiar
 * 
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class ActualizarTrasladoBeneficiarioGrupoFamiliarNovedadPersona implements NovedadCore {

    @PersistenceContext(unitName = "novedades_PU")
    private EntityManager entityManager;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(
	 * com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
ServiceClient service = null;
		/* se transforma a un objeto de datos de persona */
		DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();

		// Consulta el afiliado principal
		ConsultarDatosAfiliado consultarDatosAfiliado = new ConsultarDatosAfiliado(
				datosPersona.getNumeroIdentificacion(), datosPersona.getTipoIdentificacion());
		consultarDatosAfiliado.execute();
		AfiliadoModeloDTO afiliadoModeloDTO = consultarDatosAfiliado.getResult();
		
		// Se obtiene la informacion del grupo familiar de traslado
		GrupoFamiliarModeloDTO grupoFamiliar = datosPersona.getGrupoFamiliarTrasladoBeneficiario();
		
		// Se obtiene la informacion del beneficiario a trasladar
		ConsultarBeneficiario consultarBeneficiario = new ConsultarBeneficiario(datosPersona.getIdBeneficiario());
		consultarBeneficiario.execute();
		BeneficiarioModeloDTO beneficiarioModeloDTO = consultarBeneficiario.getResult();
		
		/*Instancia el servicio para actualizar Beneficiario*/
		BeneficiarioGrupoAfiliadoDTO beneficiarioGrupoAfiliado = new BeneficiarioGrupoAfiliadoDTO();
		beneficiarioGrupoAfiliado.setAfiliado(afiliadoModeloDTO);
		beneficiarioGrupoAfiliado.setBeneficiario(beneficiarioModeloDTO);
		beneficiarioGrupoAfiliado.setGrupoFamiliar(grupoFamiliar);
		/**-------------------------- */
 beneficiarioGrupoAfiliado.getGrupoFamiliar().setIdAfiliado(beneficiarioGrupoAfiliado.getAfiliado().getIdAfiliado());
        ActualizarGrupoFamiliarPersona actualizarGrupoFamiliarService = new ActualizarGrupoFamiliarPersona(
                beneficiarioGrupoAfiliado.getGrupoFamiliar());
        actualizarGrupoFamiliarService.execute();
        Long idGrupoFamiliar = actualizarGrupoFamiliarService.getResult();

        // Se asocia el grupo familiar al beneficiario
        DatosBasicosIdentificacionDTO inDTO = new DatosBasicosIdentificacionDTO();
        inDTO.setPersona(PersonaDTO.convertPersonaToDTO(beneficiarioGrupoAfiliado.getBeneficiario().convertToPersonaEntity(), null));
        AsociarBeneficiarioAGrupoFamiliar asociarBeneficiarioAGrupoFamiliar = new AsociarBeneficiarioAGrupoFamiliar(idGrupoFamiliar,
                beneficiarioGrupoAfiliado.getAfiliado().getIdAfiliado(), inDTO);

			asociarBeneficiarioAGrupoFamiliar.execute();

		//inserto novedad medio de pago
		 // DatosPersonaNovedadDTO datosPersona = solicitudNovedadDTO.getDatosPersona();
		 
		solicitudNovedadDTO.getDatosPersona().setIdGrupoFamiliar(idGrupoFamiliar);
          solicitudNovedadDTO.setTipoTransaccion(TipoTransaccionEnum.CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE);
          solicitudNovedadDTO.getNovedadDTO().setRutaCualificada("com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona");
          solicitudNovedadDTO.getNovedadDTO().setNovedad(TipoTransaccionEnum.CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE);
          solicitudNovedadDTO.getNovedadDTO().setIdNovedad(Long.valueOf(1023));
          solicitudNovedadDTO.getNovedadDTO().setTipoNovedad(TipoNovedadEnum.GRUPO_FAMILIAR);
		 MedioDePagoModeloDTO medioDePagoModeloDTO = datosPersona.getMedioDePagoModeloDTO();
		  medioDePagoModeloDTO.setIdGrupoFamiliar(idGrupoFamiliar);

		  if(idGrupoFamiliar != null){
			String cadenaSinIdGrupoFamiliar = "\"grupoFamiliarTrasladoBeneficiario\":\\{\"idGrupoFamiliar\":\"null\",";
		  	String cadenaIdGrupoFamiliar = "\"grupoFamiliarTrasladoBeneficiario\":{\"idGrupoFamiliar\":"+Long.toString(idGrupoFamiliar)+",";
  

		  	ConsultarDatosTemporales consultarDatosTemporales = new ConsultarDatosTemporales(solicitudNovedadDTO.getIdSolicitud());
		  	consultarDatosTemporales.execute();
		  	String datosTemporales = consultarDatosTemporales.getResult();
  
		  	String nuevoJson = datosTemporales.replaceAll(cadenaSinIdGrupoFamiliar, cadenaIdGrupoFamiliar);
  
		  	System.out.println("nuevoJson: " + nuevoJson);
		   	GuardarDatosTemporales guardarDatosTemporales = new GuardarDatosTemporales(solicitudNovedadDTO.getIdSolicitud(), nuevoJson);
        	guardarDatosTemporales.execute();

		  }
		  
// ActualizarMedioDePagoGrupoFamiliarRutine a = new ActualizarMedioDePagoGrupoFamiliarRutine();
//	    a.actualizarMedioDePagoGrupoFamiliar(medioDePagoModeloDTO, entityManager);
		//fin insercion
		    service = new ActualizarMedioDePagoGrupoFamiliar(medioDePagoModeloDTO);
		
			System.out.println("**__**ActualizarTrasladoBeneficiarioGrupoFamiliarNovedadPersona.datosPersona.getMedioDePagoModeloDTO().getAdmonSubsidio() " +datosPersona.getMedioDePagoModeloDTO().getAdmonSubsidio().getNumeroIdentificacion() );
		return service;
		/**------------------- */
		
		//EjecutarTrasladoBeneficiariosGrupoFamiliar ejecutarTrasladoBeneficiariosGrupoFamiliar =  new EjecutarTrasladoBeneficiariosGrupoFamiliar(beneficiarioGrupoAfiliado);
		//return ejecutarTrasladoBeneficiariosGrupoFamiliar;
	}
    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO solicitudNovedadDTO, EntityManager entityManager, UserDTO userDTO)  {
        // TODO Auto-generated method stub
        
    }
}