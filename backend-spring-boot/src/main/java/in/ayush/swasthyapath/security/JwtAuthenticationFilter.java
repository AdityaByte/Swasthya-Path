package in.ayush.swasthyapath.security;

import in.ayush.swasthyapath.utils.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * This class is an interceptor that intercepts every http request before reaching to the controller.
 * Here we are filtering the request, those which needs to be authenticated if they do, and they
 * are sending authentication token as header then we are extracting that token and retrieving the email field
 * from it and checking the token is valid or not if it does then we just pass the request to respective
 * controller and check it as it is authenticated.*/

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtility jwtUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            log.info("Working..");
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String email;

            if (authHeader == null || !authHeader.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
                return;
            }

            jwt = authHeader.substring(7); // Removing the Bearer.
            email = jwtUtility.extractEmail(jwt);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtility.isTokenValid(jwt, email)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, List.of());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            Authentication temp = SecurityContextHolder.getContext().getAuthentication();

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("JWT filter error: {} ", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Error processing token");
        }
    }
}
