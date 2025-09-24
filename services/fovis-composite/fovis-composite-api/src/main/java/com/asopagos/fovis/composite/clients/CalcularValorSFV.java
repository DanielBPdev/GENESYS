package com.asopagos.fovis.composite.clients;

import java.lang.Double;
import java.lang.Long;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.enumeraciones.fovis.CondicionHogarEnum;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisComposite/calcularValorSFV
 */
public class CalcularValorSFV extends ServiceClient {
 
  
  	private ModalidadEnum modalidad;
  	private Double ingresosHogar;
  	private Double valorSolucionVivienda;
  	private Long idPostulacion;
  	private Boolean beneficiarioViviendaMejoramientoSaludable;
  	private TipoIdentificacionEnum tipoIdentificacionJefeHogar;
  	private String numeroIdentificacionJefeHogar;
  	private String departamentoSolucionVivienda;
  	private CondicionHogarEnum condicionHogar;
  	private NombreCondicionEspecialEnum condicionEspecial;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Double result;
  
 	public CalcularValorSFV (ModalidadEnum modalidad, TipoIdentificacionEnum tipoIdentificacionJefeHogar, String numeroIdentificacionJefeHogar,
            Boolean beneficiarioViviendaMejoramientoSaludable, String departamentoSolucionVivienda, Double valorSolucionVivienda,
            Double ingresosHogar, Long idPostulacion, CondicionHogarEnum condicionHogar, NombreCondicionEspecialEnum condicionEspecial){
 		super();
		this.modalidad=modalidad;
		this.tipoIdentificacionJefeHogar=tipoIdentificacionJefeHogar;
		this.numeroIdentificacionJefeHogar=numeroIdentificacionJefeHogar;
		this.beneficiarioViviendaMejoramientoSaludable=beneficiarioViviendaMejoramientoSaludable;
		this.departamentoSolucionVivienda=departamentoSolucionVivienda;
		this.valorSolucionVivienda=valorSolucionVivienda;
		this.ingresosHogar=ingresosHogar;		
		this.idPostulacion=idPostulacion;		
		this.condicionHogar=condicionHogar;
		this.condicionEspecial=condicionEspecial;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("modalidad", modalidad)
						.queryParam("ingresosHogar", ingresosHogar)
						.queryParam("valorSolucionVivienda", valorSolucionVivienda)
						.queryParam("idPostulacion", idPostulacion)
						.queryParam("beneficiarioViviendaMejoramientoSaludable", beneficiarioViviendaMejoramientoSaludable)
						.queryParam("tipoIdentificacionJefeHogar", tipoIdentificacionJefeHogar)
						.queryParam("numeroIdentificacionJefeHogar", numeroIdentificacionJefeHogar)
						.queryParam("departamentoSolucionVivienda", departamentoSolucionVivienda)
						.queryParam("condicionHogar", condicionHogar)
						.queryParam("condicionEspecial", condicionEspecial)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	

	@Override
	protected void getResultData(Response response) {
		this.result = (Double) response.readEntity(Double.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Double getResult() {
		return result;
	}

 
  	public void setModalidad (ModalidadEnum modalidad){
 		this.modalidad=modalidad;
 	}
 	
 	public ModalidadEnum getModalidad (){
 		return modalidad;
 	}
 	
 	public void setCondicionHogar (CondicionHogarEnum condicionHogar){
 		this.condicionHogar=condicionHogar;
 	}
 	
 	public CondicionHogarEnum getCondicionHogar (){
 		return condicionHogar;
 	}
 	
  	public void setIngresosHogar (Double ingresosHogar){
 		this.ingresosHogar=ingresosHogar;
 	}
 	
 	public Double getIngresosHogar (){
 		return ingresosHogar;
 	}
  	public void setValorSolucionVivienda (Double valorSolucionVivienda){
 		this.valorSolucionVivienda=valorSolucionVivienda;
 	}
 	
 	public Double getValorSolucionVivienda (){
 		return valorSolucionVivienda;
 	}
  	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  	public void setBeneficiarioViviendaMejoramientoSaludable (Boolean beneficiarioViviendaMejoramientoSaludable){
 		this.beneficiarioViviendaMejoramientoSaludable=beneficiarioViviendaMejoramientoSaludable;
 	}
 	
 	public Boolean getBeneficiarioViviendaMejoramientoSaludable (){
 		return beneficiarioViviendaMejoramientoSaludable;
 	}
  	public void setTipoIdentificacionJefeHogar (TipoIdentificacionEnum tipoIdentificacionJefeHogar){
 		this.tipoIdentificacionJefeHogar=tipoIdentificacionJefeHogar;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionJefeHogar (){
 		return tipoIdentificacionJefeHogar;
 	}
  	public void setNumeroIdentificacionJefeHogar (String numeroIdentificacionJefeHogar){
 		this.numeroIdentificacionJefeHogar=numeroIdentificacionJefeHogar;
 	}
 	
 	public String getNumeroIdentificacionJefeHogar (){
 		return numeroIdentificacionJefeHogar;
 	}
  	public void setDepartamentoSolucionVivienda (String departamentoSolucionVivienda){
 		this.departamentoSolucionVivienda=departamentoSolucionVivienda;
 	}
 	
 	public String getDepartamentoSolucionVivienda (){
 		return departamentoSolucionVivienda;
 	}
 	
 	public NombreCondicionEspecialEnum getCondicionEspecial() {
		return condicionEspecial;
	}

	public void setCondicionEspecial(NombreCondicionEspecialEnum condicionEspecial) {
		this.condicionEspecial = condicionEspecial;
	}
  
}