package com.asopagos.subsidiomonetario.pagos.business.interfaces;


import java.util.List;

import javax.ejb.Local;

import com.asopagos.subsidiomonetario.pagos.dto.DescuentoSubsidioAsignadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoProgramadoDTO;


/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-31-XXX <br/>
 *
 * @author <a href="mailto:hhernandez@heinsohn.com.co"> Ricardo Hernandez Cediel</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

@Local
public interface IConsultasModeloSubsidio {

    /**
     * Metodo que registrar la carga del archivo de retiro del tercero pagador
     * 
     * @param cargueArchivoRetiro
     *        objeto que contiene información relevante de la caarga del archivo de retiro para ser
     *        almacenada en la base de datos.
     * @return identificador de la carga del archivo de retiro creada en la base de datos
     */
    public List<DetalleSubsidioAsignadoProgramadoDTO> consultarFechasProgramadasSubsidioFallecimiento(String numeroRadicado);
    
}

  