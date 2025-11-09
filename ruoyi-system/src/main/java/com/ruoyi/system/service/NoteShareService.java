package com.ruoyi.system.service;

import com.ruoyi.system.domain.NoteShare;

import java.util.List;

public interface NoteShareService {

    public int  createNoteShare(NoteShare noteShare);
    public NoteShare getNoteByShareId(String shareId);
    public int updateNoteShare(NoteShare noteShare);
    public int deleteNoteShare(String shareId);
}
