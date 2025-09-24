package com.asopagos.dto;

import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.procesamiento.IndicePlanillaOF;
import com.asopagos.enumeraciones.pila.FasePila2Enum;
import com.asopagos.enumeraciones.pila.PerfilLecturaPilaEnum;
import com.asopagos.enumeraciones.pila.TipoCargaArchivoEnum;

/**
 * DTO de tranferencia de datos para el consumo de los servicios de PILA
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:hhernandez@heinsohn.com.co">Ricardo Hernandez Cediel.</a>
 *
 */
public class ArchivoPilaDTO extends InformacionArchivoDTO {

    /** nombre del archivo */
    //private String fileName;

    /** flujo de bytes del arhivo */
    //private byte[] dataFile;

    /** Tipo archivo a cargar */
    private PerfilLecturaPilaEnum perfilArchivo;

    /** modo como se carga el archivo automático/manual */
    private TipoCargaArchivoEnum modo;

    /** ID de índice de planilla OI */
    private Long idIndicePlanillaOI;

    /** ID de índice de planilla OF */
    private Long idIndicePlanillaOF;

    /** El indice de planilla */
    private IndicePlanilla indicePlanilla;

    /** El indice de planilla de operador financiero */
    private IndicePlanillaOF indicePlanillaOF;

    /** El Usuario que realiza la carga */
    private String usuario;

    /** Representa la fase de procesamiento de PILA 2 */
    private FasePila2Enum faseProceso;

    /** Marca para saber que la ejecución es una simulación de proceso asistido para PILA2 */
    private Boolean esSimulado;
    
    public ArchivoPilaDTO() {
    	super();
    }

    /**
     * @return the fileName
     */
    /*
     * public String getFileName() {
     * return fileName;
     * }
     */

    /**
     * @param fileName
     *        the fileName to set
     */
    /*
     * public void setFileName(String fileName) {
     * this.fileName = fileName;
     * }
     */

    /**
     * @return the dataFile
     */
    /*
     * public byte[] getDataFile() {
     * return dataFile;
     * }
     */

    /**
     * @param dataFile
     *        the dataFile to set
     */
    /*
     * public void setDataFile(byte[] dataFile) {
     * this.dataFile = dataFile;
     * }
     */

    /**
     * @return the modo
     */
    public TipoCargaArchivoEnum getModo() {
        return modo;
    }

    /**
     * @param modo
     *        the modo to set
     */
    public void setModo(TipoCargaArchivoEnum modo) {
        this.modo = modo;
    }

    /**
     * @return the perfilArchivo
     */
    public PerfilLecturaPilaEnum getPerfilArchivo() {
        return perfilArchivo;
    }

    /**
     * @param perfilArchivo
     *        the perfilArchivo to set
     */
    public void setPerfilArchivo(PerfilLecturaPilaEnum perfilArchivo) {
        this.perfilArchivo = perfilArchivo;
    }

    /**
     * @return the indicePlanilla
     */
    public IndicePlanilla getIndicePlanilla() {
        return indicePlanilla;
    }

    /**
     * @param indicePlanilla
     *        the indicePlanilla to set
     */
    public void setIndicePlanilla(IndicePlanilla indicePlanilla) {
        this.indicePlanilla = indicePlanilla;
    }

    /**
     * @return the indicePlanillaOF
     */
    public IndicePlanillaOF getIndicePlanillaOF() {
        return indicePlanillaOF;
    }

    /**
     * @param indicePlanillaOF
     *        the indicePlanillaOF to set
     */
    public void setIndicePlanillaOF(IndicePlanillaOF indicePlanillaOF) {
        this.indicePlanillaOF = indicePlanillaOF;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     *        the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the idIndicePlanillaOI
     */
    public Long getIdIndicePlanillaOI() {
        return idIndicePlanillaOI;
    }

    /**
     * @param idIndicePlanillaOI
     *        the idIndicePlanillaOI to set
     */
    public void setIdIndicePlanillaOI(Long idIndicePlanillaOI) {
        this.idIndicePlanillaOI = idIndicePlanillaOI;
    }

    /**
     * @return the idIndicePlanillaOF
     */
    public Long getIdIndicePlanillaOF() {
        return idIndicePlanillaOF;
    }

    /**
     * @param idIndicePlanillaOF
     *        the idIndicePlanillaOF to set
     */
    public void setIdIndicePlanillaOF(Long idIndicePlanillaOF) {
        this.idIndicePlanillaOF = idIndicePlanillaOF;
    }

    /**
     * @return the faseProceso
     */
    public FasePila2Enum getFaseProceso() {
        return faseProceso;
    }

    /**
     * @param faseProceso
     *        the faseProceso to set
     */
    public void setFaseProceso(FasePila2Enum faseProceso) {
        this.faseProceso = faseProceso;
    }

    /**
     * @return the esSimulado
     */
    public Boolean getEsSimulado() {
        return esSimulado;
    }

    /**
     * @param esSimulado
     *        the esSimulado to set
     */
    public void setEsSimulado(Boolean esSimulado) {
        this.esSimulado = esSimulado;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArchivoPilaDTO [perfilArchivo=");
		builder.append(perfilArchivo);
		builder.append(", modo=");
		builder.append(modo);
		builder.append(", idIndicePlanillaOI=");
		builder.append(idIndicePlanillaOI);
		builder.append(", idIndicePlanillaOF=");
		builder.append(idIndicePlanillaOF);
		builder.append(", indicePlanilla=");
		builder.append(indicePlanilla);
		builder.append(", indicePlanillaOF=");
		builder.append(indicePlanillaOF);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append(", faseProceso=");
		builder.append(faseProceso);
		builder.append(", esSimulado=");
		builder.append(esSimulado);
		builder.append("]");
		return builder.toString();
	}
}
