package org.application.domain;

public class TelefoneDto {

	public TelefoneDto(String ddd, String numero) {
		super();
		this.ddd = ddd;
		this.numero = numero;
	}
	public String ddd;
	public String numero;
	
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
}
