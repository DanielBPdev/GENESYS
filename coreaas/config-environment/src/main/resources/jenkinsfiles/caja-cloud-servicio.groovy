node {

    stage('descarga-codigo') {
        git branch: '${branch}', url: '${urlgit}'
    }   
    
    stage('creacion configuracion') {
        dir(pwd()+'/coreaas/configmaps') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'gcloud container clusters get-credentials ${clusterkubernetes} --zone=${clusterlocation} --project=${kubernetesproject}'
                sh 'kubectl get pods'
                sh 'mvn -T 2C clean package fabric8:resource-apply -DskipTests -Durl.core=${urlcore} -Duser.core=${usuarioConCore} -Dpassword.core=${passwordcore} -Durl.aud=${urlaud} -Duser.aud=${useraud} -Dpassword.aud=${passwordaud} -Durl.security=${urlsecurity} -Duser.security=${usersecurity} -Dpassword.security=${passwordsecurity} -Durl.pila=${urlpila} -Duser.pila=${userpila} -Dpassword.pila=${passwordpila} -Durl.subsidio=${urlsubsidio} -Duser.subsidio=${usersubsidio} -Dpassword.subsidio=${passwordsubsidio} -Durl.reportes=${urlreportes} -Duser.reportes=${userreportes} -Dpassword.reportes=${passwordreportes} -Durl.bpms=${urlbpms} -Duser.bpms=${userbpms} -Dpassword.bpms=${passwordbpms} -Dactivemq.ip=${activemqip} -Dccf.clientId=${ccfclientId} -Ddocker.registry.name=${dockerregistry} -Dproperty.mdb.mails.maxsessions=${mailsMaxSessions} -Dccf.codigo=${ccfcodigo}'
            }
        }
    }
   
    stage('compilación entidades y base de datos') {
        dir(pwd()+'/coreaas') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
				sh 'mvn -T 1C clean install -DskipTests -DdatabaseNameCore=${databaseNameCore} -Dliquibase.username=${liquibaseusername} -Ddatabase.aud=${databaseaud} -Dliquibase.password=${liquibasepassword} -Dliquibase.contexts=${liquibasecontexts} -Ddbserver.pruebas-asopagos=${dbserverpruebasasopagos} -DdatabaseName=${databaseName} -DdatabasePilaPassword=${databasePilaPassword} -DdatabaseCorePassword=${databaseCorePassword} -DusuarioConPila=${usuarioConPila} -DusuarioConCore=${usuarioConCore} -DsshUser=${sshUser} -DsshPass=${sshPass} -DSSIS.home=${SSIShome} -DSSIS.folder=${SSISfolder} -Dnombre.usuario=${nombreusuario} -Ddatabase.subsidio=${subsidiodatabase} -DdatabaseHost=${databaseHost} -Ddefault.schema=${defaultschema} -Ppruebas-asopagos,model-database'
            }
        }
    }    
	
	stage('compilación proyecto rutine') {
        dir(pwd()+'/coreaas/rutines') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn -T 2C clean install -DskipTests'
            }
        }
    }
    
    stage('compilación proyecto commons') {
        dir(pwd()+'/coreaas/commons') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn -T 1C clean install -DskipTests'
            }
        }
    }
    
    stage('despliegue servicios') {
        dir(pwd()+'/coreaas/services') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'gcloud container clusters get-credentials ${clusterkubernetes} --zone=${clusterlocation} --project=${kubernetesproject}'
                sh 'kubectl get pods'
                sh 'mvn --projects ${proyectos} clean install fabric8:build fabric8:push fabric8:resource-apply -DskipTests -Durl.core=${urlcore} -Duser.core=${usuarioConCore} -Dpassword.core=${passwordcore} -Durl.aud=${urlaud} -Duser.aud=${useraud} -Dpassword.aud=${passwordaud} -Durl.security=${urlsecurity} -Duser.security=${usersecurity} -Dpassword.security=${passwordsecurity} -Durl.pila=${urlpila} -Duser.pila=${userpila} -Dpassword.pila=${passwordpila} -Durl.subsidio=${urlsubsidio} -Duser.subsidio=${usersubsidio} -Dpassword.subsidio=${passwordsubsidio} -Durl.reportes=${urlreportes} -Duser.reportes=${userreportes} -Dpassword.reportes=${passwordreportes} -Durl.bpms=${urlbpms} -Duser.bpms=${userbpms} -Dpassword.bpms=${passwordbpms} -Dpila.database=${piladatabase} -Dnombre.usuario=${nombreusuario} -Ddefault.schema=${defaultschema} -Ddocker.registry.name=${dockerregistry} "-Ddocker.registry.password=${registrykey}" -Pdefault,fabric8'
            }
        }
    }
}