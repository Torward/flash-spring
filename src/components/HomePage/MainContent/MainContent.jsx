import React, {useEffect, useMemo, useState} from "react";
import { Icon, Stack } from "@mui/material";
import StatusBar from "../StatusBar/StatusBar";
import Post from "../Post/Post";
import AddPostForm from "../Post/AddPost/AddPostForm";
import { useDispatch, useSelector } from "react-redux";
import { getAllPosts } from "../../../store/Post/Action";


const MainContent = ({ statuses, addPost }) => {
    const dispatch = useDispatch();
    const [modalActive, setModalActive] = useState(false);
    const { post,loading, error } = useSelector((state) => state);
    console.log(post);
    const postComponents = useMemo(() => post.posts.map(p => <Post
        key={p.postId}
        post={p}
    />), [post.posts]);

    const statusComponents = useMemo(() => statuses.map(s => (
        <div key={s.postId} className="relative group animate-stack-cards" style={{ animation: 'stackCards 1s forwards' }}>
            <StatusBar
                id={s.postId}
                userName={s.username}
                imageURL={s.imageURL}
                userAvatar={s.userAvatar}
            />
        </div>
    )), [statuses]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                dispatch(getAllPosts());
            } catch (err) {
                console.error('Ошибка при получении постов:', err);
            }
        };
        fetchData()
    }, [post.like, post.bookmark, post.repost, post.bookmarkedPosts]);
    const openWindow = () => setModalActive(true);
    if (loading) {
        return <div>Загрузка...</div>;
    }
    if (error) {
        return <div>Ошибка: {error.message}</div>;
    }
    return (
        <div className="bg-transparent min-h-screen">
            <div className="mt-2 flex flex-col w-full items-center xl:mt-20 lg:mt-20">
                <input className="xl:w-[92.5%] md:h-10 sm:h-10 md:w-[92.5%] sm:w-[92.5%] md:ml-0 md:mr-0 sm:ml-0 sm:mr-0 ml-12 mr-7 opacity-100 outline-none
                transition duration-700 ease-in-out
                bg-custom-gradient rounded-lg border
                 border-secondary h-8 pl-2 placeholder-blue-400"
                       type="text"
                       placeholder="Ищи здесь..."
                />
            </div>
            <Stack container>
                <div >
                    <div className="relative mt-7 ml-7 text-2xl font-semibold text-blue-600 md:ml-10 lg:ml-12">
                        <span>ИСТОРИИ</span>
                    </div>
                    <div className='relative bg-custom-gradient mt-7 mr-7 ml-7 xl:w-[92.5%] h-[210px] flex justify-center items-center rounded-[10px] md:mr-10 md:ml-10 lg:mr-12 lg:ml-12 '>
                        {statusComponents}
                    </div>
                </div>
                <div className={'cursor-pointer flex justify-between text-white mt-7 ml-7 mr-7 pb-1 border-b border-white md:ml-10 md:mr-10 lg:ml-12 lg:mr-12'} onClick={openWindow}>
                    <span>Поделись впечатлениями</span>
                    <Icon>add</Icon>
                </div>
                <div className="mt-7 md:ml-10 md:mr-10 lg:ml-12 lg:mr-12">
                    {postComponents}
                </div>
            </Stack>
            <AddPostForm active={modalActive} setActive={setModalActive} addPost={addPost} />
        </div>
    );
}

export default MainContent;