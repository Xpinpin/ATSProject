/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.Date;

/**
 *
 * @author Nguyen Pham
 */
public class JobComment extends Base implements IJobComment {

    private Integer CommentId;
    private String Comment;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Date DeletedAt;
    private int JobId;

//<editor-fold defaultstate="collapsed" desc="Constructors">
    public JobComment(String Comment, int JobId) {
        this.Comment = Comment;
        this.JobId = JobId;
    }

    public JobComment(String Comment, Date CreatedAt, int JobId) {
        this.Comment = Comment;
        this.CreatedAt = CreatedAt;
        this.JobId = JobId;
    }

    public JobComment() {
    }

    
    public JobComment(String Comment) {
        setComment(Comment) ;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Properties">
    public Integer getCommentId() {
        return CommentId;
    }

    public void setCommentId(Integer CommentId) {
        this.CommentId = CommentId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        if (Comment.trim() == null || Comment.trim().equals("")) {
            addError(ErrorFactory.createInstance(1, "Comment  is required."));
        } else {
             this.Comment = Comment;
        }
    
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date UpdatedAt) {
        this.UpdatedAt = UpdatedAt;
    }

    public Date getDeletedAt() {
        return DeletedAt;
    }

    public void setDeletedAt(Date DeletedAt) {
        this.DeletedAt = DeletedAt;
    }

    public int getJobId() {
        return JobId;
    }

    public void setJobId(int JobId) {
        this.JobId = JobId;
    }
//</editor-fold>

}
