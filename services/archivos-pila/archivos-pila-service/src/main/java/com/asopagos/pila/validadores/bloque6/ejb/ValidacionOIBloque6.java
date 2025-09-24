package com.asopagos.pila.validadores.bloque6.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.ccf.afiliaciones.PreRegistroEmpresaDesCentralizada;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoFRegistro6;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIPRegistro1;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIPRegistro3;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro1;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro3;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIPEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.pila.validadores.bloque6.interfaces.IValidacionOIBloque6;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripción:</b> Clase que contiene la función para la conciliación del
 * Operador de Información con el Operador Financiero en el Bloque 6 <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 393 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
@Stateless
public class ValidacionOIBloque6 implements IValidacionOIBloque6, Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidacionOIBloque6.class);

    @Inject
    private IConsultaModeloDatosCore consultaModeloCore;
    /**
     * Referencia al EJB de persistencia de datos de validador
     */
    @Inject
    private IPersistenciaDatosValidadores persistencia;

    @Override
    public RespuestaValidacionDTO validarBloque6(Map<String, Object> contexto, RespuestaValidacionDTO result) {
        String firmaMetodo = "validarBloque6(Map<String, Object>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        // se consulta el listado de errores para agregar las inconsistencias que se encuentren
        RespuestaValidacionDTO erroresResultado = (RespuestaValidacionDTO) contexto.get(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO);

        // se toma del parámetro
        if (erroresResultado == null) {
            erroresResultado = result;
        }

        // sí le listado de errores aún no existe, se le crea
        if (erroresResultado == null) {
            erroresResultado = new RespuestaValidacionDTO();
            contexto.put(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO, erroresResultado);
        }

        if (persistencia != null) {
            // Nombre del campo para el mensaje de salida
            String nombreCampo = "";
            Object registro1I = null;
            Object registro3I = null;

            // se toma el índice del archivo del contexto
            IndicePlanilla indicePlanilla = (IndicePlanilla) contexto.get(ConstantesContexto.INDICE_PLANILLA);

            if (indicePlanilla != null) {
                // se consulta la información de registro 1 de archivo I (para obtener las variables de búsquea para OF)
                registro1I = persistencia.consultarRegistro1ArchivoI(indicePlanilla);

                if (registro1I == null) {
                    String mensaje = "No se encuentra el Registro tipo 1 de la planilla";

                    logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: " + mensaje);
                    throw new TechnicalException(mensaje);
                }

                // se consulta la información de registro 3 de archivo I (para el valor que se va a comparar)
                try {
                    registro3I = persistencia.consultarRegistro3ArchivoI(indicePlanilla);
                } catch (ErrorFuncionalValidacionException e1) {
                    String mensaje = "No se encuentra el Registro tipo 3 de la planilla";

                    logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo + " :: " + mensaje);
                    throw new TechnicalException(mensaje);
                }

                // se toman los valores de búsqueda en OF                
                Long numeroPlanilla = null;
                String periodoPago = null;
                Short operadorInformacion = null;
                String idAportante = null;
                String nomSucursal = null;
                String codSucursal = null;

                if (registro1I instanceof PilaArchivoIRegistro1) {
                    numeroPlanilla = new Long(((PilaArchivoIRegistro1) registro1I).getNumPlanilla());
                    periodoPago = ((PilaArchivoIRegistro1) registro1I).getPeriodoAporte();
                    operadorInformacion = ((PilaArchivoIRegistro1) registro1I).getCodOperador();
                    idAportante = ((PilaArchivoIRegistro1) registro1I).getIdAportante();
                    nomSucursal = ((PilaArchivoIRegistro1) registro1I).getNomSucursal();
                    codSucursal = ((PilaArchivoIRegistro1) registro1I).getCodSucursal();
                    nombreCampo = "Archivo Tipo I - Registro 3 Renglón 39 - Campo 3: Total Aportes";
                } else if (registro1I instanceof PilaArchivoIPRegistro1) {
                    numeroPlanilla = new Long(((PilaArchivoIPRegistro1) registro1I).getNumPlanilla());
                    periodoPago = ((PilaArchivoIPRegistro1) registro1I).getPeriodoAporte();
                    operadorInformacion = ((PilaArchivoIPRegistro1) registro1I).getCodOperador();
                    idAportante = ((PilaArchivoIPRegistro1) registro1I).getIdPagador();
                    nomSucursal = ((PilaArchivoIPRegistro1) registro1I).getNomSucursal();
                    codSucursal = ((PilaArchivoIPRegistro1) registro1I).getCodSucursal();
                    nombreCampo = "Archivo Tipo IP - Registro 3 - Campo 5: Valor total a pagar";
                }

                if (numeroPlanilla != null && periodoPago != null && operadorInformacion != null) {
                    // se consulta la información de registro 6 de archivo F
                    PilaArchivoFRegistro6 registro6F = null;

                    try {
                        registro6F = persistencia.consultarRegistro6OF(numeroPlanilla, periodoPago, operadorInformacion);
                    } catch (ErrorFuncionalValidacionException e) {
                        // sí no se encuentra un registro 6, el error se maneja internamente
                    }

                    if (registro6F != null) {
                        // comparo los valores                       
                        BigDecimal valorTotalR3I = null;

                        String idCampo = null;

                        if (registro3I instanceof PilaArchivoIRegistro3) {
                            valorTotalR3I = ((PilaArchivoIRegistro3) registro3I).getValorTotalAportes();
                            idCampo = EtiquetaArchivoIEnum.I3_33.toString();
                        } else if (registro3I instanceof PilaArchivoIPRegistro3) {
                            valorTotalR3I = ((PilaArchivoIPRegistro3) registro3I).getValorTotalPagar();
                            idCampo = EtiquetaArchivoIPEnum.IP35.toString();
                        }

                        if (valorTotalR3I == null) {
                            logger.error("No se tiene valor de planilla en registro 3");
                            String mensaje = MensajesValidacionEnum.ERROR_CAMPO
                                    .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.name(), nombreCampo);

                            // se añade la inconsistencia
                            erroresResultado.addErrorDetalladoValidadorDTO(
                                    FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_6_OI, null));
                        }

                        /*
                         * en el caso de encontrar un registro 6, se agrega al contexto para que
                         * el orquestador marque el estado de la revisión del registro 6 de OF
                         */
                        contexto.put(ConstantesContexto.REGISTRO_6, registro6F);

                        BigDecimal valorOFregistro6 = registro6F.getValorPlanilla();

                        // se valida el ID del aportante para saber sí genera inconsistencia T0
                        // primero se igualan a String (eliminación de ceros a la izq)
                        String idAportanteOI = StringUtils.isNumeric(idAportante) ? Long.valueOf(idAportante).toString() : idAportante;
                        String idAportanteOF = StringUtils.isNumeric(registro6F.getIdAportante())
                                ? Long.valueOf(registro6F.getIdAportante()).toString() : registro6F.getIdAportante();

                        PreRegistroEmpresaDesCentralizada desCentralizada = consultaModeloCore.consultarPersonaPorEmpresaDescentralizada(idAportanteOF, nomSucursal, codSucursal);
                        
                        if (desCentralizada != null) {
                            logger.info("Se encuentra Empresa Descentralizada Bloque 6: " + desCentralizada.getNumeroIdentificacionSerial());
                            idAportanteOF = desCentralizada.getNumeroIdentificacionSerial();
                        }

                        /*
                         * CC 0244937 - Ajustes4, pestaña Anexo 2.1.1 - punto 8:
                         * El cruce del número de identificación tiene 2 opciones, con y sin el último caracter en el archivo financiero
                         * por DV
                         */
                        String idAportanteOFsinDV = idAportanteOF.length() > 1 ? idAportanteOF.substring(0, idAportanteOF.length() - 1)
                                : idAportanteOF;

                        if (!idAportanteOI.equalsIgnoreCase(idAportanteOF.trim())
                                && !idAportanteOI.equalsIgnoreCase(idAportanteOFsinDV.trim())) {
                            logger.error("La identificación del aportante no coincide");
                            logger.error("Número identificación del aportante en OI: " + idAportanteOI);
                            logger.error("Número identificación del aportante en OF: " + idAportanteOF);
                            logger.error("Número identificación del aportante en OF sin DV: " + idAportanteOFsinDV);

                            String mensaje = MensajesValidacionEnum.ERROR_CONCILIACION_IDENTIFICACION_APORTANTE
                                    .getReadableMessage(TipoErrorValidacionEnum.TIPO_0.name(), idAportanteOI, idAportanteOF);

                            // se añade la inconsistencia
                            erroresResultado.addErrorDetalladoValidadorDTO(
                                    FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_6_OI, null));
                        }

                        // se comparan los valores entre OI y OF
                        if (valorTotalR3I != null && valorTotalR3I.compareTo(valorOFregistro6) != 0) {
                            logger.error("Los valores del aporte no coinciden");
                            logger.error("Valor del aporte en OI: " + valorTotalR3I.toPlainString());
                            logger.error("Valor del aporte en OF: " + valorOFregistro6.toPlainString());

                            String mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_COINCIDE_CON_VALOR_OF.getReadableMessage(idCampo,
                                    valorTotalR3I.toPlainString(), TipoErrorValidacionEnum.TIPO_1.name(), nombreCampo,
                                    valorTotalR3I.toPlainString(), valorOFregistro6.toPlainString());

                            // se añade la inconsistencia
                            erroresResultado.addErrorDetalladoValidadorDTO(
                                    FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_6_OI, null));
                        }
                    } else {
                        logger.warn("No se encuentra un registro 6 de OF");
                        logger.warn("Número de planilla: " + numeroPlanilla);
                        logger.warn("Período de aporte: " + periodoPago);
                        logger.warn("Operador Información: " + operadorInformacion);
                        String mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_SE_ENCUENTRA_INFORMACION_RECAUDO_EN_OF
                                .getReadableMessage(TipoErrorValidacionEnum.TIPO_1.name(), "" + numeroPlanilla);

                        // se añade la inconsistencia
                        erroresResultado.addErrorDetalladoValidadorDTO(
                                FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_6_OI, null));
                    }
                } else {
                    String mensaje = MensajesValidacionEnum.ERROR_SIN_DATOS_BUSQUEDA_PLANILLA_OF
                            .getReadableMessage(TipoErrorValidacionEnum.TIPO_1.name());

                    // se añade la inconsistencia
                    erroresResultado.addErrorDetalladoValidadorDTO(
                            FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_6_OI, null));
                }
            } else {
                logger.error("No se cuenta con la entrada de índice de planilla a conciliar");
                String mensaje = MensajesValidacionEnum.ERROR_SIN_INFORMACION_BASE_ARCHIVO_DETALLE_APORTE
                        .getReadableMessage(TipoErrorValidacionEnum.TIPO_1.name());

                // se añade la inconsistencia
                erroresResultado
                        .addErrorDetalladoValidadorDTO(FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_6_OI, null));
            }
        } else {
            logger.error("Falló la conexión con la BD para consultar la información de la planilla a conciliar");
            String mensaje = MensajesValidacionEnum.ERROR_SIN_ACCESO_A_INFO_BD.getReadableMessage(TipoErrorValidacionEnum.TIPO_1.name());

            // se añade la inconsistencia
            erroresResultado
                    .addErrorDetalladoValidadorDTO(FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_6_OI, null));
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return erroresResultado;
    }
}
