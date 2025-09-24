package com.asopagos.usuarios.service;

import java.util.List;
import javax.ejb.Local;
import com.asopagos.entidades.seguridad.Pregunta;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;

@Local
public interface IPreguntasPersistenceServices {
	
	public Long persistirPregunta(String pregunta);
	
	public void actualizarPregunta(Pregunta pregunta);
	
	public void borrarPregunta(Long id);
	
	public String buscarPregunta(Long id);
	
	public String buscarPregunta(String pregunta);
	
	public List<Pregunta>  buscarPreguntasPorEstado(EstadoActivoInactivoEnum estado);
}
