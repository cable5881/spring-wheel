package com.lqb.demo;

import com.lqb.springframework.annotation.Service;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/12/9 15:47
 **/
@Service
public class MotherService {
    public String call() {
        return "你妈妈叫你不要加班！";
    }
}
