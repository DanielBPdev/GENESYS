INSERT INTO "MODULE_SEG" (ID, NAME) VALUES ('5', 'FileProcessing');

INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('23', 'FileDefinitionType', '/lion/fileProcessing/fileDefinitionType/fileDefinitionTypeList.jsf', '5');
INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('24', 'FileDefinition', '/lion/fileProcessing/fileDefinition/fileDefinitionList.jsf', '5');
INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('25', 'LineCatalog', '/lion/fileProcessing/lineCatalog/LineCatalogList.jsf', '5');
INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('26', 'FieldCatalog', '/lion/fileProcessing/fieldCatalog/FieldCatalogList.jsf', '5');
INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('27', 'ProcessorCatalog', '/lion/fileProcessing/processorCatalog/ProcessorCatalogList.jsf', '5');
INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('28', 'ValidatorCatalog', '/lion/fileProcessing/validatorCatalog/ValidatorCatalogList.jsf', '5');
INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('29', 'Logs de error', '/lion/fileProcessing/logs/fileDefinitionLog.jsf', '5');

SET IDENTITY_INSERT PERMISSION_SEG ON
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('69', '23', '1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('70', '/lion/fileProcessing/fileDefinitionType/fileDefinitionTypeEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('71', '/lion/fileProcessing/fileDefinitionType/fileDefinitionTypeView.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('72', '24', '1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('73', '/lion/fileProcessing/fileDefinition/fieldDefinitionEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('74', '/lion/fileProcessing/fileDefinition/fieldDefinitionList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('75', '/lion/fileProcessing/fileDefinition/fieldProcDefinitionEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('76', '/lion/fileProcessing/fileDefinition/fieldProcDefinitionList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('77', '/lion/fileProcessing/fileDefinition/fieldProcParamValueEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('78', '/lion/fileProcessing/fileDefinition/fieldProcParamValueList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('79', '/lion/fileProcessing/fileDefinition/fieldValDefinitionEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('80', '/lion/fileProcessing/fileDefinition/fieldValDefinitionList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('81', '/lion/fileProcessing/fileDefinition/fieldValParamValueEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('82', '/lion/fileProcessing/fileDefinition/fieldValParamValueList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('83', '/lion/fileProcessing/fileDefinition/fileDefinitionEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('84', '/lion/fileProcessing/fileDefinition/lineDefinitionEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('85', '/lion/fileProcessing/fileDefinition/lineDefinitionList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('86', '/lion/fileProcessing/fileDefinition/lineProcDefinitionEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('87', '/lion/fileProcessing/fileDefinition/lineProcDefinitionList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('88', '/lion/fileProcessing/fileDefinition/lineProcParamValueEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('89', '/lion/fileProcessing/fileDefinition/lineProcParamValueList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('90', '/lion/fileProcessing/fileDefinition/lineValDefinitionEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('91', '/lion/fileProcessing/fileDefinition/lineValDefinitionList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('92', '/lion/fileProcessing/fileDefinition/lineValParamValueEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('93', '/lion/fileProcessing/fileDefinition/lineValParamValueList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('94', '/lion/fileProcessing/fileDefinitionView/fieldDefinitionList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('95', '/lion/fileProcessing/fileDefinitionView/fieldProcDefinitionList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('96', '/lion/fileProcessing/fileDefinitionView/fieldProcParamValueList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('97', '/lion/fileProcessing/fileDefinitionView/fieldValDefinitionList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('98', '/lion/fileProcessing/fileDefinitionView/fieldValParamValueList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('99', '/lion/fileProcessing/fileDefinitionView/fileDefinitionView.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('100', '/lion/fileProcessing/fileDefinitionView/lineDefinitionList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('101', '/lion/fileProcessing/fileDefinitionView/lineProcDefinitionList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('102', '/lion/fileProcessing/fileDefinitionView/lineProcParamValueList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('103', '/lion/fileProcessing/fileDefinitionView/lineValDefinitionList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('104', '/lion/fileProcessing/fileDefinitionView/lineValParamValueList.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('105', '25', '1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('106', '/lion/fileProcessing/lineCatalog/LineCatalogEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('107', '/lion/fileProcessing/lineCatalog/LineCatalogView.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('108', '26', '1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('109', '/lion/fileProcessing/fieldCatalog/FieldCatalogEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('110', '/lion/fileProcessing/fieldCatalog/FieldCatalogView.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('111', '27', '1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('112', '/lion/fileProcessing/processorCatalog/ProcessorCatalogEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('113', '/lion/fileProcessing/processorCatalog/ProcessorCatalogView.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('114', '28', '1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('115', '/lion/fileProcessing/validatorCatalog/ValidatorCatalogEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('116', '/lion/fileProcessing/validatorCatalog/ValidatorCatalogView.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('117', '29', '1');
SET IDENTITY_INSERT PERMISSION_SEG OFF

Insert into POLICY_SEG (ID,IMPLCLASS,IDPOLICYTYPE) values (1,'co.com.heinsohn.lion.security.policymanagement.impl.BasicUserAuthenticationPolicy',0);
Insert into POLICY_SEG (ID,IMPLCLASS,IDPOLICYTYPE) values (9,'co.com.heinsohn.lion.security.policymanagement.impl.PasswordMinimumLengthPolicy',2);
Insert into POLICY_SEG (ID,IMPLCLASS,IDPOLICYTYPE) values (10,'co.com.heinsohn.lion.security.policymanagement.impl.ResourceAuthorizationPolicy',1);
Insert into POLICY_SEG (ID,IMPLCLASS,IDPOLICYTYPE) values (4,'co.com.heinsohn.lion.security.policymanagement.impl.PasswordExpiration',0);
Insert into POLICY_SEG (ID,IMPLCLASS,IDPOLICYTYPE) values (5,'co.com.heinsohn.lion.security.policymanagement.impl.RemembranceExpiration',0);
Insert into POLICY_SEG (ID,IMPLCLASS,IDPOLICYTYPE) values (2,'co.com.heinsohn.lion.security.policymanagement.impl.CheckFailedLogin',0);
Insert into POLICY_SEG (ID,IMPLCLASS,IDPOLICYTYPE) values (3,'co.com.heinsohn.lion.security.policymanagement.impl.CheckInactivityDays',0);
Insert into POLICY_SEG (ID,IMPLCLASS,IDPOLICYTYPE) values (6,'co.com.heinsohn.lion.security.policymanagement.impl.CheckConsecutiveCharacters',2);
Insert into POLICY_SEG (ID,IMPLCLASS,IDPOLICYTYPE) values (7,'co.com.heinsohn.lion.security.policymanagement.impl.CheckMask',2);
Insert into POLICY_SEG (ID,IMPLCLASS,IDPOLICYTYPE) values (8,'co.com.heinsohn.lion.security.policymanagement.impl.CheckPasswordLength',2);
Insert into POLICY_SEG (ID,IMPLCLASS,IDPOLICYTYPE) values (11,'co.com.heinsohn.lion.security.policymanagement.impl.UserAuthenticationLDAPPolicy',0);
Insert into POLICY_SEG (ID,IMPLCLASS,IDPOLICYTYPE) values (12,'co.com.heinsohn.lion.security.policymanagement.impl.ChangePasswordFirstLogin',0);

Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (8,'daysDurationPassword','java.lang.Integer',4);
Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (9,'passwordMinimumLength','java.lang.Integer',9);
Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (3,'rememberKeyExpiration','java.lang.Long',5);
Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (4,'consecutiveChars','java.lang.Integer',6);
Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (5,'regularExpressions','java.lang.String',7);
Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (6,'maximumLength','java.lang.Integer',8);
Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (7,'minimumLength','java.lang.Integer',8);
Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (1,'numberAttempts','java.lang.Integer',2);
Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (2,'dayInactivityAllowed','java.lang.Integer',3);
Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (10,'host','java.lang.String',11);
Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (11,'port','java.lang.String',11);
Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (12,'domain','java.lang.String',11);
Insert into POLICYPARAM_SEG (ID,NAME,PARAMTYPE,IDPOLICY) values (13,'dn','java.lang.String',11);

INSERT INTO "TENANT_SEG" (ID,COMPANYID,COMPANYNAME,DOMAIN,STATE) VALUES ('1',NULL,'lion','heinsohn.com.co',0);

SET IDENTITY_INSERT PROFILE_SEG ON
INSERT INTO "PROFILE_SEG" (ID,DESCRIPTION,NAME,STATE,IDINTERNALPROFILE,IDTENANT) VALUES ('1','Administrador','Admin',0,NULL,'1');
SET IDENTITY_INSERT PROFILE_SEG OFF

SET IDENTITY_INSERT TENANTPOLICY_SEG ON
INSERT INTO "TENANTPOLICY_SEG" (ID,ENABLE,EXECUTEBYADMIN,ORDERING,IDPOLICY,IDTENANT) VALUES ('1','Y',null,1,'1','1');
INSERT INTO "TENANTPOLICY_SEG" (ID,ENABLE,EXECUTEBYADMIN,ORDERING,IDPOLICY,IDTENANT) VALUES ('2','Y',null,2,'2','1');
INSERT INTO "TENANTPOLICY_SEG" (ID,ENABLE,EXECUTEBYADMIN,ORDERING,IDPOLICY,IDTENANT) VALUES ('3','Y',null,3,'3','1');
INSERT INTO "TENANTPOLICY_SEG" (ID,ENABLE,EXECUTEBYADMIN,ORDERING,IDPOLICY,IDTENANT) VALUES ('4','Y',null,4,'4','1');
INSERT INTO "TENANTPOLICY_SEG" (ID,ENABLE,EXECUTEBYADMIN,ORDERING,IDPOLICY,IDTENANT) VALUES ('5','Y',null,5,'5','1');
INSERT INTO "TENANTPOLICY_SEG" (ID,ENABLE,EXECUTEBYADMIN,ORDERING,IDPOLICY,IDTENANT) VALUES ('6','Y',null,6,'10','1');
INSERT INTO "TENANTPOLICY_SEG" (ID,ENABLE,EXECUTEBYADMIN,ORDERING,IDPOLICY,IDTENANT) VALUES ('7','Y',null,7,'6','1');
INSERT INTO "TENANTPOLICY_SEG" (ID,ENABLE,EXECUTEBYADMIN,ORDERING,IDPOLICY,IDTENANT) VALUES ('8','Y',null,8,'7','1');
INSERT INTO "TENANTPOLICY_SEG" (ID,ENABLE,EXECUTEBYADMIN,ORDERING,IDPOLICY,IDTENANT) VALUES ('9','Y',null,9,'8','1');
INSERT INTO "TENANTPOLICY_SEG" (ID,ENABLE,EXECUTEBYADMIN,ORDERING,IDPOLICY,IDTENANT) VALUES ('10','Y',null,10,'9','1');
INSERT INTO "TENANTPOLICY_SEG" (ID,ENABLE,EXECUTEBYADMIN,ORDERING,IDPOLICY,IDTENANT) VALUES ('11','Y',null,11,'12','1');
SET IDENTITY_INSERT TENANTPOLICY_SEG OFF

SET IDENTITY_INSERT TENANTPOLICYPARAMS_SEG ON
INSERT INTO "TENANTPOLICYPARAMS_SEG" (ID,PARAMVALUE,POLICYPARAM_SEG_ID,IDTENANTPOLICY) VALUES ('1','3','1','2');
INSERT INTO "TENANTPOLICYPARAMS_SEG" (ID,PARAMVALUE,POLICYPARAM_SEG_ID,IDTENANTPOLICY) VALUES ('2','15','2','3');
INSERT INTO "TENANTPOLICYPARAMS_SEG" (ID,PARAMVALUE,POLICYPARAM_SEG_ID,IDTENANTPOLICY) VALUES ('3','5','3','5');
INSERT INTO "TENANTPOLICYPARAMS_SEG" (ID,PARAMVALUE,POLICYPARAM_SEG_ID,IDTENANTPOLICY) VALUES ('4','3','4','7');
INSERT INTO "TENANTPOLICYPARAMS_SEG" (ID,PARAMVALUE,POLICYPARAM_SEG_ID,IDTENANTPOLICY) VALUES ('5','^.*[0-9].*$ ^.*[a-zA-Z].*$ ^[a-zA-Z0-9_-]','5','8');
INSERT INTO "TENANTPOLICYPARAMS_SEG" (ID,PARAMVALUE,POLICYPARAM_SEG_ID,IDTENANTPOLICY) VALUES ('6','10','6','9');
INSERT INTO "TENANTPOLICYPARAMS_SEG" (ID,PARAMVALUE,POLICYPARAM_SEG_ID,IDTENANTPOLICY) VALUES ('7','5','7','9');
INSERT INTO "TENANTPOLICYPARAMS_SEG" (ID,PARAMVALUE,POLICYPARAM_SEG_ID,IDTENANTPOLICY) VALUES ('8','40','8','4');
INSERT INTO "TENANTPOLICYPARAMS_SEG" (ID,PARAMVALUE,POLICYPARAM_SEG_ID,IDTENANTPOLICY) VALUES ('9','5','9','10');
SET IDENTITY_INSERT TENANTPOLICYPARAMS_SEG OFF

SET IDENTITY_INSERT USER_SEG ON
INSERT INTO "USER_SEG" (ID,EMAIL,FAILEDATTEMPTS,FIRSTNAME,LASTLOGIN,LASTNAME,PASSWORDEXPIRATION,STATE,USERNAME,IDTENANT) VALUES ('1',null,3,'Admin','12/06/12','',null,0,'sysadmin@heinsohn.com.co','1');
SET IDENTITY_INSERT USER_SEG OFF

SET IDENTITY_INSERT USERPROFILE_SEG ON
INSERT INTO "USERPROFILE_SEG" (ID,IDPROFILE,IDUSER) VALUES ('1','1','1');
SET IDENTITY_INSERT USERPROFILE_SEG OFF


INSERT INTO "MODULE_SEG" (ID, NAME) VALUES ('1', 'Seguridad');

INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('1', 'Admon Usuarios', '/lion/security/user/userList.jsf', '1');
INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('2', 'Admon Perfiles', '/lion/security/profile/profileList.jsf', '1');
INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('3', 'Admon Companias', '/lion/security/tenant/tenantList.jsf', '1');
INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('4', 'Admon Sucursales', '/lion/security/branch/branchList.jsf', '1');
INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('5', 'Admon Politicas', '/lion/security/policy/tenantPolicyList.jsf', '1');
INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('6', 'Admon Permisos', '/lion/security/permission/permissionList.jsf', '1');
INSERT INTO "USECASE_SEG" (ID, NAME, URL, IDMODULE) VALUES ('7', 'Cambiar contrasena', '/lion/security/changePassword/changePassword.jsf', '1');

SET IDENTITY_INSERT PERMISSION_SEG ON
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('1', '1', '1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('2', '/lion/security/user/userEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('3', '2', '1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('4', '/lion/security/profile/profileEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('5', '3', '1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('6', '/lion/security/tenant/tenantEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('7', '4', '1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('8', '/lion/security/branch/branchEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('9', '5', '1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('10', '/lion/security/policy/tenantPolicyEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('11', '/lion/security/policy/tenantPolicyParams.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('12', '6', '1');
INSERT INTO "PERMISSION_SEG" (ID, URL, IDPROFILE) VALUES ('13', '/lion/security/permission/permissionEdit.jsf','1');
INSERT INTO "PERMISSION_SEG" (ID, IDUSECASE, IDPROFILE) VALUES ('14', '7', '1');
SET IDENTITY_INSERT PERMISSION_SEG OFF

