package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.asopagos.dto.CruceDetalleDTO;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.dto.modelo.IntegranteHogarModeloDTO;
import com.asopagos.dto.modelo.JefeHogarModeloDTO;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.entidades.ccf.fovis.JefeHogar;
import com.asopagos.entidades.ccf.fovis.PostulacionFOVIS;
import com.asopagos.entidades.ccf.fovis.SolicitudGestionCruce;
import com.asopagos.entidades.ccf.fovis.SolicitudPostulacion;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.fovis.parametricas.CicloAsignacion;
import com.asopagos.dto.modelo.LegalizacionDesembolosoProveedorModeloDTO;
import com.asopagos.dto.modelo.SolicitudVerificacionFovisModeloDTO;
import com.asopagos.enumeraciones.TipoPostulacionFOVISEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.CausaIntentoFallidoPostulacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoCruceHogarEnum;
import com.asopagos.enumeraciones.fovis.TipoCruceEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudGestionCruceEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoRadicacionEnum;



public class SolicitudPostulacionFOVISDTO implements Serializable {


    /**
     * Serial
     */
    private static final long serialVersionUID = 14542121545454666L;

    /**
     * Identificador único, llave primaria
     */
    private Long idSolicitudPostulacion;

    /**
     * Identificador solicitud global
     */
    private Long idSolicitud;

    /**
     * Identificador solicitud global
     */
    private String numeroRadicacion;

    /**
     * Identificador de la instancia de proceso BPM.
     */
    private Long idInstanciaProceso;

    /**
     * Postulación asociada a la solicitud
     */
    private PostulacionFOVISModeloDTO postulacion;

    /**
     * Lista de chequeo de requisitos documentales generales de la postulación
     */
    private ListaChequeoDTO listaChequeo;

    /**
     * Lista de los miembros del hogar asociados a la solicitud postulación
     */
    private List<IntegranteHogarModeloDTO> integrantesHogar;

    /**
     * Estado de la solicitud postulación.
     */
    private EstadoSolicitudPostulacionEnum estadoSolicitud;

    /**
     * Lista de condiciones especiales asociada a la Persona
     */
    private List<NombreCondicionEspecialEnum> condicionesEspeciales;

    /**
     * Información del oferente
     */
    private OferenteDTO oferente;

    /**
     * Información del proveedor
     */
    private List<LegalizacionDesembolosoProveedorModeloDTO> legalizacionProveedor;

    /**
     * Escalamiento a miembros del hogar
     */
    private EscalamientoSolicitudDTO escalamientoMiembrosHogar;

    /**
     * Escalamiento jurídico
     */
    private EscalamientoSolicitudDTO escalamientoJuridico;

    /**
     * Escalamiento al técnico en construcción
     */
    private EscalamientoSolicitudDTO escalamientoTecnicoConstruccion;

    /**
     * Escalamiento a miembros del hogar reportado por back
     */
    private EscalamientoSolicitudDTO escalamientoMiembrosHogarBack;

    /**
     * Escalamiento jurídico reportado por back
     */
    private EscalamientoSolicitudDTO escalamientoJuridicoBack;

    /**
     * Escalamiento al técnico en construcción reportado por back
     */
    private EscalamientoSolicitudDTO escalamientoTecnicoConstruccionBack;

    /**
     * Escalamiento único asociado en HU 020
     */
    private EscalamientoSolicitudDTO escalamientoEspecial;

    /**
     * Canal de recepcion de la solicitud
     */
    private CanalRecepcionEnum canalRecepcion;

    /**
     * Tipo transaccion en proceso.
     */
    private TipoTransaccionEnum tipoTransaccionEnum;
    /**
     * Tipo transaccion en proceso.
     */
    private TipoTransaccionEnum tipoTransaccion;

    /**
     * Método de envío de los documentos
     */
    private FormatoEntregaDocumentoEnum metodoEnvio;

    /**
     * Tarea activa.
     */
    private Long idTarea;

    /**
     * Indica la causa por la cual un intento de postulación no pudo ser
     * radicado
     */
    private CausaIntentoFallidoPostulacionEnum causaIntentoFallido;

    /**
     * Formulario
     */
    private String formularioWeb;

    /**
     * Observaciones de la solicitud de postulación para control interno.
     */
    private String observaciones;

    /**
     * Observaciones de la solicitud de postulación para el usuario web.
     */
    private String observacionesWeb;

    /**
     * Identificador de la postulacion
     */
    private Long idPostulacion;

    /**
     * Fecha radicacion
     */
    private Long fechaRadicacion;

    /**
     * Identificador comunicado solicitud
     */
    private String idComunicadoSolicitud;

    /**
     * Tipo de solicitud
     */
    private TipoSolicitudEnum tipoSolicitud;

    /**
     * Indica el tipo de postulación
     */
    private TipoPostulacionFOVISEnum tipoPostulacion;

    /**
     * Indica el estado de la documentación
     */
    private EstadoDocumentacionEnum estadoDocumentacion;

    private SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisModeloDTO;

    private SolicitudPostulacionFOVISDTO datosPostulacionFovis;

    private String idDocumentoControlInterno;

    private String idCajaCorrespondencia;

    private String clasificacion;

    private TipoRadicacionEnum tipoRadicacion;

    private String usuarioRadicacion;

    private String ciudadSedeRadicacion;

    private String anulada;

    private String cargaAfiliacionMultiple;

    private String destinatario;

    private EstadoCruceHogarEnum estadoCruceHogar;

    private Long idDiferenciaCargueActualizacion;

    private Long numeroRadicadoPostulacion;

    private String resultadoProceso;

    private Long sedeDestinatario;

    private TipoCruceEnum tipoCruce;

    private EstadoSolicitudGestionCruceEnum estadoSolicitudGestionCruce;

    private Long fechaCreacion;


    private Long idSolicitudGestionCruce;


    private String observacion;
    /**
     * Lista de cruces asociados a una solicitud gestion
     */
    private List<CruceDetalleDTO> listCrucesAsociados;

    private Date fechaValidacionCruce;

    /**
     * Constructor por defecto
     */
    public SolicitudPostulacionFOVISDTO() {
        super();
    }

    /**
     * Constructor de la clase
     *
     * @param idSolicitudPostulacion
     * @param idSolicitud
     * @param numeroRadicacion
     * @param postulacion
     */
    public SolicitudPostulacionFOVISDTO(String idSolicitud, String numeroRadicacion, Date fechaRadicacion,
                                        String idSolicitudPostulacion, String idPostulacion, EstadoSolicitudPostulacionEnum estadoSolicitud,
                                        String idComunicadoSolicitud) {
        super();
        this.idSolicitudPostulacion = idSolicitudPostulacion != null ? Long.parseLong(idSolicitudPostulacion) : null;
        this.idSolicitud = idSolicitud != null ? Long.parseLong(idSolicitud) : null;
        this.numeroRadicacion = numeroRadicacion;
        this.idPostulacion = idPostulacion != null ? Long.parseLong(idPostulacion) : null;
        this.estadoSolicitud = estadoSolicitud;
        this.fechaRadicacion = fechaRadicacion != null ? fechaRadicacion.getTime() : null;
        this.idComunicadoSolicitud = idComunicadoSolicitud;
        this.tipoSolicitud = TipoSolicitudEnum.POSTULACION_FOVIS;
    }

    /**
     * Constructor con los datos generales de la postulacion
     * @param solicitudPostulacion
     *        Info Solicitud
     * @param postulacionFOVIS
     *        Info Postulacion
     * @param cicloAsignacion
     *        Info CicloAsignacion
     * @param jefeHogar
     *        Info JefeHogar
     * @param persona
     *        Info Persona
     * @param personaDetalle
     *        Info PersonaDetalle
     */
    public SolicitudPostulacionFOVISDTO(SolicitudPostulacion solicitudPostulacion, PostulacionFOVIS postulacionFOVIS,
                                        CicloAsignacion cicloAsignacion, JefeHogar jefeHogar, Persona persona, PersonaDetalle personaDetalle) {
        this.setIdSolicitudPostulacion(solicitudPostulacion.getIdSolicitudPostulacion());
        this.setIdSolicitud(solicitudPostulacion.getSolicitudGlobal().getIdSolicitud());
        this.setNumeroRadicacion(solicitudPostulacion.getSolicitudGlobal().getNumeroRadicacion());
        this.setEstadoSolicitud(solicitudPostulacion.getEstadoSolicitud());
        this.setCanalRecepcion(solicitudPostulacion.getSolicitudGlobal().getCanalRecepcion());
        this.setTipoTransaccionEnum(solicitudPostulacion.getSolicitudGlobal().getTipoTransaccion());
        this.setTipoTransaccion(solicitudPostulacion.getSolicitudGlobal().getTipoTransaccion());
        this.setMetodoEnvio(solicitudPostulacion.getSolicitudGlobal().getMetodoEnvio());
        this.setObservaciones(solicitudPostulacion.getObservaciones());
        this.setIdPostulacion(solicitudPostulacion.getIdPostulacionFOVIS());
        if (solicitudPostulacion.getSolicitudGlobal().getFechaRadicacion() != null) {
            this.setFechaRadicacion(solicitudPostulacion.getSolicitudGlobal().getFechaRadicacion().getTime());
        }
        if (solicitudPostulacion.getSolicitudGlobal().getIdInstanciaProceso() != null) {
            this.setIdInstanciaProceso(new Long(solicitudPostulacion.getSolicitudGlobal().getIdInstanciaProceso()));
        }
        this.setPostulacion(new PostulacionFOVISModeloDTO(postulacionFOVIS, cicloAsignacion));
        this.getPostulacion().setJefeHogar(new JefeHogarModeloDTO(jefeHogar, persona, personaDetalle, postulacionFOVIS));
    }

    /**
     * Constructor con los datos generales de la postulacion y el cruce
     * @param solicitudPostulacion
     *        Info Solicitud
     * @param postulacionFOVIS
     *        Info Postulacion
     * @param cicloAsignacion
     *        Info CicloAsignacion
     * @param jefeHogar
     *        Info JefeHogar
     * @param persona
     *        Info Persona
     * @param personaDetalle
     *        Info PersonaDetalle
     * @param solicitudGestionCruce
     *        Info solicitudGestionCruce
     */
    public SolicitudPostulacionFOVISDTO(SolicitudPostulacion solicitudPostulacion, PostulacionFOVIS postulacionFOVIS,
                                        CicloAsignacion cicloAsignacion, JefeHogar jefeHogar, Persona persona,
                                        PersonaDetalle personaDetalle, SolicitudGestionCruce solicitudGestionCruce) {
        this.setIdSolicitudPostulacion(solicitudPostulacion.getIdSolicitudPostulacion());
        this.setIdSolicitud(solicitudPostulacion.getSolicitudGlobal().getIdSolicitud());
        this.setNumeroRadicacion(solicitudPostulacion.getSolicitudGlobal().getNumeroRadicacion());
        this.setEstadoSolicitud(solicitudPostulacion.getEstadoSolicitud());
        this.setCanalRecepcion(solicitudPostulacion.getSolicitudGlobal().getCanalRecepcion());
        this.setTipoTransaccionEnum(solicitudPostulacion.getSolicitudGlobal().getTipoTransaccion());
        this.setTipoTransaccion(solicitudPostulacion.getSolicitudGlobal().getTipoTransaccion());
        this.setMetodoEnvio(solicitudPostulacion.getSolicitudGlobal().getMetodoEnvio());
        this.setObservaciones(solicitudPostulacion.getObservaciones());
        this.setIdPostulacion(solicitudPostulacion.getIdPostulacionFOVIS());
        this.setTipoCruce(solicitudGestionCruce.getTipoCruce());
        if (solicitudPostulacion.getSolicitudGlobal().getFechaRadicacion() != null) {
            this.setFechaRadicacion(solicitudPostulacion.getSolicitudGlobal().getFechaRadicacion().getTime());
        }
        if (solicitudPostulacion.getSolicitudGlobal().getIdInstanciaProceso() != null) {
            this.setIdInstanciaProceso(new Long(solicitudPostulacion.getSolicitudGlobal().getIdInstanciaProceso()));
        }
        this.setPostulacion(new PostulacionFOVISModeloDTO(postulacionFOVIS, cicloAsignacion));
        this.getPostulacion().setJefeHogar(new JefeHogarModeloDTO(jefeHogar, persona, personaDetalle, postulacionFOVIS));
    }

    /**
     * Obtiene el valor de idSolicitudPostulacion
     *
     * @return El valor de idSolicitudPostulacion
     */
    public Long getIdSolicitudPostulacion() {
        return idSolicitudPostulacion;
    }

    /**
     * Establece el valor de idSolicitudPostulacion
     *
     * @param idSolicitudPostulacion
     *        El valor de idSolicitudPostulacion por asignar
     */
    public void setIdSolicitudPostulacion(Long idSolicitudPostulacion) {
        this.idSolicitudPostulacion = idSolicitudPostulacion;
    }

    /**
     * Obtiene el valor de idSolicitud
     *
     * @return El valor de idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Establece el valor de idSolicitud
     *
     * @param idSolicitud
     *        El valor de idSolicitud por asignar
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Obtiene el valor de postulacion
     *
     * @return El valor de postulacion
     */
    public PostulacionFOVISModeloDTO getPostulacion() {
        return postulacion;
    }

    /**
     * Establece el valor de postulacion
     *
     * @param postulacion
     *        El valor de postulacion por asignar
     */
    public void setPostulacion(PostulacionFOVISModeloDTO postulacion) {
        this.postulacion = postulacion;
    }

    /**
     * Obtiene el valor de listaChequeo
     *
     * @return El valor de listaChequeo
     */
    public ListaChequeoDTO getListaChequeo() {
        return listaChequeo;
    }

    /**
     * Establece el valor de listaChequeo
     *
     * @param listaChequeo
     *        El valor de listaChequeo por asignar
     */
    public void setListaChequeo(ListaChequeoDTO listaChequeo) {
        this.listaChequeo = listaChequeo;
    }

    /**
     * Obtiene el valor de integrantesHogar
     *
     * @return El valor de integrantesHogar
     */
    public List<IntegranteHogarModeloDTO> getIntegrantesHogar() {
        return integrantesHogar;
    }

    /**
     * Establece el valor de integrantesHogar
     *
     * @param integrantesHogar
     *        El valor de integrantesHogar por asignar
     */
    public void setIntegrantesHogar(List<IntegranteHogarModeloDTO> integrantesHogar) {
        this.integrantesHogar = integrantesHogar;
    }

    /**
     * Obtiene el valor de estadoSolicitud
     *
     * @return El valor de estadoSolicitud
     */
    public EstadoSolicitudPostulacionEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * Establece el valor de estadoSolicitud
     *
     * @param estadoSolicitud
     *        El valor de estadoSolicitud por asignar
     */
    public void setEstadoSolicitud(EstadoSolicitudPostulacionEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * Obtiene el valor de oferente
     *
     * @return El valor de oferente
     */
    public OferenteDTO getOferente() {
        return oferente;
    }

    /**
     * Establece el valor de oferente
     *
     * @param oferente
     *        El valor de oferente por asignar
     */
    public void setOferente(OferenteDTO oferente) {
        this.oferente = oferente;
    }


    /**
     * Obtiene el valor de proveedor
     *
     * @return El valor de proveedor
     */
    public List<LegalizacionDesembolosoProveedorModeloDTO> getLegalizacionProveedor() {
        return legalizacionProveedor;
    }

    /**
     * Establece el valor de proveedor
     *
     * @param proveedor
     *        El valor de proveedor por asignar
     */
    public void setLegalizacionProveedor(List<LegalizacionDesembolosoProveedorModeloDTO> legalizacionProveedor) {
        this.legalizacionProveedor = legalizacionProveedor;
    }

    /**
     * Obtiene el valor de escalamientoMiembrosHogar
     *
     * @return El valor de escalamientoMiembrosHogar
     */
    public EscalamientoSolicitudDTO getEscalamientoMiembrosHogar() {
        return escalamientoMiembrosHogar;
    }

    /**
     * Establece el valor de escalamientoMiembrosHogar
     *
     * @param escalamientoMiembrosHogar
     *        El valor de escalamientoMiembrosHogar por asignar
     */
    public void setEscalamientoMiembrosHogar(EscalamientoSolicitudDTO escalamientoMiembrosHogar) {
        this.escalamientoMiembrosHogar = escalamientoMiembrosHogar;
    }

    /**
     * Obtiene el valor de escalamientoJuridico
     *
     * @return El valor de escalamientoJuridico
     */
    public EscalamientoSolicitudDTO getEscalamientoJuridico() {
        return escalamientoJuridico;
    }

    /**
     * Establece el valor de escalamientoJuridico
     *
     * @param escalamientoJuridico
     *        El valor de escalamientoJuridico por asignar
     */
    public void setEscalamientoJuridico(EscalamientoSolicitudDTO escalamientoJuridico) {
        this.escalamientoJuridico = escalamientoJuridico;
    }

    /**
     * Obtiene el valor de escalamientoTecnicoConstruccion
     *
     * @return El valor de escalamientoTecnicoConstruccion
     */
    public EscalamientoSolicitudDTO getEscalamientoTecnicoConstruccion() {
        return escalamientoTecnicoConstruccion;
    }

    /**
     * Establece el valor de escalamientoTecnicoConstruccion
     *
     * @param escalamientoTecnicoConstruccion
     *        El valor de escalamientoTecnicoConstruccion por asignar
     */
    public void setEscalamientoTecnicoConstruccion(EscalamientoSolicitudDTO escalamientoTecnicoConstruccion) {
        this.escalamientoTecnicoConstruccion = escalamientoTecnicoConstruccion;
    }

    /**
     * @return the escalamientoMiembrosHogarBack
     */
    public EscalamientoSolicitudDTO getEscalamientoMiembrosHogarBack() {
        return escalamientoMiembrosHogarBack;
    }

    /**
     * @param escalamientoMiembrosHogarBack
     *        the escalamientoMiembrosHogarBack to set
     */
    public void setEscalamientoMiembrosHogarBack(EscalamientoSolicitudDTO escalamientoMiembrosHogarBack) {
        this.escalamientoMiembrosHogarBack = escalamientoMiembrosHogarBack;
    }

    /**
     * @return the escalamientoJuridicoBack
     */
    public EscalamientoSolicitudDTO getEscalamientoJuridicoBack() {
        return escalamientoJuridicoBack;
    }

    /**
     * @param escalamientoJuridicoBack
     *        the escalamientoJuridicoBack to set
     */
    public void setEscalamientoJuridicoBack(EscalamientoSolicitudDTO escalamientoJuridicoBack) {
        this.escalamientoJuridicoBack = escalamientoJuridicoBack;
    }

    /**
     * @return the escalamientoTecnicoConstruccionBack
     */
    public EscalamientoSolicitudDTO getEscalamientoTecnicoConstruccionBack() {
        return escalamientoTecnicoConstruccionBack;
    }

    /**
     * @param escalamientoTecnicoConstruccionBack
     *        the escalamientoTecnicoConstruccionBack to set
     */
    public void setEscalamientoTecnicoConstruccionBack(EscalamientoSolicitudDTO escalamientoTecnicoConstruccionBack) {
        this.escalamientoTecnicoConstruccionBack = escalamientoTecnicoConstruccionBack;
    }

    /**
     * Obtiene el valor de canalRecepcion
     *
     * @return El valor de canalRecepcion
     */
    public CanalRecepcionEnum getCanalRecepcion() {
        return canalRecepcion;
    }

    /**
     * Establece el valor de canalRecepcion
     *
     * @param canalRecepcion
     *        El valor de canalRecepcion por asignar
     */
    public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }

    /**
     * @return the tipoTransaccionEnum
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccionEnum
     *        the tipoTransaccionEnum to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @return the tipoTransaccionEnum
     */
    public TipoTransaccionEnum getTipoTransaccionEnum() {
        return tipoTransaccionEnum;
    }

    /**
     * @param tipoTransaccionEnum
     *        the tipoTransaccionEnum to set
     */
    public void setTipoTransaccionEnum(TipoTransaccionEnum tipoTransaccionEnum) {
        this.tipoTransaccionEnum = tipoTransaccionEnum;
    }

    /**
     * @return the escalamientoEspecial
     */
    public EscalamientoSolicitudDTO getEscalamientoEspecial() {
        return escalamientoEspecial;
    }

    /**
     * @param escalamientoEspecial
     *        the escalamientoEspecial to set
     */
    public void setEscalamientoEspecial(EscalamientoSolicitudDTO escalamientoEspecial) {
        this.escalamientoEspecial = escalamientoEspecial;
    }

    /**
     * @return the metodoEnvio
     */
    public FormatoEntregaDocumentoEnum getMetodoEnvio() {
        return metodoEnvio;
    }

    /**
     * @param metodoEnvio
     *        the metodoEnvio to set
     */
    public void setMetodoEnvio(FormatoEntregaDocumentoEnum metodoEnvio) {
        this.metodoEnvio = metodoEnvio;
    }

    /**
     * @return the idInstanciaProceso
     */
    public Long getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso
     *        the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(Long idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    /**
     * @return the idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * @param idTarea
     *        the idTarea to set
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * @return the numeroRadicacion
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * @param numeroRadicacion
     *        the numeroRadicacion to set
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * Obtiene el valor de causaIntentoFallido
     *
     * @return El valor de causaIntentoFallido
     */
    public CausaIntentoFallidoPostulacionEnum getCausaIntentoFallido() {
        return causaIntentoFallido;
    }

    /**
     * Establece el valor de causaIntentoFallido
     *
     * @param causaIntentoFallido
     *        El valor de causaIntentoFallido por asignar
     */
    public void setCausaIntentoFallido(CausaIntentoFallidoPostulacionEnum causaIntentoFallido) {
        this.causaIntentoFallido = causaIntentoFallido;
    }

    /**
     * @return the formularioWeb
     */
    public String getFormularioWeb() {
        return formularioWeb;
    }

    /**
     * @param formularioWeb
     *        the formularioWeb to set
     */
    public void setFormularioWeb(String formularioWeb) {
        this.formularioWeb = formularioWeb;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones
     *        the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Obtiene el valor de condicionesEspeciales
     *
     * @return El valor de condicionesEspeciales
     */
    public List<NombreCondicionEspecialEnum> getCondicionesEspeciales() {
        return condicionesEspeciales;
    }

    /**
     * Establece el valor de condicionesEspeciales
     *
     * @param condicionesEspeciales
     *        El valor de condicionesEspeciales por asignar
     */
    public void setCondicionesEspeciales(List<NombreCondicionEspecialEnum> condicionesEspeciales) {
        this.condicionesEspeciales = condicionesEspeciales;
    }

    /**
     * @return the observacionesWeb
     */
    public String getObservacionesWeb() {
        return observacionesWeb;
    }

    /**
     * @param observacionesWeb
     *        the observacionesWeb to set
     */
    public void setObservacionesWeb(String observacionesWeb) {
        this.observacionesWeb = observacionesWeb;
    }


    /**
     * @return the idPostulacion
     */
    public Long getIdPostulacion() {
        return idPostulacion;
    }


    /**
     * @param idPostulacion the idPostulacion to set
     */
    public void setIdPostulacion(Long idPostulacion) {
        this.idPostulacion = idPostulacion;
    }


    /**
     * @return the fechaRadicacion
     */
    public Long getFechaRadicacion() {
        return fechaRadicacion;
    }


    /**
     * @param fechaRadicacion the fechaRadicacion to set
     */
    public void setFechaRadicacion(Long fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    /**
     * @return the idComunicadoSolicitud
     */
    public String getIdComunicadoSolicitud() {
        return idComunicadoSolicitud;
    }

    /**
     * @param idComunicadoSolicitud the idComunicadoSolicitud to set
     */
    public void setIdComunicadoSolicitud(String idComunicadoSolicitud) {
        this.idComunicadoSolicitud = idComunicadoSolicitud;
    }

    /**
     * @return the tipoSolicitud
     */
    public TipoSolicitudEnum getTipoSolicitud() {
        return tipoSolicitud;
    }

    /**
     * @param tipoSolicitud the tipoSolicitud to set
     */
    public void setTipoSolicitud(TipoSolicitudEnum tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    /**
     * @return the tipoPostulacion
     */
    public TipoPostulacionFOVISEnum getTipoPostulacion() {
        return tipoPostulacion;
    }


    /**
     * @param tipoPostulacion the tipoPostulacion to set
     */
    public void setTipoPostulacion(TipoPostulacionFOVISEnum tipoPostulacion) {
        this.tipoPostulacion = tipoPostulacion;
    }

    public SolicitudVerificacionFovisModeloDTO getSolicitudVerificacionFovisModeloDTO() {
        return solicitudVerificacionFovisModeloDTO;
    }

    public void setSolicitudVerificacionFovisModeloDTO(SolicitudVerificacionFovisModeloDTO solicitudVerificacionFovisModeloDTO) {
        this.solicitudVerificacionFovisModeloDTO = solicitudVerificacionFovisModeloDTO;
    }

    /**
     * @return the datosPostulacionFovis
     */
    public SolicitudPostulacionFOVISDTO getDatosPostulacionFovis() {
        return datosPostulacionFovis;
    }

    /**
     * @param datosPostulacionFovis
     *        the datosPostulacionFovis to set
     */
    public void setDatosPostulacionFovis(SolicitudPostulacionFOVISDTO datosPostulacionFovis) {
        this.datosPostulacionFovis = datosPostulacionFovis;
    }
    public String getIdDocumentoControlInterno() {
        return idDocumentoControlInterno;
    }

    /**
     * @param idDocumentoControlInterno
     *        the idDocumentoControlInterno to set
     */
    public void setIdDocumentoControlInterno(String idDocumentoControlInterno) {
        this.idDocumentoControlInterno = idDocumentoControlInterno;
    }


    public EstadoDocumentacionEnum getEstadoDocumentacion() {
        return this.estadoDocumentacion;
    }

    public void setEstadoDocumentacion(EstadoDocumentacionEnum estadoDocumentacion) {
        this.estadoDocumentacion = estadoDocumentacion;
    }

    public String getIdCajaCorrespondencia() {
        return this.idCajaCorrespondencia;
    }

    public void setIdCajaCorrespondencia(String idCajaCorrespondencia) {
        this.idCajaCorrespondencia = idCajaCorrespondencia;
    }

    public String getClasificacion() {
        return this.clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public TipoRadicacionEnum getTipoRadicacion() {
        return this.tipoRadicacion;
    }

    public void setTipoRadicacion(TipoRadicacionEnum tipoRadicacion) {
        this.tipoRadicacion = tipoRadicacion;
    }

    public String getUsuarioRadicacion() {
        return this.usuarioRadicacion;
    }

    public void setUsuarioRadicacion(String usuarioRadicacion) {
        this.usuarioRadicacion = usuarioRadicacion;
    }

    public String getCiudadSedeRadicacion() {
        return this.ciudadSedeRadicacion;
    }

    public void setCiudadSedeRadicacion(String ciudadSedeRadicacion) {
        this.ciudadSedeRadicacion = ciudadSedeRadicacion;
    }

    public String getAnulada() {
        return this.anulada;
    }

    public void setAnulada(String anulada) {
        this.anulada = anulada;
    }

    public String getCargaAfiliacionMultiple() {
        return this.cargaAfiliacionMultiple;
    }

    public void setCargaAfiliacionMultiple(String cargaAfiliacionMultiple) {
        this.cargaAfiliacionMultiple = cargaAfiliacionMultiple;
    }

    public String getDestinatario() {
        return this.destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public EstadoCruceHogarEnum getEstadoCruceHogar() {
        return this.estadoCruceHogar;
    }

    public void setEstadoCruceHogar(EstadoCruceHogarEnum estadoCruceHogar) {
        this.estadoCruceHogar = estadoCruceHogar;
    }

    public Long setIdDiferenciaCargueActualizacion() {
        return this.idDiferenciaCargueActualizacion;
    }

    public void setIdDiferenciaCargueActualizacion(Long idDiferenciaCargueActualizacion) {
        this.idDiferenciaCargueActualizacion = idDiferenciaCargueActualizacion;
    }

    public Long getNumeroRadicadoPostulacion() {
        return this.numeroRadicadoPostulacion;
    }

    public void setNumeroRadicadoPostulacion (Long numeroRadicadoPostulacion) {
        this.numeroRadicadoPostulacion = numeroRadicadoPostulacion;
    }

    public Long getSedeDestinatario() {
        return this.sedeDestinatario;
    }

    public void setSedeDestinatario (Long sedeDestinatario) {
        this.sedeDestinatario = sedeDestinatario;
    }

    public String getResultadoProceso() {
        return this.resultadoProceso;
    }

    public void setResultadoProceso (String resultadoProceso) {
        this.resultadoProceso = resultadoProceso;
    }


    public TipoCruceEnum getTipoCruce() {
        return this.tipoCruce;
    }

    public void setTipoCruce (TipoCruceEnum tipoCruce) {
        this.tipoCruce = tipoCruce;
    }

    public EstadoSolicitudGestionCruceEnum getEstadoSolicitudGestionCruce() {
        return this.estadoSolicitudGestionCruce;
    }

    public void setEstadoSolicitudGestionCruce (EstadoSolicitudGestionCruceEnum estadoSolicitudGestionCruce) {
        this.estadoSolicitudGestionCruce = estadoSolicitudGestionCruce;
    }


    public Long getFechaCreacion() {
        return fechaCreacion;
    }


    public void setFechaCreacion(Long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getObservacion() {
        return observacion;
    }


    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Long getIdSolicitudGestionCruce() {
        return idSolicitudGestionCruce;
    }


    public void setIdSolicitudGestionCruce(Long idSolicitudGestionCruce) {
        this.idSolicitudGestionCruce = idSolicitudGestionCruce;
    }

    /**
     * @return the listCrucesAsociados
     */
    public List<CruceDetalleDTO> getListCrucesAsociados() {
        return listCrucesAsociados;
    }

    /**
     * @param listCrucesAsociados
     *        the listCrucesAsociados to set
     */
    public void setListCrucesAsociados(List<CruceDetalleDTO> listCrucesAsociados) {
        this.listCrucesAsociados = listCrucesAsociados;
    }

    public Date getFechaValidacionCruce() {
        return fechaValidacionCruce;
    }

    public void setFechaValidacionCruce(Date fechaValidacionCruce) {
        this.fechaValidacionCruce = fechaValidacionCruce;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "SolicitudPostulacionFOVISDTO{" +
                "idSolicitudPostulacion=" + idSolicitudPostulacion +
                ", idSolicitud=" + idSolicitud +
                ", numeroRadicacion='" + numeroRadicacion + '\'' +
                ", idInstanciaProceso=" + idInstanciaProceso +
                ", postulacion=" + postulacion +
                ", listaChequeo=" + listaChequeo +
                ", integrantesHogar=" + integrantesHogar +
                ", estadoSolicitud=" + estadoSolicitud +
                ", condicionesEspeciales=" + condicionesEspeciales +
                ", oferente=" + oferente +
                ", escalamientoMiembrosHogar=" + escalamientoMiembrosHogar +
                ", escalamientoJuridico=" + escalamientoJuridico +
                ", escalamientoTecnicoConstruccion=" + escalamientoTecnicoConstruccion +
                ", escalamientoMiembrosHogarBack=" + escalamientoMiembrosHogarBack +
                ", escalamientoJuridicoBack=" + escalamientoJuridicoBack +
                ", escalamientoTecnicoConstruccionBack=" + escalamientoTecnicoConstruccionBack +
                ", escalamientoEspecial=" + escalamientoEspecial +
                ", canalRecepcion=" + canalRecepcion +
                ", tipoTransaccionEnum=" + tipoTransaccionEnum +
                ", tipoTransaccion=" + tipoTransaccion +
                ", metodoEnvio=" + metodoEnvio +
                ", idTarea=" + idTarea +
                ", causaIntentoFallido=" + causaIntentoFallido +
                ", formularioWeb='" + formularioWeb + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", observacionesWeb='" + observacionesWeb + '\'' +
                ", idPostulacion=" + idPostulacion +
                ", fechaRadicacion=" + fechaRadicacion +
                ", idComunicadoSolicitud='" + idComunicadoSolicitud + '\'' +
                ", tipoSolicitud=" + tipoSolicitud +
                ", tipoPostulacion=" + tipoPostulacion +
                ", legalizacionProveedor=" + legalizacionProveedor +
                '}';
    }
}
