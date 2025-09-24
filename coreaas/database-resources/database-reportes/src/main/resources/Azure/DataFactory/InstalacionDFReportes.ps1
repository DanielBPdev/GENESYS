$user = "reportesasopagosdev@heinshonasopagos.onmicrosoft.com"
$pass = Get-Content "D:\Password.txt" | ConvertTo-SecureString -AsPlainText -Force
$cred = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $user, $pass
Login-AzureRmAccount -Credential $cred

$resourceGroup = "INTEGRACION_ASOPAGOS_HBT_RG"
$path = "D:\ASOPAGOS_DEV\ASOPAGOS_AAS-project\coreaas\database-resources\database-reportes\src\main\resources\Azure\DataFactory\"
$DFName = "IntegracionAsopagosHBTDF"

Set-AzureRmDataFactoryV2 -Name $DFName -Location "East US " -ResourceGroupName $resourceGroup -Force

# Connections
Set-AzureRmDataFactoryV2LinkedService -Name "core" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Connections\core.json") -Force
Set-AzureRmDataFactoryV2LinkedService -Name "core_aud" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Connections\core_aud.json") -Force
Set-AzureRmDataFactoryV2LinkedService -Name "pila" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Connections\pila.json") -Force
Set-AzureRmDataFactoryV2LinkedService -Name "reportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Connections\reportes.json") -Force
Set-AzureRmDataFactoryV2LinkedService -Name "subsidio" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Connections\subsidio.json") -Force

#Datasets
Set-AzureRmDataFactoryV2Dataset -Name "DSAuditoria"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSAuditoria.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSCore"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSCore.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSPila"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSPila.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSReportes"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportes.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSReportesAudDynamicTableDestiny"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportesAudDynamicTableDestiny.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSReportesDatoTemporalSolicitud"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportesDatoTemporalSolicitud.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSReportesPlanillaPILA"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportesPlanillaPILA.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSReportesEstadoArchivoPILA"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportesEstadoArchivoPILA.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSReportesDboDynamicTableDestiny"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportesDboDynamicTableDestiny.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSCoreAudDynamicTableDestiny"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSCoreAudDynamicTableDestiny.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSAuditoriaDynamicTableDestiny"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSAuditoriaDynamicTableDestiny.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSAudRevision"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSAudRevision.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSAudRevisionEntidad"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSAudRevisionEntidad.json") -Force
# Datasets Historicos Estados
Set-AzureRmDataFactoryV2Dataset -Name "DSReportesEstadoAfiliacionEmpleadorCaja"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportesEstadoAfiliacionEmpleadorCaja.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSReportesEstadoAfiliacionPersonaCaja"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportesEstadoAfiliacionPersonaCaja.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSReportesEstadoAfiliacionPersonaEmpresa"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportesEstadoAfiliacionPersonaEmpresa.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSReportesEstadoAfiliacionPersonaIndependiente"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportesEstadoAfiliacionPersonaIndependiente.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSReportesEstadoAfiliacionPersonaPensionado"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportesEstadoAfiliacionPersonaPensionado.json") -Force
# Datasets Categorias
Set-AzureRmDataFactoryV2Dataset -Name "DSReportesCategoriaAfiliado"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportesCategoriaAfiliado.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSReportesCategoriaBeneficiario"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSReportesCategoriaBeneficiario.json") -Force

#Pipelines
Set-AzureRmDataFactoryV2Pipeline -Name "SimularAuditoriaCore" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionAuditoria\SimularAuditoriaCore.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "SincronizarAuditoria" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionAuditoria\SincronizarAuditoria.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "CondicionalSincronizarAuditoria" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionAuditoria\CondicionalSincronizarAuditoria.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "CargarIncrementalAuditoriaCore" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionAuditoria\CargarIncrementalAuditoriaCore.json") -Force 
Set-AzureRmDataFactoryV2Pipeline -Name "CargaInicialCoreReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\InicioTenant\CargaInicialCoreReportes.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "CargaCoreMasivaReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionReportes\CargaCoreMasivaReportes.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "CargaPilaMasivaReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionReportes\CargaPilaMasivaReportes.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "SincronizarAuditoriaReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionReportes\SincronizarAuditoriaReportes.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "CargaIncrementalPila" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionReportes\CargaIncrementalPila.json") -Force 
Set-AzureRmDataFactoryV2Pipeline -Name "CargaIncrementalMasivaReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionReportes\CargaIncrementalMasivaReportes.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "ProcesarHistoricoEstados" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionReportes\ProcesarHistoricoEstados.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "ProcesarReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionReportes\ProcesarReportes.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "FlujoCompletoMigracionReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionReportes\FlujoCompletoMigracionReportes.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "MigracionReportesPeriodos" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionReportes\MigracionReportesPeriodos.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "ActualizarStaging" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionSubsidios\ActualizarStaging.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "CargarReportesNormativos" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionReportes\CargarReportesNormativos.json") -Force 
Set-AzureRmDataFactoryV2Pipeline -Name "CargaIncrementalPila" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionReportes\CargaIncrementalPila.json") -Force

#Triggers
Stop-AzureRmDataFactoryV2Trigger -Name "TRGCoreAudToReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -Force
Set-AzureRmDataFactoryV2Trigger -Name "TRGCoreAudToReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Triggers\IntegracionReportes\TRGCoreAudToReportes.json") -Force
Start-AzureRmDataFactoryV2Trigger -Name "TRGCoreAudToReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -Force

Stop-AzureRmDataFactoryV2Trigger -Name "TRGPipelineReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -Force
Set-AzureRmDataFactoryV2Trigger -Name "TRGPipelineReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Triggers\IntegracionReportes\TRGPipelineReportes.json") -Force
Start-AzureRmDataFactoryV2Trigger -Name "TRGPipelineReportes" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -Force
Set-AzureRmDataFactoryV2Trigger -Name "TRGCargarReportesNormativos" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Triggers\IntegracionReportes\TRGCargarReportesNormativos.json") -Force
Start-AzureRmDataFactoryV2Trigger -Name "TRGCargarReportesNormativos" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -Force


$sqlserver = "asopagosintegracionhtb01"

#Para escalar BD a Standard S4
Set-AzureRmSqlDatabase -ServerName $sqlserver -Name "subsidio" -ResourceGroupName $resourceGroup -Edition Standard -RequestedServiceObjectiveName "S2"
Set-AzureRmSqlDatabase -ServerName $sqlserver -Name "reportes" -ResourceGroupName $resourceGroup -Edition Standard -RequestedServiceObjectiveName "S2"
Set-AzureRmSqlDatabase -ServerName $sqlserver -Name "core_aud" -ResourceGroupName $resourceGroup -Edition Standard -RequestedServiceObjectiveName "S2"
Set-AzureRmSqlDatabase -ServerName $sqlserver -Name "core" -ResourceGroupName $resourceGroup -Edition Standard -RequestedServiceObjectiveName "S2"
Set-AzureRmSqlDatabase -ServerName $sqlserver -Name "pila" -ResourceGroupName $resourceGroup -Edition Standard -RequestedServiceObjectiveName "S2"

#Ejecutar Pipeline CargaCoreMasivaReportes
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName CargaCoreMasivaReportes
$RunId

#Ejecutar Pipeline CargaPilaMasivaReportes
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName CargaPilaMasivaReportes
$RunId

#Ejecutar Pipeline CargaIncrementalMasivaReportes
#$hash = @{FechaRevision = "20171130"}
#$hash = @{FechaRevision = "20171231"}
#$hash = @{FechaRevision = "20180130"}
#$hash = @{FechaRevision = "20180228"}
#$hash = @{FechaRevision = "20180331"}
#$hash = @{FechaRevision = "20180430"}
#$hash = @{FechaRevision = "20180531"}
#$hash = @{FechaRevision = "20181231"}
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName CargaIncrementalMasivaReportes -Parameter $hash
$RunId

#Ejecutar Pipeline CargaInicialCoreReportes - tablas sin auditoria
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName CargaInicialCoreReportes
$RunId

#Ejecutar Pipeline ProcesarReportes
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName ProcesarReportes
$RunId


#Ejecutar Pipeline FlujoCompletoMigracionReportes
#$hash = @{FechaRevision = "20190131"}
#$hash = @{FechaRevision = "20190228"}
#$hash = @{FechaRevision = "20190331"}
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName FlujoCompletoMigracionReportes -Parameter $hash
$RunId

#Ejecutar Pipeline MigracionReportesPeriodos
$array = @{periodo = "20190131"},@{periodo = "20190228"},@{periodo = "20190331"}
$hash = @{periodos = $array}
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName MigracionReportesPeriodos -Parameter $hash
$RunId


#Ejecutar Pipeline CargarIncrementalAuditoriaCore
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName CargarIncrementalAuditoriaCore



#Para monitorear ejecucion de $runId
#$RunId = "B0AE85EC-BBA2-4A84-9FFC-54AB9944953A".ToLower()
$exec = Get-AzureRmDataFactoryV2PipelineRun -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineRunId $RunId
$exec.Status
$exec

#Para escalar BD a Basic

Set-AzureRmSqlDatabase -ServerName $sqlserver -Name "subsidio" -ResourceGroupName $resourceGroup -Edition Basic
Set-AzureRmSqlDatabase -ServerName $sqlserver -Name "reportes" -ResourceGroupName $resourceGroup -Edition Basic
Set-AzureRmSqlDatabase -ServerName $sqlserver -Name "core" -ResourceGroupName $resourceGroup -Edition Basic
Set-AzureRmSqlDatabase -ServerName $sqlserver -Name "core_aud" -ResourceGroupName $resourceGroup -Edition Basic
Set-AzureRmSqlDatabase -ServerName $sqlserver -Name "pila" -ResourceGroupName $resourceGroup -Edition Basic

Get-AzureRmSqlDatabase -ServerName $sqlserver -ResourceGroupName $resourceGroup -DatabaseName core_aud
Get-AzureRmSqlDatabase -ServerName $sqlserver -ResourceGroupName $resourceGroup -DatabaseName reportes

Get-AzureRmDataFactoryActivityWindow -ResourceGroupName $resourceGroup -DataFactoryName $DFName


# pruebas
$array = @{periodo = "20131231"},@{periodo = "20141231"},@{periodo = "20151231"}
$hash = @{periodos = $array}
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName MigracionReportesPeriodos -Parameter $hash
$RunId

# primero años 

#luego meses 

$array = @{periodo = "20180630"},@{periodo = "20180731"},@{periodo = "20180831"},@{periodo = "20180930"},@{periodo = "20181031"},@{periodo = "20181130"},@{periodo = "20181231"}
$hash = @{periodos = $array}
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName MigracionReportesPeriodos -Parameter $hash
$RunId

# luego enero 

$array = @{periodo = "20190131"},@{periodo = "20190228"},@{periodo = "20190331"},@{periodo = "20190405"}
$hash = @{periodos = $array}
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName MigracionReportesPeriodos -Parameter $hash
$RunId


# staging

$hash = @{migracion = $true}
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName ActualizarStaging -Parameter $hash
$RunId


$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName ActualizarStaging