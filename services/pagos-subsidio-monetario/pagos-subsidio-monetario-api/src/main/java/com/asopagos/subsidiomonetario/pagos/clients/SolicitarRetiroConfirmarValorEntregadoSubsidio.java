package com.asopagos.subsidiomonetario.pagos.clients;

import java.math.BigDecimal;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import java.util.Map;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/solicitarRetiro/confirmarValorEntregadoSubsidio
 */
public class SolicitarRetiroConfirmarValorEntregadoSubsidio extends ServiceClient {
 
  
  	private String departamento;
  	private BigDecimal valorSolicitado;
  	private String idTransaccionTercerPagador;
  	private BigDecimal saldoActualSubsidio;
  	private String municipio;
  	private String numeroIdAdmin;
  	private String user;
  	private TipoMedioDePagoEnum medioDePago;
	private String check;
  	private Boolean isVentanilla;
  	private Long fecha;
  	private String usuario;
  	private String idPuntoCobro;
  	private TipoIdentificacionEnum tipoIdAdmin;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public SolicitarRetiroConfirmarValorEntregadoSubsidio (String departamento,BigDecimal valorSolicitado,String idTransaccionTercerPagador,BigDecimal saldoActualSubsidio,String municipio,String numeroIdAdmin,String user,TipoMedioDePagoEnum medioDePago, String check, Boolean isVentanilla,Long fecha,String usuario,String idPuntoCobro,TipoIdentificacionEnum tipoIdAdmin){
 		super();
		this.departamento=departamento;
		this.valorSolicitado=valorSolicitado;
		this.idTransaccionTercerPagador=idTransaccionTercerPagador;
		this.saldoActualSubsidio=saldoActualSubsidio;
		this.municipio=municipio;
		this.numeroIdAdmin=numeroIdAdmin;
		this.user=user;
		this.medioDePago=medioDePago;
		this.check=check;
		this.isVentanilla=isVentanilla;
		this.fecha=fecha;
		this.usuario=usuario;
		this.idPuntoCobro=idPuntoCobro;
		this.tipoIdAdmin=tipoIdAdmin;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("departamento", departamento)
						.queryParam("valorSolicitado", valorSolicitado)
						.queryParam("idTransaccionTercerPagador", idTransaccionTercerPagador)
						.queryParam("saldoActualSubsidio", saldoActualSubsidio)
						.queryParam("municipio", municipio)
						.queryParam("numeroIdAdmin", numeroIdAdmin)
						.queryParam("user", user)
						.queryParam("medioDePago", medioDePago)
						.queryParam("check", check)
						.queryParam("isVentanilla", isVentanilla)
						.queryParam("fecha", fecha)
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
  	public void setIdTransaccionTercerPagador (String idTransaccionTercerPagador){
 		this.idTransaccionTercerPagador=idTransaccionTercerPagador;
 	}
 	
 	public String getIdTransaccionTercerPagador (){
 		return idTransaccionTercerPagador;
 	}
  	public void setSaldoActualSubsidio (BigDecimal saldoActualSubsidio){
 		this.saldoActualSubsidio=saldoActualSubsidio;
 	}
 	
 	public BigDecimal getSaldoActualSubsidio (){
 		return saldoActualSubsidio;
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
  	public void setIsVentanilla (Boolean isVentanilla){
 		this.isVentanilla=isVentanilla;
 	}
 	
 	public Boolean getIsVentanilla (){
 		return isVentanilla;
 	}
  	public void setFecha (Long fecha){
 		this.fecha=fecha;
 	}
 	
 	public Long getFecha (){
 		return fecha;
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