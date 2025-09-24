package com.asopagos.util;

import javax.persistence.EnumType;
import com.asopagos.util.EncryptionData.FLAGS;

/**
 * Utilitario temporal para dar soporte a Asopagos en la generación de sus claves.
 * @author <a href="mailto:ogiral@heinsohn.com.co"> ogiral</a>
 */
public class EncrypUtil {

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args[0].equalsIgnoreCase("-h")) {
            System.out.println(getHelp());
        }
        else {
            EncryptionData ecyData = new EncryptionData(args);
            DesEncrypter desc = new DesEncrypter(ecyData.getkey());

            if (ecyData.getOperation().equals(FLAGS.D)) {
                System.out.println("\n\n\tEl resultado es: " + desc.decrypt(ecyData.getValue()));
            }
            else {
                System.out.println("\n\n\tEl resultado es: " + desc.encrypt(ecyData.getValue()));
            }
        }
    }

    private static String getHelp() {
        StringBuilder text = new StringBuilder();
        text.append("\n\t");
        text.append("El valor a trabajar debe indicarse siempre como primer argumento");
        text.append("\n\tDebe indicarse alguno de los flags -D o -E y opcional el flag -S");
        text.append("\n\tEl flag -D indica que se debe desencriptar el valor indicado");
        text.append("\n\tEl flag -E indica que se debe encriptar el valor indicado");
        text.append(
                "\n\tEl flag -S indica que la operación a realizar utiliza un KEY diferente al default e inmediatamente debe indicarse el valor del key a utilizar");
        text.append("\n\n\n\tEjemplos: Para encriptar la palbra \"password\", se debe ejecutar:");
        text.append("\n\tjava -jar encryptorUtil.jar password -E");
        text.append("\n\n\tPara desencriptar el valor cifrado \"eMlgzcygjZmSki0/XxCOIqdZQr/jlNU7\", se debe ejecutar: ");
        text.append("\n\tjava -jar encryptorUtil.jar eMlgzcygjZmSki0/XxCOIqdZQr/jlNU7 -D");
        text.append("\n\n\tPara indicar una operación con un key personalizado se debe invocar: ");
        text.append("\n\tjava -jar encryptorUtil.jar password -E -ScustomKey");
        text.append("\n\n\tRecuerde que la desencripción requiere debe hacerse con la misma key con la que se generó");
        return text.toString();
    }
}

class EncryptionData {

    /**
     * Posibles modificadores para ejecución, S: adiciona SALT, D: desencriptar, E: encriptar
     */
    enum FLAGS {
        S, D, E
    };

    /**
     * Valor semilla por defecto con el que se entrega la solución. Es creado únicamente para efectos de ejecución en modo utilitario
     * standAlone
     */
    private static final String SALT = "euNuWPuienBrq2t/lEnpCw3f3IpFuQRA";

    private String key = SALT;
    private String valueToWork;
    private FLAGS operation;

    /**
     * @param key
     * @param valueToWork
     * @param operation
     */
    EncryptionData(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("No se han expresado argumentos de ejecución");
        }

        valueToWork = args[0];

        String flag;
        for (int i = 1; i < args.length; i++) {
            flag = args[i].substring(1, 2);
            if (flag.equals(FLAGS.S.name())) {
                if (args[i].substring(2).length() == 0) {
                    throw new IllegalArgumentException("No se está indicando un valor válido para la llave");
                }
                key = args[i].substring(2);
            }
            else {
                if (flag.equals(FLAGS.E.name()) || flag.equals(FLAGS.D.name())) {
                    if (operation != null) {
                        throw new IllegalArgumentException("Se está estableciendo más de un valor de operación");
                    }
                    operation = EnumType.valueOf(FLAGS.class, flag);
                }
                else {
                    throw new IllegalArgumentException("Se está indicando un flag inválido");
                }
            }
        }
    }

    FLAGS getOperation() {
        return operation;
    }

    String getValue() {
        return valueToWork;
    }

    String getkey() {
        return key;
    }
}