package br.com.danieleleao.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.danieleleao.todolist.user.IUserRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		var servletPath = request.getServletPath();
		if (servletPath.startsWith("/tasks/")){
			//Pegar a autenticacção (usuario e senha)
			var authorization = request.getHeader("Authorization");
			//retorna Authorization: Basic bHVjYXNjb3RvY28yOnhlcXVlbWF0ZWw=
			System.out.println("Authorization: " + authorization);
			//removendo o Basic da String
			var authEncoded = authorization.substring("Basic".length()).trim();
			
			byte[] authDecode = Base64.getDecoder().decode(authEncoded);
			
			var authString = new String(authDecode);
			
			System.out.println("Authorization: " + authString);
			
			String[] credentials = authString.split(":");
			String username = credentials[0];
			String password = credentials[1];
			System.out.println(username);
			System.out.println(password);
			// validar  usuario
			var user = this.userRepository.findByUsername(username);
			if (user == null) {
				response.sendError(401);//nao tem autorizaçao
			} else {
				// validar senha
				var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(),user.getPassword());
				if (passwordVerify.verified) {
					//segue viagem
					request.setAttribute("idUser", user.getId());
					filterChain.doFilter(request, response);
				} else {
					response.sendError(401);//nao tem autorizaçao
				}
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}
	
}
