#Abrir powershell ISE como Administrador
#Validar version con el siguiente comando:
Get-Module -Name PowerShellGet -ListAvailable | Select-Object -Property Name,Version,Path

#Si la versión de powerShellGet es menor a 1.1.2.0, actualizatlo con el siguiente comando
Install-Module PowerShellGet -Force

#Instalar Azure Resource Manager con las opción "Yes to All"
Install-Module -Name AzureRM -Force

#El paso siguiente a este (Importar Azure Resource Manager) puede fallar si no se ejecuta lo siguiente. "Yes to All"
Set-ExecutionPolicy -ExecutionPolicy ALLSIGNED

#Importar Azure Resource Manager
Import-Module -Name AzureRM