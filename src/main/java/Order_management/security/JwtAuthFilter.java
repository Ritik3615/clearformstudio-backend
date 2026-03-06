package Order_management.security;

import Order_management.service.CustomUserDetailsService;
import Order_management.service.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String userName = jwtUtil.extractUserName(token);

        try {
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // This fires the Hibernate query
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

                // Check if token is NOT invalid (i.e., it is valid)
                if (!jwtUtil.isTokenInvalid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);

        } catch (UsernameNotFoundException e) {
            // This stops the 403 and sends your custom message
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 or 401
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");
            // DO NOT call filterChain.doFilter(request, response) here
        }
    }
}

