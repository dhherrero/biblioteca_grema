import React, { useState } from 'react';
import axios from 'axios';
import Navbar from '../NavBar/Navbar';
import { newLibro } from '../../service/bookService';
import "./Upload.css"



const UploadForm = () => {
  const [portada, setPortada] = useState(null);
  const [imagen2, setImagen2] = useState(null);
  const [imagen3, setImagen3] = useState(null);
  const [response,setResponse]= useState();
  const [response1,setResponse1]= useState();
  const [response2,setResponse2]= useState();
  const [response3,setResponse3]= useState();


  const [body, setBody] = useState({
        titulo:null,
        autores:null,
        isbn:null,
        edad:null,
        editorial:null,
        fechaEdicion:null,
        lenguaPublicacion:null,
        lenguaTraduccion:null,
        numeroPaginas:null,
        descripcion:null,
        edicion:null,
        formato:null,
        genero:null,
        copias:null,
        portada: null,
        imagen2: null,
        imagen3: null
  });

 
 
  const handleFileInputChange = (event) => {
    setPortada(event.target.files[0]);
  };

  const handleFileInputChange2 = (event) => {
    setImagen2(event.target.files[0]);
  };

  const handleFileInputChange3 = (event) => {
    setImagen3(event.target.files[0]);
  };

  const handleLibroChange = event => {
    const { name, value } = event.target;
    setBody({ ...body, [name]: value });
  };

  const handlePortada = async()=>{
        const formData = new FormData();
        formData.append('file', portada);
        formData.append('upload_preset', 'evsd0zlj');
        
        try {
          const response = await axios.post(
            'https://api.cloudinary.com/v1_1/dnsxhjncj/image/upload',
            formData
          );
          body.portada=response.data.secure_url
          setPortada(null);
          setResponse1("Imagen cargada correctamente")
        } catch (error) {
          console.log(error);
          setResponse1("Error al cargar la foto");
        }
  }

  const handleImagen2 = async()=>{
    const formData2 = new FormData();
        formData2.append('file', imagen2);
        formData2.append('upload_preset', 'evsd0zlj');
        
        try {
          const response = await axios.post(
            'https://api.cloudinary.com/v1_1/dnsxhjncj/image/upload',
            formData2
          );
          body.imagen2=response.data.secure_url
          setImagen2(null);
          setResponse2("Imagen cargada correctamente")
        } catch (error) {
          console.log(error);
          setResponse2("Error al cargar la foto");
        }
  }
  const handleImagen3 =async()=>{
    const formData3 = new FormData();
    formData3.append('file', imagen3);
    formData3.append('upload_preset', 'evsd0zlj');
    
    try {
      const response = await axios.post(
        'https://api.cloudinary.com/v1_1/dnsxhjncj/image/upload',
        formData3
      );
      body.imagen3=response.data.secure_url
      setBody({ ...body, [imagen3]: response.data.secure_url });
      setImagen3(null);
      setResponse3("Imagen cargada correctamente")
    } catch (error) {
      console.log(error);
      setResponse3("Error al cargar la foto");
    }
  }


 

  const handleFormSubmit = async () => {
      if(portada|| imagen2 || imagen3){
        setResponse("Hay que cargar las imágenes antes")
      }
      else{
      console.log(body)
      try{
          await newLibro(body).then((result)=> {console.log("RESULTADO UPLOAD: "+ result);setResponse(result)})
      }
      catch{
        setResponse("Error al cargar el libro");
      }}
    }

/* response.data.secure_url es el link a guardar en la bbdd */
  return (
    <>
     <Navbar />
    <div className='content' >
        <h4>Rellene los siguientes campos</h4>
      <div  className="formulario">
        <input type="text" className="inputNew" onChange={handleLibroChange} name='titulo' placeholder='Titulo'/><br/>
        <textarea type="text" id="descripcion" onChange={handleLibroChange} name='descripcion' className="inputNew" placeholder='Descripción'/><br/>
        <input type="text" className="inputNew" onChange={handleLibroChange} name='autores' placeholder='Autor/es, por ejemplo:  Juan Rodriguez, Felipe Pintor'/><br/>
        
        <div className='numeros'>
          <input type="number" onChange={handleLibroChange} className="inputNew" name='numeroPaginas' placeholder='Número de páginas'/>
          <input type="number" className="inputNew" onChange={handleLibroChange} name='copias' placeholder='Número de copias'/>
          <input type="number" className="inputNew" onChange={handleLibroChange}name='edad' placeholder='Edad recomendada'/>
        </div>
        <input type="text" className="inputNew" onChange={handleLibroChange} name='isbn' placeholder='ISBN'/><br/>
        <div className='dobleCampo' >
          <input type="text" className="inputNew" onChange={handleLibroChange} name='lenguaPublicacion' placeholder='Lengua publicación'/>
          <input type="text"className="inputNew" onChange={handleLibroChange} name='lenguaTraduccion' placeholder='Lengua traducción'/>
        </div>
        <div className='dobleCampo' >
          <input type="text" className="inputNew" onChange={handleLibroChange} name='formato' placeholder='Formato'/>
          <input type="text" className="inputNew" onChange={handleLibroChange} name='genero' placeholder='Género'/> <br/>
        </div>
        <span className='inputOpcion'>Portada</span>
        <input type="file" onChange={handleFileInputChange}/><br/>
        <button type="button" className='cargar' onClick={handlePortada}>Cargar</button><br/>
        {response1? <div><p><b>Resultado:</b> {response1} </p> <br/></div>:"" }
        <span className='inputOpcion'>Imagen 2</span>
        <input type="file" onChange={handleFileInputChange2} /><br/>
        <button type="button" className='cargar' onClick={handleImagen2}>Cargar</button><br/>
        {response2? <div><p><b>Resultado:</b> {response2} </p> <br/></div>:"" }
        <span className='inputOpcion'>Imagen 3</span>
        <input type="file" onChange={handleFileInputChange3} /><br/>
        <button type="button" className='cargar' onClick={handleImagen3}>Cargar</button><br/>
        {response3? <div><p><b>Resultado:</b> {response3} </p> <br/></div>:"" }
        <span className='inputOpcion'><b>Añadir libro </b> </span><br/>
        <button type="button" className='upload' onClick={handleFormSubmit}>Añadir</button><br/>
        {response? <div><p><b>Resultado:</b> {response} </p> <br/></div>:"" }
  </div>
    </div></>
  );
};

export default UploadForm;
