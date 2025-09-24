/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS184]    Script Date: 2023-09-20 9:13:20 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS184]    Script Date: 2023-09-13 12:06:32 PM ******/
-- =============================================
-- Author:		Pedro Luis Parra Gamboa
-- Create date: 2021/01/29
-- Description:	Genera reporte de Giass 184
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_REP_REPGIASS184]
AS
BEGIN
SET ANSI_WARNINGS OFF
SET NOCOUNT ON

--REPORTE 184 _ GIASS
--Calculo de las fechas de reporte
DECLARE @fecha DATE = dbo.getLocalDate(),
		@fechaHora DATETIME ;
SET @fechaHora = DATEADD (HOUR,16,CONVERT (DATETIME,@fecha)); 

drop table if exists #indepen2
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


SELECT em.empId, 'ACTIVACION' AS T_Transaccion, convert(date,solFechaRadicacion) as solFechaRadicacion
INTO #Transacciones
	FROM Solicitud WITH(NOLOCK)
	INNER JOIN SolicitudAfiliaciEmpleador WITH(NOLOCK) ON solId = saeSolicitudGlobal AND saeEstadoSolicitud = 'CERRADA' AND solResultadoProceso = 'APROBADA'
	INNER JOIN Empleador em ON em.empId = saeEmpleador
	GROUP BY em.empId, convert(date,solFechaRadicacion)
	UNION
	SELECT em.empId, 'INACTIVACION', convert(date,solFechaRadicacion) as solFechaRadicacion
	FROM Solicitud  WITH(NOLOCK)
	INNER JOIN SolicitudNovedad WITH(NOLOCK) ON snoSolicitudGlobal = solId
	INNER JOIN SolicitudNovedadEmpleador WITH(NOLOCK) ON sneIdSolicitudNovedad = snoId AND solResultadoProceso = 'APROBADA' AND snoEstadoSolicitud = 'CERRADA'
	INNER JOIN Empleador  em WITH(NOLOCK) ON em.empId = sneIdEmpleador
	WHERE solTipoTransaccion IN ('DESAFILIACION', 'DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_CERO_TRABAJADORES',
	'DESAFILIACION_AUTOMATICA_POR_MORA')
	GROUP BY em.empId, convert(date,solFechaRadicacion);

SELECT *, ROW_NUMBER() OVER (PARTITION BY empId ORDER BY solFechaRadicacion, T_Transaccion) AS Orden
INTO #Transacciones_Ordenadas
	FROM #Transacciones;

-- Consulta del reporte dependientes
SELECT DISTINCT
CASE T.perTipoIdentificacion 
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
T.perNumeroIdentificacion AS 'C9',
CONVERT (VARCHAR (10), Z.solFechaRadicacion) AS 'C523',
ISNULL (CONVERT (varchar (10), (SELECT solFechaRadicacion FROM #Transacciones_Ordenadas b WHERE  b.empId = T.empId AND b.Orden = (Z.Orden + 1))), '') AS 'C525',
CONVERT (DATE, comFinal.comFecha) AS 'C582',
concat (CASE WHEN comFinal.comFecha IS NOT NULL THEN '1' ELSE '' END,';') AS 'C583'
FROM 
	(SELECT pe.perTipoIdentificacion, pe.pernumeroIdentificacion, em.empId
	FROM persona pe WITH(NOLOCK)
	INNER JOIN empresa e WITH(NOLOCK) ON e.empPersona = pe.perId
	INNER JOIN empleador em WITH(NOLOCK) ON em.empEmpresa = e.empId
	INNER JOIN solicitudAfiliaciEmpleador sae WITH(NOLOCK) ON sae.saeEmpleador = em.empId
	INNER JOIN Solicitud sol  WITH(NOLOCK)
	        ON solId = sae.saeSolicitudGlobal AND sae.saeEstadoSolicitud = 'CERRADA' AND sol.solResultadoProceso = 'APROBADA'
	GROUP BY pe.perTipoIdentificacion, pe.pernumeroIdentificacion, em.empId) AS T
LEFT JOIN #Transacciones_Ordenadas AS Z
ON T.empId = Z.empId
--Paz y Salvo
LEFT JOIN (SELECT DISTINCT * FROM (
			SELECT cerEmpleador, CASE WHEN MAX (comId) OVER (PARTITION BY empId) = comId THEN comFechaComunicado ELSE NULL END AS comFecha
			FROM PlantillaComunicado WITH(NOLOCK)
			INNER JOIN Comunicado WITH(NOLOCK) ON comPlantillaComunicado = pcoId
			INNER JOIN Certificado WITH(NOLOCK) ON cerId = comId
			INNER JOIN Empleador WITH(NOLOCK) ON empId = cerEmpleador
			WHERE pcoAsunto = 'CERTIFICADO PAZ Y SALVO EMPLEADOR') AS com1
			WHERE com1.comFecha IS NOT NULL) 
			AS comFinal
ON T.empId = comFinal.cerEmpleador
WHERE Z.T_Transaccion = 'ACTIVACION' AND Z.solFechaRadicacion <= @fechaHora
	
	union all

	-- Consulta del reporte independientes y pensionados
	select distinct
		case per.perTipoIdentificacion 
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
		END AS 'C8',
		per.perNumeroIdentificacion AS 'C9',
		max(CONVERT (VARCHAR (10), roa.roaFechaAfiliacion)) AS 'C523',
		max(ISNULL (CONVERT(VARCHAR (10), cast(roa.roaFechaRetiro as date)), '')) AS 'C525', --fecha fin de la afiliacion
		null as 'C582',
		';' as 'C583'
	from Persona as per
		inner join Afiliado as afi on afi.afiPersona = per.perId
		inner join RolAfiliado as roa 
			on roa.roaAfiliado = afi.afiId and roa.roaTipoAfiliado <> 'TRABAJADOR_DEPENDIENTE'
			--and roa.roaFechaAfiliacion = (select max(r.roaFechaAfiliacion) from RolAfiliado as r where r.roaAfiliado = roa.roaAfiliado)
		inner join SolicitudAfiliacionPersona as sap on sap.sapRolAfiliado = roa.roaId
		inner join Solicitud as sol on sap.sapSolicitudGlobal = sol.solId
	where sap.sapEstadoSolicitud = 'CERRADA' AND sol.solResultadoProceso = 'APROBADA'
		and roaEstadoAfiliado IN ('ACTIVO', 'INACTIVO')
		AND per.perNumeroIdentificacion NOT IN (SELECT p.perNumeroIdentificacion FROM Persona p JOIN Empresa emp ON emp.empPersona = p.perId and p.perNumeroIdentificacion not in (
		select cedula from #indepen2))
        --and per.perNumeroIdentificacion not in ('10252135') 
		group by per.perTipoIdentificacion ,per.perNumeroIdentificacion
		order by 2
		--and per.perNumeroIdentificacion = '1002799269'
	--OBSERVACIONES
END