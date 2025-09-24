if  exists (select * from fieldloadcatalog where id = 32133157)
update fieldloadcatalog
set name = 'fechaRecepcionDocumentos',description = 'Fecha recepción de documentos del trabajador o cabeza de familia'
where id = 32133157