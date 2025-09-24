package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import com.asopagos.entidades.transversal.core.ComunicadoTransversalBitacora;

public class GuardarRelacionComunicadoBitacoraCarteraDTO  implements Serializable {

    private Long idBitacoraCartera;

    private Long idComunicado;

    public Long getIdBitacoraCartera() {
        return this.idBitacoraCartera;
    }

    public void setIdBitacoraCartera(Long idBitacoraCartera) {
        this.idBitacoraCartera = idBitacoraCartera;
    }

    public Long getIdComunicado() {
        return this.idComunicado;
    }

    public void setIdComunicado(Long idComunicado) {
        this.idComunicado = idComunicado;
    }

    public ComunicadoTransversalBitacora convertToEntity() {
        ComunicadoTransversalBitacora comunicadoTransversalBitacora = new ComunicadoTransversalBitacora();
        comunicadoTransversalBitacora.setBitacoraCartera(this.getIdBitacoraCartera());
        comunicadoTransversalBitacora.setComunicado(this.getIdComunicado());
        return comunicadoTransversalBitacora;
    }

    @Override
    public String toString() {
        return "{" +
            " idBitacoraCartera='" + getIdBitacoraCartera() + "'" +
            ", idComunicado='" + getIdComunicado() + "'" +
            "}";
    }


}