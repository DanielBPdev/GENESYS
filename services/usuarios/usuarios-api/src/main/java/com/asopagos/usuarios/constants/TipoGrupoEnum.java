package com.asopagos.usuarios.constants;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Enumeracion que representa los tipos de grupos de usuarios existentes en el aplicativo
 * <b>Descripcion:</b> Enumeracion para los tipos de reportes<br/>
 * <b>Módulo:</b> Asopagos - HU-157<br/>
 *
 * @author <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */
@XmlEnum
public enum TipoGrupoEnum {

    /**
     * Representa los grupos asociados al tipo caja de compensacion familiar
     */
    CAJA_COMPENSACION,

    /**
     * Representa los grupos asociados al tipo empleadores
     */
    EMPLEADORES,

    /**
     * Representa los grupos asociados al tipo predefinido
     */
    PREDEFINIDO,

    /**
     * Representa todos los grupos
     */
    TODOS;

}
