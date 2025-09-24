package com.asopagos.afiliaciones.business.ejb;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.asopagos.afiliaciones.business.interfaces.IconsultasModeloAud;
import com.asopagos.afiliaciones.constants.NamedQueriesConstants;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * <b>Descripcion:</b> Clase que implementa las funciones para la consulta de
 * información en el modelo de datos Core_aud <br/>
 *
 * @author <a href="squintero:squintero@heinsohn.com.co"> squintero</a>
 */
@Stateless
public class ConsultasModeloAud implements IconsultasModeloAud, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ConsultasModeloAud.class);

    /**
     * Entity Manager
     */
    @PersistenceContext(unitName = "AfiliacionesAud_PU")
    private EntityManager entityManager;
	
	@Override
	public List<Object[]> consultarSalarioMinimoPeriodo(Long periodo) {
		try {
			List<Object[]> salida = entityManager.createNamedQuery(NamedQueriesConstants.BUSCAR_HISTORICO_VALOR_SALARIO_MINIMO_EN_PERIODO)
					.setParameter("periodo", periodo)
					.getResultList();
			
			return salida;
		} catch (Exception e) {
			return null;
		}
	}

}
