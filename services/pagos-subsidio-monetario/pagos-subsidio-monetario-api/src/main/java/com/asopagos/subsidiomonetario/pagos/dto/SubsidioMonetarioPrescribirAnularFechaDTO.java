package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.core.SitioPago;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.subsidiomonetario.liquidacion.SolicitudLiquidacionSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.DetalleSubsidioAsignado;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoCuotaSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoLiquidacionSubsidioEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de los subsidios monetarios
 * que serán anulados por fecha de vencimiento y los subsidios monterios que se van a prescribir
 * <br/>
 * <b>Módulo:</b> Asopagos - HU - 31 - 223 y 224<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class SubsidioMonetarioPrescribirAnularFechaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Periodo de liquidación del subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private Date periodoLiquidado;

    /**
     * Identificador de la liquidación asociada del subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private Long idLiquidacionAsociada;

    /**
     * Fecha de la liquidación asociada del subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private Date fechaLiquidacionAsociada;

    /**
     * Tipo de liquidación asociada al detalle
     */
    private TipoLiquidacionSubsidioEnum tipoLiquidacion;

    /**
     * Empleador relacionado al subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private PersonaModeloDTO empleador;

    /**
     * Afiliado principal relacionado al subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private PersonaModeloDTO afiliadoPrincipal;

    /**
     * Código del grupo familiar relacionado al subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private Byte codigoGrupoFamiliar;

    /**
     * Parentesco del beneficiario del subisidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private ClasificacionEnum parentescoBeneficiario;

    /**
     * Beneficiario del subsidio monetario a ser
     * prescrito o anulado por fecha de vencimiento
     */
    private PersonaModeloDTO beneficiario;

    /**
     * Tipo de cuota que tiene el subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private TipoCuotaSubsidioEnum tipoCuota;

    /**
     * Administrador del subsidio monetario a ser
     * prescrito o anulado por fecha de vencimiento
     */
    private PersonaModeloDTO administradorSubsidio;

    /**
     * Medio de pago que se debe mostrar en la HU-31-224
     */
    private TipoMedioDePagoEnum medioDePago;

    /**
     * Código del departamento por si se quiere ver el resumen por sitio de pago
     */
    private String codigoDepartamento;

    /**
     * Nombre del departamento por si se quiere ver el resumen por sitio de pago
     */
    private String nombreDepartamento;

    /**
     * Código del municipio por si se quiere ver el resumen por sitio de pago.
     */
    private String codigoMunicipio;

    /**
     * Código del sitio de pago.
     */
    private String codigoSitioDePago;

    /**
     * Nombre del sitio de pago.
     */
    private String nombreSitioDePago;

    /**
     * Nombre del municipio por si se quiere ver el resumen por sitio de pago
     */
    private String nombreMunicipio;

    /**
     * Valor total del subsidio monetario a ser
     * prescrito o anulado por fecha de vencimiento
     */
    private BigDecimal valorTotal;

    /**
     * Identificador de la cuenta del administrador del subsidio asociado al detalle.
     */
    private Long idCuentaAdminSubsidio;

    /**
     * Identificador del detalle de subsidio asignado a la solicitud de liquidación
     */
    private Long idDetalleSubsidioAsignado;
    
    /*
     * Identificador del número de registro 
     */
    private Integer numeroRegistro;

    /**
     * Constructor de la clase vacio.
     */
    public SubsidioMonetarioPrescribirAnularFechaDTO() {

    }

    /**
     * 
     * Constructor que instancia el objeto con datos de distintas entidades
     * 
     * @param fechaPeriodo
     *        fecha del periodo liquidado
     * @param detalleSubsidio
     *        entidad del detalle de subsidio asignado
     * @param solicitudLiquidacionSubsidio
     *        entidad de la solicitud de liquidación del subsidio relacionada al detalle de subsidio asignado.
     * @param empleador
     *        entidad del empleador relacionado al detalle de subsidio asignado
     * @param afiliado
     *        entidad del afiliado principal relacionado al
     * @param beneficiario
     *        entidad de la persona que representa al beneficiario
     * @param adminSubsidio
     *        entidad de la persona que contiene la información del administrador del subsidio
     * @param sitioPago
     *        entidad del sitio de pago
     * @param codigoGrupoFamiliar
     *        código del grupo familiar asociado al afiliado principal
     * @param nombreDepartamento
     *        nombre del departamento del sitio de pago
     * @param nombreMunicipio
     *        nombre del municipio del sitio de pago
     * @param parentescoBeneficiario
     *        Parentesco familiar que tenga el beneficiario
     */
    public SubsidioMonetarioPrescribirAnularFechaDTO(Date fechaPeriodo, DetalleSubsidioAsignado detalleSubsidio,
            SolicitudLiquidacionSubsidio solicitudLiquidacionSubsidio, Persona empleador, Persona afiliado, Persona beneficiario,
            Persona adminSubsidio, SitioPago sitioPago, Byte codigoGrupoFamiliar, String nombreDepartamento, String nombreMunicipio,
            TipoMedioDePagoEnum medioDePago, ClasificacionEnum parentescoBeneficiario, String codigoDepartamento, String codigoMunicipio) {

        this.setPeriodoLiquidado(fechaPeriodo);
        this.setIdLiquidacionAsociada(solicitudLiquidacionSubsidio.getIdProcesoLiquidacionSubsidio());
        this.setFechaLiquidacionAsociada(solicitudLiquidacionSubsidio.getFechaEvaluacionSegundoNivel());
        this.setTipoLiquidacion(detalleSubsidio.getTipoLiquidacionSubsidio());

        PersonaModeloDTO empleadorDTO = new PersonaModeloDTO();
        empleadorDTO.setTipoIdentificacion(empleador.getTipoIdentificacion());
        empleadorDTO.setNumeroIdentificacion(empleador.getNumeroIdentificacion());
        empleadorDTO.setRazonSocial(empleador.getRazonSocial());
        empleadorDTO.setPrimerNombre(empleador.getPrimerNombre());
        empleadorDTO.setSegundoNombre(empleador.getSegundoNombre());
        empleadorDTO.setPrimerApellido(empleador.getPrimerApellido());
        empleadorDTO.setSegundoApellido(empleador.getSegundoApellido());
        this.setEmpleador(empleadorDTO);

        PersonaModeloDTO afiliadoDTO = new PersonaModeloDTO();
        afiliadoDTO.setTipoIdentificacion(afiliado.getTipoIdentificacion());
        afiliadoDTO.setNumeroIdentificacion(afiliado.getNumeroIdentificacion());
        afiliadoDTO.setPrimerNombre(afiliado.getPrimerNombre());
        afiliadoDTO.setSegundoNombre(afiliado.getSegundoNombre());
        afiliadoDTO.setPrimerApellido(afiliado.getPrimerApellido());
        afiliadoDTO.setSegundoApellido(afiliado.getSegundoApellido());
        this.setAfiliadoPrincipal(afiliadoDTO);

        this.setCodigoGrupoFamiliar(codigoGrupoFamiliar);

        //parentesco familiar beneficiario ?

        PersonaModeloDTO beneficiarioDTO = new PersonaModeloDTO();
        beneficiarioDTO.setTipoIdentificacion(beneficiario.getTipoIdentificacion());
        beneficiarioDTO.setNumeroIdentificacion(beneficiario.getNumeroIdentificacion());
        beneficiarioDTO.setPrimerNombre(beneficiario.getPrimerNombre());
        beneficiarioDTO.setSegundoNombre(beneficiario.getSegundoNombre());
        beneficiarioDTO.setPrimerApellido(beneficiario.getPrimerApellido());
        beneficiarioDTO.setSegundoApellido(beneficiario.getSegundoApellido());
        this.setBeneficiario(beneficiarioDTO);

        this.setTipoCuota(detalleSubsidio.getTipoCuotaSubsidio());

        PersonaModeloDTO adminSubsidioDTO = new PersonaModeloDTO();
        adminSubsidioDTO.setTipoIdentificacion(adminSubsidio.getTipoIdentificacion());
        adminSubsidioDTO.setNumeroIdentificacion(adminSubsidio.getNumeroIdentificacion());
        adminSubsidioDTO.setPrimerNombre(adminSubsidio.getPrimerNombre());
        adminSubsidioDTO.setSegundoNombre(adminSubsidio.getSegundoNombre());
        adminSubsidioDTO.setPrimerApellido(adminSubsidio.getPrimerApellido());
        adminSubsidioDTO.setSegundoApellido(adminSubsidio.getSegundoApellido());
        this.setAdministradorSubsidio(adminSubsidioDTO);

        this.setCodigoSitioDePago(sitioPago.getCodigo());
        this.setNombreSitioDePago(sitioPago.getNombre());
        this.setValorTotal(detalleSubsidio.getValorTotal());
        this.setCodigoDepartamento(codigoDepartamento);
        this.setNombreMunicipio(nombreMunicipio);
        this.setNombreDepartamento(nombreDepartamento);
        this.setCodigoMunicipio(codigoMunicipio);
        this.setIdDetalleSubsidioAsignado(detalleSubsidio.getIdDetalleSubsidioAsignado());
        this.setIdCuentaAdminSubsidio(detalleSubsidio.getIdCuentaAdministradorSubsidio());

        this.setMedioDePago(medioDePago);

        this.setParentescoBeneficiario(parentescoBeneficiario);
    }

    /**
     * @return the periodoLiquidado
     */
    public Date getPeriodoLiquidado() {
        return periodoLiquidado;
    }

    /**
     * @param periodoLiquidado
     *        the periodoLiquidado to set
     */
    public void setPeriodoLiquidado(Date periodoLiquidado) {
        this.periodoLiquidado = periodoLiquidado;
    }

    /**
     * @return the idLiquidacionAsociada
     */
    public Long getIdLiquidacionAsociada() {
        return idLiquidacionAsociada;
    }

    /**
     * @param idLiquidacionAsociada
     *        the idLiquidacionAsociada to set
     */
    public void setIdLiquidacionAsociada(Long idLiquidacionAsociada) {
        this.idLiquidacionAsociada = idLiquidacionAsociada;
    }

    /**
     * @return the fechaLiquidacionAsociada
     */
    public Date getFechaLiquidacionAsociada() {
        return fechaLiquidacionAsociada;
    }

    /**
     * @param fechaLiquidacionAsociada
     *        the fechaLiquidacionAsociada to set
     */
    public void setFechaLiquidacionAsociada(Date fechaLiquidacionAsociada) {
        this.fechaLiquidacionAsociada = fechaLiquidacionAsociada;
    }

    /**
     * @return the tipoLiquidacion
     */
    public TipoLiquidacionSubsidioEnum getTipoLiquidacion() {
        return tipoLiquidacion;
    }

    /**
     * @param tipoLiquidacion
     *        the tipoLiquidacion to set
     */
    public void setTipoLiquidacion(TipoLiquidacionSubsidioEnum tipoLiquidacion) {
        this.tipoLiquidacion = tipoLiquidacion;
    }

    /**
     * @return the empleador
     */
    public PersonaModeloDTO getEmpleador() {
        return empleador;
    }

    /**
     * @param empleador
     *        the empleador to set
     */
    public void setEmpleador(PersonaModeloDTO empleador) {
        this.empleador = empleador;
    }

    /**
     * @return the afiliadoPrincipal
     */
    public PersonaModeloDTO getAfiliadoPrincipal() {
        return afiliadoPrincipal;
    }

    /**
     * @param afiliadoPrincipal
     *        the afiliadoPrincipal to set
     */
    public void setAfiliadoPrincipal(PersonaModeloDTO afiliadoPrincipal) {
        this.afiliadoPrincipal = afiliadoPrincipal;
    }

    /**
     * @return the codigoGrupoFamiliar
     */
    public Byte getCodigoGrupoFamiliar() {
        return codigoGrupoFamiliar;
    }

    /**
     * @param codigoGrupoFamiliar
     *        the codigoGrupoFamiliar to set
     */
    public void setCodigoGrupoFamiliar(Byte codigoGrupoFamiliar) {
        this.codigoGrupoFamiliar = codigoGrupoFamiliar;
    }

    /**
     * @return the parentescoBeneficiario
     */
    public ClasificacionEnum getParentescoBeneficiario() {
        return parentescoBeneficiario;
    }

    /**
     * @param parentescoBeneficiario
     *        the parentescoBeneficiario to set
     */
    public void setParentescoBeneficiario(ClasificacionEnum parentescoBeneficiario) {
        this.parentescoBeneficiario = parentescoBeneficiario;
    }

    /**
     * @return the beneficiario
     */
    public PersonaModeloDTO getBeneficiario() {
        return beneficiario;
    }

    /**
     * @param beneficiario
     *        the beneficiario to set
     */
    public void setBeneficiario(PersonaModeloDTO beneficiario) {
        this.beneficiario = beneficiario;
    }

    /**
     * @return the tipoCuota
     */
    public TipoCuotaSubsidioEnum getTipoCuota() {
        return tipoCuota;
    }

    /**
     * @param tipoCuota
     *        the tipoCuota to set
     */
    public void setTipoCuota(TipoCuotaSubsidioEnum tipoCuota) {
        this.tipoCuota = tipoCuota;
    }

    /**
     * @return the administradorSubsidio
     */
    public PersonaModeloDTO getAdministradorSubsidio() {
        return administradorSubsidio;
    }

    /**
     * @param administradorSubsidio
     *        the administradorSubsidio to set
     */
    public void setAdministradorSubsidio(PersonaModeloDTO administradorSubsidio) {
        this.administradorSubsidio = administradorSubsidio;
    }

    /**
     * @return the medioDePago
     */
    public TipoMedioDePagoEnum getMedioDePago() {
        return medioDePago;
    }

    /**
     * @param medioDePago
     *        the medioDePago to set
     */
    public void setMedioDePago(TipoMedioDePagoEnum medioDePago) {
        this.medioDePago = medioDePago;
    }

    /**
     * @return the codigoDepartamento
     */
    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    /**
     * @param codigoDepartamento
     *        the codigoDepartamento to set
     */
    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    /**
     * @return the nombreDepartamento
     */
    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    /**
     * @param nombreDepartamento
     *        the nombreDepartamento to set
     */
    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    /**
     * @return the codigoMunicipio
     */
    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    /**
     * @param codigoMunicipio
     *        the codigoMunicipio to set
     */
    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    /**
     * @return the nombreMunicipio
     */
    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    /**
     * @param nombreMunicipio
     *        the nombreMunicipio to set
     */
    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    /**
     * @return the codigoSitioDePago
     */
    public String getCodigoSitioDePago() {
        return codigoSitioDePago;
    }

    /**
     * @param codigoSitioDePago
     *        the codigoSitioDePago to set
     */
    public void setCodigoSitioDePago(String codigoSitioDePago) {
        this.codigoSitioDePago = codigoSitioDePago;
    }

    /**
     * @return the nombreSitioDePago
     */
    public String getNombreSitioDePago() {
        return nombreSitioDePago;
    }

    /**
     * @param nombreSitioDePago
     *        the nombreSitioDePago to set
     */
    public void setNombreSitioDePago(String nombreSitioDePago) {
        this.nombreSitioDePago = nombreSitioDePago;
    }

    /**
     * @return the valorTotal
     */
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    /**
     * @param valorTotal
     *        the valorTotal to set
     */
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * @return the idCuentaAdminSubsidio
     */
    public Long getIdCuentaAdminSubsidio() {
        return idCuentaAdminSubsidio;
    }

    /**
     * @param idCuentaAdminSubsidio
     *        the idCuentaAdminSubsidio to set
     */
    public void setIdCuentaAdminSubsidio(Long idCuentaAdminSubsidio) {
        this.idCuentaAdminSubsidio = idCuentaAdminSubsidio;
    }

    /**
     * @return the idDetalleSubsidioAsignado
     */
    public Long getIdDetalleSubsidioAsignado() {
        return idDetalleSubsidioAsignado;
    }

    /**
     * @param idDetalleSubsidioAsignado
     *        the idDetalleSubsidioAsignado to set
     */
    public void setIdDetalleSubsidioAsignado(Long idDetalleSubsidioAsignado) {
        this.idDetalleSubsidioAsignado = idDetalleSubsidioAsignado;
    }

    /**
     * @return the numeroRegistro
     */
    public Integer getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     * @param numeroRegistro the numeroRegistro to set
     */
    public void setNumeroRegistro(Integer numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }
    
    
}
