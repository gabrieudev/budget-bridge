import styles from "./Footer.module.css";
import { FaGithub, FaLinkedin } from "react-icons/fa";

function Footer() {
  return (
    <footer className={styles.footer}>
      <ul className={styles.list}>
        <li>
          <a
            href="https://www.linkedin.com/in/gabrieudev/"
            target="_blank"
            rel="external"
          >
            <FaLinkedin />
          </a>
        </li>
        <li>
          <a
            href="https://github.com/gabrieudev"
            target="_blank"
            rel="external"
          >
            <FaGithub />
          </a>
        </li>
      </ul>
      <p>
        <span>Budget Bridge</span> &copy; 2024
      </p>
    </footer>
  );
}

export default Footer;
