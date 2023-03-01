package io.khansun.iDrop.Filter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;

public class AuthorizationFilter extends OncePerRequestFilter{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/auth/login") || request.getServletPath().equals("/auth/register") || request.getServletPath().equals("/auth/roles") || request.getServletPath().equals("/token/refresh")) {
            filterChain.doFilter(request, response);
        }
        else {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    System.out.println("Username: " + username);
                    for (SimpleGrantedAuthority authority : authorities) {
                        System.out.println(authority.getAuthority());
                    }
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }
                catch (Exception e) {
                    response.setHeader("error", e.getMessage());
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
                    return;
                }
            }
            else {
                response.setHeader("error", "Authorization header must be provided");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization header must be provided");
            }
        }
    }

}
