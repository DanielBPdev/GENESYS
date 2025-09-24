package com.asopagos.aportes.masivos.util;


import com.asopagos.aportes.masivos.dto.*;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.DefinicionCamposCargaDTO;
import co.com.heinsohn.lion.fileCommon.dto.DetailedErrorDTO;
import co.com.heinsohn.lion.fileprocessing.dto.FileLoaderOutDTO;
import com.asopagos.aportes.masivos.service.business.interfaces.IConsultasModeloCore;
import javax.inject.Inject;
import com.asopagos.dto.aportes.CorreccionAportanteDTO;
import com.asopagos.aportes.composite.dto.CorreccionDTO;
import com.asopagos.aportes.clients.ConsultarRegistroDetallado;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.dto.aportes.EvaluacionDTO;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.enumeraciones.pila.MarcaRegistroAporteArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.aportes.OrigenAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;


public class ArchivosAportesMasivosUtils {

    /**
     * Ya que el procesamiento de aportes es a nivel cotizante,
     * se debe agrupar a nivel detalle los aportes teniendo en cuenta
     * el periodo de pago y numero de identificacion del aportante
     * 
     * @param aportes
     * @return
     */

    private static final ILogger logger = LogManager.getLogger(ArchivosAportesMasivosUtils.class);

    //@Inject no es un bean por lo que no es posible injectar dependencias
	private IConsultasModeloCore consultasCore;

    public ArchivosAportesMasivosUtils(IConsultasModeloCore consultasCore) {
        this.consultasCore = consultasCore;
    }

    public ArchivosAportesMasivosUtils() {}
      
    public List<ResultadoValidacionAporteDTO> agruparAportes(
        List<ResultadoAporteMasivoDTO> listaDetalle, Long idCargue) {
            logger.info("Inicio metodo agrupar aportes");
            Map<String, ResultadoValidacionAporteDTO> agrupadorAportes = new HashMap<>();
            
            for (ResultadoAporteMasivoDTO datoCotizante : listaDetalle) {
                logger.info(datoCotizante.toString());
                String llave = datoCotizante.getTipoIdentificacionAportante().name();
                llave += datoCotizante.getNumeroIdentificacionAportante();
                Date periodoPago = datoCotizante.getPeriodoPago();
                LocalDate localDate = periodoPago.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                llave += localDate.format(DateTimeFormatter.ofPattern("MM-yyyy"));
                Date fechaRecepcionAporte = datoCotizante.getFechaRecepcionAporte()!=null?datoCotizante.getFechaRecepcionAporte():new Date();
                LocalDate localDate2 = fechaRecepcionAporte.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                llave += localDate2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Date fechaPago = datoCotizante.getFechaDePago();
                LocalDate localDate3 = fechaPago.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                llave += localDate3.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                // Se agrega a la llave compuesta el tipo de Empleador
                String tipoAportante = datoCotizante.getTipoAportante().getDescripcion();
                llave += tipoAportante;
                logger.info("Inicio metodo agrupar aportes, " + llave);

                // Llave unica por tipo de identificacion aportante, numero de identificacion
                // Y periodo de pago para agrupacion de aportes
                if (!agrupadorAportes.containsKey(llave)) {
                    ResultadoValidacionAporteDTO res = new ResultadoValidacionAporteDTO();

                    DatosRadicacionMasivaDTO radicacionAportante = toDatosRadicacionMasiva(datoCotizante);
                    res.setEcmIdentificador(datoCotizante.getEcmIdentificador());
                    res.setResultadoDatosRadicacion(radicacionAportante);
                    res.setIdCargue(idCargue);
                    List<ResultadoCotizanteAporteMasivoDTO> cotizantes = new ArrayList<ResultadoCotizanteAporteMasivoDTO>();
                    res.setResultadoCotizantesMasivos(cotizantes);
                    res.getResultadoCotizantesMasivos().add(
                        agregarNuevoCotizante(datoCotizante));

                    agrupadorAportes.put(llave, res);
                } else {
                    List<ResultadoCotizanteAporteMasivoDTO> cotizantes = agrupadorAportes.get(llave).getResultadoCotizantesMasivos();
                    cotizantes.add(agregarNuevoCotizante(datoCotizante));
                    // se agrega datos de cotizante para el aporte
                }
            }


        List<ResultadoValidacionAporteDTO> aportes = new ArrayList<>(agrupadorAportes.values());
        return aportes;
    }


    private DatosRadicacionMasivaDTO toDatosRadicacionMasiva(ResultadoAporteMasivoDTO cotizante) {
        DatosRadicacionMasivaDTO datosRadicacionMasivaDTO = new DatosRadicacionMasivaDTO();
        datosRadicacionMasivaDTO.setTipoIdentificacion(cotizante.getTipoIdentificacionAportante());
        datosRadicacionMasivaDTO.setNumeroIdentificacion(cotizante.getNumeroIdentificacionAportante());
        datosRadicacionMasivaDTO.setRazonSocialAportante(cotizante.getRazonSocial());
        datosRadicacionMasivaDTO.setIdDepartamento(cotizante.getIdDepartamento());
        datosRadicacionMasivaDTO.setIdMunicipio(cotizante.getIdMunicipio());
        datosRadicacionMasivaDTO.setRazonSocial(cotizante.getRazonSocial());
        datosRadicacionMasivaDTO.setPeriodoPago(cotizante.getPeriodoPago());
        datosRadicacionMasivaDTO.setFechaRecepcionAporte(cotizante.getFechaRecepcionAporte());
        datosRadicacionMasivaDTO.setFechaDePago(cotizante.getFechaDePago());
        datosRadicacionMasivaDTO.setTipoAportante(cotizante.getTipoAportante());

        return datosRadicacionMasivaDTO;
    }

    private ResultadoCotizanteAporteMasivoDTO agregarNuevoCotizante(ResultadoAporteMasivoDTO aporte) {
        ResultadoCotizanteAporteMasivoDTO cotizante = new ResultadoCotizanteAporteMasivoDTO();
        
        cotizante.setTipoIdentificacionCotizante(aporte.getTipoIdentificacionCotizante());
        cotizante.setTipoCotizante(aporte.getTipoCotizante());
        cotizante.setNumeroIdentificacionCotizante(aporte.getNumeroIdentificacionCotizante());
        cotizante.setNumeroDocumentoCotizante(aporte.getNumeroDocumentoCotizante());
        cotizante.setPrimerNombreCotizante(aporte.getPrimerNombreCotizante());
        cotizante.setSegundoNombreCotizante(aporte.getSegundoNombreCotizante());
        cotizante.setPrimerApellidoCotizante(aporte.getPrimerApellidoCotizante());
        cotizante.setSegundoApellidoCotizante(aporte.getSegundoApellidoCotizante());
        cotizante.setConceptoDePago(aporte.getConceptoDePago());
        cotizante.setIbc(aporte.getIbc());
        cotizante.setIng(aporte.getIng());
        cotizante.setRet(aporte.getRet());
        cotizante.setIrl(aporte.getIrl());
        cotizante.setVsp(aporte.getVsp());
        cotizante.setVst(aporte.getVst());
        cotizante.setSln(aporte.getSln());
        cotizante.setIge(aporte.getIge());
        cotizante.setLma(aporte.getLma());
        cotizante.setVac(aporte.getVac());
        cotizante.setSus(aporte.getSus());
        cotizante.setSalarioBasico(aporte.getSalarioBasico());
        cotizante.setDiasCotizados(aporte.getDiasCotizados());
        cotizante.setDiasMora(aporte.getDiasMora());
        cotizante.setTarifa(aporte.getTarifa());
        cotizante.setNumeroDeHorasLaboradas(aporte.getNumeroDeHorasLaboradas());
        cotizante.setAporteObligatorio(aporte.getAporteObligatorio());
        cotizante.setValorIntereses(aporte.getValorIntereses());
        cotizante.setTotalAporte(aporte.getTotalAporte());
        //cotizante.setLinea(aporte.getLinea());
        

        return cotizante;

    }
    

    private Date convertirFecha(String fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(fecha);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * Nuestro metodo robado de NovedadesCargueMultiple
     */

    public List<ResultadoHallazgosValidacionArchivoDTO> consultarListaHallazgos(Long fileDefinitionId, FileLoaderOutDTO outDTO) {
        // Lista de errores
        List<ResultadoHallazgosValidacionArchivoDTO> listaHallazgos = new ArrayList<>();
        // Campos asociados al archivo
        List<DefinicionCamposCargaDTO> campos = consultarCamposDelArchivo(fileDefinitionId);
        // Se verifica si se registraron errores en la tabla FileLoadedLog
        if (outDTO.getFileLoadedId() != null && outDTO.getDetailedErrors() != null && !outDTO.getDetailedErrors().isEmpty()) {
            // Se recorren los errores y se crean los respectivos hallazgos
            for (DetailedErrorDTO detalleError : outDTO.getDetailedErrors()) {
                listaHallazgos.add(obtenerHallazgo(campos, detalleError.getMessage(), detalleError.getLineNumber()));
            }
        }
        return listaHallazgos;
    }


    /**
     * Nuestro metodo robado de NovedadesCargueMultiple
     */

    private ResultadoHallazgosValidacionArchivoDTO obtenerHallazgo(List<DefinicionCamposCargaDTO> campos, String mensaje, Long lineNumber) {
        ResultadoHallazgosValidacionArchivoDTO hallazgo = null;
        // Indica si el mensaje contiene el nombre del campo
        Boolean encontroCampo = Boolean.FALSE;
        // Se separa el mensaje por caracter ;
        String[] arregloMensaje = mensaje.split(";");
        // Se verifica si el mensaje contiene algún campo
        for (DefinicionCamposCargaDTO campo : campos) {
            for (int i = 0; i < arregloMensaje.length; i++) {
                if (arregloMensaje[i].contains(campo.getName())) {
                    mensaje = arregloMensaje[i].replace(campo.getName(), campo.getLabel());
                    hallazgo = crearHallazgo(lineNumber, campo.getLabel(), mensaje);
                    encontroCampo = Boolean.TRUE;
                    break;
                }
            }
        }
        // Si no se encontro campo se crea el hallazgo sin campo
        if (!encontroCampo) {
            hallazgo = crearHallazgo(lineNumber, "", mensaje);
        }
        return hallazgo;
    }


    /**
	 * Metodo encargado retornar un DTO que se construye con los datos que
	 * llegan por parametro
	 * 
	 * @param lineNumber
	 * @param campo
	 * @param errorMessage
	 * @return retorna el resultado hallazgo validacion
	 */
	private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
		ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
		hallazgo.setNumeroLinea(lineNumber);
		hallazgo.setNombreCampo(campo);
		hallazgo.setError(errorMessage);
		return hallazgo;
	}
    

    private List<DefinicionCamposCargaDTO> consultarCamposDelArchivo(Long fileLoadedId) {
        return consultasCore.consultarCamposDelArchivo(fileLoadedId);
	}


    public CorreccionDTO guardarResultadoValidacionEnCorreccion(CorreccionAportanteDTO resultadoSimulacion, CorreccionDTO datoTemporal) {

        //TODO realizar el seteo de campos dados en la simulacion para persisir la correccion
        /**
         * lstCotizantes:
            -> evaluacion:
                -> valorAporte:
                -> persona
                -> depV0
                -> devV1
                -> depV2
                -> idv1
                -> pdv1
            -> evaluacionSimulada (misma)
            -> aportes
                -> estadoAporte
                -> aporteFinal
                -> interesesFinales
                -> tipoAjusteMovimiento
                -> cumple
                -> diasCotizado
                -> diasCotizadoNuevo
                -> salarioBasico
                -> salarioBasicoNuevo
                -> ibc
                -> ibcNuevo
                -> salarioIntegal
                -> salarioIntegralNievo
                -> numeroHoasLaboral
                -> numeroHorasLaboralNuevo
                -> montoAporte
                -> montoAporteNuevo
                -> aporteObligatorio
                -> aporteObligatorioNuevo
            -> historico (null)
            -> evaluacionSimulacion: OK
            -> correccion 
                ->tipoAportante
                -> tipoIdentificacion
                -> numeroIdentificacion
                ->empleadorPersona
                ->tipoPersona
                ->claseAportante
                ->origenAportante: CORRECCION_APORTE
                -> comentario: null
                -> cotizantesBusqueda: (de cotizantes nuveos de la correccion)
                -> cotizantesNuevos (el mismo cotizante de la correccion)
         */
        ConsultarRegistroDetallado servicio = new ConsultarRegistroDetallado(resultadoSimulacion.getIdRegistroGeneral());
        servicio.execute();
        List<RegistroDetalladoModeloDTO> lstRegistrosDetallados = servicio.getResult();

        datoTemporal.setLstCotizantes(asignarEvaluacionCotizante(
            datoTemporal.getLstCotizantes(),
            lstRegistrosDetallados,
            true
        ));

        List<CotizanteDTO> cotizantes = new ArrayList<>();

        for (CotizanteDTO  cotizante : datoTemporal.getLstCotizantes()) {
            CorreccionAportanteDTO correccion = new CorreccionAportanteDTO();

            CotizanteDTO cot = new CotizanteDTO();

            correccion.setTipoAportante(resultadoSimulacion.getTipoAportante());
            correccion.setTipoIdentificacion(resultadoSimulacion.getTipoIdentificacion());
            correccion.setNumeroIdentificacion(resultadoSimulacion.getNumeroIdentificacion());
            correccion.setEmpleadorPersona(resultadoSimulacion.getEmpleadorPersona());
            correccion.setTipoPersona(resultadoSimulacion.getTipoPersona());
            correccion.setSeAcogeBeneficios(resultadoSimulacion.getSeAcogeBeneficios());
            correccion.setClaseAportante(resultadoSimulacion.getClaseAportante());
            correccion.setPeriodoPago(resultadoSimulacion.getPeriodoPago());
            correccion.setDv(resultadoSimulacion.getDv());
            correccion.setRazonSocial(resultadoSimulacion.getRazonSocial());
            correccion.setIdRegistroGeneral(resultadoSimulacion.getIdRegistroGeneral());
            correccion.setPagadorPorTerceros(resultadoSimulacion.getPagadorPorTerceros());
            correccion.setCotizantesNuevos(Arrays.asList(cotizante));
            correccion.setCotizantesBusqueda(datoTemporal.getLstCotizantes());
            correccion.setOrigenAportante(OrigenAporteEnum.CORRECCION_APORTE);

            cot.setTipoIdentificacion(cotizante.getTipoIdentificacion());
            cot.setNumeroIdentificacion(cotizante.getNumeroIdentificacion());
            cot.setDv(cotizante.getDv());
            cot.setPrimerNombre(cotizante.getPrimerNombre());
            cot.setSegundoNombre(cotizante.getSegundoNombre());
            cot.setPrimerApellido(cotizante.getPrimerApellido());
            cot.setSegundoApellido(cotizante.getSegundoApellido());
            cot.setNombreCompletoCotizante(cotizante.getNombreCompletoCotizante());
            cot.setRazonSocialAportante(cotizante.getRazonSocialAportante());
            cot.setFechaIngreso(cotizante.getFechaIngreso());
            cot.setFechaRetiro(cotizante.getFechaRetiro());
            cot.setTipoCotizante(cotizante.getTipoCotizante());
            cot.setSubtipoCotizante(cotizante.getSubtipoCotizante());
            cot.setDepartamentoLaboral(cotizante.getDepartamentoLaboral());
            cot.setMunicipioLaboral(cotizante.getMunicipioLaboral());
            cot.setDiasCotizados(cotizante.getDiasCotizados());
            cot.setSalarioBasico(cotizante.getSalarioBasico());
            cot.setValorIBC(cotizante.getValorIBC());
            cot.setTarifa(cotizante.getTarifa());
            cot.setAporteObligatorio(cotizante.getAporteObligatorio());
            cot.setCorrecciones(cotizante.getCorrecciones());
            cot.setSalarioIntegral(cotizante.getSalarioIntegral());
            cot.setHorasLaboradas(cotizante.getHorasLaboradas());
            cot.setValorMora(cotizante.getValorMora());
            cot.setEstadoPeriodoPago(cotizante.getEstadoPeriodoPago());
            cot.setTipoAfiliado(cotizante.getTipoAfiliado());
            cot.setTieneModificaciones(cotizante.getTieneModificaciones());
            cot.setAportoPorSiMismo(cotizante.getAportoPorSiMismo());
            cot.setTipoIdentificacionAportante(cotizante.getTipoIdentificacionAportante());
            cot.setNumeroIdentificacionAportante(cotizante.getNumeroIdentificacionAportante());
            cot.setNombreAportante(cotizante.getNombreAportante());
            cot.setGestionado(cotizante.getGestionado());
            cot.setResultado(cotizante.getResultado());
            cot.setMonto(cotizante.getMonto());
            cot.setInteres(cotizante.getInteres());
            cot.setExtranjeroNoObligadoCotizar(cotizante.getExtranjeroNoObligadoCotizar());
            cot.setColombianoExterior(cotizante.getColombianoExterior());
            cot.setComentarioNovedad(cotizante.getComentarioNovedad());
            cot.setCumpleNovedad(cotizante.getCumpleNovedad());
            cot.setIdCotizante(cotizante.getIdCotizante());
            cot.setOrigen(cotizante.getOrigen());
            cot.setIdRegistroDetalladoNuevo(cotizante.getIdRegistroDetalladoNuevo());
            logger.info("Dato registro detallado nuevo: " + cotizante.getIdRegistroDetalladoNuevo());
            cot.setIdEcm(cotizante.getIdEcm());
            cot.setIdRegistro(cotizante.getIdRegistro());
            logger.info("Dato idRegistro: " + cotizante.getIdRegistro());
            cot.setEstadoAporteCotizante(cotizante.getEstadoAporteCotizante());

            cot.setCorreccion(correccion);

            cotizantes.add(cot);

        }
        datoTemporal.setLstCotizantes(cotizantes);
        return datoTemporal;
    }

    private List<CotizanteDTO> asignarEvaluacionCotizante(List<CotizanteDTO> cotizantes,
            List<RegistroDetalladoModeloDTO> registroDetalladoList, boolean aporteManual) {
        logger.info(
                "Inicio de método asignarEvaluacionCotizante(List<CotizanteDTO> cotizantes, List<RegistroDetalladoModeloDTO> registroDetalladoList)");
        for (CotizanteDTO cotizante : cotizantes) {
            for (RegistroDetalladoModeloDTO registroDetalladoModeloDTO : registroDetalladoList) {
                if ((aporteManual && cotizante.getIdCotizante() != null
                        && cotizante.getIdCotizante().equals(registroDetalladoModeloDTO.getId()))
                        || (!aporteManual && cotizante.getIdRegistroDetalladoNuevo() != null
                                && cotizante.getIdRegistroDetalladoNuevo()
                                        .equals(registroDetalladoModeloDTO.getId()))) {
                    EvaluacionDTO evaluacion = new EvaluacionDTO();
                    if (TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE.equals(cotizante.getTipoAfiliado())) {
                        evaluacion.setDepV0(registroDetalladoModeloDTO.getOutEstadoValidacionV0());
                        evaluacion.setDepV1(registroDetalladoModeloDTO.getOutEstadoValidacionV1());
                        evaluacion.setDepV2(registroDetalladoModeloDTO.getOutEstadoValidacionV2());
                        evaluacion.setDepV3(registroDetalladoModeloDTO.getOutEstadoValidacionV3());
                        evaluacion.setIdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setPdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                    } else if (TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE.equals(cotizante.getTipoAfiliado())) {
                        evaluacion.setIdv1(registroDetalladoModeloDTO.getOutEstadoValidacionV1());
                        evaluacion.setDepV0(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV2(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV3(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setPdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                    } else if (TipoAfiliadoEnum.PENSIONADO.equals(cotizante.getTipoAfiliado())) {
                        evaluacion.setPdv1(registroDetalladoModeloDTO.getOutEstadoValidacionV1());
                        evaluacion.setDepV0(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV2(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setDepV3(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                        evaluacion.setIdv1(EstadoValidacionRegistroAporteEnum.NO_APLICA);
                    }
                    evaluacion.setPersona(cotizante.getTipoAfiliado());
                    evaluacion.setValorAporte(registroDetalladoModeloDTO.getAporteObligatorio());
                    if (aporteManual) {
                        cotizante.setEvaluacion(evaluacion);
                    } else {
                        cotizante.setEvaluacionSimulada(evaluacion);
                    }

                    cotizante.setEvaluacionSimulacion(calcularEstadoSimulacion(registroDetalladoModeloDTO));
                }
            }
        }

        logger.info(
                "Fin de método asignarEvaluacionCotizante(List<CotizanteDTO> cotizantes, List<RegistroDetalladoModeloDTO> registroDetalladoList)");
        return cotizantes;
    }

    private EstadoValidacionRegistroAporteEnum calcularEstadoSimulacion(RegistroDetalladoModeloDTO registroDetallado) {
        logger.info("Inicio de método calcularEstadoSimulacion(EvaluacionDTO evaluacion)");

        EstadoValidacionRegistroAporteEnum noOk = EstadoValidacionRegistroAporteEnum.NO_OK;
        if (MarcaRegistroAporteArchivoEnum.NO_VALIDADO_BD
                .equals(registroDetallado.getOutMarcaValidacionRegistroAporte())) {
            return EstadoValidacionRegistroAporteEnum.NO_VALIDADO_BD;
        }
        if (EstadoRegistroAportesArchivoEnum.NO_OK.equals(registroDetallado.getOutEstadoRegistroAporte())
                || registroDetallado.getOutEstadoRegistroAporte() == null
                || registroDetallado.getOutMarcaValidacionRegistroAporte() == null) {
            return noOk;
        }
        if (EstadoValidacionRegistroAporteEnum.NO_CUMPLE.equals(registroDetallado.getOutEstadoValidacionV0())
                || noOk.equals(registroDetallado.getOutEstadoValidacionV2())
                || noOk.equals(registroDetallado.getOutEstadoValidacionV3())) {
            return noOk;
        } else {
            return EstadoValidacionRegistroAporteEnum.OK;
        }
    }

    private List<RegistroDetalladoModeloDTO> consultarRegistroDetallado(Long idRegistroGeneral) {
        logger.info("Inicio de método consultarRegistroDetallado(Long idRegistroGeneral)");
        ConsultarRegistroDetallado consultarRegistroDetalladoService = new ConsultarRegistroDetallado(
                idRegistroGeneral);
        consultarRegistroDetalladoService.execute();
        List<RegistroDetalladoModeloDTO> registroDetalladoList = consultarRegistroDetalladoService.getResult();
        logger.info("Fin de método consultarRegistroDetallado(Long idRegistroGeneral)");
        return registroDetalladoList;
    }

    
}
