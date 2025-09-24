package com.asopagos.novedades.personas.web.load;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.UbicacionDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.transversal.core.Departamento;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;
import com.asopagos.dto.modelo.ConfirmacionAbonoBancarioCargueDTO;
import com.asopagos.novedades.clients.ObtenerTitularCuentaAdministradorSubsidioByCasId;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.novedades.business.interfaces.EntityManagerProceduresPersistenceLocal;
import com.asopagos.entidades.subsidiomonetario.pagos.ConfirmacionAbonos;
/**
 * <b>Descripcion:</b> <b>Módulo:</b> Asopagos - HU:241 </br>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
public class ConfirmacionAbonosBancariosPersistLine implements IPersistLine {

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
     *      javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        Map<String, Object> line;

        InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO = null;
        for (int i = 0; i < lines.size(); i++) {
//            ACTIVAR ESTAS LÍNEAS SI SE DEBEN PROCESAR LOS EXITOSOS E IGNORAR LOS FALLIDOS!!!
            LineArgumentDTO lineArgumentDTO = lines.get(i);
            List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                    .getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS));

            //Cambio esta validacion para que retorne los registros validos sin importar si hay hallazgos.
            boolean persist = true;
            if (!hallazgos.isEmpty() && hallazgos.stream().anyMatch(iteHallazgo -> 
                    iteHallazgo.getNumeroLinea().equals(lineArgumentDTO.getLineNumber()))) {
                persist = false;
            }
            
            if (persist) {
                try {
                    EntityManager emCore = obtenerEntityCore();
                    line = lineArgumentDTO.getLineValues();
                    ConfirmacionAbonos registro = new ConfirmacionAbonos();
                    // Contiene la informacion de la linea del archivo para la asignacion de diferencias
                    informacionActualizacionNovedadDTO = new InformacionActualizacionNovedadDTO();
                    
                    //informacion de la estructura del archivo conf de abonos bancarios - admin subsidio
                    ConfirmacionAbonoBancarioCargueDTO confirmacionAbonoDTO = new ConfirmacionAbonoBancarioCargueDTO();
                     
                    registro.setEstadoRegistro("EN_PROCESO");             
                     if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_ID_ADMIN_SUBSIDIO)) {
                         String tipoIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_ID_ADMIN_SUBSIDIO)
                                 .toUpperCase();
                         TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
                                 .obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
                         confirmacionAbonoDTO.setTipoIdentificacion(tipoIdentEnum);
                         registro.setTipoIdentificacion(tipoIdentEnum);
                     }else{
                        
                        //Consultar tipo de identificación por el CASID
                        Object[] obtenerTitualarAdminSubsidioObjectResult =null;
                        String casId = (String) line.get((ArchivoCampoNovedadConstante.ID_TRANSACCION_CUENTA_ADMIN_SUBSIDIO));
                        ObtenerTitularCuentaAdministradorSubsidioByCasId obtenerTipoIdentificacion = new ObtenerTitularCuentaAdministradorSubsidioByCasId(casId);
                        obtenerTipoIdentificacion.execute();   
                        obtenerTitualarAdminSubsidioObjectResult = obtenerTipoIdentificacion.getResult();
                        String tipoIdentificacion = obtenerTitualarAdminSubsidioObjectResult[0].toString();  
                        TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
                                 .obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
                         confirmacionAbonoDTO.setTipoIdentificacion(tipoIdentEnum);
                         registro.setTipoIdentificacion(tipoIdentEnum);
                         
                     }
                  
                    
                    // Se asigna el campo No 2 - Número de identificación 
                   
                     if (esCampoValido(line, ArchivoCampoNovedadConstante.NUM_ID_ADMIN_SUBSIDIO)) {
                         String numeroIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.NUM_ID_ADMIN_SUBSIDIO);
                         confirmacionAbonoDTO.setNumeroIdentificacion(numeroIdentificacion);
                         registro.setNumeroIdentificacion(numeroIdentificacion);
                     }
                    
                    
                    //
                 
                     if (esCampoValido(line, ArchivoCampoNovedadConstante.ID_TRANSACCION_CUENTA_ADMIN_SUBSIDIO)) {
                         String casId = getValorCampo(line, ArchivoCampoNovedadConstante.ID_TRANSACCION_CUENTA_ADMIN_SUBSIDIO);
                         confirmacionAbonoDTO.setCasId(casId);
                         registro.setIdCuentaAdminSubsidio(Long.valueOf(casId));
                     }
                  
                    
                  
                      if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_CUENTA)) {
                          String tipoCuenta = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_CUENTA);
                          confirmacionAbonoDTO.setTipoCuenta(tipoCuenta);
                          registro.setTipoCuentaAdmonSubsidio(tipoCuenta);
                      }
                    
                    
                     
                       if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_CUENTA)) {
                           String numeroCuenta = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_CUENTA);
                           confirmacionAbonoDTO.setNumeroCuenta(numeroCuenta);
                           registro.setNumeroCuentaAdminSubsidio(numeroCuenta);
                       }
                      
                  
                     if (esCampoValido(line, ArchivoCampoNovedadConstante.VALOR_TRANSFERENCIA)) {
                         String valorTransferencia = getValorCampo(line, ArchivoCampoNovedadConstante.VALOR_TRANSFERENCIA);
                         confirmacionAbonoDTO.setValorTransferencia(valorTransferencia);
                         registro.setValorTransferencia(valorTransferencia);
                     }
             
                     if (esCampoValido(line, ArchivoCampoNovedadConstante.RESULTADO_ABONO)) {
                         String resultadoAbono = getValorCampo(line, ArchivoCampoNovedadConstante.RESULTADO_ABONO);
                         confirmacionAbonoDTO.setResultadoAbono(resultadoAbono);
                         registro.setResultadoAbono(resultadoAbono);
                         if(resultadoAbono.equals("ABONO EXITOSO")){
                            registro.setEstadoRegistro("FINALIZADO");             
                         }
                     }
                     
              
                     if (esCampoValido(line, ArchivoCampoNovedadConstante.MOTIVO)) {
                         String motivo = getValorCampo(line, ArchivoCampoNovedadConstante.MOTIVO);
                         confirmacionAbonoDTO.setMotivoRechazoAbono(motivo);
                         registro.setMotivoRechazo(motivo);
                     }else{
                         confirmacionAbonoDTO.setMotivoRechazoAbono("");
                         registro.setMotivoRechazo("");
                     }
               
                     
                     if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_HORA_CONFIRMACION)) {
                         String fechaConfirmacion = getValorCampo(line, ArchivoCampoNovedadConstante.FECHA_HORA_CONFIRMACION);
                         confirmacionAbonoDTO.setFechaConfirmacionAbono(fechaConfirmacion);
                         registro.setFechaConfirmacionAbono(fechaConfirmacion);
                     }


                     String nombreArchivo = lineArgumentDTO.getContext().get("nombreArchivo").toString();
                     registro.setNombreArchivo(nombreArchivo);
                     registro.setNumeroLinea(lineArgumentDTO.getLineNumber());
                     em.persist(registro);

                 
                    //informacionActualizacionNovedadDTO.setConfirmacionAbonoAdminSubsidio(confirmacionAbonoDTO);

                    /*
                    ((List<InformacionActualizacionNovedadDTO>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(informacionActualizacionNovedadDTO);
                             */
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    /*
                     * 
                     ((List<InformacionActualizacionNovedadDTO>) lineArgumentDTO.getContext()
                             .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(informacionActualizacionNovedadDTO);
                     */
                }
            }
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

    private EntityManager obtenerEntityCore() {
        EntityManagerProceduresPersistenceLocal em = ResourceLocator.lookupEJBReference(EntityManagerProceduresPersistenceLocal.class);
        return em.getEntityManager();
    }
}