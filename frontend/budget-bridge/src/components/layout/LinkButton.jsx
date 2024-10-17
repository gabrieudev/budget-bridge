import styles from "./LinkButton.module.css";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

function LinkButton({ to, text }) {
  return (
    <Link to={to} className={styles.btn}>
      {text}
    </Link>
  );
}

LinkButton.propTypes = {
  to: PropTypes.string.isRequired,
  text: PropTypes.string,
};

export default LinkButton;
