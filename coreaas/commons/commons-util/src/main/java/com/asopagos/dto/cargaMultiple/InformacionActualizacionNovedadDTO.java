/**
 * 
 */
package com.asopagos.dto.cargaMultiple;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.transversal.personas.AFP;
import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import com.asopagos.dto.modelo.ConfirmacionAbonoBancarioCargueDTO;


/**
 * <b>Descripción:</b> DTO que transporta los datos de actualizacion para una novedad.
 * 
 * @author Jose Arley Correa <jocorrea@heinsohn.com.co>
 */
@XmlRootElement
public class InformacionActualizacionNovedadDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7026049507920703643L;

    /**
     * Contiene la informacion del representanteLegal
     */
    private PersonaModeloDTO representanteLegal;

    /**
     * Contiene la infromacion del beneficiario
     */
    private BeneficiarioModeloDTO beneficiario;

    /**
     * Contiene la informacion del afiliado principal
     */
    private AfiliadoModeloDTO afiliado;

    /**
     * Contiene la informacion del empleador
     */
    private EmpleadorModeloDTO empleador;

    /**
     * Lista con los campos diferentes del archivo contra la BD
     */
    private List<String> camposDiferentes;

    /**
     * Mapa de novedad (tipo transaccion) con lista de campos diferentes con envio al Back (Especialista de novedades)
     */
    private Map<TipoTransaccionEnum, List<String>> tiposNovedad;
    /**
     * Mapa de novedad (tipo transaccion) con lista de campos diferentes con Cierre automatico
     */
    private Map<TipoTransaccionEnum, List<String>> tiposNovedadConCierreAutomatico;
    // Campos que no pertenecen a las entidades del sistema
    /**
     * Contiene la fecha de renovacion de camara de comercio contenida en el archivo
     */
    private Long fechaRenovacionCamaraComercio;

    /**
     * Contiene la actividdad secundaria contenida en el archivo
     */
    private String actividadEconomicaSecundaria;

    /**
     * Contiene la nacionalidad contenida en el archivo
     */
    private String nacionalidad;

    /**
     * Contiene la condicion especial de pago contenida en el archivo
     */
    private String condicionEspecialPago;

    /**
     * Identifica a la ubicación de nacimiento de la persona
     */
    private UbicacionModeloDTO ubicacionNacimientoModeloDTO;

    /**
     * Identifica a la ubicación de expedicion de documento de la persona
     */
    private UbicacionModeloDTO ubicacionExpedicionDocModeloDTO;

    /**
     * Clasificacion de la persona objeto de la novedad
     */
    private ClasificacionEnum clasificacion;

    /**
     * Representa la administradora de Pension
     */
    private AFP administradoraPension;

    /**
     * Indica si es pensionado
     */
    private Boolean pensionado;

    /**
     * Identificador ECM del archivo
     */
    private String codigoIdentificacionECM;

    /**
     * Tipo Archivo respuesta enviado
     */
    private TipoArchivoRespuestaEnum tipoArchivoRespuesta;

    /**
     * Contiene el tipo de solicitante
     */
    private String tipoSolicitante;
    
    /**
     * Campo para transportar el idcargue al nuevo servicio GLPI 55721
     */
    Long idCargue;
    
    /**
     * Campo para transportar la informacion para sustitucion patronal GLPI 62260
     */
    private DatosEmpleadorNovedadDTO datosEmpleadorNovedadDTO;
    
	/**
     * DTO que guarda la info del cargue de confirmacion abonos bancarios
     */
    private ConfirmacionAbonoBancarioCargueDTO confirmacionAbonoAdminSubsidio;
	
    /**
     * DTO que guarda la info del cargue de confirmacion abonos bancarios
     */
    private Boolean primerIntento;

    /**
     * 
     */
    public InformacionActualizacionNovedadDTO() {
        super();
    }

    /**
     * @return the representanteLegal
     */
    public PersonaModeloDTO getRepresentanteLegal() {
        return representanteLegal;
    }

    /**
     * @param representanteLegal
     *        the representanteLegal to set
     */
    public void setRepresentanteLegal(PersonaModeloDTO representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    /**
     * @return the beneficiario
     */
    public BeneficiarioModeloDTO getBeneficiario() {
        return beneficiario;
    }

    /**
     * @param beneficiario
     *        the beneficiario to set
     */
    public void setBeneficiario(BeneficiarioModeloDTO beneficiario) {
        this.beneficiario = beneficiario;
    }

    /**
     * @return the afiliado
     */
    public AfiliadoModeloDTO getAfiliado() {
        return afiliado;
    }

    /**
     * @param afiliado
     *        the afiliado to set
     */
    public void setAfiliado(AfiliadoModeloDTO afiliado) {
        this.afiliado = afiliado;
    }

    /**
     * @return the empleador
     */
    public EmpleadorModeloDTO getEmpleador() {
        return empleador;
    }

    /**
     * @param empleador
     *        the empleador to set
     */
    public void setEmpleador(EmpleadorModeloDTO empleador) {
        this.empleador = empleador;
    }

    /**
     * @return the camposDiferentes
     */
    public List<String> getCamposDiferentes() {
        return camposDiferentes;
    }

    /**
     * @param camposDiferentes
     *        the camposDiferentes to set
     */
    public void setCamposDiferentes(List<String> camposDiferentes) {
        this.camposDiferentes = camposDiferentes;
    }

    /**
     * @return the tiposNovedad
     */
    public Map<TipoTransaccionEnum, List<String>> getTiposNovedad() {
        return tiposNovedad;
    }

    /**
     * @param tiposNovedad
     *        the tiposNovedad to set
     */
    public void setTiposNovedad(Map<TipoTransaccionEnum, List<String>> tiposNovedad) {
        this.tiposNovedad = tiposNovedad;
    }

    /**
     * @return the fechaRenovacionCamaraComercio
     */
    public Long getFechaRenovacionCamaraComercio() {
        return fechaRenovacionCamaraComercio;
    }

    /**
     * @param fechaRenovacionCamaraComercio
     *        the fechaRenovacionCamaraComercio to set
     */
    public void setFechaRenovacionCamaraComercio(Long fechaRenovacionCamaraComercio) {
        this.fechaRenovacionCamaraComercio = fechaRenovacionCamaraComercio;
    }

    /**
     * @return the actividadEconomicaSecundaria
     */
    public String getActividadEconomicaSecundaria() {
        return actividadEconomicaSecundaria;
    }

    /**
     * @param actividadEconomicaSecundaria
     *        the actividadEconomicaSecundaria to set
     */
    public void setActividadEconomicaSecundaria(String actividadEconomicaSecundaria) {
        this.actividadEconomicaSecundaria = actividadEconomicaSecundaria;
    }

    /**
     * @return the nacionalidad
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * @param nacionalidad
     *        the nacionalidad to set
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * @return the condicionEspecialPago
     */
    public String getCondicionEspecialPago() {
        return condicionEspecialPago;
    }

    /**
     * @param condicionEspecialPago
     *        the condicionEspecialPago to set
     */
    public void setCondicionEspecialPago(String condicionEspecialPago) {
        this.condicionEspecialPago = condicionEspecialPago;
    }

    /**
     * @return the ubicacionNacimientoModeloDTO
     */
    public UbicacionModeloDTO getUbicacionNacimientoModeloDTO() {
        return ubicacionNacimientoModeloDTO;
    }

    /**
     * @param ubicacionNacimientoModeloDTO
     *        the ubicacionNacimientoModeloDTO to set
     */
    public void setUbicacionNacimientoModeloDTO(UbicacionModeloDTO ubicacionNacimientoModeloDTO) {
        this.ubicacionNacimientoModeloDTO = ubicacionNacimientoModeloDTO;
    }

    /**
     * @return the ubicacionExpedicionDocModeloDTO
     */
    public UbicacionModeloDTO getUbicacionExpedicionDocModeloDTO() {
        return ubicacionExpedicionDocModeloDTO;
    }

    /**
     * @param ubicacionExpedicionDocModeloDTO
     *        the ubicacionExpedicionDocModeloDTO to set
     */
    public void setUbicacionExpedicionDocModeloDTO(UbicacionModeloDTO ubicacionExpedicionDocModeloDTO) {
        this.ubicacionExpedicionDocModeloDTO = ubicacionExpedicionDocModeloDTO;
    }

    /**
     * @return the clasificacion
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    /**
     * @param clasificacion
     *        the clasificacion to set
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @return the administradoraPension
     */
    public AFP getAdministradoraPension() {
        return administradoraPension;
    }

    /**
     * @param administradoraPension
     *        the administradoraPension to set
     */
    public void setAdministradoraPension(AFP administradoraPension) {
        this.administradoraPension = administradoraPension;
    }

    /**
     * @return the esPensionado
     */
    public Boolean isPensionado() {
        return pensionado;
    }

    /**
     * @param esPensionado
     *        the esPensionado to set
     */
    public void setPensionado(Boolean pensionado) {
        this.pensionado = pensionado;
    }

    /**
     * @return the codigoIdentificacionECM
     */
    public String getCodigoIdentificacionECM() {
        return codigoIdentificacionECM;
    }

    /**
     * @param codigoIdentificacionECM
     *        the codigoIdentificacionECM to set
     */
    public void setCodigoIdentificacionECM(String codigoIdentificacionECM) {
        this.codigoIdentificacionECM = codigoIdentificacionECM;
    }

    /**
     * @return the tipoArchivoRespuesta
     */
    public TipoArchivoRespuestaEnum getTipoArchivoRespuesta() {
        return tipoArchivoRespuesta;
    }

    /**
     * @param tipoArchivoRespuesta
     *        the tipoArchivoRespuesta to set
     */
    public void setTipoArchivoRespuesta(TipoArchivoRespuestaEnum tipoArchivoRespuesta) {
        this.tipoArchivoRespuesta = tipoArchivoRespuesta;
    }

    /**
     * @return the pensionado
     */
    public Boolean getPensionado() {
        return pensionado;
    }

    /**
     * @return the tipoSolicitante
     */
    public String getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante
     *        the tipoSolicitante to set
     */
    public void setTipoSolicitante(String tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public Long getIdCargue() {
        return idCargue;
    }

    public void setIdCargue(Long idCargue) {
        this.idCargue = idCargue;
    }

    public DatosEmpleadorNovedadDTO getDatosEmpleadorNovedadDTO() {
        return datosEmpleadorNovedadDTO;
    }

    public void setDatosEmpleadorNovedadDTO(DatosEmpleadorNovedadDTO datosEmpleadorNovedadDTO) {
        this.datosEmpleadorNovedadDTO = datosEmpleadorNovedadDTO;
    }
	
	public ConfirmacionAbonoBancarioCargueDTO getConfirmacionAbonoAdminSubsidio() {
        return confirmacionAbonoAdminSubsidio;
    }

    public void setConfirmacionAbonoAdminSubsidio(ConfirmacionAbonoBancarioCargueDTO confirmacionAbonoAdminSubsidio) {
        this.confirmacionAbonoAdminSubsidio = confirmacionAbonoAdminSubsidio;
    }

    public Map<TipoTransaccionEnum, List<String>> getTiposNovedadConCierreAutomatico() {
        return tiposNovedadConCierreAutomatico;
    }

    public void setTiposNovedadConCierreAutomatico(Map<TipoTransaccionEnum, List<String>> tiposNovedadConCierreAutomatico) {
        this.tiposNovedadConCierreAutomatico = tiposNovedadConCierreAutomatico;
    }

    public Boolean getPrimerIntento() {
        return this.primerIntento;
    }

    public void setPrimerIntento(Boolean primerIntento) {
        this.primerIntento = primerIntento;
    }

}