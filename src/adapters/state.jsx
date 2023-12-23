import {rerenderEntireTree} from "./render";

let state = {
    dialogsPage: {
        dialogs: [
            {
                username: 'Атос',
                id: 1,
                avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
                details: "Не влюбляйтесь, гиблое дело",
                location: "Paris",
                isGroup: false
            },
            {
                username: 'Портос',
                id: 2,
                avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
                details: "Не влюбляйтесь, гиблое дело",
                location: "Paris",
                isGroup: false
            },
            {
                username: 'Арамис',
                id: 3,
                avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
                details: "Не влюбляйтесь, гиблое дело",
                location: "Paris",
                isGroup: false
            },
            {
                username: "Д'Артаньян",
                id: 4,
                avatarImg: "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
                details: "Не влюбляйтесь, гиблое дело",
                location: "Paris",
                isGroup: false
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
                userAvatar:
                    "https://mon.medikforum.ru/uploads/stars/portos_/medium_c6d5ddb22642d08ce79afae12ecb8783.jpeg",
                postImageURL:
                    "https://vsegda-pomnim.com/uploads/posts/2022-04/1651049541_52-vsegda-pomnim-com-p-shtorm-v-okeane-foto-56.jpg",
                timeStamp: "11.10.2022",
                likes: "7772",
            },
            {
                postId: 2,
                username: "Портос",
                location: "Paris",
                userAvatar:
                    "https://mon.medikforum.ru/uploads/stars/portos_/medium_c6d5ddb22642d08ce79afae12ecb8783.jpeg",
                postImageURL:
                    "https://iodb.ru/uploads/2020/07/les.jpg",
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
}
export let addPost = (postMessage) => {
    let newPost = {
        postId: 3,
        username: "Атос",
        location: "Paris",
        userAvatar:
            "https://mon.medikforum.ru/uploads/stars/atos_/medium_cd8efa6879b92dab9f473bf24fd35fcc.jpeg",
        postImageURL: postMessage,
        timeStamp: "11.12.2022",
        likes: "7772",
    };
    state.feedPage.posts.push(newPost);
    rerenderEntireTree(state);
}
export let addMessage = () => {
    debugger;
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