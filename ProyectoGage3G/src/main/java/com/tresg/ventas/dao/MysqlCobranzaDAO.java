package com.tresg.ventas.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.tresg.incluido.jpa.EstadoJPA;
import com.tresg.util.formato.Formateo;
import com.tresg.util.jpa.JpaUtil;
import com.tresg.ventas.interfaz.CobranzaDAO;
import com.tresg.ventas.jpa.CobranzaFacturaJPAPK;
import com.tresg.ventas.jpa.CobranzaJPA;
import com.tresg.ventas.jpa.VentaJPA;

public class MysqlCobranzaDAO implements CobranzaDAO {

	EntityManager em = null;

	private void open() {
		em = JpaUtil.getEntityManager();
	}

	private void close() {
		em.close();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<CobranzaJPA> listarFacturasPorCobrar() {
		open();
		Query q = em.createNamedQuery(CobranzaJPA.LISTAR_COBRANZA);

		return q.getResultList();
	}
	
	@Override
	public String actualizarFacturaCredito(CobranzaJPA cobranza) {
		open();

		String mensaje;
		CobranzaFacturaJPAPK cf = null;
		Formateo formatoHora=new Formateo();

		em.getTransaction().begin();
		try {
			Query q = em.createNamedQuery(CobranzaJPA.MONTO_SALDO).setParameter("p",
					cobranza.getId().getNumComprobante());

			BigDecimal monto = (BigDecimal) q.getSingleResult();

			BigDecimal saldo = monto.subtract(cobranza.getMontoPago());

			if (monto.compareTo(cobranza.getMontoPago()) <= -1) {
				mensaje = "Monto ingresado supera la cuota de pago";

			} else {

				Query q2 = em.createNamedQuery(CobranzaJPA.GENERA_NUMERO_LETRA).setParameter("p",
						cobranza.getId().getNumComprobante());
				int numero = (int) q2.getSingleResult();

				cf = new CobranzaFacturaJPAPK();
				cf.setNumCobranza(numero);
				cf.setNumComprobante(cobranza.getId().getNumComprobante());

				cobranza.setFecha(new Date());
				cobranza.setHora(formatoHora.obtenerHora());
				cobranza.setMontoPago(cobranza.getMontoPago());
				cobranza.setMontoSaldo(saldo);
				cobranza.setId(cf);

				em.merge(cobranza);

				mensaje = "Cobranza realizada";

			}

			if (saldo.compareTo(BigDecimal.ZERO) == 0) {

				EstadoJPA objEstado=new EstadoJPA();
				objEstado.setCodEstado(9);

				VentaJPA objVenta = em.find(VentaJPA.class, cobranza.getId().getNumComprobante());
				objVenta.setEstado(objEstado);

				em.merge(objVenta);
			}

			em.getTransaction().commit();
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw e;
		}
		close();
		return mensaje;
	}

}
