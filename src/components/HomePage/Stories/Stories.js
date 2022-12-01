import React, {Component} from "react";
import {Avatar, Icon, Stack} from "@mui/material";
import AddCircleIcon from '@mui/icons-material/AddCircle';
import "./Stories.css";

class Stories extends Component {
    constructor(props) {
        super(props);

        this.state = {
            albumList: [],
        };
    }

    componentDidMount() {
        this.getAlbum();
    }

    getAlbum = () => {
        let data = [
            {
                imageURL:
                    "https://cdn.fishki.net/upload/post/2020/03/08/3250904/sh1.jpg",
            },
            {
                imageURL:
                    "https://kartinkin.net/pics/uploads/posts/2022-08/thumbs/1659718567_40-kartinkin-net-p-tenistii-les-priroda-krasivo-foto-43.jpg",
            },
            {
                imageURL:
                    "https://sun9-41.userapi.com/s/v1/if1/aKB9A6cBXbVDJRrxeA3UPJiln_wCsAYJtY7OmphcPFphorC5P2OI4hd5m4NvU1u1yfkuCQ.jpg?size=400x400&quality=96&crop=278,42,679,679&ava=1",
            },
        ];
        this.setState({
            albumList: data,
        });
    };
    render() {
        return (

            <div className="albums-container">
                <AddCircleIcon sx={{width: 60, height: 60}}>add_circle</AddCircleIcon>
                {this.state.albumList.map((item, index) => (
                <div className="albums-container__payload">
                    <Avatar src={item.imageURL}  sx={{width: 50, height: 50}}/>
                </div>
                ))}
            </div>

        );
    }
}

export default Stories;
