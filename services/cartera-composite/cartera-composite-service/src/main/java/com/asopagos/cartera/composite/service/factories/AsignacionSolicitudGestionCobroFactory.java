package com.asopagos.cartera.composite.service.factories;

import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.rest.security.dto.UserDTO;

import java.util.List;

/**
 * <b>Descripción: </b> Fábrica para la asignación de solicitudes de gestión de
 * cobro en cartera de aportes <br/>
 * <b>Historia de Usuario: </b> HU-164
 *
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 * Benavides</a>
 */
public class AsignacionSolicitudGestionCobroFactory {

    /**
     * Instancia <code>singleton</code> de la clase
     */
    private static AsignacionSolicitudGestionCobroFactory instance;

    /**
     * Método que obtiene la instancia <code>singleton</code> de la clase
     *
     * @return La instancia <code>singleton</code>
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static AsignacionSolicitudGestionCobroFactory getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (instance == null) {
            instance = new AsignacionSolicitudGestionCobroFactory();
        }

        return instance;
    }

    /**
     * Método que crea la asignación de solicitudes de gestión de cobro
     *
     * @param accionCobro Tipo de acción de cobro
     * @param userDTO     Información del usuario que realiza la asignación
     * @return El objeto <code>AsignacionSolicitudGestionCobro</code>
     * instanciado
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public AsignacionSolicitudGestionCobro crearAsignacion(TipoAccionCobroEnum accionCobro, UserDTO userDTO) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        AsignacionSolicitudGestionCobro asignacion = null;

        switch (accionCobro) {
            case A1:
                asignacion = new AsignacionSolicitudGestionCobro1A(userDTO);
                break;
            case B1:
                asignacion = new AsignacionSolicitudGestionCobro1B(userDTO);
                break;
            case C1:
                asignacion = new AsignacionSolicitudGestionCobro1C(userDTO);
                break;
            case D1:
                asignacion = new AsignacionSolicitudGestionCobro1D(userDTO);
                break;
            case E1:
                asignacion = new AsignacionSolicitudGestionCobro1E(userDTO);
                break;
            case F1:
                asignacion = new AsignacionSolicitudGestionCobro1F(userDTO);
                break;
            case A2:
                asignacion = new AsignacionSolicitudGestionCobro2A(userDTO);
                break;
            case B2:
                asignacion = new AsignacionSolicitudGestionCobro2B(userDTO);
                break;
            case C2:
                asignacion = new AsignacionSolicitudGestionCobro2C(userDTO);
                break;
            case D2:
                asignacion = new AsignacionSolicitudGestionCobro2D(userDTO);
                break;
            case E2:
                asignacion = new AsignacionSolicitudGestionCobro2E(userDTO);
                break;
            case F2:
                asignacion = new AsignacionSolicitudGestionCobro2F(userDTO);
                break;
            case G2:
                asignacion = new AsignacionSolicitudGestionCobro2G(userDTO);
                break;
            case H2:
                asignacion = new AsignacionSolicitudGestionCobro2H(userDTO);
                break;
            case LC2A:
                asignacion = new AsignacionSolicitudGestionCobroLC2A(userDTO);
                break;
            case LC3A:
                asignacion = new AsignacionSolicitudGestionCobroLC3A(userDTO);
                break;
            case LC4A:
                asignacion = new AsignacionSolicitudGestionCobroLC4A(userDTO);
                break;
            case LC4C:
                asignacion = new AsignacionSolicitudGestionCobroLC4C(userDTO);
                break;
            case LC5A:
                asignacion = new AsignacionSolicitudGestionCobroLC5A(userDTO);
                break;
            case LC5C:
                asignacion = new AsignacionSolicitudGestionCobroLC5C(userDTO);
                break;
            case H2_C6:
                //CREADO NUEVO COMUNICADO NOTIFI_MORA_DESAF
                asignacion = new AsignacionSolicitudGestionCobroH2C6(userDTO);
                break;
            case F1_C6:
                //CREADO NUEVO COMUNICADO GESTION_MORA_DESAF
                asignacion = new AsignacionSolicitudGestionCobroF1C6(userDTO);
                break;
            case H2_C6_EX:
                //CREADO NUEVO COMUNICADO DESAFIALICON
                asignacion = new AsignacionSolicitudGestionCobroExpulsionH2C6(userDTO, null);
                break;
            case F1_C6_EX:
                //CREADO NUEVO COMUNICADO DESAFIALICON
                asignacion = new AsignacionSolicitudGestionCobroExpulsionF1C6(userDTO, null);
                break;
            default:
                asignacion = new AsignacionSolicitudGestionCobroCierre(accionCobro, userDTO);
                break;
        }

        return asignacion;
    }

    public AsignacionSolicitudGestionCobro crearAsignacion(TipoAccionCobroEnum accionCobro, UserDTO userDTO, List<Long> idPersonasAProcesar) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        AsignacionSolicitudGestionCobro asignacion = null;

        switch (accionCobro) {
            case H2_C6_EX:
                //CREADO NUEVO COMUNICADO DESAFIALICON
                asignacion = new AsignacionSolicitudGestionCobroExpulsionH2C6(userDTO, idPersonasAProcesar);
                break;
            case F1_C6_EX:
                //CREADO NUEVO COMUNICADO DESAFIALICON
                asignacion = new AsignacionSolicitudGestionCobroExpulsionF1C6(userDTO, idPersonasAProcesar);
                break;
            default:
                asignacion = new AsignacionSolicitudGestionCobroCierre(accionCobro, userDTO);
                break;
        }

        return asignacion;
    }

}
