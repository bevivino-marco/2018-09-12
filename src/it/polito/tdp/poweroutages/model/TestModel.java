package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model m = new Model();
		m.creaGrafo();
		System.out.println(m.getResult(m.getListaN().get(3)));
		System.out.println(m.simula(4));

	}

}
