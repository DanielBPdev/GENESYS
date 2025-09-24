package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.novedades.RegistroNovedadFutura;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * DTO que representa las novedades futuras que se ingresan por aportes manuales o PILA.
 * <b>Historia de Usuario: </b> HU 398 Registrar y relacionar novedades.
 * 
 * @author <a href="atoro@heinsohn.com.co">Angélica Toro Murillo</a>
 */
@XmlRootElement
public class RegistroNovedadFuturaModeloDTO implements Serializable {

    /**
     * Serialización
     */
    private static final long serialVersionUID = 1L;

    /**
     * Código identificador de llave primaria de la novedad futura
     */
    private Long id;

    /**
     * Persona cotizante a la cual se le tiene prevista la novedad futura.
     */
    private Long idPersona;
    /**
     * Descripción del tipo de transacción del proceso a nivel de novedades
     */
    private TipoTransaccionEnum tipoTransaccion;

    /**
     * Fecha inicial de aplicacion de la novedad indicada en en el AporteGeneral de la planilla PILA
     */
    private Date fechaInicio;

    /**
     * Fecha final de aplicacion de la novedad indicada en en el AporteGeneral de la planilla PILA
     */
    private Date fechaFin;

    /**
     * Descripción del canal de recepción de la novedad (por defecto PILA)
     */
    private CanalRecepcionEnum canalRecepcion;

    /**
     * Referencia a la glosa de comentario asociada a la transacción el estado de la novedad
     */
    private String comentario;

    /**
     * Registro Detallado(Pila) asociado a la Solicitud de Novedad.
     */
    private Long idRegistroDetallado;
    /**
     * Clasificación del trabajador a la Solicitud de Novedad.
     */
    private ClasificacionEnum clasificacion;

    /**
     * Id del empleador asociado a la Solicitud de novedad.
     */
    private Long idEmpleador;

    /**
     * Indica si el registro de novedad futura ya se proceso por el sistema
     */
    private Boolean registroProcesado;

    /**
     * Información persona
     */
    private PersonaModeloDTO personaModeloDTO;

    /**
     * Información empresa
     */
    private EmpleadorModeloDTO empleadorModeloDTO;

    /**
     * Constructor por defecto
     */
    public RegistroNovedadFuturaModeloDTO() {
        super();
    }

    /**
     * Constructor del DTO a partir de la entidad
     * @param registroNovedadFutura
     *        Información registro novedad futura
     * @param persona
     *        Información persona asociada novedad
     * @param empleador
     *        Información empleador asociado novedad
     */
    public RegistroNovedadFuturaModeloDTO(RegistroNovedadFutura registroNovedadFutura, Persona persona, Empleador empleador) {
        this.convertToDTO(registroNovedadFutura);
        EmpleadorModeloDTO empleadorModeloDTO = new EmpleadorModeloDTO(empleador);
        this.setEmpleadorModeloDTO(empleadorModeloDTO);
        PersonaModeloDTO personaModeloDTO = new PersonaModeloDTO(persona, null);
        this.setPersonaModeloDTO(personaModeloDTO);
    }

    /**
     * Método encargado de convertir de DTO a Entidad.
     * @return entidad convertida.
     */
    public RegistroNovedadFutura convertToEntity() {
        RegistroNovedadFutura novedadFutura = new RegistroNovedadFutura();
        novedadFutura.setId(this.getId());
        novedadFutura.setCanalRecepcion(this.getCanalRecepcion());
        novedadFutura.setComentario(this.getComentario());
        novedadFutura.setFechaFin(this.getFechaFin());
        novedadFutura.setFechaInicio(this.getFechaInicio());
        novedadFutura.setIdPersona(this.getIdPersona());
        novedadFutura.setTipoTransaccion(this.getTipoTransaccion());
        novedadFutura.setIdRegistroDetallado(this.getIdRegistroDetallado());
        novedadFutura.setClasificacion(this.getClasificacion());
        novedadFutura.setIdEmpleador(this.getIdEmpleador());
        novedadFutura.setRegistroProcesado(this.getRegistroProcesado());
        return novedadFutura;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param solicitud
     *        entidad a convertir.
     */
    public void convertToDTO(RegistroNovedadFutura registroNovedadFutura) {
        this.setId(registroNovedadFutura.getId());
        this.setCanalRecepcion(registroNovedadFutura.getCanalRecepcion());
        this.setComentario(registroNovedadFutura.getComentario());
        this.setFechaFin(registroNovedadFutura.getFechaFin());
        this.setFechaInicio(registroNovedadFutura.getFechaInicio());
        this.setIdPersona(registroNovedadFutura.getIdPersona());
        this.setTipoTransaccion(registroNovedadFutura.getTipoTransaccion());
        this.setIdRegistroDetallado(registroNovedadFutura.getIdRegistroDetallado());
        this.setClasificacion(registroNovedadFutura.getClasificacion());
        this.setIdEmpleador(registroNovedadFutura.getIdEmpleador());
        this.setRegistroProcesado(registroNovedadFutura.getRegistroProcesado());
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
     * Método que retorna el valor de idPersona.
     * @return valor de idPersona.
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Método encargado de modificar el valor de idPersona.
     * @param valor
     *        para modificar idPersona.
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Método que retorna el valor de tipoTransaccion.
     * @return valor de tipoTransaccion.
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * Método encargado de modificar el valor de tipoTransaccion.
     * @param valor
     *        para modificar tipoTransaccion.
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * Método que retorna el valor de fechaInicio.
     * @return valor de fechaInicio.
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método encargado de modificar el valor de fechaInicio.
     * @param valor
     *        para modificar fechaInicio.
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Método que retorna el valor de fechaFin.
     * @return valor de fechaFin.
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * Método encargado de modificar el valor de fechaFin.
     * @param valor
     *        para modificar fechaFin.
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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

    /**
     * Método que retorna el valor de comentario.
     * @return valor de comentario.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Método encargado de modificar el valor de comentario.
     * @param valor
     *        para modificar comentario.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @return the idRegistroDetallado
     */
    public Long getIdRegistroDetallado() {
        return idRegistroDetallado;
    }

    /**
     * @param idRegistroDetallado
     *        the idRegistroDetallado to set
     */
    public void setIdRegistroDetallado(Long idRegistroDetallado) {
        this.idRegistroDetallado = idRegistroDetallado;
    }

    /**
     * Método que retorna el valor de clasificacion.
     * @return valor de clasificacion.
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    /**
     * Método que retorna el valor de idEmpleador.
     * @return valor de idEmpleador.
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * Método encargado de modificar el valor de clasificacion.
     * @param valor
     *        para modificar clasificacion.
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * Método encargado de modificar el valor de idEmpleador.
     * @param valor
     *        para modificar idEmpleador.
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * Método que retorna el valor de registroProcesado
     * @return valor de registroProcesado
     */
    public Boolean getRegistroProcesado() {
        return registroProcesado;
    }

    /**
     * Método encargado de modificar el valor de registroProcesado
     * @param registroProcesado
     *        valor para modificar registroProcesado
     */
    public void setRegistroProcesado(Boolean registroProcesado) {
        this.registroProcesado = registroProcesado;
    }

    /**
     * @return the personaModeloDTO
     */
    public PersonaModeloDTO getPersonaModeloDTO() {
        return personaModeloDTO;
    }

    /**
     * @param personaModeloDTO
     *        the personaModeloDTO to set
     */
    public void setPersonaModeloDTO(PersonaModeloDTO personaModeloDTO) {
        this.personaModeloDTO = personaModeloDTO;
    }

    /**
     * @return the empleadorModeloDTO
     */
    public EmpleadorModeloDTO getEmpleadorModeloDTO() {
        return empleadorModeloDTO;
    }

    /**
     * @param empleadorModeloDTO
     *        the empleadorModeloDTO to set
     */
    public void setEmpleadorModeloDTO(EmpleadorModeloDTO empleadorModeloDTO) {
        this.empleadorModeloDTO = empleadorModeloDTO;
    }

}