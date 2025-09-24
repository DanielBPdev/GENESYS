package com.asopagos.novedades.convertidores.persona;
/**
 * 
 */

 import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.ArrayList;
 import java.util.List;
 import javax.persistence.EntityManager;
 import com.asopagos.afiliados.clients.ActualizarRolAfiliado;
 import com.asopagos.afiliados.clients.ConsultarRolAfiliado;
 import com.asopagos.dto.PersonaDTO;
 import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
 import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
 import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
 import com.asopagos.novedades.composite.clients.VerificarPersonaNovedadRegistrarAnalisisFovis;
 import com.asopagos.novedades.composite.service.NovedadCore;
 import com.asopagos.novedades.dto.DatosPersonaNovedadDTO;
 import com.asopagos.novedades.dto.SolicitudNovedadDTO;
 import com.asopagos.rest.security.dto.UserDTO;
 import com.asopagos.services.common.ServiceClient;

/**
 * Clase que contiene la lógica para actualizar los datos basicos de un Rol Afiliado
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class ActualizarRolAfiliadoNovedadPersona implements NovedadCore{

	List<TipoTransaccionEnum> cambioClaseTrabajador;
	List<TipoTransaccionEnum> cambioTipoSalario; 
	List<TipoTransaccionEnum> cambioSucursal;
	List<TipoTransaccionEnum> cambioClaseIndependiente;
	List<TipoTransaccionEnum> cambioValorIngresosIndependiente;
	List<TipoTransaccionEnum> actualizacionEntidadPagadoraPensionado;
	List<TipoTransaccionEnum> actualizacionValorMesadaPensional;
	List<TipoTransaccionEnum> activarPagadorPension;
	List<TipoTransaccionEnum> activacionEntidadPagadoraIndependiente;
	List<TipoTransaccionEnum> desactivacionEntidadPagadoraIndependiente;
	List<TipoTransaccionEnum> actualizarOportunidadPagoIndependiente;
	
	
	
	/* (non-Javadoc)
	 * @see com.asopagos.novedades.composite.service.NovedadCore#transformarServicio(com.asopagos.novedades.dto.SolicitudNovedadDTO)
	 */
	@Override
	public ServiceClient transformarServicio(SolicitudNovedadDTO solicitudNovedadDTO) {
		System.out.println("Entra a transformarServicio ActualizarRolAfiliadoNovedadPersona");

		/*se transforma a un objeto de datos del empleador*/
		DatosPersonaNovedadDTO datosPersona = (DatosPersonaNovedadDTO) solicitudNovedadDTO.getDatosPersona();
		TipoTransaccionEnum novedad = solicitudNovedadDTO.getNovedadDTO().getNovedad();
		
		ConsultarRolAfiliado consultarRolAfiliado = new ConsultarRolAfiliado(datosPersona.getIdRolAfiliado());
		consultarRolAfiliado.execute();
		RolAfiliadoModeloDTO rolAfiliadoDTO = consultarRolAfiliado.getResult();
		
		// Indica si se debe verificar la validacion de postulado FOVIS
		Boolean validarPostuladoFovis = false;
		
		/*Asocia los tipos de Novedad de Persona.*/
		this.llenarListaNovedades();
		/*Si es Cambio Clase trabajador Novedad 124*/
		if (cambioClaseTrabajador.contains(novedad)) {
			if(datosPersona.getClaseTrabajador().equals(ClaseTrabajadorEnum.VETERANO_FUERZA_PUBLICA) && 
				(rolAfiliadoDTO.getFechaFinCondicionVet() == null &&
				rolAfiliadoDTO.getFechaInicioCondicionVet() == null)) {
				// Obtén la fecha actual
				Date fechaOriginal = new Date();
				rolAfiliadoDTO.setFechaInicioCondicionVet(fechaOriginal);
				// Utiliza Calendar para sumar dos años a la fecha
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(fechaOriginal);
				calendario.add(Calendar.YEAR, 2); // Suma 2 años
				// Obtén la nueva fecha con dos años añadidos
				Date fechaDosAniosDespues = calendario.getTime();
				rolAfiliadoDTO.setFechaFinCondicionVet(fechaDosAniosDespues);
			}else if(!datosPersona.getClaseTrabajador().equals(ClaseTrabajadorEnum.VETERANO_FUERZA_PUBLICA) && ClaseTrabajadorEnum.VETERANO_FUERZA_PUBLICA.equals(rolAfiliadoDTO.getClaseTrabajador()) ){
				rolAfiliadoDTO.setFechaFinCondicionVet(new Date());
				System.out.println("fecha de rolafiliado a persistir 1");
				System.out.println(rolAfiliadoDTO.getFechaFinCondicionVet());
			}
			rolAfiliadoDTO.setClaseTrabajador(datosPersona.getClaseTrabajador());
			/*Novedad 125*/
		} else if (cambioTipoSalario.contains(novedad)) {
			rolAfiliadoDTO.setTipoSalario(datosPersona.getTipoSalarioTrabajador());
			rolAfiliadoDTO.setValorSalarioMesadaIngresos(datosPersona.getValorSalarioMensualTrabajador());
			rolAfiliadoDTO.setCargo(datosPersona.getCargoOficioDesempeniadoTrabajador());
			rolAfiliadoDTO.setTipoContrato(datosPersona.getTipoContratoLaboralTrabajador());
			validarPostuladoFovis = true;
			/*Novedad 130 */
		} else if (cambioSucursal.contains(novedad)) {
			rolAfiliadoDTO.setIdSucursalEmpleador(datosPersona.getSucursalEmpleadorTrabajador().getIdSucursalEmpresa());
			/*Novedad 176*/
		} else if (cambioClaseIndependiente.contains(novedad)) {
			rolAfiliadoDTO.setClaseIndependiente(datosPersona.getClaseIndependiente());
			/*Novedad 179*/
		} else if (cambioValorIngresosIndependiente.contains(novedad)) {
			rolAfiliadoDTO.setValorSalarioMesadaIngresos(datosPersona.getIngresosMensualesIndependiente());
		} else if (actualizacionEntidadPagadoraPensionado.contains(novedad)) {
			rolAfiliadoDTO.setPagadorAportes(datosPersona.getEntidadPagadoraDeAportesPensionado());
			rolAfiliadoDTO.setFechaFinPagadorPension(datosPersona.getVigenciaPagosPensionado());
		} else if (actualizacionValorMesadaPensional.contains(novedad)) {
			rolAfiliadoDTO.setValorSalarioMesadaIngresos(datosPersona.getValorMesadaPensional());
			validarPostuladoFovis = true;
		} else if (activarPagadorPension.contains(novedad)){
			rolAfiliadoDTO.setPagadorPension(datosPersona.getPagadorFondoPensiones());
			rolAfiliadoDTO.setIdentificadorAnteEntidadPagadora(datosPersona.getIdEntidadPagadora());
			rolAfiliadoDTO.setFechaFinPagadorPension(datosPersona.getVigenciaPagosPensionado());
			rolAfiliadoDTO.setEstadoEnEntidadPagadoraPensionado(EstadoActivoInactivoEnum.ACTIVO);
		} else if (activacionEntidadPagadoraIndependiente.contains(novedad)) {
			rolAfiliadoDTO.setPagadorAportes(datosPersona.getEntidadPagadoraDeAportes());
			rolAfiliadoDTO.setIdentificadorAnteEntidadPagadora(datosPersona.getIdEntidadPagadora());
			rolAfiliadoDTO.setFechaFinPagadorAportes(datosPersona.getVigenciaPagosDependiente());
			rolAfiliadoDTO.setEstadoEnEntidadPagadora(EstadoActivoInactivoEnum.ACTIVO);
		} else if (desactivacionEntidadPagadoraIndependiente.contains(novedad)) {
			rolAfiliadoDTO.setPagadorAportes(datosPersona.getEntidadPagadoraDeAportes());
			rolAfiliadoDTO.setIdentificadorAnteEntidadPagadora(datosPersona.getIdEntidadPagadora());
			rolAfiliadoDTO.setFechaFinPagadorAportes(datosPersona.getVigenciaPagosDependiente());
			rolAfiliadoDTO.setEstadoEnEntidadPagadora(EstadoActivoInactivoEnum.INACTIVO);
		} else if (actualizarOportunidadPagoIndependiente.contains(novedad)){
		    rolAfiliadoDTO.setOportunidadPago(datosPersona.getOportunidadAporte());
		}
		
		// Se verifica si se indico desde validaciones que la persona esta asociada a una postulacion FOVIS
		// Se crea la tarea para revisarla en la HU325-77
		if (validarPostuladoFovis) {
            // Se verifica si la persona esta asociada a una postulacion FOVIS
            List<PersonaDTO> listaPersonas = new ArrayList<>();
            PersonaDTO persona = new PersonaDTO();
            persona.setNumeroIdentificacion(rolAfiliadoDTO.getAfiliado().getNumeroIdentificacion());
            persona.setTipoIdentificacion(rolAfiliadoDTO.getAfiliado().getTipoIdentificacion());
            listaPersonas.add(persona);
            // Se crea la tarea para revisarla en la HU325-77
            VerificarPersonaNovedadRegistrarAnalisisFovis verificarPersonaNovedadRegistrarAnalisisFovis = new VerificarPersonaNovedadRegistrarAnalisisFovis(
                    solicitudNovedadDTO.getIdSolicitudNovedad(), listaPersonas);
            verificarPersonaNovedadRegistrarAnalisisFovis.execute();

		}
		
		ActualizarRolAfiliado actualizarRolAfiliadoDTO = new ActualizarRolAfiliado(rolAfiliadoDTO);
		return actualizarRolAfiliadoDTO;
	}

	/**
	 * Asocia las novedades por cada tipo
	 * 
	 */
	private void llenarListaNovedades(){
		/*Novedad 124 */
		cambioClaseTrabajador = new ArrayList<>();
		cambioClaseTrabajador.add(TipoTransaccionEnum.CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_DEPWEB);
		cambioClaseTrabajador.add(TipoTransaccionEnum.CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_PRESENCIAL);
		
		/*Novedad 125 */
		cambioTipoSalario = new ArrayList<>();
		cambioTipoSalario.add(TipoTransaccionEnum.ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_DEPWEB);
		cambioTipoSalario.add(TipoTransaccionEnum.ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL);
		
		/*Novedad 130 */
		cambioSucursal = new ArrayList<>();
		cambioSucursal.add(TipoTransaccionEnum.CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB);
		
		/*Novedad 176*/
		cambioClaseIndependiente = new ArrayList<>();
		cambioClaseIndependiente.add(TipoTransaccionEnum.CAMBIO_CLASE_DE_INDEPENDIENTE_PRESENCIAL);
		cambioClaseIndependiente.add(TipoTransaccionEnum.CAMBIO_CLASE_DE_INDEPENDIENTE_WEB);
		
		/*Novedad 179*/
		cambioValorIngresosIndependiente = new ArrayList<>();
		cambioValorIngresosIndependiente.add(TipoTransaccionEnum.CAMBIAR_VALOR_DE_INGRESOS_MENSUALES_INDEPENDIENTE_PRESENCIAL);
		cambioValorIngresosIndependiente.add(TipoTransaccionEnum.CAMBIAR_VALOR_DE_INGRESOS_MENSUALES_INDEPENDIENTE_WEB);
		
		/*Novedad 138 - 143 back*/
		actualizacionEntidadPagadoraPensionado = new ArrayList<>();
		actualizacionEntidadPagadoraPensionado.add(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL);
		actualizacionEntidadPagadoraPensionado.add(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MAYOR_1_5SM_0_6_WEB);
		actualizacionEntidadPagadoraPensionado.add(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL);
		actualizacionEntidadPagadoraPensionado.add(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MAYOR_1_5SM_2_WEB);
		actualizacionEntidadPagadoraPensionado.add(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL);
		actualizacionEntidadPagadoraPensionado.add(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MENOR_1_5SM_0_6_WEB);
		actualizacionEntidadPagadoraPensionado.add(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL);
		actualizacionEntidadPagadoraPensionado.add(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MENOR_1_5SM_2_WEB);
		actualizacionEntidadPagadoraPensionado.add(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL);
		actualizacionEntidadPagadoraPensionado.add(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_PENSION_FAMILIAR_WEB);
		
		/*Novedad 145 - 150 back*/
		actualizacionValorMesadaPensional = new ArrayList<>();
		actualizacionValorMesadaPensional.add(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL);
		actualizacionValorMesadaPensional.add(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_0_6_WEB);
		actualizacionValorMesadaPensional.add(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL);
		actualizacionValorMesadaPensional.add(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_2_WEB);
		actualizacionValorMesadaPensional.add(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL);
		actualizacionValorMesadaPensional.add(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_0_6_WEB);
		actualizacionValorMesadaPensional.add(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL);
		actualizacionValorMesadaPensional.add(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_2_WEB);
		actualizacionValorMesadaPensional.add(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_GRUPO_FAMILIAR_PRESENCIAL);
		actualizacionValorMesadaPensional.add(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_GRUPO_FAMILIAR_WEB);
		
		/*Novedad 166 - 170 back*/
		activarPagadorPension = new ArrayList<>();
		activarPagadorPension.add(TipoTransaccionEnum.ACTIVAR_PAGADOR_DE_PENSION_PENSIONADO_MAYOR_1_5SM_0_6);
		activarPagadorPension.add(TipoTransaccionEnum.ACTIVAR_PAGADOR_DE_PENSION_PENSIONADO_MAYOR_1_5SM_2);
		activarPagadorPension.add(TipoTransaccionEnum.ACTIVAR_PAGADOR_DE_PENSION_PENSIONADO_MENOR_1_5SM_0_6);
		activarPagadorPension.add(TipoTransaccionEnum.ACTIVAR_PAGADOR_DE_PENSION_PENSIONADO_MENOR_1_5SM_2);
		activarPagadorPension.add(TipoTransaccionEnum.ACTIVAR_PAGADOR_DE_PENSION_PENSIONADO_PENSION_FAMILIAR);
		
		/*Novedad 175 back*/
		activacionEntidadPagadoraIndependiente = new ArrayList<>();
		activacionEntidadPagadoraIndependiente.add(TipoTransaccionEnum.ACTIVACION_ENTIDAD_PAGADORA_APORTES_INDEPENDIENTES_PRESENCIAL);
		activacionEntidadPagadoraIndependiente.add(TipoTransaccionEnum.ACTIVACION_ENTIDAD_PAGADORA_APORTES_INDEPENDIENTES_WEB);
		
		/*Novedad 176 back*/
		desactivacionEntidadPagadoraIndependiente = new ArrayList<>();
		desactivacionEntidadPagadoraIndependiente.add(TipoTransaccionEnum.DESACTIVAR_ENTIDA_PAGADORA_APORTES_INDEPENDIENTES_PRESENCIAL);
		desactivacionEntidadPagadoraIndependiente.add(TipoTransaccionEnum.DESACTIVAR_ENTIDA_PAGADORA_APORTES_INDEPENDIENTES_WEB);
		
		actualizarOportunidadPagoIndependiente = new ArrayList<>();
		actualizarOportunidadPagoIndependiente.add(TipoTransaccionEnum.ACTUALIZACION_OPORTUNIDAD_PAGO_APORTES_INDEPENDIENTE_PRESENCIAL);
		actualizarOportunidadPagoIndependiente.add(TipoTransaccionEnum.ACTUALIZACION_OPORTUNIDAD_PAGO_APORTES_INDEPENDIENTE_WEB);
	}

    @Override
    public void transformarEjecutarRutinaNovedad(SolicitudNovedadDTO solicitudNovedadDTO, EntityManager entityManager, UserDTO userDTO)  {
        // TODO Auto-generated method stub
        
    }
}


