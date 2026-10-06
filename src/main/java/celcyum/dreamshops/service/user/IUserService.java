package celcyum.dreamshops.service.user;

import celcyum.dreamshops.dto.UserDto;
import celcyum.dreamshops.model.User;
import celcyum.dreamshops.request.CreateUserRequest;
import celcyum.dreamshops.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
