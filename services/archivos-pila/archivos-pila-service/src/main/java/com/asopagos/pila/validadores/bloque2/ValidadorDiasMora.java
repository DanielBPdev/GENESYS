package com.asopagos.pila.validadores.bloque2;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.entidades.pila.parametrizacion.NormatividadFechaVencimiento;
import com.asopagos.entidades.pila.parametrizacion.OportunidadPresentacion;
import com.asopagos.entidades.pila.soporte.PasoValores;
import com.asopagos.entidades.transversal.core.DiasFestivos;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import com.asopagos.rest.exception.TechnicalException;
import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Metodo se encarga de validar dias en mora<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorDiasMora extends FieldValidator {

    // es requerido que la fecha de vencimiento y dias de mora calculados se manejen a nivel de atributo de instancia para la dinámica con planillas de pensionados
    private Calendar fechaVencimiento = null;
    private Long diasMoraCalculados = null;
    private String nombreCampo = null;
    private String tipoError = null;
    private String idCampo = null;
    private Integer diasMora = null;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorDiasMora.class);

    /** Constantes para mensajes de falta de parámetros y error en cálculo */

    /**
     * Metodo se encarga de validar dias en mora
     * @param FieldArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(FieldArgumentDTO arg) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");
        
        this.fechaVencimiento = null;
        this.diasMoraCalculados = null;
        this.nombreCampo = null;
        this.tipoError = null;
        this.idCampo = null;
        this.diasMora = null;

        if (arg.getFieldValue() != null && !arg.getFieldValue().toString().isEmpty()) {
            String mensaje = null;

            Map<String, Object> contexto = arg.getContext();

            // Lectura y declaracion de variables
            List<DiasFestivos> festivos = (List<DiasFestivos>) contexto.get(ConstantesContexto.FESTIVOS);

            // se toma el período del contexto
            Object valorCampo = contexto.get(ConstantesContexto.NOMBRE_PERIODO_PAGO);
            String periodoAporte = null;
            if (valorCampo instanceof String) {
                periodoAporte = (String) valorCampo;
            }
            else if (valorCampo != null) {
                periodoAporte = valorCampo.toString();
            }

            // se toma el tipo de planilla del contexto
            valorCampo = contexto.get(ConstantesContexto.TIPO_PLANILLA);
            String tipoPlanilla = null;
            if (valorCampo instanceof String) {
                tipoPlanilla = (String) valorCampo;
            }
            else if (valorCampo != null) {
                tipoPlanilla = valorCampo.toString();
            }

            TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);

            nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

            // se busca en el contexto si se ha detectado algún cotizante que evalúe contra mes actual(03) o contra mes vencido (57)
            Boolean independienteMesActual = (Boolean) contexto.get(ConstantesContexto.TIENE_INDEPENDIENTE_MES_ACTUAL);
            if (independienteMesActual == null) {
                independienteMesActual = false;
            }
            Boolean independienteMesVencido = (Boolean) contexto.get(ConstantesContexto.TIENE_INDEPENDIENTE_MES_VENCIDO);
            if (independienteMesVencido == null) {
                independienteMesVencido = false;
            }

            // se leen el ID del aportante y el tipo de archivo del contexto
            String id = (String) contexto.get(ConstantesContexto.NOMBRE_ID_APORTANTE);
            String tipoArchivo = (String) contexto.get(ConstantesContexto.NOMBRE_TIPO_ARCHIVO);

            TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);
            if (tipoArchivoEnum == null) {
                // error por falta de tipo de archivo
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.TIPO_ARCHIVO);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            // se leen la cantidad de cotizantes, la clase del aportante y la fecha del aporte del contexto
            Integer cantidadPersonas = (Integer) contexto.get(ConstantesContexto.CANTIDAD_COTIZANTES);
            String claseAportante = (String) contexto.get(ConstantesContexto.CLASE_APORTANTE);
            Date fechaAporte = FuncionesValidador.convertirDate((String) contexto.get(ConstantesContexto.NOMBRE_FECHA_PAGO));

            if (fechaAporte == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, MessagesConstants.FECHA_APORTE);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            diasMora = (Integer) arg.getFieldValue();
            
            //Para Planillas Q debe ser mayor a cero el valor en mora
            if(TipoPlanillaEnum.ACUERDOS.equals(tipoPlanillaEnum) && diasMora == 0) {
            	mensaje = MensajesValidacionEnum.ERROR_CANTIDAD_DIAS_MORA_EN_CERO.getReadableMessage(idCampo, diasMora.toString(),
                        tipoError, nombreCampo, diasMora.toString());

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            List<NormatividadFechaVencimiento> entradasNormatividad = (List<NormatividadFechaVencimiento>) contexto
                    .get(ConstantesContexto.NORMATIVIDAD);

            // con el tipo de archivo, se determina sí se debe evaluar como tipo I por ser planilla de pensionados
            if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo())) {
                // el tipo de planilla se toma siempre como empleados con el fin que se trate
                // de una presentación de mes vencido
                tipoPlanillaEnum = TipoPlanillaEnum.EMPLEADOS;
            }

            // se consultan los escenarios de normatividad aplicables al período

            PeriodoPagoPlanillaEnum oportunidad = null;
            List<OportunidadPresentacion> entradasOportunidad = (List<OportunidadPresentacion>) contexto
                    .get(ConstantesContexto.OPORTUNIDAD_VENCIMIENTO);

            // se consulta la oportunidad en la presentación de la planilla

            if (entradasNormatividad != null && entradasOportunidad != null) {
                if (entradasOportunidad.size() == 1) {
                    oportunidad = entradasOportunidad.get(0).getOportunidad();
                }
                else {
                    oportunidad = elegirOportunidad(tipoPlanillaEnum, entradasOportunidad, independienteMesActual, independienteMesVencido);
                }

                if (!GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo())) {
                    // caso de planilla de tipo I - IR

                    if (cantidadPersonas == null) {
                        mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                                MessagesConstants.CANTIDAD_PERSONAS);

                        logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }
                    
                    
                    //Se busca la naturaleza juridica traida desde el archivo I
                    Integer naturalezaJuridica = null;
                    
                    if (contexto.get(ConstantesContexto.INFORMACION_BLOQUE_3) != null) {
                    	Map<String, Object>  informacionBloque3Map=(Map<String, Object>) contexto.get(ConstantesContexto.INFORMACION_BLOQUE_3);
                    	
                    	if( informacionBloque3Map!=null) {
                    		Object objetoNaturaleza = informacionBloque3Map.get("naturaleza");
                    		
                    		if(objetoNaturaleza!= null && objetoNaturaleza instanceof String[]) {
                    				String [] naturalezaArray = (String[]) objetoNaturaleza;
                        			
                    				try {
                    					if(naturalezaArray != null && naturalezaArray.length>=2) {
                            				naturalezaJuridica = Integer.valueOf(naturalezaArray[1]);
                            			}
									} catch (Exception e) {
										naturalezaJuridica = null;
									}                        			
                    		}
                    	}
                    }                                        

                    if (!generarFechaVencimientoYDiasMora(entradasNormatividad, id, tipoArchivoEnum, cantidadPersonas, claseAportante,
                            oportunidad, periodoAporte, fechaAporte, festivos, null)) {
                    	
                    	if(!(naturalezaJuridica!= null && naturalezaJuridica== 1 
                    			&& TipoPlanillaEnum.CORRECIONES.equals(tipoPlanillaEnum))) {
                    		
                    		//Se debe omitir las planillas O para este caso el valor dias mora
                    		if(!TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaEnum)) {
                    			mensaje = MensajesValidacionEnum.ERROR_CANTIDAD_DIAS_MORA.getReadableMessage(idCampo, diasMora.toString(),
                                        tipoError, nombreCampo, diasMora.toString(), diasMoraCalculados.toString());
                    		}
                    		
                            // se almacena la fecha de vencimiento en el contexto para usarla en el cálculo de valor de mora
                            contexto.put(ConstantesContexto.FECHA_VENCIMIENTO, fechaVencimiento.getTime());

                            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                            throw new FileProcessingException(mensaje);
                    	}
                    }

                    // se almacena la fecha de vencimiento en el contexto para usarla en el cálculo de valor de mora
                    if(fechaVencimiento!= null) {
                    	contexto.put(ConstantesContexto.FECHA_VENCIMIENTO, fechaVencimiento.getTime());
                    }
                    
                }
                else if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo())) {

                    /*
                     * Para los archivos de pensionados, se deben modificar algunas variables ya que se trata de información
                     * que no está contenida en el archivo IP-IPR luego se requiere de la clase de aportante como primera
                     * opción, se busca sí una lectura previa de archivo AP - APR ha proporcionado el dato.
                     * 
                     * Funciona de igual manera para la naturaleza jurídica
                     */
                    Integer naturalezaJuridica = null;
                    NaturalezaJuridicaEnum naturalezaJuridicaEnum = null;

                    if (contexto.get(ConstantesContexto.PASO_VARIABLES) != null) {
                        List<PasoValores> valoresRecibidos = (List<PasoValores>) contexto.get(ConstantesContexto.PASO_VARIABLES);

                        for (PasoValores pasoValores : valoresRecibidos) {
                            if (pasoValores.getNombreVariable().equals(ConstantesContexto.CLASE_APORTANTE)) {
                                claseAportante = pasoValores.getValorVariable();

                                // sí el campo se encuentra, se le agrega al contexto, con lo cual se notifica que fue usado y se puede eliminar
                                contexto.put(ConstantesContexto.CLASE_APORTANTE, claseAportante);

                                // se establece el valor de la cantidad de personas con base en la clase de aportante
                                switch (ClaseAportanteEnum.obtenerClaseAportanteEnum(claseAportante)) {
                                    case CLASE_A:
                                        cantidadPersonas = 200;
                                        break;
                                    case CLASE_B:
                                        cantidadPersonas = 20;
                                        break;
                                    case CLASE_I:
                                        cantidadPersonas = 100;
                                        break;
                                    default:
                                        cantidadPersonas = null;
                                        break;
                                }

                                // si cuento con la cantidad de personas, continuo con el cálculo de los días de mora
                                if (cantidadPersonas != null) {
                                    if (!generarFechaVencimientoYDiasMora(entradasNormatividad, id, tipoArchivoEnum, cantidadPersonas,
                                            claseAportante, oportunidad, periodoAporte, fechaAporte, festivos, naturalezaJuridicaEnum)
                                    		&& !TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaEnum)) {
                                        mensaje = MensajesValidacionEnum.ERROR_CANTIDAD_DIAS_MORA.getReadableMessage(idCampo,
                                                diasMora.toString(), tipoError, nombreCampo, diasMora.toString(),
                                                diasMoraCalculados.toString());

                                        // se almacena la fecha de vencimiento en el contexto para usarla en el cálculo de valor de mora
                                        contexto.put(ConstantesContexto.FECHA_VENCIMIENTO, fechaVencimiento.getTime());

                                        logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                                        throw new FileProcessingException(mensaje);
                                    }

                                    // se almacena la fecha de vencimiento en el contexto para usarla en el cálculo de valor de mora
                                    if(fechaVencimiento!= null) {
                                    	contexto.put(ConstantesContexto.FECHA_VENCIMIENTO, fechaVencimiento.getTime());
                                    }
                         
                                }
                                else {
                                    // error por falta de cantidad de personas
                                    mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                            ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                                            MessagesConstants.CANTIDAD_PERSONAS);

                                    logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                                    throw new FileProcessingException(mensaje);
                                }
                            }
                            else if (pasoValores.getNombreVariable().equals(ConstantesContexto.NATURALEZA_JURIDICA)) {
                                if (pasoValores.getValorVariable() != null && StringUtils.isNumeric(pasoValores.getValorVariable())) {
                                    naturalezaJuridica = Integer.parseInt(pasoValores.getValorVariable());
                                }
                                else {
                                    // error por falta de naturaleza jurídica
                                    mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                            ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                                            MessagesConstants.NATURALEZA_JURIDICA);

                                    logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                                    throw new FileProcessingException(mensaje);
                                }
                            }
                        }
                    }

                    if (claseAportante == null) {
                        /*
                         * En caso de no haber recibido el valor de la clase de aportante desde un archivo AP - APR
                         * es necesario realizar la comprobación con cada uno de los valores posibles para el campo
                         * en la normatividad vigente, los valores posibles son A, B e I defino una variable de
                         * control para determinar sí la cantidad de los días de mora concuerda en algún escenario
                         */
                        boolean concuerda = false;

                        for (ClaseAportanteEnum claseAportanteEnum : ClaseAportanteEnum.values()) {
                            claseAportante = claseAportanteEnum.getCodigo();

                            switch (claseAportanteEnum) {
                                case CLASE_A:
                                    cantidadPersonas = 200;
                                    break;
                                case CLASE_B:
                                    cantidadPersonas = 20;
                                    break;
                                case CLASE_I:
                                    cantidadPersonas = 100;
                                    break;
                                default:
                                    cantidadPersonas = null;
                                    break;
                            }

                            // si cuento con la cantidad de personas, continuo con el cálculo de los días de mora
                            if (cantidadPersonas != null
                                    && generarFechaVencimientoYDiasMora(entradasNormatividad, id, tipoArchivoEnum, cantidadPersonas,
                                            claseAportante, oportunidad, periodoAporte, fechaAporte, festivos, naturalezaJuridicaEnum)) {

                                // teniendo en cuenta que el valor en el archivo concuerda con el dato calculado,  se ingresa la 
                                // clase de aportante empleada al contexto para que el orquestador la persista y se compruebe 
                                // durante la validación de bloque 2 del archivo AP - APR 

                                contexto.put(ConstantesContexto.CLASE_APORTANTE, claseAportante);

                                concuerda = true;
                            }

                            // se almacena la fecha de vencimiento en el contexto para usarla en el cálculo de valor de mora
                            contexto.put(ConstantesContexto.FECHA_VENCIMIENTO, fechaVencimiento.getTime());
                        }

                        // si luego de calcular los días de mora con todas las opciones el valor del campo sigue sin concordar, 
                        // se reporta la inconsistencia y se agrega una clase de aportante "invalida" al contexto, para que la 
                        // lectura del archivo AP - APR no almacene un dato que no será empleado
                        if (!concuerda) {
                            contexto.put(ConstantesContexto.CLASE_APORTANTE, "0");

                            mensaje = MensajesValidacionEnum.ERROR_CLASE_PAGADOR_PENSIONES.getReadableMessage(idCampo, diasMora.toString(),
                                    tipoError, nombreCampo, diasMora.toString());

                            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                            throw new FileProcessingException(mensaje);

                        }
                    }

                    if (naturalezaJuridica != null) {
                        naturalezaJuridicaEnum = NaturalezaJuridicaEnum.obtenerNaturalezaJuridica(naturalezaJuridica);

                        if (naturalezaJuridicaEnum == null) {
                            // error por falta de naturaleza jurídica
                            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                                    MessagesConstants.NATURALEZA_JURIDICA);

                            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                            throw new FileProcessingException(mensaje);
                        }
                    }
                    else {
                        // error por falta de naturaleza jurídica
                        mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                                ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                                MessagesConstants.NATURALEZA_JURIDICA);

                        logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                        throw new FileProcessingException(mensaje);
                    }
                }

            }
            else {
                // error por falta de normatividad u oportunidad
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                        MessagesConstants.NORMATIVIDAD_OPORTUNIDAD);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);

            }
        }
        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

    /**
     * Función para elegir la oportunidad de presentación de planilla entre varias opciones
     * @param List<OportunidadPresentacion>
     *        entradasOportunidad
     * @param independienteMesActual
     * @param independienteMesVencido
     * @return PeriodoPagoPlanillaEnum objeto que representa un tipo de pago
     */
    private PeriodoPagoPlanillaEnum elegirOportunidad(TipoPlanillaEnum tipoPlanilla, List<OportunidadPresentacion> entradasOportunidad,
            boolean independienteMesActual, boolean independienteMesVencido) {

        PeriodoPagoPlanillaEnum result = null;

        for (OportunidadPresentacion oportunidadPresentacion : entradasOportunidad) {
            // para el tipo de archivo requerido

            TipoPlanillaEnum tipoPlanillaOportunidad = TipoPlanillaEnum.obtenerTipoPlanilla(oportunidadPresentacion.getTipoPlanilla());

            if (tipoPlanilla != null && tipoPlanilla.equals(tipoPlanillaOportunidad)) {
                String tipoCotizanteEspecifico = oportunidadPresentacion.getTipoCotizanteEspecifico();
                if (tipoCotizanteEspecifico != null && ((independienteMesActual && tipoCotizanteEspecifico.equals("03"))
                        || (independienteMesVencido && tipoCotizanteEspecifico.equals("57")))) {
                    result = oportunidadPresentacion.getOportunidad();
                }
                else if (oportunidadPresentacion.getTipoCotizanteEspecifico() == null) {
                    result = oportunidadPresentacion.getOportunidad();
                }
            }
        }
        return result;
    }

    /**
     * Este metodo se encarga de generar los dias en mora
     * @param claseAportante
     * @param cantidadPersonas
     * @param tipoArchivo
     * @param id
     * @param entradasNormatividad
     * @param fechaAporte
     * @param periodoAporte
     * @param oportunidad
     * @param festivos
     * @param naturalezaJuridica
     * @return true si no tiene dias en mora
     */
    private boolean generarFechaVencimientoYDiasMora(List<NormatividadFechaVencimiento> entradasNormatividad, String id,
            TipoArchivoPilaEnum tipoArchivo, Integer cantidadPersonas, String claseAportante, PeriodoPagoPlanillaEnum oportunidad,
            String periodoAporte, Date fechaAporte, List<DiasFestivos> festivos, NaturalezaJuridicaEnum naturalezaJuridica)
            throws FileProcessingException {

        String firmaMetodo = "generarFechaVencimientoYDiasMora(List<NormatividadFechaVencimiento>, String, "
                + "TipoArchivoPilaEnum, Integer, String, PeriodoPagoPlanillaEnum, String, Date, List<DiasFestivos>, "
                + "NaturalezaJuridicaEnum)";

        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        String mensaje = null;

        // primero se establece el caso específico de normatividad
        NormatividadFechaVencimiento casoEspecificoNorma = null;

        try {
            casoEspecificoNorma = FuncionesValidador.elegirNormatividad(entradasNormatividad, id, periodoAporte, tipoArchivo,
                    cantidadPersonas, claseAportante, naturalezaJuridica, fechaAporte);
        } catch (TechnicalException e) {
            // se prepara el error de componente con base en el mensaje recibido por parte de la función utilitaria
            mensaje = configurarMensajeError(e.getMessage());

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        // en este punto, contando con un caso puntual de la norma, se establece la fecha de vencimiento
        if (casoEspecificoNorma != null && oportunidad != null) {
        	
        	//Si no existe tipoFecha se salta el calculo de la fecha de vencimiento y dias de mora
        	if(casoEspecificoNorma.getTipoFecha() == null) {
        		return true;
        	}
        	
            try {
                // se sobre escribe la oportunidad en caso que la normatividad defina una por defecto
                PeriodoPagoPlanillaEnum oportunidadReal = casoEspecificoNorma.getOportunidad() != null
                        ? casoEspecificoNorma.getOportunidad() : oportunidad;
                        
                fechaVencimiento = FuncionesValidador.calcularFechaVencimiento(periodoAporte, oportunidadReal, casoEspecificoNorma,
                        festivos);
            } catch (TechnicalException e) {
                // se prepara el error de componente con base en el mensaje recibido por parte de la función utilitaria
                mensaje = configurarMensajeError(e.getMessage());

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            // teniendo una fecha de vencimiento, se compara con la fecha de pago para establecer los días de mora
            if (fechaVencimiento != null) {
                diasMoraCalculados = calcularDiasMora(fechaAporte, fechaVencimiento);

                logger.info("e. diasMora " + diasMora);
                logger.info("e. diasMoraCalculados.intValue() " + diasMoraCalculados.intValue());
                if (diasMora.compareTo(diasMoraCalculados.intValue()) == 0) {
                    
                    logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
                    return true;
                }
            }
            else {
                // error por falta de fecha de vencimiento
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                        ConstantesComunesProcesamientoPILA.FECHA_VENCIMIENTO);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

        }
        else {
            // error por no encontrar un caso específico de norma u oportunidad en la trega que se pueda aplicar
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                    MessagesConstants.NORMATIVIDAD_OPORTUNIDAD);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
            throw new FileProcessingException(mensaje);

        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return false;
    }

    /**
     * Función para calcular la cantidad de dias de mora con base en una fecha de vencimiento
     * @param fechaVencimiento
     * @param fechaAporte
     * @return <b>Long</b>
     *         valor de dias en mora
     * @throws FileProcessingException
     */
    private Long calcularDiasMora(Date fechaAporte, Calendar fechaVencimiento) throws FileProcessingException {
        logger.debug("Inicia calcularDiasMora(Date, Calendar)");
        String mensaje = null;

        Long diasMoraCalculadosFuncion = null;

        try {
            diasMoraCalculadosFuncion = CalendarUtil.getDiferenceBetweenDates(fechaVencimiento, CalendarUtil.toCalendar(fechaAporte),
                    Calendar.SECOND);

            diasMoraCalculadosFuncion = (diasMoraCalculadosFuncion / 60 / 60 / 24);

            // sí la cantidad de días de mora resulta negativa, se reportan como 0
            if (diasMoraCalculadosFuncion.compareTo(0L) < 0) {
                diasMoraCalculadosFuncion = 0L;
            }

        } catch (Exception e) {
            mensaje = MensajesValidacionEnum.ERROR_CALCULO.getReadableMessage(idCampo, diasMora.toString(), tipoError, nombreCampo,
                    MessagesConstants.DIAS_MORA, e.getMessage());
            logger.debug("Finaliza calcularDiasMora(Date, Calendar) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug("Finaliza calcularDiasMora(Date, Calendar)");
        return diasMoraCalculadosFuncion;
    }

    /**
     * Método empleado para la conversión de un mensaje de error simple emitido por las funciones utilitarias de cálculo de fecha de
     * vencimiento
     * @param mensajeSimple
     *        Mensaje de error recibido de la función utilitaria
     * @return <b>String</b>
     *         Mensaje de error para excepción de FileProcessing
     */
    private String configurarMensajeError(String mensajeSimple) {
        String firmaMetodo = "ValidadorDiasMora.configurarMensajeError(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensajeCompuesto = null;

        // se configura el mensaje compuesto con base en el contenido del mensaje simple
        if (mensajeSimple.contains(ConstantesComunesProcesamientoPILA.RANGO_DIGITO_FINAL_ID)) {
            // error por falta de datos de rango
            mensajeCompuesto = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                    ConstantesComunesProcesamientoPILA.RANGO_DIGITO_FINAL_ID);
        }
        else if (mensajeSimple.contains(ConstantesComunesProcesamientoPILA.PERIODO_MAYOR)) {
            mensajeCompuesto = MensajesValidacionEnum.ERROR_CALCULO.getReadableMessage(idCampo, diasMora.toString(), tipoError, nombreCampo,
                    ConstantesComunesProcesamientoPILA.PERIODO_MAYOR, mensajeSimple);
        }
        else if (mensajeSimple.contains(ConstantesComunesProcesamientoPILA.FECHA_VENCIMIENTO)) {
            mensajeCompuesto = MensajesValidacionEnum.ERROR_CALCULO.getReadableMessage(idCampo, diasMora.toString(), tipoError, nombreCampo,
                    ConstantesComunesProcesamientoPILA.FECHA_VENCIMIENTO, mensajeSimple);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return mensajeCompuesto;
    }
}
