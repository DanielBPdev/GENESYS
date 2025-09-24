package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.transversal.core.CodigoCIIU;
import com.asopagos.entidades.transversal.personas.ARL;
import com.asopagos.enumeraciones.cartera.MotivoFiscalizacionAportanteEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;

/**
 * DTO que contiene los campos de los roles de un afiliado.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class EmpresaModeloDTO extends PersonaModeloDTO implements Serializable {
    /**
     * Código identificador de llave primaria de la empresa
     */
    private Long idEmpresa;

    /**
     * Nombre comercial de la empresa
     */
    private String nombreComercial;

    /**
     * Fecha de constitución de la empresa
     */
    private Long fechaConstitucion;

    /**
     * Descripción de la naturalez jurídica
     */
    private NaturalezaJuridicaEnum naturalezaJuridica;

    /**
     * Código CIIU (Clasificación Industrial Internacional Uniforme de todas las actividades económicas)
     */
    private CodigoCIIU codigoCIIU;

    /**
     * Id que identifica a la ARL asociada a la empresa
     */
    private ARL arl;

    /**
     * Código de la última caja de compensación de la empresa
     */
    private Integer idUltimaCajaCompensacion;

    /**
     * Página web
     */
    private String paginaWeb;

    /**
     * Id que identifica al representante legal asociado a la empresa
     */
    private Long idPersonaRepresentanteLegal;

    /**
     * Id que identifica al representante legal suplente asociado a la empresa
     */
    private Long idPersonaRepresentanteLegalSuplente;

    /**
     * Indicador S/N si la empresa cuenta con revisión especial [S=Si N=No]
     */
    private Boolean especialRevision;

    /**
     * Id que identifica al representante legal asociado a la empresa
     */
    private Long idUbicacionRepresentanteLegal;

    /**
     * Id que identifica al representante legal suplente asociado a la empresa
     */
    private Long idUbicacionRepresentanteLegalSuplente;

    /**
     * Marca de empresa para proceso de fiscalización de aportes
     */
    private Boolean enviadoAFiscalizacion;
    
    /**
     * Motivo de la marca de envío a fiscalización
     */
    private MotivoFiscalizacionAportanteEnum motivoFiscalizacion;
    
    /**
     * Fecha de fiscalización de la empresa
     */
    private Long fechaFiscalizacion;
    
    /**
     * Bandera que indica si el convenio ya existe para la empresa consultada
     */
    private Boolean existeConvenio;
    
    /**
     * Constructor por defecto
     * */
    public EmpresaModeloDTO(){
        super();
    }
    
    /**
     * Constructor con base en entity
     * */
    public EmpresaModeloDTO(Empresa emp){
        super();
        this.convertToDTO(emp);
    }

    /**
     * Método encargado de convertir de Entidad a DTO
     * @param empleador
     *        entidad a convertir.
     * @return DTO convertido
     */
    public EmpresaModeloDTO convertToDTO(Empresa empresa) {
        super.convertToDTO(empresa.getPersona(), null);
        if (empresa.getArl() != null && empresa.getArl().getIdARL() != null) {
            ARL arl = new ARL();
            arl.setIdARL(empresa.getArl().getIdARL());
            arl.setNombre(empresa.getArl().getNombre());
            this.setArl(arl);
        }
        if (empresa.getCodigoCIIU() != null && empresa.getCodigoCIIU().getIdCodigoCIIU() != null) {
            CodigoCIIU codigo = new CodigoCIIU();
            codigo.setIdCodigoCIIU(empresa.getCodigoCIIU().getIdCodigoCIIU());
            codigo.setCodigo(empresa.getCodigoCIIU().getCodigo());
            codigo.setDescripcion(empresa.getCodigoCIIU().getDescripcion());
            codigo.setCodigoSeccion(empresa.getCodigoCIIU().getCodigoSeccion());
            codigo.setDescripcionSeccion(empresa.getCodigoCIIU().getDescripcionSeccion());
            codigo.setCodigoDivision(empresa.getCodigoCIIU().getCodigoDivision());
            codigo.setDescripcionDivision(empresa.getCodigoCIIU().getDescripcionDivision());
            codigo.setCodigoGrupo(empresa.getCodigoCIIU().getCodigoGrupo());
            codigo.setDescripcionGrupo(empresa.getCodigoCIIU().getCodigoGrupo());
            this.setCodigoCIIU(codigo);

        }
        this.setEspecialRevision(empresa.getEspecialRevision());
        if (empresa.getFechaConstitucion() != null) {
            this.setFechaConstitucion(empresa.getFechaConstitucion().getTime());
        }
        this.setIdEmpresa(empresa.getIdEmpresa());
        this.setIdPersonaRepresentanteLegal(empresa.getIdPersonaRepresentanteLegal());
        this.setIdPersonaRepresentanteLegalSuplente(empresa.getIdPersonaRepresentanteLegalSuplente());
        this.setIdUbicacionRepresentanteLegal(empresa.getIdUbicacionRepresentanteLegal());
        this.setIdUbicacionRepresentanteLegalSuplente(empresa.getIdUbicacionRepresentanteLegalSuplente());
        this.setIdUltimaCajaCompensacion(empresa.getIdUltimaCajaCompensacion());
        this.setNaturalezaJuridica(empresa.getNaturalezaJuridica());
        this.setNombreComercial(empresa.getNombreComercial());
        this.setPaginaWeb(empresa.getPaginaWeb());

        if(empresa.getEnviadoAFiscalizacion() != null)
        {
            this.setEnviadoAFiscalizacion(empresa.getEnviadoAFiscalizacion());
        }
        if(empresa.getMotivoFiscalizacion() != null){
            this.setMotivoFiscalizacion(empresa.getMotivoFiscalizacion());
        }
        if(empresa.getFechaFiscalizacion() != null){
            this.setFechaFiscalizacion(empresa.getFechaFiscalizacion().getTime());
        }
        return this;
    }

    /**
     * Método encargado de convertir un DTO a Entidad
     * @return entidad convertida.
     */
    public Empresa convertToEmpresaEntity() {
        Empresa empresa = new Empresa();
        empresa.setArl(this.getArl());
        //empresa.setCodigoCIIU(this.getCodigoCIIU());
        empresa.setEspecialRevision(this.getEspecialRevision());
        if (this.getFechaConstitucion() != null) {
            empresa.setFechaConstitucion(new Date(this.getFechaConstitucion()));
        }
        empresa.setIdEmpresa(this.getIdEmpresa());
        empresa.setIdPersonaRepresentanteLegal(this.getIdPersonaRepresentanteLegal());
        empresa.setIdPersonaRepresentanteLegalSuplente(this.getIdPersonaRepresentanteLegalSuplente());
        empresa.setIdUbicacionRepresentanteLegal(this.getIdUbicacionRepresentanteLegal());
        empresa.setIdUbicacionRepresentanteLegalSuplente(this.getIdUbicacionRepresentanteLegalSuplente());
        empresa.setIdUltimaCajaCompensacion(this.getIdUltimaCajaCompensacion());
        empresa.setNaturalezaJuridica(this.getNaturalezaJuridica());
        empresa.setNombreComercial(this.getNombreComercial());
        empresa.setPaginaWeb(this.getPaginaWeb());
        if (this.getCreadoPorPila() != null) {
            empresa.setCreadoPorPila(this.getCreadoPorPila());
        }
        if(this.getEnviadoAFiscalizacion() != null){
            empresa.setEnviadoAFiscalizacion(this.getEnviadoAFiscalizacion());
        }
        if(this.getMotivoFiscalizacion() != null){
            empresa.setMotivoFiscalizacion(this.getMotivoFiscalizacion());
        }
        if(this.getFechaFiscalizacion() != null){
            empresa.setFechaFiscalizacion(new Date(this.getFechaFiscalizacion()));
        }
        
        Persona persona = super.convertToPersonaEntity();
        empresa.setPersona(persona);
        return empresa;

    }

    /**
     * Método que copia un DTO a una entidad.
     * @param empresa
     *        previamente consultada
     * @return empresa copiada.
     */
    public Empresa copyDTOToEntity(Empresa empresa) {
        if (this.getArl() != null) {
            empresa.setArl(this.getArl());
        }
        if (this.getCodigoCIIU() != null) {
            empresa.setCodigoCIIU(this.getCodigoCIIU());
        }
        if (this.getEspecialRevision() != null) {
            empresa.setEspecialRevision(this.getEspecialRevision());
        }
        if (this.getFechaConstitucion() != null) {
            empresa.setFechaConstitucion(new Date(this.getFechaConstitucion()));
        }
        if (this.getIdEmpresa() != null) {
            empresa.setIdEmpresa(this.getIdEmpresa());
        }
        if (this.getIdPersonaRepresentanteLegal() != null) {
            empresa.setIdPersonaRepresentanteLegal(this.getIdPersonaRepresentanteLegal());
        }
        if (this.getIdPersonaRepresentanteLegalSuplente() != null) {
            empresa.setIdPersonaRepresentanteLegalSuplente(this.getIdPersonaRepresentanteLegalSuplente());
        }
        if (this.getIdUbicacionRepresentanteLegal() != null) {
            empresa.setIdUbicacionRepresentanteLegal(this.getIdUbicacionRepresentanteLegal());
        }
        if (this.getIdUbicacionRepresentanteLegalSuplente() != null) {
            empresa.setIdUbicacionRepresentanteLegalSuplente(this.getIdUbicacionRepresentanteLegalSuplente());
        }
        if (this.getIdUltimaCajaCompensacion() != null) {
            empresa.setIdUltimaCajaCompensacion(this.getIdUltimaCajaCompensacion());
        }
        if (this.getNaturalezaJuridica() != null) {
            empresa.setNaturalezaJuridica(this.getNaturalezaJuridica());
        }
        if (this.getNombreComercial() != null) {
            empresa.setNombreComercial(this.getNombreComercial());
        }
        if (this.getPaginaWeb() != null) {
            empresa.setPaginaWeb(this.getPaginaWeb());
        }
        Persona persona = super.copyDTOToEntity(empresa.getPersona());
        if (persona.getIdPersona() != null) {
            empresa.setPersona(persona);
        }
        if(this.getEnviadoAFiscalizacion() != null){
            empresa.setEnviadoAFiscalizacion(this.getEnviadoAFiscalizacion());
        }
        if(this.getMotivoFiscalizacion() != null){
            empresa.setMotivoFiscalizacion(this.getMotivoFiscalizacion());
        }
        if(this.getFechaFiscalizacion() != null){
            empresa.setFechaFiscalizacion(new Date(this.getFechaFiscalizacion()));
        }
        return empresa;

    }

    /**
     * Método que retorna el valor de idEmpresa.
     * @return valor de idEmpresa.
     */
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * Método encargado de modificar el valor de idEmpresa.
     * @param valor
     *        para modificar idEmpresa.
     */
    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * Método que retorna el valor de nombreComercial.
     * @return valor de nombreComercial.
     */
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * Método encargado de modificar el valor de nombreComercial.
     * @param valor
     *        para modificar nombreComercial.
     */
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    /**
     * Método que retorna el valor de fechaConstitucion.
     * @return valor de fechaConstitucion.
     */
    public Long getFechaConstitucion() {
        return fechaConstitucion;
    }

    /**
     * Método encargado de modificar el valor de fechaConstitucion.
     * @param valor
     *        para modificar fechaConstitucion.
     */
    public void setFechaConstitucion(Long fechaConstitucion) {
        this.fechaConstitucion = fechaConstitucion;
    }

    /**
     * Método que retorna el valor de naturalezaJuridica.
     * @return valor de naturalezaJuridica.
     */
    public NaturalezaJuridicaEnum getNaturalezaJuridica() {
        return naturalezaJuridica;
    }

    /**
     * Método encargado de modificar el valor de naturalezaJuridica.
     * @param valor
     *        para modificar naturalezaJuridica.
     */
    public void setNaturalezaJuridica(NaturalezaJuridicaEnum naturalezaJuridica) {
        this.naturalezaJuridica = naturalezaJuridica;
    }

    /**
     * Método que retorna el valor de codigoCIIU.
     * @return valor de codigoCIIU.
     */
    public CodigoCIIU getCodigoCIIU() {
        return codigoCIIU;
    }

    /**
     * Método encargado de modificar el valor de codigoCIIU.
     * @param valor
     *        para modificar codigoCIIU.
     */
    public void setCodigoCIIU(CodigoCIIU codigoCIIU) {
        this.codigoCIIU = codigoCIIU;
    }

    /**
     * Método que retorna el valor de arl.
     * @return valor de arl.
     */
    public ARL getArl() {
        return arl;
    }

    /**
     * Método encargado de modificar el valor de arl.
     * @param valor
     *        para modificar arl.
     */
    public void setArl(ARL arl) {
        this.arl = arl;
    }

    /**
     * Método que retorna el valor de idUltimaCajaCompensacion.
     * @return valor de idUltimaCajaCompensacion.
     */
    public Integer getIdUltimaCajaCompensacion() {
        return idUltimaCajaCompensacion;
    }

    /**
     * Método encargado de modificar el valor de idUltimaCajaCompensacion.
     * @param valor
     *        para modificar idUltimaCajaCompensacion.
     */
    public void setIdUltimaCajaCompensacion(Integer idUltimaCajaCompensacion) {
        this.idUltimaCajaCompensacion = idUltimaCajaCompensacion;
    }

    /**
     * Método que retorna el valor de paginaWeb.
     * @return valor de paginaWeb.
     */
    public String getPaginaWeb() {
        return paginaWeb;
    }

    /**
     * Método encargado de modificar el valor de paginaWeb.
     * @param valor
     *        para modificar paginaWeb.
     */
    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    /**
     * Método que retorna el valor de idPersonaRepresentanteLegal.
     * @return valor de idPersonaRepresentanteLegal.
     */
    public Long getIdPersonaRepresentanteLegal() {
        return idPersonaRepresentanteLegal;
    }

    /**
     * Método encargado de modificar el valor de idPersonaRepresentanteLegal.
     * @param valor
     *        para modificar idPersonaRepresentanteLegal.
     */
    public void setIdPersonaRepresentanteLegal(Long idPersonaRepresentanteLegal) {
        this.idPersonaRepresentanteLegal = idPersonaRepresentanteLegal;
    }

    /**
     * Método que retorna el valor de idPersonaRepresentanteLegalSuplente.
     * @return valor de idPersonaRepresentanteLegalSuplente.
     */
    public Long getIdPersonaRepresentanteLegalSuplente() {
        return idPersonaRepresentanteLegalSuplente;
    }

    /**
     * Método encargado de modificar el valor de idPersonaRepresentanteLegalSuplente.
     * @param valor
     *        para modificar idPersonaRepresentanteLegalSuplente.
     */
    public void setIdPersonaRepresentanteLegalSuplente(Long idPersonaRepresentanteLegalSuplente) {
        this.idPersonaRepresentanteLegalSuplente = idPersonaRepresentanteLegalSuplente;
    }

    /**
     * Método que retorna el valor de especialRevision.
     * @return valor de especialRevision.
     */
    public Boolean getEspecialRevision() {
        return especialRevision;
    }

    /**
     * Método encargado de modificar el valor de especialRevision.
     * @param valor
     *        para modificar especialRevision.
     */
    public void setEspecialRevision(Boolean especialRevision) {
        this.especialRevision = especialRevision;
    }

    /**
     * Método que retorna el valor de idUbicacionRepresentanteLegal.
     * @return valor de idUbicacionRepresentanteLegal.
     */
    public Long getIdUbicacionRepresentanteLegal() {
        return idUbicacionRepresentanteLegal;
    }

    /**
     * Método encargado de modificar el valor de idUbicacionRepresentanteLegal.
     * @param valor
     *        para modificar idUbicacionRepresentanteLegal.
     */
    public void setIdUbicacionRepresentanteLegal(Long idUbicacionRepresentanteLegal) {
        this.idUbicacionRepresentanteLegal = idUbicacionRepresentanteLegal;
    }

    /**
     * Método que retorna el valor de idUbicacionRepresentanteLegalSuplente.
     * @return valor de idUbicacionRepresentanteLegalSuplente.
     */
    public Long getIdUbicacionRepresentanteLegalSuplente() {
        return idUbicacionRepresentanteLegalSuplente;
    }

    /**
     * Método encargado de modificar el valor de idUbicacionRepresentanteLegalSuplente.
     * @param valor
     *        para modificar idUbicacionRepresentanteLegalSuplente.
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

    /**
     * @return the fechaFiscalizacion
     */
    public Long getFechaFiscalizacion() {
        return fechaFiscalizacion;
    }

    /**
     * @param fechaFiscalizacion the fechaFiscalizacion to set
     */
    public void setFechaFiscalizacion(Long fechaFiscalizacion) {
        this.fechaFiscalizacion = fechaFiscalizacion;
    }

    public Boolean getExisteConvenio() {
        return existeConvenio;
    }

    public void setExisteConvenio(Boolean existeConvenio) {
        this.existeConvenio = existeConvenio;
    }
}
