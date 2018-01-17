package fr.adaming.dao;

import java.util.List;


import fr.adaming.model.Produit;

public interface IProduitDao {
	public Produit addproduit(Produit p);

	public Produit getProduit(int id_p);

	public void deleteProduit(int id_p);

	public Produit updateProduit(Produit p);

	public List<Produit> getAllPorduit();

	public List<Produit> getAllPorduitByCategorie(int id_c);
}
