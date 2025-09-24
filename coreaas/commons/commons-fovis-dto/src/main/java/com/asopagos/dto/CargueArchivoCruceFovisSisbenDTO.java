package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisSisben;

/**
 * Entidad que registra los archivos de cruce cargados al sistema <br/>
 * <b>Historia de Usuario: </b>321-033
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class CargueArchivoCruceFovisSisbenDTO extends CargueArchivoCruceFovisHojaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4809547931121660134L;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertHojaToDTO(com.asopagos.dto.cargaMultiple.InformacionHojaCruceFovisDTO)
     */
    @Override
    public void convertHojaToDTO(InformacionHojaCruceFovisDTO infoHoja) {
        this.setApellidosNombres(infoHoja.getApellidosNombres());
        this.setIdentificacion(infoHoja.getIdentificacion());
        this.setParentesco(infoHoja.getParentesco());
        this.setPuntaje(infoHoja.getPuntaje());
        this.setSexo(infoHoja.getSexo());
        this.setZona(infoHoja.getZona());
        this.setTipoDocumento(infoHoja.getTipoDocumento());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertToEntity()
     */
    @Override
    public CargueArchivoCruceFovisSisben convertToEntity() {
        CargueArchivoCruceFovisSisben cargueArchivoCruceFovis = new CargueArchivoCruceFovisSisben();
        cargueArchivoCruceFovis.setApellidosNombres(this.getApellidosNombres());
        cargueArchivoCruceFovis.setId(this.getId());
        cargueArchivoCruceFovis.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoCruceFovis());
        cargueArchivoCruceFovis.setIdentificacion(this.getIdentificacion());
        cargueArchivoCruceFovis.setParentesco(this.getParentesco());
        cargueArchivoCruceFovis.setPuntaje(this.getPuntaje());
        cargueArchivoCruceFovis.setSexo(this.getSexo());
        cargueArchivoCruceFovis.setZona(this.getZona());
        cargueArchivoCruceFovis.setTipoIdentificacion(this.getTipoDocumento());
        return cargueArchivoCruceFovis;
    }

    /**
     * @param entity
     * @return
     */
    public static CargueArchivoCruceFovisSisbenDTO convertEntityToDTO(CargueArchivoCruceFovisSisben entity) {
        CargueArchivoCruceFovisSisbenDTO cargueDTO = new CargueArchivoCruceFovisSisbenDTO();
        cargueDTO.setApellidosNombres(entity.getApellidosNombres());
        cargueDTO.setId(entity.getId());
        cargueDTO.setIdCargueArchivoCruceFovis(entity.getIdCargueArchivoCruceFovis());
        cargueDTO.setIdentificacion(entity.getIdentificacion());
        cargueDTO.setParentesco(entity.getParentesco());
        cargueDTO.setPuntaje(entity.getPuntaje());
        cargueDTO.setSexo(entity.getSexo());
        cargueDTO.setZona(entity.getZona());
        cargueDTO.setTipoDocumento(entity.getTipoIdentificacion());
        return cargueDTO;
    }

    @Override
    public void processStatement(PreparedStatement statement) throws Exception {
        statement.setString(1, this.getIdentificacion());
        statement.setString(2, this.getApellidosNombres());
        statement.setString(3, this.getPuntaje());
        statement.setString(4, this.getSexo());
        statement.setString(5, this.getZona());
        statement.setString(6, this.getParentesco());
        statement.setBigDecimal(7, new BigDecimal(this.getIdCargueArchivoCruceFovis()));
        statement.setString(8, this.getTipoDocumento());
    }

    @Override
    public String getTableName() {
        return "CargueArchivoCruceFovisSisben";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cfsId");
        list.add("cfsIdentificacion");
        list.add("cfsApellidosNombres");
        list.add("cfsPuntaje");
        list.add("cfsSexo");
        list.add("cfsZona");
        list.add("cfsParantesco");
        list.add("cfsCargueArchivoCruceFovis");
        list.add("cfsTipoIdentificacion");
        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisSisben";
    }
}
