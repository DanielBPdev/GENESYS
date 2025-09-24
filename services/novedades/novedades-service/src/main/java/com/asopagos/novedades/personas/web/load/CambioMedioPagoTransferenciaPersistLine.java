// ==================== MASIVO TRANSFERENCIA
package com.asopagos.novedades.personas.web.load;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidades.subsidiomonetario.liquidacion.SubsidioMonetarioValorPignorado;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.OrigenRegistroPignoracionEnum;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.entidades.transversal.core.CargueTrasladoMedioPagoTranferencia;
import com.asopagos.novedades.business.interfaces.EntityManagerProceduresPersistenceLocal;
import com.asopagos.enumeraciones.core.OrigenCambioMedioPagoTransferenciaEnum;
/**
 * <b>Descripcion:</b> Clase que contiene la implementación requerida para realizar la persistencia de la información
 * obtenida del archivo de descuentos
 * <br/>
 * <b>Módulo:</b> Asopagos - HU 311-432<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class CambioMedioPagoTransferenciaPersistLine implements IPersistLine {
    
    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(CambioMedioPagoTransferenciaPersistLine.class);
    
    /**
     * Lista de identificadores asociados a los registros del archivo
     */
    private List<Long> identificadoresRegistros = new ArrayList<>();

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List, javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
	@Override
    public void persistLine(List<LineArgumentDTO> arg0, EntityManager emCore) throws FileProcessingException {
        logger.info("Inicia persistLine CambioMedioPagoTransferenciaPersistLine(List<LineArgumentDTO>, EntityManager)");
        
		EntityManager em = obtenerEntityCore();
        
        for (LineArgumentDTO lineArgumentDTO : arg0) {
            System.out.println("*** TIPO_IDENTIFICACION_AFILIADO_PRINCIPAL: " +lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO_PRINCIPAL));
            try {
            	
            	 List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                         .getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS));

                 boolean errorLinea = false;

                 for (int i = 0; i < hallazgos.size(); i++) {

                     if (lineArgumentDTO.getLineNumber() == hallazgos.get(i).getNumeroLinea().longValue()) {
                         errorLinea = true;
                         break;
                     }

                 }
 
                if (!errorLinea) {
                	 Map<String, Object> values = lineArgumentDTO.getLineValues();

                     for(Map.Entry<String,Object> entrada : values.entrySet()){
                        logger.warn("entra map de las lineas");
                        logger.info("Llave: "+ entrada.getKey() +" Valor: "+ (String) entrada.getValue());
                     }

                     CargueTrasladoMedioPagoTranferencia cargue = new CargueTrasladoMedioPagoTranferencia();
                     cargue.setTipoIdentificacionAfiliadoPrincipal(calcularTipoIdentificacion(values.get(ArchivoMultipleCampoConstants.TIPO_IDENTIFICACION_AFILIADO_PRINCIPAL).toString()));
                     cargue.setNumeroIdentificacionAfiliadoPrincipal(values.get(ArchivoMultipleCampoConstants.NUMERO_IDENTIFICACION_AFILIADO_PRINCIPAL).toString());
                     cargue.setCodigoBanco(Long.valueOf(values.get(ArchivoMultipleCampoConstants.BANCO).toString()));
                     cargue.setTipoCuenta(Long.valueOf(values.get(ArchivoMultipleCampoConstants.TIPO_CUENTA).toString()));
                     cargue.setNumeroCuenta(Long.valueOf(values.get(ArchivoMultipleCampoConstants.NUMERO_CUENTA).toString()));
                     cargue.setOrigenRegistro(OrigenCambioMedioPagoTransferenciaEnum.CARGUE_ARCHIVO);
                     cargue.setCodigoIdentificacionECM(lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.CODIGO_IECM_ARCHIVO).toString());

                     ((List<CargueTrasladoMedioPagoTranferencia>) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_CARGUE_NOVEDAD_MASIVA_TRANSFERENCIA)).add(cargue);
                     
                     em.persist(cargue);
                    //  identificadoresRegistros.add(cargue.getIdSubsidioMonetarioValorPignorado());
                     em.flush();
                }
            } catch (Exception e) {
                FileProcessingException e1 = new FileProcessingException("Error persistiendo", e);
                e1.setLineArgumentDTO(lineArgumentDTO);
                throw e;
            }
        }
        logger.info("Finaliza persistLine CambioMedioPagoTransferenciaPersistLine(List<LineArgumentDTO>, EntityManager)");
    }
    
    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
     */
    @Override
    public void setRollback(EntityManager arg0) throws FileProcessingException {
		EntityManager em = obtenerEntityCore();
        try {
            // Query query = em.createNamedQuery(NamedQueriesConstants.ELIMINAR_REGISTROS_ARCHIVO_DESCUENTOS).setParameter("myIds",
            //         identificadoresRegistros);
            // int count = query.executeUpdate();
            System.out.println("*** Registros Eliminados a la Entidad SubsidioMonetarioValorPignorado" );
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
	private EntityManager obtenerEntityCore() {
        EntityManagerProceduresPersistenceLocal em = ResourceLocator.lookupEJBReference(EntityManagerProceduresPersistenceLocal.class);
        return em.getEntityManager();
    }

    private TipoIdentificacionEnum calcularTipoIdentificacion(String abreviado){
        logger.info("Incia private TipoIdentificacionEnum calcularTipoIdentificacion(String abreviado)");
        switch (abreviado){
            case "CC":
                return TipoIdentificacionEnum.CEDULA_CIUDADANIA;
            case "CE":
                return TipoIdentificacionEnum.CEDULA_EXTRANJERIA;
            case "TI":
                return TipoIdentificacionEnum.TARJETA_IDENTIDAD;
            case "PA":
                return TipoIdentificacionEnum.PASAPORTE;
            case "CD":
                return TipoIdentificacionEnum.CARNE_DIPLOMATICO;
            // case "SC":
            //     return TipoIdentificacionEnum.SALVO_CONDUCTO;
            case "PE":
                return TipoIdentificacionEnum.PERM_ESP_PERMANENCIA;
            case "PT":
                return TipoIdentificacionEnum.PERM_PROT_TEMPORAL;
            default:
                logger.warn("El valor de 'abreviado' no coincide con ningún tipo conocido: " + abreviado);
                throw new IllegalArgumentException("El valor de 'abreviado' no es válido: " + abreviado);
        }
    }
}
