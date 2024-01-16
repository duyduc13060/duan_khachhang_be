package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.model.dto.ClickLikeDto;
import com.example.du_an_demo_be.model.entity.ClickLikeEntity;
import com.example.du_an_demo_be.payload.response.ServiceResult;
import com.example.du_an_demo_be.repository.ClickLikeRepository;
import com.example.du_an_demo_be.security.CustomerDetailService;
import com.example.du_an_demo_be.service.ClickService;
import com.example.du_an_demo_be.until.CurrentUserUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClickLikeServiceImpl implements ClickService {

    private final ClickLikeRepository clickLikeRepository;
    private final ModelMapper modelMapper;

//    public List<ClickLikeDto> listClickLikeWithPrompt(){
//
//    }


    @Override
    public ServiceResult<ClickLikeDto> clickLike(ClickLikeDto clickLikeDto){
        CustomerDetailService customer = CurrentUserUtils.getCurrentUserUtils();

        Optional<ClickLikeEntity> findByUsernameLike = this.clickLikeRepository
                .findByUsernameLikeAndPromptId(
                        customer.getUsername(),
                        clickLikeDto.getPromptId()
                );

        // todo: đã like nếu click vào thì thực hiện xóa
        if(findByUsernameLike.isPresent()){
            this.clickLikeRepository.deleteById(findByUsernameLike.get().getId());
        }else{
            // todo: user chưa like prompt nào thì save
            ClickLikeEntity clickLikeEntity = this.modelMapper.map(clickLikeDto,ClickLikeEntity.class);
            clickLikeEntity.setUsernameLike(customer.getUsername());

            this.clickLikeRepository.save(clickLikeEntity);
        }
        return new ServiceResult<>(this.modelMapper.map(findByUsernameLike,ClickLikeDto.class), HttpStatus.OK,"Like thành công");
    }


    @Override
    public ServiceResult<Page<ClickLikeDto>> listUsernameClickLike(ClickLikeDto clickLikeDto){

        Page<ClickLikeDto> clickLikeDtoList =
                this.clickLikeRepository.listUsernameClickLike(
                        clickLikeDto.getPromptId(),
                        PageRequest.of(clickLikeDto.getPage(), clickLikeDto.getPageSize())
                );
        return new ServiceResult<>(
                clickLikeDtoList,
                HttpStatus.OK,
                "Like thành công");
    }

    @Override
    public ServiceResult<ClickLikeDto> viewDetail(ClickLikeDto clickLikeDto){
        CustomerDetailService customer = CurrentUserUtils.getCurrentUserUtils();
        Optional<ClickLikeEntity> findByUsernameLike = this.clickLikeRepository.findByUsernameLikeAndPromptId(customer.getUsername(),clickLikeDto.getPromptId());

        return new ServiceResult<>(
                this.modelMapper.map(findByUsernameLike,ClickLikeDto.class),
                HttpStatus.OK,
                "Like thành công");
    }

    @Override
    public ServiceResult<ClickLikeDto> countNumberLike(ClickLikeDto clickLikeDto){
        ClickLikeDto clickLikeDto1 = this.clickLikeRepository.countNumberLike(clickLikeDto.getPromptId());
        return new ServiceResult<>(
                clickLikeDto1,
                HttpStatus.OK,
                "count number like");
    }


}
