import './User.css';
import {useEffect, useState} from "react";
import { editarUser,deleteUser } from '../../service/userService';


export default function User (persona){
   
   const user= persona.persona;

   const [isEditable, setIsEditable] = useState(false);

   const [nombre, setNombre] = useState(user.nombre?user.nombre:"Desconocido");
   const [nif, setNif] = useState(user.nif?user.nif:"Desconocido");
   const [password, setPassword] = useState(user.password?user.password:"Desconocido");
   const [correo, setCorreo] = useState(user.correoElectronico?user.correoElectronico:"Desconocido");
   const [telefono, setTelefono] = useState(user.telefono?user.telefono:"Desconocido");
   const [direccion, setDireccion] = useState(user.direccion?user.direccion:"Desconocido");
   const [web, setWeb] = useState(user.webPersonal?user.webPersonal:"Desconocido");

   const [backgroundColor, setBackgroundColor] = useState('white');
   const [border, setBorder] = useState('0px');

   const [borderEdit, setBorderEdit] = useState('2px solid #8DB6F5');

   const [color, setBackgroundEliminarColor] = useState('red');
   const [borderEliminar, setEliminarBorder] = useState('1px solid red');
   const [response, setResponse]=useState();
 
   const handleEditButtonClick = () => {
     setIsEditable(true);
     setBackgroundColor('#F9F2D7');
    setBorder('2px dashed #E1D8B1');
    setBackgroundEliminarColor();
    setEliminarBorder();
    setBorderEdit();
   };

   const handleSaveButtonClick = async() => {
    setIsEditable(false);
    setBackgroundColor('white');
   setBorder('0px');
   setBackgroundEliminarColor('red');
    setEliminarBorder('1px solid red');
    setBorderEdit('2px solid #8DB6F5');
    const body = {
        nif:nif,
        nombre: nombre,
        password: password,
        telefono:telefono,
        direccion:direccion,
        correoElectronico: correo,
        webPersonal: web
    }
    try{
        await editarUser(body).then(window.location.href="/allUsers")
    }catch{
        setResponse("Error")
    }

  };

  const handeButtonEliminar = async() => {
    const body = {
        nif:nif
    }
    await deleteUser(body).then(window.location.href="/allUsers")
  }


 
   
 
    return(
    <>
        <div className='totalInfoUser'>
            <div className='textoUser'>
                <p><b>Nombre: </b> <input className='datosUser' style={{ backgroundColor, border }} type="text" value={nombre} onChange={(name) => setNombre(name.target.value)} disabled={!isEditable} /></p>
                {/*<p><b>NIF: </b> <input className='datosUser' style={{ backgroundColor, border }} type="text" value={nif} onChange={(n) => setNif(n.target.value)} disabled={!isEditable} /></p>*/}
                <p><b>Password: </b><input className='datosUser' style={{ backgroundColor, border }} type="text" value={password} onChange={(t) => setPassword(t.target.value)} disabled={!isEditable} /></p>
                <p><b>Correo electrónico: </b> <input className='datosUser' style={{ backgroundColor, border }} type="text" value={correo} onChange={(email) => setCorreo(email.target.value)} disabled={!isEditable} /></p>
                <p><b>Teléfono: </b> <input className='datosUser' style={{ backgroundColor, border }} type="text" value={telefono} onChange={(telephone) => setTelefono(telephone.target.value)} disabled={!isEditable} /></p>
                <p><b>Dirección: </b><input className='datosUser' style={{ backgroundColor, border }} type="text" value={direccion} onChange={(adress) => setDireccion(adress.target.value)} disabled={!isEditable} /></p>
                <p><b>Web personal: </b><input className='datosUser' style={{ backgroundColor, border }} type="text" value={web} onChange={(www) => setWeb(www.target.value)} disabled={!isEditable} /></p>
            </div>
            <div className='botonesUser'>
                <div className="alienacion">
                    <button className="accionUser" onClick={handleEditButtonClick}  style={{border: borderEdit}} disabled={isEditable}>Editar</button> <br/> 
                    <button className="accionUser"  onClick={handleSaveButtonClick}  disabled={!isEditable}>Guardar</button><br/>
                    <button className="accionUser"  onClick={handeButtonEliminar} style={{color: color,border: borderEliminar}} disabled={isEditable}>Eliminar</button>
                    {response? <div><p><b>{response}</b> </p><br/></div>:"" }
                </div> 
            </div>
        </div>
    </>

    )

}