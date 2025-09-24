package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.dto.DetalleResultadoPorAdministradorDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarGrupoFamiliarLiquidacionesPorTrabajador
 */
public class ConsultarGrupoFamiliarLiquidacionesPorTrabajador extends ServiceClient {
 
  
  	private Long periodo;
  	private String numeroRadicacion;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
	private String numeroIdentificacionEmp;
  	private TipoIdentificacionEnum tipoIdentificacionEmp;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleResultadoPorAdministradorDTO> result;
  
 	public ConsultarGrupoFamiliarLiquidacionesPorTrabajador (Long periodo,String numeroRadicacion,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacionEmp,TipoIdentificacionEnum tipoIdentificacionEmp){
 		super();
		this.periodo=periodo;
		this.numeroRadicacion=numeroRadicacion;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.numeroIdentificacionEmp=numeroIdentificacionEmp;
		this.tipoIdentificacionEmp=tipoIdentificacionEmp;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("periodo", periodo)
						.queryParam("numeroRadicacion", numeroRadicacion)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("numeroIdentificacionEmpl", numeroIdentificacionEmp)
						.queryParam("tipoIdentificacionEmpl", tipoIdentificacionEmp)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DetalleResultadoPorAdministradorDTO>) response.readEntity(new GenericType<List<DetalleResultadoPorAdministradorDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DetalleResultadoPorAdministradorDTO> getResult() {
		return result;
	}

 
  	public void setPeriodo (Long periodo){
 		this.periodo=periodo;
 	}
 	
 	public Long getPeriodo (){
 		return periodo;
 	}
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
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
	 public void setNumeroIdentificacionEmp (String numeroIdentificacionEmp){
		this.numeroIdentificacionEmp=numeroIdentificacionEmp;
	}
	
	public String getNumeroIdentificacionEmp (){
		return numeroIdentificacionEmp;
	}
	 public void setTipoIdentificacionEmp (TipoIdentificacionEnum tipoIdentificacionEmp){
		this.tipoIdentificacionEmp=tipoIdentificacionEmp;
	}
	
	public TipoIdentificacionEnum getTipoIdentificacionEmp (){
		return tipoIdentificacionEmp;
	}
  
}