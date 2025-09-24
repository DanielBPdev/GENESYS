package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarBeneficiario
 */
public class ConsultarBeneficiarioByTipo extends ServiceClient {
 
  
  	private List<ClasificacionEnum> tipoBeneficiario;
  	private String primerApellido;
  	private String textoFiltro;
  	private Long fechaFinCertificado;
  	private String primerNombre;
  	private String segundoApellido;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String segundoNombre;
  	private EstadoAfiliadoEnum estadoRespectoAfiliado;
  	private Boolean tipoHijo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BeneficiarioModeloDTO> result;
  
 	public ConsultarBeneficiarioByTipo (List<ClasificacionEnum> tipoBeneficiario,String primerApellido,String textoFiltro,Long fechaFinCertificado,String primerNombre,String segundoApellido,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String segundoNombre,EstadoAfiliadoEnum estadoRespectoAfiliado,Boolean tipoHijo){
 		super();
		this.tipoBeneficiario=tipoBeneficiario;
		this.primerApellido=primerApellido;
		this.textoFiltro=textoFiltro;
		this.fechaFinCertificado=fechaFinCertificado;
		this.primerNombre=primerNombre;
		this.segundoApellido=segundoApellido;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.segundoNombre=segundoNombre;
		this.estadoRespectoAfiliado=estadoRespectoAfiliado;
		this.tipoHijo=tipoHijo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoBeneficiario", tipoBeneficiario.toArray())
						.queryParam("primerApellido", primerApellido)
						.queryParam("textoFiltro", textoFiltro)
						.queryParam("fechaFinCertificado", fechaFinCertificado)
						.queryParam("primerNombre", primerNombre)
						.queryParam("segundoApellido", segundoApellido)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("segundoNombre", segundoNombre)
						.queryParam("estadoRespectoAfiliado", estadoRespectoAfiliado)
						.queryParam("tipoHijo", tipoHijo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<BeneficiarioModeloDTO>) response.readEntity(new GenericType<List<BeneficiarioModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<BeneficiarioModeloDTO> getResult() {
		return result;
	}

 
  	public void setTipoBeneficiario (List<ClasificacionEnum> tipoBeneficiario){
 		this.tipoBeneficiario=tipoBeneficiario;
 	}
 	
 	public List<ClasificacionEnum> getTipoBeneficiario (){
 		return tipoBeneficiario;
 	}
  	public void setPrimerApellido (String primerApellido){
 		this.primerApellido=primerApellido;
 	}
 	
 	public String getPrimerApellido (){
 		return primerApellido;
 	}
  	public void setTextoFiltro (String textoFiltro){
 		this.textoFiltro=textoFiltro;
 	}
 	
 	public String getTextoFiltro (){
 		return textoFiltro;
 	}
  	public void setFechaFinCertificado (Long fechaFinCertificado){
 		this.fechaFinCertificado=fechaFinCertificado;
 	}
 	
 	public Long getFechaFinCertificado (){
 		return fechaFinCertificado;
 	}
  	public void setPrimerNombre (String primerNombre){
 		this.primerNombre=primerNombre;
 	}
 	
 	public String getPrimerNombre (){
 		return primerNombre;
 	}
  	public void setSegundoApellido (String segundoApellido){
 		this.segundoApellido=segundoApellido;
 	}
 	
 	public String getSegundoApellido (){
 		return segundoApellido;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setSegundoNombre (String segundoNombre){
 		this.segundoNombre=segundoNombre;
 	}
 	
 	public String getSegundoNombre (){
 		return segundoNombre;
 	}
  	public void setEstadoRespectoAfiliado (EstadoAfiliadoEnum estadoRespectoAfiliado){
 		this.estadoRespectoAfiliado=estadoRespectoAfiliado;
 	}
 	
 	public EstadoAfiliadoEnum getEstadoRespectoAfiliado (){
 		return estadoRespectoAfiliado;
 	}
  	public void setTipoHijo (Boolean tipoHijo){
 		this.tipoHijo=tipoHijo;
 	}
 	
 	public Boolean getTipoHijo (){
 		return tipoHijo;
 	}
  
}