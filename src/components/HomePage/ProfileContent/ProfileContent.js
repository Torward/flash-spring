import React from "react";
import { FormControl, Grid, MenuItem, Select, Stack } from "@mui/material";
import style from "./ProfileContent.module.css";
import CustomDatePicker from "../../../widgets/CustomDatePicker/CustomDatePicker";
import dayjs from 'dayjs';

const ProfileContent = (props) => {
    const [isChanged, setIsChanged] = React.useState(false);
    const [gender, setGender] = React.useState("мужской");
    const [birthDate, setBirthDate] = React.useState(dayjs('01-01-1995')); // Установите начальную дату

    const changeValueState = () => setIsChanged(!isChanged);
    const handleGenderChange = (event) => {
        setGender(event.target.value);
    };

    const handleDateChange = (date) => {
        setBirthDate(date);
    };

    return (
        <div className={'h-full'}>
            <Stack className={style.profile_content}>
                <Grid container spacing={2} className={'pl-5'}>
                    <Grid item xs={12}>
                        <div className={style.cover}>
                            <img
                                src={'https://ip1.anime-pictures.net/direct-images/332/332d0c3637b3383fa5d74f3197f13e8a.jpg?if=ANIME-PICTURES.NET_-_693005-8840x4940-ghost+blade-yan+%28ghost+blade%29-wlop-single-fringe-short+hair.jpg'}
                                alt={'cover'}
                            />
                        </div>
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <div className={style.person}>
                            <div className={style.person_info}>
                                <div id={'age'} className={style.section} onClick={changeValueState}>
                                    <span className={style.header}>ВОЗРАСТ</span>
                                    {!isChanged ? (
                                        <span className={style.value}>28</span>
                                    ) : (
                                        <CustomDatePicker value={birthDate} onChange={handleDateChange} />
                                    )}
                                </div>
                                <div id={'sex'} className={style.section} onClick={changeValueState}>
                                    <span className={style.header}>ПОЛ</span>
                                    {!isChanged ? (
                                        <span className={style.value}>{gender}</span>
                                    ) : (
                                        <FormControl variant="outlined" size="small">
                                            <Select
                                                labelId="gender-label"
                                                value={gender}
                                                onChange={handleGenderChange}
                                                color={'primary'}
                                                sx={{
                                                    '& .MuiOutlinedInput-notchedOutline': {
                                                        borderColor: 'rgb(0, 200, 255)',
                                                    },
                                                    '&:hover .MuiOutlinedInput-notchedOutline': {
                                                        borderColor: 'rgb(46, 108, 173)',
                                                    },
                                                    '& .MuiInputBase-input': {
                                                        color: 'rgb(0, 200, 255)',
                                                    },
                                                    '& .MuiSvgIcon-root': {
                                                        color: 'rgb(0, 200, 255)',
                                                    },
                                                }}
                                            >
                                                <MenuItem value={"мужской"}>Мужской</MenuItem>
                                                <MenuItem value={"женский"}>Женский</MenuItem>
                                            </Select>
                                        </FormControl>
                                    )}
                                </div>
                                <div id={'location'} className={style.section}>
                                    <span className={style.header}>МЕСТО НАХОЖДЕНИЯ</span>
                                    <span className={style.value}>Париж</span>
                                </div>
                                <div id={'full-name'} className={style.section}>
                                    <span className={style.header}>ПОЛНОЕ ИМЯ</span>
                                    <span className={style.value}>
                                        Исаак дю Валлон де Брасье де Пьерфон
                                    </span>
                                </div>
                                <div id={'social-status'} className={style.section}>
                                    <span className={style.header}>СОЦИАЛЬНЫЙ СТАТУС</span>
                                    <span className={style.value}>Вдовец</span>
                                </div>
                                <div id={'person-status'} className={style.section}>
                                    <span className={style.header}>ВАЖНОЕ В СЛОВАХ</span>
                                    <span className={style.value}>{props.details}</span>
                                </div>
                                <div id={'person-work'} className={style.section}>
                                    <span className={style.header}>РАБОТА</span>
                                    <span className={style.value}>Барон</span>
                                </div>
                            </div>
                        </div>
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <div className={style.interests}>
                            <div className={style.interests_info}>
                                <div id={'interests-hobbies'} className={style.section}>
                                    <span className={style.header}>ХОББИ</span>
                                    <span className={style.value}>Убивать гвардейцев Кардинала</span>
                                </div>
                                <div id={'interests-musics'} className={style.section}>
                                    <span className={style.header}>МУЗЫКА</span>
                                    <span className={style.value}>Браво</span>
                                </div>
                                <div id={'interests-movies'} className={style.section}>
                                    <span className={style.header}>ФИЛЬМЫ</span>
                                    <span className={style.value}>Чародеи</span>
                                </div>
                                <div id={'interests-games'} className={style.section}>
                                    <span className={style.header}>ИГРЫ</span>
                                    <span className={style.value}>Салочки</span>
                                </div>
                                <div id={'interests-books'} className={style.section}>
                                    <span className={style.header}>КНИГИ</span>
                                    <span className={style.value}>Понедельник начинается в субботу</span>
                                </div>
                            </div>
                        </div>
                    </Grid>
                </Grid>
            </Stack>
        </div>
    );
}

export default ProfileContent;