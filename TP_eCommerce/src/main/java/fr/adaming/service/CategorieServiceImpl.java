package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;

@Service("categorieService")
@Transactional
public class CategorieServiceImpl implements ICategorieService {

	@Autowired
	private ICategorieDao categorieDao;
	
	@Override
	public Categorie addCategorie(Categorie c) {
		return categorieDao.addCategorie(c);
	}

	@Override
	public Categorie getCategorieById(int id_c) {
		return categorieDao.getCategorie(id_c);
	}

	@Override
	public void deleteCategorie(int id_c) {
		categorieDao.deleteCategorie(id_c);
		
	}

	@Override
	public Categorie updateCategorie(Categorie c) {
		return categorieDao.updateCategorie(c);
	}

	@Override
	public List<Categorie> getAllCategorie() {
		return categorieDao.getAllCategorie();
	}

	@Override
	public List<Categorie> getSearchCategorie(String req) {
		return categorieDao.getSearchCategorie(req);
	}

}
