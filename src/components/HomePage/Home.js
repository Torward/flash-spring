import React, {Component} from "react";
import {Route, Routes} from "react-router-dom";
import Grid from "@mui/material/Grid";
import "./Home.css";
import SideBar from "./SideNavBar/SideBar";
import MainContent from "./MainContent/MainContent";
import RightSide from "./RightSide/RightSide";
import ProfileContent from "./ProfileContent/ProfileContent";
import Dialogs from "./Dialogs/Dialogs";
import AddPost from "./Post/AddPost/AddPost";


class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            profileDetailsList: [],
        };
    }

    componentDidMount() {
        this.getProfileDetails();
    }

    // Сюда подаём profileDetails
    getProfileDetails = () => {
        let data = [
            {
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
        this.setState({profileDetailsList: data});
    };

    render() {
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
        // let dialogsComponent = this.state.profileDetailsList.map(item => <Dialogs
        //         username={item.username}
        //         id={item.id}
        //         avatarImg={item.avatarImg}
        //         details={item.details}
        //         location={item.location}
        //     />

        // );
        return (

            <Grid container>
                <Grid item xs={2}>
                    <div className="home-page__navbar">

                            <SideBar
                                username={userData[0].username}
                                details={userData[0].details}
                                avatarImg={userData[0].avatarImg}
                                posts={userData[0].posts}
                                followers={userData[0].followers}
                                following={userData[0].following}
                            />

                    </div>
                </Grid>
                <Grid item xs={8}>
                    <Routes>
                        <Route index path={'feed'} element={<MainContent posts={this.props.feedState.posts}
                                    statuses={this.props.feedState.statuses}
                        />}/>

                         <Route path={'dialogs/*'} element={<Dialogs  dialogs={this.props.dialogsState.dialogs}  messages={this.props.dialogsState.messages}/>}/>


                        {this.state.profileDetailsList.map((item, index) => (
                            <Route path={'profile'} element={<ProfileContent
                                username={item.username}
                                details={item.details}
                                avatarImg={item.avatarImg}
                            />}/>
                        ))}

                        <Route path={'add_post'} element={<AddPost />}/>

                    </Routes>
                </Grid>
                <Grid item xs={2}>

                        <RightSide id={userData[0].id}  avatarImg={userData[0].avatarImg}/>

                </Grid>
            </Grid>
        );
    }
}

export default Home;
