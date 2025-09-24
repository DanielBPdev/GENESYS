package com.asopagos.novedades.clients;

import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/** 
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/validacionesRegistroArchConfirmAbonosBancario
 */    

public class ValidacionesRegistroArchConfirmAbonosBancario extends ServiceClient { 
        private String tipoIdenAdminSubsidio;
   	private String numeroIdenAdminSubsidio;
        private String casId;
        private String tipoCuenta;
        private String numeroCuenta;
  	private String valorTransferencia;
        private String resultadoAbono;
  	private String motivoRechazoAbono;
  	private String fechaConfirmacionAbono;
  	private String idValidacion;

   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaValidacionArchivoDTO result;

    public ValidacionesRegistroArchConfirmAbonosBancario(String tipoIdenAdminSubsidio, String numeroIdenAdminSubsidio, String casId, String tipoCuenta, String numeroCuenta, String valorTransferencia, String resultadoAbono, String motivoRechazoAbono, String fechaConfirmacionAbono, String idValidacion) {
        super();
        this.tipoIdenAdminSubsidio = tipoIdenAdminSubsidio;
        this.numeroIdenAdminSubsidio = numeroIdenAdminSubsidio;
        this.casId = casId;
        this.tipoCuenta = tipoCuenta;
        this.numeroCuenta = numeroCuenta;
        this.valorTransferencia = valorTransferencia;
        this.resultadoAbono = resultadoAbono;
        this.motivoRechazoAbono = motivoRechazoAbono;
        this.fechaConfirmacionAbono = fechaConfirmacionAbono;
        this.idValidacion = idValidacion;
    }
  
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
                        .queryParam("tipoIdenAdminSubsidio", tipoIdenAdminSubsidio)
			.queryParam("numeroIdenAdminSubsidio", numeroIdenAdminSubsidio)
                        .queryParam("casId", casId)
                        .queryParam("tipoCuenta", tipoCuenta)
                        .queryParam("numeroCuenta", numeroCuenta)
			.queryParam("valorTransferencia", valorTransferencia)
			.queryParam("resultadoAbono", resultadoAbono)
                        .queryParam("motivoRechazoAbono", motivoRechazoAbono)
			.queryParam("fechaConfirmacionAbono", fechaConfirmacionAbono)
			.queryParam("idValidacion", idValidacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (RespuestaValidacionArchivoDTO) response.readEntity(RespuestaValidacionArchivoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public RespuestaValidacionArchivoDTO getResult() {
		return result;
	}

 
  	public void setNumeroIdenAdminSubsidio (String numeroIdenAdminSubsidio){
 		this.numeroIdenAdminSubsidio=numeroIdenAdminSubsidio;
 	}
 	
 	public String getNumeroIdenAdminSubsidio (){
 		return numeroIdenAdminSubsidio;
 	}
  	public void setValorTransferencia (String valorTransferencia){
 		this.valorTransferencia=valorTransferencia;
 	}
 	
 	public String getValorTransferencia (){
 		return valorTransferencia;
 	}
  	public void setTipoCuenta (String tipoCuenta){
 		this.tipoCuenta=tipoCuenta;
 	}
 	
 	public String getTipoCuenta (){
 		return tipoCuenta;
 	}
  	public void setFechaConfirmacionAbono (String fechaConfirmacionAbono){
 		this.fechaConfirmacionAbono=fechaConfirmacionAbono;
 	}
 	
 	public String getFechaConfirmacionAbono (){
 		return fechaConfirmacionAbono;
 	}
  	public void setMotivoRechazoAbono (String motivoRechazoAbono){
 		this.motivoRechazoAbono=motivoRechazoAbono;
 	}
 	
 	public String getMotivoRechazoAbono (){
 		return motivoRechazoAbono;
 	}
  	public void setTipoIdenAdminSubsidio (String tipoIdenAdminSubsidio){
 		this.tipoIdenAdminSubsidio=tipoIdenAdminSubsidio;
 	}
 	
 	public String getTipoIdenAdminSubsidio (){
 		return tipoIdenAdminSubsidio;
 	}
  	public void setNumeroCuenta (String numeroCuenta){
 		this.numeroCuenta=numeroCuenta;
 	}
 	
 	public String getNumeroCuenta (){
 		return numeroCuenta;
 	}
  	public void setIdValidacion (String idValidacion){
 		this.idValidacion=idValidacion;
 	}
 	
 	public String getIdValidacion (){
 		return idValidacion;
 	}
  	public void setCasId (String casId){
 		this.casId=casId;
 	}
 	
 	public String getCasId (){
 		return casId;
 	}
  	public void setResultadoAbono (String resultadoAbono){
 		this.resultadoAbono=resultadoAbono;
 	}
 	
 	public String getResultadoAbono (){
 		return resultadoAbono;
 	}
  
  
}