package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "rechMB")
@ViewScoped
public class RechercherMB implements Serializable {
	
	@ManagedProperty(value="#{produitService}")
	IProduitService produitService;
	
	@ManagedProperty(value="#{categorieService}")
	ICategorieService categorieService;

	
	private String req;
	private List<Produit> listeProduitS;
	private List<Categorie> listeCategorieS;
	
	
	public RechercherMB() {
		super();
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}


	
	public String getReq() {
		return req;
	}

	public void setReq(String req) {
		this.req = req;
	}

	
	
	public List<Produit> getListeProduitS() {
		return listeProduitS;
	}

	public void setListeProduitS(List<Produit> listeProduitS) {
		this.listeProduitS = listeProduitS;
	}

	public List<Categorie> getListeCategorieS() {
		return listeCategorieS;
	}

	public void setListeCategorie(List<Categorie> listeCategorieS) {
		this.listeCategorieS = listeCategorieS;
	}

	public String recherche(){
		
	this.listeProduitS=	produitService.getSearchProduct(this.req);
	
	this.listeCategorieS= categorieService.getSearchCategorie(this.req);
	
	System.out.println("LISTEEEEEEEEE");
	
	System.out.println(listeProduitS);
	System.out.println(listeCategorieS);
	System.out.println("FINN");
		
	if (listeProduitS==null && listeCategorieS==null){
		return "pageRecherche";
	}
		
		return "pageRecherche";
	}
}
