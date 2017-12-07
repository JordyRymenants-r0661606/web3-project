package domain;

import java.util.List;
import java.util.Properties;

import db.PersonDb;
import db.PersonDbInDatabase;
import db.PersonDbInMemory;
import db.ProductDb;
import db.ProductDbInDatabase;
import db.ProductDbInMemory;
import domain.model.Person;
import domain.model.Product;

public class ShopService {
	private PersonDb personDb;
	private ProductDb productDb;

	public ShopService( Properties properties ){
		try {
			//personDb = new PersonDbInMemory();
			personDb = new PersonDbInDatabase( properties );
			
			//productDb = new ProductDbInMemory();
			productDb = new ProductDbInDatabase( properties );
		} catch( Exception e ) {
			System.out.println( e.getMessage() );
		}
	}
	
	public Person getPerson(String personId) {
		return getPersonDb().get(personId);
	}

	public List<Person> getPersons() {
		return getPersonDb().getAll();
	}

	public void addPerson(Person person) {
		getPersonDb().add(person);
	}

	public void updatePersons(Person person) {
		getPersonDb().update(person);
	}

	public void deletePerson(String id) {
		getPersonDb().delete(id);
	}

	private PersonDb getPersonDb() {
		return personDb;
	}
	
	public Product getProduct(int productId) {
		return getProductDb().get(productId);
	}
	
	public List<Product> getProducts() {
		return getProductDb().getAll();
	}
	
	public void addProduct(Product product) {
		getProductDb().add(product);
	}
	
	public void updateProducts(Product product){
		getProductDb().update(product);
	}
	
	public void deleteProduct(int id){
		getProductDb().delete(id);
	}
	
	public ProductDb getProductDb() {
		return productDb;
	}
}
