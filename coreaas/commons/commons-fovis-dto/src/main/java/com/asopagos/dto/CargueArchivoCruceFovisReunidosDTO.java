package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisReunidos;

/**
 * Entidad que registra los archivos de cruce cargados al sistema <br/>
 * <b>Historia de Usuario: </b>321-033
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class CargueArchivoCruceFovisReunidosDTO extends CargueArchivoCruceFovisHojaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5529446792332659006L;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertHojaToDTO(com.asopagos.dto.cargaMultiple.InformacionHojaCruceFovisDTO)
     */
    @Override
    public void convertHojaToDTO(InformacionHojaCruceFovisDTO infoHoja) {
        this.setApellidosNombres(infoHoja.getApellidosNombres());
        this.setDepartamento(infoHoja.getDepartamento());
        this.setDocumento(infoHoja.getDocumento());
        this.setMunicipio(infoHoja.getMunicipio());
        this.setTipoDocumento(infoHoja.getTipoDocumento());
        this.setCodTipoDocumento(infoHoja.getCodTipoDocumento());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertToEntity()
     */
    @Override
    public CargueArchivoCruceFovisReunidos convertToEntity() {
        CargueArchivoCruceFovisReunidos cargueArchivoCruceFovis = new CargueArchivoCruceFovisReunidos();
        cargueArchivoCruceFovis.setApellidosNombres(this.getApellidosNombres());
        cargueArchivoCruceFovis.setDepartamento(this.getDepartamento());
        cargueArchivoCruceFovis.setDocumento(this.getDocumento());
        cargueArchivoCruceFovis.setId(this.getId());
        cargueArchivoCruceFovis.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoCruceFovis());
        cargueArchivoCruceFovis.setMunicipio(this.getMunicipio());
        cargueArchivoCruceFovis.setTipoDocumento(this.getTipoDocumento());
        cargueArchivoCruceFovis.setTipoIdentificacion(this.getCodTipoDocumento());
        return cargueArchivoCruceFovis;
    }

    /**
     * @param entity
     * @return
     */
    public static CargueArchivoCruceFovisReunidosDTO convertEntityToDTO(CargueArchivoCruceFovisReunidos entity) {
        CargueArchivoCruceFovisReunidosDTO cargueDTO = new CargueArchivoCruceFovisReunidosDTO();
        cargueDTO.setApellidosNombres(entity.getApellidosNombres());
        cargueDTO.setDepartamento(entity.getDepartamento());
        cargueDTO.setDocumento(entity.getDocumento());
        cargueDTO.setId(entity.getId());
        cargueDTO.setIdCargueArchivoCruceFovis(entity.getIdCargueArchivoCruceFovis());
        cargueDTO.setMunicipio(entity.getMunicipio());
        cargueDTO.setTipoDocumento(entity.getTipoDocumento());
        cargueDTO.setCodTipoDocumento(entity.getTipoIdentificacion());
        return cargueDTO;
    }

    @Override
    public void processStatement(PreparedStatement statement) throws Exception {
        statement.setString(1, this.getDocumento());
        statement.setString(2, this.getTipoDocumento());
        statement.setString(3, this.getApellidosNombres());
        statement.setString(4, this.getMunicipio());
        statement.setString(5, this.getDepartamento());
        statement.setBigDecimal(6, new BigDecimal(this.getIdCargueArchivoCruceFovis()));
        statement.setString(7, this.getCodTipoDocumento());
    }

    @Override
    public String getTableName() {
        return "CargueArchivoCruceFovisReunidos";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cfrId");
        list.add("cfrDocumento");
        list.add("cfrTipoDocumento");
        list.add("cfrApellidosNombres");
        list.add("cfrMunicipio");
        list.add("cfrDepartamento");
        list.add("cfrCargueArchivoCruceFovis");
        list.add("cfrTipoIdentificacion");
        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisReunidos";
    }
}
