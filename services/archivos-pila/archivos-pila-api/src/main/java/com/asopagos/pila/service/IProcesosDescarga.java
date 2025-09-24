package com.asopagos.pila.service;

import java.util.List;
import javax.ejb.Local;
import com.asopagos.dto.ArchivoPilaDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.util.ConexionServidorFTPUtil;

/**
 * <b>Descripcion:</b> Interfaz que define los métodos para la descarga asíncrona de
 * contenido de archivos PILA<br/>
 * <b>Módulo:</b> Asopagos - HU-211-387 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */
@Local
public interface IProcesosDescarga {
    /**
     * Método para la descarga Asincrona de archivos del FTP. Al finalizar la descarga actualiza los respectivos índices
     * 
     * @param conexionFTP
     *        Datos de conexión con el FTP
     * @param archivosDescargados
     *        Listado de los archivos a descargar del FTP
     */
    public List<IndicePlanilla> descargarYguardarListadoFTPAsincrono(ConexionServidorFTPUtil<ArchivoPilaDTO> conexionFTP,
            List<ArchivoPilaDTO> archivosDescargados, String usuario, Long idProceso);

    /**
     * Método para la descarga Sincrona de archivos del FTP
     * 
     * @param conexionFTP
     *        Datos de conexión con el FTP
     * @param archivosDescargadosTemp
     *        Listado de los archivos a descargar del FTP
     *        @return Listado de los archivos descargados
     */
    public List<ArchivoPilaDTO> descargarYguardarListadoFTPSincrono(ConexionServidorFTPUtil<ArchivoPilaDTO> conexionFTP,
            List<ArchivoPilaDTO> archivosDescargados);
}
