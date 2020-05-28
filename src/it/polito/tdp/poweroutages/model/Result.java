package it.polito.tdp.poweroutages.model;

public class Result implements Comparable<Result>{
	private Nerc n;
	private double peso;
	public Result(Nerc n, double peso) {
		super();
		this.n = n;
		this.peso = peso;
	}
	public Nerc getN() {
		return n;
	}
	public void setN(Nerc n) {
		this.n = n;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((n == null) ? 0 : n.hashCode());
		long temp;
		temp = Double.doubleToLongBits(peso);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Result other = (Result) obj;
		if (n == null) {
			if (other.n != null)
				return false;
		} else if (!n.equals(other.n))
			return false;
		if (Double.doubleToLongBits(peso) != Double.doubleToLongBits(other.peso))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("n=%s, peso=%s\n", n, peso);
	}
	@Override
	public int compareTo(Result arg0) {
		int p1 = (int) (this.peso);
		int p2 = (int) (arg0.getPeso());
		return p2-p1;
	}
	

}
