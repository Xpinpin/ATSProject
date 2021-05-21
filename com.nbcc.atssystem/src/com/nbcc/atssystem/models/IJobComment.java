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
public interface IJobComment  extends IBase {
    public Integer getCommentId();    
    public void setCommentId(Integer CommentId);
    
    public String getComment();
    
    public void setComment(String Comment);
    
    public Date getCreatedAt();
    
    public void setCreatedAt(Date CreatedAt);
    
    public Date getUpdatedAt();
    
    public void setUpdatedAt(Date UpdatedAt);
    
    public Date getDeletedAt();
    
    public void setDeletedAt(Date DeletedAt);
    
    public int getJobId();
    
    public void setJobId(int JobId);
}
