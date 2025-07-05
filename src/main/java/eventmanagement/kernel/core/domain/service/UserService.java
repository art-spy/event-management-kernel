package eventmanagement.kernel.core.domain.service;

import eventmanagement.kernel.core.domain.model.UserBO;

import java.util.List;

public interface UserService {

    List<UserBO> findAll();
    UserBO findById(Long id);
    UserBO create(UserBO user);
    UserBO update(Long id, UserBO user);
    void delete(Long id);

}
