UPDATE GradoAcademico
SET graNivelEducativo = 'BASICA_PRIMARIA_INCOMPLETA_ADULTOS'
WHERE graId IN (23, 24, 25, 26) and graNivelEducativo = 'BASICA_PRIMARIA_INCOMPLETA'

UPDATE GradoAcademico
SET graNivelEducativo = 'BASICA_SECUNDARIA_INCOMPLETA_ADULTOS'
WHERE graId IN (28, 29, 30) and graNivelEducativo = 'BASICA_SECUNDARIA_INCOMPLETA'

UPDATE GradoAcademico
SET graNivelEducativo = 'BASICA_PRIMARIA_COMPLETA_ADULTOS'
WHERE graId = 27 and graNivelEducativo = 'BASICA_PRIMARIA_COMPLETA'

UPDATE GradoAcademico
SET graNivelEducativo = 'BASICA_SECUNDARIA_COMPLETA_ADULTOS'
WHERE graId = 31 and graNivelEducativo = 'BASICA_SECUNDARIA_COMPLETA'

UPDATE GradoAcademico
SET graNivelEducativo = 'MEDIA_COMPLETA_ADULTOS'
WHERE graId = 33 and graNivelEducativo = 'MEDIA_COMPLETA'

UPDATE GradoAcademico
SET graNivelEducativo = 'MEDIA_INCOMPLETA_ADULTOS'
WHERE graId = 34 and graNivelEducativo = 'MEDIA_INCOMPLETA'