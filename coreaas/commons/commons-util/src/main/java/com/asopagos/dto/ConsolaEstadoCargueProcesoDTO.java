/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.transversal.core.ConsolaEstadoCargueMasivo;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;
import java.util.ArrayList;

/**
 * <b>Descripción:</b> DTO para registrar en la consola para estado de cargue
 * procesos<b>Historia de Usuario:</b> TRA-368
 *
 * @author Julian Andres Sanchez<jusanchez@heinsohn.com.co>
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class ConsolaEstadoCargueProcesoDTO implements Serializable {

    /**
     * SERIAL
     */
    private static final long serialVersionUID = -1928864619003450548L;

    /**
     * Código de la caja de compensación familiar
     */
    private String ccf;

    /**
     * Descripción de tipo de proceso masivo
     */
    @NotNull
    private TipoProcesoMasivoEnum proceso;

    /**
     * Usuario que ejecuto el proceso
     */
    private String usuario;

    /**
     * Fecha inicio del cargue
     */
    private Long fechaInicio;

    /**
     * Fecha fin del cargue
     */
    private Long fechaFin;

    /**
     * Número de registros que deben ser procesados
     */
    private Long numRegistroObjetivo;

    /**
     * Valor que indica la suma de los campos “N° registros validos” y el campo” N° de
     * registros con error” siendo así la misma cantidad del campo “N° de
     * registros objetivo”
     */
    private Long numRegistroProcesado;

    /**
     * Número de registros que no fueron procesados correctamente
     */
    private Long numRegistroValidados;

    /**
     * Número de registros que fueron procesados sin inconsistencias
     */
    private Long numRegistroConErrores;

    /**
     * Descripción del estado de cargue masivo
     */
    private EstadoCargueMasivoEnum estado;

    /**
     * Porcentaje actual de la carga de los archivos
     */
    private BigDecimal gradoAvance;

    /**
     * Identificador del registro de carga de archivo (relaciona los errores)
     */
    private Long fileLoaded_id;

    /**
     * Cargue_id no tiene tiene relación con nadie, es el identificador del
     * cargue que se realizo en el proceso
     */
    @NotNull
    private Long cargue_id;

    /**
     * Identificador del archivo en el ECM
     */
    private String identificacionECM;

    /**
     * Nombrel del archivo cargado
     */
    private String nombreArchivo;

    /**
     * Identificador del registro de consola estado cargue
     */
    private Long idConsolaEstadoCargueMasivo;

    /**
     * Identificador de la definición de archivo cargado
     */
    private Long fileDefinition_id;

    /**
     * Listado de errores presentados en el archivo.
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstErroresArhivo;

    /**
     * Constructor por defecto
     */
    public ConsolaEstadoCargueProcesoDTO() {
        super();
    }

    /**
     * Constructor del DTO a partir de la entidad
     * @param consolaEstadoCargueMasivo
     *        Entidad de consola de cargue
     */
    public ConsolaEstadoCargueProcesoDTO(ConsolaEstadoCargueMasivo consolaEstadoCargueMasivo) {
        super();
        this.convertToDTO(consolaEstadoCargueMasivo);
    }

    /**
     * Constructor usado para las consultas de la consola
     * @param ccf
     *        Código de la ccf
     * @param proceso
     *        Descripción del proceso
     * @param usuario
     *        Usuario que inicio el proceso
     * @param fechaInicio
     *        Fecha en que se inicio el proceso
     * @param fechaFin
     *        Fecha en que se finalizo el proceso
     * @param numRegistroObjetivo
     *        Cantidad de registros a validar
     * @param numRegistroProcesado
     *        Cantidad de registros procesados
     * @param numRegistroValidados
     *        Cantidad de registros validados
     * @param numRegistroConErrores
     *        Cantidad de registros con errores
     * @param estado
     *        Estado del proceso
     * @param gradoAvance
     *        Porcentaje de avance del proceso
     * @param fileLoaded_id
     *        Identificador de la carga de archivo
     * @param nombreArchivo
     *        Nombre del archivo del proceso
     * @param cargue_id
     *        Codigo identificador del cargue
     * @param identificacionECM
     *        Identificador del archivo cargado en el ECM
     * @param idConsolaEstadoCargueMasivo
     *        Identificador del registro de consola estado cargue
     */
    public ConsolaEstadoCargueProcesoDTO(String ccf, String proceso, String usuario, Date fechaInicio, Date fechaFin,
            Long numRegistroObjetivo, Long numRegistroProcesado, Long numRegistroValidados, Long numRegistroConErrores, String estado,
            BigDecimal gradoAvance, Long fileLoaded_id, String nombreArchivo, Long cargue_id, String identificacionECM,
            Long idConsolaEstadoCargueMasivo, Long fileDefinition_id) {
        super();
        this.ccf = ccf;
        if (proceso != null) {
            this.proceso = TipoProcesoMasivoEnum.valueOf(proceso);
        }
        this.usuario = usuario;
        if (fechaInicio != null) {
            this.fechaInicio = fechaInicio.getTime();
        }
        if (fechaFin != null) {
            this.fechaFin = fechaFin.getTime();
        }
        this.numRegistroObjetivo = numRegistroObjetivo;
        this.numRegistroProcesado = numRegistroProcesado;
        this.numRegistroValidados = numRegistroValidados;
        this.numRegistroConErrores = numRegistroConErrores;
        if (estado != null) {
            this.estado = EstadoCargueMasivoEnum.valueOf(estado);
        }
        this.gradoAvance = gradoAvance;
        this.fileLoaded_id = fileLoaded_id;
        this.nombreArchivo = nombreArchivo;
        this.cargue_id = cargue_id;
        this.identificacionECM = identificacionECM;
        this.idConsolaEstadoCargueMasivo = idConsolaEstadoCargueMasivo;
        this.fileDefinition_id = fileDefinition_id;
    }

    /**
     * Convierte la información de la entidad al DTO
     * @param consolaEstadoCargueMasivo
     *        Entidad de consola cargue
     */
    public void convertToDTO(ConsolaEstadoCargueMasivo consolaEstadoCargueMasivo) {
        this.setCargue_id(consolaEstadoCargueMasivo.getCargueId());
        this.setCcf(consolaEstadoCargueMasivo.getCcf());
        this.setEstado(consolaEstadoCargueMasivo.getEstadoCargueMasivo());
        if (consolaEstadoCargueMasivo.getFechaFin() != null) {
            this.setFechaFin(consolaEstadoCargueMasivo.getFechaFin().getTime());
        }
        if (consolaEstadoCargueMasivo.getFechaInicio() != null) {
            this.setFechaInicio(consolaEstadoCargueMasivo.getFechaInicio().getTime());
        }
        this.setFileLoaded_id(consolaEstadoCargueMasivo.getFileLoadedId());
        this.setGradoAvance(consolaEstadoCargueMasivo.getGradoAvance());
        this.setIdConsolaEstadoCargueMasivo(consolaEstadoCargueMasivo.getIdConsolaEstadoCargueMasivo());
        this.setIdentificacionECM(consolaEstadoCargueMasivo.getIdentificacionECM());
        this.setNombreArchivo(consolaEstadoCargueMasivo.getNombreArchivo());
        this.setNumRegistroConErrores(consolaEstadoCargueMasivo.getNumRegistroConErrores());
        this.setNumRegistroObjetivo(consolaEstadoCargueMasivo.getNumRegistroObjetivo());
        this.setNumRegistroProcesado(consolaEstadoCargueMasivo.getNumRegistroProcesado());
        this.setNumRegistroValidados(consolaEstadoCargueMasivo.getNumRegistroValidos());
        this.setProceso(consolaEstadoCargueMasivo.getTipoProcesoMasivo());
        this.setUsuario(consolaEstadoCargueMasivo.getUsuario());
    }

    /**
     * Convierte el DTO a entidad para el manejo de persitencia
     * @return Entidad consola cargue
     */
    public ConsolaEstadoCargueMasivo convertToEntity() {
        ConsolaEstadoCargueMasivo consolaEstadoCargueMasivo = new ConsolaEstadoCargueMasivo();
        consolaEstadoCargueMasivo.setCargueId(this.getCargue_id());
        consolaEstadoCargueMasivo.setCcf(this.getCcf());
        consolaEstadoCargueMasivo.setEstadoCargueMasivo(this.getEstado());
        if (this.getFechaFin() != null) {
            consolaEstadoCargueMasivo.setFechaFin(new Date(this.getFechaFin()));
        }
        if (this.getFechaInicio() != null) {
            consolaEstadoCargueMasivo.setFechaInicio(new Date(this.getFechaInicio()));
        }
        consolaEstadoCargueMasivo.setFileLoaded_id(this.getFileLoaded_id());
        consolaEstadoCargueMasivo.setGradoAvance(this.getGradoAvance());
        consolaEstadoCargueMasivo.setIdConsolaEstadoCargueMasivo(this.getIdConsolaEstadoCargueMasivo());
        consolaEstadoCargueMasivo.setIdentificacionECM(this.getIdentificacionECM());
        consolaEstadoCargueMasivo.setNombreArchivo(this.getNombreArchivo());
        consolaEstadoCargueMasivo.setNumRegistroConErrores(this.getNumRegistroConErrores());
        consolaEstadoCargueMasivo.setNumRegistroObjetivo(this.getNumRegistroObjetivo());
        consolaEstadoCargueMasivo.setNumRegistroProcesado(this.getNumRegistroProcesado());
        consolaEstadoCargueMasivo.setNumRegistroValidos(this.getNumRegistroValidados());
        consolaEstadoCargueMasivo.setTipoProcesoMasivo(this.getProceso());
        consolaEstadoCargueMasivo.setUsuario(this.getUsuario());
        return consolaEstadoCargueMasivo;
    }

    /**
     * Copia el DTO a la entidad enviada por parametro
     * @param consolaEstadoCargueMasivo
     *        Entidad previamente consultada que se va a modificar
     */
    public void copyDTOToEntity(ConsolaEstadoCargueMasivo consolaEstadoCargueMasivo) {
        if (this.getCargue_id() != null) {
            consolaEstadoCargueMasivo.setCargueId(this.getCargue_id());
        }
        if (this.getCcf() != null) {
            consolaEstadoCargueMasivo.setCcf(this.getCcf());
        }
        if (this.getEstado() != null) {
            consolaEstadoCargueMasivo.setEstadoCargueMasivo(this.getEstado());
        }
        if (this.getFechaFin() != null) {
            consolaEstadoCargueMasivo.setFechaFin(new Date(this.getFechaFin()));
        }
        if (this.getFechaInicio() != null) {
            consolaEstadoCargueMasivo.setFechaInicio(new Date(this.getFechaInicio()));
        }
        if (this.getFileLoaded_id() != null) {
            consolaEstadoCargueMasivo.setFileLoaded_id(this.getFileLoaded_id());
        }
        if (this.getGradoAvance() != null) {
            consolaEstadoCargueMasivo.setGradoAvance(this.getGradoAvance());
        }
        if (this.getIdConsolaEstadoCargueMasivo() != null) {
            consolaEstadoCargueMasivo.setIdConsolaEstadoCargueMasivo(this.getIdConsolaEstadoCargueMasivo());
        }
        if (this.getIdentificacionECM() != null) {
            consolaEstadoCargueMasivo.setIdentificacionECM(this.getIdentificacionECM());
        }
        if (this.getNombreArchivo() != null) {
            consolaEstadoCargueMasivo.setNombreArchivo(this.getNombreArchivo());
        }
        if (this.getNumRegistroConErrores() != null) {
            consolaEstadoCargueMasivo.setNumRegistroConErrores(this.getNumRegistroConErrores());
        }
        if (this.getNumRegistroObjetivo() != null) {
            consolaEstadoCargueMasivo.setNumRegistroObjetivo(this.getNumRegistroObjetivo());
        }
        if (this.getNumRegistroProcesado() != null) {
            consolaEstadoCargueMasivo.setNumRegistroProcesado(this.getNumRegistroProcesado());
        }
        if (this.getNumRegistroValidados() != null) {
            consolaEstadoCargueMasivo.setNumRegistroValidos(this.getNumRegistroValidados());
        }
        if (this.getProceso() != null) {
            consolaEstadoCargueMasivo.setTipoProcesoMasivo(this.getProceso());
        }
        if (this.getUsuario() != null) {
            consolaEstadoCargueMasivo.setUsuario(this.getUsuario());
        }
    }

    public void limpiarNulos() {
        if (this.ccf == null) this.ccf = "";
        if (this.usuario == null) this.usuario = "";
        if (this.fechaInicio == null) this.fechaInicio = 0L;
        if (this.fechaFin == null) this.fechaFin = 0L;
        if (this.numRegistroObjetivo == null) this.numRegistroObjetivo = 0L;
        if (this.numRegistroProcesado == null) this.numRegistroProcesado = 0L;
        if (this.numRegistroValidados == null) this.numRegistroValidados = 0L;
        if (this.numRegistroConErrores == null) this.numRegistroConErrores = 0L;
        if (this.gradoAvance == null) this.gradoAvance = BigDecimal.ZERO;
        if (this.fileLoaded_id == null) this.fileLoaded_id = 0L;
        if (this.cargue_id == null) this.cargue_id = 0L;
        if (this.identificacionECM == null) this.identificacionECM = "";
        if (this.nombreArchivo == null) this.nombreArchivo = "";
        if (this.idConsolaEstadoCargueMasivo == null) this.idConsolaEstadoCargueMasivo = 0L;
        if (this.fileDefinition_id == null) this.fileDefinition_id = 0L;
        if (this.lstErroresArhivo == null) this.lstErroresArhivo = new ArrayList<>();
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
            this.getNombreArchivo(),
            fechaInicioString,
            this.getEstado() != null ? this.getEstado().getDescripcion() : "",
            fechaFinString,
            this.getNumRegistroObjetivo() != null ? this.getNumRegistroObjetivo().toString() : "",
            this.getNumRegistroConErrores() != null ? this.getNumRegistroConErrores().toString() : "",
            //this.getNumRegistroProcesado() != null ? this.getNumRegistroProcesado().toString() : "",
            //this.getNumRegistroValidados() != null ? this.getNumRegistroValidados().toString() : "",
        };
        return data;
    }


    /**
     * @return the ccf
     */
    public String getCcf() {
        return ccf;
    }

    /**
     * @return the proceso
     */
    public TipoProcesoMasivoEnum getProceso() {
        return proceso;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
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
     * @return the numRegistroObjetivo
     */
    public Long getNumRegistroObjetivo() {
        return numRegistroObjetivo;
    }

    /**
     * @return the numRegistroProcesado
     */
    public Long getNumRegistroProcesado() {
        return numRegistroProcesado;
    }

    /**
     * @return the numRegistroValidados
     */
    public Long getNumRegistroValidados() {
        return numRegistroValidados;
    }

    /**
     * @return the numRegistroConErrores
     */
    public Long getNumRegistroConErrores() {
        return numRegistroConErrores;
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
     * @return the fileLoaded_id
     */
    public Long getFileLoaded_id() {
        return fileLoaded_id;
    }

    /**
     * @return the cargue_id
     */
    public Long getCargue_id() {
        return cargue_id;
    }

    /**
     * @return the identificacionECM
     */
    public String getIdentificacionECM() {
        return identificacionECM;
    }

    /**
     * @param ccf
     *        the ccf to set
     */
    public void setCcf(String ccf) {
        this.ccf = ccf;
    }

    /**
     * @param proceso
     *        the proceso to set
     */
    public void setProceso(TipoProcesoMasivoEnum proceso) {
        this.proceso = proceso;
    }

    /**
     * @param usuario
     *        the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
     * @param numRegistroObjetivo
     *        the numRegistroObjetivo to set
     */
    public void setNumRegistroObjetivo(Long numRegistroObjetivo) {
        this.numRegistroObjetivo = numRegistroObjetivo;
    }

    /**
     * @param numRegistroProcesado
     *        the numRegistroProcesado to set
     */
    public void setNumRegistroProcesado(Long numRegistroProcesado) {
        this.numRegistroProcesado = numRegistroProcesado;
    }

    /**
     * @param numRegistroValidados
     *        the numRegistroValidados to set
     */
    public void setNumRegistroValidados(Long numRegistroValidados) {
        this.numRegistroValidados = numRegistroValidados;
    }

    /**
     * @param numRegistroConErrores
     *        the numRegistroConErrores to set
     */
    public void setNumRegistroConErrores(Long numRegistroConErrores) {
        this.numRegistroConErrores = numRegistroConErrores;
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
     * @param fileLoaded_id
     *        the fileLoaded_id to set
     */
    public void setFileLoaded_id(Long fileLoaded_id) {
        this.fileLoaded_id = fileLoaded_id;
    }

    /**
     * @param cargue_id
     *        the cargue_id to set
     */
    public void setCargue_id(Long cargue_id) {
        this.cargue_id = cargue_id;
    }

    /**
     * @param identificacionECM
     *        the identificacionECM to set
     */
    public void setIdentificacionECM(String identificacionECM) {
        this.identificacionECM = identificacionECM;
    }

    /**
     * @return the lstErroresArhivo
     */
    public List<ResultadoHallazgosValidacionArchivoDTO> getLstErroresArhivo() {
        return lstErroresArhivo;
    }

    /**
     * @param lstErroresArhivo
     *        the lstErroresArhivo to set
     */
    public void setLstErroresArhivo(List<ResultadoHallazgosValidacionArchivoDTO> lstErroresArhivo) {
        this.lstErroresArhivo = lstErroresArhivo;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo
     *        the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the idConsolaEstadoCargueMasivo
     */
    public Long getIdConsolaEstadoCargueMasivo() {
        return idConsolaEstadoCargueMasivo;
    }

    /**
     * @param idConsolaEstadoCargueMasivo
     *        the idConsolaEstadoCargueMasivo to set
     */
    public void setIdConsolaEstadoCargueMasivo(Long idConsolaEstadoCargueMasivo) {
        this.idConsolaEstadoCargueMasivo = idConsolaEstadoCargueMasivo;
    }

    /**
     * @return the fileDefinition_id
     */
    public Long getFileDefinition_id() {
        return fileDefinition_id;
    }

    /**
     * @param fileDefinition_id the fileDefinition_id to set
     */
    public void setFileDefinition_id(Long fileDefinition_id) {
        this.fileDefinition_id = fileDefinition_id;
    }

}
