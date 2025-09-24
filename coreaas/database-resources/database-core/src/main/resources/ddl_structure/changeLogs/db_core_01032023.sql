CREATE TABLE GestorDocumentalFolium (
	gdfid smallint IDENTITY(1,1) NOT NULL,
	gdfcanal VARCHAR(255),
    gdfcodigoRegistro_canal VARCHAR(255),
    gdftipoDocumental VARCHAR(255),
    gdftema VARCHAR(255),
    gdftipoIdentificacion VARCHAR(255),
    gdfnumeroIdentificacion VARCHAR(255),
    gdfnombre VARCHAR(255),
    gdfciudad VARCHAR(255),
    gdfbarrio VARCHAR(255),
    gdfdireccion VARCHAR(255),
    gdftelefono VARCHAR(255),
    gdfcelular VARCHAR(255),
    gdfemail VARCHAR(255),
    gdfobservacion VARCHAR(255),
    gdfgenerarArchivo BIT,
    gdfusuario VARCHAR(255)
    
);