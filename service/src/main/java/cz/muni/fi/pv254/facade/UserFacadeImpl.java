package cz.muni.fi.pv254.facade;

import cz.muni.fi.pv254.MappingService;
import cz.muni.fi.pv254.UserService;
import cz.muni.fi.pv254.dto.UserAuthenticateDTO;
import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {
    @Inject
    private MappingService mappingService;

    @Inject
    private UserService userService;

    @Override
    public UserDTO add(UserDTO user, String password) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password is null");
        }
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password is an empty string");
        }
        User mapped = mappingService.mapTo(user, User.class);
        User us = userService.registerUser(mapped, password);
        return mappingService.mapTo(us, UserDTO.class);
    }

    @Override
    public void remove(UserDTO user) {
        userService.remove(mappingService.mapTo(user, User.class));
    }

    @Override
    public void update(UserDTO user) {
        userService.update(mappingService.mapTo(user, User.class));
    }

    @Override
    public List<UserDTO> findAll() {
        return mappingService.mapTo(userService.findAll(), UserDTO.class);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return null;
        }
        return mappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return null;
        }
        return mappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public Boolean isAdmin(UserDTO user) {
        return userService.isAdmin(mappingService.mapTo(user, User.class));
    }

    @Override
    public UserDTO authenticate(UserAuthenticateDTO user) {
        User foundUser = userService.findByEmail(user.getEmail());
        if (foundUser == null) {
            return null;
        }
        if (userService.authenticate(foundUser, user.getPassword())) {
            return mappingService.mapTo(foundUser, UserDTO.class);
        }

        return null;
    }

    @Override
    public UserDTO findBySteamId(Long id) {
        return findBySteamId(id, false);
    }

    @Override
    public UserDTO findBySteamId(Long id, boolean populateRecommendations) {
        User user = userService.findBySteamId(id, populateRecommendations);
        if (user == null) {
            return null;
        }
        return mappingService.mapTo(user, UserDTO.class);
    }
}
