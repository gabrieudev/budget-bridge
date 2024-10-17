import styles from "./Navbar.module.css";
import { Link } from "react-router-dom";
import Logo from "../../assets/logo.png";

function Navbar() {
  return (
    <nav className={styles.navbar}>
      <Link to="/">
        <img src={Logo} alt="Budge Bridge" />
      </Link>
      <ul className={styles.list}>
        <li className={styles.item}>
          <Link to="/">Home</Link>
        </li>
        <li className={styles.item}>
          <Link to="/projects">Projects</Link>
        </li>
        <li className={styles.item}>
          <Link to="/">Company</Link>
        </li>
        <li className={styles.item}>
          <Link to="/">Contact</Link>
        </li>
      </ul>
    </nav>
  );
}

export default Navbar;
