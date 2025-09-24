package com.asopagos.subsidiomonetario.pagos.persist;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.subsidiomonetario.pagos.constants.CamposArchivoConstants;
import com.asopagos.subsidiomonetario.pagos.dto.RetiroCandidatoDTO;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripcion:</b> Clase que permite almacenar los retiros candidatos que pasaron
 * la validación de la estructura de linea del archivo, para ser comparadas con los registros
 * de la base de datos posteriormente.
 * <br/>
 * <b>Módulo:</b> Asopagos - HU - 31 - 205<br/>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
public class PagosSubsidioMonetarioPersistLine implements IPersistLine {

    @Override
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        // TODO Auto-generated method stub
        for (LineArgumentDTO lineArgumentDTO : lines) {

            RetiroCandidatoDTO retiroCandidatoDTO = null;

            try {

                List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                        .getContext().get(CamposArchivoConstants.LISTA_HALLAZGOS));

                boolean errorLinea = false;

                for (int i = 0; i < hallazgos.size(); i++) {

                    if (lineArgumentDTO.getLineNumber() == hallazgos.get(i).getNumeroLinea().longValue()) {
                        errorLinea = true;
                        break;
                    }

                }

                if (!errorLinea) {
                    retiroCandidatoDTO = crearRetiroCandidatoDTO(lineArgumentDTO);

                    ((List<RetiroCandidatoDTO>) lineArgumentDTO.getContext().get(CamposArchivoConstants.LISTA_CANDIDATOS))
                            .add(retiroCandidatoDTO);
                }


            } catch (Exception e) {

                ((List<RetiroCandidatoDTO>) lineArgumentDTO.getContext().get(CamposArchivoConstants.LISTA_CANDIDATOS))
                        .add(retiroCandidatoDTO);
            }
        }

    }

    @Override
    public void setRollback(EntityManager em) throws FileProcessingException {
        // TODO Auto-generated method stub

    }

    /**
     * Metodo que crea un retiro del tercer pagador candidato para ser
     * comparado posteriormente con los registros de la base de datos.
     * @param lineArgumentDTO:
     *        Contiene un map con cada campo de cada linea del archivo
     * @return DTO del retiro candidato del tercer pagador.
     */
    private RetiroCandidatoDTO crearRetiroCandidatoDTO(LineArgumentDTO lineArgumentDTO) {

        Map<String, Object> line = lineArgumentDTO.getLineValues();

        String idTransaccionTerceroPagador = (String) line.get(CamposArchivoConstants.IDENTIFICACION_TRANSACCION_TERCERO_PAGADOR);

        TipoIdentificacionEnum tipoIdAdministradorSubsidio = TipoIdentificacionEnum
                .obtenerTiposIdentificacionPILAEnum(line.get(CamposArchivoConstants.TIPO_IDENTIFICACION_ADMINISTRADOR_SUBSIDIO).toString());

        String numeroIdAdministradorSubsidio = (String) line.get(CamposArchivoConstants.NUMERO_IDENTIFICACION_ADMINISTRADOR_SUBSIDIO);

        BigDecimal valorRealTransaccion = (BigDecimal) line.get(CamposArchivoConstants.VALOR_REAL_TRANSACCION);

        Date fechaTransaccion = (Date) line.get(CamposArchivoConstants.FECHA_TRANSACCION);

        String horaTransaccion = (String) line.get(CamposArchivoConstants.HORA_TRANSACCION);

        String departamento = (String) line.get(CamposArchivoConstants.DEPARTAMENTO);

        String municipio = (String) line.get(CamposArchivoConstants.MUNICIPIO);

        Character tipoSubsidio = (Character) line.get(CamposArchivoConstants.TIPO_SUBSIDIO);

        RetiroCandidatoDTO retiroCandidatoDTO = new RetiroCandidatoDTO();

        retiroCandidatoDTO.setIdentificacionTransaccionTerceroPagador(idTransaccionTerceroPagador);
        retiroCandidatoDTO.setTipoIdentificacionAdministradorSubsidio(tipoIdAdministradorSubsidio);
        retiroCandidatoDTO.setNumeroIdentificacionAdministradorSubsidio(numeroIdAdministradorSubsidio);
        retiroCandidatoDTO.setValorRealTransaccion(valorRealTransaccion);
        retiroCandidatoDTO.setFechaTransaccion(fechaTransaccion);
        retiroCandidatoDTO.setHoraTransaccion(horaTransaccion);
        retiroCandidatoDTO.setDepartamento(departamento);
        retiroCandidatoDTO.setMunicipio(municipio);
        retiroCandidatoDTO.setTipoSubsidio(tipoSubsidio);

        return retiroCandidatoDTO;
    }

}
