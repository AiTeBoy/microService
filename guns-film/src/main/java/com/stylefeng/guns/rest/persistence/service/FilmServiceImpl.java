package com.stylefeng.guns.rest.persistence.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dodo.film.FilmService;
import com.dodo.film.beans.Banner;
import com.dodo.film.beans.Film;
import com.dodo.film.beans.FilmsInfo;
import com.stylefeng.guns.rest.persistence.dao.MtimeBannerTMapper;
import com.stylefeng.guns.rest.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.persistence.model.MtimeBannerT;
import com.stylefeng.guns.rest.persistence.model.MtimeFilmT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service(interfaceClass = FilmService.class)
public class FilmServiceImpl implements FilmService {

    @Autowired
    MtimeBannerTMapper mtimeBannerTMapper;

    @Autowired
    MtimeFilmTMapper mtimeFilmTMapper;

    @Override
    public Map index() {
        Map<String,Object> map = new HashMap<>();
        List<MtimeBannerT> mtimeBannerTS = mtimeBannerTMapper.selectList(null);
        List<Banner> banners = new ArrayList<>();
        for (MtimeBannerT mtimeBannerT : mtimeBannerTS) {
            Banner banner = new Banner(mtimeBannerT.getBannerAddress(),mtimeBannerT.getUuid()+"",mtimeBannerT.getBannerUrl());
            banners.add(banner);
        }
        map.put("banners",banners);//banners

        //按照电影票房把电影进行排序并返回
        List<Film> boxRanking = selectFilm("film_box_office",1);
        map.put("boxRanking",boxRanking);//boxRanking

        //按照预期电影票房排序查询并返回
        List<Film> expectRanking = selectFilm("film_preSaleNum",2);
        map.put("expectRanking",expectRanking);//expectRanking

        //热播电影排序并返回
        FilmsInfo hotFilm = new FilmsInfo();
        hotFilm.setFilmNum(boxRanking.size());
        hotFilm.setFilmInfo(boxRanking);
        map.put("hotFilms",hotFilm);//hotFilms

        //即将上映排序并返回
        FilmsInfo soonFilms = new FilmsInfo();
        soonFilms.setFilmNum(expectRanking.size());
        soonFilms.setFilmInfo(expectRanking);
        map.put("soonFilms",soonFilms);//soonFilms

        //top100排序并返回
        List<Film> films = selectFilm("film_score", 1);
        map.put("top100",films);//top100

        //是json转换的时候出的问题
//        System.out.println(map);
////        String s = JSON.toJSONString(map);
////        System.out.println(s);
        //进行综合封装
        Map<String,Object> result = new HashMap<>();
        result.put("data",map);
        result.put("imgPre","http://img.meetingshop.cn/");
        result.put("msg","");
        result.put("nowPage","");
        result.put("status",0);
        result.put("totalPage","");
        return result;
    }

    //按照列的排序返回数据（DESC）
    public List<Film> selectFilm(String colum,Integer status){
        EntityWrapper<MtimeFilmT> boxOfficWrapper = new EntityWrapper<>();
        List<String> boxDesc = new ArrayList<>();
        boxDesc.add(colum);
        if(status != null) {
            boxOfficWrapper.eq("film_status", status).orderDesc(boxDesc);
        }else {
            boxOfficWrapper.orderDesc(boxDesc);
        }
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectList(boxOfficWrapper);
        List<Film> films = new ArrayList<>();
        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
            Film film = new Film(mtimeFilmT.getFilmBoxOffice(),mtimeFilmT.getFilmPresalenum(),mtimeFilmT.getFilmCats(),
                    mtimeFilmT.getUuid()+"",null,mtimeFilmT.getFilmName(),mtimeFilmT.getFilmScore(),mtimeFilmT.getFilmType(),
                    mtimeFilmT.getImgAddress(),mtimeFilmT.getFilmScore(),mtimeFilmT.getFilmTime()+"");
            films.add(film);
        }
        return films;
    }
}
