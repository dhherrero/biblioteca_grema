package com.app.backbiblioteca.Back.books;
import com.app.backbiblioteca.Back.books.controller.BookController;
import com.app.backbiblioteca.Back.books.controller.BookRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;



@SpringBootTest
@ContextConfiguration(classes = BookController.class)
public class BookControllerTest {


    @Mock
    private BookController bookController = new BookController();
    @Test
    public void readBookOk(){
        BookRequest payload = new BookRequest();
        payload.setId(1);
        MockitoAnnotations.openMocks(this);
        Mockito.when(bookController.readBook(payload)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(bookController.readBook(payload),new ResponseEntity <>(HttpStatus.OK));
    }
    @Test
    public void readBookNotFound(){
        BookRequest payload = new BookRequest();
        payload.setId(125);
        MockitoAnnotations.openMocks(this);
        Mockito.when(bookController.readBook(payload)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        assertEquals(bookController.readBook(payload),new ResponseEntity <>(HttpStatus.NOT_FOUND));
    }
    @Test
    public void readBookNotAcceptable(){
        BookRequest payload = new BookRequest();
        MockitoAnnotations.openMocks(this);
        Mockito.when(bookController.readBook(payload)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
        assertEquals(bookController.readBook(payload),new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    public void deleteBookOK(){
        BookRequest payload = new BookRequest();
        MockitoAnnotations.openMocks(this);
        payload.setId(1);
        Mockito.when(bookController.deleteBook(payload)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(bookController.deleteBook(payload),new ResponseEntity <>(HttpStatus.OK));
    }
    @Test
    public void deleteBookNotAcceptable(){
        BookRequest payload = new BookRequest();
        MockitoAnnotations.openMocks(this);
        Mockito.when(bookController.deleteBook(payload)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
        assertEquals(bookController.deleteBook(payload),new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    public void allBooksOK(){
        BookRequest payload = new BookRequest();
        MockitoAnnotations.openMocks(this);
        Mockito.when(bookController.allBooks("")).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(bookController.allBooks(""),new ResponseEntity <>(HttpStatus.OK));
    }
    @Test
    public void allBooksOrderBy(){
        MockitoAnnotations.openMocks(this);
        Mockito.when(bookController.allBooks("titulo")).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(bookController.allBooks("titulo"),new ResponseEntity <>(HttpStatus.OK));
    }
    @Test
    public void allBooksOrderByCampoQueNoExisteYDevuelvePorDefecto(){
        MockitoAnnotations.openMocks(this);
        Mockito.when(bookController.allBooks("pelicula")).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(bookController.allBooks("pelicula"),new ResponseEntity <>(HttpStatus.OK));
    }
    @Test
    public void newBookOk(){
        BookRequest payload = new BookRequest();
        payload.setTitulo("Historia de Grecia");
        MockitoAnnotations.openMocks(this);
        Mockito.when(bookController.newBook(payload)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
        assertEquals(bookController.newBook(payload),new ResponseEntity <>(HttpStatus.CREATED));
    }
    @Test
    public void newBookNotAcceptable(){
        BookRequest payload = new BookRequest();
        MockitoAnnotations.openMocks(this);
        Mockito.when(bookController.newBook(payload)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
        assertEquals(bookController.newBook(payload),new ResponseEntity <>(HttpStatus.NOT_ACCEPTABLE));
    }

}
