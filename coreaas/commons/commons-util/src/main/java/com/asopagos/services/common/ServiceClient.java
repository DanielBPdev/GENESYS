/**
 * 
 */
package com.asopagos.services.common;

import java.util.UUID;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import com.asopagos.common.services.locator.ServiceLocator;
import com.asopagos.common.services.locator.ServicesLocatorFactory;
import com.asopagos.common.services.locator.datamodel.ServiceEntry;
import com.asopagos.common.services.locator.excepcion.ServiceNotFoundException;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.AsopagosException;
import com.asopagos.rest.exception.FunctionalConstraintException;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.rest.exception.RecursoNoAutorizadoException;
import com.asopagos.rest.exception.ServicioNoEncontradoException;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.rest.security.filter.AddAuditHeaders;
import com.asopagos.rest.security.filter.AddAuthenticationToken;
import com.asopagos.rest.security.filter.AddHeaderAuthenticationToken;

/**
 * Clase que crea un servicio rest deacuerdo a la configuración del archivo
 * sercvices.xml
 * 
 * @author alopez
 * 
 * @version 2.0
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 *         modificado: 22/08/2016
 *
 */
public abstract class ServiceClient {

	/** Respuesta del servicio */
	protected Response response;

	/** Token a asignar para las peticiones de los procesos WEB */
	protected String token;

	/**
	 * Identificador de la transacción. Es diferente de <code>null</code> si el
	 * microservicio es invocado por un composite.
	 */
	protected String txId;

	/**
	 * Corresponde a un identificador único dentro de la misma transacción. Es
	 * diferente de <code>null</code> si el cliente es invocado por un composite.
	 */
	protected String requestId;

	/** Método que resuleve los parametros y realiza el consumo del servicio */
	protected abstract Response invoke(WebTarget webTarget, String path);

	/** Método que obtiene el resultado de la petición */
	protected abstract void getResultData(Response response);

	/**
	 * Método que ejecuta, valida y asigna el resultado del consumo del servicio
	 * rest
	 */
	public void execute() {

		ILogger logger = LogManager.getLogger(this.getClass());
		Client client = ClientBuilder.newClient();

		// se adiciona el header de autenticación
		addAuthenticationToken(client);

		// se adiciona el id de transaccion si el servicio es invocado por un composite
		addAuditHeaders(client);

		// obtener información del archivo
		ServiceEntry entry = null;
		try {
			ServiceLocator serviceLocator = ServicesLocatorFactory.getServiceLocator();
			entry = serviceLocator.getServiceEntry(getServiceName());
			WebTarget webTarget = client.target(entry.getEndpoint());

			// se invoca el servicio
			Response response = invoke(webTarget, entry.getPath());
			this.response = response;

			AsopagosException exception = null;
			Response.Status status = Response.Status.fromStatusCode(response.getStatus());
			switch (status) {
			case OK: // 200
			case NO_CONTENT: // 204
				break;
			case BAD_REQUEST: // 400
				// cómo la invocación es de micro servicios quien lo invoque
				// debe determinar si la excepción se trata de una
				// BPMSExecutionException o una ParametroInvalidoExcepcion
				exception = new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_HTTP_BAD_REQUEST);
				break;
			case UNAUTHORIZED: // 401
				exception = new RecursoNoAutorizadoException(MensajesGeneralConstants.ERROR_HTTP_UNAUTHORIZED);
				break;
			case NOT_FOUND: // 404
				exception = new ServicioNoEncontradoException(MensajesGeneralConstants.ERROR_HTTP_NOT_FOUND);
				logger.error("Servicio no encontrado en la URL: " + entry.toString());
				break;
			case CONFLICT: // 409
				exception = new FunctionalConstraintException(MensajesGeneralConstants.ERROR_HTTP_CONFLICT);
				break;
			case INTERNAL_SERVER_ERROR: // 500
				logger.error("\n\t predecessor status:: "+status.getReasonPhrase());
				exception = new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR);
				break;
			default:
				String errorMessage = "\tRespuesta inesperada en invocación desde cliente, HTTP Response Status: "
						.concat(status.getStatusCode() + " ").concat(status.toString());
				logger.error(errorMessage);
				exception = new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_INTERNAL_SERVER_ERROR,errorMessage);
				break;
			}

			if (exception == null) {
				getResultData(response);
				client.close();
			} else {
				client.close();
				throw exception;
			}
		} catch (ServiceNotFoundException e) {
			// se controla error 404
			logger.debug("Servicio no encontrado: " + entry + "\n");
			logger.error("\tHBT: Service not found-> " + getServiceName() + "\n" + e);
			throw new TechnicalException(MensajesGeneralConstants.ERROR_HTTP_NOT_FOUND, e);
		}
	}

	/**
	 * Si el cliente es invocada por un composite se encuentra dentro del contexto
	 * de una transacción.
	 * 
	 * @param client
	 */
	private void addAuditHeaders(Client client) {
		this.requestId = UUID.randomUUID().toString();
		client.register(new AddAuditHeaders(requestId));
	}

	/**
	 * Metodo encargado de agreagr la autenticacion del token
	 * 
	 * @param client, cliente
	 */
	private void addAuthenticationToken(Client client) {
		// se adiciona el header de autenticación
		if (token == null) {
			client.register(new AddHeaderAuthenticationToken());
		} else {
			client.register(new AddAuthenticationToken(token));
		}
	}

	/**
	 * Método para obtener la respuesta del servicio
	 * 
	 * @return
	 */
	public Response getResponse() {
		return this.response;
	}

	/**
	 * Retorna el nombre simple de la clase cliente
	 * 
	 * @return
	 */
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

}