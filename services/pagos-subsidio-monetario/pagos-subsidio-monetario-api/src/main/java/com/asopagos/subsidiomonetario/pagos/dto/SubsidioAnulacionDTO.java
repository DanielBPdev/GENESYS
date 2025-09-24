package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import com.asopagos.rest.security.dto.UserDTO;

/**
 * <b>Descripcion:</b> Clase DTO que permite anular una cuenta del administrador de subsidios con sus detalles
 * de subsidios asignados. <br/>
 * <b>Módulo:</b> Asopagos - HU-31-207-208-220-221<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class SubsidioAnulacionDTO implements Serializable {

    private static final long serialVersionUID = -4575615986289041470L;

    /**
     * Cuenta del administrador del subsidio candidata a ser anulada, sea con reemplazo o sin reemplazo.
     */
    private CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioOrigDTO;

    /**
     * Lista de detalles de subsidios asignados señalados para anulación con reemplazo o sin reemplazo.
     */
    private List<DetalleSubsidioAsignadoDTO> listaAnularDetallesDTO;

    /**
     * Datos del medio de pago por el cual se realizara el cambio despues de la anulación con reemplazo.
     * Este campo solo aplica para la HU-31-219 cambio medio de pago.
     */
    private MedioDePagoModeloDTO medioDePagoModelo;
    
    private UserDTO usuarioDTO;

    public SubsidioAnulacionDTO() {
    }

    /**
     * Constructor que instancia la clase con la cuenta del administrador del subsidio
     * y la lista de detalles de subsidios a anular.
     * @param cuentaAdministradorSubsidioDTO
     *        <code>CuentaAdministradorSubsidioDTO</code>
     *        Cuenta del administrador del subsidio
     * @param listaDetalles
     *        <code>List<DetalleSubsidioAsignadoDTO></code>
     *        Lista de detalles de subsidios asignados.
     */
    public SubsidioAnulacionDTO(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioDTO,
            List<DetalleSubsidioAsignadoDTO> listaDetalles) {
        this.cuentaAdministradorSubsidioOrigDTO = cuentaAdministradorSubsidioDTO;
        this.listaAnularDetallesDTO = listaDetalles;
    }

    /**
     * @return the cuentaAdministradorSubsidioOrigDTO
     */
    public CuentaAdministradorSubsidioDTO getCuentaAdministradorSubsidioOrigDTO() {
        return cuentaAdministradorSubsidioOrigDTO;
    }

    /**
     * @param cuentaAdministradorSubsidioOrigDTO
     *        the cuentaAdministradorSubsidioOrigDTO to set
     */
    public void setCuentaAdministradorSubsidioOrigDTO(CuentaAdministradorSubsidioDTO cuentaAdministradorSubsidioOrigDTO) {
        this.cuentaAdministradorSubsidioOrigDTO = cuentaAdministradorSubsidioOrigDTO;
    }

    /**
     * @return the listaAnularDetallesDTO
     */
    public List<DetalleSubsidioAsignadoDTO> getListaAnularDetallesDTO() {
        return listaAnularDetallesDTO;
    }

    /**
     * @param listaAnularDetallesDTO
     *        the listaAnularDetallesDTO to set
     */
    public void setListaAnularDetallesDTO(List<DetalleSubsidioAsignadoDTO> listaAnularDetallesDTO) {
        this.listaAnularDetallesDTO = listaAnularDetallesDTO;
    }

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

	public UserDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UserDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

    
}
