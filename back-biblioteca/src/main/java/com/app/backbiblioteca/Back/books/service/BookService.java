package com.app.backbiblioteca.Back.books.service;

import com.app.backbiblioteca.Back.books.BookDTO.BookDTO;
import com.app.backbiblioteca.Back.books.controller.BookRequest;
import com.app.backbiblioteca.Back.config.DatabaseConfig;
import com.app.backbiblioteca.Back.reservas.service.ReservaService;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@NoArgsConstructor
public class BookService {
    private final Logger logger= LogManager.getLogger(this.getClass());
    private static DatabaseConfig db = new DatabaseConfig();
    private static final String BOOK_NOT_CREATED= "BOOK NOT CREATED";
    private static final String BOOK_NOT_DELETED= "BOOK NOT DELETED";
    private static final String PHOTO_ERROR= "PHOTO_NOT_CREATED";
    private static final String BOOK_CREATED= "BOOK CREATED";
    private static final String PHOTO_CREATED= "PHOTO CREATED";

    /**
     * Método que devuelve la columna correspondiente a la base de datos
     * @param orderBy
     * @return string
     */
    public String getOrder(String orderBy){
        Map<String,String> mapResponse= new HashMap<>();
        String order=" ORDER BY ";
        mapResponse.put("titulo",order+ "titulo");
        mapResponse.put("autor",order+ "autores");
        mapResponse.put("edad",order+ "edad");
        mapResponse.put("editorial",order+ "editorial");
        mapResponse.put("fecha",order+ "fechaEdicion");
        mapResponse.put("genero",order+ "genero");
        mapResponse.put("formato",order+ "formato");
        mapResponse.put("defecto"," ");
        return mapResponse.get(orderBy);
    }

    /**
     * Método que guarda un ResultSet en un objeto tipo BookDTO
     * @param rs
     * @return objeto tipo BookDTO
     * @throws SQLException
     */
    private BookDTO saveInBook(ResultSet rs) throws SQLException {
        BookDTO book= BookDTO.builder().id(rs.getInt("id")).titulo(rs.getString("titulo")).
                autores(rs.getString("autores")).isbn(rs.getString("isbn")).
                edad(rs.getInt("edad")).editorial(rs.getString("editorial")).
                fechaEdicion(rs.getDate("fechaEdicion")).lenguaPublicacion(rs.getString("lenguaPublicacion")).
                lenguaTraduccion(rs.getString("lenguaTraduccion")).numeroPaginas(rs.getInt("numeroPaginas")).
                descripcion(rs.getString("descripcion")).edicion(rs.getInt("edicion")).formato(rs.getString("formato")).
                genero(rs.getString("genero")).copias(rs.getInt("copias")).
                portada(rs.getString("portada")).imagen2(rs.getString("imagen2")).imagen3(rs.getString("imagen3")).build();

        return book;
    }

    /**
     * Método para borrar un libro de la base de datos
     * @param id
     * @return confirmación del libro borrado con éxito
     */
    public HttpStatus deleteBook(int id) {
        String sql= "DELETE FROM libro WHERE id=?";
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setInt(1,id);
            pst.execute();
            dbcon.close();
            logger.info("/deleteBook isClosed?: "+dbcon.isClosed());
        }
        catch (SQLException throwables){
            logger.error(throwables);
            logger.error(BOOK_NOT_DELETED);
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.OK;
    }


    /**
     * Método para introducir un libro en la base de datos
     * @param book
     * @return confirmación del libro añadido con éxito
     */
    public HttpStatus insertBook(BookRequest book){
        String sql ="INSERT INTO libro (titulo, autores, isbn, edad, editorial, fechaEdicion, lenguaPublicacion, lenguaTraduccion, numeroPaginas, descripcion, edicion, formato, genero, copias,portada,imagen2,imagen3) VALUES(?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?)";

        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setString(1, book.getTitulo());
            pst.setString(2, book.getAutores());
            pst.setString(3, book.getIsbn());
            pst.setInt(4,book.getEdad());
            pst.setString(5, book.getEditorial());
            pst.setDate(6,book.getFechaEdicion());
            pst.setString(7,book.getLenguaPublicacion());
            pst.setString(8,book.getLenguaTraduccion());
            pst.setInt(9,book.getNumeroPaginas());
            pst.setString(10, book.getDescripcion());
            pst.setInt(11, book.getEdicion());
            pst.setString(12, book.getFormato());
            pst.setString(13, book.getGenero());
            pst.setInt(14, book.getCopias());
            pst.setString(15,book.getPortada());
            pst.setString(16,book.getImagen2());
            pst.setString(17,book.getImagen3());
            pst.execute();
            logger.info(BOOK_CREATED);
            dbcon.close();
            logger.info("/insertBook isClosed?: "+dbcon.isClosed());


        } catch (SQLException throwables) {
            logger.error(throwables);
            logger.error(BOOK_NOT_CREATED);
            return HttpStatus.NOT_ACCEPTABLE;
        }
            return HttpStatus.CREATED;
    }

    /**
     *Método que obtiene todos los libros de la base de datos, además puede ordenarlos en función del valor introducido
     * @param orderBy
     * @return listado de libros
     */
    public ArrayList <BookDTO> allBooks(String orderBy){
        String sql ="SELECT * FROM libro " + getOrder(orderBy) ;
        ArrayList <BookDTO> listaLibros = new ArrayList<>();
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                BookDTO book= saveInBook(rs);
                listaLibros.add(book);
            }
            dbcon.close();
            logger.info("/allBooks isClosed?: "+dbcon.isClosed());

        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return listaLibros;
    }

    /**
     * Método que comprueba si un usuario puede reservar un libro
     * @param idLibro
     * @param nifUsuario
     * @return boolean
     */
    public boolean userCanReservBook(int idLibro, String nifUsuario) throws SQLException {
        String sql= "SELECT COUNT(*) AS count from reservas where nifUsuario =? AND idLibro =? AND estadoReserva ='activa'";
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setString(1,nifUsuario);
            pst.setInt(2,idLibro);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                int result= rs.getInt("count");
                if (result>=1) return  false;
            }
        }catch (SQLException throwables){
            logger.error(throwables);
            return false; }
        ReservaService reservaService = new ReservaService();
        if(!reservaService.puedeReservar(nifUsuario)) return false;
        return  true;
    }


    /**
     * Método que devuelva la información de un libro
     * @param payload
     * @return book
     */
    public Object readBook(BookRequest payload){
        logger.info("/getBook: "+ payload.getId());
        String sql ="SELECT * FROM libro  where id =?";
        BookDTO libro=null;

        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setInt(1,payload.getId());
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                libro= saveInBook(rs);
                ReservaService reservaService = new ReservaService();
                libro.setDisponible(reservaService.libroDisponible(payload.getId()));
                libro.setCanReserve(userCanReservBook(payload.getId(),payload.getNifUsuario()));
            }
            else{
                logger.info("BOOK '"+payload.getId()+"' NOT FOUND");
                dbcon.close();
                logger.info("/readBook isClosed?: "+dbcon.isClosed());
                return HttpStatus.NOT_FOUND;
            }
            dbcon.close();
            logger.info("/readBook isClosed?: "+dbcon.isClosed());

        } catch (SQLException throwables) {
            logger.error(throwables);
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return libro;
    }
}
