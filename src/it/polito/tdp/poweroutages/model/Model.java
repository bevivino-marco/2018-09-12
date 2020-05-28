package it.polito.tdp.poweroutages.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	// definizione grafi;
//	private SimpleGraph<Integer, DefaultEdge> grafo;
//	private SimpleDirectedGraph<Integer, DefaultEdge> grafo;
	private SimpleWeightedGraph<Nerc, DefaultWeightedEdge> grafo;
//	private SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> grafo;
	// idMap e liste;
	private List <Nerc> listaN;
	private List <Rel>  listaR;
	private Map <Integer, Nerc> mappaN;
	private PowerOutagesDAO dao;
	
	// dao;
public Model() {
	dao = new PowerOutagesDAO();
	listaN = new LinkedList<>(dao.loadAllNercs());
}
public void creaGrafo() {
	
	mappaN = new HashMap<>();
	for (Nerc n : listaN) {
		mappaN.put(n.getId(), n);
	}
	//System.out.println(listaN.toString());
	listaR = new LinkedList <>(dao.getRel(mappaN));
	//System.out.println(listaR.size());
	// creo il grafo;
//	grafo = new SimpleGraph<Integer,DefaultEdge> (DefaultEdge.class);
//  grafo = new SimpleDirectedGraph<Integer,DefaultEdge> (DefaultEdge.class);
	grafo = new SimpleWeightedGraph<Nerc,DefaultWeightedEdge> (DefaultWeightedEdge.class);
//	grafo = new SimpleDirectedWeightedGraph<Integer,DefaultWeightedEdge> (DefaultWeightedEdge.class);

	// definisco i veritici;
//	grafo.addVertex(i);
	Graphs.addAllVertices(grafo, listaN);
	
	
	// creo gli archi;
	for (Rel r : listaR) {
		Nerc n1 = r.getN1();
		Nerc n2 = r.getN2();
		double peso = dao.getPeso(n1, n2);
		Graphs.addEdgeWithVertices(grafo , n1, n2, peso);
		//System.out.println(grafo.getEdge(n1, n2)+" peso = "+grafo.getEdgeWeight(grafo.getEdge(n1, n2)));
	}
//  grafo.addEdge(e1,e2);
//	Graphs.addEdge(g, sourceVertex, targetVertex, weight);
//  Graphs.addEdgeWithVertices(grafo , partenza, arrivo, peso)
//	grafo.setEdgeWeight(partenza, arrivo, peso);
	
	// valori
	System.out.println("N. vertici : "+grafo.vertexSet().size());
	System.out.println("N. archi : "+grafo.edgeSet().size());
	

}
public List<Nerc> getListaN() {
	return listaN;
}
public List<Result> getResult(Nerc n) {
	List <Nerc> ln = new LinkedList<>(Graphs.neighborListOf(grafo, n));
	List <Result> result = new LinkedList <>();
	for (Nerc nv : ln) {
		result.add(new Result(nv,grafo.getEdgeWeight(grafo.getEdge(n, nv))));
	}
	Collections.sort(result);
	return result;
}
public String simula(int m) {
	Simulazione s = new Simulazione();
	s.init(dao.getE( m, mappaN), this.grafo);
	return s.getRes();
	
}

}
