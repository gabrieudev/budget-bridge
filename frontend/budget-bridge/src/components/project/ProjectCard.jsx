import styles from "./ProjectCard.module.css";
import PropTypes from "prop-types";
import { BsPencil, BsFillTrashFill } from "react-icons/bs";
import { Link } from "react-router-dom";

function ProjectCard({ id, name, budget, cost, category, handleRemove }) {
  const remove = (e) => {
    e.preventDefault();
    handleRemove(id);
  };

  return (
    <div className={styles.card}>
      <h4>{name}</h4>
      <p>
        <span>Budget: </span>
        {budget}
      </p>
      <p>
        <span>Current cost: </span>
        {cost}
      </p>
      <p className={styles.category}>
        <span className={`${styles[category.toLowerCase()]}`}></span>
        {category}
      </p>
      <div className={styles.actions}>
        <Link className={styles.edit} to={`/projects/${id}`}>
          <BsPencil /> Edit
        </Link>
        <button className={styles.remove} onClick={remove}>
          <BsFillTrashFill /> Remove
        </button>
      </div>
    </div>
  );
}

ProjectCard.propTypes = {
  id: PropTypes.number.isRequired,
  name: PropTypes.string.isRequired,
  cost: PropTypes.number.isRequired,
  budget: PropTypes.number.isRequired,
  category: PropTypes.string.isRequired,
  handleRemove: PropTypes.func.isRequired,
};

export default ProjectCard;
