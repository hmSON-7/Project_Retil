package com.project.Retil.settings.security;

import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User_Information> findUser = userRepository.findByEmail(email);
        if (findUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User_Information user = findUser.get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }
}