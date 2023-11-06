//package com.neobis.onlinestore.security;
//
//import com.neobis.onlinestore.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class UserDetailsConfiguration implements UserDetailsService {
//    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username).orElseThrow(
//                () -> new UsernameNotFoundException(
//                        String.format(USER_NOT_FOUND_MSG, username))
//        );
//    }
//}
