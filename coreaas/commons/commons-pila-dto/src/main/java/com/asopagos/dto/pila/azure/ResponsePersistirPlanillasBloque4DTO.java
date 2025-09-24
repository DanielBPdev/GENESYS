package com.asopagos.dto.pila.azure;

import java.util.List;

public class ResponsePersistirPlanillasBloque4DTO {

	private String runId;
	private String status;
	private List<ArchivoPilaAzureDTO> archivosPilaAzure;

	public ResponsePersistirPlanillasBloque4DTO() {
	}

	public ResponsePersistirPlanillasBloque4DTO(String runId, String status,
			List<ArchivoPilaAzureDTO> archivosPilaAzure) {
		super();
		this.runId = runId;
		this.status = status;
		this.archivosPilaAzure = archivosPilaAzure;
	}

	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ArchivoPilaAzureDTO> getArchivosPilaAzure() {
		return archivosPilaAzure;
	}

	public void setArchivosPilaAzure(List<ArchivoPilaAzureDTO> archivosPilaAzure) {
		this.archivosPilaAzure = archivosPilaAzure;
	}
}