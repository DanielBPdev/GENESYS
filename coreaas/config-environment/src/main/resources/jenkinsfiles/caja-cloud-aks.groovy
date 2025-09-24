node {
    stage('descarga-codigo') {
        git branch: '${branch}', url: '${urlgit}'
    }    

    stage('imagen wildfly swarm ee') {
        dir(pwd()+'/coreaas/containers/wildfly-swarm-ee') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'az login -u ${azurelogin} -p ${azurepassword}'
                sh 'az aks get-credentials --resource-group ${resourcegroup} --name ${clusterkubernetes}'
                //Se ejecuta kubectl para actualizar la información del token de autorización para comunicación con kubernetes
                sh 'kubectl get pods'
                sh 'mvn clean package -U -Pfabric8 -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  -Dnombre.usuario=${nombreusuario} -Dpila.database=${piladatabase} -Dsubsidio.database=${subsidiodatabase} -Ddefault.schema=${defaultschema} -Durl.core=${urlcore} -Duser.core=${usuarioConCore} -Dpassword.core=${passwordcore} -Durl.aud=${urlaud} -Duser.aud=${useraud} -Dpassword.aud=${passwordaud} -Durl.security=${urlsecurity} -Duser.security=${usersecurity} -Dpassword.security=${passwordsecurity} -Durl.pila=${urlpila} -Duser.pila=${userpila} -Dpassword.pila=${passwordpila} -Durl.subsidio=${urlsubsidio} -Duser.subsidio=${usersubsidio} -Dpassword.subsidio=${passwordsubsidio} -Durl.reportes=${urlreportes} -Duser.reportes=${userreportes} -Dpassword.reportes=${passwordreportes} -Durl.bpms=${urlbpms} -Duser.bpms=${userbpms} -Dpassword.bpms=${passwordbpms}'
                sh 'mvn fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}"'
            }
        }
    }

    stage('imagen wildfly swarm messaging') {
        dir(pwd()+'/coreaas/containers/wildfly-swarm-messaging') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'az login -u ${azurelogin} -p ${azurepassword}'
                sh 'az aks get-credentials --resource-group ${resourcegroup} --name ${clusterkubernetes}'
                //Se ejecuta kubectl para actualizar la información del token de autorización para comunicación con kubernetes
                sh 'kubectl get pods'
                sh 'mvn clean package -U -Pfabric8 -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  -Dnombre.usuario=${nombreusuario} -Dpila.database=${piladatabase} -Dsubsidio.database=${subsidiodatabase} -Ddefault.schema=${defaultschema} -Durl.core=${urlcore} -Duser.core=${usuarioConCore} -Dpassword.core=${passwordcore} -Durl.aud=${urlaud} -Duser.aud=${useraud} -Dpassword.aud=${passwordaud} -Durl.security=${urlsecurity} -Duser.security=${usersecurity} -Dpassword.security=${passwordsecurity} -Durl.pila=${urlpila} -Duser.pila=${userpila} -Dpassword.pila=${passwordpila} -Durl.subsidio=${urlsubsidio} -Duser.subsidio=${usersubsidio} -Dpassword.subsidio=${passwordsubsidio} -Durl.reportes=${urlreportes} -Duser.reportes=${userreportes} -Dpassword.reportes=${passwordreportes} -Durl.bpms=${urlbpms} -Duser.bpms=${userbpms} -Dpassword.bpms=${passwordbpms}'
                sh 'mvn fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}"'
            }
        }
    }

    stage('imagen wildfly swarm simple') {
        dir(pwd()+'/coreaas/containers/wildfly-swarm-simple') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'az login -u ${azurelogin} -p ${azurepassword}'
                sh 'az aks get-credentials --resource-group ${resourcegroup} --name ${clusterkubernetes}'
                //Se ejecuta kubectl para actualizar la información del token de autorización para comunicación con kubernetes
                sh 'kubectl get pods'
                sh 'mvn clean package -U -Pfabric8 -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  -Dnombre.usuario=${nombreusuario} -Dpila.database=${piladatabase} -Dsubsidio.database=${subsidiodatabase} -Ddefault.schema=${defaultschema} -Durl.core=${urlcore} -Duser.core=${usuarioConCore} -Dpassword.core=${passwordcore} -Durl.aud=${urlaud} -Duser.aud=${useraud} -Dpassword.aud=${passwordaud} -Durl.security=${urlsecurity} -Duser.security=${usersecurity} -Dpassword.security=${passwordsecurity} -Durl.pila=${urlpila} -Duser.pila=${userpila} -Dpassword.pila=${passwordpila} -Durl.subsidio=${urlsubsidio} -Duser.subsidio=${usersubsidio} -Dpassword.subsidio=${passwordsubsidio} -Durl.reportes=${urlreportes} -Duser.reportes=${userreportes} -Dpassword.reportes=${passwordreportes} -Durl.bpms=${urlbpms} -Duser.bpms=${userbpms} -Dpassword.bpms=${passwordbpms}'
                sh 'mvn fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}"'
            }
        }
    }


    stage('despliegue infinispan') {
        dir(pwd()+'/coreaas/containers/infinispan') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'docker pull jboss/infinispan-server'
                sh 'az login -u ${azurelogin} -p ${azurepassword}'
                sh 'az aks get-credentials --resource-group ${resourcegroup} --name ${clusterkubernetes}'
                //Se ejecuta kubectl para actualizar la información del token de autorización para comunicación con kubernetes
                sh 'kubectl get pods'
                sh 'mvn clean fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}"'
                sh 'mvn clean install -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  -Ddocker.registry.user=${registryuser} "-Ddocker.registry.password=${registrykey}"'
            }
        }
    }

    stage('despliegue nginx') {
        dir(pwd()+'/coreaas/containers/nginx-plus') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'docker pull debian:stretch-slim'
                sh 'mvn clean fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}"'
                sh 'mvn clean install -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}"'
            }
        }
    }

    stage('despliegue activemq') {
        dir(pwd()+'/coreaas/containers/activemq') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn clean fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}"'
                sh 'mvn clean install -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}"'
            }
        }
    }
    /*
    stage('despliegue openoffice') {
        dir(pwd()+'/coreaas/containers/openoffice') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'docker pull debian:9'
                sh 'mvn clean fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}"'
                sh 'mvn clean install -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}"'
            }
        }
    }*/

    stage('despliegue keycloak') {
        dir(pwd()+'/coreaas/containers/keycloak') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'docker pull jboss/keycloak:2.1.0.Final'
                sh 'mvn clean fabric8:build fabric8:push -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}"'
                sh 'mvn clean install -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}" -Dkeycloak.user=${keycloakuser} "-Dkeycloak.password=${keycloakpassword}" -Dkeycloak.db.host=${keycloakdbhost} -Dkeycloak.db.name=${keycloakdbname} -Dkeycloak.db.user=${keycloakdbuser} "-Dkeycloak.db.password=${keycloakdbpassword}"'
            }
        }
    }

    stage('compilación entidades y base de datos') {
        dir(pwd()+'/coreaas') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
				sh 'mvn clean install -DdatabaseNameCore=${databaseNameCore} -Dliquibase.username=${liquibaseusername} -Ddatabase.aud=${databaseaud} -Dliquibase.password=${liquibasepassword} -Dliquibase.contexts=${liquibasecontexts} -Ddbserver.pruebas-asopagos=${dbserverpruebasasopagos} -DdatabaseName=${databaseName} -DdatabasePilaPassword=${databasePilaPassword} -DdatabaseCorePassword=${databaseCorePassword} -DusuarioConPila=${usuarioConPila} -DusuarioConCore=${usuarioConCore} -DsshUser=${sshUser} -DsshPass=${sshPass} -DSSIS.home=${SSIShome} -DSSIS.folder=${SSISfolder} -Dnombre.usuario=${nombreusuario} -Ddatabase.subsidio=${subsidiodatabase} -DdatabaseHost=${databaseHost} -Ddefault.schema=${defaultschema} -Ppruebas-asopagos,model-database'
            }
        }
    }    
    stage('compilación proyecto commons') {
        dir(pwd()+'/coreaas/commons') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn clean install'
            }
        }
    }
    stage('compilación plantillas-despliegue') {
        dir(pwd()+'/coreaas/util-develop/plantillas-despliegue') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn clean install -Durl.core=${urlcore} -Duser.core=${usuarioConCore} -Dpassword.core=${passwordcore} -Durl.aud=${urlaud} -Duser.aud=${useraud} -Dpassword.aud=${passwordaud} -Durl.security=${urlsecurity} -Duser.security=${usersecurity} -Dpassword.security=${passwordsecurity} -Durl.pila=${urlpila} -Duser.pila=${userpila} -Dpassword.pila=${passwordpila} -Durl.subsidio=${urlsubsidio} -Duser.subsidio=${usersubsidio} -Dpassword.subsidio=${passwordsubsidio} -Durl.reportes=${urlreportes} -Duser.reportes=${userreportes} -Dpassword.reportes=${passwordreportes} -Durl.bpms=${urlbpms} -Duser.bpms=${userbpms} -Dpassword.bpms=${passwordbpms}'
            }
        }
    }    
    stage('compilación servicios') {
        dir(pwd()+'/coreaas/services') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn clean package -U -Pfabric8 -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  -Dnombre.usuario=${nombreusuario} -Dpila.database=${piladatabase} -Dsubsidio.database=${subsidiodatabase} -Ddefault.schema=${defaultschema} -Durl.core=${urlcore} -Duser.core=${usuarioConCore} -Dpassword.core=${passwordcore} -Durl.aud=${urlaud} -Duser.aud=${useraud} -Dpassword.aud=${passwordaud} -Durl.security=${urlsecurity} -Duser.security=${usersecurity} -Dpassword.security=${passwordsecurity} -Durl.pila=${urlpila} -Duser.pila=${userpila} -Dpassword.pila=${passwordpila} -Durl.subsidio=${urlsubsidio} -Duser.subsidio=${usersubsidio} -Dpassword.subsidio=${passwordsubsidio} -Durl.reportes=${urlreportes} -Duser.reportes=${userreportes} -Dpassword.reportes=${passwordreportes} -Durl.bpms=${urlbpms} -Duser.bpms=${userbpms} -Dpassword.bpms=${passwordbpms}'
            }
        }
    }
    //Para que se pueda usar Docker se debe dar permiso al archivo /var/run/docker.sock
    //ejemplo: chown /var/run/docker.sock jenkins
    stage('build & push servicios') {
        dir(pwd()+'/coreaas/services') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn fabric8:build fabric8:push -Pfabric8 -Durl.core=${urlcore} -Duser.core=${usuarioConCore} -Dpassword.core=${passwordcore} -Durl.aud=${urlaud} -Duser.aud=${useraud} -Dpassword.aud=${passwordaud} -Durl.security=${urlsecurity} -Duser.security=${usersecurity} -Dpassword.security=${passwordsecurity} -Durl.pila=${urlpila} -Duser.pila=${userpila} -Dpassword.pila=${passwordpila} -Durl.subsidio=${urlsubsidio} -Duser.subsidio=${usersubsidio} -Dpassword.subsidio=${passwordsubsidio} -Durl.reportes=${urlreportes} -Duser.reportes=${userreportes} -Dpassword.reportes=${passwordreportes} -Durl.bpms=${urlbpms} -Duser.bpms=${userbpms} -Dpassword.bpms=${passwordbpms} -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}"'
            }
        }
    } 
    stage('despliegue servicios') {
        dir(pwd()+'/coreaas/services') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'az aks get-credentials --resource-group ${resourcegroup} --name ${clusterkubernetes}'
                sh 'kubectl get pods'
                sh 'mvn fabric8:resource-apply -Durl.core=${urlcore} -Duser.core=${usuarioConCore} -Dpassword.core=${passwordcore} -Durl.aud=${urlaud} -Duser.aud=${useraud} -Dpassword.aud=${passwordaud} -Durl.security=${urlsecurity} -Duser.security=${usersecurity} -Dpassword.security=${passwordsecurity} -Durl.pila=${urlpila} -Duser.pila=${userpila} -Dpassword.pila=${passwordpila} -Durl.subsidio=${urlsubsidio} -Duser.subsidio=${usersubsidio} -Dpassword.subsidio=${passwordsubsidio} -Durl.reportes=${urlreportes} -Duser.reportes=${userreportes} -Dpassword.reportes=${passwordreportes} -Durl.bpms=${urlbpms} -Duser.bpms=${userbpms} -Dpassword.bpms=${passwordbpms} -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  -Pfabric8'
            }
        }
    }   
    
	stage('compilación despliegue esb') {
        dir(pwd()+'/coreaas/esb') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'docker pull openjdk:8-jre-stretch'
                sh 'az aks get-credentials --resource-group ${resourcegroup} --name ${clusterkubernetes}'
                sh 'kubectl get pods'
                sh 'mvn clean package fabric8:build fabric8:push -Ddatabase.name=${databaseNameCore} -Ddatabase.username=${databaseusername} -DdatabaseUserPassword=${databaseUserPassword} -Dservice.port=${serviceport} -Dpila.username=${pilausername} -Dhost.ip=${dbserverpruebasasopagos} -DdatabasePilaPassword=${databasePilaPassword} -Dpila.database=${piladatabase} -Dactivemq.service.host=${activemqservicehost} -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}" -Pfabric8'
                sh 'mvn fabric8:deploy -Ddatabase.name=${databaseNameCore} -Ddatabase.username=${databaseusername} -DdatabaseUserPassword=${databaseUserPassword} -Dservice.port=${serviceport} -Dpila.username=${pilausername} -Dhost.ip=${dbserverpruebasasopagos} -DdatabasePilaPassword=${databasePilaPassword} -Dpila.database=${piladatabase} -Dactivemq.service.host=${activemqservicehost} -Ddocker.registry.name=${dockerregistry} -Ddocker.registry.user=${registryuser}  "-Ddocker.registry.password=${registrykey}" -Pfabric8'
            }
        }
    }
    stage('ejecución liquibase') {
        dir(pwd()+'/coreaas/database-resources') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn liquibase:update -Dliquibase.username=${liquibaseusername} -Ddatabase.aud=${databaseaud} -Dliquibase.password=${liquibasepassword} -Dliquibase.contexts=${liquibasecontexts} -Ddbserver.pruebas-asopagos=${dbserverpruebasasopagos} -DdatabaseName=${databaseName} -Ddatabase.subsidio=${subsidiodatabase} -DdatabaseHost=${dbserverpruebasasopagos} -Ppruebas-asopagos'
            }
        }
    }

}
