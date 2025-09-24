package com.asopagos.aportes.masivos.dto;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import java.math.BigDecimal;
import com.asopagos.entidades.pila.masivos.MasivoDetallado;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ResultadoCotizanteAporteMasivoDTO implements Serializable{
   
    private TipoIdentificacionEnum tipoIdentificacionCotizante;

    private TipoCotizanteEnum tipoCotizante;

    private String numeroIdentificacionCotizante;

    private String numeroDocumentoCotizante;

    private String primerNombreCotizante;
    
    private String segundoNombreCotizante;

    private String primerApellidoCotizante;

    private String segundoApellidoCotizante;

    private String conceptoDePago;

    private BigDecimal ibc;

    private String ing;

    private String ret;

    private String irl;

    private String vsp;

    private String vst;

    private String sln;

    private String ige;

    private String lma;

    private String vac;

    private String sus;

    private BigDecimal salarioBasico;

    private Short diasCotizados;

    private Integer diasMora;

    private BigDecimal tarifa;

    private Integer numeroDeHorasLaboradas;

    private BigDecimal aporteObligatorio;

    private BigDecimal valorIntereses;

    private BigDecimal totalAporte;

    private Long linea;


    public MasivoDetallado toMasivoDetallado(Long idMasivoGeneral) {
        
        MasivoDetallado entidad = new MasivoDetallado();
        entidad.setMasivoGeneral(idMasivoGeneral);
        if (this.tipoCotizante != null) {
            entidad.setTipoCotizante(tipoCotizante.getCodigo());
        }
        entidad.setTipoIdentificacionCotizante(this.tipoIdentificacionCotizante);
        entidad.setNumeroIdentificacionCotizante(this.numeroDocumentoCotizante);
        entidad.setPrimerNombreCotizante(this.primerNombreCotizante);
        entidad.setSegundoNombreCotizante(this.segundoNombreCotizante);
        entidad.setPrimerApellidoCotizante(this.primerApellidoCotizante);
        entidad.setSegundoApellidoCotizante(this.segundoApellidoCotizante);
        if (this.conceptoDePago != null) {
            entidad.setConceptoPago(this.conceptoDePago);
        }
        if (this.ibc != null) {
            entidad.setIBC(this.ibc);
        }
        if (this.ing != null) {
            entidad.setNovIngreso(this.ing);
        }
        if (this.ret != null) {
            entidad.setNovRetiro(this.ret);
        }
        if (this.vsp != null) {
            entidad.setNovVSP(this.vsp);
        }
        if (this.vst != null) {
            entidad.setNovVST(this.vst);
        }
        if (this.sln != null) {
            entidad.setNovSLN(this.sln);
        }
        if (this.ige != null) {
            entidad.setNovIGE(this.ige);
        }
        if (this.lma != null) {
            entidad.setNovLMA(this.lma);

        }
        if (this.vac != null) {
            entidad.setNovVACLR(this.vac);
        }
        if (this.sus != null) {
            entidad.setNovSUS(this.sus);
        }
        if (this.valorIntereses != null) {
            entidad.setValorIntereses(this.valorIntereses);
        }
        if (this.diasMora != null) {
            entidad.setDiasMora(this.diasMora.shortValue());
        }
        entidad.setDiasCotizados(this.diasCotizados);
        entidad.setSalarioBasico(this.salarioBasico);
        entidad.setTarifa(this.tarifa);
        entidad.setNumeroHorasLaboradas(this.numeroDeHorasLaboradas);
        entidad.setAporteObligatorio(this.aporteObligatorio);
        entidad.setTotalAporte(this.totalAporte);

        return entidad;


    }


    public TipoIdentificacionEnum getTipoIdentificacionCotizante() {
        return this.tipoIdentificacionCotizante;
    }

    public void setTipoIdentificacionCotizante(TipoIdentificacionEnum tipoIdentificacionCotizante) {
        this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
    }

    public TipoCotizanteEnum getTipoCotizante() {
        return this.tipoCotizante;
    }

    public void setTipoCotizante(TipoCotizanteEnum tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }

    public String getNumeroIdentificacionCotizante() {
        return this.numeroIdentificacionCotizante;
    }

    public void setNumeroIdentificacionCotizante(String numeroIdentificacionCotizante) {
        this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
    }

    public String getNumeroDocumentoCotizante() {
        return this.numeroDocumentoCotizante;
    }

    public void setNumeroDocumentoCotizante(String numeroDocumentoCotizante) {
        this.numeroDocumentoCotizante = numeroDocumentoCotizante;
    }

    public String getPrimerNombreCotizante() {
        return this.primerNombreCotizante;
    }

    public void setPrimerNombreCotizante(String primerNombreCotizante) {
        this.primerNombreCotizante = primerNombreCotizante;
    }

    public String getSegundoNombreCotizante() {
        return this.segundoNombreCotizante;
    }

    public void setSegundoNombreCotizante(String segundoNombreCotizante) {
        this.segundoNombreCotizante = segundoNombreCotizante;
    }

    public String getPrimerApellidoCotizante() {
        return this.primerApellidoCotizante;
    }

    public void setPrimerApellidoCotizante(String primerApellidoCotizante) {
        this.primerApellidoCotizante = primerApellidoCotizante;
    }

    public String getSegundoApellidoCotizante() {
        return this.segundoApellidoCotizante;
    }

    public void setSegundoApellidoCotizante(String segundoApellidoCotizante) {
        this.segundoApellidoCotizante = segundoApellidoCotizante;
    }

    public String getConceptoDePago() {
        return this.conceptoDePago;
    }

    public void setConceptoDePago(String conceptoDePago) {
        this.conceptoDePago = conceptoDePago;
    }

    public BigDecimal getIbc() {
        return this.ibc;
    }

    public void setIbc(BigDecimal ibc) {
        this.ibc = ibc;
    }

    public String getIng() {
        return this.ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getRet() {
        return this.ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getIrl() {
        return this.irl;
    }

    public void setIrl(String irl) {
        this.irl = irl;
    }

    public String getVsp() {
        return this.vsp;
    }

    public void setVsp(String vsp) {
        this.vsp = vsp;
    }

    public String getVst() {
        return this.vst;
    }

    public void setVst(String vst) {
        this.vst = vst;
    }

    public String getSln() {
        return this.sln;
    }

    public void setSln(String sln) {
        this.sln = sln;
    }

    public String getIge() {
        return this.ige;
    }

    public void setIge(String ige) {
        this.ige = ige;
    }

    public String getLma() {
        return this.lma;
    }

    public void setLma(String lma) {
        this.lma = lma;
    }

    public String getVac() {
        return this.vac;
    }

    public void setVac(String vac) {
        this.vac = vac;
    }

    public String getSus() {
        return this.sus;
    }

    public void setSus(String sus) {
        this.sus = sus;
    }

    public BigDecimal getSalarioBasico() {
        return this.salarioBasico;
    }

    public void setSalarioBasico(BigDecimal salarioBasico) {
        this.salarioBasico = salarioBasico;
    }

    public Short getDiasCotizados() {
        return this.diasCotizados;
    }

    public void setDiasCotizados(Short diasCotizados) {
        this.diasCotizados = diasCotizados;
    }

    public Integer getDiasMora() {
        return this.diasMora;
    }

    public void setDiasMora(Integer diasMora) {
        this.diasMora = diasMora;
    }

    public BigDecimal getTarifa() {
        return this.tarifa;
    }

    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    public Integer getNumeroDeHorasLaboradas() {
        return this.numeroDeHorasLaboradas;
    }

    public void setNumeroDeHorasLaboradas(Integer numeroDeHorasLaboradas) {
        this.numeroDeHorasLaboradas = numeroDeHorasLaboradas;
    }

    public BigDecimal getAporteObligatorio() {
        return this.aporteObligatorio;
    }

    public void setAporteObligatorio(BigDecimal aporteObligatorio) {
        this.aporteObligatorio = aporteObligatorio;
    }

    public BigDecimal getValorIntereses() {
        return this.valorIntereses;
    }

    public void setValorIntereses(BigDecimal valorIntereses) {
        this.valorIntereses = valorIntereses;
    }

    public BigDecimal getTotalAporte() {
        return this.totalAporte;
    }

    public void setTotalAporte(BigDecimal totalAporte) {
        this.totalAporte = totalAporte;
    }

    public Long getLinea() {
        return this.linea;
    }

    public void setLinea(Long linea) {
        this.linea = linea;
    }


    
   
    
}
