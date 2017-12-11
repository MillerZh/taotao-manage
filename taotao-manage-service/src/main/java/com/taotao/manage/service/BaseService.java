package com.taotao.manage.service;

import java.util.Date;
import java.util.List;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;

/**
 * 1、 queryById 2、 queryAll 3、 queryOne 4、 queryListByWhere 5、 queryPageListByWhere 6、 save 7、
 * update 8、 deleteById 9、 deleteByIds 10、 deleteByWhere
 * 
 * @author Administrator
 *
 * @param <T>
 */
public abstract class BaseService<T extends BasePojo> {

    public abstract Mapper<T> getMapper();

    public T queryById(Long id) {
        return this.getMapper().selectByPrimaryKey(id);
    }

    /**
     * 查询全部
     * 
     * @return
     */
    public List<T> queryAll() {
        return this.getMapper().select(null);
    }

    /**
     * 查询一个
     * 
     * @param record
     * @return
     */
    public T queryOne(T record) {
        return this.getMapper().selectOne(record);
    }

    /**
     * 根据条件查询
     * 
     * @param record
     * @return
     */
    public List<T> queryListByWhere(T record) {
        return this.getMapper().select(record);
    }

    /**
     * 根据条件查询page
     * 
     * @param pages
     * @param rows
     * @return
     */
    public PageInfo<T> queryPageListByWhere(Integer pages, Integer rows, T record) {
        PageHelper.startPage(pages, rows);
        List<T> list = this.queryListByWhere(record);
        return new PageInfo<>(list);
    }

    /**
     * 保存
     * 
     * @return
     */
    public Integer save(T record) {
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return this.getMapper().insert(record);
    }
    
    /**
     * 灵活保存
     */
    public Integer saveSelective(T record){
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return this.getMapper().insertSelective(record);
    }

    /**
     * 更新
     * 
     * @return
     */
    public int update(T record) {
        record.setUpdated(new Date());
        return this.getMapper().updateByPrimaryKey(record);
    }
    
    public int updateSelective(T record) {
        record.setUpdated(new Date());
        return this.getMapper().updateByPrimaryKeySelective(record);
    }

    // 8、 deleteById 9、 deleteByIds 10、 deleteByWhere
    public int deleteById(Long id){
        return this.getMapper().deleteByPrimaryKey(id);
    }
    
    public int deleteByIds(Class<T>	clazz, List<Object> values, String property){
    	Example example = new Example(clazz);
    	example.createCriteria().andIn(property, values);
        return this.getMapper().deleteByExample(example);
    }
    
    public int deleteByWhere(T record){
    	return this.getMapper().delete(record);
    }
}
















