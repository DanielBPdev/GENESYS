package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisCatMed;

/**
 * Entidad que registra los archivos de cruce cargados al sistema <br/>
 * <b>Historia de Usuario: </b>321-033
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class CargueArchivoCruceFovisCatMedDTO extends CargueArchivoCruceFovisHojaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2348498901454462894L;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertHojaToDTO(com.asopagos.dto.cargaMultiple.InformacionHojaCruceFovisDTO)
     */
    @Override
    public void convertHojaToDTO(InformacionHojaCruceFovisDTO infoHoja) {
        this.setApellidosNombres(infoHoja.getApellidosNombres());
        this.setCedulaCatastral(infoHoja.getCedulaCatastral());
        this.setDepartamento(infoHoja.getDepartamento());
        this.setDireccion(infoHoja.getDireccionInmueble());
        this.setIdentificacion(infoHoja.getIdentificacion());
        this.setMatriculaInmobiliaria(infoHoja.getMatriculaInmobiliaria());
        this.setMunicipio(infoHoja.getMunicipio());
        this.setNombreEntidad(infoHoja.getNombreEntidad());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertToEntity()
     */
    @Override
    public Object convertToEntity() {
        CargueArchivoCruceFovisCatMed cargueArchivoCruceFovis = new CargueArchivoCruceFovisCatMed();
        cargueArchivoCruceFovis.setApellidosNombres(this.getApellidosNombres());
        cargueArchivoCruceFovis.setCedulaCatastral(this.getCedulaCatastral());
        cargueArchivoCruceFovis.setDepartamento(this.getDepartamento());
        cargueArchivoCruceFovis.setDireccion(this.getDireccion());
        cargueArchivoCruceFovis.setId(this.getId());
        cargueArchivoCruceFovis.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoCruceFovis());
        cargueArchivoCruceFovis.setIdentificacion(this.getIdentificacion());
        cargueArchivoCruceFovis.setMatriculaInmobiliaria(this.getMatriculaInmobiliaria());
        cargueArchivoCruceFovis.setMunicipio(this.getMunicipio());
        cargueArchivoCruceFovis.setNombreEntidad(this.getNombreEntidad());
        return cargueArchivoCruceFovis;
    }

    /**
     * @param entity
     * @return
     */
    public static CargueArchivoCruceFovisCatMedDTO convertEntityToDTO(CargueArchivoCruceFovisCatMed entity) {
        CargueArchivoCruceFovisCatMedDTO cargueDTO = new CargueArchivoCruceFovisCatMedDTO();
        cargueDTO.setApellidosNombres(entity.getApellidosNombres());
        cargueDTO.setCedulaCatastral(entity.getCedulaCatastral());
        cargueDTO.setDepartamento(entity.getDepartamento());
        cargueDTO.setDireccion(entity.getDireccion());
        cargueDTO.setId(entity.getId());
        cargueDTO.setIdCargueArchivoCruceFovis(entity.getIdCargueArchivoCruceFovis());
        cargueDTO.setIdentificacion(entity.getIdentificacion());
        cargueDTO.setMatriculaInmobiliaria(entity.getMatriculaInmobiliaria());
        cargueDTO.setMunicipio(entity.getMunicipio());
        cargueDTO.setNombreEntidad(entity.getNombreEntidad());
        return cargueDTO;
    }

    @Override
    public void processStatement(PreparedStatement statement) throws Exception {
        statement.setString(1, this.getNombreEntidad());
        statement.setString(2, this.getIdentificacion());
        statement.setString(3, this.getApellidosNombres());
        statement.setString(4, this.getCedulaCatastral());
        statement.setString(5, this.getDireccion());
        statement.setString(6, this.getMatriculaInmobiliaria());
        statement.setString(7, this.getDepartamento());
        statement.setString(8, this.getMunicipio());
        statement.setBigDecimal(9, new BigDecimal(this.getIdCargueArchivoCruceFovis()));
    }

    @Override
    public String getTableName() {
        return "CargueArchivoCruceFovisCatMed";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cfmId");
        list.add("cfmNombreEntidad");
        list.add("cfmIdentificacion");
        list.add("cfmApellidosNombres");
        list.add("cfmCedulaCatastral");
        list.add("cfmDireccion");
        list.add("cfmMatriculaInmobiliaria");
        list.add("cfmDepartamento");
        list.add("cfmMunicipio");
        list.add("cfmCargueArchivoCruceFovis");
        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisCatMed";
    }
}
