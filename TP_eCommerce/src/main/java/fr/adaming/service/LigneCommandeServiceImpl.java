package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

@Service("ligneCommandeService")
@Transactional
public class LigneCommandeServiceImpl implements ILigneCommandeService {

	@Autowired
	private ILigneCommandeDao ligneCommandeDao;

	// getter et setter
	public ILigneCommandeDao getLigneCommandeDao() {
		return ligneCommandeDao;
	}

	public void setLigneCommandeDao(ILigneCommandeDao ligneCommandeDao) {
		this.ligneCommandeDao = ligneCommandeDao;
	}

	// les Méthodes
	@Override
	public LigneCommande addLigneCommande(LigneCommande lc) {
		LigneCommande lcOut = ligneCommandeDao.addLigneCommande(lc);
		return lcOut;
	}

	@Override
	public LigneCommande updateLigneCommande(LigneCommande lc) {
		LigneCommande lcOut = ligneCommandeDao.updateLigneCommande(lc);
		return lcOut;
	}

	@Override
	public void deleteLigneCommande(int idLigneCommande) {
		ligneCommandeDao.deleteLigneCommande(idLigneCommande);

	}

	@Override
	public LigneCommande getLigneCommande(int idLigneCommande) {
		LigneCommande lcOut = ligneCommandeDao.getLigneCommande(idLigneCommande);
		return lcOut;
	}

	@Override
	public List<LigneCommande> getAllLigneCommandeByIdCommande(long idCommande) {
		List<LigneCommande> listeOut = ligneCommandeDao.getAllLigneCommandeByIdCommande(idCommande);
		return listeOut;
	}

	@Override
	public double calculPrixLigneCommande(LigneCommande lc, Produit p) {
		double prix = ligneCommandeDao.calculPrixLigneCommande(lc, p);
		return prix;
	}

	@Override
	public List<LigneCommande> getAllLignesCommandes() {
		List<LigneCommande> listeOut = ligneCommandeDao.getAllLignesCommandes();
		return listeOut;
	}

}
