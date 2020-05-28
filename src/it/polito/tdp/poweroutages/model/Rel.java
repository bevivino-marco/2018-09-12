package it.polito.tdp.poweroutages.model;

public class Rel {
	private Nerc n1;
	private Nerc n2;
	private int id;
	public Rel(Nerc n1, Nerc n2, int id) {
		super();
		this.n1 = n1;
		this.n2 = n2;
		this.id = id;
	}
	public Nerc getN1() {
		return n1;
	}
	public void setN1(Nerc n1) {
		this.n1 = n1;
	}
	public Nerc getN2() {
		return n2;
	}
	public void setN2(Nerc n2) {
		this.n2 = n2;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rel other = (Rel) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("n1=%s, n2=%s, id=%s\n", n1, n2, id);
	}
	

}
