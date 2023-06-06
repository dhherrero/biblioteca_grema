package com.app.backbiblioteca.Back.reservas;


import com.app.backbiblioteca.Back.reservas.controller.ReservaController;
import com.app.backbiblioteca.Back.reservas.dto.ReservasDTO;
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
@ContextConfiguration(classes = ReservaController.class)
public class ReservasControllerTest {

    @Mock
    private ReservaController reservaController = new ReservaController();

    @Test
    public void newReservaOk() throws SQLException {
        ReservasDTO reservasDTO = ReservasDTO.builder().idLibro(1).nifUsuario("00000000A").build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(reservaController.newReserva(reservasDTO)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(reservaController.newReserva(reservasDTO),new ResponseEntity <>(HttpStatus.OK));
    }

    @Test
    public void newReservaNotAcceptable() throws SQLException {
        ReservasDTO reservasDTO = ReservasDTO.builder().build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(reservaController.newReserva(reservasDTO)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
        assertEquals(reservaController.newReserva(reservasDTO),new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    public void allReservasOk (){
        MockitoAnnotations.openMocks(this);
        Mockito.when(reservaController.allReservas()).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(reservaController.allReservas(),new ResponseEntity <>(HttpStatus.OK));
    }

    @Test
    public void cancelarOk(){
        ReservasDTO reservasDTO = ReservasDTO.builder().idReserva(1).build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(reservaController.cancelar(reservasDTO)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(reservaController.cancelar(reservasDTO),new ResponseEntity <>(HttpStatus.OK));
    }

    @Test
    public void cancelarNotAcceptable(){
        ReservasDTO reservasDTO = ReservasDTO.builder().build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(reservaController.cancelar(reservasDTO)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
        assertEquals(reservaController.cancelar(reservasDTO),new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    public void reservasDeOk(){
        ReservasDTO reservasDTO = ReservasDTO.builder().nifUsuario("00000000A").build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(reservaController.reservasDe(reservasDTO)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(reservaController.reservasDe(reservasDTO),new ResponseEntity <>(HttpStatus.OK));
    }

    @Test
    public void ampliarOk(){
        ReservasDTO reservasDTO = ReservasDTO.builder().idReserva(20).build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(reservaController.ampliar(reservasDTO)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(reservaController.ampliar(reservasDTO),new ResponseEntity <>(HttpStatus.OK));
    }

    @Test
    public void ampliarNotAcceptable(){
        ReservasDTO reservasDTO = ReservasDTO.builder().idReserva(20).build();
        MockitoAnnotations.openMocks(this);
        Mockito.when(reservaController.ampliar(reservasDTO)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
        assertEquals(reservaController.ampliar(reservasDTO),new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
    }




}
