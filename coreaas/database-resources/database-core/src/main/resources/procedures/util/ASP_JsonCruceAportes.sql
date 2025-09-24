CREATE OR ALTER     PROCEDURE [dbo].[ASP_JsonCruceAportes]  
(  
 @Aportes varchar(max)  
)  
AS  
BEGIN  
  SET NOCOUNT ON;
  
  declare @AportesResult varchar(max)  
  
  ;with Aportes as (  
  select *  
  from openjson(@Aportes)   
  with (TipoDoc varchar(50) '$.tipoIdentificacion', numeroDoc varchar(25) '$.numeroIdentificacion' 
  , razonSocial varchar(50) '$.nombre', tipoAportante varchar(50) '$.tipoAportante')) 
  select @AportesResult = (  
select b.numeroIdentificacion,
  b.tipoIdentificacion,
  b.nombre,
 b.tipoAportante ,
  max(b.estadoCCF) as estadoCCF,
  max( b.tipoAfiliacionCCF) as tipoAfiliacionCCF,
   max(CAST(b.fechaRetiro AS DATE)) as fechaRetiro from (
  select 
  apt.TipoDoc as [tipoIdentificacion],
  apt.numeroDoc as [numeroIdentificacion],
  apt.razonSocial as [nombre],
  apt.tipoAportante as [tipoAportante],
  ra.roaEstadoAfiliado as [estadoCCF],
   ra.roaTipoAfiliado as [tipoAfiliacionCCF],
   (case  WHEN ra.roaEstadoAfiliado is not null then ( case when (select COUNT(*) from RolAfiliado r  where  r.roaAfiliado=a.afiId and r.roaEstadoAfiliado='ACTIVO'  )=0 THEN max(ra.roaFechaRetiro) ELSE NULL END) else NULL end )  as [fechaRetiro]
  from Aportes as apt
  left join persona p on p.perTipoIdentificacion=apt.TipoDoc and p.perNumeroIdentificacion=apt.numeroDoc
  and apt.razonSocial = p.perRazonSocial
  left join dbo.AporteDetallado ad on ad.apdPersona=p.perId
  left join dbo.AporteGeneral ag on ag.apgId=ad.apdAporteGeneral
  left join Afiliado a on a.afiPersona=p.perId
  left join RolAfiliado ra on ra.roaAfiliado=a.afiId
  WHERE ra.roaTipoAfiliado=apt.tipoAportante
  group by 
  apt.TipoDoc,
  apt.numeroDoc,
  apt.razonSocial,
  apt.tipoAportante ,
  ra.roaEstadoAfiliado,
   ra.roaTipoAfiliado,
   a.afiId
  UNION ALL
  select 
  apt.TipoDoc as [tipoIdentificacion],
  apt.numeroDoc as [numeroIdentificacion],
  apt.razonSocial as [nombre],
  apt.tipoAportante as [tipoAportante],
  em.empEstadoEmpleador as [estadoCCF],
    (case  WHEN em.empEstadoEmpleador is not null then  'EMPLEADOR' else NULL END )as [tipoAfiliacionCCF],
  CAST(em.empFechaRetiro AS DATE)as [fechaRetiro]
  from Aportes as apt
  left join persona p on (p.perTipoIdentificacion=apt.TipoDoc and p.perNumeroIdentificacion=apt.numeroDoc)
  LEFT join Empresa e on e.empPersona = p.perId
  left join empleador em on em.empEmpresa = e.empid
  ) as b
  group by   b.numeroIdentificacion,
  b.tipoIdentificacion,
  b.nombre,
  b.tipoAportante 

  for json path,  include_null_values)  
    
  select @AportesResult
END