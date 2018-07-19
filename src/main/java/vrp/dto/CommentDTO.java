package vrp.dto;

import java.io.Serializable;
import java.util.Date;

public class CommentDTO implements Serializable {

    private final String comment;
    private final String userName;
    private final Date commentDate;

    public CommentDTO( final String comment
                     , final String userName
                     , final Date commentDate) {
        this.comment = comment;
        this.userName = userName;
        this.commentDate = commentDate;
    }

    public String getComment() {
        return comment;
    }

    public String getUserName() {
        return userName;
    }

    public Date getCommentDate() {
        return commentDate;
    }
}
