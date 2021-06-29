package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public void getAllGenes(Map<String,Genes>idMap){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		//List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!idMap.containsKey(res.getString("GeneID"))) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				idMap.put(genes.getGeneId(), genes);
			}
			}
			res.close();
			st.close();
			conn.close();
			//return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//return null;
		}
	}
	

	public List<Genes> getVertici(Map<String,Genes>idMap){
		String sql="SELECT DISTINCT g.GeneID AS g_id "
				+ "FROM genes g "
				+ "WHERE g.Essential='Essential' ";
		List<Genes> result = new ArrayList<Genes>();
				Connection conn = DBConnect.getConnection();

				try {
					PreparedStatement st = conn.prepareStatement(sql);
					ResultSet res = st.executeQuery();
					while (res.next()) {
						result.add(idMap.get(res.getString("g_id")));
					}
					res.close();
					st.close();
					conn.close();
					return result;
					
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
	}

	public List<Adiacenza> getAdiacenze(Map<String,Genes>idMap){
		String sql="SELECT g1.GeneID AS id1, g2.GeneID AS id2, i.Expression_Corr AS valore, g1.Chromosome AS crom1, g2.Chromosome AS crom2 "
				+ "FROM interactions i, genes g1, genes g2 "
				+ "WHERE g1.GeneID <> g2.GeneID AND i.GeneID1=g1.GeneID AND i.GeneID2=g2.GeneID "
				+ "GROUP BY g1.GeneID, g2.GeneID ";
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Genes gene1= idMap.get(res.getString("id1"));
				Genes gene2= idMap.get(res.getString("id2"));
				if(gene1!=null && gene2!=null) {
					
					double peso=0.0;
					int crom1= res.getInt("crom1");
					int crom2=res.getInt("crom2");
					//se cromosomi uguali 
					if(crom1==crom2) {
						 peso= 2* (Math.abs(res.getDouble("valore")));
					} else {
						 peso= Math.abs(res.getDouble("valore"));
					}
					Adiacenza a= new  Adiacenza(gene1, gene2, peso);
					result.add(a);
			} else {
				System.out.println("errore in get adiacenze");
			}
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
