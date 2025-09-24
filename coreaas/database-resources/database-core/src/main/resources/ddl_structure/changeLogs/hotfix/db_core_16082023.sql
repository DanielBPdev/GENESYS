IF NOT EXISTS (SELECT 1 FROM validacionproceso  WHERE vapproceso = 'POSTULACION_FOVIS_WEB' and vapBloque = '321-027-2')
 INSERT INTO [dbo].validacionproceso
       (vapbloque
       ,vapvalidacion
       ,vapproceso
       ,vapestadoproceso
       ,vaporden
       ,vapObjetoValidacion
       ,vapinversa)
select
       vapbloque
       ,vapvalidacion
       ,'POSTULACION_FOVIS_WEB'
       ,vapestadoproceso 
       ,vaporden
       ,vapObjetoValidacion
       ,vapinversa
	   from validacionproceso
		where vapBloque = '321-027-2'