package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IAdministrateurDao;
import fr.adaming.model.Administrateur;

@Service("adminService")
@Transactional
public class AdministrateurServiceImpl implements IAdministrateurService {

	@Autowired
	private IAdministrateurDao administrateurDao;

	@Override
	public Administrateur isExist(Administrateur a) {
		return administrateurDao.isExist(a);

	}

}
