
export default function ProjectCard({id, name, description, date, imageurl, creationDate, onClickFunction}){
    return (
        <div className="card mb-3" style={{width: "100%"}} onClick={()=>{onClickFunction(id)}}>
            <div className="row g-0">
                <div className="project-card-image-container col-md-4">
                    <img src={imageurl} className= "project-card-image img-fluid rounded-start" alt="..."></img>
                </div>
                <div className="col-md-8">
                    <div className="card-body">
                        <h5 className="card-title">{name}</h5>
                        <p className="card-text" style={{height:"120px"}}>{description}</p>
                        <p className="card-text" style={{textAlign:"right"}}>{creationDate}</p>
                    </div>
                </div>
            </div>
        </div>
    )
}