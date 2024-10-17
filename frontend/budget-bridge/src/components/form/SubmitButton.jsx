import styles from "./SubmitButton.module.css";
import PropTypes from "prop-types";

function SubmitButton({ text }) {
  return (
    <div className={styles.form__control}>
      <button className={styles.btn}>{text}</button>
    </div>
  );
}

SubmitButton.propTypes = {
  text: PropTypes.string.isRequired,
};

export default SubmitButton;
