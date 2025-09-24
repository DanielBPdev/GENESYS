package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisUnidos;

/**
 * Entidad que registra los archivos de cruce cargados al sistema <br/>
 * <b>Historia de Usuario: </b>321-033
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class CargueArchivoCruceFovisUnidosDTO extends CargueArchivoCruceFovisHojaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3054581474125586358L;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertHojaToDTO(com.asopagos.dto.cargaMultiple.InformacionHojaCruceFovisDTO)
     */
    @Override
    public void convertHojaToDTO(InformacionHojaCruceFovisDTO infoHoja) {
        this.setApellidosNombres(infoHoja.getApellidosNombres());
        this.setDepartamento(infoHoja.getDepartamento());
        this.setFolio(infoHoja.getFolio());
        this.setIdentificacion(infoHoja.getIdentificacion());
        this.setMunicipio(infoHoja.getMunicipio());
        this.setParentesco(infoHoja.getParentesco());
        this.setSexo(infoHoja.getSexo());
        this.setTipoDocumento(infoHoja.getTipoDocumento());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertToEntity()
     */
    @Override
    public CargueArchivoCruceFovisUnidos convertToEntity() {
        CargueArchivoCruceFovisUnidos cargueArchivoCruceFovis = new CargueArchivoCruceFovisUnidos();
        cargueArchivoCruceFovis.setApellidosNombres(this.getApellidosNombres());
        cargueArchivoCruceFovis.setDepartamento(this.getDepartamento());
        cargueArchivoCruceFovis.setFolio(this.getFolio());
        cargueArchivoCruceFovis.setId(this.getId());
        cargueArchivoCruceFovis.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoCruceFovis());
        cargueArchivoCruceFovis.setIdentificacion(this.getIdentificacion());
        cargueArchivoCruceFovis.setMunicipio(this.getMunicipio());
        cargueArchivoCruceFovis.setParentesco(this.getParentesco());
        cargueArchivoCruceFovis.setSexo(this.getSexo());
        cargueArchivoCruceFovis.setTipoIdentificacion(this.getTipoDocumento());
        return cargueArchivoCruceFovis;
    }

    /**
     * @param entity
     * @return
     */
    public static CargueArchivoCruceFovisUnidosDTO convertEntityToDTO(CargueArchivoCruceFovisUnidos entity) {
        CargueArchivoCruceFovisUnidosDTO cargueDTO = new CargueArchivoCruceFovisUnidosDTO();
        cargueDTO.setApellidosNombres(entity.getApellidosNombres());
        cargueDTO.setDepartamento(entity.getDepartamento());
        cargueDTO.setFolio(entity.getFolio());
        cargueDTO.setId(entity.getId());
        cargueDTO.setIdCargueArchivoCruceFovis(entity.getIdCargueArchivoCruceFovis());
        cargueDTO.setIdentificacion(entity.getIdentificacion());
        cargueDTO.setMunicipio(entity.getMunicipio());
        cargueDTO.setParentesco(entity.getParentesco());
        cargueDTO.setSexo(entity.getSexo());
        cargueDTO.setTipoDocumento(entity.getTipoIdentificacion());
        return cargueDTO;
    }

    @Override
    public void processStatement(PreparedStatement statement) throws Exception {
        statement.setString(1, this.getIdentificacion());
        statement.setString(2, this.getApellidosNombres());
        statement.setString(3, this.getFolio());
        statement.setString(4, this.getSexo());
        statement.setString(5, this.getParentesco());
        statement.setString(6, this.getDepartamento());
        statement.setString(7, this.getMunicipio());
        statement.setBigDecimal(8, new BigDecimal(this.getIdCargueArchivoCruceFovis()));
        statement.setString(9, this.getTipoDocumento());
    }

    @Override
    public String getTableName() {
        return "CargueArchivoCruceFovisUnidos";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cfuId");
        list.add("cfuIdentificacion");
        list.add("cfuApellidosNombres");
        list.add("cfuFolio");
        list.add("cfuSexo");
        list.add("cfuParantesco");
        list.add("cfuDepartamento");
        list.add("cfuMunicipio");
        list.add("cfuCargueArchivoCruceFovis");
        list.add("cfuTipoIdentificacion");
        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisUnidos";
    }
}
