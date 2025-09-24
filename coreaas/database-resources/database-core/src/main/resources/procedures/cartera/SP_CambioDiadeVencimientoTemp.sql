CREATE OR ALTER PROCEDURE  [dbo].[SP_CambioDiadeVencimientoTemp] AS
	BEGIN 
			-----COLOCANDO EL DÍA HÁBIL PARA CARTERA 
			BEGIN
		      UPDATE emp 
			     SET emp.empDiaHabilVencimientoAporte =  CASE WHEN CHARINDEX('S', p.perNumeroIdentificacion) = 0 THEN 
															  CASE 
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 0 and 7 THEN 2 
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 8 and 14 THEN 3
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 15 and 21 THEN 4
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 22 and 28 THEN 5
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 29 and 35 THEN 6
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 36 and 42 THEN 7
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 43 and 49 THEN 8
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 50 and 56 THEN 9
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 57 and 63 THEN 10
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 64 and 69 THEN 11
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 70 and 75 THEN 12
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 76 and 81 THEN 13
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 82 and 87 THEN 14
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 88 and 93 THEN 15
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 94 and 99 THEN 16	
															  END
														 ELSE 
															CASE WHEN CHARINDEX('S', p.perNumeroIdentificacion) > 0 THEN 
																 CASE 
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 0  and 7  THEN 2  
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 8  and 14 THEN 3  
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 15 and 21 THEN 4 
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 22 and 28 THEN 5
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 29 and 35 THEN 6
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 36 and 42 THEN 7
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 43 and 49 THEN 8
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 50 and 56 THEN 9
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 57 and 63 THEN 10
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 64 and 69 THEN 11
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 70 and 75 THEN 12
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 76 and 81 THEN 13
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 82 and 87 THEN 14
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 88 and 93 THEN 15
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 94 and 99 THEN 16
		 
																 END
															END
														END
			 FROM Empleador as emp
	   INNER JOIN Empresa as e ON emp.empempresa = e.empid 
	   INNER JOIN Persona as p ON perid = emppersona 
	   LEFT JOIN PreRegistroEmpresaDescentralizada pred ON pred.prdNumeroIdentificacionSerial = P.perNumeroIdentificacion 
			WHERE emp.empEstadoEmpleador <> 'INACTIVO'  and pred.prdNumeroIdentificacionSerial is null
			 --  AND emp.empDiaHabilVencimientoAporte IS NULL
		END
			BEGIN
		      UPDATE emp 
			     SET emp.empDiaHabilVencimientoAporte =  CASE WHEN CHARINDEX('S', p.perNumeroIdentificacion) = 0 THEN 
															  CASE 
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 0 and 7 THEN 2 
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 8 and 14 THEN 3
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 15 and 21 THEN 4
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 22 and 28 THEN 5
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 29 and 35 THEN 6
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 36 and 42 THEN 7
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 43 and 49 THEN 8
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 50 and 56 THEN 9
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 57 and 63 THEN 10
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 64 and 69 THEN 11
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 70 and 75 THEN 12
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 76 and 81 THEN 13
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 82 and 87 THEN 14
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 88 and 93 THEN 15
																WHEN SUBSTRING(p.perNumeroIdentificacion,(len(p.perNumeroIdentificacion)-1),2) between 94 and 99 THEN 16	
															  END
														 ELSE 
															CASE WHEN CHARINDEX('S', p.perNumeroIdentificacion) > 0 THEN 
																 CASE 
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 0  and 7  THEN 2  
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 8  and 14 THEN 3  
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 15 and 21 THEN 4 
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 22 and 28 THEN 5
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 29 and 35 THEN 6
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 36 and 42 THEN 7
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 43 and 49 THEN 8
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 50 and 56 THEN 9
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 57 and 63 THEN 10
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 64 and 69 THEN 11
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 70 and 75 THEN 12
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 76 and 81 THEN 13
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 82 and 87 THEN 14
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 88 and 93 THEN 15
																	WHEN SUBSTRING(p.perNumeroIdentificacion,(CHARINDEX('S', p.perNumeroIdentificacion)-2),2) between 94 and 99 THEN 16
		 
																 END
															END
														END
			 FROM Empleador as emp
	   INNER JOIN Empresa as e ON emp.empempresa = e.empid 
	   INNER JOIN Persona as p ON perid = emppersona 
	   INNER JOIN PreRegistroEmpresaDescentralizada pred ON pred.prdNumeroIdentificacionSerial = P.perNumeroIdentificacion 
			WHERE emp.empEstadoEmpleador <> 'INACTIVO' 
			  AND emp.empDiaHabilVencimientoAporte IS NULL
		END 

			  --- DEPURACIÓN DE LA CARTERA POR MANTIS NO SOLUCIONADAS
			  
-- /*

--				 --0. Eliminación y nueva generación de las tablas
--				DROP TABLE IF EXISTS carteradependienteControlAud;
--				DROP TABLE IF EXISTS CarteraControlAud;

--				/*CREACIÓN TABLA CONTROL CARTERADEPENDIENTE AUD*/
--				CREATE TABLE carteradependienteControlAud
--				(cadDeudaPresunta   NUMERIC, 
--				 cadEstadoOperacion VARCHAR(10), 
--				 cadCartera         BIGINT, 
--				 cadPersona         BIGINT, 
--				 cadDeudaReal       NUMERIC, 
--				 cadAgregadoManual  BIGINT, 
--				 cadId              BIGINT,
--				 descripcion        VARCHAR(500),
--				 FechaModificacion DATE,
--				 Ejecucion BIT
--				);

--				/*CREACIÓN TABLA CONTROL CARTERA AUD*/
--				CREATE TABLE CarteraControlAud
--				(CarDeudaPresunta         NUMERIC, 
--				 CarEstadoCartera         VARCHAR (6), 
--				 CarEstadoOperacion       VARCHAR (10), 
--				 CarFechaCreacion         DATETIME, 
--				 CarPersona               BIGINT, 
--				 CarMetodo                VARCHAR (8), 
--				 CarPeriodoDeuda          DATE, 
--				 CarRiesgoIncobrabilidad  VARCHAR (50), 
--				 CarTipoAccionCobro       VARCHAR (4), 
--				 CarTipoDeuda             VARCHAR (11), 
--				 CarTipoLineaCobro        VARCHAR (3), 
--				 CarTipoSolicitante       VARCHAR (13), 
--				 CarFechaAsignacionAccion DATETIME, 
--				 CarUsuarioTraspaso       VARCHAR (255), 
--				 CarId                    BIGINT, 
--				 CarDeudaPresuntaUnitaria NUMERIC, 
--				 descripcion              VARCHAR (500), 
--				 FechaModificacion        DATE,
--				 Ejecucion BIT
--				);


--				/*2. VALIDACIÓN REVISIÓN SI HAY APORTES POR ALGÚN PERIODO EN CARTERA*/
       
--					SELECT peremp.perNumeroIdentificacion AS NumeroIdentificacionEmpresa, 
--						   peremp.perTipoIdentificacion AS TipoIdentificacionEmpresa, 
--						   carDeudaPresunta, 
--						   carPeriodoDeuda, 
--						   car.carTipoLineaCobro, 
--						   pertra.perNumeroIdentificacion AS NumeroIdentificacionTrabajador, 
--						   pertra.perTipoIdentificacion AS TipoIdentificacionTrabajador, 
--						   cad.cadDeudaPresunta, 
--						   car.carDeudaPresuntaUnitaria, 
--						   cad.cadEstadoOperacion, 
--						   regNombreAportante, 
--						   regTipoIdentificacionAportante, 
--						   regNumeroIdentificacionAportante, 
--						   regPeriodoAporte, 
--						   carEstadoCartera, 
--						   carEstadoOperacion, 
--						   redAporteObligatorio, 
--						   redOUTAporteObligatorioMod, 
--						   redNovIngreso, 
--						   redNovRetiro, 
--						   redNovVSP, 
--						   redNovVST, 
--						   redNovSLN, 
--						   redNovIGE, 
--						   redNovLMA, 
--						   redNovVACLR, 
--						   redNovSUS, 
--						   regValTotalApoObligatorio, 
--						   (carDeudaPresuntaUnitaria - cadDeudaPresunta) AS restaUnitariaVSpresunta
--					---UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--					INTO #carterasaneadaporaportes20210527LC1
--					FROM Cartera car
--						 INNER JOIN Persona peremp WITH(NOLOCK) ON peremp.perId = carPersona
--						 INNER JOIN CarteraDependiente cad WITH(NOLOCK) ON cad.cadCartera = car.carId
--						 INNER JOIN Persona pertra WITH(NOLOCK) ON pertra.perId = cad.cadPersona
--						 INNER JOIN pila.RegistroGeneral Rg ON rg.regNumeroIdentificacionAportante = peremp.perNumeroIdentificacion
--															   AND rg.regTipoIdentificacionAportante = peremp.perTipoIdentificacion
--						 INNER JOIN pila.RegistroDetallado RD ON RD.redNumeroIdentificacionCotizante = pertra.perNumeroIdentificacion
--																 AND rd.redTipoIdentificacionCotizante = pertra.perTipoIdentificacion
--																 AND rg.regId = rd.redRegistroGeneral
--					WHERE LEFT(car.carPeriodoDeuda, 7) = rg.regperiodoAporte
--						  AND CAD.cadEstadoOperacion = 'VIGENTE'
--						  AND carTipoLineaCobro ='LC1' 
--						  AND redAporteObligatorio > 0
--						--AND regNumeroIdentificacionAportante = '823000505'
--						--and redNumeroIdentificacionCotizante = '6818400'
--					GROUP BY peremp.perNumeroIdentificacion, peremp.perTipoIdentificacion, 
--							 carDeudaPresunta, carPeriodoDeuda,pertra.perNumeroIdentificacion, 
--							 pertra.perTipoIdentificacion, cad.cadDeudaPresunta, cad.cadEstadoOperacion, 
--							 regNombreAportante,regTipoIdentificacionAportante, regNumeroIdentificacionAportante, 
--							 regPeriodoAporte,carTipoLineaCobro,carid,carEstadoCartera,carEstadoOperacion, 
--							 redAporteObligatorio,redOUTAporteObligatorioMod, 
--							 redNovIngreso,redNovRetiro,redNovVSP,redNovVST, 
--							 redNovSLN,redNovIGE,redNovLMA,redNovVACLR,redNovSUS, 
--							 regValTotalApoObligatorio,cadEstadoOperacion,
--							 carDeudaPresuntaUnitaria;

--							 /*INSERTAR TABLACONTROL*/
--					INSERT INTO carteradependienteControlAud
--					SELECT DISTINCT cd.* ,'AjustePorvalidacionaportesPorLC1' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod, 0 as Ejecucion
--					FROM Cartera c
--					INNER JOIN Persona p ON c.carpersona = p.perid 
--					INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--					INNER JOIN persona pd ON pd.perid = cd.cadPersona
--					INNER JOIN #carterasaneadaporaportes20210527LC1 x ON pd.pertipoidentificacion  = x.TipoIdentificacionTrabajador
--					AND pd.pernumeroidentificacion = x.NumeroIdentificacionTrabajador 
--					AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--					AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--					AND c.carPeriodoDeuda = x.carPeriodoDeuda

--					/*actualizar core*/
--					--SELECT DISTINCT x.*,cd.* ,'validacionaportesLC1' as descripcion,dbo.GetLocalDate() as fechaMod
--					UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--					FROM Cartera c
--					INNER JOIN Persona p ON c.carpersona = p.perid 
--					INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--					INNER JOIN persona pd ON pd.perid = cd.cadPersona
--					INNER JOIN #carterasaneadaporaportes20210527LC1 x ON pd.pertipoidentificacion  = x.TipoIdentificacionTrabajador
--					AND pd.pernumeroidentificacion = x.NumeroIdentificacionTrabajador 
--					AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--					AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--					AND c.carPeriodoDeuda = x.carPeriodoDeuda

--					/**/
--					INSERT INTO core.aud.Revision
--					SELECT cadId AS revIp, 
--							'CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REVNOMBREUSUARIO, 
--							NULL AS revRequestId, 
--							datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) REVTIMESTAMP
--					FROM carteradependienteControlAud T
--					WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'AjustePorvalidacionaportesPorLC1' AND  Ejecucion = 0;

--					INSERT INTO core.aud.RevisionEntidad
--					SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REEENTITYCLASSNAME, 
--						   1 AS REEREVISIONTYPE, 
--						   T.REVTIMESTAMP AS REVTIMESTAMP, 
--						   T.REVID AS REEREVISION
--					FROM core.aud.Revision T WITH(NOLOCK)
--					WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--						  AND T.REVID NOT IN
--					(
--						SELECT REEREVISION
--						FROM core.aud.RevisionEntidad
--					);

--					INSERT INTO CORE.aud.CARTERADEPENDIENTE_AUD
--					SELECT  cadId, 
--							R.REVID AS REV, 
--							1 AS REVTYPE, 
--							cadDeudaPresunta, 
--							cadEstadoOperacion, 
--							cadCartera, 
--							cadPersona, 
--							cadDeudaReal, 
--							cadAgregadoManual
--					FROM carteradependienteControlAud P
--						INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.cadId = R.revIp
--					WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--					AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'AjustePorvalidacionaportesPorLC1' AND Ejecucion = 0
--					ORDER BY P.cadId;

--					UPDATE carteradependienteControlAud SET	Ejecucion = 1 WHERE Ejecucion = 0

--					/*PARA C6*/
--					SELECT peremp.perNumeroIdentificacion AS NumeroIdentificacionEmpresa, 
--						   peremp.perTipoIdentificacion AS TipoIdentificacionEmpresa, 
--						   carDeudaPresunta, 
--						   carPeriodoDeuda, 
--						   car.carTipoLineaCobro, 
--						   pertra.perNumeroIdentificacion AS NumeroIdentificacionTrabajador, 
--						   pertra.perTipoIdentificacion AS TipoIdentificacionTrabajador, 
--						   cad.cadDeudaPresunta, 
--						   car.carDeudaPresuntaUnitaria, 
--						   cad.cadEstadoOperacion, 
--						   regNombreAportante, 
--						   regTipoIdentificacionAportante, 
--						   regNumeroIdentificacionAportante, 
--						   regPeriodoAporte, 
--						   carEstadoCartera, 
--						   carEstadoOperacion, 
--						   redAporteObligatorio, 
--						   redOUTAporteObligatorioMod, 
--						   redNovIngreso, 
--						   redNovRetiro, 
--						   redNovVSP, 
--						   redNovVST, 
--						   redNovSLN, 
--						   redNovIGE, 
--						   redNovLMA, 
--						   redNovVACLR, 
--						   redNovSUS, 
--						   regValTotalApoObligatorio, 
--						   (carDeudaPresuntaUnitaria - cadDeudaPresunta) AS restaUnitariaVSpresunta
--					---UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--					INTO #carterasaneadaporaportes20210527C6
--					FROM Cartera car
--						 INNER JOIN Persona peremp WITH(NOLOCK) ON peremp.perId = carPersona
--						 INNER JOIN CarteraDependiente cad WITH(NOLOCK) ON cad.cadCartera = car.carId
--						 INNER JOIN Persona pertra WITH(NOLOCK) ON pertra.perId = cad.cadPersona
--						 INNER JOIN pila.RegistroGeneral Rg ON rg.regNumeroIdentificacionAportante = peremp.perNumeroIdentificacion
--															   AND rg.regTipoIdentificacionAportante = peremp.perTipoIdentificacion
--						 INNER JOIN pila.RegistroDetallado RD ON RD.redNumeroIdentificacionCotizante = pertra.perNumeroIdentificacion
--																 AND rd.redTipoIdentificacionCotizante = pertra.perTipoIdentificacion
--																 AND rg.regId = rd.redRegistroGeneral
--					WHERE LEFT(car.carPeriodoDeuda, 7) = rg.regperiodoAporte
--						  AND CAD.cadEstadoOperacion = 'VIGENTE'
--						  AND carTipoLineaCobro ='C6' 
--						  AND redAporteObligatorio > 0
--						--AND regNumeroIdentificacionAportante = '823000505'
--						--and redNumeroIdentificacionCotizante = '6818400'
--					GROUP BY peremp.perNumeroIdentificacion, peremp.perTipoIdentificacion, 
--							 carDeudaPresunta, carPeriodoDeuda,pertra.perNumeroIdentificacion, 
--							 pertra.perTipoIdentificacion, cad.cadDeudaPresunta, cad.cadEstadoOperacion, 
--							 regNombreAportante,regTipoIdentificacionAportante, regNumeroIdentificacionAportante, 
--							 regPeriodoAporte,carTipoLineaCobro,carid,carEstadoCartera,carEstadoOperacion, 
--							 redAporteObligatorio,redOUTAporteObligatorioMod, 
--							 redNovIngreso,redNovRetiro,redNovVSP,redNovVST, 
--							 redNovSLN,redNovIGE,redNovLMA,redNovVACLR,redNovSUS, 
--							 regValTotalApoObligatorio,cadEstadoOperacion,
--							 carDeudaPresuntaUnitaria;

--					INSERT INTO carteradependienteControlAud
--					SELECT DISTINCT cd.* ,'validacionPorAportesParaC6' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod, 0 AS Ejecucion
--					FROM Cartera c
--					INNER JOIN Persona p ON c.carpersona = p.perid 
--					INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--					INNER JOIN persona pd ON pd.perid = cd.cadPersona
--					INNER JOIN #carterasaneadaporaportes20210527C6 x ON pd.pertipoidentificacion  = x.TipoIdentificacionTrabajador
--					AND pd.pernumeroidentificacion = x.NumeroIdentificacionTrabajador 
--					AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--					AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--					AND c.carPeriodoDeuda = x.carPeriodoDeuda


--					--SELECT DISTINCT x.*,cd.* ,'validacionaportesC6' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod
--					UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--					FROM Cartera c
--					INNER JOIN Persona p ON c.carpersona = p.perid 
--					INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--					INNER JOIN persona pd ON pd.perid = cd.cadPersona
--					INNER JOIN #carterasaneadaporaportes20210527C6 x ON pd.pertipoidentificacion  = x.TipoIdentificacionTrabajador
--					AND pd.pernumeroidentificacion = x.NumeroIdentificacionTrabajador 
--					AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--					AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--					AND c.carPeriodoDeuda = x.carPeriodoDeuda


--					INSERT INTO core.aud.Revision
--					SELECT cadId AS revIp, 
--							'CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REVNOMBREUSUARIO, 
--							NULL AS revRequestId, 
--							datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--					FROM carteradependienteControlAud T
--					WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionPorAportesParaC6' AND Ejecucion = 0

--					INSERT INTO core.aud.RevisionEntidad
--					SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REEENTITYCLASSNAME, 
--						   1 AS REEREVISIONTYPE, 
--						   T.REVTIMESTAMP AS REVTIMESTAMP, 
--						   T.REVID AS REEREVISION
--					FROM core.aud.Revision T WITH(NOLOCK)
--					WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--						  AND T.REVID NOT IN
--					(
--						SELECT REEREVISION
--						FROM core.aud.RevisionEntidad
--					);

--					INSERT INTO CORE.aud.CARTERADEPENDIENTE_AUD
--					SELECT  cadId, 
--							R.REVID AS REV, 
--							1 AS REVTYPE, 
--							cadDeudaPresunta, 
--							cadEstadoOperacion, 
--							cadCartera, 
--							cadPersona, 
--							cadDeudaReal, 
--							cadAgregadoManual
--					FROM carteradependienteControlAud P
--						INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.cadId = R.revIp
--					WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--					AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionPorAportesParaC6' AND Ejecucion = 0
--					ORDER BY P.cadId;

--					UPDATE carteradependienteControlAud SET Ejecucion = 1 WHERE Ejecucion = 0


--/*
--TEMPORAL 20220816


--*/



-- ----INICIO INSERTAR TEMPORALMENTE MIENTRAS ARREGLANOV 20220816 OLGA VEGA 
--			DROP TABLE #ERRORES
--			DROP TABLE #ERRORES2
--			DROP TABLE #ValidacionNovedadesDifRetLC1
--							SELECT  pemp.pertipoidentificacion as tipoidempresa,pemp.perNumeroIdentificacion as idempresa,
--							ptr.pertipoidentificacion as tipoidtrab, ptr.perNumeroIdentificacion as idtrabajador,
--												c.carPeriodoDeuda, C.CARID   , pemp.perid as peridempresa ,ptr.perid as peridtrab ,
--												CADDEUDAPRESUNTA, CADESTADOOPERACION , CADCARTERA,
--										CADPERSONA , CADDEUDAREAL,CADAGREGADOMANUAL, CADID,  
--						   (carDeudaPresuntaUnitaria - cadDeudaPresunta) AS restaUnitariaVSpresunta 
--						 	INTO   #errores
--								  FROM persona ptr WITH(NOLOCK)
--							inner join afiliado WITH(NOLOCK) on afipersona = ptr.perid 
--							inner join rolafiliado WITH(NOLOCK) on afiid  = roaAfiliado AND roaEstadoAfiliado = 'ACTIVO'
--							inner join empleador em WITH(NOLOCK) on em.empId = roaEmpleador
--							inner join empresa e WITH(NOLOCK) on e.empid = em.empEmpresa
--							inner join persona pemp WITH(NOLOCK) on pemp.perid = e.empPersona
--							inner join cartera c WITH(NOLOCK) on c.carPersona = pemp.perid 
--							  and carEstadoCartera = 'MOROSO'
--							AND carDeudaPresunta>0
--							AND carEstadoOperacion = 'VIGENTE'
--							AND carTipoLineaCobro ='LC1'
--							AND LEFT (carPeriodoDeuda ,7) < LEFT (roaFechaAfiliacion,7)
--							INNER JOIN carteradependiente cd WITH(NOLOCK) on cd.cadCartera= c.carid and cadPersona= ptr.perid 
--							AND CD.cadDeudaPresunta>0	
--							 AND CD.cadEstadoOperacion ='VIGENTE'
 
--							 CREATE CLUSTERED INDEX IX_idsaportantetrab ON #errores (Tipoidempresa,	idempresa,tipoidtrab,	idtrabajador)

--							SELECT tipoidempresa,	idempresa,	tipoidtrab,	idtrabajador	,
--							        CONVERT(DATE,carPeriodoDeuda) AS PERIODO,
--							        CADDEUDAPRESUNTA, CADESTADOOPERACION , CADCARTERA,
--									CADPERSONA , CADDEUDAREAL,CADAGREGADOMANUAL, CADID,redId,restaUnitariaVSpresunta
--							 INTO #ERRORES2
--							 FROM  #errores

--							inner join pila.registrodetallado ON 
--							   tipoidtrab  = redTipoIdentificacionCotizante
--							  AND idtrabajador = redNumeroIdentificacionCotizante 
--							  AND redNovRetiro = 'X'

--							  inner join [pila].[Registrogeneral]  
--							  ON  redRegistroGeneral = regid 
--							  and tipoidempresa  = regTipoIdentificacionAportante
--							 AND idempresa = regNumeroIdentificacionAportante		

--								-----quitando aportes
--								LEFT JOIN (
--								select 
--								regTipoIdentificacionAportante,regNumeroIdentificacionAportante ,redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,
--								regPeriodoAporte 
--								from (
--				      SELECT CASE WHEN  regPeriodoAporte  = MAX( regPeriodoAporte ) 
--							OVER (PARTITION BY   redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante 
--								) THEN regPeriodoAporte  ELSE NULL END AS regPeriodoAporte 
--							,regTipoIdentificacionAportante,regNumeroIdentificacionAportante, redid ,
--												redTipoIdentificacionCotizante,	redNumeroIdentificacionCotizante 								 
--											FROM   [pila].[RegistroGeneral]   								 
--									inner join [pila].[RegistroDetallado]  
--											ON redRegistroGeneral = redid  
--											) aporte
--											where regPeriodoAporte is not null
--											) Z
--							ON Z.regTipoIdentificacionAportante = #errores.tipoidempresa
--							AND Z.regNumeroIdentificacionAportante = #errores.idempresa
--							AND Z.redTipoIdentificacionCotizante = #errores.tipoidtrab
--							AND Z.redNumeroIdentificacionCotizante = #errores.idtrabajador
--							AND Z.regPeriodoAporte = #errores.carPeriodoDeuda
--							WHERE Z.regNumeroIdentificacionAportante IS NULL
--							AND  LEFT(CONVERT(DATE,#errores.carPeriodoDeuda),7)<  LEFT(CONVERT(DATE,(DATEADD(MONTH,-1,GETDATE()))),7)
 

--					---2. ****** OTRAD NOVEDADES**---
--							select x.*, carteradependiente.*, c.carPeriodoDeuda, (carDeudaPresuntaUnitaria - cadDeudaPresunta) AS restaUnitariaVSpresunta
--							 -- UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--					 	 	 INTO #ValidacionNovedadesDifRetLC1
--							FROM cartera c
--							inner join persona pe with(Nolock)on pe.perId = c.carpersona 
--							inner join carteradependiente with(Nolock) on cadcartera = carId
--							inner join persona pt with(Nolock) on pt.perId = cadpersona 
--							left join afiliado with(Nolock) on afipersona = pt.perid 
--							left join RolAfiliado with(Nolock) on roaafiliado = afiid 
--							  INNER JOIN  (SELECT CASE WHEN  rdnfechainicionovedad  = MAX(rdnfechainicionovedad )
--											OVER (PARTITION BY   redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante 
--												 ) THEN rdnfechainicionovedad  ELSE rdnFechaFinNovedad END AS rdnfechainicionovedad 
--													,regTipoIdentificacionAportante,regNumeroIdentificacionAportante, redid ,
--																				 redTipoIdentificacionCotizante,	redNumeroIdentificacionCotizante,
--																				 rdnTipoNovedad,	rdnAccionNovedad, rdnFechaFinNovedad

--																			  FROM [pila].[RegistroDetallado]  
--																		INNER JOIN [pila].[RegistroGeneral]   
--																				ON redRegistroGeneral = regid 
--																		inner join [pila].[RegistroDetalladoNovedad]  
--																				ON rdnRegistroDetallado = redid   
--												 where 	redNovSLN = 'X' OR redNovIGE = 'X' OR redNovLMA = 'X'  ) x  on x.regTipoIdentificacionAportante = pe.perTipoIdentificacion
--					 												 and x.regNumeroIdentificacionAportante = pe.perNumeroIdentificacion
--																	 and x.redTipoIdentificacionCotizante = pt.perTipoIdentificacion
--					 												 and x.redNumeroIdentificacionCotizante = pt.perNumeroIdentificacion
--										AND LEFT (c.carPeriodoDeuda ,7) = left(rdnFechaInicioNovedad,7)							 
--							--AND carTipoLineaCobro = 'C6'
--							AND carTipoLineaCobro = 'LC1'
--							AND cadEstadoOperacion = 'VIGENTE'
--					--		 order by pe.perNumeroIdentificacion asc

--UNION
----SELECT * FROM #ValidacionNovedadesDifRetLC1
--						---	INSERT  INTO #ValidacionNovedadesDifRetLC1
				 	 
--							SELECT  CONVERT(VARCHAR,LEFT(PERIODO,7)+'-01') AS NOV ,tipoidempresa,	idempresa,REDID, 	 
--									tipoidtrab,	idtrabajador,	'NOVEDAD_IGE',	'RELACIONAR_NOVEDAD',
--									CONVERT(VARCHAR,LEFT(PERIODO,7)+'-30')AS F ,
--										0,'VIGENTE',
--										---CADDEUDAPRESUNTA, CADESTADOOPERACION , 
--										CADCARTERA,
--										CADPERSONA , 0 AS CADDEUDAREAL,CADAGREGADOMANUAL, CADID, 
--										CONVERT(VARCHAR,PERIODO) AS PERIODO , restaUnitariaVSpresunta
--										FROM #ERRORES2
--	 order by 3 asc

	

-----FINAL ERROR TEMPORAL NOVEDADES



--							 INSERT INTO carteradependienteControlAud
--							SELECT DISTINCT cd.* ,'validacionNodedadDifRetiroLC1' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod, 0 AS Ejecucion
--							FROM Cartera c
--							INNER JOIN Persona p ON c.carpersona = p.perid 
--							INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--							INNER JOIN persona pd ON pd.perid = cd.cadPersona
--							INNER JOIN #ValidacionNovedadesDifRetLC1 x ON pd.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--							AND pd.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--							AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--							AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--							AND c.carPeriodoDeuda = x.carPeriodoDeuda



--							-- SELECT DISTINCT x.*,cd.* ,'validacionNodedadDifRetiroLC1' as descripcion,GETDATE() as fechaMod
--							UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--							FROM Cartera c
--							INNER JOIN Persona p ON c.carpersona = p.perid 
--							INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--							INNER JOIN persona pd ON pd.perid = cd.cadPersona
--							INNER JOIN #ValidacionNovedadesDifRetLC1 x ON pd.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--							AND pd.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--							AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--							AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--							AND c.carPeriodoDeuda = x.carPeriodoDeuda

--							INSERT INTO core.aud.Revision
--							SELECT cadId AS revIp, 
--									'CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REVNOMBREUSUARIO, 
--									NULL AS revRequestId, 
--									datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--							FROM carteradependienteControlAud T
--							WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionNodedadDifRetiroLC1' AND Ejecucion = 0

--							INSERT INTO core.aud.RevisionEntidad
--							SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REEENTITYCLASSNAME, 
--								   1 AS REEREVISIONTYPE, 
--								   T.REVTIMESTAMP AS REVTIMESTAMP, 
--								   T.REVID AS REEREVISION
--							FROM core.aud.Revision T WITH(NOLOCK)
--							WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--								  AND T.REVID NOT IN
--							(
--								SELECT REEREVISION
--								FROM core.aud.RevisionEntidad
--							);



--							INSERT INTO CORE.aud.CARTERADEPENDIENTE_AUD
--							SELECT  cadId, 
--									R.REVID AS REV, 
--									1 AS REVTYPE, 
--									cadDeudaPresunta, 
--									cadEstadoOperacion, 
--									cadCartera, 
--									cadPersona, 
--									cadDeudaReal, 
--									cadAgregadoManual
--							FROM carteradependienteControlAud P
--								INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.cadId = R.revIp
--							WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--							AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionNodedadDifRetiroLC1' AND Ejecucion = 0
--							ORDER BY P.cadId;


--							UPDATE carteradependienteControlAud SET Ejecucion = 1 WHERE Ejecucion = 0

--							---****** OTRAD NOVEDADES c6**---
--							select x.*, carteradependiente.*, c.carPeriodoDeuda, (carDeudaPresuntaUnitaria - cadDeudaPresunta) AS restaUnitariaVSpresunta
--							 -- UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--							 INTO #ValidacionNovedadesDifRetC6
--							FROM cartera c
--							inner join persona pe with(Nolock)on pe.perId = c.carpersona 
--							inner join carteradependiente with(Nolock) on cadcartera = carId
--							inner join persona pt with(Nolock) on pt.perId = cadpersona 
--							left join afiliado with(Nolock) on afipersona = pt.perid 
--							left join RolAfiliado with(Nolock) on roaafiliado = afiid 
--							  INNER JOIN  (SELECT CASE WHEN  rdnfechainicionovedad  = MAX(rdnfechainicionovedad )
--											OVER (PARTITION BY   redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante 
--												 ) THEN rdnfechainicionovedad  ELSE rdnFechaFinNovedad END AS rdnfechainicionovedad 
--													,regTipoIdentificacionAportante,regNumeroIdentificacionAportante, redid ,
--																				 redTipoIdentificacionCotizante,	redNumeroIdentificacionCotizante,
--																				 rdnTipoNovedad,	rdnAccionNovedad, rdnFechaFinNovedad

--																			  FROM [pila].[RegistroDetallado]  
--																		INNER JOIN [pila].[RegistroGeneral]   
--																				ON redRegistroGeneral = regid 
--																		inner join [pila].[RegistroDetalladoNovedad]  
--																				ON rdnRegistroDetallado = redid   
--												 where 	redNovSLN = 'X' OR redNovIGE = 'X' OR redNovLMA = 'X'  ) x  on x.regTipoIdentificacionAportante = pe.perTipoIdentificacion
--					 												 and x.regNumeroIdentificacionAportante = pe.perNumeroIdentificacion
--																	 and x.redTipoIdentificacionCotizante = pt.perTipoIdentificacion
--					 												 and x.redNumeroIdentificacionCotizante = pt.perNumeroIdentificacion
--										AND LEFT (c.carPeriodoDeuda ,7) = left(rdnFechaInicioNovedad,7)							 
--							--AND carTipoLineaCobro = 'C6'
--							AND carTipoLineaCobro = 'C6'
--							AND cadEstadoOperacion = 'VIGENTE'
--							 order by pe.perNumeroIdentificacion asc

--							 INSERT INTO carteradependienteControlAud
--							 SELECT DISTINCT cd.cadDeudaPresunta, cd.cadEstadoOperacion, cd.cadCartera,cd.cadPersona,cd.cadDeudaReal,cd.cadAgregadoManual,cd.cadId
--							 ,'validacionNodedadDifRetiroC6' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod, 0 AS Ejecucion
--							FROM Cartera c
--							INNER JOIN Persona p ON c.carpersona = p.perid 
--							INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--							INNER JOIN persona pd ON pd.perid = cd.cadPersona
--							INNER JOIN #ValidacionNovedadesDifRetC6 x ON pd.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--							AND pd.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--							AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--							AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--							AND c.carPeriodoDeuda = x.carPeriodoDeuda

--							-- SELECT DISTINCT x.*,cd.* ,'validacionNodedadDifRetiroC6' as descripcion,GETDATE() as fechaMod
--							UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--							FROM Cartera c
--							INNER JOIN Persona p ON c.carpersona = p.perid 
--							INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--							INNER JOIN persona pd ON pd.perid = cd.cadPersona
--							INNER JOIN #ValidacionNovedadesDifRetC6 x ON pd.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--							AND pd.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--							AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--							AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--							AND c.carPeriodoDeuda = x.carPeriodoDeuda



--							INSERT INTO core.aud.Revision
--							SELECT cadId AS revIp, 
--									'CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REVNOMBREUSUARIO, 
--									NULL AS revRequestId, 
--									datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--							FROM carteradependienteControlAud T
--							WHERE FechaModificacion = CAST (DBO.GetLocalDate() AS date) AND descripcion = 'validacionNodedadDifRetiroC6'

--							INSERT INTO core.aud.RevisionEntidad
--							SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REEENTITYCLASSNAME, 
--								   1 AS REEREVISIONTYPE, 
--								   T.REVTIMESTAMP AS REVTIMESTAMP, 
--								   T.REVID AS REEREVISION
--							FROM core.aud.Revision T WITH(NOLOCK)
--							WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--								  AND T.REVID NOT IN
--							(
--								SELECT REEREVISION
--								FROM core.aud.RevisionEntidad
--							);



--							INSERT INTO CORE.aud.CARTERADEPENDIENTE_AUD
--							SELECT  cadId, 
--									R.REVID AS REV, 
--									1 AS REVTYPE, 
--									cadDeudaPresunta, 
--									cadEstadoOperacion, 
--									cadCartera, 
--									cadPersona, 
--									cadDeudaReal, 
--									cadAgregadoManual
--							FROM carteradependienteControlAud P
--								INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.cadId = R.revIp
--							WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--							AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionNodedadDifRetiroC6' AND Ejecucion = 0
--							ORDER BY P.cadId;


--							UPDATE carteradependienteControlAud SET Ejecucion = 1 WHERE ejecucion = 0


							
--						/*3. *PARA NOVEDAD DE RETIRO CON ESTADO ACTIVO PERO PARA LA LINEA DE COBRO LC1*/
--						SELECT distinct  x.* , c.carPeriodoDeuda, roafecharetiro, roaFechaAfiliacion,
--							(SELECT roaEstadoAfiliado 
--							   FROM VW_EstadoAfiliacionPersonaEmpresa 
--							  WHERE perTipoIdentificacion =  ptr.pertipoidentificacion  
--								AND perNumeroIdentificacion =  ptr.pernumeroidentificacion 
--								AND perTipoIdentificacionEmpleador =  pemp.pertipoidentificacion 
--								AND perNumeroIdentificacionEmpleador =  pemp.pernumeroidentificacion ) AS estado
--					  , cadEstadoOperacion, cadDeudaPresunta, 
--					  (carDeudaPresuntaUnitaria - cadDeudaPresunta) AS restaUnitariaVSpresunta , cardeudapresunta, carDeudaPresuntaUnitaria, 
--					  carTipolineacobro, carEstadoOperacion
--					into #carterasActualizarPorNoVRET
--							FROM cartera c 
--					  INNER JOIN persona pemp on pemp.perid = carpersona 
--					  inner join empresa e on e.empPersona = pemp.perid 
--					  inner join CarteraDependiente cd on cd.cadcartera = carid 
--					  inner join persona ptr on ptr.perid = cadPersona
--					   inner join afiliado on afipersona = ptr.perId
--					   inner join rolafiliado on roaAfiliado = afiid 
--					   left join Empleador em on em.empId = roaEmpleador and em.empempresa = e.empid 
 
--					  INNER JOIN  (SELECT CASE WHEN  rdnfechainicionovedad  = MAX( rdnfechainicionovedad ) 
--									OVER (PARTITION BY   redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante 
--										 ) THEN rdnfechainicionovedad  ELSE NULL END AS rdnfechainicionovedad 
--											,regTipoIdentificacionAportante,regNumeroIdentificacionAportante, redid ,
--																		 redTipoIdentificacionCotizante,	redNumeroIdentificacionCotizante,
--																		 rdnTipoNovedad,	rdnAccionNovedad
--																	  FROM [pila].[RegistroDetallado]  
--																INNER JOIN [pila].[RegistroGeneral]   
--																		ON redRegistroGeneral = regid 
--																inner join [pila].[RegistroDetalladoNovedad]  
--																		ON rdnRegistroDetallado = redid   
--										 where rdnTipoNovedad='NOVEDAD_RET'	--redNovRetiro = 'X'
--										 ) x 
--										ON   ptr.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--										  AND ptr.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--										  AND pemp.pertipoidentificacion  = x.regTipoIdentificacionAportante
--										  AND pemp.pernumeroidentificacion = x.regNumeroIdentificacionAportante 

--							----REINGRESO

--						LEFT JOIN  (SELECT CASE WHEN  rdnfechainicionovedad  = MAX( ISNULL(rdnfechainicionovedad,rdnfechafinnovedad) ) 
--						OVER (PARTITION BY   redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,regTipoIdentificacionAportante,
--									regNumeroIdentificacionAportante 
--								) THEN rdnfechainicionovedad  ELSE rdnFechaFinNovedad END AS rdnfechainicionovedad 
--								,regTipoIdentificacionAportante,regNumeroIdentificacionAportante, redid ,
--																redTipoIdentificacionCotizante,	redNumeroIdentificacionCotizante 
--															FROM [pila].[RegistroDetallado]  
--													INNER JOIN [pila].[RegistroGeneral]   
--															ON redRegistroGeneral = regid 
--													inner join [pila].[RegistroDetalladoNovedad]  
--															ON rdnRegistroDetallado = redid   
--								WHERE rdnfechainicionovedad IS NOT NULL 
--								AND rdnTipoNovedad ='NOVEDAD_ING'
--						--  and 
--								--and redNumeroIdentificacionCotizante  ='1005625954' 
--								--and regNumeroIdentificacionAportante ='901066394'
--							) REINTEGRO
--							ON   ptr.pertipoidentificacion  = REINTEGRO.redTipoIdentificacionCotizante
--								AND ptr.pernumeroidentificacion = REINTEGRO.redNumeroIdentificacionCotizante 
--								AND pemp.pertipoidentificacion  = REINTEGRO.regTipoIdentificacionAportante
--								AND pemp.pernumeroidentificacion = REINTEGRO.regNumeroIdentificacionAportante 
--								AND  left(convert(date,reintegro.rdnfechainicionovedad),7) >= LEFT (carPeriodoDeuda ,7)

--							where x.rdnfechainicionovedad is not null  
--							AND LEFT (c.carPeriodoDeuda ,7) >= left(x.rdnFechaInicioNovedad,7) 
--							AND cadEstadoOperacion ='VIGENTE'
--							AND carTipolineacobro = 'LC1'
--								and  (select roaEstadoAfiliado from VW_EstadoAfiliacionPersonaEmpresa where perTipoIdentificacion =  ptr.pertipoidentificacion  and 
--													perNumeroIdentificacion =  ptr.pernumeroidentificacion and perTipoIdentificacionEmpleador =  pemp.pertipoidentificacion 
--													and perNumeroIdentificacionEmpleador =  pemp.pernumeroidentificacion )  = 'ACTIVO'
--								--and carDeudaPresunta>0 ---and cadEstadoOperacion = 'vigente'
--								and     (x.rdnfechainicionovedad >= roaFechaIngreso and roafecharetiro IS NULL
--							or     x.rdnfechainicionovedad >= roaFechaAfiliacion and roafecharetiro IS NULL)
--							order by regNumeroIdentificacionAportante desc

--						 /*PARA INSERTAR A TABLA CONTROL Y ACTUALIZAR CASO LC1*/
--						 INSERT INTO carteradependienteControlAud
--						SELECT DISTINCT cd.*,'validacionNovedadRetiroLC1Activo' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod, 0 AS Ejecucion
--						FROM Cartera c
--						INNER JOIN Persona p ON c.carpersona = p.perid 
--						INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--						INNER JOIN persona pd ON pd.perid = cd.cadPersona
--						INNER JOIN #carterasActualizarPorNoVRET x ON pd.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--						AND pd.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--						AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--						AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--						AND c.carPeriodoDeuda = x.carPeriodoDeuda

--						 /*PARA ACTUALIZAR EN CORE*/
--						--SELECT DISTINCT x.* 	  
--						UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--						FROM Cartera c
--						INNER JOIN Persona p ON c.carpersona = p.perid 
--						INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--						INNER JOIN persona pd ON pd.perid = cd.cadPersona
--						INNER JOIN #carterasActualizarPorNoVRET x ON pd.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--						AND pd.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--						AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--						AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--						AND c.carPeriodoDeuda = x.carPeriodoDeuda

--						INSERT INTO core.aud.Revision
--						SELECT cadId AS revIp, 
--								'CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REVNOMBREUSUARIO, 
--								NULL AS revRequestId, 
--								datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--						FROM carteradependienteControlAud T
--						WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionNovedadRetiroLC1Activo' AND Ejecucion = 0

--						INSERT INTO core.aud.RevisionEntidad
--						SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REEENTITYCLASSNAME, 
--							   1 AS REEREVISIONTYPE, 
--							   T.REVTIMESTAMP AS REVTIMESTAMP, 
--							   T.REVID AS REEREVISION
--						FROM core.aud.Revision T WITH(NOLOCK)
--						WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--							  AND T.REVID NOT IN
--						(
--							SELECT REEREVISION
--							FROM core.aud.RevisionEntidad
--						);

--						INSERT INTO CORE.aud.CARTERADEPENDIENTE_AUD
--						SELECT  cadId, 
--								R.REVID AS REV, 
--								1 AS REVTYPE, 
--								cadDeudaPresunta, 
--								cadEstadoOperacion, 
--								cadCartera, 
--								cadPersona, 
--								cadDeudaReal, 
--								cadAgregadoManual
--						FROM carteradependienteControlAud P
--							INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.cadId = R.revIp
--						WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--						AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionNovedadRetiroLC1Activo' AND Ejecucion = 0
--						ORDER BY P.cadId;


--						UPDATE carteradependienteControlAud SET Ejecucion = 1 WHERE Ejecucion = 0

--						/*4. PARA NOVEDAD DE RETIRO CON ESTADO ACTIVO PERO PARA LA LINEA DE COBRO C6*/
--					SELECT distinct  x.* , c.carPeriodoDeuda, roafecharetiro, roaFechaAfiliacion,
--							(SELECT roaEstadoAfiliado 
--							   FROM VW_EstadoAfiliacionPersonaEmpresa 
--							  WHERE perTipoIdentificacion =  ptr.pertipoidentificacion  
--								AND perNumeroIdentificacion =  ptr.pernumeroidentificacion 
--								AND perTipoIdentificacionEmpleador =  pemp.pertipoidentificacion 
--								AND perNumeroIdentificacionEmpleador =  pemp.pernumeroidentificacion ) AS estado
--					  , cadEstadoOperacion, cadDeudaPresunta, 
--					  (carDeudaPresuntaUnitaria - cadDeudaPresunta) AS restaUnitariaVSpresunta , cardeudapresunta, carDeudaPresuntaUnitaria, 
--					  carTipolineacobro, carEstadoOperacion
--					into #carterasActualizarPorNoVRETC6
--							FROM cartera c 
--					  INNER JOIN persona pemp on pemp.perid = carpersona 
--					  inner join empresa e on e.empPersona = pemp.perid 
--					  inner join CarteraDependiente cd on cd.cadcartera = carid 
--					  inner join persona ptr on ptr.perid = cadPersona
--					   inner join afiliado on afipersona = ptr.perId
--					   inner join rolafiliado on roaAfiliado = afiid 
--					   left join Empleador em on em.empId = roaEmpleador and em.empempresa = e.empid 
 
--					  INNER JOIN  (SELECT CASE WHEN  rdnfechainicionovedad  = MAX( rdnfechainicionovedad ) 
--									OVER (PARTITION BY   redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante 
--										 ) THEN rdnfechainicionovedad  ELSE NULL END AS rdnfechainicionovedad 
--											,regTipoIdentificacionAportante,regNumeroIdentificacionAportante, redid ,
--																		 redTipoIdentificacionCotizante,	redNumeroIdentificacionCotizante,
--																		 rdnTipoNovedad,	rdnAccionNovedad
--																	  FROM [pila].[RegistroDetallado]  
--																INNER JOIN [pila].[RegistroGeneral]   
--																		ON redRegistroGeneral = regid 
--																inner join [pila].[RegistroDetalladoNovedad]  
--																		ON rdnRegistroDetallado = redid   
--										 where rdnTipoNovedad='NOVEDAD_RET'	--redNovRetiro = 'X'
--										 ) x 
--										ON   ptr.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--										  AND ptr.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--										  AND pemp.pertipoidentificacion  = x.regTipoIdentificacionAportante
--										  AND pemp.pernumeroidentificacion = x.regNumeroIdentificacionAportante 

--							----REINGRESO

--						LEFT JOIN  (SELECT CASE WHEN  rdnfechainicionovedad  = MAX( ISNULL(rdnfechainicionovedad,rdnfechafinnovedad) ) 
--						OVER (PARTITION BY   redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,regTipoIdentificacionAportante,
--									regNumeroIdentificacionAportante 
--								) THEN rdnfechainicionovedad  ELSE rdnFechaFinNovedad END AS rdnfechainicionovedad 
--								,regTipoIdentificacionAportante,regNumeroIdentificacionAportante, redid ,
--																redTipoIdentificacionCotizante,	redNumeroIdentificacionCotizante 
--															FROM [pila].[RegistroDetallado]  
--													INNER JOIN [pila].[RegistroGeneral]   
--															ON redRegistroGeneral = regid 
--													inner join [pila].[RegistroDetalladoNovedad]  
--															ON rdnRegistroDetallado = redid   
--								WHERE rdnfechainicionovedad IS NOT NULL 
--								AND rdnTipoNovedad ='NOVEDAD_ING'
--						--  and 
--								--and redNumeroIdentificacionCotizante  ='1005625954' 
--								--and regNumeroIdentificacionAportante ='901066394'
--							) REINTEGRO
--							ON   ptr.pertipoidentificacion  = REINTEGRO.redTipoIdentificacionCotizante
--								AND ptr.pernumeroidentificacion = REINTEGRO.redNumeroIdentificacionCotizante 
--								AND pemp.pertipoidentificacion  = REINTEGRO.regTipoIdentificacionAportante
--								AND pemp.pernumeroidentificacion = REINTEGRO.regNumeroIdentificacionAportante 
--								AND  left(convert(date,reintegro.rdnfechainicionovedad),7) >= LEFT (carPeriodoDeuda ,7)

--							where x.rdnfechainicionovedad is not null  
--							AND LEFT (c.carPeriodoDeuda ,7) >= left(x.rdnFechaInicioNovedad,7) 
--							AND cadEstadoOperacion ='VIGENTE'
--							AND carTipolineacobro = 'C6'
--								and  (select roaEstadoAfiliado from VW_EstadoAfiliacionPersonaEmpresa where perTipoIdentificacion =  ptr.pertipoidentificacion  and 
--													perNumeroIdentificacion =  ptr.pernumeroidentificacion and perTipoIdentificacionEmpleador =  pemp.pertipoidentificacion 
--													and perNumeroIdentificacionEmpleador =  pemp.pernumeroidentificacion )  = 'ACTIVO'
--								--and carDeudaPresunta>0 ---and cadEstadoOperacion = 'vigente'
--								and     (x.rdnfechainicionovedad >= roaFechaIngreso and roafecharetiro IS NULL
--							or     x.rdnfechainicionovedad >= roaFechaAfiliacion and roafecharetiro IS NULL)
--							order by regNumeroIdentificacionAportante desc


--						 /*PARA ACTUALIZAR EN CORE*/

--						 /*INSERTAR A TABLA CONTROL*/
--						INSERT INTO carteradependienteControlAud
--						SELECT DISTINCT cd.*,'validacionNovedadRetiroC6Activo' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod, 0 AS Ejecucion
--						FROM Cartera c
--						INNER JOIN Persona p ON c.carpersona = p.perid 
--						INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--						INNER JOIN persona pd ON pd.perid = cd.cadPersona
--						INNER JOIN #carterasActualizarPorNoVRETC6 x ON pd.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--						AND pd.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--						AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--						AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--						AND c.carPeriodoDeuda = x.carPeriodoDeuda

--						--SELECT DISTINCT x.* 	  
--						UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--						FROM Cartera c
--						INNER JOIN Persona p ON c.carpersona = p.perid 
--						INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--						INNER JOIN persona pd ON pd.perid = cd.cadPersona
--						INNER JOIN #carterasActualizarPorNoVRETC6 x ON pd.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--						AND pd.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--						AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--						AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--						AND c.carPeriodoDeuda = x.carPeriodoDeuda

--						INSERT INTO core.aud.Revision
--						SELECT cadId AS revIp, 
--								'CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REVNOMBREUSUARIO, 
--								NULL AS revRequestId, 
--							   datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--						FROM carteradependienteControlAud T
--						WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionNovedadRetiroC6Activo' AND Ejecucion = 0

--						INSERT INTO core.aud.RevisionEntidad
--						SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REEENTITYCLASSNAME, 
--							   1 AS REEREVISIONTYPE, 
--							   T.REVTIMESTAMP AS REVTIMESTAMP, 
--							   T.REVID AS REEREVISION
--						FROM core.aud.Revision T WITH(NOLOCK)
--						WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--							  AND T.REVID NOT IN
--						(
--							SELECT REEREVISION
--							FROM core.aud.RevisionEntidad
--						);

--						INSERT INTO CORE.aud.CARTERADEPENDIENTE_AUD
--						SELECT  cadId, 
--								R.REVID AS REV, 
--								1 AS REVTYPE, 
--								cadDeudaPresunta, 
--								cadEstadoOperacion, 
--								cadCartera, 
--								cadPersona, 
--								cadDeudaReal, 
--								cadAgregadoManual
--						FROM carteradependienteControlAud P
--							INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.cadId = R.revIp
--						WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--						AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionNovedadRetiroC6Activo' AND Ejecucion = 0
--						ORDER BY P.cadId;

--						UPDATE carteradependienteControlAud SET Ejecucion = 1 WHERE Ejecucion = 0



--						/*NOVEDADES DE RETIRO CON ESTADO DIFERENTE ACTIVO*/
--							SELECT distinct  x.* , c.carPeriodoDeuda, roafecharetiro, roaFechaAfiliacion,
--									(SELECT roaEstadoAfiliado 
--									   FROM VW_EstadoAfiliacionPersonaEmpresa 
--									  WHERE perTipoIdentificacion =  ptr.pertipoidentificacion  
--										AND perNumeroIdentificacion =  ptr.pernumeroidentificacion 
--										AND perTipoIdentificacionEmpleador =  pemp.pertipoidentificacion 
--										AND perNumeroIdentificacionEmpleador =  pemp.pernumeroidentificacion ) AS estado
--							  , cadEstadoOperacion, cadDeudaPresunta, 
--							  (carDeudaPresuntaUnitaria - cadDeudaPresunta) AS restaUnitariaVSpresunta , cardeudapresunta, carDeudaPresuntaUnitaria, 
--							  carTipolineacobro, carEstadoOperacion

--							into  #cruceconretirosNOACTIVOSLC1
--									FROM cartera c 
--							  INNER JOIN persona pemp on pemp.perid = carpersona 
--							  inner join empresa e on e.empPersona = pemp.perid 
--							  inner join CarteraDependiente cd on cd.cadcartera = carid 
--							  inner join persona ptr on ptr.perid = cadPersona
--							  left join afiliado on afipersona = ptr.perId
--							  left join rolafiliado on roaAfiliado = afiid 
--							  left join Empleador em on em.empId = roaEmpleador and em.empempresa = e.empid 
 
--							  INNER JOIN  (SELECT CASE WHEN  rdnfechainicionovedad  = MAX( rdnfechainicionovedad ) 
--											OVER (PARTITION BY   redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante 
--												 ) THEN rdnfechainicionovedad  ELSE NULL END AS rdnfechainicionovedad 
--													,regTipoIdentificacionAportante,regNumeroIdentificacionAportante, redid ,
--																				 redTipoIdentificacionCotizante,	redNumeroIdentificacionCotizante,
--																				 rdnTipoNovedad,	rdnAccionNovedad
--																			  FROM [pila].[RegistroDetallado]  
--																		INNER JOIN [pila].[RegistroGeneral]   
--																				ON redRegistroGeneral = regid 
--																		inner join [pila].[RegistroDetalladoNovedad]  
--																				ON rdnRegistroDetallado = redid   
													
--												 where 	redNovRetiro = 'X' 
--												 --and redNumeroIdentificacionCotizante = '10768586' 
--												 and rdnTipoNovedad='NOVEDAD_RET') x 
--												ON   ptr.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--												  AND ptr.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--												  AND pemp.pertipoidentificacion  = x.regTipoIdentificacionAportante
--												  AND pemp.pernumeroidentificacion = x.regNumeroIdentificacionAportante 
--											  where x.rdnfechainicionovedad is not null 
 
--							AND LEFT (c.carPeriodoDeuda ,7) >= left(rdnFechaInicioNovedad,7) 
--							AND cadEstadoOperacion ='VIGENTE'
--							--AND pemp.pernumeroidentificacion in ('901198010')
--							--AND ptr.pernumeroidentificacion in ('73570472')
--							--AND carTipolineacobro = 'C6'
--							AND carTipolineacobro = 'LC1'
--							 and  (select roaEstadoAfiliado from VW_EstadoAfiliacionPersonaEmpresa where perTipoIdentificacion =  ptr.pertipoidentificacion  and 
--												 perNumeroIdentificacion =  ptr.pernumeroidentificacion and perTipoIdentificacionEmpleador =  pemp.pertipoidentificacion 
--												   and perNumeroIdentificacionEmpleador =  pemp.pernumeroidentificacion )  <> 'ACTIVO'
--							and rdnTipoNovedad ='NOVEDAD_RET'
--							order by redNumeroIdentificacionCotizante desc


--							INSERT INTO carteradependienteControlAud
--							SELECT DISTINCT cd.* ,'validacionaNOVRETDifActivoLC1' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod, 0 AS Ejecucion
--							FROM Cartera c
--							INNER JOIN Persona p ON c.carpersona = p.perid 
--							INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--							INNER JOIN persona pd ON pd.perid = cd.cadPersona
--							INNER JOIN #cruceconretirosNOACTIVOSLC1 x ON pd.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--							AND pd.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--							AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--							AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--							AND c.carPeriodoDeuda = x.carPeriodoDeuda

--							--SELECT DISTINCT x.*,cd.* ,'validacionaNOVRETDifActivoLC1' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod  
--							UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--							FROM Cartera c
--							INNER JOIN Persona p ON c.carpersona = p.perid 
--							INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--							INNER JOIN persona pd ON pd.perid = cd.cadPersona
--							INNER JOIN #cruceconretirosNOACTIVOSLC1 x ON pd.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--							AND pd.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--							AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--							AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--							AND c.carPeriodoDeuda = x.carPeriodoDeuda



--							INSERT INTO core.aud.Revision
--							SELECT cadId AS revIp, 
--									'CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REVNOMBREUSUARIO, 
--									NULL AS revRequestId, 
--									datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--							FROM carteradependienteControlAud T
--							WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionaNOVRETDifActivoLC1' AND Ejecucion = 0

--							INSERT INTO core.aud.RevisionEntidad
--							SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REEENTITYCLASSNAME, 
--								   1 AS REEREVISIONTYPE, 
--								   T.REVTIMESTAMP AS REVTIMESTAMP, 
--								   T.REVID AS REEREVISION
--							FROM core.aud.Revision T WITH(NOLOCK)
--							WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--								  AND T.REVID NOT IN
--							(
--								SELECT REEREVISION
--								FROM core.aud.RevisionEntidad
--							);

--							INSERT INTO CORE.aud.CARTERADEPENDIENTE_AUD
--							SELECT  cadId, 
--									R.REVID AS REV, 
--									1 AS REVTYPE, 
--									cadDeudaPresunta, 
--									cadEstadoOperacion, 
--									cadCartera, 
--									cadPersona, 
--									cadDeudaReal, 
--									cadAgregadoManual
--							FROM carteradependienteControlAud P
--								INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.cadId = R.revIp
--							WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--							AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionaNOVRETDifActivoLC1' AND Ejecucion = 0
--							ORDER BY P.cadId;


--							UPDATE carteradependienteControlAud SET Ejecucion = 1 WHERE ejecucion = 0

--							/*NOVEDADES DE RETIRO CON ESTADO DIFERENTE ACTIVO*/
--							SELECT distinct  x.* , c.carPeriodoDeuda, roafecharetiro, roaFechaAfiliacion,
--									(SELECT roaEstadoAfiliado 
--									   FROM VW_EstadoAfiliacionPersonaEmpresa 
--									  WHERE perTipoIdentificacion =  ptr.pertipoidentificacion  
--										AND perNumeroIdentificacion =  ptr.pernumeroidentificacion 
--										AND perTipoIdentificacionEmpleador =  pemp.pertipoidentificacion 
--										AND perNumeroIdentificacionEmpleador =  pemp.pernumeroidentificacion ) AS estado
--							  , cadEstadoOperacion, cadDeudaPresunta, 
--							  (carDeudaPresuntaUnitaria - cadDeudaPresunta) AS restaUnitariaVSpresunta , cardeudapresunta, carDeudaPresuntaUnitaria, 
--							  carTipolineacobro, carEstadoOperacion

--							into  #cruceconretirosNOACTIVOSC6
--									FROM cartera c 
--							  INNER JOIN persona pemp on pemp.perid = carpersona 
--							  inner join empresa e on e.empPersona = pemp.perid 
--							  inner join CarteraDependiente cd on cd.cadcartera = carid 
--							  inner join persona ptr on ptr.perid = cadPersona
--							  left join afiliado on afipersona = ptr.perId
--							  left join rolafiliado on roaAfiliado = afiid 
--							  left join Empleador em on em.empId = roaEmpleador and em.empempresa = e.empid 
 
--							  INNER JOIN  (SELECT CASE WHEN  rdnfechainicionovedad  = MAX( rdnfechainicionovedad ) 
--											OVER (PARTITION BY   redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante,regTipoIdentificacionAportante,regNumeroIdentificacionAportante 
--												 ) THEN rdnfechainicionovedad  ELSE NULL END AS rdnfechainicionovedad 
--													,regTipoIdentificacionAportante,regNumeroIdentificacionAportante, redid ,
--																				 redTipoIdentificacionCotizante,	redNumeroIdentificacionCotizante,
--																				 rdnTipoNovedad,	rdnAccionNovedad
--																			  FROM [pila].[RegistroDetallado]  
--																		INNER JOIN [pila].[RegistroGeneral]   
--																				ON redRegistroGeneral = regid 
--																		inner join [pila].[RegistroDetalladoNovedad]  
--																				ON rdnRegistroDetallado = redid   
													
--												 where 	redNovRetiro = 'X' 
--												 --and redNumeroIdentificacionCotizante = '10768586' 
--												 and rdnTipoNovedad='NOVEDAD_RET') x 
--												ON   ptr.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--												  AND ptr.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--												  AND pemp.pertipoidentificacion  = x.regTipoIdentificacionAportante
--												  AND pemp.pernumeroidentificacion = x.regNumeroIdentificacionAportante 
--											  where x.rdnfechainicionovedad is not null 
 
--							AND LEFT (c.carPeriodoDeuda ,7) >= left(rdnFechaInicioNovedad,7) 
--							AND cadEstadoOperacion ='VIGENTE'
--							--AND pemp.pernumeroidentificacion in ('901198010')
--							--AND ptr.pernumeroidentificacion in ('73570472')
--							--AND carTipolineacobro = 'C6'
--							AND carTipolineacobro = 'C6'
--							 and  (select roaEstadoAfiliado from VW_EstadoAfiliacionPersonaEmpresa where perTipoIdentificacion =  ptr.pertipoidentificacion  and 
--												 perNumeroIdentificacion =  ptr.pernumeroidentificacion and perTipoIdentificacionEmpleador =  pemp.pertipoidentificacion 
--												   and perNumeroIdentificacionEmpleador =  pemp.pernumeroidentificacion )  <> 'ACTIVO'
--							and rdnTipoNovedad ='NOVEDAD_RET'
--							order by redNumeroIdentificacionCotizante desc

--							INSERT INTO carteradependienteControlAud
--							SELECT DISTINCT cd.* ,'validacionaNOVRETDifActivoC6' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod, 0 AS Ejecucion
--							FROM Cartera c
--							INNER JOIN Persona p ON c.carpersona = p.perid 
--							INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--							INNER JOIN persona pd ON pd.perid = cd.cadPersona
--							INNER JOIN #cruceconretirosNOACTIVOSC6 x ON pd.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--							AND pd.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--							AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--							AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--							AND c.carPeriodoDeuda = x.carPeriodoDeuda

--							--SELECT DISTINCT x.*,cd.* ,'validacionaNOVRETDifActivoC6' as descripcion,GETDATE() as fechaMod
--							UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--							FROM Cartera c
--							INNER JOIN Persona p ON c.carpersona = p.perid 
--							INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--							INNER JOIN persona pd ON pd.perid = cd.cadPersona
--							INNER JOIN #cruceconretirosNOACTIVOSC6 x ON pd.pertipoidentificacion  = x.redTipoIdentificacionCotizante
--							AND pd.pernumeroidentificacion = x.redNumeroIdentificacionCotizante 
--							AND p.pertipoidentificacion  = x.regTipoIdentificacionAportante
--							AND p.pernumeroidentificacion = x.regNumeroIdentificacionAportante
--							AND c.carPeriodoDeuda = x.carPeriodoDeuda

--							INSERT INTO core.aud.Revision
--							SELECT cadId AS revIp, 
--									'CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REVNOMBREUSUARIO, 
--									NULL AS revRequestId, 
--								   datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--							FROM carteradependienteControlAud T
--							WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionaNOVRETDifActivoC6' AND Ejecucion = 0

--							INSERT INTO core.aud.RevisionEntidad
--							SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REEENTITYCLASSNAME, 
--								   1 AS REEREVISIONTYPE, 
--								   T.REVTIMESTAMP AS REVTIMESTAMP, 
--								   T.REVID AS REEREVISION
--							FROM core.aud.Revision T WITH(NOLOCK)
--							WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--								  AND T.REVID NOT IN
--							(
--								SELECT REEREVISION
--								FROM core.aud.RevisionEntidad
--							);

--							INSERT INTO CORE.aud.CARTERADEPENDIENTE_AUD
--							SELECT  cadId, 
--									R.REVID AS REV, 
--									1 AS REVTYPE, 
--									cadDeudaPresunta, 
--									cadEstadoOperacion, 
--									cadCartera, 
--									cadPersona, 
--									cadDeudaReal, 
--									cadAgregadoManual
--							FROM carteradependienteControlAud P
--								INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.cadId = R.revIp
--							WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--							AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionaNOVRETDifActivoC6' AND Ejecucion = 0
--							ORDER BY P.cadId;


--							UPDATE carteradependienteControlAud SET Ejecucion = 1 WHERE ejecucion = 0



--							/*5 . SCRIPT PARA novedades de retiro registradas del proceso de gestión de deuda por persona  */
--									SELECT pertra.perNumeroIdentificacion AS NumeroIdentificacionTrabajador, 
--										   pertra.perTipoIdentificacion AS TipoIdentificacionTrabajador, 
--										   carPeriodoDeuda, 
--										   canFechaInicio as FechaInicioNovedad, 
--										   canFechaFin as FechaFinNovedad, 
--										   canTipoNovedad as TipoNovedad,
--										   peremp.perNumeroIdentificacion as NumeroIdentificacionEmpresa,
--										   peremp.perTipoIdentificacion as TipoIdentificacionEmpresa,
--										   can.canFechaCreacion,
--										   LEFT(carPeriodoDeuda, 7) as PeriodoDeuda,
--										   LEFT(canFechaInicio, 7) as FechaInicio,
--										   CAD.*
--										   --UPDATE cad SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--									into #gestionpersonaNovRet
--									FROM CarteraNovedad can
--										 INNER JOIN persona pertra ON perid = canPersona
--										 INNER JOIN CarteraDependiente cad ON cadPersona = perId
--										 INNER JOIN Cartera car ON cadCartera = carId
--										 INNER JOIN Persona peremp ON peremp.perId = carPersona
--									WHERE cadEstadoOperacion = 'VIGENTE'
--										  AND canTipoNovedad = 'RETIRO_TRABAJADOR_DEPENDIENTE'
--										 AND LEFT(carPeriodoDeuda, 7) >= LEFT(canFechaInicio, 7)
--										 --and pertra.perNumeroIdentificacion = '1005568888'

--									/*INSERTAR A TABLA CONTROL*/
--									INSERT INTO carteradependienteControlAud
--									SELECT DISTINCT cd.* ,'validacionGestionDeudaPersonaNOVRET' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod, 0 AS Ejecucion
--									FROM Cartera c
--									INNER JOIN Persona p ON c.carpersona = p.perid 
--									INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--									INNER JOIN persona pd ON pd.perid = cd.cadPersona
--									INNER JOIN #gestionpersonaNovRet x ON pd.pertipoidentificacion  = x.TipoIdentificacionTrabajador
--									AND pd.pernumeroidentificacion = x.NumeroIdentificacionTrabajador 
--									AND p.pertipoidentificacion  = x.TipoIdentificacionEmpresa
--									AND p.pernumeroidentificacion = x.NumeroIdentificacionEmpresa
--									AND c.carPeriodoDeuda = x.carPeriodoDeuda

--									--SELECT DISTINCT x.*,cd.* ,'validacionGestionDeudaPersona' as descripcion,GETDATE() as fechaMod
--									UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--									FROM Cartera c
--									INNER JOIN Persona p ON c.carpersona = p.perid 
--									INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--									INNER JOIN persona pd ON pd.perid = cd.cadPersona
--									INNER JOIN #gestionpersonaNovRet x ON pd.pertipoidentificacion  = x.TipoIdentificacionTrabajador
--									AND pd.pernumeroidentificacion = x.NumeroIdentificacionTrabajador 
--									AND p.pertipoidentificacion  = x.TipoIdentificacionEmpresa
--									AND p.pernumeroidentificacion = x.NumeroIdentificacionEmpresa
--									AND c.carPeriodoDeuda = x.carPeriodoDeuda

--									INSERT INTO core.aud.Revision
--									SELECT cadId AS revIp, 
--											'CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REVNOMBREUSUARIO, 
--											NULL AS revRequestId,         
--											datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--									FROM carteradependienteControlAud T
--									WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionGestionDeudaPersonaNOVRET' AND Ejecucion = 0

--									INSERT INTO core.aud.RevisionEntidad
--									SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REEENTITYCLASSNAME, 
--										   1 AS REEREVISIONTYPE, 
--										   T.REVTIMESTAMP AS REVTIMESTAMP, 
--										   T.REVID AS REEREVISION
--									FROM core.aud.Revision T WITH(NOLOCK)
--									WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--										  AND T.REVID NOT IN
--									(
--										SELECT REEREVISION
--										FROM core.aud.RevisionEntidad
--									);

--									INSERT INTO CORE.aud.CARTERADEPENDIENTE_AUD
--									SELECT  cadId, 
--											R.REVID AS REV, 
--											1 AS REVTYPE, 
--											cadDeudaPresunta, 
--											cadEstadoOperacion, 
--											cadCartera, 
--											cadPersona, 
--											cadDeudaReal, 
--											cadAgregadoManual
--									FROM carteradependienteControlAud P
--										INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.cadId = R.revIp
--									WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--									AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionGestionDeudaPersonaNOVRET' AND Ejecucion = 0
--									ORDER BY P.cadId;


--									UPDATE carteradependienteControlAud SET Ejecucion = 1 WHERE Ejecucion = 0

--									/*SCRIPT PARA novedades diferente a retiro registradas del proceso de gestión de deuda por persona */
--									SELECT pertra.perNumeroIdentificacion AS NumeroIdentificacionTrabajador, 
--										   pertra.perTipoIdentificacion AS TipoIdentificacionTrabajador, 
--										   cad.cadEstadoOperacion,
--										   cad.cadDeudaPresunta,
--										   carPeriodoDeuda, 
--										   canFechaInicio as FechaInicioNovedad, 
--										   canFechaFin as FechaFinNovedad, 
--										   canTipoNovedad as TipoNovedad,
--										   peremp.perNumeroIdentificacion as NumeroIdentificacionEmpresa,
--										   peremp.perTipoIdentificacion as TipoIdentificacionEmpresa,
--										   canFechaCreacion
--										   --UPDATE cad SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--										   into #gestiónpersonaNovDiffRet
--									FROM CarteraNovedad
--										 INNER JOIN persona pertra ON perid = canPersona
--										 INNER JOIN CarteraDependiente cad ON cadPersona = perId
--										 INNER JOIN Cartera car ON cadCartera = carId
--										 INNER JOIN Persona peremp ON peremp.perId = carPersona
--									WHERE cadEstadoOperacion = 'VIGENTE'
--										  AND canTipoNovedad IN('INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL',
--										  'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL', 
--										  'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL', 
--										  'VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL', 
--										  'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL')
--										 AND carPeriodoDeuda BETWEEN canFechaInicio AND canFechaFin;

--									INSERT INTO carteradependienteControlAud
--									SELECT DISTINCT cd.* ,'validacionGestionDeudaPersonaNovdifRetiro' as descripcion,CAST (DBO.GetLocalDate() AS date)  as fechaMod, 0 AS Ejecucion
--									FROM Cartera c
--									INNER JOIN Persona p ON c.carpersona = p.perid 
--									INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--									INNER JOIN persona pd ON pd.perid = cd.cadPersona
--									INNER JOIN #gestiónpersonaNovDiffRet x ON pd.pertipoidentificacion  = x.TipoIdentificacionTrabajador
--									AND pd.pernumeroidentificacion = x.NumeroIdentificacionTrabajador 
--									AND p.pertipoidentificacion  = x.TipoIdentificacionEmpresa
--									AND p.pernumeroidentificacion = x.NumeroIdentificacionEmpresa
--									AND c.carPeriodoDeuda = x.carPeriodoDeuda

--									--SELECT DISTINCT x.*,cd.* ,'validacionGestionDeudaPersonaNovdifRetiro' as descripcion,CAST (DBO.GetLocalDate() AS date)  as fechaMod
--									UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--									FROM Cartera c
--									INNER JOIN Persona p ON c.carpersona = p.perid 
--									INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--									INNER JOIN persona pd ON pd.perid = cd.cadPersona
--									INNER JOIN #gestiónpersonaNovDiffRet x ON pd.pertipoidentificacion  = x.TipoIdentificacionTrabajador
--									AND pd.pernumeroidentificacion = x.NumeroIdentificacionTrabajador 
--									AND p.pertipoidentificacion  = x.TipoIdentificacionEmpresa
--									AND p.pernumeroidentificacion = x.NumeroIdentificacionEmpresa
--									AND c.carPeriodoDeuda = x.carPeriodoDeuda

--									INSERT INTO core.aud.Revision
--									SELECT cadId AS revIp, 
--											'CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REVNOMBREUSUARIO, 
--											NULL AS revRequestId, 
--											datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--									FROM carteradependienteControlAud T
--									WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionGestionDeudaPersonaNovdifRetiro' AND Ejecucion = 0

--									INSERT INTO core.aud.RevisionEntidad
--									SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REEENTITYCLASSNAME, 
--										   1 AS REEREVISIONTYPE, 
--										   T.REVTIMESTAMP AS REVTIMESTAMP, 
--										   T.REVID AS REEREVISION
--									FROM core.aud.Revision T WITH(NOLOCK)
--									WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--										  AND T.REVID NOT IN
--									(
--										SELECT REEREVISION
--										FROM core.aud.RevisionEntidad
--									);

--									INSERT INTO CORE.aud.CARTERADEPENDIENTE_AUD
--									SELECT  cadId, 
--											R.REVID AS REV, 
--											1 AS REVTYPE, 
--											cadDeudaPresunta, 
--											cadEstadoOperacion, 
--											cadCartera, 
--											cadPersona, 
--											cadDeudaReal, 
--											cadAgregadoManual
--									FROM carteradependienteControlAud P
--										INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.cadId = R.revIp
--									WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--									AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'validacionGestionDeudaPersonaNovdifRetiro' AND Ejecucion = 0
--									ORDER BY P.cadId;

--									UPDATE carteradependienteControlAud SET Ejecucion = 1 WHERE ejecucion = 0

--									/*AJUSTE DE AGREGAR VALIDACIÓN NOVEDAD PRESENCIAL*/
--									/*VALIDACIÓN DE NOVEDADES DE RETIRO PARA PRESENCIAL*/
--								 SELECT per.perNumeroIdentificacion as NumeroIdentificacionEmpresa,
--										per.perTipoIdentificacion as TipoIdentificacionEmpresa,
--										cad.cadEstadoOperacion,			
--										car.carPeriodoDeuda,
--										car.carDeudaPresunta,
--										car.carTipoLineaCobro,
--										car.carTipoAccionCobro,
--										CAR.carPersona,
--										Novedades.perNumeroIdentificacion as NumeroIdentificacionTrabajador,
--										Novedades.perTipoIdentificacion as TipoIdentificacionTrabajador,
--										CASE WHEN roaFechaRetiro IS NOT NULL THEN cast (SUBSTRING(cast (roaFechaRetiro as varchar),1,11) as date) 
--										ELSE Novedades.solFechaCreacion END as roaFechaRetiro,
--										cad.cadCartera,
--										Novedades.solFechaCreacion
--								INTO #NovedadesRetiroPresencialLC1
--								--UPDATE CarteraDependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--								FROM Cartera car with (nolock)
--								INNER JOIN persona per with (nolock) on carpersona = perid 
--								INNER JOIN carteradependiente cad with (nolock) on cadcartera = carid
--								INNER JOIN Persona per1 with (nolock) on per1.perId = cad.cadpersona
--								INNER JOIN Empresa emp with (nolock) on emp.empPersona = per.perId
--								INNER JOIN Empleador empl with (nolock) on empl.empEmpresa = emp.empId
--								INNER JOIN Afiliado afi with (nolock) on afi.afiPersona = per1.perId
--								INNER JOIN RolAfiliado roa with (nolock) on roa.roaAfiliado = afi.afiId and roaEmpleador = empl.empId
--								INNER JOIN (SELECT perId,perNumeroIdentificacion,perTipoIdentificacion,solFechaCreacion--,nopFechaInicio,nopFechaFin
-- 											FROM Solicitud with (nolock)
--											INNER JOIN solicitudnovedad with (nolock) ON solid = snoSolicitudGlobal
--											INNER JOIN solicitudnovedadpersona with (nolock) ON snpSolicitudNovedad = snoid 			
--											INNER JOIN persona with (nolock) ON perid = snppersona  
--											WHERE Soltipotransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE')																			
--											GROUP BY  perid,perNumeroIdentificacion,solFechaCreacion,perTipoIdentificacion) AS Novedades ON Novedades.perId = cadPersona
--								WHERE  cadEstadoOperacion = 'VIGENTE'  and carPeriodoDeuda >= cast (SUBSTRING(cast (roaFechaRetiro as varchar),1,11) as date) and carTipoLineaCobro = 'LC1'
--								--and per1.perNumeroIdentificacion = '8603295'
--								GROUP BY cad.cadEstadoOperacion,car.carPeriodoDeuda,per.perNumeroIdentificacion,Novedades.perNumeroIdentificacion,car.carDeudaPresunta,car.carTipoLineaCobro,
--										car.carTipoAccionCobro,roaFechaRetiro,cad.cadCartera,per.perTipoIdentificacion,carPersona,Novedades.solFechaCreacion,Novedades.perTipoIdentificacion
--								order by NumeroIdentificacionTrabajador

--								INSERT INTO carteradependienteControlAud
--								SELECT DISTINCT cd.* ,'AjustePorvalidacionaNovedadesRetiroPresenciales' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod, 0 AS Ejecucion
--								FROM Cartera c
--								INNER JOIN Persona p ON c.carpersona = p.perid 
--								INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--								INNER JOIN persona pd ON pd.perid = cd.cadPersona
--								INNER JOIN #NovedadesRetiroPresencialLC1 x ON pd.pertipoidentificacion  = x.TipoIdentificacionTrabajador
--								AND pd.pernumeroidentificacion = x.NumeroIdentificacionTrabajador AND p.pertipoidentificacion  = x.TipoIdentificacionEmpresa
--								AND p.pernumeroidentificacion = x.NumeroIdentificacionEmpresa
--								AND c.carPeriodoDeuda = x.carPeriodoDeuda
--								--WHERE PD.perNumeroIdentificacion = '8603295'

--										/*ACTUALIZAR CORE*/
--								UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--								FROM Cartera c
--								INNER JOIN Persona p ON c.carpersona = p.perid 
--								INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--								INNER JOIN persona pd ON pd.perid = cd.cadPersona
--								INNER JOIN #NovedadesRetiroPresencialLC1 x ON pd.pertipoidentificacion  = x.TipoIdentificacionTrabajador
--								AND pd.pernumeroidentificacion = x.NumeroIdentificacionTrabajador AND p.pertipoidentificacion  = x.TipoIdentificacionEmpresa
--								AND p.pernumeroidentificacion = x.NumeroIdentificacionEmpresa
--								AND c.carPeriodoDeuda = x.carPeriodoDeuda

--								/*INSERT A REVISIÓN REVISIÓN*/
--								INSERT INTO core.aud.Revision
--								SELECT cadId AS revIp, 
--										'CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REVNOMBREUSUARIO, 
--										NULL AS revRequestId, 
--										datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--								FROM carteradependienteControlAud T
--								WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'AjustePorvalidacionaNovedadesRetiroPresenciales' AND  Ejecucion = 0;

--								/*INSERT A REVISÓN ENTIDAD*/
--								INSERT INTO core.aud.RevisionEntidad
--								SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REEENTITYCLASSNAME, 
--										1 AS REEREVISIONTYPE, 
--										T.REVTIMESTAMP AS REVTIMESTAMP, 
--										T.REVID AS REEREVISION
--								FROM core.aud.Revision T WITH(NOLOCK)
--								WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--										AND T.REVID NOT IN
--								(
--									SELECT REEREVISION
--									FROM core.aud.RevisionEntidad
--								);

--								INSERT INTO CORE.aud.CARTERADEPENDIENTE_AUD
--								SELECT  cadId, 
--										R.REVID AS REV, 
--										1 AS REVTYPE, 
--										cadDeudaPresunta, 
--										cadEstadoOperacion, 
--										cadCartera, 
--										cadPersona, 
--										cadDeudaReal, 
--										cadAgregadoManual
--								FROM carteradependienteControlAud P
--									INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.cadId = R.revIp
--								WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--								AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'AjustePorvalidacionaNovedadesRetiroPresenciales' AND Ejecucion = 0
--								ORDER BY P.cadId;

--								UPDATE carteradependienteControlAud SET	Ejecucion = 1 WHERE Ejecucion = 0


--								/*Validacion que el trabajdor no tiene afiliacion no formalizada*/
--						SELECT pertra.perNumeroIdentificacion as NumeroIdentificacionTrabajador, 
--							   pertra.perTipoIdentificacion as TipoIdentificacionTrabajador,
--							   peremp.perNumeroIdentificacion as NumeroIdentificacionEmpresa,
--							   peremp.perTipoIdentificacion as TipoIdentificacionEmpresa,
--							   cadEstadoOperacion,
--							   cadDeudaPresunta
--							   carDeudaPresunta,
--							   carPeriodoDeuda,
--							   roaEstadoAfiliado,
--							   carTipoLineaCobro
--					 	INTO #ValidacionNotieneAfiliacionNoformalizada
--						FROM carteradependiente cad
--							 INNER JOIN Persona pertra ON pertra.perId = cadPersona
--							 LEFT JOIN Afiliado afi ON afi.afiPersona = pertra.perId
--							 LEFT JOIN RolAfiliado roa ON roa.roaAfiliado = afi.afiId
--							 INNER JOIN Cartera car on car.carId = cadCartera
--							 INNER JOIN Persona peremp on peremp.perId = car.carPersona
--						WHERE roa.roaid IS NULL and cadEstadoOperacion = 'VIGENTE'

--						UNION
--						---adicionar las que faltaron por olga vega 2022-06-08
--							SELECT  pd.perNumeroIdentificacion as NumeroIdentificacionTrabajador, 
--							   pd.perTipoIdentificacion as TipoIdentificacionTrabajador,
--							   p.perNumeroIdentificacion as NumeroIdentificacionEmpresa,
--							   p.perTipoIdentificacion as TipoIdentificacionEmpresa,
--							   cadEstadoOperacion,
--							   cadDeudaPresunta
--							   carDeudaPresunta,
--							   carPeriodoDeuda,
--							   ROLAFILIADO.roaEstadoAfiliado,
--							   carTipoLineaCobro  
--						  FROM Cartera c
--					INNER JOIN Persona p ON c.carpersona = p.perid 
--					INNER JOIN EMPRESA E ON empPersona = P.PERID 
--					INNER JOIN EMPLEADOR EM ON EM.empEmpresa = E.empId
--					INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--					INNER JOIN persona pd ON pd.perid = cd.cadPersona
--					--INNER JOIN VW_EstadoAfiliacionPersonaEmpresa V 
--					--        ON pd.perId = v.perId
--					--	   AND em.empid = v.roaEmpleador
--					INNER JOIN Afiliado a on a.afipersona = pd.perid 
--					 LEFT JOIN RolAfiliado on roaAfiliado = a.afiid 
--					       AND RolAfiliado.roaEmpleador = em.empid 
--					     WHERE roaid is null 
--						 --AND carEstadoCartera = 'MOROSO'
--						 --AND carEstadoOperacion = 'VIGENTE'
--						   AND cadDeudaPresunta>0





--						 /*PARA INSERTAR A TABLA CONTROL Y ACTUALIZAR CASO LC1*/
--						 INSERT INTO carteradependienteControlAud
--						SELECT DISTINCT cd.*,'ValidacionNotieneAfiliacionNoformalizada' as descripcion,CAST (DBO.GetLocalDate() AS date) as fechaMod ,0 as Ejecucion 
--						FROM Cartera c
--						INNER JOIN Persona p ON c.carpersona = p.perid 
--						INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--						INNER JOIN persona pd ON pd.perid = cd.cadPersona
--						INNER JOIN #ValidacionNotieneAfiliacionNoformalizada x ON pd.pertipoidentificacion  = x.TipoIdentificacionTrabajador
--						AND pd.pernumeroidentificacion = x.NumeroIdentificacionTrabajador 
--						AND p.pertipoidentificacion  = x.TipoIdentificacionEmpresa
--						AND p.pernumeroidentificacion = x.NumeroIdentificacionEmpresa
--						AND c.carPeriodoDeuda = x.carPeriodoDeuda

--						/*ACTUALIZAR EN CORE*/
--						UPDATE carteradependiente SET cadDeudaPresunta = 0, cadestadooperacion ='NO_VIGENTE'
--						FROM Cartera c
--						INNER JOIN Persona p ON c.carpersona = p.perid 
--						INNER JOIN carteradependiente cd ON c.carid = cd.cadCartera
--						INNER JOIN persona pd ON pd.perid = cd.cadPersona
--						INNER JOIN #ValidacionNotieneAfiliacionNoformalizada x ON pd.pertipoidentificacion  = x.TipoIdentificacionTrabajador
--						AND pd.pernumeroidentificacion = x.NumeroIdentificacionTrabajador 
--						AND p.pertipoidentificacion  = x.TipoIdentificacionEmpresa
--						AND p.pernumeroidentificacion = x.NumeroIdentificacionEmpresa
--						AND c.carPeriodoDeuda = x.carPeriodoDeuda

--						INSERT INTO core.aud.Revision
--						SELECT cadId AS revIp, 
--								'CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REVNOMBREUSUARIO, 
--								NULL AS revRequestId, 
--								datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--						FROM carteradependienteControlAud T
--						WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'ValidacionNotieneAfiliacionNoformalizada' AND  Ejecucion = 0;

--						INSERT INTO core.aud.RevisionEntidad
--						SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERADEPENDIENTE_POR_VALIDACION' AS REEENTITYCLASSNAME, 
--								1 AS REEREVISIONTYPE, 
--								T.REVTIMESTAMP AS REVTIMESTAMP, 
--								T.REVID AS REEREVISION
--						FROM core.aud.Revision T WITH(NOLOCK)
--						WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--								AND T.REVID NOT IN
--						(
--							SELECT REEREVISION
--							FROM core.aud.RevisionEntidad
--						);

--						INSERT INTO CORE.aud.CARTERADEPENDIENTE_AUD
--						SELECT  cadId, 
--								R.REVID AS REV, 
--								1 AS REVTYPE, 
--								cadDeudaPresunta, 
--								cadEstadoOperacion, 
--								cadCartera, 
--								cadPersona, 
--								cadDeudaReal, 
--								cadAgregadoManual
--						FROM carteradependienteControlAud P
--							INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.cadId = R.revIp
--						WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERADEPENDIENTE_POR_VALIDACION'
--						AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'ValidacionNotieneAfiliacionNoformalizada' AND Ejecucion = 0
--						ORDER BY P.cadId;

--						UPDATE carteradependienteControlAud SET	Ejecucion = 1 WHERE Ejecucion = 0
--									                                   /*VALIDACIONES*/
--		 -------6. VALIDACION SUMATORIA DE TODOS LOS TRABAJADORES EN CERO Y CARTERA QUE DEBERIAN ESTAR AL DÍA

--									SELECT c.*,
--											--carDeudaPresunta,pertipoidentificacion,pernumeroidentificacion,perrazonsocial,
--											(SELECT SUM(caddeudapresunta)FROM carteradependiente WHERE cadcartera = carid) as suma
--									into #Validacioncontrol1
--									--update cartera set carDeudaPresunta =  0, carestadooperacion = 'NO_VIGENTE', CARESTADOCARTERA = 'AL_DIA'
--									FROM cartera c
--										 INNER JOIN persona ON perid = carPersona
--										 INNER JOIN CarteraDependiente ON cadCartera = CARID
--									WHERE carDeudaPresunta <>(SELECT SUM(caddeudapresunta)FROM carteradependiente WHERE cadcartera = carid)
--										  --  and carDeudaPresunta= 0
--									AND( SELECT SUM(caddeudapresunta)FROM carteradependiente WHERE cadcartera = carid) = 0
--									ORDER BY carpersona; 


--									/*INSERTAR EN TABLA CONTROL AUD*/
--									INSERT INTO CarteraControlAud
--										   SELECT carDeudaPresunta, 
--												  carEstadoCartera, 
--												  carEstadoOperacion, 
--												  carFechaCreacion, 
--												  carPersona, 
--												  carMetodo, 
--												  carPeriodoDeuda, 
--												  carRiesgoIncobrabilidad, 
--												  carTipoAccionCobro, 
--												  carTipoDeuda, 
--												  carTipoLineaCobro, 
--												  carTipoSolicitante, 
--												  carFechaAsignacionAccion, 
--												  carUsuarioTraspaso, 
--												  carId, 
--												  carDeudaPresuntaUnitaria, 
--												  'TODOS LOS TRABAJADORES EN CERO Y CARTERA AÚN VIGENTE' AS descripcion, 
--												  CAST(DBO.GetLocalDate() AS DATE) AS fechaMod,
--												  0 AS Ejecucion
--										   FROM #Validacioncontrol1;

--									/*ACTUALIZAR EN CORE*/
--									--select *
--									update cartera set carDeudaPresunta =  0, carestadooperacion = 'NO_VIGENTE', CARESTADOCARTERA = 'AL_DIA'
--									FROM cartera c
--										 INNER JOIN #Validacioncontrol1  val ON val.carPersona = c. carPersona and val.carid = c.carid


--									INSERT INTO core.aud.Revision
--									SELECT carId AS revIp, 
--											'CORE.CARTERA__POR_CONTROL' AS REVNOMBREUSUARIO, 
--											NULL AS revRequestId, 
--											datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--									FROM CarteraControlAud T
--									WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'TODOS LOS TRABAJADORES EN CERO Y CARTERA AÚN VIGENTE' AND ejecucion = 0 
--									ORDER BY 1;
--												/*core.aud.RevisionEntidad*/
--									INSERT INTO core.aud.RevisionEntidad
--									SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERA_POR_CONTROL' AS REEENTITYCLASSNAME, 
--											1 AS REEREVISIONTYPE, 
--											T.REVTIMESTAMP AS REVTIMESTAMP, 
--											T.REVID AS REEREVISION
--									FROM core.aud.Revision T WITH(NOLOCK)
--									WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERA__POR_CONTROL'
--											AND T.REVID NOT IN
--									(
--										SELECT REEREVISION
--										FROM core.aud.RevisionEntidad
--									)
--									ORDER BY T.revIp;

--											/*CORE.aud.CARTERA_AUD*/
--									INSERT INTO CORE.aud.CARTERA_AUD
--									SELECT R.REVID AS REV, 
--											1 AS REVTYPE, 
--											carDeudaPresunta, 
--											carEstadoCartera, 
--											carEstadoOperacion, 
--											carFechaCreacion, 
--											carPersona, 
--											carMetodo, 
--											carPeriodoDeuda, 
--											carRiesgoIncobrabilidad, 
--											carTipoAccionCobro, 
--											carTipoDeuda, 
--											carTipoLineaCobro, 
--											carTipoSolicitante, 
--											carFechaAsignacionAccion, 
--											P.carId, 
--											carUsuarioTraspaso, 
--											carDeudaPresuntaUnitaria
--									FROM CarteraControlAud p
--										INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.carId = R.revIp
--									WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERA__POR_CONTROL'
--									AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'TODOS LOS TRABAJADORES EN CERO Y CARTERA AÚN VIGENTE' AND Ejecucion = 0
--									ORDER BY P.carId;


--									UPDATE CarteraControlAud SET Ejecucion = 1 WHERE Ejecucion = 0

--									 /*validación de vigente pero todos sus estados cadestado operación es no vigente*/

--									SELECT C.*,cadVigente.cadVigente, cadNoVigente.cadNoVigente
--									INTO #Validacioncontrol2
--									--UPDATE Cartera SET Cartera.carDeudaPresunta = 0 , Cartera.carEstadoCartera = 'AL_DIA', Cartera.carEstadoOperacion = 'NO_VIGENTE'
--									FROM Cartera  C
--									LEFT JOIN (SELECT cadCartera, COUNT (DISTINCT cadId) AS  cadVigente
--												FROM CarteraDependiente 
--												WHERE cadEstadoOperacion = 'VIGENTE' 
--												GROUP BY cadCartera) AS cadVigente
--									ON carId = cadVigente.cadCartera
--									LEFT JOIN (SELECT cadCartera, COUNT (DISTINCT cadId) AS  cadNoVigente
--												FROM CarteraDependiente 
--												WHERE cadEstadoOperacion = 'NO_VIGENTE' 
--												GROUP BY cadCartera) AS cadNoVigente
--									ON carId = cadNoVigente.cadCartera
--									WHERE carEstadoOperacion = 'VIGENTE' 
--									  AND cadVigente IS NULL 
--									  AND carTipoLineaCobro = 'LC1' --and cadNoVigente IS NOT  NULL
--									--GROUP BY carId, carEstadoOperacion, cadVigente.cadVigente, cadNoVigente.cadNoVigente
--									order by carId

--									/*INSERTAR EN TABLA CONTROL AUD*/
--									INSERT INTO CarteraControlAud
--										   SELECT carDeudaPresunta, 
--												  carEstadoCartera, 
--												  carEstadoOperacion, 
--												  carFechaCreacion, 
--												  carPersona, 
--												  carMetodo, 
--												  carPeriodoDeuda, 
--												  carRiesgoIncobrabilidad, 
--												  carTipoAccionCobro, 
--												  carTipoDeuda, 
--												  carTipoLineaCobro, 
--												  carTipoSolicitante, 
--												  carFechaAsignacionAccion, 
--												  carUsuarioTraspaso, 
--												  carId, 
--												  carDeudaPresuntaUnitaria, 
--												  'TODOS LOS REGISTROS DE CADESTADOOPERECION EN NO_VIGENTE Y CARTERA SIGUE CON ESTADO VIGENTE' AS descripcion, 
--												  CAST(DBO.GetLocalDate() AS DATE) AS fechaMod,
--												  0 AS Ejecucion
--										   FROM #Validacioncontrol2;

--									/*ACTUALIZAR EN CORE*/
--									--select *
--									UPDATE cartera SET carDeudaPresunta =  0, carestadooperacion = 'NO_VIGENTE', CARESTADOCARTERA = 'AL_DIA'
--									FROM cartera c
--							  INNER JOIN #Validacioncontrol2  val ON val.carPersona = c. carPersona and val.carid = c.carid
	 
--									INSERT INTO core.aud.Revision
--									SELECT carId AS revIp, 
--											'CORE.CARTERA__POR_CONTROL' AS REVNOMBREUSUARIO, 
--											NULL AS revRequestId, 
--										   datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--									FROM CarteraControlAud T
--									WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'TODOS LOS REGISTROS DE CADESTADOOPERECION EN NO_VIGENTE Y CARTERA SIGUE CON ESATDO VIGENTE' AND Ejecucion = 0
--									ORDER BY 1;
--												/*core.aud.RevisionEntidad*/
--									INSERT INTO core.aud.RevisionEntidad
--									SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERA_POR_CONTROL' AS REEENTITYCLASSNAME, 
--											1 AS REEREVISIONTYPE, 
--											T.REVTIMESTAMP AS REVTIMESTAMP, 
--											T.REVID AS REEREVISION
--									FROM core.aud.Revision T WITH(NOLOCK)
--									WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERA__POR_CONTROL'
--											AND T.REVID NOT IN
--									(
--										SELECT REEREVISION
--										FROM core.aud.RevisionEntidad
--									)
--									ORDER BY T.revIp;

--											/*CORE.aud.CARTERA_AUD*/
--									INSERT INTO CORE.aud.CARTERA_AUD
--									SELECT R.REVID AS REV, 
--											1 AS REVTYPE, 
--											carDeudaPresunta, 
--											carEstadoCartera, 
--											carEstadoOperacion, 
--											carFechaCreacion, 
--											carPersona, 
--											carMetodo, 
--											carPeriodoDeuda, 
--											carRiesgoIncobrabilidad, 
--											carTipoAccionCobro, 
--											carTipoDeuda, 
--											carTipoLineaCobro, 
--											carTipoSolicitante, 
--											carFechaAsignacionAccion, 
--											P.carId, 
--											carUsuarioTraspaso, 
--											carDeudaPresuntaUnitaria
--									FROM CarteraControlAud p
--										INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.carId = R.revIp
--									WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERA__POR_CONTROL'
--									AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'TODOS LOS REGISTROS DE CADESTADOOPERECION EN NO_VIGENTE Y CARTERA SIGUE CON ESATDO VIGENTE' AND ejecucion = 0
--									ORDER BY P.carId;

--									/*VALIDACIÓN SUMA DE TRABAJADORES ES DIFERENTE AL VALOR DE CARTERA*/
--									SELECT C.*,dependiente.sumaDependientes 
--									INTO #Validacioncontrol3
--									FROM Cartera C
--									INNER JOIN persona on perId = carPersona
--									LEFT JOIN (SELECT cadCartera,SUM(cadDeudaPresunta) as sumaDependientes
--									FROM CarteraDependiente  GROUP BY cadCartera) as dependiente on dependiente.cadCartera = carId
--									where dependiente.sumaDependientes <> carDeudaPresunta

--									UNION 
-------ANEXO VALIDACION PARA LOS QUE ARREGLA LA SUMATORIA 2022-08-23
--								    SELECT C.*,dependiente.sumaDependientes  	 
--									FROM Cartera C
--									INNER JOIN persona on perId = carPersona
--									LEFT JOIN (SELECT cadCartera,SUM(cadDeudaPresunta) as sumaDependientes
--									FROM CarteraDependiente  GROUP BY cadCartera) as dependiente on dependiente.cadCartera = carId
--									where dependiente.sumaDependientes = carDeudaPresunta
--									AND dependiente.sumaDependientes>0 
--									AND carEstadoCartera= 'AL_DIA'
--									AND carEstadoOperacion = 'NO_VIGENTE'
									 
									
--									UNION
-------ANEXO VALIDACION PARA LOS QUE ARREGLA LA SUMATORIA 2022-08-23
--									SELECT C.*,dependiente.sumaDependientes  
--									--UPDATE C SET  carDeudaPresunta= dependiente.sumaDependientes
--									FROM Cartera C
--									INNER JOIN persona on perId = carPersona
--									LEFT JOIN (SELECT cadCartera,SUM(cadDeudaPresunta) as sumaDependientes
--									FROM CarteraDependiente  GROUP BY cadCartera) as dependiente on dependiente.cadCartera = carId
--									where dependiente.sumaDependientes <> carDeudaPresunta
--									AND dependiente.sumaDependientes>0 
--									AND carEstadoCartera= 'AL_DIA'
--									AND carEstadoOperacion = 'NO_VIGENTE'
--									order by carPeriodoDeuda



--									UPDATE CarteraControlAud SET Ejecucion = 1 WHERE Ejecucion = 0

--									INSERT INTO CarteraControlAud
--										   SELECT carDeudaPresunta, 
--												  carEstadoCartera, 
--												  carEstadoOperacion, 
--												  carFechaCreacion, 
--												  carPersona, 
--												  carMetodo, 
--												  carPeriodoDeuda, 
--												  carRiesgoIncobrabilidad, 
--												  carTipoAccionCobro, 
--												  carTipoDeuda, 
--												  carTipoLineaCobro, 
--												  carTipoSolicitante, 
--												  carFechaAsignacionAccion, 
--												  carUsuarioTraspaso, 
--												  carId, 
--												  carDeudaPresuntaUnitaria, 
--												  'VALIDACIÓN SUMA DE TRABAJADORES ES DIFERENTE AL VALOR DE CARTERA' AS descripcion, 
--												  CAST(DBO.GetLocalDate() AS DATE) AS fechaMod,
--												  0 AS Ejecucion
--										   FROM #Validacioncontrol3;

--									--select *
--									UPDATE Cartera set carDeudaPresunta = sumaDependientes
--									FROM cartera c
--							  INNER JOIN #Validacioncontrol3  val 
--							     	 ON val.carPersona = c. carPersona and val.carid = c.carid

-------ANEXO VALIDACION PARA LOS QUE ARREGLA LA SUMATORIA 2022-08-23
--	 							  UPDATE Cartera set carEstadoCartera = 'MOROSO',	carEstadoOperacion= 'VIGENTE'
--									FROM cartera c
--							  INNER JOIN #Validacioncontrol3  val 
--							     	  ON val.carPersona = c. carPersona and val.carid = c.carid
--							       WHERE sumaDependientes = C.carDeudaPresunta
--								   AND sumaDependientes>0
--								   AND c.carEstadoCartera = 'AL_DIA'
--								   AND c.carEstadoOperacion= 'NO_VIGENTE'

-------ANEXO VALIDACION PARA LOS QUE ARREGLA LA SUMATORIA 2022-08-23
--					              UPDATE Cartera set carEstadoCartera = 'MOROSO',	carEstadoOperacion= 'VIGENTE',
--								          carDeudaPresunta= sumaDependientes
--									FROM cartera c
--							  INNER JOIN #Validacioncontrol3  val 
--							     	  ON val.carPersona = c. carPersona and val.carid = c.carid
--							       WHERE sumaDependientes <> C.carDeudaPresunta
--									AND sumaDependientes>0 
--									AND c.carEstadoCartera= 'AL_DIA'
--									AND c.carEstadoOperacion = 'NO_VIGENTE'

--									INSERT INTO core.aud.Revision
--									SELECT carId AS revIp, 
--											'CORE.CARTERA__POR_CONTROL' AS REVNOMBREUSUARIO, 
--											NULL AS revRequestId, 
--											datediff_big (ms, '1969-12-31 19:00:00', dbo.GetLocalDate()) AS REVTIMESTAMP
--									FROM CarteraControlAud T
--									WHERE FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'VALIDACIÓN SUMA DE TRABAJADORES ES DIFERENTE AL VALOR DE CARTERA' AND ejecucion = 0
--									ORDER BY 1;
--												/*core.aud.RevisionEntidad*/
--									INSERT INTO core.aud.RevisionEntidad
--									SELECT 'COM.ASOPAGOS.ENTIDADES.CCF.CORE.CARTERA_POR_CONTROL' AS REEENTITYCLASSNAME, 
--											1 AS REEREVISIONTYPE, 
--											T.REVTIMESTAMP AS REVTIMESTAMP, 
--											T.REVID AS REEREVISION
--									FROM core.aud.Revision T WITH(NOLOCK)
--									WHERE T.REVNOMBREUSUARIO = 'CORE.CARTERA__POR_CONTROL'
--											AND T.REVID NOT IN
--									(
--										SELECT REEREVISION
--										FROM core.aud.RevisionEntidad
--									)
--									ORDER BY T.revIp;

--											/*CORE.aud.CARTERA_AUD*/
--									INSERT INTO CORE.aud.CARTERA_AUD
--									SELECT R.REVID AS REV, 
--											1 AS REVTYPE, 
--											carDeudaPresunta, 
--											carEstadoCartera, 
--											carEstadoOperacion, 
--											carFechaCreacion, 
--											carPersona, 
--											carMetodo, 
--											carPeriodoDeuda, 
--											carRiesgoIncobrabilidad, 
--											carTipoAccionCobro, 
--											carTipoDeuda, 
--											carTipoLineaCobro, 
--											carTipoSolicitante, 
--											carFechaAsignacionAccion, 
--											P.carId, 
--											carUsuarioTraspaso, 
--											carDeudaPresuntaUnitaria
--									FROM CarteraControlAud p
--										INNER JOIN core.aud.Revision R WITH(NOLOCK) ON P.carId = R.revIp
--									WHERE R.REVNOMBREUSUARIO = 'CORE.CARTERA__POR_CONTROL'
--									AND FechaModificacion = (SELECT CAST (DBO.GetLocalDate() AS date)) AND descripcion = 'VALIDACIÓN SUMA DE TRABAJADORES ES DIFERENTE AL VALOR DE CARTERA' AND Ejecucion = 0
--									ORDER BY P.carId;

--	 								UPDATE CarteraControlAud SET Ejecucion = 1 WHERE Ejecucion = 0

--	*/
		END  


	