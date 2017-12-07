package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.model.Product;

public class ProductDbInDatabase implements ProductDb {

	Properties properties;
	
	public ProductDbInDatabase( Properties properties ) {
		try {
			Class.forName( "org.postgresql.Driver" );
			this.properties = properties;
		} catch( Exception e ) {
			throw new DbException( e.getMessage(), e );
		}
	}
	
	/* (non-Javadoc)
	 * @see db.ProductDb#get(int)
	 */
	@Override
	public Product get(int id){
		if(id < 0){
			throw new DbException("No valid id given");
		}

		Product product = null;
		
		try {
			Connection connection = DriverManager.getConnection( properties.getProperty( "url" ), properties );
			PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM product WHERE product_id=?"
			);
			statement.setInt( 1, id );
			ResultSet result = statement.executeQuery();

			result.next();
			product = new Product(
				result.getInt( "product_id" ),
				result.getString( "name" ),
				result.getString( "description" ),
				result.getDouble( "price" )
			);

			statement.close();
			connection.close();
		} catch( Exception e ) {
			throw new DbException( "Database Error", e );
		}
		
		return product;
	}
	
	/* (non-Javadoc)
	 * @see db.ProductDb#getAll()
	 */
	@Override
	public List<Product> getAll(){
		ArrayList<Product> products = new ArrayList<>();
		
		try {
			Connection connection = DriverManager.getConnection( properties.getProperty( "url" ), properties );
			PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM product"
			);
			ResultSet result = statement.executeQuery();

			while( result.next() ) {
				products.add( new Product(
					result.getInt( "product_id" ),
					result.getString( "name" ),
					result.getString( "description" ),
					result.getDouble( "price" )
				) );
			}

			statement.close();
			connection.close();
		} catch( Exception e ) {
			throw new DbException( "Database Error", e );
		}
		
		return products;
	}

	/* (non-Javadoc)
	 * @see db.ProductDb#add(domain.model.Product)
	 */
	@Override
	public void add(Product product){
		if(product == null){
			throw new DbException("No product given");
		}

		try {
			Connection connection = DriverManager.getConnection( properties.getProperty( "url" ), properties );
			PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO product( name, description, price )" +
				"VALUES( ?, ?, ? )"
			);
			statement.setString( 1, product.getName() );
			statement.setString( 2, product.getDescription() );
			statement.setDouble( 3, product.getPrice() );
			statement.executeUpdate();
			
			statement.close();
			connection.close();
		} catch( Exception e ) {
			throw new DbException( "Database Error", e );
		}
	}
	
	/* (non-Javadoc)
	 * @see db.ProductDb#update(domain.model.Product)
	 */
	@Override
	public void update(Product product){
		if(product == null){
			throw new DbException("No product given");
		}
		try {
			Connection connection = DriverManager.getConnection( properties.getProperty( "url" ), properties );
			PreparedStatement statement = connection.prepareStatement(
				"UPDATE product SET name=?, description=?, price=?" +
				"WHERE product_id=?"
			);
			statement.setString( 1, product.getName() );
			statement.setString( 2, product.getDescription() );
			statement.setDouble( 3, product.getPrice() );
			statement.setInt( 4, product.getProductId() );
			statement.executeUpdate();

			statement.close();
			connection.close();
		} catch( Exception e ) {
			throw new DbException( "Database Error", e );
		}
	}
	
	/* (non-Javadoc)
	 * @see db.ProductDb#delete(int)
	 */
	@Override
	public void delete(int id){
		if(id < 0){
			throw new DbException("No valid id given");
		}
		
		try {
			Connection connection = DriverManager.getConnection( properties.getProperty( "url" ), properties );
			PreparedStatement statement = connection.prepareStatement(
				"DELETE FROM product WHERE product_id=?"
			);
			statement.setInt( 1, id );
			statement.executeUpdate();
			
			statement.close();
			connection.close();
		} catch( Exception e ) {
			throw new DbException( "Database Error", e );
		}
	}
}
