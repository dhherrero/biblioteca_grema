import { Link } from "wouter";
import "./Not-found.css"

export default function NotFound (){
    return(
        <div>
            <Link to="/"><img src="/404.png" className="errorIcon" /></Link>
        </div>
    )
}