import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

import fr.adaming.model.Categorie;
import fr.adaming.service.ICategorieService;

@ManagedBean(name = "caMB")
@ViewScoped
public class CategorieManagedBean {

	@ManagedProperty(value="#{categorieService}")
	private ICategorieService categorieService;

	private Categorie categorie;
	private List<Categorie> listeCategories;

	private HttpSession maSession;

	private String image;

	public CategorieManagedBean() {
		this.categorie = new Categorie();
	}

	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public ICategorieService getCategorieService() {
		return categorieService;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

	public String ajouterCatgeorie() {
		// Récuperer l'agent dans la session
		this.categorie = categorieService.addCategorie(this.categorie);

		if (this.categorie.getIdCategorie() != 0) {
			this.getAllCategories();
			maSession.setAttribute("categoriesList", this.listeCategories);
			return "accueilAdmin";
		} else
			return "failure";
	}

	public String modifierCategorie() {
		// Récuperer l'agent dans la session
		this.categorie = categorieService.updateCategorie(this.categorie);

		if (this.categorie.getIdCategorie() != 0) {
			// recuperer la nouvelle liste de la bd
			this.getAllCategories();
			// this.listeCategories = categorieService.getAllCategorie();
			// Mettre a jour la liste dans la session
			maSession.setAttribute("categoriesList", this.listeCategories);
			return "accueilAdmin";
		} else
			return "accueilAdmin";
	}

	public String supprimerCategorie() {
		System.out.println("ID catégorie :" + this.categorie.getIdCategorie());
		// Récuperer l'agent dans la session
		categorieService.deleteCategorie(this.categorie.getIdCategorie());
		this.getAllCategories();
		maSession.setAttribute("categoriesList", this.listeCategories);
		return "accueilAdmin";
	}

	public String rechercherCategorie() {
		System.out.println("ID Categorie : " + this.categorie.getIdCategorie());
		this.categorie = categorieService.getCategorieById(this.categorie.getIdCategorie());
		System.out.println("Categorie : " + this.categorie);
		if (this.categorie.getIdCategorie() != 0) {
			return "rechercherCategorie";
		} else
			return "rechercherCategorie";

	}

	public String modifLien() {

		// Appel de la methode service
		Categorie cOut = categorieService.getCategorieById(this.categorie.getIdCategorie());

		this.categorie = cOut;
		return "modifCategorie";
	}

	// Cette methode permet de transformer une image UploadFile en byte array
	public void upload(FileUploadEvent event) {
		UploadedFile uploadFile = event.getFile();
		// Recuperer le contenu de l'image en byte array (pixels)
		byte[] contents = uploadFile.getContents();
		System.out.println("----------------   " + contents);
		categorie.setPhoto(contents);
		// Transforme byte array en string (format basé64)
		image = "data:image/png;base64," + Base64.encodeBase64String(categorie.getPhoto());
		
	}

	public void getAllCategories() {
		List<Categorie> listOut = categorieService.getAllCategorie();
		this.listeCategories = new ArrayList<>();

		for (Categorie element : listOut) {
			if (element.getPhoto() == null) {
				element.setImage(null);
			} else {
				element.setImage("data:image/png;base64," + Base64.encodeBase64(element.getPhoto()));
			}
			this.listeCategories.add(element);
		}
	}

}
