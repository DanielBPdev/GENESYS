package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

public class ConsultarRolAfiliadoEmpresa extends ServiceClient {
 
    private TipoIdentificacionEnum tipoIdentificacionAfiliado;
    private String numeroIdentificacionAfiliado;
    private TipoIdentificacionEnum tipoIdentificacionEmpresa;
    private String numeroIdentificacionEmpresa;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public ConsultarRolAfiliadoEmpresa (TipoIdentificacionEnum tipoIdentificacionAfiliado,String numeroIdentificacionAfiliado,TipoIdentificacionEnum tipoIdentificacionEmpresa,String numeroIdentificacionEmpresa){
 		super();
        this.tipoIdentificacionAfiliado=tipoIdentificacionAfiliado;
        this.numeroIdentificacionAfiliado=numeroIdentificacionAfiliado;
        this.tipoIdentificacionEmpresa=tipoIdentificacionEmpresa;
        this.numeroIdentificacionEmpresa=numeroIdentificacionEmpresa;
     	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacionAfiliado", tipoIdentificacionAfiliado)
						.queryParam("numeroIdentificacionAfiliado", numeroIdentificacionAfiliado)
						.queryParam("tipoIdentificacionEmpresa", tipoIdentificacionEmpresa)
						.queryParam("numeroIdentificacionEmpresa", numeroIdentificacionEmpresa)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Long getResult() {
		return result;
	}
  

    public TipoIdentificacionEnum getTipoIdentificacionAfiliado() {
        return this.tipoIdentificacionAfiliado;
    }

    public void setTipoIdentificacionAfiliado(TipoIdentificacionEnum tipoIdentificacionAfiliado) {
        this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
    }

    public String getNumeroIdentificacionAfiliado() {
        return this.numeroIdentificacionAfiliado;
    }

    public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
    }

    public TipoIdentificacionEnum getTipoIdentificacionEmpresa() {
        return this.tipoIdentificacionEmpresa;
    }

    public void setTipoIdentificacionEmpresa(TipoIdentificacionEnum tipoIdentificacionEmpresa) {
        this.tipoIdentificacionEmpresa = tipoIdentificacionEmpresa;
    }

    public String getNumeroIdentificacionEmpresa() {
        return this.numeroIdentificacionEmpresa;
    }

    public void setNumeroIdentificacionEmpresa(String numeroIdentificacionEmpresa) {
        this.numeroIdentificacionEmpresa = numeroIdentificacionEmpresa;
    }

   
    public void setResult(long result) {
        this.result = result;
    }
  
}