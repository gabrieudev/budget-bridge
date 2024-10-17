import styles from "./Home.module.css";
import Savings from "../../../assets/savings.png";
import LinkButton from "../../layout/LinkButton";

function Home() {
  return (
    <div>
      <section className={styles.container}>
        <h1>
          Welcome to <span>Budget Bridge</span>
        </h1>
        <p>Start managing your projects now!</p>
        <LinkButton to="/newproject" text="Create project" />
        <img src={Savings} alt="Budget Bridge" />
      </section>
    </div>
  );
}

export default Home;
