import {rerenderEntireTree} from "./render";
import axios from "axios";

let state = {

    dialogsPage: {
        dialogs: [
            {
                username: 'Атос',
                id: 1,
                avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
                details: "Не влюбляйтесь, гиблое дело",
                dialogBackgroundImage: "https://playground.com/post/1930s-color--bug-eyed-monster-in-the-style-of-the-naoto-h-clra8ofrf15ros601ftlnfxki",

                location: "Paris",
                isGroup: false,
                isActive: false,
                messages: [
                    {
                        id: 1,
                        userId: 3,
                        text: 'Прииивет! Как дела?',
                        time: '10:25',
                        username: 'Арамис',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    }, {
                        id: 2,
                        userId: 3,
                        text: 'Эй! Есть кто-нибудь дома?',
                        time: '10:27',
                        username: 'Арамис',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    }, {
                        id: 3,
                        userId: 3,
                        text: 'Я спрашиваю: "Эй! Кто-нибудь дома?" ',
                        time: '10:25',
                        username: 'Арамис',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    }, {
                        id: 4,
                        userId: 1,
                        text: 'Ну.',
                        time: '10:25',
                        username: 'Атос',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    },
                ]
            },
            {
                username: 'Портос',
                id: 2,
                avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
                details: "Не влюбляйтесь, гиблое дело",
                location: "Paris",
                isGroup: false,
                isActive: false,
                messages: [
                    {
                        id: 1,
                        userId: 2,
                        text: 'Чокак?',
                        time: '10:25',
                        username: 'Арамис',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    }, {
                        id: 2,
                        userId: 4,
                        text: 'Норм, а чо?',
                        time: '10:27',
                        username: 'Арамис',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    }, {
                        id: 3,
                        userId: 2,
                        text: 'Да ничо." ',
                        time: '10:25',
                        username: 'Арамис',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    }, {
                        id: 4,
                        userId: 4,
                        text: 'Ну и ничо.',
                        time: '10:25',
                        username: 'Атос',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    },
                ]
            },
            {
                username: 'Арамис',
                id: 3,
                avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
                details: "Не влюбляйтесь, гиблое дело",
                location: "Paris",
                isGroup: false,
                isActive: true,
                messages: [
                    {
                        id: 1,
                        text: 'Прииивет! Как дела?',
                        time: '10:25',
                        username: 'Арамис',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    }, {
                        id: 2,
                        text: 'Эй! Есть кто-нибудь дома?',
                        time: '10:27',
                        username: 'Арамис',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    }, {
                        id: 3,
                        text: 'Я спрашиваю: "Эй! Кто-нибудь дома?" ',
                        time: '10:25',
                        username: 'Арамис',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    }, {
                        id: 4,
                        text: 'Ну.',
                        time: '10:25',
                        username: 'Атос',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    },
                ]
            },
            {
                username: "Д'Артаньян",
                id: 4,
                avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
                details: "Не влюбляйтесь, гиблое дело",
                location: "Paris",
                isGroup: false,
                isActive: false,
                messages: [
                    {
                        id: 1,
                        userId: 2,
                        text: 'Чокак?',
                        time: '10:25',
                        username: 'Арамис',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    }, {
                        id: 2,
                        userId: 4,
                        text: 'Норм, а чо?',
                        time: '10:27',
                        username: 'Арамис',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    }, {
                        id: 3,
                        userId: 2,
                        text: 'Да ничо." ',
                        time: '10:25',
                        username: 'Арамис',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    }, {
                        id: 4,
                        userId: 4,
                        text: 'Ну и ничо.',
                        time: '10:25',
                        username: 'Атос',
                        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
                    },
                ]
            },
        ],

        messages: [
            {
                id: 1,
                text: 'Прииивет! Как дела?',
                time: '10:25',
                username: 'Арамис',
                avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
            }, {
                id: 2,
                text: 'Эй! Есть кто-нибудь дома?',
                time: '10:27',
                username: 'Арамис',
                avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
            }, {
                id: 3,
                text: 'Я спрашиваю: "Эй! Кто-нибудь дома?" ',
                time: '10:25',
                username: 'Арамис',
                avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
            }, {
                id: 4,
                text: 'Ну.',
                time: '10:25',
                username: 'Атос',
                avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
            },
        ],
        newMessageText: '',
    },
    feedPage: {
        posts: [
            {
                postId: 1,
                username: "Портос",
                location: "Paris",
                userAvatar: "https://mon.medikforum.ru/uploads/stars/portos_/medium_c6d5ddb22642d08ce79afae12ecb8783.jpeg",
                postImageURL: "https://vsegda-pomnim.com/uploads/posts/2022-04/1651049541_52-vsegda-pomnim-com-p-shtorm-v-okeane-foto-56.jpg",
                postContent: 'Прииивет! Как дела?',
                timeStamp: "11.10.2022",
                likes: "7772",
            },
            {
                postId: 2,
                username: "Портос",
                location: "Paris",
                userAvatar: "https://mon.medikforum.ru/uploads/stars/portos_/medium_c6d5ddb22642d08ce79afae12ecb8783.jpeg",
                postImageURL: "https://iodb.ru/uploads/2020/07/les.jpg",
                postContent: '',
                timeStamp: "11.10.2022",
                likes: "7772",
            },
        ],
        statuses: [
            {
                id: 4,
                username: "Портос",
                imageURL:
                    "https://cdn.fishki.net/upload/post/2020/03/08/3250904/sh1.jpg",
                userAvatar: "https://mon.medikforum.ru/uploads/stars/portos_/medium_c6d5ddb22642d08ce79afae12ecb8783.jpeg",
            },
            {
                id: 4,
                username: "Портос",
                imageURL:
                    "https://cdn.fishki.net/upload/post/2020/03/08/3250904/sh1.jpg",
                userAvatar: "https://mon.medikforum.ru/uploads/stars/portos_/medium_c6d5ddb22642d08ce79afae12ecb8783.jpeg",
            },
            {
                id: 4,
                username: "Портос",
                imageURL:
                    "https://cdn.fishki.net/upload/post/2020/03/08/3250904/sh1.jpg",
                userAvatar: "https://mon.medikforum.ru/uploads/stars/portos_/medium_c6d5ddb22642d08ce79afae12ecb8783.jpeg",
            },
            {
                id: 4,
                username: "Портос",
                imageURL:
                    "https://sun9-41.userapi.com/s/v1/if1/aKB9A6cBXbVDJRrxeA3UPJiln_wCsAYJtY7OmphcPFphorC5P2OI4hd5m4NvU1u1yfkuCQ.jpg?size=400x400&quality=96&crop=278,42,679,679&ava=1",
                userAvatar: "https://mon.medikforum.ru/uploads/stars/portos_/medium_c6d5ddb22642d08ce79afae12ecb8783.jpeg",
            },
        ],
    },
    loginPage: {
        users: {
            userId: 1,
            username: "Атос",
            avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
            password: "123456",
        }

    },
    userPage: {
        username: "Атос",
        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92d"
    }

}
export const addPost = (postMessage) => {
    // try {
        //     event.preventDefault();
        //     event.persist();
        //     if(userInfo.content.length < 1){
        //         setError('Поле не должно оставаться пустым!');
        //         return;
        //     }
        //     axios.post(`http://localhost:8080/addPost`, {
        //         title: userInfo.title,
        //         content: userInfo.content,
        //     })
        //         .then(res => {
        //             if(res.data.success === true){
        //                 // history.push('/');
        //             }
        //         })
        // } catch (error) { throw error;}
    const newPost = {
        postId: 3,
        username: "Атос",
        location: "Paris",
        userAvatar:
            "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
        postImageURL: '',
        postContent: postMessage,
        timeStamp: "11.12.2022",
        likes: "7772",
    };
    state.feedPage.posts.push(newPost);
    rerenderEntireTree(state);
}
export const addStatus = (statusMessage) => {
    const newStatus = {
        id: 3,
        username: "Атос",
        imageURL: statusMessage,
        userAvatar: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
    };
    state.feedPage.statuses.push(newStatus);
    rerenderEntireTree(state);
}

export const addLike = (postId) => {
    state.feedPage.posts[postId].likes += 1;
    rerenderEntireTree(state);
}
export const addComment = (postId) => {
    state.feedPage.posts[postId].comments.push({
        id: 3,
        text: 'Комментарий',
        time: '10:26',
        username: 'Атос',
        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
    })

    rerenderEntireTree(state);
}
export const addUser = async (newUser) => {
    console.log(newUser);
    const apiUrl = 'http://localhost:8187/auth/signup';
    try {
    const response = await axios.post(apiUrl, {
        password: newUser.password.value,
        email: newUser.email.value,
        firstName: newUser.firstName.value,
        lastName: newUser.lastName.value,
    });
    console.log(response);
    alert("Вы успешно зарегистрированы");
    console.log("Перед редиректом");
    window.location = "/"
} catch (err) {
    console.error("Register request error");
    console.error(err);
}

    const newUserD =({
        userId: 3,
        username: "Атос",
        location: "Paris",
        userAvatar:
            "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
    })
    state.loginPage.users.push(newUserD)
    rerenderEntireTree(state);
}

export const addMessage = () => {


    let newMessage = {
        id: 5,
        text: state.dialogsPage.newMessageText,
        time: '10:26',
        username: 'Атос',
        avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg"
    }
    state.dialogsPage.messages.push(newMessage);
    state.dialogsPage.newMessageText = '';
    rerenderEntireTree(state);
}
export let updateMessageHandler = (newText) => {
    state.dialogsPage.newMessageText = newText;
    rerenderEntireTree(state);
}
export default state;