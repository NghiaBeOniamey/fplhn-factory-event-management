import FormEvent from "./OREDEventInformationForm/Form";
import CommentSection from "./OREDComment/Comment";
import "../OREventDetail/index.css";
import { useParams } from "react-router-dom";

const OREventDetail = () => {
    // const { id } = useParams();
    return (
        <>
            <div className="div-container">
                <FormEvent />
            </div>
            <div className="div-container">
                <CommentSection/>
            </div>
        </>
    );
};

export default OREventDetail;
