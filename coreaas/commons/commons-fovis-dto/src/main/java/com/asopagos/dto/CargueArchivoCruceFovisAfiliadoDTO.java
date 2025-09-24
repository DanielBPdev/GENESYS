package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisAfiliado;

/**
 * <b>Descripcion:</b> Clase que contiene la informacion del Cargue Archivo de cruce Fovis<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 * 321-033
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
@XmlRootElement
public class CargueArchivoCruceFovisAfiliadoDTO extends CargueArchivoCruceFovisHojaDTO implements Serializable {

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
        this.setApellidos(infoHoja.getApellidos());
        this.setIdentificacion(infoHoja.getIdentificacion());
        this.setNitEntidad(infoHoja.getNitEntidad());
        this.setNombreEntidad(infoHoja.getNombreEntidad());
        this.setNombres(infoHoja.getNombres());
        this.setTipoDocumento(infoHoja.getTipoDocumento());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertToEntity()
     */
    @Override
    public CargueArchivoCruceFovisAfiliado convertToEntity() {
        CargueArchivoCruceFovisAfiliado cargueArchivoCruceFovis = new CargueArchivoCruceFovisAfiliado();
        cargueArchivoCruceFovis.setApellidos(this.getApellidos());
        cargueArchivoCruceFovis.setId(this.getId());
        cargueArchivoCruceFovis.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoCruceFovis());
        cargueArchivoCruceFovis.setIdentificacion(this.getIdentificacion());
        cargueArchivoCruceFovis.setNitEntidad(this.getNitEntidad());
        cargueArchivoCruceFovis.setNombreEntidad(this.getNombreEntidad());
        cargueArchivoCruceFovis.setNombres(this.getNombres());
        cargueArchivoCruceFovis.setTipoIdentificacion(this.getTipoDocumento());
        return cargueArchivoCruceFovis;
    }

    /**
     * @param entity
     * @return
     */
    public static CargueArchivoCruceFovisAfiliadoDTO convertEntityToDTO(CargueArchivoCruceFovisAfiliado entity) {
        CargueArchivoCruceFovisAfiliadoDTO cargueDTO = new CargueArchivoCruceFovisAfiliadoDTO();
        cargueDTO.setId(entity.getId());
        cargueDTO.setApellidos(entity.getApellidos());
        cargueDTO.setIdCargueArchivoCruceFovis(entity.getIdCargueArchivoCruceFovis());
        cargueDTO.setIdentificacion(entity.getIdentificacion());
        cargueDTO.setNitEntidad(entity.getNitEntidad());
        cargueDTO.setNombreEntidad(entity.getNombreEntidad());
        cargueDTO.setNombres(entity.getNombres());
        cargueDTO.setTipoDocumento(entity.getTipoIdentificacion());
        return cargueDTO;
    }

    @Override
    public void processStatement(PreparedStatement statement) throws Exception {
        statement.setString(1, this.getNitEntidad());
        statement.setString(2, this.getNombreEntidad());
        statement.setString(3, this.getIdentificacion());
        statement.setString(4, this.getApellidos());
        statement.setString(5, this.getNombres());
        statement.setBigDecimal(6, new BigDecimal(this.getIdCargueArchivoCruceFovis()));
        statement.setString(7, this.getTipoDocumento());
    }

    @Override
    public String getTableName() {
        return "CargueArchivoCruceFovisAfiliado";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cfaId");
        list.add("cfaNitEntidad");
        list.add("cfaNombreEntidad");
        list.add("cfaIdentificacion");
        list.add("cfaApellidos");
        list.add("cfaNombres");
        list.add("cfaCargueArchivoCruceFovis");
        list.add("cfaTipoIdentificacion");
        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisAfiliado";
    }

}
