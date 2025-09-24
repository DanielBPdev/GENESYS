package com.asopagos.dto.fovis;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.CruceDetalleDTO;
import com.asopagos.entidades.ccf.fovis.Cruce;
import com.asopagos.entidades.ccf.fovis.CruceDetalle;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.fovis.EstadoCruceHogarEnum;
import com.asopagos.enumeraciones.fovis.TipoCruceEnum;

/**
 * <b>Descripción</b> DTO que representa los datos que debe llevar la consulta
 * de las cartas de asignacion generadas <b>HU-051</b>
 * 
 * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
 */
@XmlRootElement
public class HistoricoCrucesFOVISDTO extends CruceDetalleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private TipoCruceEnum tipoCruce;

    private EstadoCruceHogarEnum estadoCruceHogar;

    /**
     * Constructor por defecto
     */
    public HistoricoCrucesFOVISDTO() {
        super();
    }

    /**
     * Constructor con la informacion de cruce
     * @param persona
     *        Información persona asociada
     * @param cruce
     *        Información cruce
     * @param cruceDetalle
     *        Información detalle cruce
     */
    public HistoricoCrucesFOVISDTO(Persona persona, Cruce cruce, CruceDetalle cruceDetalle) {
        super(persona, cruceDetalle, cruce);
    }

    /**
     * @return the tipoCruce
     */
    public TipoCruceEnum getTipoCruce() {
        return tipoCruce;
    }

    /**
     * @param tipoCruce
     *        the tipoCruce to set
     */
    public void setTipoCruce(TipoCruceEnum tipoCruce) {
        this.tipoCruce = tipoCruce;
    }

    /**
     * @return the estadoCruceHogar
     */
    public EstadoCruceHogarEnum getEstadoCruceHogar() {
        return estadoCruceHogar;
    }

    /**
     * @param estadoCruceHogar
     *        the estadoCruceHogar to set
     */
    public void setEstadoCruceHogar(EstadoCruceHogarEnum estadoCruceHogar) {
        this.estadoCruceHogar = estadoCruceHogar;
    }
}
