import React from 'react';
import {List, Avatar, ListItem, ListItemAvatar, ListItemText, Typography} from "@mui/material";


export const CommentList = ({ comments }) => {
    return (
        <List>
            {comments.map((comment) => (
                <ListItem key={comment.id}>
                    <ListItemAvatar>
                        <Avatar alt={comment.author} src={comment.avatarUrl} />
                    </ListItemAvatar>
                    <ListItemText primary={comment.content} secondary={<Typography variant="caption">{comment.date}</Typography>} />
                    <CommentList comments={comment.replies} />
                </ListItem>
            ))}
        </List>
    );
};