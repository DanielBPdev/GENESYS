package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Beneficiario;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.GrupoFamiliar;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.subsidiomonetario.pagos.DetalleSubsidioAsignado;
import com.asopagos.entidades.subsidiomonetario.liquidacion.SolicitudLiquidacionSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoSubsidioAsignadoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.MotivoAnulacionSubsidioAsignadoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.OrigenRegistroSubsidioAsignadoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoCuotaSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoLiquidacionSubsidioEnum;

/**
 * <b>Descripcion:</b> Clase DTO que representa el detalle de subisdio asignado <br/>
 * <b>Módulo:</b> Asopagos - HU - 31 -198<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class DetalleSubsidioAsignadoDTO implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 4194410649323554823L;

    /**
     * Identificador de la base de datos del detalle del subsidio asignado
     */
    private Long idDetalleSubsidioAsignado;

    /**
     * Nombre de usuario que crea el registro del detalle
     */
    private String usuarioCreador;

    /**
     * Fecha en la cual se creo el detalle de subsidio asignado.
     */
    private Date fechaHoraCreacion;

    /**
     * Estado con el cual se creo el detalle de subsidio asignado.
     */
    private EstadoSubsidioAsignadoEnum estado;

    /**
     * Motivo por el cual el detalle de subsidio asignado llega a ser anulado.
     */
    private MotivoAnulacionSubsidioAsignadoEnum motivoAnulacion;

    /**
     * Detalle de la anulación.
     */
    private String detalleAnulacion;

    /**
     * Origen del detalle de subsidio asignado, indica de donde se origino el registro del detalle.
     */
    private OrigenRegistroSubsidioAsignadoEnum origenRegistroSubsidio;

    /**
     * Tipo de liquidación que origino el registro del detalle de subsidio asignado.
     */
    private TipoLiquidacionSubsidioEnum tipoLiquidacionSubsidio;

    /**
     * Tipo de cuota de subsidio que tiene el detalle de subsidio asignado.
     */
    private TipoCuotaSubsidioEnum tipoCuotaSubsidio;

    /**
     * Valor del subsidio asignado.
     */
    private BigDecimal valorSubsidioMonetario;

    /**
     * Valor del descuento del detalle de subsidio asignado.
     */
    private BigDecimal valorDescuento;

    /**
     * Valor original abonado al detalle de subsidio asignado.
     */
    private BigDecimal valorOriginalAbonado;

    /**
     * Valor total del detalle de subsidio asignado.
     */
    private BigDecimal valorTotal;

    /**
     * Fecha de transacción del retiro del detalle del subsidio asignado.
     */
    private Date fechaTransaccionRetiro;

    /**
     * Nombre de usuario quien realizo la transacción de retiro del
     * detalle del subsidio asignado.
     */
    private String usuarioTransaccionRetiro;

    /**
     * Fecha de anulación del detalle del subsidio asignado.
     */
    private Date fechaTransaccionAnulacion;

    /**
     * Nombre de usuario quien realizo la transacción de anulación del
     * detalle del subsidio asignado.
     */
    private String usuarioTransaccionAnulacion;

    /**
     * Fecha de la ultima modificación realizada en el detalle del subsidio asignado.
     */
    private Date fechaHoraUltimaModificacion;

    /**
     * Nombre de usuario que realizo la ultima modificación en el registro
     * en el detalle del subsidio asignado
     */
    private String usuarioUltimaModificacion;

    /**
     * Por medio de la solicitud de liquidacion del subsidio
     * se ingresa al periodo liquidado
     * Tambien los campos de la liquidación
     */
    private Long idSolicitudLiquidacionSubsidio;

    /**
     * Identificador del empleador relacionado con el detalle del subsidio asignado.
     */
    private Long idEmpleador;

    /**
     * Identificador del afiliado principal relacionado con el detalle del subsidio asignado.
     */
    private Long idAfiliadoPrincipal;

    /**
     * Identificador del grupo familiar relacionado con el detalle del subsidio asignado.
     */
    private Long idGrupoFamiliar;

    /**
     * Identificador del beneficiario relacionado con el detalle del subsidio asignado.
     */
    private Long idBeneficiarioDetalle;

    /**
     * Identificador del administrador del subsidio relacionado con el detalle del subsidio asignado.
     */
    private Long idAdministradorSubsidio;

    /**
     * Identificador del detalle del subisdio asignado original, con el cual se relaciona.
     */
    private Long idRegistroOriginalRelacionado;

    /**
     * Identificador de la cuenta de administrador del subsidio relacionado con el detalle del subsidio asignado.
     */
    private Long idCuentaAdministradorSubsidio;

    /**
     * Referencia de los datos de empleador relacionado con el detalle del subsidio asignado.
     */
    private EmpleadorModeloDTO empleador;

    /**
     * Referencia de los datos del afiliado principal relacionado con el detalle del subsidio asignado.
     */
    private AfiliadoModeloDTO afiliadoPrincipal;

    /**
     * Referencia de los datos del grupo familiar relacionado con el detalle del subsidio asignado.
     */
    private Short numeroGrupoFamilarRelacionador;

    /**
     * Referencia de los datos del beneficiario relacionado con el detalle del subsidio asignado.
     */
    private PersonaModeloDTO beneficiario;

    /**
     * Referencia de los datos del administrador del subsidio relacionado con el detalle del subsidio asignado.
     */
    private PersonaModeloDTO administradorSubsidio;

    /**
     * Periodo de liquidación del subsidio monetario
     * a ser prescrito o anulado por fecha de vencimiento
     */
    private Date periodoLiquidado;

    /**
     * referencia al registro en la la tabla Resultado de Validacion de Liquidacion relacionado
     * con el detalle de subsidio asignado
     */
    private Long idResultadoValidacionLiquidacion;

    /**
     * Código de la entidad del descuento por si el detalle tiene un descuento aplicado.
     */
    private Long tipoDescuento;

    /**
     * referencia al identificador de la tabla CondicionPersonaBeneficiario
     */
    private Long idCondicionPersonaBeneficiario;

    /**
     * Nombre de la entidad de descuento
     */
    private String nombreTipoDescuento;

    /**
     * Fecha de la liquidación asociada al detalle
     */
    private Date fechaLiquidacionAsociada;

    // CAMPOS DEL MEDIO DE PAGO ASOCIADOS A LA CUENTA DE LOS DETALLES 

    /**
     * Tipo de medio de pago relacionado con la cuenta del administrador del subsidio.
     */
    private TipoMedioDePagoEnum medioDePago;

    /**
     * Número de la tarjeta del administrador del subsidio
     * si el medio de pago tipo tarjeta.
     */
    private String numeroTarjetaAdminSubsidio;

    /**
     * Código del banco del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private String codigoBancoAdminSubsidio;

    /**
     * Nombre del banco del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private String nombreBancoAdminSubsidio;

    /**
     * Tipo de cuenta del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private TipoCuentaEnum tipoCuentaAdminSubsidio;

    /**
     * Número de la cuenta del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private String numeroCuentaAdminSubsidio;

    /**
     * Tipo de identificación del titular de la cuenta del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private TipoIdentificacionEnum tipoIdentificacionTitularCuentaAdminSubsidio;

    /**
     * Número de identificación del titular del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private String numeroIdentificacionTitularCuentaAdminSubsidio;

    /**
     * Nombre del titular de la cuenta del administrador del subsidio
     * si el medio de pago es tipo banco.
     */
    private String nombreTitularCuentaAdminSubsidio;
    
    /**
     * referencia al registro en el detalle de solicitud de una anulacion
     */
    private Long idDetalleSolicitudAnulacionSubsidioCobrado;
    
    /**
     * Nombre del tercero pagador relacionado con la cuenta del administrador del subsidio.
     */
    private String nombreTerceroPagador;
    

    /**
     * Constructor vacio
     */
    public DetalleSubsidioAsignadoDTO() {
    }

    /**
     * Constructor que convierte una instancia de DetalleSubsidioAsignadoDTO a partir de la entidad.
     * @param detalleSubsidioAsignado
     *        entidad del detalle de subsidio asignado
     */
    public DetalleSubsidioAsignadoDTO(DetalleSubsidioAsignado detalleSubsidioAsignado) {

        this.setIdDetalleSubsidioAsignado(detalleSubsidioAsignado.getIdDetalleSubsidioAsignado());
        this.setUsuarioCreador(detalleSubsidioAsignado.getUsuarioCreador());
        this.setFechaHoraCreacion(detalleSubsidioAsignado.getFechaHoraCreacion());
        this.setEstado(detalleSubsidioAsignado.getEstado());
        this.setMotivoAnulacion(detalleSubsidioAsignado.getMotivoAnulacion());
        this.setDetalleAnulacion(detalleSubsidioAsignado.getDetalleAnulacion());
        this.setOrigenRegistroSubsidio(detalleSubsidioAsignado.getOrigenRegistroSubsidio());
        this.setTipoLiquidacionSubsidio(detalleSubsidioAsignado.getTipoLiquidacionSubsidio());
        this.setTipoCuotaSubsidio(detalleSubsidioAsignado.getTipoCuotaSubsidio());
        this.setValorSubsidioMonetario(detalleSubsidioAsignado.getValorSubsidioMonetario());
        this.setValorDescuento(detalleSubsidioAsignado.getValorDescuento());
        this.setValorOriginalAbonado(detalleSubsidioAsignado.getValorOriginalAbonado());
        this.setValorTotal(detalleSubsidioAsignado.getValorTotal());
        this.setFechaTransaccionRetiro(detalleSubsidioAsignado.getFechaTransaccionRetiro());
        this.setUsuarioTransaccionRetiro(detalleSubsidioAsignado.getUsuarioTransaccionRetiro());
        this.setFechaTransaccionAnulacion(detalleSubsidioAsignado.getFechaTransaccionAnulacion());
        this.setUsuarioTransaccionAnulacion(detalleSubsidioAsignado.getUsuarioTransaccionAnulacion());
        this.setFechaHoraUltimaModificacion(detalleSubsidioAsignado.getFechaHoraUltimaModificacion());
        this.setUsuarioUltimaModificacion(detalleSubsidioAsignado.getUsuarioUltimaModificacion());
        this.setPeriodoLiquidado(detalleSubsidioAsignado.getPeriodoLiquidado());
        this.setIdSolicitudLiquidacionSubsidio(detalleSubsidioAsignado.getIdSolicitudLiquidacionSubsidio());
        this.setIdEmpleador(detalleSubsidioAsignado.getIdEmpleador());
        this.setIdAfiliadoPrincipal(detalleSubsidioAsignado.getIdAfiliadoPrincipal());
        this.setIdGrupoFamiliar(detalleSubsidioAsignado.getIdGrupoFamiliar());
        this.setIdBeneficiarioDetalle(detalleSubsidioAsignado.getIdBeneficiarioDetalle());
        this.setIdAdministradorSubsidio(detalleSubsidioAsignado.getIdAdministradorSubsidio());
        this.setIdRegistroOriginalRelacionado(detalleSubsidioAsignado.getIdRegistroOriginalRelacionado());
        this.setIdCuentaAdministradorSubsidio(detalleSubsidioAsignado.getIdCuentaAdministradorSubsidio());
        this.setPeriodoLiquidado(detalleSubsidioAsignado.getPeriodoLiquidado());
        this.setIdResultadoValidacionLiquidacion(detalleSubsidioAsignado.getIdResultadoValidacionLiquidacion());
        this.setIdDetalleSolicitudAnulacionSubsidioCobrado(detalleSubsidioAsignado.getIdDetalleSolicitudAnulacionSubsidioCobrado());
        this.setNombreTerceroPagador(detalleSubsidioAsignado.getNombreTerceroPagador());
    }

    /**
     * Constructor que convierte una instancia de DetalleSubsidioAsignadoDTO a partir de la entidad.
     * @param detalleSubsidioAsignado
     *        entidad del detalle de subsidio asignado
     * @param empleador
     *        <code>Empleador</code>
     *        Representa un empleador afiliado a la caja de compensación.
     * @param afiliado
     *        <code>Afiliado</code>
     *        Representa una persona afiliada a la caja de compensación.
     * @param grupoFamiliar
     *        <code>GrupoFamiliar</code>
     *        Representa los grupos familiares de un afiliado.
     * @param beneficiario
     *        <code>Beneficiario</code>
     *        Representa la información del beneficiario.
     * @param administradorSubsidio
     *        <code>Persona</code>
     *        Representa una administrador de subsidio y contiene sus datos básicos.
     * @param periodoLiquidacion
     *        <code>Date</code>
     *        Representa la fecha del periodo de liquidación asociada al detalle.
     */
    public DetalleSubsidioAsignadoDTO(DetalleSubsidioAsignado detalleSubsidioAsignado, Empleador empleador, Afiliado afiliado,
            GrupoFamiliar grupoFamiliar, Beneficiario beneficiario, Persona administradorSubsidio, Date periodoLiquidacion) {
        this.setIdDetalleSubsidioAsignado(detalleSubsidioAsignado.getIdDetalleSubsidioAsignado());
        this.setUsuarioCreador(detalleSubsidioAsignado.getUsuarioCreador());
        this.setFechaHoraCreacion(detalleSubsidioAsignado.getFechaHoraCreacion());
        this.setEstado(detalleSubsidioAsignado.getEstado());
        this.setMotivoAnulacion(detalleSubsidioAsignado.getMotivoAnulacion());
        this.setDetalleAnulacion(detalleSubsidioAsignado.getDetalleAnulacion());
        this.setOrigenRegistroSubsidio(detalleSubsidioAsignado.getOrigenRegistroSubsidio());
        this.setTipoLiquidacionSubsidio(detalleSubsidioAsignado.getTipoLiquidacionSubsidio());
        this.setTipoCuotaSubsidio(detalleSubsidioAsignado.getTipoCuotaSubsidio());
        this.setValorSubsidioMonetario(detalleSubsidioAsignado.getValorSubsidioMonetario());
        this.setValorDescuento(detalleSubsidioAsignado.getValorDescuento());
        this.setValorOriginalAbonado(detalleSubsidioAsignado.getValorOriginalAbonado());
        this.setValorTotal(detalleSubsidioAsignado.getValorTotal());
        this.setFechaTransaccionRetiro(detalleSubsidioAsignado.getFechaTransaccionRetiro());
        this.setUsuarioTransaccionRetiro(detalleSubsidioAsignado.getUsuarioTransaccionRetiro());
        this.setFechaTransaccionAnulacion(detalleSubsidioAsignado.getFechaTransaccionAnulacion());
        this.setUsuarioTransaccionAnulacion(detalleSubsidioAsignado.getUsuarioTransaccionAnulacion());
        this.setFechaHoraUltimaModificacion(detalleSubsidioAsignado.getFechaHoraUltimaModificacion());
        this.setUsuarioUltimaModificacion(detalleSubsidioAsignado.getUsuarioUltimaModificacion());

        this.setIdSolicitudLiquidacionSubsidio(detalleSubsidioAsignado.getIdSolicitudLiquidacionSubsidio());
        this.setIdEmpleador(detalleSubsidioAsignado.getIdEmpleador());
        this.setIdAfiliadoPrincipal(detalleSubsidioAsignado.getIdAfiliadoPrincipal());
        this.setIdGrupoFamiliar(detalleSubsidioAsignado.getIdGrupoFamiliar());
        this.setNumeroGrupoFamilarRelacionador(Short.valueOf(grupoFamiliar.getNumero().toString()));
        this.setIdBeneficiarioDetalle(detalleSubsidioAsignado.getIdBeneficiarioDetalle());
        this.setIdAdministradorSubsidio(detalleSubsidioAsignado.getIdAdministradorSubsidio());
        this.setIdRegistroOriginalRelacionado(detalleSubsidioAsignado.getIdRegistroOriginalRelacionado());
        this.setIdCuentaAdministradorSubsidio(detalleSubsidioAsignado.getIdCuentaAdministradorSubsidio());
        this.setNombreTerceroPagador(detalleSubsidioAsignado.getNombreTerceroPagador());

        //se establecen los datos del empleador relacionado en el detalle de subsidio asignado
        EmpleadorModeloDTO empleadorModeloDTO = new EmpleadorModeloDTO(empleador);
        this.setEmpleador(empleadorModeloDTO);

        //se establecen los datos del afiilado relacionado en el detalle de subsidio asignado
        AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
        afiliadoModeloDTO.convertToDTO(afiliado, null);
        this.setAfiliadoPrincipal(afiliadoModeloDTO);

        //se establecen los datos del grupo familiar relacionado en el detalle de subsidio asignado
        //        GrupoFamiliarModeloDTO grupoFamiliarModeloDTO = new GrupoFamiliarModeloDTO();
        //        grupoFamiliarModeloDTO.convertToDTO(grupoFamiliar);
        //        this.setGrupoFamiliar(grupoFamiliarModeloDTO);

        //se establecen los datos del beneficiario relacionado en el detalle de subsidio asignado
        BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();
        beneficiarioModeloDTO.convertToDTO(beneficiario, null, null, null);
        this.setBeneficiario(beneficiarioModeloDTO);

        //se establecen los datos del adminsitrador de subsidio relacionado en el detalle de subsidio asignado
        PersonaModeloDTO administradorSubsidioDTO = new PersonaModeloDTO();
        administradorSubsidioDTO.convertToDTO(administradorSubsidio, null);
        this.setAdministradorSubsidio(administradorSubsidioDTO);

        this.setPeriodoLiquidado(periodoLiquidacion);
        this.setIdResultadoValidacionLiquidacion(detalleSubsidioAsignado.getIdResultadoValidacionLiquidacion());
        this.setIdDetalleSolicitudAnulacionSubsidioCobrado(detalleSubsidioAsignado.getIdDetalleSolicitudAnulacionSubsidioCobrado());
    }
    
    
    public DetalleSubsidioAsignadoDTO(DetalleSubsidioAsignado detalleSubsidioAsignado, Empleador empleador, Afiliado afiliado,
            GrupoFamiliar grupoFamiliar, Persona beneficiario, Persona administradorSubsidio, Date periodoLiquidacion,CuentaAdministradorSubsidio cuentaAdministradorSubsidio,
            SolicitudLiquidacionSubsidio sol) {
        this.setIdDetalleSubsidioAsignado(detalleSubsidioAsignado.getIdDetalleSubsidioAsignado());
        this.setUsuarioCreador(detalleSubsidioAsignado.getUsuarioCreador());
        this.setFechaHoraCreacion(detalleSubsidioAsignado.getFechaHoraCreacion());
        this.setEstado(detalleSubsidioAsignado.getEstado());
        this.setMotivoAnulacion(detalleSubsidioAsignado.getMotivoAnulacion());
        this.setDetalleAnulacion(detalleSubsidioAsignado.getDetalleAnulacion());
        this.setOrigenRegistroSubsidio(detalleSubsidioAsignado.getOrigenRegistroSubsidio());
        this.setTipoLiquidacionSubsidio(detalleSubsidioAsignado.getTipoLiquidacionSubsidio());
        this.setTipoCuotaSubsidio(detalleSubsidioAsignado.getTipoCuotaSubsidio());
        this.setValorSubsidioMonetario(detalleSubsidioAsignado.getValorSubsidioMonetario());
        this.setValorDescuento(detalleSubsidioAsignado.getValorDescuento());
        this.setValorOriginalAbonado(detalleSubsidioAsignado.getValorOriginalAbonado());
        this.setValorTotal(detalleSubsidioAsignado.getValorTotal());
        this.setFechaTransaccionRetiro(detalleSubsidioAsignado.getFechaTransaccionRetiro());
        this.setUsuarioTransaccionRetiro(detalleSubsidioAsignado.getUsuarioTransaccionRetiro());
        this.setFechaTransaccionAnulacion(detalleSubsidioAsignado.getFechaTransaccionAnulacion());
        if (this.getOrigenRegistroSubsidio() != null && this.getOrigenRegistroSubsidio().equals(OrigenRegistroSubsidioAsignadoEnum.ANULACION)) {
            this.setFechaTransaccionAnulacion(null);
        }
        this.setUsuarioTransaccionAnulacion(detalleSubsidioAsignado.getUsuarioTransaccionAnulacion());
        this.setFechaHoraUltimaModificacion(detalleSubsidioAsignado.getFechaHoraUltimaModificacion());
        this.setUsuarioUltimaModificacion(detalleSubsidioAsignado.getUsuarioUltimaModificacion());

        this.setIdSolicitudLiquidacionSubsidio(detalleSubsidioAsignado.getIdSolicitudLiquidacionSubsidio());
        this.setIdEmpleador(detalleSubsidioAsignado.getIdEmpleador());
        this.setIdAfiliadoPrincipal(detalleSubsidioAsignado.getIdAfiliadoPrincipal());
        this.setIdGrupoFamiliar(detalleSubsidioAsignado.getIdGrupoFamiliar());
        this.setIdBeneficiarioDetalle(detalleSubsidioAsignado.getIdBeneficiarioDetalle());
        this.setIdAdministradorSubsidio(detalleSubsidioAsignado.getIdAdministradorSubsidio());
        this.setIdRegistroOriginalRelacionado(detalleSubsidioAsignado.getIdRegistroOriginalRelacionado());
        this.setIdCuentaAdministradorSubsidio(detalleSubsidioAsignado.getIdCuentaAdministradorSubsidio());
        this.setNombreTerceroPagador(detalleSubsidioAsignado.getNombreTerceroPagador());

        //se establecen los datos del empleador relacionado en el detalle de subsidio asignado
        EmpleadorModeloDTO empleadorModeloDTO = new EmpleadorModeloDTO(empleador);
        this.setEmpleador(empleadorModeloDTO);

        //se establecen los datos del afiilado relacionado en el detalle de subsidio asignado
        AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
        afiliadoModeloDTO.convertToDTO(afiliado, null);
        this.setAfiliadoPrincipal(afiliadoModeloDTO);

        //se establecen los datos del grupo familiar relacionado en el detalle de subsidio asignado
        //        GrupoFamiliarModeloDTO grupoFamiliarModeloDTO = new GrupoFamiliarModeloDTO();
        //        grupoFamiliarModeloDTO.convertToDTO(grupoFamiliar);
        //        this.setGrupoFamiliar(grupoFamiliarModeloDTO);

        //se establecen los datos del beneficiario relacionado en el detalle de subsidio asignado
        PersonaModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();
        beneficiarioModeloDTO.convertToDTO(beneficiario, null);
        this.setBeneficiario(beneficiarioModeloDTO);

        //se establecen los datos del adminsitrador de subsidio relacionado en el detalle de subsidio asignado
        PersonaModeloDTO administradorSubsidioDTO = new PersonaModeloDTO();
        administradorSubsidioDTO.convertToDTO(administradorSubsidio, null);
        this.setAdministradorSubsidio(administradorSubsidioDTO);

        this.setPeriodoLiquidado(periodoLiquidacion);
        this.setIdResultadoValidacionLiquidacion(detalleSubsidioAsignado.getIdResultadoValidacionLiquidacion());
        this.setNombreBancoAdminSubsidio(cuentaAdministradorSubsidio.getNombreBancoAdmonSubsidio());
        this.setIdCuentaAdministradorSubsidio(cuentaAdministradorSubsidio.getIdCuentaAdministradorSubsidio());
        this.setNombreTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNombreTitularCuentaAdmonSubsidio());
        this.setNumeroCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroCuentaAdmonSubsidio());
        this.setNumeroIdentificacionTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroIdentificacionTitularCuentaAdmonSubsidio());
        this.setTipoIdentificacionTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoIdentificacionTitularCuentaAdmonSubsidio());
        this.setTipoCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoCuentaAdmonSubsidio());
        this.setCodigoBancoAdminSubsidio(cuentaAdministradorSubsidio.getCodigoBancoAdmonSubsidio());
        this.setNumeroTarjetaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroTarjetaAdmonSubsidio());
        this.setTipoCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoCuentaAdmonSubsidio());
        this.setMedioDePago(cuentaAdministradorSubsidio.getMedioDePagoTransaccion());
        this.setFechaLiquidacionAsociada(sol.getFechaInicial());
        this.setIdDetalleSolicitudAnulacionSubsidioCobrado(detalleSubsidioAsignado.getIdDetalleSolicitudAnulacionSubsidioCobrado());
    }
    
    /**
     * Constructor para la HU 31-227
     * @param detalleSubsidioAsignado
     * @param administradorSubsidio
     * @param cuentaAdministradorSubsidio
     */
    public DetalleSubsidioAsignadoDTO(DetalleSubsidioAsignado detalleSubsidioAsignado, Persona administradorSubsidio, CuentaAdministradorSubsidio cuentaAdministradorSubsidio) {
        this.setIdDetalleSubsidioAsignado(detalleSubsidioAsignado.getIdDetalleSubsidioAsignado());
        this.setUsuarioCreador(detalleSubsidioAsignado.getUsuarioCreador());
        this.setFechaHoraCreacion(detalleSubsidioAsignado.getFechaHoraCreacion());
        this.setEstado(detalleSubsidioAsignado.getEstado());
        this.setMotivoAnulacion(detalleSubsidioAsignado.getMotivoAnulacion());
        this.setDetalleAnulacion(detalleSubsidioAsignado.getDetalleAnulacion());
        this.setOrigenRegistroSubsidio(detalleSubsidioAsignado.getOrigenRegistroSubsidio());
        this.setTipoLiquidacionSubsidio(detalleSubsidioAsignado.getTipoLiquidacionSubsidio());
        this.setTipoCuotaSubsidio(detalleSubsidioAsignado.getTipoCuotaSubsidio());
        this.setValorSubsidioMonetario(detalleSubsidioAsignado.getValorSubsidioMonetario());
        this.setValorDescuento(detalleSubsidioAsignado.getValorDescuento());
        this.setValorOriginalAbonado(detalleSubsidioAsignado.getValorOriginalAbonado());
        this.setValorTotal(detalleSubsidioAsignado.getValorTotal());
        this.setFechaTransaccionRetiro(detalleSubsidioAsignado.getFechaTransaccionRetiro());
        this.setUsuarioTransaccionRetiro(detalleSubsidioAsignado.getUsuarioTransaccionRetiro());
        this.setFechaTransaccionAnulacion(detalleSubsidioAsignado.getFechaTransaccionAnulacion());
        this.setUsuarioTransaccionAnulacion(detalleSubsidioAsignado.getUsuarioTransaccionAnulacion());
        this.setFechaHoraUltimaModificacion(detalleSubsidioAsignado.getFechaHoraUltimaModificacion());
        this.setUsuarioUltimaModificacion(detalleSubsidioAsignado.getUsuarioUltimaModificacion());

        this.setIdSolicitudLiquidacionSubsidio(detalleSubsidioAsignado.getIdSolicitudLiquidacionSubsidio());
        this.setIdEmpleador(detalleSubsidioAsignado.getIdEmpleador());
        this.setIdAfiliadoPrincipal(detalleSubsidioAsignado.getIdAfiliadoPrincipal());
        this.setIdGrupoFamiliar(detalleSubsidioAsignado.getIdGrupoFamiliar());
        this.setIdBeneficiarioDetalle(detalleSubsidioAsignado.getIdBeneficiarioDetalle());
        this.setIdAdministradorSubsidio(detalleSubsidioAsignado.getIdAdministradorSubsidio());
        this.setIdRegistroOriginalRelacionado(detalleSubsidioAsignado.getIdRegistroOriginalRelacionado());
        this.setIdCuentaAdministradorSubsidio(detalleSubsidioAsignado.getIdCuentaAdministradorSubsidio());
        this.setNombreTerceroPagador(detalleSubsidioAsignado.getNombreTerceroPagador());

        //se establecen los datos del adminsitrador de subsidio relacionado en el detalle de subsidio asignado
        PersonaModeloDTO administradorSubsidioDTO = new PersonaModeloDTO();
        administradorSubsidioDTO.convertToDTO(administradorSubsidio, null);
        this.setAdministradorSubsidio(administradorSubsidioDTO);

        this.setIdResultadoValidacionLiquidacion(detalleSubsidioAsignado.getIdResultadoValidacionLiquidacion());
        this.setNombreBancoAdminSubsidio(cuentaAdministradorSubsidio.getNombreBancoAdmonSubsidio());
        this.setIdCuentaAdministradorSubsidio(cuentaAdministradorSubsidio.getIdCuentaAdministradorSubsidio());
        this.setNombreTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNombreTitularCuentaAdmonSubsidio());
        this.setNumeroCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroCuentaAdmonSubsidio());
        this.setNumeroIdentificacionTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroIdentificacionTitularCuentaAdmonSubsidio());
        this.setTipoIdentificacionTitularCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoIdentificacionTitularCuentaAdmonSubsidio());
        this.setTipoCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoCuentaAdmonSubsidio());
        this.setCodigoBancoAdminSubsidio(cuentaAdministradorSubsidio.getCodigoBancoAdmonSubsidio());
        this.setNumeroTarjetaAdminSubsidio(cuentaAdministradorSubsidio.getNumeroTarjetaAdmonSubsidio());
        this.setTipoCuentaAdminSubsidio(cuentaAdministradorSubsidio.getTipoCuentaAdmonSubsidio());
        this.setMedioDePago(cuentaAdministradorSubsidio.getMedioDePagoTransaccion());
        this.setIdDetalleSolicitudAnulacionSubsidioCobrado(detalleSubsidioAsignado.getIdDetalleSolicitudAnulacionSubsidioCobrado());
    }

    /**
     * Metodo que convierte un DTO a un entity
     * 
     * @return entity de detalle subsidio asignado
     */
    public DetalleSubsidioAsignado convertToEntity() {
 
        DetalleSubsidioAsignado detalleSubsidioAsignado = new DetalleSubsidioAsignado();

        detalleSubsidioAsignado.setIdDetalleSubsidioAsignado(this.getIdDetalleSubsidioAsignado());
        detalleSubsidioAsignado.setUsuarioCreador(this.getUsuarioCreador());
        detalleSubsidioAsignado.setFechaHoraCreacion(this.getFechaHoraCreacion());
        detalleSubsidioAsignado.setEstado(this.getEstado()); 
        detalleSubsidioAsignado.setMotivoAnulacion(this.getMotivoAnulacion());
        detalleSubsidioAsignado.setDetalleAnulacion(this.getDetalleAnulacion());
        detalleSubsidioAsignado.setOrigenRegistroSubsidio(this.getOrigenRegistroSubsidio());
        detalleSubsidioAsignado.setTipoLiquidacionSubsidio(this.getTipoLiquidacionSubsidio());
        detalleSubsidioAsignado.setTipoCuotaSubsidio(this.getTipoCuotaSubsidio());
        detalleSubsidioAsignado.setValorSubsidioMonetario(this.getValorSubsidioMonetario());
        detalleSubsidioAsignado.setValorDescuento(this.getValorDescuento());
        detalleSubsidioAsignado.setValorOriginalAbonado(this.getValorOriginalAbonado());
        detalleSubsidioAsignado.setValorTotal(this.getValorTotal());
        detalleSubsidioAsignado.setFechaTransaccionRetiro(this.getFechaTransaccionRetiro());
        detalleSubsidioAsignado.setUsuarioTransaccionRetiro(this.getUsuarioTransaccionRetiro());
        detalleSubsidioAsignado.setFechaTransaccionAnulacion(this.getFechaTransaccionAnulacion());
        detalleSubsidioAsignado.setUsuarioTransaccionAnulacion(this.getUsuarioTransaccionAnulacion());
        detalleSubsidioAsignado.setFechaHoraUltimaModificacion(this.getFechaHoraUltimaModificacion());
        detalleSubsidioAsignado.setUsuarioUltimaModificacion(this.getUsuarioUltimaModificacion());
        detalleSubsidioAsignado.setPeriodoLiquidado(this.getPeriodoLiquidado());
        detalleSubsidioAsignado.setIdSolicitudLiquidacionSubsidio(this.getIdSolicitudLiquidacionSubsidio());

        detalleSubsidioAsignado.setIdEmpleador(this.getIdEmpleador());
        detalleSubsidioAsignado.setIdAfiliadoPrincipal(this.getIdAfiliadoPrincipal());
        detalleSubsidioAsignado.setIdGrupoFamiliar(this.getIdGrupoFamiliar());
        detalleSubsidioAsignado.setIdBeneficiarioDetalle(this.getIdBeneficiarioDetalle());
        detalleSubsidioAsignado.setIdAdministradorSubsidio(this.getIdAdministradorSubsidio());
        detalleSubsidioAsignado.setIdRegistroOriginalRelacionado(this.getIdRegistroOriginalRelacionado());
        detalleSubsidioAsignado.setIdCuentaAdministradorSubsidio(this.getIdCuentaAdministradorSubsidio());
        detalleSubsidioAsignado.setPeriodoLiquidado(this.getPeriodoLiquidado());
        detalleSubsidioAsignado.setIdResultadoValidacionLiquidacion(this.getIdResultadoValidacionLiquidacion());
        detalleSubsidioAsignado.setIdCondicionPersonaBeneficiario(this.getIdCondicionPersonaBeneficiario());
        detalleSubsidioAsignado.setIdDetalleSolicitudAnulacionSubsidioCobrado(this.getIdDetalleSolicitudAnulacionSubsidioCobrado());
        detalleSubsidioAsignado.setNombreTerceroPagador(this.getNombreTerceroPagador());

        return detalleSubsidioAsignado;
    }

    /**
     * Metodo que permite clonar un objeto
     * 
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public DetalleSubsidioAsignadoDTO clone() {
        DetalleSubsidioAsignadoDTO obj = null;
        try {
            obj = (DetalleSubsidioAsignadoDTO) super.clone();
        } catch (CloneNotSupportedException ex) {

        }
        return obj;
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
     * @return the usuarioCreador
     */
    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    /**
     * @param usuarioCreador
     *        the usuarioCreador to set
     */
    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    /**
     * @return the fechaHoraCreacion
     */
    public Date getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    /**
     * @param fechaHoraCreacion
     *        the fechaHoraCreacion to set
     */
    public void setFechaHoraCreacion(Date fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    /**
     * @return the estado
     */
    public EstadoSubsidioAsignadoEnum getEstado() {
        return estado;
    }

    /**
     * @param estado
     *        the estado to set
     */
    public void setEstado(EstadoSubsidioAsignadoEnum estado) {
        this.estado = estado;
    }

    /**
     * @return the motivacionAnulacion
     */
    public MotivoAnulacionSubsidioAsignadoEnum getMotivoAnulacion() {
        return motivoAnulacion;
    }

    /**
     * @param motivacionAnulacion
     *        the motivacionAnulacion to set
     */
    public void setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum motivacionAnulacion) {
        this.motivoAnulacion = motivacionAnulacion;
    }

    /**
     * @return the detalleAnulacion
     */
    public String getDetalleAnulacion() {
        return detalleAnulacion;
    }

    /**
     * @param detalleAnulacion
     *        the detalleAnulacion to set
     */
    public void setDetalleAnulacion(String detalleAnulacion) {
        this.detalleAnulacion = detalleAnulacion;
    }

    /**
     * @return the origenRegistroSubsidio
     */
    public OrigenRegistroSubsidioAsignadoEnum getOrigenRegistroSubsidio() {
        return origenRegistroSubsidio;
    }

    /**
     * @param origenRegistroSubsidio
     *        the origenRegistroSubsidio to set
     */
    public void setOrigenRegistroSubsidio(OrigenRegistroSubsidioAsignadoEnum origenRegistroSubsidio) {
        this.origenRegistroSubsidio = origenRegistroSubsidio;
    }

    /**
     * @return the tipoLiquidacionSubsidio
     */
    public TipoLiquidacionSubsidioEnum getTipoLiquidacionSubsidio() {
        return tipoLiquidacionSubsidio;
    }

    /**
     * @param tipoLiquidacionSubsidio
     *        the tipoLiquidacionSubsidio to set
     */
    public void setTipoLiquidacionSubsidio(TipoLiquidacionSubsidioEnum tipoLiquidacionSubsidio) {
        this.tipoLiquidacionSubsidio = tipoLiquidacionSubsidio;
    }

    /**
     * @return the tipoCuotaSubsidio
     */
    public TipoCuotaSubsidioEnum getTipoCuotaSubsidio() {
        return tipoCuotaSubsidio;
    }

    /**
     * @param tipoCuotaSubsidio
     *        the tipoCuotaSubsidio to set
     */
    public void setTipoCuotaSubsidio(TipoCuotaSubsidioEnum tipoCuotaSubsidio) {
        this.tipoCuotaSubsidio = tipoCuotaSubsidio;
    }

    /**
     * @return the valorSubsidioMonetario
     */
    public BigDecimal getValorSubsidioMonetario() {
        return valorSubsidioMonetario;
    }

    /**
     * @param valorSubsidioMonetario
     *        the valorSubsidioMonetario to set
     */
    public void setValorSubsidioMonetario(BigDecimal valorSubsidioMonetario) {
        this.valorSubsidioMonetario = valorSubsidioMonetario;
    }

    /**
     * @return the valorDescuento
     */
    public BigDecimal getValorDescuento() {
        return valorDescuento;
    }

    /**
     * @param valorDescuento
     *        the valorDescuento to set
     */
    public void setValorDescuento(BigDecimal valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    /**
     * @return the valorOriginalAbonado
     */
    public BigDecimal getValorOriginalAbonado() {
        return valorOriginalAbonado;
    }

    /**
     * @param valorOriginalAbonado
     *        the valorOriginalAbonado to set
     */
    public void setValorOriginalAbonado(BigDecimal valorOriginalAbonado) {
        this.valorOriginalAbonado = valorOriginalAbonado;
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
     * @return the fechaTransaccionRetiro
     */
    public Date getFechaTransaccionRetiro() {
        return fechaTransaccionRetiro;
    }

    /**
     * @param fechaTransaccionRetiro
     *        the fechaTransaccionRetiro to set
     */
    public void setFechaTransaccionRetiro(Date fechaTransaccionRetiro) {
        this.fechaTransaccionRetiro = fechaTransaccionRetiro;
    }

    /**
     * @return the usuarioTransaccionRetiro
     */
    public String getUsuarioTransaccionRetiro() {
        return usuarioTransaccionRetiro;
    }

    /**
     * @param usuarioTransaccionRetiro
     *        the usuarioTransaccionRetiro to set
     */
    public void setUsuarioTransaccionRetiro(String usuarioTransaccionRetiro) {
        this.usuarioTransaccionRetiro = usuarioTransaccionRetiro;
    }

    /**
     * @return the fechaTransaccionAnulacion
     */
    public Date getFechaTransaccionAnulacion() {
        return fechaTransaccionAnulacion;
    }

    /**
     * @param fechaTransaccionAnulacion
     *        the fechaTransaccionAnulacion to set
     */
    public void setFechaTransaccionAnulacion(Date fechaTransaccionAnulacion) {
        this.fechaTransaccionAnulacion = fechaTransaccionAnulacion;
    }

    /**
     * @return the usuarioTransaccionAnulacion
     */
    public String getUsuarioTransaccionAnulacion() {
        return usuarioTransaccionAnulacion;
    }

    /**
     * @param usuarioTransaccionAnulacion
     *        the usuarioTransaccionAnulacion to set
     */
    public void setUsuarioTransaccionAnulacion(String usuarioTransaccionAnulacion) {
        this.usuarioTransaccionAnulacion = usuarioTransaccionAnulacion;
    }

    /**
     * @return the fechaHoraUltimaModificacion
     */
    public Date getFechaHoraUltimaModificacion() {
        return fechaHoraUltimaModificacion;
    }

    /**
     * @param fechaHoraUltimaModificacion
     *        the fechaHoraUltimaModificacion to set
     */
    public void setFechaHoraUltimaModificacion(Date fechaHoraUltimaModificacion) {
        this.fechaHoraUltimaModificacion = fechaHoraUltimaModificacion;
    }

    /**
     * @return the usuarioUltimaModificacion
     */
    public String getUsuarioUltimaModificacion() {
        return usuarioUltimaModificacion;
    }

    /**
     * @param usuarioUltimaModificacion
     *        the usuarioUltimaModificacion to set
     */
    public void setUsuarioUltimaModificacion(String usuarioUltimaModificacion) {
        this.usuarioUltimaModificacion = usuarioUltimaModificacion;
    }

    /**
     * @return the idSolicitudLiquidacionSubsidio
     */
    public Long getIdSolicitudLiquidacionSubsidio() {
        return idSolicitudLiquidacionSubsidio;
    }

    /**
     * @param idSolicitudLiquidacionSubsidio
     *        the idSolicitudLiquidacionSubsidio to set
     */
    public void setIdSolicitudLiquidacionSubsidio(Long idSolicitudLiquidacionSubsidio) {
        this.idSolicitudLiquidacionSubsidio = idSolicitudLiquidacionSubsidio;
    }

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador
     *        the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * @return the idAfiliadoPrincipal
     */
    public Long getIdAfiliadoPrincipal() {
        return idAfiliadoPrincipal;
    }

    /**
     * @param idAfiliadoPrincipal
     *        the idAfiliadoPrincipal to set
     */
    public void setIdAfiliadoPrincipal(Long idAfiliadoPrincipal) {
        this.idAfiliadoPrincipal = idAfiliadoPrincipal;
    }

    /**
     * @return the idGrupoFamiliar
     */
    public Long getIdGrupoFamiliar() {
        return idGrupoFamiliar;
    }

    /**
     * @param idGrupoFamiliar
     *        the idGrupoFamiliar to set
     */
    public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }

    /**
     * @return the idBeneficiarioDetalle
     */
    public Long getIdBeneficiarioDetalle() {
        return idBeneficiarioDetalle;
    }

    /**
     * @param idBeneficiario
     *        the idBeneficiarioDetalle to set
     */
    public void setIdBeneficiarioDetalle(Long idBeneficiario) {
        this.idBeneficiarioDetalle = idBeneficiario;
    }

    /**
     * @return the idAdministradorSubsidio
     */
    public Long getIdAdministradorSubsidio() {
        return idAdministradorSubsidio;
    }

    /**
     * @param idAdministradorSubsidio
     *        the idAdministradorSubsidio to set
     */
    public void setIdAdministradorSubsidio(Long idAdministradorSubsidio) {
        this.idAdministradorSubsidio = idAdministradorSubsidio;
    }

    /**
     * @return the idRegistroOriginalRelacionado
     */
    public Long getIdRegistroOriginalRelacionado() {
        return idRegistroOriginalRelacionado;
    }

    /**
     * @param idRegistroOriginalRelacionado
     *        the idRegistroOriginalRelacionado to set
     */
    public void setIdRegistroOriginalRelacionado(Long idRegistroOriginalRelacionado) {
        this.idRegistroOriginalRelacionado = idRegistroOriginalRelacionado;
    }

    /**
     * @return the idCuentaAdministradorSubsidio
     */
    public Long getIdCuentaAdministradorSubsidio() {
        return idCuentaAdministradorSubsidio;
    }

    /**
     * @param idCuentaAdministradorSubsidio
     *        the idCuentaAdministradorSubsidio to set
     */
    public void setIdCuentaAdministradorSubsidio(Long idCuentaAdministradorSubsidio) {
        this.idCuentaAdministradorSubsidio = idCuentaAdministradorSubsidio;
    }

    /**
     * @return the empleador
     */
    public EmpleadorModeloDTO getEmpleador() {
        return empleador;
    }

    /**
     * @param empleador
     *        the empleador to set
     */
    public void setEmpleador(EmpleadorModeloDTO empleador) {
        this.empleador = empleador;
    }

    /**
     * @return the afiliadoPrincipal
     */
    public AfiliadoModeloDTO getAfiliadoPrincipal() {
        return afiliadoPrincipal;
    }

    /**
     * @param afiliadoPrincipal
     *        the afiliadoPrincipal to set
     */
    public void setAfiliadoPrincipal(AfiliadoModeloDTO afiliadoPrincipal) {
        this.afiliadoPrincipal = afiliadoPrincipal;
    }

    /**
     * @return the numeroGrupoFamilarRelacionador
     */
    public Short getNumeroGrupoFamilarRelacionador() {
        return numeroGrupoFamilarRelacionador;
    }

    /**
     * @param numeroGrupoFamilarRelacionador
     *        the numeroGrupoFamilarRelacionador to set
     */
    public void setNumeroGrupoFamilarRelacionador(Short numeroGrupoFamilarRelacionador) {
        this.numeroGrupoFamilarRelacionador = numeroGrupoFamilarRelacionador;
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
     * @return the idResultadoValidacionLiquidacion
     */
    public Long getIdResultadoValidacionLiquidacion() {
        return idResultadoValidacionLiquidacion;
    }

    /**
     * @param idResultadoValidacionLiquidacion
     *        the idResultadoValidacionLiquidacion to set
     */
    public void setIdResultadoValidacionLiquidacion(Long idResultadoValidacionLiquidacion) {
        this.idResultadoValidacionLiquidacion = idResultadoValidacionLiquidacion;
    }

    /**
     * @return the tipoDescuento
     */
    public Long getTipoDescuento() {
        return tipoDescuento;
    }

    /**
     * @param tipoDescuento
     *        the tipoDescuento to set
     */
    public void setTipoDescuento(Long tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    /**
     * @return the idCondicionPersonaBeneficiario
     */
    public Long getIdCondicionPersonaBeneficiario() {
        return idCondicionPersonaBeneficiario;
    }

    /**
     * @param idCondicionPersonaBeneficiario
     *        the idCondicionPersonaBeneficiario to set
     */
    public void setIdCondicionPersonaBeneficiario(Long idCondicionPersonaBeneficiario) {
        this.idCondicionPersonaBeneficiario = idCondicionPersonaBeneficiario;
    }

    /**
     * @return the nombreTipoDescuento
     */
    public String getNombreTipoDescuento() {
        return nombreTipoDescuento;
    }

    /**
     * @param nombreTipoDescuento
     *        the nombreTipoDescuento to set
     */
    public void setNombreTipoDescuento(String nombreTipoDescuento) {
        this.nombreTipoDescuento = nombreTipoDescuento;
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
     * @return the numeroTarjetaAdminSubsidio
     */
    public String getNumeroTarjetaAdminSubsidio() {
        return numeroTarjetaAdminSubsidio;
    }

    /**
     * @param numeroTarjetaAdminSubsidio
     *        the numeroTarjetaAdminSubsidio to set
     */
    public void setNumeroTarjetaAdminSubsidio(String numeroTarjetaAdminSubsidio) {
        this.numeroTarjetaAdminSubsidio = numeroTarjetaAdminSubsidio;
    }

    /**
     * @return the codigoBancoAdminSubsidio
     */
    public String getCodigoBancoAdminSubsidio() {
        return codigoBancoAdminSubsidio;
    }

    /**
     * @param codigoBancoAdminSubsidio
     *        the codigoBancoAdminSubsidio to set
     */
    public void setCodigoBancoAdminSubsidio(String codigoBancoAdminSubsidio) {
        this.codigoBancoAdminSubsidio = codigoBancoAdminSubsidio;
    }

    /**
     * @return the nombreBancoAdminSubsidio
     */
    public String getNombreBancoAdminSubsidio() {
        return nombreBancoAdminSubsidio;
    }

    /**
     * @param nombreBancoAdminSubsidio
     *        the nombreBancoAdminSubsidio to set
     */
    public void setNombreBancoAdminSubsidio(String nombreBancoAdminSubsidio) {
        this.nombreBancoAdminSubsidio = nombreBancoAdminSubsidio;
    }

    /**
     * @return the tipoCuentaAdminSubsidio
     */
    public TipoCuentaEnum getTipoCuentaAdminSubsidio() {
        return tipoCuentaAdminSubsidio;
    }

    /**
     * @param tipoCuentaAdminSubsidio
     *        the tipoCuentaAdminSubsidio to set
     */
    public void setTipoCuentaAdminSubsidio(TipoCuentaEnum tipoCuentaAdminSubsidio) {
        this.tipoCuentaAdminSubsidio = tipoCuentaAdminSubsidio;
    }

    /**
     * @return the numeroCuentaAdminSubsidio
     */
    public String getNumeroCuentaAdminSubsidio() {
        return numeroCuentaAdminSubsidio;
    }

    /**
     * @param numeroCuentaAdminSubsidio
     *        the numeroCuentaAdminSubsidio to set
     */
    public void setNumeroCuentaAdminSubsidio(String numeroCuentaAdminSubsidio) {
        this.numeroCuentaAdminSubsidio = numeroCuentaAdminSubsidio;
    }

    /**
     * @return the tipoIdentificacionTitularCuentaAdminSubsidio
     */
    public TipoIdentificacionEnum getTipoIdentificacionTitularCuentaAdminSubsidio() {
        return tipoIdentificacionTitularCuentaAdminSubsidio;
    }

    /**
     * @param tipoIdentificacionTitularCuentaAdminSubsidio
     *        the tipoIdentificacionTitularCuentaAdminSubsidio to set
     */
    public void setTipoIdentificacionTitularCuentaAdminSubsidio(TipoIdentificacionEnum tipoIdentificacionTitularCuentaAdminSubsidio) {
        this.tipoIdentificacionTitularCuentaAdminSubsidio = tipoIdentificacionTitularCuentaAdminSubsidio;
    }

    /**
     * @return the numeroIdentificacionTitularCuentaAdminSubsidio
     */
    public String getNumeroIdentificacionTitularCuentaAdminSubsidio() {
        return numeroIdentificacionTitularCuentaAdminSubsidio;
    }

    /**
     * @param numeroIdentificacionTitularCuentaAdminSubsidio
     *        the numeroIdentificacionTitularCuentaAdminSubsidio to set
     */
    public void setNumeroIdentificacionTitularCuentaAdminSubsidio(String numeroIdentificacionTitularCuentaAdminSubsidio) {
        this.numeroIdentificacionTitularCuentaAdminSubsidio = numeroIdentificacionTitularCuentaAdminSubsidio;
    }

    /**
     * @return the nombreTitularCuentaAdminSubsidio
     */
    public String getNombreTitularCuentaAdminSubsidio() {
        return nombreTitularCuentaAdminSubsidio;
    }

    /**
     * @param nombreTitularCuentaAdminSubsidio
     *        the nombreTitularCuentaAdminSubsidio to set
     */
    public void setNombreTitularCuentaAdminSubsidio(String nombreTitularCuentaAdminSubsidio) {
        this.nombreTitularCuentaAdminSubsidio = nombreTitularCuentaAdminSubsidio;
    }

	public Long getIdDetalleSolicitudAnulacionSubsidioCobrado() {
        return idDetalleSolicitudAnulacionSubsidioCobrado;
    }

    public void setIdDetalleSolicitudAnulacionSubsidioCobrado(Long idDetalleSolicitudAnulacionSubsidioCobrado) {
        this.idDetalleSolicitudAnulacionSubsidioCobrado = idDetalleSolicitudAnulacionSubsidioCobrado;
    }

    /**
     * @return the nombreTerceroPagador
     */
    public String getNombreTerceroPagador() {
        return nombreTerceroPagador;
    }

    /**
     * @param nombreTerceroPagador the nombreTerceroPagador to set
     */
    public void setNombreTerceroPagador(String nombreTerceroPagador) {
        this.nombreTerceroPagador = nombreTerceroPagador;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DetalleSubsidioAsignadoDTO [idDetalleSubsidioAsignado=");
		builder.append(idDetalleSubsidioAsignado);
		builder.append(", usuarioCreador=");
		builder.append(usuarioCreador);
		builder.append(", fechaHoraCreacion=");
		builder.append(fechaHoraCreacion);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", motivoAnulacion=");
		builder.append(motivoAnulacion);
		builder.append(", detalleAnulacion=");
		builder.append(detalleAnulacion);
		builder.append(", origenRegistroSubsidio=");
		builder.append(origenRegistroSubsidio);
		builder.append(", tipoLiquidacionSubsidio=");
		builder.append(tipoLiquidacionSubsidio);
		builder.append(", tipoCuotaSubsidio=");
		builder.append(tipoCuotaSubsidio);
		builder.append(", valorSubsidioMonetario=");
		builder.append(valorSubsidioMonetario);
		builder.append(", valorDescuento=");
		builder.append(valorDescuento);
		builder.append(", valorOriginalAbonado=");
		builder.append(valorOriginalAbonado);
		builder.append(", valorTotal=");
		builder.append(valorTotal);
		builder.append(", fechaTransaccionRetiro=");
		builder.append(fechaTransaccionRetiro);
		builder.append(", usuarioTransaccionRetiro=");
		builder.append(usuarioTransaccionRetiro);
		builder.append(", fechaTransaccionAnulacion=");
		builder.append(fechaTransaccionAnulacion);
		builder.append(", usuarioTransaccionAnulacion=");
		builder.append(usuarioTransaccionAnulacion);
		builder.append(", fechaHoraUltimaModificacion=");
		builder.append(fechaHoraUltimaModificacion);
		builder.append(", usuarioUltimaModificacion=");
		builder.append(usuarioUltimaModificacion);
		builder.append(", idSolicitudLiquidacionSubsidio=");
		builder.append(idSolicitudLiquidacionSubsidio);
		builder.append(", idEmpleador=");
		builder.append(idEmpleador);
		builder.append(", idAfiliadoPrincipal=");
		builder.append(idAfiliadoPrincipal);
		builder.append(", idGrupoFamiliar=");
		builder.append(idGrupoFamiliar);
		builder.append(", idBeneficiarioDetalle=");
		builder.append(idBeneficiarioDetalle);
		builder.append(", idAdministradorSubsidio=");
		builder.append(idAdministradorSubsidio);
		builder.append(", idRegistroOriginalRelacionado=");
		builder.append(idRegistroOriginalRelacionado);
		builder.append(", idCuentaAdministradorSubsidio=");
		builder.append(idCuentaAdministradorSubsidio);
		builder.append(", empleador=");
		builder.append(empleador);
		builder.append(", afiliadoPrincipal=");
		builder.append(afiliadoPrincipal);
		builder.append(", numeroGrupoFamilarRelacionador=");
		builder.append(numeroGrupoFamilarRelacionador);
		builder.append(", beneficiario=");
		builder.append(beneficiario);
		builder.append(", administradorSubsidio=");
		builder.append(administradorSubsidio);
		builder.append(", periodoLiquidado=");
		builder.append(periodoLiquidado);
		builder.append(", idResultadoValidacionLiquidacion=");
		builder.append(idResultadoValidacionLiquidacion);
		builder.append(", tipoDescuento=");
		builder.append(tipoDescuento);
		builder.append(", idCondicionPersonaBeneficiario=");
		builder.append(idCondicionPersonaBeneficiario);
		builder.append(", nombreTipoDescuento=");
		builder.append(nombreTipoDescuento);
		builder.append(", fechaLiquidacionAsociada=");
		builder.append(fechaLiquidacionAsociada);
		builder.append(", medioDePago=");
		builder.append(medioDePago);
		builder.append(", numeroTarjetaAdminSubsidio=");
		builder.append(numeroTarjetaAdminSubsidio);
		builder.append(", codigoBancoAdminSubsidio=");
		builder.append(codigoBancoAdminSubsidio);
		builder.append(", nombreBancoAdminSubsidio=");
		builder.append(nombreBancoAdminSubsidio);
		builder.append(", tipoCuentaAdminSubsidio=");
		builder.append(tipoCuentaAdminSubsidio);
		builder.append(", numeroCuentaAdminSubsidio=");
		builder.append(numeroCuentaAdminSubsidio);
		builder.append(", tipoIdentificacionTitularCuentaAdminSubsidio=");
		builder.append(tipoIdentificacionTitularCuentaAdminSubsidio);
		builder.append(", numeroIdentificacionTitularCuentaAdminSubsidio=");
		builder.append(numeroIdentificacionTitularCuentaAdminSubsidio);
		builder.append(", nombreTitularCuentaAdminSubsidio=");
		builder.append(nombreTitularCuentaAdminSubsidio);
		builder.append(", idDetalleSolicitudAnulacionSubsidioCobrado=");
        builder.append(idDetalleSolicitudAnulacionSubsidioCobrado);
        builder.append(", nombreTerceroPagador=");
        builder.append(nombreTerceroPagador);
		builder.append("]");
		return builder.toString();
	}

    
    
}
