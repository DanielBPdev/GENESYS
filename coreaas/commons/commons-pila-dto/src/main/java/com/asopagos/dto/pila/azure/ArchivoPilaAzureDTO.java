package com.asopagos.dto.pila.azure;

public class ArchivoPilaAzureDTO {
	
	private String archivo;
	private String idPlanilla;
	private String tipo;
	
	public ArchivoPilaAzureDTO() {
	}
	
	public ArchivoPilaAzureDTO(String archivo, String idPlanilla, String tipo) {
		this.archivo = archivo;
		this.idPlanilla = idPlanilla;
		this.tipo = tipo;
	}
	
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	public String getIdPlanilla() {
		return idPlanilla;
	}
	public void setIdPlanilla(String idPlanilla) {
		this.idPlanilla = idPlanilla;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
