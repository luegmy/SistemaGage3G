package com.tresg.util.converter;


import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.tresg.almacen.jsf.RegistroMovimientoBean;
import com.tresg.incluido.jpa.ProductoJPA;

@FacesConverter("productoConverter")

public class ProductoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (context == null) {
			throw new NullPointerException("context");
		}
		if (component == null) {
			throw new NullPointerException("component");
		}
		FacesContext ctx = FacesContext.getCurrentInstance();
		ValueExpression vex = ctx.getApplication().getExpressionFactory().createValueExpression(ctx.getELContext(),
				"#{almacenBean}", RegistroMovimientoBean.class);
		RegistroMovimientoBean bean = (RegistroMovimientoBean) vex.getValue(ctx.getELContext());
		ProductoJPA objProducto;
		try {
			objProducto = bean.obtenerProductoPorCodigo(new Integer(value));
		} catch (NumberFormatException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valor Desconocido",
					"Error en Codigo");
			throw new ConverterException(message);
		}
		if (objProducto == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valor Desconocido",
					"Error en Objeto");
			throw new ConverterException(message);
		}
		return objProducto;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (context == null) {
            throw new NullPointerException("context");
        }
        if (component == null) {
            throw new NullPointerException("component");
        }
        return String.valueOf(((ProductoJPA) value).getCodProducto());
	}

}
