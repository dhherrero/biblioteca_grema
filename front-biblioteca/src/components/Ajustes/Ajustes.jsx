import Navbar from "../NavBar/Navbar";
import './Ajustes.css';
import { infoUser } from "../../service/userService";
import {useEffect, useState} from "react";
import { changePassword } from "../../service/userService";

export default function Ajustes (){
    const noContent ="undefined"
    const [borderOld, setBorderOld]= useState('grey solid 1px');
    const [user, setUser] = useState({nombre:'',nif:'',correoElectronico:'',telefono:'',direccion:''})
    const [borderEdit, setBorderEdit] = useState('grey solid 1px');
    const [newP, setNewP] = useState("");
    const [eqP, setEqP] = useState("");
    const [actualP, setActualP] = useState("")
    const [response, setResponse] = useState()
    const [isDisable,setIsDiasble]= useState(true);
    const [oldPassCheck, setOldCheck]= useState(false);
    const [newPasCheck, setNewPCheck]= useState(false);

    useEffect(()=> {
        infoUser(sessionStorage.getItem("nif"))
            .then((result) =>{ setUser(result); console.log(user)})
        
    },[])

    const checkPasswordEquals = (password) =>{
        if (password=== ''){
            setBorderEdit("grey solid 1px")
            setNewPCheck(false)
            setIsDiasble(true)
        }
        else if (password === eqP) {
            setBorderEdit('green solid 1px')
            setNewPCheck(true)
            if (oldPassCheck===true){
                setIsDiasble(false)
            }
        }
        else {
            setBorderEdit("red solid 1px")
            setNewPCheck(false)
            setIsDiasble(true)
        }
    }
    const secondCheck= (password) =>{
        if (password=== ''){
            setBorderEdit("grey solid 1px")
            setNewPCheck(false)
            setIsDiasble(true)
        }
        else if (password === newP) {
            setBorderEdit('green solid 1px')
            setNewPCheck(true)
            if (oldPassCheck===true){
                setIsDiasble(false)
            }
            
        }
        else {
            setBorderEdit("red solid 1px")
            setNewPCheck(false)
            setIsDiasble(true)
        }
    }
    const handleActualPas = (password)=>{
        setActualP(password)
       if (password===user.password){
            setOldCheck(true)
            setBorderOld('green solid 1px')
            if (newPasCheck===true){
                setIsDiasble(false)
            }
        }
        else if (password==''){
            setOldCheck(false)
            setBorderOld('grey solid 1px')
            setIsDiasble(true)
        }
        else{
            setOldCheck(false)
            setBorderOld('red solid 1px')
            setIsDiasble(true)
        }
        
    }

    const handleChangeNewPassword = (password) =>{
        setNewP(password);
        checkPasswordEquals(password);
            
    }

    const handleEqPassword = (password)=>{
        setEqP(password);
        secondCheck(password);
    }

    const handleChangePassword = async()=>{
        const body = {
            nif: sessionStorage.getItem("nif"),
            password : newP
        }
        try{
            await changePassword(body).then(setResponse("Se ha cambiado correctamente"))
        }catch{
            setResponse("Error al cambiar la contraseña")
        }
    }
    
    return(
        <>
            <Navbar />
            <h2 className="tt2"> AJUSTES</h2>
            
            <div className="ajustesContent">
                <div className="infoUser">
                <h3 className="changePP">Información usuario</h3>
                    <p><b>Nombre:</b> {user.nombre?user.nombre:noContent}</p>
                    <p><b>NIF:</b> {user.nif?user.nif:noContent}</p>
                    <p><b>Correo electrónico: </b>  {user.correoElectronico?user.nombre:noContent}</p>
                    <p><b>Teléfono:</b> {user.telefono?user.telefono:noContent}</p>
                    <p><b>Dirección:</b> {user.direccion?user.direccion:noContent}</p>
                </div>
                <div className="cambiarP">
                    <form>
                        <div className="formularioCambio">
                            <h3 className="changePP">Cambio de contraseña </h3>
                            <label  className="labelPassword" >Contraseña actual</label>
                            <input className="inputAjustes" type="password" style={{border: borderOld}} name="oldPassword" value={actualP} onChange={(actualPas) => {handleActualPas(actualPas.target.value)}}/>
                            <label className="labelPassword" >Contraseña nueva</label>
                            <input className="inputAjustes" style={{border: borderEdit}} value={newP} onChange={(newPas) => {handleChangeNewPassword(newPas.target.value)}} type="password" name="newPassword"/>
                            <label className="labelPassword"  >Repite la contraseña nueva</label>
                            <input className="inputAjustes" style={{border: borderEdit}} value={eqP} onChange={(eqPas) => handleEqPassword(eqPas.target.value)} type="password" name="repNewPassword"/> <br/>
                            <button className="accionUser" disabled={isDisable} onClick={handleChangePassword}>Cambiar contraseña</button>  <br/>
                            {response? <div><p><b>Resultado:</b> {response} </p> <br/></div>:"" }
                        </div>
                        
                    </form>
                    
                </div>
            </div>
            
        </>
    )

}