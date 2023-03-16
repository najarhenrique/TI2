package app;

import static spark.Spark.*;

import service.Service;


public class Aplicacao {
	
	private static Service service = new Service();
	
	public static void main(String[] args) {
		port(6789);

        post("/contato", (request, response) -> service.add(request, response));
        get("/contato/:id", (request, response) -> service.get(request, response));
        get("/contato/update/:id", (request, response) -> service.update(request, response));
        get("/contato/delete/:id", (request, response) -> service.remove(request, response));
        get("/contato", (request, response) -> service.getAll(request, response));
	}
}
