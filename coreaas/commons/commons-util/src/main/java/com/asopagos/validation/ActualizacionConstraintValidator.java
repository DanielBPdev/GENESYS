package com.asopagos.validation;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.validacion.GrupoActualizacion;
import com.asopagos.validation.annotation.ValidacionActualizacion;

/**
 * <b>Descripción:</b> Clase que implementa las validaciones de los objetos que
 * se anotan con ValidacionActualizacion 
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class ActualizacionConstraintValidator implements ConstraintValidator<ValidacionActualizacion, Object> {
    
    
    /**
     * Referencia al validador de Bean Validation
     */
    private Validator validator;
    
    
    /**
     * @param params
     * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)  
     */
    @Override
    public void initialize(ValidacionActualizacion params) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * @param object El objeto a ser validado
     * @param context
     * @return true si el objeto Serializable pasa todas las validaciones, false
     * en caso contrario
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext) 
     */
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        
        /* Acorde a las recomendaciones de la especificación los objetos nulos 
        deben ser válidos, en caso contrario se debe usar explicitamente la
        anotación @NotNull donde se requiera */
        if (object == null) {
            return true;
        }

        if (object instanceof Collection) {
            for (Object obj : (Collection) object) {
                //Valida que los elementos de una colección no sean nulos
                if (obj == null) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(MensajesGeneralConstants.ERROR_ELEMENTO_DE_LISTA_NULO)
                            .addPropertyNode("elemento").addConstraintViolation();
                    return false;
                }                
                boolean validado = validarObjeto(obj, context);
                if (!validado) {
                    return false;
                }
            }
            return true;
        } else {
            return validarObjeto(object, context);
        }
    }
    
    /**
     * Validación de un objeto individual
     */
    private boolean validarObjeto(Object object, ConstraintValidatorContext context) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, GrupoActualizacion.class);
        if (constraintViolations.isEmpty()) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();
            while (iter.hasNext()) {
                ConstraintViolation<Object> violation = iter.next();
                context.buildConstraintViolationWithTemplate(violation.getMessage())
                        .addPropertyNode(violation.getPropertyPath().toString())
                        .addConstraintViolation();
            }
            return false;
        }        
    }
    
}