package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.NoteShare;
import com.ruoyi.system.mapper.NoteShareMapper;
import com.ruoyi.system.service.NoteShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteShareServiceImpl implements  NoteShareService{
    @Autowired
    private NoteShareMapper noteShareMapper;

    @Override
    public int createNoteShare(NoteShare noteShare) {
        return noteShareMapper.insert(noteShare);
    }

    @Override
    public NoteShare getNoteByShareId(String shareId) {
        return noteShareMapper.selectByShareId(shareId);
    }

    @Override
    public int updateNoteShare(NoteShare noteShare) {
        return noteShareMapper.update(noteShare);
    }

    @Override
    public int deleteNoteShare(String shareId) {
        return noteShareMapper.delete(shareId);
    }
}