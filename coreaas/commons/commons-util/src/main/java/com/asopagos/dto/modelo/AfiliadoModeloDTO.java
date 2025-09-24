package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.personas.Pais;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import com.asopagos.enumeraciones.core.CausaServiciosSinAfiliacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO que contiene los campos de un Afiliado.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class AfiliadoModeloDTO extends PersonaModeloDTO implements Serializable {
    /**
     * Código identificador de llave primaria del afiliado
     */
    private Long idAfiliado;

    /**
     * Atributo que indica si un afiliado tiene o no pignoración de subsidio
     */
    private Boolean pignoracionSubsidio;

    /**
     * Atributo que indica si un afiliado tiene o no cesión de subsidio
     */
    private Boolean cesionSubsidio;

    /**
     * Atributo que indica si un afiliado tiene o no retención de subsidio
     */
    private Boolean retencionSubsidio;

    /**
     * Atributo que indica si un afiliado tiene o no servicios sin afiliación
     */
    private Boolean servicioSinAfiliacion;

    /**
     * Atributo que indica la causa de los servicios sin afiliación
     */
    private CausaServiciosSinAfiliacionEnum causaSinAfiliacion;
    /**
     * Atributo que indica la fecha de inicio servicios sin afiliación
     */
    private Long fechaInicioServiciosSinAfiliacion;
    /**
     * Atributo que indica la fecha fin de servicios sin afiliación
     */
    private Long fechaFinServicioSinAfiliacion;    
    /**
     * Atributo que indica la la sucursal del afiliado
     */
    private String sucursalAfiliado;
    /**
     * Atributo que indica la informacion del empleador del trabajador
     */
    private EmpleadorModeloDTO empleador;
    /**
     * Atributo que indica la fecha de inicio de labores del afiliado
     */
    private Long fechaInicioLaboresAfiliado;
    /**
     * Atributo que indica la fecha de inicio de afiliacion
     */
    private Long fechaInicioAfiliacion;
    /**
     * Atributo que indica el salario del afiliado
     */
    private String salarioAfiliado;
    /**
     * Atributo que indica la fecha de retiro de labores del afiliado
     */
    private Long fechaRetiroAfiliado;
    /**
     * Atributo que indica el motivo de retiro del afiliado
     */
    private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacionTrabajador;
    /**
    * Motivo de desafiliación
    */
   private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacionBeneficiario;
    /**
     * Atributo que indica la fecha fin labores con el empleador origen
     */
//     @JsonIgnore
    private Long fechaFinLaboresEmpleadorOrigen;     
    /**
     * Atributo que indica la fecha inicio labores con el empleadore destino
     */
//    @JsonIgnore
    private Long fechaInicioLaboresEmpleadorDestino;

    /**
     * Atributo que indica el correo electrónico del afiliado
     */
    private String email;
    /**
     * Atributo que indica el celular del afiliado
     */
    private String celular;
    /**
     * Atributo que indica el telefono del afiliado
     */
    private String telefono;
    /**
     * Atributo que indica el idRolAfiliado para la actualización del salario
     */
    private Long idRolAfiliado;
    /**
     * Atributo que indica el MedioDePagoModeloDTO
     */
    private MedioDePagoModeloDTO medioDePagoModeloDTO;
    /**
     * Atributo que indica el pais
     */
    private Pais pais;
    
    /**
     * Constructor por defecto
     * */
    public AfiliadoModeloDTO(){
        super();
    }
    
    /**
     * Constructor con base en entity
     * */
    public AfiliadoModeloDTO(Afiliado afiliado){
        super();
        this.convertToDTO(afiliado, null);
    }

    /**
     * Método encargado de convertir de DTO a Entidad.
     * @param this
     *        DTO a convertir.
     * @return entidad convertida.
     */
    public Afiliado convertToEntity() {
        Afiliado afiliado = new Afiliado();
        Persona persona = super.convertToPersonaEntity();
        afiliado.setPersona(persona);
        afiliado.setIdAfiliado(this.getIdAfiliado());
        afiliado.setPignoracionSubsidio(this.getPignoracionSubsidio());
        afiliado.setCesionSubsidio(this.getCesionSubsidio());
        afiliado.setRetencionSubsidio(this.getRetencionSubsidio());
        afiliado.setServicioSinAfiliacion(this.getServicioSinAfiliacion());
        afiliado.setCausaSinAfiliacion(this.getCausaSinAfiliacion());
        if (this.getFechaInicioServiciosSinAfiliacion() != null) {
            afiliado.setFechaInicioServiciosSinAfiliacion(new Date(this.getFechaInicioServiciosSinAfiliacion()));
        }
        if (this.getFechaFinServicioSinAfiliacion() != null) {
            afiliado.setFechaFinServicioSinAfiliacion(new Date(this.getFechaFinServicioSinAfiliacion()));
        }
        return afiliado;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * @param afiliado
     *        entidad a convertir.
     */
    public void convertToDTO(Afiliado afiliado, PersonaDetalle personaDetalle) {
        super.convertToDTO(afiliado.getPersona(), personaDetalle);
        this.setIdAfiliado(afiliado.getIdAfiliado());
        this.setPignoracionSubsidio(afiliado.getPignoracionSubsidio());
        this.setCesionSubsidio(afiliado.getCesionSubsidio());
        this.setRetencionSubsidio(afiliado.getRetencionSubsidio());
        this.setServicioSinAfiliacion(afiliado.getServicioSinAfiliacion());
        this.setCausaSinAfiliacion(afiliado.getCausaSinAfiliacion());
        if (afiliado.getFechaInicioServiciosSinAfiliacion() != null) {
            this.setFechaInicioServiciosSinAfiliacion(afiliado.getFechaInicioServiciosSinAfiliacion().getTime());
        }
        if (afiliado.getFechaFinServicioSinAfiliacion() != null) {
            this.setFechaFinServicioSinAfiliacion(afiliado.getFechaFinServicioSinAfiliacion().getTime());
        }
    }

    /**
     * Método encargado de convertir de Entidad a DTO. Versión simple
     * @param afiliado
     *        entidad a convertir.
     */
    public void convertToDTO(Afiliado afiliado) {
        this.setIdAfiliado(afiliado.getIdAfiliado());
        this.setPignoracionSubsidio(afiliado.getPignoracionSubsidio());
        this.setCesionSubsidio(afiliado.getCesionSubsidio());
        this.setRetencionSubsidio(afiliado.getRetencionSubsidio());
        this.setServicioSinAfiliacion(afiliado.getServicioSinAfiliacion());
        this.setCausaSinAfiliacion(afiliado.getCausaSinAfiliacion());
        if (afiliado.getFechaInicioServiciosSinAfiliacion() != null) {
            this.setFechaInicioServiciosSinAfiliacion(afiliado.getFechaInicioServiciosSinAfiliacion().getTime());
        }
        if (afiliado.getFechaFinServicioSinAfiliacion() != null) {
            this.setFechaFinServicioSinAfiliacion(afiliado.getFechaFinServicioSinAfiliacion().getTime());
        }
    }

    /**
     * Método encargado de copiar un DTO a una Entidad.
     * @param afiliado
     *        previamente consultado.
     */
    public Afiliado copyDTOToEntiy(Afiliado afiliado) {
        Persona persona = super.copyDTOToEntity(afiliado.getPersona());
        if (persona.getIdPersona() != null) {
            afiliado.setPersona(persona);
        }
        if (this.getIdAfiliado() != null) {
            afiliado.setIdAfiliado(this.getIdAfiliado());
        }
        if (this.getPignoracionSubsidio() != null) {
            afiliado.setPignoracionSubsidio(this.getPignoracionSubsidio());
        }
        if (this.getCesionSubsidio() != null) {
            afiliado.setCesionSubsidio(this.getCesionSubsidio());
        }
        if (this.getRetencionSubsidio() != null) {
            afiliado.setRetencionSubsidio(this.getRetencionSubsidio());
        }
        if (this.getServicioSinAfiliacion() != null) {
            afiliado.setServicioSinAfiliacion(this.getServicioSinAfiliacion());
        }
        if (this.getCausaSinAfiliacion() != null) {
            afiliado.setCausaSinAfiliacion(this.getCausaSinAfiliacion());
        }
        if (this.getFechaInicioServiciosSinAfiliacion() != null) {
            afiliado.setFechaInicioServiciosSinAfiliacion(new Date(this.getFechaInicioServiciosSinAfiliacion()));
        }
        if (this.getFechaFinServicioSinAfiliacion() != null) {
            afiliado.setFechaFinServicioSinAfiliacion(new Date(this.getFechaFinServicioSinAfiliacion()));
        }

        return afiliado;
    }

    /**
     * Método que retorna el valor de idAfiliado.
     * @return valor de idAfiliado.
     */
    public Long getIdAfiliado() {
        return idAfiliado;
    }

    /**
     * Método encargado de modificar el valor de idAfiliado.
     * @param valor
     *        para modificar idAfiliado.
     */
    public void setIdAfiliado(Long idAfiliado) {
        this.idAfiliado = idAfiliado;
    }

    /**
     * Método que retorna el valor de pignoracionSubsidio.
     * @return valor de pignoracionSubsidio.
     */
    public Boolean getPignoracionSubsidio() {
        return pignoracionSubsidio;
    }

    /**
     * Método encargado de modificar el valor de pignoracionSubsidio.
     * @param valor
     *        para modificar pignoracionSubsidio.
     */
    public void setPignoracionSubsidio(Boolean pignoracionSubsidio) {
        this.pignoracionSubsidio = pignoracionSubsidio;
    }

    /**
     * Método que retorna el valor de cesionSubsidio.
     * @return valor de cesionSubsidio.
     */
    public Boolean getCesionSubsidio() {
        return cesionSubsidio;
    }

    /**
     * Método encargado de modificar el valor de cesionSubsidio.
     * @param valor
     *        para modificar cesionSubsidio.
     */
    public void setCesionSubsidio(Boolean cesionSubsidio) {
        this.cesionSubsidio = cesionSubsidio;
    }

    /**
     * Método que retorna el valor de retencionSubsidio.
     * @return valor de retencionSubsidio.
     */
    public Boolean getRetencionSubsidio() {
        return retencionSubsidio;
    }

    /**
     * Método encargado de modificar el valor de retencionSubsidio.
     * @param valor
     *        para modificar retencionSubsidio.
     */
    public void setRetencionSubsidio(Boolean retencionSubsidio) {
        this.retencionSubsidio = retencionSubsidio;
    }

    /**
     * Método que retorna el valor de servicioSinAfiliacion.
     * @return valor de servicioSinAfiliacion.
     */
    public Boolean getServicioSinAfiliacion() {
        return servicioSinAfiliacion;
    }

    /**
     * Método encargado de modificar el valor de servicioSinAfiliacion.
     * @param valor
     *        para modificar servicioSinAfiliacion.
     */
    public void setServicioSinAfiliacion(Boolean servicioSinAfiliacion) {
        this.servicioSinAfiliacion = servicioSinAfiliacion;
    }

    /**
     * Método que retorna el valor de causaSinAfiliacion.
     * @return valor de causaSinAfiliacion.
     */
    public CausaServiciosSinAfiliacionEnum getCausaSinAfiliacion() {
        return causaSinAfiliacion;
    }

    /**
     * Método encargado de modificar el valor de causaSinAfiliacion.
     * @param valor
     *        para modificar causaSinAfiliacion.
     */
    public void setCausaSinAfiliacion(CausaServiciosSinAfiliacionEnum causaSinAfiliacion) {
        this.causaSinAfiliacion = causaSinAfiliacion;
    }

    /**
     * Método que retorna el valor de fechaInicioServiciosSinAfiliacion
     * @return fechaInicioServiciosSinAfiliacion
     */
    public Long getFechaInicioServiciosSinAfiliacion() {
        return fechaInicioServiciosSinAfiliacion;
    }

    /**
     * Método que modifica el valor de fechaInicioServiciosSinAfiliacion
     * @param fechaInicioServiciosSinAfiliacion
     */
    public void setFechaInicioServiciosSinAfiliacion(Long fechaInicioServiciosSinAfiliacion) {
        this.fechaInicioServiciosSinAfiliacion = fechaInicioServiciosSinAfiliacion;
    }

    /**
     * Método que retorna el valor de fechaFinServicioSinAfiliacion
     * @return fechaFinServicioSinAfiliacion
     */
    public Long getFechaFinServicioSinAfiliacion() {
        return fechaFinServicioSinAfiliacion;
    }

    /**
     * Método que modifica el valor de fechaFinServicioSinAfiliacion
     * @param fechaFinServicioSinAfiliacion
     */
    public void setFechaFinServicioSinAfiliacion(Long fechaFinServicioSinAfiliacion) {
        this.fechaFinServicioSinAfiliacion = fechaFinServicioSinAfiliacion;
    }

    public String getSucursalAfiliado() {
        return sucursalAfiliado;
    }

    public void setSucursalAfiliado(String sucursalAfiliado) {
        this.sucursalAfiliado = sucursalAfiliado;
    }

    public EmpleadorModeloDTO getEmpleador() {
        return empleador;
    }

    public void setEmpleador(EmpleadorModeloDTO empleador) {
        this.empleador = empleador;
    }

    public Long getFechaInicioLaboresAfiliado() {
        return fechaInicioLaboresAfiliado;
    }

    public void setFechaInicioLaboresAfiliado(Long fechaInicioLaboresAfiliado) {
        this.fechaInicioLaboresAfiliado = fechaInicioLaboresAfiliado;
    }

    public Long getFechaInicioAfiliacion() {
        return fechaInicioAfiliacion;
    }

    public void setFechaInicioAfiliacion(Long fechaInicioAfiliacion) {
        this.fechaInicioAfiliacion = fechaInicioAfiliacion;
    }

    public String getSalarioAfiliado() {
        return salarioAfiliado;
    }

    public void setSalarioAfiliado(String salarioAfiliado) {
        this.salarioAfiliado = salarioAfiliado;
    }

    public Long getFechaRetiroAfiliado() {
        return fechaRetiroAfiliado;
    }

    public void setFechaRetiroAfiliado(Long fechaRetiroAfiliado) {
        this.fechaRetiroAfiliado = fechaRetiroAfiliado;
    }

    public MotivoDesafiliacionAfiliadoEnum getMotivoDesafiliacionTrabajador() {
        return motivoDesafiliacionTrabajador;
    }

    public void setMotivoDesafiliacionTrabajador(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacionTrabajador) {
        this.motivoDesafiliacionTrabajador = motivoDesafiliacionTrabajador;
    }

    public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacionBeneficiario() {
        return motivoDesafiliacionBeneficiario;
    }

    public void setMotivoDesafiliacionBeneficiario(MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacionBeneficiario) {
        this.motivoDesafiliacionBeneficiario = motivoDesafiliacionBeneficiario;
    }

    public Long getFechaFinLaboresEmpleadorOrigen() {
        return fechaFinLaboresEmpleadorOrigen;
    }

    public void setFechaFinLaboresEmpleadorOrigen(Long fechaFinLaboresEmpleadorOrigen) {
        this.fechaFinLaboresEmpleadorOrigen = fechaFinLaboresEmpleadorOrigen;
    }

    public Long getFechaInicioLaboresEmpleadorDestino() {
        return fechaInicioLaboresEmpleadorDestino;
    }

    public void setFechaInicioLaboresEmpleadorDestino(Long fechaInicioLaboresEmpleadorDestino) {
        this.fechaInicioLaboresEmpleadorDestino = fechaInicioLaboresEmpleadorDestino;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getIdRolAfiliado() {
        return idRolAfiliado;
    }

    public void setIdRolAfiliado(Long idRolAfiliado) {
        this.idRolAfiliado = idRolAfiliado;
    }

    public MedioDePagoModeloDTO getMedioDePagoModeloDTO() {
        return medioDePagoModeloDTO;
    }

    public void setMedioDePagoModeloDTO(MedioDePagoModeloDTO medioDePagoModeloDTO) {
        this.medioDePagoModeloDTO = medioDePagoModeloDTO;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
