package com.asopagos.subsidiomonetario.pagos.load.source;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.subsidiomonetario.pagos.constants.NamedQueriesConstants;
import com.asopagos.subsidiomonetario.pagos.constants.TipoArchivoPagoEnum;
import com.asopagos.subsidiomonetario.pagos.dto.RegistroPagoBancoDTO;
import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;
import co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource;
import co.com.heinsohn.lion.filegenerator.exception.FileGeneratorException;

/**
 * <b>Descripcion:</b> Clase que se encarga de obtener la información para la generación del archivo de consignaciones a bancos o pagos
 * judiciales<br/>
 * <b>Módulo:</b> Asopagos - HU 311-441<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DataSourceLinePagoBanco implements ILineDataSource {

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(DataSourceLinePagoBanco.class);

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.filegenerator.ejb.ILineDataSource#getData(co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO, int,
     *      int, javax.persistence.EntityManager)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Object> getData(QueryFilterInDTO queryFilter, int firstResult, int maxResults, EntityManager em)
            throws FileGeneratorException {
        String firmaMetodo = "DataSourceLinePagoBanco.getData(QueryFilterInDTO,int,int,EntityManager)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<RegistroPagoBancoDTO> pagosBancosDTO = new ArrayList<RegistroPagoBancoDTO>();

        try {

            List<Object[]> registrosConsignacionesPagos = new ArrayList<Object[]>();
            Boolean isMesAMes = Boolean.FALSE;
            if (((ArchivoPagoBancoFilterDTO) queryFilter).getNumeroRadicacion() != null && !((ArchivoPagoBancoFilterDTO) queryFilter).getNumeroRadicacion().isEmpty()) {
            	try {
            		//Se verifica si la solicitud de liquidación es MES a MES
                    String tipoDesembolso = (String)em
                            .createNamedQuery(NamedQueriesConstants.CONSULTAR_SOLICITUD_LIQUIDACION_TIPO_DESEMBOLSO)
                            .setParameter("numeroRadicacion", ((ArchivoPagoBancoFilterDTO) queryFilter).getNumeroRadicacion()).getSingleResult();
                    if(tipoDesembolso != null && !tipoDesembolso.isEmpty()) {
                    	if (tipoDesembolso.equals(ModoDesembolsoEnum.MES_POR_MES.name())) {
                    		isMesAMes = Boolean.TRUE;
                    	} 
                    }
				} catch (NoResultException e) {
					isMesAMes = Boolean.FALSE;
				}
            } 
            if (isMesAMes) {
            	 registrosConsignacionesPagos = em
                         .createNamedQuery(NamedQueriesConstants.CONSULTAR_PAGOS_CONSIGNACIONES_BANCARIAS_GENERACION_ARCHIVO_MES_POR_MES)
                         .setParameter("numeroRadicacion", ((ArchivoPagoBancoFilterDTO) queryFilter).getNumeroRadicacion()).getResultList();
            } else {
            	if (((ArchivoPagoBancoFilterDTO) queryFilter).getLstIdCuentasNuevas() != null) {
            		 registrosConsignacionesPagos = em
                             .createNamedQuery(NamedQueriesConstants.CONSULTAR_PAGOS_TRANSACCIONES_219_GENERACION_ARCHIVO)
                             .setParameter("lstIdCuentasTransaccion", ((ArchivoPagoBancoFilterDTO) queryFilter).getLstIdCuentasNuevas()).getResultList();
            	} else {
            		 registrosConsignacionesPagos = em
                             .createNamedQuery(NamedQueriesConstants.CONSULTAR_PAGOS_CONSIGNACIONES_BANCARIAS_GENERACION_ARCHIVO)
                             .setParameter("numeroRadicacion", ((ArchivoPagoBancoFilterDTO) queryFilter).getNumeroRadicacion()).getResultList();
            	}
            }
            
                //Obtener la información de las consignaciones a bancos
                //TODO a esta consulta falta enviarle el tipo de archivo que se desea generar para que seleccione consignaciones o pagos judiciales
//                registrosConsignacionesPagos = em
//                        .createNamedQuery(NamedQueriesConstants.CONSULTAR_PAGOS_CONSIGNACIONES_BANCARIAS_GENERACION_ARCHIVO)
//                        .setParameter("numeroRadicacion", ((ArchivoPagoBancoFilterDTO) queryFilter).getNumeroRadicacion()).getResultList();
            //}
            //else {
                //Obtener la información de las consignaciones a bancos por concepto de pagos judiciales
            //}

            for (Object[] registroConsignacionesPagos : registrosConsignacionesPagos) {
                RegistroPagoBancoDTO pagoBancoDTO = new RegistroPagoBancoDTO();

                pagoBancoDTO.setCodigoEmpresa(registroConsignacionesPagos[0].toString());
                pagoBancoDTO.setNumeroCuentaSudameris(registroConsignacionesPagos[1].toString());
                pagoBancoDTO.setTipoCuentaSudameris(registroConsignacionesPagos[2].toString());
                pagoBancoDTO.setNombreEmpresa(registroConsignacionesPagos[3].toString());
                pagoBancoDTO.setDescripcionGeneral(registroConsignacionesPagos[4].toString());
                pagoBancoDTO.setCodigoBancoReceptor(registroConsignacionesPagos[5].toString());
                pagoBancoDTO.setTipoCuentaReceptora(registroConsignacionesPagos[6].toString());
                pagoBancoDTO.setNumeroCuentaReceptora(registroConsignacionesPagos[7].toString());
                if (registroConsignacionesPagos[8].toString().length() != 12) {
                    String numero = "00000000000";
                    pagoBancoDTO.setNumeroIdentificacion(numero.substring(registroConsignacionesPagos[8].toString().length() - 1)
                            .concat(registroConsignacionesPagos[8].toString()));
                }
                else {
                    pagoBancoDTO.setNumeroIdentificacion(registroConsignacionesPagos[8].toString());
                }
                pagoBancoDTO.setNombreDestinatario(registroConsignacionesPagos[9].toString());
                pagoBancoDTO.setDescripcionPago(registroConsignacionesPagos[10].toString());
                pagoBancoDTO.setValorPago(BigDecimal.valueOf(Double.parseDouble(registroConsignacionesPagos[11].toString())));
                
                //validación para saber si se esta generando el archivo por consignaciones bancarias
                if(((ArchivoPagoBancoFilterDTO) queryFilter).getTipoArchivoPago().equals(TipoArchivoPagoEnum.CONSIGNACIONES_BANCOS) &&
                        (registroConsignacionesPagos[12] == null || !Boolean.parseBoolean(registroConsignacionesPagos[12].toString()))){
                    pagosBancosDTO.add(pagoBancoDTO);
                }else if(((ArchivoPagoBancoFilterDTO) queryFilter).getTipoArchivoPago().equals(TipoArchivoPagoEnum.PAGOS_JUDICIALES) &&
                        (registroConsignacionesPagos[12] != null && Boolean.parseBoolean(registroConsignacionesPagos[12].toString()))){
                    pagosBancosDTO.add(pagoBancoDTO);
                }
                
            }

        } catch (Exception e) {
            logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e.getMessage());
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
        return (List) pagosBancosDTO;
    }
    
}
