package com.tresg.util.formato;

import java.io.Serializable;
import java.util.regex.Pattern;

public class MontoEnLetras implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String[] unidades = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
    private static final String[] decenas = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
        "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ",
        "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
    private static final String[] centenas = {"", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
        "setecientos ", "ochocientos ", "novecientos "};
    
    public String convertir(String numero, boolean mayusculas) {
        String literal = "";
        String numeroLiteral;
   
        //si el numero utiliza (.) en lugar de (,) -> se reemplaza
        numeroLiteral = numero.replace(".", ",");
        //si el numero no tiene parte decimal, se le agrega ,00
        if(numeroLiteral.indexOf(',')==-1){
        	numeroLiteral = numero .concat(",00");
        }
        //se valida formato de entrada -> 0,00 y 999 999 999,00
        if (Pattern.matches("\\d{1,9},\\d{1,2}", numeroLiteral)) {
            //se divide el numero 0000000,00 -> entero y decimal
            String [] numeroArreglo = numeroLiteral.split(",");            
            //de da formato al numero decimal
            String parteDecimal = "con".concat(" ").concat(numeroArreglo[1]).concat("/100 Soles");
            //se convierte el numeroArregloero a literal
            if (Integer.parseInt(numeroArreglo[0]) == 0) {//si el valor es cero
                literal = "cero ";
            } else if (Integer.parseInt(numeroArreglo[0]) > 999999) {//si es millon
                literal = getMillones(numeroArreglo[0]);
            } else if (Integer.parseInt(numeroArreglo[0]) > 999) {//si es miles
                literal = getMiles(numeroArreglo[0]);
            } else if (Integer.parseInt(numeroArreglo[0]) > 99) {//si es centena
                literal = getCentenas(numeroArreglo[0]);
            } else if (Integer.parseInt(numeroArreglo[0]) > 9) {//si es decena
                literal = getDecenas(numeroArreglo[0]);
            } else {//sino unidades -> 9
                literal = getUnidades(numeroArreglo[0]);
            }
            //devuelve el resultado en mayusculas o minusculas
            if (mayusculas) {
                return literal.concat(parteDecimal).toUpperCase();
            } else {
                return literal.concat(parteDecimal);
            }
        } else {//error, no se puede convertir
            return literal;
        }
    }

    /* funciones para convertir los numeros a literales */

    private String getUnidades(String numero) {// 1 - 9
        //si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9
        String num = numero.substring(numero.length() - 1);
        return unidades[Integer.parseInt(num)];
    }

    private String getDecenas(String num) {// 99                        
        int n = Integer.parseInt(num);
        if (n < 10) {//para casos como -> 01 - 09
            return getUnidades(num);
        } else if (n > 19) {//para 20...99
            String u = getUnidades(num);
            if ("".equals(u)) { //para 20,30,40,50,60,70,80,90
                return decenas[Integer.parseInt(num.substring(0, 1)) + 8];
            } else {
                return decenas[Integer.parseInt(num.substring(0, 1)) + 8].concat("y ").concat(u);
            }
        } else {//numeros entre 11 y 19
            return decenas[n - 10];
        }
    }

    private String getCentenas(String num) {// 999 o 099
        if( Integer.parseInt(num)>99 ){//es centena
            if (Integer.parseInt(num) == 100) {//caso especial
                return "cien ";
            } else {
                 return centenas[Integer.parseInt(num.substring(0, 1))].concat(getDecenas(num.substring(1)));
            } 
        }else{//por Ej. 099 
            //se quita el 0 antes de convertir a decenas
        	int decena=Integer.parseInt(num);
            return getDecenas(Integer.toString(decena));            
        }        
    }

    private String getMiles(String numero) {// 999 999
        //obtiene las centenas
        String c = numero.substring(numero.length() - 3);
        //obtiene los miles
        String m = numero.substring(0, numero.length() - 3);
        String n;
        //se comprueba que miles tenga valor entero
        if (Integer.parseInt(m) > 0) {
            n = getCentenas(m);           
            return n.concat("mil ").concat(getCentenas(c));
        } else {
            return "".concat(getCentenas(c));
        }

    }

    private String getMillones(String numero) { //000 000 000        
        //se obtiene los miles
        String miles = numero.substring(numero.length() - 6);
        //se obtiene los millones
        String millon = numero.substring(0, numero.length() - 6);
        String n;
        if(millon.length()>1){
            n = getCentenas(millon).concat("millones ");
        }else{
            n = getUnidades(millon).concat("millon ");
        }
        return n.concat(getMiles(miles));        
    }

}
