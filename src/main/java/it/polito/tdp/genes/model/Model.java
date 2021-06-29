package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.Adiacenza;
import it.polito.tdp.genes.db.GenesDao;


public class Model {
	
	
	private GenesDao dao;
	private SimpleWeightedGraph<Genes,DefaultWeightedEdge> grafo;
	private Map<String,Genes> idMap;
	
	public Model() {
		dao = new GenesDao();
		idMap = new HashMap<String,Genes>();
		dao.getAllGenes(idMap);
	}
	
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<Genes,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//vertici
		Graphs.addAllVertices(grafo, dao.getVertici(idMap));
		
		//archi
		for(Adiacenza a: dao.getAdiacenze(idMap)) {
			if(this.grafo.containsVertex(a.getGene1()) && this.grafo.containsVertex(a.getGene2())) {
				DefaultWeightedEdge e= this.grafo.getEdge(a.getGene1(), a.getGene2());
				if(e==null) {
					Graphs.addEdgeWithVertices(grafo, a.getGene1(), a.getGene2(), a.getPeso());
				}
			}
		}
		
		
	}
	
	//STAMPO GENI ADIACENTI ( VICINI ) A QUELLO SELEZIONATO CON IL PESO
	//IN ORDINE DI PESO 
	
	//CREO CLASSE E IMPLEMENTO LI IL COMPARABLE
	public List<GeniAdiacenti> getGeniAdiacenti(Genes tendina){
		List<GeniAdiacenti> adiacenti = new ArrayList<GeniAdiacenti>();
		
		for(Genes vicino: Graphs.neighborListOf(grafo, tendina)) {
			
			DefaultWeightedEdge e= this.grafo.getEdge(tendina, vicino);
			Double pesoVicino= this.grafo.getEdgeWeight(e);
			
			GeniAdiacenti ga = new GeniAdiacenti(vicino, pesoVicino);
			adiacenti.add(ga);
		}
		Collections.sort(adiacenti);
		return adiacenti;
	}
	
	public Set<Genes> getVerticiTendina(){
		return this.grafo.vertexSet();
	}
	
	public int numVertici() {
		if(this.grafo!=null)
			return this.grafo.vertexSet().size();
		return 0;
	}
	public int numArchi() {
		if(this.grafo!=null)
			return this.grafo.edgeSet().size();
		return 0;
	}
	
}
