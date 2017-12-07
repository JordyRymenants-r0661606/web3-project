package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.model.Product;

public class ProductDbInMemory implements ProductDb {
	private Map<Integer, Product> records = new HashMap<Integer, Product>();
	
	public ProductDbInMemory () {
		add( new Product("Rose", "Thorny plant", 2.25) );
		add( new Product("Something", "Test", 0.89 ) );
		add( new Product("Another Thing", "Hmm", 3.54) );
		add( new Product("ABC", "Alphabet", 1.75) );
		add( new Product("123", "Counting", 1.57) );
		add( new Product("IDK", "No clue", 5.12) );
		add( new Product("Meow", "Cat", 1.68) );
		add( new Product("Mt Dew", "Drink", 2.10) );
	}
	
	/* (non-Javadoc)
	 * @see db.ProductDb#get(int)
	 */
	@Override
	public Product get(int id){
		if(id < 0){
			throw new DbException("No valid id given");
		}
		return records.get(id);
	}
	
	/* (non-Javadoc)
	 * @see db.ProductDb#getAll()
	 */
	@Override
	public List<Product> getAll(){
		return new ArrayList<Product>(records.values());	
	}

	/* (non-Javadoc)
	 * @see db.ProductDb#add(domain.model.Product)
	 */
	@Override
	public void add(Product product){
		if(product == null){
			throw new DbException("No product given");
		}
		int id = records.size() + 1;
		product.setProductId(id);
		if (records.containsKey(product.getProductId())) {
			throw new DbException("Product already exists");
		}
		records.put(product.getProductId(), product);
	}
	
	/* (non-Javadoc)
	 * @see db.ProductDb#update(domain.model.Product)
	 */
	@Override
	public void update(Product product){
		if(product == null){
			throw new DbException("No product given");
		}
		if(!records.containsKey(product.getProductId())){
			throw new DbException("No product found");
		}
		records.put(product.getProductId(), product);
	}
	
	/* (non-Javadoc)
	 * @see db.ProductDb#delete(int)
	 */
	@Override
	public void delete(int id){
		if(id < 0){
			throw new DbException("No valid id given");
		}
		records.remove(id);
	}
}
