import React from 'react'
import style from './Dialogs.module.css'
import ItemDialog from "./ItemDialog/ItemDialog";
import {NavLink} from "react-router-dom";
import Message from "./Message/Message";

const Dialogs = (props) => {

    let dialogComponents = props.dialogs.map(d => <ItemDialog username={d.username}
                                                         id={d.id}
                                                         avatarImg={d.avatarImg}
                                                         details={d.details}
                                                         location={d.location}
    />)
    let messageComponents = props.messages.map(m =>  <Message id={m.id}
                                                        text={m.text}
                                                        time={m.time}
                                                        username={m.username}
                                                        avatarImg={m.avatarImg}
    />)

    return (
        <div className={style.background}>
            <div className={style.container}>
                <div className={style.dialogs}>
                    <div className={style.content_dialogs}>
                        {dialogComponents}
                    </div>
                </div>

                <div className={style.messages}>
                    <div className={style.messages_content}>
                        {messageComponents}
                    </div>
                </div>
                <div>

                </div>
            </div>
        </div>
    )

}
export default Dialogs