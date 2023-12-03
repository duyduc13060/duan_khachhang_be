package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.exception.BadRequestException;
import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.model.entity.UserEntity;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.repository.UserCustomRepository;
import com.example.du_an_demo_be.repository.UserRepository;
import com.example.du_an_demo_be.service.UserService;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserCustomRepository userCustomRepository;
    private final PasswordEncoder passwordEncoder;


    //todo: Lấy ra danh sách user
    @Override
    public DefaultResponse<?> getListUser(){
        DefaultResponse<List<UserDto>> respose = new DefaultResponse<>();
        List<UserEntity> userEntityList = userRepository.findAll();

        List<UserDto> userDtoList = userEntityList.stream()
                .map(e -> modelMapper.map(e,UserDto.class))
                .collect(Collectors.toList());

        respose.setData(userDtoList);
        return respose;
    }


    @Override
    public DefaultResponse<List<UserDto>> search(UserDto userDto){

        List<UserDto> listUserDto = userCustomRepository.searchUser(userDto);

        return DefaultResponse.success(listUserDto);
    }

    @Override
    public DefaultResponse<?> createUser(UserDto userDto) {

        DefaultResponse<UserDto> response =  this.validateCreateUpdateUser(userDto);

        if(!response.getSuccess().equals(200)){
            throw new BadRequestException("Có lỗi xảy ra trong quá trinh thêm mới !!!");
        }

        Optional<UserEntity> findByUsername = userRepository.findByUsername(userDto.getUsername());
        if(findByUsername.isPresent()){
            throw new BadRequestException("Username đã tồn tại bạn không thể tạo mới !!!");
        }


        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode("123456aA@"));

        this.userRepository.save(userEntity);
        response.setData(modelMapper.map(userEntity,UserDto.class));
        response.setMessage("Thêm mới thành công");
        return response;
    }

    @Override
    public DefaultResponse<?> updateUser(UserDto userDto){
        DefaultResponse<UserDto> response = new DefaultResponse<>();

        UserEntity findById = this.userRepository.getById(userDto.getId());
        if(findById == null){
            throw new BadRequestException("Id not found" + userDto.getId());
        }
        findById = modelMapper.map(userDto, UserEntity.class);

        this.userRepository.save(findById);

        response.setData(modelMapper.map(findById, UserDto.class));
        response.setMessage("Cập nhật thành công");
        response.setSuccess(HttpStatus.OK.value());
        return response;
    }


    @Override
    public DefaultResponse<?> deleteUser(Long userId){
        DefaultResponse<UserDto> response = new DefaultResponse<>();

        UserEntity findById = this.userRepository.getById(userId);
        if(findById == null){
            throw new BadRequestException("Id not dfound" + userId);
        }

        userRepository.deleteById(findById.getId());
        response.setMessage("Xóa user thành công");
        return response;
    }

    @Override
    public DefaultResponse<?> viewDetailUser(Long userId){
        UserEntity findById = this.userRepository.getById(userId);
        if(findById == null){
            throw new BadRequestException("Id not dfound" + userId);
        }
        return DefaultResponse.success(modelMapper.map(findById,UserDto.class));
    }

    private DefaultResponse<UserDto> validateCreateUpdateUser(UserDto userDto){
        DefaultResponse<UserDto> response = new DefaultResponse<>();

        if(Objects.isNull(userDto.getUsername())){
            response.setSuccess(HttpStatus.NOT_FOUND.value());
            response.setMessage("username không được để trống");
            response.setData(null);
            return response;
        }

        if(Objects.isNull(userDto.getFullname())){
            response.setSuccess(HttpStatus.NOT_FOUND.value());
            response.setMessage("fullname không được để trống");
            response.setData(null);
            return response;
        }

//        if(Objects.isNull(userDto.getPhone())){
//            response.setSuccess(HttpStatus.NOT_FOUND.value());
//            response.setMessage("phone không được để trống");
//            response.setData(null);
//            return response;
//        }

        response.setSuccess(HttpStatus.OK.value());
        response.setMessage("Thành công");
        response.setData(userDto);

        return response;

    }


    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"model\":\"mistral-7b-instruct\",\"messages\":[{\"role\":\"system\",\"content\":\"Be precise and concise.\"},{\"role\":\"user\",\"content\":\"How many stars are there in our galaxy?\"}]}");

        Request request = new Request.Builder()
                .url("https://api.perplexity.ai/chat/completions")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer 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")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Xử lý dữ liệu response ở đây
                String responseBody = response.body().string();
                System.out.println("Response: " + responseBody);
            } else {
                // Xử lý khi response không thành công
                System.err.println("Error: " + response.code() + " - " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
