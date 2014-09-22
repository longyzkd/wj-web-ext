package me.service.resource;

import java.util.List;

import me.entity.Doc;
import me.entity.Menu;
import me.repository.resource.ResourceDao;

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
	private ResourceDao dao;

	@Transactional(readOnly=true)
	public List<Menu> getPanels(Long userId) {
		return dao.findPanels(userId);
	}
	@Transactional(readOnly=true)
	public List<Menu> getNodes(String id) {
		return dao.findNodes(id);
	}
}
