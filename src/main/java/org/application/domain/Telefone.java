package org.application.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name="Telefone")
@Table(name="TELEFONE")
public class Telefone extends BaseEntity implements Serializable {

	@Override
	public String toString() {
		return "Telefone [ddd=" + ddd + ", numero=" + numero + ", usuario=" + usuario + "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
    public Telefone(@Size(max = 2) @NotNull String ddd, @NotNull String numero, Usuario usuario) {
		super();
		this.ddd = ddd;
		this.numero = numero;
		this.usuario = usuario;
	}
    
    

	public Telefone() {
		super();
		// TODO Auto-generated constructor stub
	}



	@Size(max = 2)
    @Column(length = 2, name="\"ddd\"")
    @NotNull
    private String ddd;
    

	@Column(name="\"numero\"")
    @NotNull
    private String numero;
    
	@ManyToOne
	@JoinColumn(name = "usuario_id")
    private Usuario usuario;

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ddd == null) ? 0 : ddd.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;
		if (ddd == null) {
			if (other.ddd != null)
				return false;
		} else if (!ddd.equals(other.ddd))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}



	
	

}
