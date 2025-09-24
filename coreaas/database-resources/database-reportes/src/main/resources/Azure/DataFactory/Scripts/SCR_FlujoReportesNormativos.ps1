$user = "reportesasopagosdev@heinshonasopagos.onmicrosoft.com"
$pass = Get-Content "D:\Password.txt" | ConvertTo-SecureString -AsPlainText -Force
$cred = New-Object -TypeName System.Management.Automation.PSCredential -ArgumentList $user, $pass
Login-AzureRmAccount -Credential $cred

$resourceGroup = "INTEGRACION_ASOPAGOS_HBT_RG"
$path = "D:\ASOPAGOS_DEV\ASOPAGOS_AAS-project\coreaas\database-resources\database-reportes\src\main\resources\Azure\DataFactory\"
$DFName = "IntegracionAsopagosHBTDF"

Set-AzureRmDataFactoryV2 -Name $DFName -Location "East US " -ResourceGroupName $resourceGroup -Force



#Ejecutar Pipeline CargaIncrementalMasivaReportes
$hash = @{FechaRevision = "20191231"}
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName CargaIncrementalMasivaReportes -Parameter $hash
$RunId


#Ejecutar Pipeline MigracionReportes **************************************************************

$StstartDate = "20200101"
$StendDate = "20200103"

$startDate = [datetime]::parseexact($StstartDate, 'yyyyMMdd', $null)
$endDate = [datetime]::parseexact($StendDate, 'yyyyMMdd', $null)
[int]$daysToSkip = 1 

$data = @()

 
while ($startDate -le $endDate) {
    $data += $startDate.ToString("yyyyMMdd")            
    $startDate = $startDate.AddDays($daysToSkip)
}
#Write-Host $data

# ejecucion
$hash = @{listaFechas = $data}
$RunId = Invoke-AzureRmDataFactoryV2Pipeline -ResourceGroupName $resourceGroup -DataFactoryName $DFName -PipelineName MigracionReportes -Parameter $hash
$RunId

# ****************************************************************************************************************


