package com.firatkaya.model.projection;

import com.firatkaya.entity.Comment;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "commentExceptr", types = Comment.class)
public interface CommentDetailExcept {

    String getComment_Id();

    String getUser_name();

    String getComment_Message();

    String getComment_Time();

    String getUser_Profil_Photo();

    boolean getIs_Updated();

    String getComment_Updated_Time();

    boolean getIs_Hide();
}
