package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio;
import com.asopagos.entidades.subsidiomonetario.pagos.TransaccionesFallidasSubsidio;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionFallidaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ResultadoGestionTransaccionFallidaEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene las transacciones fallidas
 * que se almacenan y muestran en la bandeja<br/>
 * <b>Módulo:</b> Asopagos - HU - 31 - 200 <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class TransaccionFallidaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4612106461813911788L;

    /**
     * Indentificador de registro de las transacciones fallidas de subsidio monetario
     */
    private Long idTransaccionesFallidasSubsidio;

    /**
     * Indica la fecha y hora de la operación de la transacción falllida
     */
    private Date fechaHoraRegistro;

    /**
     * Indica el canal por el cual se genero la transaccion fallida
     */
    private CanalRecepcionEnum canal;

    /**
     * Representa el estado de la resolución de la transaccion fallida
     */
    private EstadoTransaccionFallidaEnum estadoResolucion;

    /**
     * Representa el resultado de la gestión de la transaccion fallida
     */
    private ResultadoGestionTransaccionFallidaEnum resultadoGestion;

    /**
     * Indica la descripcion de las acciones realizadas para resolver el caso
     */
    private String accionesRealizadasParaResolverCaso;

    /**
     * Representa los diferentes tipo de transaccion que pueden registrar como una
     * transaccion fallida
     */
    private TipoTransaccionSubsidioEnum tipoTransaccionPagoSubsidio;

    /**
     * Referencia el identificador del registro en la cuenta administrador de subsidio
     * relacionada como transaccion fallida
     */
    private Long idCuentaAdmonSubsidio;

    /**
     * Cuenta del administrador de subsidio relacionado con la transacción fallida
     */
    private CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO;


    /**
     * Constructor sin parametros.
     * Se crea la transaccion fallida con tres campos inicializados
     * que son los que siempre irán en cada registro de una transacción.
     */
    public TransaccionFallidaDTO() {
        this.setCanal(CanalRecepcionEnum.WEB);
        this.setEstadoResolucion(EstadoTransaccionFallidaEnum.PENDIENTE);;
        this.setFechaHoraRegistro(new Date());
    }

    /**
     * Constructor que inicializa el DTO a partir de la entidad.
     * 
     * @param transaccionesFallidasSubsidio
     *        entidad de las transacciones.
     */
    public TransaccionFallidaDTO(TransaccionesFallidasSubsidio transaccionesFallidasSubsidio) {

        this.setIdTransaccionesFallidasSubsidio(transaccionesFallidasSubsidio.getIdTransaccionFallidaSubsidio());
        this.setFechaHoraRegistro(transaccionesFallidasSubsidio.getFechaHoraRegistro());
        this.setCanal(transaccionesFallidasSubsidio.getCanal());
        this.setEstadoResolucion(transaccionesFallidasSubsidio.getEstadoResolucion());
        this.setResultadoGestion(transaccionesFallidasSubsidio.getResultadoGestion());
        this.setAccionesRealizadasParaResolverCaso(transaccionesFallidasSubsidio.getAccionesRealizadasParaResolverCaso());
        this.setTipoTransaccionPagoSubsidio(transaccionesFallidasSubsidio.getTipoTransaccionPagoSubsidioEnum());
        this.setIdCuentaAdmonSubsidio(transaccionesFallidasSubsidio.getIdCuentaAdmistradorSubsidio());
    }

    /**
     * Constructor encargado de relacionar una transacción fallida con
     * la cuenta del administrador de subsidio (abono que registro la falla en alguna transacción)
     * con sus respectivos detalles.
     * 
     * @param transaccionesFallidasSubsidio
     *        entidad de las transacciones fallidas
     * @param cuentaAdmonSubsidio
     *        entidad de la cuenta de administrador del subsidio relacionada con la transacción fallida.
     * @param persona
     *        entidad persona que contiene la información basica del administrador del subsidio.
     */
    public TransaccionFallidaDTO(TransaccionesFallidasSubsidio transaccionesFallidasSubsidio,
            CuentaAdministradorSubsidio cuentaAdmonSubsidio , Persona persona) {

        CuentaAdministradorSubsidioDTO cuentaAdminSubsidio = new CuentaAdministradorSubsidioDTO(cuentaAdmonSubsidio, persona);
        
        this.setIdTransaccionesFallidasSubsidio(transaccionesFallidasSubsidio.getIdTransaccionFallidaSubsidio());
        this.setFechaHoraRegistro(transaccionesFallidasSubsidio.getFechaHoraRegistro());
        this.setCanal(transaccionesFallidasSubsidio.getCanal());
        this.setEstadoResolucion(transaccionesFallidasSubsidio.getEstadoResolucion());
        this.setResultadoGestion(transaccionesFallidasSubsidio.getResultadoGestion());
        this.setAccionesRealizadasParaResolverCaso(transaccionesFallidasSubsidio.getAccionesRealizadasParaResolverCaso());
        this.setTipoTransaccionPagoSubsidio(transaccionesFallidasSubsidio.getTipoTransaccionPagoSubsidioEnum());
        this.setIdCuentaAdmonSubsidio(cuentaAdmonSubsidio.getIdCuentaAdministradorSubsidio());

        this.setCuentaAdministradorSubsidioDTO(cuentaAdminSubsidio);
    }

    /**
     * Metodo encargado de convertir el DTO en Entity
     * 
     * @return entidad de la transacción fallida.
     */
    public TransaccionesFallidasSubsidio convertToEntity() {
        // TODO Auto-generated method stub
        TransaccionesFallidasSubsidio transaccionesFallidasSubsidio = new TransaccionesFallidasSubsidio();

        transaccionesFallidasSubsidio.setFechaHoraRegistro(this.getFechaHoraRegistro());
        transaccionesFallidasSubsidio.setCanal(this.getCanal());
        transaccionesFallidasSubsidio.setEstadoResolucion(this.getEstadoResolucion());
        transaccionesFallidasSubsidio.setTipoTransaccionPagoSubsidioEnum(this.getTipoTransaccionPagoSubsidio());
        transaccionesFallidasSubsidio.setIdCuentaAdmistradorSubsidio(this.getIdCuentaAdmonSubsidio());

        return transaccionesFallidasSubsidio;
    }


    /**
     * @return the idTransaccionesFallidasSubsidio
     */
    public Long getIdTransaccionesFallidasSubsidio() {
        return idTransaccionesFallidasSubsidio;
    }

    /**
     * @param idTransaccionesFallidasSubsidio
     *        the idTransaccionesFallidasSubsidio to set
     */
    public void setIdTransaccionesFallidasSubsidio(Long idTransaccionesFallidasSubsidio) {
        this.idTransaccionesFallidasSubsidio = idTransaccionesFallidasSubsidio;
    }

    /**
     * @return the fechaHoraRegistro
     */
    public Date getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    /**
     * @param fechaHoraRegistro
     *        the fechaHoraRegistro to set
     */
    public void setFechaHoraRegistro(Date fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    /**
     * @return the canal
     */
    public CanalRecepcionEnum getCanal() {
        return canal;
    }

    /**
     * @param canal
     *        the canal to set
     */
    public void setCanal(CanalRecepcionEnum canal) {
        this.canal = canal;
    }

    /**
     * @return the estadoResolucion
     */
    public EstadoTransaccionFallidaEnum getEstadoResolucion() {
        return estadoResolucion;
    }

    /**
     * @param estadoResolucion
     *        the estadoResolucion to set
     */
    public void setEstadoResolucion(EstadoTransaccionFallidaEnum estadoResolucion) {
        this.estadoResolucion = estadoResolucion;
    }

    /**
     * @return the resultadoGestion
     */
    public ResultadoGestionTransaccionFallidaEnum getResultadoGestion() {
        return resultadoGestion;
    }

    /**
     * @param resultadoGestion
     *        the resultadoGestion to set
     */
    public void setResultadoGestion(ResultadoGestionTransaccionFallidaEnum resultadoGestion) {
        this.resultadoGestion = resultadoGestion;
    }

    /**
     * @return the accionesRealizadasParaResolverCaso
     */
    public String getAccionesRealizadasParaResolverCaso() {
        return accionesRealizadasParaResolverCaso;
    }

    /**
     * @param accionesRealizadasParaResolverCaso
     *        the accionesRealizadasParaResolverCaso to set
     */
    public void setAccionesRealizadasParaResolverCaso(String accionesRealizadasParaResolverCaso) {
        this.accionesRealizadasParaResolverCaso = accionesRealizadasParaResolverCaso;
    }

    /**
     * @return the tipoTransaccionPagoSubsidio
     */
    public TipoTransaccionSubsidioEnum getTipoTransaccionPagoSubsidio() {
        return tipoTransaccionPagoSubsidio;
    }

    /**
     * @param tipoTransaccionPagoSubsidio
     *        the tipoTransaccionPagoSubsidioEnum to set
     */
    public void setTipoTransaccionPagoSubsidio(TipoTransaccionSubsidioEnum tipoTransaccionPagoSubsidioEnum) {
        this.tipoTransaccionPagoSubsidio = tipoTransaccionPagoSubsidioEnum;
    }

    /**
     * @return the cuentaAdministradorSubsidioDTO
     */
    public CuentaAdministradorSubsidioDTO getCuentaAdministradorSubsidioDTO() {
        return cuentaAdministradorSubsidioDTO;
    }

    /**
     * @param cuentaAdministradorSubsidioDTO
     *        the cuentaAdministradorSubsidioDTO to set
     */
    public void setCuentaAdministradorSubsidioDTO(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO) {
        this.cuentaAdministradorSubsidioDTO = cuentaAdministradorSubsidioDTO;
    }

    /**
     * @return the idCuentaAdmonSubsidio
     */
    public Long getIdCuentaAdmonSubsidio() {
        return idCuentaAdmonSubsidio;
    }

    /**
     * @param idCuentaAdmonSubsidio
     *        the idCuentaAdmonSubsidio to set
     */
    public void setIdCuentaAdmonSubsidio(Long idCuentaAdmonSubsidio) {
        this.idCuentaAdmonSubsidio = idCuentaAdmonSubsidio;
    }


}
