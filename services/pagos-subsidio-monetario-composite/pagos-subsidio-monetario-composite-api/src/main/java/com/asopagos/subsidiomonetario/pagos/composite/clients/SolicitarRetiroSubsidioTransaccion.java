package com.asopagos.subsidiomonetario.pagos.composite.clients;

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
 * /rest/PagosSubsidioMonetarioComposite/solicitarRetiroSubsidioTransaccion
 */
public class SolicitarRetiroSubsidioTransaccion extends ServiceClient {
 
  
  	private String departamento;
  	private BigDecimal valorSolicitado;
  	private String municipio;
  	private String numeroIdAdmin;
  	private String user;
  	private String password;
  	private String usuario;
  	private Long fecha;
  	private String idTransaccionTercerPagador;
  	private String idPuntoCobro;
  	private TipoIdentificacionEnum tipoIdAdmin;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public SolicitarRetiroSubsidioTransaccion (String departamento,BigDecimal valorSolicitado,String municipio,String numeroIdAdmin,String user,String password,String usuario,Long fecha,String idTransaccionTercerPagador,String idPuntoCobro,TipoIdentificacionEnum tipoIdAdmin){
 		super();
		this.departamento=departamento;
		this.valorSolicitado=valorSolicitado;
		this.municipio=municipio;
		this.numeroIdAdmin=numeroIdAdmin;
		this.user=user;
		this.password=password;
		this.usuario=usuario;
		this.fecha=fecha;
		this.idTransaccionTercerPagador=idTransaccionTercerPagador;
		this.idPuntoCobro=idPuntoCobro;
		this.tipoIdAdmin=tipoIdAdmin;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("departamento", departamento)
						.queryParam("valorSolicitado", valorSolicitado)
						.queryParam("municipio", municipio)
						.queryParam("numeroIdAdmin", numeroIdAdmin)
						.queryParam("user", user)
						.queryParam("password", password)
						.queryParam("usuario", usuario)
						.queryParam("fecha", fecha)
						.queryParam("idTransaccionTercerPagador", idTransaccionTercerPagador)
						.queryParam("idPuntoCobro", idPuntoCobro)
						.queryParam("tipoIdAdmin", tipoIdAdmin)
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

 
  	public void setDepartamento (String departamento){
 		this.departamento=departamento;
 	}
 	
 	public String getDepartamento (){
 		return departamento;
 	}
  	public void setValorSolicitado (BigDecimal valorSolicitado){
 		this.valorSolicitado=valorSolicitado;
 	}
 	
 	public BigDecimal getValorSolicitado (){
 		return valorSolicitado;
 	}
  	public void setMunicipio (String municipio){
 		this.municipio=municipio;
 	}
 	
 	public String getMunicipio (){
 		return municipio;
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
  	public void setPassword (String password){
 		this.password=password;
 	}
 	
 	public String getPassword (){
 		return password;
 	}
  	public void setUsuario (String usuario){
 		this.usuario=usuario;
 	}
 	
 	public String getUsuario (){
 		return usuario;
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
  
}