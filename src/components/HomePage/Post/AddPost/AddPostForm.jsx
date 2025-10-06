import React, {useState} from "react";
import "./AddPost.css"
import "react-quill/dist/quill.snow.css";
import {Box, Button, FormControl, TextField, Typography} from "@mui/material";
import * as Yup from "yup";
import {Field, useFormik} from "formik";
import {useDispatch} from "react-redux";
import {addPost} from "../../../../store/Post/Action";
import {EmojiEmotions, Image, LocationOn, MusicNote, PlayCircle, VideoFile, VideoLabel} from "@mui/icons-material";

const validationSchema = Yup.object().shape({
    content: Yup.string().required('Содержание обязательно'),
})

function AddPostForm({active, setActive}) {

    const [uploadingImage, setUploadingImage] = useState(false);
    const [selectedImage, setSelectedImage] = useState("");

    const [uploadingVideo, setUploadingVideo] = useState(false);
    const [selectedVideo, setSelectedVideo] = useState("");

    const [uploadingMusic, setUploadingMusic] = useState(false);
    const [selectedMusic, setSelectedMusic] = useState("");

    const [input, setInput] = useState('');
    const [speed, setSpeed] = useState(0);
    const [startTime, setStartTime] = useState(null)

    const dispatch = useDispatch();

    const handleSelectImage = (e) => {
        e.preventDefault();
        setUploadingImage(true);
        const imgUrl = e.target.files[0];
        formik.setFieldValue("image", imgUrl);
        setSelectedImage(imgUrl);
        setUploadingImage(false);
    };
    function handleSelectVideo(e) {
        e.preventDefault();
        setUploadingVideo(true);
        const vidUrl = e.target.files[0];
        formik.setFieldValue("video", vidUrl);
        setSelectedVideo(vidUrl);
        setUploadingVideo(false);
    }
    const handleSelectMusic = (e) => {
        e.preventDefault();
        setUploadingMusic(true);
        const musicUrl = e.target.files[0];
        formik.setFieldValue("music", musicUrl);
        setSelectedMusic(musicUrl);
        setUploadingMusic(false);
    }

    const formik = useFormik({
        initialValues: {
            content: '',
            image: '',
            video: '',
            music: '',
        },
        validationSchema: validationSchema,
        onSubmit: (values) => {
            console.log("Значения поста",values);
            dispatch(addPost(values))
            setActive(false);
            formik.resetForm();
        },
    })
    const onChangeStatistic = (e) => {
        if (e.target.value.length === 1) {
            setStartTime(new Date());
        } else if (e.target.value.length > 1) {
            const currentTime = new Date();
            const timeDiff = (currentTime - startTime) / 1000;// Разница во времени в секундах
            const currentSpeed = (input.length / 5) / (timeDiff / 60); // Скорость ввода в символа в секунду
            setSpeed(currentSpeed.toFixed(2));
        }
        setInput(e.target.value);
    }

    return (
        <Box sx={{maxHeight: '100vh', width: '100vw', overflow: 'scroll'}} className={active ? 'modal active' : 'modal'}
             onClick={() => setActive(false)}>
            <div className={active ? 'modal__content active' : 'modal__content'}
                 onClick={event => event.stopPropagation()}>
                <div className={'row'}>
                    <form onSubmit={formik.handleSubmit} className={'update__forms'}>
                        <h3 className={'my_account__content'}> Создай свои впечатления </h3>
                        <div className={'form_row'}>
                            <Box className={'relative w-full flex justify-center items-center  my-2'}>
                                <FormControl className={'w-full absolute top-0 left-0 flex justify-start items-start'}
                                             sx={{color: 'white'}}>
                                    <TextField
                                        fullWidth
                                        multiline
                                        label={'Содержание поста'}
                                        id="content"
                                        name="content"
                                        type={"text"}
                                        placeholder={"Напиши что-нибудь..."}
                                        value={formik.values.content}
                                        onChange={formik.handleChange}
                                        onBlur={formik.handleBlur}
                                        error={formik.touched.content && Boolean(formik.errors.content)}
                                        helperText={formik.touched.content && formik.errors.content}
                                        maxRows={8}
                                        sx={{
                                            position: 'absolute',
                                            top: 5,
                                            left: 0,
                                        }}
                                        inputProps={{
                                            style: {
                                                height: '170px',
                                                borderRadius: '5px',
                                                color: 'rgb(51, 153, 255)',
                                                '&::placeholder': {
                                                    color: 'rgb(109,174,234)',
                                                },
                                                '&:hover': {
                                                    borderColor: 'rgba(71, 163, 255, 0.767)',
                                                },
                                                '& ::helper': {
                                                    fontSize: '12px',
                                                }
                                            },
                                        }}
                                        InputLabelProps={{
                                            style: {
                                                color: 'rgb(109,174,234)',
                                            }
                                        }}
                                    />
                                </FormControl>
                            </Box>

                            <Box className="w-full" sx={{
                                display: 'flex',
                                justifyContent: 'end',
                                alignItems: 'center',
                            }}>
                                <Box className={'relative w-full flex justify-start items-center  my-2'}>
                                    <label className={'flex justify-center items-center cursor-pointer'}>
                                        <Image sx={{
                                            color: 'rgb(128, 191, 255)',
                                            cursor: 'pointer',
                                            '&:hover': {
                                                color: 'rgb(220,234,243)',
                                            }
                                        }}/>
                                        <input type="file"
                                               name="imageUrl"
                                               className={'image_input'}
                                               onChange={handleSelectImage}
                                        />
                                    </label>
                                    <label className={'flex justify-center items-center cursor-pointer'}>
                                        <PlayCircle sx={{
                                            color: 'rgb(128, 191, 255)',
                                            cursor: 'pointer',
                                            '&:hover': {
                                                color: 'rgb(220,234,243)',
                                            }
                                        }}/>
                                        <input type="file"
                                               name="videoUrl"
                                               className={'image_input'}
                                               onChange={handleSelectVideo}
                                        />
                                    </label>
                                    <label className={'flex justify-center items-center cursor-pointer'}>
                                        <MusicNote sx={{
                                            color: 'rgb(128, 191, 255)',
                                            cursor: 'pointer',
                                            '&:hover': {
                                                color: 'rgb(220,234,243)',
                                            }
                                        }}/>
                                        <input type="file"
                                               name="musicUrl"
                                               className={'image_input'}
                                               onChange={handleSelectMusic}
                                        />
                                    </label>
                                    <label className={'flex justify-center items-center cursor-pointer'}>
                                        <LocationOn sx={{
                                            color: 'rgb(128, 191, 255)',
                                            cursor: 'pointer',
                                            '&:hover': {
                                                color: 'rgb(220,234,243)',
                                            }
                                        }}/>
                                        {/*location*/}
                                    </label>
                                    <label className={'flex justify-center items-center cursor-pointer'}>
                                        <EmojiEmotions sx={{
                                            color: 'rgb(128, 191, 255)',
                                            cursor: 'pointer',
                                            '&:hover': {
                                                color: 'rgb(220,234,243)',
                                            }
                                        }}/>
                                        {/*emojis*/}
                                    </label>
                                </Box>
                                <Button type="submit"
                                        variant="outlined"
                                        sx={{
                                            color: 'rgb(128, 191, 255)',
                                            '&.MuiButton-root': {
                                                minWidth: '150px',
                                            }
                                        }}> <Typography
                                    sx={{
                                        letterSpacing: '1px',
                                    }} fontSize={14} fontWeight={600}>Опубликовать</Typography> </Button>
                            </Box>
                        </div>
                    </form>
                    {/*<p>Скорость ввода: {speed} символов/мин</p>*/}
                    {/*<p>Количество введённых символов: {input.length}</p>*/}
                </div>
            </div>
        </Box>
    );
}

export default AddPostForm;