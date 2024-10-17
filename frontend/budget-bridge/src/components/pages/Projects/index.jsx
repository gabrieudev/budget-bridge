import Message from "../../layout/Message";
import styles from "./Projects.module.css";
import LinkButton from "../../layout/LinkButton";
import ProjectCard from "../../project/ProjectCard";
import { useState, useEffect } from "react";
import api from "../../../services/api";
import Loading from "../../layout/Loading";

function Projects() {
  const [removeLoading, setRemoveLoading] = useState(false);
  const [projects, setProjects] = useState([]);
  const [messageType, setMessageType] = useState("");
  const [message, setMessage] = useState("");

  async function getProjects() {
    let projectsFromApi = await api.get("/projects");
    setProjects(projectsFromApi.data);
  }

  useEffect(() => {
    getProjects();
    setRemoveLoading(true);
  }, []);

  async function removeProject(id) {
    try {
      await api.delete(`/projects/${id}`);
      getProjects();
      setMessageType("success");
      setMessage("Project removed successfully");
    } catch (error) {
      setMessageType("error");
      setMessage(error.response?.data?.detail || "Unknown error");
    }
  }

  return (
    <div className={styles.container}>
      <div className={styles.container__title}>
        <h1>My projects</h1>
        <LinkButton to="/newproject" text="Create project" />
      </div>
      {message && messageType && (
        <Message type={messageType} message={message} />
      )}
      <div className={styles.projects}>
        {projects.length > 0 &&
          projects.map((project) => {
            return (
              <ProjectCard
                key={project.id}
                id={project.id}
                name={project.name}
                budget={project.budget}
                cost={project.cost}
                category={project.category.name}
                handleRemove={removeProject}
              />
            );
          })}
        {!removeLoading && <Loading />}
        {removeLoading && projects.length === 0 && (
          <p className={styles.no__project}>You have no project...</p>
        )}
      </div>
    </div>
  );
}

export default Projects;
