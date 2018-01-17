import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "lcMB")
// @SessionScoped
@RequestScoped
public class LigneCommandeManagedBean implements Serializable {

	@ManagedProperty(value="#{ligneCommandeService}")
	private ILigneCommandeService ligneCommandeService;
	@ManagedProperty(value="#{produitService}")
	private IProduitService produitService;
	@ManagedProperty(value="#{commandeService}")
	private ICommandeService commandeService;

	private LigneCommande ligneCommande;
	private Produit produit;
	private Commande commande;
	private boolean indice = false;
	List<LigneCommande> listeLigneCommande;
	private int idCommande;

	// Constructeur par défaut
	public LigneCommandeManagedBean() {
		this.ligneCommande = new LigneCommande();
		this.produit = new Produit();
		this.listeLigneCommande = new ArrayList<LigneCommande>();

	}
	//Injection des dépendances
	public void setLigneCommandeService(ILigneCommandeService ligneCommandeService) {
		this.ligneCommandeService = ligneCommandeService;
	}


	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}


	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}

	// Getters et setters
	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}


	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
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

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public boolean isIndice() {
		return indice;
	}

	public void setIndice(boolean indice) {
		this.indice = indice;
	}


	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}

	public int getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(int idCommande) {
		this.idCommande = idCommande;
	}

	// Les méthodes
	public String ajouterLigneCommande() {
		// récupération du produit par l'id entré
		this.produit = produitService.getProduit(this.produit.getIdProduit());
		// spécification du produit pour la ligne de commande
		this.ligneCommande.setProduit(this.produit);
		// calcul du prix total
		this.ligneCommande.setPrix(ligneCommandeService.calculPrixLigneCommande(this.ligneCommande, this.produit));

		if(this.produit.getQuantite() >= 0) {
			// modification de la quantité de produit en stock
			int quantiteRestante = this.produit.getQuantite() - this.ligneCommande.getQuantite();

			// Modifier la quantité de produit en stock restant
			if (quantiteRestante > 0) {
				this.produit.setQuantite(quantiteRestante);
				produitService.updateProduit(this.produit);

				// ajout de la ligne dans la base de données
				this.ligneCommande = ligneCommandeService.addLigneCommande(this.ligneCommande);
				System.out.println(this.ligneCommande);
			}

		}
		
		//pour envoyer les lignes commandes dans le panier
		//récupérer toutes les lignes de commandes avec un id comande null (car non validée)
		this.listeLigneCommande=ligneCommandeService.getAllLignesCommandes();
		
		//Passer la liste des lignes commandes dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeLCPanier", this.listeLigneCommande);
		

		
		if (this.ligneCommande.getIdLigneCommande() != 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Success", "Ligne de commande ajoutée"));
			return "afficherListeProduitClient";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Failure", "Ligne de commande non ajoutée"));
			return "afficherListeProduitClient";
		}

	}

	public String modifierLigneCommande() {
		// récupération du produit par l'id entré
		this.produit = produitService.getProduit(this.produit.getIdProduit());
		// spécification du produit pour la ligne de commande
		this.ligneCommande.setProduit(this.produit);
		// calcul du prix total
		this.ligneCommande.setPrix(ligneCommandeService.calculPrixLigneCommande(this.ligneCommande, this.produit));
		// update de la ligne dans la base de données
		this.ligneCommande = ligneCommandeService.updateLigneCommande(this.ligneCommande);
		if (this.ligneCommande != null) {
			return "accueil";
		} else {
			return "modifierLigneCommande";
		}

	}

	public String rechercherLigneCommande() {
		LigneCommande lcOut = ligneCommandeService.getLigneCommande(this.ligneCommande.getIdLigneCommande());
		if (lcOut != null) {
			this.ligneCommande = lcOut;
			return "rechercherLigneCommande";
		} else {
			return "rechercherLigneCommande";
		}

	}

	public String supprimerLigneCommande() {

		ligneCommandeService.deleteLigneCommande(this.ligneCommande.getIdLigneCommande());
		LigneCommande lcOut = ligneCommandeService.getLigneCommande(this.ligneCommande.getIdLigneCommande());
		if (lcOut == null) {
			return "accueil";
		} else {
			return "supprimerLigneCommande";
		}

	}

	public String afficherLigneCommandeByIDCommande() {

		indice = true;
		// passer toutes les lignes de commande d'une commande dans une liste
		// List<LigneCommande> listeLCbyC =
		// ligneCommandeService.getAllLigneCommandeByIdCommande(this.commande.getIdCommande());
		// Passer la liste dans la sessio,

		// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeLCbyC",
		// liste);
		this.listeLigneCommande = ligneCommandeService.getAllLigneCommandeByIdCommande(this.idCommande);
		for (LigneCommande ligneCommande : this.listeLigneCommande) {
			System.out.println(ligneCommande);
		}

		this.commande = commandeService.getCommande(this.idCommande);
		return "accueilClient";
	}

}
