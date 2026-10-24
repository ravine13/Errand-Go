package com.errandGo.backend.security;

import com.errandGo.backend.controller.AuthController;
import com.errandGo.backend.service.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Set<String> SAFE_METHODS = Set.of("GET", "HEAD", "OPTIONS", "TRACE");

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        Cookie cookie = WebUtils.getCookie(request, AuthController.COOKIE_NAME);


        if (cookie != null
                && !SAFE_METHODS.contains(request.getMethod())
                && request.getHeader("X-Requested-With") == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (cookie != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                String token = cookie.getValue();
                UserDetails user = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
                if (jwtUtil.validateToken(token, user)) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (JwtException | IllegalArgumentException | UsernameNotFoundException e) {
                // expired, malformed or unknown-user token: treat the request as logged out
            }
        }
        chain.doFilter(request, response);
    }
}