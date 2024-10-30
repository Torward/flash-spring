import React, {useState} from "react";
import "./SignIn.css";
import {useFormik} from "formik";
import * as Yup from "yup";
import {Box, TextField} from "@mui/material";
import {useDispatch} from "react-redux";
import {loginUser} from "../../../store/Auth/Action";
import {Visibility, VisibilityOff} from "@mui/icons-material";

const validationSchema = Yup.object({
    email: Yup.string()
        .email('Неверный формат почты')
        .required('Почта обязательна'),
    password: Yup.string()
        .min(6, 'Пароль должен быть не менее 6 символов')
        .required('Пароль обязателен'),
})
const SignIn = (props) => {
    const dispatch = useDispatch()
    const [visibility, setVisibility] = useState(false)
    const formik = useFormik({
        initialValues: {
            email: '',
            password: '',
        },
        validationSchema,
        onSubmit: (values) => {
            dispatch(loginUser(values))
            console.log("попытка войти: ", values);
        }
    })

    return (
        <form onSubmit={formik.handleSubmit}>
        <div className={'flex flex-col justify-space-around'}>
            <Box className={'relative w-full h-[70px] flex justify-center items-center overflow-hidden mt-2'}>
                <TextField label={'Адрес электронной почты'}
                           fullWidth
                           id="email"
                           name="email"
                           type="email"
                           size={'small'}
                           onChange={formik.handleChange}
                           onBlur={formik.handleBlur}
                           value={formik.values.email}
                           error={formik.touched.email && Boolean(formik.errors.email)}
                           helperText={formik.touched.email && formik.errors.email}
                           sx={{
                               position: 'absolute',
                               top: 5,
                               left: 0
                           }}
                           inputProps={{
                               style: {
                                   borderRadius: '5px',
                                   color: 'rgb(51, 153, 255)',
                                   backgroundColor: 'rgba(49, 104, 160, 0.377)',
                                   '&::placeholder': {color: 'rgb(109,174,234)',},
                                   '&:hover': {borderColor: 'rgba(71, 163, 255, 0.767)',},
                                   '& ::helper': {fontSize: '12px',}
                               },
                           }}
                           InputLabelProps={{style: {color: 'rgb(109,174,234)',}}}
                />
            </Box>
            <Box className={'relative w-full h-[70px]  flex justify-center items-center overflow-hidden  mt-2'}>
            <TextField label={'Пароль'}
                       fullWidth
                       id="password"
                       name="password"
                       type={visibility?'text':'password'}
                       size={'small'}
                       sx={{
                           position: 'absolute',
                           top: 5,
                           left: 0,
                           // mt: '1rem',
                           height: '40px'
                       }}
                       onChange={formik.handleChange}
                       onBlur={formik.handleBlur}
                       value={formik.values.password}
                       error={formik.touched.password && Boolean(formik.errors.password)}
                       helperText={formik.touched.password && formik.errors.password}
                       inputProps={{
                           style: {
                               height: '40px',
                               borderRadius: '5px',
                               color: 'rgb(51, 153, 255)',
                               backgroundColor: 'rgba(49, 104, 160, 0.377)',
                               '& MuiFormLabel': {
                                   color: 'rgb(109,174,234)',
                               },
                               '&:hover': {
                                   borderColor: 'rgba(71, 163, 255, 0.767)',
                               },
                           }
                       }}
                       InputLabelProps={{
                           style: {
                               color: 'rgb(109,174,234)',
                           }
                       }}
            />
                <Box className={'z-10 absolute h-[24px] w-[24px] right-2 top-3'}>
                    {visibility?
                        <Visibility className={'text-gray-600'} onClick={() => setVisibility(false)}/>
                        :
                        <VisibilityOff className={'text-gray-600'} onClick={() => setVisibility(true)}/>
                    }
                </Box>

            </Box>
            <button type={'submit'} className="login-page__sign-in__button">Входи</button>
        </div>
        </form>
    );
}

export default SignIn;