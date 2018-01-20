package fr.adaming.service;

import java.util.List;



import fr.adaming.model.Categorie;


public interface ICategorieService {
	public Categorie addCategorie(Categorie c);

	public Categorie getCategorieById(int id_c);

	public void deleteCategorie(int id_c);

	public Categorie updateCategorie(Categorie c);
	
	public List<Categorie> getAllCategorie();
	
	public List<Categorie> getSearchCategorie(String req);
}
