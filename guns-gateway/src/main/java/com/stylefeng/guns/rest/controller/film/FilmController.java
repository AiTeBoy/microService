package com.stylefeng.guns.rest.controller.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.dodo.film.FilmService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("film")
public class FilmController {

    @Reference(interfaceClass = FilmService.class,check = false)
    FilmService filmService;

    @RequestMapping("getIndex")
    public Map FilmIndex(){
        Map result = filmService.index();
        return result;
    }
}
