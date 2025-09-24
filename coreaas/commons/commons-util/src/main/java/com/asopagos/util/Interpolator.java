package com.asopagos.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Utilitario para remplazar parámetros en cadenas.
 * 
 * @author <a href="mailto:ogiral@heinsohn.com.co">Leonardo Giral</a>
 */
public class Interpolator {

    /**
     * Número máximo de parámetros que se pueden remplazar
     */
    public static final int MAX_PARAMS = 10;

    /**
     * Reemplaza los parámetros enviados en <code>params</code> en la cadena
     * <code>string</code><br>
     * p.e. Para la <code>cadena Hola {0} nuevo {1}.</code> y los parámetros
     * <code>"mundo", "!"</code><br>
     * se produce la cadena: <code>"Hola mundo nuevo !</code>""
     * 
     * @param string
     *            Cadena base sobre la que se reemplazaran los parámetros
     * @param params
     *            Parámetros a ser remplazados en la cadena base
     * @return cadena con los parámetros remplazados
     * @throws IllegalArgumentException
     *             cuando se envían más de {@link MAX_PARAMS}
     */
    public static String interpolate(String string, Object... params) {
        if (params == null) {
            params = new Object[0];
        }

        if (params.length > MAX_PARAMS) {
            throw new IllegalArgumentException("Más de " + MAX_PARAMS
                    + " a remplazar");
        }

        if (string != null) {
            if (string.indexOf('#') >= 0 || string.indexOf('{') >= 0) {
                string = interpolateExpressions(string, params);
            }            
        }

        return string;
    }

    private static String interpolateExpressions(String string,
            Object... params) {
        StringTokenizer tokens = new StringTokenizer(string, "#{}", true);
        StringBuilder builder = new StringBuilder(string.length());
        try {
            while (tokens.hasMoreTokens()) {
                String tok = tokens.nextToken();

                if ("{".equals(tok)) {
                    StringBuilder expr = new StringBuilder();

                    expr.append(tok);
                    int level = 1;

                    while (tokens.hasMoreTokens()) {
                        String nextTok = tokens.nextToken();
                        expr.append(nextTok);

                        if (nextTok.equals("{")) {
                            ++level;
                        } else if (nextTok.equals("}")) {
                            if (--level == 0) {
                                try {
                                    if (params.length == 0) {
                                        builder.append(expr.toString());
                                    } else {
                                        String value = new MessageFormat(
                                                expr.toString(),
                                                Locale.getDefault())
                                                .format(params);
                                        builder.append(value);
                                    }
                                } catch (Exception e) {
                                    builder.append(expr);
                                }
                                expr = null;
                                break;
                            }
                        }
                    }

                    if (expr != null) {
                        builder.append(expr);
                    }
                } else {
                    builder.append(tok);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return builder.toString();
    }

}
