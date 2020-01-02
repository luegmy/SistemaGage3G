package com.tresg.util.optional;

import java.util.Optional;


public class ValoresNulos {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object optionalCadenas(Object valor) {
		Optional op = Optional.ofNullable(valor);
		return op.orElse("");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object optionalEnteros(Object valor) {
		Optional op = Optional.ofNullable(valor);
		return op.orElse(0);
	}


}
