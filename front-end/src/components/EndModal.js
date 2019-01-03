import React from "react";
import Modal from "@material-ui/core/Modal";
import { range } from "../util";

export const EndModal = ({ isOpen, onClose, onRate }) => (
  <Modal open={isOpen} onClose={onClose} center>
    <React.Fragment>
      <h2>Thank you for playing this game.</h2>
      <h3>Please rate it!</h3>

      <div className="score-container">
        {range(1, 5).map(i => (
          <button
            type="button"
            onClick={() => onRate(i)}
            className="score-item"
            key={i}
          >
            {i}
          </button>
        ))}
      </div>
    </React.Fragment>
  </Modal>
);
