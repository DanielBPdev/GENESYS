--liquibase formatted sql

--changeset lzarate:01 stripComments:true /*29/09/2016-halzate-HU-121-108*/  

ALTER TABLE beneficiario ADD benSalarioMensualBeneficiario numeric (19);

--changeset sbriñez:02 stripComments:true /*29/09/2016-sbriñez-HU-121-109*/  

CREATE SEQUENCE SEC_consecutivoGestionPersonaEntidadPagadora START WITH 1   INCREMENT BY 1 ;  
GO 