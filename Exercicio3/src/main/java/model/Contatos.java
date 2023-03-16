package model;

public class Contatos {
	private int id;
	private String nome;
	private String email;
	private String celular;
	
	public Contatos() {
		this.id = -1;
		this.nome = "";
		this.email = "";
		this.celular = "*";
	}
	
	public Contatos(int id, String nome, String email, String celular) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.celular = celular;
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	@Override
	public String toString() {
		return "Contato [Id=" + id +", Nome="+nome+ ", Email=" + email + ", celular=" + celular +"]" ;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Contatos) obj).getId());
	}
}
