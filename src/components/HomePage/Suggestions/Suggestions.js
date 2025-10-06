import React, { Component } from "react";
import { Avatar, Icon, Stack } from "@mui/material";
import "./Suggestions.css";

class Suggestions extends Component {
  constructor(props) {
    super(props);

    this.state = {
      suggestionList: [],
    };
  }

  componentDidMount() {
    this.getSuggestion();
  }

  getSuggestion = () => {
    let data = [
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
      {
        username: "Атос",
        avatarImg:
          "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
        details: "Не влюбляйтесь, гиблое дело",
        location: "Paris",
      },
    ];
    this.setState({
      suggestionList: data,
    });
  };

  render() {
    return (
      <div>
        {this.state.suggestionList.map((item, index) => (
          <div className="suggestion-body__element">
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

export default Suggestions;
