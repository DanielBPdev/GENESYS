package com.asopagos.dto.cartera;

import com.asopagos.dto.modelo.CarteraDependienteModeloDTO;
import com.asopagos.dto.modelo.CarteraNovedadModeloDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripción: </b> Clase que representa los datos del proceso de gestión
 * manual de deuda <br/>
 * <b>Historia de Usuario: </b> HU-239
 *
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 * Benavides</a>
 */
@XmlRootElement
public class GestionDeudaDTO extends CarteraDependienteModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 7152112766913250428L;

    /**
     * Lista de novedades a aplicar al cotizante
     */
    private List<CarteraNovedadModeloDTO> listaNovedades;

    /**
     * Observaciones de la novedad proceso interno CCF
     */
    private String observaciones;
    /**
     * identificador del documento
     */
    private String idDocumento;

    private Long idOperacion;

    /**
     * Método que obtiene el objeto del tipo
     * <code>CarteraDependienteModeloDTO</code> a partir del DTO
     *
     * @return Objeto <code>CarteraDependienteModeloDTO</code>
     */
    public CarteraDependienteModeloDTO obtenerCarteraDependiente() {
        CarteraDependienteModeloDTO carteraDependienteDTO = new CarteraDependienteModeloDTO();
        carteraDependienteDTO.setDeudaPresunta(this.getDeudaPresunta());
        carteraDependienteDTO.setDeudaReal(this.getDeudaReal());
        carteraDependienteDTO.setEstadoOperacion(this.getEstadoOperacion());
        carteraDependienteDTO.setIdCartera(this.getIdCartera());
        carteraDependienteDTO.setIdCarteraDependiente(this.getIdCarteraDependiente());
        carteraDependienteDTO.setIdPersona(this.getIdPersona());
        carteraDependienteDTO.setAgregadoManualmente(this.getAgregadoManualmente());
        return carteraDependienteDTO;
    }

    /**
     * Obtiene el valor de listaNovedades
     *
     * @return El valor de listaNovedades
     */
    public List<CarteraNovedadModeloDTO> getListaNovedades() {
        return listaNovedades;
    }

    /**
     * Establece el valor de listaNovedades
     *
     * @param listaNovedades El valor de listaNovedades por asignar
     */
    public void setListaNovedades(List<CarteraNovedadModeloDTO> listaNovedades) {
        this.listaNovedades = listaNovedades;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Long getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Long idOperacion) {
        this.idOperacion = idOperacion;
    }
}
