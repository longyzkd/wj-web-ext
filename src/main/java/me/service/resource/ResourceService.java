package me.service.resource;

import java.util.List;

import me.entity.Menu;
import me.entity.Office;
import me.repository.common.Page;
import me.repository.menu.MenuDao;
import me.repository.office.OfficeDao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
@Transactional
public class ResourceService {

	private static Logger logger = LoggerFactory.getLogger(ResourceService.class);
	
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private OfficeDao officeDao;

	@Transactional(readOnly=true)
	public List<Menu> getPanels(Long userId) {
		return menuDao.findPanels(userId);
	}
	@Transactional(readOnly=true)
	public List<Menu> getNodes(String id) {
		return menuDao.findNodes(id);
	}
	public Page<Office> getOffices(Page<Office> pageObj, Office office) {
		return officeDao.findOffices(pageObj,office);
		
	}
	public void del(Office office) {
		if(office != null && office.getId()!=null){
			officeDao.deleteCasade(office.getId(),"%,"+office.getId()+",%");
//			officeDao.delete(office.getId());
		}else{
			throw new RuntimeException();
		}
		
	}
	public void del(Menu menu) {
		if(menu != null && menu.getId()!=null){
			menuDao.delete(menu.getId());
		}else{
			throw new RuntimeException();
		}
		
		
	}
	public void del(List<Office > root) {
		if(!CollectionUtils.isEmpty(root)){
			for(Office off:root){
				del(off);
			}
		}
		
	}
//	public void create(Office office) {
//		officeDao.save(office);
//		
//	}
	public void create(Office office) {
//		office.setParent(officeDao.get(office.getParent().getId()));
		Office parent = officeDao.get(office.getParentId());
		String oldParentIds = office.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		office.setParentIds(parent.getParentIds()+parent.getId()+",");
		officeDao.clear();
		officeDao.save(office);
		// 更新子节点 parentIds
		List<Office> list = officeDao.findByParentIdsLike("%,"+office.getId()+",%");
		if(!CollectionUtils.isEmpty(list)){
			
			for (Office e : list){
				e.setParentIds(e.getParentIds().replace(oldParentIds, office.getParentIds()));
				officeDao.save(e);
			}
		}
		//UserUtils.removeCache(UserUtils.CACHE_office_LIST);
		
		
		
		
	}
	public <E> List<E> getEntityBy(String beanClazz,String property, Object val,Object rawValue,String action) {
		if(StringUtils.isEmpty(action)){
			
			return menuDao.findBy(beanClazz,property,val);
		}else if("edit".equals(action)){
			return menuDao.findExcept(beanClazz,property,val,rawValue);
		}
		return null;
		
	}
}
