import axios from "axios"


export const obtenerLibros= async(orderBy) =>{
    const url = "http://localhost:8080/book/allBooks"
    //const url = `http://localhost:8080/book/allBooks/${orderBy}`
    const result = await axios.get(url, {params: {orderBy}})
    //const result = await axios.get(url)
    return result
}

export const obtenerUnLibro= async(requestNumero) =>{
    try{
        const body= {
            id:requestNumero,
            nifUsuario: sessionStorage.getItem("nif")
        }
        const response = await axios.post(
            'http://localhost:8080/book/getBook',
            body
          );
          console.log(response.data)
        return response.data
    }
    catch (error) {
        console.log(error);
      }  
}

export const newLibro = async(body) =>{
    try{
        console.log(body)
        const response = await axios.post(
            'http://localhost:8080/book/newBook',
            body
          );
          console.log(response.data)
        return response.data
    } catch (error) {
        console.log(error);
      }
}

export const deleteBook = async (body) =>{  
    const url= "http://localhost:8080/book/deleteBook"
    const result= await axios.post(url,body)
    return result
}






