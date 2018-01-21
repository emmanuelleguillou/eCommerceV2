package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "rechMB")
@RequestScoped
public class RechercherMB implements Serializable {

	@ManagedProperty(value = "#{produitService}")
	IProduitService produitService;

	@ManagedProperty(value = "#{categorieService}")
	ICategorieService categorieService;

	private String req;
	private List<Produit> listeProduitS=null;
	private List<Categorie> listeCategorieS=null;

	private boolean renderedProd = false;
	private boolean renderedCat = false;

	public RechercherMB() {
		
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

	public void setListeCategorieS(List<Categorie> listeCategorieS) {
		this.listeCategorieS = listeCategorieS;
	}

	public boolean isRenderedProd() {
		return renderedProd;
	}

	public void setRenderedProd(boolean renderedProd) {
		this.renderedProd = renderedProd;
	}

	public boolean isRenderedCat() {
		return renderedCat;
	}

	public void setRenderedCat(boolean renderedCat) {
		this.renderedCat = renderedCat;
	}

	public String recherche() {

		this.listeProduitS = produitService.getSearchProduct(this.req);

		this.listeCategorieS = categorieService.getSearchCategorie(this.req);

		System.out.println("LISTEEEEEEEEE");

		System.out.println(listeProduitS);
		System.out.println(listeCategorieS);
		System.out.println("FINN");

		if (listeProduitS != null)  {
			this.renderedProd = true;
		}
		if (listeCategorieS != null)  {
			this.renderedCat = true;
		}
		return "pageRecherche";
	}
}
