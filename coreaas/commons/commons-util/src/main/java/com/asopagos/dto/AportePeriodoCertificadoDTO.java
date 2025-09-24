package com.asopagos.dto;

import java.io.Serializable;

import com.asopagos.entidades.ccf.personas.Empleador;

public class AportePeriodoCertificadoDTO implements Serializable{

	private static final long serialVersionUID = 7037260301181935627L;

	private String valorInteres;
	private String fechaRecaudo;
	private String numeroPlanilla;
	private String valorTotalAporte;
	private String periodoAporte;
	public String getPeriodoAporte() {
		return periodoAporte;
	}
	public void setPeriodoAporte(String periodoAporte) {
		this.periodoAporte = periodoAporte;
	}
	private String tipoAfiliacion;
	private Empleador emp;
	
	public Empleador getEmp() {
		return emp;
	}
	public void setEmp(Empleador emp) {
		this.emp = emp;
	}
	public String getFechaRecaudo() {
		return fechaRecaudo;
	}
	public void setFechaRecaudo(String fechaRecaudo) {
		this.fechaRecaudo = fechaRecaudo;
	}
	public String getNumeroPlanilla() {
		return numeroPlanilla;
	}
	public void setNumeroPlanilla(String numeroPlanilla) {
		this.numeroPlanilla = numeroPlanilla;
	}
	public String getValorTotalAporte() {
		return valorTotalAporte;
	}
	public void setValorTotalAporte(String valorTotalAporte) {
		this.valorTotalAporte = valorTotalAporte;
	}
	public String getTipoAfiliacion() {
		return tipoAfiliacion;
	}
	public void setTipoAfiliacion(String tipoAfiliacion) {
		this.tipoAfiliacion = tipoAfiliacion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getValorInteres() {
		return valorInteres;
	} 
		
	public void setValorInteres(String valorInteres) {this.valorInteres = valorInteres;}

	@Override
	public String toString() {
		return "{" +
			" valorInteres='" + getValorInteres() + "'" +
			", fechaRecaudo='" + getFechaRecaudo() + "'" +
			", numeroPlanilla='" + getNumeroPlanilla() + "'" +
			", valorTotalAporte='" + getValorTotalAporte() + "'" +
			", periodoAporte='" + getPeriodoAporte() + "'" +
			", tipoAfiliacion='" + getTipoAfiliacion() + "'" +
			", emp='" + getEmp() + "'" +
			"}";
	}
}


