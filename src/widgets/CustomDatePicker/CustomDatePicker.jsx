import React from 'react';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import TextField from '@mui/material/TextField';

const CustomDatePicker = ({ value, onChange }) => {
    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker
                value={value}
                onChange={onChange}
                renderInput={(params) => (
                    <TextField
                        {...params}
                        color="primary"
                        size="small"
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
                    />
                )}
            />
        </LocalizationProvider>
    );
};

export default CustomDatePicker;