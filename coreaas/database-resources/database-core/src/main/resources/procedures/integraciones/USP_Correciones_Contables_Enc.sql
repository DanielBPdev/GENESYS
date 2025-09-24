CREATE OR ALTER PROCEDURE [sap].[USP_Correciones_Contables_Enc]
@CampoAModificar NVARCHAR(MAX), ---Cada campo separado por coma
@ValorAIngresar NVARCHAR(MAX), -- Cada valor separado por coma en el mismo orden de los campos
@Referencia VARCHAR (20), -- Referencia del documento contable a modificar
@integracion varchar(10) -- CM,FOVIS,APORTES
As
DECLARE 
@concat nvarchar(max),
@sql nvarchar(max),
@sql2 nvarchar(max)


---creacion de tablas temporales
create table #tempC (
id int identity(1,1),
campo nvarchar (max)
)
create table #tempV (
id int identity(1,1),
valor nvarchar (max)
)
----Fin de cracion de temporales

---Extracion de columnas y valores a modificar
insert into #tempC
SELECT VALUE AS CAMPOS  FROM STRING_SPLIT(@CampoAModificar,',')
where VALUE NOT IN ('referencia','consecutivo','fechaDocumento','tipomovimiento','asignacion','fechaContabilizacion')

insert into #tempV
SELECT VALUE AS Valores  FROM STRING_SPLIT(@ValorAIngresar,',')
-----Fin de la extracion de columnas y valores a modificar

---Creacion del set con las columnas y los valores
select @concat= COALESCE(@concat + ' , ', '') + concat(campo,'= ''',valor,'''') from #tempc c
inner join #tempV v on c.id = v.id
---Fin Creacion del set con las columnas y los valores

---Creacion del update
select @sql =concat ('update sap.IC_',@integracion,'_Enc set ',@concat,', estadoreg = ''P'' from sap.IC_',@integracion,'_Enc  where estadoReg in  (''E'',','''P'') and referencia = ''',@Referencia,'''')
--select @sql2 =concat ('update sap.IC_',@integracion,'Enc set estadoreg = ''P'' from sap.IC',@integracion,'Enc e inner join sap.IC',@integracion,'_Det d on e.consecutivo = d.consecutivo where estadoReg in  (''E'',','''P'') and referencia = ''',@Referencia,'')
---Fin creacion del update


--select @sql
--select @sql2


exec sys.[sp_executesql] @sql
--exec sys.[sp_executesql] @sql2