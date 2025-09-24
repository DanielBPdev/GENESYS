package com.asopagos.subsidiomonetario.pagos.clients;

import java.math.BigDecimal;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import java.util.Map;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/solicitarRetiroSubsidio
 */
public class SolicitarRetiroSubsidio extends ServiceClient {
 
  
  	private BigDecimal saldoActualSubsidio;
  	private String departamento;
  	private BigDecimal valorSolicitado;
  	private String municipio;
  	private String numeroIdAdmin;
  	private String user;
  	private TipoMedioDePagoEnum medioDePago;
  	private Long fecha;
  	private String idTransaccionTercerPagador;
  	private String usuario;
  	private String idPuntoCobro;
  	private TipoIdentificacionEnum tipoIdAdmin;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public SolicitarRetiroSubsidio (BigDecimal saldoActualSubsidio,String departamento,BigDecimal valorSolicitado,String municipio,String numeroIdAdmin,String user,TipoMedioDePagoEnum medioDePago,Long fecha,String idTransaccionTercerPagador,String usuario,String idPuntoCobro,TipoIdentificacionEnum tipoIdAdmin){
 		super();
		this.saldoActualSubsidio=saldoActualSubsidio;
		this.departamento=departamento;
		this.valorSolicitado=valorSolicitado;
		this.municipio=municipio;
		this.numeroIdAdmin=numeroIdAdmin;
		this.user=user;
		this.medioDePago=medioDePago;
		this.fecha=fecha;
		this.idTransaccionTercerPagador=idTransaccionTercerPagador;
		this.usuario=usuario;
		this.idPuntoCobro=idPuntoCobro;
		this.tipoIdAdmin=tipoIdAdmin;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("saldoActualSubsidio", saldoActualSubsidio)
						.queryParam("departamento", departamento)
						.queryParam("valorSolicitado", valorSolicitado)
						.queryParam("municipio", municipio)
						.queryParam("numeroIdAdmin", numeroIdAdmin)
						.queryParam("user", user)
						.queryParam("medioDePago", medioDePago)
						.queryParam("fecha", fecha)
						.queryParam("idTransaccionTercerPagador", idTransaccionTercerPagador)
						.queryParam("usuario", usuario)
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

 
  	public void setSaldoActualSubsidio (BigDecimal saldoActualSubsidio){
 		this.saldoActualSubsidio=saldoActualSubsidio;
 	}
 	
 	public BigDecimal getSaldoActualSubsidio (){
 		return saldoActualSubsidio;
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
  	public void setMedioDePago (TipoMedioDePagoEnum medioDePago){
 		this.medioDePago=medioDePago;
 	}
 	
 	public TipoMedioDePagoEnum getMedioDePago (){
 		return medioDePago;
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
  
}