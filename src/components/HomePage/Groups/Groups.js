import React, { Component } from "react";
import { Avatar, Icon, Stack } from "@mui/material";
import "./Groups.css";

class Groups extends Component {
  constructor(props) {
    super(props);

    this.state = {
      groupsList: [],
    };
  }

  componentDidMount() {
    this.getGroups();
  }

  getGroups = () => {
    let data = [
      {
        username: "70 ПСЧ",
        avatarImg:
          "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
        details: "Погасим",
        location: "Врангель",
      },
      {
        username: "Атос",
        avatarImg:
          "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
        details: "Не влюбляйтесь, гиблое дело",
        location: "Paris",
      },
      {
        username: "Атос",
        avatarImg:
          "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
        details: "Не влюбляйтесь, гиблое дело",
        location: "Paris",
      },
      {
        username: "Атос",
        avatarImg:
          "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
        details: "Не влюбляйтесь, гиблое дело",
        location: "Paris",
      },
    ];
    this.setState({
      groupsList: data,
    });
  };
  render() {
    return (
      <div>
        {this.state.groupsList.map((item, index) => (
          <div className="groups-body__element">
            <Avatar
              className="element__img"
              src={item.avatarImg}
              sx={{
                width: 36,
                height: 36,
              }}
            />
            <div className="element__info">
              <span className="element__info__username">{item.username}</span>
              <span className="element__info__details">{item.details}</span>
              <span className="element__info__location">{item.location}</span>
            </div>
          </div>
        ))}
      </div>
    );
  }
}

export default Groups;
