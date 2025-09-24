package com.asopagos.novedades.personas.web.load;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.asopagos.novedades.constants.NamedQueriesConstants;
import com.asopagos.novedades.constants.CamposGestionArchivosUsuariosConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.usuarios.dto.UsuarioGestionDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.exception.TechnicalException;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.util.Interpolator;

/**
 * <b>Descripción:</b> Clase para procesar líneas de un archivo y generar instancias de UsuarioGestionDTO.
 * 
 * @author 
 */
public class CargueInfoCreacionUsuariosEmpleador implements IPersistLine {

    /**
     * Procesa las líneas de un archivo para crear objetos UsuarioGestionDTO.
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
     *      javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        Map<String, Object> line;
        Map<String, String> correosUsados = new HashMap<>();

        for (LineArgumentDTO lineArgumentDTO : lines) {
            List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) 
                lineArgumentDTO.getContext().get(CamposGestionArchivosUsuariosConstants.LISTA_HALLAZGOS));

            try {
                line = lineArgumentDTO.getLineValues();
                UsuarioGestionDTO usuarioGestionDTO = new UsuarioGestionDTO();

                // Campo 1 - Tipo de identificación
                if (esCampoValido(line, CamposGestionArchivosUsuariosConstants.TIPO_IDENTIFICACION_EMPLEADOR)) {
                    String tipoIdentificacion = getValorCampo(line, CamposGestionArchivosUsuariosConstants.TIPO_IDENTIFICACION_EMPLEADOR).toUpperCase();
                    String tipoIdentEnum = TipoIdentificacionEnum
                            .obtenerTiposIdentificacionEnumGestionUsuario(tipoIdentificacion);
                    usuarioGestionDTO.setTipoIdentificacion(tipoIdentEnum);
                }

                // Campo 2 - Número de identificación
                if (esCampoValido(line, CamposGestionArchivosUsuariosConstants.NUMERO_IDENTIFICACION_EMPLEADOR)) {
                    String numeroIdentificacion = getValorCampo(line, CamposGestionArchivosUsuariosConstants.NUMERO_IDENTIFICACION_EMPLEADOR);
                    usuarioGestionDTO.setNumIdentificacion(numeroIdentificacion);

                    // Validar existencia en BD
                    Boolean usuarioExiste = existeUsuarioEnBD(usuarioGestionDTO.getTipoIdentificacion().toString(), numeroIdentificacion, em);
                    if (!usuarioExiste) {
                        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
                        hallazgo.setNumeroLinea(lineArgumentDTO.getLineNumber());
                        hallazgo.setNombreCampo(CamposGestionArchivosUsuariosConstants.NUMERO_IDENTIFICACION_EMPLEADOR);
                        hallazgo.setError("El Empleador No Existe en la Base de Datos");
                        hallazgos.add(hallazgo);
                    } else {
                        // Consultar correo
                        String correo = obtenerCorreoDesdeBD(usuarioGestionDTO.getTipoIdentificacion().toString(), numeroIdentificacion, em);

                        if (correo == null || correo.trim().isEmpty()) {
                            ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
                            hallazgo.setNumeroLinea(lineArgumentDTO.getLineNumber());
                            hallazgo.setNombreCampo("correo");
                            hallazgo.setError("El empleador no tiene correo registrado.");
                            hallazgos.add(hallazgo);
                        } else if (correosUsados.containsKey(correo.toLowerCase())) {
                            ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
                            hallazgo.setNumeroLinea(lineArgumentDTO.getLineNumber());
                            hallazgo.setNombreCampo("correo");
                            hallazgo.setError("correo duplicado con otro usuario");
                            hallazgos.add(hallazgo);
                        } else {
                            // Registrar el correo como usado y setear en el DTO
                            correosUsados.put(correo.toLowerCase(), numeroIdentificacion);
                            usuarioGestionDTO.setEmail(correo);
                        }
                    }
                }

                // Solo si no hay hallazgos para esta línea, se agrega a la lista de usuarios a crear
                if (hallazgos.stream().noneMatch(h -> h.getNumeroLinea() == lineArgumentDTO.getLineNumber())) {
                    ((List<UsuarioGestionDTO>) lineArgumentDTO.getContext()
                        .get(CamposGestionArchivosUsuariosConstants.LISTA_GESTION_USUARIOS))
                        .add(usuarioGestionDTO);
                }

            } catch (Exception e) {
                throw new TechnicalException(e);
            }
        }
    }

    /**
     * Método para verificar si un usuario existe en la base de datos, sin usar COUNT.
     *
     * @param tipoIdentificacion Tipo de identificación del usuario
     * @param numeroIdentificacion Número de identificación del usuario
     * @param em EntityManager para ejecutar la consulta
     * @return true si el usuario existe, false en caso contrario
     */
    private boolean existeUsuarioEnBD(String tipoIdentificacion, String numeroIdentificacion, EntityManager em) {
        Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_EMPLEADOR_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION);
        query.setParameter("numIdentificacion", numeroIdentificacion);
        query.setParameter("tipoIdentificacion", tipoIdentificacion);

        try {
            Object resultado = query.getSingleResult();
            return resultado != null;
        } catch (javax.persistence.NoResultException e) {
            return false;
        } catch (Exception e) {
            System.err.println("Error en la consulta: " + e.getMessage());
            return false;
        }
    }

    private String obtenerCorreoDesdeBD(String tipoIdentificacion, String numeroIdentificacion, EntityManager em) {
        try {
            Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_CORREO_EMPLEADOR_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION);
            query.setParameter("numIdentificacion", numeroIdentificacion);
            query.setParameter("tipoIdentificacion", tipoIdentificacion);
            return (String) query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        } catch (Exception e) {
            System.err.println("Error consultando correo: " + e.getMessage());
            return null;
        }
    }

    /**
     * @param line
     * @param nombreCampo
     * @return
     */
    private Boolean esCampoValido(Map<String, Object> line, String nombreCampo) {
        if (line.get(nombreCampo) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * @param line
     * @param nombreCampo
     * @return
     */
    private String getValorCampo(Map<String, Object> line, String nombreCampo) {
        return ((String) line.get(nombreCampo));
    }

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
     */
    @Override
    public void setRollback(EntityManager em) throws FileProcessingException {
        throw new UnsupportedOperationException();
    }
}