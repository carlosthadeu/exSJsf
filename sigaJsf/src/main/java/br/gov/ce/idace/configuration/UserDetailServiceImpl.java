/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.idace.configuration;


import br.gov.ce.idace.entity.auth.SecUser;
import br.gov.ce.idace.session.auth.SecRoleFacade;
import br.gov.ce.idace.session.auth.SecUserFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos.Santos
 */
@EJB(name = "secUserFacade", beanInterface = SecUserFacade.class)
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private SecUserFacade secUserFacade;
    private SecRoleFacade secRoleFacade;

    private static final String COLLABORATEUR_EJB_LOOKUP_PATH = "java:module/SecUserFacade";
    private static final String ROLE_EJB_LOOKUP_PATH = "java:module/SecRoleFacade";

    public UserDetailServiceImpl() {

    }

    // this class is used by spring controller to authenticate and authorize user
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        SecUser u;
        try {

            InitialContext initialContext = new InitialContext();
            secUserFacade = (SecUserFacade) initialContext.lookup(COLLABORATEUR_EJB_LOOKUP_PATH);
            Map<String, String> parametroConsulta = new HashMap<String, String>();
            parametroConsulta.put("username", username);
            u = secUserFacade.findByNamedQuerySingle("SecUser.findByUsername", parametroConsulta);
            if (u == null) {
                throw new UsernameNotFoundException("user name not found");
            }

        } catch (Exception e) {
            throw new UsernameNotFoundException("database error ");
        }
        return buildUserFromUserEntity(u);
    }

    private User buildUserFromUserEntity(SecUser userEntity) {
        // convert model user to spring security user
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();
        boolean enabled = userEntity.getEnabled();
        boolean accountNonExpired = !userEntity.getAccountExpired();
        boolean credentialsNonExpired = !userEntity.getPasswordExpired();
        boolean accountNonLocked = userEntity.getEnabled();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        try {
            InitialContext initialContext = new InitialContext();
            secRoleFacade = (SecRoleFacade) initialContext.lookup(ROLE_EJB_LOOKUP_PATH);
            for (String secRole : secRoleFacade.rolesPorUsuario(userEntity.getId())) {
                authorities.add(new SimpleGrantedAuthority(secRole));
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("database error ");
        }

        User springUser = new User(username,
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);
        return springUser;
    }
}
