package com.raims.springsecurity.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public void userRegistration(@RequestBody RegistrationRequest request, @RequestParam String role) {
        registrationService.userRegistration(request, role);
    }

}
