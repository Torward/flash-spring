import React from "react";
import "./SignUp.css";
import {useFormik} from "formik";
import * as Yup from 'yup';
import {Box, FormControl, TextField} from "@mui/material";

const SignUp = (props) => {
    const yup = Yup.object().shape({
        email: Yup.string()
            .email('Пожалуйста, введите правильный адрес электронной почты')
            .required('введите адрес электронной почты'),
        firstName: Yup.string()
            .min(2, 'Имя должно содержать не менее 2 символов')
            .required('введите имя'),
        lastName: Yup.string()
            .min(2, 'Фамилия должна содержать не менее 2 символов')
            .required('введите фамилию'),
        password: Yup.string()
            .min(8, 'Пароль должен содержать не менее 8 символов')
            .required('введите пароль'),
        confirmPassword: Yup.string()
            .oneOf([Yup.ref('password'), null], 'Пароли не совпадают')
            .required('подтвердите пароль'),
    });
    const formik = useFormik({
        initialValues: {
            firstName: "",
            lastName: "",
            email: "",
            password: "",
        },
        handleSubmit: values => {
            console.log(values);
            addUserHandler(values);
        },
        validationSchema: yup,
    })

    const addUserHandler = (values) => {
        props.addUser(values);
    };

    return (
        <Box>
            <form onSubmit={formik.handleSubmit}>
                <Box className={'w-[310px] flex flex-col justify-around items-center py-1'}>
                    <Box className={'w-full relative h-[77px]'}>
                    <FormControl className={'w-full absolute top-0 left-0'} sx={{color: 'white'}}>
                        <TextField
                            label={'Адрес электронной почты'}
                            id="email"
                            name="email"
                            type="email"
                            size={'small'}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            value={formik.values.email}
                            error={formik.touched.email && formik.errors.email}
                            helperText={formik.touched.email && formik.errors.email}
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
                    <Box className={'w-full relative h-[77px]'}>
                    <FormControl className={'w-full absolute top-0 left-0'} sx={{color: 'white'}}>
                        <TextField
                            label={'Имя пользователя'}
                            id="firstName"
                            name="firstName"
                            type="firstName"
                            size={'small'}
                            sx={{
                                '& label.Mui-focused': {
                                    color: 'rgb(51, 153, 255)',
                                },
                            }}
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
                    <Box className={'w-full relative h-[77px]'}>
                    <FormControl className={'w-full absolute top-0 left-0'} sx={{color: 'white'}}>
                        <TextField
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
                    <Box className={'w-full relative h-[70px]'}>
                    <FormControl className={'w-full absolute top-0 left-0'} sx={{color: 'white'}}>
                        <TextField
                            label={'Пароль'}
                            id="password"
                            name="password"
                            type="password"
                            size={'small'}
                            sx={{
                                mt: '1rem',
                                height: '36px'
                            }}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            value={formik.values.password}
                            error={formik.touched.password && formik.errors.password}
                            helperText={formik.touched.password && formik.errors.password}
                            inputProps={{
                                style: {
                                    height: '36px',
                                    borderRadius: '5px',
                                    color: 'rgb(51, 153, 255)',
                                    backgroundColor: 'rgba(49, 104, 160, 0.377)',
                                    '& MuiFormLabel-root': {
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
                        <button className="login-page__sign-in__button" onClick={addUserHandler}>Регистрируюсь</button>
                    </FormControl>
                    </Box>
                </Box>
            </form>
        </Box>
    );
}


export default SignUp;
