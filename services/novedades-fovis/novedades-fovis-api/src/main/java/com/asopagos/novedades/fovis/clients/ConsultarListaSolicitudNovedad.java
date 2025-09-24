package com.asopagos.novedades.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudNovedadFovisModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedadesFovis/solicitud/consultar/filtrada
 */
public class ConsultarListaSolicitudNovedad extends ServiceClient {
 
  
  	private TipoTransaccionEnum tipoNovedad;
  	private Long idPostulacion;
  	private ResultadoProcesoEnum resultadoProceso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudNovedadFovisModeloDTO> result;
  
 	public ConsultarListaSolicitudNovedad (TipoTransaccionEnum tipoNovedad,Long idPostulacion,ResultadoProcesoEnum resultadoProceso){
 		super();
		this.tipoNovedad=tipoNovedad;
		this.idPostulacion=idPostulacion;
		this.resultadoProceso=resultadoProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoNovedad", tipoNovedad)
						.queryParam("idPostulacion", idPostulacion)
						.queryParam("resultadoProceso", resultadoProceso)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudNovedadFovisModeloDTO>) response.readEntity(new GenericType<List<SolicitudNovedadFovisModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudNovedadFovisModeloDTO> getResult() {
		return result;
	}

 
  	public void setTipoNovedad (TipoTransaccionEnum tipoNovedad){
 		this.tipoNovedad=tipoNovedad;
 	}
 	
 	public TipoTransaccionEnum getTipoNovedad (){
 		return tipoNovedad;
 	}
  	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  	public void setResultadoProceso (ResultadoProcesoEnum resultadoProceso){
 		this.resultadoProceso=resultadoProceso;
 	}
 	
 	public ResultadoProcesoEnum getResultadoProceso (){
 		return resultadoProceso;
 	}
  
}