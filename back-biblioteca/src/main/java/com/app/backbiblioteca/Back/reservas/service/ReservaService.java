package com.app.backbiblioteca.Back.reservas.service;

import com.app.backbiblioteca.Back.books.BookDTO.BookDTO;
import com.app.backbiblioteca.Back.config.DatabaseConfig;
import com.app.backbiblioteca.Back.reservas.dto.ReservasDTO;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
@NoArgsConstructor
public class ReservaService {


    private final Logger logger= LogManager.getLogger(this.getClass());
    private static DatabaseConfig db = new DatabaseConfig();

    /**
     * Introduce una nueva reserva en la base de datos
     * @param reservasDTO
     * @return confrmación si se ha añadido correctamente
     * @throws SQLException
     */
    public HttpStatus newReserva(ReservasDTO reservasDTO) throws SQLException {
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusDays(30);

        if (!libroDisponible(reservasDTO.getIdLibro())){
            logger.warn("No puede reservar, no hay libros disponibles");
            return HttpStatus.FORBIDDEN;
        }
        if (yaLoHaReservado(reservasDTO.getNifUsuario(), reservasDTO.getIdLibro())){
            logger.warn("No puede reservar, la persona ya lo ha reservado");
            return HttpStatus.FORBIDDEN;
        }
        if (!puedeReservar(reservasDTO.getNifUsuario())){
            logger.warn("No puede reservar, ya tiene 3 reservas");
            return HttpStatus.FORBIDDEN;
        }
        String sql = "INSERT INTO reservas (nifUsuario, idLibro, fechaInicio, fechaFin, estadoReserva) VALUES(?, ?, ?, ?, ?)";
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setString(1,reservasDTO.getNifUsuario());
            pst.setInt(2,reservasDTO.getIdLibro());
            pst.setDate(3, Date.valueOf(fechaInicio));
            pst.setDate(4,Date.valueOf(fechaFin));
            pst.setString(5, "activa");
            pst.execute();
            dbcon.close();
            logger.info("/newReserva isClosed?: "+dbcon.isClosed());
        }
        catch (SQLException throwables) {
            logger.error(throwables);
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.CREATED;
    }

    /**
     * Comprueba si un libro esta disponible para reservar
     * @param idLibro
     * @return true or false
     */
    public boolean libroDisponible(int idLibro){
        String sql = "SELECT COUNT(*) FROM reservas WHERE idLibro = ? AND estadoReserva='activa'";
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setInt(1,idLibro);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                int librosReservados= rs.getInt(1);
                int copias = copiasLibro(idLibro);
                logger.info("Hay " + (copias-librosReservados) + " copias disponibles");
                if ((copias-librosReservados)>0){
                    return true;
                }
            }
        }catch (SQLException throwables) {
            logger.error(throwables);
        }
        return false;
    }

    /**
     * Comprueba el numero de copias de un libro
     * @param idLibro
     * @return número de copias
     */
    public int copiasLibro (int idLibro){
        String sql = "SELECT copias  FROM libro WHERE id = ? ";
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setInt(1,idLibro);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }
        }catch (SQLException throwables) {
            logger.error(throwables);
        }
        return 0;
    }

    /**
     * Un usuario puede reservar si tiene menos de 3 reservas, este método se enarga de comprobar si puede reservar
     * @param nifUsuario
     * @return boolean puede reservar
     * @throws SQLException
     */
    public boolean puedeReservar(String nifUsuario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservas WHERE nifUsuario = ? AND estadoReserva = 'activa' ";
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setString(1,nifUsuario);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                int numReservasActivas= rs.getInt(1);
                if (numReservasActivas<3){
                    return true;
                }
            }
            dbcon.close();
        }catch (SQLException throwables) {
            logger.error(throwables);
        }
        return false;
    }

    /**
     * Comprueba si un usuario ha reservadoo un libro
     * @param nifUsuario
     * @param idLibro
     * @return boolean de libro reservadi
     * @throws SQLException
     */
    public boolean yaLoHaReservado(String nifUsuario, int idLibro) throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservas WHERE nifUsuario = ? AND idLibro = ? and estadoReserva = 'activa';";
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setString(1,nifUsuario);
            pst.setInt(2,idLibro);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                int numReservasActivas= rs.getInt(1);
                if (numReservasActivas>=1){
                    return true;
                }
            }
            dbcon.close();
        }catch (SQLException throwables) {
            logger.error(throwables);
        }
        return false;
    }

    /**
     * Cambia el estado de una reserva activa a inactiva
     * @param idReserva
     * @return confirmación de que el cambio se ha realizado con éxito
     */
    public HttpStatus cancelarRservas (int idReserva){
        String sql = "UPDATE reservas SET estadoReserva='inactiva' WHERE id=?";
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setInt(1,idReserva);
            pst.executeUpdate();
        }catch (SQLException throwables) {
            logger.error(throwables);
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.OK;
    }

    /**
     * Obtiene la fehcha de finalización de una reserva
     * @param idReserva
     * @return fecha
     */
    public LocalDate getFechaFin (int idReserva){
        String sql = "SELECT fechaFin FROM reservas WHERE id=?";
        LocalDate fechaFin = null;
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setInt(1,idReserva);
            ResultSet rs =pst.executeQuery();
            if (rs.next()){
                fechaFin = rs.getDate("fechaFin").toLocalDate();
            }
        }catch (SQLException throwables) {
            logger.error(throwables);
        }
        return fechaFin;
    }

    /**
     * Amplia la fecha de finalización de un reserva en la base de datos
     * @param idReserva
     * @return confirmación de si se ha realizado la operación con éxito
     */
    public HttpStatus ampliarReserva (int idReserva){
        String sql = "UPDATE reservas SET fechaFin=? WHERE id=?";
        LocalDate fechaFin = getFechaFin(idReserva);
        LocalDate nuevaFecha = fechaFin.plusDays(30);
        Date fechaNueva = Date.valueOf(nuevaFecha);
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setDate(1,fechaNueva);
            pst.setInt(2, idReserva);
            pst.executeUpdate();
        }catch (SQLException throwables) {
            logger.error(throwables);
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.OK;

    }

    /**
     * Almacena un resultset en un objeto de tipo reservaDTO
     * @param rs
     * @return objeto de tipo reserva
     * @throws SQLException
     */
    public ReservasDTO saveInReserva(ResultSet rs) throws SQLException {
        return ReservasDTO.builder().idReserva(rs.getInt("id")).
                idLibro(rs.getInt("idLibro")).fechaFin(rs.getDate("fechaFin")).
                fechaInicio(rs.getDate("fechaInicio")).nifUsuario(rs.getString("nifUsuario")).
                estadoReserva(rs.getString("estadoReserva")).titulo(rs.getString("titulo")).
                portada(rs.getString("portada")).build();
    }

    /**
     * Obtiene un listado de todas las reservas en la base de datos
     * @return listado de las reservas
     */
    public ArrayList<ReservasDTO> allReservas(){
        String sql ="SELECT reservas.* , libro.titulo, libro.portada  FROM reservas JOIN libro  ON reservas.idLibro = libro.id WHERE reservas.estadoReserva='activa'";
        ArrayList <ReservasDTO> listaReservas = new ArrayList<>();
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                ReservasDTO reserva= saveInReserva(rs);
                listaReservas.add(reserva);
            }
            dbcon.close();
            logger.info("/allReservas isClosed?: "+dbcon.isClosed());
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return listaReservas;
    }

    /**
     * Devuelve un listado de las reservas activas de un usuario
     * @param nifUsuario
     * @return listado de reservas
     */
    public ArrayList<ReservasDTO> nifReservas(String nifUsuario){
        String sql ="SELECT reservas.* , libro.titulo, libro.portada  FROM reservas JOIN libro  ON reservas.idLibro = libro.id WHERE reservas.nifUsuario=? AND reservas.estadoReserva='activa'";
        ArrayList <ReservasDTO> listaReservas = new ArrayList<>();
        try(Connection dbcon= db.hikariDataSource.getConnection(); PreparedStatement pst= dbcon.prepareStatement(sql)) {
            pst.setString(1,nifUsuario);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                ReservasDTO reserva= saveInReserva(rs);
                listaReservas.add(reserva);
            }
            dbcon.close();
            logger.info("/nifReservas isClosed?: "+dbcon.isClosed());
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return listaReservas;
    }

}
