package com.chen.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    public static <v> v copyBean(Object object, Class<v> clazz) {
        v o = null;
        try {
            o = clazz.newInstance();
            BeanUtils.copyProperties(object, o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    public static <v> List<v> copyBeanList(List<?> list, Class<v> clazz) {
        return list.stream().map(u -> copyBean(u, clazz)).collect(Collectors.toList());
    }
}