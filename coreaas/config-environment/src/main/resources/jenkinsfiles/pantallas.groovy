node {
   stage('descarga-codigo') {
        git branch: '${branch}', credentialsId: '289f15fb-3cb4-49d7-aacb-fb81bc587b5c', url: 'http://50.23.29.217/Heinsohn/ASOPAGOS_AAS-project.git'
    }

    stage('npm-install'){
        dir(pwd()+'/coreaas/view'){
            sh 'npm install'
        }
    }   
	
	stage('copy-gulp-fixes'){
        dir(pwd()+'/coreaas/view'){
			sh 'mkdir -p node_modules/babel-plugin-undeclared-variables-check/lib/'
            sh 'cp prototipos/gulp-fixes/babel/index.js node_modules/babel-plugin-undeclared-variables-check/lib/'
			sh 'mkdir -p node_modules/gulp-content-filter/'
			sh 'cp prototipos/gulp-fixes/gulp-content-filter/index.js node_modules/gulp-content-filter/'
        }
    }   
     
    stage('compilacion-proyectos'){
        dir(pwd()+'/coreaas'){
            dir(pwd()+'/view'){
                 withMaven(jdk: 'JDK1.8', maven: 'maven 3.5.2'){
                    sh 'mvn clean install -X -DskipTests -Dcarpeta.portlets=${carpetaportlets} -Dcarpeta.deploy=${carpetadeploy} -Ddomain.port.keycloak.hbt=${domainportkeycloakasopagos} -Durl.liferay=${urlliferay} -Durl.keycloak=${urlkeycloak} -Durl.nginx=${urlnginx} -Dliferay.server.maven=${liferayservermaven} -Ddomain.name.asopagos=${domainnameasopagos} -Ddomain.name.hbt=${domainnamehbt} -Ddomain.port.keycloak.asopagos=${domainportkeycloakasopagos} -Dambiente.liferay=${ambienteliferay} -Durl.liferay.conexion=${urlliferay} -Pliferay-google'
                }
            }
        }
    }   
    
    stage('despliegue view'){
        dir(pwd()+'/coreaas/view'){
             withMaven(jdk: 'JDK1.8', maven: 'maven 3.5.2'){
                sh 'mvn wagon:sshexec -Dambiente.liferay=${ambienteliferay} -Dcarpeta.portlets=${carpetaportlets} -Dcarpeta.deploy=${carpetadeploy} -Dliferay.server.maven=${liferayservermaven} -Durl.liferay.conexion=${urlliferay} -Pliferay-google'
            }
        }
    }
}