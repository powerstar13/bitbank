import HashLoader from "react-spinners/HashLoader";
import { css } from "@emotion/react";

const override = css`
  display: block;
  margin: 20px auto;
  padding: 20px auto;
  border-color: "4A90E2";

`;

const Loader = ({loading}) => {

    return (
        <div>
            <HashLoader color="#4A90E2" loading={loading} css={override} size={70}  />
        </div>
    );
}

export default Loader;