package com.asopagos.pila.validadores.bloque0.ejb;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IGestorEstadosValidacion;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.bloque0.interfaces.IValidacionOFBloque0;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> Clase con el procedimiento para la validación del bloque 0 del archivo de Operador Financiero <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 388, 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson Andrés Arboleda V.</a>
 */
@Stateless
public class ValidacionOFBloque0 implements IValidacionOFBloque0, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidacionOIBloque0.class);

    /**
     * Referencia al EJB de persistencia de datos de validador
     */
    @Inject
    private IPersistenciaDatosValidadores persistencia;

    /**
     * Referencia al EJB de gestión de estados
     */
    @Inject
    private IGestorEstadosValidacion gestorEstados;

    /** DTO de respuesta para la validación */
    private RespuestaValidacionDTO respuesta;

    @Override
    public RespuestaValidacionDTO validarBloqueCero(ArchivoPilaDTO archivoPila, Map<String, Object> contexto)
            throws ErrorFuncionalValidacionException {

        logger.debug("Inicia validarBloqueCero(ArchivoPilaDTO, Map<String, Object>, PersistenciaDatosValidadoresInterface)");

        respuesta = new RespuestaValidacionDTO();

        EstadoProcesoArchivoEnum estado = null;

        // se lee el tamaño límite de archivos desde el contexto
        Long sizeLimit = null;
        if (contexto.get(ConstantesContexto.LIMITE_TAMANO_INDIVIDUAL) == null
                || !StringUtils.isNumeric((String) contexto.get(ConstantesContexto.LIMITE_TAMANO_INDIVIDUAL))) {
            // falta del parámetro, se lanza excepción técnica
            logger.debug("Finaliza validarBloqueCero(ArchivoPilaDTO, Map<String, Object>, PersistenciaDatosValidadoresInterface)");
            throw new TechnicalException("No se cuenta con el parámetro de tamaño límite para archivos cargados");
        }

        sizeLimit = Long.valueOf((String) contexto.get(ConstantesContexto.LIMITE_TAMANO_INDIVIDUAL));

        // en primer lugar se valida el tamaño y extensión del archivo
        estado = validarTamanioArchivo(archivoPila, sizeLimit);

        if (estado == null) {
            estado = validarExtension(archivoPila);
        }

        // se valida el nombre del archivo
        contexto = validarNombre(archivoPila.getFileName(), contexto);

        estado = (EstadoProcesoArchivoEnum) contexto.get(ConstantesContexto.ESTADO_NOMBRE);

        Date fechaPago = (Date) contexto.get(ConstantesContexto.NOMBRE_FECHA_PAGO);
        String codBanco = (String) contexto.get(ConstantesContexto.NOMBRE_CODIGO_BANCO);

        IndicePlanillaOF indiceNuevo = null;

        // se va a registrar un nuevo archivo OF
        indiceNuevo = new IndicePlanillaOF();

        indiceNuevo = prepararIndice(archivoPila, indiceNuevo, (String) contexto.get(ConstantesContexto.NOMBRE_CODIGO_ENTIDAD), codBanco,
                fechaPago);

        // se asigna el estado por errores sí aplica
        if (estado != null) {
            indiceNuevo.setEstado(estado);
            // sí se presenta error, se asigna fecha de proceso
            indiceNuevo.setFechaProceso(Calendar.getInstance().getTime());
            indiceNuevo.setUsuarioProceso(archivoPila.getUsuario());
        }

        indiceNuevo = persistencia.registrarEnIndicePlanillasOF(indiceNuevo);

        // Registro el estado para el bloque
        try {
            gestorEstados.registrarEstadoArchivoOF(indiceNuevo, indiceNuevo.getEstado(),
                    FuncionesValidador.determinarAccion(indiceNuevo.getEstado(), indiceNuevo.getTipoArchivo()), "", 0);
        } catch (ErrorFuncionalValidacionException e) {
            // la operación específica realizada en este método no da lugar a un estado no válido que lance la excepción
        }

        respuesta.setIndicePlanillaOF(indiceNuevo);
        logger.debug("Finaliza validarBloqueCero(ArchivoPilaDTO, Map<String, Object>, PersistenciaDatosValidadoresInterface)");
        return respuesta;
    }

    /**
     * Método para validar la estructura del nombre del archivo
     * @throws ErrorFuncionalValidacionException
     * 
     */
    private Map<String, Object> validarNombre(String nombreArchivo, Map<String, Object> contexto) {
        logger.debug("Inicia validarNombre(String, Map<String, Object>)");

        String mensaje = null;

        Date fechaPago = null;
        String codBanco = null;

        // en primer lugar, fragmento el nombre del archivo para establecer la cantidad de campos encontrados (4 campos)
        String[] nombreSeparado = nombreArchivo.split("_");
        if (nombreSeparado.length != 4) {
            mensaje = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_NO_CUMPLE_FORMATO.getReadableMessage(
                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO, nombreArchivo, TipoErrorValidacionEnum.TIPO_3.toString());

            agregarError(mensaje);
        }

        // se verifica que el nombre del archivo esté en mayúscula sostenida (exeptuando la extensión)
        String nombreBase = nombreArchivo.split("\\.")[0];
        String nombreMayuscula = nombreBase.toUpperCase();
        if (!nombreBase.equals(nombreMayuscula)) {
            mensaje = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CARACTERES_MINUSCULA.getReadableMessage(
                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO, nombreArchivo, TipoErrorValidacionEnum.TIPO_3.toString());

            agregarError(mensaje);
        }

        // se comprueba cada fragmento del nombre
        for (int i = 0; i < nombreSeparado.length; i++) {
            switch (i) {
                case 0: // Fecha de recaudo o pago
                    // formato yyyyMMdd
                    if (!Pattern.matches(ConstantesComunesProcesamientoPILA.PATRON_FORMATO_FECHA, nombreSeparado[i])) {
                        mensaje = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_FORMATO_FECHA_NO_VALIDO.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_FECHA_RECAUDO, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString(), nombreArchivo);

                        agregarError(mensaje);
                    }
                    else {
                        // coherencia de la fecha
                        Integer anio = null;
                        Integer mes = null;
                        Integer dia = null;

                        try {
                            anio = Integer.parseInt(nombreSeparado[i].substring(0, 4));
                            mes = Integer.parseInt(nombreSeparado[i].substring(4, 6));
                            dia = Integer.parseInt(nombreSeparado[i].substring(6, 8));

                            try {
                                LocalDate.of(anio, mes, dia);
                            } catch (DateTimeException e) {
                                mensaje = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_FECHA_PAGO_NO_VALIDA.getReadableMessage(
                                        ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_FECHA_RECAUDO, nombreSeparado[i],
                                        TipoErrorValidacionEnum.TIPO_2.toString(), nombreArchivo);

                                agregarError(mensaje);
                            }

                            fechaPago = new GregorianCalendar(anio, mes, dia).getTime();
                        } catch (Exception e) {
                            mensaje = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_FECHA_PAGO_NO_VALIDA.getReadableMessage(
                                    ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_FECHA_RECAUDO, nombreSeparado[i],
                                    TipoErrorValidacionEnum.TIPO_2.toString(), nombreArchivo);

                            agregarError(mensaje);
                        }
                    }

                    contexto.put(ConstantesContexto.NOMBRE_FECHA_PAGO, fechaPago);
                    break;
                case 1: // Código de la entidad administradora
                    // validación del tamaño del dato
                    if (nombreSeparado[i].length() > 6) {
                        mensaje = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CODIGO_ENTIDAD_SUPERA_CARACTERES_DEFINIDOS.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_ENTIDAD_ADMINISTRADORA, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString(), nombreArchivo);

                        agregarError(mensaje);
                    }

                    // validación de caracteres alfanumericos en mayúscula
                    if (!Pattern.matches(ConstantesComunesProcesamientoPILA.PATRON_FORMATO_CODIGO_CCF_MAYUSCULA, nombreSeparado[i])) {

                        mensaje = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CODIGO_ADMINISTRADORA_INVALIDO.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_ENTIDAD_ADMINISTRADORA, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString());

                        agregarError(mensaje);
                    }

                    // validación del código de la CCF de la planilla respecto a la entidad que tramita la misma
                    String codigoCCF = (String) contexto.get(ConstantesContexto.CODIGO_CCF);
                    if (!nombreSeparado[i].equals(codigoCCF)) {

                        mensaje = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_CODIGO_ADMINISTRADORA_NO_CONCUERDA.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_ENTIDAD_ADMINISTRADORA, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString(), codigoCCF);

                        agregarError(mensaje);
                    }
                    contexto.put(ConstantesContexto.NOMBRE_CODIGO_ENTIDAD, nombreSeparado[i]);
                    break;
                case 2: // Código del banco recaudador
                    codBanco = nombreSeparado[i];
                    contexto.put(ConstantesContexto.NOMBRE_CODIGO_BANCO, codBanco);
                    break;
                case 3: // Tipo de archivo
                    if (!nombreSeparado[i].split("\\.")[0].equals("F")) {
                        mensaje = MensajesValidacionEnum.ERROR_NOMBRE_ARCHIVO_TIPO_ARCHIVO.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_TIPO_ARCHIVO, nombreSeparado[i],
                                TipoErrorValidacionEnum.TIPO_2.toString(), nombreArchivo);

                        agregarError(mensaje);
                    }
                    break;
                default:
                    break;
            }
        }

        if (mensaje != null) {
            contexto.put(ConstantesContexto.ESTADO_NOMBRE, EstadoProcesoArchivoEnum.INCONSISTENCIA_NOMBRE_ARCHIVO);
        }

        logger.debug("Finaliza validarNombre(String, Map<String, Object>)");
        return contexto;
    }

    /**
     * Método para agregar un error a la respuesta
     */
    private void agregarError(String mensaje) {
        ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();

        error = FuncionesValidador.descomponerMensajeErrorValidacion(error, mensaje);

        error.setBloque(BloqueValidacionEnum.BLOQUE_0_OF);
        error.setTipoArchivo(TipoArchivoPilaEnum.ARCHIVO_OF);

        respuesta.addErrorDetalladoValidadorDTO(error);
    }

    /**
     * Método que prepara al nuevo índice de planilla
     * 
     * @param archivoPila
     *        DTO de carga del archivo
     * @param indicePlanilla
     *        Nueva instancia de índice de planilla para preparar
     * @param codigoCCF
     *        Código de la administradora
     * @param codBanco
     *        Código del banco
     * @param fechaPago
     *        Fecha de pago del aporte
     * @return IndicePlanillaOF
     *         Instancia nueva actualizada
     * @throws ErrorFuncionalValidacionException
     *         Cuando no se cuenta con la información requerida para el registro
     */
    private IndicePlanillaOF prepararIndice(ArchivoPilaDTO archivoPila, IndicePlanillaOF indicePlanilla, String codigoCCF, String codBanco,
            Date fechaPago) throws ErrorFuncionalValidacionException {
        logger.debug("Inicia prepararIndice(ArchivoPilaDTO, IndicePlanilla)");

        if (validarInformacionBaseRegistro(archivoPila, codigoCCF, codBanco, fechaPago)) {

            indicePlanilla.setNombreArchivo(archivoPila.getFileName());
            indicePlanilla.setFechaRecibo(Calendar.getInstance().getTime());
            indicePlanilla.setCodigoAdministradora(codigoCCF);
            indicePlanilla.setCodigoBancoRecaudador(codBanco);
            indicePlanilla.setFechaPago(fechaPago);
            indicePlanilla.setTipoArchivo(TipoArchivoPilaEnum.ARCHIVO_OF);
            indicePlanilla.setTipoCargaArchivo(archivoPila.getModo());
            indicePlanilla.setUsuario(archivoPila.getUsuario());
            indicePlanilla.setEstado(EstadoProcesoArchivoEnum.CARGADO_EXITOSAMENTE);
            indicePlanilla.setRegistroActivo(true);
            indicePlanilla.setTamanoArchivo(archivoPila.getSize());

            // se agregan los identificadores del documento en ECM
            indicePlanilla.setIdDocumento(archivoPila.getIdentificadorDocumento());
            indicePlanilla.setVersionDocumento(archivoPila.getVersionDocumento());
        }
        else {
            String mensaje = MensajesValidacionEnum.ERROR_ARCHIVO_INCOMPLETO.getReadableMessage(TipoErrorValidacionEnum.TIPO_3.toString());
            logger.debug("Finaliza prepararIndice(ArchivoPilaDTO, IndicePlanilla) - " + mensaje);

            throw new ErrorFuncionalValidacionException(mensaje, new Throwable());
        }

        logger.debug("Finaliza prepararIndice(ArchivoPilaDTO, IndicePlanilla)");
        return indicePlanilla;
    }

    /**
     * Método para establecer que se cuenta con la información básica para realizar el registro de un nuevo índice de planilla
     * @param archivoPila
     *        DTO con la información del archivo que se desea cargar
     * @param codigoCCF
     *        Código de la administradora
     * @param codBanco
     *        Código del banco
     * @param fechaPago
     *        Fecha de pago del aporte
     * @return <b>Boolean</b>
     *         Indica cumplimiento en la información básica
     */
    private Boolean validarInformacionBaseRegistro(ArchivoPilaDTO archivoPila, String codigoCCF, String codBanco, Date fechaPago) {
        if (archivoPila != null) {
            // nombre del archivo
            if (archivoPila.getFileName() == null && archivoPila.getFileName().isEmpty()) {
                return false;
            }

            // tipo de carga
            if (archivoPila.getModo() == null) {
                return false;
            }

            // usuario que realiza la carga
            if (archivoPila.getUsuario() == null || archivoPila.getUsuario().isEmpty()) {
                return false;
            }

            // identificador del documento en ECM
            if (archivoPila.getIdentificadorDocumento() == null) {
                return false;
            }

            // versión del documento en ECM
            if (archivoPila.getVersionDocumento() == null) {
                return false;
            }
        }
        else {
            return false;
        }

        if (codBanco == null || codigoCCF == null || fechaPago == null) {
            return false;
        }

        return true;
    }

    /**
     * Método que se encarga de validar el tamaño de un archivo PILA a cargar
     * 
     * @param archivoPila
     *        DTO con la información del archivo del archivo
     * @param sizeLimit
     *        Parámetro de límite para el tamaño del archivo
     * @return EstadoProcesoArchivoEnum
     *         Estado de error derivado del tamaño de archivo
     */
    private EstadoProcesoArchivoEnum validarTamanioArchivo(ArchivoPilaDTO archivoPila, Long sizeLimit) {
        logger.debug("Inicia validarTamanioArchivo(ArchivoPilaDTO)");

        EstadoProcesoArchivoEnum respuestaEstado = null;

        String mensaje = null;
        String tipoError = TipoErrorValidacionEnum.TIPO_3.toString();

        // se convierte el valor del parámetro a bytes
        Long sizeLimitBytes = sizeLimit * 1024 * 1024;

        // primero se valida el tamaño cero
        if (archivoPila != null) {
            if (archivoPila.getSize() == null || archivoPila.getSize() == 0) {
                respuestaEstado = EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_CERO;

                mensaje = MensajesValidacionEnum.ERROR_TAMANO_ARCHIVO_CERO.getReadableMessage(tipoError, archivoPila.getFileName());
            }
            else {
                // luego se valida tamaño máximo individual
                // la validación de tamaño máximo sólo aplica para carga manual

                if (!TipoCargaArchivoEnum.MANUAL.equals(archivoPila.getModo())) {
                    // el tamaño está definido en Bytes, así que se compara con el valor parametrizado
                    if (archivoPila.getSize().compareTo(sizeLimitBytes) > 0) {
                        String tamanoEnMb = Long.toString(archivoPila.getSize() / 1024 / 1024);

                        respuestaEstado = EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_EXCEDIDO;

                        mensaje = MensajesValidacionEnum.ERROR_TAMANO_ARCHIVO_INDIVIDUAL.getReadableMessage(
                                ConstantesComunesProcesamientoPILA.TAMANO_ARCHIVO, tamanoEnMb, tipoError, archivoPila.getFileName(),
                                sizeLimit.toString());
                    }
                }
            }
        }
        else {
            // si no se recibe el DTO, se considera tamaño cero
            respuestaEstado = EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_TAMANO_CERO;

            mensaje = MensajesValidacionEnum.ERROR_ARCHIVO_NULO.getReadableMessage(tipoError);
        }

        // sí se presenta un error de validación
        if (respuestaEstado != null) {
            ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
            error.setBloque(BloqueValidacionEnum.BLOQUE_0_OF);
            error = FuncionesValidador.descomponerMensajeErrorValidacion(error, mensaje);

            if (archivoPila != null) {
                error.setTipoArchivo(FuncionesValidador.getTipoArchivo(archivoPila.getFileName()));
            }

            this.respuesta.addErrorDetalladoValidadorDTO(error);
        }

        logger.debug("Finaliza validarTamanioArchivo(ArchivoPilaDTO)");
        return respuestaEstado;
    }

    /**
     * Método que valida que la extensión del archivo sea txt
     * 
     * @param archivoPila
     *        DTO con la información del archivo del archivo
     * @return EstadoProcesoArchivoEnum
     *         Estado de error derivado del tamaño de archivo
     */
    private EstadoProcesoArchivoEnum validarExtension(ArchivoPilaDTO archivoPila) {
        logger.debug("Inicia validarExtension(ArchivoPilaDTO)");

        EstadoProcesoArchivoEnum respuestaEstado = null;

        String mensaje = null;
        String tipoError = TipoErrorValidacionEnum.TIPO_3.toString();

        if (archivoPila.getFileName() == null || !(archivoPila.getFileName().toLowerCase().endsWith(".txt"))
                || archivoPila.getFileName().split("\\.").length > 2) {
            respuestaEstado = EstadoProcesoArchivoEnum.DESCARGADO_CON_INCONSISTENCIAS_EXTENSION_NO_VALIDA;

            mensaje = MensajesValidacionEnum.ERROR_EXTENSION_ARCHIVO
                    .getReadableMessage(ConstantesComunesProcesamientoPILA.EXTENSION_ARCHIVO, archivoPila.getFileName(), tipoError);
        }

        // sí se presenta un error de validación
        if (respuestaEstado != null) {
            ErrorDetalladoValidadorDTO error = new ErrorDetalladoValidadorDTO();
            error.setBloque(BloqueValidacionEnum.BLOQUE_0_OF);
            error = FuncionesValidador.descomponerMensajeErrorValidacion(error, mensaje);

            if (archivoPila != null) {
                error.setTipoArchivo(FuncionesValidador.getTipoArchivo(archivoPila.getFileName()));
            }

            this.respuesta.addErrorDetalladoValidadorDTO(error);
        }

        logger.debug("Finaliza validarExtension(ArchivoPilaDTO)");
        return respuestaEstado;
    }

}
