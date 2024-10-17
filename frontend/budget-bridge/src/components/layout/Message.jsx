import styles from "./Message.module.css";
import PropTypes from "prop-types";
import { useState, useEffect } from "react";

function Message({ type, message }) {
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    if (!message) {
      setVisible(false);
      return;
    }
    setVisible(true);

    const timer = setTimeout(() => {
      setVisible(false);
    }, 3000);

    return () => clearTimeout(timer);
  }, [message]);

  return (
    <>
      {visible && (
        <div className={`${styles.message} ${styles[type]}`}>{message}</div>
      )}
    </>
  );
}

Message.propTypes = {
  type: PropTypes.string.isRequired,
  message: PropTypes.string.isRequired,
};

export default Message;
