package cz.muni.fi.pv254.service;

import cz.muni.fi.pv254.UserService;
import cz.muni.fi.pv254.config.ServiceConfiguration;
import cz.muni.fi.pv254.dao.UserDao;
import cz.muni.fi.pv254.entity.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import javax.inject.Inject;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserDao userDao;

    @Inject
    @InjectMocks
    private UserService userService;

    private User user = new User();

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void resetMocks() {
        reset(userDao);
    }
//
//    @Test
//    public void test_registerNewUser(){
//        userService.registerUser(user,"password");
//
//        verify(userDao).add(user);
//    }
//
//    @Test
//    public void test_updateUser(){
//        userService.update(user);
//
//        verify(userDao).update(user);
//    }
//
//    @Test
//    public void test_removeUser(){
//        userService.remove(user);
//
//        verify(userDao).remove(user);
//    }
//    @Test
//    public void test_findAllUsers(){
//        userService.findAll();
//
//        verify(userDao).findAll();
//    }
//
//    @Test
//    public void test_findById(){
//        userService.findById(0L);
//
//        verify(userDao).findById(0L);
//    }
//
//    @Test
//    public void test_findByEmail(){
//        userService.findByEmail("email");
//
//        verify(userDao).findByEmail("email");
//    }
//
//    @Test
//    public void test_isAdmin(){
//        user.setId(0L);
//        user.setIsAdmin(Boolean.TRUE);
//        when(userDao.findById(user.getId())).thenReturn(user);
//
//        userService.isAdmin(user);
//
//        verify(userDao).findById(user.getId());
//    }
//
//    @Test
//    public void test_Authenticate(){
//        user.setId(0L);
//        user.setIsAdmin(Boolean.FALSE);
//        user.setName("User1");
//        user.setEmail("email");
//        userService.registerUser(user,"password");
//
//        Assert.assertNotNull(user);
//        Assert.assertNotNull(user.getPasswordHash());
//
//        Assert.assertTrue(userService.authenticate(user,"password"));
//
//        Assert.assertFalse(userService.authenticate(user,"not password"));
//    }
}
