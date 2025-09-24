package com.asopagos.dto.webservices;

import java.io.Serializable;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Email;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.core.TipoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.enumeraciones.personas.ClaseIndependienteEnum;
import com.asopagos.enumeraciones.aportes.PorcentajeTarifaEnum;

public class AfiliarTrabajadorIndDTO extends AfiliaTrabajadorDTO implements Serializable,Cloneable {

    @NotNull(message = "El campo claseIndependiente es obligatorio.")
    private ClaseIndependienteEnum claseIndependiente;

    @NotNull(message = "El campo porcentajeAporte es obligatorio.")
    private PorcentajeTarifaEnum porcentajeAporte;

    @NotNull(message = "El campo ingresosMenensuales es obligatorio.")
    @DecimalMin(value = "1000000.00", message = "El valor del campo ingresosMenensuales debe de ser mayor a $1.000.000.00.")
    private BigDecimal ingresosMenensuales;

    public AfiliarTrabajadorIndDTO() {
        super();
    }

    public AfiliarTrabajadorIndDTO(ClaseIndependienteEnum claseIndependiente, PorcentajeTarifaEnum porcentajeAporte, BigDecimal ingresosMenensuales) {
        super();
        this.claseIndependiente = claseIndependiente;
        this.porcentajeAporte = porcentajeAporte;
        this.ingresosMenensuales = ingresosMenensuales;
    }

    public ClaseIndependienteEnum getClaseIndependiente() {
        return this.claseIndependiente;
    }

    public void setClaseIndependiente(ClaseIndependienteEnum claseIndependiente) {
        this.claseIndependiente = claseIndependiente;
    }

    public PorcentajeTarifaEnum getPorcentajeAporte() {
        return this.porcentajeAporte;
    }

    public void setPorcentajeAporte(PorcentajeTarifaEnum porcentajeAporte) {
        this.porcentajeAporte = porcentajeAporte;
    }

    public BigDecimal getIngresosMenensuales() {
        return this.ingresosMenensuales;
    }

    public void setIngresosMenensuales(BigDecimal ingresosMenensuales) {
        this.ingresosMenensuales = ingresosMenensuales;
    }

    public AfiliarTrabajadorIndDTO claseIndependiente(ClaseIndependienteEnum claseIndependiente) {
        setClaseIndependiente(claseIndependiente);
        return this;
    }

    public AfiliarTrabajadorIndDTO porcentajeAporte(PorcentajeTarifaEnum porcentajeAporte) {
        setPorcentajeAporte(porcentajeAporte);
        return this;
    }

    public AfiliarTrabajadorIndDTO ingresosMenensuales(BigDecimal ingresosMenensuales) {
        setIngresosMenensuales(ingresosMenensuales);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " claseIndependiente='" + getClaseIndependiente() + "'" +
            ", porcentajeAporte='" + getPorcentajeAporte() + "'" +
            ", ingresosMenensuales='" + getIngresosMenensuales() + "'" +
            "}";
    }
}
