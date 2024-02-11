import React from "react";
import {Avatar, Box, Typography} from "@mui/material";
import style from "./Message.module.css";

const Message = (props) => {
    return (
        <Box>
            <Box className={'flex flex-col w-full'}
                 sx={{
                     padding: '10px 20px',
                     border: '1px solid rgba(255, 255, 255, .25)',
                     borderRadius: '20px',
                     boxShadow: '0 0 10px 1px rgba(0, 0, 0, 0.25)',
                     backdropFilter: 'blur(15px)',
                     backgroundColor: 'rgba(255, 255, 255, 0.45)'
                 }}>
                <div className={'w-full'}>

                    <p className="mb-0">
                        <Typography color={'black'} >{props.text}</Typography>
                    </p>
                    <Box className={'w-full'} sx={{textAlign: 'right'}}>
                        <Typography fontSize={'13px'} color={'#545454'} variant={'subtitle1'}>{props.username}</Typography>
                    </Box>
                    <p className={style.chat_time}>
                        <Typography fontSize={'13px'} color={'#545454'}>{props.time}</Typography>
                    </p>
                </div>
            </Box>
        </Box>
    )

}
export default Message