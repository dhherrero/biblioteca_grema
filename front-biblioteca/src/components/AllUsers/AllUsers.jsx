
import Navbar from "../NavBar/Navbar"
import User from "../User/User"
import { useEffect, useState } from "react";
import {getAllUsers} from "../../service/userService";
import "./AllUsers.css"

export default function AllUsers (){
    const [usuarios, setUsuarios] = useState([])

    useEffect(()=> {
        getAllUsers()
            .then((result) => setUsuarios(result.data))
        
    },[])
    return(
        <>
            <Navbar />
            <h2 className="tt5"> USUARIOS</h2>
            <div className="listaUsuarios">
            {usuarios.map((usuario,i)=>{
                return(
                    <div key={usuario.nif}>
                       <User persona={usuario} />
                    </div>
                )
            })}
            </div>
            
            
            
        </>
    )
}