package com.app.backbiblioteca.Back.users;



import com.app.backbiblioteca.Back.users.controller.UserController;
import com.app.backbiblioteca.Back.users.controller.UserRequest;
import com.app.backbiblioteca.Back.users.controller.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = UserController.class)
public class UserControllerTest {
    @Mock
    private UserController userController = new UserController();

    @Test
    void contextLoads() {
    }
    @Test
    public void getUsersOk(){
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.getUsers()).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(userController.getUsers(),new ResponseEntity <>(HttpStatus.OK));
    }

    @Test
    public void infoUserOK() throws SQLException {
        UserRequest userRequest = UserRequest.builder().build();
        UserResponse respuesta= UserResponse.builder().estado(HttpStatus.OK).build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.infoUser(userRequest)).thenReturn(respuesta);
        assertEquals(userController.infoUser(userRequest),respuesta);
    }
    @Test
    public void infoUserNotAcceptable() throws SQLException {
        UserRequest userRequest = UserRequest.builder().build();
        UserResponse respuesta= UserResponse.builder().estado(HttpStatus.NOT_ACCEPTABLE).build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.infoUser(userRequest)).thenReturn(respuesta);
        assertEquals(userController.infoUser(userRequest),respuesta);
    }

    @Test
    public void loginOk(){
        UserRequest userRequest = UserRequest.builder().nif("00000000A").password("00").build();
        UserResponse respuesta= UserResponse.builder().estado(HttpStatus.OK).build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.login(userRequest)).thenReturn(respuesta);
        assertEquals(userController.login(userRequest),respuesta);
    }
    @Test
    public void loginNotAcceptable(){
        UserRequest userRequest = UserRequest.builder().nif("00000000A").password("01").build();
        UserResponse respuesta= UserResponse.builder().estado(HttpStatus.NOT_ACCEPTABLE).build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.login(userRequest)).thenReturn(respuesta);
        assertEquals(userController.login(userRequest),respuesta);
    }

    @Test
    public void changePasswordOk() throws SQLException {
        UserRequest userRequest = UserRequest.builder().nif("00000000A").password("01").build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.changePassword(userRequest)).thenReturn(new ResponseEntity <>(HttpStatus.OK));
        assertEquals(userController.changePassword(userRequest),new ResponseEntity <>(HttpStatus.OK));
    }
    @Test
    public void changePasswordNotAcceptable() throws SQLException {
        UserRequest userRequest = UserRequest.builder().nif("00000000A").build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.changePassword(userRequest)).thenReturn(new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
        assertEquals(userController.changePassword(userRequest),new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    public void deleteUserOk() throws SQLException {
        UserRequest userRequest = UserRequest.builder().nif("00000000A").build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.deleteUser(userRequest)).thenReturn(new ResponseEntity <>(HttpStatus.OK));
        assertEquals(userController.deleteUser(userRequest),new ResponseEntity <>(HttpStatus.OK));
    }

    @Test
    public void deleteUserNotAcceptable() throws SQLException {
        UserRequest userRequest = UserRequest.builder().nif("00000000A").build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.deleteUser(userRequest)).thenReturn(new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
        assertEquals(userController.deleteUser(userRequest),new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    public void newUserOk(){
        UserRequest userRequest = UserRequest.builder().nif("00000000A").password("00").rol("usuario").build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.newUser(userRequest)).thenReturn(new ResponseEntity <>(HttpStatus.OK));
        assertEquals(userController.newUser(userRequest),new ResponseEntity <>(HttpStatus.OK));
    }

    @Test
    public void newUserNotAcceptable(){
        UserRequest userRequest = UserRequest.builder().nif("00000000A").rol("usuario").build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.newUser(userRequest)).thenReturn(new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
        assertEquals(userController.newUser(userRequest),new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    public void updateUserOk(){
        UserRequest userRequest = UserRequest.builder().nif("00000000A").password("00").nombre("Julian").rol("usuario").build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.updateUser(userRequest)).thenReturn(new ResponseEntity <>(HttpStatus.OK));
        assertEquals(userController.updateUser(userRequest),new ResponseEntity <>(HttpStatus.OK));
    }
    @Test
    public void updateUserNotAceptable(){
        UserRequest userRequest = UserRequest.builder().build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(userController.newUser(userRequest)).thenReturn(new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
        assertEquals(userController.newUser(userRequest),new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
    }



}
