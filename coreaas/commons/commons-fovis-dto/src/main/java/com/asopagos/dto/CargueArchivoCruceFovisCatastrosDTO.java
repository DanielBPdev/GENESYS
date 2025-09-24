package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.InformacionHojaCruceFovisDTO;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisCatastros;

/**
 * <b>Descripcion:</b> Clase que contiene la informacion del Cargue Archivo de cruce Fovis<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 * 321-033
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
@XmlRootElement
public class CargueArchivoCruceFovisCatastrosDTO  extends CargueArchivoCruceFovisHojaDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5543315851381732592L;

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertHojaToDTO(com.asopagos.dto.cargaMultiple.InformacionHojaCruceFovisDTO)
     */
    @Override
    public void convertHojaToDTO(InformacionHojaCruceFovisDTO infoHoja) {
        this.setNitEntidad(infoHoja.getNitEntidad());
        this.setNombreEntidad(infoHoja.getNombreEntidad());
        this.setTipoDocumento(infoHoja.getTipoDocumento());
        this.setIdentificacion(infoHoja.getIdentificacion());
        this.setApellidosNombres(infoHoja.getApellidosNombres());
        this.setCedulaCatastral(infoHoja.getCedulaCatastral());
        this.setDireccion(infoHoja.getDireccion());
        this.setMatriculaInmobiliaria(infoHoja.getMatriculaInmobiliaria());
        this.setDepartamento(infoHoja.getDepartamento());
        this.setMunicipio(infoHoja.getMunicipio());
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertToEntity()
     */
    @Override
    public CargueArchivoCruceFovisCatastros convertToEntity() {
        CargueArchivoCruceFovisCatastros cargueArchivoCruceFovisCatastros = new CargueArchivoCruceFovisCatastros();
        cargueArchivoCruceFovisCatastros.setNitEntidad(this.getNitEntidad());
        cargueArchivoCruceFovisCatastros.setNombreEntidad(this.getNombreEntidad());
        cargueArchivoCruceFovisCatastros.setTipoIdentificacion(this.getTipoDocumento());
        cargueArchivoCruceFovisCatastros.setIdentificacion(this.getIdentificacion());
        cargueArchivoCruceFovisCatastros.setApellidosNombres(this.getApellidosNombres());
        cargueArchivoCruceFovisCatastros.setCedulaCatastral(this.getCedulaCatastral());
        cargueArchivoCruceFovisCatastros.setDireccion(this.getDireccion());
        cargueArchivoCruceFovisCatastros.setMatriculaInmobiliaria(this.getMatriculaInmobiliaria());
        cargueArchivoCruceFovisCatastros.setDepartamento(this.getDepartamento());
        cargueArchivoCruceFovisCatastros.setMunicipio(this.getMunicipio());
        cargueArchivoCruceFovisCatastros.setId(this.getId());
        return cargueArchivoCruceFovisCatastros;
    }

    /**
     * @param entity
     * @return
     */
    public static CargueArchivoCruceFovisCatastrosDTO convertEntityToDTO(CargueArchivoCruceFovisCatastros entity) {
        CargueArchivoCruceFovisCatastrosDTO cargueDTO = new CargueArchivoCruceFovisCatastrosDTO();
        cargueDTO.setId(entity.getId());
        cargueDTO.setIdCargueArchivoCruceFovis(entity.getIdCargueArchivoCruceFovis());
        cargueDTO.setNitEntidad(entity.getNitEntidad());
        cargueDTO.setNombreEntidad(entity.getNombreEntidad());
        cargueDTO.setTipoDocumento(entity.getTipoIdentificacion());
        cargueDTO.setIdentificacion(entity.getIdentificacion());
        cargueDTO.setApellidosNombres(entity.getApellidosNombres());
        cargueDTO.setCedulaCatastral(entity.getCedulaCatastral());
        cargueDTO.setDireccion(entity.getDireccion());
        cargueDTO.setMatriculaInmobiliaria(entity.getMatriculaInmobiliaria());
        cargueDTO.setDepartamento(entity.getDepartamento());
        cargueDTO.setMunicipio(entity.getMunicipio());

        return cargueDTO;
    }

    @Override
    public void processStatement(PreparedStatement statement) throws Exception {
        statement.setString(1, this.getNitEntidad());
        statement.setString(2, this.getNombreEntidad());
        statement.setString(3, this.getTipoDocumento());
        statement.setString(4, this.getIdentificacion());
        statement.setString(5, this.getApellidosNombres());
        statement.setString(6, this.getCedulaCatastral());
        statement.setString(7, this.getDireccion());
        statement.setString(8, this.getMatriculaInmobiliaria());
        statement.setString(9, this.getDepartamento());
        statement.setString(10, this.getMunicipio());
        statement.setBigDecimal(11, new BigDecimal(this.getIdCargueArchivoCruceFovis()));
    }

    @Override
    public String getTableName() {
        return "CargueArchivoCruceFovisCatastros";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cfdId");
        list.add("cfdNitEntidad");
        list.add("cfdNombreEntidad");
        list.add("cfdTipoIdentificacion");
        list.add("cfdIdentificacion");
        list.add("cfdApellidosNombres");
        list.add("cfdCedulaCatastral");
        list.add("cfdDireccion");
        list.add("cfdMatriculaInmobiliaria");
        list.add("cfdDepartamento");
        list.add("cfdMunicipio");
        list.add("cfdCargueArchivoCruceFovis");

        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisCatastros";
    }


}
