package com.asopagos.historicos.dto;

import com.asopagos.entidades.transversal.core.EstadisticasGenesys;

import java.io.Serializable;
import java.math.BigDecimal;

public class HistoricoEstadisticasGenesysDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer cantidadEmpresas;
    private Integer cantidadAfiliados;
    private Integer cantidadBeneficiarios;
    private Integer cantidadEmpresasMorosas;
    private String liquidacionMesUno;
    private Integer liquidacionConDerechoUno;
    private Integer liquidacionSinDerechoUno;
    private String liquidacionMesDos;
    private Integer liquidacionSinDerechoDos;
    private Integer liquidacionConDerechoDos;
    private String liquidacionMesTres;
    private Integer liquidacionConDerechoTres;
    private Integer liquidacionSinDerechoTres;
    private String liquidacionMesCuatro;
    private Integer liquidacionConDerechoCuatro;
    private Integer liquidacionSinDerechoCuatro;
    private String liquidacionMesCinco;
    private Integer liquidacionConDerechoCinco;
    private Integer liquidacionSinDerechoCinco;
    private String liquidacionMesSeis;
    private Integer liquidacionConDerechoSeis;
    private Integer liquidacionSinDerechoSeis;
    private Integer liquidacionesMasivas;
    private Integer liquidacionesPorFallecimiento;
    private Integer liquidacionesPorAjuste;
    private Integer liquidacionesPorReconocimiento;
    private Integer postulacionesAsignadas;
    private Integer PostulacionesRecibidasEnElAnio;
    private Integer postulacionesPorAsignar;
    private Integer postulacionesLegalizadas;
    private BigDecimal valorAsignacionUltimoMes;
    private Integer AfiliacionesSinTerminarEnAnio;
    private Integer aportesPlanillasNotificadas ;
    private Integer aportesManuales;
    private Integer aportesCorreciones;
    private Integer aportesDevoluciones;
    private String aporteMesUno;
    private Integer aportesRelacionadosUno;
    private Integer aportesRegistradosUno;
    private String aporteMesDos;
    private Integer aportesRelacionadosDos;
    private Integer aportesRegistradosDos;
    private String aporteMesTres;
    private Integer aportesRelacionadosTres;
    private Integer aportesRegistradosTres;
    private String aporteMesCuatro;
    private Integer aportesRelacionadosCuatro;
    private Integer aportesRegistradosCuatro;
    private String aporteMesCinco;
    private Integer aportesRelacionadosCinco;
    private Integer aportesRegistradosCinco;
    private String aporteMesSeis;
    private Integer aportesRelacionadosSeis;
    private Integer aportesRegistradosSeis;

    private HistoricoAportesDTO historicoAportesDTO;

    private HistoricoLiquidacionesDTO historicoLiquidacionesDTO;

    public HistoricoEstadisticasGenesysDTO() {
        super();
    }
    public HistoricoEstadisticasGenesysDTO(EstadisticasGenesys estadisticasGenesys) {
        convertToDTO(estadisticasGenesys);
    }

    public Integer getCantidadEmpresas() {
        return cantidadEmpresas;
    }

    public void setCantidadEmpresas(Integer cantidadEmpresas) {
        this.cantidadEmpresas = cantidadEmpresas;
    }

    public Integer getCantidadAfiliados() {
        return cantidadAfiliados;
    }

    public void setCantidadAfiliados(Integer cantidadAfiliados) {
        this.cantidadAfiliados = cantidadAfiliados;
    }

    public Integer getCantidadBeneficiarios() {
        return cantidadBeneficiarios;
    }

    public void setCantidadBeneficiarios(Integer cantidadBeneficiarios) {
        this.cantidadBeneficiarios = cantidadBeneficiarios;
    }

    public Integer getCantidadEmpresasMorosas() {
        return cantidadEmpresasMorosas;
    }

    public void setCantidadEmpresasMorosas(Integer cantidadEmpresasMorosas) {
        this.cantidadEmpresasMorosas = cantidadEmpresasMorosas;
    }

    public String getLiquidacionMesUno() {
        return liquidacionMesUno;
    }

    public void setLiquidacionMesUno(String liquidacionMesUno) {
        this.liquidacionMesUno = liquidacionMesUno;
    }

    public Integer getLiquidacionConDerechoUno() {
        return liquidacionConDerechoUno;
    }

    public void setLiquidacionConDerechoUno(Integer liquidacionConDerechoUno) {
        this.liquidacionConDerechoUno = liquidacionConDerechoUno;
    }

    public Integer getLiquidacionSinDerechoUno() {
        return liquidacionSinDerechoUno;
    }

    public void setLiquidacionSinDerechoUno(Integer liquidacionSinDerechoUno) {
        this.liquidacionSinDerechoUno = liquidacionSinDerechoUno;
    }

    public String getLiquidacionMesDos() {
        return liquidacionMesDos;
    }

    public void setLiquidacionMesDos(String liquidacionMesDos) {
        this.liquidacionMesDos = liquidacionMesDos;
    }

    public Integer getLiquidacionSinDerechoDos() {
        return liquidacionSinDerechoDos;
    }

    public void setLiquidacionSinDerechoDos(Integer liquidacionSinDerechoDos) {
        this.liquidacionSinDerechoDos = liquidacionSinDerechoDos;
    }

    public Integer getLiquidacionConDerechoDos() {
        return liquidacionConDerechoDos;
    }

    public void setLiquidacionConDerechoDos(Integer liquidacionConDerechoDos) {
        this.liquidacionConDerechoDos = liquidacionConDerechoDos;
    }

    public String getLiquidacionMesTres() {
        return liquidacionMesTres;
    }

    public void setLiquidacionMesTres(String liquidacionMesTres) {
        this.liquidacionMesTres = liquidacionMesTres;
    }

    public Integer getLiquidacionConDerechoTres() {
        return liquidacionConDerechoTres;
    }

    public void setLiquidacionConDerechoTres(Integer liquidacionConDerechoTres) {
        this.liquidacionConDerechoTres = liquidacionConDerechoTres;
    }

    public Integer getLiquidacionSinDerechoTres() {
        return liquidacionSinDerechoTres;
    }

    public void setLiquidacionSinDerechoTres(Integer liquidacionSinDerechoTres) {
        this.liquidacionSinDerechoTres = liquidacionSinDerechoTres;
    }

    public String getLiquidacionMesCuatro() {
        return liquidacionMesCuatro;
    }

    public void setLiquidacionMesCuatro(String liquidacionMesCuatro) {
        this.liquidacionMesCuatro = liquidacionMesCuatro;
    }

    public Integer getLiquidacionConDerechoCuatro() {
        return liquidacionConDerechoCuatro;
    }

    public void setLiquidacionConDerechoCuatro(Integer liquidacionConDerechoCuatro) {
        this.liquidacionConDerechoCuatro = liquidacionConDerechoCuatro;
    }

    public Integer getLiquidacionSinDerechoCuatro() {
        return liquidacionSinDerechoCuatro;
    }

    public void setLiquidacionSinDerechoCuatro(Integer liquidacionSinDerechoCuatro) {
        this.liquidacionSinDerechoCuatro = liquidacionSinDerechoCuatro;
    }

    public String getLiquidacionMesCinco() {
        return liquidacionMesCinco;
    }

    public void setLiquidacionMesCinco(String liquidacionMesCinco) {
        this.liquidacionMesCinco = liquidacionMesCinco;
    }

    public Integer getLiquidacionConDerechoCinco() {
        return liquidacionConDerechoCinco;
    }

    public void setLiquidacionConDerechoCinco(Integer liquidacionConDerechoCinco) {
        this.liquidacionConDerechoCinco = liquidacionConDerechoCinco;
    }

    public Integer getLiquidacionSinDerechoCinco() {
        return liquidacionSinDerechoCinco;
    }

    public void setLiquidacionSinDerechoCinco(Integer liquidacionSinDerechoCinco) {
        this.liquidacionSinDerechoCinco = liquidacionSinDerechoCinco;
    }

    public String getLiquidacionMesSeis() {
        return liquidacionMesSeis;
    }

    public void setLiquidacionMesSeis(String liquidacionMesSeis) {
        this.liquidacionMesSeis = liquidacionMesSeis;
    }

    public Integer getLiquidacionConDerechoSeis() {
        return liquidacionConDerechoSeis;
    }

    public void setLiquidacionConDerechoSeis(Integer liquidacionConDerechoSeis) {
        this.liquidacionConDerechoSeis = liquidacionConDerechoSeis;
    }

    public Integer getLiquidacionSinDerechoSeis() {
        return liquidacionSinDerechoSeis;
    }

    public void setLiquidacionSinDerechoSeis(Integer liquidacionSinDerechoSeis) {
        this.liquidacionSinDerechoSeis = liquidacionSinDerechoSeis;
    }

    public Integer getLiquidacionesMasivas() {
        return liquidacionesMasivas;
    }

    public void setLiquidacionesMasivas(Integer liquidacionesMasivas) {
        this.liquidacionesMasivas = liquidacionesMasivas;
    }

    public Integer getLiquidacionesPorFallecimiento() {
        return liquidacionesPorFallecimiento;
    }

    public void setLiquidacionesPorFallecimiento(Integer liquidacionesPorFallecimiento) {
        this.liquidacionesPorFallecimiento = liquidacionesPorFallecimiento;
    }

    public Integer getLiquidacionesPorAjuste() {
        return liquidacionesPorAjuste;
    }

    public void setLiquidacionesPorAjuste(Integer liquidacionesPorAjuste) {
        this.liquidacionesPorAjuste = liquidacionesPorAjuste;
    }

    public Integer getLiquidacionesPorReconocimiento() {
        return liquidacionesPorReconocimiento;
    }

    public void setLiquidacionesPorReconocimiento(Integer liquidacionesPorReconocimiento) {
        this.liquidacionesPorReconocimiento = liquidacionesPorReconocimiento;
    }

    public Integer getPostulacionesAsignadas() {
        return postulacionesAsignadas;
    }

    public void setPostulacionesAsignadas(Integer postulacionesAsignadas) {
        this.postulacionesAsignadas = postulacionesAsignadas;
    }

    public Integer getPostulacionesRecibidasEnElAnio() {
        return PostulacionesRecibidasEnElAnio;
    }

    public void setPostulacionesRecibidasEnElAnio(Integer PostulacionesRecibidasEnElAnio) {
        this.PostulacionesRecibidasEnElAnio = PostulacionesRecibidasEnElAnio;
    }

    public Integer getPostulacionesPorAsignar() {
        return postulacionesPorAsignar;
    }

    public void setPostulacionesPorAsignar(Integer postulacionesPorAsignar) {
        this.postulacionesPorAsignar = postulacionesPorAsignar;
    }

    public Integer getPostulacionesLegalizadas() {
        return postulacionesLegalizadas;
    }

    public void setPostulacionesLegalizadas(Integer postulacionesLegalizadas) {
        this.postulacionesLegalizadas = postulacionesLegalizadas;
    }

    public BigDecimal getValorAsignacionUltimoMes() {
        return valorAsignacionUltimoMes;
    }

    public void setValorAsignacionUltimoMes(BigDecimal valorAsignacionUltimoMes) {
        this.valorAsignacionUltimoMes = valorAsignacionUltimoMes;
    }

    public Integer getAfiliacionesSinTerminarEnAnio() {
        return AfiliacionesSinTerminarEnAnio;
    }

    public void setAfiliacionesSinTerminarEnAnio(Integer AfiliacionesSinTerminarEnAnio) {
        this.AfiliacionesSinTerminarEnAnio = AfiliacionesSinTerminarEnAnio;
    }

    public Integer getAportesPlanillasNotificadas() {
        return aportesPlanillasNotificadas;
    }

    public void setAportesPlanillasNotificadas(Integer aportesPlanillasNotificadas) {
        this.aportesPlanillasNotificadas = aportesPlanillasNotificadas;
    }

    public Integer getAportesManuales() {
        return aportesManuales;
    }

    public void setAportesManuales(Integer aportesManuales) {
        this.aportesManuales = aportesManuales;
    }

    public Integer getAportesCorreciones() {
        return aportesCorreciones;
    }

    public void setAportesCorreciones(Integer aportesCorreciones) {
        this.aportesCorreciones = aportesCorreciones;
    }

    public Integer getAportesDevoluciones() {
        return aportesDevoluciones;
    }

    public void setAportesDevoluciones(Integer aportesDevoluciones) {
        this.aportesDevoluciones = aportesDevoluciones;
    }

    public String getAporteMesUno() {
        return aporteMesUno;
    }

    public void setAporteMesUno(String aporteMesUno) {
        this.aporteMesUno = aporteMesUno;
    }

    public Integer getAportesRelacionadosUno() {
        return aportesRelacionadosUno;
    }

    public void setAportesRelacionadosUno(Integer aportesRelacionadosUno) {
        this.aportesRelacionadosUno = aportesRelacionadosUno;
    }

    public Integer getAportesRegistradosUno() {
        return aportesRegistradosUno;
    }

    public void setAportesRegistradosUno(Integer aportesRegistradosUno) {
        this.aportesRegistradosUno = aportesRegistradosUno;
    }

    public String getAporteMesDos() {
        return aporteMesDos;
    }

    public void setAporteMesDos(String aporteMesDos) {
        this.aporteMesDos = aporteMesDos;
    }

    public Integer getAportesRelacionadosDos() {
        return aportesRelacionadosDos;
    }

    public void setAportesRelacionadosDos(Integer aportesRelacionadosDos) {
        this.aportesRelacionadosDos = aportesRelacionadosDos;
    }

    public Integer getAportesRegistradosDos() {
        return aportesRegistradosDos;
    }

    public void setAportesRegistradosDos(Integer aportesRegistradosDos) {
        this.aportesRegistradosDos = aportesRegistradosDos;
    }

    public String getAporteMesTres() {
        return aporteMesTres;
    }

    public void setAporteMesTres(String aporteMesTres) {
        this.aporteMesTres = aporteMesTres;
    }

    public Integer getAportesRelacionadosTres() {
        return aportesRelacionadosTres;
    }

    public void setAportesRelacionadosTres(Integer aportesRelacionadosTres) {
        this.aportesRelacionadosTres = aportesRelacionadosTres;
    }

    public Integer getAportesRegistradosTres() {
        return aportesRegistradosTres;
    }

    public void setAportesRegistradosTres(Integer aportesRegistradosTres) {
        this.aportesRegistradosTres = aportesRegistradosTres;
    }

    public String getAporteMesCuatro() {
        return aporteMesCuatro;
    }

    public void setAporteMesCuatro(String aporteMesCuatro) {
        this.aporteMesCuatro = aporteMesCuatro;
    }

    public Integer getAportesRelacionadosCuatro() {
        return aportesRelacionadosCuatro;
    }

    public void setAportesRelacionadosCuatro(Integer aportesRelacionadosCuatro) {
        this.aportesRelacionadosCuatro = aportesRelacionadosCuatro;
    }

    public Integer getAportesRegistradosCuatro() {
        return aportesRegistradosCuatro;
    }

    public void setAportesRegistradosCuatro(Integer aportesRegistradosCuatro) {
        this.aportesRegistradosCuatro = aportesRegistradosCuatro;
    }

    public String getAporteMesCinco() {
        return aporteMesCinco;
    }

    public void setAporteMesCinco(String aporteMesCinco) {
        this.aporteMesCinco = aporteMesCinco;
    }

    public Integer getAportesRelacionadosCinco() {
        return aportesRelacionadosCinco;
    }

    public void setAportesRelacionadosCinco(Integer aportesRelacionadosCinco) {
        this.aportesRelacionadosCinco = aportesRelacionadosCinco;
    }

    public Integer getAportesRegistradosCinco() {
        return aportesRegistradosCinco;
    }

    public void setAportesRegistradosCinco(Integer aportesRegistradosCinco) {
        this.aportesRegistradosCinco = aportesRegistradosCinco;
    }

    public String getAporteMesSeis() {
        return aporteMesSeis;
    }

    public void setAporteMesSeis(String aporteMesSeis) {
        this.aporteMesSeis = aporteMesSeis;
    }

    public Integer getAportesRelacionadosSeis() {
        return aportesRelacionadosSeis;
    }

    public void setAportesRelacionadosSeis(Integer aportesRelacionadosSeis) {
        this.aportesRelacionadosSeis = aportesRelacionadosSeis;
    }

    public Integer getAportesRegistradosSeis() {
        return aportesRegistradosSeis;
    }

    public void setAportesRegistradosSeis(Integer aportesRegistradosSeis) {
        this.aportesRegistradosSeis = aportesRegistradosSeis;
    }

    public HistoricoAportesDTO getHistoricoAportesDTO() {
        return historicoAportesDTO;
    }

    public void setHistoricoAportesDTO(HistoricoAportesDTO historicoAportesDTO) {
        this.historicoAportesDTO = historicoAportesDTO;
    }

    public HistoricoLiquidacionesDTO getHistoricoLiquidacionesDTO() {
        return historicoLiquidacionesDTO;
    }

    public void setHistoricoLiquidacionesDTO(HistoricoLiquidacionesDTO historicoLiquidacionesDTO) {
        this.historicoLiquidacionesDTO = historicoLiquidacionesDTO;
    }


    public void convertToDTO(EstadisticasGenesys estadisticasGenesys){
        HistoricoAportesDTO historicoAportesDTO1 = new HistoricoAportesDTO();
        historicoAportesDTO1.convertToHistoricoAportesDTO(estadisticasGenesys);

        HistoricoLiquidacionesDTO historicoLiquidacionesDTO1 = new HistoricoLiquidacionesDTO();
        historicoLiquidacionesDTO1.convertToHistoricoLiquidacionesDTO(estadisticasGenesys);

        this.setCantidadEmpresas(estadisticasGenesys.getCantidadEmpresas());
        this.setCantidadAfiliados(estadisticasGenesys.getCantidadAfiliados());
        this.setCantidadBeneficiarios(estadisticasGenesys.getCantidadBeneficiarios());
        this.setCantidadEmpresasMorosas(estadisticasGenesys.getCantidadEmpresasMorosas());
        this.setPostulacionesAsignadas(estadisticasGenesys.getPostulacionesAsignadas());
        this.setPostulacionesRecibidasEnElAnio(estadisticasGenesys.getPostulacionesRecibidasEnElAnio());
        this.setPostulacionesPorAsignar(estadisticasGenesys.getPostulacionesPorAsignar());
        this.setPostulacionesLegalizadas(estadisticasGenesys.getPostulacionesLegalizadas());
        this.setValorAsignacionUltimoMes(estadisticasGenesys.getValorAsignacionUltimoMes());
        this.setAfiliacionesSinTerminarEnAnio(estadisticasGenesys.getAfiliacionesSinTerminarEnAnio());
        this.setLiquidacionesMasivas(estadisticasGenesys.getLiquidacionesMasivas());
        this.setLiquidacionesPorFallecimiento(estadisticasGenesys.getLiquidacionesPorFallecimiento());
        this.setLiquidacionesPorAjuste(estadisticasGenesys.getLiquidacionesPorAjuste());
        this.setLiquidacionesPorReconocimiento(estadisticasGenesys.getLiquidacionesPorReconocimiento());
        this.setAportesPlanillasNotificadas(estadisticasGenesys.getAportesPlanillasNotificadas());
        this.setAportesManuales(estadisticasGenesys.getAportesManuales());
        this.setAportesCorreciones(estadisticasGenesys.getAportesCorreciones());
        this.setAportesDevoluciones(estadisticasGenesys.getAportesDevoluciones());
        this.setHistoricoAportesDTO(historicoAportesDTO1);
        this.setHistoricoLiquidacionesDTO(historicoLiquidacionesDTO1);
    }

    @Override
    public java.lang.String toString() {
        return "HistoricoEstadisticasGenesysDTO{" +
                "cantidadEmpresas=" + getCantidadEmpresas() +
                ", cantidadAfiliados=" + getCantidadAfiliados() +
                ", cantidadBeneficiarios=" + getCantidadBeneficiarios() +
                ", cantidadEmpresasMorosas=" + cantidadEmpresasMorosas +
                ", liquidacionMesUno='" + liquidacionMesUno + '\'' +
                ", liquidacionConDerechoUno=" + liquidacionConDerechoUno +
                ", liquidacionSinDerechoUno=" + liquidacionSinDerechoUno +
                ", liquidacionMesDos='" + liquidacionMesDos + '\'' +
                ", liquidacionSinDerechoDos=" + liquidacionSinDerechoDos +
                ", liquidacionConDerechoDos=" + liquidacionConDerechoDos +
                ", liquidacionMesTres='" + liquidacionMesTres + '\'' +
                ", liquidacionConDerechoTres=" + liquidacionConDerechoTres +
                ", liquidacionSinDerechoTres=" + liquidacionSinDerechoTres +
                ", liquidacionMesCuatro='" + liquidacionMesCuatro + '\'' +
                ", liquidacionConDerechoCuatro=" + liquidacionConDerechoCuatro +
                ", liquidacionSinDerechoCuatro=" + liquidacionSinDerechoCuatro +
                ", liquidacionMesCinco='" + liquidacionMesCinco + '\'' +
                ", liquidacionConDerechoCinco=" + liquidacionConDerechoCinco +
                ", liquidacionSinDerechoCinco=" + liquidacionSinDerechoCinco +
                ", liquidacionMesSeis='" + liquidacionMesSeis + '\'' +
                ", liquidacionConDerechoSeis=" + liquidacionConDerechoSeis +
                ", liquidacionSinDerechoSeis=" + liquidacionSinDerechoSeis +
                ", liquidacionesMasivas=" + liquidacionesMasivas +
                ", liquidacionesPorFallecimiento=" + liquidacionesPorFallecimiento +
                ", liquidacionesPorAjuste=" + liquidacionesPorAjuste +
                ", liquidacionesPorReconocimiento=" + liquidacionesPorReconocimiento +
                ", postulacionesAsignadas=" + postulacionesAsignadas +
                ", PostulacionesRecibidasEnElAnio=" + PostulacionesRecibidasEnElAnio +
                ", postulacionesPorAsignar=" + postulacionesPorAsignar +
                ", postulacionesLegalizadas=" + postulacionesLegalizadas +
                ", valorAsignacionUltimoMes=" + valorAsignacionUltimoMes +
                ", AfiliacionesSinTerminarEnAnio=" + AfiliacionesSinTerminarEnAnio +
                ", aportesPlanillasNotificadas=" + aportesPlanillasNotificadas +
                ", aportesManuales=" + aportesManuales +
                ", aportesCorreciones=" + aportesCorreciones +
                ", aportesDevoluciones=" + aportesDevoluciones +
                ", aporteMesUno='" + aporteMesUno + '\'' +
                ", aportesRelacionadosUno=" + aportesRelacionadosUno +
                ", aportesRegistradosUno=" + aportesRegistradosUno +
                ", aporteMesDos='" + aporteMesDos + '\'' +
                ", aportesRelacionadosDos=" + aportesRelacionadosDos +
                ", aportesRegistradosDos=" + aportesRegistradosDos +
                ", aporteMesTres='" + aporteMesTres + '\'' +
                ", aportesRelacionadosTres=" + aportesRelacionadosTres +
                ", aportesRegistradosTres=" + aportesRegistradosTres +
                ", aporteMesCuatro='" + aporteMesCuatro + '\'' +
                ", aportesRelacionadosCuatro=" + aportesRelacionadosCuatro +
                ", aportesRegistradosCuatro=" + aportesRegistradosCuatro +
                ", aporteMesCinco='" + aporteMesCinco + '\'' +
                ", aportesRelacionadosCinco=" + aportesRelacionadosCinco +
                ", aportesRegistradosCinco=" + aportesRegistradosCinco +
                ", aporteMesSeis='" + aporteMesSeis + '\'' +
                ", aportesRelacionadosSeis=" + aportesRelacionadosSeis +
                ", aportesRegistradosSeis=" + aportesRegistradosSeis +
                '}';
    }
}