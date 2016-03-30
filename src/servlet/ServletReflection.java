package servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletReflection extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletReflection() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String nomeClasse = (String) request.getParameter("classe");
		String nomeMetodo = (String) request.getParameter("metodo");
		Method metodo;
		Class<?> classe;
		try {
			classe = Class.forName("control." + nomeClasse);
			Object obj = classe.newInstance();
			metodo = obj.getClass().getMethod(nomeMetodo, HttpServletRequest.class, HttpServletResponse.class);
			metodo.invoke(obj, (HttpServletRequest) request, (HttpServletResponse) response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
