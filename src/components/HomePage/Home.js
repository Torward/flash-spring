import React, { useState } from "react";
import { Route, Routes } from "react-router-dom";
import "./Home.css";
import SideBar from "./SideNavBar/SideBar";
import MainContent from "./MainContent/MainContent";
import RightSide from "./RightSide/RightSide";
import ProfileContent from "./ProfileContent/ProfileContent";
import Dialogs from "./Dialogs/Dialogs";
import AddPostForm from "./Post/AddPost/AddPostForm";
import BookmarksContent from "./BookmarksContent/BookmarksContent";

const Home = (props) => {
    let data = [
        {
            username: "Портос",
            id: 2,
            details:
                "Вспышка — быстрое сгорание газопаровоздушной смеси над поверхностью горючего вещества, сопровождающееся кратковременным видимым свечением",
            avatarImg:
                "https://storage.googleapis.com/pai-images/1a1ba887dbdb40928296a2163fa65b35.jpeg",
            posts: 52,
            backgroundImg: '',
            followers: 772,
            following: 182,
            location: "Paris"
        },
        {
            username: 'Атос',
            id: 1,
            details: "Не влюбляйтесь, гиблое дело",
            avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
            posts: 72,
            followers: 782,
            following: 187,
            location: "Paris"
        },
        {
            username: 'Арамис',
            id: 1,
            details: "Не влюбляйтесь, гиблое дело",
            avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
            posts: 72,
            followers: 782,
            following: 187,
            location: "Paris"
        },
        {
            username: "Д'Артаньян",
            id: 1,
            details: "Не влюбляйтесь, гиблое дело",
            avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
            posts: 72,
            followers: 782,
            following: 187,
            location: "Paris"
        }
    ];

    const [profileDetailsList, setProfileDetailsList] = useState(data);
    let userData = [{
        username: "Портос",
        id: 2,
        details:
            "Вспышка — быстрое сгорание газопаровоздушной смеси над поверхностью горючего вещества, сопровождающееся кратковременным видимым свечением",
        avatarImg:
            "https://mon.medikforum.ru/uploads/stars/portos_/medium_c6d5ddb22642d08ce79afae12ecb8783.jpeg",
        posts: 52,
        followers: 772,
        following: 182,
        location: "Paris"
    }];

    return (
        <div className="flex flex-col md:flex-row">
            <div className="w-full md:w-1/6">
                <div className="home-page__navbar h-screen">
                    <SideBar
                        username={userData[0].username}
                        details={userData[0].details}
                        avatarImg={userData[0].avatarImg}
                        posts={userData[0].posts}
                        followers={userData[0].followers}
                        following={userData[0].following}
                    />
                </div>
            </div>
            <div className="w-full md:w-4/6">
                <Routes>
                    <Route index element={<MainContent statuses={props.feedState.statuses} />} />
                    <Route index path={'feed'} element={<MainContent statuses={props.feedState.statuses} />} />
                    <Route path={'bookmarks'} element={<BookmarksContent />} />
                    <Route path={'dialogs/*'} element={<Dialogs dialogs={props.dialogsState.dialogs}
                                                                messages={props.dialogsState.messages}
                                                                newMessageText={props.dialogsState.newMessageText}
                                                                addMessage={props.addMessage}
                                                                updateMessageHandler={props.updateMessageHandler}
                    />} />
                    {profileDetailsList.map((item, index) => (
                        <Route path={'profile/*'} element={<ProfileContent
                            key={index}
                            username={item.username}
                            details={item.details}
                            avatarImg={item.avatarImg}
                        />} />
                    ))}
                    <Route path={'add_post'} element={<AddPostForm
                        addPost={props.addPost}
                    />} />
                </Routes>
            </div>
            <div className="w-full md:w-1/6">
                <RightSide id={userData[0].id} avatarImg={userData[0].avatarImg} addPost={props.addPost} />
            </div>
        </div>
    );
}

export default Home;