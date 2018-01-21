import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "lcMB")

// @RequestScoped
@ViewScoped
public class LigneCommandeManagedBean implements Serializable {

	@ManagedProperty(value = "#{ligneCommandeService}")
	private ILigneCommandeService ligneCommandeService;
	@ManagedProperty(value = "#{produitService}")
	private IProduitService produitService;
	@ManagedProperty(value = "#{commandeService}")
	private ICommandeService commandeService;

	private LigneCommande ligneCommande;
	private Produit produit;
	private Commande commande;
	private boolean indice = false;
	List<LigneCommande> listeLigneCommande;
	private int idCommande;
	private double prixTotal;

	// Constructeur par d�faut
	public LigneCommandeManagedBean() {
		this.ligneCommande = new LigneCommande();
		this.produit = new Produit();
		this.listeLigneCommande = new ArrayList<LigneCommande>();
		this.commande = new Commande();
	}

	// Injection des d�pendances
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

	public double getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(double prixTotal) {
		this.prixTotal = prixTotal;
	}

	// Les m�thodes
	public String ajouterLigneCommande() {
		// r�cup�ration du produit par l'id entr�
		this.produit = produitService.getProduit(this.produit.getIdProduit());
		// System.out.println("L'ID DU PRODUIT EST : " +
		// this.produit.getIdProduit());

		// sp�cification du produit pour la ligne de commande
		this.ligneCommande.setProduit(this.produit);
		// calcul du prix total pour une ligne commande
		this.ligneCommande.setPrix(ligneCommandeService.calculPrixLigneCommande(this.ligneCommande, this.produit));

		if (this.produit.getQuantite() >= 0) {
			// modification de la quantit� de produit en stock
			int quantiteRestante = this.produit.getQuantite() - this.ligneCommande.getQuantite();

			// Modifier la quantit� de produit en stock restant
			if (quantiteRestante > 0) {
				this.produit.setQuantite(quantiteRestante);
				produitService.updateProduit(this.produit);

				// ajout de la ligne dans la base de donn�es
				this.ligneCommande = ligneCommandeService.addLigneCommande(this.ligneCommande);
				// System.out.println(this.ligneCommande);
			}

		}

		// pour envoyer les lignes commandes dans le panier
		// r�cup�rer toutes les lignes de commandes avec un id comande null (car
		// non valid�e)
		this.listeLigneCommande = ligneCommandeService.getAllLignesCommandes();

		// Passer la liste des lignes commandes dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeLCPanier",
				this.listeLigneCommande);

		// Calcul du prix de toutes les lignes commandes
		this.prixTotal = ligneCommandeService.calculPrixToutesLignesCommandes(this.listeLigneCommande);
		System.out.println("prix total des lc: ----------------------- " + this.prixTotal);

		//Passer le prix total dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("prixTotal", this.prixTotal);
		
		if (this.ligneCommande.getIdLigneCommande() != 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Success", "Ligne de commande ajout�e"));
			return "afficherListeProduitClient";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Failure", "Ligne de commande non ajout�e"));
			return "afficherListeProduitClient";
		}

	}

	public String modifierLigneCommande() {
		// r�cup�ration du produit par l'id entr�
		this.produit = produitService.getProduit(this.produit.getIdProduit());
		// sp�cification du produit pour la ligne de commande
		this.ligneCommande.setProduit(this.produit);
		// calcul du prix total
		this.ligneCommande.setPrix(ligneCommandeService.calculPrixLigneCommande(this.ligneCommande, this.produit));
		// update de la ligne dans la base de donn�es
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

		
		//Si la ligne de commande est supprim�e la quantit� de produt s�lectionn� est remis en stock
		this.ligneCommande=ligneCommandeService.getLigneCommande(this.ligneCommande.getIdLigneCommande());
		this.ligneCommande.getProduit().setQuantite(this.ligneCommande.getQuantite());
		produitService.updateProduit(this.ligneCommande.getProduit());
		
		//suppresion de cette ligne de commande
		ligneCommandeService.deleteLigneCommande(this.ligneCommande.getIdLigneCommande());
		
		//verification de la suppression de la ligne de commande
		LigneCommande lcOut = ligneCommandeService.getLigneCommande(this.ligneCommande.getIdLigneCommande());
		
		// r�cup�rer toutes les lignes de commandes avec un id comande null (car
		// non valid�e)
		this.listeLigneCommande = ligneCommandeService.getAllLignesCommandes();

		// Passer la liste des lignes commandes dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeLCPanier",
				this.listeLigneCommande);

		
		
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

		this.listeLigneCommande = ligneCommandeService.getAllLigneCommandeByIdCommande(idCommande);

		// Passer la liste dans la session
		// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeLCbyC",
		// this.listeLigneCommande);

		for (LigneCommande ligneCommande : this.listeLigneCommande) {
			System.out.println(ligneCommande);
		}
		this.commande = commandeService.getCommande(idCommande);

		if (this.commande != null) {

			return "CommandeOK";
		} else {

			return "CommandeKO";
		}

	}

}
