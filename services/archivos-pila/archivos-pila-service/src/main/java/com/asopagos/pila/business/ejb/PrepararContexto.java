package com.asopagos.pila.business.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.soporte.PasoValores;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.SubTipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoRegistroArchivoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.business.interfaces.IPersistenciaPreparacionContexto;
import com.asopagos.pila.business.interfaces.IPrepararContexto;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.dto.ControlLoteOFDTO;
import com.asopagos.pila.dto.ControlSubgrupoCorreccionDTO;
import com.asopagos.pila.dto.ErrorDetalladoValidadorDTO;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.util.FuncionesValidador;

/**
 * <b>Descripción:</b> Clase que se encarga de preconfigurar el mapa de variables de contexto
 * antes de iniciar con un bloque de validación<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391, 407, 393 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Stateless
public class PrepararContexto implements IPrepararContexto, Serializable {
    private static final long serialVersionUID = 1L;

    /** Injección del EJB con los servicios de persistencia para la preparación del mapa de contexto */
    @Inject
    private IPersistenciaPreparacionContexto persistenciaPreparacion;

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(PrepararContexto.class);
    
    /**
     * Mapa de valores generales
     * */
    private Map<String, Object> mapaValores = FuncionesValidador.valoresGeneralesValidacion;

    /**
     * (non-Javadoc)
     * @see com.asopagos.pila.business.interfaces.IPrepararContexto#prepararContexto(com.asopagos.enumeraciones.pila.BloqueValidacionEnum,
     *      java.lang.Long, java.lang.Object)
     */
    public Map<String, Object> prepararContexto(BloqueValidacionEnum bloque, Long numeroPlanilla, Object indicePlanilla)
            throws ErrorFuncionalValidacionException {
        logger.debug("Inicia prepararContexto(BloqueValidacionEnum, Long, Map<String, Object>)");
        
        Map<String, Object> contexto = new HashMap<>();
        contexto.put(ConstantesContexto.BLOQUE, bloque);

        try {
        switch (bloque) {
            case BLOQUE_0_OI:
                contexto.put(ConstantesContexto.LIMITE_TAMANO_INDIVIDUAL, mapaValores.get(ConstantesContexto.LIMITE_TAMANO_INDIVIDUAL));
                break;
            case BLOQUE_1_OI:
                contexto.put(ConstantesContexto.OPERADORES_INFORMACION, mapaValores.get(ConstantesContexto.OPERADORES_INFORMACION));
                contexto.put(ConstantesContexto.TIPOS_ID_VALIDOS, consultarTiposIdValidosB1(indicePlanilla));
                contexto.put(ConstantesContexto.CODIGO_CCF, mapaValores.get(ConstantesContexto.CODIGO_CCF));
                break;
            case BLOQUE_2_OI:
                // lista de errores de componente
                contexto.put(ConstantesContexto.ERRORES_DETALLADOS, new ArrayList<ErrorDetalladoValidadorDTO>());

                contexto.put(ConstantesContexto.CODIGO_CCF, mapaValores.get(ConstantesContexto.CODIGO_CCF));
                contexto.put(ConstantesContexto.SALARIO_MINIMO, consultarSMLMV());
                contexto.put(ConstantesContexto.TIPOS_ID_VALIDOS, consultarTiposIdValidosB1(indicePlanilla));
                contexto.put(ConstantesContexto.DEPARTAMENTOS, mapaValores.get(ConstantesContexto.DEPARTAMENTOS));
                contexto.put(ConstantesContexto.MUNICIPIOS, mapaValores.get(ConstantesContexto.MUNICIPIOS));
                contexto.put(ConstantesContexto.DEPARTAMENTOS_MUNICIPIOS, mapaValores.get(ConstantesContexto.DEPARTAMENTOS_MUNICIPIOS));
                contexto.put(ConstantesContexto.OPERADORES_INFORMACION, mapaValores.get(ConstantesContexto.OPERADORES_INFORMACION));
                contexto.put(ConstantesContexto.CODIGOS_CIIU, mapaValores.get(ConstantesContexto.CODIGOS_CIIU));
                contexto.put(ConstantesContexto.FESTIVOS, mapaValores.get(ConstantesContexto.FESTIVOS));
                contexto.put(ConstantesContexto.COTIZANTES_IBC_NO_CERO, consultarCotizantesIBCNoCero());
                contexto.put(ConstantesContexto.TOLERANCIA_VALOR_MORA, consultarMargenToleranciaEnMora());
                
                contexto.put(ConstantesContexto.PASO_VARIABLES, persistenciaPreparacion.consultarVariablesBloquePlanilla(numeroPlanilla,
                        ((IndicePlanilla) indicePlanilla).getCodigoOperadorInformacion(), bloque));
            
                contexto.put(ConstantesContexto.NORMATIVIDAD, mapaValores.get(ConstantesContexto.NORMATIVIDAD));
                contexto.put(ConstantesContexto.OPORTUNIDAD_VENCIMIENTO, mapaValores.get(ConstantesContexto.OPORTUNIDAD_VENCIMIENTO));
                contexto.put(ConstantesContexto.TASAS_INTERES, mapaValores.get(ConstantesContexto.TASAS_INTERES));
                contexto.put(ConstantesContexto.CASOS_DESCUENTO_INTERES, mapaValores.get(ConstantesContexto.CASOS_DESCUENTO_INTERES));

                contexto.put(ConstantesContexto.COTIZANTES_DIAS_CERO, consultarCotizantesCeroDias());
                contexto.put(ConstantesContexto.LISTA_CONTROL_REGISTROS, FuncionesValidador.inicializarListaControlRegistros());
                Map<TipoRegistroArchivoEnum, Set<Long>> listaRegistrosFaltantes = new EnumMap<>(TipoRegistroArchivoEnum.class);
                contexto.put(ConstantesContexto.LISTA_REGISTROS_FALTANTES, listaRegistrosFaltantes);

                contexto.put(ConstantesContexto.INICIO_1429, mapaValores.get(ConstantesContexto.INICIO_1429));
                contexto.put(ConstantesContexto.FIN_1429, mapaValores.get(ConstantesContexto.FIN_1429));
                
                contexto.put(ConstantesContexto.LISTA_CONTROL_CORRECCIONES, new HashMap<String, ControlSubgrupoCorreccionDTO>());

                contexto = consultarUbicacionCCF(contexto);
                break;
            case BLOQUE_3_OI:
                contexto.put(ConstantesContexto.VARIABLE_BLOQUE2, consultarYOrganizarVariablesPasoBloques(numeroPlanilla, bloque,
                        ((IndicePlanilla) indicePlanilla).getCodigoOperadorInformacion()));
                break;
            case BLOQUE_4_OI:
                // lista de errores de componente
                contexto.put(ConstantesContexto.ERRORES_DETALLADOS, new ArrayList<ErrorDetalladoValidadorDTO>());

                contexto.put(ConstantesContexto.MINIMO_DIAS_PAGO, mapaValores.get(ConstantesContexto.MINIMO_DIAS_PAGO));
                contexto.put(ConstantesContexto.FESTIVOS, mapaValores.get(ConstantesContexto.FESTIVOS));
                contexto.put(ConstantesContexto.CODIGO_CCF, mapaValores.get(ConstantesContexto.CODIGO_CCF));
                contexto.put(ConstantesContexto.SALARIO_MINIMO, consultarSMLMV());
                contexto.put(ConstantesContexto.REINTENTOS, mapaValores.get(ConstantesContexto.REINTENTOS));

                contexto.put(ConstantesContexto.COTIZANTES_DIAS_CERO, consultarCotizantesCeroDias());
                contexto.put(ConstantesContexto.MODIFICADOR_SALARIO_INTEGRAL, consultarModificadorSalarioIntegral());
                listaRegistrosFaltantes = new EnumMap<>(TipoRegistroArchivoEnum.class);
                contexto.put(ConstantesContexto.LISTA_REGISTROS_FALTANTES, listaRegistrosFaltantes);
                break;
            case BLOQUE_0_OF:
                contexto.put(ConstantesContexto.CODIGO_CCF, mapaValores.get(ConstantesContexto.CODIGO_CCF));
                contexto.put(ConstantesContexto.LIMITE_TAMANO_INDIVIDUAL, mapaValores.get(ConstantesContexto.LIMITE_TAMANO_INDIVIDUAL));
                break;
            case BLOQUE_1_OF:
                // lista de errores de componente
                contexto.put(ConstantesContexto.ERRORES_DETALLADOS, new ArrayList<ErrorDetalladoValidadorDTO>());

                // listado de Operadores de Información
                contexto.put(ConstantesContexto.OPERADORES_INFORMACION, mapaValores.get(ConstantesContexto.OPERADORES_INFORMACION));

                // listado de Operadores Financieros
                contexto.put(ConstantesContexto.OPERADORES_FINANCIEROS, mapaValores.get(ConstantesContexto.OPERADORES_FINANCIEROS));

                contexto.put(ConstantesComunesProcesamientoPILA.TIPO_ARCHIVO, "F");

                // Set de números de planilla incluidos en el archivo OF
                contexto.put(ConstantesContexto.MAPA_NUMEROS_PLANILLA_EN_OF, new HashSet<String>());
                contexto.put(ConstantesContexto.LISTA_CONTROL_REGISTROS, FuncionesValidador.inicializarListaControlRegistros());
                listaRegistrosFaltantes = new EnumMap<>(TipoRegistroArchivoEnum.class);
                contexto.put(ConstantesContexto.LISTA_REGISTROS_FALTANTES, listaRegistrosFaltantes);

                // Mapa de control de sumatorias por lote
                contexto.put(ConstantesContexto.MAPA_CONTROL_SUMATORIAS_LOTES_OF, new HashMap<Integer, ControlLoteOFDTO>());
                break;
            default:
                break;
        }
        } catch (Exception e) {
			e.printStackTrace();
		}

        logger.debug("Finaliza prepararContexto(BloqueValidacionEnum, Long, Map<String, Object>)");
        return contexto;
    }

    /**
     * Función encargada de obtener el listado de tipos de identificiación válidos para el B1
     * @param indicePlanilla
     *        Índice de planilla para determinar los tipos de identificación válidos por tipo de archivo
     * @return <b>List<TipoIdentificacionEnum></b>
     *         Listado de los tipos de identificación válidos para el nombre del archivo en B1
     */
    private List<TipoIdentificacionEnum> consultarTiposIdValidosB1(Object indicePlanilla) {
        logger.debug("Inicia consultarTiposIdValidosB1(Object)");

        List<TipoIdentificacionEnum> result = new ArrayList<>();

        if (indicePlanilla != null && indicePlanilla instanceof IndicePlanilla) {
            if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(((IndicePlanilla) indicePlanilla).getTipoArchivo().getGrupo())) {
                result.add(TipoIdentificacionEnum.NIT);
                result.add(TipoIdentificacionEnum.CEDULA_CIUDADANIA);
                result.add(TipoIdentificacionEnum.CEDULA_EXTRANJERIA);
                result.add(TipoIdentificacionEnum.CARNE_DIPLOMATICO);
                result.add(TipoIdentificacionEnum.PASAPORTE);
                result.add(TipoIdentificacionEnum.PERM_ESP_PERMANENCIA);
                result.add(TipoIdentificacionEnum.PERM_PROT_TEMPORAL);
            }
            else {
                result.add(TipoIdentificacionEnum.NIT);
                result.add(TipoIdentificacionEnum.CEDULA_CIUDADANIA);
                result.add(TipoIdentificacionEnum.CEDULA_EXTRANJERIA);
                result.add(TipoIdentificacionEnum.CARNE_DIPLOMATICO);
                result.add(TipoIdentificacionEnum.PASAPORTE);
                result.add(TipoIdentificacionEnum.TARJETA_IDENTIDAD);
                result.add(TipoIdentificacionEnum.SALVOCONDUCTO);
                result.add(TipoIdentificacionEnum.PERM_ESP_PERMANENCIA);
                result.add(TipoIdentificacionEnum.PERM_PROT_TEMPORAL);
            }
        }

        logger.debug("Finaliza consultarTiposIdValidosB1(Object)");
        return result;
    }

    /**
     * Función para obtener un listado de los tipos de cotizante que no admiten un valor de IBC de cero
     * (Tabla 10, pestaña 2, Anexo 2.1.1, valores obligatorios (x))
     * 
     * @return <b>List<TipoCotizanteEnum></b>
     *         Listado con los tipos de cotizante que no admiten un valor de IBC de cero
     */
    private List<TipoCotizanteEnum> consultarCotizantesIBCNoCero() {
        logger.debug("Inicia consultarCotizantesIBCNoCero()");

        List<TipoCotizanteEnum> result = new ArrayList<>();

        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE);

        logger.debug("Finaliza consultarCotizantesIBCNoCero()");
        return result;
    }

    /**
     * Función para obtener un listado de los tipos de cotizante que admiten cero días cotizados
     * (Tabla 10, pestaña 2, Anexo 2.1.1, valores opcionales (o))
     * 
     * @return <b>List<TipoCotizanteEnum></b>
     *         Listado con los tipos de cotizante que admiten cero días cotizados
     */
    private List<TipoCotizanteEnum> consultarCotizantesCeroDias() {
        logger.debug("Inicia consultarCotizantesCeroDias()");
        List<TipoCotizanteEnum> result = new ArrayList<>();

        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPENDIENTE);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_ESTUDIANTES_REG_ESP);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_AFILIADO_PARTICIPE);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_VOLUNTARIO_ARL);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES);
        result.add(TipoCotizanteEnum.TIPO_COTIZANTE_EDIL_JAL);

        logger.debug("Finaliza consultarCotizantesCeroDias()");
        return result;
    }

    /**
     * Función para consultar el listado de variables de paso entre bloques
     * 
     * @param numeroPlanilla
     *        Número de la planilla consultada
     * @param bloque
     *        bloque de validación que emplea las variables de paso
     * @param codOperador 
     *        Código del operador de información que emite la planilla
     * @return Map<String, String[]>
     *         Mapa con el listado de las variables de paso consultado para el bloque
     * @throws ErrorFuncionalValidacionException
     */
    private Map<String, String[]> consultarYOrganizarVariablesPasoBloques(Long numeroPlanilla, BloqueValidacionEnum bloque, String codOperador)
            throws ErrorFuncionalValidacionException {
        logger.debug("Inicia consultarVariablesPasoBloques(String, BloqueValidacionEnum, String)");
        HashMap<String, String[]> variables = null;

        List<PasoValores> result = persistenciaPreparacion.consultarVariablesBloquePlanilla(numeroPlanilla, codOperador, bloque);

        if (result != null && !result.isEmpty()) {
            variables = new HashMap<>();

            for (PasoValores valor : result) {
                /**
                 * la información se organiza en el mapa de la siguiente forma
                 * ConstantesContexto.: Nombre del campo
                 * Value: {valor en archivo A, valor en archivo I, código del campo A, código de campo I, tipo archivo A, tipo archivo I}
                 * 
                 */

                // primero se verifica sí el nombre de la variable ya se encuentra en el mapa
                if (variables.get(valor.getNombreVariable()) == null) {
                    // sí no se tiene se agrega
                    variables.put(valor.getNombreVariable(), new String[6]);
                }

                // luego se agrega el valor, dependiendo del tipo de archivo definido en el dato leído
                if (SubTipoArchivoPilaEnum.INFORMACION_APORTANTE.equals(valor.getTipoPlanilla().getSubtipo())) {
                    variables.get(valor.getNombreVariable())[0] = valor.getValorVariable();
                    variables.get(valor.getNombreVariable())[2] = valor.getCodigoCampo();
                    variables.get(valor.getNombreVariable())[4] = valor.getTipoPlanilla().getCodigo();
                }
                else {
                    variables.get(valor.getNombreVariable())[1] = valor.getValorVariable();
                    variables.get(valor.getNombreVariable())[3] = valor.getCodigoCampo();
                    variables.get(valor.getNombreVariable())[5] = valor.getTipoPlanilla().getCodigo();
                }
            }
        }

        logger.debug("Finaliza consultarVariablesPasoBloques(String, BloqueValidacionEnum, String)");
        return variables;
    }

    /**
     * Función para consultar los códigos de ciudad y departamento de la CCF
     */
    private Map<String, Object> consultarUbicacionCCF(Map<String, Object> contexto) {
        contexto.put(ConstantesContexto.CODIGO_DPTO_CCF, mapaValores.get(ConstantesContexto.CODIGO_DPTO_CCF));
        contexto.put(ConstantesContexto.CODIGO_MUNI_CCF, mapaValores.get(ConstantesContexto.CODIGO_MUNI_CCF));
        return contexto;
    }

    /**
     * Función para consultar el valor del SMLMV de un período o año específico
     */
    private BigDecimal consultarSMLMV() {
        // TODO se debe cambiar por consulta a histórico de parámetros por año de período
        return new BigDecimal((String) mapaValores.get(ConstantesContexto.SALARIO_MINIMO));
    }

    /**
     * Función para obtener el margen de tolerancia en la comparación del valor de mora
     * @return Margen de tolrancia parametrizado
     */
    private BigDecimal consultarMargenToleranciaEnMora() {
        logger.debug("Inicia consultarMargenToleranciaEnMora()");
        BigDecimal result = null;

        result = new BigDecimal((String) mapaValores.get(ConstantesContexto.TOLERANCIA_VALOR_MORA));

        logger.debug("Finaliza consultarMargenToleranciaEnMora()");
        return result;
    }

    /**
     * Método encargado de la consulta del parámetro para el cálculo del valor mínimo para un salario integral
     * 
     * @return <b>Integer</b>
     *         Modificador del salario mínimo para establecer el valor mínimo de un salario integral
     */
    private BigDecimal consultarModificadorSalarioIntegral() {
        logger.debug("Inicia consultarModificadorSalarioIntegral()");
        BigDecimal result = null;

        result = new BigDecimal((String) mapaValores.get(ConstantesContexto.MODIFICADOR_SALARIO_INTEGRAL));

        logger.debug("Finaliza consultarModificadorSalarioIntegral()");
        return result;
    }
}
