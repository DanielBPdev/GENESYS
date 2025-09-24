node {
    stage('descarga-codigo') {
        git branch: '${branch}', url: '${urlgit}'
    }    
    
    stage('ejecución liquibase') {
        dir(pwd()+'/coreaas/database-resources') {
            withMaven(jdk: 'JDK1.8', maven: 'maven 3.3.9') {
                sh 'mvn -T 1C -DskipTests liquibase:update -Dliquibase.username=${liquibaseusername} -Ddatabase.aud=${databaseaud} -Dliquibase.password=${liquibasepassword} -Dliquibase.contexts=${liquibasecontexts} -Ddbserver.pruebas-asopagos=${dbserverpruebasasopagos} -DdatabaseName=${databaseName} -Ddatabase.subsidio=${subsidiodatabase} -DdatabaseHost=${dbserverpruebasasopagos} -Ppruebas-asopagos'
            }
        }
    }

}
