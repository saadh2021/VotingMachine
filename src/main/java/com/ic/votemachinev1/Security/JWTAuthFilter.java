package com.ic.votemachinev1.Security;

import com.ic.votemachinev1.Model.UsersEntity;
import com.ic.votemachinev1.Service.Imp.AdminServiceImp;
import com.ic.votemachinev1.Utils.SessionData;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTUtility jwtHelper;
    private final AdminServiceImp userDetailsService;
    private final SessionData sessionData;
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        //String token = request.getHeader("Authorization");
        String token = sessionData.getToken();
        if (token != null) {
            token = sessionData.getToken();
            String userId = null;
            try {
                userId = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                log.info(e.getMessage());
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token");
                log.info(e.getMessage());
            } catch (Exception e) {
                log.info(e.getMessage());
            }
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsersEntity userDetails = this.userDetailsService.loadUserByUsername(userId);
                log.debug(userDetails.getRole().name());
                Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
                if (Boolean.TRUE.equals(validateToken)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.info("Validation fails !!");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
    private String extractJwtToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}