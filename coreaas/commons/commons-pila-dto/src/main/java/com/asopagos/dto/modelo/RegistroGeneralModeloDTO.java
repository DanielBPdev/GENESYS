package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.asopagos.entidades.pila.staging.RegistroGeneral;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.ModalidadPlanillaEnum;
import com.asopagos.enumeraciones.cartera.MotivoFiscalizacionAportanteEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.MotivoProcesoPilaManualEnum;

/**
 * DTO con los datos de un Registro general en la staging.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 * @author Alfonso Baquero E. <abaquero@heinsohn.com.co>
 *
 */
public class RegistroGeneralModeloDTO implements Serializable { 
    private static final long serialVersionUID = 1L;
    /**
     * Código identificador de llave primaria llamada No. de operación de recaudo general
     */
    private Long id;
    /**
     * Referencia al registro de transaccion que agrupa registros relacionados en staging
     */
    private Long transaccion;
    /**
     * Marca de Referencia que indica si el registro de aporte es de un pensionado
     */
    private Boolean esAportePensionados;
    /**
     * Nombre o razon social del aportante
     */
    private String nombreAportante;
    /**
     * Descripción del tipo de identificacion del aportante
     */
    private TipoIdentificacionEnum tipoIdentificacionAportante;
    /**
     * Número de identificación del aportante
     */
    private String numeroIdentificacionAportante;
    /**
     * Digito de verificación del aportante
     */
    private Short digVerAportante;
    /**
     * Período de pago del aporte
     */
    private String periodoAporte;
    /**
     * Indica el tipo de planilla indicada en <code>TipoPlanillaEnum</code>
     */
    private String tipoPlanilla;
    /**
     * Indica la clase de aportante indicada en <code>ClaseAportanteEnum</code>
     */
    private String claseAportante;
    /**
     * Indica el código de la sucursal o de la dependencia
     */
    private String codSucursal;
    /**
     * Indica el nombre de la sucursal o de la dependencia
     */
    private String nomSucursal;
    /**
     * Indica la cantidad de pensionados reportados en planilla
     */
    private Integer cantPensionados;
    /**
     * Indica la Modalidad de la Planilla indicada en <code>ModalidadPlanillaEnum</code>
     */
    private ModalidadPlanillaEnum modalidadPlanilla;
    /**
     * Indica el aporte obligatorio del aporte
     */
    private BigDecimal valTotalApoObligatorio;
    /**
     * Indica el valor interés de mora para un pensionado
     */
    private BigDecimal valorIntMora;
    /**
     * Indica la fecha de reacudo del aporte
     */
    private Long fechaRecaudo;
    /**
     * Codigo de la entidad financiera recaudadora o receptora
     */
    private Short codigoEntidadFinanciera;
    /**
     * Referencia al operador de informacion que se relaciona en el registro
     * de la planilla integrada de liquidación de aportes
     */
    private Long operadorInformacion;
    /**
     * Indica el número de cuenta por la cual se hace recaudo del aporte
     */
    private String numeroCuenta;
    /**
     * Indica la fecha de actualización del registro.
     */
    private Long fechaActualizacion;
    /**
     * Referencia al registro de control asociado al registro general del aporte cargado
     * por operador de informacion mediante proceso automatico en staging
     */
    private Long registroControl;
    /**
     * Referencia al registro de control asociado al registro general del aporte cargado
     * mediante proceso manual en staging
     */
    private Long registroControlManual;
    /**
     * Referencia al registro de control asociado al registro general del aporte
     * del operador financiero cargado mediante proceso automatico en staging
     */
    private Long registroFControl;
    /**
     * Indica el campo de salida del proceso: tarifa del empleador
     */
    private BigDecimal outTarifaEmpleador;
    /**
     * Indica el campo de salida del proceso: finalizo proceso manual? 1=[si] y 0=[no]
     */
    private Boolean outFinalizadoProcesoManual;
    /**
     * Indica el campo de salida del proceso: es Empleador? 1=[si] y 0=[no]
     */
    private Boolean outEsEmpleador;
    /**
     * Indica el campo de salida del proceso: estado del empleador/aportante
     */
    private EstadoEmpleadorEnum outEstadoEmpleador;
    /**
     * Indica el campo de salida del proceso: típo de beneficio
     */
    private TipoBeneficioEnum outTipoBeneficio;
    /**
     * Indica el campo de salida del proceso: tiene beneficio activo? 1=[si] y 0=[no]
     */
    private Boolean outBeneficioActivo;
    /**
     * Indica el campo de salida del proceso: Empleador reintegrable? 1=[si] y 0=[no]
     */
    private Boolean outEsEmpleadorReintegrable;
    /**
     * Indica el campo de salida del proceso: estado de archivo de planilla
     */
    private EstadoProcesoArchivoEnum outEstadoArchivo;
    /**
     * Indica el número de planilla ingresada por PILA
     */
    private String numPlanilla;
    /**
     * Indica que el registro es una simulación de ejecución asistida
     */
    private Boolean esSimulado;
    /**
     * Estado del registro respecto a situación de corrección o ajuste
     */
    private EstadoAporteEnum estadoEvaluacion;

    /**
     * Indica que el aportante requiere que las sucursales de PILA tengan prioridad
     */
    private Boolean outMarcaSucursalPILA;

    /**
     * Indica el código de la sucursal principal del aportante en BD transaccional
     */
    private String outCodSucursalPrincipal;
    /**
     * Indica el nombre de la sucursal principal del aportante en BD transaccional
     */
    private String outNomSucursalPrincipal;
    /**
     * Dirección reportada por el aportante.
     */
    private String direccion;
    /**
     * Codigo de la ciudad o municipio.
     */
    private String codCiudad;
    
    /**
     * Codigo del departamento.
     */
    private String codDepartamento;
    
    /**
     * Teléfono del aportante..
     */
    private Long telefono;
    
    /**
     * Fax del aportante.
     */
    private Long fax;
    
    /**
     * Correo electrónico del aportante.
     */
    private String email;
    
    /**
     * Naturaleza Jurídica.
     */
    private Short naturalezaJuridica;
    
    /**
     * Fecha matrícula.
     */
    private Long fechaMatricula;
    
    /**
     * Marca de aportante para proceso de fiscalización de aportes
     * */
    private Boolean outEnviadoAFiscalizacion;
    
    /**
     * Motivo de la marca de envío a fiscalización
     * */
    private MotivoFiscalizacionAportanteEnum outMotivoFiscalizacion;
    
    /**
     * Marca de presencia de registro tipo 4 UGPP en planilla PILA
     * */
    private String numPlanillaAsociada;
    
    /**
     * Motivo para remitir la planilla a gestión manual
     * */
    private MotivoProcesoPilaManualEnum outMotivoProcesoManual;
    
    /**
     * Primer nombre del aportante como persona natural
     * */
    private String outPrimerNombreAportante;
    
    /**
     * Segundo nombre del aportante como persona natural
     * */
    private String outSegundoNombreAportante;
    
    /**
     * Primer apellido del aportante como persona natural
     * */
    private String outPrimerApellidoAportante;
    
    /**
     * Segundo apellido del aportante como persona natural
     * */
    private String outSegundoApellidoAportante;
    /**
     * Días de mora para el aporte
     */
    private Short diasMora;
    /**
     * Fecha pago de la planilla asociada 
     */
    private Date fechaPlanilla;
    /**
     * Forma de presentación del aporte  
     */
    private String formaPresentacion;
    /**
     * Número total de empleados
     */
    private Integer cantEmpleados;
    /**
     * Número total de afiliados
     */
    private Integer cantAfiliados;

    /**
     * cuentaBancariaRecaudo
     */
    private Integer cuentaBancariaRecaudo;

    /**
     * Tipo de persona ingresada en el aporte
     */
    private String tipoPersona;
    /**
     * Indicador que establece que el registro se encuentra en proceso (presentación en bandeja de gestión)
     * */
    private Boolean outEnProceso;
    
    /**
     * Cantidad de registros tipo 2
     * */
    private Integer cantidadReg2;
    
    /**
     * Fecha de pago de la planilla asociada
     * */
    private Date fechaPagoPlanillaAsociada;
    
    /**
     * Indica que se trata del registro actual (inicial o luego de modificaciones)
     * */
    private Boolean outRegistroActual;
    
    /**
     * Referencia al registro general original (sólo aplica para registros creados a partir de devoluciones)
     * */
    private Long outRegInicial;
    
    /** Indica si la planilla aún no ha terminado su procesamiento  */
    @JsonIgnore
    private Boolean enProcesamiento;
    
    /** Indica si la planilla tuvo un fallo en su procesamiento y se debe ir a la bandeja transitoria */
    @JsonIgnore
    private Boolean enBandejaTransitoria;
    
    /** Constructor por defecto */
    public RegistroGeneralModeloDTO(){
        super();
    }
    
    /** Constructor a partir de entity */
    public RegistroGeneralModeloDTO(RegistroGeneral registroGeneral){
        super();
        this.convertToDTO(registroGeneral);
    }

    /**
     * Método encargado de convertir de DTO a Entidad.
     * @return entidad convertida.
     */
    public RegistroGeneral convertToEntity() {
        RegistroGeneral registroGeneral = new RegistroGeneral();        
        registroGeneral.setCuentaBancariaRecaudo(this.getCuentaBancariaRecaudo());
        registroGeneral.setCantPensionados(this.getCantPensionados());
        registroGeneral.setClaseAportante(this.getClaseAportante());
        registroGeneral.setCodigoEntidadFinanciera(this.getCodigoEntidadFinanciera());
        registroGeneral.setCodSucursal(this.getCodSucursal());
        registroGeneral.setDigVerAportante(this.getDigVerAportante());
        registroGeneral.setEsAportePensionados(this.getEsAportePensionados());
        if (this.getFechaActualizacion() != null) {
            registroGeneral.setFechaActualizacion(new Date(this.getFechaActualizacion()));
        }
        if(this.getFechaRecaudo()!=null){
            registroGeneral.setFechaRecaudo(new Date(this.getFechaRecaudo()));
        }
        registroGeneral.setId(this.getId());
        if(this.getModalidadPlanilla()!=null){
            registroGeneral.setModalidadPlanilla(this.getModalidadPlanilla().getCodigo().shortValue());
        }
        registroGeneral.setNombreAportante(this.getNombreAportante());
        registroGeneral.setNomSucursal(this.getNomSucursal());
        registroGeneral.setNumeroCuenta(this.getNumeroCuenta());
        registroGeneral.setNumeroIdentificacionAportante(this.getNumeroIdentificacionAportante());
        registroGeneral.setOperadorInformacion(this.getOperadorInformacion());
        registroGeneral.setOutBeneficioActivo(this.getOutBeneficioActivo());
        registroGeneral.setOutEsEmpleador(this.getOutEsEmpleador());
        registroGeneral.setOutEsEmpleadorReintegrable(this.getOutEsEmpleadorReintegrable());
        registroGeneral.setOutEstadoArchivo(this.getOutEstadoArchivo());
        registroGeneral.setOutEstadoEmpleador(this.getOutEstadoEmpleador());
        registroGeneral.setOutFinalizadoProcesoManual(this.getOutFinalizadoProcesoManual());
        registroGeneral.setOutTarifaEmpleador(this.getOutTarifaEmpleador());
        registroGeneral.setOutTipoBeneficio(this.getOutTipoBeneficio());
        registroGeneral.setPeriodoAporte(this.getPeriodoAporte());
        registroGeneral.setRegistroControl(this.getRegistroControl());
        registroGeneral.setRegistroControlManual(this.getRegistroControlManual());
        registroGeneral.setRegistroFControl(this.getRegistroFControl());
        registroGeneral.setTipoIdentificacionAportante(this.getTipoIdentificacionAportante());
        registroGeneral.setTipoPlanilla(this.getTipoPlanilla());
        registroGeneral.setTransaccion(this.getTransaccion());
        registroGeneral.setValorIntMora(this.getValorIntMora());
        registroGeneral.setValTotalApoObligatorio(this.getValTotalApoObligatorio());
        registroGeneral.setId(this.getId());
        registroGeneral.setEsSimulado(this.getEsSimulado());
        registroGeneral.setEstadoEvaluacion(this.getEstadoEvaluacion());
        registroGeneral.setOutMarcaSucursalPILA(this.getOutMarcaSucursalPILA());
        registroGeneral.setOutCodSucursalPrincipal(this.getOutCodSucursalPrincipal());
        registroGeneral.setOutNomSucursalPrincipal(this.getOutNomSucursalPrincipal());
        registroGeneral.setNumPlanilla(this.getNumPlanilla());
        registroGeneral.setDireccion(this.getDireccion());
        registroGeneral.setCodCiudad(this.getCodCiudad());
        registroGeneral.setCodDepartamento(this.getCodDepartamento());
        registroGeneral.setEmail(this.getEmail());
        if(this.getFechaMatricula() != null){
            registroGeneral.setFechaMatricula(new Date(this.getFechaMatricula()));
        }
        registroGeneral.setTelefono(this.getTelefono());
        registroGeneral.setFax(this.getFax());
        registroGeneral.setNaturalezaJuridica(this.getNaturalezaJuridica());
        registroGeneral.setOutEnviadoAFiscalizacion(this.getOutEnviadoAFiscalizacion());
        registroGeneral.setOutMotivoFiscalizacion(this.getOutMotivoFiscalizacion());
        registroGeneral.setOutMotivoProcesoManual(this.getOutMotivoProcesoManual());
        registroGeneral.setNumPlanillaAsociada(this.getNumPlanillaAsociada());
        registroGeneral.setOutPrimerNombreAportante(this.getOutPrimerNombreAportante());
        registroGeneral.setOutSegundoNombreAportante(this.getOutSegundoNombreAportante());
        registroGeneral.setOutPrimerApellidoAportante(this.getOutPrimerApellidoAportante());
        registroGeneral.setOutSegundoApellidoAportante(this.getOutSegundoApellidoAportante());
        registroGeneral.setDiasMora(this.getDiasMora());
        registroGeneral.setFechaPlanilla(this.getFechaPlanilla());
        registroGeneral.setFormaPresentacion(this.getFormaPresentacion());
        registroGeneral.setCantEmpleados(this.getCantEmpleados());
        registroGeneral.setCantAfiliados(this.getCantAfiliados());
        registroGeneral.setTipoPersona(this.getTipoPersona());
        registroGeneral.setOutEnProceso(this.getOutEnProceso());
        registroGeneral.setCantidadReg2(this.getCantidadReg2());
        registroGeneral.setFechaPagoPlanillaAsociada(this.getFechaPagoPlanillaAsociada());
        registroGeneral.setOutRegInicial(this.getOutRegInicial());
        registroGeneral.setOutRegistroActual(this.getOutRegistroActual());
        return registroGeneral;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param solicitud
     *        entidad a convertir.
     */
    public RegistroGeneralModeloDTO(
        Long id,
        Long transaccion,
        Boolean esAportePensionados,
        String nombreAportante,
        String tipoIdentificacionAportante,
        String numeroIdentificacionAportante,
        Short digVerAportante,
        String periodoAporte,
        String tipoPlanilla,
        String claseAportante,
        String codSucursal,
        String nomSucursal,
        Integer cantPensionados,
        String modalidadPlanilla,
        BigDecimal valTotalApoObligatorio,
        BigDecimal valorIntMora,
        Date fechaRecaudo,
        Short codigoEntidadFinanciera,
        Long operadorInformacion,
        String numeroCuenta,
        Date fechaActualizacion,
        Long registroControl,
        Long registroControlManual,
        Long registroFControl,
        BigDecimal outTarifaEmpleador,
        Boolean outFinalizadoProcesoManual,
        Boolean outEsEmpleador,
        String outEstadoEmpleador,
        String outTipoBeneficio,
        Boolean outBeneficioActivo,
        Boolean outEsEmpleadorReintegrable,
        String outEstadoArchivo,
        Boolean esSimulado,
        String estadoEvaluacion,
        Boolean outMarcaSucursalPILA,
        String outCodSucursalPrincipal,
        String outNomSucursalPrincipal,
        String numPlanilla,
        String direccion,
        String codCiudad,
        String codDepartamento,
        String email,
        Date fechaMatricula,
        Long telefono,
        Long fax,
        Short naturalezaJuridica,
        Boolean outEnviadoAFiscalizacion,
        String outMotivoFiscalizacion,
        String outMotivoProcesoManual,
        String numPlanillaAsociada,
        String outPrimerNombreAportante,
        String outSegundoNombreAportante,
        String outPrimerApellidoAportante,
        String outSegundoApellidoAportante,
        Short diasMora,
        Date fechaPlanilla,
        String formaPresentacion,
        Integer cantEmpleados,
        Integer cantAfiliados,
        String tipoPersona,
        Boolean outEnProceso,
        Integer cantidadReg2,
        Date fechaPagoPlanillaAsociada,
        Boolean outRegistroActual,
        Long  outRegInicial,
        Integer cuentaBancariaRecaudo
    ) {
        this.id = id;
        this.transaccion = transaccion;
        this.esAportePensionados = esAportePensionados;
        this.nombreAportante = nombreAportante;
        if (tipoIdentificacionAportante != null && !tipoIdentificacionAportante.equals("")) {
            this.tipoIdentificacionAportante = TipoIdentificacionEnum.valueOf(tipoIdentificacionAportante);
        }
        this.numeroIdentificacionAportante = numeroIdentificacionAportante;
        this.digVerAportante = digVerAportante;
        this.periodoAporte = periodoAporte;
        this.tipoPlanilla = tipoPlanilla;
        this.claseAportante = claseAportante;
        this.codSucursal = codSucursal;
        this.nomSucursal = nomSucursal;
        this.cantPensionados = cantPensionados;
        if (modalidadPlanilla != null && !modalidadPlanilla.equals("")) {
            this.modalidadPlanilla = ModalidadPlanillaEnum.obtenerModalidadPlanillaEnum(Integer.valueOf(modalidadPlanilla));
        }
        this.valTotalApoObligatorio = valTotalApoObligatorio;
        this.valorIntMora = valorIntMora;
        if (fechaRecaudo != null) {
            this.fechaRecaudo = fechaRecaudo.getTime();
        }
        this.codigoEntidadFinanciera = codigoEntidadFinanciera;
        this.operadorInformacion = operadorInformacion;
        this.numeroCuenta = numeroCuenta;
        if (fechaActualizacion != null) {
            this.fechaActualizacion = fechaActualizacion.getTime();
        }
        this.registroControl = registroControl;
        this.registroControlManual = registroControlManual;
        this.registroFControl = registroFControl;
        this.outTarifaEmpleador = outTarifaEmpleador;
        this.outFinalizadoProcesoManual = outFinalizadoProcesoManual;
        this.outEsEmpleador = outEsEmpleador;
        if (outEstadoEmpleador != null && !outEstadoEmpleador.equals("")){
            EstadoEmpleadorEnum estadoE = EstadoEmpleadorEnum.valueOf(outEstadoEmpleador);
            this.outEstadoEmpleador = estadoE;
        }
        if (outTipoBeneficio != null && !outTipoBeneficio.equals("")){
            TipoBeneficioEnum tipoB = TipoBeneficioEnum.valueOf(outTipoBeneficio);
            this.outTipoBeneficio = tipoB;
        }
        this.outBeneficioActivo = outBeneficioActivo;
        this.outEsEmpleadorReintegrable = outEsEmpleadorReintegrable;
        if (outEstadoArchivo != null && !outEstadoArchivo.equals("")){
            EstadoProcesoArchivoEnum estadoA = EstadoProcesoArchivoEnum.valueOf(outEstadoArchivo);
            this.outEstadoArchivo = estadoA;
        }
        this.esSimulado = esSimulado;
        if (estadoEvaluacion != null && !estadoEvaluacion.equals("")){
            EstadoAporteEnum estadoApo = EstadoAporteEnum.valueOf(estadoEvaluacion);
            this.estadoEvaluacion = estadoApo;
        }
        this.outMarcaSucursalPILA = outMarcaSucursalPILA;
        this.outCodSucursalPrincipal = outCodSucursalPrincipal;
        this.outNomSucursalPrincipal = outNomSucursalPrincipal;
        this.numPlanilla = numPlanilla;
        this.direccion = direccion;
        this.codCiudad = codCiudad;
        this.codDepartamento = codDepartamento;
        this.email = email;
        if (fechaMatricula != null) {
            this.fechaMatricula = fechaMatricula.getTime();
        }
        this.telefono = telefono;
        this.fax = fax;
        this.naturalezaJuridica = naturalezaJuridica;
        this.outEnviadoAFiscalizacion = outEnviadoAFiscalizacion;
        if (outMotivoFiscalizacion != null && !outMotivoFiscalizacion.equals("")){
            MotivoFiscalizacionAportanteEnum motFis = MotivoFiscalizacionAportanteEnum.valueOf(outMotivoFiscalizacion);
            this.outMotivoFiscalizacion = motFis;
        }
        if (outMotivoProcesoManual != null && !outMotivoProcesoManual.equals("")){
            MotivoProcesoPilaManualEnum motPro = MotivoProcesoPilaManualEnum.valueOf(outMotivoProcesoManual);
            this.outMotivoProcesoManual = motPro;
        }
        this.numPlanillaAsociada = numPlanillaAsociada;
        this.outPrimerNombreAportante = outPrimerNombreAportante;
        this.outSegundoNombreAportante = outSegundoNombreAportante;
        this.outPrimerApellidoAportante = outPrimerApellidoAportante;
        this.outSegundoApellidoAportante = outSegundoApellidoAportante;
        this.diasMora = diasMora;
        this.fechaPlanilla = fechaPlanilla;
        this.formaPresentacion = formaPresentacion;
        this.cantEmpleados = cantEmpleados;
        this.cantAfiliados = cantAfiliados;
        this.tipoPersona = tipoPersona;
        this.outEnProceso = outEnProceso;
        this.cantidadReg2 = cantidadReg2;
        this.fechaPagoPlanillaAsociada = fechaPagoPlanillaAsociada;
        this.outRegistroActual = outRegistroActual;
        this.outRegInicial = outRegInicial;
        this.cuentaBancariaRecaudo = cuentaBancariaRecaudo;

    }

    public void convertToDTO(RegistroGeneral registroGeneral) {
        this.id = registroGeneral.getId();
        this.transaccion = registroGeneral.getTransaccion();
        this.esAportePensionados = registroGeneral.getEsAportePensionados();
        this.nombreAportante = registroGeneral.getNombreAportante();
        this.tipoIdentificacionAportante = registroGeneral.getTipoIdentificacionAportante();
        this.numeroIdentificacionAportante = registroGeneral.getNumeroIdentificacionAportante();
        this.digVerAportante = registroGeneral.getDigVerAportante();
        this.periodoAporte = registroGeneral.getPeriodoAporte();
        this.tipoPlanilla = registroGeneral.getTipoPlanilla();
        this.claseAportante = registroGeneral.getClaseAportante();
        this.codSucursal = registroGeneral.getCodSucursal();
        this.nomSucursal = registroGeneral.getNomSucursal();
        this.cantPensionados = registroGeneral.getCantPensionados();
        if (registroGeneral.getModalidadPlanilla() != null) {
            this.modalidadPlanilla = ModalidadPlanillaEnum.obtenerModalidadPlanillaEnum(registroGeneral.getModalidadPlanilla().intValue());
        }
        this.valTotalApoObligatorio = registroGeneral.getValTotalApoObligatorio();
        this.valorIntMora = registroGeneral.getValorIntMora();
        if (registroGeneral.getFechaRecaudo() != null) {
            this.fechaRecaudo = registroGeneral.getFechaRecaudo().getTime();
        }
        this.codigoEntidadFinanciera = registroGeneral.getCodigoEntidadFinanciera();
        this.operadorInformacion = registroGeneral.getOperadorInformacion();
        this.numeroCuenta = registroGeneral.getNumeroCuenta();
        if (registroGeneral.getFechaActualizacion() != null) {
            this.fechaActualizacion = registroGeneral.getFechaActualizacion().getTime();
        }
        this.registroControl = registroGeneral.getRegistroControl();
        this.registroControlManual = registroGeneral.getRegistroControlManual();
        this.registroFControl = registroGeneral.getRegistroFControl();
        this.outTarifaEmpleador = registroGeneral.getOutTarifaEmpleador();
        this.outFinalizadoProcesoManual = registroGeneral.getOutFinalizadoProcesoManual();
        this.outEsEmpleador = registroGeneral.getOutEsEmpleador();
        this.outEstadoEmpleador = registroGeneral.getOutEstadoEmpleador();
        this.outTipoBeneficio = registroGeneral.getOutTipoBeneficio();
        this.outBeneficioActivo = registroGeneral.getOutBeneficioActivo();
        this.outEsEmpleadorReintegrable = registroGeneral.getOutEsEmpleadorReintegrable();
        this.outEstadoArchivo = registroGeneral.getOutEstadoArchivo();
        this.esSimulado = registroGeneral.getEsSimulado();
        this.estadoEvaluacion = registroGeneral.getEstadoEvaluacion();
        this.outMarcaSucursalPILA = registroGeneral.getOutMarcaSucursalPILA();
        this.outCodSucursalPrincipal = registroGeneral.getOutCodSucursalPrincipal();
        this.outNomSucursalPrincipal = registroGeneral.getOutNomSucursalPrincipal();
        this.numPlanilla = registroGeneral.getNumPlanilla();
        this.setDireccion(registroGeneral.getDireccion());
        this.setCodCiudad(registroGeneral.getCodCiudad());
        this.setCodDepartamento(registroGeneral.getCodDepartamento());
        this.setEmail(registroGeneral.getEmail());
        if(registroGeneral.getFechaMatricula() != null){
            this.setFechaMatricula(registroGeneral.getFechaMatricula().getTime());
        }
        this.setTelefono(registroGeneral.getTelefono());
        this.setFax(registroGeneral.getFax());
        this.setNaturalezaJuridica(registroGeneral.getNaturalezaJuridica());
        this.setOutEnviadoAFiscalizacion(registroGeneral.getOutEnviadoAFiscalizacion());
        this.setOutMotivoFiscalizacion(registroGeneral.getOutMotivoFiscalizacion());
        this.setOutMotivoProcesoManual(registroGeneral.getOutMotivoProcesoManual());
        this.setNumPlanillaAsociada(registroGeneral.getNumPlanillaAsociada());
        this.setOutPrimerNombreAportante(registroGeneral.getOutPrimerNombreAportante());
        this.setOutSegundoNombreAportante(registroGeneral.getOutSegundoNombreAportante());
        this.setOutPrimerApellidoAportante(registroGeneral.getOutPrimerApellidoAportante());
        this.setOutSegundoApellidoAportante(registroGeneral.getOutSegundoApellidoAportante());
        this.setDiasMora(registroGeneral.getDiasMora());
        this.setFechaPlanilla(registroGeneral.getFechaPlanilla());
        this.setFormaPresentacion(registroGeneral.getFormaPresentacion());
        this.setCantEmpleados(registroGeneral.getCantEmpleados());
        this.setCantAfiliados(registroGeneral.getCantAfiliados());
        this.setTipoPersona(registroGeneral.getTipoPersona());
        this.setOutEnProceso(registroGeneral.getOutEnProceso());
        this.setCantidadReg2(registroGeneral.getCantidadReg2());
        this.setFechaPagoPlanillaAsociada(registroGeneral.getFechaPagoPlanillaAsociada());
        this.setOutRegistroActual(registroGeneral.getOutRegistroActual());
        this.setOutRegInicial(registroGeneral.getOutRegInicial());
        this.setCuentaBancariaRecaudo(registroGeneral.getCuentaBancariaRecaudo()); 
        
    }

    /**
     * Método encargado de copiar un DTO a una Entidad.
     * @param solicitudAporte
     *        previamente consultado.
     * @return solicitudAporte solicitud modificada con los datos del DTO.
     */
    public RegistroGeneral copyDTOToEntiy(RegistroGeneral registroGeneral) {
        return registroGeneral;

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
     * Método que retorna el valor de transaccion.
     * @return valor de transaccion.
     */
    public Long getTransaccion() {
        return transaccion;
    }

    /**
     * Método encargado de modificar el valor de transaccion.
     * @param valor
     *        para modificar transaccion.
     */
    public void setTransaccion(Long transaccion) {
        this.transaccion = transaccion;
    }

    /**
     * Método que retorna el valor de esAportePensionados.
     * @return valor de esAportePensionados.
     */
    public Boolean getEsAportePensionados() {
        return esAportePensionados;
    }

    /**
     * Método encargado de modificar el valor de esAportePensionados.
     * @param valor
     *        para modificar esAportePensionados.
     */
    public void setEsAportePensionados(Boolean esAportePensionados) {
        this.esAportePensionados = esAportePensionados;
    }

    /**
     * Método que retorna el valor de nombreAportante.
     * @return valor de nombreAportante.
     */
    public String getNombreAportante() {
        return nombreAportante;
    }

    /**
     * Método encargado de modificar el valor de nombreAportante.
     * @param valor
     *        para modificar nombreAportante.
     */
    public void setNombreAportante(String nombreAportante) {
        this.nombreAportante = nombreAportante;
    }

    /**
     * Método que retorna el valor de tipoIdentificacionAportante.
     * @return valor de tipoIdentificacionAportante.
     */
    public TipoIdentificacionEnum getTipoIdentificacionAportante() {
        return tipoIdentificacionAportante;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacionAportante.
     * @param valor
     *        para modificar tipoIdentificacionAportante.
     */
    public void setTipoIdentificacionAportante(TipoIdentificacionEnum tipoIdentificacionAportante) {
        this.tipoIdentificacionAportante = tipoIdentificacionAportante;
    }

    /**
     * Método que retorna el valor de numeroIdentificacionAportante.
     * @return valor de numeroIdentificacionAportante.
     */
    public String getNumeroIdentificacionAportante() {
        return numeroIdentificacionAportante;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacionAportante.
     * @param valor
     *        para modificar numeroIdentificacionAportante.
     */
    public void setNumeroIdentificacionAportante(String numeroIdentificacionAportante) {
        this.numeroIdentificacionAportante = numeroIdentificacionAportante;
    }

    /**
     * Método que retorna el valor de digVerAportante.
     * @return valor de digVerAportante.
     */
    public Short getDigVerAportante() {
        return digVerAportante;
    }

    /**
     * Método encargado de modificar el valor de digVerAportante.
     * @param valor
     *        para modificar digVerAportante.
     */
    public void setDigVerAportante(Short digVerAportante) {
        this.digVerAportante = digVerAportante;
    }

    /**
     * Método que retorna el valor de periodoAporte.
     * @return valor de periodoAporte.
     */
    public String getPeriodoAporte() {
        return periodoAporte;
    }

    /**
     * Método encargado de modificar el valor de periodoAporte.
     * @param valor
     *        para modificar periodoAporte.
     */
    public void setPeriodoAporte(String periodoAporte) {
        this.periodoAporte = periodoAporte;
    }

    /**
     * Método que retorna el valor de tipoPlanilla.
     * @return valor de tipoPlanilla.
     */
    public String getTipoPlanilla() {
        return tipoPlanilla;
    }

    /**
     * Método encargado de modificar el valor de tipoPlanilla.
     * @param valor
     *        para modificar tipoPlanilla.
     */
    public void setTipoPlanilla(String tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }

    /**
     * Método que retorna el valor de claseAportante.
     * @return valor de claseAportante.
     */
    public String getClaseAportante() {
        return claseAportante;
    }

    /**
     * Método encargado de modificar el valor de claseAportante.
     * @param valor
     *        para modificar claseAportante.
     */
    public void setClaseAportante(String claseAportante) {
        this.claseAportante = claseAportante;
    }

    /**
     * Método que retorna el valor de codSucursal.
     * @return valor de codSucursal.
     */
    public String getCodSucursal() {
        return codSucursal;
    }

    /**
     * Método encargado de modificar el valor de codSucursal.
     * @param valor
     *        para modificar codSucursal.
     */
    public void setCodSucursal(String codSucursal) {
        this.codSucursal = codSucursal;
    }

    /**
     * Método que retorna el valor de nomSucursal.
     * @return valor de nomSucursal.
     */
    public String getNomSucursal() {
        return nomSucursal;
    }

    /**
     * Método encargado de modificar el valor de nomSucursal.
     * @param valor
     *        para modificar nomSucursal.
     */
    public void setNomSucursal(String nomSucursal) {
        this.nomSucursal = nomSucursal;
    }

    /**
     * Método que retorna el valor de cantPensionados.
     * @return valor de cantPensionados.
     */
    public Integer getCantPensionados() {
        return cantPensionados;
    }

    /**
     * Método encargado de modificar el valor de cantPensionados.
     * @param valor
     *        para modificar cantPensionados.
     */
    public void setCantPensionados(Integer cantPensionados) {
        this.cantPensionados = cantPensionados;
    }

    

    /**
     * Método que retorna el valor de cantPensionados.
     * @return valor de cantPensionados.
     */
    public Integer getCuentaBancariaRecaudo() {
        return cuentaBancariaRecaudo;
    }

    /**
     * Método encargado de modificar el valor de cantPensionados.
     * @param valor
     *        para modificar cantPensionados.
     */
    public void setCuentaBancariaRecaudo(Integer cuentaBancariaRecaudo) {
        this.cuentaBancariaRecaudo = cuentaBancariaRecaudo;
    }

    /**
     * Método que retorna el valor de modalidadPlanilla.
     * @return valor de modalidadPlanilla.
     */
    public ModalidadPlanillaEnum getModalidadPlanilla() {
        return modalidadPlanilla;
    }

    /**
     * Método encargado de modificar el valor de modalidadPlanilla.
     * @param valor
     *        para modificar modalidadPlanilla.
     */
    public void setModalidadPlanilla(ModalidadPlanillaEnum modalidadPlanilla) {
        this.modalidadPlanilla = modalidadPlanilla;
    }

    /**
     * Método que retorna el valor de fechaRecaudo.
     * @return valor de fechaRecaudo.
     */
    public Long getFechaRecaudo() {
        return fechaRecaudo;
    }

    /**
     * Método encargado de modificar el valor de fechaRecaudo.
     * @param valor
     *        para modificar fechaRecaudo.
     */
    public void setFechaRecaudo(Long fechaRecaudo) {
        this.fechaRecaudo = fechaRecaudo;
    }

    /**
     * Método que retorna el valor de codigoEntidadFinanciera.
     * @return valor de codigoEntidadFinanciera.
     */
    public Short getCodigoEntidadFinanciera() {
        return codigoEntidadFinanciera;
    }

    /**
     * Método encargado de modificar el valor de codigoEntidadFinanciera.
     * @param valor
     *        para modificar codigoEntidadFinanciera.
     */
    public void setCodigoEntidadFinanciera(Short codigoEntidadFinanciera) {
        this.codigoEntidadFinanciera = codigoEntidadFinanciera;
    }

    /**
     * Método que retorna el valor de operadorInformacion.
     * @return valor de operadorInformacion.
     */
    public Long getOperadorInformacion() {
        return operadorInformacion;
    }

    /**
     * Método encargado de modificar el valor de operadorInformacion.
     * @param valor
     *        para modificar operadorInformacion.
     */
    public void setOperadorInformacion(Long operadorInformacion) {
        this.operadorInformacion = operadorInformacion;
    }

    /**
     * Método que retorna el valor de numeroCuenta.
     * @return valor de numeroCuenta.
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * Método encargado de modificar el valor de numeroCuenta.
     * @param valor
     *        para modificar numeroCuenta.
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * Método que retorna el valor de fechaActualizacion.
     * @return valor de fechaActualizacion.
     */
    public Long getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * Método encargado de modificar el valor de fechaActualizacion.
     * @param valor
     *        para modificar fechaActualizacion.
     */
    public void setFechaActualizacion(Long fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
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
     * Método que retorna el valor de registroControlManual.
     * @return valor de registroControlManual.
     */
    public Long getRegistroControlManual() {
        return registroControlManual;
    }

    /**
     * Método encargado de modificar el valor de registroControlManual.
     * @param valor
     *        para modificar registroControlManual.
     */
    public void setRegistroControlManual(Long registroControlManual) {
        this.registroControlManual = registroControlManual;
    }

    /**
     * Método que retorna el valor de registroFControl.
     * @return valor de registroFControl.
     */
    public Long getRegistroFControl() {
        return registroFControl;
    }

    /**
     * Método encargado de modificar el valor de registroFControl.
     * @param valor
     *        para modificar registroFControl.
     */
    public void setRegistroFControl(Long registroFControl) {
        this.registroFControl = registroFControl;
    }

    /**
     * Método que retorna el valor de outTarifaEmpleador.
     * @return valor de outTarifaEmpleador.
     */
    public BigDecimal getOutTarifaEmpleador() {
        return outTarifaEmpleador;
    }

    /**
     * Método encargado de modificar el valor de outTarifaEmpleador.
     * @param valor
     *        para modificar outTarifaEmpleador.
     */
    public void setOutTarifaEmpleador(BigDecimal outTarifaEmpleador) {
        this.outTarifaEmpleador = outTarifaEmpleador;
    }

    /**
     * Método que retorna el valor de outFinalizadoProcesoManual.
     * @return valor de outFinalizadoProcesoManual.
     */
    public Boolean getOutFinalizadoProcesoManual() {
        return outFinalizadoProcesoManual;
    }

    /**
     * Método encargado de modificar el valor de outFinalizadoProcesoManual.
     * @param valor
     *        para modificar outFinalizadoProcesoManual.
     */
    public void setOutFinalizadoProcesoManual(Boolean outFinalizadoProcesoManual) {
        this.outFinalizadoProcesoManual = outFinalizadoProcesoManual;
    }

    /**
     * Método que retorna el valor de outEsEmpleador.
     * @return valor de outEsEmpleador.
     */
    public Boolean getOutEsEmpleador() {
        return outEsEmpleador;
    }

    /**
     * Método encargado de modificar el valor de outEsEmpleador.
     * @param valor
     *        para modificar outEsEmpleador.
     */
    public void setOutEsEmpleador(Boolean outEsEmpleador) {
        this.outEsEmpleador = outEsEmpleador;
    }

    /**
     * Método que retorna el valor de outEstadoEmpleador.
     * @return valor de outEstadoEmpleador.
     */
    public EstadoEmpleadorEnum getOutEstadoEmpleador() {
        return outEstadoEmpleador;
    }

    /**
     * Método encargado de modificar el valor de outEstadoEmpleador.
     * @param valor
     *        para modificar outEstadoEmpleador.
     */
    public void setOutEstadoEmpleador(EstadoEmpleadorEnum outEstadoEmpleador) {
        this.outEstadoEmpleador = outEstadoEmpleador;
    }

    /**
     * Método que retorna el valor de outTipoBeneficio.
     * @return valor de outTipoBeneficio.
     */
    public TipoBeneficioEnum getOutTipoBeneficio() {
        return outTipoBeneficio;
    }

    /**
     * Método encargado de modificar el valor de outTipoBeneficio.
     * @param valor
     *        para modificar outTipoBeneficio.
     */
    public void setOutTipoBeneficio(TipoBeneficioEnum outTipoBeneficio) {
        this.outTipoBeneficio = outTipoBeneficio;
    }

    /**
     * Método que retorna el valor de outBeneficioActivo.
     * @return valor de outBeneficioActivo.
     */
    public Boolean getOutBeneficioActivo() {
        return outBeneficioActivo;
    }

    /**
     * Método encargado de modificar el valor de outBeneficioActivo.
     * @param valor
     *        para modificar outBeneficioActivo.
     */
    public void setOutBeneficioActivo(Boolean outBeneficioActivo) {
        this.outBeneficioActivo = outBeneficioActivo;
    }

    /**
     * Método que retorna el valor de outEsEmpleadorReintegrable.
     * @return valor de outEsEmpleadorReintegrable.
     */
    public Boolean getOutEsEmpleadorReintegrable() {
        return outEsEmpleadorReintegrable;
    }

    /**
     * Método encargado de modificar el valor de outEsEmpleadorReintegrable.
     * @param valor
     *        para modificar outEsEmpleadorReintegrable.
     */
    public void setOutEsEmpleadorReintegrable(Boolean outEsEmpleadorReintegrable) {
        this.outEsEmpleadorReintegrable = outEsEmpleadorReintegrable;
    }

    /**
     * Método que retorna el valor de outEstadoArchivo.
     * @return valor de outEstadoArchivo.
     */
    public EstadoProcesoArchivoEnum getOutEstadoArchivo() {
        return outEstadoArchivo;
    }

    /**
     * Método encargado de modificar el valor de outEstadoArchivo.
     * @param valor
     *        para modificar outEstadoArchivo.
     */
    public void setOutEstadoArchivo(EstadoProcesoArchivoEnum outEstadoArchivo) {
        this.outEstadoArchivo = outEstadoArchivo;
    }

    /**
     * @return the esSimulado
     */
    public Boolean getEsSimulado() {
        return esSimulado;
    }

    /**
     * @param esSimulado
     *        the esSimulado to set
     */
    public void setEsSimulado(Boolean esSimulado) {
        this.esSimulado = esSimulado;
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
     * @return the valTotalApoObligatorio
     */
    public BigDecimal getValTotalApoObligatorio() {
        return valTotalApoObligatorio;
    }

    /**
     * @param valTotalApoObligatorio
     *        the valTotalApoObligatorio to set
     */
    public void setValTotalApoObligatorio(BigDecimal valTotalApoObligatorio) {
        this.valTotalApoObligatorio = valTotalApoObligatorio;
    }

    /**
     * @return the valorIntMora
     */
    public BigDecimal getValorIntMora() {
        return valorIntMora;
    }

    /**
     * @param valorIntMora
     *        the valorIntMora to set
     */
    public void setValorIntMora(BigDecimal valorIntMora) {
        this.valorIntMora = valorIntMora;
    }

    /**
     * @return the outMarcaSucursalPILA
     */
    public Boolean getOutMarcaSucursalPILA() {
        return outMarcaSucursalPILA;
    }

    /**
     * @param outMarcaSucursalPILA
     *        the outMarcaSucursalPILA to set
     */
    public void setOutMarcaSucursalPILA(Boolean outMarcaSucursalPILA) {
        this.outMarcaSucursalPILA = outMarcaSucursalPILA;
    }

    /**
     * @return the outCodSucursalPrincipal
     */
    public String getOutCodSucursalPrincipal() {
        return outCodSucursalPrincipal;
    }

    /**
     * @param outCodSucursalPrincipal
     *        the outCodSucursalPrincipal to set
     */
    public void setOutCodSucursalPrincipal(String outCodSucursalPrincipal) {
        this.outCodSucursalPrincipal = outCodSucursalPrincipal;
    }

    /**
     * @return the outNomSucursalPrincipal
     */
    public String getOutNomSucursalPrincipal() {
        return outNomSucursalPrincipal;
    }

    /**
     * @param outNomSucursalPrincipal
     *        the outNomSucursalPrincipal to set
     */
    public void setOutNomSucursalPrincipal(String outNomSucursalPrincipal) {
        this.outNomSucursalPrincipal = outNomSucursalPrincipal;
    }

    /**
     * @return the numPlanilla
     */
    public String getNumPlanilla() {
        return numPlanilla;
    }

    /**
     * @param numPlanilla the numPlanilla to set
     */
    public void setNumPlanilla(String numPlanilla) {
        this.numPlanilla = numPlanilla;
    }

    /**
     * Método que retorna el valor de direccion.
     * @return valor de direccion.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Método encargado de modificar el valor de direccion.
     * @param valor para modificar direccion.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Método que retorna el valor de codCiudad.
     * @return valor de codCiudad.
     */
    public String getCodCiudad() {
        return codCiudad;
    }

    /**
     * Método encargado de modificar el valor de codCiudad.
     * @param valor para modificar codCiudad.
     */
    public void setCodCiudad(String codCiudad) {
        this.codCiudad = codCiudad;
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
     * @param valor para modificar codDepartamento.
     */
    public void setCodDepartamento(String codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    /**
     * Método que retorna el valor de telefono.
     * @return valor de telefono.
     */
    public Long getTelefono() {
        return telefono;
    }

    /**
     * Método encargado de modificar el valor de telefono.
     * @param valor para modificar telefono.
     */
    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    /**
     * Método que retorna el valor de fax.
     * @return valor de fax.
     */
    public Long getFax() {
        return fax;
    }

    /**
     * Método encargado de modificar el valor de fax.
     * @param valor para modificar fax.
     */
    public void setFax(Long fax) {
        this.fax = fax;
    }

    /**
     * Método que retorna el valor de email.
     * @return valor de email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método encargado de modificar el valor de email.
     * @param valor para modificar email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método que retorna el valor de naturalezaJuridica.
     * @return valor de naturalezaJuridica.
     */
    public Short getNaturalezaJuridica() {
        return naturalezaJuridica;
    }

    /**
     * Método encargado de modificar el valor de naturalezaJuridica.
     * @param valor para modificar naturalezaJuridica.
     */
    public void setNaturalezaJuridica(Short naturalezaJuridica) {
        this.naturalezaJuridica = naturalezaJuridica;
    }

    /**
     * Método que retorna el valor de fechaMatricula.
     * @return valor de fechaMatricula.
     */
    public Long getFechaMatricula() {
        return fechaMatricula;
    }

    /**
     * Método encargado de modificar el valor de fechaMatricula.
     * @param valor para modificar fechaMatricula.
     */
    public void setFechaMatricula(Long fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    /**
     * @return the outEnviadoAFiscalizacion
     */
    public Boolean getOutEnviadoAFiscalizacion() {
        return outEnviadoAFiscalizacion;
    }

    /**
     * @param outEnviadoAFiscalizacion the outEnviadoAFiscalizacion to set
     */
    public void setOutEnviadoAFiscalizacion(Boolean outEnviadoAFiscalizacion) {
        this.outEnviadoAFiscalizacion = outEnviadoAFiscalizacion;
    }

    /**
     * @return the outMotivoFiscalizacion
     */
    public MotivoFiscalizacionAportanteEnum getOutMotivoFiscalizacion() {
        return outMotivoFiscalizacion;
    }

    /**
     * @param outMotivoFiscalizacion the outMotivoFiscalizacion to set
     */
    public void setOutMotivoFiscalizacion(MotivoFiscalizacionAportanteEnum outMotivoFiscalizacion) {
        this.outMotivoFiscalizacion = outMotivoFiscalizacion;
    }

    /**
     * @return the numPlanillaAsociada
     */
    public String getNumPlanillaAsociada() {
        return numPlanillaAsociada;
    }

    /**
     * @param numPlanillaAsociada the numPlanillaAsociada to set
     */
    public void setNumPlanillaAsociada(String numPlanillaAsociada) {
        this.numPlanillaAsociada = numPlanillaAsociada;
    }

    /**
     * @return the outMotivoProcesoManual
     */
    public MotivoProcesoPilaManualEnum getOutMotivoProcesoManual() {
        return outMotivoProcesoManual;
    }

    /**
     * @param outMotivoProcesoManual the outMotivoProcesoManual to set
     */
    public void setOutMotivoProcesoManual(MotivoProcesoPilaManualEnum outMotivoProcesoManual) {
        this.outMotivoProcesoManual = outMotivoProcesoManual;
    }

    /**
     * @return the outPrimerNombreAportante
     */
    public String getOutPrimerNombreAportante() {
        return outPrimerNombreAportante;
    }

    /**
     * @param outPrimerNombreAportante the outPrimerNombreAportante to set
     */
    public void setOutPrimerNombreAportante(String outPrimerNombreAportante) {
        this.outPrimerNombreAportante = outPrimerNombreAportante;
    }

    /**
     * @return the outSegundoNombreAportante
     */
    public String getOutSegundoNombreAportante() {
        return outSegundoNombreAportante;
    }

    /**
     * @param outSegundoNombreAportante the outSegundoNombreAportante to set
     */
    public void setOutSegundoNombreAportante(String outSegundoNombreAportante) {
        this.outSegundoNombreAportante = outSegundoNombreAportante;
    }

    /**
     * @return the outPrimerApellidoAportante
     */
    public String getOutPrimerApellidoAportante() {
        return outPrimerApellidoAportante;
    }

    /**
     * @param outPrimerApellidoAportante the outPrimerApellidoAportante to set
     */
    public void setOutPrimerApellidoAportante(String outPrimerApellidoAportante) {
        this.outPrimerApellidoAportante = outPrimerApellidoAportante;
    }

    /**
     * @return the outSegundoApellidoAportante
     */
    public String getOutSegundoApellidoAportante() {
        return outSegundoApellidoAportante;
    }

    /**
     * @param outSegundoApellidoAportante the outSegundoApellidoAportante to set
     */
    public void setOutSegundoApellidoAportante(String outSegundoApellidoAportante) {
        this.outSegundoApellidoAportante = outSegundoApellidoAportante;
    }

    /**
     * @return the diasMora
     */
    public Short getDiasMora() {
        return diasMora;
    }

    /**
     * @param diasMora the diasMora to set
     */
    public void setDiasMora(Short diasMora) {
        this.diasMora = diasMora;
    }

    /**
     * @return the fechaPlanilla
     */
    public Date getFechaPlanilla() {
        return fechaPlanilla;
    }

    /**
     * @param fechaPlanilla the fechaPlanilla to set
     */
    public void setFechaPlanilla(Date fechaPlanilla) {
        this.fechaPlanilla = fechaPlanilla;
    }

    /**
     * @return the formaPresentacion
     */
    public String getFormaPresentacion() {
        return formaPresentacion;
    }

    /**
     * @param formaPresentacion the formaPresentacion to set
     */
    public void setFormaPresentacion(String formaPresentacion) {
        this.formaPresentacion = formaPresentacion;
    }

    /**
     * @return the cantEmpleados
     */
    public Integer getCantEmpleados() {
        return cantEmpleados;
    }

    /**
     * @param cantEmpleados the cantEmpleados to set
     */
    public void setCantEmpleados(Integer cantEmpleados) {
        this.cantEmpleados = cantEmpleados;
    }

    /**
     * @return the cantAfiliados
     */
    public Integer getCantAfiliados() {
        return cantAfiliados;
    }

    /**
     * @param cantAfiliados the cantAfiliados to set
     */
    public void setCantAfiliados(Integer cantAfiliados) {
        this.cantAfiliados = cantAfiliados;
    }

    /**
     * @return the tipoPersona
     */
    public String getTipoPersona() {
        return tipoPersona;
    }

    /**
     * @param tipoPersona the tipoPersona to set
     */
    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    /**
     * @return the outEnProceso
     */
    public Boolean getOutEnProceso() {
        return outEnProceso;
    }

    /**
     * @param outEnProceso the outEnProceso to set
     */
    public void setOutEnProceso(Boolean outEnProceso) {
        this.outEnProceso = outEnProceso;
    }

    /**
     * @return the cantidadReg2
     */
    public Integer getCantidadReg2() {
        return cantidadReg2;
    }

    /**
     * @param cantidadReg2 the cantidadReg2 to set
     */
    public void setCantidadReg2(Integer cantidadReg2) {
        this.cantidadReg2 = cantidadReg2;
    }

    /**
     * @return the fechaPagoPlanillaAsociada
     */
    public Date getFechaPagoPlanillaAsociada() {
        return fechaPagoPlanillaAsociada;
    }

    /**
     * @param fechaPagoPlanillaAsociada the fechaPagoPlanillaAsociada to set
     */
    public void setFechaPagoPlanillaAsociada(Date fechaPagoPlanillaAsociada) {
        this.fechaPagoPlanillaAsociada = fechaPagoPlanillaAsociada;
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
	 * @return the enProcesamiento
	 */
	public Boolean getEnProcesamiento() {
		return enProcesamiento;
	}

	/**
	 * @param enProcesamiento the enProcesamiento to set
	 */
	public void setEnProcesamiento(Boolean enProcesamiento) {
		this.enProcesamiento = enProcesamiento;
	}

	/**
	 * @return the enBandejaTransitoria
	 */
	public Boolean getEnBandejaTransitoria() {
		return enBandejaTransitoria;
	}

	/**
	 * @param enBandejaTransitoria the enBandejaTransitoria to set
	 */
	public void setEnBandejaTransitoria(Boolean enBandejaTransitoria) {
		this.enBandejaTransitoria = enBandejaTransitoria;
	}



    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", transaccion='" + getTransaccion() + "'" +
            ", esAportePensionados='" + getEsAportePensionados() + "'" +
            ", nombreAportante='" + getNombreAportante() + "'" +
            ", tipoIdentificacionAportante='" + getTipoIdentificacionAportante() + "'" +
            ", numeroIdentificacionAportante='" + getNumeroIdentificacionAportante() + "'" +
            ", digVerAportante='" + getDigVerAportante() + "'" +
            ", periodoAporte='" + getPeriodoAporte() + "'" +
            ", tipoPlanilla='" + getTipoPlanilla() + "'" +
            ", claseAportante='" + getClaseAportante() + "'" +
            ", codSucursal='" + getCodSucursal() + "'" +
            ", nomSucursal='" + getNomSucursal() + "'" +
            ", cantPensionados='" + getCantPensionados() + "'" +
            ", modalidadPlanilla='" + getModalidadPlanilla() + "'" +
            ", valTotalApoObligatorio='" + getValTotalApoObligatorio() + "'" +
            ", valorIntMora='" + getValorIntMora() + "'" +
            ", fechaRecaudo='" + getFechaRecaudo() + "'" +
            ", codigoEntidadFinanciera='" + getCodigoEntidadFinanciera() + "'" +
            ", operadorInformacion='" + getOperadorInformacion() + "'" +
            ", numeroCuenta='" + getNumeroCuenta() + "'" +
            ", fechaActualizacion='" + getFechaActualizacion() + "'" +
            ", registroControl='" + getRegistroControl() + "'" +
            ", registroControlManual='" + getRegistroControlManual() + "'" +
            ", registroFControl='" + getRegistroFControl() + "'" +
            ", outTarifaEmpleador='" + getOutTarifaEmpleador() + "'" +
            ", outFinalizadoProcesoManual='" + getOutFinalizadoProcesoManual() + "'" +
            ", outEsEmpleador='" + getOutEsEmpleador() + "'" +
            ", outEstadoEmpleador='" + getOutEstadoEmpleador() + "'" +
            ", outTipoBeneficio='" + getOutTipoBeneficio() + "'" +
            ", outBeneficioActivo='" + getOutBeneficioActivo() + "'" +
            ", outEsEmpleadorReintegrable='" + getOutEsEmpleadorReintegrable() + "'" +
            ", outEstadoArchivo='" + getOutEstadoArchivo() + "'" +
            ", numPlanilla='" + getNumPlanilla() + "'" +
            ", esSimulado='" + getEsSimulado() + "'" +
            ", estadoEvaluacion='" + getEstadoEvaluacion() + "'" +
            ", outMarcaSucursalPILA='" + getOutMarcaSucursalPILA() + "'" +
            ", outCodSucursalPrincipal='" + getOutCodSucursalPrincipal() + "'" +
            ", outNomSucursalPrincipal='" + getOutNomSucursalPrincipal() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", codCiudad='" + getCodCiudad() + "'" +
            ", codDepartamento='" + getCodDepartamento() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", naturalezaJuridica='" + getNaturalezaJuridica() + "'" +
            ", fechaMatricula='" + getFechaMatricula() + "'" +
            ", outEnviadoAFiscalizacion='" + getOutEnviadoAFiscalizacion() + "'" +
            ", outMotivoFiscalizacion='" + getOutMotivoFiscalizacion() + "'" +
            ", numPlanillaAsociada='" + getNumPlanillaAsociada() + "'" +
            ", outMotivoProcesoManual='" + getOutMotivoProcesoManual() + "'" +
            ", outPrimerNombreAportante='" + getOutPrimerNombreAportante() + "'" +
            ", outSegundoNombreAportante='" + getOutSegundoNombreAportante() + "'" +
            ", outPrimerApellidoAportante='" + getOutPrimerApellidoAportante() + "'" +
            ", outSegundoApellidoAportante='" + getOutSegundoApellidoAportante() + "'" +
            ", diasMora='" + getDiasMora() + "'" +
            ", fechaPlanilla='" + getFechaPlanilla() + "'" +
            ", formaPresentacion='" + getFormaPresentacion() + "'" +
            ", cantEmpleados='" + getCantEmpleados() + "'" +
            ", cantAfiliados='" + getCantAfiliados() + "'" +
            ", cuentaBancariaRecaudo='" + getCuentaBancariaRecaudo() + "'" +
            ", tipoPersona='" + getTipoPersona() + "'" +
            ", outEnProceso='" + getOutEnProceso() + "'" +
            ", cantidadReg2='" + getCantidadReg2() + "'" +
            ", fechaPagoPlanillaAsociada='" + getFechaPagoPlanillaAsociada() + "'" +
            ", outRegistroActual='" + getOutRegistroActual() + "'" +
            ", outRegInicial='" + getOutRegInicial() + "'" +
            ", enProcesamiento='" + getEnProcesamiento() + "'" +
            ", enBandejaTransitoria='" + getEnBandejaTransitoria() + "'" +
            "}";
    }
    
}
