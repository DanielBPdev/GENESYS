package com.asopagos.enumeraciones;

public enum ResultadoRegistroContactoEnum {
	EMPLEADOR_AFILIADO(1, "Empleador ya afiliado"), 
	REINTEGRO_ACTIVA(2,	"Reintegro con cuenta WEB activa"), 
	REINTEGRO_INACTIVA(3, "Reintegro sin cuenta WEB"), 
	NUEVA_AFILIACION(4,	"Nueva afiliación"), 
	NO_AFILIABLE(5, "Empleador no afiliable"),
	AFILIACION_EN_PROCESO(6, "Afiliación en proceso"), 
	AFILIACION_WEB_EN_PROCESO(7,"Afiliacion WEB en proceso"),
	CLASIFICACION_INCORRECTA(8,"Empleador con clasificación distinta a la registrada en BD"),
	TIEMPO_DILIGENCIA_FORM(9,"El tiempo de diligenciamiento del formulario expiró"),
	USUARIO_NO_EXISTE(10,"No existe usuario al cual asignarle la solicitud");

	private Integer identificador;
	private String nombre;

	private ResultadoRegistroContactoEnum(Integer identificador, String nombre) {
		this.identificador = identificador;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public static boolean contains(String value) {
    if (value == null) {
        return false;
    }
    for (ResultadoRegistroContactoEnum e : ResultadoRegistroContactoEnum.values()) {
        if (e.getNombre().equalsIgnoreCase(value)) {
            return true;
        }
    }
    return false;
}
}
