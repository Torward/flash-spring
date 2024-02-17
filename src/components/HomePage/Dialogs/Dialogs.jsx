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

    const dialogComponents = props.dialogs.map(dialog => <ItemDialog
        key={dialog.id}
        username={dialog.username}
        id={dialog.id}
        avatarImg={dialog.avatarImg}
        details={dialog.details}
        location={dialog.location}
        isActive={dialog.isActive}
    />)
    const messageComponents = props.messages.map(message => <Message
        key={message.id}
        id={message.id}
        text={message.text}
        time={message.time}
        username={message.username}
        avatarImg={message.avatarImg}
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

        if (!textAreaRef.current.value.onChange) {
            props.addMessage();
        }
        // setModalActive(false)

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