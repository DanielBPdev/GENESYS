node {
    stage('descarga-codigo') {
        git branch: '${branch}', url: '${urlgit}'
    }    

	stage('compilación despliegue esb') {
        dir(pwd()+'/coreaas/esb') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'docker pull fabric8/java-alpine-openjdk8-jre' 
                sh 'docker pull openjdk:8-jre-stretch'
                sh 'docker pull fabric8/java-alpine-openjdk8-jre:1.4.0'
                sh 'gcloud container clusters get-credentials ${clusterkubernetes} --zone=${clusterlocation} --project=${kubernetesproject}'
                sh 'kubectl get pods'
                sh 'mvn clean package fabric8:build fabric8:push -Dseven.endpoint.rcnmcont=${endpointsevenrcnmcont} -Dseven.endpoint.riewssec.faclien=${endpointsevenfaclien} -Dseven.endpoint.riewssec.popvdor=${endpointsevenpopvdor} -Dseven.endpoint.riewssec.gnterce=${endpointsevengnterce} -Dseven.endpoint.rpocxpag=${endpointsevenrpocxpag} -Dseven.endpoint.rtsconsd=${endpointsevenrtsconsd} -Dseven.endpoint.protocol=${endpointsevenprotocol} -Danibol.endpoint=${anibolendpoint} -Danibol.endpoint.protocol=${endpointanibolprotocol} -Dzenith.endpoint.protocol=${protocolozenith} -Dactivemqip=${activemqip} -Dactivemqport=${activemqport} -Dactivemquser=${activemquser} -Dactivemqpassword=${activemqpassword} -Decm.endpoint.user=${ecmuser} -Decm.endpoint.password=${ecmpassword} -Decm.endpoint.operationNamespace=${ecmoperationnamespace} -Decm.endpoint.url=${ecmurl} -Ddatabase.urlcore=${urlcore} -Ddatabase.usercore=${usercore} -Ddatabase.passwordcore=${passwordcore} -Ddatabase.name=${databaseNameCore} -Ddatabase.username=${databaseusername} -DdatabaseUserPassword=${databaseUserPassword} -Dservice.port=${serviceport} -Dpila.username=${pilausername} -Dhost.ip=${dbserverpruebasasopagos} -DdatabasePilaPassword=${databasePilaPassword} -Dpila.database=${piladatabase} -Dactivemq.service.host=${activemqservicehost} -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}" -Pseven-fabric8'
                sh 'mvn fabric8:deploy -Dseven.endpoint.rcnmcont=${endpointsevenrcnmcont} -Dseven.endpoint.riewssec.faclien=${endpointsevenfaclien} -Dseven.endpoint.riewssec.popvdor=${endpointsevenpopvdor} -Dseven.endpoint.riewssec.gnterce=${endpointsevengnterce} -Dseven.endpoint.rpocxpag=${endpointsevenrpocxpag} -Dseven.endpoint.rtsconsd=${endpointsevenrtsconsd} -Dseven.endpoint.protocol=${endpointsevenprotocol} -Dzenith.endpoint.protocol=${protocolozenith} -Dactivemqip=${activemqip} -Dactivemqport=${activemqport} -Dactivemquser=${activemquser} -Dactivemqpassword=${activemqpassword} -Decm.endpoint.user=${ecmuser} -Decm.endpoint.password=${ecmpassword} -Decm.endpoint.operationNamespace=${ecmoperationnamespace} -Decm.endpoint.url=${ecmurl} -Ddatabase.urlcore=${urlcore} -Ddatabase.usercore=${usercore} -Ddatabase.passwordcore=${passwordcore} -Dtoken.endpoint=${tokenendpoint} -Danibol.endpoint=${anibolendpoint} -Danibol.endpoint.protocol=${endpointanibolprotocol} -Dzenith.endpoint.datosBeneficiarioSubsidio=${zenithbeneficiario} -Ddatabase.name=${databaseNameCore} -Dzenith.endpoint.datoSubsidio=${zenithsubsidio} -Dzenith.endpoint.confirmacion=${zenithconfirmacion} -Ddatabase.username=${databaseusername} -DdatabaseUserPassword=${databaseUserPassword} -Dservice.port=${serviceport} -Dpila.username=${pilausername} -Dhost.ip=${dbserverpruebasasopagos} -DdatabasePilaPassword=${databasePilaPassword} -Dpila.database=${piladatabase} -Dactivemq.service.host=${activemqservicehost} -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}" -Pseven-fabric8'
            }
        }
    }
}
