/**
 * 
 */
package com.asopagos.tareashumanas.client;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.jbpm.services.task.impl.model.xml.JaxbContent;
import org.kie.api.task.model.Status;
import org.kie.remote.client.jaxb.JaxbTaskSummaryListResponse;
import org.kie.services.client.serialization.jaxb.impl.process.JaxbProcessInstanceResponse;
import org.kie.services.client.serialization.jaxb.rest.JaxbGenericResponse;

/**
 * Interface con las operaciones disponibles en runtime para Jbpm
 * @author alopez
 */
@Path("rest")
public interface JBPMClient {

    @GET
    @Path("task/{taskId}")
    public org.kie.remote.jaxb.gen.Task getTask(@PathParam(value = "taskId") Long taskID);

    @GET
    @Path("task/content/{contentId}")
    public JaxbContent getContent(@PathParam(value = "contentId") Long contentId);

    @GET
    @Path("task/query")
    public JaxbTaskSummaryListResponse queryTask(@QueryParam("status") List<Status> statuses,
            @QueryParam("processInstanceId") Long processInstanceId, @QueryParam("taskOwner") String owner);

    @GET
    @Path("task/query")
    public JaxbTaskSummaryListResponse queryTask(@QueryParam("status") List<Status> statuses,
            @QueryParam("processInstanceId") Long processInstanceId);
    
    @GET
    @Path("task/query")
    public JaxbTaskSummaryListResponse queryTask(@QueryParam("status") List<Status> statuses, 
            @QueryParam("taskOwner") List<String> owner, @QueryParam("s") Integer size, @QueryParam("p") Integer page);

    @POST
    @Path("/task/{taskId}/{operation}")
    public JaxbGenericResponse doTaskOperation(@PathParam(value = "taskId") Long taskId, @PathParam("operation") String operation);

    @POST
    @Path("/runtime/{deploymentId}/process/{processDefId}/start")
    public JaxbProcessInstanceResponse startProcess(@PathParam(value = "deploymentId") String deploymentId,
            @PathParam(value = "processDefId") String processDefinitionId);

    @POST
    @Path("/runtime/{deploymentId}/process/instance/{procInstanceID}/signal")
    public JaxbGenericResponse signalProcess(@PathParam(value = "deploymentId") String deploymentId,
            @PathParam(value = "procInstanceID") Long processInstanceId, @QueryParam("signal") String signal,
            @QueryParam("event") String event);

    @POST
    @Path("/task/{taskId}/delegate")
    public void reassignTask(@PathParam(value = "taskId") Long taskID, @QueryParam("targetEntityId") String targetId);

    @POST
    @Path("/runtime/{deploymentId}/process/instance/{procInstId}/abort")
    public JaxbGenericResponse abortProcessInstance(@PathParam(value = "deploymentId") String deploymentId,
            @PathParam(value = "procInstId") Long processInstanceId);
}
