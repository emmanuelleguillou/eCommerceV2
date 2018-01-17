import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.swing.plaf.synth.SynthSpinnerUI;

import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Panier;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;
import org.apache.commons.mail.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@ManagedBean(name = "paMB")
@SessionScoped
public class PanierManagedBean implements Serializable {

	@ManagedProperty(value="#{ligneCommandeService}")
	ILigneCommandeService ligneCommandeService;

	@ManagedProperty(value="#{commandeService}")
	ICommandeService commandeService;

	private Panier panier;
	private LigneCommande ligneCommande;
	private List<LigneCommande> listeLignecommande;
	private long idCommande;
	private Commande commande;
	// public static final String chemin = "C:/Users/PDT/testPdf.pdf";

	// constructeur par défaut
	public PanierManagedBean() {
		this.panier = new Panier();
		this.ligneCommande = new LigneCommande();
		this.listeLignecommande = new ArrayList<LigneCommande>();

	}
	
	//Injection des dépendances
	public void setLigneCommandeService(ILigneCommandeService ligneCommandeService) {
		this.ligneCommandeService = ligneCommandeService;
	}



	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}



	// getter et setter
	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public List<LigneCommande> getListeLignecommande() {
		return listeLignecommande;
	}

	public void setListeLignecommande(List<LigneCommande> listeLignecommande) {
		this.listeLignecommande = listeLignecommande;
	}

	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}

	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}

	public long getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(long idCommande) {
		this.idCommande = idCommande;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	// Les méthodes
	public String envoyerFacture() {
		// Récupération de toutes les lignes de commandes associées à un
		// idCommande null
		this.listeLignecommande = ligneCommandeService.getAllLigneCommandeByIdCommande(this.idCommande);

		// Récupere la commande
		this.commande = commandeService.getCommande(this.idCommande);

		// Création d'un document de taille A4 avec une marge de 36 sur
		// chaque bord
		// Document document = new Document(PageSize.A4, 36, 36, 36, 36);
		Document document = new Document();
		try {
			// Définir le type de document souhaité ainsi que son nom
			PdfWriter.getInstance(document, new FileOutputStream("C:/Users/inti0294/Desktop/PDFTp/commande" + this.idCommande + ".pdf"));
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
			table.addCell("ID Ligne Commande");
			table.addCell("Id Produit");
			table.addCell("Produit");
			table.addCell("Prix unitaire");
			table.addCell("Quantité");
			table.addCell("Prix total");
			double prixT=0;
			for (LigneCommande ligneCommande : listeLignecommande) {
				table.addCell(Integer.toString(ligneCommande.getIdLigneCommande()));
				table.addCell(Integer.toString(ligneCommande.getProduit().getIdProduit()));
				table.addCell(ligneCommande.getProduit().getDesignation());
				table.addCell(Double.toString(ligneCommande.getProduit().getPrix()));
				table.addCell(Integer.toString(ligneCommande.getQuantite()));
				table.addCell(Double.toString(ligneCommande.getPrix()));
				prixT=prixT+ligneCommande.getPrix();
			}
			
			Phrase p2 = new Phrase("Total de la commande : " +prixT +"€");
			
			//Ajout des elements dans le documents
			document.add(new Paragraph(c1));
		
			document.add(new Paragraph(p1));

			
			document.add(table);
			
			
			document.add(new Paragraph(p2));
			
			
			
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
		 attachment.setPath("C:/Users/inti0294/Desktop/PDFTp/commande" + this.idCommande + ".pdf");
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
	
	//pour envoyer les lignes commandes dans le panier
	public String envoyerPanier() {
		//récupérer toutes les lignes de commandes avec un id comande null (car non validée)
		this.listeLignecommande=ligneCommandeService.getAllLignesCommandes();
		
		//Passer la liste des lignes commandes dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeLCPanier", this.listeLignecommande);
		
				
		return "panier" ;
	}
	
}
