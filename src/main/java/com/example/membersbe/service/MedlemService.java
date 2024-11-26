package com.example.membersbe.service;

import com.example.membersbe.repository.MedlemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MedlemService implements UserDetailsService {

    private final MedlemRepository medlemRepository;

    public MedlemService(MedlemRepository medlemRepository) {
        this.medlemRepository = medlemRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return medlemRepository.findByEpost(username).orElseThrow();
    }
}
