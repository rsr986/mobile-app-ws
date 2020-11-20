package com.company.app.ws.service.impl;

import com.company.app.ws.io.entity.UserEntity;
import com.company.app.ws.io.repository.UserRepository;
import com.company.app.ws.service.UserService;
import com.company.app.ws.shared.Utils;
import com.company.app.ws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    @Override
    public UserDto createUser(UserDto user) {
        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exists");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setEncryptedPassword("test");
        userEntity.setUserId(utils.generateUserId(30));

        UserEntity storedUserEntity = userRepository.save(userEntity);

        UserDto retrunValue = new UserDto();
        BeanUtils.copyProperties(storedUserEntity, retrunValue);

        return retrunValue;
    }
}
