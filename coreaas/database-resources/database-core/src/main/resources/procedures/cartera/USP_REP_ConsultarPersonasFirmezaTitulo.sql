/****** Object:  StoredProcedure [dbo].[USP_REP_ConsultarPersonasFirmezaTitulo]    Script Date: 2023-08-09 4:09:12 PM ******/

CREATE OR ALTER   PROCEDURE [dbo].[USP_REP_ConsultarPersonasFirmezaTitulo] AS

	SET NOCOUNT ON
	SET ANSI_NULLS ON
	SET QUOTED_IDENTIFIER ON


BEGIN
 
			-----INICIO POBLACIÓN QUE DEBEN EXCLUIR DE CUALQUIERA DE LOS METODOS

				SELECT bcaNumeroOperacion
				INTO #Pobexc
			    FROM cartera car WITH(NOLOCK)
		  INNER JOIN persona p WITH(NOLOCK)ON car.carPersona= p.perid
		  INNER JOIN BitacoraCartera b WITH(NOLOCK) ON b.bcaPersona= p.perid
			   WHERE car.carTipoLineaCobro in ('LC1','LC4','LC5')  
				 AND car.carMetodo = 'METODO_1'
				 AND car.carTipoAccionCobro IN ( 'A01', 'A1', 'AB1','B1', 'BC1')
						  		  
			    UNION
			  
			    SELECT bcaNumeroOperacion
			    FROM cartera car WITH(NOLOCK)
				        INNER JOIN persona p WITH(NOLOCK) ON car.carPersona= p.perid
				        INNER JOIN BitacoraCartera b WITH(NOLOCK) ON b.bcaPersona= p.perid
			    WHERE car.carTipoLineaCobro in ('LC1','LC4','LC5')  
				    AND car.carMetodo =  'METODO_2'
				    AND car.carTipoAccionCobro IN ('A02', 'A2','B2','BC2')
				GROUP BY bcaNumeroOperacion

				-----FIN POBLACIÓN QUE DEBEN EXCLUIR DE CUALQUIERA DE LOS METODOS 
			
	-----VARIABLES NECESARIAS PARA EL SERVICIO QUE REALIZA LA FIRMEZA DEL TITULO

	SELECT  NULL AS IdCartera, TipoIdentificacion,	NumeroIdentificacion,	TipoSolicitante,	Actividad,	Operacion,	metodo,	bcaFecha, perid,bcaResultado
	FROM
		(
			SELECT  --carid AS IdCartera,
			        perTipoIdentificacion AS TipoIdentificacion,
			        perNumeroIdentificacion AS NumeroIdentificacion,
			        carTipoSolicitante AS TipoSolicitante,
			        bcaactividad AS Actividad,
			        b.bcaNumeroOperacion as Operacion,
			        carMetodo as metodo , bcaFecha ,perId,MAX(bcaResultado ) AS bcaResultado 
		      	FROM cartera car WITH(NOLOCK)
		  INNER JOIN persona p WITH(NOLOCK)ON car.carPersona= p.perid
		  INNER JOIN BitacoraCartera b WITH(NOLOCK) ON b.bcaPersona= p.perid
		   LEFT JOIN #Pobexc exc ON exc.bcaNumeroOperacion = b.bcaNumeroOperacion -----POBLACIÓN QUE DEBEN EXCLUIR 
			   WHERE car.carTipoLineaCobro in ('LC1','LC4','LC5')  
				 AND car.carEstadoCartera = 'MOROSO'
				 AND car.carEstadoOperacion = 'VIGENTE'
				 AND car.carDeudaPresunta > 0
				 AND carTipoAccionCobro IS NOT NULL
				 AND exc.bcaNumeroOperacion IS NULL
 	        GROUP BY perTipoIdentificacion, perNumeroIdentificacion ,
			         carTipoSolicitante, bcaactividad, b.bcaNumeroOperacion,
			         carMetodo, bcaFecha, perId
		) x
	WHERE DATEDIFF(DAY, x.bcaFecha, GETDATE()) >= (SELECT cnsValor FROM Constante WHERE cnsNombre = 'DIAS_FIRMEZA_TITULO')
	  AND Operacion NOT IN (SELECT bcaNumeroOperacion
	                        FROM BitacoraCartera WITH(NOLOCK)
	                        WHERE bcaActividad = 'FIRMEZA_TITULO_EJECUTIVO'
							)-----CONDICIONES GENERAL PARA TODA LA POBLACIÓN 
	  AND (
			(Actividad IN ('GENERAR_LIQUIDACION') 
	                AND (x.metodo = 'METODO_1')
			)-----CONDICIONES PARA METODO 1 
			OR (Actividad IN ('D2','REGISTRO_NOTIFICACION_PERSONAL','E2')
				AND bcaResultado IN ('ENVIADO', 'NOTIFICADO_PERSONALMENTE', 'PUBLICADO','EXITOSO')
				AND (x.metodo = 'METODO_2')
				)-----CONDICIONES PARA METODO 2 
		  )
	GROUP BY  TipoIdentificacion, NumeroIdentificacion, TipoSolicitante, Actividad,	Operacion,	metodo,	bcaFecha, perid,bcaResultado


	DROP TABLE #Pobexc

END