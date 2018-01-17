package fr.adaming.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="categories")
public class Categorie implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCategorie;
	private String nomCategorie;
	private String description;
	
	@Lob
	private byte[] photo;
	
	@Transient
	private String image;

	// transformation de l'uml en java
	@OneToMany(mappedBy="categorie")
	private List<Produit> listeDesProduits;

	// constructeur par défaut
	public Categorie() {
		super();
		// TODO Auto-generated constructor stub
	}

	// constructeur sans id
	public Categorie(String nomCategorie, String description, byte[] photo) {
		super();
		this.nomCategorie = nomCategorie;
		this.description = description;
		this.photo = photo;
		
	}

	// constructeur avec id
	public Categorie(int idCategorie, String nomCategorie, String description, byte[] photo) {
		super();
		this.idCategorie = idCategorie;
		this.nomCategorie = nomCategorie;
		this.description = description;
		this.photo = photo;
		
	}

	// getters et setters
	public int getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	public String getNomCategorie() {
		return nomCategorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Produit> getListeDesProduits() {
		return listeDesProduits;
	}

	public void setListeDesProduits(List<Produit> listeDesProduits) {
		this.listeDesProduits = listeDesProduits;
	}

	@Override
	public String toString() {
		return "Categorie [idCategorie=" + idCategorie + ", nomCategorie=" + nomCategorie + ", description="
				+ description + "]";
	}

	
	

	


	

}
