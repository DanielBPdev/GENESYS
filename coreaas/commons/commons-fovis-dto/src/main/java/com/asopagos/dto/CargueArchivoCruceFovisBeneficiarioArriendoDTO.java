package com.asopagos.dto;

import com.asopagos.entidades.ccf.fovis.CargueArchivoCruceFovisBeneficiarioArriendo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que registra los archivos de cruce cargados al sistema <br/>
 * <b>Historia de Usuario: </b>321-033
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class CargueArchivoCruceFovisBeneficiarioArriendoDTO extends CargueArchivoCruceFovisHojaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -73544760470939551L;

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
        this.setFechaAsignacion(infoHoja.getFechaAsignacion());
        this.setValorAsignado(infoHoja.getValorAsignado());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.dto.CargueArchivoCruceFovisHojaDTO#convertToEntity()
     */
    @Override
    public CargueArchivoCruceFovisBeneficiarioArriendo convertToEntity() {
        CargueArchivoCruceFovisBeneficiarioArriendo cargueArchivoCruceFovis = new CargueArchivoCruceFovisBeneficiarioArriendo();
        cargueArchivoCruceFovis.setApellidos(this.getApellidos());
        cargueArchivoCruceFovis.setId(this.getId());
        cargueArchivoCruceFovis.setIdCargueArchivoCruceFovis(this.getIdCargueArchivoCruceFovis());
        cargueArchivoCruceFovis.setIdentificacion(this.getIdentificacion());
        cargueArchivoCruceFovis.setNitEntidad(this.getNitEntidad());
        cargueArchivoCruceFovis.setNombreEntidad(this.getNombreEntidad());
        cargueArchivoCruceFovis.setNombres(this.getNombres());
        cargueArchivoCruceFovis.setTipoIdentificacion(this.getTipoDocumento());
        cargueArchivoCruceFovis.setFechaAsignacion(this.getFechaAsignacion());
        cargueArchivoCruceFovis.setValorAsignado(this.getValorAsignado());
        return cargueArchivoCruceFovis;
    }

    /**
     * @param entity
     * @return
     */
    public static CargueArchivoCruceFovisBeneficiarioArriendoDTO convertEntityToDTO(CargueArchivoCruceFovisBeneficiarioArriendo entity) {
        CargueArchivoCruceFovisBeneficiarioArriendoDTO cargueDTO = new CargueArchivoCruceFovisBeneficiarioArriendoDTO();
        cargueDTO.setId(entity.getId());
        cargueDTO.setApellidos(entity.getApellidos());
        cargueDTO.setIdCargueArchivoCruceFovis(entity.getIdCargueArchivoCruceFovis());
        cargueDTO.setIdentificacion(entity.getIdentificacion());
        cargueDTO.setNitEntidad(entity.getNitEntidad());
        cargueDTO.setNombreEntidad(entity.getNombreEntidad());
        cargueDTO.setNombres(entity.getNombres());
        cargueDTO.setTipoDocumento(entity.getTipoIdentificacion());
        cargueDTO.setFechaAsignacion(entity.getFechaAsignacion());
        cargueDTO.setValorAsignado(entity.getValorAsignado());
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
        Date fechaAsignacion = null;
        if (this.getFechaAsignacion() != null) {
            fechaAsignacion = new Date(this.getFechaAsignacion().getTime());
        }
        statement.setDate(8, fechaAsignacion);
        statement.setString(9, this.getValorAsignado());
    }

    @Override
    public String getTableName() {
        return "CargueArchivoCruceFovisBeneficiarioArriendo";
    }

    @Override
    public List<String> getColumnsName() {
        List<String> list = new ArrayList<>();
        list.add("cfeId");
        list.add("cfeNitEntidad");
        list.add("cfeNombreEntidad");
        list.add("cfeIdentificacion");
        list.add("cfeApellidos");
        list.add("cfeNombres");
        list.add("cfeCargueArchivoCruceFovis");
        list.add("cfeTipoIdentificacion");
        list.add("cfeFechaAsignacion");
        list.add("cfeValorAsignado");
        return list;
    }

    @Override
    public String getSequenceName() {
        return "SEC_CargueArchivoCruceFovisBeneficiarioArriendo";
    }

}
