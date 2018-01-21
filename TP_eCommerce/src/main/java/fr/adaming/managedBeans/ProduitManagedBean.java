import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.mail.*;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "prMB")
@ViewScoped
public class ProduitManagedBean implements Serializable {
	@ManagedProperty(value="#{produitService}")
	private IProduitService produitService;
	@ManagedProperty(value="#{categorieService}")
	private ICategorieService categorieService;

	private Categorie categorie;
	private List<Produit> listeProduit;
	private List<Produit> listeProduitAll;
	private Produit produit;
	private int nbProduit;
	private HttpSession maSession;
	private int idCategorie;
	
	private String image;

	public ProduitManagedBean() {
		this.produit = new Produit();
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		// Recuperer la liste des produit
		this.listeProduit = (List<Produit>) maSession.getAttribute("produitListByCat");
		this.listeProduitAll = (List<Produit>) maSession.getAttribute("produitListAll");
	}
	//Injection des dépendances

	public IProduitService getProduitService() {
		return produitService;
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}
	

	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public int getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	public List<Produit> getListeProduitAll() {
		return listeProduitAll;
	}

	public void setListeProduitAll(List<Produit> listeProduitAll) {
		this.listeProduitAll = listeProduitAll;
	}

	public int getNbProduit() {
		return nbProduit;
	}

	public void setNbProduit(int nbProduit) {
		this.nbProduit = nbProduit;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String ajouterProduitByCat() {
		// Recuperer la categorie
		int idCat = (int) maSession.getAttribute("idCat");
		this.produit.setCategorie(categorieService.getCategorieById(idCat));
		this.produit.setSelectionne(false);
		this.produit = produitService.addproduit(this.produit);

		if (this.produit.getIdProduit() != 0) {
			// recuperer la nouvelle liste de la bd
			//this.listeProduit = produitService.getAllPorduit();
			
			List<Produit> listeOut= produitService.getAllPorduitByCategorie(idCategorie);
			this.listeProduit = new ArrayList<Produit>();
			
			for (Produit element : listeOut) {
				if (element.getPhoto() == null) {
					element.setImage(null);
				} else {
					element.setImage("data:image/png;base64," + Base64.encodeBase64(element.getPhoto()));
				}
				this.listeProduit.add(element);
			}
			
			
			
			// Mettre a jour la liste dans la session
			maSession.setAttribute("produitListByCat", this.listeProduit);
			return "accueilAdmin";
		} else
			return "failure";
	}

	public String supprimerProduitByCat() {

		produitService.deleteProduit(this.produit.getIdProduit());
		System.out.println("IDCategorieTest= : " + idCategorie);
		int idCat = (int) maSession.getAttribute("idCat");
		// recuperer la nouvelle liste de la bd
		//this.listeProduit = produitService.getAllPorduitByCategorie(idCat);
		// smaSession.setAttribute("idCategorie", idCategorie);
		
		List<Produit> listeOut= produitService.getAllPorduitByCategorie(idCategorie);
		this.listeProduit = new ArrayList<Produit>();
		
		for (Produit element : listeOut) {
			if (element.getPhoto() == null) {
				element.setImage(null);
			} else {
				element.setImage("data:image/png;base64," + Base64.encodeBase64(element.getPhoto()));
			}
			this.listeProduit.add(element);
		}
		
		
		
		
		// Mettre a jour la liste dans la session
		maSession.setAttribute("produitListByCat", this.listeProduit);
		return "afficherListeProduit";
	}

	public String modifierProduit() {
		// Récuperer la catégorie
		int idCat = (int) maSession.getAttribute("idCat");

		this.produit.setCategorie(categorieService.getCategorieById(idCat));
		this.produit = produitService.updateProduit(this.produit);

		if (this.produit.getIdProduit() != 0) {
			idCat = (int) maSession.getAttribute("idCat");
			// recuperer la nouvelle liste de la bd
			//this.listeProduit = produitService.getAllPorduitByCategorie(idCat);
			
			
			List<Produit> listeOut= produitService.getAllPorduitByCategorie(idCategorie);
			this.listeProduit = new ArrayList<Produit>();
			
			for (Produit element : listeOut) {
				if (element.getPhoto() == null) {
					element.setImage(null);
				} else {
					element.setImage("data:image/png;base64," + Base64.encodeBase64(element.getPhoto()));
				}
				this.listeProduit.add(element);
			}
			
			
			// Mettre a jour la liste dans la session
			maSession.setAttribute("produitListByCat", this.listeProduit);
			return "afficherListeProduit";
		} else
			return "afficherListeProduit";
	}

	public String rechercherProduit() {
		this.produit = produitService.getProduit(this.produit.getIdProduit());
		if (this.produit.getIdProduit() != 0) {
			return "rechercherProduit";
		} else
			return "rechercherProduit";

	}

	public String afficherParCategorie() {
		maSession.setAttribute("idCat", idCategorie);
		
		//this.listeProduit = produitService.getAllPorduitByCategorie(idCategorie);
		
		List<Produit> listeOut= produitService.getAllPorduitByCategorie(idCategorie);
		this.listeProduit = new ArrayList<Produit>();
		
		for (Produit element : listeOut) {
			if (element.getPhoto() == null) {
				element.setImage(null);
			} else {
				element.setImage("data:image/png;base64," + Base64.encodeBase64(element.getPhoto()));
			}
			this.listeProduit.add(element);
		}
		
		
		// Ajouter la liste dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitListByCat", listeProduit);
		return "afficherListeProduit";
	}

	public String afficherAll() {
		this.listeProduitAll = produitService.getAllPorduit();
		// Ajouter la liste dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitListAll", listeProduitAll);
		return "afficherProduits";
	}

	public String modifLien() {
		// Recuperation du produit
		Produit pOut = produitService.getProduit(this.produit.getIdProduit());

		this.produit = pOut;
		// Retour dans la page de modification avec remplissage des champs
		return "modifProduit";
	}

	public String afficherParCategorieClient() {
		maSession.setAttribute("idCat", idCategorie);

		//this.listeProduit = produitService.getAllPorduitByCategorie(idCategorie);
		
		List<Produit> listeOut= produitService.getAllPorduitByCategorie(idCategorie);
		this.listeProduit = new ArrayList<Produit>();
		
		for (Produit element : listeOut) {
			if (element.getPhoto() == null) {
				element.setImage(null);
			} else {
				element.setImage("data:image/png;base64," + Base64.encodeBase64(element.getPhoto()));
			}
			this.listeProduit.add(element);
		}
		
		
		// Ajouter la liste dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitListByCat", listeProduit);
		return "afficherListeProduitClient";
	}

	public String envoyeEmail() {
		try {
			
			// Creation de la piece jointe
			  EmailAttachment attachment = new EmailAttachment();
			  attachment.setPath("C:/Users/inti0294/Downloads/logo1.jpg");
			  attachment.setDisposition(EmailAttachment.ATTACHMENT);
			  attachment.setDescription("Picture of John");
			  attachment.setName("John");
			
			 //Creation du mail simple
			//Email email = new SimpleEmail();
			 
			//Creation du mail avec piece jointe
			MultiPartEmail email = new MultiPartEmail(); 
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			//Parametrage du compte
			email.setAuthenticator(new DefaultAuthenticator("manulg13@gmail.com", "wanadoo8"));
			email.setSSLOnConnect(true);
			//Adresse de l'envoyeur
			email.setFrom("manulg13@gmail.com");

			email.setSubject("TestMail");
			email.setMsg("This is a test mail ... :-)");
			email.addTo("manu49_8@hotmail.fr");
			
			//Ajouter la pièce jointe
			email.attach(attachment);
			//envoyer le mail
			email.send();

		} catch (EmailException e) {
			System.out.println("Echec envoyer mail");
			e.printStackTrace();
		}
		return "accueil";
	}

	// Cette methode permet de transformer une image UploadFile en byte array
	public void upload(FileUploadEvent event) {
		UploadedFile uploadFile = event.getFile();
		// Recuperer le contenu de l'image en byte array (pixels)
		byte[] contents = uploadFile.getContents();
		System.out.println("----------------   " + contents);
		produit.setPhoto(contents);
		// Transforme byte array en string (format basé64)
		image = "data:image/png;base64," + Base64.encodeBase64String(produit.getPhoto());
		
	}
	
	
}
