package com.asopagos.cache.impl.infinispan;

import javax.cache.configuration.Factory;

public class ServicioCacheLoaderFactory implements Factory<ServicioCacheLoader> {

	private static final long serialVersionUID = 8256119594344722545L;

	@Override
	public ServicioCacheLoader create() {
		return new ServicioCacheLoader();
	}
}
