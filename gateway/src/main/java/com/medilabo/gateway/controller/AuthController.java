package com.medilabo.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes endpoints used by the frontend to check authentication
 * and retrieve the CSRF token.
 *
 * <p>Access to these endpoints requires authentication,
 * enforced by the gateway security filter chain.</p>
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * Confirms that an authenticated request can access the gateway.
     *
     * <p>Credential verification is performed by Spring Security
     * before this method is called.</p>
     *
     * @return an HTTP 200 response with no body
     */
    @GetMapping("/check")
    public ResponseEntity<Void> checkAuthentification() {

        return ResponseEntity.ok().build();
    }

    /**
     * Returns the CSRF token associated with the current session.
     *
     * <p>The frontend uses the returned header name and token
     * when sending requests that require CSRF protection.</p>
     *
     * @param csrfToken the token provided by Spring Security
     * @return the CSRF token and its header and parameter names
     */
    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(CsrfToken csrfToken) {
        return csrfToken;
    }
}
