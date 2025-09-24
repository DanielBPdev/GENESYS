package com.asopagos.novedades.personas.web.load;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern; 
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO; // Cambiado de AfiliadoModeloDTO a BeneficiarioModeloDTO
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.constants.NamedQueriesConstants;
import com.asopagos.novedades.constants.ArchivoCampoNovedadCambioTipoNumeroMasivoConstante;
import com.asopagos.rest.exception.TechnicalException;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

public class CargueNovedadMasivaCambioNumeroTipoIdentificacionBeneficiario implements IPersistLine {

    // --- Patrones de expresiones regulares para validación de caracteres ---
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("\\d+");
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("[a-zA-Z0-9]+");

    /**
     * (non-Javadoc)
     * * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
     * javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        Map<String, Object> line;
        InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO = null;

        for (LineArgumentDTO lineArgumentDTO : lines) {
            List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                    .getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS));

            try {
                line = lineArgumentDTO.getLineValues();

                informacionActualizacionNovedadDTO = new InformacionActualizacionNovedadDTO();
                BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();

                // --- VALIDACIÓN Campos obligatorios ---
                boolean hayCamposFaltantes = false;

                boolean tipoIdentificacionActualPresente = esCampoValido(line, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_IDENTIFICACION_ACTUAL_BENEFICIARIO);
                boolean numeroIdentificacionActualPresente = esCampoValido(line, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_ACTUAL_BENEFICIARIO);
                boolean tipoIdentificacionNuevoPresente = esCampoValido(line, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_IDENTIFICACION_NUEVO_BENEFICIARIO);
                boolean numeroIdentificacionNuevoPresente = esCampoValido(line, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_NUEVO_BENEFICIARIO);

                if (!tipoIdentificacionActualPresente || !numeroIdentificacionActualPresente ||
                    !tipoIdentificacionNuevoPresente || !numeroIdentificacionNuevoPresente) {
                    
                    hayCamposFaltantes = true;

                    agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_IDENTIFICACION_ACTUAL_BENEFICIARIO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.DATOS_FALTANTES_MSG);
                    
                    agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_IDENTIFICACION_ACTUAL_BENEFICIARIO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.REGISTRAR_TODOS_LOS_CAMPOS_MSG);
                }

                if (hayCamposFaltantes) {
                    continue; 
                }

                // Se llega aqui si los 4 campos estan presentes y no vacios
                // Se obtiene sus valores de forma segura y se asignan
                String tipoIdentificacionActualStr = getValorCampo(line, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_IDENTIFICACION_ACTUAL_BENEFICIARIO).toUpperCase();
                String numeroIdentificacionActualStr = getValorCampo(line, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_ACTUAL_BENEFICIARIO);
                String tipoIdentificacionNuevoStr = getValorCampo(line, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_IDENTIFICACION_NUEVO_BENEFICIARIO).toUpperCase();
                String numeroIdentificacionNuevoStr = getValorCampo(line, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_NUEVO_BENEFICIARIO);

                beneficiarioModeloDTO.setTipoIdentificacion(TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoIdentificacionActualStr));
                beneficiarioModeloDTO.setNumeroIdentificacion(numeroIdentificacionActualStr);
                beneficiarioModeloDTO.setTipoIdentificacionNuevo(TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoIdentificacionNuevoStr));
                beneficiarioModeloDTO.setNumeroIdentificacionNuevo(numeroIdentificacionNuevoStr);

                // --- Validaciones de Lógica de Cambio de Identificación ---
                boolean tipoEsIgual = tipoIdentificacionActualStr.equals(tipoIdentificacionNuevoStr);
                boolean numeroEsIgual = numeroIdentificacionActualStr.equals(numeroIdentificacionNuevoStr);

                // --- VALIDACIÓN Tipo y Número Nuevo no deben ser iguales a los Actuales ---
                if (tipoEsIgual && numeroEsIgual) {
                    agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_IDENTIFICACION_NUEVO_BENEFICIARIO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_NUMERO_NUEVO_COINCIDE_ACTUAL_MSG);
                    continue;
                }

                // --- VALIDACIÓN Cambio de TI a RC ---
                if ("TI".equals(tipoIdentificacionActualStr) && "RC".equals(tipoIdentificacionNuevoStr)) {
                    agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_IDENTIFICACION_NUEVO_BENEFICIARIO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.CAMBIO_TI_A_RC_MSG);
                    continue;
                }

                // --- VALIDACIÓN Cambio de CC a RC ---
                if ("CC".equals(tipoIdentificacionActualStr) && "RC".equals(tipoIdentificacionNuevoStr)) {
                    agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_IDENTIFICACION_NUEVO_BENEFICIARIO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.CAMBIO_CC_A_RC_MSG);
                    continue;
                }

                // --- VALIDACIÓN Cambio de CC a TI ---
                if ("CC".equals(tipoIdentificacionActualStr) && "TI".equals(tipoIdentificacionNuevoStr)) {
                    agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_IDENTIFICACION_NUEVO_BENEFICIARIO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.CAMBIO_CC_A_TI_MSG);
                    continue;
                }

                // Validación para IDENTIFICACIÓN ACTUAL
                if (!validarTipoNumeroIdentificacion(tipoIdentificacionActualStr, numeroIdentificacionActualStr, 
                    hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), 
                    ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_ACTUAL_BENEFICIARIO)) {
                    continue;
                }

                // Validación para IDENTIFICACIÓN NUEVA
                if (!validarTipoNumeroIdentificacion(tipoIdentificacionNuevoStr, numeroIdentificacionNuevoStr, 
                    hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), 
                    ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_NUEVO_BENEFICIARIO)) {
                    continue;
                }
                
                // --- VALIDACIÓN Si NUMERO_IDENTIFICACION_NUEVO_BENEFICIARIO ya existe como persona ---
                // if (existeNumeroIdentificacion(beneficiarioModeloDTO.getNumeroIdentificacionNuevo(), em)) {
                //     agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_NUEVO_BENEFICIARIO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_NUEVO_YA_EXISTE_CON_OTRO_TIPO_MSG);
                //     continue;
                // }

                // --- VALIDACIÓN Si NUMERO_IDENTIFICACION_NUEVO_AFILIADO ya existe con OTRA persona ---
                if (existeNumeroIdentificacionConOtraPersona(beneficiarioModeloDTO.getNumeroIdentificacionNuevo(),beneficiarioModeloDTO.getTipoIdentificacion().toString(), beneficiarioModeloDTO.getNumeroIdentificacion().toString(), em)) {
                    agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_NUEVO_AFILIADO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_NUEVO_YA_EXISTE_CON_OTRO_TIPO_MSG);
                    continue;
                }
                
                // --- VALIDACIÓN si el beneficiario  existe como tal ---
                Boolean beneficiarioExiste = existeBeneficiario(beneficiarioModeloDTO.getTipoIdentificacion().toString(), beneficiarioModeloDTO.getNumeroIdentificacion().toString(), em);
                 Boolean beneficiarioFallecido = existeBeneficiarioFallecido(beneficiarioModeloDTO.getTipoIdentificacion().toString(), beneficiarioModeloDTO.getNumeroIdentificacion().toString(), em);
                if (!beneficiarioExiste) {
                    // --- VALIDACIÓN si el beneficiario  esta fallecido ---
                    if (beneficiarioFallecido) {
                        agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_ACTUAL_BENEFICIARIO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.BENEFICIARIO_FALLECIDO_MSG);
                        continue;
                    } else {
                        agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_ACTUAL_BENEFICIARIO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIPO_NUMERO_ACTUAL_NO_EXISTE_MSG);
                    }
                    continue;
                }
                
                // --- VALIDACIÓN si el beneficiario existe como afiliado  ---
                Boolean afiliadoExiste = existeAfiliado(beneficiarioModeloDTO.getTipoIdentificacion().toString(), beneficiarioModeloDTO.getNumeroIdentificacion().toString(), em);
                if (afiliadoExiste) {
                    agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_ACTUAL_BENEFICIARIO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.BENEFICIARIO_EXISTE_OTRO_TIPO_MSG);
                    continue;
                }

                // --- VALIDACIONES Novedades Activas Específicas para Beneficiario ---
                String tipoNovedadActiva = existeNovedadActivaBeneficiario(beneficiarioModeloDTO.getTipoIdentificacion().toString(), beneficiarioModeloDTO.getNumeroIdentificacion().toString(), em);

                if (tipoNovedadActiva != null) {
                    if ("CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS".equals(tipoNovedadActiva)) { 
                        agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_ACTUAL_BENEFICIARIO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIENE_UNA_SOLICITUD_MSG);
                        continue;
                    } else if ("REPORTE_FALLECIMIENTO_PERSONAS".equals(tipoNovedadActiva)) { 
                        agregarHallazgo(hallazgos, Long.valueOf(lineArgumentDTO.getLineNumber()), ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.NUMERO_IDENTIFICACION_ACTUAL_BENEFICIARIO, ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.TIENE_UNA_SOLICITUD_FALLECIMIENTO_MSG);
                        continue;
                    }
                }

                if (hallazgos.stream().noneMatch(h -> h.getNumeroLinea() == lineArgumentDTO.getLineNumber())) { 
                    informacionActualizacionNovedadDTO.setBeneficiario(beneficiarioModeloDTO);

                    // Si todas las validaciones pasaron, se añade la información para el procesamiento final
                    ((List<InformacionActualizacionNovedadDTO>) lineArgumentDTO.getContext()
                                .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(informacionActualizacionNovedadDTO);
                }


            } catch (Exception e) {
                // Captura cualquier excepción inesperada y la envuelve en una TechnicalException
                throw new TechnicalException(e);
            }
        }
    }

    /**
     * Verifica si el beneficario existe como beneficiario y afiliado al tiempo
     */
    private boolean existeAfiliado(String tipoIdentificacion, String numeroIdentificacion, EntityManager em) {
        Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_AFILIADO_EXISTE_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION);
        query.setParameter("numIdentificacion", numeroIdentificacion);
        query.setParameter("tipoIdentificacion", tipoIdentificacion);
        query.setMaxResults(1);

        try {
            Object resultado = query.getSingleResult();
            return resultado != null;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            System.err.println("Error en la consulta de afiliado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si un beneficiario existe y está activo
     */
    private boolean existeBeneficiario(String tipoIdentificacion, String numeroIdentificacion, EntityManager em) {
        Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_BENEFICIARIO_EXISTE_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION);
        query.setParameter("numIdentificacion", numeroIdentificacion);
        query.setParameter("tipoIdentificacion", tipoIdentificacion);
        query.setMaxResults(1);

        try {
            Object resultado = query.getSingleResult();
            return resultado != null;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            System.err.println("Error en la consulta de beneficiario activo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si un beneficiario esta fallecido
     */
    private boolean existeBeneficiarioFallecido(String tipoIdentificacion, String numeroIdentificacion, EntityManager em) {
        Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_DATOS_BENEFICIARIO_FALLECIDO_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION);
        query.setParameter("numIdentificacion", numeroIdentificacion);
        query.setParameter("tipoIdentificacion", tipoIdentificacion);
        query.setMaxResults(1);

        try {
            Object resultado = query.getSingleResult();
            return resultado != null;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            System.err.println("Error en la consulta de beneficiario activo: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verifica si el beneficiario tiene una novedad activa
     */
    private String existeNovedadActivaBeneficiario(String tipoIdentificacion, String numeroIdentificacion, EntityManager em){
        Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_NOVEDADES_ACTIVAS_BENEFICIARIO_POR_NUMERO_IDENTIFICACION_Y_TIPO_IDENTIFICACION);
        query.setParameter("numIdentificacion", numeroIdentificacion);
        query.setParameter("tipoIdentificacion", tipoIdentificacion);

        try {
            List<Object[]> results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                for (Object result : results) {
                    if (result instanceof Object[]) {
                        Object[] row = (Object[]) result;
                        String snoEstadoSolicitud = (String) row[7];
                        String solTipoTransaccion = (String) row[10];
                        String solResultadoProceso = (String) row[12];

                        if (snoEstadoSolicitud != null && !"CERRADA".equals(snoEstadoSolicitud) &&
                            !"APROBADA".equals(solResultadoProceso)) {
                            // Los nombres de las transacciones pueden necesitar ajuste para beneficiarios
                            if (solTipoTransaccion != null && solTipoTransaccion.contains("CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS")) {
                                return "CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS";
                            } else if (solTipoTransaccion != null && solTipoTransaccion.contains("REPORTE_FALLECIMIENTO_PERSONAS")) {
                                return "REPORTE_FALLECIMIENTO_PERSONAS";
                            }
                        }
                    }
                }
            }
            return null;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.err.println("Error en la consulta de novedad activa de beneficiario: " + e.getMessage());
            return null;
        }
    }

    /**
     * Verifica si el nuevo número de identificación ya existe
     */
    private boolean existeNumeroIdentificacion(String nuevoNumeroIdentificacion, EntityManager em) {
        Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_NUMERO_EXISTE_POR_NUMERO);
        query.setParameter("numIdentificacion", nuevoNumeroIdentificacion);
        query.setMaxResults(1);

        try {
            Object resultado = query.getSingleResult();
            return resultado != null;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            System.err.println("Error en la consulta de existencia de nuevo número de identificación (general): " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si el nuevo número de identificación ya existe asociado a OTRA persona,
     * excluyendo la combinación actual de tipo y número de identificación.
     */
    private boolean existeNumeroIdentificacionConOtraPersona(String nuevoNumeroIdentificacion, String tipoIdentificacionActual, String numeroIdentificacionActual, EntityManager em) {
        Query query = em.createNamedQuery(NamedQueriesConstants.CONSULTAR_PERSONA_EXISTE_POR_NUMERO_EXCLUYENDO_ACTUAL);
        query.setParameter("numNuevo", nuevoNumeroIdentificacion);
        query.setParameter("tipoActual", tipoIdentificacionActual);
        query.setParameter("numActual", numeroIdentificacionActual);
        query.setMaxResults(1);

        try {
            Object resultado = query.getSingleResult();
            return resultado != null;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            System.err.println("Error en la consulta de existencia de nuevo número de identificación excluyendo actual: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica la longitud del número de documento por tipo de documento
     */
    private boolean validarTipoNumeroIdentificacion(String tipoIdentificacion, String numeroIdentificacion, 
                                                    List<ResultadoHallazgosValidacionArchivoDTO> hallazgos, 
                                                    Long lineNumber, String campoNombre) {
        
        boolean isValid = true;
        String errorMessage = null;

        switch (tipoIdentificacion) {
            case "CC":
                if (numeroIdentificacion.length() < 1 || numeroIdentificacion.length() > 10 || !NUMERIC_PATTERN.matcher(numeroIdentificacion).matches()) {
                    errorMessage = ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.LONGITUD_NUMERO_CC_MSG;
                }
                break;
            case "CE":
                if (numeroIdentificacion.length() < 1 || numeroIdentificacion.length() > 16 || !NUMERIC_PATTERN.matcher(numeroIdentificacion).matches()) {
                    errorMessage = ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.LONGITUD_NUMERO_CE_MSG;
                }
                break;
            case "PA":
                if (numeroIdentificacion.length() < 1 || numeroIdentificacion.length() > 16 || !ALPHANUMERIC_PATTERN.matcher(numeroIdentificacion).matches()) {
                    errorMessage = ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.LONGITUD_NUMERO_PA_MSG;
                }
                break;
            case "CD":
                if (numeroIdentificacion.length() < 1 || numeroIdentificacion.length() > 15 || !ALPHANUMERIC_PATTERN.matcher(numeroIdentificacion).matches()) {
                    errorMessage = ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.LONGITUD_NUMERO_CD_MSG;
                }
                break;
            case "PE":
                if (numeroIdentificacion.length() != 16 || !ALPHANUMERIC_PATTERN.matcher(numeroIdentificacion).matches()) {
                    errorMessage = ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.LONGITUD_NUMERO_PE_MSG;
                }
                break;
            case "SC":
                if (numeroIdentificacion.length() < 1 || numeroIdentificacion.length() > 16 || !ALPHANUMERIC_PATTERN.matcher(numeroIdentificacion).matches()) {
                    errorMessage = ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.LONGITUD_NUMERO_SC_MSG;
                }
                break;
            case "PT":
                if (numeroIdentificacion.length() < 1 || numeroIdentificacion.length() > 8 || !NUMERIC_PATTERN.matcher(numeroIdentificacion).matches()) {
                    errorMessage = ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.LONGITUD_NUMERO_PT_MSG;
                }
                break;
            case "RC":
                if (! (numeroIdentificacion.length() == 8 || numeroIdentificacion.length() == 10 || numeroIdentificacion.length() == 11) || 
                     !NUMERIC_PATTERN.matcher(numeroIdentificacion).matches()) {
                    errorMessage = ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.LONGITUD_NUMERO_RC_MSG;
                }
                break;
            case "TI":
                if (! (numeroIdentificacion.length() == 10 || numeroIdentificacion.length() == 11) || 
                     !NUMERIC_PATTERN.matcher(numeroIdentificacion).matches()) {
                    errorMessage = ArchivoCampoNovedadCambioTipoNumeroMasivoConstante.LONGITUD_NUMERO_TI_MSG;
                }
                break;
            default:
                errorMessage = "Tipo de identificación no soportado o inválido para la validación de formato.";
                break;
        }

        if (errorMessage != null) {
            agregarHallazgo(hallazgos, lineNumber, campoNombre, errorMessage);
            isValid = false;
        }
        return isValid;
    }

    /**
     * Método auxiliar para añadir hallazgos de forma limpia.
     */
    private void agregarHallazgo(List<ResultadoHallazgosValidacionArchivoDTO> hallazgos, Long lineNumber, String campoNombre, String mensajeError) {
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(campoNombre);
        hallazgo.setError(mensajeError);
        hallazgos.add(hallazgo);
    }

    /**
     * @param line
     * @param nombreCampo
     * @return
     */
    private Boolean esCampoValido(Map<String, Object> line, String nombreCampo) {
        if (line.get(nombreCampo) != null) {
            String value = ((String) line.get(nombreCampo)).trim();
            return !value.isEmpty();
        }
        return Boolean.FALSE;
    }

    /**
     * @param line
     * @param nombreCampo
     * @return
     */
    private String getValorCampo(Map<String, Object> line, String nombreCampo) {
        Object value = line.get(nombreCampo);
        return (value != null) ? ((String) value).trim() : "";
    }

    /**
     * (non-Javadoc)
     * * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
     */
    @Override
    public void setRollback(EntityManager em) throws FileProcessingException {
        throw new UnsupportedOperationException();
    }
}