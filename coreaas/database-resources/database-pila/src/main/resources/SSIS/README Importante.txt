Luego de haber modificado la DTSX revisar que:

1. las credenciales de conexi�n a pila y core correspondan al usuario quien realiz� la modificaci�n. 
Ej: para el usuario "arocha" las conexiones para pruebas debieron ser: core_arocha y pila_arocha 
sin "guardar la contrase�a"

2. Verificar las propiedades del archivo archivo DTSX. En "seguridad", la propiedad "ProtectionLevel" debe
tener el valor "DontSaveSensitive"

3. Desde Git Bash, ubicado en "..\ASOPAGOS_AAS-project\coreaas\database-resources\database-pila\" ejecutar
el siguiente comando: 

mvn clean install -Ppila

Nota: Es fundamental ejecutar esta instrucci�n antes de publicar cambios para que, al instalar en los
ambientes remotos, la ETL quede actualizada.