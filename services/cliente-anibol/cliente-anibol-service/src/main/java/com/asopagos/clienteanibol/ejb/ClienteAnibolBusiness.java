package com.asopagos.clienteanibol.ejb;

import java.util.List;
import javax.ws.rs.QueryParam;
import com.asopagos.clienteanibol.dto.ResultadoAnibolDTO;
import com.asopagos.clienteanibol.dto.ResultadoProcesamientoDTO;
import com.asopagos.clienteanibol.dto.SaldoTarjetaDTO;
import com.asopagos.clienteanibol.dto.TarjetaDTO;
import com.asopagos.clienteanibol.service.ClienteAnibolService;
import com.asopagos.clienteanibol.dto.ResultadoDispersionAnibolDTO;

public class ClienteAnibolBusiness implements ClienteAnibolService{

    @Override
    public TarjetaDTO consultarTarjetaActiva(String tipoId, String id) {
        TarjetaDTO tarjetaDTO = new TarjetaDTO();
        tarjetaDTO.setNumeroTarjeta("12345678");
        return tarjetaDTO;
    }

    @Override
    public ResultadoAnibolDTO abonarSaldoTarjetas(List<SaldoTarjetaDTO> saldoTarjetasDTO) {
        ResultadoAnibolDTO resultadoAnibolDTO = new ResultadoAnibolDTO();
        resultadoAnibolDTO.setExitoso(true);
        return resultadoAnibolDTO;
    }

    @Override
    public ResultadoAnibolDTO descontarSaldoTarjetas(List<SaldoTarjetaDTO> saldoTarjetasDTO) {
        ResultadoAnibolDTO resultadoAnibolDTO = new ResultadoAnibolDTO();
        resultadoAnibolDTO.setExitoso(true);
        return resultadoAnibolDTO;
    }
    
    @Override
    public List<ResultadoProcesamientoDTO> consultarEstadoProcesamiento(String idProceso) {
        return null;
    }

    @Override
    public void consultarEstadoProcesamientoPrescripcion(String idProceso) {
    }

    @Override
    public ResultadoDispersionAnibolDTO consultarEstadoProcesamientoV2(String idProceso){
        return null;
    }

}
