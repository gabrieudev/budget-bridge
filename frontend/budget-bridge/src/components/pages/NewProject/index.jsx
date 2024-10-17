import styles from "./NewProject.module.css";
import ProjectForm from "../../project/ProjectForm";
import api from "../../../services/api";
import { useNavigate } from "react-router-dom";

function NewProject() {
  const navigate = useNavigate();

  async function createProject(project) {
    try {
      await api.post("/projects", project);
      navigate("/projects");
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <div className={styles.container}>
      <h1>Create project</h1>
      <p>Create your project to add the services</p>
      <ProjectForm btnText="Create project" handleSubmit={createProject} />
    </div>
  );
}

export default NewProject;
