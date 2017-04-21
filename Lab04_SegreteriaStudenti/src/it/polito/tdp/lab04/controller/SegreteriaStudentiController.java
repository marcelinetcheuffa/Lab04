package it.polito.tdp.lab04.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {
	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ComboBox<Corso> comboCorso;

	@FXML
	private Button btnCercaIscrittiCorso;

	@FXML
	private TextField txtMatricola;

	@FXML
	private Button btnCercaNome;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCognome;

	@FXML
	private Button btnCercaCorsi;

	@FXML
	private Button btnIscrivi;

	@FXML
	private TextArea txtResult;

	@FXML
	private Button btnReset;

	@FXML
	void doCercaCorsi(ActionEvent event) {
		txtResult.clear();
		String inserita = txtMatricola.getText();
		if (!model.isValidID(inserita)) {
			txtResult.appendText("Formato matricola non corretto");
			return;
		}
		int matricola = Integer.parseInt(inserita);
		Studente stud = model.getStudentByID(matricola);
		if (stud == null) {
			txtResult.appendText("La matricola inserita non esiste");
			return;
		}
		List<Corso> corsiByStud = model.getCorsiByStudente(stud);
		for (Corso cbs : corsiByStud) {
			txtResult.appendText(cbs + "\n");
		}

	}

	@FXML
	void doCercaIscrittiCorso(ActionEvent event) {
		txtResult.clear();
		Corso corso = comboCorso.getValue();
		String inserita = txtMatricola.getText();
		if (corso == null) {
			txtResult.appendText("Selezionare un corso");
			return;
		}
		List<Studente> list = model.getStudentiByCorso(corso);
		for (Studente stud : list) {
			txtResult.appendText(stud + "\n");
		}
		if(corso!=null && inserita.length()!=0){
			if (!model.isValidID(inserita)) {
				txtResult.appendText("Formato matricola non corretto");
				return;
			}
			int matricola = Integer.parseInt(inserita);
			Studente stud = model.getStudentByID(matricola);
			if (stud == null) {
				txtResult.appendText("La matricola inserita non esiste");
				return;
			}
		  List<Studente> allIScritti = model.getAllIscritti();
		   if(allIScritti.contains(stud)){
			  txtResult.appendText("Studente iscritto ad un Corso");
		   }
		   else{
			   txtResult.appendText("Studente non iscritto ad alcun corso");
		   }
		   List<Studente> iscrittiAC = model.getStudentiByCorso(corso);
		   if(iscrittiAC==null){
			   txtResult.appendText("Corso senza iscritti");
		   }
			
		}
	}

	@FXML
	void doCercaNome(ActionEvent event) {
		txtResult.clear();
		String inserita = txtMatricola.getText();
		if (!model.isValidID(inserita)) {
			txtResult.appendText("Formato matricola non corretto");
			return;
		}
		int matricola = Integer.parseInt(inserita);
		Studente stud = model.getStudentByID(matricola);
		if (stud == null) {
			txtResult.appendText("La matricola inserita non esiste");
			return;
		}
		txtNome.setText(stud.getNome());
		txtCognome.setText(stud.getCognome());
	}

	@FXML
	void doIscrivi(ActionEvent event) {
		txtResult.clear();
		Corso corso = comboCorso.getValue();
		String inserita = txtMatricola.getText();
		if (corso == null || inserita.length() == 0) {
			txtResult.appendText("Selezionare un corso e inserire una Matricola");
			return;
		}
		if (!model.isValidID(inserita)) {
			txtResult.appendText("Formato matricola non corretto");
			return;
		}
		int matricola = Integer.parseInt(inserita);
		Studente stud = model.getStudentByID(matricola);
		if (stud == null) {
			txtResult.appendText("La matricola inserita non esiste");
			return;
		}
		List<Studente> students = model.getStudentiByCorso(corso);
		if (students.contains(stud)) {
			txtResult.appendText("Studente già iscritto a quel corso");
			return;
		}
		else{
		students.add(stud);
		txtResult.appendText(String.format("Studente %s iscritto correttamete al corso %s ",stud.toString(),corso.toString()));
		}
	}

	@FXML
	void doReset(ActionEvent event) {
		txtResult.clear();
		txtMatricola.clear();
		txtCognome.clear();
		txtNome.clear();

	}

	@FXML
	void initialize() {
		assert comboCorso != null : "fx:id=\"comboCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaNome != null : "fx:id=\"btnCercaNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		comboCorso.getItems().add(null);
		comboCorso.getItems().addAll(model.getAllCorsi());
	}
}
