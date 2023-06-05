import './MisReservas.css'
import {reservas } from '../../service/reservasService'
import Navbar from "../NavBar/Navbar";
import { useState, useEffect } from 'react';
import { ampliarReserva, cancelarReserva } from '../../service/reservasService';

export default function MisReservas(){
  const nif= sessionStorage.getItem("nif")
  const rol= sessionStorage.getItem("rol")
  const portadaDefecto="/noAvaiable.png"

  const [misReserva, setMisReservas] = useState([])
  
  const handleAmpliarReserva = async(idReserva)=> {
    const body = {
        idReserva: idReserva
    }
    await ampliarReserva(body).then(window.location.href="/misreservas")
  }
  const handleCancelarReserva = async(idReserva)=> {
    const body = {
        idReserva: idReserva
    }
    await cancelarReserva(body).then(window.location.href="/misreservas")
  }


  useEffect(()=> {
    reservas(nif,rol)
        .then((data) => {setMisReservas(data); console.log(data)})
    },[])

    return(
        <>
            <Navbar />
            <div className='listaReservas'>
                <h2>MIS RESERVAS</h2>
            {misReserva.length>0?misReserva.map(({titulo,portada,fechaInicio, fechaFin, idReserva, nifUsuario },i)=>{
                return(
                    <div className='reserva' key={idReserva}>
                        <img src={portada?portada:portadaDefecto}></img>
                        <div className='reserva-content'>
                            <h3>{titulo}</h3>
                            {sessionStorage.getItem("rol")==="superusuario" &&<p className='infoReserva'><b>NIF:</b> {nifUsuario}</p>}
                            <div className='fechas'>
                                    <p className='infoReserva'><b>FECHA RESERVA:</b> {fechaInicio}</p>
                                    <p className='infoReserva' ><b>FECHA FIN RESERVA:</b> {fechaFin}</p>
                            </div>
                            <div className='botones'>
                                    <button className="botonCancelarReserva" onClick={()=>handleCancelarReserva(idReserva)}> Cancelar reserva</button> 
                                    <button className="botonAmpliarReserva" onClick={()=>handleAmpliarReserva(idReserva)}> Ampliar reserva</button>      
                            </div>
                        </div>
                    </div>

                )
            }
            
            ): (<h2>No tienes reservas</h2>)}
            </div>
        </>)



}
