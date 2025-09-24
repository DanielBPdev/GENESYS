package com.asopagos.entidaddescuento.business.interfaces;

import java.util.List;
import javax.ejb.Local;
import com.asopagos.dto.DefinicionCamposCargaDTO;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO;
import com.asopagos.entidaddescuento.dto.ArchivoSalidaDescuentoSubsidioModeloDTO;
import com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO;


/**
 * <b>Descripcion:</b> Interfaz que contiene define las funciones para la consulta de información en
 * el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-311-434 <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 * @author  <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

@Local
public interface IConsultasModeloCore {   
    
    /**
     * Metodo que consulta una entidad de descuento, sea solo por código o nombre
     * o por los dos atributos.
     * @param codigo variable que contiene el valor del código de la entidad de descuento.
     * @param nombre variable que contiene el valor del nombre de la entidad de descuento.
     * @return entidades de descuentos DTO.
     */
    public List<EntidadDescuentoModeloDTO> consultarEntidadDescuento(Long codigo, String nombre);
    
    /**
     * <b>Descripción: </b>Método que se encarga de consultar una entidad de descuento por su identificador
     * 
     * @author rlopez
     * 
     * @param codigoEntidadDescuento
     *        valor del codigo de la entidad
     * @return DTO con la información de la entidad
     */
    public EntidadDescuentoModeloDTO consultarEntidadDescuentoId(Long idEntidadDescuento);
    
    /**
     * Metodo que consulta las prioridades existentes registradas en las
     * entidades de la base de datos
     * 
     * @return una lista de las prioridades existentes.
     */
    public List<Long> consultarPrioriodadesEntidadesDescuento();
    
    /**
     * Metodo que trae ctodas las entidades de descuento registradas.
     * @param codigo variable que contiene el valor del código de la entidad de descuento.
     * @param nombre variable que contiene el valor del nombre de la entidad de descuento.
     * @return Lista de entidades de descuento.
     */
    public List<EntidadDescuentoModeloDTO> consultarEntidadesDescuento(Long codigo, String nombre);
    
    /**
     * Metodo que se encarga de crear una entidad de descuento.
     * @param entidadDescuentoModeloDTO variable que contiene los valores de la entidad de descuento.
     * @return id de la entidad de descuento creada en la base de datos.
     */
    public Long crearEntidadDescuento(EntidadDescuentoModeloDTO entidadDescuentoModeloDTO);
    
    /**
     * Metodo que se encarga de actualizar los valores de una entidad de descuento.
     * @param entidadDescuentoModeloDTO variable que contiene los valores a ser cambiados en una entidad de descuento.
     * @return id de la entidad de descuento.
     */
    public Long actualizarEntidadDescuento(EntidadDescuentoModeloDTO entidadDescuentoModeloDTO);
      
    /**
     * Metodo que encarga de traer el código maximo encontrado de las entidades de descuento
     * y sumarle un uno.
     * @return próximo código a ser asignado a una entidad de descuento
     */
    public Long buscarProximoCodigoEntidadDescuento();
    
    /**
     * Método que permite obtener la lista de identificadores de las entidades de descuento activas
     * @return lista de indentificadores de las entidades activas
     */
    public List<Long> obtenerEntidadesDescuentoActivas();
    
    
    public List<DefinicionCamposCargaDTO> consultarCamposDelArchivo(Long fileLoadedId);
    
    /**
     * Servicio que permite registrar el archivo de salida de descuentos de subsidios.
     * @param Información del archivo de Salida
     * @author flopez
     */
    public void crearRegistroArchivoSalidaDescuento(ArchivoSalidaDescuentoSubsidioModeloDTO infoArchivo);
    
    /**
     * ervicio que obtiene el Archivo de Salida de Descuentos para una solicitud y entidad de descuento
     * @param Información del archivo de Salida
     * @author flopez
     */
    public String obtenerArchivosSalidaDescuentos(String numeroRadicacion, Long idEntidadDescuento);
    
}
