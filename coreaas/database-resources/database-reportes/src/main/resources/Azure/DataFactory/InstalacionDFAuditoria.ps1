$user = "reportesasopagosdev@heinshonasopagos.onmicrosoft.com"
$pass = Get-Content "D:\Password.txt" | ConvertTo-SecureString -AsPlainText -Force
$cred = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $user, $pass
Login-AzureRmAccount -Credential $cred

#Establecer grupo de recursos creado previamente en Azure
$resourceGroup = "INTEGRACION_ASOPAGOS_HBT_RG"
#Establecer el nombre del Data Factory creado previamente en el grupo de recursos definido en el paso anterior
$DFName = "IntegracionAsopagosHBTDF"
#Definir la ruta local de los artefactos de instalación de datafactory
$path = "D:\ASOPAGOS_DEV\ASOPAGOS_AAS-project\coreaas\database-resources\database-reportes\src\main\resources\Azure\DataFactory\"

#Instalación de "Connections"
Set-AzureRmDataFactoryV2LinkedService -Name "core" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Connections\core.json") -Force
Set-AzureRmDataFactoryV2LinkedService -Name "core_aud" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Connections\core_aud.json") -Force

#Instalacion de "Datasets"
Set-AzureRmDataFactoryV2Dataset -Name "DSAuditoria"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSAuditoria.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSCore"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSCore.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSCoreAudDynamicTableDestiny"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSCoreAudDynamicTableDestiny.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSAuditoriaDynamicTableDestiny"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSAuditoriaDynamicTableDestiny.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSAudRevision"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSAudRevision.json") -Force
Set-AzureRmDataFactoryV2Dataset -Name "DSAudRevisionEntidad"  -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "Datasets\DSAudRevisionEntidad.json") -Force

#Instalacion de "Pipelines"
Set-AzureRmDataFactoryV2Pipeline -Name "SimularAuditoriaCore" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionAuditoria\SimularAuditoriaCore.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "SincronizarAuditoria" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionAuditoria\SincronizarAuditoria.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "CondicionalSincronizarAuditoria" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionAuditoria\CondicionalSincronizarAuditoria.json") -Force
Set-AzureRmDataFactoryV2Pipeline -Name "CargarIncrementalAuditoriaCore" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Pipelines\IntegracionAuditoria\CargarIncrementalAuditoriaCore.json") -Force


#Ejecutar manualmente SimularAuditoriaCore
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName SimularAuditoriaCore
$RunId

#Ejecutar manualmente CargarIncrementalAuditoriaCore
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName CargarIncrementalAuditoriaCore
$RunId

$exec = Get-AzureRmDataFactoryV2PipelineRun -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineRunId $RunId
$exec.Status
$exec

#Instalacion de Triggers
#Se elimina deshabilita y se elimina el trigger (En caso que se haya creado antes, sino, omitir estos 2 pasos)
Stop-AzureRmDataFactoryV2Trigger -Name "TRGCoreToCoreAud" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -Force
Remove-AzureRmDataFactoryV2Trigger -Name "TRGCoreToCoreAud" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -Force
#Se crea Trigger y se habilita para que se ejecute automaticamente
Set-AzureRmDataFactoryV2Trigger -Name "TRGCoreToCoreAud" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -File ($path + "\Triggers\IntegracionAuditoria\TRGCoreToCoreAud.json") -Force
Start-AzureRmDataFactoryV2Trigger -Name "TRGCoreToCoreAud" -DataFactoryName $DFName -ResourceGroupName $resourceGroup -Force
