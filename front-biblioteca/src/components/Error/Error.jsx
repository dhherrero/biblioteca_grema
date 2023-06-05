
import './Error.css'

export default function Error(props){
    
    return(
        <div className="error">
            <span>{props.content}</span>
        </div>
    )

}