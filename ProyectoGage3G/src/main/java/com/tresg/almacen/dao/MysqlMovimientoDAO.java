package com.tresg.almacen.dao;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.tresg.almacen.interfaz.MovimientoDAO;
import com.tresg.almacen.jpa.DetalleAlmacenJPA;
import com.tresg.almacen.jpa.DetalleAlmacenJPAPK;
import com.tresg.almacen.jpa.DetalleMovimientoJPA;
import com.tresg.almacen.jpa.MovimientoJPA;
import com.tresg.util.jpa.JpaUtil;
import com.tresg.util.stock.ActualizarExistencia;

public class MysqlMovimientoDAO implements MovimientoDAO {

	EntityManager em = null;

	private void open() {
		em = JpaUtil.getEntityManager();
	}

	private void close() {
		em.close();
	}

	// clase utilitaria para actualizar el stock en almacen
	ActualizarExistencia existenciaUtil = new ActualizarExistencia();

	@Override
	public int obtenerNumero() {
		open();

		int numero;
		Query q = em.createNamedQuery(MovimientoJPA.MAXIMO_NUMERO_MOVIMIENTO);
		Integer numeroMovimiento = (Integer) q.getSingleResult();
		if (numeroMovimiento != null) {
			numero = numeroMovimiento;
		} else {
			numero = 100;
		}
		return numero;
	}

	@Override
	public int generarNumeroNota() {
		open();

		int nota;
		Query q = em.createNamedQuery(MovimientoJPA.NUMERACION_MOVIMIENTO_NOTA);
		Integer numeroNota = (Integer) q.getSingleResult();
		if (numeroNota != null) {
			nota = numeroNota;
		} else {
			nota = 1;
		}
		return nota;
	}

	@Override
	public String registrarMovimiento(MovimientoJPA movimiento, int destino) {
		open();

		String mensaje = "";

		em.getTransaction().begin();
		try {
			em.persist(movimiento);
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw e;
		}

		DetalleAlmacenJPA da;
		DetalleAlmacenJPAPK dapk;

		DetalleAlmacenJPA daAux;

		for (DetalleMovimientoJPA t : movimiento.getDetalles()) {

			dapk = new DetalleAlmacenJPAPK();
			dapk.setCodAlmacen(t.getId().getCodAlmacen());
			dapk.setCodProducto(t.getId().getCodProducto());

			da = new DetalleAlmacenJPA();
			da.setId(dapk);

			if (movimiento.getTipoMovimiento().getCodMovimiento() == 2) {
				// Cuando no hay stock al inicio
				daAux = em.find(DetalleAlmacenJPA.class, da.getId());
				if (daAux != null) {
					existenciaUtil.actualizarAlmacenIncremento(new BigDecimal(t.getCantidad()), da);
				} else {
					da.setExistencia(t.getCantidad());
					em.persist(da);
				}

				mensaje = "Se registro la entrada de productos";

			} else if (movimiento.getTipoMovimiento().getCodMovimiento() == 1) {
				existenciaUtil.actualizarAlmacenDecremento(new BigDecimal(t.getCantidad()), da);

				mensaje = "Se registro la salida de productos";

			} else {

				actualizarTransferencia(destino, da, t.getCantidad(), t.getId().getCodProducto());
				mensaje = "Se registro la transferencia de productos";
			}
		}
		em.getTransaction().commit();

		close();
		return mensaje;

	}

	void actualizarTransferencia(int destino, DetalleAlmacenJPA da, int cantidad, int producto) {

		existenciaUtil = new ActualizarExistencia();
		DetalleAlmacenJPAPK dapkDestino = new DetalleAlmacenJPAPK();
		DetalleAlmacenJPA daDestino = new DetalleAlmacenJPA();

		dapkDestino.setCodAlmacen(destino);
		dapkDestino.setCodProducto(producto);
		daDestino.setId(dapkDestino);

		DetalleAlmacenJPA daAux = em.find(DetalleAlmacenJPA.class, daDestino.getId());
		if (daAux != null) {
			existenciaUtil.actualizarAlmacenIncremento(new BigDecimal(cantidad), daDestino);
			existenciaUtil.actualizarAlmacenDecremento(new BigDecimal(cantidad), da);
		} else {
			existenciaUtil.actualizarAlmacenDecremento(new BigDecimal(cantidad), da);
			daDestino.setExistencia(cantidad);
			em.persist(daDestino);
		}

	}

}
