/**
 * 
 */
package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.fovis.HojaArchivoCruceEnum;

/**
 * @author jocorrea
 *
 */
@XmlRootElement
public class ResultadoCruceFOVISDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5722598182565722938L;

    private Long idArchivoCruce;

    private Map<HojaArchivoCruceEnum,List<ResultadoHallazgosValidacionArchivoDTO>> listHojasFallidas;

    private Boolean excepcion;

    private Boolean extensionFallo;

    private Boolean nombresHojasFallo;

    public ResultadoCruceFOVISDTO() {
        super();
    }

    /**
     * @return the idArchivoCruce
     */
    public Long getIdArchivoCruce() {
        return idArchivoCruce;
    }

    /**
     * @param idArchivoCruce
     *        the idArchivoCruce to set
     */
    public void setIdArchivoCruce(Long idArchivoCruce) {
        this.idArchivoCruce = idArchivoCruce;
    }

    /**
     * @return the listHojasFallidas
     */
    public Map<HojaArchivoCruceEnum,List<ResultadoHallazgosValidacionArchivoDTO>> getListHojasFallidas() {
        return listHojasFallidas;
    }

    /**
     * @param listHojasFallidas
     *        the listHojasFallidas to set
     */
    public void setListHojasFallidas(Map<HojaArchivoCruceEnum,List<ResultadoHallazgosValidacionArchivoDTO>> listHojasFallidas) {
        this.listHojasFallidas = listHojasFallidas;
    }

    /**
     * @return the excepcion
     */
    public Boolean getExcepcion() {
        return excepcion;
    }

    /**
     * @param excepcion
     *        the excepcion to set
     */
    public void setExcepcion(Boolean excepcion) {
        this.excepcion = excepcion;
    }

    /**
     * @return the extensionFallo
     */
    public Boolean getExtensionFallo() {
        return extensionFallo;
    }

    /**
     * @param extensionFallo
     *        the extensionFallo to set
     */
    public void setExtensionFallo(Boolean extensionFallo) {
        this.extensionFallo = extensionFallo;
    }

    /**
     * @return the nombresHojasFallo
     */
    public Boolean getNombresHojasFallo() {
        return nombresHojasFallo;
    }

    /**
     * @param nombresHojasFallo
     *        the nombresHojasFallo to set
     */
    public void setNombresHojasFallo(Boolean nombresHojasFallo) {
        this.nombresHojasFallo = nombresHojasFallo;
    }

}
