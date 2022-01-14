package com.github.throyer.appointments.controllers;

import com.github.throyer.appointments.domain.session.dto.RefreshTokenRequest;
import com.github.throyer.appointments.domain.session.dto.RefreshTokenResponse;
import com.github.throyer.appointments.domain.session.dto.TokenRequest;
import com.github.throyer.appointments.domain.session.dto.TokenResponse;
import com.github.throyer.appointments.domain.session.service.CreateTokenService;
import com.github.throyer.appointments.domain.session.service.RefreshTokenService;
import static com.github.throyer.appointments.utils.Response.ok;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions")
public class SessionsController {

    @Autowired
    private CreateTokenService createService;

    @Autowired
    private RefreshTokenService refreshService;

    @PostMapping
    public ResponseEntity<TokenResponse> create(
        @RequestBody @Valid TokenRequest request
    ) {
        var token = createService.create(request);
        return ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(
        @RequestBody @Valid RefreshTokenRequest request
    ) {
        var token = refreshService.refresh(request);
        return ok(token);
    }
}