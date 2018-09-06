package com.tresg.util.optional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	
	public static void main(String[] args) {
		BigDecimal a=new BigDecimal("25.00");
		BigDecimal b=new BigDecimal("1.18");
		
		System.out.println("--"+a.divide(b,2, RoundingMode.UP));
		System.out.println("--"+a.divide(b,2, RoundingMode.DOWN));
		System.out.println("--"+a.divide(b,2, RoundingMode.CEILING));
		System.out.println("--"+a.divide(b,2, RoundingMode.FLOOR));
		System.out.println("--"+a.divide(b,2, RoundingMode.HALF_UP));
		System.out.println("--"+a.divide(b,2, RoundingMode.HALF_DOWN));
		System.out.println("--"+a.divide(b,2, RoundingMode.HALF_EVEN));
	}


}
