import React, {useState} from "react";
import "./Navbar.css"
import { Link } from "wouter";

const Navbar = () => {
    const [isOpen, setIsOpen] = useState(false)
    return(
        <div className="navbar">
          
            <div className="nav_logo"><Link to="/biblioteca"><img src="/grema.png" className="navIcon" alt="GREMA logo" title="Logo GREMA"/></Link>  <span className="titulo">BIBLIOTECA GREMA</span> </div>
            <div className={`nav_items ${isOpen && "open"}`}>
                <Link to="/biblioteca"><a>INICIO</a></Link>
                <Link to="/misreservas"><a> {sessionStorage.getItem("rol")==="superusuario"?"RESERVAS":"MIS RESERVAS"}</a></Link>
                {sessionStorage.getItem("rol")==="superusuario" && (<Link to="/newBook"><a>NUEVO LIBRO</a></Link>)}
                {sessionStorage.getItem("rol")==="superusuario" && (<Link to="/allUsers"><a>USUARIOS</a></Link>)}
                <Link to="/ajustes"><a>AJUSTES</a></Link>
                <Link to="/"><a>LOGOUT</a></Link>
            </div>
            <div className={`nav_toggle ${isOpen && "open"}`} onClick={ () => setIsOpen(!isOpen)} >
                <span></span>
                <span></span>
                <span></span>
            </div>
        </div>
    )
}
export default Navbar