package com.app.springsecuritypoc.security;

import com.app.springsecuritypoc.dto.UserInfoToUserDetails;
import com.app.springsecuritypoc.entity.UserInfo;
import com.app.springsecuritypoc.entity.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfoObject = userInfoRepository.findByName(username);

        return userInfoObject.map(UserInfoToUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Founds In Db"));

    }

}
