/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.GeniAdiacenti;
import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGeni"
    private ComboBox<Genes> cmbGeni; // Value injected by FXMLLoader

    @FXML // fx:id="btnGeniAdiacenti"
    private Button btnGeniAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtIng"
    private TextField txtIng; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	
    	txtResult.clear();
    	cmbGeni.getItems().clear(); //pulisco la tendina ogni volta che ricreo il grafo
    	this.model.creaGrafo();
    	
    	// Ri-abilita i bottoni
        btnGeniAdiacenti.setDisable(false);
        btnSimula.setDisable(false);
        cmbGeni.setDisable(false);
        txtIng.setDisable(false);
    	
    	
    	txtResult.appendText("GRAFO CREATO!"+"\n");
    	txtResult.appendText("# VERTICI "+this.model.numVertici()+"\n");
    	txtResult.appendText("# ARCHI "+this.model.numArchi()+"\n");
    	cmbGeni.getItems().addAll(this.model.getVerticiTendina());
    	
    }

    @FXML
    void doGeniAdiacenti(ActionEvent event) {
    	
    	Genes scelta = this.cmbGeni.getValue();
    	if(scelta==null) {
    		txtResult.appendText("seleziona un gene"+"\n");
    		return ;
    	}
    	
    	this.doCreaGrafo(event);  //cosi se non l'ho creato richiama il grafo
    	txtResult.appendText(" "+"\n");
    	txtResult.appendText("GENI ADIACENTI DI "+scelta+"\n");
    	List<GeniAdiacenti> result = this.model.getGeniAdiacenti(scelta);
    	if(result.isEmpty()) {
    		txtResult.appendText("non esistono geni adiacenti al selezionato"+"\n");
    		return ;
    	}
    	
    	for(GeniAdiacenti ga: result) {
    		txtResult.appendText(ga+"\n");
    	}

    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGeni != null : "fx:id=\"cmbGeni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGeniAdiacenti != null : "fx:id=\"btnGeniAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtIng != null : "fx:id=\"txtIng\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

     // Disabilita i bottoni (finché non verrà creato il grafo)
        btnGeniAdiacenti.setDisable(true);
        btnSimula.setDisable(true);
        cmbGeni.setDisable(true);
        txtIng.setDisable(true);
        
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
    
}
