--Crear schema sap


if not exists (select * from sys.schemas where name = 'sap') 
begin
EXEC('create schema sap')
end