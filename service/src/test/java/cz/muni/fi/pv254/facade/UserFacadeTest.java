package cz.muni.fi.pv254.facade;

import cz.muni.fi.pv254.MappingService;
import cz.muni.fi.pv254.UserService;
import cz.muni.fi.pv254.config.ServiceConfiguration;
import cz.muni.fi.pv254.dto.UserAuthenticateDTO;
import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.entity.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional
public class UserFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MappingService mappingService;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserFacade userFacade = new UserFacadeImpl();

    private User user = new User();

    private UserDTO userDTO = new UserDTO();

    private UserAuthenticateDTO authenticateDTO = new UserAuthenticateDTO();

    private List<User> users = new ArrayList<>();

    private List<UserDTO> usersDTOs = new ArrayList<>();

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);

        user.setEmail("email1");
        user.setId(0L);
        user.setName("name");
        user.setPasswordHash("hash");
        user.setIsAdmin(Boolean.FALSE);

        users.add(user);

        userDTO.setEmail("email2");
        userDTO.setId(0L);
        userDTO.setName("name");
        userDTO.setPasswordHash("hash");
        userDTO.setIsAdmin(Boolean.FALSE);

        usersDTOs.add(userDTO);

        when(mappingService.mapTo(userDTO,User.class)).thenReturn(user);
        when(mappingService.mapTo(user,UserDTO.class)).thenReturn(userDTO);
        when(mappingService.mapTo(usersDTOs,User.class)).thenReturn(users);
        when(mappingService.mapTo(users,UserDTO.class)).thenReturn(usersDTOs);

        authenticateDTO.setEmail("email1");
        authenticateDTO.setPassword("password");
    }

    @BeforeMethod
    public void resetMocks() {
        reset(userService);
    }
//
//    @Test
//    public void test_UserAdd(){
//        userFacade.add(userDTO,"password");
//
//        verify(userService).registerUser(user,"password");
//    }
//
//    @Test
//    public void test_UserUpdate(){
//        userFacade.update(userDTO);
//
//        verify(userService).update(user);
//    }
//
//    @Test
//    public void test_UserRemove(){
//        userFacade.remove(userDTO);
//
//        verify(userService).remove(user);
//    }
//
//    @Test
//    public void test_UserFindById(){
//        when(userService.findById(user.getId())).thenReturn(user);
//        UserDTO found = userFacade.findById(user.getId());
//
//        verify(userService).findById(user.getId());
//        Assert.assertEquals(found,userDTO);
//    }
//
//    @Test
//    public void test_UserFindByEmail(){
//        when(userService.findByEmail("email")).thenReturn(user);
//        UserDTO found = userFacade.findByEmail("email");
//
//        verify(userService).findByEmail("email");
//        Assert.assertEquals(found,userDTO);
//    }
//
//    @Test
//    public void test_UserFindAll(){
//        when(userService.findAll()).thenReturn(users);
//        List<UserDTO> found =  userFacade.findAll();
//
//        verify(userService).findAll();
//        Assert.assertEquals(found,usersDTOs);
//    }
//
//    @Test
//    public void test_UserIsAdmin(){
//        userFacade.isAdmin(userDTO);
//
//        verify(userService).isAdmin(user);
//    }
//
//    @Test
//    public void test_UserAuthenticate(){
//        when(userService.findByEmail(user.getEmail())).thenReturn(user);
//        userFacade.authenticate(authenticateDTO);
//
//        verify(userService).findByEmail(authenticateDTO.getEmail());
//        verify(userService).authenticate(user, authenticateDTO.getPassword());
//    }
}
