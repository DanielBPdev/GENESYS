--liquibase formatted sql

--changeSET heinsohn:01 context:integracion runOnChange:true
--comment: Configuracion del ambiente ambiente integración COMFABOY
UPDATE ReporteKPI SET rekIdGrupo = 'd2044b92-af76-4bf7-bfce-db27aedc3738', rekIdReporte = '93f85bda-1b30-4007-b647-28f54f767833' WHERE rekNombreReporte = 'AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'MENSUAL';
UPDATE ReporteKPI SET rekIdGrupo = 'd2044b92-af76-4bf7-bfce-db27aedc3738', rekIdReporte = '669f26e2-c079-4042-9747-94b93899940f' WHERE rekNombreReporte = 'AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'BIMENSUAL';
UPDATE ReporteKPI SET rekIdGrupo = 'd2044b92-af76-4bf7-bfce-db27aedc3738', rekIdReporte = '465605ba-7d31-4e6d-96d3-9d20865eb154' WHERE rekNombreReporte = 'AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'TRIMESTRAL';
UPDATE ReporteKPI SET rekIdGrupo = 'd2044b92-af76-4bf7-bfce-db27aedc3738', rekIdReporte = '90e8df4e-0cc9-4403-b620-e3aba5be7d93' WHERE rekNombreReporte = 'AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'SEMESTRAL';
UPDATE ReporteKPI SET rekIdGrupo = 'd2044b92-af76-4bf7-bfce-db27aedc3738', rekIdReporte = '4fea3307-343f-4e4d-b15d-43a4887553c3' WHERE rekNombreReporte = 'AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'ANUAL';

UPDATE ReporteKPI SET rekIdGrupo = '210cf395-cbe9-4b68-8494-c5645351a24a', rekIdReporte = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738' WHERE rekNombreReporte = 'AFILIACIONES_PERSONA' AND rekFrecuencia = 'MENSUAL';
UPDATE ReporteKPI SET rekIdGrupo = 'a820ed1f-fb8c-4159-a191-06d75410ae5f', rekIdReporte = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738' WHERE rekNombreReporte = 'AFILIACIONES_PERSONA' AND rekFrecuencia = 'BIMENSUAL';
UPDATE ReporteKPI SET rekIdGrupo = '6fdb5587-d133-4f5d-bdb0-d98701a122d1', rekIdReporte = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738' WHERE rekNombreReporte = 'AFILIACIONES_PERSONA' AND rekFrecuencia = 'TRIMESTRAL';
UPDATE ReporteKPI SET rekIdGrupo = '850382c4-26c2-457f-9194-e787a007f30a', rekIdReporte = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738' WHERE rekNombreReporte = 'AFILIACIONES_PERSONA' AND rekFrecuencia = 'SEMESTRAL';
UPDATE ReporteKPI SET rekIdGrupo = 'f4ca5d9b-2809-4be3-a25c-8e5671af2903', rekIdReporte = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738' WHERE rekNombreReporte = 'AFILIACIONES_PERSONA' AND rekFrecuencia = 'ANUAL';

UPDATE ReporteKPI SET rekIdGrupo = '3e542e8a-f3ca-4cd1-9e0e-6126b897441d', rekIdReporte = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738' WHERE rekNombreReporte = 'APORTES_PILA' AND rekFrecuencia = 'MENSUAL';
UPDATE ReporteKPI SET rekIdGrupo = 'a8a8692a-6b53-49e9-8ecd-1ddee2261685', rekIdReporte = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738' WHERE rekNombreReporte = 'APORTES_PILA' AND rekFrecuencia = 'BIMENSUAL';
UPDATE ReporteKPI SET rekIdGrupo = 'e2f50538-29c6-4886-ac09-da5e36568f82', rekIdReporte = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738' WHERE rekNombreReporte = 'APORTES_PILA' AND rekFrecuencia = 'TRIMESTRAL';
UPDATE ReporteKPI SET rekIdGrupo = 'd72f5844-a6a8-4ec7-91db-34cf66a497c0', rekIdReporte = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738' WHERE rekNombreReporte = 'APORTES_PILA' AND rekFrecuencia = 'SEMESTRAL';
UPDATE ReporteKPI SET rekIdGrupo = 'b0bf25d2-bd42-4bdf-903f-33aee1c9598e', rekIdReporte = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738' WHERE rekNombreReporte = 'APORTES_PILA' AND rekFrecuencia = 'ANUAL';

UPDATE ReporteKPI SET rekIdGrupo = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738', rekIdReporte = 'a4c619ee-740e-406c-babe-21e4c8d79f74' WHERE rekNombreReporte = 'NOVEDADES_EMPRESA' AND rekFrecuencia = 'MENSUAL';
UPDATE ReporteKPI SET rekIdGrupo = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738', rekIdReporte = '430706a2-7a37-4bab-8e06-8d87faeba176' WHERE rekNombreReporte = 'NOVEDADES_EMPRESA' AND rekFrecuencia = 'BIMENSUAL';
UPDATE ReporteKPI SET rekIdGrupo = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738', rekIdReporte = 'b8102e3b-cb78-408f-8024-5e17e56debb8' WHERE rekNombreReporte = 'NOVEDADES_EMPRESA' AND rekFrecuencia = 'TRIMESTRAL';
UPDATE ReporteKPI SET rekIdGrupo = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738', rekIdReporte = '3a7d776c-a327-4d05-9991-181d9aae7f7e' WHERE rekNombreReporte = 'NOVEDADES_EMPRESA' AND rekFrecuencia = 'SEMESTRAL';
UPDATE ReporteKPI SET rekIdGrupo = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738', rekIdReporte = '91509928-e0b8-45ba-a7a6-96f6087c47bf' WHERE rekNombreReporte = 'NOVEDADES_EMPRESA' AND rekFrecuencia = 'ANUAL';

UPDATE ReporteKPI SET rekIdGrupo = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738', rekIdReporte = 'c0e2ec2c-c540-4cff-a56f-ed7916f9e6bc' WHERE rekNombreReporte = 'NOVEDADES_PERSONA' AND rekFrecuencia = 'MENSUAL';
UPDATE ReporteKPI SET rekIdGrupo = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738', rekIdReporte = 'f7b5baec-22be-4dc2-8763-b14593c8c237' WHERE rekNombreReporte = 'NOVEDADES_PERSONA' AND rekFrecuencia = 'BIMENSUAL';
UPDATE ReporteKPI SET rekIdGrupo = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738', rekIdReporte = 'cbece2b6-e2be-4666-a5dd-7daa7cacceb8' WHERE rekNombreReporte = 'NOVEDADES_PERSONA' AND rekFrecuencia = 'TRIMESTRAL';
UPDATE ReporteKPI SET rekIdGrupo = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738', rekIdReporte = '3ef722d8-64db-4764-a632-8afd3a3e447f' WHERE rekNombreReporte = 'NOVEDADES_PERSONA' AND rekFrecuencia = 'SEMESTRAL';
UPDATE ReporteKPI SET rekIdGrupo = 'D2044B92-AF76-4BF7-BFCE-DB27AEDC3738', rekIdReporte = '7d9516e7-4b60-44c7-92d5-0500ff55e8ee' WHERE rekNombreReporte = 'NOVEDADES_PERSONA' AND rekFrecuencia = 'ANUAL';

--changeSET heinsohn:02 context:pruebas runAlways:true runOnChange:true
--comment: Configuracion del ambiente pruebas HBT COMFACAUCA 
UPDATE ReporteKPI SET rekIdReporte='f921d0ce-25b7-4eb5-b212-9b9fa4966e1d', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'MENSUAL';
UPDATE ReporteKPI SET rekIdReporte='fcad0d08-28bd-4901-8fa5-abc34fc42e27', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'BIMENSUAL';
UPDATE ReporteKPI SET rekIdReporte='ff21c40a-1d91-412b-a77c-b5928f1b29ab', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'TRIMESTRAL';
UPDATE ReporteKPI SET rekIdReporte='bd659b5d-7c29-4a9b-8203-6eeee33ff7db', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'SEMESTRAL';
UPDATE ReporteKPI SET rekIdReporte='4a5ede63-13c3-4ce6-b024-48c237036326', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'ANUAL';

--changeSET heinsohn:04 context:integracion-asopagos  runAlways:true runOnChange:true
--comment: Configuracion del ambiente integracion asopagos integracionasopagos
UPDATE ReporteKPI SET rekIdReporte='f921d0ce-25b7-4eb5-b212-9b9fa4966e1d', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'MENSUAL';
UPDATE ReporteKPI SET rekIdReporte='fcad0d08-28bd-4901-8fa5-abc34fc42e27', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'BIMENSUAL';
UPDATE ReporteKPI SET rekIdReporte='ff21c40a-1d91-412b-a77c-b5928f1b29ab', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'TRIMESTRAL';
UPDATE ReporteKPI SET rekIdReporte='bd659b5d-7c29-4a9b-8203-6eeee33ff7db', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'SEMESTRAL';
UPDATE ReporteKPI SET rekIdReporte='4a5ede63-13c3-4ce6-b024-48c237036326', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'ANUAL';

--changeSET heinsohn:05 context:asopagos_confa runOnChange:true
--comment: Configuracion del ambiente asopagos_confa
UPDATE ReporteKPI SET rekIdGrupo = 'D44CDC57-8638-4C23-9B4F-6C94D11DA64B',rekIdReporte ='772DC9D2-B629-4A87-9151-9E922B8B52E4' WHERE rekNombreReporte = 'AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'MENSUAL';
UPDATE ReporteKPI SET rekIdGrupo = 'D44CDC57-8638-4C23-9B4F-6C94D11DA64B',rekIdReporte ='24EEEBD3-8BDB-4E75-B1C6-DF81A1246569' WHERE rekNombreReporte = 'AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'BIMENSUAL';
UPDATE ReporteKPI SET rekIdGrupo = 'D44CDC57-8638-4C23-9B4F-6C94D11DA64B',rekIdReporte ='FFD06426-80E2-4B3A-8A2A-D45F2C765AB2' WHERE rekNombreReporte = 'AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'TRIMESTRAL';
UPDATE ReporteKPI SET rekIdGrupo = 'D44CDC57-8638-4C23-9B4F-6C94D11DA64B',rekIdReporte ='B8448A73-31C7-4B7A-9B0B-87512A25FD13' WHERE rekNombreReporte = 'AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'SEMESTRAL';
UPDATE ReporteKPI SET rekIdGrupo = 'D44CDC57-8638-4C23-9B4F-6C94D11DA64B',rekIdReporte ='BFCEE98F-D8D8-4B72-A93B-9CD2B871F203' WHERE rekNombreReporte = 'AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'ANUAL';
--changeSET heinsohn:06 context:asopagos_funcional runOnChange:true
--comment: Configuracion del ambiente asopagos_funcional
UPDATE ReporteKPI SET rekIdReporte='f921d0ce-25b7-4eb5-b212-9b9fa4966e1d', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'MENSUAL';
UPDATE ReporteKPI SET rekIdReporte='fcad0d08-28bd-4901-8fa5-abc34fc42e27', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'BIMENSUAL';
UPDATE ReporteKPI SET rekIdReporte='ff21c40a-1d91-412b-a77c-b5928f1b29ab', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'TRIMESTRAL';
UPDATE ReporteKPI SET rekIdReporte='bd659b5d-7c29-4a9b-8203-6eeee33ff7db', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'SEMESTRAL';
UPDATE ReporteKPI SET rekIdReporte='4a5ede63-13c3-4ce6-b024-48c237036326', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'ANUAL';

--changeSET heinsohn:07 context:asopagosSubsidio runOnChange:true
--comment: Configuracion del ambiente ASOPAGOSSUBSIDIO - HBT
UPDATE ReporteKPI SET rekIdReporte='f921d0ce-25b7-4eb5-b212-9b9fa4966e1d', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'MENSUAL';
UPDATE ReporteKPI SET rekIdReporte='fcad0d08-28bd-4901-8fa5-abc34fc42e27', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'BIMENSUAL';
UPDATE ReporteKPI SET rekIdReporte='ff21c40a-1d91-412b-a77c-b5928f1b29ab', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'TRIMESTRAL';
UPDATE ReporteKPI SET rekIdReporte='bd659b5d-7c29-4a9b-8203-6eeee33ff7db', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'SEMESTRAL';
UPDATE ReporteKPI SET rekIdReporte='4a5ede63-13c3-4ce6-b024-48c237036326', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'ANUAL';

--changeSET heinsohn:08 context:asopagos_subsidio runOnChange:true
--comment: Configuracion del ambiente asopagos_subsidio - Asopagos
UPDATE ReporteKPI SET rekIdReporte='f921d0ce-25b7-4eb5-b212-9b9fa4966e1d', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'MENSUAL';
UPDATE ReporteKPI SET rekIdReporte='fcad0d08-28bd-4901-8fa5-abc34fc42e27', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'BIMENSUAL';
UPDATE ReporteKPI SET rekIdReporte='ff21c40a-1d91-412b-a77c-b5928f1b29ab', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'TRIMESTRAL';
UPDATE ReporteKPI SET rekIdReporte='bd659b5d-7c29-4a9b-8203-6eeee33ff7db', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'SEMESTRAL';
UPDATE ReporteKPI SET rekIdReporte='4a5ede63-13c3-4ce6-b024-48c237036326', rekIdGrupo = 'f56a16eb-43ce-433f-8290-f5ee5b75393a' WHERE rekNombreReporte='AFILIACIONES_EMPLEADOR' AND rekFrecuencia = 'ANUAL';
