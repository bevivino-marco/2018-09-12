package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;


public class Event implements Comparable<Event>{

	public enum TipoEvento{
		INIZIO,
		FINE,
	}
	
	private TipoEvento tipo;
	private LocalDateTime data;
	private Nerc n;
	
	public Event(TipoEvento tipo,LocalDateTime data,  Nerc n) {
		super();
		this.tipo=tipo;
		this.data=data;
		this.n=n;
		
	}
	
	
	public TipoEvento getTipo() {
		return tipo;
	}


	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}


	public LocalDateTime getData() {
		return data;
	}


	public void setData(LocalDateTime data) {
		this.data = data;
	}


	public Nerc getN() {
		return n;
	}


	public void setN(Nerc n) {
		this.n = n;
	}


	@Override
	public int compareTo(Event o) {
		return this.data.compareTo(o.getData());
	}


	@Override
	public String toString() {
		return String.format("%s, data=%s, n=%s\n", tipo, data, n);
	}
	
	
	
}


