
import axios from "axios"

const handleLocation= (nif,rol,setLocation) =>{
  setLocation("/biblioteca")
  sessionStorage.setItem("nif",nif)
  sessionStorage.setItem("rol",rol)

}
export const getAllUsers= async() =>{
  const url = "http://localhost:8080/user/allUsers"
  //const url = `http://localhost:8080/book/allBooks/${orderBy}`
  const result = await axios.get(url)
  //const result = await axios.get(url)
  return result
}
export const infoUser= async(nif) =>{
  try{
      const body= {
         nif: nif
      }
      const response = await axios.post(
          'http://localhost:8080/user/infoUser',
          body
        );
        console.log(response.data)
      return response.data
  }
  catch (error) {
      console.log(error);
    }  
}

export const login= async(user,setLocation,setResponse) =>{
    try {
        const {data: {nif,rol,estado }}= await axios.post(
          'http://localhost:8080/user/login',
          user);
      
        console.log(estado)
        estado === "OK" ?  handleLocation(nif,rol,setLocation)  : setResponse("Usuario y/o contraseña incorrectos")
        
      } catch (error) {
        console.log(error);
      }
}

export const register= async(body,setLocation,setResponse)=>{
  try {
    const response = await axios.post(
      'http://localhost:8080/user/newUser',
      body
    );
    console.log(response.data);
    response.data=== 'OK' ? handleLocation(body.nif, body.rol, setLocation) :setResponse("Error al registrar el usuario, compruebe los datos")
  } catch (error) {
    console.log(error);
  }
}

export const changePassword = async(body)=>{
  const url= "http://localhost:8080/user/changePassword"
  const result= await axios.post(url,body)
  return result
}

export const deleteUser = async(body)=>{
  const url= "http://localhost:8080/user/deleteUser"
  const result= await axios.post(url,body)
  return result
}

export const editarUser = async(body)=>{
  const url= "http://localhost:8080/user/updateUser"
  const result= await axios.post(url,body)
  return result
}
