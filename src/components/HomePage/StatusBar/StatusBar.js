import React, { Component } from "react";
import { Avatar } from "@mui/material";
import "./StatusBar.css";

class StatusBar extends Component {
  constructor(props) {
    super(props);

    this.state = {

    };
  }

  render() {
    return (
      <div>
        <div className="statusbar-container">
            <div className="statusbar-container__stories-thumbnail" draggable="true">

              <img src={this.props.imageURL} alt="stories-thumbnail"></img>{" "}
              <div className="statusbar-container__avatar">
                <Avatar
                  src={this.props.userAvatar}
                  sx={{
                    width: 40,
                    height: 40,
                    // position: "relative",
                    // left: 55,
                    // top: -50,
                  }}
                />
              </div>
            </div>
        </div>
      </div>
    );
  }
}

export default StatusBar;
