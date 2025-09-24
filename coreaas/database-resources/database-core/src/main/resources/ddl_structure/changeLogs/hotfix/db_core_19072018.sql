--liquibase formatted sql

--changeset abaquero:01
--comment: Se modifican los nombres de los tipos de líneas en configuración para tratamiento de mensajes de error de estructura PILA
update lineloadcatalog set name = '%%Registro Tipo 1 - Información aportante por pensionado%%' where id = 1
update lineloadcatalog set name = '%%Registro Tipo 1 - Información aportante por in/dependiente%%' where id = 2
update lineloadcatalog set name = '%%Registro Tipo 1 - Detalle aporte por in/dependiente%%' where id = 3
update lineloadcatalog set name = '%%Registro Tipo 2 - Detalle aporte por in/dependiente%%' where id = 4
update lineloadcatalog set name = '%%Registro Tipo 3, renglón 31 - Detalle aporte por in/dependiente%%' where id = 5
update lineloadcatalog set name = '%%Registro Tipo 3, renglón 36 - Detalle aporte por in/dependiente%%' where id = 6
update lineloadcatalog set name = '%%Registro Tipo 3, renglón 39 - Detalle aporte por in/dependiente%%' where id = 7
update lineloadcatalog set name = '%%Registro Tipo 1 - Detalle aporte por pensionado%%' where id = 8
update lineloadcatalog set name = '%%Registro Tipo 2 - Detalle aporte por pensionado%%' where id = 9
update lineloadcatalog set name = '%%Registro Tipo 3 - Detalle aporte por pensionado%%' where id = 10
update lineloadcatalog set name = '%%Registro Tipo 1 - Archivo Log Financiero%%' where id = 11
update lineloadcatalog set name = '%%Registro Tipo 5 - Archivo Log Financiero%%' where id = 12
update lineloadcatalog set name = '%%Registro Tipo 6 - Archivo Log Financiero%%' where id = 13
update lineloadcatalog set name = '%%Registro Tipo 8 - Archivo Log Financiero%%' where id = 14
update lineloadcatalog set name = '%%Registro Tipo 9 - Archivo Log Financiero%%' where id = 15
