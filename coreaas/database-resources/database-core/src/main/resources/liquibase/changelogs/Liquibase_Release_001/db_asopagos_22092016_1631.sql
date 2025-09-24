--liquibase formatted sql

--changeset alopez:1 stripComments:false
/*22/09/2016-alopez*/
UPDATE PARAMETRO SET prmValor='132e4816-0876-4711-add8-24da0215f663' WHERE prmNombre='IDM_CLIENT_WEB_CLIENT_SECRET';
