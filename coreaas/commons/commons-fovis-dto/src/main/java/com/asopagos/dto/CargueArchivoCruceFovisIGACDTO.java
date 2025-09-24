package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisIGAC;

/**
 * Entidad que registra los archivos de cruce cargados al sistema <br/>
 * <b>Historia de Usuario: </b>321-033
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class CargueArchivoCruceFovisIGACDTO extends CargueArchivoCruceFovisHojaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6054419676086693977L;

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

    @Override
    public CargueArchivoCruceFovisIGAC convertToEntity() {
        CargueArchivoCruceFovisIGAC cargueArchivoCruceFovis = new CargueArchivoCruceFovisIGAC();
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
    public static CargueArchivoCruceFovisIGACDTO convertEntityToDTO(CargueArchivoCruceFovisIGAC entity) {
        CargueArchivoCruceFovisIGACDTO cargueDTO = new CargueArchivoCruceFovisIGACDTO();
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
        return "CargueArchivoCruceFovisIGAC";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cfgId");
        list.add("cfgNitEntidad");
        list.add("cfgNombreEntidad");
        list.add("cfgIdentificacion");
        list.add("cfgApellidosNombres");
        list.add("cfgCedulaCatastral");
        list.add("cfgDireccion");
        list.add("cfgMatriculaInmobiliaria");
        list.add("cfgDepartamento");
        list.add("cfgMunicipio");
        list.add("cfgCargueArchivoCruceFovis");
        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisIGAC";
    }
}
