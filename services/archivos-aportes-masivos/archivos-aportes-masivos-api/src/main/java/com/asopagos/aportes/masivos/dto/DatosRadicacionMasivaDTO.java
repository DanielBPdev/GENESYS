package com.asopagos.aportes.masivos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.aportes.OrigenAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.core.DecisionSiNoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.entidades.pila.masivos.MasivoGeneral;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;



/**
 * Datos de aportes masivos relacionada con el aportante
 * author: Juan David Quintero juan.quintero@asopagos.com
 */
@XmlRootElement
public class DatosRadicacionMasivaDTO implements Serializable {

    private TipoIdentificacionEnum tipoIdentificacion;

    private String numeroIdentificacion;

    private String razonSocialAportante;

    private Long idDepartamento;

    private Long idMunicipio;

    private String razonSocial;

    private Date periodoPago;

    private Date fechaRecepcionAporte;

    private Date fechaDePago;

    private TipoSolicitanteMovimientoAporteEnum tipoAportante;

    
    public MasivoGeneral toMasivosGeneral(Long idArchivo) {
        MasivoGeneral entidad = new MasivoGeneral();
        entidad.setIdMasivoArchivo(idArchivo);
        entidad.setTipoIdentificacionAportante(this.tipoIdentificacion);
        entidad.setNumeroIdentificacionAportante(this.numeroIdentificacion);
        if (this.idDepartamento != null) {
            entidad.setCodDepartamento(this.idDepartamento.toString());
        }
        if (this.idMunicipio != null) {
            entidad.setCodMunicipio(this.idMunicipio.toString());
        }
        if (this.idDepartamento != null) {
            entidad.setRazonSocial(this.razonSocial);
        }
        if (this.periodoPago != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM");

            entidad.setPeriodoPago(df.format(this.periodoPago));
        }
        if (this.fechaRecepcionAporte != null) {
            entidad.setFechaRecepcionAporte(this.fechaRecepcionAporte);
        }
        if (this.fechaDePago != null) {
            entidad.setFechaPago(this.fechaDePago);
        }
        if (this.tipoAportante != null) {
            entidad.setTipoAportante(this.tipoAportante);
        }
        return entidad;
        

    }
    


    public TipoIdentificacionEnum getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return this.numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getRazonSocialAportante() {
        return this.razonSocialAportante;
    }

    public void setRazonSocialAportante(String razonSocialAportante) {
        this.razonSocialAportante = razonSocialAportante;
    }

    public Long getIdDepartamento() {
        return this.idDepartamento;
    }

    public void setIdDepartamento(Long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Long getIdMunicipio() {
        return this.idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Date getPeriodoPago() {
        return this.periodoPago;
    }

    public void setPeriodoPago(Date periodoPago) {
        this.periodoPago = periodoPago;
    }

    public Date getFechaRecepcionAporte() {
        return this.fechaRecepcionAporte;
    }

    public void setFechaRecepcionAporte(Date fechaRecepcionAporte) {
        this.fechaRecepcionAporte = fechaRecepcionAporte;
    }

    public Date getFechaDePago() {
        return this.fechaDePago;
    }

    public void setFechaDePago(Date fechaDePago) {
        this.fechaDePago = fechaDePago;
    }


    public TipoSolicitanteMovimientoAporteEnum getTipoAportante() {
        return this.tipoAportante;
    }

    public void setTipoAportante(TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        this.tipoAportante = tipoAportante;
    }

    @Override
    public String toString() {
        return "{" +
            " tipoIdentificacion='" + getTipoIdentificacion() + "'" +
            ", numeroIdentificacion='" + getNumeroIdentificacion() + "'" +
            ", razonSocialAportante='" + getRazonSocialAportante() + "'" +
            ", idDepartamento='" + getIdDepartamento() + "'" +
            ", idMunicipio='" + getIdMunicipio() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", periodoPago='" + getPeriodoPago() + "'" +
            ", fechaRecepcionAporte='" + getFechaRecepcionAporte() + "'" +
            ", fechaDePago='" + getFechaDePago() + "'" +
            ", tipoAportante='" + getTipoAportante() + "'" +
            "}";
    }
    
}
