export default function ProjectData({projectData}){

    return (
        <div className="container">
            <div className="row my-5">

                <div className="col-md-6">
                    <img src={"/images/"+projectData.imageFileName} alt="Project Image" className="project-image img-fluid"></img>
                </div>

                <div className="col-md-6">
                    <h2>{projectData.name}</h2>
                    <h4>short description</h4>
                    <p>{projectData.shortDescription}</p>
                </div>

                <div className="col-md-12 my-5">
                    <h4>full description</h4>
                    <p>{projectData.description}</p>
                </div>
            </div>
        </div>
    )
}