package com.asopagos.entidaddescuento.business.interfaces;

import java.util.List;
import javax.ejb.Local;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO;
import com.asopagos.entidaddescuento.dto.ArchivoSalidaDescuentoSubsidioModeloDTO;

/**
 * <b>Descripcion:</b> Interfaz que contiene define las funciones para la consulta de información en
 * el modelo de datos Subsidio <br/>
 * <b>Módulo:</b> Asopagos - HU-311-432 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */

@Local
public interface IConsultasModeloSubsidio {

    /**
     * Método que se encarga de registrar la información de trazabilidad del archivo de descuentos
     * @param informarcionTrazabilidadDTO
     *        DTO con la información de trazabilidad
     * @return identificador de la trazabilidad registrada
     */
    public Long registrarTrazabilidadArchivoDescuentos(ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO informarcionTrazabilidadDTO);

    /**
     * Método que se encarga de actualizar la información de trazabilidad del archivo de descuentos
     * @param informarcionTrazabilidadDTO
     *        DTO con la información de trazabilidad
     * @return identificador de la trazabilidad registrada
     */
    public Long actualizarTrazabilidadArchivoDescuentos(ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO informarcionTrazabilidadDTO);

    /**
     * Método que permite obtener la lista de registros asociados a la trazabilidad de los archivos de descuento con estado CARGADO
     * @return DTO´s con la información de trazabilidad
     */
    public List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO> obtenerInformacionTrazabilidad(List<String> nombresArchivos,
                                                                                                  List<Long> identificadoresEntidades);

    /**
     * Método que permite obtener la información de trazabilidad relacionada con un archivo de descuento a partir de su identificador
     * @return DTO con la información de trazabilidad
     */
    public ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO obtenerInformacionTrazabilidadId(Long idTrazabilidad);

    /**
     * Método que permite obtener la lista de Entidades de descuento que participaron en la liquidación.
     * @param numeroRadicacion
     *        valor del número de radicación
     * @author flopez
     * @return lista de indentificadores de entidad de descuento
     */
    public List<Long> obtenerEntidadesDescuentoRadicacion(String numeroRadicacion);

    /**
     * Método que permite acutalizar la información de los archivos de descuento para una liquidación cancelada
     * @param numeroRadicacion
     *        valor del número de radicación
     * @author rlopez
     */
    public void actualizarArchivosDescuentoLiquidacionCancelada(String numeroRadicacion);

    /**
     * Método que permite ejecutar el SP ACTUALIZAR_DESCUENTOS_NUEVO_ARCHIVO para actualizar los archivos sin descuentos pendientes
     * @param idArchivo
     *        valor del id del archivo cargado
     * @param codigoEntidad
     *        valor del id de la entidad de descuento
     * @author jMontana
     */
    public Long ejecutarActualizacionArchivosDescuento(Long idArchivo, Long codigoEntidad);

    /**
     * Método que permite consultar los ArchivoEntidadDescuentoSubsidioPignorado por el nombre
     * @param nombreArchivo
     *        valor del nombre del archivo cargado
     */
    public String consultarArchivosDescuentoPorNombre(String nombreArchivo);
}
