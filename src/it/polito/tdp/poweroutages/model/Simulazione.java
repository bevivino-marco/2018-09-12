package it.polito.tdp.poweroutages.model;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;


public class Simulazione {
	//Modello -> Stato del sistema ad ogni passo
	private Graph<Nerc, DefaultWeightedEdge> grafo;

	//Tipi di evento/coda prioritaria
	// 1 solo evento
	private PriorityQueue<Event> queue;
	
	
	//Parametri della simulazione
	//......
	
	//Valori in output
	private int catastrofi =0;
	//......
	
	public void init( List <Event> lE,Graph<Nerc,DefaultWeightedEdge> grafo) {
		//ricevo i parametri
		//.....
		this.grafo=grafo;
		//impostazione dello stato iniziale
		//.....
		queue = new PriorityQueue<Event>();
		queue.addAll(lE);
		System.out.println(queue.toString());
		run();
		
		
	}
	
	public void run() {
		//Estraggo un evento per volta dalla coda e lo eseguo,
		//finchè la coda non si svuota
		Event e;

		while((e = queue.poll()) != null){
			//ESEGUO L'EVENTO
			System.out.println(e.toString());
			switch (e.getTipo()) {
				case INIZIO:
					
					List <Nerc> vicini = new LinkedList<>();
					List <Nerc> vicinidisp = new LinkedList<>();
					vicini.addAll(Graphs.neighborListOf(grafo, e.getN()));
					
					
					int c=0;
					for (Nerc n : vicini) {
						if (n.isOccupato()==false)
							c++;
					}
					if (vicini.size()==0 || c==0) {
						catastrofi++;
						
					}else {
					
						
						
					if (e.getN().getDonazioni().size()>=2) {
						double min=0.0;
						Nerc nm=null;
						
						for (Nerc n : e.getN().getDonazioni()) {
							double peso = grafo.getEdgeWeight(grafo.getEdge(e.getN(), n));
							if (peso<min && n.isOccupato()==false) {
								min=c;
								nm=n;
							}
						
						}
						/*if (!nm.equals(null)) {
							 nm.setDonazione(e.getN());
							 nm.setOccupato(true);
							}*/
						
					}
					
					
					
					else if (e.getN().getDonazioni().isEmpty()){
						double min=0.0;
						Nerc nm=null;
						for (Nerc n : Graphs.neighborListOf(grafo, e.getN())) {
							double peso = grafo.getEdgeWeight(grafo.getEdge(e.getN(), n));
							if (peso<min && n.isOccupato()==false) {
								min=c;
								nm=n;
							}
							
						
						}
						/*if (nm.getValue()==null) {
						 nm.setDonazione(e.getN());
						 nm.setOccupato(true);
						}*/
					 }
					
					
					else {
						 
						 Nerc n = e.getN().getDonazioni().get(0);
						 if (n.isOccupato()==true){
							 catastrofi++;
							 
						 }

						 n.setDonazione(e.getN());
						 n.setOccupato(true);
					 }}
					break;
			
				case FINE:
					e.getN().setOccupato(false);
					break;
			
			}
		}	
	}

	public String getRes() {
		String s = "";
		for (Nerc n : grafo.vertexSet()) {
			s+= n.getDonazioni().size()+"\n";
		}
		return s+"\n le catastrofi sono state : "+catastrofi;
	}

	

}


