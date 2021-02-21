package com.raims.springsecurity.auth;

import com.raims.springsecurity.exceptions.AppException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.raims.springsecurity.exceptions.ErrorMessage.EMAIL_ALREADY_REGISTERED;
import static com.raims.springsecurity.exceptions.ErrorMessage.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class AuthUserService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authUserRepository.findUserByUsername(username)
                .orElseThrow(() -> new AppException(USER_NOT_FOUND, username));
    }

    public void registerUser(AuthUser authUser) {
        checkIfEmailExists(authUser.getUsername());
        authUser.setPassword(bcryptPasswordEncoder.encode(authUser.getPassword()));
        authUserRepository.save(authUser);
    }

    public void checkIfEmailExists(String email) {
        if (authUserRepository.findUserByUsername(email).isPresent())
            throw new AppException(EMAIL_ALREADY_REGISTERED, email);
    }

}
