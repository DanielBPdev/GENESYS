package com.asopagos.listaschequeo.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.TipoRequisitoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.validacion.GrupoActualizacion;
import com.asopagos.validacion.GrupoCreacion;

/**
 * <b>Descripción:</b> DTO para intercambio de tipos de solicitante por 
 * requisito
 * <b>Historia de Usuario:</b> HU-TRA-061 Administración general de listas de
 * chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class RequisitoCajaClasificacionDTO implements Serializable {

	/**
	 * Código identificador de llave primaria del requisito tipo solicitante
	 */
    @NotNull(groups = {GrupoActualizacion.class})
    private Long idRequisitoTipoSolicitanteCaja;
   
    /**
     * Referencia al requisito asociada al requisito documental
     */
    @NotNull(groups = {GrupoCreacion.class, GrupoActualizacion.class})
    private Long idRequisito;
    
	/**
	 * Referencia a la caja de compensación
	 */
    @NotNull(groups = {GrupoCreacion.class, GrupoActualizacion.class})
	private Integer idCajaCompensacion;    
    
    /**
     * Descripción de la clasificación
     */
    @NotNull(groups = {GrupoCreacion.class, GrupoActualizacion.class})
    private ClasificacionEnum clasificacion;
    
    /**
     * Descripción tipo de transacción asociado al requisito de tipo solicitante
     */
    @NotNull(groups = {GrupoCreacion.class, GrupoActualizacion.class})
	private TipoTransaccionEnum tipoTransaccion;    

	/**
	 * Descripción del estado de obligatoriedad del requisito a partir del 
     * tipo de transacción, clasificación y caja de compensación
	 */
    @NotNull(groups = {GrupoCreacion.class, GrupoActualizacion.class})
    private EstadoRequisitoTipoSolicitanteEnum estado;
    
    /**
     * Texto de ayuda
     */
    private String textoAyuda;
    
    /**
     * Tipo requisito
     */
    @NotNull(groups = {GrupoCreacion.class, GrupoActualizacion.class})
    private TipoRequisitoEnum tipoRequisito;
    
    /**
     * Lista de los gruposUusarios
     */
    private List<String> grupoUsuario;
    
    /**
     * Constructor de la clase
     */
    public RequisitoCajaClasificacionDTO(){
    }
    
    /**
     * Constructor de la clase
     * 
     * @param idRequisitoTipoSolicitanteCaja
     * @param idRequisito 
     * @param idCajaCompensacion
     * @param clasificacion
     * @param tipoTransaccion
     * @param estado
     * @param textoAyuda
     */
    public RequisitoCajaClasificacionDTO(Long idRequisitoTipoSolicitanteCaja, Long idRequisito, Integer idCajaCompensacion,
    		ClasificacionEnum clasificacion, TipoTransaccionEnum tipoTransaccion, EstadoRequisitoTipoSolicitanteEnum estado,
    		String textoAyuda, TipoRequisitoEnum tipoRequisito){
    	this.idRequisitoTipoSolicitanteCaja = idRequisitoTipoSolicitanteCaja;
    	this.idRequisito = idRequisito;
    	this.idCajaCompensacion = idCajaCompensacion;
    	this.clasificacion = clasificacion;
    	this.tipoTransaccion = tipoTransaccion;
    	this.estado = estado;
    	this.textoAyuda = textoAyuda;
    	this.tipoRequisito = tipoRequisito;
    }
    
    /**
     * @return the idRequisitoTipoSolicitanteCaja
     */
    public Long getIdRequisitoTipoSolicitanteCaja() {
        return idRequisitoTipoSolicitanteCaja;
    }

    /**
     * @param idRequisitoTipoSolicitanteCaja the idRequisitoTipoSolicitanteCaja to set
     */
    public void setIdRequisitoTipoSolicitanteCaja(Long idRequisitoTipoSolicitanteCaja) {
        this.idRequisitoTipoSolicitanteCaja = idRequisitoTipoSolicitanteCaja;
    }

    /**
     * @return the idRequisito
     */
    public Long getIdRequisito() {
        return idRequisito;
    }

    /**
     * @param idRequisito the idRequisito to set
     */
    public void setIdRequisito(Long idRequisito) {
        this.idRequisito = idRequisito;
    }

    /**
     * @return the idCajaCompensacion
     */
    public Integer getIdCajaCompensacion() {
        return idCajaCompensacion;
    }

    /**
     * @param idCajaCompensacion the idCajaCompensacion to set
     */
    public void setIdCajaCompensacion(Integer idCajaCompensacion) {
        this.idCajaCompensacion = idCajaCompensacion;
    }

    /**
     * @return the clasificacion
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion the clasificacion to set
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @return the estado
     */
    public EstadoRequisitoTipoSolicitanteEnum getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoRequisitoTipoSolicitanteEnum estado) {
        this.estado = estado;
    }

    /**
     * @return the textoAyuda
     */
    public String getTextoAyuda() {
        return textoAyuda;
    }

    /**
     * @param textoAyuda the textoAyuda to set
     */
    public void setTextoAyuda(String textoAyuda) {
        this.textoAyuda = textoAyuda;
    }

    /**
     * @return the tipoRequisito
     */
    public TipoRequisitoEnum getTipoRequisito() {
        return tipoRequisito;
    }

    /**
     * @param tipoRequisito the tipoRequisito to set
     */
    public void setTipoRequisito(TipoRequisitoEnum tipoRequisito) {
        this.tipoRequisito = tipoRequisito;
    }

    /**
     * @return the grupoUsuario
     */
    public List<String> getGrupoUsuario() {
        return grupoUsuario;
    }

    /**
     * @param grupoUsuario the grupoUsuario to set
     */
    public void setGrupoUsuario(List<String> grupoUsuario) {
        this.grupoUsuario = grupoUsuario;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequisitoCajaClasificacionDTO [idRequisitoTipoSolicitanteCaja=");
		builder.append(idRequisitoTipoSolicitanteCaja);
		builder.append(", idRequisito=");
		builder.append(idRequisito);
		builder.append(", idCajaCompensacion=");
		builder.append(idCajaCompensacion);
		builder.append(", clasificacion=");
		builder.append(clasificacion);
		builder.append(", tipoTransaccion=");
		builder.append(tipoTransaccion);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", textoAyuda=");
		builder.append(textoAyuda);
		builder.append(", tipoRequisito=");
		builder.append(tipoRequisito);
		builder.append(", grupoUsuario=");
		builder.append(grupoUsuario);
		builder.append("]");
		return builder.toString();
	}
    
    
}
