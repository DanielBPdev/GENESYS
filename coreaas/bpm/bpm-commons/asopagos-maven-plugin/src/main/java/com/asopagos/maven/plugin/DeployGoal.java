package com.asopagos.maven.plugin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
import org.kie.services.client.serialization.jaxb.impl.audit.AbstractJaxbHistoryObject;
import org.kie.services.client.serialization.jaxb.impl.audit.JaxbHistoryLogList;
import org.kie.services.client.serialization.jaxb.impl.audit.JaxbProcessInstanceLog;
import org.kie.services.client.serialization.jaxb.impl.deploy.JaxbDeploymentJobResult;
import org.kie.services.client.serialization.jaxb.impl.deploy.JaxbDeploymentUnit;
import org.kie.services.client.serialization.jaxb.impl.deploy.JaxbDeploymentUnitList;

@Mojo(name = "deploy")
public class DeployGoal extends AbstractMojo {

	@Parameter(property = "project.artifactId")
	private String artifactId;
	@Parameter(property = "project.groupId")
	private String groupId;
	@Parameter(property = "project.version")
	private String version;
	@Parameter(property = "env.JBOSS_CONTEXT")
	private String server;
	@Parameter
	private Properties connectionParams;
	@Parameter
	private String deployType;
	@Parameter(property = "env.JBOSS_HOME")
	private String jbossHome;

	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			String server = null;
			String port = null;
			String protocol = null;
			String depId = groupId + ":" + artifactId + ":" + version;
			String user = null;
			String pass = null;
			if (deployType == null || deployType.equals("PROCESS")) {
				if (connectionParams != null) {
					server = connectionParams.getProperty("host");
					port = connectionParams.getProperty("port");
					protocol = connectionParams.getProperty("protocol");
					user = connectionParams.getProperty("user");
					pass = connectionParams.getProperty("pass");
				}
				String authString = (user != null ? user : "bpmsAdmin") + ":" + (pass != null ? pass : "Asdf1234$");

				String url = (this.server != null ? this.server
						: (protocol != null ? protocol + "://" : "http://") + (server != null ? server : "localhost")
								+ (port != null ? ":" + port + "/" : ":8080/"));

				validateDeploymentExistence(authString, url, depId);
				boolean deployed = false;
				JaxbDeploymentJobResult deployResult=null;
				while (!deployed) {
					deployResult = invokeService(
							url + "business-central/rest/deployment/" + depId + "/deploy", "POST", authString,
							JaxbDeploymentJobResult.class);
					if (!deployResult.getExplanation()
							.equals("The deployment already exists and must be first undeployed!")) {
						deployed = true;
					}
				}
				getLog().info(deployResult.getExplanation());
			} else if (deployType.equals("LIB")) {
				if (jbossHome != null) {
					String jarName = artifactId + "-" + version + ".jar";
					File jar = new File("./target/" + jarName);
					File targetDir = new File(
							jbossHome + "/standalone/deployments/business-central.war/WEB-INF/lib/" + jarName);
					Files.copy(jar.toPath(), new FileOutputStream(targetDir));
					getLog().info("JAR " + jarName + " has been copied");
				}
			}
		} catch (Exception e) {
			throw new MojoExecutionException(e.getLocalizedMessage());
		}
	}

	private void validateDeploymentExistence(String authString, String url, String deploymentId) throws Exception {

		JaxbDeploymentUnitList deployments = invokeService(url + "business-central/rest/deployment/", "GET", authString,
				JaxbDeploymentUnitList.class);

		for (JaxbDeploymentUnit unit : deployments.getDeploymentUnitList()) {
			if (unit.getArtifactId().equals(artifactId) && unit.getGroupId().equals(groupId)
					&& unit.getVersion().equals(version)) {
				getLog().info("Deployment " + deploymentId + " already exists");
				JaxbHistoryLogList instances = invokeService(url + "business-central/rest/history/instances", "GET",
						authString, JaxbHistoryLogList.class);
				if (instances != null) {
					if (instances.getHistoryLogList() != null && !instances.getHistoryLogList().isEmpty()) {
						for (AbstractJaxbHistoryObject<ProcessInstanceLog> log : instances.getHistoryLogList()) {
							JaxbProcessInstanceLog instance = (JaxbProcessInstanceLog) log;
							if (instance.getStatus() == 1 && instance.getExternalId().equals(deploymentId)) {
								invokeService(url + "business-central/rest/runtime/" + deploymentId
										+ "/process/instance/" + instance.getProcessInstanceId() + "/abort", "POST",
										authString, null);
							}
						}
					}
				}
				invokeService(url + "business-central/rest/deployment/" + deploymentId + "/undeploy", "POST",
						authString, JaxbDeploymentJobResult.class);
				getLog().info("Deployment " + deploymentId + " undeployed");

				break;
			}
		}
	}

	private <T> T invokeService(String url, String method, String authString, Class<T> returnClazz) throws Exception {
		ClientRequest request = new ClientRequest(url);
		request.header("Authorization", "Basic " + DatatypeConverter.printBase64Binary(authString.getBytes("UTF-8")));
		ClientResponse<String> response = null;
		if (method.equals("GET")) {
			response = request.get(String.class);
		} else if (method.equals("POST")) {
			response = request.post(String.class);
		}
		if (returnClazz != null) {
			InputStream s = new ByteArrayInputStream(response.getEntity().getBytes());
			JAXBContext jaxbTaskContext = JAXBContext.newInstance(returnClazz);
			T realResponse = (T) jaxbTaskContext.createUnmarshaller().unmarshal(s);
			return realResponse;
		} else {
			return null;
		}
	}

}
