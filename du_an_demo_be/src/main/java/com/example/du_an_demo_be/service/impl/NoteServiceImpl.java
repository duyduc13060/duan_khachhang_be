package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.model.dto.NoteDto;
import com.example.du_an_demo_be.model.entity.NoteEntity;
import com.example.du_an_demo_be.repository.NoteRepository;
import com.example.du_an_demo_be.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final ModelMapper modelMapper;

//    todo: hàm này định dùng để làm phân trang nhưng chưa cần
//    @Override
//    public Page<NoteDto> getNoteByUser(Long userId){
//        Page<NoteEntity> pages = this.noteRepository.getNoteByUser(userId);
//
//        Page<NoteDto> dtoPage =
//                pages.map(noteEntity -> modelMapper.map(noteEntity,NoteDto.class));
//        return dtoPage;
//    }

    @Override
    public List<NoteDto> getNoteByUser(String username) {
        // todo:Lấy ra danh sách các note theo user đăng nhập
        List<NoteEntity> noteEntityList = this.noteRepository.getNoteByUsername(username);

        // todo:Chuyển đổi dữ liệu entity dang dto để view lên giao diện cho người dùng
        List<NoteDto> noteDtoList = noteEntityList
                .stream()
                .map(e -> modelMapper.map(e, NoteDto.class))
                .collect(Collectors.toList());
        return noteDtoList;
    }


}
