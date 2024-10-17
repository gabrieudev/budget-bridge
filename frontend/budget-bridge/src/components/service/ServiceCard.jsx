import PropTypes from "prop-types";
import styles from "../project/ProjectCard.module.css";
import { BsFillTrashFill } from "react-icons/bs";

function ServiceCard({ id, name, cost, description, handleRemove }) {
  const remove = (e) => {
    e.preventDefault();
    handleRemove(id);
  };

  return (
    <div className={styles.card}>
      <h4>{name}</h4>
      <p>
        <span>Cost: </span> {cost}
      </p>
      <p>{description}</p>
      <div className={styles.actions}>
        <button onClick={remove}>
          <BsFillTrashFill />
          Remove
        </button>
      </div>
    </div>
  );
}

ServiceCard.propTypes = {
  id: PropTypes.number,
  name: PropTypes.string,
  cost: PropTypes.number,
  description: PropTypes.string,
  handleRemove: PropTypes.func,
};

export default ServiceCard;
