package dao;

import java.io.IOException;
import java.sql.*;

import model.Contatos;

public class DAO {
	private int maxId;
	private Connection conexao;
	
	public int getMaxId() {
		return maxId;
	}

	public DAO() throws IOException {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "contatos";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "postgres";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public void add(Contatos contato) {
		try {  
			this.maxId = (contato.getId() > this.maxId) ? contato.getId() : this.maxId;
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO contato "
					       + "VALUES ("+contato.getId()+ ", '" + contato.getNome() + "', '"  
					       + contato.getEmail() + "', '" + contato.getCelular() + "');");
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public Contatos get(int id) {
		Contatos contato = new Contatos();
		try {  
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM contato WHERE id = "+ id);	
			contato.setId(rs.getInt("id"));
			contato.setNome(rs.getString("nome"));
			contato.setCelular(rs.getString("celular"));
			contato.setEmail(rs.getString("email"));
			st.close();

		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return contato;
	}

	public void update(Contatos contato) {
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE contato SET email = '" + contato.getEmail() + "', celular = '"  
				       + contato.getCelular() + "', nome = '" + contato.getNome() + "'"
					   + " WHERE id = " + contato.getId();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public void remove(int id) {
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM contato WHERE id = " + id);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public Contatos[] getAll() {
		Contatos[] contato = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM contato");		
	         if(rs.next()){
	             rs.last();
	             contato = new Contatos[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	            	 contato[i] = new Contatos(rs.getInt("id"), rs.getString("nome"), 
	                		                  rs.getString("email"), rs.getString("celular"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return contato;
	}
}
