package com.ruoyi.framework.security.token;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
public class PonyNotesAuthenticationToken extends AbstractAuthenticationToken {


    private String subject;

    private String name;

    public PonyNotesAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public PonyNotesAuthenticationToken(String subject,String name) {
        super(Collections.emptyList());
        this.name = name;
        this.subject = subject;
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}
