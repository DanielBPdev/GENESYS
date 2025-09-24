CREATE OR ALTER   PROCEDURE [dbo].[ASP_JsonPersona]  
(  
 @personas varchar(max)  
)  
AS  
BEGIN  
  
  
  declare @perPed varchar(max)  
  
  ;with personas as (  
  select *  
  from openjson(@personas)   
  with (pertipoId varchar(25) '$.tipoIdentificacion', numId varchar(25) '$.numeroIdentificacion'))  
  select @perPed = (  
  select 
  p.perTipoIdentificacion as tipoIdentificacion,
  p.perNumeroIdentificacion as numeroIdentificacion,
  p.perPrimerNombre as primerNombre,
  p.perSegundoNombre as segundoNombre,
  p.perPrimerApellido as primerApellido,
  p.perSegundoApellido as segundoApellido,
  p.perId as idPersona,
  CAST(DATEDIFF(SECOND, '1970-01-01 00:00:00', pd.pedFechaNacimiento) AS BIGINT) * 1000 as fechaNacimiento,
  pd.pedFallecido as fallecido,
  p.perDigitoVerificacion as digitoVerificacion,
  p.perRazonSocial as razonSocial,
  pd.pedEstadoCivil as estadoCivil,
  pd.pedFechaExpedicionDocumento as fechaExpedicionDocumento,
  pd.pedGenero as genero,
  pd.pedOcupacionProfesion as idOcupacionProfesion,
  pd.pedNivelEducativo as nivelEducativo,
  pd.pedCabezaHogar as cabezaHogar,
  pd.pedHabitaCasaPropia as habitaCasaPropia,
  pd.pedAutorizaUsoDatosPersonales as autorizaUsoDatosPersonales,
  pd.pedResideSectorRural as resideSectorRural,
  pd.pedGradoAcademico as gradoAcademico,
  pd.pedFechaFallecido as fechaFallecido,
  pd.pedBeneficiarioSubsidio as beneficiarioSubsidio,
  pd.pedFechaDefuncion as fechaDefuncion,
  pd.pedEstudianteTrabajoDesarrolloHumano as estudianteTrabajoDesarrolloHumano,
  pd.pedPersonaPadre as idPersonaPadre,
  pd.pedPersonaMadre as idPersonaMadre,
  pd.pedOrientacionSexual as orientacionSexual,
  pd.pedPertenenciaEtnica as pertenenciaEtnica,
  pd.pedFactorVulnerabilidad as factorVulnerabilidad,
  pd.pedPaisResidencia as idPaisResidencia,
  ubiAutorizacionEnvioEmail as autorizacionEnvioEmail,
  ubiId as 'ubicacionDTO.idUbicacion',
  ubiMunicipio as 'ubicacionDTO.idMunicipio',
  ubiDireccionFisica as 'ubicacionDTO.direccion',
  ubiCodigoPostal as 'ubicacionDTO.codigoPostal',
  ubiTelefonoFijo as 'ubicacionDTO.telefonoFijo',
  ubiIndicativoTelFijo as 'ubicacionDTO.indicativoTelefonoFijo',
  ubiTelefonoCelular as 'ubicacionDTO.telefonoCelular',
  ubiEmail as 'ubicacionDTO.correoElectronico',
  ubiAutorizacionEnvioEmail as 'ubicacionDTO.autorizacionEnvioEmail',
  ubiDescripcionIndicacion as 'ubicacionDTO.descripcionIndicacion',
  ubiSectorUbicacion as 'ubicacionDTO.sectorUbicacion',
  ubiEmailSecundario as 'ubicacionDTO.correoElectronicoSecundario'
  from personas as pa  
  inner join dbo.Persona as p on p.perTipoIdentificacion = pa.pertipoId and pa.numId = p.perNumeroIdentificacion  
  inner join dbo.PersonaDetalle as pd on p.perId = pd.pedPersona  
   inner join Ubicacion on  ubiId = p.perUbicacionPrincipal

  for json path,  include_null_values)  
    
  select @perPed
END