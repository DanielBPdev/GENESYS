package com.asopagos.novedades.convertidores.persona;
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ConsultarBeneficiario;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.CondicionInvalidezModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.novedades.composite.clients.VerificarPersonaNovedadRegistrarAnalisisFovis;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.personas.clients.ActualizarCondicionInvalidez;
import com.asopagos.personas.clients.ConsultarCondicionInvalidez;
import com.asopagos.personas.clients.ConsultarDatosPersona;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar los datos de condicion de Invalidez
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarCondicionInvalidezNovedadPersona implements NovedadCore {

    private static final List<TipoTransaccionEnum> txReporteCondicionInvalidez =  new ArrayList<>();

    static{
        txReporteCondicionInvalidez.add(TipoTransaccionEnum.REPORTE_INVALIDEZ_PERSONAS);
        txReporteCondicionInvalidez.add(TipoTransaccionEnum.REPORTE_INVALIDEZ_PERSONAS_DEPWEB);
        txReporteCondicionInvalidez.add(TipoTransaccionEnum.REPORTE_INVALIDEZ_PERSONAS_WEB);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
     */
    @Override
    public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {

        /* se transforma a un objeto de datos del empleador */
        DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();

        BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();
        PersonaModeloDTO personaDTO = new PersonaModeloDTO();
        
        Boolean condicionInvalidez = null;
        Long fechaReporte = null;
        Long fechaInicio = null;
        Boolean conyugeCuidador = null;
        Long fechaInicioConyugeCuidador = null;
        Long fechaFinConyugeCuidador = null;
        Long idConyugeCuidador = null;
        
        // Se consulta si se selecciono un beneficiario o un afiliado
        if (datosPersona.getIdBeneficiario() != null) {
            ConsultarBeneficiario consultarBeneficiario = new ConsultarBeneficiario(datosPersona.getIdBeneficiario());
            consultarBeneficiario.execute();
            beneficiarioModeloDTO = consultarBeneficiario.getResult();
            personaDTO = beneficiarioModeloDTO;
            
            condicionInvalidez = datosPersona.getCondicionInvalidezHijo();
            fechaReporte = datosPersona.getFechaRepoteinvalidezHijo();
            fechaInicio = datosPersona.getFechaInicioInvalidezHijo();
            conyugeCuidador = datosPersona.getConyugeCuidadorHijo();
            fechaInicioConyugeCuidador = datosPersona.getFechaInicioConyugeCuidadorHijo();
            fechaFinConyugeCuidador = datosPersona.getFechaFinConyugeCuidadorHijo();
            idConyugeCuidador = datosPersona.getIdConyugeCuidador();
            
        }
        else {
            ConsultarDatosPersona consultarDatosPersona = new ConsultarDatosPersona(datosPersona.getNumeroIdentificacion(),
                    datosPersona.getTipoIdentificacion());
            consultarDatosPersona.execute();
            personaDTO = consultarDatosPersona.getResult();
            
            condicionInvalidez = datosPersona.getCondicionInvalidezTrabajador();
            fechaReporte = datosPersona.getFechaReporteInvalidezTrabajador();
            fechaInicio = datosPersona.getFechaInicioInvalidezTrabajador();
        }

        ConsultarCondicionInvalidez condicionInvalidezService = new ConsultarCondicionInvalidez(personaDTO.getNumeroIdentificacion(),
                personaDTO.getTipoIdentificacion());
        condicionInvalidezService.execute();
        CondicionInvalidezModeloDTO condicionInvalidezModeloDTO = condicionInvalidezService.getResult();
        if (condicionInvalidezModeloDTO == null) {
            condicionInvalidezModeloDTO = new CondicionInvalidezModeloDTO();
        }
        condicionInvalidezModeloDTO.setIdPersona(personaDTO.getIdPersona());

        if (txReporteCondicionInvalidez.contains(novedad)) {
            condicionInvalidezModeloDTO.setCondicionInvalidez(condicionInvalidez);
            condicionInvalidezModeloDTO.setFechaReporteInvalidez(fechaReporte);
            condicionInvalidezModeloDTO.setFechaInicioInvalidez(fechaInicio);
            condicionInvalidezModeloDTO.setConyugeCuidador(conyugeCuidador);
            condicionInvalidezModeloDTO.setFechaInicioConyugeCuidador(fechaInicioConyugeCuidador);
            condicionInvalidezModeloDTO.setFechaFinConyugeCuidador(fechaFinConyugeCuidador);
            condicionInvalidezModeloDTO.setIdConyugeCuidador(idConyugeCuidador);
        }
        // Se verifica si se indico desde validaciones si la persona esta asociada a una postulacion FOVIS
        // Se crea la tarea para revisarla en la HU325-77
        // Se verifica si la persona esta asociada a una postulacion FOVIS
        List<PersonaDTO> listaPersonas = new ArrayList<>();
        PersonaDTO persona = new PersonaDTO();
        persona.setNumeroIdentificacion(personaDTO.getNumeroIdentificacion());
        persona.setTipoIdentificacion(personaDTO.getTipoIdentificacion());
        listaPersonas.add(persona);
        // Se crea la tarea para revisarla en la HU325-77
        VerificarPersonaNovedadRegistrarAnalisisFovis verificarPersonaNovedadRegistrarAnalisisFovis = new VerificarPersonaNovedadRegistrarAnalisisFovis(
                solicitudNovedadDTO.getIdSolicitudNovedad(), listaPersonas);
        verificarPersonaNovedadRegistrarAnalisisFovis.execute();
        ActualizarCondicionInvalidez actualizarCondicionInvalidez = new ActualizarCondicionInvalidez(condicionInvalidezModeloDTO);
        return actualizarCondicionInvalidez;
    }

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        // TODO Auto-generated method stub
        
    }

}
