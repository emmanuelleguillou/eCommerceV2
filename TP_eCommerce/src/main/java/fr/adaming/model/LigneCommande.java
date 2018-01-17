package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lignescommandes")
public class LigneCommande implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idLigneCommande;
	private int quantite;
	private double prix;

	// transformation uml en java
	@ManyToOne
	@JoinColumn(name="id_produit", referencedColumnName = "idProduit")
	private Produit produit;

	@ManyToOne
	@JoinColumn(name="id_commande", referencedColumnName = "idCommande")
	Commande commande;

	@ManyToOne
	@JoinColumn(name="id_panier" ,referencedColumnName="idPanier")
	private Panier panier;
	
	// Constructeur pa défaut
	public LigneCommande() {
		super();
		// TODO Auto-generated constructor stub
	}

	// constructeur sans id
	public LigneCommande(int quantite, double prix) {
		super();
		this.quantite = quantite;
		this.prix = prix;
	}

	// constructeur avec id
	public LigneCommande(int idLigneCommande, int quantite, double prix) {
		super();
		this.idLigneCommande = idLigneCommande;
		this.quantite = quantite;
		this.prix = prix;
	}

	// getters et setters
	
	public int getQuantite() {
		return quantite;
	}

	public int getIdLigneCommande() {
		return idLigneCommande;
	}

	public void setIdLigneCommande(int idLigneCommande) {
		this.idLigneCommande = idLigneCommande;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}


	public Commande getCommande() {
		return commande;
	}

	public void setCommandes(Commande commande) {
		this.commande = commande;
	}

	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	
	// tostrong
	@Override
	public String toString() {
		return "LigneCommande [idLigneCommande=" + idLigneCommande + ", quantite=" + quantite + ", prix=" + prix + "]";
	}

	
	

}
