package com.codewithak.dream_shop.service.user;

import com.codewithak.dream_shop.dto.UserDto;
import com.codewithak.dream_shop.exceptions.AlreadyExistsException;
import com.codewithak.dream_shop.exceptions.ResourceNotFoundException;
import com.codewithak.dream_shop.model.User;
import com.codewithak.dream_shop.repository.UserRepository;
import com.codewithak.dream_shop.request.CreateUserRequest;
import com.codewithak.dream_shop.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No user found "));
        return convertToDto(user);
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {
        User newUser = Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("user already exists"));
        return convertToDto(newUser);
    }

    @Override
    public UserDto updateUser(UserUpdateRequest request, Long userId) {
        User updatedUser = userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return convertToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User not found");
        });
    }

    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
