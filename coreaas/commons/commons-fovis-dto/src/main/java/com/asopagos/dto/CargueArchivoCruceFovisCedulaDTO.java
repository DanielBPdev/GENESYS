package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisCedula;

/**
 * Entidad que registra los archivos de cruce cargados al sistema <br/>
 * <b>Historia de Usuario: </b>321-033
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class CargueArchivoCruceFovisCedulaDTO extends CargueArchivoCruceFovisHojaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1082907120353770462L;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertHojaToDTO(com.asopagos.dto.cargaMultiple.InformacionHojaCruceFovisDTO)
     */
    @Override
    public void convertHojaToDTO(InformacionHojaCruceFovisDTO infoHoja) {
        this.setNroCedula(infoHoja.getNroCedula());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertToEntity()
     */
    @Override
    public CargueArchivoCruceFovisCedula convertToEntity() {
        CargueArchivoCruceFovisCedula cargue = new CargueArchivoCruceFovisCedula();
        cargue.setId(this.getId());
        cargue.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoCruceFovis());
        cargue.setNroCedula(this.getNroCedula());
        return cargue;
    }

    /**
     * @param entity
     * @return
     */
    public static CargueArchivoCruceFovisCedulaDTO convertEntityToDTO(CargueArchivoCruceFovisCedula entity) {
        CargueArchivoCruceFovisCedulaDTO cargueDTO = new CargueArchivoCruceFovisCedulaDTO();
        cargueDTO.setId(entity.getId());
        cargueDTO.setIdCargueArchivoCruceFovis(entity.getIdCargueArchivoCruceFovis());
        cargueDTO.setNroCedula(entity.getNroCedula());
        return cargueDTO;
    }

    @Override
    public String getTableName() {
        return "CargueArchivoCruceFovisCedula";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cfcId");
        list.add("cfcNroCedula");
        list.add("cfcCargueArchivoCruceFovis");
        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisCedula";
    }

    @Override
    public void processStatement(PreparedStatement statement) throws Exception {
        statement.setString(1, this.getNroCedula());
        statement.setBigDecimal(2, new BigDecimal(this.getIdCargueArchivoCruceFovis()));
    }

}
