package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.EntidadPagadora;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.entidades.transversal.personas.AFP;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.cartera.MotivoFiscalizacionAportanteEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import com.asopagos.enumeraciones.core.ExpulsionEnum;
import com.asopagos.enumeraciones.core.TipoContratoEnum;
import com.asopagos.enumeraciones.core.TipoSalarioEnum;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO que contiene los campos de los roles de un afiliado.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class RolAfiliadoModeloDTO implements Serializable {
	/**
	 * Código identificador de llave primaria del rol afiliado 
	 */
    private Long idRolAfiliado;

    /**
     * Id que identifica al afiliado asociada al rol afiliado
     */
    private AfiliadoModeloDTO afiliado;

    /**
     * Descripción del sujeto trámite de tipo afiliado
     */
    private TipoAfiliadoEnum tipoAfiliado;

    /**
     * Descripción del estado del rol afiliado
     */
    private EstadoAfiliadoEnum estadoAfiliado;

    /**
     * Descripción del estado activo/inactivo de la entidad pagadora
     */
    private EstadoActivoInactivoEnum estadoEnEntidadPagadora;

    /**
     * Descripción de las posibles clases de personas independientes
     */
    private ClaseIndependienteEnum claseIndependiente;

	/**
	 * Id que identifica al empleador asociada al rol afiliado
	 */
    private Long idEmpleador;

    /**
     * Id que identifica a la sucursal empleador asociada al rol afiliado
     */
    private Long idSucursalEmpleador;
    
    /**
     * Id que identifica a la administradora de fondo de pensiones asociada al rol afiliado
     */
    private AFP pagadorPension;    
    
    /**
     * Id que identifica al entidad pagadora asociada al rol afiliado
     */
    private EntidadPagadora pagadorAportes;
    
    /**
     * Código identificador ante entidad pagadora, aplica para cuando la persona es tipo solicitante :PENSIONADO
     */
    private String identificadorAnteEntidadPagadora;    

    /**
     * Fecha de ingreso
     */
    private Long fechaIngreso;

    /**
     * Fecha de retiro
     */
    private Long fechaRetiro;

    /**
     * Valor del salario de la mesada de ingresos
     */
    private BigDecimal valorSalarioMesadaIngresos;

    /**
     * Porcentaje del pago de los aportes
     */
    private BigDecimal porcentajePagoAportes;

    /**
     * Descripción de las posibles clases de personas independientes
     */
    private ClaseTrabajadorEnum claseTrabajador;

    /**
     * Descripción del tipo de salario del rol afiliado
     */
    private TipoSalarioEnum tipoSalario;

    /**
     * Descripción del tipo de contrato del rol afiliado     	
     */
    private TipoContratoEnum tipoContrato;

    /**
     * Descripción del cargo del afiliado
     */
    private String cargo;

    /**
     * Número de horas laboradas por mes del rol afiliado
     */
    private Short horasLaboradasMes;

    /**
     * Fecha de la afiliación
     */
    private Long fechaAfiliacion;
    
    /**
     * Descripción de la desafiliacion del afiliado
     */
    private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion;
    
    /**
     * Indicador S/N el motivo de afiliacion es sustitucionPatronal [S=Si N=No]
     */
	private Boolean sustitucionPatronal;
	
	/**
	 * Fecha vigencia del pagador de aportes.
	 */
	private Long fechaFinPagadorAportes;
	
	/**
	 * Fecha vigencia del pagador de pensiones.
	 */
	private Long fechaFinPagadorPension;
	
	/**
     * Descripción del estado activo/inactivo de la entidad pagadora
     */
    private EstadoActivoInactivoEnum estadoEnEntidadPagadoraPensionado;
    
    /**
     * Día hábil de vencimiento de aportes 
     * */
    private Short diaHabilVencimientoAporte;
    
    /**
     * Marca que permite saber si el empleador
     * esta en proceso de desafiliacion
     */
    private ExpulsionEnum marcaExpulsion;
    
    /**
     * Marca de empresa para proceso de fiscalización de aportes
     */
    private Boolean enviadoAFiscalizacion;
    
    /**
     * Motivo de la marca de envío a fiscalización
     */
    private MotivoFiscalizacionAportanteEnum motivoFiscalizacion;

    /**
     * Fecha de fiscalización de la empresa
     */
    private Long fechaFiscalizacion;
    
    /**
     * Oportunidad de pago
     */
    private PeriodoPagoPlanillaEnum oportunidadPago;
    
    /**
     * Canal de recepción de novedad o solicitud de reingreso
     * */
    private CanalRecepcionEnum canalReingreso;
    
    /**
     * Referencia al aporte detallado que incluyó la novedad de reingreso
     * */
    private Long referenciaAporteReingreso;
    
    /**
     * Referencia a la solicitud de reingreso en afiliaciones 
     * */
    private Long referenciaSolicitudReingreso;
    
    /**
     * Fecha fin contrato (por mantis 0246012)
     */
    private Long fechaFinContrato;

    // El código anterior declara una variable de instancia privada llamada "fechaInicioCondicionVet"
    // de tipo Fecha.
    private Date fechaInicioCondicionVet;

    // El código anterior declara una variable de instancia privada llamada "fechaFinCondicionVet" de
    // tipo Fecha.
    private Date fechaFinCondicionVet;

    private CanalRecepcionEnum canalRecepcion;
    
	/**
	 * Método encargado de convertir de una entidad RolAfiliado a RolAfiliadoDTO
	 * @param rolAfiliado entidad rol afiliado.
	 */
	public void convertToDTO(RolAfiliado rolAfiliado, PersonaDetalle personaDetalle) {
		AfiliadoModeloDTO afiliadoDTO = new AfiliadoModeloDTO();
		afiliadoDTO.convertToDTO(rolAfiliado.getAfiliado(),personaDetalle);
		this.setAfiliado(afiliadoDTO);
		if (rolAfiliado.getEmpleador() != null && rolAfiliado.getEmpleador().getIdEmpleador() != null) {
			this.setIdEmpleador(rolAfiliado.getEmpleador().getIdEmpleador());
		}
		this.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
		this.setSustitucionPatronal(rolAfiliado.getSustitucionPatronal());
		this.setCargo(rolAfiliado.getCargo());
		this.setClaseIndependiente(rolAfiliado.getClaseIndependiente());
		this.setClaseTrabajador(rolAfiliado.getClaseTrabajador());
		this.setEstadoAfiliado(rolAfiliado.getEstadoAfiliado());
		this.setEstadoEnEntidadPagadora(rolAfiliado.getEstadoEnEntidadPagadora());
		if (rolAfiliado.getFechaAfiliacion() != null) {
			this.setFechaAfiliacion(rolAfiliado.getFechaAfiliacion().getTime());
		}
		if (rolAfiliado.getFechaIngreso() != null) {
			this.setFechaIngreso(rolAfiliado.getFechaIngreso().getTime());
		}
		if (rolAfiliado.getFechaRetiro() != null) {
			this.setFechaRetiro(rolAfiliado.getFechaRetiro().getTime());
		}
		this.setHorasLaboradasMes(rolAfiliado.getHorasLaboradasMes());
		this.setIdentificadorAnteEntidadPagadora(rolAfiliado.getIdentificadorAnteEntidadPagadora());
		this.setMotivoDesafiliacion(rolAfiliado.getMotivoDesafiliacion());
		if(rolAfiliado.getPagadorAportes()!=null && rolAfiliado.getPagadorAportes().getIdEntidadPagadora()!=null){
			EntidadPagadora entidadPagadora = new EntidadPagadora();
			entidadPagadora.setIdEntidadPagadora(rolAfiliado.getPagadorAportes().getIdEntidadPagadora());
			this.setPagadorAportes(entidadPagadora);
		}
		if(rolAfiliado.getPagadorPension()!=null&& rolAfiliado.getPagadorPension().getIdAFP()!=null){
			AFP afp = new AFP();
			afp.setIdAFP(rolAfiliado.getPagadorPension().getIdAFP());
			this.setPagadorPension(afp);
		}
		this.setPorcentajePagoAportes(rolAfiliado.getPorcentajePagoAportes());
		this.setTipoAfiliado(rolAfiliado.getTipoAfiliado());
		this.setTipoContrato(rolAfiliado.getTipoContrato());
		this.setTipoSalario(rolAfiliado.getTipoSalario());
		this.setValorSalarioMesadaIngresos(rolAfiliado.getValorSalarioMesadaIngresos());
		if (rolAfiliado.getSucursalEmpleador() != null && rolAfiliado.getSucursalEmpleador().getIdSucursalEmpresa() != null) {
			this.setIdSucursalEmpleador(rolAfiliado.getSucursalEmpleador().getIdSucursalEmpresa());
		}
		if (rolAfiliado.getFechaFinPagadorAportes() != null) {
			this.setFechaFinPagadorAportes(rolAfiliado.getFechaFinPagadorAportes().getTime());
		}
		if (rolAfiliado.getFechaFinPagadorPension() != null) {
			this.setFechaFinPagadorPension(rolAfiliado.getFechaFinPagadorPension().getTime());
		}
		this.setEstadoEnEntidadPagadoraPensionado(rolAfiliado.getEstadoEnEntidadPagadoraPension());
		this.setDiaHabilVencimientoAporte(rolAfiliado.getDiaHabilVencimientoAporte());
		this.setMarcaExpulsion(rolAfiliado.getMarcaExpulsion());
		this.setEnviadoAFiscalizacion(rolAfiliado.getEnviadoAFiscalizacion());
		this.setMotivoFiscalizacion(rolAfiliado.getMotivoFiscalizacion());
		if(rolAfiliado.getFechaFiscalizacion()!= null){
		    this.setFechaFiscalizacion(rolAfiliado.getFechaFiscalizacion().getTime());
		}
		this.setOportunidadPago(rolAfiliado.getOportunidadPago());
		this.setCanalReingreso(rolAfiliado.getCanalReingreso());
		this.setReferenciaAporteReingreso(rolAfiliado.getReferenciaAporteReingreso());
		this.setReferenciaSolicitudReingreso(rolAfiliado.getReferenciaSolicitudReingreso());
		if (rolAfiliado.getFechaFinContrato() != null) {
            this.setFechaFinContrato(rolAfiliado.getFechaFinContrato().getTime());
        }
        if(rolAfiliado.getFechaInicioCondicionVet()!=null){
            this.setFechaInicioCondicionVet(rolAfiliado.getFechaInicioCondicionVet());
        }
        if(rolAfiliado.getFechaFinCondicionVet() != null){
            this.setFechaFinCondicionVet(rolAfiliado.getFechaFinCondicionVet());
        }
	}
    
    /**
     * Método encargado de convertir de una entidad RolAfiliado a RolAfiliadoDTO (simple)
     * @param rolAfiliado entidad rol afiliado.
     */
    public void convertToDTO(RolAfiliado rolAfiliado) {
        if (rolAfiliado.getEmpleador() != null && rolAfiliado.getEmpleador().getIdEmpleador() != null) {
            this.setIdEmpleador(rolAfiliado.getEmpleador().getIdEmpleador());
        }
        this.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
        this.setSustitucionPatronal(rolAfiliado.getSustitucionPatronal());
        this.setCargo(rolAfiliado.getCargo());
        this.setClaseIndependiente(rolAfiliado.getClaseIndependiente());
        this.setClaseTrabajador(rolAfiliado.getClaseTrabajador());
        this.setEstadoAfiliado(rolAfiliado.getEstadoAfiliado());
        this.setEstadoEnEntidadPagadora(rolAfiliado.getEstadoEnEntidadPagadora());
        if (rolAfiliado.getFechaAfiliacion() != null) {
            this.setFechaAfiliacion(rolAfiliado.getFechaAfiliacion().getTime());
        }
        if (rolAfiliado.getFechaIngreso() != null) {
            this.setFechaIngreso(rolAfiliado.getFechaIngreso().getTime());
        }
        if (rolAfiliado.getFechaRetiro() != null) {
            this.setFechaRetiro(rolAfiliado.getFechaRetiro().getTime());
        }
        this.setHorasLaboradasMes(rolAfiliado.getHorasLaboradasMes());
        this.setIdentificadorAnteEntidadPagadora(rolAfiliado.getIdentificadorAnteEntidadPagadora());
        this.setMotivoDesafiliacion(rolAfiliado.getMotivoDesafiliacion());
        if(rolAfiliado.getPagadorAportes()!=null && rolAfiliado.getPagadorAportes().getIdEntidadPagadora()!=null){
            EntidadPagadora entidadPagadora = new EntidadPagadora();
            entidadPagadora.setIdEntidadPagadora(rolAfiliado.getPagadorAportes().getIdEntidadPagadora());
            this.setPagadorAportes(entidadPagadora);
        }
        if(rolAfiliado.getPagadorPension()!=null&& rolAfiliado.getPagadorPension().getIdAFP()!=null){
            AFP afp = new AFP();
            afp.setIdAFP(rolAfiliado.getPagadorPension().getIdAFP());
            this.setPagadorPension(afp);
        }
        this.setPorcentajePagoAportes(rolAfiliado.getPorcentajePagoAportes());
        this.setTipoAfiliado(rolAfiliado.getTipoAfiliado());
        this.setTipoContrato(rolAfiliado.getTipoContrato());
        this.setTipoSalario(rolAfiliado.getTipoSalario());
        this.setValorSalarioMesadaIngresos(rolAfiliado.getValorSalarioMesadaIngresos());
        if (rolAfiliado.getSucursalEmpleador() != null && rolAfiliado.getSucursalEmpleador().getIdSucursalEmpresa() != null) {
            this.setIdSucursalEmpleador(rolAfiliado.getSucursalEmpleador().getIdSucursalEmpresa());
        }
        if (rolAfiliado.getFechaFinPagadorAportes() != null) {
            this.setFechaFinPagadorAportes(rolAfiliado.getFechaFinPagadorAportes().getTime());
        }
        if (rolAfiliado.getFechaFinPagadorPension() != null) {
            this.setFechaFinPagadorPension(rolAfiliado.getFechaFinPagadorPension().getTime());
        }
        this.setEstadoEnEntidadPagadoraPensionado(rolAfiliado.getEstadoEnEntidadPagadoraPension());
        this.setDiaHabilVencimientoAporte(rolAfiliado.getDiaHabilVencimientoAporte());
        this.setMarcaExpulsion(rolAfiliado.getMarcaExpulsion());
        this.setEnviadoAFiscalizacion(rolAfiliado.getEnviadoAFiscalizacion());
        this.setMotivoFiscalizacion(rolAfiliado.getMotivoFiscalizacion());
        if(rolAfiliado.getFechaFiscalizacion()!= null){
            this.setFechaFiscalizacion(rolAfiliado.getFechaFiscalizacion().getTime());
        }
        this.setOportunidadPago(rolAfiliado.getOportunidadPago());
        this.setCanalReingreso(rolAfiliado.getCanalReingreso());
        this.setReferenciaAporteReingreso(rolAfiliado.getReferenciaAporteReingreso());
        this.setReferenciaSolicitudReingreso(rolAfiliado.getReferenciaSolicitudReingreso());
        if (rolAfiliado.getFechaFinContrato() != null) {
            this.setFechaFinContrato(rolAfiliado.getFechaFinContrato().getTime());
        }
        if(rolAfiliado.getFechaInicioCondicionVet()!=null){
            this.setFechaInicioCondicionVet(rolAfiliado.getFechaInicioCondicionVet());
        }
        if(rolAfiliado.getFechaFinCondicionVet() != null){
            this.setFechaFinCondicionVet(rolAfiliado.getFechaFinCondicionVet());
        }
    }
	/**
	 * Método encargado de convertir un RolAfiliadoDTO a una entidad RolAfiliado
	 * @return entidad rol afiliado.
	 */
	public RolAfiliado convertToEntity() {
		RolAfiliado rolAfiliado = new RolAfiliado();
		rolAfiliado.setIdRolAfiliado(this.getIdRolAfiliado());
		rolAfiliado.setAfiliado(this.getAfiliado().convertToEntity());
		rolAfiliado.setSustitucionPatronal(this.getSustitucionPatronal());
		rolAfiliado.setCargo(this.getCargo());
		rolAfiliado.setClaseIndependiente(this.getClaseIndependiente());
		rolAfiliado.setClaseTrabajador(this.getClaseTrabajador());
		if (this.getIdEmpleador() != null) {
			Empleador empleador = new Empleador();
			empleador.setIdEmpleador(this.getIdEmpleador());
			rolAfiliado.setEmpleador(empleador);
		}
		rolAfiliado.setEstadoAfiliado(this.getEstadoAfiliado());
		rolAfiliado.setEstadoEnEntidadPagadora(this.getEstadoEnEntidadPagadora());
		if (this.getFechaAfiliacion() != null) {
			rolAfiliado.setFechaAfiliacion(new Date(this.getFechaAfiliacion()));
		}
		if (this.getFechaIngreso() != null) {
			rolAfiliado.setFechaIngreso(new Date(this.getFechaIngreso()));
		}
		if (this.getFechaRetiro() != null) {
			rolAfiliado.setFechaRetiro(new Date(this.getFechaRetiro()));
		}
		rolAfiliado.setHorasLaboradasMes(this.getHorasLaboradasMes());
		rolAfiliado.setIdentificadorAnteEntidadPagadora(this.getIdentificadorAnteEntidadPagadora());
		rolAfiliado.setMotivoDesafiliacion(this.getMotivoDesafiliacion());
		rolAfiliado.setPagadorAportes(this.getPagadorAportes());
		rolAfiliado.setPagadorPension(this.getPagadorPension());
		rolAfiliado.setPorcentajePagoAportes(this.getPorcentajePagoAportes());
		rolAfiliado.setTipoAfiliado(this.getTipoAfiliado());
		rolAfiliado.setTipoContrato(this.getTipoContrato());
		rolAfiliado.setTipoSalario(this.getTipoSalario());
		rolAfiliado.setValorSalarioMesadaIngresos(this.getValorSalarioMesadaIngresos());
		if (this.getIdSucursalEmpleador() != null) {
			SucursalEmpresa sucursalEmpresa = new SucursalEmpresa();
			sucursalEmpresa.setIdSucursalEmpresa(this.getIdSucursalEmpleador());
			rolAfiliado.setSucursalEmpleador(sucursalEmpresa);
		}
		if (this.getFechaFinPagadorAportes() != null) {
			rolAfiliado.setFechaFinPagadorAportes(new Date(this.getFechaFinPagadorAportes()));
		}
		if (this.getFechaFinPagadorPension() != null) {
			rolAfiliado.setFechaFinPagadorPension(new Date(this.getFechaFinPagadorPension()));
		}
		rolAfiliado.setEstadoEnEntidadPagadoraPension(this.getEstadoEnEntidadPagadoraPensionado());
		
		rolAfiliado.setDiaHabilVencimientoAporte(this.getDiaHabilVencimientoAporte());
		rolAfiliado.setMarcaExpulsion(this.getMarcaExpulsion());
		rolAfiliado.setEnviadoAFiscalizacion(this.getEnviadoAFiscalizacion());
		rolAfiliado.setMotivoFiscalizacion(this.getMotivoFiscalizacion());
	    if(this.getFechaFiscalizacion()!= null){
	        rolAfiliado.setFechaFiscalizacion(new Date(this.getFechaFiscalizacion()));
	    }
        rolAfiliado.setOportunidadPago(this.getOportunidadPago());
        rolAfiliado.setCanalReingreso(this.getCanalReingreso());
        rolAfiliado.setReferenciaAporteReingreso(this.getReferenciaAporteReingreso());
        rolAfiliado.setReferenciaSolicitudReingreso(this.getReferenciaSolicitudReingreso());
        if (this.getFechaFinContrato() != null) {
            rolAfiliado.setFechaFinContrato(new Date(this.getFechaFinContrato()));
        }
        if(this.getFechaFinCondicionVet() != null){
            rolAfiliado.setFechaFinCondicionVet(this.getFechaFinCondicionVet());
        }
        if(this.getFechaInicioCondicionVet() != null){
            rolAfiliado.setFechaInicioCondicionVet(this.getFechaInicioCondicionVet());
        }
		return rolAfiliado;
	}

    /**
     * Método encargado de copiar el DTO a la entidad.
     * @param rolAfiliado
     *        entidad previamente consultada que se desea modificar
     */
    public void copyDTOToEntity(RolAfiliado rolAfiliado) {
        if (this.getAfiliado() != null) {
            rolAfiliado.setAfiliado(this.getAfiliado().copyDTOToEntiy(rolAfiliado.getAfiliado()));
        }
        if (this.getCanalReingreso() != null) {
            rolAfiliado.setCanalReingreso(this.getCanalReingreso());
        }
        if (this.getCargo() != null) {
            rolAfiliado.setCargo(this.getCargo());
        }
        if (this.getClaseIndependiente() != null) {
            rolAfiliado.setClaseIndependiente(this.getClaseIndependiente());
        }
        if (this.getClaseTrabajador() != null) {
            rolAfiliado.setClaseTrabajador(this.getClaseTrabajador());
        }
        if (this.getDiaHabilVencimientoAporte() != null) {
            rolAfiliado.setDiaHabilVencimientoAporte(this.getDiaHabilVencimientoAporte());
        }
        if (this.getEnviadoAFiscalizacion() != null) {
            rolAfiliado.setEnviadoAFiscalizacion(this.getEnviadoAFiscalizacion());
        }
        if (this.getEstadoAfiliado() != null) {
            rolAfiliado.setEstadoAfiliado(this.getEstadoAfiliado());
        }
        if (this.getEstadoEnEntidadPagadora() != null) {
            rolAfiliado.setEstadoEnEntidadPagadora(this.getEstadoEnEntidadPagadora());
        }
        if (this.getEstadoEnEntidadPagadoraPensionado() != null) {
            rolAfiliado.setEstadoEnEntidadPagadoraPension(this.getEstadoEnEntidadPagadoraPensionado());
        }
        if (this.getFechaAfiliacion() != null) {
            rolAfiliado.setFechaAfiliacion(new Date(this.getFechaAfiliacion()));
        }
        if (this.getFechaFinPagadorAportes() != null) {
            rolAfiliado.setFechaFinPagadorAportes(new Date(this.getFechaFinPagadorAportes()));
        }
        if (this.getFechaFinPagadorPension() != null) {
            rolAfiliado.setFechaFinPagadorPension(new Date(this.getFechaFinPagadorPension()));
        }
        if (this.getFechaFiscalizacion() != null) {
            rolAfiliado.setFechaFiscalizacion(new Date(this.getFechaFiscalizacion()));
        }
        if (this.getFechaIngreso() != null) {
            rolAfiliado.setFechaIngreso(new Date(this.getFechaIngreso()));
        }
        if (this.getFechaRetiro() != null) {
            rolAfiliado.setFechaRetiro(new Date(this.getFechaRetiro()));
        }
        if (this.getHorasLaboradasMes() != null) {
            rolAfiliado.setHorasLaboradasMes(this.getHorasLaboradasMes());
        }
        if (this.getIdEmpleador() != null) {
            rolAfiliado.getEmpleador().setIdEmpleador(this.getIdEmpleador());
        }
        if (this.getIdentificadorAnteEntidadPagadora() != null) {
            rolAfiliado.setIdentificadorAnteEntidadPagadora(this.getIdentificadorAnteEntidadPagadora());
        }
        if (this.getIdSucursalEmpleador() != null) {
            SucursalEmpresa sucursalEmpresa = new SucursalEmpresa();
            sucursalEmpresa.setIdSucursalEmpresa(this.getIdSucursalEmpleador());
            rolAfiliado.setSucursalEmpleador(sucursalEmpresa);
        }
        if (this.getMarcaExpulsion() != null) {
            rolAfiliado.setMarcaExpulsion(this.getMarcaExpulsion());
        }
        if (this.getMotivoDesafiliacion() != null) {
            rolAfiliado.setMotivoDesafiliacion(this.getMotivoDesafiliacion());
        }
        if (this.getMotivoFiscalizacion() != null) {
            rolAfiliado.setMotivoFiscalizacion(this.getMotivoFiscalizacion());
        }
        if (this.getOportunidadPago() != null) {
            rolAfiliado.setOportunidadPago(this.getOportunidadPago());
        }
        if (this.getPagadorAportes() != null) {
            rolAfiliado.setPagadorAportes(this.getPagadorAportes());
        }
        if (this.getPagadorPension() != null) {
            rolAfiliado.setPagadorPension(this.getPagadorPension());
        }
        if (this.getPorcentajePagoAportes() != null) {
            rolAfiliado.setPorcentajePagoAportes(this.getPorcentajePagoAportes());
        }
        if (this.getReferenciaAporteReingreso() != null) {
            rolAfiliado.setReferenciaAporteReingreso(this.getReferenciaAporteReingreso());
        }
        if (this.getReferenciaSolicitudReingreso() != null) {
            rolAfiliado.setReferenciaSolicitudReingreso(this.getReferenciaSolicitudReingreso());
        }
        if (this.getSustitucionPatronal() != null) {
            rolAfiliado.setSustitucionPatronal(this.getSustitucionPatronal());
        }
        if (this.getTipoAfiliado() != null) {
            rolAfiliado.setTipoAfiliado(this.getTipoAfiliado());
        }
        if (this.getTipoContrato() != null) {
            rolAfiliado.setTipoContrato(this.getTipoContrato());
        }
        if (this.getTipoSalario() != null) {
            rolAfiliado.setTipoSalario(this.getTipoSalario());
        }
        if (this.getValorSalarioMesadaIngresos() != null) {
            rolAfiliado.setValorSalarioMesadaIngresos(this.getValorSalarioMesadaIngresos());
        }
        if(this.getFechaFinCondicionVet() != null){
            rolAfiliado.setFechaFinCondicionVet(this.getFechaFinCondicionVet());
        }
        if(this.getFechaInicioCondicionVet()!=null){
            rolAfiliado.setFechaInicioCondicionVet(this.getFechaInicioCondicionVet());
        }
    }

    public RolAfiliadoModeloDTO(RolAfiliado rolAfiliado, PersonaDetalle perDet) {
        this.convertToDTO(rolAfiliado, perDet);
    }
    
    public RolAfiliadoModeloDTO(RolAfiliado rolAfiliado, Afiliado afiliado){
        super();
        this.convertToDTO(rolAfiliado);
        AfiliadoModeloDTO afiliadoDTO = new AfiliadoModeloDTO(afiliado);
        this.afiliado = afiliadoDTO;
    }

    public RolAfiliadoModeloDTO() {
    }

	/**
	 * Método que retorna el valor de idRolAfiliado.
	 * @return valor de idRolAfiliado.
	 */
	public Long getIdRolAfiliado() {
		return idRolAfiliado;
	}

    public Boolean isSustitucionPatronal() {
        return this.sustitucionPatronal;
    }

    public Boolean isEnviadoAFiscalizacion() {
        return this.enviadoAFiscalizacion;
    }

    public Date getFechaInicioCondicionVet() {
        return this.fechaInicioCondicionVet;
    }

    public void setFechaInicioCondicionVet(Date fechaInicioCondicionVet) {
        this.fechaInicioCondicionVet = fechaInicioCondicionVet;
    }

    public Date getFechaFinCondicionVet() {
        return this.fechaFinCondicionVet;
    }

    public void setFechaFinCondicionVet(Date fechaFinCondicionVet) {
        this.fechaFinCondicionVet = fechaFinCondicionVet;
    }

	/**
	 * Método encargado de modificar el valor de idRolAfiliado.
	 * @param valor para modificar idRolAfiliado.
	 */
	public void setIdRolAfiliado(Long idRolAfiliado) {
		this.idRolAfiliado = idRolAfiliado;
	}

	/**
	 * Método que retorna el valor de afiliado.
	 * @return valor de afiliado.
	 */
	public AfiliadoModeloDTO getAfiliado() {
		return afiliado;
	}

	/**
	 * Método encargado de modificar el valor de afiliado.
	 * @param valor para modificar afiliado.
	 */
	public void setAfiliado(AfiliadoModeloDTO afiliado) {
		this.afiliado = afiliado;
	}

	/**
	 * Método que retorna el valor de tipoAfiliado.
	 * @return valor de tipoAfiliado.
	 */
	public TipoAfiliadoEnum getTipoAfiliado() {
		return tipoAfiliado;
	}

	/**
	 * Método encargado de modificar el valor de tipoAfiliado.
	 * @param valor para modificar tipoAfiliado.
	 */
	public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
		this.tipoAfiliado = tipoAfiliado;
	}

	/**
	 * Método que retorna el valor de estadoAfiliado.
	 * @return valor de estadoAfiliado.
	 */
	public EstadoAfiliadoEnum getEstadoAfiliado() {
		return estadoAfiliado;
	}

	/**
	 * Método encargado de modificar el valor de estadoAfiliado.
	 * @param valor para modificar estadoAfiliado.
	 */
	public void setEstadoAfiliado(EstadoAfiliadoEnum estadoAfiliado) {
		this.estadoAfiliado = estadoAfiliado;
	}

	/**
	 * Método que retorna el valor de estadoEnEntidadPagadora.
	 * @return valor de estadoEnEntidadPagadora.
	 */
	public EstadoActivoInactivoEnum getEstadoEnEntidadPagadora() {
		return estadoEnEntidadPagadora;
	}

	/**
	 * Método encargado de modificar el valor de estadoEnEntidadPagadora.
	 * @param valor para modificar estadoEnEntidadPagadora.
	 */
	public void setEstadoEnEntidadPagadora(EstadoActivoInactivoEnum estadoEnEntidadPagadora) {
		this.estadoEnEntidadPagadora = estadoEnEntidadPagadora;
	}

	/**
	 * Método que retorna el valor de claseIndependiente.
	 * @return valor de claseIndependiente.
	 */
	public ClaseIndependienteEnum getClaseIndependiente() {
		return claseIndependiente;
	}

	/**
	 * Método encargado de modificar el valor de claseIndependiente.
	 * @param valor para modificar claseIndependiente.
	 */
	public void setClaseIndependiente(ClaseIndependienteEnum claseIndependiente) {
		this.claseIndependiente = claseIndependiente;
	}

	/**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }
    
    /**
     * @param idEmpleador the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }
    
    /**
     * @return the idSucursalEmpleador
     */
    public Long getIdSucursalEmpleador() {
        return idSucursalEmpleador;
    }
    
    /**
     * @param idSucursalEmpleador the idSucursalEmpleador to set
     */
    public void setIdSucursalEmpleador(Long idSucursalEmpleador) {
        this.idSucursalEmpleador = idSucursalEmpleador;
    }
    
    /**
	 * Método que retorna el valor de pagadorPension.
	 * @return valor de pagadorPension.
	 */
	public AFP getPagadorPension() {
		return pagadorPension;
	}

	/**
	 * Método encargado de modificar el valor de pagadorPension.
	 * @param valor para modificar pagadorPension.
	 */
	public void setPagadorPension(AFP pagadorPension) {
		this.pagadorPension = pagadorPension;
	}

	/**
	 * Método que retorna el valor de pagadorAportes.
	 * @return valor de pagadorAportes.
	 */
	public EntidadPagadora getPagadorAportes() {
		return pagadorAportes;
	}

	/**
	 * Método encargado de modificar el valor de pagadorAportes.
	 * @param valor para modificar pagadorAportes.
	 */
	public void setPagadorAportes(EntidadPagadora pagadorAportes) {
		this.pagadorAportes = pagadorAportes;
	}

	/**
	 * Método que retorna el valor de identificadorAnteEntidadPagadora.
	 * @return valor de identificadorAnteEntidadPagadora.
	 */
	public String getIdentificadorAnteEntidadPagadora() {
		return identificadorAnteEntidadPagadora;
	}

	/**
	 * Método encargado de modificar el valor de identificadorAnteEntidadPagadora.
	 * @param valor para modificar identificadorAnteEntidadPagadora.
	 */
	public void setIdentificadorAnteEntidadPagadora(String identificadorAnteEntidadPagadora) {
		this.identificadorAnteEntidadPagadora = identificadorAnteEntidadPagadora;
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
	 * @param valor para modificar fechaIngreso.
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
	 * @param valor para modificar fechaRetiro.
	 */
	public void setFechaRetiro(Long fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

	/**
	 * Método que retorna el valor de valorSalarioMesadaIngresos.
	 * @return valor de valorSalarioMesadaIngresos.
	 */
	public BigDecimal getValorSalarioMesadaIngresos() {
		return valorSalarioMesadaIngresos;
	}

	/**
	 * Método encargado de modificar el valor de valorSalarioMesadaIngresos.
	 * @param valor para modificar valorSalarioMesadaIngresos.
	 */
	public void setValorSalarioMesadaIngresos(BigDecimal valorSalarioMesadaIngresos) {
		this.valorSalarioMesadaIngresos = valorSalarioMesadaIngresos;
	}

	/**
	 * Método que retorna el valor de porcentajePagoAportes.
	 * @return valor de porcentajePagoAportes.
	 */
	public BigDecimal getPorcentajePagoAportes() {
		return porcentajePagoAportes;
	}

	/**
	 * Método encargado de modificar el valor de porcentajePagoAportes.
	 * @param valor para modificar porcentajePagoAportes.
	 */
	public void setPorcentajePagoAportes(BigDecimal porcentajePagoAportes) {
		this.porcentajePagoAportes = porcentajePagoAportes;
	}

	/**
	 * Método que retorna el valor de claseTrabajador.
	 * @return valor de claseTrabajador.
	 */
	public ClaseTrabajadorEnum getClaseTrabajador() {
		return claseTrabajador;
	}

	/**
	 * Método encargado de modificar el valor de claseTrabajador.
	 * @param valor para modificar claseTrabajador.
	 */
	public void setClaseTrabajador(ClaseTrabajadorEnum claseTrabajador) {
		this.claseTrabajador = claseTrabajador;
	}

	/**
	 * Método que retorna el valor de tipoSalario.
	 * @return valor de tipoSalario.
	 */
	public TipoSalarioEnum getTipoSalario() {
		return tipoSalario;
	}

	/**
	 * Método encargado de modificar el valor de tipoSalario.
	 * @param valor para modificar tipoSalario.
	 */
	public void setTipoSalario(TipoSalarioEnum tipoSalario) {
		this.tipoSalario = tipoSalario;
	}

	/**
	 * Método que retorna el valor de tipoContrato.
	 * @return valor de tipoContrato.
	 */
	public TipoContratoEnum getTipoContrato() {
		return tipoContrato;
	}

	/**
	 * Método encargado de modificar el valor de tipoContrato.
	 * @param valor para modificar tipoContrato.
	 */
	public void setTipoContrato(TipoContratoEnum tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	/**
	 * Método que retorna el valor de cargo.
	 * @return valor de cargo.
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * Método encargado de modificar el valor de cargo.
	 * @param valor para modificar cargo.
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * Método que retorna el valor de horasLaboradasMes.
	 * @return valor de horasLaboradasMes.
	 */
	public Short getHorasLaboradasMes() {
		return horasLaboradasMes;
	}

	/**
	 * Método encargado de modificar el valor de horasLaboradasMes.
	 * @param valor para modificar horasLaboradasMes.
	 */
	public void setHorasLaboradasMes(Short horasLaboradasMes) {
		this.horasLaboradasMes = horasLaboradasMes;
	}

	/**
	 * Método que retorna el valor de fechaAfiliacion.
	 * @return valor de fechaAfiliacion.
	 */
	public Long getFechaAfiliacion() {
		return fechaAfiliacion;
	}

	/**
	 * Método encargado de modificar el valor de fechaAfiliacion.
	 * @param valor para modificar fechaAfiliacion.
	 */
	public void setFechaAfiliacion(Long fechaAfiliacion) {
		this.fechaAfiliacion = fechaAfiliacion;
	}

	/**
	 * Método que retorna el valor de motivoDesafiliacion.
	 * @return valor de motivoDesafiliacion.
	 */
	public MotivoDesafiliacionAfiliadoEnum getMotivoDesafiliacion() {
		return motivoDesafiliacion;
	}

	/**
	 * Método encargado de modificar el valor de motivoDesafiliacion.
	 * @param valor para modificar motivoDesafiliacion.
	 */
	public void setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion) {
		this.motivoDesafiliacion = motivoDesafiliacion;
	}

	/**
	 * Método que retorna el valor de sustitucionPatronal.
	 * @return valor de sustitucionPatronal.
	 */
	public Boolean getSustitucionPatronal() {
		return sustitucionPatronal;
	}

	/**
	 * Método encargado de modificar el valor de sustitucionPatronal.
	 * @param valor para modificar sustitucionPatronal.
	 */
	public void setSustitucionPatronal(Boolean sustitucionPatronal) {
		this.sustitucionPatronal = sustitucionPatronal;
	}
	/**
	 * Método que retorna el valor de fechaFinPagadorAportes.
	 * @return valor de fechaFinPagadorAportes.
	 */
	public Long getFechaFinPagadorAportes() {
		return fechaFinPagadorAportes;
	}
	/**
	 * Método encargado de modificar el valor de fechaFinPagadorAportes.
	 * @param valor para modificar fechaFinPagadorAportes.
	 */
	public void setFechaFinPagadorAportes(Long fechaFinPagadorAportes) {
		this.fechaFinPagadorAportes = fechaFinPagadorAportes;
	}
	/**
	 * Método que retorna el valor de fechaFinPagadorPension.
	 * @return valor de fechaFinPagadorPension.
	 */
	public Long getFechaFinPagadorPension() {
		return fechaFinPagadorPension;
	}
	/**
	 * Método encargado de modificar el valor de fechaFinPagadorPension.
	 * @param valor para modificar fechaFinPagadorPension.
	 */
	public void setFechaFinPagadorPension(Long fechaFinPagadorPension) {
		this.fechaFinPagadorPension = fechaFinPagadorPension;
	}
	/**
	 * Método que retorna el valor de estadoEnEntidadPagadoraPensionado
	 * @return estadoEnEntidadPagadoraPensionado
	 */
	public EstadoActivoInactivoEnum getEstadoEnEntidadPagadoraPensionado() {
		return estadoEnEntidadPagadoraPensionado;
	}
	/**
	 * Método que modifica el valor de estadoEnEntidadPagadoraPensionado
	 * @param estadoEnEntidadPagadoraPensionado
	 */
	public void setEstadoEnEntidadPagadoraPensionado(EstadoActivoInactivoEnum estadoEnEntidadPagadoraPensionado) {
		this.estadoEnEntidadPagadoraPensionado = estadoEnEntidadPagadoraPensionado;
	}
    /**
     * @return the diaHabilVencimientoAporte
     */
    public Short getDiaHabilVencimientoAporte() {
        return diaHabilVencimientoAporte;
    }
    /**
     * @param diaHabilVencimientoAporte the diaHabilVencimientoAporte to set
     */
    public void setDiaHabilVencimientoAporte(Short diaHabilVencimientoAporte) {
        this.diaHabilVencimientoAporte = diaHabilVencimientoAporte;
    }
    /**
     * Método que retorna el valor de marcaExpulsion.
     * @return valor de marcaExpulsion.
     */
    public ExpulsionEnum getMarcaExpulsion() {
        return marcaExpulsion;
    }
    /**
     * Método encargado de modificar el valor de marcaExpulsion.
     * @param valor para modificar marcaExpulsion.
     */
    public void setMarcaExpulsion(ExpulsionEnum marcaExpulsion) {
        this.marcaExpulsion = marcaExpulsion;
    }
    /**
     * Método que retorna el valor de enviadoAFiscalizacion.
     * @return valor de enviadoAFiscalizacion.
     */
    public Boolean getEnviadoAFiscalizacion() {
        return enviadoAFiscalizacion;
    }
    /**
     * Método encargado de modificar el valor de enviadoAFiscalizacion.
     * @param valor para modificar enviadoAFiscalizacion.
     */
    public void setEnviadoAFiscalizacion(Boolean enviadoAFiscalizacion) {
        this.enviadoAFiscalizacion = enviadoAFiscalizacion;
    }
    /**
     * Método que retorna el valor de motivoFiscalizacion.
     * @return valor de motivoFiscalizacion.
     */
    public MotivoFiscalizacionAportanteEnum getMotivoFiscalizacion() {
        return motivoFiscalizacion;
    }
    /**
     * Método encargado de modificar el valor de motivoFiscalizacion.
     * @param valor para modificar motivoFiscalizacion.
     */
    public void setMotivoFiscalizacion(MotivoFiscalizacionAportanteEnum motivoFiscalizacion) {
        this.motivoFiscalizacion = motivoFiscalizacion;
    }
    /**
     * Método que retorna el valor de fechaFiscalizacion.
     * @return valor de fechaFiscalizacion.
     */
    public Long getFechaFiscalizacion() {
        return fechaFiscalizacion;
    }
    /**
     * Método encargado de modificar el valor de fechaFiscalizacion.
     * @param valor para modificar fechaFiscalizacion.
     */
    public void setFechaFiscalizacion(Long fechaFiscalizacion) {
        this.fechaFiscalizacion = fechaFiscalizacion;
    }
    /**
     * @return the oportunidadPago
     */
    public PeriodoPagoPlanillaEnum getOportunidadPago() {
        return oportunidadPago;
    }
    /**
     * @param oportunidadPago the oportunidadPago to set
     */
    public void setOportunidadPago(PeriodoPagoPlanillaEnum oportunidadPago) {
        this.oportunidadPago = oportunidadPago;
    }
    /**
     * @return the canalReingreso
     */
    public CanalRecepcionEnum getCanalReingreso() {
        return canalReingreso;
    }
    /**
     * @param canalReingreso the canalReingreso to set
     */
    public void setCanalReingreso(CanalRecepcionEnum canalReingreso) {
        this.canalReingreso = canalReingreso;
    }
    /**
     * @return the referenciaAporteReingreso
     */
    public Long getReferenciaAporteReingreso() {
        return referenciaAporteReingreso;
    }
    /**
     * @param referenciaAporteReingreso the referenciaAporteReingreso to set
     */
    public void setReferenciaAporteReingreso(Long referenciaAporteReingreso) {
        this.referenciaAporteReingreso = referenciaAporteReingreso;
    }
    /**
     * @return the referenciaSolicitudReingreso
     */
    public Long getReferenciaSolicitudReingreso() {
        return referenciaSolicitudReingreso;
    }
    /**
     * @param referenciaSolicitudReingreso the referenciaSolicitudReingreso to set
     */
    public void setReferenciaSolicitudReingreso(Long referenciaSolicitudReingreso) {
        this.referenciaSolicitudReingreso = referenciaSolicitudReingreso;
    }

    /**
     * Método que retorna el valor de fechaFinContrato.
     * @return valor de fechaFinContrato.
     */
    public Long getFechaFinContrato() {
        return fechaFinContrato;
    }

    /**
     * Método encargado de modificar el valor de fechaFinContrato.
     * @param valor para modificar fechaFinContrato.
     */
    public void setFechaFinContrato(Long fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
    }

    /**
     * Método que retorna el valor de canalRecepcion.
     * @return valor de canalRecepcion.
     */
    public CanalRecepcionEnum getCanalRecepcion() {
        return canalRecepcion;
    }

    /**
     * Método encargado de modificar el valor de canalRecepcion.
     * @param valor
     *        para modificar canalRecepcion.
     */
    public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }


    @Override
    public String toString() {
        return "{" +
            " idRolAfiliado='" + getIdRolAfiliado() + "'" +
            ", afiliado='" + getAfiliado() + "'" +
            ", tipoAfiliado='" + getTipoAfiliado() + "'" +
            ", estadoAfiliado='" + getEstadoAfiliado() + "'" +
            ", estadoEnEntidadPagadora='" + getEstadoEnEntidadPagadora() + "'" +
            ", claseIndependiente='" + getClaseIndependiente() + "'" +
            ", idEmpleador='" + getIdEmpleador() + "'" +
            ", idSucursalEmpleador='" + getIdSucursalEmpleador() + "'" +
            ", pagadorPension='" + getPagadorPension() + "'" +
            ", pagadorAportes='" + getPagadorAportes() + "'" +
            ", identificadorAnteEntidadPagadora='" + getIdentificadorAnteEntidadPagadora() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", fechaRetiro='" + getFechaRetiro() + "'" +
            ", valorSalarioMesadaIngresos='" + getValorSalarioMesadaIngresos() + "'" +
            ", porcentajePagoAportes='" + getPorcentajePagoAportes() + "'" +
            ", claseTrabajador='" + getClaseTrabajador() + "'" +
            ", tipoSalario='" + getTipoSalario() + "'" +
            ", tipoContrato='" + getTipoContrato() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", horasLaboradasMes='" + getHorasLaboradasMes() + "'" +
            ", fechaAfiliacion='" + getFechaAfiliacion() + "'" +
            ", motivoDesafiliacion='" + getMotivoDesafiliacion() + "'" +
            ", fechaFinPagadorAportes='" + getFechaFinPagadorAportes() + "'" +
            ", fechaFinPagadorPension='" + getFechaFinPagadorPension() + "'" +
            ", estadoEnEntidadPagadoraPensionado='" + getEstadoEnEntidadPagadoraPensionado() + "'" +
            ", diaHabilVencimientoAporte='" + getDiaHabilVencimientoAporte() + "'" +
            ", marcaExpulsion='" + getMarcaExpulsion() + "'" +
            ", motivoFiscalizacion='" + getMotivoFiscalizacion() + "'" +
            ", fechaFiscalizacion='" + getFechaFiscalizacion() + "'" +
            ", oportunidadPago='" + getOportunidadPago() + "'" +
            ", canalReingreso='" + getCanalReingreso() + "'" +
            ", referenciaAporteReingreso='" + getReferenciaAporteReingreso() + "'" +
            ", referenciaSolicitudReingreso='" + getReferenciaSolicitudReingreso() + "'" +
            ", fechaFinContrato='" + getFechaFinContrato() + "'" +
            ", fechaInicioCondicionVet='"+ getFechaInicioCondicionVet()+"'"+
            ", fechaFinCondicionVet='"+ getFechaFinCondicionVet()+"'"+
            "}";
    }

}
