
package com.asopagos.aportes.composite.clients;
import com.asopagos.aportes.composite.dto.RegistrarNovedadConTransaccionDTO;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/AportesComposite/RegistrarNovedadConTipoTransaccionAportesCompositeAsync
 */
public class RegistrarNovedadConTipoTransaccionAportesCompositeAsync extends ServiceClient { 
   	private RegistrarNovedadConTransaccionDTO registrarNovedadConTransaccionDTO;
  	private CanalRecepcionEnum canal;
  	private TipoIdentificacionEnum tipoIdAportante;
  	private String numeroIdAportante;
  	private Boolean esTrabajadorReintegrable;
   	private Boolean esEmpleadorReintegrable;

  
  

    public RegistrarNovedadConTipoTransaccionAportesCompositeAsync(RegistrarNovedadConTransaccionDTO registrarNovedadConTransaccionDTO, CanalRecepcionEnum canal, TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante, Boolean esTrabajadorReintegrable, Boolean esEmpleadorReintegrable) {
        super();
        this.registrarNovedadConTransaccionDTO = registrarNovedadConTransaccionDTO;
        this.canal = canal;
        this.tipoIdAportante = tipoIdAportante;
        this.numeroIdAportante = numeroIdAportante;
        this.esTrabajadorReintegrable = esTrabajadorReintegrable;
        this.esEmpleadorReintegrable = esEmpleadorReintegrable;
    }
 	
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("canal", canal)
			.queryParam("tipoIdAportante", tipoIdAportante)
			.queryParam("numeroIdAportante", numeroIdAportante)
			.queryParam("esTrabajadorReintegrable", esTrabajadorReintegrable)
			.queryParam("esEmpleadorReintegrable", esEmpleadorReintegrable)
			.request(MediaType.APPLICATION_JSON)
			.post(registrarNovedadConTransaccionDTO == null ? null : Entity.json(registrarNovedadConTransaccionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}

    public RegistrarNovedadConTransaccionDTO getRegistrarNovedadConTransaccionDTO() {
        return this.registrarNovedadConTransaccionDTO;
    }

    public void setRegistrarNovedadConTransaccionDTO(RegistrarNovedadConTransaccionDTO registrarNovedadConTransaccionDTO) {
        this.registrarNovedadConTransaccionDTO = registrarNovedadConTransaccionDTO;
    }

    public CanalRecepcionEnum getCanal() {
        return this.canal;
    }

    public void setCanal(CanalRecepcionEnum canal) {
        this.canal = canal;
    }

    public TipoIdentificacionEnum getTipoIdAportante() {
        return this.tipoIdAportante;
    }

    public void setTipoIdAportante(TipoIdentificacionEnum tipoIdAportante) {
        this.tipoIdAportante = tipoIdAportante;
    }

    public String getNumeroIdAportante() {
        return this.numeroIdAportante;
    }

    public void setNumeroIdAportante(String numeroIdAportante) {
        this.numeroIdAportante = numeroIdAportante;
    }

    public Boolean isEsTrabajadorReintegrable() {
        return this.esTrabajadorReintegrable;
    }

    public Boolean getEsTrabajadorReintegrable() {
        return this.esTrabajadorReintegrable;
    }

    public void setEsTrabajadorReintegrable(Boolean esTrabajadorReintegrable) {
        this.esTrabajadorReintegrable = esTrabajadorReintegrable;
    }

    public Boolean isEsEmpleadorReintegrable() {
        return this.esEmpleadorReintegrable;
    }

    public Boolean getEsEmpleadorReintegrable() {
        return this.esEmpleadorReintegrable;
    }

    public void setEsEmpleadorReintegrable(Boolean esEmpleadorReintegrable) {
        this.esEmpleadorReintegrable = esEmpleadorReintegrable;
    }
	
  
}