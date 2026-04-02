package com.quitq.Mapper;

import com.quitq.dto.UserResponsedto;
import com.quitq.model.User;

public class UserMapper {
    public static UserResponsedto maptoDto(User user){
      return new UserResponsedto(user.getId(),user.getName(),user.getEmail(),user.getAddress(),user.getAuthorities().toString());
    }
}
