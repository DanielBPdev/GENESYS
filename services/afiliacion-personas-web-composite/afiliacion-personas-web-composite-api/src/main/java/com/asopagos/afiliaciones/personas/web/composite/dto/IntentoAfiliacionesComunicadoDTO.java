package com.asopagos.afiliaciones.personas.web.composite.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;

/**
 * <b>Descripción:</b> DTO para el registro de intentos de afiliación de beneficiarios de un afiliado
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Juan Diego Ocampo Q. <jocampo@heinsohn.com.co>
 */
@XmlRootElement
public class IntentoAfiliacionesComunicadoDTO implements Serializable {

    private List<IntentoAfiliacionInDTO> intentoAfiliaciones;

    private String idInstanciaProceso;

    private String correoElectronicoEmpleador;

    private String correoElectronicoAfiliado;

    private EtiquetaPlantillaComunicadoEnum comunicadoEmpleador;

    private EtiquetaPlantillaComunicadoEnum comunicadoAfiliado;

    /**
     * @return the idInstanciaProceso
     */
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso
     *        the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    /**
     * @return the correoElectronicoEmpleador
     */
    public String getCorreoElectronicoEmpleador() {
        return correoElectronicoEmpleador;
    }

    /**
     * @param correoElectronicoEmpleador
     *        the correoElectronicoEmpleador to set
     */
    public void setCorreoElectronicoEmpleador(String correoElectronicoEmpleador) {
        this.correoElectronicoEmpleador = correoElectronicoEmpleador;
    }

    /**
     * @return the comunicadoEmpleador
     */
    public EtiquetaPlantillaComunicadoEnum getComunicadoEmpleador() {
        return comunicadoEmpleador;
    }

    /**
     * @param comunicadoEmpleador
     *        the comunicadoEmpleador to set
     */
    public void setComunicadoEmpleador(EtiquetaPlantillaComunicadoEnum comunicadoEmpleador) {
        this.comunicadoEmpleador = comunicadoEmpleador;
    }

    /**
     * @return the correoElectronicoAfiliado
     */
    public String getCorreoElectronicoAfiliado() {
        return correoElectronicoAfiliado;
    }

    /**
     * @param correoElectronicoAfiliado the correoElectronicoAfiliado to set
     */
    public void setCorreoElectronicoAfiliado(String correoElectronicoAfiliado) {
        this.correoElectronicoAfiliado = correoElectronicoAfiliado;
    }

    /**
     * @return the comunicadoAfiliado
     */
    public EtiquetaPlantillaComunicadoEnum getComunicadoAfiliado() {
        return comunicadoAfiliado;
    }

    /**
     * @param comunicadoAfiliado the comunicadoAfiliado to set
     */
    public void setComunicadoAfiliado(EtiquetaPlantillaComunicadoEnum comunicadoAfiliado) {
        this.comunicadoAfiliado = comunicadoAfiliado;
    }

    /**
     * @return the intentoAfiliaciones
     */
    public List<IntentoAfiliacionInDTO> getIntentoAfiliaciones() {
        return intentoAfiliaciones;
    }

    /**
     * @param intentoAfiliaciones the intentoAfiliaciones to set
     */
    public void setIntentoAfiliaciones(List<IntentoAfiliacionInDTO> intentoAfiliaciones) {
        this.intentoAfiliaciones = intentoAfiliaciones;
    }

}
