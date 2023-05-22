package org.saini.blogrestapi.security;

import org.saini.blogrestapi.entity.User;
import org.saini.blogrestapi.repository.RoleRepository;
import org.saini.blogrestapi.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomUserDetailsService(UserRepository userRepository,
                                    RoleRepository roleRepository){
        this.userRepository=userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepository.findByUserNameOrEmail(username,username)
                .orElseThrow(()->new UsernameNotFoundException("No user found with username or email "+username) );

        Set<GrantedAuthority> authorities= user.getRoles().stream().map((role -> new SimpleGrantedAuthority(role.getName())
        )).collect(Collectors.toSet());

        UserDetails userDetails=new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),authorities);


        return userDetails;
    }
}
