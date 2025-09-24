package com.asopagos.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.core.CodigoCIIU;
import com.asopagos.entidades.transversal.personas.ARL;
import com.asopagos.enumeraciones.cartera.MotivoFiscalizacionAportanteEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.util.PersonasUtils;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class EmpresaDTO {

    protected Long idEmpresa;

    protected PersonaDTO persona;

    protected String nombreComercial;

    protected Date fechaConstitucion;

    protected NaturalezaJuridicaEnum naturalezaJuridica;

    protected CodigoCIIU codigoCIIU;

    protected ARL arl;

    protected Integer idUltimaCajaCompensacion;

    protected String paginaWeb;

    protected Long idPersonaRepresentanteLegal;

    protected Long idPersonaRepresentanteLegalSuplente;

    protected Boolean especialRevision;

    private Long idUbicacionRepresentanteLegal;

    private Long idUbicacionRepresentanteLegalSuplente;
    
    private Boolean enviadoAFiscalizacion;
    
    private MotivoFiscalizacionAportanteEnum motivoFiscalizacion;


    public static EmpresaDTO convertEmpresaToDTO(Empresa empresa) {
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setNombreComercial(empresa.getNombreComercial());
        empresaDTO.setFechaConstitucion(empresa.getFechaConstitucion());
        empresaDTO.setNaturalezaJuridica(empresa.getNaturalezaJuridica());
        empresaDTO.setCodigoCIIU(empresa.getCodigoCIIU());
        empresaDTO.setArl(empresa.getArl());
        empresaDTO.setIdUltimaCajaCompensacion(empresa.getIdUltimaCajaCompensacion());
        empresaDTO.setPaginaWeb(empresa.getPaginaWeb());
        empresaDTO.setIdPersonaRepresentanteLegal(empresa.getIdPersonaRepresentanteLegal());
        empresaDTO.setIdPersonaRepresentanteLegalSuplente(empresa.getIdPersonaRepresentanteLegalSuplente());
        empresaDTO.setEspecialRevision(empresa.getEspecialRevision());
        empresaDTO.setIdUbicacionRepresentanteLegal(empresa.getIdUbicacionRepresentanteLegal());
        empresaDTO.setIdUbicacionRepresentanteLegalSuplente(empresa.getIdUbicacionRepresentanteLegalSuplente());
        if(empresa.getPersona()!=null){
            empresaDTO.setPersona(PersonaDTO.convertPersonaToDTO(empresa.getPersona(), null));
        }
        if(empresa.getEnviadoAFiscalizacion()!=null){
            empresaDTO.setEnviadoAFiscalizacion(empresa.getEnviadoAFiscalizacion());
        }
        if(empresa.getMotivoFiscalizacion()!=null){
            empresaDTO.setMotivoFiscalizacion(empresa.getMotivoFiscalizacion());
        }
            
        
        return empresaDTO;
    }
    
    /**
     * Metodo constructor
     */
    public EmpresaDTO() {

    }

    /**
     * Constructor con empresa
     * @param Empresa
     *        Empresa base
     */
    public EmpresaDTO(Empresa empresa) {
        this.setIdEmpresa(empresa.getIdEmpresa());
        this.setArl(empresa.getArl());
        this.setCodigoCIIU(empresa.getCodigoCIIU());
        this.setEspecialRevision(empresa.getEspecialRevision());
        this.setFechaConstitucion(empresa.getFechaConstitucion());
        this.setIdPersonaRepresentanteLegal(empresa.getIdPersonaRepresentanteLegal());
        this.setIdPersonaRepresentanteLegalSuplente((empresa.getIdPersonaRepresentanteLegalSuplente()));
        this.setIdUbicacionRepresentanteLegal(empresa.getIdUbicacionRepresentanteLegal());
        this.setIdUbicacionRepresentanteLegalSuplente(empresa.getIdPersonaRepresentanteLegalSuplente());
        this.setIdUltimaCajaCompensacion(empresa.getIdUltimaCajaCompensacion());
        this.setNaturalezaJuridica(empresa.getNaturalezaJuridica());
        this.setNombreComercial(empresa.getNombreComercial());
        this.setPaginaWeb(empresa.getPaginaWeb());
        PersonaDTO persona = PersonaDTO.convertPersonaToDTO(empresa.getPersona(), null); 
        this.setPersona(persona);
    }
    
    /**
     * @return the idEmpresa
     */
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * @return the persona
     */
    public PersonaDTO getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

    /**
     * @return the nombreComercial
     */
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * @param nombreComercial the nombreComercial to set
     */
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    /**
     * @return the fechaConstitucion
     */
    public Date getFechaConstitucion() {
        return fechaConstitucion;
    }

    /**
     * @param fechaConstitucion the fechaConstitucion to set
     */
    public void setFechaConstitucion(Date fechaConstitucion) {
        this.fechaConstitucion = fechaConstitucion;
    }

    /**
     * @return the naturalezaJuridica
     */
    public NaturalezaJuridicaEnum getNaturalezaJuridica() {
        return naturalezaJuridica;
    }

    /**
     * @param naturalezaJuridica the naturalezaJuridica to set
     */
    public void setNaturalezaJuridica(NaturalezaJuridicaEnum naturalezaJuridica) {
        this.naturalezaJuridica = naturalezaJuridica;
    }

    /**
     * @return the codigoCIIU
     */
    public CodigoCIIU getCodigoCIIU() {
        return codigoCIIU;
    }

    /**
     * @param codigoCIIU the codigoCIIU to set
     */
    public void setCodigoCIIU(CodigoCIIU codigoCIIU) {
        this.codigoCIIU = codigoCIIU;
    }

    /**
     * @return the arl
     */
    public ARL getArl() {
        return arl;
    }

    /**
     * @param arl the arl to set
     */
    public void setArl(ARL arl) {
        this.arl = arl;
    }

    /**
     * @return the idUltimaCajaCompensacion
     */
    public Integer getIdUltimaCajaCompensacion() {
        return idUltimaCajaCompensacion;
    }

    /**
     * @param idUltimaCajaCompensacion the idUltimaCajaCompensacion to set
     */
    public void setIdUltimaCajaCompensacion(Integer idUltimaCajaCompensacion) {
        this.idUltimaCajaCompensacion = idUltimaCajaCompensacion;
    }

    /**
     * @return the paginaWeb
     */
    public String getPaginaWeb() {
        return paginaWeb;
    }

    /**
     * @param paginaWeb the paginaWeb to set
     */
    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    /**
     * @return the idPersonaRepresentanteLegal
     */
    public Long getIdPersonaRepresentanteLegal() {
        return idPersonaRepresentanteLegal;
    }

    /**
     * @param idPersonaRepresentanteLegal the idPersonaRepresentanteLegal to set
     */
    public void setIdPersonaRepresentanteLegal(Long idPersonaRepresentanteLegal) {
        this.idPersonaRepresentanteLegal = idPersonaRepresentanteLegal;
    }

    /**
     * @return the idPersonaRepresentanteLegalSuplente
     */
    public Long getIdPersonaRepresentanteLegalSuplente() {
        return idPersonaRepresentanteLegalSuplente;
    }

    /**
     * @param idPersonaRepresentanteLegalSuplente the idPersonaRepresentanteLegalSuplente to set
     */
    public void setIdPersonaRepresentanteLegalSuplente(Long idPersonaRepresentanteLegalSuplente) {
        this.idPersonaRepresentanteLegalSuplente = idPersonaRepresentanteLegalSuplente;
    }

    /**
     * @return the especialRevision
     */
    public Boolean getEspecialRevision() {
        return especialRevision;
    }

    /**
     * @param especialRevision the especialRevision to set
     */
    public void setEspecialRevision(Boolean especialRevision) {
        this.especialRevision = especialRevision;
    }

    /**
     * @return the idUbicacionRepresentanteLegal
     */
    public Long getIdUbicacionRepresentanteLegal() {
        return idUbicacionRepresentanteLegal;
    }

    /**
     * @param idUbicacionRepresentanteLegal the idUbicacionRepresentanteLegal to set
     */
    public void setIdUbicacionRepresentanteLegal(Long idUbicacionRepresentanteLegal) {
        this.idUbicacionRepresentanteLegal = idUbicacionRepresentanteLegal;
    }

    /**
     * @return the idUbicacionRepresentanteLegalSuplente
     */
    public Long getIdUbicacionRepresentanteLegalSuplente() {
        return idUbicacionRepresentanteLegalSuplente;
    }

    /**
     * @param idUbicacionRepresentanteLegalSuplente the idUbicacionRepresentanteLegalSuplente to set
     */
    public void setIdUbicacionRepresentanteLegalSuplente(Long idUbicacionRepresentanteLegalSuplente) {
        this.idUbicacionRepresentanteLegalSuplente = idUbicacionRepresentanteLegalSuplente;
    }

    /**
     * @return the enviadoAFiscalizacion
     */
    public Boolean getEnviadoAFiscalizacion() {
        return enviadoAFiscalizacion;
    }

    /**
     * @param enviadoAFiscalizacion the enviadoAFiscalizacion to set
     */
    public void setEnviadoAFiscalizacion(Boolean enviadoAFiscalizacion) {
        this.enviadoAFiscalizacion = enviadoAFiscalizacion;
    }

    /**
     * @return the motivoFiscalizacion
     */
    public MotivoFiscalizacionAportanteEnum getMotivoFiscalizacion() {
        return motivoFiscalizacion;
    }

    /**
     * @param motivoFiscalizacion the motivoFiscalizacion to set
     */
    public void setMotivoFiscalizacion(MotivoFiscalizacionAportanteEnum motivoFiscalizacion) {
        this.motivoFiscalizacion = motivoFiscalizacion;
    }
}
