package com.asopagos.dto.modelo;

import java.util.Objects;

public class OficinaJuzgadoDTO {
    private Long ctaId;
    private String nroCtaJudicial;
    private String codJuzgado;
    private String nombreJuzgado;

    public OficinaJuzgadoDTO(Long ctaId, String nroCtaJudicial, String codJuzgado, String nombreJuzgado) {
        this.ctaId = ctaId;
        this.nroCtaJudicial = nroCtaJudicial;
        this.codJuzgado = codJuzgado;
        this.nombreJuzgado = nombreJuzgado;
    }

    public OficinaJuzgadoDTO() {
    }

    public Long getCtaId() {
        return ctaId;
    }

    public void setCtaId(Long ctaId) {
        this.ctaId = ctaId;
    }

    public String getNroCtaJudicial() {
        return nroCtaJudicial;
    }

    public void setNroCtaJudicial(String nroCtaJudicial) {
        this.nroCtaJudicial = nroCtaJudicial;
    }

    public String getCodJuzgado() {
        return codJuzgado;
    }

    public void setCodJuzgado(String codJuzgado) {
        this.codJuzgado = codJuzgado;
    }

    public String getNombreJuzgado() {
        return nombreJuzgado;
    }

    public void setNombreJuzgado(String nombreJuzgado) {
        this.nombreJuzgado = nombreJuzgado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OficinaJuzgadoDTO that = (OficinaJuzgadoDTO) o;
        return Objects.equals(ctaId, that.ctaId) && Objects.equals(nroCtaJudicial, that.nroCtaJudicial) && Objects.equals(codJuzgado, that.codJuzgado) && Objects.equals(nombreJuzgado, that.nombreJuzgado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ctaId, nroCtaJudicial, codJuzgado, nombreJuzgado);
    }

    @Override
    public String toString() {
        return "OficinaJuzgadoDTO{" +
                "ctaId=" + ctaId +
                ", nroCtaJudicial='" + nroCtaJudicial + '\'' +
                ", codJuzgado='" + codJuzgado + '\'' +
                ", nombreJuzgado='" + nombreJuzgado + '\'' +
                '}';
    }
}
