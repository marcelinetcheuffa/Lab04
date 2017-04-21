package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	public Studente getStudentByID(int matricola){
	  String sql ="select matricola,cognome,nome,CDS from studente where matricola=?";
	  Connection conn; 
	  Studente stud =null;
	  try {
		conn =  ConnectDB.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1,matricola);
		ResultSet rs = st.executeQuery();
		if(rs.next()){
		  stud = new Studente(rs.getInt("matricola"),rs.getString("cognome"),
				           rs.getString("nome"),rs.getString("CDS"));
		}
		return stud;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		throw new RuntimeException("Errore DB", e);
	}
	  
	}
	public List<Studente> getTuttiIStudenti() {

		final String sql = "SELECT * FROM studente";

		List<Studente> studenti = new LinkedList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
              studenti.add(new Studente(rs.getInt("matricola"),rs.getString("cognome"),
            		  rs.getString("nome"),rs.getString("CDS")));
			}

			return studenti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	public List<Integer> getAllIscritti() {

		final String sql = "select distinct matricola from iscrizione";

		List<Integer> iscritti = new LinkedList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
              iscritti.add(rs.getInt("matricola"));
			}

			return iscritti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	public List<String> getCorsiByStudente(Studente stud) {
		  List<String> list = new LinkedList<>();
		  String sql="select codins from iscrizione where matricola=?";
		  Connection conn;
		  try {
			 conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,stud.getMatricola());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				list.add(rs.getString("codins"));
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore DB", e);
		}
		}
	


}
