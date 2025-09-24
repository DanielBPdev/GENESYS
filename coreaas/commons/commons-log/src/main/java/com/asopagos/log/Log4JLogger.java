package com.asopagos.log;

import org.apache.log4j.Logger;

/**
 * <b>Descripción:</b> Implementación de ILogger que usa el framework Log4J
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
class Log4JLogger implements ILogger {

	/**
	 * Logger envuelto de Log4J
	 */
	private final Logger wrapped;

	public Log4JLogger(Class clazz) {
		wrapped = Logger.getLogger(clazz);
	}

	/**
	 * @see com.asopagos.log.AbstractLogger#info(java.lang.Object)
	 */
	@Override
	public void info(Object object) {
		wrapped.info(object);
	}

	/**
	 * @see com.asopagos.log.AbstractLogger#info(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	@Override
	public void info(Object object, Throwable throwable) {
		wrapped.info(object, throwable);
	}

	/**
	 * @see com.asopagos.log.AbstractLogger#trace(java.lang.Object)
	 */
	@Override
	public void trace(Object object) {
		wrapped.trace(object);
	}

	/**
	 * @see com.asopagos.log.AbstractLogger#trace(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	@Override
	public void trace(Object object, Throwable throwable) {
		wrapped.trace(object, throwable);
	}

	/**
	 * @see com.asopagos.log.AbstractLogger#debug(java.lang.Object)
	 */
	@Override
	public void debug(Object object) {
		wrapped.debug(object);
	}

	/**
	 * @see com.asopagos.log.AbstractLogger#debug(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	@Override
	public void debug(Object object, Throwable throwable) {
		wrapped.debug(object, throwable);
	}

	/**
	 * @see com.asopagos.log.AbstractLogger#error(java.lang.Object)
	 */
	@Override
	public void error(Object object) {
		wrapped.error(object);
	}

	/**
	 * @see com.asopagos.log.AbstractLogger#error(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	@Override
	public void error(Object object, Throwable throwable) {
		wrapped.error(object, throwable);
	}

	/**
	 * @see com.asopagos.log.AbstractLogger#fatal(java.lang.Object)
	 */
	@Override
	public void fatal(Object object) {
		wrapped.fatal(object);
	}

	/**
	 * @see com.asopagos.log.AbstractLogger#fatal(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	@Override
	public void fatal(Object object, Throwable throwable) {
		wrapped.fatal(object, throwable);
	}

	/**
	 * @see com.asopagos.log.AbstractLogger#warn(java.lang.Object)
	 */
	@Override
	public void warn(Object object) {
		wrapped.warn(object);
	}

	/**
	 * @see com.asopagos.log.AbstractLogger#warn(java.lang.Object,
	 *      java.lang.Throwable)
	 */
	@Override
	public void warn(Object object, Throwable throwable) {
		wrapped.warn(object, throwable);
	}

}
