package io.sibguti.starter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.io.IOException;
import java.util.Set;

@AutoConfiguration
public class AutoConfigurationInputFilter extends HttpFilter {
    private final Set<String> endpointsToCloseSession;

    public AutoConfigurationInputFilter(Set<String> endpointsToCloseSession) {
        this.endpointsToCloseSession = endpointsToCloseSession;
    }
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String path = request.getRequestURI();
        if (endpointsToCloseSession.contains(path)) {
            request.getSession().invalidate();
        }
        chain.doFilter(request, response);
    }
}
