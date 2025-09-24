/**
 * 
 */
package com.asopagos.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;
import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.transversal.core.ConsolaEstadoProcesoMasivo;

import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.TipoProcesosMasivosEnum;
import com.asopagos.enumeraciones.core.ErroresConsolaEnum;

/**
 * <b>Descripción:</b> DTO para registrar en la consola para estado de cargue
 * procesos<b>Historia de Usuario:</b> TRA-368
 *
 * @author Julian Andres Sanchez<jusanchez@heinsohn.com.co>
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class ConsolaEstadoProcesoDTO implements Serializable {

    /**
     * SERIAL
     */
    private static final long serialVersionUID = -1928864619003450548L;


    /**
     * Descripción de tipo de proceso masivo
     */
    @NotNull
    private TipoProcesosMasivosEnum proceso;
    /**
     * Fecha inicio del cargue
     */
    private Long fechaInicio;

    /**
     * Fecha fin del cargue
     */
    private Long fechaFin;

    /**
     * Descripción del estado de cargue masivo
     */
    private EstadoCargueMasivoEnum estado;

    /**
     * Porcentaje actual de la carga de los archivos
     */
    private BigDecimal gradoAvance;


    /**
     * Identificador del registro de consola estado cargue
     */
    private Long idConsolaEstadoProcesoMasivo;
  
    /** 
     * Identificador del error del proceso 
     */
    private ErroresConsolaEnum error;

    /**
     * Constructor por defecto
     */
    public ConsolaEstadoProcesoDTO() {
        super();
    }

    /**
     *        Código de la ccf
     * @param proceso
     *        Usuario que inicio el proceso
     * @param fechaInicio
     *        Fecha en que se inicio el proceso
     * @param fechaFin
     *        Cantidad de registros con errores
     * @param estado
     *        Estado del proceso
     * @param gradoAvance
     * 
     * @param idConsolaEstadoProcesoMasivo
     * 
     * @param error
     *        Identificador del registro de consola estado cargue
     */
    public ConsolaEstadoProcesoDTO(String proceso,Date fechaInicio, Date fechaFin,
         String estado, BigDecimal gradoAvance, Long idConsolaEstadoProcesoMasivo, String error) {
        super();
        if (proceso != null) {
            this.proceso = TipoProcesosMasivosEnum.valueOf(proceso);
        }
        if (fechaInicio != null) {
            this.fechaInicio = fechaInicio.getTime();
        }
        if (fechaFin != null) {
            this.fechaFin = fechaFin.getTime();
        }
        if (estado != null) {
            this.estado = EstadoCargueMasivoEnum.valueOf(estado);
        }
        this.gradoAvance = gradoAvance;
        this.idConsolaEstadoProcesoMasivo = idConsolaEstadoProcesoMasivo;
        if (error != null && !error.equals("")) {
            this.error = ErroresConsolaEnum.valueOf(error);
        }
    }

    /**
     * Convierte la información de la entidad al DTO
     * @param ConsolaEstadoProcesoMasivo
     *        Entidad de consola cargue
     */
    public void convertToDTO(ConsolaEstadoProcesoMasivo consolaEstadoProcesoMasivo) {
        this.setEstado(consolaEstadoProcesoMasivo.getEstadoCargueMasivo());
        if (consolaEstadoProcesoMasivo.getFechaFin() != null) {
            this.setFechaFin(consolaEstadoProcesoMasivo.getFechaFin().getTime());
        }
        if (consolaEstadoProcesoMasivo.getFechaInicio() != null) {
            this.setFechaInicio(consolaEstadoProcesoMasivo.getFechaInicio().getTime());
        }
        this.setGradoAvance(consolaEstadoProcesoMasivo.getGradoAvance());
        this.setIdConsolaEstadoProcesoMasivo(consolaEstadoProcesoMasivo.getIdConsolaEstadoProcesoMasivo());
        this.setProceso(consolaEstadoProcesoMasivo.getTipoProcesoMasivo());
        this.setError(consolaEstadoProcesoMasivo.getError());
    }

    /**
     * Convierte el DTO a entidad para el manejo de persitencia
     * @return Entidad consola cargue
     */
    public ConsolaEstadoProcesoMasivo convertToEntity() {
        ConsolaEstadoProcesoMasivo consolaEstadoProcesoMasivo = new ConsolaEstadoProcesoMasivo();
        consolaEstadoProcesoMasivo.setEstadoCargueMasivo(this.getEstado());
        if (this.getFechaFin() != null) {
            consolaEstadoProcesoMasivo.setFechaFin(new Date(this.getFechaFin()));
        }
        if (this.getFechaInicio() != null) {
            consolaEstadoProcesoMasivo.setFechaInicio(new Date(this.getFechaInicio()));
        }
        consolaEstadoProcesoMasivo.setGradoAvance(this.getGradoAvance());
        consolaEstadoProcesoMasivo.setIdConsolaEstadoProcesoMasivo(this.getIdConsolaEstadoProcesoMasivo());
        consolaEstadoProcesoMasivo.setTipoProcesoMasivo(this.getProceso());
        consolaEstadoProcesoMasivo.setError(this.getError());
        return consolaEstadoProcesoMasivo;
    }

    /**
     * Copia el DTO a la entidad enviada por parametro
     * @param ConsolaEstadoProcesoMasivo
     *        Entidad previamente consultada que se va a modificar
     */
    public void copyDTOToEntity(ConsolaEstadoProcesoMasivo consolaEstadoProcesoMasivo) {
        if (this.getEstado() != null) {
            consolaEstadoProcesoMasivo.setEstadoCargueMasivo(this.getEstado());
        }
        if (this.getFechaFin() != null) {
            consolaEstadoProcesoMasivo.setFechaFin(new Date(this.getFechaFin()));
        }
        if (this.getFechaInicio() != null) {
            consolaEstadoProcesoMasivo.setFechaInicio(new Date(this.getFechaInicio()));
        }
        if (this.getGradoAvance() != null) {
            consolaEstadoProcesoMasivo.setGradoAvance(this.getGradoAvance());
        }
        if (this.getIdConsolaEstadoProcesoMasivo() != null) {
            consolaEstadoProcesoMasivo.setIdConsolaEstadoProcesoMasivo(this.getIdConsolaEstadoProcesoMasivo());
        }
        if (this.getProceso() != null) {
            consolaEstadoProcesoMasivo.setTipoProcesoMasivo(this.getProceso());
        }
        if (this.getError() != null) {
            consolaEstadoProcesoMasivo.setError(this.getError());
        }
    }

    public void limpiarNulos() {
        if (this.fechaInicio == null) this.fechaInicio = 0L;
        if (this.fechaFin == null) this.fechaFin = 0L;
        if (this.gradoAvance == null) this.gradoAvance = BigDecimal.ZERO;
        if (this.idConsolaEstadoProcesoMasivo == null) this.idConsolaEstadoProcesoMasivo = 0L;
    }

    public String[] toListString() {
        this.limpiarNulos();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String fechaInicioString = this.getFechaInicio() != null ? dateFormat.format(new Date(this.getFechaInicio())) : "";

        Calendar year2000 = Calendar.getInstance();
        year2000.set(2000, Calendar.JANUARY, 1, 0, 0);

        String fechaFinString = "";
        if (this.getFechaFin() != null) {
            Date fechaFin = new Date(this.getFechaFin());
            if (fechaFin.after(year2000.getTime())) {
                fechaFinString = dateFormat.format(fechaFin);
            }
        }

        String[] data = new String[] {
            this.getProceso() != null ? this.getProceso().getDescripcion() : "",
            fechaInicioString,
            this.getEstado() != null ? this.getEstado().name() : "",
            fechaFinString,
            gradoAvance.toString(),
            this.getError() != null ? this.getError().getDescripcion(): "",
            //this.getNumRegistroProcesado() != null ? this.getNumRegistroProcesado().toString() : "",
            //this.getNumRegistroValidados() != null ? this.getNumRegistroValidados().toString() : "",
        };
        return data;
    }

    /**
     * @return the proceso
     */
    public TipoProcesosMasivosEnum getProceso() {
        return proceso;
    }

    /**
     * @return the fechaInicio
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Long getFechaFin() {
        return fechaFin;
    }
    /**
     * @return the estado
     */
    public EstadoCargueMasivoEnum getEstado() {
        return estado;
    }

    /**
     * @return the gradoAvance
     */
    public BigDecimal getGradoAvance() {
        return gradoAvance;
    }



    /**
     * @param proceso
     *        the proceso to set
     */
    public void setProceso(TipoProcesosMasivosEnum proceso) {
        this.proceso = proceso;
    }


    /**
     * @param fechaInicio
     *        the fechaInicio to set
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @param fechaFin
     *        the fechaFin to set
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @param estado
     *        the estado to set
     */
    public void setEstado(EstadoCargueMasivoEnum estado) {
        this.estado = estado;
    }

    /**
     * @param gradoAvance
     *        the gradoAvance to set
     */
    public void setGradoAvance(BigDecimal gradoAvance) {
        this.gradoAvance = gradoAvance;
    }

    /**
     * @return the idConsolaEstadoProcesoMasivo
     */
    public Long getIdConsolaEstadoProcesoMasivo() {
        return idConsolaEstadoProcesoMasivo;
    }

    /**
     * @param idConsolaEstadoProcesoMasivo
     *        the idConsolaEstadoProcesoMasivo to set
     */
    public void setIdConsolaEstadoProcesoMasivo(Long idConsolaEstadoProcesoMasivo) {
        this.idConsolaEstadoProcesoMasivo = idConsolaEstadoProcesoMasivo;
    }
    
    /**
     * @return the idConsolaEstadoProcesoMasivo
     */
    public ErroresConsolaEnum getError() {
        return error;
    }

    /**
     * @param error
     *        the idConsolaEstadoProcesoMasivo to set
     */
    public void setError(ErroresConsolaEnum error) {
        this.error = error;
    }

}
