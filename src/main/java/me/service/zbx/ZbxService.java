package me.service.zbx;

import java.util.List;

import me.entity.Zbx;
import me.repository.common.Page;
import me.repository.office.OfficeDao;
import me.repository.zbx.ZbxDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ZbxService {
	private static Logger logger = LoggerFactory.getLogger(ZbxService.class);
	
	@Autowired
	private ZbxDao dao;
	
	@Autowired
	private OfficeDao officeDao;
	
	@Transactional(readOnly=true)
	public Page<Zbx> getList(Page<Zbx> page, Zbx zbx) {
		Page<Zbx> p = dao.findZbxs(page,zbx);
		List<Zbx> zbxs = p.getList();
		for(Zbx z :zbxs){//TODO 后台hql transformer criteria关联查询
			z.setOfficeName(officeDao.get(z.getOfficeId()).getName());
		}
		return p;
	}

	public List<Zbx> getPropertys(String entityName) {
		return dao.findPropertys(entityName);
		
	}

	public List<Zbx> getEntitys() {
		return dao.findAllEntitys();
	}

	public List<Zbx> getZbxList(String entityName) {
		return dao.findZbxList(entityName);
	}

	public void create(Zbx zbx1) {
		dao.save(zbx1);
		
	}
}
