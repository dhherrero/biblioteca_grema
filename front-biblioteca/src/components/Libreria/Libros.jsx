import Navbar from "../NavBar/Navbar";
import ListaLibros from "../ListaLibros/ListaLibros";
import './Libros.css';



export default function Libros (){
    return(
        <>
            <Navbar />
            <h2 className="tt"> LIBROS</h2>
            <div className="booksList">
                <ListaLibros />
            </div>  
            
        </>
    )

}
