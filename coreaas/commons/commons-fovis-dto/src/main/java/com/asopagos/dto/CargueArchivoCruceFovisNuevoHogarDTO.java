package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisNuevoHogar;

/**
 * Entidad que registra los archivos de cruce cargados al sistema <br/>
 * <b>Historia de Usuario: </b>321-033
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class CargueArchivoCruceFovisNuevoHogarDTO extends CargueArchivoCruceFovisHojaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9008280282209501426L;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertHojaToDTO(com.asopagos.dto.cargaMultiple.InformacionHojaCruceFovisDTO)
     */
    @Override
    public void convertHojaToDTO(InformacionHojaCruceFovisDTO infoHoja) {
        this.setApellidosNombres(infoHoja.getApellidosNombres());
        this.setAsignadoPosteriorReporte(infoHoja.getAsignadoPosteriorReporte());
        this.setCajaCompensacion(infoHoja.getCajaCompensacion());
        this.setEntidadOtorgante(infoHoja.getEntidadOtorgante());
        this.setFechaSolicitud(infoHoja.getFechaSolicitud());
        this.setIdentificacion(infoHoja.getIdentificacion());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertToEntity()
     */
    @Override
    public CargueArchivoCruceFovisNuevoHogar convertToEntity() {
        CargueArchivoCruceFovisNuevoHogar cargueArchivoCruceFovis = new CargueArchivoCruceFovisNuevoHogar();
        cargueArchivoCruceFovis.setApellidosNombres(this.getApellidosNombres());
        cargueArchivoCruceFovis.setAsignadoPosteriorReporte(this.getAsignadoPosteriorReporte());
        cargueArchivoCruceFovis.setCajaCompensacion(this.getCajaCompensacion());
        cargueArchivoCruceFovis.setEntidadOtorgante(this.getEntidadOtorgante());
        cargueArchivoCruceFovis.setFechaSolicitud(this.getFechaSolicitud());
        cargueArchivoCruceFovis.setId(this.getId());
        cargueArchivoCruceFovis.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoCruceFovis());
        cargueArchivoCruceFovis.setIdentificacion(this.getIdentificacion());
        return cargueArchivoCruceFovis;
    }

    /**
     * @param entity
     * @return
     */
    public static CargueArchivoCruceFovisNuevoHogarDTO convertEntityToDTO(CargueArchivoCruceFovisNuevoHogar entity) {
        CargueArchivoCruceFovisNuevoHogarDTO cargueDTO = new CargueArchivoCruceFovisNuevoHogarDTO();
        cargueDTO.setApellidosNombres(entity.getApellidosNombres());
        cargueDTO.setAsignadoPosteriorReporte(entity.getAsignadoPosteriorReporte());
        cargueDTO.setCajaCompensacion(entity.getCajaCompensacion());
        cargueDTO.setEntidadOtorgante(entity.getEntidadOtorgante());
        cargueDTO.setFechaSolicitud(entity.getFechaSolicitud());
        cargueDTO.setId(entity.getId());
        cargueDTO.setIdCargueArchivoCruceFovis(entity.getIdCargueArchivoCruceFovis());
        cargueDTO.setIdentificacion(entity.getIdentificacion());
        return cargueDTO;
    }

    @Override
    public void processStatement(PreparedStatement statement) throws Exception {
        statement.setString(1, this.getIdentificacion());
        statement.setString(2, this.getApellidosNombres());
        Date fechaSolic = null;
        if (this.getFechaSolicitud() != null) {
            fechaSolic = new Date(this.getFechaSolicitud().getTime());
        }
        statement.setDate(3, fechaSolic);
        statement.setString(4, this.getEntidadOtorgante());
        statement.setString(5, this.getCajaCompensacion());
        statement.setString(6, this.getAsignadoPosteriorReporte());
        statement.setBigDecimal(7, new BigDecimal(this.getIdCargueArchivoCruceFovis()));
    }

    @Override
    public String getTableName() {
        return "CargueArchivoCruceFovisNuevoHogar";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cfhId");
        list.add("cfhIdentificacion");
        list.add("cfhApellidosNombres");
        list.add("cfhFechaSolicitud");
        list.add("cfhEntidadOrtogante");
        list.add("cfhCajaCompensacion");
        list.add("cfhAsignadoPosteriorReporte");
        list.add("cfhCargueArchivoCruceFovis");
        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisNuevoHogar";
    }
}
