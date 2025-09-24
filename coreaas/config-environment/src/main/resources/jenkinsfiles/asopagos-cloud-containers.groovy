node {
    stage('descarga-codigo') {
        git branch: '${branch}', url: '${urlgit}'
    }    

    stage('conexión a cluster kubernetes') {
        sh 'gcloud container clusters get-credentials ${clusterkubernetes} --zone=${clusterlocation} --project=${kubernetesproject}'
        sh 'kubectl get pods'
    }
    
    
    stage('despliegue nginx') {
        dir(pwd()+'/coreaas/containers/nginx-plus-asopagos') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'docker pull debian:stretch-slim'
                sh 'mvn clean fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}"'
                sh 'mvn clean install -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}"'
            }
        }
    }

    stage('despliegue nginx gateway') {
        dir(pwd()+'/coreaas/containers/nginx-plus-gateway') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'docker pull debian:stretch-slim'
                sh 'mvn clean fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}"'
                sh 'mvn clean install -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}" -Dnginx.ip.comfaboy=${nginxcomfaboy} -Dnginx.ip.comfahuila=${nginxcomfahuila} -Dnginx.ip.asopagos=${nginxasopagos}'
            }
        }
    }

    stage('despliegue activemq general') {
        dir(pwd()+'/coreaas/containers/activemq') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn clean fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}"'
                sh 'mvn clean install -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}"'
            }
        }
    }

    stage('despliegue activemq general') {
        dir(pwd()+'/coreaas/containers/activemq-asopagos') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn clean fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}"'
                sh 'mvn clean install -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}"'
            }
        }
    }

    stage('despliegue keycloak') {
        dir(pwd()+'/coreaas/containers/keycloak') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'docker pull jboss/keycloak:2.1.0.Final'
                sh 'mvn clean fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}"'
                sh 'mvn clean install -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}" -Dkeycloak.user=${keycloakuser} "-Dkeycloak.password=${keycloakpassword}" -Dkeycloak.db.host=${keycloakdbhost} -Dkeycloak.db.name=${keycloakdbname} -Dkeycloak.db.user=${keycloakdbuser} "-Dkeycloak.db.password=${keycloakdbpassword}"'
            }
        }
    }
}