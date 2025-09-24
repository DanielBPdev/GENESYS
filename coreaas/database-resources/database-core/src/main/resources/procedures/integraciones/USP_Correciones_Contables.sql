CREATE OR ALTER PROCEDURE [sap].[USP_Correciones_Contables]
@CampoAModificar NVARCHAR(MAX), ---Cada campo separado por coma
@ValorAIngresar NVARCHAR(MAX), -- Cada valor separado por coma en el mismo orden de los campos
@Referencia VARCHAR (20), -- Referencia del documento contable a modificar
@integracion varchar(10), -- CM,FOVIS,APORTES
@clavecont varchar(10),
@id nvarchar(max)--- NUMERO DE LA CLAVE CONTABLE A MODIFICAR SEPARADO POR COMAS 
As
DECLARE 
@concat nvarchar(max),
@sql nvarchar(max),
@sql2 nvarchar(max)

---creacion de tablas temporales
create table #tempC (
id int identity(1,1),
campo Nvarchar (max)
)
create table #tempV (
id int identity(1,1),
valor Nvarchar (max)
)
----Fin de cracion de temporales

---Extración de columnas y valores a modificar
insert into #tempC
SELECT VALUE AS CAMPOS  FROM STRING_SPLIT(@CampoAModificar,',')
where VALUE NOT IN ('referencia','consecutivo','fechaDocumento','tipomovimiento','asignacion')

insert into #tempV
SELECT VALUE AS Valores  FROM STRING_SPLIT(@ValorAIngresar,',')
-----Fin de la extración de columnas y valores a modificar

---Creación del set con las columnas y los valores
select @concat= COALESCE(@concat + ' , ', '') + concat(campo,'= ''',valor,'''') from #tempc c
inner join #tempV v on c.id = v.id
---Fin Creación del set con las columnas y los valores

---Creacion del update
select @sql =concat ('update sap.IC_',@integracion,'_Det set ',@concat,' from sap.IC_',@integracion,'_Enc e inner join sap.IC_',@integracion,'_Det d on e.consecutivo = d.consecutivo where estadoReg in  (''E'',','''P'') and referencia = ''',@Referencia,''' and clavecont in (',@clavecont, ')','and id in (',@id, ')')
select @sql2 =concat ('update sap.IC_',@integracion,'_Enc set estadoreg = ''P'' from sap.IC_',@integracion,'_Enc e inner join sap.IC_',@integracion,'_Det d on e.consecutivo = d.consecutivo where estadoReg in  (''E'',','''P'') and referencia = ''',@Referencia,''' and clavecont in (',@clavecont, ')','and id in (',@id,')')
---Fin creacion del update


--select @sql
--select @sql2


exec sys.[sp_executesql] @sql
exec sys.[sp_executesql] @sql2