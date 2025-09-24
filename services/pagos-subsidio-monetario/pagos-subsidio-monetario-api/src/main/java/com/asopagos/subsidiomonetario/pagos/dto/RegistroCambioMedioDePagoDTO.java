package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de entrada para registrar el
 * cambio de medio de pago de los abonos que pertenecen a un mismo administrador de subsidio<br/>
 * <b>Módulo:</b> Asopagos - HU-31-219 <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class RegistroCambioMedioDePagoDTO implements Serializable {

    private static final long serialVersionUID = 2444612840056926795L;

    /**
     * Medio de pago seleccionado para reemplazar los que tiene asociados
     * los registros de abonos selecciondados.
     */
    @NotNull
    private MedioDePagoModeloDTO medioDePagoModelo;

    /**
     * Lista de registros de abonos seleccionados para cambiar el medio
     * de pagos asociados respectivamente.
     */
    @NotNull
    private List<CuentaAdministradorSubsidioDTO> listaRegistrosAbonos;

    /**
     * @return the medioDePagoModelo
     */
    public MedioDePagoModeloDTO getMedioDePagoModelo() {
        return medioDePagoModelo;
    }

    /**
     * @param medioDePagoModelo
     *        the medioDePagoModelo to set
     */
    public void setMedioDePagoModelo(MedioDePagoModeloDTO medioDePagoModelo) {
        this.medioDePagoModelo = medioDePagoModelo;
    }

    /**
     * @return the listaRegistrosAbonos
     */
    public List<CuentaAdministradorSubsidioDTO> getListaRegistrosAbonos() {
        return listaRegistrosAbonos;
    }

    /**
     * @param listaRegistrosAbonos
     *        the listaRegistrosAbonos to set
     */
    public void setListaRegistrosAbonos(List<CuentaAdministradorSubsidioDTO> listaRegistrosAbonos) {
        this.listaRegistrosAbonos = listaRegistrosAbonos;
    }

}
