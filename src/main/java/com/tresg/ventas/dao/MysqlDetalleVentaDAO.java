package com.tresg.ventas.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.QueryHints;

import com.tresg.util.jpa.JpaUtil;
import com.tresg.ventas.interfaz.DetalleVentaDAO;
import com.tresg.ventas.jpa.DetalleVentaJPA;

public class MysqlDetalleVentaDAO implements DetalleVentaDAO {
	
	EntityManager em = null;

	private void open() {
		em = JpaUtil.getEntityManager();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DetalleVentaJPA> listarDetalleVenta() {
		open();
		Query q = em.createNamedQuery(DetalleVentaJPA.LISTAR_DETALLE_VENTAS).setHint(QueryHints.REFRESH, true);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetalleVentaJPA> consultarDetalleProductoPorVenta() {
		open();
		
		List<DetalleVentaJPA>detalles=new ArrayList<>();
		
		Query q = em.createNamedQuery(DetalleVentaJPA.LISTAR_PRODUCTOS_VENTAS);
		
		List<Object[]> lista=q.getResultList();
		
		for (int i = 0; i < lista.size(); i++) {
			Object[] arr = lista.get(i);
			DetalleVentaJPA obj=new DetalleVentaJPA();
			for (int j = 0; j < arr.length; j++) {
				obj.setCodigoProducto((int) (arr[0]));
				obj.setDescripcionProducto(arr[1].toString());
				obj.setDescripcionTipoProducto(arr[2].toString());
				obj.setCantidadSuma((int)arr[3]);
				obj.setCantidadMonto((double) arr[4]);
			}
			
			detalles.add(obj);
		}

		return detalles;
	}

}
