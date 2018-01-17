package fr.adaming.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "commandes")
public class Commande implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCommande;

	// Convertir la date java en format SQL
	@Temporal(TemporalType.DATE) // L'objet de sortir de simpleDateFormt est
									// dans le package util.Date, celui de
	// la BD est de type sql.Date. C'est pourquoi on doit utiliser temporal
	private Date dateCommande = new Date();

	// Transformation des associations uml en java
	@ManyToOne
	@JoinColumn(name = "client_id", referencedColumnName = "idClient")
	private Client client;

	@OneToMany
	@JoinColumn(referencedColumnName="idCommande")
	private List<LigneCommande> ListeLigneCommande;

	public Commande() {
		super();
	}

	public Commande(Date dateCommande) {
		super();
		this.dateCommande = dateCommande;
	}

	public Commande(Long idCommande, Date dateCommande) {
		super();
		this.idCommande = idCommande;
		this.dateCommande = dateCommande;
	}

	public Long getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(Long idCommande) {
		this.idCommande = idCommande;
	}

	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<LigneCommande> getListeLigneCommande() {
		return ListeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		ListeLigneCommande = listeLigneCommande;
	}
	
	

}
