package me.service.resource;

import java.util.List;

import me.entity.Menu;
import me.entity.Office;
import me.repository.common.Page;
import me.repository.menu.MenuDao;
import me.repository.office.OfficeDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
			officeDao.delete(office.getId());
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
}
