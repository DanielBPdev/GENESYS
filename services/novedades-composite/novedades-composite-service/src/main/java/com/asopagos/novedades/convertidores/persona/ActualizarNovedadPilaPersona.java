package com.asopagos.novedades.convertidores.persona;
/**
 * 
 */
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.asopagos.afiliados.clients.ActualizarNovedadPila;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.dto.modelo.NovedadDetalleModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.novedades.composite.clients.VerificarPersonaNovedadRegistrarAnalisisFovis;
import com.asopagos.novedades.composite.service.NovedadCore;
import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.rutine.afiliadosrutines.actualizarnovedadpila.ActualizarNovedadPilaRutine;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.afiliados.clients.ConsultarRolAfiliado;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.asopagos.cartera.clients.ActualizarDeudaPresuntaCarteraAsincrono;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.empleadores.clients.ConsultarPersonaEmpleador;
import com.asopagos.entidades.ccf.personas.Persona;

/**
 * @author dsuesca
 *
 */
/**
 * @author dsuesca
 *
 */
/**
 * Clase que contiene la lógica para actualizar los datos de una Novedad Pila
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarNovedadPilaPersona implements NovedadCore{
	
	private List<TipoTransaccionEnum> actualizacionNovedadDetalle;
	
	private List<TipoTransaccionEnum> txVSP;
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
	    
		
		/*Instancia el servicio para actualizar Novedad Detalle*/
		ActualizarNovedadPila actualizarNovedadPila = new ActualizarNovedadPila(obtenerObjetoServicio(solicitudNovedadDTO));
		return actualizarNovedadPila;
	};
	

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO datosNovedad, EntityManager entityManager, UserDTO userDTO) {
        NovedadDetalleModeloDTO objetoNovedad = obtenerObjetoServicio(datosNovedad);
        ActualizarNovedadPilaRutine a = new ActualizarNovedadPilaRutine();
        a.actualizarNovedadPila(objetoNovedad, entityManager); 
    }

    
    private NovedadDetalleModeloDTO obtenerObjetoServicio(SolicitudNovedadDTO solicitudNovedadDTO){
        /*se transforma a un objeto de datos del empleador*/
        DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
        TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
        
        NovedadDetalleModeloDTO novedadDetalleModeloDTO = new NovedadDetalleModeloDTO();
        
        /*Asocia los tipos de Novedad de Persona.*/
        this.agregarListaNovedades(); 
        if (actualizacionNovedadDetalle.contains(novedad)) {
            novedadDetalleModeloDTO.setFechaInicio(datosPersona.getFechaInicioNovedad());
            novedadDetalleModeloDTO.setFechaFin(datosPersona.getFechaFinNovedad());
            novedadDetalleModeloDTO.setIdSolicitudNovedad(solicitudNovedadDTO.getIdSolicitudNovedad());
            novedadDetalleModeloDTO.setVigente(datosPersona.getNovedadVigente());
        }
        if (txVSP.contains(novedad)) {
            // Se verifica si la persona esta asociada a una postulacion FOVIS
            List<PersonaDTO> listaPersonas = new ArrayList<>();
            PersonaDTO persona = new PersonaDTO();
            persona.setNumeroIdentificacion(datosPersona.getNumeroIdentificacion());
            persona.setTipoIdentificacion(datosPersona.getTipoIdentificacion());
            listaPersonas.add(persona);
            // Se crea la tarea para revisarla en la HU325-77
            VerificarPersonaNovedadRegistrarAnalisisFovis verificarPersonaNovedadRegistrarAnalisisFovis = new VerificarPersonaNovedadRegistrarAnalisisFovis(
                    solicitudNovedadDTO.getIdSolicitudNovedad(), listaPersonas);
            verificarPersonaNovedadRegistrarAnalisisFovis.execute();
        }
        System.out.println("ActualizarNovedadPilaPersona");
        // try{
        //     if(datosPersona.getIdRolAfiliado() !=null || !datosPersona.getIdRolAfiliado().toString().isEmpty()){
        //     ConsultarRolAfiliado rolAfiliadoService = new ConsultarRolAfiliado(datosPersona.getIdRolAfiliado());
        //         rolAfiliadoService.execute();
        //     RolAfiliadoModeloDTO rolAfiliadoModeloDTO = rolAfiliadoService.getResult();
        //     if(rolAfiliadoModeloDTO.getTipoAfiliado() == TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE && 
        //     (solicitudNovedadDTO.getCanalRecepcion() != CanalRecepcionEnum.APORTE_MANUAL && 
        //         solicitudNovedadDTO.getCanalRecepcion() != CanalRecepcionEnum.PILA)
        //     ){
        //         ConsultarPersonaEmpleador c = new ConsultarPersonaEmpleador(rolAfiliadoModeloDTO.getIdEmpleador());
        //         c.execute();
        //         Persona dataPersonaEmpresa = c.getResult();
        //         Date fecha = new Date(datosPersona.getFechaInicioNovedad());
        //         System.out.println("validacion del periodo para la novedad");
        //         System.out.println(fecha);
        //         SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM");
        //         String periodo = formato.format(fecha);
        //         ActualizarDeudaPresuntaCarteraAsincrono actualizarDeuda = new ActualizarDeudaPresuntaCarteraAsincrono(TipoSolicitanteMovimientoAporteEnum.EMPLEADOR,dataPersonaEmpresa.getNumeroIdentificacion(),periodo,dataPersonaEmpresa.getTipoIdentificacion());
        //         actualizarDeuda.execute();
        //     }
        // }
        // }catch(Exception e){
        //     // si falla no culpar a jaider :p esto lo pidio gabo 
        //     System.out.println(e);
        // }
        return novedadDetalleModeloDTO;
    }
    
    
    /**
     * Asocia las novedades que realizan modificaciones en la tabla NovedadDetalle
     * 
     */
    private void agregarListaNovedades(){   
        /*Novedad 132 - 136 */
        actualizacionNovedadDetalle = new ArrayList<>();
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE);
        // Novedad LMA
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_DEPWEB);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_WEB);
        // Novedad IGE - IRL
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_DEPWEB);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_WEB);
        
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_DEPWEB);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_WEB);
        // Novedad SLN
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_DEPWEB);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PRESENCIAL);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_WEB);
        // Novedad VAC
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_DEPWEB);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_PRESENCIAL);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_WEB);
        // Novedad VST
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_DEPWEB);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PRESENCIAL);
        actualizacionNovedadDetalle.add(TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_WEB);
        // Novedad VSP
        txVSP = new ArrayList<>();
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_DEPWEB);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_WEB);
        // Novedad VSP Pensionados
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_25ANIOS_PRESENCIAL);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_25ANIOS_WEB);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_WEB);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_2_WEB);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_6_WEB);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_WEB);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_2_WEB);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL);
        txVSP.add(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_PENSION_FAMILIAR_WEB);
        
        actualizacionNovedadDetalle.addAll(txVSP);
    }
    
}