package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisFechasCorte;

/**
 * Entidad que registra los archivos de cruce cargados al sistema <br/>
 * <b>Historia de Usuario: </b>321-033
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class CargueArchivoCruceFovisFechasCorteDTO extends CargueArchivoCruceFovisHojaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3203287655795938052L;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertHojaToDTO(com.asopagos.dto.cargaMultiple.InformacionHojaCruceFovisDTO)
     */
    @Override
    public void convertHojaToDTO(InformacionHojaCruceFovisDTO infoHoja) {
        this.setFechaActualizacion(infoHoja.getFechaActualizacion());
        this.setFechaCorte(infoHoja.getFechaCorte());
        this.setNitEntidad(infoHoja.getNitEntidad());
        this.setNombreEntidad(infoHoja.getNombreEntidad());
        this.setTipoInformacion(infoHoja.getTipoInformacion());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertToEntity()
     */
    @Override
    public CargueArchivoCruceFovisFechasCorte convertToEntity() {
        CargueArchivoCruceFovisFechasCorte cargueArchivoCruceFovis = new CargueArchivoCruceFovisFechasCorte();
        cargueArchivoCruceFovis.setFechaActualizacion(this.getFechaActualizacion());
        cargueArchivoCruceFovis.setFechaCorte(this.getFechaCorte());
        cargueArchivoCruceFovis.setId(this.getId());
        cargueArchivoCruceFovis.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoCruceFovis());
        cargueArchivoCruceFovis.setNitEntidad(this.getNitEntidad());
        cargueArchivoCruceFovis.setNombreEntidad(this.getNombreEntidad());
        cargueArchivoCruceFovis.setTipoInformacion(this.getTipoInformacion());
        return cargueArchivoCruceFovis;
    }

    /**
     * @param entity
     * @return
     */
    public static CargueArchivoCruceFovisFechasCorteDTO convertEntityToDTO(CargueArchivoCruceFovisFechasCorte entity) {
        CargueArchivoCruceFovisFechasCorteDTO cargueDTO = new CargueArchivoCruceFovisFechasCorteDTO();
        cargueDTO.setFechaActualizacion(entity.getFechaActualizacion());
        cargueDTO.setFechaCorte(entity.getFechaCorte());
        cargueDTO.setId(entity.getId());
        cargueDTO.setIdCargueArchivoCruceFovis(entity.getIdCargueArchivoCruceFovis());
        cargueDTO.setNitEntidad(entity.getNitEntidad());
        cargueDTO.setNombreEntidad(entity.getNombreEntidad());
        cargueDTO.setTipoInformacion(entity.getTipoInformacion());
        return cargueDTO;
    }

    @Override
    public void processStatement(PreparedStatement statement) throws Exception {
        statement.setString(1, this.getNitEntidad());
        statement.setString(2, this.getNombreEntidad());
        statement.setString(3, this.getTipoInformacion());
        Date fechaCorte = null;
        if (this.getFechaCorte() != null) {
            fechaCorte = new Date(this.getFechaCorte().getTime());
        }
        statement.setDate(4, fechaCorte);
        Date fechaActualiza = null;
        if (this.getFechaActualizacion() != null) {
            fechaActualiza = new Date(this.getFechaActualizacion().getTime());
        }
        statement.setDate(5, fechaActualiza);
        statement.setBigDecimal(6, new BigDecimal(this.getIdCargueArchivoCruceFovis()));
    }

    @Override
    public String getTableName() {
        return "CargueArchivoCruceFovisFechasCorte";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cffId");
        list.add("cffNitEntidad");
        list.add("cffNombreEntidad");
        list.add("cffTipoInformacion");
        list.add("cffFechaCorte");
        list.add("cffFechaActualizacion");
        list.add("cffCargueArchivoCruceFovis");
        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisFechasCorte";
    }
}