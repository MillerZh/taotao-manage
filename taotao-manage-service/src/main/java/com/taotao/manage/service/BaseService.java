package com.taotao.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;

/**
 * 1、 queryById 2、 queryAll 3、 queryOne 4、 queryListByWhere 5、
 * queryPageListByWhere 6、 save 7、 update 8、 deleteById 9、 deleteByIds 10、
 * deleteByWhere
 * 
 * @author Administrator
 *
 * @param <T>
 */
public abstract class BaseService<T extends BasePojo> {

	@Autowired
	private Mapper<T> mapper;

	public T queryById(Long id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	public List<T> queryAll() {
		return this.mapper.select(null);
	}

	/**
	 * 查询一个
	 * 
	 * @param record
	 * @return
	 */
	public T queryOne(T record) {
		return this.mapper.selectOne(record);
	}

	/**
	 * 根据条件查询
	 * 
	 * @param record
	 * @return
	 */
	public List<T> queryListByWhere(T record) {
		return this.mapper.select(record);
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
		return this.mapper.insert(record);
	}

	/**
	 * 灵活保存
	 */
	public Integer saveSelective(T record) {
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return this.mapper.insertSelective(record);
	}

	/**
	 * 更新
	 * 
	 * @return
	 */
	public int update(T record) {
		record.setUpdated(new Date());
		return this.mapper.updateByPrimaryKey(record);
	}

	public int updateSelective(T record) {
		record.setUpdated(new Date());
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	// 8、 deleteById 9、 deleteByIds 10、 deleteByWhere
	public int deleteById(Long id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	public int deleteByIds(Class<T> clazz, List<Object> values, String property) {
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, values);
		return this.mapper.deleteByExample(example);
	}

	public int deleteByWhere(T record) {
		return this.mapper.delete(record);
	}
}
