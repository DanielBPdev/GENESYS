package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisCatBog;

/**
 * Entidad que registra los archivos de cruce cargados al sistema <br/>
 * <b>Historia de Usuario: </b>321-033
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class CargueArchivoCruceFovisCatBogDTO extends CargueArchivoCruceFovisHojaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3466353440737764869L;

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
        this.setNitEntidad(infoHoja.getNitEntidad());
        this.setNombreEntidad(infoHoja.getNombreEntidad());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertToEntity()
     */
    @Override
    public CargueArchivoCruceFovisCatBog convertToEntity() {
        CargueArchivoCruceFovisCatBog cargueArchivoCruceFovis = new CargueArchivoCruceFovisCatBog();
        cargueArchivoCruceFovis.setApellidosNombres(this.getApellidosNombres());
        cargueArchivoCruceFovis.setCedulaCatastral(this.getCedulaCatastral());
        cargueArchivoCruceFovis.setDepartamento(this.getDepartamento());
        cargueArchivoCruceFovis.setDireccion(this.getDireccion());
        cargueArchivoCruceFovis.setId(this.getId());
        cargueArchivoCruceFovis.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoCruceFovis());
        cargueArchivoCruceFovis.setIdentificacion(this.getIdentificacion());
        cargueArchivoCruceFovis.setMatriculaInmobiliaria(this.getMatriculaInmobiliaria());
        cargueArchivoCruceFovis.setMunicipio(this.getMunicipio());
        cargueArchivoCruceFovis.setNitEntidad(this.getNitEntidad());
        cargueArchivoCruceFovis.setNombreEntidad(this.getNombreEntidad());
        return cargueArchivoCruceFovis;
    }

    /**
     * @param entity
     * @return
     */
    public static CargueArchivoCruceFovisCatBogDTO convertEntityToDTO(CargueArchivoCruceFovisCatBog entity) {
        CargueArchivoCruceFovisCatBogDTO cargueDTO = new CargueArchivoCruceFovisCatBogDTO();
        cargueDTO.setApellidosNombres(entity.getApellidosNombres());
        cargueDTO.setCedulaCatastral(entity.getCedulaCatastral());
        cargueDTO.setDepartamento(entity.getDepartamento());
        cargueDTO.setDireccion(entity.getDireccion());
        cargueDTO.setId(entity.getId());
        cargueDTO.setIdCargueArchivoCruceFovis(entity.getIdCargueArchivoCruceFovis());
        cargueDTO.setIdentificacion(entity.getIdentificacion());
        cargueDTO.setMatriculaInmobiliaria(entity.getMatriculaInmobiliaria());
        cargueDTO.setMunicipio(entity.getMunicipio());
        cargueDTO.setNitEntidad(entity.getNitEntidad());
        cargueDTO.setNombreEntidad(entity.getNombreEntidad());
        return cargueDTO;
    }

    @Override
    public void processStatement(PreparedStatement statement) throws Exception {
        statement.setString(1, this.getNitEntidad());
        statement.setString(2, this.getNombreEntidad());
        statement.setString(3, this.getIdentificacion());
        statement.setString(4, this.getApellidosNombres());
        statement.setString(5, this.getCedulaCatastral());
        statement.setString(6, this.getDireccion());
        statement.setString(7, this.getMatriculaInmobiliaria());
        statement.setString(8, this.getDepartamento());
        statement.setString(9, this.getMunicipio());
        statement.setBigDecimal(10, new BigDecimal(this.getIdCargueArchivoCruceFovis()));
    }

    @Override
    public String getTableName() {
        return "CargueArchivoCruceFovisCatBog";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cfoId");
        list.add("cfoNitEntidad");
        list.add("cfoNombreEntidad");
        list.add("cfoIdentificacion");
        list.add("cfoApellidosNombres");
        list.add("cfoCedulaCatastral");
        list.add("cfoDireccion");
        list.add("cfoMatriculaInmobiliaria");
        list.add("cfoDepartamento");
        list.add("cfoMunicipio");
        list.add("cfoCargueArchivoCruceFovis");
        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisCatBog";
    }
}
