import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;

@ManagedBean(name = "cMB")
@RequestScoped
public class CommandeManagedBean implements Serializable {

	@ManagedProperty(value="#{commandeService}")
	ICommandeService commandeService;
	@ManagedProperty(value="#{ligneCommandeService}")
	ILigneCommandeService ligneCommandeService;

	private Commande commande;
	private Client client;
	private List<LigneCommande> listeLigneCommande;
	private LigneCommande ligneCommande;
	private long idCommande;

	// constructeur par défaut
	public CommandeManagedBean() {
		this.commande = new Commande();
		this.client = new Client();
		this.ligneCommande = new LigneCommande();
	}
	//Injection des dépendances
	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}


	public void setLigneCommandeService(ILigneCommandeService ligneCommandeService) {
		this.ligneCommandeService = ligneCommandeService;
	}

	// getters et setters
	public Commande getCommande() {
		return commande;
	}


	public long getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(long idCommande) {
		this.idCommande = idCommande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}

	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}

	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}
	

	// Les méthodes
	public String ajouterComande() {
		
		//Créer une commande
		this.commande = commandeService.addCommande(this.commande);
		
		//récupérer toutes les lignes de commandes avec un idCommande null
		this.listeLigneCommande = ligneCommandeService.getAllLignesCommandes();
		double prixTotalC=0;
		double prixTotalRemise=0;
		//Donner à chaque ligne de commande l'id de la commande créée
		for (LigneCommande lc : this.listeLigneCommande) {
			lc.setCommandes(this.commande);
			this.ligneCommande = ligneCommandeService.updateLigneCommande(lc);
			prixTotalC=prixTotalC+lc.getPrix();
			prixTotalRemise=prixTotalRemise + lc.getPrix() * (1- lc.getProduit().getReduction()/100);
			System.out.println("commande de la lc : " + this.ligneCommande);
		}
		
		//Injecte le prix total dans la commande
		this.commande.setPrix(prixTotalC);
		this.commande.setPrixRemise(prixTotalRemise);
		this.commande= commandeService.updateCommande(this.commande);
		
		
		

		//générer une nouvelle liste des ligne commande qui sont associées à la commande
		this.listeLigneCommande = ligneCommandeService.getAllLigneCommandeByIdCommande(this.commande.getIdCommande());
		System.out.println("liste des lc par id commande : " + this.listeLigneCommande);
		
		//On ajoute dans la sesion l'id de la commande pour le retrouver l'hors de l'affichage de la commande dans l'espace client
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idCommande", this.commande.getIdCommande());
		
		if (this.commande != null) {
			return "loginClient";
		} else {
			return "panier";
		}
	}

	public String modifierCommande() {
		this.commande = commandeService.updateCommande(this.commande);
		if (this.commande.getIdCommande() != null) {
			return "pageClient";
		} else {
			return "panier";
		}
	}

	public String rechercherCommandeParIdClientNull() {
		Commande cOut = commandeService.getCommandeByIdClNULL(this.client.getIdClient());
		if (cOut != null) {
			this.commande = cOut;
			return "rechercheCommande";
		} else {
			return "rechercherCommande";
		}
	}

	public String supprimerCommande() {
		commandeService.deleteCommande(this.commande.getIdCommande());
		Commande cOut = commandeService.getCommande(this.commande.getIdCommande());

		if (cOut == null) {
			return "pageClient";
		} else {
			return "supprimerCommande";
		}
	}

	public String rechercherToutesCommandes() {
		List<Commande> listeCommande = commandeService.gettAllCommande(this.client.getIdClient());
		//passer la liste dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeCommandes",
				listeCommande);
		return "#";
	
	}
	
	public String envoyerFacture() {
		//récupération du client correspondant 
		
		this.listeLigneCommande = ligneCommandeService.getAllLigneCommandeByIdCommande(this.idCommande);

		// Récupere la commande
		this.commande = commandeService.getCommande(this.idCommande);

		// Création d'un document de taille A4 avec une marge de 36 sur
		// chaque bord
		// Document document = new Document(PageSize.A4, 36, 36, 36, 36);
		Document document = new Document();
		try {
			// Définir le type de document souhaité ainsi que son nom
			PdfWriter.getInstance(document, new FileOutputStream("C:/Users/marin/Desktop/PDFTp/commande" + this.idCommande + ".pdf"));
			// Ouverture du document
			document.open();
			// Definition des polices
			Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16);

			// Création des elements à ajouter dans le document
			String titre = "Facture pour la commande " + this.idCommande + " de "
					+ this.commande.getClient().getNomClient() + " du " + this.commande.getDateCommande() + "\n";
			Chunk c1 = new Chunk(titre, chapterFont);

			Phrase p1 = new Phrase("Recapitulatif de votre commande : \n\n\n\n");
			
			PdfPTable table = new PdfPTable(6);
			table.addCell("Produit");
			table.addCell("Prix unitaire");
			table.addCell("Prix unitaire après remise");
			table.addCell("Quantité");
			table.addCell("Prix total");
			table.addCell("Prix total après remise");
			for (LigneCommande ligneCommande : this.listeLigneCommande) {
				table.addCell(ligneCommande.getProduit().getDesignation());
				table.addCell(Double.toString(ligneCommande.getProduit().getPrix()));
				table.addCell(Double.toString(ligneCommande.getProduit().getReduction()));
				table.addCell(Integer.toString(ligneCommande.getQuantite()));
				table.addCell(Double.toString(ligneCommande.getPrix()));
				table.addCell(Double.toString(ligneCommande.getPrix()*(1-ligneCommande.getProduit().getReduction()/100)));
			}
			
			Phrase p2 = new Phrase("Total de la commande : " +this.commande.getPrix() +"€");
			Phrase p3 = new Phrase("Total de la commande après remise : " +this.commande.getPrixRemise() +"€");
			//Ajout des elements dans le documents
			document.add(new Paragraph(c1));
		
			document.add(new Paragraph(p1));

			
			document.add(table);
			
			
			document.add(new Paragraph(p2));
			document.add(new Paragraph(p3));
			
			
		} catch (Exception e) {
			// F
			System.out.println("Echec envoyer mail");
			e.printStackTrace();
		}
		// Fermeture du document
		document.close();

		try{
		 // Creation de la piece jointe
		 EmailAttachment attachment = new EmailAttachment();
		 attachment.setPath("C:/Users/marin/Desktop/PDFTp/commande" + this.idCommande + ".pdf");
		 attachment.setDisposition(EmailAttachment.ATTACHMENT);
		 
		 
		 // Creation du mail avec piece jointe
		 MultiPartEmail email = new MultiPartEmail();
		 email.setHostName("smtp.googlemail.com");
		 email.setSmtpPort(465);
		 // Parametrage du compte
		 email.setAuthenticator(new DefaultAuthenticator("manulg13@gmail.com",
		 "wanadoo8"));
		 email.setSSLOnConnect(true);
		 // Adresse de l'envoyeur
		 email.setFrom("manulg13@gmail.com");
		// Objet du mail
		 email.setSubject("Votre commande " +this.idCommande);
		 //Corps du mail
		 email.setMsg("Bonjour, \n \n Merci pour votre commande, veuillez trouver ci-joint le recapitulatif \n");
		 //destinataire du mail
		 email.addTo(this.commande.getClient().getEmail());
		
		 // Ajouter la pièce jointe
		 email.attach(attachment);
		 // envoyer le mail
		 email.send();
		} catch (EmailException em){
			em.printStackTrace();
		}
		return "accueilClient";
		
		
	}
	
	public String rechercherCommandeIDC(){
		
		this.commande=commandeService.getCommande(this.commande.getIdCommande());
		
		return "rechercherCommande";
		
	}
	
	
	
	
	
	
}
