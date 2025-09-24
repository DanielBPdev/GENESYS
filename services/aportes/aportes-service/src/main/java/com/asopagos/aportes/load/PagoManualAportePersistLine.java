package com.asopagos.aportes.load;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.aportes.constants.ConstanteCampoArchivo;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.SubTipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripcion:</b> Clase que contiene la creación de DTO respecto a los
 * datos pertenecientes a un archivo que se tienen en el contexto <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */

public class PagoManualAportePersistLine implements IPersistLine {

	/**
	 * NovedadCotizanteDTO
	 */
	private List<NovedadCotizanteDTO> lstNovedadCotizanteDTO;
	/**
	 * Novedad cotizante dto
	 */
	private NovedadCotizanteDTO novedadCotizante = null;
	/**
	 * Listado de departamentos
	 */
	private List<Departamento> lstDepartamento;
	/**
	 * Listado de municipios
	 */
	private List<Municipio> listaMunicipios;
	/**
	 * Valor x seleccionado
	 */
	private String valorX = "X";
	/**
	 * Lista de cotizantes dto
	 */
	private List<CotizanteDTO> lstCotizante = new ArrayList<>();

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
	 *      javax.persistence.EntityManager)
	 */
	@SuppressWarnings("unchecked")
    @Override
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        CotizanteDTO cotizanteDTO = null;
        List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            LineArgumentDTO lineArgumentDTO = lines.get(i);
            lstHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
            if (lstHallazgos.isEmpty()) {
                lstDepartamento = ((List<Departamento>) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO));

                if (i == 0) {
                    lstDepartamento = ((List<Departamento>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_DEPARTAMENTO));
                    listaMunicipios = (List<Municipio>) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_MUNICIPIO);
                }
                try {
                    cotizanteDTO = simularCapturaDatosPantalla(lineArgumentDTO, lstDepartamento, listaMunicipios);
                    lineArgumentDTO.getContext().put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, lstCotizante);
                    ((List<CotizanteDTO>) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS))
                            .add(cotizanteDTO);
                } catch (Exception e) {
                    lineArgumentDTO.getContext().put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, lstCotizante);
                    ((List<CotizanteDTO>) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS))
                            .add(cotizanteDTO);
                }
            }
        }
    }

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
	 */
	@Override
	public void setRollback(EntityManager em) throws FileProcessingException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Método que simula la captura de la pantalla para mantener uniformidad en
	 * el llamado de los datos que deben ser mostrados en la HU-482
	 * 
	 * @param lineArgumentDTO
	 * @return retorna el cotizante dto
	 */
	private CotizanteDTO simularCapturaDatosPantalla(LineArgumentDTO lineArgumentDTO,
			List<Departamento> lstDepartamento, List<Municipio> listaMunicipios) {
		Map<String, Object> line = lineArgumentDTO.getLineValues();
		CotizanteDTO cotizanteDTO = new CotizanteDTO();
		// Tipo identificacion
		if (line.get(ConstanteCampoArchivo.TIPO_IDENTIFICACION_COTIZANTE) != null) {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(
					(String) line.get(ConstanteCampoArchivo.TIPO_IDENTIFICACION_COTIZANTE));
			cotizanteDTO.setTipoIdentificacion(tipoIdentificacion);
		}
		// Numero de identificacion
		if (line.get(ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE) != null) {
			cotizanteDTO
					.setNumeroIdentificacion((String) line.get(ConstanteCampoArchivo.NUMERO_IDENTIFICACION_COTIZANTE));
		}
		TipoCotizanteEnum tipoCozante = null;
		// Tipo Cotizante
		if (line.get(ConstanteCampoArchivo.TIPO_COTIZANTE) != null) {
			tipoCozante = TipoCotizanteEnum
					.obtenerTipoCotizante((Integer) line.get(ConstanteCampoArchivo.TIPO_COTIZANTE));
			cotizanteDTO.setTipoCotizante(tipoCozante);
		}
		// Sub Tipo Cotizante
		if (line.get(ConstanteCampoArchivo.SUBTIPO_COTIZANTE) != null) {
			cotizanteDTO.setSubtipoCotizante(SubTipoCotizanteEnum
					.obtenerSubTipoCotizante((Integer) line.get(ConstanteCampoArchivo.SUBTIPO_COTIZANTE)));
		}
		cotizanteDTO = validarDatosPersonales(lstDepartamento, listaMunicipios, line, cotizanteDTO);
		// Días cotizado
		if (line.get(ConstanteCampoArchivo.DIAS_COTIZADOS) != null) {
			cotizanteDTO.setDiasCotizados(((Integer) line.get(ConstanteCampoArchivo.DIAS_COTIZADOS)).shortValue());
		}
		// Salario basico
		if (line.get(ConstanteCampoArchivo.SALARIO_BASICO) != null) {
		    String salarioBasico= line.get(ConstanteCampoArchivo.SALARIO_BASICO).toString();
		    BigDecimal salario = new BigDecimal(salarioBasico);
			cotizanteDTO.setSalarioBasico(salario);
		}
		// IBC
		if (line.get(ConstanteCampoArchivo.IBC) != null) {
		    String ibc=line.get(ConstanteCampoArchivo.IBC).toString();
			BigDecimal valorIBC = new BigDecimal(ibc);
			cotizanteDTO.setValorIBC(valorIBC);
		}
		// Tarifa
		if (line.get(ConstanteCampoArchivo.TARIFA) != null) {
		    String valorTarifa=line.get(ConstanteCampoArchivo.TARIFA).toString();
			BigDecimal tarifa = new BigDecimal(valorTarifa);
			cotizanteDTO.setTarifa(tarifa);
		}
		// Aporte Obligatorio
		if (line.get(ConstanteCampoArchivo.APORTE_OBLIGATORIO) != null) {
		    String aporteObligatorio=line.get(ConstanteCampoArchivo.APORTE_OBLIGATORIO).toString();
			BigDecimal tarifaAporte =new BigDecimal(aporteObligatorio);
			cotizanteDTO.setAporteObligatorio(tarifaAporte);
		}
		// Correciones
		if (line.get(ConstanteCampoArchivo.CORRECCIONES) != null) {
			cotizanteDTO.setCorrecciones(line.get(ConstanteCampoArchivo.CORRECCIONES).toString());
		}
		// Salario Integral
		if (line.get(ConstanteCampoArchivo.SALARIO_INTEGRAL) != null) {
		    String salarioIntegral = line.get(ConstanteCampoArchivo.SALARIO_INTEGRAL).toString();
			cotizanteDTO.setSalarioIntegral(new Boolean(salarioIntegral));
		} 
		cotizanteDTO = validarFechaIngresoRetiro(line, cotizanteDTO);
		if (line.get(ConstanteCampoArchivo.NUMERO_HORA_LABORAL) != null) {
		    String horarioLaboral=line.get(ConstanteCampoArchivo.NUMERO_HORA_LABORAL).toString();
		    cotizanteDTO
					.setHorasLaboradas(Integer.valueOf(horarioLaboral).shortValue());
		}
		generarNovedades(line, tipoCozante);
		cotizanteDTO.setNovedades(lstNovedadCotizanteDTO);
		return cotizanteDTO;
	}

	/**
	 * Método encargado de validar la fecha de ingreso y fecha retiro
	 * 
	 * @param line,
	 *            mapa que contiene la informacion a validar
	 * @param cotizanteDTO,
	 *            cotizante al cual se le agregaran los campos
	 * @return retorna el cotizanteDTO de la informacion
	 */
	private CotizanteDTO validarFechaIngresoRetiro(Map<String, Object> line, CotizanteDTO cotizanteDTO) {
		// Fecha Ingreso
		if (line.get(ConstanteCampoArchivo.FECHA_INGRESO) != null) {
			String strFechaIngreso = line.get(ConstanteCampoArchivo.FECHA_INGRESO).toString();
			Long fechaIngreso = null;
			try {
				fechaIngreso = (new Date(CalendarUtils.convertirFechaDate(strFechaIngreso))).getTime();
			} catch (Exception e) {
				fechaIngreso = null;
			}
			cotizanteDTO.setFechaIngreso(fechaIngreso);
		}
		// Fecha Retiro
		if (line.get(ConstanteCampoArchivo.FECHA_RETIRO) != null) {
			String strFechaRetiro = line.get(ConstanteCampoArchivo.FECHA_RETIRO).toString();
			Long fechaRetiro = null;
			try {
				fechaRetiro = (new Date(CalendarUtils.convertirFechaDate(strFechaRetiro))).getTime();
			} catch (Exception e) {
				fechaRetiro = null;
			}
			cotizanteDTO.setFechaRetiro(fechaRetiro);
		}
		return cotizanteDTO;
	}

	/**
	 * Método encargado de validar los datos personales
	 * 
	 * @param lstDepartamento,
	 *            listados de departamentos a comprar
	 * @param listaMunicipios,
	 *            listado de municipios a comparar
	 * @param line,
	 *            mapa que contiene la información a validar
	 * @param cotizanteDTO,
	 *            cotizanteDTO al cual se le agregara la informacion
	 */
	private CotizanteDTO validarDatosPersonales(List<Departamento> lstDepartamento, List<Municipio> listaMunicipios,
			Map<String, Object> line, CotizanteDTO cotizanteDTO) {
		// Extranjero no obligado a cotizar
		if (line.get(ConstanteCampoArchivo.EXTRANJERO_NO_ABLIGADO_A_COTIZAR) != null) {
			String strExtranjero = (String) line.get(ConstanteCampoArchivo.EXTRANJERO_NO_ABLIGADO_A_COTIZAR);
			Boolean extranjero = false;
			if (strExtranjero.equals(valorX)) {
				extranjero = true;
			}
			cotizanteDTO.setExtranjeroNoObligadoCotizar(extranjero);
		}
		// Colombiano en el exterior
		if (line.get(ConstanteCampoArchivo.COLOMBIANO_EXTERIOR) != null) {
			String strColombianoExt = (String) line.get(ConstanteCampoArchivo.COLOMBIANO_EXTERIOR);
			boolean colombianoExt = false;
			if (strColombianoExt.equals(valorX)) {
				colombianoExt = true;
			}
			cotizanteDTO.setColombianoExterior(colombianoExt);
		}
		// Departamento
		Object codigoDep = line.get(ConstanteCampoArchivo.CODIGO_DEPARTAMENTO);
		if (codigoDep!=null){
		    String codDepartamento = ((String) codigoDep);
	        Departamento departamento = GetValueUtil.getDepartamento(lstDepartamento, new Long(codDepartamento));
	        if (departamento != null) {
	            cotizanteDTO.setDepartamentoLaboral(departamento.getCodigo());
	            // Municipio
	            Object codigoMun = line.get(ConstanteCampoArchivo.CODIGO_MUNICIPIO);
	            if (codigoMun!=null){
	                Municipio municipio = GetValueUtil.getMunicipioCodigo(listaMunicipios,
	                        departamento.getCodigo() + "" + (String) line.get(ConstanteCampoArchivo.CODIGO_MUNICIPIO));
	                cotizanteDTO.setMunicipioLaboral(municipio.getCodigo());    
	            }
	        }    
		}
		
		// Primer apellido
		if (line.get(ConstanteCampoArchivo.PRIMER_APELLIDO) != null) {
			cotizanteDTO.setPrimerApellido(((String) line.get(ConstanteCampoArchivo.PRIMER_APELLIDO)).toUpperCase());
		}
		// Segundo apellido
		if (line.get(ConstanteCampoArchivo.SEGUNDO_APELLIDO) != null) {
			cotizanteDTO.setSegundoApellido(((String) line.get(ConstanteCampoArchivo.SEGUNDO_APELLIDO)).toUpperCase());
		}
		// Primer Nombre
		if (line.get(ConstanteCampoArchivo.PRIMER_NOMBRE) != null) {
			cotizanteDTO.setPrimerNombre(((String) line.get(ConstanteCampoArchivo.PRIMER_NOMBRE)).toUpperCase());
		}
		// Segundo Nombre
		if (line.get(ConstanteCampoArchivo.SEGUNDO_NOMBRE) != null) {
			cotizanteDTO.setSegundoNombre(((String) line.get(ConstanteCampoArchivo.SEGUNDO_NOMBRE)).toUpperCase());
		}
		return cotizanteDTO;
	}

	/**
	 * Método encargado de genear la novedades
	 * 
	 * @param line,
	 *            mapa que contiene la informacion a validar
	 * @param tipoCozante,
	 *            Enum de tipo cotizante
	 */
	private List<NovedadCotizanteDTO> generarNovedades(Map<String, Object> line, TipoCotizanteEnum tipoCozante) {
		lstNovedadCotizanteDTO = new ArrayList<>();

		switch (tipoCozante.getTipoAfiliado()) {
		case TRABAJADOR_DEPENDIENTE:
			novedadCotizante = crearNovedadCotizanteDTO(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE, line,
					ConstanteCampoArchivo.RET, ConstanteCampoArchivo.FECHA_RETIRO, null);
			agregarNovedadLista(novedadCotizante);
			novedadCotizante = crearNovedadCotizanteDTO(
					TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL, line,
					ConstanteCampoArchivo.VSP, ConstanteCampoArchivo.FECHA_INICIO_VSP, null);
			agregarNovedadLista(novedadCotizante);
			novedadCotizante = crearNovedadCotizanteDTO(
					TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL, line,
					ConstanteCampoArchivo.VST, ConstanteCampoArchivo.FECHA_INICIO_VST,
					ConstanteCampoArchivo.FECHA_FIN_VST);
			agregarNovedadLista(novedadCotizante);
			novedadCotizante = crearNovedadCotizanteDTO(
					TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL, line,
					ConstanteCampoArchivo.SLN, ConstanteCampoArchivo.FECHA_INICIO_SLN,
					ConstanteCampoArchivo.FECHA_FIN_SLN);
			agregarNovedadLista(novedadCotizante);
			novedadCotizante = crearNovedadCotizanteDTO(
					TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL, line,
					ConstanteCampoArchivo.VAC, ConstanteCampoArchivo.FECHA_INICIO_VAC,
					ConstanteCampoArchivo.FECHA_FIN_VAC);
			agregarNovedadLista(novedadCotizante);
			break;
		case TRABAJADOR_INDEPENDIENTE:
			novedadCotizante = crearNovedadCotizanteDTO(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE, line,
					ConstanteCampoArchivo.RET, ConstanteCampoArchivo.FECHA_RETIRO, null);
			agregarNovedadLista(novedadCotizante);
			novedadCotizante = crearNovedadCotizanteDTO(
					TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL, line,
					ConstanteCampoArchivo.VSP, ConstanteCampoArchivo.FECHA_INICIO_VSP, null);
			agregarNovedadLista(novedadCotizante);
			novedadCotizante = crearNovedadCotizanteDTO(
					TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PRESENCIAL, line,
					ConstanteCampoArchivo.VST, ConstanteCampoArchivo.FECHA_INICIO_VST,
					ConstanteCampoArchivo.FECHA_FIN_VST);
			agregarNovedadLista(novedadCotizante);
			novedadCotizante = crearNovedadCotizanteDTO(
					TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PRESENCIAL, line,
					ConstanteCampoArchivo.SLN, ConstanteCampoArchivo.FECHA_INICIO_SLN,
					ConstanteCampoArchivo.FECHA_FIN_SLN);
			agregarNovedadLista(novedadCotizante);
			novedadCotizante = crearNovedadCotizanteDTO(
					TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_PRESENCIAL, line,
					ConstanteCampoArchivo.VAC, ConstanteCampoArchivo.FECHA_INICIO_VAC,
					ConstanteCampoArchivo.FECHA_FIN_VAC);
			agregarNovedadLista(novedadCotizante);
			break;
		default:
			novedadCotizante = crearNovedadCotizanteDTO(TipoTransaccionEnum.RETIRO_PENSIONADO_25ANIOS, line,
					ConstanteCampoArchivo.RET, ConstanteCampoArchivo.FECHA_RETIRO, null);
			agregarNovedadLista(novedadCotizante);
			novedadCotizante = crearNovedadCotizanteDTO(
					TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL, line,
					ConstanteCampoArchivo.VSP, ConstanteCampoArchivo.FECHA_INICIO_VSP, null);
			agregarNovedadLista(novedadCotizante);
			break;
		}
        novedadCotizante = crearNovedadCotizanteDTO(TipoTransaccionEnum.NOVEDAD_ING, line, ConstanteCampoArchivo.ING,
                ConstanteCampoArchivo.FECHA_INGRESO, null);
        agregarNovedadLista(novedadCotizante);
		novedadCotizante = crearNovedadCotizanteDTO(
				TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL, line,
				ConstanteCampoArchivo.IGE, ConstanteCampoArchivo.FECHA_INICIO_IGE, ConstanteCampoArchivo.FECHA_FIN_IGE);
		agregarNovedadLista(novedadCotizante);
		novedadCotizante = crearNovedadCotizanteDTO(
				TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL, line,
				ConstanteCampoArchivo.LMA, ConstanteCampoArchivo.FECHA_INICIO_LMA, ConstanteCampoArchivo.FECHA_FIN_LMA);
		agregarNovedadLista(novedadCotizante);
		novedadCotizante = crearNovedadCotizanteDTO(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL, line,
				ConstanteCampoArchivo.IRL, ConstanteCampoArchivo.FECHA_INICIO_IRL, ConstanteCampoArchivo.FECHA_FIN_IRL);
		agregarNovedadLista(novedadCotizante);
		return lstNovedadCotizanteDTO;
	}

	/**
	 * Método encargado de agregar la novedad de listas
	 * 
	 * @param novedad,
	 *            novedad a agregar a cotizante
	 */
	private void agregarNovedadLista(NovedadCotizanteDTO novedad) {
		if (novedad != null) {
			lstNovedadCotizanteDTO.add(novedadCotizante);
		}
		novedadCotizante = null;
	}

	/**
	 * Método encargado de crear una novedad de cotizante dto
	 * 
	 * @param tipoNovedad,
	 *            tipo de novedad
	 * @param contexto,
	 *            contenido de la informacion
	 * @param campoArchivo,
	 *            campo de la novedad en el contexto
	 * @param campoFechaInicio,
	 *            campo fecha inicio
	 * @param campoFechaFin,
	 *            campo fecha fin
	 * @return retorna la NovedadCotizanteDTO
	 */
	private NovedadCotizanteDTO crearNovedadCotizanteDTO(TipoTransaccionEnum tipoNovedad, Map<String, Object> contexto,
			String campoArchivo, String campoFechaInicio, String campoFechaFin) {
        NovedadCotizanteDTO novedadCotizanteDTO = null;
        String campo = (String) contexto.get(campoArchivo);
        if (campo != null && (campo.contains("X") || campo.contains("x"))) {
            novedadCotizanteDTO = new NovedadCotizanteDTO();
			novedadCotizanteDTO.setTipoNovedad(tipoNovedad);
			novedadCotizanteDTO.setCondicionNueva(true);
			if (campoFechaInicio != null && (contexto.get(campoFechaInicio) != null)) {
			    Date fechaInicio= CalendarUtils.convertirFechaAnoMesDia(contexto.get(campoFechaInicio).toString());
                novedadCotizanteDTO.setFechaInicio(fechaInicio !=null ? fechaInicio.getTime():null);
			}
			if (campoFechaFin != null && (contexto.get(campoFechaFin) != null)) {
			    Date fechaFin= CalendarUtils.convertirFechaAnoMesDia(contexto.get(campoFechaFin).toString());
			    novedadCotizanteDTO.setFechaFin(fechaFin!=null ? fechaFin.getTime():null);
			}
		}        
		if (TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL.equals(tipoNovedad)
				&& campo != null && !campo.isEmpty()) {
			Long campoIrl = new Long (campo);
			if (campoIrl != null && campoIrl >= ConstanteCampoArchivo.DIA_COTIZADO_INICIO
					&& campoIrl <= ConstanteCampoArchivo.DIA_COTIZADO_FINAL) {
	            novedadCotizanteDTO = new NovedadCotizanteDTO();
				novedadCotizanteDTO.setTipoNovedad(tipoNovedad);
				novedadCotizanteDTO.setCondicionNueva(true);
				if (campoFechaInicio != null && (contexto.get(campoFechaInicio) != null)) {
				    Date fechaInicio= CalendarUtils.convertirFechaAnoMesDia(contexto.get(campoFechaInicio).toString());
	                novedadCotizanteDTO.setFechaInicio(fechaInicio !=null ? fechaInicio.getTime():null);
				}
				if (campoFechaFin != null && (contexto.get(campoFechaFin) != null)) {
				    Date fechaFin= CalendarUtils.convertirFechaAnoMesDia(contexto.get(campoFechaFin).toString());
				    novedadCotizanteDTO.setFechaFin(fechaFin!=null ? fechaFin.getTime():null);
				}
			}  
			
        }
        
		return novedadCotizanteDTO;
	} 
}
