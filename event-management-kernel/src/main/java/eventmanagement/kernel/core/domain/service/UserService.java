package eventmanagement.kernel.core.domain.service;

import eventmanagement.kernel.core.domain.model.UserBO;

import java.util.List;

public interface UserService {

    List<UserBO> findAllUsers();
    UserBO findUserById(Long id);
    UserBO createUser(UserBO user);
    UserBO updateUser(Long id, UserBO user);
    void deleteUser(Long id);

}
