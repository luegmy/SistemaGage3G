package com.tresg.almacen.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.Query;

import org.eclipse.persistence.config.QueryHints;

import com.tresg.almacen.interfaz.DetalleAlmacenDAO;

import com.tresg.almacen.jpa.DetalleAlmacenJPA;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.util.jpa.JpaUtil;


public class MysqlDetalleAlmacenDAO implements DetalleAlmacenDAO {
	EntityManager em = null;
	private void open() {
		 em = JpaUtil.getEntityManager();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetalleAlmacenJPA> listarDetalleAlmacen() {
		open();
		Query q=em.createNamedQuery(DetalleAlmacenJPA.LISTAR_DETALLE_ALMACEN).setHint(QueryHints.REFRESH, true);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductoJPA> listarExistencias(String descripcion) {
		open();
		List<ProductoJPA>productos=new ArrayList<>();
		Query q = em.createNamedQuery(ProductoJPA.LISTAR_PRODUCTO_EXISTENCIA).setParameter("x", "%"+ descripcion+ "%");;
		
		List<Object[]> lista=q.getResultList();
		
		for (int i = 0; i < lista.size(); i++) {
			Object[] arr = lista.get(i);
			ProductoJPA obj=new ProductoJPA();
			for (int j = 0; j < arr.length; j++) {
				obj.setCodProducto((int) (arr[0]));
				obj.setDescripcion(arr[1].toString());
				obj.setDescripcionTipo(arr[2].toString());
				obj.setPrecioVenta((BigDecimal) arr[3]);
				obj.setDescripcionMedida(arr[4].toString());
				obj.setCantidad((int) (long) arr[5]);	
				obj.setProdSunat(arr[6].toString());
			}
			productos.add(obj);
		}

		return productos;
	}
	

}
