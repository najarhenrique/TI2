package service;

import java.io.IOException;

import dao.DAO;
import model.Contatos;
import spark.Request;
import spark.Response;


public class Service {

	private static DAO XDAO;

	public Service() {
		try {
			XDAO = new DAO();
			XDAO.conectar();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Object add(Request request, Response response) {
		int id = Integer.parseInt(request.queryParams(":id"));
		String nome = request.queryParams("nome");
		String email = request.queryParams("email");
		String celular = request.queryParams("celular");
		int idContato = XDAO.getMaxId() + 1;

		Contatos contato = new Contatos(idContato, nome, email, celular);

		XDAO.add(contato);

		response.status(201); // 201 Create
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Contatos contato = (Contatos) XDAO.get(id);
		
		if (contato != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<Contato>\n" + 
            		"\t<id>" + contato.getId() + "</id>\n" +
            		"\t<nome>" + contato.getNome() + "</nome>\n" +
            		"\t<email>" + contato.getEmail() + "</email>\n" +
            		"\t<celular>" + contato.getCelular() + "</celular>\n" +
            		"</Contato>\n";
        } else {
            response.status(404); // 404 Not found
            return "Contato " + id + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
        Contatos contato = (Contatos) XDAO.get(id);

        if (contato != null) {
        	contato.setId(Integer.parseInt(request.queryParams("id")));
        	contato.setNome(request.queryParams("nome"));
        	contato.setEmail(request.queryParams("email"));
        	contato.setCelular(request.queryParams("celular"));

        	XDAO.update(contato);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Contatos contato = (Contatos) XDAO.get(id);

        if (contato != null) {

            XDAO.remove(contato.getId());

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<Contato type=\"array\">");
		for (Contatos contato : XDAO.getAll()) {
			returnValue.append("\n<Contato>\n" + 
            "\t<id>" + contato.getId() + "</id>\n" +
            "\t<nome>" + contato.getNome() + "</nome>\n" +
            "\t<email>" + contato.getEmail() + "</email>\n" +
            "\t<celular>" + contato.getCelular() + "</celular>\n" +
            "</Contato>\n");
		}
		returnValue.append("</Contato>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}