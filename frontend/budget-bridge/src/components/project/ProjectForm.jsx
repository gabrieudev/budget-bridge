import styles from "./ProjectForm.module.css";
import Input from "../form/Input";
import Select from "../form/Select";
import SubmitButton from "../form/SubmitButton";
import PropTypes from "prop-types";
import { useState, useEffect } from "react";
import api from "../../services/api";

function ProjectForm({ btnText, projectData, handleSubmit }) {
  const [categories, setCategories] = useState([]);

  const [formData, setFormData] = useState(
    projectData || {
      name: "",
      budget: 0,
      category: {},
      cost: 0,
      services: [],
    }
  );

  useEffect(() => {
    async function getCategories() {
      let categoriesFromApi = await api.get("/categories");
      setCategories(categoriesFromApi.data);
    }

    getCategories();
  }, []);

  const handleOnChange = (field, value) => {
    setFormData((prevState) => ({
      ...prevState,
      [field]: value,
    }));
  };

  const handleNameChange = (e) => {
    handleOnChange("name", e.target.value);
  };

  const handleBudgetChange = (e) => {
    handleOnChange("budget", Number(e.target.value));
  };

  const handleCategoryChange = (e) => {
    const selectedId = Number(e.target.value);
    const selectedOption = categories.find(
      (category) => category.id === selectedId
    );

    if (selectedOption) {
      handleOnChange("category", {
        id: selectedOption.id,
        name: selectedOption.name,
      });
    }
  };

  const submit = (e) => {
    e.preventDefault();
    handleSubmit(formData);
  };

  return (
    <form onSubmit={submit} className={styles.form}>
      <Input
        type="text"
        text="Project name"
        name="name"
        placeholder="Project name"
        handleOnChange={handleNameChange}
        value={formData.name || ""}
      />
      <Input
        type="number"
        text="Total budget"
        name="budget"
        placeholder="Total budget"
        handleOnChange={handleBudgetChange}
        value={formData.budget || ""}
      />
      <Select
        name="category_id"
        text="Select a category"
        options={categories}
        handleOnChange={handleCategoryChange}
        value={formData.category ? formData.category.id : ""}
      />
      <SubmitButton text={btnText} />
    </form>
  );
}

ProjectForm.propTypes = {
  btnText: PropTypes.string,
  projectData: PropTypes.object,
  handleSubmit: PropTypes.func,
};

export default ProjectForm;
