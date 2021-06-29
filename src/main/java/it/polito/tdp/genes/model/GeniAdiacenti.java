package it.polito.tdp.genes.model;

public class GeniAdiacenti implements Comparable<GeniAdiacenti>{

	private Genes geneAdiac;
	private Double peso;
	public GeniAdiacenti(Genes geneAdiac, Double peso) {
		super();
		this.geneAdiac = geneAdiac;
		this.peso = peso;
	}
	public Genes getGeneAdiac() {
		return geneAdiac;
	}
	public void setGeneAdiac(Genes geneAdiac) {
		this.geneAdiac = geneAdiac;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return geneAdiac.getGeneId()+"  "+ peso;
	}
	@Override
	public int compareTo(GeniAdiacenti o) {
		// TODO Auto-generated method stub
		return o.peso.compareTo(this.peso);
	}
	
	
	
}
