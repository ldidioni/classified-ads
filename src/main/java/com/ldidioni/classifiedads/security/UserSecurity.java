package com.ldidioni.classifiedads.security;

import com.ldidioni.classifiedads.repositories.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userSecurity")
public class UserSecurity {

    @Autowired
    private AdRepository adRepository;

    @Transactional
    public boolean isSeller(int adId) {

        String username = adRepository.getOne(adId).getSeller().getUsername();
        return username.equals(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}