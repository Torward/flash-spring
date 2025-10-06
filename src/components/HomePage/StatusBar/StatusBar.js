import React, { Component } from "react";
import { Avatar } from "@mui/material";

class StatusBar extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        return (
            <div className={`relative group`}>
                <div className="h-52 w-36 rounded-lg p-2.5 flex flex-col transform transition-transform duration-300 hover:z-10 hover:scale-105" draggable="true">
                    <img
                        src={this.props.imageURL}
                        alt="stories-thumbnail"
                        className="min-h-full min-w-full object-cover rounded-lg"
                    />
                    <div className="relative top-[-60px] mx-auto border-2 border-blue-500 rounded-full">
                        <Avatar
                            src={this.props.userAvatar}
                            sx={{
                                width: 40,
                                height: 40,
                            }}
                            className="rounded-full"
                        />
                    </div>
                </div>
            </div>
        );
    }
}

export default StatusBar;