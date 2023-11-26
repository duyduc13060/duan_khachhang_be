package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.NoteDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoteService {
    List<NoteDto> getNoteByUser(String username);
}
