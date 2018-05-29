package com.tresg.ventas.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tresg.incluido.jpa.EstadoJPA;
import com.tresg.incluido.jpa.MediosPagoJPA;
import com.tresg.ventas.interfaz.CobranzaDAO;
import com.tresg.ventas.jpa.CobranzaFacturaJPAPK;
import com.tresg.ventas.jpa.CobranzaJPA;
import com.tresg.ventas.jpa.VentaJPA;

public class MysqlCobranzaDAO implements CobranzaDAO {

	EntityManagerFactory emf = null;
	EntityManager em = null;

	private void open() {
		emf = Persistence.createEntityManagerFactory("tresg");
		em = emf.createEntityManager();
	}

	private void close() {
		em.close();
		emf.close();
	}

	@Override
	public String actualizarFacturaCredito(CobranzaJPA cobranza) {
		open();

		String mensaje;
		CobranzaFacturaJPAPK cf = null;

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

				cobranza.setFecha(new java.sql.Date(new java.util.Date().getTime()));
				cobranza.setMontoPago(cobranza.getMontoPago());
				cobranza.setMontoSaldo(saldo);
				cobranza.setId(cf);

				em.merge(cobranza);

				mensaje = "Cobranza realizada";

			}

			if (saldo.compareTo(BigDecimal.ZERO) == 0) {

				MediosPagoJPA objPago = new MediosPagoJPA();
				objPago.setCodPago(1);
				
				EstadoJPA objEstado=new EstadoJPA();
				objEstado.setCodEstado(1);

				VentaJPA objVenta = em.find(VentaJPA.class, cobranza.getId().getNumComprobante());
				objVenta.setPago(objPago);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<CobranzaJPA> listarFacturasPorCobrar() {
		open();
		Query q = em.createNamedQuery(CobranzaJPA.LISTAR_COBRANZA);
		
		List<CobranzaJPA>lista=q.getResultList();
		CobranzaJPA objCobranza = null;
		for (CobranzaJPA m : lista) {
			objCobranza=em.find(CobranzaJPA.class, m.getId());
		}
		em.refresh(objCobranza);

		return lista;
	}

}
