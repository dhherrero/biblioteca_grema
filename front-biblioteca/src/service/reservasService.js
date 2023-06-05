import axios from "axios"

export const reservas= async(nif,rol) =>{  
    try {
        if(rol==="superusuario"){
            const {data}= await axios.post(
                "http://localhost:8080/reserva/all");
            return data
        }
        else{
            const {data}= await axios.post(
                "http://localhost:8080/reserva/reservasDe",
                {nifUsuario:nif});
            return data
        }     
      } catch (error) {
        console.log(error);
      }
}

export const newReserva = async (body) =>{  
    const url= "http://localhost:8080/reserva/new"
    const result= await axios.post(url,body)
    return result
}

export const ampliarReserva = async (body) =>{  
    const url= "http://localhost:8080/reserva/ampliarReserva"
    const result= await axios.post(url,body)
    return result
}

export const cancelarReserva = async (body) =>{  
    const url= "http://localhost:8080/reserva/cancelarReserva"
    const result= await axios.post(url,body)
    return result
}