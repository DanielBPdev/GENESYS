/**
 * 
 */
package com.asopagos.novedades.fovis.composite.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.EscalamientoSolicitudDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.ValidacionDTO;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import com.asopagos.dto.modelo.ParametrizacionNovedadModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.CausaIntentoFallidoNovedadEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoRadicacionSolicitudEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.novedades.fovis.dto.DatosFOVISNovedadRegularDTO;
import com.asopagos.novedades.fovis.dto.DatosNovedadAutomaticaFovisDTO;

/**
 * Clase DTO que contiene los datos de una solicitud de novedad FOVIS.
 * 
 * @author Edward Castano<ecastasno@heinsohn.com.co>
 *
 */
@XmlRootElement
public class SolicitudNovedadFovisDTO {

    /**
     * Id de la solicitud asociada a la novedad.
     */
    private Long idSolicitud;

    /**
     * Id de la solicitud de novedad
     */
    private Long idSolicitudNovedadFovis;

    /**
     * Id instancia del proceso.
     */
    private Long idInstancia;

    /**
     * Nombre de la novedad
     */
    private ParametrizacionNovedadModeloDTO parametrizacionNovedad;

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
     * Tipo proceso de la transaccion
     */
    private ProcesoEnum tipoProceso;

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
     * Estado de la solicitud de novedad.
     */
    private EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad;

    /**
     * Número de radicación despues de radicar la solicitud
     */
    private String numeroRadicacion;

    /**
     * Fecha de radicación de la solicitud
     */
    private Long fechaRadicacion;

    /**
     * Lista de validaciones con excepcion
     */
    private List<ValidacionDTO> listResultadoValidacion;

    /**
     * Indica si el proceso continua apesar de tener validacion fallidas por T1
     */
    private Boolean continuaProceso;

    /**
     * Descripción del nombre de la ciudad del usuario de radiación
     */
    private String ciudadSedeRadicacion;

    /**
     * DTO con la informacion a procesar por la novedad automatica
     */
    private DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaFovisDTO;

    /**
     * Informacion postulación sujeto a cambios por novedad
     */
    private SolicitudPostulacionFOVISDTO datosPostulacion;

    /**
     * Causa del intento fallido de la novedad
     */
    private CausaIntentoFallidoNovedadEnum causaIntentoFallido;

    /**
     * Información de la novedad regular
     */
    private DatosFOVISNovedadRegularDTO datosNovedadRegularFovisDTO;

    /**
     * Resultado radicación
     */
    private Integer resultadoRadicacion;

    /**
     * Identificador postulacion
     */
    private Long idPostulacion;

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
     * Escalamiento a miembros del hogar
     */
    private EscalamientoSolicitudDTO escalamientoMiembrosHogarBack;

    /**
     * Escalamiento jurídico
     */
    private EscalamientoSolicitudDTO escalamientoJuridicoBack;

    /**
     * Escalamiento al técnico en construcción
     */
    private EscalamientoSolicitudDTO escalamientoTecnicoConstruccionBack;

    /**
     * Variable listaChequeoNovedad
     */
    private List<ItemChequeoDTO> listaChequeo;

    /**
     * Enum que indica el nombre del parámetro
     */
    private ParametroFOVISEnum parametro;

    /**
     * Contiene el usuario que radica la solicitud novedad
     */
    private String usuarioRadicacion;

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
     * Método que retorna el valor de novedadDTO.
     * 
     * @return valor de novedadDTO.
     */
    public ParametrizacionNovedadModeloDTO getParametrizacionNovedad() {
        return parametrizacionNovedad;
    }

    /**
     * Método encargado de modificar el valor de novedadDTO.
     * @param valor
     *        para modificar novedadDTO.
     */
    public void setParametrizacionNovedad(ParametrizacionNovedadModeloDTO parametrizacionNovedad) {
        this.parametrizacionNovedad = parametrizacionNovedad;
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
     * @return the idSolicitudNovedadFovis
     */
    public Long getIdSolicitudNovedadFovis() {
        return idSolicitudNovedadFovis;
    }

    /**
     * @param idSolicitudNovedadFovis
     *        the idSolicitudNovedadFovis to set
     */
    public void setIdSolicitudNovedadFovis(Long idSolicitudNovedadFovis) {
        this.idSolicitudNovedadFovis = idSolicitudNovedadFovis;
    }

    /**
     * @return the estadoSolicitudNovedad
     */
    public EstadoSolicitudNovedadFovisEnum getEstadoSolicitudNovedad() {
        return estadoSolicitudNovedad;
    }

    /**
     * @param estadoSolicitudNovedad
     *        the estadoSolicitudNovedad to set
     */
    public void setEstadoSolicitudNovedad(EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad) {
        this.estadoSolicitudNovedad = estadoSolicitudNovedad;
    }

    /**
     * @return the ciudadSedeRadicacion
     */
    public String getCiudadSedeRadicacion() {
        return ciudadSedeRadicacion;
    }

    /**
     * @param ciudadSedeRadicacion
     *        the ciudadSedeRadicacion to set
     */
    public void setCiudadSedeRadicacion(String ciudadSedeRadicacion) {
        this.ciudadSedeRadicacion = ciudadSedeRadicacion;
    }

    /**
     * @return the datosNovedadAutomaticaFovisDTO
     */
    public DatosNovedadAutomaticaFovisDTO getDatosNovedadAutomaticaFovisDTO() {
        return datosNovedadAutomaticaFovisDTO;
    }

    /**
     * @param datosNovedadAutomaticaFovisDTO
     *        the datosNovedadAutomaticaFovisDTO to set
     */
    public void setDatosNovedadAutomaticaFovisDTO(DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaFovisDTO) {
        this.datosNovedadAutomaticaFovisDTO = datosNovedadAutomaticaFovisDTO;
    }

    /**
     * @return the datosPostulacion
     */
    public SolicitudPostulacionFOVISDTO getDatosPostulacion() {
        return datosPostulacion;
    }

    /**
     * @param datosPostulacion
     *        the datosPostulacion to set
     */
    public void setDatosPostulacion(SolicitudPostulacionFOVISDTO datosPostulacion) {
        this.datosPostulacion = datosPostulacion;
    }

    /**
     * @return the causaIntentoFallido
     */
    public CausaIntentoFallidoNovedadEnum getCausaIntentoFallido() {
        return causaIntentoFallido;
    }

    /**
     * @param causaIntentoFallido
     *        the causaIntentoFallido to set
     */
    public void setCausaIntentoFallido(CausaIntentoFallidoNovedadEnum causaIntentoFallido) {
        this.causaIntentoFallido = causaIntentoFallido;
    }

    /**
     * @return the datosNovedadRegularFovisDTO
     */
    public DatosFOVISNovedadRegularDTO getDatosNovedadRegularFovisDTO() {
        return datosNovedadRegularFovisDTO;
    }

    /**
     * @param datosNovedadRegularFovisDTO
     *        the datosNovedadRegularFovisDTO to set
     */
    public void setDatosNovedadRegularFovisDTO(DatosFOVISNovedadRegularDTO datosNovedadRegularFovisDTO) {
        this.datosNovedadRegularFovisDTO = datosNovedadRegularFovisDTO;
    }

    /**
     * @return the resultadoRadicacion
     */
    public Integer getResultadoRadicacion() {
        return resultadoRadicacion;
    }

    /**
     * @param resultadoRadicacion
     *        the resultadoRadicacion to set
     */
    public void setResultadoRadicacion(Integer resultadoRadicacion) {
        this.resultadoRadicacion = resultadoRadicacion;
    }

    /**
     * @return the idPostulacion
     */
    public Long getIdPostulacion() {
        return idPostulacion;
    }

    /**
     * @param idPostulacion
     *        the idPostulacion to set
     */
    public void setIdPostulacion(Long idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    /**
     * @return the escalamientoMiembrosHogar
     */
    public EscalamientoSolicitudDTO getEscalamientoMiembrosHogar() {
        return escalamientoMiembrosHogar;
    }

    /**
     * @param escalamientoMiembrosHogar
     *        the escalamientoMiembrosHogar to set
     */
    public void setEscalamientoMiembrosHogar(EscalamientoSolicitudDTO escalamientoMiembrosHogar) {
        this.escalamientoMiembrosHogar = escalamientoMiembrosHogar;
    }

    /**
     * @return the escalamientoJuridico
     */
    public EscalamientoSolicitudDTO getEscalamientoJuridico() {
        return escalamientoJuridico;
    }

    /**
     * @param escalamientoJuridico
     *        the escalamientoJuridico to set
     */
    public void setEscalamientoJuridico(EscalamientoSolicitudDTO escalamientoJuridico) {
        this.escalamientoJuridico = escalamientoJuridico;
    }

    /**
     * @return the escalamientoTecnicoConstruccion
     */
    public EscalamientoSolicitudDTO getEscalamientoTecnicoConstruccion() {
        return escalamientoTecnicoConstruccion;
    }

    /**
     * @param escalamientoTecnicoConstruccion
     *        the escalamientoTecnicoConstruccion to set
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
     * @return the listaChequeo
     */
    public List<ItemChequeoDTO> getListaChequeo() {
        return listaChequeo;
    }

    /**
     * @param listaChequeo
     *        the listaChequeo to set
     */
    public void setListaChequeo(List<ItemChequeoDTO> listaChequeo) {
        this.listaChequeo = listaChequeo;
    }

    /**
     * @return the parametro
     */
    public ParametroFOVISEnum getParametro() {
        return parametro;
    }

    /**
     * @param parametro
     *        the parametro to set
     */
    public void setParametro(ParametroFOVISEnum parametro) {
        this.parametro = parametro;
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
     * @return the tipoProceso
     */
    public ProcesoEnum getTipoProceso() {
        return tipoProceso;
    }

    /**
     * @param tipoProceso
     *        the tipoProceso to set
     */
    public void setTipoProceso(ProcesoEnum tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    @Override
    public String toString() {
        return "SolicitudNovedadFovisDTO{" +
                "idSolicitud=" + idSolicitud +
                ", idSolicitudNovedadFovis=" + idSolicitudNovedadFovis +
                ", idInstancia=" + idInstancia +
                ", parametrizacionNovedad=" + parametrizacionNovedad +
                ", canalRecepcion=" + canalRecepcion +
                ", metodoEnvio=" + metodoEnvio +
                ", clasificacion=" + clasificacion +
                ", tipoTransaccion=" + tipoTransaccion +
                ", tipoProceso=" + tipoProceso +
                ", observaciones='" + observaciones + '\'' +
                ", resultadoValidacion=" + resultadoValidacion +
                ", excepcionTipoDos=" + excepcionTipoDos +
                ", estadoSolicitudNovedad=" + estadoSolicitudNovedad +
                ", numeroRadicacion='" + numeroRadicacion + '\'' +
                ", fechaRadicacion=" + fechaRadicacion +
                ", listResultadoValidacion=" + listResultadoValidacion +
                ", continuaProceso=" + continuaProceso +
                ", ciudadSedeRadicacion='" + ciudadSedeRadicacion + '\'' +
                ", datosNovedadAutomaticaFovisDTO=" + datosNovedadAutomaticaFovisDTO +
                ", datosPostulacion=" + datosPostulacion +
                ", causaIntentoFallido=" + causaIntentoFallido +
                ", datosNovedadRegularFovisDTO=" + datosNovedadRegularFovisDTO +
                ", resultadoRadicacion=" + resultadoRadicacion +
                ", idPostulacion=" + idPostulacion +
                ", escalamientoMiembrosHogar=" + escalamientoMiembrosHogar +
                ", escalamientoJuridico=" + escalamientoJuridico +
                ", escalamientoTecnicoConstruccion=" + escalamientoTecnicoConstruccion +
                ", escalamientoMiembrosHogarBack=" + escalamientoMiembrosHogarBack +
                ", escalamientoJuridicoBack=" + escalamientoJuridicoBack +
                ", escalamientoTecnicoConstruccionBack=" + escalamientoTecnicoConstruccionBack +
                ", listaChequeo=" + listaChequeo +
                ", parametro=" + parametro +
                ", usuarioRadicacion='" + usuarioRadicacion + '\'' +
                '}';
    }
}
