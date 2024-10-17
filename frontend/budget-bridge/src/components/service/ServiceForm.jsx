import styles from "../project/ProjectForm.module.css";
import Input from "../form/Input";
import SubmitButton from "../form/SubmitButton";
import { useState } from "react";
import PropTypes from "prop-types";

function ServiceForm({ handleSubmit, textBtn, projectData }) {
  const [service, setService] = useState({});

  function submit(e) {
    e.preventDefault();
    projectData.services.push(service);
    handleSubmit(projectData);
  }

  function handleChange(e) {
    setService({ ...service, [e.target.name]: e.target.value });
  }

  return (
    <form className={styles.form} onSubmit={submit}>
      <Input
        type="text"
        text="Service name"
        name="name"
        placeholder="Service name"
        handleOnChange={handleChange}
      />
      <Input
        type="number"
        text="Service cost"
        name="cost"
        placeholder="Service cost"
        handleOnChange={handleChange}
      />
      <Input
        type="text"
        text="Service description"
        name="description"
        placeholder="Service description"
        handleOnChange={handleChange}
      />
      <SubmitButton text={textBtn} />
    </form>
  );
}

ServiceForm.propTypes = {
  textBtn: PropTypes.string,
  handleSubmit: PropTypes.func,
  projectData: PropTypes.object,
};

export default ServiceForm;
