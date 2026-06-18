package com.example.expensebackend.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    //Tiem lop tien ich JwtUtils qua contructor
    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws SecurityException, IOException, ServletException {

        //1. Lay chuoi token tu header Authorization
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        //Dinh danh hop le: "Bearer <token>"
        if (authHeader == null || !authHeader.startsWith("Bearer"))
        {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); //Cat bo chu "Bearer " de lay token thuc te
        try {
            userEmail = jwtUtils.getEmailFromToken(jwt); //Giai ma email tu token
            // neu giai ma thanh cong va chua duoc thiet lap xac thuc trong SecurityContext
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtils.validateToken(jwt, userEmail)) {
                    //Lay vai tro cua nguoi dung tu token (vi du: USER hoac ADMIN)
                    String role = jwtUtils.getRoleFromToken(jwt);

                    //Spring Security yeu cau quyen han cos tien to "ROLE_" (vi du: ROLE_USER, ROLE_ADMIN)
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                    // Tao doi tuong chua thong tin xac thuc
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userEmail,
                            null,
                            List.of(authority)
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //Luu thong tin xac thuc vao Security Context cua he thong

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            }
        catch(Exception e){
            //Neu token khong hop le (het han, sai chu ky...), bo qua de Spring Security tra ve 401/403
            logger.error("Cannot set user authentication: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
