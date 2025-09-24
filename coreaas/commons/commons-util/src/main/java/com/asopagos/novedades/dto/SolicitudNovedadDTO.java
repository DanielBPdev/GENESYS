/**
 *
 */
package com.asopagos.novedades.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.PersonaRetiroNovedadAutomaticaDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.ParametrizacionNovedadModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudNovedadEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoRadicacionSolicitudEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.dto.modelo.SolicitudNovedadModeloDTO;

/**
 * Clase DTO que contiene los datos de una solicitud de novedad.
 *
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class SolicitudNovedadDTO {
    /**
     * Id de la solicitud asociada a la novedad.
     */
    private Long idSolicitud;
    /**
     * Id de la solicitud de novedad
     */
    private Long idSolicitudNovedad;

    /**
     * Id instancia del proceso.
     */
    private Long idInstancia;

    /**
     * Nombre de la novedad
     */
    private ParametrizacionNovedadModeloDTO novedadDTO;

    /**
     * Canal de recepción de los datos.
     */
    private CanalRecepcionEnum canalRecepcion;

    /**
     * Método de envío de los documentos
     */
    private FormatoEntregaDocumentoEnum metodoEnvio;

    /**
     * Clasificación (cuando se trata de una persona)
     */
    private ClasificacionEnum clasificacion;

    /**
     * Tipo de transacción.
     */
    private TipoTransaccionEnum tipoTransaccion;

    /**
     * Observaciones
     */
    private String observaciones;

    /**
     * Resultado de radicación de la solicitud de novedad.
     */
    private ResultadoRadicacionSolicitudEnum resultadoValidacion;

    /**
     * Excepción tipo dos.
     */
    private Boolean excepcionTipoDos;

    /**
     * Datos del empleador en pantalla.
     */
    private DatosEmpleadorNovedadDTO datosEmpleador;

    /**
     * Datos de la persona en pantalla.
     */
    private DatosPersonaNovedadDTO datosPersona;

    /**
     * Datos de la persona multiple en pantalla(89526).
     */
    private List<DatosPersonaNovedadDTO> datosPersonaMultiple;

    /**
     * Personas asociadas a un retiro automático
     */
    private List<PersonaRetiroNovedadAutomaticaDTO> personasRetiroAutomatico;

    /**
     * Identificador del cargue múltiple que se realiza
     */
    private Long idCargueMultipleNovedad;

    /**
     * Estado de la solicitud de novedad.
     */
    private EstadoSolicitudNovedadEnum estadoSolicitudNovedad;

    /**
     * Número de radicación despues de radicar la solicitud.R
     */
    private String numeroRadicacion;

    /**
     * Identificador del Registro Detallado.
     */
    private Long idRegistroDetallado;

    /**
     * Identificador diferencias cargue actualizacion
     */
    private Long idDiferenciaCargueActualizacion;

    /**
     * Informacion de la diferencia que origina la novedad
     */
    private InformacionActualizacionNovedadDTO infoNovedadArchivoActualizacion;

    /**
     * Fecha de radicación de la solicitud
     */
    private Long fechaRadicacion;

    /**
     * Lista de validaciones con excepcion Tipo uno
     */
    private List<ValidacionDTO> listResultadoValidacion;

    /**
     * Indica si el proceso continua apesar de tener validacion fallidas por T1
     */
    private Boolean continuaProceso;

    /**
     * Indica si la persona asociada en la novedad esta vinculada en una postulacion FOVIS
     */
    private Boolean postuladoFOVIS;

    /**
     * Indica si la novedad es registrada por el proceso de cargue multiple
     */
    private Boolean cargaMultiple;

    /**
     * Indica que la novedad ya esta en proceso
     */
    private Boolean novedadEnProceso;

    /**
     * Indica que la novedad fallo por validación fallecimiento
     */
    private Boolean validacionFallecido;

    /**
     * Usuario de la radicación
     */
    private String usuarioRadicacion;

    /**
     * Indica si la novedad se registra en forma asincrona
     */
    private Boolean novedadAsincrona;

    /**
     * Contiene los datos para registro de novedades masivas
     */
    private DatosNovedadAutomaticaDTO datosNovedadMasiva;

    /**
     * Indica si la novedad es realizada por el proceso de carga archivo de respuesta de supervivencia
     */
    private Boolean novedadCargaArchivoRespuestaSupervivencia;

    /**
     * Indica si la solicitud de novedad viene desde un utilitario
     */
    private Boolean esUtilitario;

    /**
     * GLPI 62260 sustitucion patronal
     * Es usado para setear Fecha inicio labores con empleador (roaFechaAfiliacion)
     * Es usado para setear Ultima fecha de ingreso: (roaFechaIngreso)
     */
    private Long fechaInicioAfiliacion;

    /**
     * GLPI 67296
     * Es usado para setear la fecha fin de labores de la empresa origne en la
     * sustitucion patronal
     */
    private Long fechaFinLaboresSucursalOrigenTraslado;

    /**
     * id tabla TemNovedad
     */
    private Long tenNovedadId;


    private Long idRegistroDetalladoNovedad;

    private Boolean tarjetaMultiservicio;

    /**
     * GLPI 64825 trasladoCajasCompensacion
     * Es usado para el cambio de marca para el traslado de empresas entre ccf
     */
    private Boolean trasladoCajasCompensacion;


    /**
     * Gap 45051 actualización masiva de afiliados principales
     * id que se usa para identificar las novedades que se envian al back y así no crear otra.
     */
    private Long idSolicitudCargueMasivo;

    private Boolean isIngresoRetiro;

    private String beneficiariosCadena;

    private String sedeDestinatario; 


    /**
     * Constructor por defecto
     */
    public SolicitudNovedadDTO() {
        super();
    }

    /**
     * Método que retorna el valor de idSolicitud.
     *
     * @return valor de idSolicitud.
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Método encargado de modificar el valor de idSolicitud.
     * @param valor
     *        para modificar idSolicitud.
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Método que retorna el valor de idSolicitudNovedad.
     *
     * @return valor de idSolicitudNovedad.
     */
    public Long getIdSolicitudNovedad() {
        return idSolicitudNovedad;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudNovedad.
     * @param valor
     *        para modificar idSolicitudNovedad.
     */
    public void setIdSolicitudNovedad(Long idSolicitudNovedad) {
        this.idSolicitudNovedad = idSolicitudNovedad;
    }

    /**
     * Método que retorna el valor de novedadDTO.
     *
     * @return valor de novedadDTO.
     */
    public ParametrizacionNovedadModeloDTO getNovedadDTO() {
        return novedadDTO;
    }

    /**
     * Método encargado de modificar el valor de novedadDTO.
     * @param valor
     *        para modificar novedadDTO.
     */
    public void setNovedadDTO(ParametrizacionNovedadModeloDTO novedadDTO) {
        this.novedadDTO = novedadDTO;
    }

    /**
     * Método que retorna el valor de canalRecepcion.
     * @return valor de canalRecepcion.
     */
    public CanalRecepcionEnum getCanalRecepcion() {
        return canalRecepcion;
    }

    /**
     * Método encargado de modificar el valor de canalRecepcion.
     * @param valor
     *        para modificar canalRecepcion.
     */
    public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }

    /**
     * Método que retorna el valor de metodoEnvio.
     * @return valor de metodoEnvio.
     */
    public FormatoEntregaDocumentoEnum getMetodoEnvio() {
        return metodoEnvio;
    }

    /**
     * Método encargado de modificar el valor de metodoEnvio.
     * @param valor
     *        para modificar metodoEnvio.
     */
    public void setMetodoEnvio(FormatoEntregaDocumentoEnum metodoEnvio) {
        this.metodoEnvio = metodoEnvio;
    }

    /**
     * Método que retorna el valor de clasificacion.
     * @return valor de clasificacion.
     */
    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    /**
     * Método encargado de modificar el valor de clasificacion.
     * @param valor
     *        para modificar clasificacion.
     */
    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * Método que retorna el valor de tipoTransaccion.
     * @return valor de tipoTransaccion.
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * Método encargado de modificar el valor de tipoTransaccion.
     * @param valor
     *        para modificar tipoTransaccion.
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * Método que retorna el valor de observaciones.
     * @return valor de observaciones.
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Método encargado de modificar el valor de observaciones.
     * @param valor
     *        para modificar observaciones.
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Método que retorna el valor de resultadoValidacion.
     * @return valor de resultadoValidacion.
     */
    public ResultadoRadicacionSolicitudEnum getResultadoValidacion() {
        return resultadoValidacion;
    }

    /**
     * Método encargado de modificar el valor de resultadoValidacion.
     * @param valor
     *        para modificar resultadoValidacion.
     */
    public void setResultadoValidacion(ResultadoRadicacionSolicitudEnum resultadoValidacion) {
        this.resultadoValidacion = resultadoValidacion;
    }

    /**
     * Método que retorna el valor de excepcionTipoDos.
     * @return valor de excepcionTipoDos.
     */
    public Boolean getExcepcionTipoDos() {
        return excepcionTipoDos;
    }

    /**
     * Método encargado de modificar el valor de excepcionTipoDos.
     * @param valor
     *        para modificar excepcionTipoDos.
     */
    public void setExcepcionTipoDos(Boolean excepcionTipoDos) {
        this.excepcionTipoDos = excepcionTipoDos;
    }

    /**
     * Método que retorna el valor de datosEmpleador.
     * @return valor de datosEmpleador.
     */
    public DatosEmpleadorNovedadDTO getDatosEmpleador() {
        return datosEmpleador;
    }

    /**
     * Método encargado de modificar el valor de datosEmpleador.
     * @param valor
     *        para modificar datosEmpleador.
     */
    public void setDatosEmpleador(DatosEmpleadorNovedadDTO datosEmpleador) {
        this.datosEmpleador = datosEmpleador;
    }

    /**
     * Método que retorna el valor de datosPersona.
     * @return valor de datosPersona.
     */
    public DatosPersonaNovedadDTO getDatosPersona() {
        return datosPersona;
    }

    /**
     * Método encargado de modificar el valor de datosPersona.
     * @param valor
     *        para modificar datosPersona.
     */
    public void setDatosPersona(DatosPersonaNovedadDTO datosPersona) {
        this.datosPersona = datosPersona;
    }

    /**
     * Método que retorna el valor de datosPersonaMultiple.
     * @return valor de datosPersonaMultiple.
     */
    public List<DatosPersonaNovedadDTO> getDatosPersonaMultiple() {
        return datosPersonaMultiple;
    }

    /**
     * Método encargado de modificar el valor de datosPersonaMultiple.
     * @param valor
     *        para modificar datosPersonaMultiple.
     */
    public void setDatosPersonaMultiple(List<DatosPersonaNovedadDTO> datosPersonaMultiple) {
        this.datosPersonaMultiple = datosPersonaMultiple;
    }

    /**
     * Método que retorna el valor de idInstancia.
     * @return valor de idInstancia.
     */
    public Long getIdInstancia() {
        return idInstancia;
    }

    /**
     * Método encargado de modificar el valor de idInstancia.
     * @param valor
     *        para modificar idInstancia.
     */
    public void setIdInstancia(Long idInstancia) {
        this.idInstancia = idInstancia;
    }

    /**
     * @return the idCargueMultipleNovedad
     */
    public Long getIdCargueMultipleNovedad() {
        return idCargueMultipleNovedad;
    }

    /**
     * @param idCargueMultipleNovedad
     *        the idCargueMultipleNovedad to set
     */
    public void setIdCargueMultipleNovedad(Long idCargueMultipleNovedad) {
        this.idCargueMultipleNovedad = idCargueMultipleNovedad;
    }

    /**
     * Método que retorna el valor de estadoSolicitudNovedad.
     * @return valor de estadoSolicitudNovedad.
     */
    public EstadoSolicitudNovedadEnum getEstadoSolicitudNovedad() {
        return estadoSolicitudNovedad;
    }

    /**
     * Método encargado de modificar el valor de estadoSolicitudNovedad.
     * @param valor
     *        para modificar estadoSolicitudNovedad.
     */
    public void setEstadoSolicitudNovedad(EstadoSolicitudNovedadEnum estadoSolicitudNovedad) {
        this.estadoSolicitudNovedad = estadoSolicitudNovedad;
    }

    /**
     * Método que retorna el valor de numeroRadicacion.
     * @return valor de numeroRadicacion.
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * Método encargado de modificar el valor de numeroRadicacion.
     * @param valor
     *        para modificar numeroRadicacion.
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * @return the personasRetiroAutomatico
     */
    public List<PersonaRetiroNovedadAutomaticaDTO> getPersonasRetiroAutomatico() {
        return personasRetiroAutomatico;
    }

    /**
     * @param personasRetiroAutomatico
     *        the personasRetiroAutomatico to set
     */
    public void setPersonasRetiroAutomatico(List<PersonaRetiroNovedadAutomaticaDTO> personasRetiroAutomatico) {
        this.personasRetiroAutomatico = personasRetiroAutomatico;
    }

    /**
     * @return the idRegistroDetallado
     */
    public Long getIdRegistroDetallado() {
        return idRegistroDetallado;
    }

    /**
     * @param idRegistroDetallado
     *        the idRegistroDetallado to set
     */
    public void setIdRegistroDetallado(Long idRegistroDetallado) {
        this.idRegistroDetallado = idRegistroDetallado;
    }

    /**
     * @return the idDiferenciaCargueActualizacion
     */
    public Long getIdDiferenciaCargueActualizacion() {
        return idDiferenciaCargueActualizacion;
    }

    /**
     * @param idDiferenciaCargueActualizacion
     *        the idDiferenciaCargueActualizacion to set
     */
    public void setIdDiferenciaCargueActualizacion(Long idDiferenciaCargueActualizacion) {
        this.idDiferenciaCargueActualizacion = idDiferenciaCargueActualizacion;
    }

    /**
     * @return the infoNovedadArchivoActualizacion
     */
    public InformacionActualizacionNovedadDTO getInfoNovedadArchivoActualizacion() {
        return infoNovedadArchivoActualizacion;
    }

    /**
     * @param infoNovedadArchivoActualizacion
     *        the infoNovedadArchivoActualizacion to set
     */
    public void setInfoNovedadArchivoActualizacion(InformacionActualizacionNovedadDTO infoNovedadArchivoActualizacion) {
        this.infoNovedadArchivoActualizacion = infoNovedadArchivoActualizacion;
    }

    /**
     * @return the fechaRadicacion
     */
    public Long getFechaRadicacion() {
        return fechaRadicacion;
    }

    /**
     * @param fechaRadicacion
     *        the fechaRadicacion to set
     */
    public void setFechaRadicacion(Long fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    /**
     * @return the listResultadoValidacion
     */
    public List<ValidacionDTO> getListResultadoValidacion() {
        return listResultadoValidacion;
    }

    /**
     * @param listResultadoValidacion
     *        the listResultadoValidacion to set
     */
    public void setListResultadoValidacion(List<ValidacionDTO> listResultadoValidacion) {
        this.listResultadoValidacion = listResultadoValidacion;
    }

    /**
     * @return the continuaProceso
     */
    public Boolean getContinuaProceso() {
        return continuaProceso;
    }

    /**
     * @param continuaProceso
     *        the continuaProceso to set
     */
    public void setContinuaProceso(Boolean continuaProceso) {
        this.continuaProceso = continuaProceso;
    }

    /**
     * @return the postuladoFOVIS
     */
    public Boolean getPostuladoFOVIS() {
        return postuladoFOVIS;
    }

    /**
     * @param postuladoFOVIS
     *        the postuladoFOVIS to set
     */
    public void setPostuladoFOVIS(Boolean postuladoFOVIS) {
        this.postuladoFOVIS = postuladoFOVIS;
    }

    /**
     * @return the cargaMultiple
     */
    public Boolean getCargaMultiple() {
        return cargaMultiple;
    }

    /**
     * @param cargaMultiple
     *        the cargaMultiple to set
     */
    public void setCargaMultiple(Boolean cargaMultiple) {
        this.cargaMultiple = cargaMultiple;
    }

    /**
     * @return the novedadEnProceso
     */
    public Boolean getNovedadEnProceso() {
        return novedadEnProceso;
    }

    /**
     * @param novedadEnProceso
     *        the novedadEnProceso to set
     */
    public void setNovedadEnProceso(Boolean novedadEnProceso) {
        this.novedadEnProceso = novedadEnProceso;
    }

    /**
     * @return the validacionFallecido
     */
    public Boolean getValidacionFallecido() {
        return validacionFallecido;
    }

    /**
     * @param validacionFallecido
     *        the validacionFallecido to set
     */
    public void setValidacionFallecido(Boolean validacionFallecido) {
        this.validacionFallecido = validacionFallecido;
    }

    /**
     * @return the usuarioRadicacion
     */
    public String getUsuarioRadicacion() {
        return usuarioRadicacion;
    }

    /**
     * @param usuarioRadicacion
     *        the usuarioRadicacion to set
     */
    public void setUsuarioRadicacion(String usuarioRadicacion) {
        this.usuarioRadicacion = usuarioRadicacion;
    }

    /**
     * @return the novedadAsincrona
     */
    public Boolean getNovedadAsincrona() {
        return novedadAsincrona;
    }

    /**
     * @param novedadAsincrona
     *        the novedadAsincrona to set
     */
    public void setNovedadAsincrona(Boolean novedadAsincrona) {
        this.novedadAsincrona = novedadAsincrona;
    }

    /**
     * @return the datosNovedadMasiva
     */
    public DatosNovedadAutomaticaDTO getDatosNovedadMasiva() {
        return datosNovedadMasiva;
    }

    /**
     * @param datosNovedadMasiva
     *        the datosNovedadMasiva to set
     */
    public void setDatosNovedadMasiva(DatosNovedadAutomaticaDTO datosNovedadMasiva) {
        this.datosNovedadMasiva = datosNovedadMasiva;
    }

    /**
     * @return the novedadCargaArchivoRespuestaSupervivencia
     */
    public Boolean getNovedadCargaArchivoRespuestaSupervivencia() {
        return novedadCargaArchivoRespuestaSupervivencia;
    }

    /**
     * @param novedadCargaArchivoRespuestaSupervivencia the novedadCargaArchivoRespuestaSupervivencia to set
     */
    public void setNovedadCargaArchivoRespuestaSupervivencia(Boolean novedadCargaArchivoRespuestaSupervivencia) {
        this.novedadCargaArchivoRespuestaSupervivencia = novedadCargaArchivoRespuestaSupervivencia;
    }

    /**
     * @return the esUtilitario
     */
    public Boolean getEsUtilitario() {
        return esUtilitario;
    }

    /**
     * @param esUtilitario the esUtilitario to set
     */
    public void setEsUtilitario(Boolean esUtilitario) {
        this.esUtilitario = esUtilitario;
    }

    /**
     * @return a
     */
    public Long getTenNovedadId() {
        return tenNovedadId;
    }

    /**
     * @param tenNovedadId
     */
    public void setTenNovedadId(Long tenNovedadId) {
        this.tenNovedadId = tenNovedadId;
    }

    public Long getFechaInicioAfiliacion() {
        return fechaInicioAfiliacion;
    }

    public void setFechaInicioAfiliacion(Long fechaInicioAfiliacion) {
        this.fechaInicioAfiliacion = fechaInicioAfiliacion;
    }
    /**
     * @return the idRegistroDetalladoNovedad pila
     */
    public Long getIdRegistroDetalladoNovedad() {
        return idRegistroDetalladoNovedad;
    }

    public Boolean getTarjetaMultiservicio() {
        return this.tarjetaMultiservicio;
    }

    public void setTarjetaMultiservicio(Boolean tarjetaMultiservicio) {
        this.tarjetaMultiservicio = tarjetaMultiservicio;
    }
    /**
     * @param idRegistroDetalladoNovedad the idRegistroDetalladoNovedad to set
     */
    public void setIdRegistroDetalladoNovedad(Long idRegistroDetalladoNovedad) {
        this.idRegistroDetalladoNovedad = idRegistroDetalladoNovedad;
    }

    public Long getIdSolicitudCargueMasivo() {
        return idSolicitudCargueMasivo;
    }

    public void setIdSolicitudCargueMasivo(Long idSolicitudCargueMasivo) {
        this.idSolicitudCargueMasivo = idSolicitudCargueMasivo;
    }

    public SolicitudNovedadModeloDTO covertToSolicitudNovedadModeloDTO(){
        SolicitudNovedadModeloDTO sol = new SolicitudNovedadModeloDTO();
        sol.setEstadoSolicitud(this.estadoSolicitudNovedad);
        sol.setObservacionesNovedad(this.observaciones);
        sol.setCargaMultiple(this.cargaMultiple);
        return sol;
    }

	/**
	 * Método que retorna el valor de sedeDestinatario.
	 * @return valor de sedeDestinatario.
	 */
	public String getSedeDestinatario() {
		return sedeDestinatario;
	}
	/**
	 * Método encargado de modificar el valor de sedeDestinatario.
	 * @param valor para modificar sedeDestinatario.
	 */
	public void setSedeDestinatario(String sedeDestinatario) {
		this.sedeDestinatario = sedeDestinatario;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SolicitudNovedadDTO [idSolicitud=");
        builder.append(idSolicitud);
        builder.append(", idSolicitudNovedad=");
        builder.append(idSolicitudNovedad);
        builder.append(", idInstancia=");
        builder.append(idInstancia);
        builder.append(", novedadDTO=");
        builder.append(novedadDTO);
        builder.append(", canalRecepcion=");
        builder.append(canalRecepcion);
        builder.append(", metodoEnvio=");
        builder.append(metodoEnvio);
        builder.append(", clasificacion=");
        builder.append(clasificacion);
        builder.append(", tipoTransaccion=");
        builder.append(tipoTransaccion);
        builder.append(", observaciones=");
        builder.append(observaciones);
        builder.append(", resultadoValidacion=");
        builder.append(resultadoValidacion);
        builder.append(", excepcionTipoDos=");
        builder.append(excepcionTipoDos);
        builder.append(", datosEmpleador=");
        builder.append(datosEmpleador);
        builder.append(", datosPersona=");
        builder.append(datosPersona);
        builder.append(", datosPersonaMultiple=");
        builder.append(datosPersonaMultiple);
        builder.append(", personasRetiroAutomatico=");
        builder.append(personasRetiroAutomatico);
        builder.append(", idCargueMultipleNovedad=");
        builder.append(idCargueMultipleNovedad);
        builder.append(", estadoSolicitudNovedad=");
        builder.append(estadoSolicitudNovedad);
        builder.append(", numeroRadicacion=");
        builder.append(numeroRadicacion);
        builder.append(", idRegistroDetallado=");
        builder.append(idRegistroDetallado);
        builder.append(", idDiferenciaCargueActualizacion=");
        builder.append(idDiferenciaCargueActualizacion);
        builder.append(", infoNovedadArchivoActualizacion=");
        builder.append(infoNovedadArchivoActualizacion);
        builder.append(", fechaRadicacion=");
        builder.append(fechaRadicacion);
        builder.append(", listResultadoValidacion=");
        builder.append(listResultadoValidacion);
        builder.append(", continuaProceso=");
        builder.append(continuaProceso);
        builder.append(", postuladoFOVIS=");
        builder.append(postuladoFOVIS);
        builder.append(", cargaMultiple=");
        builder.append(cargaMultiple);
        builder.append(", novedadEnProceso=");
        builder.append(novedadEnProceso);
        builder.append(", validacionFallecido=");
        builder.append(validacionFallecido);
        builder.append(", usuarioRadicacion=");
        builder.append(usuarioRadicacion);
        builder.append(", novedadAsincrona=");
        builder.append(novedadAsincrona);
        builder.append(", datosNovedadMasiva=");
        builder.append(datosNovedadMasiva);
        builder.append(", novedadCargaArchivoRespuestaSupervivencia=");
        builder.append(novedadCargaArchivoRespuestaSupervivencia);
        builder.append("]");
        return builder.toString();
    }

    public Long getFechaFinLaboresSucursalOrigenTraslado() {
        return fechaFinLaboresSucursalOrigenTraslado;
    }

    public void setFechaFinLaboresSucursalOrigenTraslado(Long fechaFinLaboresSucursalOrigenTraslado) {
        this.fechaFinLaboresSucursalOrigenTraslado = fechaFinLaboresSucursalOrigenTraslado;
    }

    public Boolean getTrasladoCajasCompensacion() {
        return trasladoCajasCompensacion;
    }

    public void setTrasladoCajasCompensacion(boolean trasladoCajasCompensacion) {
        this.trasladoCajasCompensacion = trasladoCajasCompensacion;
    }

    public Boolean getIsIngresoRetiro() {
		return this.isIngresoRetiro;
	}

	public void setIsIngresoRetiro(Boolean isIngresoRetiro) {
		this.isIngresoRetiro = isIngresoRetiro;
	}

    public String getBeneficiariosCadena() {
        return beneficiariosCadena;
    }

    public void setBeneficiariosCadena(String beneficiariosCadena) {
        this.beneficiariosCadena = beneficiariosCadena;
    }
}
