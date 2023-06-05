import {Link} from "wouter";
import './Title.css'

export default function Title (){
    return (
        <div>
            <Link to="/" target="_blank">
            <img src="/grema.png" className="logo" alt="GREMA logo" title="Logo GREMA" />
            </Link>
             <h1>Biblioteca GREMA</h1>
        </div>
    )}