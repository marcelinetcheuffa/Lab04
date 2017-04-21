package it.polito.tdp.lab04.model;

import java.util.*;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
 private List<Corso> corsi;
 private List<Studente> studenti;
 private Map<Integer,Studente> studMap;
 private Map<String,Corso> corsoMap;
 private int myMatricola;
 private Corso myCorso;
 private List<Studente> tuttiGliIscritti;
 
 
 public Model(){
  CorsoDAO daoC = new CorsoDAO();
  StudenteDAO daoS = new StudenteDAO();
  studMap = new HashMap<>();
  corsoMap = new HashMap<>();
  this.corsi = daoC.getTuttiICorsi();
  this.studenti = daoS.getTuttiIStudenti();
  this.tuttiGliIscritti = new LinkedList<>();
  
  for(Studente stud : this.studenti){
    studMap.put(stud.getMatricola(), stud);
  }
  
  for(Corso corso : corsi){
	  corsoMap.put(corso.getCodIns(), corso) ;
  }
  for(int id : daoS.getAllIscritti()){
	  tuttiGliIscritti.add(studMap.get(id));
  }
 
 }
 public List<Studente> getAllIscritti(){
	 return this.tuttiGliIscritti;
 }
 public List<Corso> getAllCorsi(){
 return this.corsi;
 }
 public Studente getStudentByID(int matricola){   // méthode avec efficienza
  Studente studente = null;
  if(myMatricola!=matricola){
	myMatricola = matricola;
	StudenteDAO dao = new StudenteDAO();
     studente = dao.getStudentByID(myMatricola); 
  }
  return studente;
 }

public boolean isValidID(String inserita) {
   char[] array = inserita.toCharArray();
	for(int i=0;i<array.length;i++){
	  if(!Character.isDigit(array[i])){ // au cas ou il faut controller les lettres c'est isALphabetic
		return false;
	  }
	}
	return true;
}
public List<Studente> getStudentiByCorso(Corso corso){
  List<Studente> students = new LinkedList<>();
  if(myCorso==null || !myCorso.equals(corso)){
	  this.myCorso = corso;
	  CorsoDAO dao = new CorsoDAO();
	  List<Integer> matricole = dao.getStudentiIscrittiAlCorso(corso);
	  for(int matricola: matricole){
		  students.add(studMap.get(matricola));
	  }
  }
return students;
}

public List<Corso> getCorsiByStudente(Studente stud){ // méthode simple
	List<Corso> corsi = new LinkedList<>();
	StudenteDAO dao = new StudenteDAO() ;
	List<String> codinsList = dao.getCorsiByStudente(stud) ;
	for(String codins : codinsList ){
		corsi.add(corsoMap.get(codins));
	}
	return corsi;
}



}
