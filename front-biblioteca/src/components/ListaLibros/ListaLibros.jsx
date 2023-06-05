import {obtenerLibros} from "../../service/bookService";
import { useEffect, useState } from "react";
import { Link } from "wouter";
import "./ListaLibros.css"




export default function ListaLibros(){
    const portadaDefecto="/noAvaiable.png"
    const [books, setBooks] = useState([])
    const [orderBy, setOrderBy] = useState("defecto") 
    
    useEffect(()=> {
        obtenerLibros(orderBy)
            .then((result) => setBooks(result.data))
        
    },[])

    const handleChange = (event) => {
        const orderBy = event.target.value
        setOrderBy(orderBy)
        obtenerLibros(orderBy).then((result) => setBooks(result.data))
        
      };

    return(
        <>
       <span>Order by </span>
        <select value={orderBy} onChange={handleChange} className="orderBy">
            <option value="defecto">Selecciona una opción</option>
            <option value="titulo">TITULO</option>
            <option value="autor">AUTOR</option>
            <option value="edad">EDAD RECOMENDADA</option>
        </select>
        <div className="listaLibros">
            {books.map(({id, portada, titulo,autores,edad },i)=>{
                return(
                    <div key={id} className="libros">
                        <Link to={`/biblioteca/${id}`}>
                        <img className="portada" src={portada ? portada : portadaDefecto}/>
                        <div className="infoBook">
                            <p><b>Titulo: </b> {titulo}</p>
                            <p><b>Autor/es: </b> {autores}</p>
                            <p><b>Edad recomendada: </b> {edad}</p>
                        </div></Link>
                    </div>
                )
            })}</div>
        </>
    )
}