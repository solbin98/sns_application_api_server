package com.practice.chatapiserver.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestServletUtil {
    public static Long getMemberId(HttpServletRequest request){
        return ((Integer)request.getAttribute("member_id")).longValue();
    }
}
