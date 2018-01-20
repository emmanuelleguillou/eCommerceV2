package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Produit;


public interface IProduitService {
	public Produit addproduit(Produit p);

	public Produit getProduit(int id_p);

	public void deleteProduit(int id_p);

	public Produit updateProduit(Produit p);

	public List<Produit> getAllPorduit();
	
	public List<Produit> getAllPorduitByCategorie(int id_c);
	
	public List<Produit> getSearchProduct(String req);

}
