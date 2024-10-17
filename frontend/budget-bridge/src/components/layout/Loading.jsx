import styles from "./Loading.module.css";
import LoadingSvg from "../../assets/loading.svg";

function Loading() {
  return (
    <div className={styles.container}>
      <img className={styles.loader} src={LoadingSvg} alt="Loading" />
    </div>
  );
}

export default Loading;
