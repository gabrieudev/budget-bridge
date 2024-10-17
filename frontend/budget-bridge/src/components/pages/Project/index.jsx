import { useParams } from "react-router-dom";
import styles from "./Project.module.css";
import { useState, useEffect } from "react";
import api from "../../../services/api";
import Loading from "../../layout/Loading";
import ProjectForm from "../../project/ProjectForm";
import Message from "../../layout/Message";
import ServiceForm from "../../service/ServiceForm";
import ServiceCard from "../../service/ServiceCard";

function Project() {
  const { id } = useParams();
  const [project, setProject] = useState({});
  const [services, setServices] = useState([]);
  const [showProjectForm, setShowProjectForm] = useState(false);
  const [showServiceForm, setShowServiceForm] = useState(false);
  const [message, setMessage] = useState("");
  const [messageType, setMessageType] = useState("");

  async function getProject(projectId) {
    let projectFromApi = await api.get(`/projects/${projectId}`);
    setProject(projectFromApi.data);
    setServices(projectFromApi.data.services);
  }

  useEffect(() => {
    getProject(id);
  }, [id]);

  function toggleProjectForm() {
    setShowProjectForm(!showProjectForm);
  }

  function toggleServiceForm() {
    setShowServiceForm(!showServiceForm);
  }

  async function editProject(project) {
    setMessage("");

    try {
      await api.put("/projects", project);
      setMessageType("success");
      setMessage("Projet edited successfully");
      setShowProjectForm(false);
      getProject(project.id);
    } catch (error) {
      setMessageType("error");
      setMessage(error.response?.data?.detail || "Unknown error");
    }
  }

  async function createService(project) {
    setMessage("");

    try {
      await api.post("/services", {
        ...project.services[project.services.length - 1],
        project: { id: project.id },
      });
      setShowServiceForm(false);
      setMessageType("success");
      setMessage("Service created successfully");
      getProject(project.id);
    } catch (error) {
      setMessageType("error");
      setMessage(error.response?.data?.detail || "Unknown error");
    }
  }

  async function removeService(serviceId) {
    try {
      await api.delete(`/services/${serviceId}`);
      getProject(project.id);
      setMessageType("success");
      setMessage("Service deleted successfully");
      getProject(project.id);
    } catch (error) {
      setMessageType("error");
      setMessage(error.response?.data?.detail || "Unknown error");
    }
  }

  return (
    <>
      {project.name ? (
        <div>
          <div className={styles.container}>
            {message && <Message type={messageType} message={message} />}
            <h1>{project.name}</h1>
            <button className={styles.btn} onClick={toggleProjectForm}>
              {!showProjectForm ? "Edit project" : "Close"}
            </button>
            {!showProjectForm ? (
              <div className={styles.info}>
                <p>
                  <span>Category:</span> {project.category.name}
                </p>
                <p>
                  <span>Total budget:</span> {project.budget}
                </p>
                <p>
                  <span>Total used:</span> {project.cost}
                </p>
              </div>
            ) : (
              <div className={styles.info}>
                <ProjectForm
                  btnText="Edit"
                  handleSubmit={editProject}
                  projectData={project}
                />
              </div>
            )}
          </div>
          <div className={styles.service__form__container}>
            <h2>Add service:</h2>
            <button className={styles.btn} onClick={toggleServiceForm}>
              {!showServiceForm ? "Add service" : "Close"}
            </button>
            <div className={styles.info}>
              {showServiceForm && (
                <ServiceForm
                  handleSubmit={createService}
                  textBtn="Add service"
                  projectData={project}
                />
              )}
            </div>
          </div>
          <div className={styles.service__form__container}>
            <h2>Services</h2>
            <div className={styles.service__items}>
              {services.length > 0 &&
                services.map((service) => (
                  <ServiceCard
                    key={service.id}
                    id={service.id}
                    name={service.name}
                    cost={service.cost}
                    description={service.description}
                    handleRemove={removeService}
                  />
                ))}
              {services.length === 0 && <p>You have no services...</p>}
            </div>
          </div>
        </div>
      ) : (
        <Loading />
      )}
    </>
  );
}

export default Project;
