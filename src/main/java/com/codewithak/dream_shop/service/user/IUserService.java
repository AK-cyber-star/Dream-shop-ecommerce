package com.codewithak.dream_shop.service.user;

import com.codewithak.dream_shop.dto.UserDto;
import com.codewithak.dream_shop.model.User;
import com.codewithak.dream_shop.request.CreateUserRequest;
import com.codewithak.dream_shop.request.UserUpdateRequest;

public interface IUserService {

    UserDto getUserById(Long userId);
    UserDto createUser(CreateUserRequest request);
    UserDto updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    User getAuthenticatedUser();
}
