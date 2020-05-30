package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Event;
import it.polito.tdp.poweroutages.model.Event.TipoEvento;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.Rel;

public class PowerOutagesDAO {
	
	public List<Nerc> loadAllNercs() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}

	public List<Rel> getRel(Map<Integer, Nerc> mN) {
		

			String sql = "SELECT n.id,n.nerc_one n1,n.nerc_two n2\n" + 
					"FROM nercrelations AS n";
			List<Rel> lR = new LinkedList<>();

			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet res = st.executeQuery();

				while (res.next()) {
					Nerc n1 = mN.get(res.getInt("n1"));
					Nerc n2 = mN.get(res.getInt("n2"));
					Rel r = new Rel (n1, n2 , res.getInt("id"));
					lR.add(r);
				}

				conn.close();

			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

			return lR;
		
	}
	
	public double getPeso (Nerc n1 , Nerc n2) {
		String sql = "SELECT COUNT(*) " + 
				"FROM poweroutages p1, poweroutages p2  " + 
				"WHERE p1.nerc_id!=p2.nerc_id AND p1.nerc_id=? AND p2.nerc_id=?  " + 
				"AND MONTH(p1.date_event_began)=MONTH(p2.date_event_began) " + 
				"AND YEAR(p1.date_event_began)=YEAR(p2.date_event_began) " + 
				"GROUP BY MONTH(p1.date_event_began),YEAR(p1.date_event_began)";
		double cont =0.0;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,n1.getId());
			st.setInt(2,n2.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				cont++;
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return cont;
	}

	public List<Event> getE(int m,Map <Integer, Nerc> mappaN) {
		String sql = "SELECT p.nerc_id nid , p.date_event_began dI , p.date_event_finished dF " + 
				"FROM poweroutages  p " + 
				"WHERE YEAR(p.date_event_began)>=2014 AND MONTH(p.date_event_began)<=?";
		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,m);
			List<Event> lEv = new LinkedList<>();
			ResultSet res = st.executeQuery();

			while (res.next()) {
				lEv.add(new Event (TipoEvento.INIZIO, res.getTimestamp("dI").toLocalDateTime(), mappaN.get(res.getInt("nid"))));
				lEv.add(new Event (TipoEvento.FINE, res.getTimestamp("dF").toLocalDateTime(), mappaN.get(res.getInt("nid"))));
			}

			conn.close();
			return lEv;

		} catch (SQLException e) {
			throw new RuntimeException(e);
			
		}

		
		
	}
}

