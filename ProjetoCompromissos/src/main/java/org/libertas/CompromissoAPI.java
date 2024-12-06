package org.libertas;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class PessoaAPI
 */
@WebServlet("/CompromissoAPI/*")
public class CompromissoAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CompromissoAPI() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pesquisa = request.getParameter("pesquisa");
		
		Gson gson = new Gson();
		CompromissoDAO compromissodao = new CompromissoDAO();
		
		Integer id = null;
		try {
			id = Integer.parseInt(request.getPathInfo().substring(1));
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		String resposta;
		if(id == null) {
			resposta = gson.toJson(compromissodao.listar(pesquisa));
		}else {
			resposta = gson.toJson(compromissodao.consultar(id));
		}
		
		response.setHeader("content-type", "application/json");
		response.getWriter().print(resposta);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
		Gson gson = new Gson();
		Compromisso compromisso = gson.fromJson(body, Compromisso.class);
		CompromissoDAO compromissodao = new CompromissoDAO();
		String resposta = gson.toJson(compromissodao.inserir(compromisso));
		
		response.setHeader("content-type", "application/json");
		response.getWriter().print(resposta);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
		Gson gson = new Gson();
		CompromissoDAO compromissodao = new CompromissoDAO();
		Compromisso compromisso = gson.fromJson(body, Compromisso.class);
		String resposta = gson.toJson(gson.toJson(compromissodao.alterar(compromisso)));
		
		response.setHeader("content-type", "application/json");
		response.getWriter().print(resposta);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CompromissoDAO compromissodao = new CompromissoDAO();
		Gson gson = new Gson();
		
		Integer id = null;
		try {
			id = Integer.parseInt(request.getPathInfo().substring(1));
		}catch (Exception e) {
			String resp = "ID obrigatório!";
			response.getWriter().print(resp);
			return;
		}
		String resposta = gson.toJson(gson.toJson(compromissodao.excluir(id)));
		
		response.setHeader("content-type", "application/json");
		response.getWriter().print(resposta);
	}
}
