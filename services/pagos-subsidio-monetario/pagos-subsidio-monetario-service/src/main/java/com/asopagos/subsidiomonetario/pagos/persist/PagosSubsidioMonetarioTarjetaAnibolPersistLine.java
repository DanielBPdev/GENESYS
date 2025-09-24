package com.asopagos.subsidiomonetario.pagos.persist;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoRegistroArchivoConsumoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoRegistroArchivoConsumoAnibolEnum;
import com.asopagos.subsidiomonetario.pagos.constants.CamposArchivoConstants;
import com.asopagos.subsidiomonetario.pagos.dto.TarjetaRetiroCandidatoDTO;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:mosorio@heinsohn.com.co"> mosorio</a>
 */

public class PagosSubsidioMonetarioTarjetaAnibolPersistLine implements IPersistLine {

    /** (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List, javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        
        for (LineArgumentDTO lineArgumentDTO : lines) {

            TarjetaRetiroCandidatoDTO retiroCandidatoDTO = null;

            try {

                List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                        .getContext().get(CamposArchivoConstants.LISTA_HALLAZGOS));

                boolean errorLinea = false;
                
                Long numeroLineaError = null;

                for (int i = 0; i < hallazgos.size(); i++) {

                    if (lineArgumentDTO.getLineNumber() == hallazgos.get(i).getNumeroLinea().longValue()) {
                        errorLinea = true;
                        numeroLineaError = lineArgumentDTO.getLineNumber();
                    }
                }

                retiroCandidatoDTO = crearRetiroCandidatoDTO(lineArgumentDTO);
                if (!errorLinea) {// retiros de tarjetas sin errores

                    if (retiroCandidatoDTO.getIndicadorReverso().charValue() == '1') {
                        //se realiza el registro de las tarjetas que serán reversadas.
                        retiroCandidatoDTO.setTipoRegistroArchivoConsumo(TipoRegistroArchivoConsumoAnibolEnum.REVERSO);
                        retiroCandidatoDTO.setEstadoRegistro(EstadoRegistroArchivoConsumoAnibolEnum.NO_PROCESADO);
                        ((List<TarjetaRetiroCandidatoDTO>) lineArgumentDTO.getContext().get(CamposArchivoConstants.LISTA_REVERSOS))
                                .add(retiroCandidatoDTO);

                    }
                    else if (!retiroCandidatoDTO.getDescripcionTransaccion().contains("0  MN COMPRA CUOTA MONETARIA") 
                            || !retiroCandidatoDTO.getDescripcionRespuesta().contains("TRANSACCION EXITOSA")) {
                        //se realiza el registro de las tarjetas que pertenecen a otras transacciones
                        retiroCandidatoDTO.setTipoRegistroArchivoConsumo(TipoRegistroArchivoConsumoAnibolEnum.OTRA_TRANSACCION);
                        retiroCandidatoDTO.setEstadoRegistro(EstadoRegistroArchivoConsumoAnibolEnum.NO_PROCESADO);
                        ((List<TarjetaRetiroCandidatoDTO>) lineArgumentDTO.getContext()
                                .get(CamposArchivoConstants.LISTA_OTRAS_TRANSACCIONES)).add(retiroCandidatoDTO);

                    }
                    else {// se realiza el registro de los candidatos a retiros
                        retiroCandidatoDTO.setTipoRegistroArchivoConsumo(TipoRegistroArchivoConsumoAnibolEnum.RETIRO);
                        ((List<TarjetaRetiroCandidatoDTO>) lineArgumentDTO.getContext().get(CamposArchivoConstants.LISTA_CANDIDATOS))
                                .add(retiroCandidatoDTO);
                    }

                }
                else {//retiros de tarjetas con errores
                    
                    //se asocia la linea del error que detecto Lion, con el registro de la tarjeta
                    retiroCandidatoDTO.setNumeroLineaError(numeroLineaError);
                    
                    if (retiroCandidatoDTO.getIndicadorReverso().charValue() == '1')
                        retiroCandidatoDTO.setTipoRegistroArchivoConsumo(TipoRegistroArchivoConsumoAnibolEnum.REVERSO);
                    else if (!((List<Long>) lineArgumentDTO.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO_OTRAS_TRANSACCIONES))
                            .isEmpty())
                        retiroCandidatoDTO.setTipoRegistroArchivoConsumo(TipoRegistroArchivoConsumoAnibolEnum.OTRA_TRANSACCION);
                    else
                        retiroCandidatoDTO.setTipoRegistroArchivoConsumo(TipoRegistroArchivoConsumoAnibolEnum.RETIRO);
                    ((List<TarjetaRetiroCandidatoDTO>) lineArgumentDTO.getContext().get(CamposArchivoConstants.LISTA_ERRORES_HALLAZGOS))
                            .add(retiroCandidatoDTO);
                }

            } catch (Exception e) {

                ((List<TarjetaRetiroCandidatoDTO>) lineArgumentDTO.getContext().get(CamposArchivoConstants.LISTA_CANDIDATOS))
                        .add(retiroCandidatoDTO);
            }
        }
        
    }

    /** (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
     */
    @Override
    public void setRollback(EntityManager arg0) throws FileProcessingException {
        
    }
    
    /**
     * Método que crea un retiro de tarjeta candidato a ser comparado
     * posteriormente con los registros de la base de datos.
     * @param lineArgumentDTO:
     *        <code>LineArgumentDTO</code>
     *        Contiene un map con cada campo de cada linea del archivo
     * @return DTO del retiro de la tarjeta candidato.
     */
    private TarjetaRetiroCandidatoDTO crearRetiroCandidatoDTO(LineArgumentDTO lineArgumentDTO) {

        Map<String, Object> line = lineArgumentDTO.getLineValues();

        String identificadorCajaCompensacion = (String) line.get(CamposArchivoConstants.IDENTIFICADOR_CAJA_COMPENSACION);
        String numeroTarjeta = (String)line.get(CamposArchivoConstants.NUMERO_TARJETA);
        String nitEmpresa = (String) line.get(CamposArchivoConstants.NIT_EMPRESA);
        String cuentaRelacionada = (String) line.get(CamposArchivoConstants.CUENTA_RELACIONADA);
        String dispositivoOrigen = (String) line.get(CamposArchivoConstants.DISPOSITIVO_ORIGEN);
        String descripcionEstadoCobro = (String) line.get(CamposArchivoConstants.DESCRIPCION_ESTADO_COBRO);
        String descripcionTransaccion = (String) line.get(CamposArchivoConstants.DESCRIPCION_TRANSACCION);
        BigDecimal valorTransaccion = null;
        if(line.get(CamposArchivoConstants.VALOR_TRANSACCION)!=null){
        	valorTransaccion = ((BigDecimal) line.get(CamposArchivoConstants.VALOR_TRANSACCION)).movePointLeft(2);
        }
        BigDecimal valorDispensando = null;
        if(line.get(CamposArchivoConstants.VALOR_DISPENSADO)!=null){
        	valorDispensando = ((BigDecimal) line.get(CamposArchivoConstants.VALOR_DISPENSADO)).movePointLeft(2);
        }
        
        String fechaTransaccion = (String) line.get(CamposArchivoConstants.FECHA_TRANSACCION);
        
        BigDecimal valorCobro = null;
        if(line.get(CamposArchivoConstants.VALOR_A_COBRAR) != null){
        	valorCobro = ((BigDecimal) line.get(CamposArchivoConstants.VALOR_A_COBRAR)).movePointLeft(2);
        }
        
        BigDecimal valorImpuestos = null;
        if(line.get(CamposArchivoConstants.VALOR_IMPUESTOS) != null){
        	valorImpuestos = ((BigDecimal) line.get(CamposArchivoConstants.VALOR_IMPUESTOS)).movePointLeft(2);
        }
        BigDecimal totalCobro = null;
        if(line.get(CamposArchivoConstants.TOTAL_A_COBRAR) != null){
        	totalCobro = ((BigDecimal) line.get(CamposArchivoConstants.TOTAL_A_COBRAR)).movePointLeft(2);
        }
        BigDecimal impuestoEmergenciaEconomica = null;
        if(line.get(CamposArchivoConstants.IMPUESTO_EMERGENCIA_ECONOMICA)!= null){
        	impuestoEmergenciaEconomica = ((BigDecimal) line.get(CamposArchivoConstants.IMPUESTO_EMERGENCIA_ECONOMICA)).movePointLeft(2);
        }
        Character indicadorReverso = (Character) line.get(CamposArchivoConstants.INDICADOR_REVERSO);
        String respuestaAutorizador = (String)line.get(CamposArchivoConstants.RESPUESTA_AUTORIZADOR);
        String descripcionRespuesta = (String) line.get(CamposArchivoConstants.DESCRIPCION_RESPUESTA);
        String codigoAutorizacion = (String)line.get(CamposArchivoConstants.CODIGO_AUTORIZADOR);
        String subtipo = (String)line.get(CamposArchivoConstants.SUBTIPO);
        String fechaAutorizador = (String) line.get(CamposArchivoConstants.FECHA_AUTORIZADOR);
        String horaAutorizador = (String) line.get(CamposArchivoConstants.HORA_AUTORIZADOR);
        String horaDispositivo = (String) line.get(CamposArchivoConstants.HORA_DISPOSITIVO);
        String numeroReferencia = (String) line.get(CamposArchivoConstants.NUMERO_REFERENCIA);
        String red = (String) line.get(CamposArchivoConstants.RED);
        String numeroDispositivo = (String) line.get(CamposArchivoConstants.NUMERO_DISPOSITIVO);
        String codigoEstablecimiento = (String) line.get(CamposArchivoConstants.CODIGO_ESTABLECIMIENTO);
        String codigoCuenta = (String) line.get(CamposArchivoConstants.CODIGO_CUENTA);
        
        TarjetaRetiroCandidatoDTO tarjetaRetiroCandidatoDTO = new TarjetaRetiroCandidatoDTO();
        tarjetaRetiroCandidatoDTO.setBinCCF(identificadorCajaCompensacion);
        tarjetaRetiroCandidatoDTO.setNumeroTarjeta(numeroTarjeta);
        tarjetaRetiroCandidatoDTO.setNitEmpresa(nitEmpresa);
        tarjetaRetiroCandidatoDTO.setCuentaRelacionada(cuentaRelacionada);
        tarjetaRetiroCandidatoDTO.setDispositivoOrigen(dispositivoOrigen);
        tarjetaRetiroCandidatoDTO.setDescripcionCobroSubsidio(descripcionEstadoCobro);
        tarjetaRetiroCandidatoDTO.setDescripcionTransaccion(descripcionTransaccion);
        tarjetaRetiroCandidatoDTO.setValorTransaccion(valorTransaccion);
        tarjetaRetiroCandidatoDTO.setValorDispensando(valorDispensando);
        tarjetaRetiroCandidatoDTO.setFechaTransaccion(fechaTransaccion);
        tarjetaRetiroCandidatoDTO.setValorACobrar(valorCobro);
        tarjetaRetiroCandidatoDTO.setValorImpuestos(valorImpuestos);
        tarjetaRetiroCandidatoDTO.setTotalACobrar(totalCobro);
        tarjetaRetiroCandidatoDTO.setImpuestoEmergenciaEconomica(impuestoEmergenciaEconomica);
        tarjetaRetiroCandidatoDTO.setIndicadorReverso(indicadorReverso);
        tarjetaRetiroCandidatoDTO.setRespuestaAutorizador(respuestaAutorizador);
        tarjetaRetiroCandidatoDTO.setDescripcionRespuesta(descripcionRespuesta);
        tarjetaRetiroCandidatoDTO.setCodigoAutorizacion(codigoAutorizacion);
        tarjetaRetiroCandidatoDTO.setSubtipo(subtipo);
        tarjetaRetiroCandidatoDTO.setFechaAutorizador(fechaAutorizador);
        tarjetaRetiroCandidatoDTO.setHoraAutorizador(horaAutorizador);
        tarjetaRetiroCandidatoDTO.setHoraDispositivo(horaDispositivo);
        tarjetaRetiroCandidatoDTO.setNumeroReferencia(numeroReferencia);
        tarjetaRetiroCandidatoDTO.setRed(red);
        tarjetaRetiroCandidatoDTO.setNumeroDispositivo(numeroDispositivo);
        tarjetaRetiroCandidatoDTO.setCodigoEstablecimiento(codigoEstablecimiento);
        tarjetaRetiroCandidatoDTO.setCodigoCuenta(codigoCuenta);
        tarjetaRetiroCandidatoDTO.setFechaHoraCreacion(new Date());
        
        return tarjetaRetiroCandidatoDTO;
    }

}
