import React from 'react';
import { Typography, Paper, Box } from '@mui/material';

const Overview = () => {
  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Overview
      </Typography>
      <Paper sx={{ p: 2 }}>
        <Typography>
          This is the overview page of the admin dashboard. Here you can add statistics, charts, and summaries.
        </Typography>
      </Paper>
    </Box>
  );
};

export default Overview;
