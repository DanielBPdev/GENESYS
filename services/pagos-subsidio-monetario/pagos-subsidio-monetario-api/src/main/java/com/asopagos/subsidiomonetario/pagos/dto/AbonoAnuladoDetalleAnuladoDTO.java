package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoProcesoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.MotivoAnulacionSubsidioAsignadoEnum;

/**
 * <b>Descripcion:</b> Clase DTO para la comunicación con pantallas para la anulación de los
 * detalles de los abonos<br/>
 * <b>Módulo:</b> Asopagos - HU - 31 - 207,208,220 y 221<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class AbonoAnuladoDetalleAnuladoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3693459347002807893L;

    /**
     * Lista de detalles que serán anulados.
     */
    private List<DetalleSubsidioAsignadoDTO> listaAnularDetallesDTO;

    private TipoProcesoAnibolEnum tipoProceso;
    
    /**
     * Lista de id de los abonos (cuentas de administrador del subsidio)
     * que pertenencen a los detalles a ser anulados.
     */
    private List<Long> listaIdsCuentasAdmonSubsidios;

    /**
     * Motivo de anulacion que sera puesto en el motivo de anulacion de los
     * detalles una vez sea confirmada la anulacion por ANIBOL
     */
    private MotivoAnulacionSubsidioAsignadoEnum motivoAnulacion;


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
     * @return the listaIdsCuentasAdmonSubsidios
     */
    public List<Long> getListaIdsCuentasAdmonSubsidios() {
        return listaIdsCuentasAdmonSubsidios;
    }

    /**
     * @param listaIdsCuentasAdmonSubsidios
     *        the listaIdsCuentasAdmonSubsidios to set
     */
    public void setListaIdsCuentasAdmonSubsidios(List<Long> listaIdsCuentasAdmonSubsidios) {
        this.listaIdsCuentasAdmonSubsidios = listaIdsCuentasAdmonSubsidios;
    }

	/**
	 * @return the tipoProceso
	 */
	public TipoProcesoAnibolEnum getTipoProceso() {
		return tipoProceso;
	}

	/**
	 * @param tipoProceso the tipoProceso to set
	 */
	public void setTipoProceso(TipoProcesoAnibolEnum tipoProceso) {
		this.tipoProceso = tipoProceso;
	}

    /**
	 * @return the motivoAnulacion
	 */
    public MotivoAnulacionSubsidioAsignadoEnum getMotivoAnulacion() {
        return this.motivoAnulacion;
    }

    /**
	 * @param motivoAnulacion the tipoProceso to set
	 */
    public void setMotivoAnulacion(MotivoAnulacionSubsidioAsignadoEnum motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }
}
