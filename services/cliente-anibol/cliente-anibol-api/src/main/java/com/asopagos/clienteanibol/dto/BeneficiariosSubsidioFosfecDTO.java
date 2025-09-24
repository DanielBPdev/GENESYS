package com.asopagos.clienteanibol.dto;

import java.io.Serializable;
import java.util.Date;

import com.asopagos.clienteanibol.enums.BeneficiarioMPCEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoCumplimientoEnum;

public class BeneficiariosSubsidioFosfecDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private TipoIdentificacionEnum tipoIdentificacionCesante;
	private String numeroIdentificacionCesante;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private Long fechaNacimiento;
	private GeneroEnum genero;
	private String codigoCCF;
	private BeneficiarioMPCEnum beneficiarioMPC;
	private TipoCumplimientoEnum cumpleRutaEmpleabilidad ;
	private Long fechaLiquidacionBeneficio;
	private Long periodoBeneficioLiquidado;
	private Integer beneficiosLiquidados;
	private Integer cuotasMonetariasLiquidadas;
	private Integer beneficiariosCuotaMonetaria;
	private Integer cuotasIncentivoEconomicoAhorroCesantias;
	private Integer bonosLiquidados;
	private Integer montoLiquidadoSalud;
	private Integer montoLiquidadoPension;
	private Integer montoLiquidadoCuotaMonetaria;
	private Integer montoLiquidadoCesantias;
	private Integer montoLiquidadoBonoAlimentacion;
	private Integer valorTotalPrestacionesEconomicas;
	private Boolean renunciaVoluntaria;
	private Long fechaRenuncia;
	private Boolean perdidaBeneficio;
	private Long fechaPerdidaBeneficio;
	private Boolean terminacionBeneficio;
	private Long fechaTerminacionBeneficio;
	private Integer vigenciaPostulante;
	
	public TipoIdentificacionEnum getTipoIdentificacionCesante() {
		return tipoIdentificacionCesante;
	}
	public void setTipoIdentificacionCesante(TipoIdentificacionEnum tipoIdentificacionCesante) {
		this.tipoIdentificacionCesante = tipoIdentificacionCesante;
	}
	public String getNumeroIdentificacionCesante() {
		return numeroIdentificacionCesante;
	}
	public void setNumeroIdentificacionCesante(String numeroIdentificacionCesante) {
		this.numeroIdentificacionCesante = numeroIdentificacionCesante;
	}
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public Long getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Long fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public GeneroEnum getGenero() {
		return genero;
	}
	public void setGenero(GeneroEnum genero) {
		this.genero = genero;
	}
	public String getCodigoCCF() {
		return codigoCCF;
	}
	public void setCodigoCCF(String codigoCCF) {
		this.codigoCCF = codigoCCF;
	}
	public BeneficiarioMPCEnum getBeneficiarioMPC() {
		return beneficiarioMPC;
	}
	public void setBeneficiarioMPC(BeneficiarioMPCEnum beneficiarioMPC) {
		this.beneficiarioMPC = beneficiarioMPC;
	}
	public TipoCumplimientoEnum getCumpleRutaEmpleabilidad() {
		return cumpleRutaEmpleabilidad;
	}
	public void setCumpleRutaEmpleabilidad(TipoCumplimientoEnum cumpleRutaEmpleabilidad) {
		this.cumpleRutaEmpleabilidad = cumpleRutaEmpleabilidad;
	}
	public Long getFechaLiquidacionBeneficio() {
		return fechaLiquidacionBeneficio;
	}
	public void setFechaLiquidacionBeneficio(Long fechaLiquidacionBeneficio) {
		this.fechaLiquidacionBeneficio = fechaLiquidacionBeneficio;
	}
	public Long getPeriodoBeneficioLiquidado() {
		return periodoBeneficioLiquidado;
	}
	public void setPeriodoBeneficioLiquidado(Long periodoBeneficioLiquidado) {
		this.periodoBeneficioLiquidado = periodoBeneficioLiquidado;
	}
	public Integer getBeneficiosLiquidados() {
		return beneficiosLiquidados;
	}
	public void setBeneficiosLiquidados(Integer beneficiosLiquidados) {
		this.beneficiosLiquidados = beneficiosLiquidados;
	}
	public Integer getCuotasMonetariasLiquidadas() {
		return cuotasMonetariasLiquidadas;
	}
	public void setCuotasMonetariasLiquidadas(Integer cuotasMonetariasLiquidadas) {
		this.cuotasMonetariasLiquidadas = cuotasMonetariasLiquidadas;
	}
	public Integer getBeneficiariosCuotaMonetaria() {
		return beneficiariosCuotaMonetaria;
	}
	public void setBeneficiariosCuotaMonetaria(Integer beneficiariosCuotaMonetaria) {
		this.beneficiariosCuotaMonetaria = beneficiariosCuotaMonetaria;
	}
	public Integer getCuotasIncentivoEconomicoAhorroCesantias() {
		return cuotasIncentivoEconomicoAhorroCesantias;
	}
	public void setCuotasIncentivoEconomicoAhorroCesantias(Integer cuotasIncentivoEconomicoAhorroCesantias) {
		this.cuotasIncentivoEconomicoAhorroCesantias = cuotasIncentivoEconomicoAhorroCesantias;
	}
	public Integer getBonosLiquidados() {
		return bonosLiquidados;
	}
	public void setBonosLiquidados(Integer bonosLiquidados) {
		this.bonosLiquidados = bonosLiquidados;
	}
	public Integer getMontoLiquidadoSalud() {
		return montoLiquidadoSalud;
	}
	public void setMontoLiquidadoSalud(Integer montoLiquidadoSalud) {
		this.montoLiquidadoSalud = montoLiquidadoSalud;
	}
	public Integer getMontoLiquidadoPension() {
		return montoLiquidadoPension;
	}
	public void setMontoLiquidadoPension(Integer montoLiquidadoPension) {
		this.montoLiquidadoPension = montoLiquidadoPension;
	}
	public Integer getMontoLiquidadoCuotaMonetaria() {
		return montoLiquidadoCuotaMonetaria;
	}
	public void setMontoLiquidadoCuotaMonetaria(Integer montoLiquidadoCuotaMonetaria) {
		this.montoLiquidadoCuotaMonetaria = montoLiquidadoCuotaMonetaria;
	}
	public Integer getMontoLiquidadoCesantias() {
		return montoLiquidadoCesantias;
	}
	public void setMontoLiquidadoCesantias(Integer montoLiquidadoCesantias) {
		this.montoLiquidadoCesantias = montoLiquidadoCesantias;
	}
	public Integer getMontoLiquidadoBonoAlimentacion() {
		return montoLiquidadoBonoAlimentacion;
	}
	public void setMontoLiquidadoBonoAlimentacion(Integer montoLiquidadoBonoAlimentacion) {
		this.montoLiquidadoBonoAlimentacion = montoLiquidadoBonoAlimentacion;
	}
	public Integer getValorTotalPrestacionesEconomicas() {
		return valorTotalPrestacionesEconomicas;
	}
	public void setValorTotalPrestacionesEconomicas(Integer valorTotalPrestacionesEconomicas) {
		this.valorTotalPrestacionesEconomicas = valorTotalPrestacionesEconomicas;
	}
	public Boolean getRenunciaVoluntaria() {
		return renunciaVoluntaria;
	}
	public void setRenunciaVoluntaria(Boolean renunciaVoluntaria) {
		this.renunciaVoluntaria = renunciaVoluntaria;
	}
	public Long getFechaRenuncia() {
		return fechaRenuncia;
	}
	public void setFechaRenuncia(Long fechaRenuncia) {
		this.fechaRenuncia = fechaRenuncia;
	}
	public Boolean getPerdidaBeneficio() {
		return perdidaBeneficio;
	}
	public void setPerdidaBeneficio(Boolean perdidaBeneficio) {
		this.perdidaBeneficio = perdidaBeneficio;
	}
	public Long getFechaPerdidaBeneficio() {
		return fechaPerdidaBeneficio;
	}
	public void setFechaPerdidaBeneficio(Long fechaPerdidaBeneficio) {
		this.fechaPerdidaBeneficio = fechaPerdidaBeneficio;
	}
	public Boolean getTerminacionBeneficio() {
		return terminacionBeneficio;
	}
	public void setTerminacionBeneficio(Boolean terminacionBeneficio) {
		this.terminacionBeneficio = terminacionBeneficio;
	}
	public Long getFechaTerminacionBeneficio() {
		return fechaTerminacionBeneficio;
	}
	public void setFechaTerminacionBeneficio(Long fechaTerminacionBeneficio) {
		this.fechaTerminacionBeneficio = fechaTerminacionBeneficio;
	}
	public Integer getVigenciaPostulante() {
		return vigenciaPostulante;
	}
	public void setVigenciaPostulante(Integer vigenciaPostulante) {
		this.vigenciaPostulante = vigenciaPostulante;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public BeneficiariosSubsidioFosfecDTO(){}
	
	public BeneficiariosSubsidioFosfecDTO(TipoIdentificacionEnum tipoIdentificacionCesante,
			String numeroIdentificacionCesante,
			String primerNombre,
			String segundoNombre,
			String primerApellido,
			String segundoApellido,
			Long fechaNacimiento,
			GeneroEnum genero,
			String codigoCCF,
			BeneficiarioMPCEnum beneficiarioMPC,
			TipoCumplimientoEnum cumpleRutaEmpleabilidad,
			Long fechaLiquidacionBeneficio,
			Long periodoBeneficioLiquidado,
			Integer beneficiosLiquidados,
			Integer cuotasMonetariasLiquidadas,
			Integer beneficiariosCuotaMonetaria,
			Integer cuotasIncentivoEconomicoAhorroCesantias,
			Integer bonosLiquidados,
			Integer montoLiquidadoSalud,
			Integer montoLiquidadoPension,
			Integer montoLiquidadoCuotaMonetaria,
			Integer montoLiquidadoBonoAlimentacion,
			Integer valorTotalPrestacionesEconomicas,
			Boolean renunciaVoluntaria,
			Long fechaRenuncia,
			Boolean perdidaBeneficio,
			Long fechaPerdidaBeneficio,
			Boolean terminacionBeneficio,
			Long fechaTerminacionBeneficio,
			Integer vigenciaPostulante
			){
		this.tipoIdentificacionCesante = tipoIdentificacionCesante;
		this.numeroIdentificacionCesante = numeroIdentificacionCesante;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre ;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.fechaNacimiento = fechaNacimiento;
		this.genero = genero;
		this.codigoCCF = codigoCCF;
		this.beneficiarioMPC = beneficiarioMPC;
		this.cumpleRutaEmpleabilidad = cumpleRutaEmpleabilidad;
		this.fechaLiquidacionBeneficio =  fechaLiquidacionBeneficio;
		this.periodoBeneficioLiquidado = periodoBeneficioLiquidado;
		this.beneficiosLiquidados = beneficiosLiquidados;
		this.cuotasMonetariasLiquidadas = cuotasMonetariasLiquidadas;
		this.beneficiariosCuotaMonetaria = beneficiariosCuotaMonetaria;
		this.cuotasIncentivoEconomicoAhorroCesantias = cuotasIncentivoEconomicoAhorroCesantias;
		this.bonosLiquidados = bonosLiquidados;
		this.montoLiquidadoSalud = montoLiquidadoSalud;
		this.montoLiquidadoPension = montoLiquidadoPension;
		this.montoLiquidadoCuotaMonetaria =montoLiquidadoCuotaMonetaria;
		this.montoLiquidadoBonoAlimentacion = montoLiquidadoBonoAlimentacion;
		this.valorTotalPrestacionesEconomicas = valorTotalPrestacionesEconomicas;
		this.renunciaVoluntaria = renunciaVoluntaria;
		this.fechaRenuncia = fechaRenuncia;
		this.perdidaBeneficio = perdidaBeneficio;
		this.fechaPerdidaBeneficio = fechaPerdidaBeneficio ;
		this.terminacionBeneficio = terminacionBeneficio;
		this.fechaTerminacionBeneficio = fechaTerminacionBeneficio;
		this.vigenciaPostulante = vigenciaPostulante;
	}

}
