package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Produit;

@Service("produitService")
@Transactional
public class ProduitServiceImpl implements IProduitService{

	@Autowired
	private IProduitDao produitDao;
	
	@Override
	public Produit getProduit(int id_p) {
		return produitDao.getProduit(id_p);
	}

	@Override
	public void deleteProduit(int id_p) {
		produitDao.deleteProduit(id_p);
		
	}

	@Override
	public Produit updateProduit(Produit p) {
		return produitDao.updateProduit(p);
	}

	@Override
	public List<Produit> getAllPorduit() {
		return produitDao.getAllPorduit();
	}

	@Override
	public Produit addproduit(Produit p) {
		return produitDao.addproduit(p);
	}

	@Override
	public List<Produit> getAllPorduitByCategorie(int id_c) {
		return produitDao.getAllPorduitByCategorie(id_c);
	}

	@Override
	public List<Produit> getSearchProduct(String req) {
		return produitDao.getSearchProduct(req);
	}

}
