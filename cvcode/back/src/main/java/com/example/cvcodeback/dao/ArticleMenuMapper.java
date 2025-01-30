package com.example.cvcodeback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cvcodeback.entity.ArticleMenu;
import com.example.cvcodeback.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ArticleMenuMapper extends BaseMapper<ArticleMenu> {
    @Select("select id from article_menu")
    List<Integer> queryMids();
    @Select("select t.id,t.name,t.menu_id from article_menu m left JOIN article_tag t on t.menu_id = m.id WHERE m.id = #{mid};")
    List<ArticleTag> queryTagsByMid(Integer mid);
}
