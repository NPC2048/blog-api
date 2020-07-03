package com.liangyuelong.blog.config.security;

import com.liangyuelong.blog.dao.UserMapper;
import com.liangyuelong.blog.entity.BlogUser;
import com.liangyuelong.blog.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * UserDetailsService 实现
 *
 * @author yuelong.liang
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BlogUser user = userMapper.selectRoleAndPermissionByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        Collection<GrantedAuthority> authorities;
        if (user.getRoles() != null) {
            authorities = new ArrayList<>(user.getRoles().size());
            for (Role role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
        } else {
            authorities = Collections.emptyList();
        }
        return User.builder().username(username).password(user.getPassword()).authorities(authorities).build();
    }
}
