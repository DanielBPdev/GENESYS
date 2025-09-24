package com.asopagos.pila.business.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloque;
import com.asopagos.entidades.pila.procesamiento.EstadoArchivoPorBloqueOF;
import com.asopagos.entidades.pila.procesamiento.HistorialEstadoBloque;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoConciliacionArchivoFEnum;
import com.asopagos.enumeraciones.pila.EstadoParejaArchivosEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.ParejaArchivosEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaEstadosValidacion;
import com.asopagos.pila.constants.MensajesGestionEstadosEnum;
import com.asopagos.pila.dto.RespuestaRegistroEstadoDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;

/**
 * <b>Descripción:</b> Clase que se encarga de gestionar el estado por tipo de archivo y realizar la respectiva validacion<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407, 393<br>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:jpiraban@heinsohn.com.co">Jhon Angel Piraban Castellanos </a>
 */
@Stateless
public class GestorEstadosValidacion implements IGestorEstadosValidacion, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Servicios de persistencia para los estados de validación
     */
    @Inject
    private IPersistenciaEstadosValidacion persistenciaEstados;

    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(GestorEstadosValidacion.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.GestorEstadosValidacionInterface#registrarEstadoArchivo(com.asopagos.entidades.pila.
     * IndicePlanilla, com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum, com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum,
     * java.lang.String, java.lang.Integer, java.lang.Long)
     */
    @Override
    public RespuestaRegistroEstadoDTO registrarEstadoArchivo(IndicePlanilla indicePlanilla, EstadoProcesoArchivoEnum estado,
            AccionProcesoArchivoEnum accion, String causa, Integer bloqueValidado, Long idLectura)
            throws ErrorFuncionalValidacionException {

        logger.debug("Inicia registrarEstadoArchivo(IndicePlanilla, EstadoProcesoArchivoEnum, AccionProcesoArchivoEnum, String, "
                + "Integer, Long)");

        RespuestaRegistroEstadoDTO result = new RespuestaRegistroEstadoDTO();
        result.setCausaPuntual(causa);

        // se consulta un estado anterior
        EstadoArchivoPorBloque estadoArchivo = null;

        try {
            estadoArchivo = persistenciaEstados.consultarEstadoEspecificoOI(indicePlanilla);
        } catch (ErrorFuncionalValidacionException e) {
            // se ha de generar un estado por primera vez
        }

        // fecha de actualización del estado
        Date fechaActualizacion = new Date();

        if (estadoArchivo != null) {
            // se encuentra un registro de estados
            boolean registrar = false;

            // entrada de historial de estado
            HistorialEstadoBloque historial = new HistorialEstadoBloque();
            historial.setIdIndicePlanilla(indicePlanilla.getId());

            // se consultan los campos específicos para el bloque elegido
            // el estado se puede actualizar, siempre que el estado actual no se trate de un estado de error
            switch (bloqueValidado) {
                case 0:
                    if(!estadoArchivo.getEstadoBloque0().getReportarBandejaInconsistencias()){
                        estadoArchivo.setEstadoBloque0(estado);
                        estadoArchivo.setAccionBloque0(accion);
                        estadoArchivo.setFechaBloque0(fechaActualizacion);
                        
                        if (estadoArchivo.getEstadoBloque0() != null) {
                            historial.setBloque(BloqueValidacionEnum.BLOQUE_0_OI);
                            historial.setEstado(estadoArchivo.getEstadoBloque0());
                            historial.setAccion(estadoArchivo.getAccionBloque0());
                            historial.setFechaEstado(estadoArchivo.getFechaBloque0());
                        }
                        else {
                            historial = null;
                        }

                        registrar = true;
                    }
                    break;
                case 1:
                    if (!EstadoProcesoArchivoEnum.INCONSISTENCIA_NOMBRE_ARCHIVO.equals(estadoArchivo.getEstadoBloque1())) {
                        estadoArchivo.setEstadoBloque1(estado);
                        estadoArchivo.setAccionBloque1(accion);
                        estadoArchivo.setFechaBloque1(fechaActualizacion);
                        
                        if (estadoArchivo.getEstadoBloque1() != null) {
                            historial.setBloque(BloqueValidacionEnum.BLOQUE_1_OI);
                            historial.setEstado(estadoArchivo.getEstadoBloque1());
                            historial.setAccion(estadoArchivo.getAccionBloque1());
                            historial.setFechaEstado(estadoArchivo.getFechaBloque1());
                        }
                        else {
                            historial = null;
                        }

                        registrar = true;
                    }
                    break;
                case 2:
                    if (!EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA.equals(estadoArchivo.getEstadoBloque2())) {
                        estadoArchivo.setEstadoBloque2(estado);
                        estadoArchivo.setAccionBloque2(accion);
                        estadoArchivo.setFechaBloque2(fechaActualizacion);
                        
                        if (estadoArchivo.getEstadoBloque2() != null) {
                            historial.setBloque(BloqueValidacionEnum.BLOQUE_2_OI);
                            historial.setEstado(estadoArchivo.getEstadoBloque2());
                            historial.setAccion(estadoArchivo.getAccionBloque2());
                            historial.setFechaEstado(estadoArchivo.getFechaBloque2());
                        }
                        else {
                            historial = null;
                        }

                        registrar = true;
                    }
                    break;
                case 3:
                    if (!EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_INCONSISTENTES.equals(estadoArchivo.getEstadoBloque3())) {
                        estadoArchivo.setEstadoBloque3(estado);
                        estadoArchivo.setAccionBloque3(accion);
                        estadoArchivo.setFechaBloque3(fechaActualizacion);
                        
                        if (estadoArchivo.getEstadoBloque3() != null) {
                            historial.setBloque(BloqueValidacionEnum.BLOQUE_3_OI);
                            historial.setEstado(estadoArchivo.getEstadoBloque3());
                            historial.setAccion(estadoArchivo.getAccionBloque3());
                            historial.setFechaEstado(estadoArchivo.getFechaBloque3());
                        }
                        else {
                            historial = null;
                        }

                        registrar = true;
                    }
                    break;
                case 4:
                    // el estado del bloque 4 siempre puede cambiar para marcar que se completó la peristencia
                    estadoArchivo.setEstadoBloque4(estado);
                    estadoArchivo.setAccionBloque4(accion);
                    estadoArchivo.setFechaBloque4(fechaActualizacion);
                    
                    if (estadoArchivo.getEstadoBloque4() != null) {
                        historial.setBloque(BloqueValidacionEnum.BLOQUE_4_OI);
                        historial.setEstado(estadoArchivo.getEstadoBloque4());
                        historial.setAccion(estadoArchivo.getAccionBloque4());
                        historial.setFechaEstado(estadoArchivo.getFechaBloque4());
                    }
                    else {
                        historial = null;
                    }

                    registrar = true;
                    break;
                case 5:
                    if (!EstadoProcesoArchivoEnum.RECHAZADO.equals(estadoArchivo.getEstadoBloque5())) {
                        estadoArchivo.setEstadoBloque5(estado);
                        estadoArchivo.setAccionBloque5(accion);
                        estadoArchivo.setFechaBloque5(fechaActualizacion);
                        
                        if (estadoArchivo.getEstadoBloque5() != null) {
                            historial.setBloque(BloqueValidacionEnum.BLOQUE_5_OI);
                            historial.setEstado(estadoArchivo.getEstadoBloque5());
                            historial.setAccion(estadoArchivo.getAccionBloque5());
                            historial.setFechaEstado(estadoArchivo.getFechaBloque5());
                        }
                        else {
                            historial = null;
                        }

                        registrar = true;
                    }
                    break;
                case 6:
                    // el estado del bloque 6 puede cambiar para marcar la conciliación del aporte o el aporte cero
                    estadoArchivo.setEstadoBloque6(estado);
                    estadoArchivo.setAccionBloque6(accion);
                    estadoArchivo.setFechaBloque6(fechaActualizacion);
                    
                    if (estadoArchivo.getEstadoBloque6() != null) {
                        historial.setBloque(BloqueValidacionEnum.BLOQUE_6_OI);
                        historial.setEstado(estadoArchivo.getEstadoBloque6());
                        historial.setAccion(estadoArchivo.getAccionBloque6());
                        historial.setFechaEstado(estadoArchivo.getFechaBloque6());
                    }
                    else {
                        historial = null;
                    }
                    
                    registrar = true;
                    break;
                default:
                	logger.debug("No hay estado para ingresar");
                    break;
            }

            if (registrar) {
                persistenciaEstados.actualizarEstadoOI(estadoArchivo);
                if (historial != null) {
                    persistenciaEstados.registrarHistoricoEstado(historial);
                }
            }
            else {
                String mensaje = MensajesGestionEstadosEnum.ERROR_ESTADO_NO_PROCEDENTE.getReadableMessage();
                logger.error("Finaliza registrarEstadoArchivo(IndicePlanilla, EstadoProcesoArchivoEnum, AccionProcesoArchivoEnum, String, "
                        + "Integer, Long) - " + mensaje);
                throw new ErrorFuncionalValidacionException(mensaje, new Throwable());
            }
        }
        else {
            // se crea el registro del estado por primera vez
            estadoArchivo = new EstadoArchivoPorBloque();

            estadoArchivo.setIndicePlanilla(indicePlanilla);

            estadoArchivo.setTipoArchivo(indicePlanilla.getTipoArchivo());

            // el dato de estado se registra de acuerdo al bloque
            switch (bloqueValidado) {
                case 0:
                    estadoArchivo.setEstadoBloque0(estado);
                    estadoArchivo.setAccionBloque0(accion);
                    estadoArchivo.setFechaBloque0(fechaActualizacion);
                    break;
            }

            persistenciaEstados.registrarEstadoOI(estadoArchivo);
        }

        result.setCausaPuntual("");
        result.setEstado(estado);
        result.setAccion(accion);

        logger.debug("Finaliza registrarEstadoArchivo(IndicePlanilla, EstadoProcesoArchivoEnum, AccionProcesoArchivoEnum, String, "
                + "Integer, Long)");

        return result;
    }

    /**
     * Este metodo se encarga de comparar el estado por bloque
     * @param IndicePlanilla
     *        valor indicePlanilla a consultar
     * @param bloque
     *        valor del bloque a consultar
     * @return RespuestaRegistroEstadoDTO objeto con la respuesta
     * @exception FileProcessingException
     *            excepcion lanzada por procesar el archivo
     */
    public RespuestaRegistroEstadoDTO parejaArchivosAprobadaPorBloque(IndicePlanilla indicePlanilla, Integer bloque)
            throws ErrorFuncionalValidacionException {
        logger.debug("Inicia parejaArchivosAprobadaPorBloque(IndicePlanilla, Integer)");
        RespuestaRegistroEstadoDTO respuesta = new RespuestaRegistroEstadoDTO();

        // se preparan estados para cada tipo de archivo
        EstadoProcesoArchivoEnum estadoA = null;
        EstadoProcesoArchivoEnum estadoI = null;
        EstadoProcesoArchivoEnum estadoAR = null;
        EstadoProcesoArchivoEnum estadoIR = null;

        // indicador de planilla de pensionados
        boolean pensionado = false;

        List<EstadoArchivoPorBloque> result = persistenciaEstados.consultarEstadoGeneralOI(indicePlanilla.getIdPlanilla(),
                indicePlanilla.getCodigoOperadorInformacion());

        // se define un listado de estados no válidos para avanzar en la validación del bloque respectivo
        List<EstadoProcesoArchivoEnum> estadosNoValidos = new ArrayList<>();

        // se prepara el listado de estados no válidos por bloque
        switch (bloque) {
            case 0:
                estadosNoValidos.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_DUPLICADO);
                estadosNoValidos.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_NO_VALIDA);
                estadosNoValidos.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_DOBLE);
                estadosNoValidos.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_GRUPO_NO_VALIDO);
                estadosNoValidos.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_CERO);
                estadosNoValidos.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_EXCEDIDO);
                estadosNoValidos.add(EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_EXCEDIDO_GRUPO);
                break;
            case 2:
                estadosNoValidos.add(EstadoProcesoArchivoEnum.ESTRUCTURA_VALIDADA_CON_INCONSISTENCIA);
                break;
            case 3:
                estadosNoValidos.add(EstadoProcesoArchivoEnum.PAREJA_DE_ARCHIVOS_INCONSISTENTES);
                break;
            case 4:
                estadosNoValidos.add(EstadoProcesoArchivoEnum.PERSISTENCIA_ARCHIVO_FALLIDA);
                break;
            default:
                break;
        }

        // es estado anulado siempre es no válido
        estadosNoValidos.add(EstadoProcesoArchivoEnum.ANULADO);

        for (EstadoArchivoPorBloque registro : result) {

            // se toman los estados dependiendo del bloque
            switch (bloque) {
                case 0:
                    if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(registro.getTipoArchivo().getSubtipo())
                            && !registro.getTipoArchivo().isReproceso()) {
                        estadoA = actualizarEstadoParcial(registro.getEstadoBloque0(), estadoA, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(registro.getTipoArchivo().getSubtipo())
                            && registro.getTipoArchivo().isReproceso()) {
                        estadoAR = actualizarEstadoParcial(registro.getEstadoBloque0(), estadoAR, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(registro.getTipoArchivo().getSubtipo())
                            && !registro.getTipoArchivo().isReproceso()) {
                        estadoI = actualizarEstadoParcial(registro.getEstadoBloque0(), estadoI, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(registro.getTipoArchivo().getSubtipo())
                            && registro.getTipoArchivo().isReproceso()) {
                        estadoIR = actualizarEstadoParcial(registro.getEstadoBloque0(), estadoIR, estadosNoValidos);
                    }
                    break;

                case 1:
                    if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(registro.getTipoArchivo().getSubtipo())
                            && !registro.getTipoArchivo().isReproceso()) {
                        estadoA = actualizarEstadoParcial(registro.getEstadoBloque1(), estadoA, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(registro.getTipoArchivo().getSubtipo())
                            && registro.getTipoArchivo().isReproceso()) {
                        estadoAR = actualizarEstadoParcial(registro.getEstadoBloque1(), estadoAR, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(registro.getTipoArchivo().getSubtipo())
                            && !registro.getTipoArchivo().isReproceso()) {
                        estadoI = actualizarEstadoParcial(registro.getEstadoBloque1(), estadoI, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(registro.getTipoArchivo().getSubtipo())
                            && registro.getTipoArchivo().isReproceso()) {
                        estadoIR = actualizarEstadoParcial(registro.getEstadoBloque1(), estadoIR, estadosNoValidos);
                    }
                    break;
                case 2:
                    if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(registro.getTipoArchivo().getSubtipo())
                            && !registro.getTipoArchivo().isReproceso()) {
                        estadoA = actualizarEstadoParcial(registro.getEstadoBloque2(), estadoA, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(registro.getTipoArchivo().getSubtipo())
                            && registro.getTipoArchivo().isReproceso()) {
                        estadoAR = actualizarEstadoParcial(registro.getEstadoBloque2(), estadoAR, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(registro.getTipoArchivo().getSubtipo())
                            && !registro.getTipoArchivo().isReproceso()) {
                        estadoI = actualizarEstadoParcial(registro.getEstadoBloque2(), estadoI, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(registro.getTipoArchivo().getSubtipo())
                            && registro.getTipoArchivo().isReproceso()) {
                        estadoIR = actualizarEstadoParcial(registro.getEstadoBloque2(), estadoIR, estadosNoValidos);
                    }
                    break;
                case 3:
                    if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(registro.getTipoArchivo().getSubtipo())
                            && !registro.getTipoArchivo().isReproceso()) {
                        estadoA = actualizarEstadoParcial(registro.getEstadoBloque3(), estadoA, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(registro.getTipoArchivo().getSubtipo())
                            && registro.getTipoArchivo().isReproceso()) {
                        estadoAR = actualizarEstadoParcial(registro.getEstadoBloque3(), estadoAR, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(registro.getTipoArchivo().getSubtipo())
                            && !registro.getTipoArchivo().isReproceso()) {
                        estadoI = actualizarEstadoParcial(registro.getEstadoBloque3(), estadoI, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(registro.getTipoArchivo().getSubtipo())
                            && registro.getTipoArchivo().isReproceso()) {
                        estadoIR = actualizarEstadoParcial(registro.getEstadoBloque3(), estadoIR, estadosNoValidos);
                    }
                    break;
                case 4:
                    if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(registro.getTipoArchivo().getSubtipo())
                            && !registro.getTipoArchivo().isReproceso()) {
                        estadoA = actualizarEstadoParcial(registro.getEstadoBloque4(), estadoA, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(registro.getTipoArchivo().getSubtipo())
                            && registro.getTipoArchivo().isReproceso()) {
                        estadoAR = actualizarEstadoParcial(registro.getEstadoBloque4(), estadoAR, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(registro.getTipoArchivo().getSubtipo())
                            && !registro.getTipoArchivo().isReproceso()) {
                        estadoI = actualizarEstadoParcial(registro.getEstadoBloque4(), estadoI, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(registro.getTipoArchivo().getSubtipo())
                            && registro.getTipoArchivo().isReproceso()) {
                        estadoIR = actualizarEstadoParcial(registro.getEstadoBloque4(), estadoIR, estadosNoValidos);
                    }
                    break;
                case 5:
                    if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(registro.getTipoArchivo().getSubtipo())
                            && !registro.getTipoArchivo().isReproceso()) {
                        estadoA = actualizarEstadoParcial(registro.getEstadoBloque5(), estadoA, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(registro.getTipoArchivo().getSubtipo())
                            && registro.getTipoArchivo().isReproceso()) {
                        estadoAR = actualizarEstadoParcial(registro.getEstadoBloque5(), estadoAR, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(registro.getTipoArchivo().getSubtipo())
                            && !registro.getTipoArchivo().isReproceso()) {
                        estadoI = actualizarEstadoParcial(registro.getEstadoBloque5(), estadoI, estadosNoValidos);
                    }
                    else if (SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(registro.getTipoArchivo().getSubtipo())
                            && registro.getTipoArchivo().isReproceso()) {
                        estadoIR = actualizarEstadoParcial(registro.getEstadoBloque5(), estadoIR, estadosNoValidos);
                    }
                    break;
                default:
                    break;
            }

            if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(registro.getTipoArchivo().getGrupo()))
                pensionado = true;
        }

        // una vez leídos los estados, se comparan por pareja

        if (estadoAR == null && estadoIR == null) {

            if (estadoA != null && estadoI != null) {
                // se comparan los archivos normales (A e I)

                // sí los estados coinciden, pero se encuentran anulados
                if (estadoA.equals(estadoI) && estadosNoValidos.contains(estadoA)) {
                    respuesta.setCausaPuntual(EstadoParejaArchivosEnum.CONCUERDAN_ANULADOS.getMensajeEstado());
                    respuesta.setEstadoPareja(EstadoParejaArchivosEnum.CONCUERDAN_ANULADOS);
                }
                else if (estadoA.equals(estadoI)) {
                    // los estados coinciden sin estar anulados

                    respuesta.setEstadoPareja(EstadoParejaArchivosEnum.CONCUERDAN);
                    respuesta.setCausaPuntual(EstadoParejaArchivosEnum.CONCUERDAN.getMensajeEstado());
                    if (pensionado)
                        respuesta.setParejaArchivos(ParejaArchivosEnum.ARCHIVOS_AP_IP);
                    else
                        respuesta.setParejaArchivos(ParejaArchivosEnum.ARCHIVOS_A_I);
                }
                else if (estadosNoValidos.contains(estadoI)) {
                    // el archivo I se encuentra anulado
                    respuesta.setEstadoPareja(EstadoParejaArchivosEnum.ARCHIVO_I_ANULADO);
                    respuesta.setCausaPuntual(EstadoParejaArchivosEnum.ARCHIVO_I_ANULADO.getMensajeEstado());
                }
            }
            else if ((estadoA != null && estadoI == null) || (estadoAR != null && estadoIR == null)) {

                respuesta.setEstadoPareja(EstadoParejaArchivosEnum.SOLO_ARCHIVO_A);
                respuesta.setCausaPuntual(EstadoParejaArchivosEnum.SOLO_ARCHIVO_A.getMensajeEstado());

            }
            else if ((estadoA == null && estadoI != null) || (estadoAR == null && estadoIR != null)) {

                respuesta.setEstadoPareja(EstadoParejaArchivosEnum.SOLO_ARCHIVO_I);
                respuesta.setCausaPuntual(EstadoParejaArchivosEnum.SOLO_ARCHIVO_I.getMensajeEstado());

            }
        }
        else if (estadoAR != null && estadoIR != null) {
            // se comparan los archivos de reproceso (AR e IR)

            // sí los estados coinciden, pero se encuentran anulados
            if (estadoAR.equals(estadoIR) && estadosNoValidos.contains(estadoAR)) {
                respuesta.setCausaPuntual(EstadoParejaArchivosEnum.CONCUERDAN_ANULADOS.getMensajeEstado());
                respuesta.setEstadoPareja(EstadoParejaArchivosEnum.CONCUERDAN_ANULADOS);
            }
            else if (estadoAR.equals(estadoIR)) {
                // los estados coinciden sin estar anulados

                respuesta.setEstadoPareja(EstadoParejaArchivosEnum.CONCUERDAN);
                respuesta.setCausaPuntual(EstadoParejaArchivosEnum.CONCUERDAN.getMensajeEstado());
                if (pensionado)
                    respuesta.setParejaArchivos(ParejaArchivosEnum.ARCHIVOS_APR_IPR);
                else
                    respuesta.setParejaArchivos(ParejaArchivosEnum.ARCHIVOS_AR_IR);
            }
            else if (estadosNoValidos.contains(estadoIR)) {
                // el archivo IR se encuentra anulado
                respuesta.setEstadoPareja(EstadoParejaArchivosEnum.ARCHIVO_IR_ANULADO);
                respuesta.setCausaPuntual(EstadoParejaArchivosEnum.ARCHIVO_IR_ANULADO.getMensajeEstado());
            }
        }
        logger.debug("Finaliza parejaArchivosAprobadaPorBloque(IndicePlanilla, Integer)");
        return respuesta;
    }

    /**
     * Método encargado del determinar sí el estado del bloque se actualiza o no en la variable
     * @param estadoBloque
     *        Estado del bloque que se enceuntra en el índice
     * @param valorActual
     *        Estado almacenado en la variable
     * @param estadosNoValidos
     *        Listado de los estados no válidos
     * @return <b>EstadoProcesoArchivoEnum</b>
     *         Estado actualizado para la variable
     */
    private EstadoProcesoArchivoEnum actualizarEstadoParcial(EstadoProcesoArchivoEnum estadoBloque, EstadoProcesoArchivoEnum valorActual,
            List<EstadoProcesoArchivoEnum> estadosNoValidos) {
        String firmaMetodo = "GestorEstadosValidacion.actualizarEstadoParcial(EstadoProcesoArchivoEnum, EstadoProcesoArchivoEnum, "
                + "List<EstadoProcesoArchivoEnum>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        if (valorActual == null || estadosNoValidos.contains(valorActual)) {
            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return estadoBloque;
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return valorActual;
    }

    /**
     * Este metodo se encarga de registrar el estado en la pareja de archivos
     * @param IndicePlanillaA
     *        valor indicePlanilla a consultar
     * @param indicePlanillaI
     *        valor indiceplanilla
     * @param bloque
     *        valor del bloque a consultar
     * @param EstadoProcesoArchivoEnum
     *        estado del archivo
     * @param AccionProcesoArchivoEnum
     *        accion sobre el proceso
     * @param causa
     * @exception FileProcessingException
     *            excepcion lanzada por procesar el archivo
     */
    public void registrarEstadoParejaArchivos(IndicePlanilla indicePlanillaA, IndicePlanilla indicePlanillaI, Integer bloque,
            EstadoProcesoArchivoEnum estado, AccionProcesoArchivoEnum accion, String causa) throws ErrorFuncionalValidacionException {

        logger.debug("Inicia registrarEstadoParejaArchivos(IndicePlanilla, IndicePlanilla, Integer, EstadoProcesoArchivoEnum, "
                + "AccionProcesoArchivoEnum, String)");

        registrarEstadoArchivo(indicePlanillaA, estado, accion, causa, bloque, null);
        registrarEstadoArchivo(indicePlanillaI, estado, accion, causa, bloque, null);

        logger.debug("Finaliza registrarEstadoParejaArchivos(IndicePlanilla, IndicePlanilla, Integer, EstadoProcesoArchivoEnum, "
                + "AccionProcesoArchivoEnum, String)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IGestorEstadosValidacion#registrarEstadoArchivoOF(com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF,
     *      com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum, com.asopagos.enumeraciones.pila.AccionProcesoArchivoEnum,
     *      java.lang.String, java.lang.Integer)
     */
    @Override
    public void registrarEstadoArchivoOF(IndicePlanillaOF indicePlanilla, EstadoProcesoArchivoEnum estado, AccionProcesoArchivoEnum accion,
            String causa, Integer bloqueValidado) throws ErrorFuncionalValidacionException {
        logger.debug("Inicia registrarEstadoArchivoOF(IndicePlanillaOF, EstadoProcesoArchivoEnum, AccionProcesoArchivoEnum, "
                + "String, Integer, Long)");
        EstadoArchivoPorBloqueOF estadoArchivo = null;

        try {
            estadoArchivo = persistenciaEstados.consultarEstadoOF(indicePlanilla);
        } catch (ErrorFuncionalValidacionException e) {
            // no se encuentran estados previos, no es un error en este punto
        }

        if (estadoArchivo == null) {
            // se crea un nuevo registro de estado
            estadoArchivo = new EstadoArchivoPorBloqueOF();

            // en este punto, sólo es válido que se esté trabajando con el bloque 0
            if (bloqueValidado.compareTo(0) == 0) {
                estadoArchivo.setIndicePlanillaOF(indicePlanilla);
                estadoArchivo.setEstadoBloque0(estado);
                estadoArchivo.setAccionBloque0(accion);
                estadoArchivo.setFechaBloque0(new Date());

                persistenciaEstados.registrarEstadoOF(estadoArchivo);
            }
        }
        else {
            // se modifican los valores de acuerdo al bloque que se valida
            // entrada de historial de estados
            HistorialEstadoBloque historial = new HistorialEstadoBloque();
            historial.setIdIndicePlanillaOF(indicePlanilla.getId());

            switch (bloqueValidado) {
                case 1:
                    if (estadoArchivo.getEstadoBloque1() != null) {
                        historial.setBloque(BloqueValidacionEnum.BLOQUE_1_OF);
                        historial.setEstado(estadoArchivo.getEstadoBloque1());
                        historial.setAccion(estadoArchivo.getAccionBloque1());
                        historial.setFechaEstado(estadoArchivo.getFechaBloque1());
                    }
                    else {
                        historial = null;
                    }

                    estadoArchivo.setAccionBloque1(accion);
                    estadoArchivo.setEstadoBloque1(estado);
                    estadoArchivo.setFechaBloque1(new Date());
                    break;
                case 6:
                    if (estadoArchivo.getEstadoBloque6() != null && !estadoArchivo.getEstadoBloque6().equals(estado)) {
                        historial.setBloque(BloqueValidacionEnum.BLOQUE_6_OI);
                        historial.setEstado(estadoArchivo.getEstadoBloque6());
                        historial.setAccion(estadoArchivo.getAccionBloque6());
                        historial.setFechaEstado(estadoArchivo.getFechaBloque6());
                    }
                    else {
                        historial = null;
                    }

                    estadoArchivo.setAccionBloque6(accion);
                    estadoArchivo.setEstadoBloque6(estado);
                    estadoArchivo.setFechaBloque6(new Date());
                    break;
                default:
                    historial = null;
                    break;
            }

            persistenciaEstados.actualizarEstadoOF(estadoArchivo);
            if (historial != null) {
                persistenciaEstados.registrarHistoricoEstado(historial);
            }
        }
        logger.debug("Finaliza registrarEstadoArchivoOF(IndicePlanillaOF, EstadoProcesoArchivoEnum, AccionProcesoArchivoEnum, "
                + "String, Integer, Long)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.business.interfaces.GestorEstadosValidacionInterface#anularUltimoBloque(java.lang.Object)
     */
    @Override
    public void anularUltimoBloque(Object indice) throws ErrorFuncionalValidacionException {
        logger.debug("Inicia anularUltimoBloque(Object)");

        EstadoArchivoPorBloque estadoOI = null;
        EstadoArchivoPorBloqueOF estadoOF = null;

        // se prepara el hitórico de estado
        HistorialEstadoBloque historicoEstado = null;

        EstadoProcesoArchivoEnum estadoPrevio = null;
        AccionProcesoArchivoEnum accionPrevia = null;
        Date fechaPrevia = null;
        BloqueValidacionEnum bloque = null;

        // primero se consulta la instancia del estado correspondiente
        if (indice instanceof IndicePlanilla
                && SubTipoArchivoPilaEnum.DETALLE_APORTE.equals(((IndicePlanilla) indice).getTipoArchivo().getSubtipo())
                && !EstadoProcesoArchivoEnum.ANULADO.equals(((IndicePlanilla) indice).getEstadoArchivo())) {
            estadoOI = persistenciaEstados.consultarEstadoEspecificoOI((IndicePlanilla) indice);

            historicoEstado = new HistorialEstadoBloque();
            historicoEstado.setIdIndicePlanilla(((IndicePlanilla) indice).getId());

            // se recorren los estados hasta que alguno sea null o sea el B6 (empezando por el B6) para anular el bloque anterior
            if (estadoOI.getEstadoBloque6() != null) {
                estadoPrevio = estadoOI.getEstadoBloque6();
                accionPrevia = estadoOI.getAccionBloque6();
                fechaPrevia = estadoOI.getFechaBloque6();
                bloque = BloqueValidacionEnum.BLOQUE_6_OI;

                estadoOI.setEstadoBloque6(EstadoProcesoArchivoEnum.ANULADO);
                estadoOI.setAccionBloque6(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOI.setFechaBloque6(new Date());
            }
            else if (estadoOI.getEstadoBloque5() != null) {
                estadoPrevio = estadoOI.getEstadoBloque5();
                accionPrevia = estadoOI.getAccionBloque5();
                fechaPrevia = estadoOI.getFechaBloque5();
                bloque = BloqueValidacionEnum.BLOQUE_5_OI;

                estadoOI.setEstadoBloque5(EstadoProcesoArchivoEnum.ANULADO);
                estadoOI.setAccionBloque5(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOI.setFechaBloque5(new Date());
            }
            else if (estadoOI.getEstadoBloque4() != null) {
                estadoPrevio = estadoOI.getEstadoBloque4();
                accionPrevia = estadoOI.getAccionBloque4();
                fechaPrevia = estadoOI.getFechaBloque4();
                bloque = BloqueValidacionEnum.BLOQUE_4_OI;

                estadoOI.setEstadoBloque4(EstadoProcesoArchivoEnum.ANULADO);
                estadoOI.setAccionBloque4(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOI.setFechaBloque4(new Date());
            }
            else if (estadoOI.getEstadoBloque3() != null) {
                estadoPrevio = estadoOI.getEstadoBloque3();
                accionPrevia = estadoOI.getAccionBloque3();
                fechaPrevia = estadoOI.getFechaBloque3();
                bloque = BloqueValidacionEnum.BLOQUE_3_OI;

                estadoOI.setEstadoBloque3(EstadoProcesoArchivoEnum.ANULADO);
                estadoOI.setAccionBloque3(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOI.setFechaBloque3(new Date());
            }
            else if (estadoOI.getEstadoBloque2() != null) {
                estadoPrevio = estadoOI.getEstadoBloque2();
                accionPrevia = estadoOI.getAccionBloque2();
                fechaPrevia = estadoOI.getFechaBloque2();
                bloque = BloqueValidacionEnum.BLOQUE_2_OI;

                estadoOI.setEstadoBloque2(EstadoProcesoArchivoEnum.ANULADO);
                estadoOI.setAccionBloque2(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOI.setFechaBloque2(new Date());
            }
            else if (estadoOI.getEstadoBloque1() != null) {
                estadoPrevio = estadoOI.getEstadoBloque1();
                accionPrevia = estadoOI.getAccionBloque1();
                fechaPrevia = estadoOI.getFechaBloque1();
                bloque = BloqueValidacionEnum.BLOQUE_1_OI;

                estadoOI.setEstadoBloque1(EstadoProcesoArchivoEnum.ANULADO);
                estadoOI.setAccionBloque1(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOI.setFechaBloque1(new Date());
            }
            else if (estadoOI.getEstadoBloque0() != null) {
                estadoPrevio = estadoOI.getEstadoBloque0();
                accionPrevia = estadoOI.getAccionBloque0();
                fechaPrevia = estadoOI.getFechaBloque0();
                bloque = BloqueValidacionEnum.BLOQUE_0_OI;

                estadoOI.setEstadoBloque0(EstadoProcesoArchivoEnum.ANULADO);
                estadoOI.setAccionBloque0(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOI.setFechaBloque0(new Date());
            }

            // se actualiza el estado
            persistenciaEstados.actualizarEstadoOI(estadoOI);
        }
        else if (indice instanceof IndicePlanilla
                && SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(((IndicePlanilla) indice).getTipoArchivo().getSubtipo())
                && !EstadoProcesoArchivoEnum.ANULADO.equals(((IndicePlanilla) indice).getEstadoArchivo())) {
            estadoOI = persistenciaEstados.consultarEstadoEspecificoOI((IndicePlanilla) indice);

            historicoEstado = new HistorialEstadoBloque();
            historicoEstado.setIdIndicePlanilla(((IndicePlanilla) indice).getId());

            // se recorren los estados hasta que alguno sea null o sea el B4 (empezando por el B4) para anular el bloque anterior
            if (estadoOI.getEstadoBloque4() != null) {
                estadoPrevio = estadoOI.getEstadoBloque4();
                accionPrevia = estadoOI.getAccionBloque4();
                fechaPrevia = estadoOI.getFechaBloque4();
                bloque = BloqueValidacionEnum.BLOQUE_4_OI;

                estadoOI.setEstadoBloque4(EstadoProcesoArchivoEnum.ANULADO);
                estadoOI.setAccionBloque4(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOI.setFechaBloque4(new Date());
            }
            else if (estadoOI.getEstadoBloque3() != null) {
                estadoPrevio = estadoOI.getEstadoBloque3();
                accionPrevia = estadoOI.getAccionBloque3();
                fechaPrevia = estadoOI.getFechaBloque3();
                bloque = BloqueValidacionEnum.BLOQUE_3_OI;

                estadoOI.setEstadoBloque3(EstadoProcesoArchivoEnum.ANULADO);
                estadoOI.setAccionBloque3(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOI.setFechaBloque3(new Date());
            }
            else if (estadoOI.getEstadoBloque2() != null) {
                estadoPrevio = estadoOI.getEstadoBloque2();
                accionPrevia = estadoOI.getAccionBloque2();
                fechaPrevia = estadoOI.getFechaBloque2();
                bloque = BloqueValidacionEnum.BLOQUE_2_OI;

                estadoOI.setEstadoBloque2(EstadoProcesoArchivoEnum.ANULADO);
                estadoOI.setAccionBloque2(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOI.setFechaBloque2(new Date());
            }
            else if (estadoOI.getEstadoBloque1() != null) {
                estadoPrevio = estadoOI.getEstadoBloque1();
                accionPrevia = estadoOI.getAccionBloque1();
                fechaPrevia = estadoOI.getFechaBloque1();
                bloque = BloqueValidacionEnum.BLOQUE_1_OI;

                estadoOI.setEstadoBloque1(EstadoProcesoArchivoEnum.ANULADO);
                estadoOI.setAccionBloque1(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOI.setFechaBloque1(new Date());
            }
            else if (estadoOI.getEstadoBloque0() != null) {
                estadoPrevio = estadoOI.getEstadoBloque0();
                accionPrevia = estadoOI.getAccionBloque0();
                fechaPrevia = estadoOI.getFechaBloque0();
                bloque = BloqueValidacionEnum.BLOQUE_0_OI;

                estadoOI.setEstadoBloque0(EstadoProcesoArchivoEnum.ANULADO);
                estadoOI.setAccionBloque0(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOI.setFechaBloque0(new Date());
            }

            // se actualiza el estado
            persistenciaEstados.actualizarEstadoOI(estadoOI);
        }
        else if (indice instanceof IndicePlanillaOF && !EstadoProcesoArchivoEnum.ANULADO.equals(((IndicePlanillaOF) indice).getEstado())) {
            estadoOF = persistenciaEstados.consultarEstadoOF((IndicePlanillaOF) indice);

            historicoEstado = new HistorialEstadoBloque();
            historicoEstado.setIdIndicePlanillaOF(((IndicePlanillaOF) indice).getId());

            // se recorren los estados hasta que alguno sea null (empezando por el B6) para anular el bloque anterior
            if (estadoOF.getEstadoBloque6() != null) {
                estadoPrevio = estadoOF.getEstadoBloque6();
                accionPrevia = estadoOF.getAccionBloque6();
                fechaPrevia = estadoOF.getFechaBloque6();
                bloque = BloqueValidacionEnum.BLOQUE_6_OI;

                estadoOF.setEstadoBloque6(EstadoProcesoArchivoEnum.ANULADO);
                estadoOF.setAccionBloque6(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOF.setFechaBloque6(new Date());
            }
            else if (estadoOF.getEstadoBloque1() != null) {
                estadoPrevio = estadoOF.getEstadoBloque1();
                accionPrevia = estadoOF.getAccionBloque1();
                fechaPrevia = estadoOF.getFechaBloque1();
                bloque = BloqueValidacionEnum.BLOQUE_1_OF;

                estadoOF.setEstadoBloque1(EstadoProcesoArchivoEnum.ANULADO);
                estadoOF.setAccionBloque1(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOF.setFechaBloque1(new Date());
            }
            else if (estadoOF.getEstadoBloque0() != null) {
                estadoPrevio = estadoOF.getEstadoBloque0();
                accionPrevia = estadoOF.getAccionBloque0();
                fechaPrevia = estadoOF.getFechaBloque0();
                bloque = BloqueValidacionEnum.BLOQUE_0_OF;

                estadoOF.setEstadoBloque0(EstadoProcesoArchivoEnum.ANULADO);
                estadoOF.setAccionBloque0(AccionProcesoArchivoEnum.ARCHIVO_ANULADO);
                estadoOF.setFechaBloque0(new Date());
            }

            // se actualiza el estado
            persistenciaEstados.actualizarEstadoOF(estadoOF);
        }

        if (historicoEstado != null) {
            historicoEstado.setEstado(estadoPrevio);
            historicoEstado.setAccion(accionPrevia);
            historicoEstado.setFechaEstado(fechaPrevia);
            historicoEstado.setBloque(bloque);

            persistenciaEstados.registrarHistoricoEstado(historicoEstado);
        }

        logger.debug("Finaliza anularUltimoBloque(Object)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IGestorEstadosValidacion#actualizarAccionManual(com.asopagos.entidades.pila.procesamiento.IndicePlanilla)
     */
    @Override
    public void actualizarAccionManual(IndicePlanilla indice) throws ErrorFuncionalValidacionException {
        logger.info("Inicia actualizarAccionManual(IndicePlanilla)");

        EstadoArchivoPorBloque estado = null;

        // primero se consulta la instancia del estado correspondiente
        estado = persistenciaEstados.consultarEstadoEspecificoOI((IndicePlanilla) indice);

        // se recorren los estados hasta que alguno sea null (empezando por el B6) para anular el bloque anterior
        if (estado.getAccionBloque9() == null && AccionProcesoArchivoEnum.REGISTRAR_NOVEDADES_PILA.equals(estado.getAccionBloque8())) {
            estado.setAccionBloque8(AccionProcesoArchivoEnum.REGISTRAR_NOVEDADES_PILA_MANUAL);
        }
        else if (estado.getAccionBloque8() == null
                && AccionProcesoArchivoEnum.REGISTRAR_RELACIONAR_APORTE.equals(estado.getAccionBloque7())) {
            estado.setAccionBloque7(AccionProcesoArchivoEnum.REGISTRAR_RELACIONAR_APORTE_MANUAL);
        }
        else if (estado.getAccionBloque7() == null && AccionProcesoArchivoEnum.PASAR_A_CRUCE_CON_BD.equals(estado.getAccionBloque6())) {
            estado.setAccionBloque6(AccionProcesoArchivoEnum.PASAR_A_CRUCE_CON_BD_MANUAL);
        }

        // se actualiza el estado
        persistenciaEstados.actualizarEstadoOI(estado);

        logger.debug("Finaliza actualizarAccionManual(IndicePlanilla)");
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IGestorEstadosValidacion#actualizarEstadosOF(com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6,
     *      com.asopagos.enumeraciones.pila.EstadoConciliacionArchivoFEnum)
     */
    @Override
    public void actualizarEstadosOF(PilaArchivoFRegistro6 registro6, EstadoConciliacionArchivoFEnum estadoOF) {
        logger.debug("Inicia actualizarEstadosOF(PilaArchivoFRegistro6, EstadoConciliacionArchivoFEnum)");

        // se acualiza el registro tipo 6
        persistenciaEstados.actualizarEstadoArchivoFRegistro6(registro6.getId(), estadoOF);

        // se consultan los estados de conciliación de los registros tipo 6 del archivo OF
        List<String> listaEstadosR6 = persistenciaEstados.consultarEstadosRegistros6(registro6.getIndicePlanilla());

        EstadoProcesoArchivoEnum estadoArchivoOF = EstadoProcesoArchivoEnum.ARCHIVO_FINANCIERO_CONCILIADO;
        AccionProcesoArchivoEnum accionArchivoOF = AccionProcesoArchivoEnum.VALIDACIONES_FINALIZADAS;

        if (listaEstadosR6 != null && listaEstadosR6.size() > 0) {

            for (String estadoR6 : listaEstadosR6) {
                EstadoConciliacionArchivoFEnum estadoR6Enum = EstadoConciliacionArchivoFEnum.valueOf(estadoR6);
                if (!EstadoConciliacionArchivoFEnum.REGISTRO_6_CONCILIADO.equals(estadoR6Enum)) {
                    /*
                     * sí se encuentra que por lo menios un sólo registro 6 no se encuentra conciliado
                     * se cambia el estado del archivo
                     */
                    estadoArchivoOF = EstadoProcesoArchivoEnum.ARCHIVO_FINANCIERO_PENDIENTE_CONCILIACION;
                    accionArchivoOF = AccionProcesoArchivoEnum.EN_ESPERA;
                    break;
                }
            }
        }
        else {
            // sí no se ecuenta con registros tipo 6 se cambia el estado (medida de seguridad, no se debe presentar el caso)
            estadoArchivoOF = EstadoProcesoArchivoEnum.ARCHIVO_FINANCIERO_PENDIENTE_CONCILIACION;
        }

        // se actualiza el estado del archivo OF
        persistenciaEstados.actualizarEstadoConciliacionOF(registro6.getIndicePlanilla(), estadoArchivoOF, accionArchivoOF);

        logger.debug("Finaliza actualizarEstadosOF(PilaArchivoFRegistro6, EstadoConciliacionArchivoFEnum)");
    }
}
