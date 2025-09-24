/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS181]    Script Date: 2023-10-13 8:28:44 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS181]    Script Date: 2023-10-12 8:36:03 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS181]    Script Date: 2023-09-20 10:21:10 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS181]    Script Date: 2023-09-13 12:09:04 PM ******/
-- =============================================
-- Author:		Pedro Luis Parra Gamboa
-- Create date: 2021/01/29
-- Description:	Genera reporte de Giass 181 reporte empleadores 
-- =============================================
CREATE OR ALTER  PROCEDURE [dbo].[USP_REP_REPGIASS181]
AS
BEGIN
SET ANSI_WARNINGS OFF
SET NOCOUNT ON



DECLARE @fecha DATE, 
		@fechaHora DATETIME ;
set @fecha = dbo.getLocalDate();		
SET @fechaHora = DATEADD (HOUR,16,CONVERT (DATETIME,@fecha)); 


drop table if exists #indepen2
drop table if exists #depende
drop table if exists #maxRoa
create table #depende (roaid bigint)
insert into #depende (roaid)
select roaId
from persona p
join Afiliado a on a.afiPersona=p.perId
join RolAfiliado ra on ra.roaAfiliado=a.afiId
where p.pernumeroIdentificacion in (
'10224663',
'10248811',
'10249472',
'1053765083',
'15921055',
'2302014',
'24319273',
'24626097',
'30274308',
'30297236',
'30322569',
'30299802',
'30374358',
'75092550',
'30339523',
'41920640',
'24865503',
'41432118'
) and ra.roaEstadoAfiliado='INACTIVO'

----vista para sacar el minimo Id del afiliado sbayona
;with MaxRoa as (
    select 
    min(ra.roaId) as maxRoaId
    from persona p
    join Afiliado a on a.afiPersona = p.perId
    join RolAfiliado ra on ra.roaAfiliado = a.afiId
    where p.pernumeroIdentificacion in ('10254114','16051056','12965515','24327957','30275207','30288067','6113620','10252135')
    group by a.afiId
)
select 
    ra.roaId as id,
    ra.roaFechaRetiro
	into #maxRoa
from MaxRoa mr
join RolAfiliado ra on ra.roaId = mr.maxRoaId
order by ra.roaId desc

--tabla para incluir independientes que son empresa tambien

select distinct cedula into #indepen2 from  (
values
('1054987670'),('75106156'),('16079434'),('1053795225'),('1136882779'),('75083129'),('42148185'),('15491100'),('16136761'),('1053780766'),('1053800087'),('16072360'),('9924307'),
('1053774943'),('15897328'),('15961204'),('1053783992'),('1053826344'),('10281484'),('3539981'),('24341508'),('12965515'),('10270433'),('30329899'),('1053810114'),('24340824'),
('16071970'),('79511317'),('75102589'),('24346983'),('75107309'),('30312315'),('30404993'),('30274308'),('28548916'),('10288091'),('16135190'),('24336907'),('30282534'),
('4479419'),('30397134'),('10282966'),('15960426'),('24338347'),('1122784644'),('1053819348'),('1053773152'),('9846250'),('24866619'),('30331875'),('75158740'),('11523218'),('75105240'),
('75069427'),('30232422'),('1053765934'),('15961404'),('10275514'),('24327957'),('10289601'),('5571525'),('1002899216'),('52833123'),('10275304'),('75068357'),('1053838444'),('75102078'),
('30337245'),('30325765'),('1193373465'),('52346996'),('24625949'),('4051034'),('1053821928'),('1053787278'),('1053785852'),('30308585'),('18593964'),('1088026490'),('1225088498'),
('10277551'),('16078425'),('30315256'),('1053817167'),('24646166'),('15906643'),('1053815570'),('75035307'),('10288440'),('19332083'),('52404674'),('75102276'),('75066070'),('1053784605'),
('75063499'),('42112137'),('1053850551'),('75096498'),('1053788208'),('9976195'),('30322318'),('32350385'),('9739088'),('30234656'),('1105783032'),('9922504'),('52514973'),('1053785546'),
('1053842678'),('75087218'),('75087311'),('1061626195'),('1002528368'),('30402004'),('1060651501'),('6113620'),('75095897'),('10245201'),('30331549'),('10245055'),('75090382'),
('1053816488'),('30310510'),('30404582'),('75088228'),('1053797381'),('30285783'),('19308488'),('30283209'),('75101579'),('25099245'),('10284067'),('75090632'),('10216457'),('1094898435'),
('1053792506'),('1054545863'),('79230954'),('1037594702'),('75100782'),('24333114'),('1059701503'),('75093496'),('4414815'),('1088285304'),('30322434'),('30392278'),('75097258'),
('10283643'),('30275207'),('30298092'),('41932673'),('30392199'),('9863365'),('1110476467'),('75092722'),('75062940'),('1193121648'),('30329177'),('75088806'),('1121829288'),
('1092915920'),('30329608'),('1022378563'),('24766822'),('75084144'),('1061656191'),('30397765'),('10276566'),('1053800697'),('75095546'),('24336187'),('10268268'),('30280577'),
('9975760'),('30311008'),('24338501'),('75093129'),('11318932'),('24395827'),('75142690'),('75144338'),('1053820700'),('75095089'),('30236782'),('34002305'),('1060653315'),('15923592'),
('30401225'),('24348053'),('1053785908'),('15961109'),('15987983'),('24341674'),('30231728'),('52077916'),('75074161'),('24339959'),('1053766362'),('24335114'),('1053797697'),('75083322'),
('75067244'),('41243044'),('1053851248'),('4384821'),('1053773752'),('1049616325'),('1060656287'),('10250562'),('10165640'),('30398940'),('1007322536'),('10230306'),('52709145'),('51890402'),
('30288067'),('80410723'),('1053815838'),('1087987286'),('1053829597'),('16075551'),('75097511'),('1053825893'),('1055832693'),('1053765095'),('19107794'),('30233083'),('24315544'),
('10184302'),('1053862285'),('1193373465'),('4446203'),('75070299'),('24853847'),('1053815235'),('1053828326'),('25079891'),('30353372'),('30314953'),('8328778'),('1127540238'),
('20830882'),('75100864'),('1110471664'),('30314952'),('30315929'),('10263653'),('93060655'),('75065677'),('1054988127'),('16072155'),('24434368'),('1054992244'),('1053845838'),
('24873980'),('10243629'),('12201852'),('1053818653'),('1088013507'),('75098262'),('75146103'),('1053849397'),('75075032'),('75070679'),('75091761'),('1060654128'),('1193137298'),
('75074191'),('75068270'),('10222097'),('16051004'),('41536961'),('10253965'),('24852140'),('24623489'),('24365017'),('4482394'),('10247691'),('10233939'),('30287625'),('10252133'),('30282039'),('10218070'),
('24307491'),('24431806'),('25232661'),('10216802'),('10238728'),('24314718'),('24311870'),('24317755'),('30300777'),('30282052'),('24821037'),('10227160'),('30270962'),
('24304615'),('10219572'),('10252203'),('30290129'),('2834280'),('15902085'),('24867761'),('24329070'),('10257176'),('24623461'),('10228555'),('24316645'),('19225701'),('24820501'),
('24384995'),('25159438'),('10222463'),('10212770'),('24865022'),('24326155')	


) as c (cedula)

SELECT DISTINCT
CASE pe.perTipoIdentificacion 
     WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'
	ELSE ''
	END 
AS 'C8',
pe.perNumeroIdentificacion AS 'C9',
CASE WHEN  pe.perTipoIdentificacion = 'NIT' THEN CONVERT (VARCHAR (1), pe.perDigitoVerificacion) ELSE '' END AS 'C10',
SUBSTRING (CASE pe.perTipoIdentificacion WHEN 'NIT' THEN REPLACE(pe.perRazonSocial,',','') 
	ELSE CONCAT (pe.perPrimerNombre, ' ', pe.perSegundoNombre, ' ', pe.perPrimerApellido, ' ', pe.perSegundoApellido)
	END, 1, 200)
AS 'C11',
CASE e.empNaturalezaJuridica WHEN 'PRIVADA' THEN 2 
		WHEN 'PUBLICA'THEN  1 
		WHEN 'ENTIDADES_DERECHO_PUBLICO_NO_SOMETIDAS' THEN '5'
		WHEN 'MIXTA' THEN 3
		WHEN 'ORGANISMOS_MULTILATERALES' THEN 4
		ELSE ''
		END AS 'C12',
CASE pr.perTipoIdentificacion 
    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'
	ELSE ''
	END 
AS 'C513',
pr.perNumeroIdentificacion AS 'C514',
ISNULL (ur.ubiEmail, '') AS 'C515',
ISNULL (ISNULL (ur.ubiTelefonoFijo, ur.ubiTelefonoCelular), '') AS 'C516',
deu.depCodigo AS 'C517',
RIGHT (mue.munCodigo, 3) AS 'C518',
SUBSTRING (REPLACE(ue.ubiDireccionFisica,',',''), 1,40) AS 'C519',
'' AS 'C520',
ISNULL (cantTrabajadores, 0) AS 'C521', 
cii.ciiCodigo AS 'C527',
ISNULL (sue.ciiCodigo, '') AS 'C528', 
CONCAT (cast(e.empFechaConstitucion AS VARCHAR (10)) ,';') AS 'C529' 

FROM persona pe WITH(NOLOCK)
INNER JOIN empresa e WITH(NOLOCK) ON e.empPersona = pe.perId
INNER JOIN CodigoCIIU cii WITH(NOLOCK) ON cii.ciiId = e.empCodigoCIIU
INNER JOIN empleador em WITH(NOLOCK) ON em.empEmpresa = e.empId
INNER JOIN solicitudAfiliaciEmpleador sae WITH(NOLOCK) ON sae.saeEmpleador = em.empId
INNER JOIN Solicitud sol WITH(NOLOCK) ON solId = sae.saeSolicitudGlobal

LEFT JOIN UbicacionEmpresa ube WITH(NOLOCK) ON ube.ubeEmpresa = e.empId AND ube.ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
LEFT JOIN Ubicacion ue WITH(NOLOCK) ON ue.ubiId = ube.ubeUbicacion
LEFT JOIN Municipio mue WITH(NOLOCK) ON mue.munId = ue.ubiMunicipio
LEFT JOIN Departamento deu WITH(NOLOCK) ON deu.depId = mue.munDepartamento
--Datos representante legal
LEFT JOIN persona pr WITH(NOLOCK) ON pr.perId = e.empRepresentanteLegal
LEFT JOIN PersonaDetalle pdr WITH(NOLOCK) ON pdr.pedPersona = pr.perId
LEFT JOIN Ubicacion ur WITH(NOLOCK) ON ur.ubiId = e.empUbicacionRepresentanteLegal
--Trabajadores Activios del empleador
LEFT JOIN (SELECT roaEmpleador, COUNT (0) AS cantTrabajadores FROM rolAfiliado WITH(NOLOCK) WHERE (roaEstadoAfiliado = 'ACTIVO' AND roaFechaAfiliacion <= @fechaHora) 
		OR (roaEstadoAfiliado = 'INACTIVO' AND roaFechaRetiro >= @fechaHora)
		GROUP BY roaEmpleador) AS roa
ON roa.roaEmpleador = em.empId
-- sucursales empleadores
LEFT JOIN (SELECT * FROM (
SELECT P.sueEmpresa, P.ciiCodigo, CASE WHEN MIN (P.Orden) OVER (PARTITION BY P.sueEmpresa) = P.Orden THEN Orden ELSE NULL END AS Minimo
FROM  (SELECT sueEmpresa, cii1.ciiCodigo, ROW_NUMBER () OVER (PARTITION BY sueEmpresa ORDER BY sueId) AS Orden
					FROM SucursalEmpresa WITH(NOLOCK)
					INNER JOIN Empresa WITH(NOLOCK) ON empId = sueEmpresa
					INNER JOIN CodigoCIIU  Cii1 WITH(NOLOCK) ON Cii1.ciiId = sueCodigoCIIU
					INNER JOIN CodigoCIIU cii2 WITH(NOLOCK) ON cii2.ciiId = empCodigoCIIU
					WHERE cii1.ciiId <> cii2.ciiId 
					GROUP BY sueEmpresa, cii1.ciiCodigo, sueId) AS P) AS PP
			WHERE PP.Minimo IS NOT NULL) AS sue
ON sue.sueEmpresa = e.empId
WHERE sae.saeEstadoSolicitud = 'CERRADA' AND sol.solResultadoProceso = 'APROBADA'
AND em.empEstadoEmpleador IN ( 'ACTIVO','INACTIVO')
--AND ((em.empFechaCambioEstadoAfiliacion <= @fechaHora AND em.empEstadoEmpleador = 'ACTIVO') 
--OR (em.empFechaRetiro > @fechaHora AND em.empEstadoEmpleador = 'INACTIVO' AND em.empFechaCambioEstadoAfiliacion <= @fechaHora))
---and pe.perNumeroIdentificacion= '860011153'

UNION


SELECT DISTINCT
CASE pe.perTipoIdentificacion 
     WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'
	ELSE ''
	END 
AS 'C8',
pe.perNumeroIdentificacion AS 'C9',
CASE WHEN  pe.perTipoIdentificacion = 'NIT' THEN CONVERT (VARCHAR (1), pe.perDigitoVerificacion) ELSE '' END AS 'C10',
SUBSTRING (CASE pe.perTipoIdentificacion WHEN 'NIT' THEN REPLACE(pe.perRazonSocial,',','') 
	ELSE CONCAT (pe.perPrimerNombre, ' ', pe.perSegundoNombre, ' ', pe.perPrimerApellido, ' ', pe.perSegundoApellido)
	END, 1, 200)
AS 'C11',
'2' AS 'C12',
CASE pe.perTipoIdentificacion 
    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'
	ELSE ''
	END 
AS 'C513',
pe.perNumeroIdentificacion AS 'C514',
ISNULL (ue.ubiEmail, '') AS 'C515',
ISNULL (ISNULL (ue.ubiTelefonoFijo, ue.ubiTelefonoCelular), '') AS 'C516',
deu.depCodigo AS 'C517',
RIGHT (mue.munCodigo, 3) AS 'C518',
SUBSTRING (REPLACE(ue.ubiDireccionFisica,',',''), 1,40) AS 'C519',
'' AS 'C520',
'1' AS 'C521', 
case when roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' then '9700' else '0020' end AS 'C527',
case when roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE' then '9700' else '0020' end  AS 'C528', 
CONCAT (cast(isnull(roaFechaIngreso,roaFechaAfiliacion) AS VARCHAR (10)) ,';') AS 'C529' 

FROM persona pe WITH(NOLOCK)
INNER JOIN Afiliado a WITH(NOLOCK) ON  a.afiPersona= perid 
INNER JOIN RolAfiliado WITH(NOLOCK) ON roaAfiliado= A.afiId AND roaTipoAfiliado IN ('TRABAJADOR_INDEPENDIENTE','PENSIONADO')
INNER JOIN SolicitudAfiliacionPersona sae WITH(NOLOCK) ON sae.sapRolAfiliado = roaId
INNER JOIN Solicitud sol WITH(NOLOCK) ON solId = sae.sapSolicitudGlobal
 
LEFT JOIN Ubicacion ue WITH(NOLOCK) ON ue.ubiId = perUbicacionPrincipal
LEFT JOIN Municipio mue WITH(NOLOCK) ON mue.munId = ue.ubiMunicipio
LEFT JOIN Departamento deu WITH(NOLOCK) ON deu.depId = mue.munDepartamento
   
WHERE sae.sapEstadoSolicitud = 'CERRADA' AND sol.solResultadoProceso = 'APROBADA'
AND roaEstadoAfiliado IN ( 'ACTIVO','INACTIVO')
AND pe.perNumeroIdentificacion NOT IN (SELECT p.perNumeroIdentificacion FROM Persona p JOIN Empresa emp ON emp.empPersona = p.perId and p.perNumeroIdentificacion not in(
select cedula from #indepen2))
and roaId not in (select roaId from #depende)
and roaid not in (select id from #maxRoa )

END