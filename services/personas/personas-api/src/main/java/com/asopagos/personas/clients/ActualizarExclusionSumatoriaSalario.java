package com.asopagos.personas.clients;

import com.asopagos.dto.ActualizarExclusionSumatoriaSalarioDTO;
import com.asopagos.services.common.ServiceClient;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Método que hace la petición REST al servicio POST
 * /rest/personas/actualizarExclusionSumatoriaSalario
 */
public class ActualizarExclusionSumatoriaSalario extends ServiceClient {

  private ActualizarExclusionSumatoriaSalarioDTO actualizarExclusionSumatoriaSalarioDTO;

  public ActualizarExclusionSumatoriaSalario(
    ActualizarExclusionSumatoriaSalarioDTO actualizarExclusionSumatoriaSalarioDTO
  ) {
    super();
    this.actualizarExclusionSumatoriaSalarioDTO =
      actualizarExclusionSumatoriaSalarioDTO;
  }

  @Override
  protected Response invoke(WebTarget webTarget, String path) {
    Response response = webTarget
      .path(path)
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.json(actualizarExclusionSumatoriaSalarioDTO));
    return response;
  }

  @Override
  protected void getResultData(Response response) {}

  public ActualizarExclusionSumatoriaSalarioDTO getActualizarExclusionSumatoriaSalarioDTO() {
    return this.actualizarExclusionSumatoriaSalarioDTO;
  }

  public void setActualizarExclusionSumatoriaSalarioDTO(
    ActualizarExclusionSumatoriaSalarioDTO actualizarExclusionSumatoriaSalarioDTO
  ) {
    this.actualizarExclusionSumatoriaSalarioDTO =
      actualizarExclusionSumatoriaSalarioDTO;
  }
}
