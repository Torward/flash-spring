import React from 'react';
import {ComponentPreview, Previews} from '@react-buddy/ide-toolbox';
import {PaletteTree} from './palette';
import ProfileContent from "../components/HomePage/ProfileContent/ProfileContent";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
            <ComponentPreview path="/ProfileContent">
                <ProfileContent/>
            </ComponentPreview>
        </Previews>
    );
};

export default ComponentPreviews;