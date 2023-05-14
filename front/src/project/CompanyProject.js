import ProjectData from "./ProjectData";
import Button from "../common/inputs/Button";

export default function CompanyProject(){
    const takeToEvaluation = () => {
    }
    return (
        <>
            <ProjectData/>
            <Button text="Evaluate" onClickFunction={takeToEvaluation}></Button>
        </>
    )
}