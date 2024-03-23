package com.example.securityspring.Services;

import com.example.securityspring.Entitys.UserEntity;
import com.example.securityspring.Repositorys.UserRepository;
import com.example.securityspring.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(UserDTO user){

        //db에 동일한 username이 있는지 확인.
        if(userRepository.existsByUsername(user.getUsername())){
            return;
        }

        UserEntity date = new UserEntity();

        date.setUsername(user.getUsername());
        date.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        date.setRole("ROLE_ADMIN");

        userRepository.save(date);
    }
}
