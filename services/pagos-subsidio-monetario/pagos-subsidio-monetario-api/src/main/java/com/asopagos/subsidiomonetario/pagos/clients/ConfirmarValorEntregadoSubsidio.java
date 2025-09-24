package com.asopagos.subsidiomonetario.pagos.clients;

import java.math.BigDecimal;
import java.lang.Long;
import java.util.Map;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/confirmarValorEntregadoSubsidio
 */
public class ConfirmarValorEntregadoSubsidio extends ServiceClient {
 
  
  	private BigDecimal valorSolicitado;
  	private String numeroIdAdmin;
  	private String user;
  	private Long fecha;
  	private String idTransaccionTercerPagador;
  	private String usuario;
  	private String idPuntoCobro;
  	private TipoIdentificacionEnum tipoIdAdmin;
  	private BigDecimal valorEntregado;
  	private String check;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public ConfirmarValorEntregadoSubsidio (BigDecimal valorSolicitado,String numeroIdAdmin,String user,Long fecha,String idTransaccionTercerPagador,String usuario,String idPuntoCobro,TipoIdentificacionEnum tipoIdAdmin,BigDecimal valorEntregado, String check){
 		super();
		this.valorSolicitado=valorSolicitado;
		this.numeroIdAdmin=numeroIdAdmin;
		this.user=user;
		this.fecha=fecha;
		this.idTransaccionTercerPagador=idTransaccionTercerPagador;
		this.usuario=usuario;
		this.idPuntoCobro=idPuntoCobro;
		this.tipoIdAdmin=tipoIdAdmin;
		this.valorEntregado=valorEntregado;
		this.check=check;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("valorSolicitado", valorSolicitado)
						.queryParam("numeroIdAdmin", numeroIdAdmin)
						.queryParam("user", user)
						.queryParam("fecha", fecha)
						.queryParam("idTransaccionTercerPagador", idTransaccionTercerPagador)
						.queryParam("usuario", usuario)
						.queryParam("idPuntoCobro", idPuntoCobro)
						.queryParam("tipoIdAdmin", tipoIdAdmin)
						.queryParam("valorEntregado", valorEntregado)
						.queryParam("check", check)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<String,String> getResult() {
		return result;
	}

 
  	public void setValorSolicitado (BigDecimal valorSolicitado){
 		this.valorSolicitado=valorSolicitado;
 	}
 	
 	public BigDecimal getValorSolicitado (){
 		return valorSolicitado;
 	}
  	public void setNumeroIdAdmin (String numeroIdAdmin){
 		this.numeroIdAdmin=numeroIdAdmin;
 	}
 	
 	public String getNumeroIdAdmin (){
 		return numeroIdAdmin;
 	}
  	public void setUser (String user){
 		this.user=user;
 	}
 	
 	public String getUser (){
 		return user;
 	}
  	public void setFecha (Long fecha){
 		this.fecha=fecha;
 	}
 	
 	public Long getFecha (){
 		return fecha;
 	}
  	public void setIdTransaccionTercerPagador (String idTransaccionTercerPagador){
 		this.idTransaccionTercerPagador=idTransaccionTercerPagador;
 	}
 	
 	public String getIdTransaccionTercerPagador (){
 		return idTransaccionTercerPagador;
 	}
  	public void setUsuario (String usuario){
 		this.usuario=usuario;
 	}
 	
 	public String getUsuario (){
 		return usuario;
 	}
  	public void setIdPuntoCobro (String idPuntoCobro){
 		this.idPuntoCobro=idPuntoCobro;
 	}
 	
 	public String getIdPuntoCobro (){
 		return idPuntoCobro;
 	}
  	public void setTipoIdAdmin (TipoIdentificacionEnum tipoIdAdmin){
 		this.tipoIdAdmin=tipoIdAdmin;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdAdmin (){
 		return tipoIdAdmin;
 	}
  	public void setValorEntregado (BigDecimal valorEntregado){
 		this.valorEntregado=valorEntregado;
 	}
 	
 	public BigDecimal getValorEntregado (){
 		return valorEntregado;
 	}
  
}