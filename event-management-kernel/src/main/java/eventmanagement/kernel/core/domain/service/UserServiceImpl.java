package eventmanagement.kernel.core.domain.service;

import eventmanagement.kernel.core.domain.error.ErrorType;
import eventmanagement.kernel.core.domain.error.OverlappingEventException;
import eventmanagement.kernel.core.domain.error.UserAlreadyExistsException;
import eventmanagement.kernel.core.domain.error.UserNotFoundException;
import eventmanagement.kernel.core.domain.model.UserBO;
import eventmanagement.kernel.core.persistance.mapper.EntityBoMapper;
import eventmanagement.kernel.core.persistance.repository.EventJpaRepository;
import eventmanagement.kernel.core.persistance.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final EventJpaRepository eventJpaRepository;
    private final EntityBoMapper mapper;

    @Override
    public List<UserBO> findAllUsers() {
        return userJpaRepository.findAll().stream()
                .map(mapper::toBO)
                .collect(Collectors.toList());
    }

    @Override
    public UserBO findUserById(Long id) {
        return userJpaRepository.findById(id)
                .map(mapper::toBO)
                .orElseThrow(() -> new UserNotFoundException("User nicht gefunden: " + id, ErrorType.USER_NOT_FOUND));
    }

    @Override
    public UserBO createUser(UserBO user) {
        userJpaRepository.findByEmail(user.getEmail())
                .ifPresent(userEntity -> {
                    throw new UserAlreadyExistsException("E-Mail bereits vergeben.", ErrorType.USER_ALREADY_EXISTS );
                });
        return mapper.toBO(userJpaRepository.save(mapper.toEntity(user)));
    }

    @Override
    public UserBO updateUser(Long id, UserBO user) {
        var existing = userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User nicht gefunden: " + id, ErrorType.USER_NOT_FOUND));
        // validate eMail change
        if (!existing.getEmail().equals(user.getEmail())) {
            userJpaRepository.findByEmail(user.getEmail())
                    .ifPresent(userEntity -> {
                        throw new UserAlreadyExistsException("E-Mail bereits vergeben.", ErrorType.USER_ALREADY_EXISTS);
                    });
        }
        user.setId(id);
        return mapper.toBO(userJpaRepository.save(mapper.toEntity(user)));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userJpaRepository.existsById(id)) {
            throw new UserNotFoundException("User nicht gefunden: " + id, ErrorType.USER_NOT_FOUND);
        }
        if (eventJpaRepository.findEventsByParticipantId(id).isEmpty()) {
            userJpaRepository.deleteById(id);
        } else {
            throw new OverlappingEventException("User mit " + id + " ist noch Events zugeordnet.", ErrorType.USER_IN_EVENT, null);
        }

    }
}
