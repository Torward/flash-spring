package ru.lomov.flashbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
@Builder(toBuilder = true)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
    private String content;
    private String image;
    private String video;
    private String audio;
    private boolean isReply;
    private boolean isPost;
    private boolean isLiked;
    private boolean isReposted;
    private boolean isBookmarked;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppLike> likes = new ArrayList<>();

    @OneToMany
    @JoinTable(
            name = "post_shares",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "share_id"))
    private List<Share> shares = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany
    private List<Post> replyPosts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "post_repost",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<AppUser> repostAppUser = new ArrayList<>();

    @ManyToOne
    private Post replyFor;
}