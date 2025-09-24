node {
    stage('descarga-codigo') {
        git branch: '${branch}', url: '${urlgit}'
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
                sh 'mvn clean install -Durl.core=${urlcore} -Duser.core=${usuarioConCore} -Dpassword.core=${passwordcore} -Durl.aud=${urlaud} -Duser.aud=${useraud} -Dpassword.aud=${passwordaud} -Durl.security=${urlsecurity} -Duser.security=${usersecurity} -Dpassword.security=${passwordsecurity} -Durl.pila=${urlpila} -Duser.pila=${userpila} -Dpassword.pila=${passwordpila} -Durl.subsidio=${urlsubsidio} -Duser.subsidio=${usersubsidio} -Dpassword.subsidio=${passwordsubsidio} -Durl.reportes=${urlreportes} -Duser.reportes=${userreportes} -Dpassword.reportes=${passwordreportes}'
            }
        }
    }    
    stage('compilación servicios') {
        dir(pwd()+'/coreaas/services/${servicio}') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn clean package -U -Pfabric8 -Ddocker.registry.name=${dockerregistry} -Dnombre.usuario=${nombreusuario} -Dpila.database=${piladatabase} -Dsubsidio.database=${subsidiodatabase} -Ddefault.schema=${defaultschema} -fn'
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
    //Para que se pueda usar Docker se debe dar permiso al archivo /var/run/docker.sock
    //ejemplo: chown /var/run/docker.sock jenkins
    stage('build & push servicios') {
        dir(pwd()+'/coreaas/services/${servicio}') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn fabric8:build fabric8:push -Pfabric8 -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}" -fn'
            }
        }
    } 
    stage('despliegue servicios') {
        dir(pwd()+'/coreaas/services/${servicio}') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'gcloud container clusters get-credentials ${clusterkubernetes} --zone=${clusterlocation} --project=${kubernetesproject}'
                sh 'kubectl get pods'
                sh 'mvn fabric8:deploy -Ddocker.registry.name=${dockerregistry} -Pfabric8 -fn'
            }
        }
    }

}
