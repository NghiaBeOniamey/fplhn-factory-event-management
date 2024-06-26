import React, {useEffect, useState} from "react";
import {Comment} from "@ant-design/compatible";
import {Avatar, Button, Divider, Form, Input, message, Popconfirm} from "antd";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCaretDown, faCommentDots, faPaperPlane, faX,} from "@fortawesome/free-solid-svg-icons";
import AvatarDefault from "../../../assets/img/avatar_default.png";
import {PAHomeApi} from "../PAHomeApi";
import {DEFAULT_COMMENT_AVATAR} from "../../../constants/EventProperties";
import {getCurrentUser} from "../../../utils/Common";
import {dispatch} from "../../../app/store";
import {SetLoadingFalse, SetLoadingTrue} from "../../../app/reducer/Loading.reducer";

const CommentSection = ({eventId}) => {
    const [replyContent, setReplyContent] = useState("");
    const [repliesVisible, setRepliesVisible] = useState({});
    const [visibleCount, setVisibleCount] = useState(0); // số lượng comment được hiển thị ban đầu
    const [comments, setComments] = useState([]);
    const [comment, setComment] = useState("");
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [currentUser, setCurrentUser] = useState({});

    useEffect(() => {
        PAHomeApi.getCommentByIdEvent(eventId, currentPage)
        .then(
            (res) => {
               //  //console.log(res.data);
                if (res.data.data.length !== 0) {
                    setTotalPages(res.data.data[0].totalPages);
                    setCurrentPage(res.data.data[0].currentPage);
                    setComments([...res.data.data, ...comments]);
                }
            }
        ).catch((err) => {
            message.warning(err.response.data.message);
        });
    }, [currentPage]);

    useEffect(() => {
        setCurrentUser(getCurrentUser());
    }, [])

    const handleShowMore = () => {
        setVisibleCount(visibleCount + 2);
        setCurrentPage((prevCount) => prevCount + 1); // Tăng số lượng comment hiển thị lên 2
    };

    const handleToggleReply = (commentIndex) => {
        setRepliesVisible((prevState) => ({
            ...prevState,
            [commentIndex]: !prevState[commentIndex],
        }));
    };

    const handleChange = (e) => {
        setReplyContent(e.target.value);
    };

    const handleSend = (idComment, commentIndex) => {
        if (replyContent === null || replyContent === "") {
            message.error("Bình luận không được để trống!");
            return;
        }
        if (replyContent.length >= 1000) {
            message.error("Bạn đã nhập quá giới hạn số lượng từ");
            return;
        }

        PAHomeApi.postReplyComment({
            eventId: eventId,
            comment: replyContent,
            replyId: idComment,
        }).then(
            (res) => {
                const updatedComments = [...comments];
                updatedComments[commentIndex].listReply.push(res.data.data);
                setComments(updatedComments);
                setComment("");
                setReplyContent("");
            }
        ).catch((err) => {
            message.warning(err.response.data.message);
        });
        setReplyContent("");
        handleToggleReply(commentIndex);
    };

    const handleDelete = (commentID) => {
        PAHomeApi.deleteComment({
            commentId: commentID,
        })
            .then((res) => {
                let id = commentID;
                comments.forEach((item) => {
                    if (item.id === id) {
                        let updatedComments = comments.filter(
                            (record) => record.id !== id
                        );
                        setComments(updatedComments);
                        message.success("Xóa thành công");
                    }
                });
            })
            .catch((err) => {
                message.warning(err.response.data.message);
            });
    };

    const handleDeleteReply = (commentIndex, replyIndex, commentID) => {
        PAHomeApi.deleteComment({
            commentId: commentID,
        })
            .then((res) => {
                let id = commentID;
                comments[commentIndex].listReply.forEach((item) => {
                    if (item.id === id) {
                        const updatedComments = [...comments];
                        updatedComments[commentIndex].listReply.splice(replyIndex, 1);
                        setComments(updatedComments);
                        message.success("Xóa thành công");
                    }
                });
            })
            .catch((err) => {
                message.warning(err.response.data.message);
            });
    };

    const handlePostComment = () => {
        if (comment === null || comment.trim().length === 0) {
            message.error("Bình luận không được để trống!");
            return;
        }
        if (comment.length >= 1000) {
            message.error("Bạn đã nhập quá giới hạn số lượng từ");
            return;
        }

        PAHomeApi.postComment({
            eventId: eventId,
            comment: comment,
        }).then((res) => {
            setComments([...comments, res.data.data]);
            setComment("");
        }).catch((err) => {
            message.warning(err.response.data.message);
        });
    };

    const actionsComment = (commentIndex, commentID, userId) => [
        <span
            className="btn-reply-delete"
            key="reply"
            onClick={() => handleToggleReply(commentIndex)}
        >
            Trả lời
        </span>,
        currentUser.id === userId && <Popconfirm
            title="Xác nhận xóa!"
            description="Bạn có chắc muốn xóa comment này không?"
            onConfirm={() => handleDelete(commentID)}
            okText="Yes"
            cancelText="No"
            getPopupContainer={(triggerNode) => triggerNode.parentNode}
        >
            <span
                key="delete"
                className="btn-reply-delete"
            >
                Xóa
            </span>
        </Popconfirm>

    ];

    const actionsReplyComment = (index, indexReply, commentID, userId) => [
        currentUser.id === userId && <Popconfirm
            title="Xác nhận xóa!"
            description="Bạn có chắc muốn xóa comment này không?"
            onConfirm={() => handleDeleteReply(index, indexReply, commentID)}
            okText="Yes"
            cancelText="No"
            getPopupContainer={(triggerNode) => triggerNode.parentNode}
        >
            <span
                key="delete"
                className="btn-reply-delete"
            >
                Xóa
            </span>
        </Popconfirm>,
    ];

    return (
        <>
            {comments.length > 0 && totalPages - 1 !== currentPage && (
                <Button onClick={handleShowMore} className="btn-see-more" style={{width: "20%"}}>
                    <span style={{ marginRight: "7px" }}>Xem thêm bình luận</span>
                    <FontAwesomeIcon icon={faCaretDown} style={{ color: "#0098d1" }} />
                </Button>
            )}
            <Divider />
            {comments.map((comment, index) => (
                <Comment
                    key={index}
                    avatar={
                        comment.avatar === DEFAULT_COMMENT_AVATAR ? (
                            <Avatar src={AvatarDefault} />
                        ) : (
                            <Avatar src={comment.avatar} />
                        )
                    }
                    author={<strong>{comment.name}</strong>}
                    datetime={comment.lastModifiedDate}
                    content={comment.comment}
                    actions={actionsComment(index, comment.id, comment.userId)}
                    style={{
                        background: "rgba(0, 0, 0, 0)",
                        width: "85%",
                    }}
                >
                    {repliesVisible[index] && (
                        <Form>
                            <Form.Item>
                                <Input.TextArea
                                    placeholder="Nhập nội dung trả lời..."
                                    autoSize={{ minRows: 2, maxRows: 6 }}
                                    value={replyContent}
                                    onChange={handleChange}
                                />
                            </Form.Item>
                            <Form.Item>
                                <Button
                                    type="primary"
                                    onClick={() => handleSend(comment.id, index)}
                                    style={{
                                        float: "right",
                                        backgroundColor: "#0098d1",
                                    }}
                                >
                                    <span style={{ marginRight: "7px" }}>Trả lời</span>
                                    <FontAwesomeIcon
                                        icon={faPaperPlane}
                                        style={{ color: "#ffffff" }}
                                    />
                                </Button>
                                <Button
                                    type="primary"
                                    danger
                                    onClick={() => handleToggleReply(index)}
                                    style={{
                                        float: "right",
                                        marginRight: "10px",
                                    }}
                                >
                                    <span style={{ marginRight: "7px" }}>Đóng</span>
                                    <FontAwesomeIcon
                                        icon={faX}
                                        style={{ color: "#ffffff" }}
                                    />
                                </Button>
                            </Form.Item>
                        </Form>
                    )}
                    {comment.listReply.map((reply, replyIndex) => (
                        <Comment
                            key={replyIndex}
                            avatar={
                                reply.avatar === DEFAULT_COMMENT_AVATAR ? (
                                    <Avatar src={AvatarDefault} alt={"Avatar"} />
                                ) : (
                                    <Avatar src={reply.avatar} alt={"Avatar"} />
                                )
                            }
                            author={<strong>{reply.name}</strong>}
                            datetime={reply.lastModifiedDate}
                            content={reply.comment}
                            actions={currentUser.id === reply.userId ? actionsReplyComment(index, replyIndex, reply.id, reply.userId) : undefined}
                            style={{
                                background: "rgba(0, 0, 0, 0)",
                                width: "85%",
                            }}
                        />
                    ))}
                </Comment>
            ))}
            <Divider />
            <div>
                <h3 className="title-comment">
                    <b>
                        <FontAwesomeIcon
                            icon={faCommentDots}
                            style={{ color: "#0563bb" }}
                        />
                        <span style={{ marginLeft: "7px", color: "#0563bb" }}>BÌNH LUẬN</span>
                    </b>
                </h3>
            </div>
            <div style={{ width: "100%" }}>
                <Input.TextArea
                    rows={3}
                    value={comment}
                    onChange={(e) => {
                        setComment(e.target.value);
                    }}
                    placeholder="Nhập nội dung bình luận..."
                />

                <Button
                    className="btn-post-comment"
                    style={{marginBottom: "0px", marginTop: '5px'}}
                    type="primary"
                    onClick={() => handlePostComment()}
                >
                    <span style={{ marginRight: "7px" }}>Bình luận</span>
                    <FontAwesomeIcon icon={faPaperPlane} style={{ color: "#ffffff" }} />
                </Button>
            </div>
        </>
    );
};

export default CommentSection;
