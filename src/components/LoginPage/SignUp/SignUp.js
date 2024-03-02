import React from "react";
import "./SignUp.css";
import {useFormik} from "formik";
import * as Yup from 'yup';
import {Box, FormControl, TextField} from "@mui/material";
import {useDispatch} from "react-redux";
import {loginUser, registerUser} from "../../../store/Auth/Action";

const validationSchema = Yup.object().shape({
    email: Yup.string()
        .email('Пожалуйста, введите верный адрес электронной почты')
        .required('введите адрес электронной почты'),
    firstName: Yup.string()
        .min(2, 'Имя должно содержать не менее 2 символов')
        .required('введите имя'),
    lastName: Yup.string()
        .min(2, 'Фамилия должна содержать не менее 2 символов')
        .required('введите фамилию'),
    password: Yup.string()
        .min(8, 'Пароль должен содержать не менее 6 символов')
        .required('введите пароль'),
});
const SignUp = (props) => {
    const dispatch = useDispatch()
    const formik = useFormik({
        initialValues: {
            firstName: "",
            lastName: "",
            email: "",
            password: "",
        },
        onSubmit: values => {
            console.log(values);
            dispatch(registerUser(values))
        },
        validationSchema,
    })

    return (
        <Box>
            <form onSubmit={formik.handleSubmit}>
                <Box className={'w-[310px] flex flex-col justify-around items-center py-1'}>
                    <Box className={'relative w-full h-[70px]  flex justify-center items-center overflow-hidden  mt-2'}>
                    <FormControl className={'w-full absolute top-0 left-0'} sx={{color: 'white'}}>
                        <TextField
                            fullWidth
                            label={'Адрес электронной почты'}
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
                                left: 0,
                            }}
                            inputProps={{
                                style: {
                                    borderRadius: '5px',
                                    color: 'rgb(51, 153, 255)',
                                    backgroundColor: 'rgba(49, 104, 160, 0.377)',
                                    '&::placeholder': {
                                       color: 'rgb(109,174,234)',
                                    },
                                    '&:hover': {
                                        borderColor: 'rgba(71, 163, 255, 0.767)',
                                    },
                                    '&::helper': {
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
                    <Box className={'relative w-full h-[70px]  flex justify-center items-center overflow-hidden  mt-2'}>
                    <FormControl className={'w-full absolute top-0 left-0'} sx={{color: 'white'}}>
                        <TextField
                            fullWidth
                            label={'Имя пользователя'}
                            id="firstName"
                            name="firstName"
                            type="firstName"
                            size={'small'}
                            sx={{
                                position: 'absolute',
                                top: 5,
                                left: 0,
                                '& label.Mui-focused': {
                                    color: 'rgb(51, 153, 255)',
                                },
                            }}
                            value={formik.values.firstName}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            error={formik.touched.firstName && formik.errors.firstName}
                            helperText={formik.touched.firstName && formik.errors.firstName}
                            inputProps={{
                                style: {
                                    borderRadius: '5px',
                                    backgroundColor: 'rgba(49, 104, 160, 0.377)',
                                    color: 'rgb(51, 153, 255)',
                                    '&::placeholder': {
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
                    </FormControl>
                    </Box>
                    <Box className={'relative w-full h-[70px]  flex justify-center items-center overflow-hidden  mt-2'}>
                    <FormControl className={'w-full absolute top-0 left-0'} sx={{color: 'white'}}>
                        <TextField
                            fullWidth
                            label={'Фамилия пользователя'}
                            id="lastName"
                            name="lastName"
                            type="lastName"
                            size={'small'}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            value={formik.values.lastName}
                            error={formik.touched.lastName && formik.errors.lastName}
                            helperText={formik.touched.lastName && formik.errors.lastName}
                            sx={{
                                position: 'absolute',
                                top: 5,
                                left: 0,
                            }}
                            inputProps={{
                                style: {
                                    borderRadius: '5px',
                                    color: 'rgb(51, 153, 255)',
                                    backgroundColor: 'rgba(49, 104, 160, 0.377)',
                                    '& ::placeholder': {
                                        color: 'rgb(109,174,234)',
                                    },
                                    '& :hover': {
                                        borderColor: 'rgba(71, 163, 255, 0.767)',
                                    },
                                }
                            }}
                            InputLabelProps={{
                                style: {
                                    '& MuiFormControl': {
                                        position: 'absolute',
                                },
                                    color: 'rgb(109,174,234)',
                                }
                            }}
                        />
                    </FormControl>
                    </Box>
                    <Box className={'relative w-full h-[70px]  flex justify-center items-center overflow-hidden  mt-2'}>
                    <FormControl className={'w-full absolute top-0 left-0'} sx={{color: 'white'}}>
                        <TextField
                            fullWidth
                            label={'Пароль'}
                            id="password"
                            name="password"
                            type="password"
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
                            error={formik.touched.password && formik.errors.password}
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
                    </FormControl>
                    </Box>
                    <button type={'submit'} className="login-page__sign-in__button">Регистрируюсь</button>
                </Box>
            </form>
        </Box>
    );
}


export default SignUp;
