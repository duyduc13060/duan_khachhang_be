package com.example.du_an_demo_be.until;

import com.example.du_an_demo_be.security.CustomerDetailService;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserUtils {

    public static CustomerDetailService getCurrentUserUtils(){
        return (CustomerDetailService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
