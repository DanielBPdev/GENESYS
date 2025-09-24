package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.asopagos.entidades.pila.staging.RegistroDetallado;
import com.asopagos.enumeraciones.SubTipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.cartera.MotivoFiscalizacionAportanteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroCorreccionEnum;
import com.asopagos.enumeraciones.pila.MarcaRegistroAporteArchivoEnum;

/**
 * DTO con los datos de un Registro detallado en la staging.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 * @author Alfonso Baquero E. <abaquero@heinsohn.com.co>
 *
 */
public class RegistroDetalladoModeloDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Código identificador de llave primaria llamada No. de operación de recaudo
     */
    private Long id;
    /**
     * Referencia al registro general del aporte (staging)
     */
    private Long registroGeneral;
    /**
     * Indica el tipo identificación del cotizante. indicado en <code>TipoIdentificacionEnum</code>
     */
    private TipoIdentificacionEnum tipoIdentificacionCotizante;
    /**
     * Indica el Número de identificación del cotizante.
     */
    private String numeroIdentificacionCotizante;
    /**
     * Indica el tipo de cotizante indicado en <code>TipoAfiliadoEnum</code>
     */
    private Short tipoCotizante;
    /**
     * Indica el código del Departamento de la ubicación laboral.
     */
    private String codDepartamento;
    /**
     * Indica el código del Municipio de la ubicación laboral.
     */
    private String codMunicipio;
    /**
     * Indica el primer apellido del cotizante.
     */
    private String primerApellido;
    /**
     * Indica el segundo apellido del cotizante.
     */
    private String segundoApellido;
    /**
     * Indica el primer nombre del cotizante.
     */
    private String primerNombre;
    /**
     * Indica el segundo nombre del cotizante.
     */
    private String segundoNombre;
    /**
     * Indica que el registro de aporte relaciona novedad ING: Ingreso.
     */
    private String novIngreso;
    /**
     * Indica que el registro de aporte relaciona novedad RET: Retiro.
     */
    private String novRetiro;
    /**
     * Indica que el registro de aporte relaciona novedad VSP: Variación permanente de salario.
     */
    private String novVSP;
    /**
     * Indica que el registro de aporte relaciona novedad VST: Variación transitoria del salario.
     */
    private String novVST;
    /**
     * Indica que el registro de aporte relaciona novedad SLN: Suspensión temporal
     * del contrato de trabajo, licencia no remunerada o comisión de servicios.
     */
    private String novSLN;
    /**
     * Indica que el registro de aporte relaciona novedad IGE: Incapacidad Temporal
     * por Enfermedad General.
     */
    private String novIGE;
    /**
     * Indica que el registro de aporte relaciona novedad LMA: Licencia de Maternidad o paternidad.
     */
    private String novLMA;
    /**
     * Indica que el registro de aporte relaciona novedad VAC - LR: Vacaciones, Licencia remunerada
     */
    private String novVACLR;
    /**
     * Indica que el registro de aporte relaciona novedad SUS: Suspensión (pensionados)
     */
    private String novSUS;
    /**
     * Contenido del Registro tipo 2 campo 23: IRL: días de incapacidad por accidente de
     * trabajo o enfermedad laboral
     */
    private String diasIRL;
    /**
     * Indica los días cotizados.
     */
    private Short diasCotizados;
    /**
     * Indica el valor del Salario básico.
     */
    private BigDecimal salarioBasico;
    /**
     * Indica el valor de Ingreso Base Cotización (IBC)
     */
    private BigDecimal valorIBC;
    /**
     * Indica la Tarifa.
     */
    private BigDecimal tarifa;
    /**
     * Indica el valor de Aporte obligatorio.
     */
    private BigDecimal aporteObligatorio;
    /**
     * Indica que tipo de Correccion es el registro de aporte
     */
    private String correcciones;
    /**
     * Indica el valor del Salario Integral
     */
    private String salarioIntegral;
    /**
     * Indica la fecha de novedad ingreso
     */
    private Long fechaIngreso;
    /**
     * Indica la fecha de novedad retiro
     */
    private Long fechaRetiro;
    /**
     * Indica la fecha inicio novedad VSP
     */
    private Long fechaInicioVSP;
    /**
     * Indica la fecha inicio novedad SLN
     */
    private Long fechaInicioSLN;
    /**
     * Indica la fecha fin novedad SLN
     */
    private Long fechaFinSLN;
    /**
     * Indica la fecha inicio novedad IGE
     */
    private Long fechaInicioIGE;
    /**
     * Indica la fecha fin novedad IGE
     */
    private Long fechaFinIGE;
    /**
     * Indica la fecha inicio novedad LMA
     */
    private Long fechaInicioLMA;
    /**
     * Indica la fecha fin novedad LMA
     */
    private Long fechaFinLMA;
    /**
     * Indica la fecha inicio novedad VAC - LR
     */
    private Long fechaInicioVACLR;
    /**
     * Indica la fecha fin novedad VAC - LR
     */
    private Long fechaFinVACLR;
    /**
     * Indica la fecha inicio novedad VCT
     */
    private Long fechaInicioVCT;
    /**
     * Indica la fecha fin novedad VCT
     */
    private Long fechaFinVCT;
    /**
     * Indica la fecha inicio novedad IRL
     */
    private Long fechaInicioIRL;
    /**
     * Indica la fecha fin novedad IRL
     */
    private Long fechaFinIRL;
    private Long fechaInicioSuspension;
    private Long fechaFinSuspension;
    /**
     * Indica el número de horas laboradas
     */
    private Short horasLaboradas;
    /**
     * Referencia al registro de control asociado al registro general del aporte cargado
     * por operador de informacion mediante proceso automatico en staging
     */
    private Long registroControl;

    /**
     * Indica el campo de salida del proceso: marca de validacion del
     * registro del aporte en el procesamiento del archivo
     */
    private MarcaRegistroAporteArchivoEnum outMarcaValidacionRegistroAporte;

    /**
     * Indica el campo de salida del proceso: Estado del registro
     * del aporte en el procesamiento del archivo
     */
    private EstadoRegistroAportesArchivoEnum outEstadoRegistroAporte;

    /**
     * Indica el campo de salida del proceso: Identifica si el
     * registro de aporte es marcado por analisis integral
     */
    private Boolean outAnalisisIntegral;

    /**
     * Indica el campo de salida del proceso: Fecha de procesamiento
     * del registro del aporte (al momento de aplicar las validaciones
     * en el proceso)
     */
    private Long outFechaProcesamientoValidRegAporte;

    /**
     * Indica el campo de salida del proceso: Estado en el
     * procesamiento de validacion de Clase de Aportante (V0),
     * para el registro del archivo PILA indicado en <code>EstadoValidacionRegistroAporteEnum</code>
     */
    private EstadoValidacionRegistroAporteEnum outEstadoValidacionV0;

    /**
     * Indica el campo de salida del proceso: Estado en el procesamiento
     * de validacion de la tarifa (V1), para el registro del aporte de
     * archivo PILA indicado en <code>EstadoValidacionRegistroAporteEnum</code>
     */
    private EstadoValidacionRegistroAporteEnum outEstadoValidacionV1;

    /**
     * Indica el campo de salida del proceso: Estado en el procesamiento
     * de validacion del tipo de cotizante (V2), para el registro del
     * aporte de archivo PILA indicado en <code>EstadoValidacionRegistroAporteEnum</code>
     */
    private EstadoValidacionRegistroAporteEnum outEstadoValidacionV2;

    /**
     * Indica el campo de salida del proceso: Estado en el procesamiento
     * de validacion de los dias cotizados (V3), para el registro del
     * aporte de archivo PILA indicado en <code>EstadoValidacionRegistroAporteEnum</code>
     */
    private EstadoValidacionRegistroAporteEnum outEstadoValidacionV3;

    /**
     * Indica el campo de salida del proceso: clase del trabajador indicada en <code>ClaseTrabajadorEnum</code>
     */
    private String outClaseTrabajador;

    /**
     * Indica el campo de salida del proceso: Porcentaje de Pago de Aportes
     */
    private BigDecimal outPorcentajePagoAportes;

    /**
     * Indica el campo de salida del proceso: el estado del solicitante
     */
    private String outEstadoSolicitante;

    /**
     * Indica el campo de salida del proceso: es trabajador reintegrable Si=[1], No=[0]
     */
    private Boolean outEsTrabajadorReintegrable;
    /**
     * Indica la fecha de ingreso del cotizante.
     */
    private Long outFechaIngresoCotizante;
    /**
     * Indica la fecha de retiro del cotizante.
     */
    private Long outFechaRetiroCotizante;
    /**
     * Indica la fecha de la ultima novedad del cotizante.
     */
    private Long outFechaUltimaNovedad;
    /**
     * Indica el usuario que aprueba el registro del aporte.
     */
    private String usuarioAprobadorAporte;
    /**
     * Indica el número de operación de aprobacion
     */
    private String numeroOperacionAprobacion;
    /**
     * Estado del registro respecto a situación de corrección o ajuste
     */
    private EstadoAporteEnum estadoEvaluacion;
    /**
     * Estado de validación de registros de corrección
     */
    private EstadoValidacionRegistroCorreccionEnum estadoValidacionCorreccion;
    
    /**
     * Indica el código de la sucursal del cotizante en BD transaccional
     * */
    private String outCodSucursal;
    
    /**
     * Indica el nombre de la sucursal del cotizante en BD transaccional
     * */
    private String outNomSucursal;

    /**
     * Indica la decisíon de registrar o no registrar el registro en caso de corrección (Validación vs BD)
     */
    private Boolean outRegistrado;

    /**
     * Indica el tipo de afiliado del cotizante
     */
    private TipoAfiliadoEnum outTipoAfiliado;
    
    /**
     * Indica el valor de mora específico para el cotizante
     * */
    private BigDecimal outValorMoraCotizante;
    
    /**
     * Valor Aporte Obligatorio calculado por escenario de simulación manual
     * */
    private BigDecimal outAporteObligatorioMod;
    
    /**
     * Valor de los días cotizados calculado por escenario de simulación manual
     * */
    private Short outDiasCotizadosMod;
    
    /**
     * Valor IBC calculado por escenario de simulación manual
     * */
    private BigDecimal outValorIBCMod;
    
    /**
     * Valor de mora por cotizante calculado por escenario de simulación manual
     * */
    private BigDecimal outValorMoraCotizanteMod;

    /**
     * Indica la decisíon de registrar o no registrar el registro en caso de corrección (Registro / Relación Aporte)
     */
    private Boolean outRegistradoAporte;

    /**
     * Indica la decisíon de registrar o no registrar el registro en caso de corrección (Registro Novedades)
     */
    private Boolean outRegistradoNovedad;
    
     /**
     * Id del registro detallado de archivo original asociado a corrección
     * */
    private Long outIdRegDetOriginal;

    /**
     * Estado de registro o relación de aporte (Fase 2)
     * */
    private EstadoRegistroAporteEnum outEstadoRegistroRelacionAporte;    

    /**
     * Estado del registro respecto a situación de corrección o ajuste para el aporte
     */
    private EstadoAporteEnum outEstadoEvaluacionAporte;

    /**
     * Indica el subtipo de cotizante (campo de aplicación)
     * */
    private SubTipoCotizanteEnum subTipoCotizante;
    
    /**
     * Indica la fecha inicio novedad VST (aportes manuales)
     */
    private Long fechaInicioVST;
    
    /**
     * Indica la fecha fin novedad VST (aportes manuales)
     */
    private Long fechaFinVST;

    /**
     * Indicador de grupo familiar reintegrable
     * */
    private Boolean outGrupoFamiliarReintegrable;
    
    /**
     * Identificador de registro tipo 2 de pila
     * */
    private Long idRegistro2pila;
    
    /**
     * Marca a nivel de cotizante de envío a fiscalización para independientes en planillas mixtas
     * */
    private Boolean outEnviadoAFiscalizacionInd;
    
    /**
     * Motivo a nivel de cotizante de envío a fiscalización para independientes en planillas mixtas
     * */
    private MotivoFiscalizacionAportanteEnum outMotivoFiscalizacionInd;
    
    /***
     * Indica que se trata del registro actual (inicial o luego de modificaciones)
     */
    private Boolean outRegistroActual;
    
    /**
     * Referencia al registro detallado original (sólo aplica para registros creados a partir de devoluciones)
     * */
    private Long outRegInicial;
    
    /**
     * Contiene el numero agrupador de los registro de tipo A y C de una planilla de corrección
     */
    private Integer grupoAC;
    
    /**
     * Indica la Tarifa calculada por escenario de simulación manual
     */
    private BigDecimal outTarifaMod;
    
    /**
     * usuario que ejecuta la acción
     */
    private String usuarioAccion;
    
    /**
     * fecha en la que se realiza la acción
     */
    private Date fechaAccion; 
    
    /**
     * Constructor por defecto
     * */
    public RegistroDetalladoModeloDTO(){
        super();
    }
    
    /**
     * Constructor con base en entity
     * */
    public RegistroDetalladoModeloDTO(RegistroDetallado registroDetallado){
        super();
        convertToDTO(registroDetallado);
    }
    
    
    public RegistroDetalladoModeloDTO(int flag, RegistroDetallado registroDetallado){
    	this.correcciones = registroDetallado.getCorrecciones();
    	this.estadoValidacionCorreccion = registroDetallado.getEstadoRegistroCorreccion();
    	this.outRegistrado = registroDetallado.getOutRegistrado();
    	this.outRegistradoAporte = registroDetallado.getOutRegistradoAporte();
    	this.outEstadoRegistroAporte = registroDetallado.getOutEstadoRegistroAporte();
    	this.outRegistradoNovedad = registroDetallado.getOutRegistradoNovedad();
    }

    /**
     * @param registroGeneral
     * @param grupoAC
     * @param tipoIdentificacionCotizante
     * @param numeroIdentificacionCotizante
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param tipoCotizante
     * @param aporteObligatorio
     * @param diasCotizados
     * @param tarifa
     * @param novIngreso
     * @param novRetiro
     * @param novVSP
     * @param novVST
     * @param novSLN
     * @param novIGE
     * @param novLMA
     * @param novVACLR
     * @param diasIRL
     * @param correcciones
     */
	public RegistroDetalladoModeloDTO(Long id, Integer grupoAC,
			TipoIdentificacionEnum tipoIdentificacionCotizante, String numeroIdentificacionCotizante,
			String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
			Short tipoCotizante, BigDecimal aporteObligatorio, Short diasCotizados, BigDecimal tarifa,
			String novIngreso, String novRetiro, String novVSP, String novVST, String novSLN, String novIGE,
			String novLMA, String novVACLR, String diasIRL, String correcciones, Long registroControl) {

		this.id = id;
		this.grupoAC = grupoAC;
		this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
		this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.tipoCotizante = tipoCotizante;
		this.aporteObligatorio = aporteObligatorio;
		this.diasCotizados = diasCotizados;
		this.tarifa = tarifa;
		this.novIngreso = novIngreso;
		this.novRetiro = novRetiro;
		this.novVSP = novVSP;
		this.novVST = novSLN;
		this.novSLN = novSLN;
		this.novIGE = novIGE;
		this.novLMA = novLMA;
		this.novVACLR = novVACLR;
		this.diasIRL = diasIRL;
		this.correcciones = correcciones;
		this.registroControl = registroControl;
	}
    
    /**
     * Método encargado de convertir de DTO a Entidad.
     * @return entidad convertida.
     */
    public RegistroDetallado convertToEntity() {
        RegistroDetallado registroDetallado = new RegistroDetallado();
        registroDetallado.setAporteObligatorio(this.getAporteObligatorio());
        registroDetallado.setCodDepartamento(this.getCodDepartamento());
        registroDetallado.setCodMunicipio(this.getCodMunicipio());
        registroDetallado.setCorrecciones(this.getCorrecciones());
        registroDetallado.setDiasCotizados(this.getDiasCotizados());
        registroDetallado.setDiasIRL(this.getDiasIRL());
        if (this.getFechaFinIGE() != null) {
            registroDetallado.setFechaFinIGE(new Date(this.getFechaFinIGE()));
        }
        if (this.getFechaFinIRL() != null) {
            registroDetallado.setFechaFinIRL(new Date(this.getFechaFinIRL()));
        }
        if (this.getFechaFinLMA() != null) {
            registroDetallado.setFechaFinLMA(new Date(this.getFechaFinLMA()));
        }
        if (this.getFechaFinSLN() != null) {
            registroDetallado.setFechaFinSLN(new Date(this.getFechaFinSLN()));
        }
        if (this.getFechaFinSuspension() != null) {
            registroDetallado.setFechaFinSuspension(new Date(this.getFechaFinSuspension()));
        }
        if (this.getFechaFinVACLR() != null) {
            registroDetallado.setFechaFinVACLR(new Date(this.getFechaFinVACLR()));
        }
        if (this.getFechaFinVCT() != null) {
            registroDetallado.setFechaFinVCT(new Date(this.getFechaFinVCT()));
        }
        if (this.getFechaIngreso() != null) {
            registroDetallado.setFechaIngreso(new Date(this.getFechaIngreso()));
        }
        if (this.getFechaRetiro() != null) {
            registroDetallado.setFechaRetiro(new Date(this.getFechaRetiro()));
        }
        if (this.getFechaInicioIGE() != null) {
            registroDetallado.setFechaInicioIGE(new Date(this.getFechaInicioIGE()));
        }
        if (this.getFechaInicioIRL() != null) {
            registroDetallado.setFechaInicioIRL(new Date(this.getFechaInicioIRL()));
        }
        if (this.getFechaInicioLMA() != null) {
            registroDetallado.setFechaInicioLMA(new Date(this.getFechaInicioLMA()));
        }
        if (this.getFechaInicioSLN() != null) {
            registroDetallado.setFechaInicioSLN(new Date(this.getFechaInicioSLN()));
        }
        if (this.getFechaInicioSuspension() != null) {
            registroDetallado.setFechaInicioSuspension(new Date(this.getFechaInicioSuspension()));
        }
        if (this.getFechaInicioVACLR() != null) {
            registroDetallado.setFechaInicioVACLR(new Date(this.getFechaInicioVACLR()));
        }
        if (this.getFechaInicioVCT() != null) {
            registroDetallado.setFechaInicioVCT(new Date(this.getFechaInicioVCT()));
        }
        if (this.getFechaInicioVSP() != null) {
            registroDetallado.setFechaInicioVSP(new Date(this.getFechaInicioVSP()));
        }
        registroDetallado.setHorasLaboradas(this.getHorasLaboradas());
        registroDetallado.setId(this.getId());
        registroDetallado.setNovIGE(this.getNovIGE());
        registroDetallado.setNovIngreso(this.getNovIngreso());
        registroDetallado.setNovLMA(this.getNovLMA());
        registroDetallado.setNovRetiro(this.getNovRetiro());
        registroDetallado.setNovSLN(this.getNovSLN());
        registroDetallado.setNovSUS(this.getNovSUS());
        registroDetallado.setNovVACLR(this.getNovVACLR());
        registroDetallado.setNovVSP(this.getNovVSP());
        registroDetallado.setNovVST(this.getNovVST());
        registroDetallado.setNumeroIdentificacionCotizante(this.getNumeroIdentificacionCotizante());
        registroDetallado.setOutAnalisisIntegral(this.getOutAnalisisIntegral());
        registroDetallado.setOutClaseTrabajador(this.getOutClaseTrabajador());
        registroDetallado.setOutEstadoRegistroAporte(this.getOutEstadoRegistroAporte());
        registroDetallado.setOutEstadoSolicitante(this.getOutEstadoSolicitante());
        registroDetallado.setOutEstadoValidacionV0(this.getOutEstadoValidacionV0());
        registroDetallado.setOutEstadoValidacionV1(this.getOutEstadoValidacionV1());
        registroDetallado.setOutEstadoValidacionV2(this.getOutEstadoValidacionV2());
        registroDetallado.setOutEstadoValidacionV3(this.getOutEstadoValidacionV3());
        registroDetallado.setOutEsTrabajadorReintegrable(this.getOutEsTrabajadorReintegrable());
        if (this.getOutFechaIngresoCotizante() != null) {
            registroDetallado.setOutFechaIngresoCotizante(new Date(this.getOutFechaIngresoCotizante()));
        }
        if (this.getOutFechaRetiroCotizante() != null) {
            registroDetallado.setOutFechaRetiroCotizante(new Date(this.getOutFechaRetiroCotizante()));
        }
        if (this.getOutFechaProcesamientoValidRegAporte() != null) {
            registroDetallado.setOutFechaProcesamientoValidRegAporte(new Date(this.getOutFechaProcesamientoValidRegAporte()));
        }
        if (this.getOutFechaUltimaNovedad() != null) {
            registroDetallado.setOutFechaUltimaNovedad(new Date(this.getOutFechaUltimaNovedad()));
        }
        registroDetallado.setOutMarcaValidacionRegistroAporte(this.getOutMarcaValidacionRegistroAporte());
        registroDetallado.setOutPorcentajePagoAportes(this.getOutPorcentajePagoAportes());
        registroDetallado.setPrimerApellido(this.getPrimerApellido());
        registroDetallado.setPrimerNombre(this.getPrimerNombre());
        registroDetallado.setRegistroControl(this.getRegistroControl());
        registroDetallado.setRegistroGeneral(this.getRegistroGeneral());
        registroDetallado.setSalarioBasico(this.getSalarioBasico());
        registroDetallado.setSalarioIntegral(this.getSalarioIntegral());
        registroDetallado.setSegundoApellido(this.getSegundoApellido());
        registroDetallado.setSegundoNombre(this.getSegundoNombre());
        registroDetallado.setTarifa(this.getTarifa());
        registroDetallado.setTipoCotizante(this.getTipoCotizante());
        registroDetallado.setTipoIdentificacionCotizante(this.getTipoIdentificacionCotizante());
        registroDetallado.setValorIBC(this.getValorIBC());
        registroDetallado.setUsuarioAprobadorAporte(this.getUsuarioAprobadorAporte());
        registroDetallado.setNumeroOperacionAprobacion(this.getNumeroOperacionAprobacion());
        registroDetallado.setEstadoEvaluacion(this.getEstadoEvaluacion());
        registroDetallado.setEstadoRegistroCorreccion(this.getEstadoValidacionCorreccion());
        registroDetallado.setOutCodSucursal(this.getOutCodSucursal());
        registroDetallado.setOutNomSucursal(this.getOutNomSucursal());
        
        registroDetallado.setOutRegistrado(this.getOutRegistrado());
        registroDetallado.setOutRegistradoAporte(this.getOutRegistradoAporte());
        registroDetallado.setOutRegistradoNovedad(this.getOutRegistradoNovedad());
        
        registroDetallado.setOutTipoAfiliado(this.getOutTipoAfiliado());
        registroDetallado.setOutValorMoraCotizante(this.getOutValorMoraCotizante());
        registroDetallado.setOutAporteObligatorioMod(this.getOutAporteObligatorioMod());
        registroDetallado.setOutDiasCotizadosMod(this.getOutDiasCotizadosMod());
        registroDetallado.setOutValorIBCMod(this.getOutValorIBCMod());
        registroDetallado.setOutValorMoraCotizanteMod(this.getOutValorMoraCotizanteMod());
        registroDetallado.setOutIdRegDetOriginal(this.getOutIdRegDetOriginal());
        registroDetallado.setOutEstadoRegistroRelacionAporte(this.getOutEstadoRegistroRelacionAporte());
        registroDetallado.setOutEstadoEvaluacionAporte(this.getOutEstadoEvaluacionAporte());
        if (this.getFechaInicioVST() != null) {
            registroDetallado.setFechaInicioVST(new Date(this.getFechaInicioVST()));
        }
        if (this.getFechaFinVST() != null) {
            registroDetallado.setFechaFinVST(new Date(this.getFechaFinVST()));
        }
        registroDetallado.setOutGrupoFamiliarReintegrable(this.getOutGrupoFamiliarReintegrable());
        registroDetallado.setIdRegistro2pila(this.getIdRegistro2pila());
        registroDetallado.setOutEnviadoAFiscalizacionInd(this.getOutEnviadoAFiscalizacionInd());
        registroDetallado.setOutMotivoFiscalizacionInd(this.getOutMotivoFiscalizacionInd());
        if (this.getOutRegistroActual() != null) {
            registroDetallado.setOutRegistroActual(this.getOutRegistroActual());
        }
        else {
            registroDetallado.setOutRegistroActual(Boolean.FALSE);
        }
        
        registroDetallado.setOutRegInicial(this.getOutRegInicial());
        registroDetallado.setGrupoAC(this.getGrupoAC());
        registroDetallado.setOutTarifaMod(this.getOutTarifaMod());
        
        if(this.getUsuarioAccion() != null){
        	registroDetallado.setUsuarioAccion(this.getUsuarioAccion());
        }
        if(this.getFechaAccion() != null){
        	registroDetallado.setFechaAccion(this.getFechaAccion());
        }
        
        return registroDetallado;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param registroDetallado
     *        entidad a convertir.
     */
    public void convertToDTO(RegistroDetallado registroDetallado) {
        this.id = registroDetallado.getId();
        this.registroGeneral = registroDetallado.getRegistroGeneral();
        this.tipoIdentificacionCotizante = registroDetallado.getTipoIdentificacionCotizante();
        this.numeroIdentificacionCotizante = registroDetallado.getNumeroIdentificacionCotizante();
        this.tipoCotizante = registroDetallado.getTipoCotizante();
        this.codDepartamento = registroDetallado.getCodDepartamento();
        this.codMunicipio = registroDetallado.getCodMunicipio();
        this.primerApellido = registroDetallado.getPrimerApellido();
        this.segundoApellido = registroDetallado.getSegundoApellido();
        this.primerNombre = registroDetallado.getPrimerNombre();
        this.segundoNombre = registroDetallado.getSegundoNombre();
        this.novIngreso = registroDetallado.getNovIngreso();
        this.novRetiro = registroDetallado.getNovRetiro();
        this.novVSP = registroDetallado.getNovVSP();
        this.novVST = registroDetallado.getNovVST();
        this.novSLN = registroDetallado.getNovSLN();
        this.novIGE = registroDetallado.getNovIGE();
        this.novLMA = registroDetallado.getNovLMA();
        this.novVACLR = registroDetallado.getNovVACLR();
        this.novSUS = registroDetallado.getNovSUS();
        this.diasIRL = registroDetallado.getDiasIRL();
        this.diasCotizados = registroDetallado.getDiasCotizados();
        this.salarioBasico = registroDetallado.getSalarioBasico();
        this.valorIBC = registroDetallado.getValorIBC();
        this.tarifa = registroDetallado.getTarifa();
        this.aporteObligatorio = registroDetallado.getAporteObligatorio();
        this.correcciones = registroDetallado.getCorrecciones();
        this.salarioIntegral = registroDetallado.getSalarioIntegral();
        if (registroDetallado.getFechaIngreso() != null)
            this.fechaIngreso = registroDetallado.getFechaIngreso().getTime();
        if (registroDetallado.getFechaRetiro() != null)
            this.fechaRetiro = registroDetallado.getFechaRetiro().getTime();
        if (registroDetallado.getFechaInicioVSP() != null)
            this.fechaInicioVSP = registroDetallado.getFechaInicioVSP().getTime();
        if (registroDetallado.getFechaInicioSLN() != null)
            this.fechaInicioSLN = registroDetallado.getFechaInicioSLN().getTime();
        if (registroDetallado.getFechaFinSLN() != null)
            this.fechaFinSLN = registroDetallado.getFechaFinSLN().getTime();
        if (registroDetallado.getFechaInicioIGE() != null)
            this.fechaInicioIGE = registroDetallado.getFechaInicioIGE().getTime();
        if (registroDetallado.getFechaFinIGE() != null)
            this.fechaFinIGE = registroDetallado.getFechaFinIGE().getTime();
        if (registroDetallado.getFechaInicioLMA() != null)
            this.fechaInicioLMA = registroDetallado.getFechaInicioLMA().getTime();
        if (registroDetallado.getFechaFinLMA() != null)
            this.fechaFinLMA = registroDetallado.getFechaFinLMA().getTime();
        if (registroDetallado.getFechaInicioVACLR() != null)
            this.fechaInicioVACLR = registroDetallado.getFechaInicioVACLR().getTime();
        if (registroDetallado.getFechaFinVACLR() != null)
            this.fechaFinVACLR = registroDetallado.getFechaFinVACLR().getTime();
        if (registroDetallado.getFechaInicioVCT() != null)
            this.fechaInicioVCT = registroDetallado.getFechaInicioVCT().getTime();
        if (registroDetallado.getFechaFinVCT() != null)
            this.fechaFinVCT = registroDetallado.getFechaFinVCT().getTime();
        if (registroDetallado.getFechaInicioIRL() != null)
            this.fechaInicioIRL = registroDetallado.getFechaInicioIRL().getTime();
        if (registroDetallado.getFechaFinIRL() != null)
            this.fechaFinIRL = registroDetallado.getFechaFinIRL().getTime();
        if (registroDetallado.getFechaInicioSuspension() != null)
            this.fechaInicioSuspension = registroDetallado.getFechaInicioSuspension().getTime();
        if (registroDetallado.getFechaFinSuspension() != null)
            this.fechaFinSuspension = registroDetallado.getFechaFinSuspension().getTime();
        this.horasLaboradas = registroDetallado.getHorasLaboradas();
        this.registroControl = registroDetallado.getRegistroControl();
        this.outEstadoRegistroAporte = registroDetallado.getOutEstadoRegistroAporte();
        this.outAnalisisIntegral = registroDetallado.getOutAnalisisIntegral();
        if (registroDetallado.getOutFechaProcesamientoValidRegAporte() != null)
            this.outFechaProcesamientoValidRegAporte = registroDetallado.getOutFechaProcesamientoValidRegAporte().getTime();
        this.outEstadoValidacionV0 = registroDetallado.getOutEstadoValidacionV0();
        this.outEstadoValidacionV1 = registroDetallado.getOutEstadoValidacionV1();
        this.outEstadoValidacionV2 = registroDetallado.getOutEstadoValidacionV2();
        this.outEstadoValidacionV3 = registroDetallado.getOutEstadoValidacionV3();
        this.outClaseTrabajador = registroDetallado.getOutClaseTrabajador();
        this.outPorcentajePagoAportes = registroDetallado.getOutPorcentajePagoAportes();
        this.outEstadoSolicitante = registroDetallado.getOutEstadoSolicitante();
        this.outEsTrabajadorReintegrable = registroDetallado.getOutEsTrabajadorReintegrable();
        if (registroDetallado.getOutFechaIngresoCotizante() != null)
            this.outFechaIngresoCotizante = registroDetallado.getOutFechaIngresoCotizante().getTime();
        if (registroDetallado.getOutFechaRetiroCotizante() != null)
            this.outFechaRetiroCotizante = registroDetallado.getOutFechaRetiroCotizante().getTime();
        if (registroDetallado.getOutFechaUltimaNovedad() != null)
            this.outFechaUltimaNovedad = registroDetallado.getOutFechaUltimaNovedad().getTime();
        this.usuarioAprobadorAporte = registroDetallado.getUsuarioAprobadorAporte();
        this.numeroOperacionAprobacion = registroDetallado.getNumeroOperacionAprobacion();
        this.estadoEvaluacion = registroDetallado.getEstadoEvaluacion();
        this.estadoValidacionCorreccion = registroDetallado.getEstadoRegistroCorreccion();
        this.outCodSucursal = registroDetallado.getOutCodSucursal();
        this.outNomSucursal = registroDetallado.getOutNomSucursal();
        this.outMarcaValidacionRegistroAporte = registroDetallado.getOutMarcaValidacionRegistroAporte();
        this.outRegistrado = registroDetallado.getOutRegistrado();
        this.outRegistradoAporte = registroDetallado.getOutRegistradoAporte();
        this.outRegistradoNovedad = registroDetallado.getOutRegistradoNovedad();
        this.outTipoAfiliado = registroDetallado.getOutTipoAfiliado();
        this.outValorMoraCotizante = registroDetallado.getOutValorMoraCotizante();
        this.outAporteObligatorioMod = registroDetallado.getOutAporteObligatorioMod();
        this.outDiasCotizadosMod = registroDetallado.getOutDiasCotizadosMod();
        this.outValorIBCMod = registroDetallado.getOutValorIBCMod();
        this.outValorMoraCotizanteMod = registroDetallado.getOutValorMoraCotizanteMod();
        this.outIdRegDetOriginal = registroDetallado.getOutIdRegDetOriginal();
        this.outEstadoRegistroRelacionAporte = registroDetallado.getOutEstadoRegistroRelacionAporte();
        this.outEstadoEvaluacionAporte = registroDetallado.getOutEstadoEvaluacionAporte();
        this.outGrupoFamiliarReintegrable = registroDetallado.getOutGrupoFamiliarReintegrable();
        this.idRegistro2pila = registroDetallado.getIdRegistro2pila();
        this.outMarcaValidacionRegistroAporte = registroDetallado.getOutMarcaValidacionRegistroAporte();
        this.outMotivoFiscalizacionInd = registroDetallado.getOutMotivoFiscalizacionInd();
        this.outRegistroActual = registroDetallado.getOutRegistroActual() == null ? Boolean.FALSE
                : registroDetallado.getOutRegistroActual();
        this.outRegInicial = registroDetallado.getOutRegInicial();
        
        this.grupoAC = registroDetallado.getGrupoAC();
        this.outTarifaMod = registroDetallado.getOutTarifaMod();
        
        this.usuarioAccion = registroDetallado.getUsuarioAccion();
        if (registroDetallado.getFechaAccion() != null)
            this.fechaAccion = registroDetallado.getFechaAccion();
    }

    /**
     * Método encargado de copiar un DTO a una Entidad.
     * @param registroDetallado
     *        previamente consultado.
     * @return registroDetallado registro detallado modificada con los datos del DTO.
     */
    public RegistroDetallado copyDTOToEntiy(RegistroDetallado registroDetallado) {
        return registroDetallado;

    }

    /**
     * Método que retorna el valor de id.
     * @return valor de id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Método encargado de modificar el valor de id.
     * @param valor
     *        para modificar id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Método que retorna el valor de registroGeneral.
     * @return valor de registroGeneral.
     */
    public Long getRegistroGeneral() {
        return registroGeneral;
    }

    /**
     * Método encargado de modificar el valor de registroGeneral.
     * @param valor
     *        para modificar registroGeneral.
     */
    public void setRegistroGeneral(Long registroGeneral) {
        this.registroGeneral = registroGeneral;
    }

    /**
     * Método que retorna el valor de tipoIdentificacionCotizante.
     * @return valor de tipoIdentificacionCotizante.
     */
    public TipoIdentificacionEnum getTipoIdentificacionCotizante() {
        return tipoIdentificacionCotizante;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacionCotizante.
     * @param valor
     *        para modificar tipoIdentificacionCotizante.
     */
    public void setTipoIdentificacionCotizante(TipoIdentificacionEnum tipoIdentificacionCotizante) {
        this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
    }

    /**
     * Método que retorna el valor de numeroIdentificacionCotizante.
     * @return valor de numeroIdentificacionCotizante.
     */
    public String getNumeroIdentificacionCotizante() {
        return numeroIdentificacionCotizante;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacionCotizante.
     * @param valor
     *        para modificar numeroIdentificacionCotizante.
     */
    public void setNumeroIdentificacionCotizante(String numeroIdentificacionCotizante) {
        this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
    }

    /**
     * Método que retorna el valor de tipoCotizante.
     * @return valor de tipoCotizante.
     */
    public Short getTipoCotizante() {
        return tipoCotizante;
    }

    /**
     * Método encargado de modificar el valor de tipoCotizante.
     * @param valor
     *        para modificar tipoCotizante.
     */
    public void setTipoCotizante(Short tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }

    /**
     * Método que retorna el valor de codDepartamento.
     * @return valor de codDepartamento.
     */
    public String getCodDepartamento() {
        return codDepartamento;
    }

    /**
     * Método encargado de modificar el valor de codDepartamento.
     * @param valor
     *        para modificar codDepartamento.
     */
    public void setCodDepartamento(String codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    /**
     * Método que retorna el valor de codMunicipio.
     * @return valor de codMunicipio.
     */
    public String getCodMunicipio() {
        return codMunicipio;
    }

    /**
     * Método encargado de modificar el valor de codMunicipio.
     * @param valor
     *        para modificar codMunicipio.
     */
    public void setCodMunicipio(String codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    /**
     * Método que retorna el valor de primerApellido.
     * @return valor de primerApellido.
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * Método encargado de modificar el valor de primerApellido.
     * @param valor
     *        para modificar primerApellido.
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * Método que retorna el valor de segundoApellido.
     * @return valor de segundoApellido.
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * Método encargado de modificar el valor de segundoApellido.
     * @param valor
     *        para modificar segundoApellido.
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * Método que retorna el valor de primerNombre.
     * @return valor de primerNombre.
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * Método encargado de modificar el valor de primerNombre.
     * @param valor
     *        para modificar primerNombre.
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * Método que retorna el valor de segundoNombre.
     * @return valor de segundoNombre.
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * Método encargado de modificar el valor de segundoNombre.
     * @param valor
     *        para modificar segundoNombre.
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * Método que retorna el valor de novIngreso.
     * @return valor de novIngreso.
     */
    public String getNovIngreso() {
        return novIngreso;
    }

    /**
     * Método encargado de modificar el valor de novIngreso.
     * @param valor
     *        para modificar novIngreso.
     */
    public void setNovIngreso(String novIngreso) {
        this.novIngreso = novIngreso;
    }

    /**
     * Método que retorna el valor de novRetiro.
     * @return valor de novRetiro.
     */
    public String getNovRetiro() {
        return novRetiro;
    }

    /**
     * Método encargado de modificar el valor de novRetiro.
     * @param valor
     *        para modificar novRetiro.
     */
    public void setNovRetiro(String novRetiro) {
        this.novRetiro = novRetiro;
    }

    /**
     * Método que retorna el valor de novVSP.
     * @return valor de novVSP.
     */
    public String getNovVSP() {
        return novVSP;
    }

    /**
     * Método encargado de modificar el valor de novVSP.
     * @param valor
     *        para modificar novVSP.
     */
    public void setNovVSP(String novVSP) {
        this.novVSP = novVSP;
    }

    /**
     * Método que retorna el valor de novVST.
     * @return valor de novVST.
     */
    public String getNovVST() {
        return novVST;
    }

    /**
     * Método encargado de modificar el valor de novVST.
     * @param valor
     *        para modificar novVST.
     */
    public void setNovVST(String novVST) {
        this.novVST = novVST;
    }

    /**
     * Método que retorna el valor de novSLN.
     * @return valor de novSLN.
     */
    public String getNovSLN() {
        return novSLN;
    }

    /**
     * Método encargado de modificar el valor de novSLN.
     * @param valor
     *        para modificar novSLN.
     */
    public void setNovSLN(String novSLN) {
        this.novSLN = novSLN;
    }

    /**
     * Método que retorna el valor de novIGE.
     * @return valor de novIGE.
     */
    public String getNovIGE() {
        return novIGE;
    }

    /**
     * Método encargado de modificar el valor de novIGE.
     * @param valor
     *        para modificar novIGE.
     */
    public void setNovIGE(String novIGE) {
        this.novIGE = novIGE;
    }

    /**
     * Método que retorna el valor de novLMA.
     * @return valor de novLMA.
     */
    public String getNovLMA() {
        return novLMA;
    }

    /**
     * Método encargado de modificar el valor de novLMA.
     * @param valor
     *        para modificar novLMA.
     */
    public void setNovLMA(String novLMA) {
        this.novLMA = novLMA;
    }

    /**
     * Método que retorna el valor de novVACLR.
     * @return valor de novVACLR.
     */
    public String getNovVACLR() {
        return novVACLR;
    }

    /**
     * Método encargado de modificar el valor de novVACLR.
     * @param valor
     *        para modificar novVACLR.
     */
    public void setNovVACLR(String novVACLR) {
        this.novVACLR = novVACLR;
    }

    /**
     * Método que retorna el valor de novSUS.
     * @return valor de novSUS.
     */
    public String getNovSUS() {
        return novSUS;
    }

    /**
     * Método encargado de modificar el valor de novSUS.
     * @param valor
     *        para modificar novSUS.
     */
    public void setNovSUS(String novSUS) {
        this.novSUS = novSUS;
    }

    /**
     * Método que retorna el valor de diasIRL.
     * @return valor de diasIRL.
     */
    public String getDiasIRL() {
        return diasIRL;
    }

    /**
     * Método encargado de modificar el valor de diasIRL.
     * @param valor
     *        para modificar diasIRL.
     */
    public void setDiasIRL(String diasIRL) {
        this.diasIRL = diasIRL;
    }

    /**
     * Método que retorna el valor de diasCotizados.
     * @return valor de diasCotizados.
     */
    public Short getDiasCotizados() {
        return diasCotizados;
    }

    /**
     * Método encargado de modificar el valor de diasCotizados.
     * @param valor
     *        para modificar diasCotizados.
     */
    public void setDiasCotizados(Short diasCotizados) {
        this.diasCotizados = diasCotizados;
    }


    /**
     * Método que retorna el valor de tarifa.
     * @return valor de tarifa.
     */
    public BigDecimal getTarifa() {
        return tarifa;
    }

    /**
     * Método encargado de modificar el valor de tarifa.
     * @param valor
     *        para modificar tarifa.
     */
    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }


    /**
     * Método que retorna el valor de correcciones.
     * @return valor de correcciones.
     */
    public String getCorrecciones() {
        return correcciones;
    }

    /**
     * Método encargado de modificar el valor de correcciones.
     * @param valor
     *        para modificar correcciones.
     */
    public void setCorrecciones(String correcciones) {
        this.correcciones = correcciones;
    }

    /**
     * Método que retorna el valor de salarioIntegral.
     * @return valor de salarioIntegral.
     */
    public String getSalarioIntegral() {
        return salarioIntegral;
    }

    /**
     * Método encargado de modificar el valor de salarioIntegral.
     * @param valor
     *        para modificar salarioIntegral.
     */
    public void setSalarioIntegral(String salarioIntegral) {
        this.salarioIntegral = salarioIntegral;
    }

    /**
     * Método que retorna el valor de fechaIngreso.
     * @return valor de fechaIngreso.
     */
    public Long getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * Método encargado de modificar el valor de fechaIngreso.
     * @param valor
     *        para modificar fechaIngreso.
     */
    public void setFechaIngreso(Long fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * Método que retorna el valor de fechaRetiro.
     * @return valor de fechaRetiro.
     */
    public Long getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * Método encargado de modificar el valor de fechaRetiro.
     * @param valor
     *        para modificar fechaRetiro.
     */
    public void setFechaRetiro(Long fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * Método que retorna el valor de fechaInicioVSP.
     * @return valor de fechaInicioVSP.
     */
    public Long getFechaInicioVSP() {
        return fechaInicioVSP;
    }

    /**
     * Método encargado de modificar el valor de fechaInicioVSP.
     * @param valor
     *        para modificar fechaInicioVSP.
     */
    public void setFechaInicioVSP(Long fechaInicioVSP) {
        this.fechaInicioVSP = fechaInicioVSP;
    }

    /**
     * Método que retorna el valor de fechaInicioSLN.
     * @return valor de fechaInicioSLN.
     */
    public Long getFechaInicioSLN() {
        return fechaInicioSLN;
    }

    /**
     * Método encargado de modificar el valor de fechaInicioSLN.
     * @param valor
     *        para modificar fechaInicioSLN.
     */
    public void setFechaInicioSLN(Long fechaInicioSLN) {
        this.fechaInicioSLN = fechaInicioSLN;
    }

    /**
     * Método que retorna el valor de fechaFinSLN.
     * @return valor de fechaFinSLN.
     */
    public Long getFechaFinSLN() {
        return fechaFinSLN;
    }

    /**
     * Método encargado de modificar el valor de fechaFinSLN.
     * @param valor
     *        para modificar fechaFinSLN.
     */
    public void setFechaFinSLN(Long fechaFinSLN) {
        this.fechaFinSLN = fechaFinSLN;
    }

    /**
     * Método que retorna el valor de fechaInicioIGE.
     * @return valor de fechaInicioIGE.
     */
    public Long getFechaInicioIGE() {
        return fechaInicioIGE;
    }

    /**
     * Método encargado de modificar el valor de fechaInicioIGE.
     * @param valor
     *        para modificar fechaInicioIGE.
     */
    public void setFechaInicioIGE(Long fechaInicioIGE) {
        this.fechaInicioIGE = fechaInicioIGE;
    }

    /**
     * Método que retorna el valor de fechaFinIGE.
     * @return valor de fechaFinIGE.
     */
    public Long getFechaFinIGE() {
        return fechaFinIGE;
    }

    /**
     * Método encargado de modificar el valor de fechaFinIGE.
     * @param valor
     *        para modificar fechaFinIGE.
     */
    public void setFechaFinIGE(Long fechaFinIGE) {
        this.fechaFinIGE = fechaFinIGE;
    }

    /**
     * Método que retorna el valor de fechaInicioLMA.
     * @return valor de fechaInicioLMA.
     */
    public Long getFechaInicioLMA() {
        return fechaInicioLMA;
    }

    /**
     * Método encargado de modificar el valor de fechaInicioLMA.
     * @param valor
     *        para modificar fechaInicioLMA.
     */
    public void setFechaInicioLMA(Long fechaInicioLMA) {
        this.fechaInicioLMA = fechaInicioLMA;
    }

    /**
     * Método que retorna el valor de fechaFinLMA.
     * @return valor de fechaFinLMA.
     */
    public Long getFechaFinLMA() {
        return fechaFinLMA;
    }

    /**
     * Método encargado de modificar el valor de fechaFinLMA.
     * @param valor
     *        para modificar fechaFinLMA.
     */
    public void setFechaFinLMA(Long fechaFinLMA) {
        this.fechaFinLMA = fechaFinLMA;
    }

    /**
     * Método que retorna el valor de fechaInicioVACLR.
     * @return valor de fechaInicioVACLR.
     */
    public Long getFechaInicioVACLR() {
        return fechaInicioVACLR;
    }

    /**
     * Método encargado de modificar el valor de fechaInicioVACLR.
     * @param valor
     *        para modificar fechaInicioVACLR.
     */
    public void setFechaInicioVACLR(Long fechaInicioVACLR) {
        this.fechaInicioVACLR = fechaInicioVACLR;
    }

    /**
     * Método que retorna el valor de fechaFinVACLR.
     * @return valor de fechaFinVACLR.
     */
    public Long getFechaFinVACLR() {
        return fechaFinVACLR;
    }

    /**
     * Método encargado de modificar el valor de fechaFinVACLR.
     * @param valor
     *        para modificar fechaFinVACLR.
     */
    public void setFechaFinVACLR(Long fechaFinVACLR) {
        this.fechaFinVACLR = fechaFinVACLR;
    }

    /**
     * Método que retorna el valor de fechaInicioVCT.
     * @return valor de fechaInicioVCT.
     */
    public Long getFechaInicioVCT() {
        return fechaInicioVCT;
    }

    /**
     * Método encargado de modificar el valor de fechaInicioVCT.
     * @param valor
     *        para modificar fechaInicioVCT.
     */
    public void setFechaInicioVCT(Long fechaInicioVCT) {
        this.fechaInicioVCT = fechaInicioVCT;
    }

    /**
     * Método que retorna el valor de fechaFinVCT.
     * @return valor de fechaFinVCT.
     */
    public Long getFechaFinVCT() {
        return fechaFinVCT;
    }

    /**
     * Método encargado de modificar el valor de fechaFinVCT.
     * @param valor
     *        para modificar fechaFinVCT.
     */
    public void setFechaFinVCT(Long fechaFinVCT) {
        this.fechaFinVCT = fechaFinVCT;
    }

    /**
     * Método que retorna el valor de fechaInicioIRL.
     * @return valor de fechaInicioIRL.
     */
    public Long getFechaInicioIRL() {
        return fechaInicioIRL;
    }

    /**
     * Método encargado de modificar el valor de fechaInicioIRL.
     * @param valor
     *        para modificar fechaInicioIRL.
     */
    public void setFechaInicioIRL(Long fechaInicioIRL) {
        this.fechaInicioIRL = fechaInicioIRL;
    }

    /**
     * Método que retorna el valor de fechaFinIRL.
     * @return valor de fechaFinIRL.
     */
    public Long getFechaFinIRL() {
        return fechaFinIRL;
    }

    /**
     * Método encargado de modificar el valor de fechaFinIRL.
     * @param valor
     *        para modificar fechaFinIRL.
     */
    public void setFechaFinIRL(Long fechaFinIRL) {
        this.fechaFinIRL = fechaFinIRL;
    }

    /**
     * Método que retorna el valor de fechaInicioSuspension.
     * @return valor de fechaInicioSuspension.
     */
    public Long getFechaInicioSuspension() {
        return fechaInicioSuspension;
    }

    /**
     * Método encargado de modificar el valor de fechaInicioSuspension.
     * @param valor
     *        para modificar fechaInicioSuspension.
     */
    public void setFechaInicioSuspension(Long fechaInicioSuspension) {
        this.fechaInicioSuspension = fechaInicioSuspension;
    }

    /**
     * Método que retorna el valor de fechaFinSuspension.
     * @return valor de fechaFinSuspension.
     */
    public Long getFechaFinSuspension() {
        return fechaFinSuspension;
    }

    /**
     * Método encargado de modificar el valor de fechaFinSuspension.
     * @param valor
     *        para modificar fechaFinSuspension.
     */
    public void setFechaFinSuspension(Long fechaFinSuspension) {
        this.fechaFinSuspension = fechaFinSuspension;
    }

    /**
     * Método que retorna el valor de horasLaboradas.
     * @return valor de horasLaboradas.
     */
    public Short getHorasLaboradas() {
        return horasLaboradas;
    }

    /**
     * Método encargado de modificar el valor de horasLaboradas.
     * @param valor
     *        para modificar horasLaboradas.
     */
    public void setHorasLaboradas(Short horasLaboradas) {
        this.horasLaboradas = horasLaboradas;
    }

    /**
     * Método que retorna el valor de registroControl.
     * @return valor de registroControl.
     */
    public Long getRegistroControl() {
        return registroControl;
    }

    /**
     * Método encargado de modificar el valor de registroControl.
     * @param valor
     *        para modificar registroControl.
     */
    public void setRegistroControl(Long registroControl) {
        this.registroControl = registroControl;
    }

    /**
     * Método que retorna el valor de outMarcaValidacionRegistroAporte.
     * @return valor de outMarcaValidacionRegistroAporte.
     */
    public MarcaRegistroAporteArchivoEnum getOutMarcaValidacionRegistroAporte() {
        return outMarcaValidacionRegistroAporte;
    }

    /**
     * Método encargado de modificar el valor de outMarcaValidacionRegistroAporte.
     * @param valor
     *        para modificar outMarcaValidacionRegistroAporte.
     */
    public void setOutMarcaValidacionRegistroAporte(MarcaRegistroAporteArchivoEnum outMarcaValidacionRegistroAporte) {
        this.outMarcaValidacionRegistroAporte = outMarcaValidacionRegistroAporte;
    }

    /**
     * Método que retorna el valor de outEstadoRegistroAporte.
     * @return valor de outEstadoRegistroAporte.
     */
    public EstadoRegistroAportesArchivoEnum getOutEstadoRegistroAporte() {
        return outEstadoRegistroAporte;
    }

    /**
     * Método encargado de modificar el valor de outEstadoRegistroAporte.
     * @param valor
     *        para modificar outEstadoRegistroAporte.
     */
    public void setOutEstadoRegistroAporte(EstadoRegistroAportesArchivoEnum outEstadoRegistroAporte) {
        this.outEstadoRegistroAporte = outEstadoRegistroAporte;
    }

    /**
     * Método que retorna el valor de outAnalisisIntegral.
     * @return valor de outAnalisisIntegral.
     */
    public Boolean getOutAnalisisIntegral() {
        return outAnalisisIntegral;
    }

    /**
     * Método encargado de modificar el valor de outAnalisisIntegral.
     * @param valor
     *        para modificar outAnalisisIntegral.
     */
    public void setOutAnalisisIntegral(Boolean outAnalisisIntegral) {
        this.outAnalisisIntegral = outAnalisisIntegral;
    }

    /**
     * Método que retorna el valor de outFechaProcesamientoValidRegAporte.
     * @return valor de outFechaProcesamientoValidRegAporte.
     */
    public Long getOutFechaProcesamientoValidRegAporte() {
        return outFechaProcesamientoValidRegAporte;
    }

    /**
     * Método encargado de modificar el valor de outFechaProcesamientoValidRegAporte.
     * @param valor
     *        para modificar outFechaProcesamientoValidRegAporte.
     */
    public void setOutFechaProcesamientoValidRegAporte(Long outFechaProcesamientoValidRegAporte) {
        this.outFechaProcesamientoValidRegAporte = outFechaProcesamientoValidRegAporte;
    }

    /**
     * Método que retorna el valor de outEstadoValidacionV0.
     * @return valor de outEstadoValidacionV0.
     */
    public EstadoValidacionRegistroAporteEnum getOutEstadoValidacionV0() {
        return outEstadoValidacionV0;
    }

    /**
     * Método encargado de modificar el valor de outEstadoValidacionV0.
     * @param valor
     *        para modificar outEstadoValidacionV0.
     */
    public void setOutEstadoValidacionV0(EstadoValidacionRegistroAporteEnum outEstadoValidacionV0) {
        this.outEstadoValidacionV0 = outEstadoValidacionV0;
    }

    /**
     * Método que retorna el valor de outEstadoValidacionV1.
     * @return valor de outEstadoValidacionV1.
     */
    public EstadoValidacionRegistroAporteEnum getOutEstadoValidacionV1() {
        return outEstadoValidacionV1;
    }

    /**
     * Método encargado de modificar el valor de outEstadoValidacionV1.
     * @param valor
     *        para modificar outEstadoValidacionV1.
     */
    public void setOutEstadoValidacionV1(EstadoValidacionRegistroAporteEnum outEstadoValidacionV1) {
        this.outEstadoValidacionV1 = outEstadoValidacionV1;
    }

    /**
     * Método que retorna el valor de outEstadoValidacionV2.
     * @return valor de outEstadoValidacionV2.
     */
    public EstadoValidacionRegistroAporteEnum getOutEstadoValidacionV2() {
        return outEstadoValidacionV2;
    }

    /**
     * Método encargado de modificar el valor de outEstadoValidacionV2.
     * @param valor
     *        para modificar outEstadoValidacionV2.
     */
    public void setOutEstadoValidacionV2(EstadoValidacionRegistroAporteEnum outEstadoValidacionV2) {
        this.outEstadoValidacionV2 = outEstadoValidacionV2;
    }

    /**
     * Método que retorna el valor de outEstadoValidacionV3.
     * @return valor de outEstadoValidacionV3.
     */
    public EstadoValidacionRegistroAporteEnum getOutEstadoValidacionV3() {
        return outEstadoValidacionV3;
    }

    /**
     * Método encargado de modificar el valor de outEstadoValidacionV3.
     * @param valor
     *        para modificar outEstadoValidacionV3.
     */
    public void setOutEstadoValidacionV3(EstadoValidacionRegistroAporteEnum outEstadoValidacionV3) {
        this.outEstadoValidacionV3 = outEstadoValidacionV3;
    }

    /**
     * Método que retorna el valor de outClaseTrabajador.
     * @return valor de outClaseTrabajador.
     */
    public String getOutClaseTrabajador() {
        return outClaseTrabajador;
    }

    /**
     * Método encargado de modificar el valor de outClaseTrabajador.
     * @param valor
     *        para modificar outClaseTrabajador.
     */
    public void setOutClaseTrabajador(String outClaseTrabajador) {
        this.outClaseTrabajador = outClaseTrabajador;
    }

    /**
     * Método que retorna el valor de outPorcentajePagoAportes.
     * @return valor de outPorcentajePagoAportes.
     */
    public BigDecimal getOutPorcentajePagoAportes() {
        return outPorcentajePagoAportes;
    }

    /**
     * Método encargado de modificar el valor de outPorcentajePagoAportes.
     * @param valor
     *        para modificar outPorcentajePagoAportes.
     */
    public void setOutPorcentajePagoAportes(BigDecimal outPorcentajePagoAportes) {
        this.outPorcentajePagoAportes = outPorcentajePagoAportes;
    }

    /**
     * Método que retorna el valor de outEstadoSolicitante.
     * @return valor de outEstadoSolicitante.
     */
    public String getOutEstadoSolicitante() {
        return outEstadoSolicitante;
    }

    /**
     * Método encargado de modificar el valor de outEstadoSolicitante.
     * @param valor
     *        para modificar outEstadoSolicitante.
     */
    public void setOutEstadoSolicitante(String outEstadoSolicitante) {
        this.outEstadoSolicitante = outEstadoSolicitante;
    }

    /**
     * Método que retorna el valor de outEsTrabajadorReintegrable.
     * @return valor de outEsTrabajadorReintegrable.
     */
    public Boolean getOutEsTrabajadorReintegrable() {
        return outEsTrabajadorReintegrable;
    }

    /**
     * Método encargado de modificar el valor de outEsTrabajadorReintegrable.
     * @param valor
     *        para modificar outEsTrabajadorReintegrable.
     */
    public void setOutEsTrabajadorReintegrable(Boolean outEsTrabajadorReintegrable) {
        this.outEsTrabajadorReintegrable = outEsTrabajadorReintegrable;
    }

    /**
     * Método que retorna el valor de outFechaIngresoCotizante.
     * @return valor de outFechaIngresoCotizante.
     */
    public Long getOutFechaIngresoCotizante() {
        return outFechaIngresoCotizante;
    }

    /**
     * Método encargado de modificar el valor de outFechaIngresoCotizante.
     * @param valor
     *        para modificar outFechaIngresoCotizante.
     */
    public void setOutFechaIngresoCotizante(Long outFechaIngresoCotizante) {
        this.outFechaIngresoCotizante = outFechaIngresoCotizante;
    }

    /**
     * @return the outFechaRetiroCotizante
     */
    public Long getOutFechaRetiroCotizante() {
        return outFechaRetiroCotizante;
    }

    /**
     * @param outFechaRetiroCotizante the outFechaRetiroCotizante to set
     */
    public void setOutFechaRetiroCotizante(Long outFechaRetiroCotizante) {
        this.outFechaRetiroCotizante = outFechaRetiroCotizante;
    }

    /**
     * Método que retorna el valor de outFechaUltimaNovedad.
     * @return valor de outFechaUltimaNovedad.
     */
    public Long getOutFechaUltimaNovedad() {
        return outFechaUltimaNovedad;
    }

    /**
     * Método encargado de modificar el valor de outFechaUltimaNovedad.
     * @param valor
     *        para modificar outFechaUltimaNovedad.
     */
    public void setOutFechaUltimaNovedad(Long outFechaUltimaNovedad) {
        this.outFechaUltimaNovedad = outFechaUltimaNovedad;
    }

    /**
     * @return the estadoValidacionCorreccion
     */
    public EstadoValidacionRegistroCorreccionEnum getEstadoValidacionCorreccion() {
        return estadoValidacionCorreccion;
    }

    /**
     * @param estadoValidacionCorreccion
     *        the estadoValidacionCorreccion to set
     */
    public void setEstadoValidacionCorreccion(EstadoValidacionRegistroCorreccionEnum estadoValidacionCorreccion) {
        this.estadoValidacionCorreccion = estadoValidacionCorreccion;
    }

    /**
     * @return the usuarioAprobadorAporte
     */
    public String getUsuarioAprobadorAporte() {
        return usuarioAprobadorAporte;
    }

    /**
     * @param usuarioAprobadorAporte
     *        the usuarioAprobadorAporte to set
     */
    public void setUsuarioAprobadorAporte(String usuarioAprobadorAporte) {
        this.usuarioAprobadorAporte = usuarioAprobadorAporte;
    }

    /**
     * @return the numeroOperacionAprobacion
     */
    public String getNumeroOperacionAprobacion() {
        return numeroOperacionAprobacion;
    }

    /**
     * @param numeroOperacionAprobacion
     *        the numeroOperacionAprobacion to set
     */
    public void setNumeroOperacionAprobacion(String numeroOperacionAprobacion) {
        this.numeroOperacionAprobacion = numeroOperacionAprobacion;
    }

    /**
     * @return the estadoEvaluacion
     */
    public EstadoAporteEnum getEstadoEvaluacion() {
        return estadoEvaluacion;
    }

    /**
     * @param estadoEvaluacion
     *        the estadoEvaluacion to set
     */
    public void setEstadoEvaluacion(EstadoAporteEnum estadoEvaluacion) {
        this.estadoEvaluacion = estadoEvaluacion;
    }

    /**
     * Función para retornar el nombre del cotizante como una cadena completa
     * @return <b>String</b>
     *         Nombre completo
     */
    public String componerNombreCotizante() {
        StringBuilder nombreCompleto = new StringBuilder(this.getPrimerNombre());

        if (this.getSegundoNombre() != null && !this.getSegundoNombre().isEmpty()) {
            nombreCompleto.append(" ");
            nombreCompleto.append(this.getSegundoNombre());
        }

        if (this.getPrimerApellido() != null && !this.getPrimerApellido().isEmpty()) {
            nombreCompleto.append(" ");
            nombreCompleto.append(this.getPrimerApellido());
        }

        if (this.getSegundoApellido() != null && !this.getSegundoApellido().isEmpty()) {
            nombreCompleto.append(" ");
            nombreCompleto.append(this.getSegundoApellido());
        }

        return nombreCompleto.toString();
    }

    /**
     * @return the salarioBasico
     */
    public BigDecimal getSalarioBasico() {
        return salarioBasico;
    }

    /**
     * @param salarioBasico the salarioBasico to set
     */
    public void setSalarioBasico(BigDecimal salarioBasico) {
        this.salarioBasico = salarioBasico;
    }

    /**
     * @return the valorIBC
     */
    public BigDecimal getValorIBC() {
        return valorIBC;
    }

    /**
     * @param valorIBC the valorIBC to set
     */
    public void setValorIBC(BigDecimal valorIBC) {
        this.valorIBC = valorIBC;
    }

    /**
     * @return the aporteObligatorio
     */
    public BigDecimal getAporteObligatorio() {
        return aporteObligatorio;
    }

    /**
     * @param aporteObligatorio the aporteObligatorio to set
     */
    public void setAporteObligatorio(BigDecimal aporteObligatorio) {
        this.aporteObligatorio = aporteObligatorio;
    }

    /**
     * @return the outCodSucursal
     */
    public String getOutCodSucursal() {
        return outCodSucursal;
    }

    /**
     * @param outCodSucursal the outCodSucursal to set
     */
    public void setOutCodSucursal(String outCodSucursal) {
        this.outCodSucursal = outCodSucursal;
    }

    /**
     * @return the outNomSucursal
     */
    public String getOutNomSucursal() {
        return outNomSucursal;
    }

    /**
     * @param outNomSucursal the outNomSucursal to set
     */
    public void setOutNomSucursal(String outNomSucursal) {
        this.outNomSucursal = outNomSucursal;
    }

    /**
     * @return the outRegistrado
     */
    public Boolean getOutRegistrado() {
        return outRegistrado;
    }

    /**
     * @param outRegistrado the outRegistrado to set
     */
    public void setOutRegistrado(Boolean outRegistrado) {
        this.outRegistrado = outRegistrado;
    }

    /**
     * @return the idOriginal
     */
    public Long getOutIdRegDetOriginal() {
        return outIdRegDetOriginal;
    }

    /**
     * @param outIdRegDetOriginal the outIdRegDetOriginal to set
     */
    public void setOutIdRegDetOriginal(Long outIdRegDetOriginal) {
        this.outIdRegDetOriginal = outIdRegDetOriginal;
    }

    /**
     * @return the subTipoCotizante
     */
    public SubTipoCotizanteEnum getSubTipoCotizante() {
        return subTipoCotizante;
    }

    /**
     * @param subTipoCotizante the subTipoCotizante to set
     */
    public void setSubTipoCotizante(SubTipoCotizanteEnum subTipoCotizante) {
        this.subTipoCotizante = subTipoCotizante;
    }

    /**
     * @return the outValorMoraCotizante
     */
    public BigDecimal getOutValorMoraCotizante() {
        return outValorMoraCotizante;
    }

    /**
     * @param outValorMoraCotizante the outValorMoraCotizante to set
     */
    public void setOutValorMoraCotizante(BigDecimal outValorMoraCotizante) {
        this.outValorMoraCotizante = outValorMoraCotizante;
    }

    /**
     * @return the outTipoAfiliado
     */
    public TipoAfiliadoEnum getOutTipoAfiliado() {
        return outTipoAfiliado;
    }

    /**
     * @param outTipoAfiliado the outTipoAfiliado to set
     */
    public void setOutTipoAfiliado(TipoAfiliadoEnum outTipoAfiliado) {
        this.outTipoAfiliado = outTipoAfiliado;
    }

    /**
     * @return the outAporteObligatorioMod
     */
    public BigDecimal getOutAporteObligatorioMod() {
        return outAporteObligatorioMod;
    }

    /**
     * @param outAporteObligatorioMod the outAporteObligatorioMod to set
     */
    public void setOutAporteObligatorioMod(BigDecimal outAporteObligatorioMod) {
        this.outAporteObligatorioMod = outAporteObligatorioMod;
    }

    /**
     * @return the outDiasCotizadosMod
     */
    public Short getOutDiasCotizadosMod() {
        return outDiasCotizadosMod;
    }

    /**
     * @param outDiasCotizadosMod the outDiasCotizadosMod to set
     */
    public void setOutDiasCotizadosMod(Short outDiasCotizadosMod) {
        this.outDiasCotizadosMod = outDiasCotizadosMod;
    }

    /**
     * @return the outRegistradoAporte
     */
    public Boolean getOutRegistradoAporte() {
        return outRegistradoAporte;
    }

    /**
     * @param outRegistradoAporte the outRegistradoAporte to set
     */
    public void setOutRegistradoAporte(Boolean outRegistradoAporte) {
        this.outRegistradoAporte = outRegistradoAporte;
    }

    /**
     * @return the outRegistradoNovedad
     */
    public Boolean getOutRegistradoNovedad() {
        return outRegistradoNovedad;
    }

    /**
     * @param outRegistradoNovedad the outRegistradoNovedad to set
     */
    public void setOutRegistradoNovedad(Boolean outRegistradoNovedad) {
        this.outRegistradoNovedad = outRegistradoNovedad;
    }

    /**
     * @return the outEstadoRegistroRelacionAporte
     */
    public EstadoRegistroAporteEnum getOutEstadoRegistroRelacionAporte() {
        return outEstadoRegistroRelacionAporte;
    }

    /**
     * @param outEstadoRegistroRelacionAporte the outEstadoRegistroRelacionAporte to set
     */
    public void setOutEstadoRegistroRelacionAporte(EstadoRegistroAporteEnum outEstadoRegistroRelacionAporte) {
        this.outEstadoRegistroRelacionAporte = outEstadoRegistroRelacionAporte;
    }

    /**
     * @return the outEstadoEvaluacionAporte
     */
    public EstadoAporteEnum getOutEstadoEvaluacionAporte() {
        return outEstadoEvaluacionAporte;
    }

    /**
     * @param outEstadoEvaluacionAporte the outEstadoEvaluacionAporte to set
     */
    public void setOutEstadoEvaluacionAporte(EstadoAporteEnum outEstadoEvaluacionAporte) {
        this.outEstadoEvaluacionAporte = outEstadoEvaluacionAporte;
    }

    /**
     * @return the outValorIBCMod
     */
    public BigDecimal getOutValorIBCMod() {
        return outValorIBCMod;
    }

    /**
     * @param outValorIBCMod the outValorIBCMod to set
     */
    public void setOutValorIBCMod(BigDecimal outValorIBCMod) {
        this.outValorIBCMod = outValorIBCMod;
    }

    /**
     * @return the outValorMoraCotizanteMod
     */
    public BigDecimal getOutValorMoraCotizanteMod() {
        return outValorMoraCotizanteMod;
    }

    /**
     * @param outValorMoraCotizanteMod the outValorMoraCotizanteMod to set
     */
    public void setOutValorMoraCotizanteMod(BigDecimal outValorMoraCotizanteMod) {
        this.outValorMoraCotizanteMod = outValorMoraCotizanteMod;
    }

    /**
     * @return the fechaInicioVST
     */
    public Long getFechaInicioVST() {
        return fechaInicioVST;
    }

    /**
     * @param fechaInicioVST the fechaInicioVST to set
     */
    public void setFechaInicioVST(Long fechaInicioVST) {
        this.fechaInicioVST = fechaInicioVST;
    }

    /**
     * @return the fechaFinVST
     */
    public Long getFechaFinVST() {
        return fechaFinVST;
    }

    /**
     * @param fechaFinVST the fechaFinVST to set
     */
    public void setFechaFinVST(Long fechaFinVST) {
        this.fechaFinVST = fechaFinVST;
    }

    /**
     * @return the outGrupoFamiliarReintegrable
     */
    public Boolean getOutGrupoFamiliarReintegrable() {
        return outGrupoFamiliarReintegrable;
    }

    /**
     * @param outGrupoFamiliarReintegrable the outGrupoFamiliarReintegrable to set
     */
    public void setOutGrupoFamiliarReintegrable(Boolean outGrupoFamiliarReintegrable) {
        this.outGrupoFamiliarReintegrable = outGrupoFamiliarReintegrable;
    }

    /**
     * @return the idRegistro2pila
     */
    public Long getIdRegistro2pila() {
        return idRegistro2pila;
    }

    /**
     * @param idRegistro2pila the idRegistro2pila to set
     */
    public void setIdRegistro2pila(Long idRegistro2pila) {
        this.idRegistro2pila = idRegistro2pila;
    }

    /**
     * @return the outEnviadoAFiscalizacionInd
     */
    public Boolean getOutEnviadoAFiscalizacionInd() {
        return outEnviadoAFiscalizacionInd;
    }

    /**
     * @param outEnviadoAFiscalizacionInd the outEnviadoAFiscalizacionInd to set
     */
    public void setOutEnviadoAFiscalizacionInd(Boolean outEnviadoAFiscalizacionInd) {
        this.outEnviadoAFiscalizacionInd = outEnviadoAFiscalizacionInd;
    }

    /**
     * @return the outMotivoFiscalizacionInd
     */
    public MotivoFiscalizacionAportanteEnum getOutMotivoFiscalizacionInd() {
        return outMotivoFiscalizacionInd;
    }

    /**
     * @param outMotivoFiscalizacionInd the outMotivoFiscalizacionInd to set
     */
    public void setOutMotivoFiscalizacionInd(MotivoFiscalizacionAportanteEnum outMotivoFiscalizacionInd) {
        this.outMotivoFiscalizacionInd = outMotivoFiscalizacionInd;
    }

    /**
     * @return the outRegistroActual
     */
    public Boolean getOutRegistroActual() {
        return outRegistroActual;
    }

    /**
     * @param outRegistroActual the outRegistroActual to set
     */
    public void setOutRegistroActual(Boolean outRegistroActual) {
        this.outRegistroActual = outRegistroActual;
    }

    /**
     * @return the outRegInicial
     */
    public Long getOutRegInicial() {
        return outRegInicial;
    }

    /**
     * @param outRegInicial the outRegInicial to set
     */
    public void setOutRegInicial(Long outRegInicial) {
        this.outRegInicial = outRegInicial;
    }

	/**
	 * @return the grupoAC
	 */
	public Integer getGrupoAC() {
		return grupoAC;
	}

	/**
	 * @param grupoAC the grupoAC to set
	 */
	public void setGrupoAC(Integer grupoAC) {
		this.grupoAC = grupoAC;
	}

    /**
     * @return the outTarifaMod
     */
    public BigDecimal getOutTarifaMod() {
        return outTarifaMod;
    }

    /**
     * @param outTarifaMod the outTarifaMod to set
     */
    public void setOutTarifaMod(BigDecimal outTarifaMod) {
        this.outTarifaMod = outTarifaMod;
    }

	/**
	 * @return the usuarioAccion
	 */
	public String getUsuarioAccion() {
		return usuarioAccion;
	}

	/**
	 * @param usuarioAccion the usuarioAccion to set
	 */
	public void setUsuarioAccion(String usuarioAccion) {
		this.usuarioAccion = usuarioAccion;
	}

	/**
	 * @return the fechaAccion
	 */
	public Date getFechaAccion() {
		return fechaAccion;
	}

	/**
	 * @param fechaAccion the fechaAccion to set
	 */
	public void setFechaAccion(Date fechaAccion) {
		this.fechaAccion = fechaAccion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistroDetalladoModeloDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (registroGeneral != null) {
			builder.append("registroGeneral=");
			builder.append(registroGeneral);
			builder.append(", ");
		}
		if (tipoIdentificacionCotizante != null) {
			builder.append("tipoIdentificacionCotizante=");
			builder.append(tipoIdentificacionCotizante);
			builder.append(", ");
		}
		if (numeroIdentificacionCotizante != null) {
			builder.append("numeroIdentificacionCotizante=");
			builder.append(numeroIdentificacionCotizante);
			builder.append(", ");
		}
		if (tipoCotizante != null) {
			builder.append("tipoCotizante=");
			builder.append(tipoCotizante);
			builder.append(", ");
		}
		if (codDepartamento != null) {
			builder.append("codDepartamento=");
			builder.append(codDepartamento);
			builder.append(", ");
		}
		if (codMunicipio != null) {
			builder.append("codMunicipio=");
			builder.append(codMunicipio);
			builder.append(", ");
		}
		if (primerApellido != null) {
			builder.append("primerApellido=");
			builder.append(primerApellido);
			builder.append(", ");
		}
		if (segundoApellido != null) {
			builder.append("segundoApellido=");
			builder.append(segundoApellido);
			builder.append(", ");
		}
		if (primerNombre != null) {
			builder.append("primerNombre=");
			builder.append(primerNombre);
			builder.append(", ");
		}
		if (segundoNombre != null) {
			builder.append("segundoNombre=");
			builder.append(segundoNombre);
			builder.append(", ");
		}
		if (novIngreso != null) {
			builder.append("novIngreso=");
			builder.append(novIngreso);
			builder.append(", ");
		}
		if (novRetiro != null) {
			builder.append("novRetiro=");
			builder.append(novRetiro);
			builder.append(", ");
		}
		if (novVSP != null) {
			builder.append("novVSP=");
			builder.append(novVSP);
			builder.append(", ");
		}
		if (novVST != null) {
			builder.append("novVST=");
			builder.append(novVST);
			builder.append(", ");
		}
		if (novSLN != null) {
			builder.append("novSLN=");
			builder.append(novSLN);
			builder.append(", ");
		}
		if (novIGE != null) {
			builder.append("novIGE=");
			builder.append(novIGE);
			builder.append(", ");
		}
		if (novLMA != null) {
			builder.append("novLMA=");
			builder.append(novLMA);
			builder.append(", ");
		}
		if (novVACLR != null) {
			builder.append("novVACLR=");
			builder.append(novVACLR);
			builder.append(", ");
		}
		if (novSUS != null) {
			builder.append("novSUS=");
			builder.append(novSUS);
			builder.append(", ");
		}
		if (diasIRL != null) {
			builder.append("diasIRL=");
			builder.append(diasIRL);
			builder.append(", ");
		}
		if (diasCotizados != null) {
			builder.append("diasCotizados=");
			builder.append(diasCotizados);
			builder.append(", ");
		}
		if (salarioBasico != null) {
			builder.append("salarioBasico=");
			builder.append(salarioBasico);
			builder.append(", ");
		}
		if (valorIBC != null) {
			builder.append("valorIBC=");
			builder.append(valorIBC);
			builder.append(", ");
		}
		if (tarifa != null) {
			builder.append("tarifa=");
			builder.append(tarifa);
			builder.append(", ");
		}
		if (aporteObligatorio != null) {
			builder.append("aporteObligatorio=");
			builder.append(aporteObligatorio);
			builder.append(", ");
		}
		if (correcciones != null) {
			builder.append("correcciones=");
			builder.append(correcciones);
			builder.append(", ");
		}
		if (salarioIntegral != null) {
			builder.append("salarioIntegral=");
			builder.append(salarioIntegral);
			builder.append(", ");
		}
		if (fechaIngreso != null) {
			builder.append("fechaIngreso=");
			builder.append(fechaIngreso);
			builder.append(", ");
		}
		if (fechaRetiro != null) {
			builder.append("fechaRetiro=");
			builder.append(fechaRetiro);
			builder.append(", ");
		}
		if (fechaInicioVSP != null) {
			builder.append("fechaInicioVSP=");
			builder.append(fechaInicioVSP);
			builder.append(", ");
		}
		if (fechaInicioSLN != null) {
			builder.append("fechaInicioSLN=");
			builder.append(fechaInicioSLN);
			builder.append(", ");
		}
		if (fechaFinSLN != null) {
			builder.append("fechaFinSLN=");
			builder.append(fechaFinSLN);
			builder.append(", ");
		}
		if (fechaInicioIGE != null) {
			builder.append("fechaInicioIGE=");
			builder.append(fechaInicioIGE);
			builder.append(", ");
		}
		if (fechaFinIGE != null) {
			builder.append("fechaFinIGE=");
			builder.append(fechaFinIGE);
			builder.append(", ");
		}
		if (fechaInicioLMA != null) {
			builder.append("fechaInicioLMA=");
			builder.append(fechaInicioLMA);
			builder.append(", ");
		}
		if (fechaFinLMA != null) {
			builder.append("fechaFinLMA=");
			builder.append(fechaFinLMA);
			builder.append(", ");
		}
		if (fechaInicioVACLR != null) {
			builder.append("fechaInicioVACLR=");
			builder.append(fechaInicioVACLR);
			builder.append(", ");
		}
		if (fechaFinVACLR != null) {
			builder.append("fechaFinVACLR=");
			builder.append(fechaFinVACLR);
			builder.append(", ");
		}
		if (fechaInicioVCT != null) {
			builder.append("fechaInicioVCT=");
			builder.append(fechaInicioVCT);
			builder.append(", ");
		}
		if (fechaFinVCT != null) {
			builder.append("fechaFinVCT=");
			builder.append(fechaFinVCT);
			builder.append(", ");
		}
		if (fechaInicioIRL != null) {
			builder.append("fechaInicioIRL=");
			builder.append(fechaInicioIRL);
			builder.append(", ");
		}
		if (fechaFinIRL != null) {
			builder.append("fechaFinIRL=");
			builder.append(fechaFinIRL);
			builder.append(", ");
		}
		if (fechaInicioSuspension != null) {
			builder.append("fechaInicioSuspension=");
			builder.append(fechaInicioSuspension);
			builder.append(", ");
		}
		if (fechaFinSuspension != null) {
			builder.append("fechaFinSuspension=");
			builder.append(fechaFinSuspension);
			builder.append(", ");
		}
		if (horasLaboradas != null) {
			builder.append("horasLaboradas=");
			builder.append(horasLaboradas);
			builder.append(", ");
		}
		if (registroControl != null) {
			builder.append("registroControl=");
			builder.append(registroControl);
			builder.append(", ");
		}
		if (outMarcaValidacionRegistroAporte != null) {
			builder.append("outMarcaValidacionRegistroAporte=");
			builder.append(outMarcaValidacionRegistroAporte);
			builder.append(", ");
		}
		if (outEstadoRegistroAporte != null) {
			builder.append("outEstadoRegistroAporte=");
			builder.append(outEstadoRegistroAporte);
			builder.append(", ");
		}
		if (outAnalisisIntegral != null) {
			builder.append("outAnalisisIntegral=");
			builder.append(outAnalisisIntegral);
			builder.append(", ");
		}
		if (outFechaProcesamientoValidRegAporte != null) {
			builder.append("outFechaProcesamientoValidRegAporte=");
			builder.append(outFechaProcesamientoValidRegAporte);
			builder.append(", ");
		}
		if (outEstadoValidacionV0 != null) {
			builder.append("outEstadoValidacionV0=");
			builder.append(outEstadoValidacionV0);
			builder.append(", ");
		}
		if (outEstadoValidacionV1 != null) {
			builder.append("outEstadoValidacionV1=");
			builder.append(outEstadoValidacionV1);
			builder.append(", ");
		}
		if (outEstadoValidacionV2 != null) {
			builder.append("outEstadoValidacionV2=");
			builder.append(outEstadoValidacionV2);
			builder.append(", ");
		}
		if (outEstadoValidacionV3 != null) {
			builder.append("outEstadoValidacionV3=");
			builder.append(outEstadoValidacionV3);
			builder.append(", ");
		}
		if (outClaseTrabajador != null) {
			builder.append("outClaseTrabajador=");
			builder.append(outClaseTrabajador);
			builder.append(", ");
		}
		if (outPorcentajePagoAportes != null) {
			builder.append("outPorcentajePagoAportes=");
			builder.append(outPorcentajePagoAportes);
			builder.append(", ");
		}
		if (outEstadoSolicitante != null) {
			builder.append("outEstadoSolicitante=");
			builder.append(outEstadoSolicitante);
			builder.append(", ");
		}
		if (outEsTrabajadorReintegrable != null) {
			builder.append("outEsTrabajadorReintegrable=");
			builder.append(outEsTrabajadorReintegrable);
			builder.append(", ");
		}
		if (outFechaIngresoCotizante != null) {
			builder.append("outFechaIngresoCotizante=");
			builder.append(outFechaIngresoCotizante);
			builder.append(", ");
		}
		if (outFechaRetiroCotizante != null) {
			builder.append("outFechaRetiroCotizante=");
			builder.append(outFechaRetiroCotizante);
			builder.append(", ");
		}
		if (outFechaUltimaNovedad != null) {
			builder.append("outFechaUltimaNovedad=");
			builder.append(outFechaUltimaNovedad);
			builder.append(", ");
		}
		if (usuarioAprobadorAporte != null) {
			builder.append("usuarioAprobadorAporte=");
			builder.append(usuarioAprobadorAporte);
			builder.append(", ");
		}
		if (numeroOperacionAprobacion != null) {
			builder.append("numeroOperacionAprobacion=");
			builder.append(numeroOperacionAprobacion);
			builder.append(", ");
		}
		if (estadoEvaluacion != null) {
			builder.append("estadoEvaluacion=");
			builder.append(estadoEvaluacion);
			builder.append(", ");
		}
		if (estadoValidacionCorreccion != null) {
			builder.append("estadoValidacionCorreccion=");
			builder.append(estadoValidacionCorreccion);
			builder.append(", ");
		}
		if (outCodSucursal != null) {
			builder.append("outCodSucursal=");
			builder.append(outCodSucursal);
			builder.append(", ");
		}
		if (outNomSucursal != null) {
			builder.append("outNomSucursal=");
			builder.append(outNomSucursal);
			builder.append(", ");
		}
		if (outRegistrado != null) {
			builder.append("outRegistrado=");
			builder.append(outRegistrado);
			builder.append(", ");
		}
		if (outTipoAfiliado != null) {
			builder.append("outTipoAfiliado=");
			builder.append(outTipoAfiliado);
			builder.append(", ");
		}
		if (outValorMoraCotizante != null) {
			builder.append("outValorMoraCotizante=");
			builder.append(outValorMoraCotizante);
			builder.append(", ");
		}
		if (outAporteObligatorioMod != null) {
			builder.append("outAporteObligatorioMod=");
			builder.append(outAporteObligatorioMod);
			builder.append(", ");
		}
		if (outDiasCotizadosMod != null) {
			builder.append("outDiasCotizadosMod=");
			builder.append(outDiasCotizadosMod);
			builder.append(", ");
		}
		if (outValorIBCMod != null) {
			builder.append("outValorIBCMod=");
			builder.append(outValorIBCMod);
			builder.append(", ");
		}
		if (outValorMoraCotizanteMod != null) {
			builder.append("outValorMoraCotizanteMod=");
			builder.append(outValorMoraCotizanteMod);
			builder.append(", ");
		}
		if (outRegistradoAporte != null) {
			builder.append("outRegistradoAporte=");
			builder.append(outRegistradoAporte);
			builder.append(", ");
		}
		if (outRegistradoNovedad != null) {
			builder.append("outRegistradoNovedad=");
			builder.append(outRegistradoNovedad);
			builder.append(", ");
		}
		if (outIdRegDetOriginal != null) {
			builder.append("outIdRegDetOriginal=");
			builder.append(outIdRegDetOriginal);
			builder.append(", ");
		}
		if (outEstadoRegistroRelacionAporte != null) {
			builder.append("outEstadoRegistroRelacionAporte=");
			builder.append(outEstadoRegistroRelacionAporte);
			builder.append(", ");
		}
		if (outEstadoEvaluacionAporte != null) {
			builder.append("outEstadoEvaluacionAporte=");
			builder.append(outEstadoEvaluacionAporte);
			builder.append(", ");
		}
		if (subTipoCotizante != null) {
			builder.append("subTipoCotizante=");
			builder.append(subTipoCotizante);
			builder.append(", ");
		}
		if (fechaInicioVST != null) {
			builder.append("fechaInicioVST=");
			builder.append(fechaInicioVST);
			builder.append(", ");
		}
		if (fechaFinVST != null) {
			builder.append("fechaFinVST=");
			builder.append(fechaFinVST);
			builder.append(", ");
		}
		if (outGrupoFamiliarReintegrable != null) {
			builder.append("outGrupoFamiliarReintegrable=");
			builder.append(outGrupoFamiliarReintegrable);
			builder.append(", ");
		}
		if (idRegistro2pila != null) {
			builder.append("idRegistro2pila=");
			builder.append(idRegistro2pila);
			builder.append(", ");
		}
		if (outEnviadoAFiscalizacionInd != null) {
			builder.append("outEnviadoAFiscalizacionInd=");
			builder.append(outEnviadoAFiscalizacionInd);
			builder.append(", ");
		}
		if (outMotivoFiscalizacionInd != null) {
			builder.append("outMotivoFiscalizacionInd=");
			builder.append(outMotivoFiscalizacionInd);
			builder.append(", ");
		}
		if (outRegistroActual != null) {
			builder.append("outRegistroActual=");
			builder.append(outRegistroActual);
			builder.append(", ");
		}
		if (outRegInicial != null) {
			builder.append("outRegInicial=");
			builder.append(outRegInicial);
			builder.append(", ");
		}
		if (grupoAC != null) {
			builder.append("grupoAC=");
			builder.append(grupoAC);
			builder.append(", ");
		}
		if (outTarifaMod != null) {
			builder.append("outTarifaMod=");
			builder.append(outTarifaMod);
			builder.append(", ");
		}
		if (usuarioAccion != null) {
			builder.append("usuarioAccion=");
			builder.append(usuarioAccion);
			builder.append(", ");
		}
		if (fechaAccion != null) {
			builder.append("fechaAccion=");
			builder.append(fechaAccion);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
}
