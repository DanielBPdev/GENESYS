package com.asopagos.reportes.clients;

import com.asopagos.reportes.microsoft.EmbeddedReport;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/dashboard/embeddedReport
 */
public class GetEmbeddedReport extends ServiceClient {
 
  
  	private String groupId;
  	private String reportId;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EmbeddedReport result;
  
 	public GetEmbeddedReport (String groupId,String reportId){
 		super();
		this.groupId=groupId;
		this.reportId=reportId;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("groupId", groupId)
						.queryParam("reportId", reportId)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EmbeddedReport) response.readEntity(EmbeddedReport.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EmbeddedReport getResult() {
		return result;
	}

 
  	public void setGroupId (String groupId){
 		this.groupId=groupId;
 	}
 	
 	public String getGroupId (){
 		return groupId;
 	}
  	public void setReportId (String reportId){
 		this.reportId=reportId;
 	}
 	
 	public String getReportId (){
 		return reportId;
 	}
  
}