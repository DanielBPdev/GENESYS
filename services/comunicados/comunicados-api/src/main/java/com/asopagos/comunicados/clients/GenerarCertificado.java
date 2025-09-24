package com.asopagos.comunicados.clients;

import com.asopagos.enumeraciones.core.TipoCertificadoEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import java.lang.Short;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.comunicados.dto.CertificadoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/certificados/generar
 */
public class GenerarCertificado extends ServiceClient { 
   	private String tipoSolicitud;
  	private TipoCertificadoEnum tipoCertificado;
  	private String numeroIdentificacion;
  	private String dirigidoA;
  	private Long idEmpleador;
  	private EtiquetaPlantillaComunicadoEnum etiqueta;
  	private TipoAfiliadoEnum tipoAfiliado;
  	private Long idComunicado;
  	private Boolean empleador;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Boolean validaEstadoCartera;
  	private Long idCertificado;
  	private Short anio;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CertificadoDTO result;
  
 	public GenerarCertificado (String tipoSolicitud,TipoCertificadoEnum tipoCertificado,String numeroIdentificacion,String dirigidoA,Long idEmpleador,EtiquetaPlantillaComunicadoEnum etiqueta,TipoAfiliadoEnum tipoAfiliado,Long idComunicado,Boolean empleador,TipoIdentificacionEnum tipoIdentificacion,Boolean validaEstadoCartera,Long idCertificado,Short anio){
 		super();
		this.tipoSolicitud=tipoSolicitud;
		this.tipoCertificado=tipoCertificado;
		this.numeroIdentificacion=numeroIdentificacion;
		this.dirigidoA=dirigidoA;
		this.idEmpleador=idEmpleador;
		this.etiqueta=etiqueta;
		this.tipoAfiliado=tipoAfiliado;
		this.idComunicado=idComunicado;
		this.empleador=empleador;
		this.tipoIdentificacion=tipoIdentificacion;
		this.validaEstadoCartera=validaEstadoCartera;
		this.idCertificado=idCertificado;
		this.anio=anio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoSolicitud", tipoSolicitud)
			.queryParam("tipoCertificado", tipoCertificado)
			.queryParam("numeroIdentificacion", numeroIdentificacion)
			.queryParam("dirigidoA", dirigidoA)
			.queryParam("idEmpleador", idEmpleador)
			.queryParam("etiqueta", etiqueta)
			.queryParam("tipoAfiliado", tipoAfiliado)
			.queryParam("idComunicado", idComunicado)
			.queryParam("empleador", empleador)
			.queryParam("tipoIdentificacion", tipoIdentificacion)
			.queryParam("validaEstadoCartera", validaEstadoCartera)
			.queryParam("idCertificado", idCertificado)
			.queryParam("anio", anio)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (CertificadoDTO) response.readEntity(CertificadoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public CertificadoDTO getResult() {
		return result;
	}

 
  	public void setTipoSolicitud (String tipoSolicitud){
 		this.tipoSolicitud=tipoSolicitud;
 	}
 	
 	public String getTipoSolicitud (){
 		return tipoSolicitud;
 	}
  	public void setTipoCertificado (TipoCertificadoEnum tipoCertificado){
 		this.tipoCertificado=tipoCertificado;
 	}
 	
 	public TipoCertificadoEnum getTipoCertificado (){
 		return tipoCertificado;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setDirigidoA (String dirigidoA){
 		this.dirigidoA=dirigidoA;
 	}
 	
 	public String getDirigidoA (){
 		return dirigidoA;
 	}
  	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  	public void setEtiqueta (EtiquetaPlantillaComunicadoEnum etiqueta){
 		this.etiqueta=etiqueta;
 	}
 	
 	public EtiquetaPlantillaComunicadoEnum getEtiqueta (){
 		return etiqueta;
 	}
  	public void setTipoAfiliado (TipoAfiliadoEnum tipoAfiliado){
 		this.tipoAfiliado=tipoAfiliado;
 	}
 	
 	public TipoAfiliadoEnum getTipoAfiliado (){
 		return tipoAfiliado;
 	}
  	public void setIdComunicado (Long idComunicado){
 		this.idComunicado=idComunicado;
 	}
 	
 	public Long getIdComunicado (){
 		return idComunicado;
 	}
  	public void setEmpleador (Boolean empleador){
 		this.empleador=empleador;
 	}
 	
 	public Boolean getEmpleador (){
 		return empleador;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setValidaEstadoCartera (Boolean validaEstadoCartera){
 		this.validaEstadoCartera=validaEstadoCartera;
 	}
 	
 	public Boolean getValidaEstadoCartera (){
 		return validaEstadoCartera;
 	}
  	public void setIdCertificado (Long idCertificado){
 		this.idCertificado=idCertificado;
 	}
 	
 	public Long getIdCertificado (){
 		return idCertificado;
 	}
  	public void setAnio (Short anio){
 		this.anio=anio;
 	}
 	
 	public Short getAnio (){
 		return anio;
 	}
  
  
}