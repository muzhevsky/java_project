
export default function ProjectCard({id, name, description, date, imageurl, onClickFunction}){
    return (
        <div className="card mb-3" style={{"maxWidth": "540px"}} onClick={()=>{onClickFunction(id)}}>
            <div className="row g-0">
                <div className="project-card-image-container col-md-4">
                    <img src={imageurl} className= "project-card-image img-fluid rounded-start" alt="..."></img>
                </div>
                <div className="col-md-8">
                    <div className="card-body">
                        <h5 className="card-title">{name}</h5>
                        <p className="card-text">{description}</p>
                        <p className="card-text"><small className="text-body-secondary">{date}</small>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}