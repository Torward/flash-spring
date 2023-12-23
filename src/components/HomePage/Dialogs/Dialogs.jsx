import React, {useEffect, useRef, useState} from 'react'
import style from './Dialogs.module.css'
import ItemDialog from "./ItemDialog/ItemDialog";
import {NavLink} from "react-router-dom";
import Message from "./Message/Message";
import {IconButton, TextareaAutosize} from "@mui/material";
import {Send} from "@mui/icons-material";
import Draggable from "react-draggable";


const Dialogs = (props) => {
    const [open, setOpen] = useState(false);
    let [modalActive, setModalActive] = useState(false);

    let dialogComponents = props.dialogs.map(d => <ItemDialog username={d.username}
                                                              id={d.id}
                                                              avatarImg={d.avatarImg}
                                                              details={d.details}
                                                              location={d.location}
    />)
    let messageComponents = props.messages.map(m => <Message id={m.id}
                                                             text={m.text}
                                                             time={m.time}
                                                             username={m.username}
                                                             avatarImg={m.avatarImg}
    />)

    let containerRef = useRef();
    let textAreaRef = React.createRef()

    let openTextArea = () => {
        setOpen(!open);
        setModalActive(!modalActive);

    }

    let closeTextArea = () => {
        setOpen(open);
        setModalActive(!modalActive);
    }

    let addMessage = () => {
        if (!textAreaRef.current.value.onChange){
            props.addMessage();
        }

    }
    let onMassageChange = () => {
        let text = textAreaRef.current.value;
        props.updateMessageHandler(text);
    }

    useEffect(() => {
        let handler = (event) => {
            if (!containerRef.current.contains(event.target)) {
                setOpen(false);
                setModalActive(false);
            }
        }
        document.addEventListener("mousedown", handler);
        return () => {
            document.removeEventListener("mousedown", handler);
        }
    });

    return (
        <div className={style.background}>
            <div className={style.container}>
                <div className={style.dialogs}>
                    <div className={style.content_dialogs}>
                        {dialogComponents}
                    </div>
                </div>

                <div className={style.messages}>
                    <div className={style.messages_content__user}>
                        {messageComponents}
                    </div>
                </div>
            </div>
            <Draggable>
                <div className={style.input_container} ref={containerRef}>
                    <TextareaAutosize className={`${style.input_field}  ${open ? style.active : style.inactive}`}
                                      name="message"
                                      ref={textAreaRef}
                                      onChange={onMassageChange}
                                      maxRows={4}
                                      value={props.newMessageText}
                                      placeholder={'Начни писать...'} required/>
                    <IconButton type={'submit'} aria-label="send message"
                                onClick={modalActive ? addMessage : openTextArea}
                                onDrag={closeTextArea}
                                onDragLeave={closeTextArea}
                                onDrop={closeTextArea}
                                onDragEnd={closeTextArea}
                                onDragExit={closeTextArea}
                    >
                        <Send fontSize={"large"} fill={'success'} className={style.send_button}/>
                    </IconButton>
                </div>
            </Draggable>

        </div>
    )

}
export default Dialogs