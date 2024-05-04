package com.app.nailappointment.dto.mapper;

import com.app.nailappointment.db.UserEntity;
import com.app.nailappointment.dto.UserDTO;

public class UserMapper {

    public static UserDTO mapEntityToDTO(UserEntity userEntity, String userId) {
        return new UserDTO(userId, userEntity.getEmail(), userEntity.getUsername());
    }
}
