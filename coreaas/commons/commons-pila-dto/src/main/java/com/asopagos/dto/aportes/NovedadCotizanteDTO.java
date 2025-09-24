
package com.asopagos.dto.aportes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.afiliaciones.ResultadoProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.pila.TipoNovedadPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;


/**
 * Clase DTO con los datos para radicar una solicitud de aporte manual.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class NovedadCotizanteDTO implements Serializable{
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Tipo de novedad.
	 */
	private TipoTransaccionEnum tipoNovedad;
	/**
	 * Indica si la novedad fue seleccionada o no.
	 */
	private Boolean condicion;
	/**
	 * Fecha de inicio de la novedad.
	 */
	private Long fechaInicio;
	/**
	 * Fecha fin de la novedad
	 */
	private Long fechaFin;
	/**
	 * Atributo que indica si se debería enviar a aprobar la novedad.
	 */
	private Boolean aplicar;
	/**
	 * Atributo que indica si se aplica a procesos posteriores.
	 */
	private Boolean novedadFutura;
	/**
	 * Atributo que indica si la novedad fue seleccionada o no para devoluciones o correcciones.
	 */
	private Boolean condicionNueva;
	/**
	 * Atributo que indica la fecha inicio para devoluciones o correcciones.
	 */
	private Long fechaInicioNueva;
	/**
	 * Atributo que indica la fecha fin de para devoluciones o correcciones.
	 */
	private Long fechaFinNueva;
	
	/**
	 * Atributo que indica si una novedad fue aplicada o no.
	 */
	private Boolean estadoNovedad;
	
	/**
	 * Referencia al logger
	 */
	private static final ILogger logger = LogManager.getLogger(NovedadCotizanteDTO.class);

	/**
	 * Constructor
	 */
	public NovedadCotizanteDTO() {
	}
	
	/** Método que convierte un arreglo de objetos en un <code>NovedadCotizanteDTO</code> 
	 *  Se utiliza en la HU-005
	 * @param novedad El arreglo de objetos consultados
	 */
	public void convertToDTO(Object[] novedad){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if (novedad[0] != null) {
                this.tipoNovedad = TipoTransaccionEnum.valueOf(novedad[0].toString());
            }
            else {
                this.tipoNovedad = TipoNovedadPilaEnum.NOVEDAD_ING.equals(TipoNovedadPilaEnum.valueOf(novedad[4].toString()))? TipoTransaccionEnum.NOVEDAD_ING:null; 
            }
            if (novedad[1] != null) {
                Date fecha = formatter.parse(novedad[1].toString());
                this.fechaInicio = fecha.getTime();
            }
            if (novedad[2] != null) {
                Date fecha = formatter.parse(novedad[2].toString());
                this.fechaFin = fecha.getTime();
            }
            if (novedad[3] != null) {
                this.estadoNovedad = novedad[3].toString().equalsIgnoreCase("APROBADA") ? Boolean.TRUE : Boolean.FALSE;
            }
            if (novedad[5] != null) {
                this.novedadFutura = (Boolean)novedad[5] == true ? Boolean.TRUE : Boolean.FALSE;
            }
        } catch (Exception e) {
            logger.error("Ocurrió un error al convertir las fechas de la novedad");
        }
        this.estadoNovedad = novedad[3].toString().equals(ResultadoProcesoEnum.APROBADA.name());
    }
	
    /**
     * Método que retorna el valor de tipoNovedad.
     * @return valor de tipoNovedad.
     */
    public TipoTransaccionEnum getTipoNovedad() {
        return tipoNovedad;
    }

    /**
     * Método encargado de modificar el valor de tipoNovedad.
     * @param valor para modificar tipoNovedad.
     */
    public void setTipoNovedad(TipoTransaccionEnum tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

    /**
     * Método que retorna el valor de condicion.
     * @return valor de condicion.
     */
    public Boolean getCondicion() {
        return condicion;
    }

    /**
     * Método encargado de modificar el valor de condicion.
     * @param valor para modificar condicion.
     */
    public void setCondicion(Boolean condicion) {
        this.condicion = condicion;
    }

    /**
     * Método que retorna el valor de fechaInicio.
     * @return valor de fechaInicio.
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método encargado de modificar el valor de fechaInicio.
     * @param valor para modificar fechaInicio.
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Método que retorna el valor de fechaFin.
     * @return valor de fechaFin.
     */
    public Long getFechaFin() {
        return fechaFin;
    }

    /**
     * Método encargado de modificar el valor de fechaFin.
     * @param valor para modificar fechaFin.
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Método que retorna el valor de aplicar.
     * @return valor de aplicar.
     */
    public Boolean getAplicar() {
        return aplicar;
    }

    /**
     * Método encargado de modificar el valor de aplicar.
     * @param valor para modificar aplicar.
     */
    public void setAplicar(Boolean aplicar) {
        this.aplicar = aplicar;
    }

    /**
     * Método que retorna el valor de novedadFutura.
     * @return valor de novedadFutura.
     */
    public Boolean getNovedadFutura() {
        return novedadFutura;
    }

    /**
     * Método encargado de modificar el valor de novedadFutura.
     * @param valor para modificar novedadFutura.
     */
    public void setNovedadFutura(Boolean novedadFutura) {
        this.novedadFutura = novedadFutura;
    }

    /**
     * Método que retorna el valor de condicionNueva.
     * @return valor de condicionNueva.
     */
    public Boolean getCondicionNueva() {
        return condicionNueva;
    }

    /**
     * Método encargado de modificar el valor de condicionNueva.
     * @param valor para modificar condicionNueva.
     */
    public void setCondicionNueva(Boolean condicionNueva) {
        this.condicionNueva = condicionNueva;
    }

    /**
     * Método que retorna el valor de fechaInicioNueva.
     * @return valor de fechaInicioNueva.
     */
    public Long getFechaInicioNueva() {
        return fechaInicioNueva;
    }

    /**
     * Método encargado de modificar el valor de fechaInicioNueva.
     * @param valor para modificar fechaInicioNueva.
     */
    public void setFechaInicioNueva(Long fechaInicioNueva) {
        this.fechaInicioNueva = fechaInicioNueva;
    }

    /**
     * Método que retorna el valor de fechaFinNueva.
     * @return valor de fechaFinNueva.
     */
    public Long getFechaFinNueva() {
        return fechaFinNueva;
    }

    /**
     * Método encargado de modificar el valor de fechaFinNueva.
     * @param valor para modificar fechaFinNueva.
     */
    public void setFechaFinNueva(Long fechaFinNueva) {
        this.fechaFinNueva = fechaFinNueva;
    }

    /**
     * Método que retorna el valor de estadoNovedad.
     * @return valor de estadoNovedad.
     */
    public Boolean getEstadoNovedad() {
        return estadoNovedad;
    }

    /**
     * Método encargado de modificar el valor de estadoNovedad.
     * @param valor para modificar estadoNovedad.
     */
    public void setEstadoNovedad(Boolean estadoNovedad) {
        this.estadoNovedad = estadoNovedad;
    }	
}
