package com.example.securityspring.Services;

import com.example.securityspring.Entitys.UserEntity;
import com.example.securityspring.Repositorys.UserRepository;
import com.example.securityspring.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public int joinProcess(UserDTO user, Model m){

        //db에 동일한 username이 있는지 확인.
        if(userRepository.existsByUsername(user.getUsername())){
            m.addAttribute("message","이미 동일한 id가 있습니다.");
            return 0;
        }

        UserEntity date = new UserEntity();

        date.setUsername(user.getUsername());
        date.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        date.setRole("ROLE_ADMIN");

        userRepository.save(date);
        return 1;
    }
}
