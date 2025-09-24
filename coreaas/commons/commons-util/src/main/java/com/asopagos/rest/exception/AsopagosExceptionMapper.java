package com.asopagos.rest.exception;

import javax.persistence.PersistenceException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.NotAuthorizedException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import com.asopagos.dto.RespuestaErrorDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * <b>Descripción:</b> Clase mapeadora de las excepciones de negocio a los
 * mensajes de respuesta de los servicios REST. <br>
 * <b>Historia de Usuario:</b> Transversal <br>
 * <br>
 * <b>Genera una respuesta HTTP de acuerdo a la excepción generada</b>
 * <ul>
 * <li>Status: Excepción</li>
 * </ul>
 * <b>Familia 4xx</b>
 * <ul>
 * <li><code>Status.BAD_REQUEST(400, "Bad Request")</code>:
 * BPMSExecutionException, ParametroInvalidoExcepcion</li>
 * <li><code>Status.UNAUTHORIZED(401, "Unauthorized")</code>:
 * RecursoNoAutorizadoException</li>
 * <li><code>Status.NOT_FOUND(404, "Not Found")</code>:
 * ServicioNoEncontradoException</li>
 * <li><code>Status.CONFLICT(409, "Conflict")</code>:
 * FunctionalConstraintException</li>
 * </ul>
 * <b>Familia 5xx</b>
 * <ul>
 * <li><code>Status.INTERNAL_SERVER_ERROR(500, "Internal Server Error")</code>:
 * TechnicalException</li>
 * </ul>
 *
 * @author Sergio Briñez <sbrinez@heinsohn.com.co> <br>
 *         <b>Modificado:</b> Juan Diego Ocampo Q <jocampo@heinsohn.com.co>
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class AsopagosExceptionMapper implements ExceptionMapper<Exception> {

    ILogger logger = LogManager.getLogger(this.getClass());

    /**
     * Implementación del mensaje de respuesta según la excepción recibida
     *
     * @param exception
     * @return mensaje en formato json
     */
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(Exception exception) {

        logger.warn("Mapper Exception was call, primary exception: ", exception);

        RespuestaErrorDTO errorDTO = new RespuestaErrorDTO();
        Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR; // (500, "Internal Server Error") :
        // TechnicalException
        String mensaje = exception.getMessage();

        // Excepciones de asopagos
        if (exception instanceof BPMSExecutionException
                || (exception.getCause() != null && exception.getCause() instanceof BPMSExecutionException)) {
            httpStatus = Response.Status.BAD_REQUEST; // (400, "Bad Request")
            errorDTO.setErrorBPM(Boolean.TRUE);
        }
        else if (exception instanceof ParametroInvalidoExcepcion
                || (exception.getCause() != null && exception.getCause() instanceof ParametroInvalidoExcepcion)) {
            httpStatus = Response.Status.BAD_REQUEST; // (400, "Bad Request")
        }
        else if (exception instanceof RecursoNoAutorizadoException
                || (exception.getCause() != null && exception.getCause() instanceof RecursoNoAutorizadoException)
                || exception instanceof NotAuthorizedException) {
            httpStatus = Response.Status.UNAUTHORIZED; // (401, "Unauthorized")
        }
        else if (exception instanceof ServicioNoEncontradoException
                || (exception.getCause() != null && exception.getCause() instanceof ServicioNoEncontradoException)) {
            httpStatus = Response.Status.NOT_FOUND; // (404, "Not Found")
        }
        else if (exception instanceof FunctionalConstraintException
                || (exception.getCause() != null && exception.getCause() instanceof FunctionalConstraintException)) {
            httpStatus = Response.Status.CONFLICT; // (409, "Conflict")
        }
        else if (exception.getCause() != null && exception.getCause() instanceof PersistenceException) {
            if (exception.getCause().getCause() != null && exception.getCause().getCause() instanceof ConstraintViolationException) {
                httpStatus = Response.Status.CONFLICT; // (409, "Conflict")
                mensaje = exception.getCause().getMessage();
            }
        }
        else if (exception instanceof WebApplicationException) {
            // se extrae el estado que el mismo servidor de aplicación para peticiones HTTP
            // asigna
            WebApplicationException e = (WebApplicationException) exception;
            httpStatus = Response.Status.fromStatusCode(e.getResponse().getStatus());
        }
        else if (exception.getCause() != null && exception.getCause() instanceof WebApplicationException) {
            WebApplicationException e = (WebApplicationException) exception.getCause();
            httpStatus = Response.Status.fromStatusCode(e.getResponse().getStatus());
            mensaje = exception.getCause().getMessage();
        }

        if (exception instanceof ErrorExcepcion
                || (exception.getCause() != null && exception.getCause() instanceof ErrorExcepcion)) {
            httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
            errorDTO.setStatus(httpStatus);
            errorDTO.setMensaje(exception.getMessage().replace("com.asopagos.rest.exception.ErrorExcepcion: ", ""));
            return Response.status(httpStatus).type(MediaType.APPLICATION_JSON).entity(errorDTO).build();
        } else {
            errorDTO.setStatus(httpStatus);
            errorDTO.setMensaje(mensaje);
            if (httpStatus.equals(Response.Status.INTERNAL_SERVER_ERROR)) {
                String stackTrace = ExceptionUtils.getStackTrace(exception);
                errorDTO.setStackTrace(stackTrace);
            }
            return Response.status(httpStatus).type(MediaType.APPLICATION_JSON).entity(errorDTO).build();
        }


    }
}
