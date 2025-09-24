package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisCatAnt;

/**
 * Entidad que registra los archivos de cruce cargados al sistema <br/>
 * <b>Historia de Usuario: </b>321-033
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class CargueArchivoCruceFovisCatAntDTO extends CargueArchivoCruceFovisHojaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3400477628138081378L;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertHojaToDTO(com.asopagos.dto.cargaMultiple.InformacionHojaCruceFovisDTO)
     */
    @Override
    public void convertHojaToDTO(InformacionHojaCruceFovisDTO infoHoja) {
        this.setApellidos(infoHoja.getApellidos());
        this.setCedulaCatastral(infoHoja.getCedulaCatastral());
        this.setDepartamento(infoHoja.getDepartamento());
        this.setDireccionInmueble(infoHoja.getDireccionInmueble());
        this.setIdentificacion(infoHoja.getIdentificacion());
        this.setMatriculaInmobiliaria(infoHoja.getMatriculaInmobiliaria());
        this.setMunicipio(infoHoja.getMunicipio());
        this.setNitEntidad(infoHoja.getNitEntidad());
        this.setNombreEntidad(infoHoja.getNombreEntidad());
        this.setNombres(infoHoja.getNombres());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertToEntity()
     */
    @Override
    public CargueArchivoCruceFovisCatAnt convertToEntity() {
        CargueArchivoCruceFovisCatAnt cargueArchivoCruceFovis = new CargueArchivoCruceFovisCatAnt();
        cargueArchivoCruceFovis.setApellidos(this.getApellidos());
        cargueArchivoCruceFovis.setCedulaCatastral(this.getCedulaCatastral());
        cargueArchivoCruceFovis.setDepartamento(this.getDepartamento());
        cargueArchivoCruceFovis.setDireccionInmueble(this.getDireccionInmueble());
        cargueArchivoCruceFovis.setId(this.getId());
        cargueArchivoCruceFovis.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoCruceFovis());
        cargueArchivoCruceFovis.setIdentificacion(this.getIdentificacion());
        cargueArchivoCruceFovis.setMatriculaInmobiliaria(this.getMatriculaInmobiliaria());
        cargueArchivoCruceFovis.setMunicipio(this.getMunicipio());
        cargueArchivoCruceFovis.setNitEntidad(this.getNitEntidad());
        cargueArchivoCruceFovis.setNombreEntidad(this.getNombreEntidad());
        cargueArchivoCruceFovis.setNombres(this.getNombres());
        return cargueArchivoCruceFovis;
    }

    /**
     * @param entity
     * @return
     */
    public static CargueArchivoCruceFovisCatAntDTO convertEntityToDTO(CargueArchivoCruceFovisCatAnt entity) {
        CargueArchivoCruceFovisCatAntDTO cargueDTO = new CargueArchivoCruceFovisCatAntDTO();
        cargueDTO.setApellidos(entity.getApellidos());
        cargueDTO.setCedulaCatastral(entity.getCedulaCatastral());
        cargueDTO.setDepartamento(entity.getDepartamento());
        cargueDTO.setDireccionInmueble(entity.getDireccionInmueble());
        cargueDTO.setId(entity.getId());
        cargueDTO.setIdCargueArchivoCruceFovis(entity.getIdCargueArchivoCruceFovis());
        cargueDTO.setIdentificacion(entity.getIdentificacion());
        cargueDTO.setMatriculaInmobiliaria(entity.getMatriculaInmobiliaria());
        cargueDTO.setMunicipio(entity.getMunicipio());
        cargueDTO.setNitEntidad(entity.getNitEntidad());
        cargueDTO.setNombreEntidad(entity.getNombreEntidad());
        cargueDTO.setNombres(entity.getNombres());
        return cargueDTO;
    }

    @Override
    public void processStatement(PreparedStatement statement) throws Exception {
        statement.setString(1, this.getNitEntidad());
        statement.setString(2, this.getNombreEntidad());
        statement.setString(3, this.getIdentificacion());
        statement.setString(4, this.getApellidos());
        statement.setString(5, this.getNombres());
        statement.setString(6, this.getCedulaCatastral());
        statement.setString(7, this.getDireccionInmueble());
        statement.setString(8, this.getMatriculaInmobiliaria());
        statement.setString(9, this.getDepartamento());
        statement.setString(10, this.getMunicipio());
        statement.setBigDecimal(11, new BigDecimal(this.getIdCargueArchivoCruceFovis()));
    }

    @Override
    public String getTableName() {
        return "CargueArchivoCruceFovisCatAnt";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cfnId");
        list.add("cfnNitEntidad");
        list.add("cfnNombreEntidad");
        list.add("cfnIdentificacion");
        list.add("cfnApellidos");
        list.add("cfnNombres");
        list.add("cfnCedulaCatastral");
        list.add("cfnDireccionInmueble");
        list.add("cfnMatriculaInmobiliaria");
        list.add("cfnDepartamento");
        list.add("cfnMunicipio");
        list.add("cfnCargueArchivoCruceFovis");
        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisCatAnt";
    }
}
