package com.taotao.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taotao.manage.pojo.ContentCategory;

@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

	public Integer save(ContentCategory t) {
		try {
			ContentCategory parent = super.queryById(t.getParentId());
			if (!parent.getIsParent()) {
				parent.setIsParent(true);
				super.updateSelective(parent);
			}
			super.save(t);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("save ContentCategory error!");
			e.printStackTrace();
		}
		return 0;
	}

	public void deleteAll(ContentCategory contentCategory) {
		List<Object> ids = new ArrayList<Object>();
		ids.add(contentCategory.getId());

		// 递归查找该节点下的所有子节点id
		this.findAllSubNode(ids, contentCategory.getId());

		super.deleteByIds(ContentCategory.class, ids, "id");

		// 判断该节点是否还有兄弟节点，如果没有，修改父节点的isParent为false
		ContentCategory record = new ContentCategory();
		record.setParentId(contentCategory.getParentId());
		List<ContentCategory> list = super.queryListByWhere(record);
		if (null == list || list.isEmpty()) {
			ContentCategory parent = new ContentCategory();
			parent.setId(contentCategory.getParentId());
			parent.setIsParent(false);
			super.updateSelective(parent);
		}
	}

	private void findAllSubNode(List<Object> ids, Long pid) {
		ContentCategory record = new ContentCategory();
		record.setParentId(pid);
		List<ContentCategory> list = super.queryListByWhere(record);
		for (ContentCategory contentCategory : list) {
			ids.add(contentCategory.getId());
			// 判断该节点是否为父节点，如果是，继续调用该方法查找子节点
			if (contentCategory.getIsParent()) {
				// 开始递归
				findAllSubNode(ids, contentCategory.getId());
			}
		}
	}

}
