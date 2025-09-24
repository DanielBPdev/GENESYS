--liquibase formatted sql

--comment: Administrar Priorización Destinatarios GLPI 29357

INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
   
    	SELECT dc.dcoId , gp.gprId , 0
    	FROM DestinatarioComunicado dc, GrupoPrioridad gp  
    	whERE dcoId IN (573,574,579,576,575,578,577,580,533,534,535,536,537)
    	AND gp. gprId BETWEEN 54 AND 85;