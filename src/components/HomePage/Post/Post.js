import React, { Component } from "react";
import Grid from "@mui/material/Grid";

import { Avatar, Icon, Stack } from "@mui/material";
import "./Post.css";
// import postImage from "../../images/postfoto.jpg";

class Post extends Component {
  constructor(props) {
    super(props);

    this.state = {
      commentList: [],
    };
  }

  componentDidMount() {
    this.getComments();
  }
  // Сюда подаём Comments
  getComments = () => {
    let data = [
      {
        username: "Портос",
        commentId: "1",
        timeStamp: "11.10.2022",
        description: "Первый комментарий",
      },
      {
        username: "Атос",
        commentId: "2",
        timeStamp: "11.10.2022",
        description: "Второй комментарий",
      },
      {
        username: "Арамис",
        commentId: "3",
        timeStamp: "11.10.2022",
        description: "Третий комментарий",
      },
    ];
    this.setState({ commentList: data });
  };

  render() {
    return (
      <div>
        {" "}
        <Stack className="post-container">
          {" "}
          <div className="post-container__header">
            {" "}
            <div className="post-container__header__user-info">
              {" "}
              <Avatar
                src={this.props.userAvatar}
                sx={{
                  width: 24,
                  height: 24,
                }}
                className="post-avatar"
              />{" "}
              <div className="post-data">
                <span className="post-username">{this.props.userName}</span>
                <span className="post-location">{this.props.location}</span>
              </div>
            </div>{" "}
          </div>{" "}
          <div className="post-container__img-block">
            {" "}
            <img
              className="img-block__img"
              src={this.props.postImage}
              alt="post_image"
            ></img>{" "}
          </div>{" "}
          <div className="post-container__analitics">
            {" "}
            <Icon
              className="favorite"
              sx={{
                marginRight: 2,
              }}
            >
              favorite_border_outlined
            </Icon>{" "}
            <Icon
              className="chat"
              sx={{
                marginRight: 2,
              }}
            >
              chat_bubble_outline_outlined
            </Icon>{" "}
            <Icon
              className="share"
              sx={{
                marginRight: 1,
              }}
            >
              share_outlined
            </Icon>{" "}
            <Icon
              className="bookmark"
              sx={{
                marginRight: 2,
              }}
            >
              bookmark_border_outlined
            </Icon>{" "}
            <div className="appLikes-block">
              {" "}
              <span>{this.props.appLikes}</span>{" "}
            </div>{" "}
          </div>{" "}
          <div className="post-container__comments">
            {this.state.commentList.map((item, index) => (
              <div className="comment-payload">
                {item.username}: {item.description}
              </div>
            ))}
          </div>{" "}
          <div className="post-container__comment-input">
            {" "}
            <input
              className="comment-input__input"
              type="text"
              placeholder="Напиши чёнить..."
            ></input>{" "}
          </div>{" "}
        </Stack>{" "}
      </div>
    );
  }
}

export default Post;
