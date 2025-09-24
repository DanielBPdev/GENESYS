--liquibase formatted sql
--changeset lepaez:01 runAlways:true runOnChange:true
--comment: Borrado de tables Diagrams
 
IF OBJECT_ID('GraphicFeatureDefinition', 'U') IS NOT NULL DROP TABLE GraphicFeatureDefinition;
 
IF OBJECT_ID('GraphicFeature', 'U') IS NOT NULL DROP TABLE GraphicFeature;