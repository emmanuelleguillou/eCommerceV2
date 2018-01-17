package fr.adaming.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "produits")
public class Produit implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProduit;
	private String designation;
	private String description;
	private double prix;
	private int quantite;

	@Transient
	private int nbProduit;
	
	@Column(columnDefinition = "boolean default false")
	private boolean selectionne=false;

	@Lob
	private byte[] photo;
	
	@Transient
	private String image;

	// trasformation de l'associtation uml en java
	@ManyToOne
	@JoinColumn(referencedColumnName = "idCategorie")
	private Categorie categorie;

	@OneToMany(mappedBy = "produit")
	private List<LigneCommande> listeLigneCommande;

	// constructeur par défaut
	public Produit() {
		super();
		// TODO Auto-generated constructor stub
	}

	// constructeur sans id
	public Produit(String designation, String description, double prix, int quantite, Boolean selectionne,
			byte[] photo) {
		super();
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photo = photo;
	}

	// COnstructeur avec id
	public Produit(int idProduit, String designation, String description, double prix, int quantite,
			Boolean selectionne, byte[] photo) {
		super();
		this.idProduit = idProduit;
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photo = photo;
	}

	// getters et setters
	public int getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public boolean isSelectionne() {
		return selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public Categorie getCategorie() {
		return categorie;
	}
	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	
	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}
	
	

	public int getNbProduit() {
		return nbProduit;
	}

	public void setNbProduit(int nbProduit) {
		this.nbProduit = nbProduit;
	}

	@Override
	public String toString() {
		return "Produit [idProduit=" + idProduit + ", designation=" + designation + ", description=" + description
				+ ", prix=" + prix + ", quantite=" + quantite + ", categorie=" + categorie + "]";
	}

}
