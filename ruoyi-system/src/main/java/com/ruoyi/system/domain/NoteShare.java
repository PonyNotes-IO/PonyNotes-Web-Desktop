package com.ruoyi.system.domain;

import java.io.Serializable;
import java.util.Date;

public class NoteShare implements Serializable {
    private static final long serialVersionUID = 1L;

    private String shareId;
    private String noteId;
    private String noteVersion;
    private String authorId;
    private Date shareTime;
    private Date expireTime;
    private String accessType; // 'public', 'password', 'private'
    private String accessPassword;
    private String shareToken;
    private Boolean isActive;
    private Integer accessCount;

    // getter and setter methods
    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteVersion() {
        return noteVersion;
    }

    public void setNoteVersion(String noteVersion) {
        this.noteVersion = noteVersion;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getAccessPassword() {
        return accessPassword;
    }

    public void setAccessPassword(String accessPassword) {
        this.accessPassword = accessPassword;
    }

    public String getShareToken() {
        return shareToken;
    }

    public void setShareToken(String shareToken) {
        this.shareToken = shareToken;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }

    @Override
    public String toString() {
        return "NoteShare{" +
                "shareId='" + shareId + '\'' +
                ", noteId='" + noteId + '\'' +
                ", noteVersion='" + noteVersion + '\'' +
                ", authorId='" + authorId + '\'' +
                ", shareTime=" + shareTime +
                ", expireTime=" + expireTime +
                ", accessType='" + accessType + '\'' +
                ", isActive=" + isActive +
                ", accessCount=" + accessCount +
                '}';
    }
}
