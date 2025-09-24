package com.asopagos.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.asopagos.validation.ActualizacionConstraintValidator;

/**
 * <b>Descripción:</b> Anotación que permite identificar los atributos que y 
 * parámetros sobre los cuales se deben aplicar validaciones del grupo de 
 * actulización
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Constraint(validatedBy = ActualizacionConstraintValidator.class)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ValidacionActualizacion {

    String message() default "Mensaje de error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
