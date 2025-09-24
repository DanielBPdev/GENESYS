package com.asopagos.aportes.masivos.service.business.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ws.rs.core.Context;

import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.aportes.dto.ConsultaPresenciaNovedadesDTO;
import com.asopagos.aportes.dto.CuentaAporteDTO;
import com.asopagos.aportes.dto.DatosComunicadoPlanillaDTO;
import com.asopagos.aportes.dto.InformacionPlanillasRegistrarProcesarDTO;
import com.asopagos.aportes.dto.NovedadesProcesoAportesDTO;
import com.asopagos.aportes.masivos.dto.ArchivoAporteMasivoDTO;
import com.asopagos.aportes.masivos.dto.ArchivoDevolucionDTO;
import com.asopagos.aportes.masivos.dto.ResultadoArchivoAporteDTO;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;
import com.asopagos.dto.modelo.TemAporteActualizadoModeloDTO;
import com.asopagos.entidades.pila.procesamiento.IndicePlanilla;
import com.asopagos.entidades.pila.temporal.TemNovedad;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.entidades.pila.masivos.*;
import com.asopagos.dto.*;
import com.asopagos.aportes.masivos.dto.ResultadoValidacionAporteDTO;
import com.asopagos.dto.aportes.CorreccionAportanteDTO;
import com.asopagos.aportes.masivos.dto.ReporteDevolucionDetallado;
import com.asopagos.aportes.masivos.dto.ReporteDevolucionesSimulado;
import com.asopagos.aportes.masivos.dto.ReporteRecaudoSimulado;



/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de
 * información en el modelo de datos de PILA <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="Andres Rey:andres.rey@asopagos.com"> Andres Rey</a>
 */
@Local
public interface IConsultasModeloPila {

    public String prueba();
    
    /**
	 * Método encargado de la consulta de índices de planilla PILA a partir de
	 * un listado de IDs
	 * 
	 * @param idsPlanilla
	 * @return
	 */
	public List<IndicePlanillaModeloDTO> consultarIndicesPlanillaPorIds(List<Long> idsPlanilla);


	public void procesarArchivoAportes(ResultadoArchivoAporteDTO resultadoArchivo,Long idSolicitud, String numeroRadicado, UserDTO userDTO);

	public void procesarArchivoDevolucion(ArchivoDevolucionDTO resultadoArchivo,Long solicitud,String numeroRadicado, UserDTO userDTO);

	public MasivoArchivo guardarArchivoMasivo(MasivoArchivo datosArchivo, UserDTO userDTO);
	public List<MasivoArchivo> consultarArchivoAporte();

	public MasivoArchivo consultarArchivoMasivo(Long idArchivoMasivo);
	
	
	public void crearPersonaAporteMasivo();

	


	public void guardarTemporalMasivo(ResultadoArchivoAporteDTO resultadoArchivo);

	public void simularAporteMasivo(String nombreArchivo);

	public List<Object> finalizarAporteMasivo(String nombreArchivo);

	public void eliminarArchivoMasivo(Long idArchivoMasivo);

	public List<MasivoSimulado> consultarRecaudoSimulado(Long idSolicitud);

	public Long consultarArchivoEnProcesoAportes();

	public void actualizarSolicitudMasiva(MasivoArchivo datosArchivo);

	public void actualizarArchivoMasivo(Long idArchivomasivo, String estado);

	public void actualizarArchivoMasivo(Long idArchivoMasivo, MasivoArchivo archivoMasivo);

	public void persistirDetallesArchivoAporte(Long idMasivoArchivo, ResultadoValidacionAporteDTO aporte);

	public MasivoArchivo consultarArchivoMasivoPorECM(String ecmIdentificador);

	public CorreccionAportanteDTO popularCorreccionAportante(CorreccionAportanteDTO correccionAportanteDTO);
	
	public List<AnalisisDevolucionDTO> consultarAportesADevolver(String numeroRadicado);

	public List<AnalisisDevolucionDTO> consultarAportesSimuladosDevolucion(String numeroRadicado);

	public void consultarDevolucionMasivoGeneral(String idSolicitud);

	public void consultarDevolucionMasivoDetallado(String idSolicitud);

	public void simularDevolucionMasivoGeneral(String numeroRadicado);

	public void crearSolicitudCascadaDevolucion(String numeroRadicado);

	public MasivoGeneral consultarMasivoGeneral(Long solicitud, String numeroIdentificacion);

	public List<ReporteDevolucionDetallado> getReporteDevolucionesDetalle(String numeroRadicado);
    
	public MasivoArchivo consultarArchivoMasivoPorRadicado(String numeroRadicado);

	public Object[] consultarMontosValoresRecaudoAportesMasivos(String idArchivo, String numeroArchivo);

	public List<ReporteDevolucionesSimulado> consultarDevolucionesSimuladoReporte(String numeroRadicacion);

	public List<ReporteRecaudoSimulado> consultarRecaudoSimuladoReporte(Long idSolicitud);

}
