node {
    stage('descarga-codigo') {
        git branch: '${branch}', url: '${urlgit}'
    }    
    
    stage('despliegue infinispan') {
        dir(pwd()+'/coreaas/containers/infinispan') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'docker pull jboss/infinispan-server:9.4.11.Final'
                sh 'gcloud container clusters get-credentials ${clusterkubernetes} --zone=${clusterlocation} --project=${kubernetesproject}'
                //Se ejecuta kubectl para actualizar la información del token de autorización para comunicación con kubernetes
                sh 'kubectl get pods'
                sh 'mvn clean fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}"'
                sh 'mvn clean install -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}"'
            }
        }
    }

    stage('despliegue nginx') {
        dir(pwd()+'/coreaas/containers/nginx-plus') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'docker pull debian:stretch-slim'
                sh 'mvn clean fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}"'
                sh 'mvn clean install -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}"'
            }
        }
    }

    stage('despliegue activemq') {
        dir(pwd()+'/coreaas/containers/activemq') {
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
