package ru.lomov.flashbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isReply;
    private boolean isComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "like_id", insertable = false, updatable = false)
    private List<AppLike> likes = new ArrayList<>();

    @OneToMany
    private List<Comment> replyComments = new ArrayList<>();

    @ManyToOne
    private Comment replyFor;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT")
    private String media;


    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;

}
