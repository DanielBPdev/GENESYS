package com.asopagos.usuarios.service;

import javax.ejb.Local;
import com.asopagos.entidades.seguridad.ReferenciaToken;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

@Local
public interface IReferenciaTokenPersistenceServices {
	public void persistir(ReferenciaToken referenciaToken);
	public ReferenciaToken actualizarReferenciaToken(ReferenciaToken referenciaTokens);
	public void borrarReferenciaToken(Long id);
	public ReferenciaToken buscarReferenciaToken(Long id);
	public ReferenciaToken buscarReferenciaToken(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,Short digitoVerificacion);
    public ReferenciaToken buscarReferenciaToken(String sessionId);
}
