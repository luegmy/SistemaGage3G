package com.tresg.ventas.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

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
		Query q = em.createNamedQuery(DetalleVentaJPA.LISTAR_DETALLE_VENTAS);
		//se realiza esta maniobra para mostrar detalle
		List<DetalleVentaJPA>lista=q.getResultList();
		DetalleVentaJPA objDetalle=null;
		for (DetalleVentaJPA d : lista) {
			objDetalle=em.find(DetalleVentaJPA.class, d.getId());
		}
		em.refresh(objDetalle);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetalleVentaJPA> consultarDetalleProductoPorVenta(Date fechaIni, Date fechaFin) {
		open();
		
		List<DetalleVentaJPA>detalles=new ArrayList<>();
		
		Query q = em.createNamedQuery(DetalleVentaJPA.LISTAR_PRODUCTOS_VENTAS);
		q.setParameter("p", fechaIni,TemporalType.DATE);
		q.setParameter("q", fechaFin,TemporalType.DATE);
		
		List<Object[]> lista=q.getResultList();
		
		for (int i = 0; i < lista.size(); i++) {
			Object[] arr = lista.get(i);
			DetalleVentaJPA obj=new DetalleVentaJPA();
			for (int j = 0; j < arr.length; j++) {
				obj.setCodigoProducto((int) (arr[0]));
				obj.setDescripcionProducto(arr[1].toString());
				//obj.setDescripcionTipo(arr[2].toString());
				obj.setCantidad(new BigDecimal(arr[3].toString()));
				obj.setPrecio(new BigDecimal(arr[4].toString()));
			}
			
			detalles.add(obj);
		}

		return detalles;
	}




}
