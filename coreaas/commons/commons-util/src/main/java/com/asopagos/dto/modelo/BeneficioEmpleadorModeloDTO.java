package com.asopagos.dto.modelo;	
import java.io.Serializable;
import java.util.Date;

import com.asopagos.entidades.ccf.core.Beneficio;
import com.asopagos.entidades.ccf.core.BeneficioEmpleador;
import com.asopagos.enumeraciones.afiliaciones.MotivoInactivacionBeneficioEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;

/**
 * DTO con los datos del Modelo de Beneficio de un empleador.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
public class BeneficioEmpleadorModeloDTO implements Serializable{
	
	/**
     * 
     */
    private static final long serialVersionUID = 8017103545070156136L;

    /**
	 * Código identificador de llave primaria del beneficio del empleador
	 */
	private Long idBeneficioEmpleador;
	
	/**
	 * Identificador del Beneficio Asociado.
	 */
	private Long idBeneficio;

	/**
	 * Nombre del tipo de  beneficio del empleador
	 */
	private TipoBeneficioEnum tipoBeneficio;

	/**
	 * Indicador S/N si el beneficio del empleador se encuentra activo
	 */
	private Boolean beneficioActivo;

	/**
	 * Fecha de inicio del beneficio del empleador
	 */
	private Long fechaBeneficioInicio;

	/**
	 * Fecha de finalización del beneficio del empleador
	 */
	private Long fechaBeneficioFin;

	/**
	 * Descripcion del motivo de inactivacion del beneficio de la Ley 1429
	 */
	private MotivoInactivacionBeneficioEnum motivoInactivacionBeneficio;
	
	/**
	 * Id que identifica al empleador asociado al beneficio
	 */
	private Long idEmpleador;
	
	/**
	 * Año de beneficio actual o ultimo año que lo tuvo
	 */
	private Short anioBeneficio;
	
	/**
     * Indica si el empleador pertenece a uno de los departamentos validos para 
     * acceder al benficio de ley 1429
     */
	private Boolean perteneceDepartamento;

	/**
     * Asocia los datos del DTO a la Entidad
     * @return BeneficioEmpleador beneficio.
     */
    public BeneficioEmpleador convertToEntity() {
    	BeneficioEmpleador beneficioEmpleador = new BeneficioEmpleador();
    	beneficioEmpleador.setBeneficioActivo(this.getBeneficioActivo());
    	if(this.getFechaBeneficioFin()!=null){
    		beneficioEmpleador.setFechaDesvinculacion(new Date(this.getFechaBeneficioFin()));
    	}
    	if(this.getFechaBeneficioInicio()!=null){
    		beneficioEmpleador.setFechaVinculacion(new Date(this.getFechaBeneficioInicio()));
    	}
    	beneficioEmpleador.setIdBeneficio(this.getIdBeneficio());
    	beneficioEmpleador.setIdBeneficioEmpleador(this.getIdBeneficioEmpleador());
    	beneficioEmpleador.setIdEmpleador(this.getIdEmpleador());
    	beneficioEmpleador.setMotivoInactivacion(this.getMotivoInactivacionBeneficio());
    	beneficioEmpleador.setPerteneceDepartamento(this.getPerteneceDepartamento());
    	return beneficioEmpleador;
    }
    
    /**
     * Método encargado de convertir un beneficio al dto correspondiente.
     * @param Asocia los datos de la Entidad al DTO
     */
    public void convertToDTO (BeneficioEmpleador beneficio) {
        this.setBeneficioActivo(beneficio.getBeneficioActivo());
    	if(beneficio.getFechaDesvinculacion()!=null){
    		this.setFechaBeneficioFin(beneficio.getFechaDesvinculacion().getTime());
    	}
    	if(beneficio.getFechaVinculacion()!=null){
    		this.setFechaBeneficioInicio(beneficio.getFechaVinculacion().getTime());
    	}
    	this.setIdBeneficioEmpleador(beneficio.getIdBeneficioEmpleador());
    	this.setIdBeneficio(beneficio.getIdBeneficio());
    	this.setIdEmpleador(beneficio.getIdEmpleador());
    	this.setMotivoInactivacionBeneficio(beneficio.getMotivoInactivacion());
    	this.setPerteneceDepartamento(beneficio.getPerteneceDepartamento());
    }
    
    /**
     * Copia los datos del DTO a la Entidad.
     * @param beneficio previamente consultada.
     * @return Ubicacion ubicacion modificada.
     */
    public BeneficioEmpleador copyDTOToEntity (BeneficioEmpleador beneficio) {
    	if(this.getBeneficioActivo()!=null){
    		beneficio.setBeneficioActivo(this.getBeneficioActivo());
    	}
    	if(this.getFechaBeneficioFin()!=null){
    		beneficio.setFechaDesvinculacion(new Date(this.getFechaBeneficioFin()));
    	}
    	if(this.getFechaBeneficioInicio()!=null){
    		beneficio.setFechaVinculacion(new Date(this.getFechaBeneficioInicio()));
    	}
    	if(this.getIdBeneficioEmpleador()!=null){
    		beneficio.setIdBeneficioEmpleador(this.getIdBeneficioEmpleador());
    	}
    	if(this.getIdBeneficio()!=null){
    		beneficio.setIdBeneficio(this.getIdBeneficio());
    	}
    	if(this.getIdEmpleador()!=null){
    		beneficio.setIdEmpleador(this.getIdEmpleador());
    	}
    	if(this.getMotivoInactivacionBeneficio()!=null){
    		beneficio.setMotivoInactivacion(this.getMotivoInactivacionBeneficio());
    	}
    	if (this.getPerteneceDepartamento() != null) {
			beneficio.setPerteneceDepartamento(this.getPerteneceDepartamento());
		}
    	return beneficio;
    }
    
	public BeneficioEmpleadorModeloDTO() {
		
	}
	
	public BeneficioEmpleadorModeloDTO(BeneficioEmpleador beneficioEmpleador, Beneficio beneficio) {
		this.setBeneficioActivo(beneficioEmpleador.getBeneficioActivo());
    	if(beneficioEmpleador.getFechaDesvinculacion()!=null){
    		this.setFechaBeneficioFin(beneficioEmpleador.getFechaDesvinculacion().getTime());
    	}
    	if(beneficioEmpleador.getFechaVinculacion()!=null){
    		this.setFechaBeneficioInicio(beneficioEmpleador.getFechaVinculacion().getTime());
    	}
    	this.setIdBeneficioEmpleador(beneficioEmpleador.getIdBeneficioEmpleador());
    	this.setIdBeneficio(beneficioEmpleador.getIdBeneficio());
    	this.setIdEmpleador(beneficioEmpleador.getIdEmpleador());
    	this.setMotivoInactivacionBeneficio(beneficioEmpleador.getMotivoInactivacion());
    	this.setPerteneceDepartamento(beneficioEmpleador.getPerteneceDepartamento());
    	this.setTipoBeneficio(beneficio.getTipoBeneficio());		
	}

	/**
	 * @return the idBeneficioEmpleador
	 */
	public Long getIdBeneficioEmpleador() {
		return idBeneficioEmpleador;
	}

	/**
	 * @param idBeneficioEmpleador the idBeneficioEmpleador to set
	 */
	public void setIdBeneficioEmpleador(Long idBeneficioEmpleador) {
		this.idBeneficioEmpleador = idBeneficioEmpleador;
	}

	/**
	 * Método que retorna el valor de tipoBeneficio.
	 * @return valor de tipoBeneficio.
	 */
	public TipoBeneficioEnum getTipoBeneficio() {
		return tipoBeneficio;
	}

	/**
	 * Método encargado de modificar el valor de tipoBeneficio.
	 * @param valor para modificar tipoBeneficio.
	 */
	public void setTipoBeneficio(TipoBeneficioEnum tipoBeneficio) {
		this.tipoBeneficio = tipoBeneficio;
	}

	/**
	 * Método que retorna el valor de beneficioActivo.
	 * @return valor de beneficioActivo.
	 */
	public Boolean getBeneficioActivo() {
		return beneficioActivo;
	}

	/**
	 * Método encargado de modificar el valor de beneficioActivo.
	 * @param valor para modificar beneficioActivo.
	 */
	public void setBeneficioActivo(Boolean beneficioActivo) {
		this.beneficioActivo = beneficioActivo;
	}

	/**
	 * Método que retorna el valor de fechaBeneficioInicio.
	 * @return valor de fechaBeneficioInicio.
	 */
	public Long getFechaBeneficioInicio() {
		return fechaBeneficioInicio;
	}

	/**
	 * Método encargado de modificar el valor de fechaBeneficioInicio.
	 * @param valor para modificar fechaBeneficioInicio.
	 */
	public void setFechaBeneficioInicio(Long fechaBeneficioInicio) {
		this.fechaBeneficioInicio = fechaBeneficioInicio;
	}

	/**
	 * Método que retorna el valor de fechaBeneficioFin.
	 * @return valor de fechaBeneficioFin.
	 */
	public Long getFechaBeneficioFin() {
		return fechaBeneficioFin;
	}

	/**
	 * Método encargado de modificar el valor de fechaBeneficioFin.
	 * @param valor para modificar fechaBeneficioFin.
	 */
	public void setFechaBeneficioFin(Long fechaBeneficioFin) {
		this.fechaBeneficioFin = fechaBeneficioFin;
	}

	/**
	 * @return the motivoInactivacionBeneficio
	 */
	public MotivoInactivacionBeneficioEnum getMotivoInactivacionBeneficio() {
		return motivoInactivacionBeneficio;
	}

	/**
	 * @param motivoInactivacionBeneficio the motivoInactivacionBeneficio to set
	 */
	public void setMotivoInactivacionBeneficio(MotivoInactivacionBeneficioEnum motivoInactivacionBeneficio) {
		this.motivoInactivacionBeneficio = motivoInactivacionBeneficio;
	}

	/**
	 * Método que retorna el valor de idEmpleador.
	 * @return valor de idEmpleador.
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}

	/**
	 * Método encargado de modificar el valor de idEmpleador.
	 * @param valor para modificar idEmpleador.
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

	/**
	 * @return the idBeneficio
	 */
	public Long getIdBeneficio() {
		return idBeneficio;
	}

	/**
	 * @param idBeneficio the idBeneficio to set
	 */
	public void setIdBeneficio(Long idBeneficio) {
		this.idBeneficio = idBeneficio;
	}

    /**
     * @return the anioBeneficio
     */
    public Short getAnioBeneficio() {
        return anioBeneficio;
    }

    /**
     * @param anioBeneficio the anioBeneficio to set
     */
    public void setAnioBeneficio(Short anioBeneficio) {
        this.anioBeneficio = anioBeneficio;
    }

	/**
	 * @return the perteneceDepartamento
	 */
	public Boolean getPerteneceDepartamento() {
		return perteneceDepartamento;
	}

	/**
	 * @param perteneceDepartamento the perteneceDepartamento to set
	 */
	public void setPerteneceDepartamento(Boolean perteneceDepartamento) {
		this.perteneceDepartamento = perteneceDepartamento;
	}
}
