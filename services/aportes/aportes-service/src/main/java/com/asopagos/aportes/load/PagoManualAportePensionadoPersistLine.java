package com.asopagos.aportes.load;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIPEnum;
import com.asopagos.util.CalendarUtils;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripcion:</b> Clase que se encarga de realizar la transformación de los datos del 
 * archivo en los DTO que representan la información del formulario de cotizantes<br/>
 * <b>Módulo:</b> Asopagos - HU-212-482 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class PagoManualAportePensionadoPersistLine implements IPersistLine {
    /**
     * Lista de cotizantes dto
     */
    private List<CotizanteDTO> lstCotizante = new ArrayList<>();

    /**
     * NovedadCotizanteDTO
     */
    private List<NovedadCotizanteDTO> lstNovedadCotizanteDTO;
    
    /**
     * Novedad cotizante dto
     */
    private NovedadCotizanteDTO novedadCotizante = null;

    /** (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List, javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        CotizanteDTO cotizanteDTO = null;
        List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;
        for (int i = 0; i < lines.size(); i++) {
            LineArgumentDTO lineArgumentDTO = lines.get(i);
            lstHallazgos = (List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO.getContext()
                    .get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS);
            if (lstHallazgos.isEmpty()) {
                try {
                    cotizanteDTO = simularCapturaDatosPantalla(lineArgumentDTO);
                    lineArgumentDTO.getContext().put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, lstCotizante);
                    ((List<CotizanteDTO>) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS))
                            .add(cotizanteDTO);
                } catch (Exception e) {
                    lineArgumentDTO.getContext().put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, lstCotizante);
                    ((List<CotizanteDTO>) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS))
                            .add(cotizanteDTO);
                }
            }
        }
    }

    /** (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
     */
    @Override
    public void setRollback(EntityManager em) throws FileProcessingException {
        throw new UnsupportedOperationException();
    }

    /**
     * Método que simula la captura de la pantalla para mantener uniformidad en
     * el llamado de los datos que deben ser mostrados en la HU-482
     * 
     * @param lineArgumentDTO
     * @return retorna el cotizante dto
     */
    private CotizanteDTO simularCapturaDatosPantalla(LineArgumentDTO lineArgumentDTO) {
        Map<String, Object> line = lineArgumentDTO.getLineValues();
        CotizanteDTO cotizanteDTO = new CotizanteDTO();
        // Tipo identificacion
        if (line.get(EtiquetaArchivoIPEnum.IP23.getNombreCampo()) != null) {
            TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(
                    (String) line.get(EtiquetaArchivoIPEnum.IP23.getNombreCampo()));
            cotizanteDTO.setTipoIdentificacion(tipoIdentificacion);
        }
        // Numero de identificacion
        if (line.get(EtiquetaArchivoIPEnum.IP24.getNombreCampo()) != null) {
            cotizanteDTO
                    .setNumeroIdentificacion((String) line.get(EtiquetaArchivoIPEnum.IP24.getNombreCampo()));
        }

        cotizanteDTO = validarDatosPersonales(line, cotizanteDTO);
        
        // Salario basico
        if (line.get(EtiquetaArchivoIPEnum.IP213.getNombreCampo()) != null) {
            String salarioBasico= line.get(EtiquetaArchivoIPEnum.IP213.getNombreCampo()).toString();
            BigDecimal salario = new BigDecimal(salarioBasico);
            cotizanteDTO.setSalarioBasico(salario);
        }

        // Tarifa
        if (line.get(EtiquetaArchivoIPEnum.IP211.getNombreCampo()) != null) {
            String valorTarifa=line.get(EtiquetaArchivoIPEnum.IP211.getNombreCampo()).toString();
            BigDecimal tarifa = new BigDecimal(valorTarifa);
            cotizanteDTO.setTarifa(tarifa);
        }
        
        // Aporte Obligatorio
        if (line.get(EtiquetaArchivoIPEnum.IP212.getNombreCampo()) != null) {
            String aporteObligatorio=line.get(EtiquetaArchivoIPEnum.IP212.getNombreCampo()).toString();
            BigDecimal tarifaAporte =new BigDecimal(aporteObligatorio);
            cotizanteDTO.setAporteObligatorio(tarifaAporte);
        }
        
        // Días cotizados
        if (line.get(EtiquetaArchivoIPEnum.IP214.getNombreCampo()) != null) {
            String diasCotizadosString = line.get(EtiquetaArchivoIPEnum.IP214.getNombreCampo()).toString();
            Short diasCotizados = Short.valueOf(diasCotizadosString);
            cotizanteDTO.setDiasCotizados(diasCotizados);
        }
        
        cotizanteDTO = validarFechaIngresoRetiro(line, cotizanteDTO);

        generarNovedades(line);
        cotizanteDTO.setNovedades(lstNovedadCotizanteDTO);
        return cotizanteDTO;
    }

    /**
     * Método encargado de validar los datos personales
     * 
     * @param lstDepartamento,
     *            listados de departamentos a comprar
     * @param listaMunicipios,
     *            listado de municipios a comparar
     * @param line,
     *            mapa que contiene la información a validar
     * @param cotizanteDTO,
     *            cotizanteDTO al cual se le agregara la informacion
     */
    private CotizanteDTO validarDatosPersonales(Map<String, Object> line, CotizanteDTO cotizanteDTO) {
        // Primer apellido
        if (line.get(EtiquetaArchivoIPEnum.IP25.getNombreCampo()) != null) {
            cotizanteDTO.setPrimerApellido(((String) line.get(EtiquetaArchivoIPEnum.IP25.getNombreCampo())).toUpperCase());
        }
        // Segundo apellido
        if (line.get(EtiquetaArchivoIPEnum.IP26.getNombreCampo()) != null) {
            cotizanteDTO.setSegundoApellido(((String) line.get(EtiquetaArchivoIPEnum.IP26.getNombreCampo())).toUpperCase());
        }
        // Primer Nombre
        if (line.get(EtiquetaArchivoIPEnum.IP27.getNombreCampo()) != null) {
            cotizanteDTO.setPrimerNombre(((String) line.get(EtiquetaArchivoIPEnum.IP27.getNombreCampo())).toUpperCase());
        }
        // Segundo Nombre
        if (line.get(EtiquetaArchivoIPEnum.IP28.getNombreCampo()) != null) {
            cotizanteDTO.setSegundoNombre(((String) line.get(EtiquetaArchivoIPEnum.IP28.getNombreCampo())).toUpperCase());
        }
        return cotizanteDTO;
    }

    /**
     * Método encargado de validar la fecha de ingreso y fecha retiro
     * 
     * @param line,
     *            mapa que contiene la informacion a validar
     * @param cotizanteDTO,
     *            cotizante al cual se le agregaran los campos
     * @return retorna el cotizanteDTO de la informacion
     */
    private CotizanteDTO validarFechaIngresoRetiro(Map<String, Object> line, CotizanteDTO cotizanteDTO) {
        // Fecha Ingreso
        if (line.get(EtiquetaArchivoIPEnum.IP219.getNombreCampo()) != null) {
            String strFechaIngreso = line.get(EtiquetaArchivoIPEnum.IP219.getNombreCampo()).toString();
            Long fechaIngreso = null;
            try {
                fechaIngreso = (new Date(CalendarUtils.convertirFechaDate(strFechaIngreso))).getTime();
            } catch (Exception e) {
                fechaIngreso = null;
            }
            cotizanteDTO.setFechaIngreso(fechaIngreso);
        }
        // Fecha Retiro
        if (line.get(EtiquetaArchivoIPEnum.IP220.getNombreCampo()) != null) {
            String strFechaRetiro = line.get(EtiquetaArchivoIPEnum.IP220.getNombreCampo()).toString();
            Long fechaRetiro = null;
            try {
                fechaRetiro = (new Date(CalendarUtils.convertirFechaDate(strFechaRetiro))).getTime();
            } catch (Exception e) {
                fechaRetiro = null;
            }
            cotizanteDTO.setFechaRetiro(fechaRetiro);
        }
        return cotizanteDTO;
    }

    /**
     * Método encargado de genear la novedades
     * 
     * @param line,
     *            mapa que contiene la informacion a validar
     */
    private List<NovedadCotizanteDTO> generarNovedades(Map<String, Object> line) {
        lstNovedadCotizanteDTO = new ArrayList<>();
        
        novedadCotizante = crearNovedadCotizanteDTO(TipoTransaccionEnum.NOVEDAD_ING, line, 
                EtiquetaArchivoIPEnum.IP215.getNombreCampo(), EtiquetaArchivoIPEnum.IP219.getNombreCampo(), null);
        agregarNovedadLista(novedadCotizante);

        novedadCotizante = crearNovedadCotizanteDTO(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6, line,
                EtiquetaArchivoIPEnum.IP216.getNombreCampo(), EtiquetaArchivoIPEnum.IP220.getNombreCampo(), null);
        agregarNovedadLista(novedadCotizante);
        
        novedadCotizante = crearNovedadCotizanteDTO(
                TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL, line,
                EtiquetaArchivoIPEnum.IP217.getNombreCampo(), EtiquetaArchivoIPEnum.IP221.getNombreCampo(), null);
        agregarNovedadLista(novedadCotizante);
        
        novedadCotizante = crearNovedadCotizanteDTO(
                TipoTransaccionEnum.SUSPENSION_PENSIONADO_SUS, line,
                EtiquetaArchivoIPEnum.IP218.getNombreCampo(), EtiquetaArchivoIPEnum.IP222.getNombreCampo(), EtiquetaArchivoIPEnum.IP223.getNombreCampo());
        agregarNovedadLista(novedadCotizante);
        
        return lstNovedadCotizanteDTO;
    }

    /**
     * Método encargado de agregar la novedad de listas
     * 
     * @param novedad,
     *            novedad a agregar a cotizante
     */
    private void agregarNovedadLista(NovedadCotizanteDTO novedad) {
        if (novedad != null) {
            lstNovedadCotizanteDTO.add(novedadCotizante);
        }
        novedadCotizante = null;
    }

    /**
     * Método encargado de crear una novedad de cotizante dto
     * 
     * @param tipoNovedad,
     *            tipo de novedad
     * @param contexto,
     *            contenido de la informacion
     * @param campoArchivo,
     *            campo de la novedad en el contexto
     * @param campoFechaInicio,
     *            campo fecha inicio
     * @param campoFechaFin,
     *            campo fecha fin
     * @return retorna la NovedadCotizanteDTO
     */
    private NovedadCotizanteDTO crearNovedadCotizanteDTO(TipoTransaccionEnum tipoNovedad, Map<String, Object> contexto,
            String campoArchivo, String campoFechaInicio, String campoFechaFin) {
        NovedadCotizanteDTO novedadCotizanteDTO = null;
        String campo = (String) contexto.get(campoArchivo);
        if (campo != null && (campo.contains("X") || campo.contains("x"))) {
            novedadCotizanteDTO = new NovedadCotizanteDTO();
            novedadCotizanteDTO.setTipoNovedad(tipoNovedad);
            novedadCotizanteDTO.setCondicionNueva(true);
            if (campoFechaInicio != null && (contexto.get(campoFechaInicio) != null)) {
                Date fechaInicio= CalendarUtils.convertirFechaAnoMesDia(contexto.get(campoFechaInicio).toString());
                novedadCotizanteDTO.setFechaInicio(fechaInicio !=null ? fechaInicio.getTime():null);
            }
            if (campoFechaFin != null && (contexto.get(campoFechaFin) != null)) {
                Date fechaFin= CalendarUtils.convertirFechaAnoMesDia(contexto.get(campoFechaFin).toString());
                novedadCotizanteDTO.setFechaFin(fechaFin!=null ? fechaFin.getTime():null);
            }
        }
        return novedadCotizanteDTO;
    }

}
