package ui.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Properties;

import domain.DomainException;
import domain.ShopService;
import domain.model.Person;
import domain.model.Product;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ShopService shopService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	
    	ServletContext context = getServletContext();
    	
    	Properties properties = new Properties();
    	Enumeration<String> parametersNames = context.getInitParameterNames();
    	while( parametersNames.hasMoreElements() ) {
    		String propertyName = parametersNames.nextElement();
    		properties.setProperty( propertyName, context.getInitParameter( propertyName ) );
    	}
		
		shopService = new ShopService( properties );
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = "index.jsp";
		String action = request.getParameter( "action" );
		int productId;
		
		if( action != null ) {
			switch( action ) {
			case "overview":
				request.setAttribute( "users", shopService.getPersons() );
				page = "personoverview.jsp";
				break;
			
			case "products":
				request.setAttribute( "products", shopService.getProducts() );
				page = "products.jsp";
				break;
				
			case "addProduct":
				page = "addProduct.jsp";
				break;
				
			case "updateProduct":
				productId = Integer.parseInt( request.getParameter( "productId" ) );
				request.setAttribute( "product", shopService.getProduct( productId ) );
				page = "updateProduct.jsp";
				break;
				
			case "deleteProduct":
				productId = Integer.parseInt( request.getParameter( "productId" ) );
				request.setAttribute( "product", shopService.getProduct( productId ) );
				page = "deleteProductConfirmation.jsp";
				break;
				
			case "deletePerson":
				request.setAttribute( "user", shopService.getPerson( request.getParameter( "userid" ) ) );
				page = "deletePersonConfirmation.jsp";
				break;
				
			case "checkPassword":
				request.setAttribute( "userid", request.getParameter( "userid" ) );
				page = "checkPassword.jsp";
				break;
			
			case "signUp":
				page = "signUp.jsp";
				break;
				
			case "index":
				page = "index.jsp";
				break;
				
			default:
				throw new DomainException( "Requested page not found" );
			}
		}
		
		request.getRequestDispatcher( page ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter( "action" );
		
		switch( action ) {
		case "signUp":
			signUp( request, response );
			break;
			
		case "addProduct":
			addProduct( request, response );
			break;
			
		case "updateProduct":
			updateProduct( request, response );
			break;
			
		case "deleteProductConfirm":
			deleteProduct( request, response );
			break;
			
		case "deletePersonConfirm":
			deletePerson( request, response );
			break;
			
		case "checkPassword":
			try {
				checkPassword( request, response );
			} catch( NoSuchAlgorithmException e ) {
				throw new DomainException( e.getMessage() );
			}
			break;
		}
	}
	
	private void signUp( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		ArrayList<String> errors = new ArrayList<>();
		
		String userid = request.getParameter( "userid" );
		String firstName = request.getParameter( "firstName" );
		String lastName = request.getParameter( "lastName" );
		String email = request.getParameter( "email" );
		String password = request.getParameter( "password" );
		
		Person person = new Person();
		
		try {
			person.setUserid( userid );
		} catch( Exception e ) {
			errors.add( e.getMessage() );
		}
		try {
			person.setFirstName( firstName );
		} catch( Exception e ) {
			errors.add( e.getMessage() );
		}
		try {
			person.setLastName( lastName );
		} catch( Exception e ) {
			errors.add( e.getMessage() );
		}
		try {
			person.setEmail( email );
		} catch( Exception e ) {
			errors.add( e.getMessage() );
		}
		try {
			person.setPasswordHashed( password );
		} catch( Exception e ) {
			errors.add( e.getMessage() );
		}
		
		if( errors.size() <= 0 ) {
			try {
				shopService.addPerson( person );
			} catch( Exception e ) {
				errors.add( e.getMessage() );
			}
		}
		
		if( errors.size() > 0 ) {
			request.setAttribute( "errors", errors );
			request.setAttribute( "user", person );
			request.getRequestDispatcher( "signUp.jsp" ).forward( request, response );
		} else {
			request.getRequestDispatcher( "index.jsp" ).forward( request, response );
		}
	}

	private void addProduct( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		ArrayList<String> errors = new ArrayList<>();
		
		String name = request.getParameter( "name" );
		String description = request.getParameter( "description" );
		String price = request.getParameter( "price" );
		
		Product product = new Product();
		
		try {
			product.setName( name );
		} catch( Exception e ) {
			errors.add( e.getMessage() );
		}
		try {
			product.setDescription( description );
		} catch( Exception e ) {
			errors.add( e.getMessage() );
		}
		try {
			product.setPrice( price );
		} catch( Exception e ) {
			errors.add( e.getMessage() );
		}
		
		if( errors.size() <= 0 ) {
			try {
				shopService.addProduct( product );
			} catch( Exception e ) {
				errors.add( e.getMessage() );
			}
		}
		
		if( errors.size() > 0 ) {
			request.setAttribute( "errors", errors );
			request.setAttribute( "product", product );
			request.getRequestDispatcher( "addProduct.jsp" ).forward( request, response );
		} else {
			request.getRequestDispatcher( "index.jsp" ).forward( request, response );
		}
	}

	private void updateProduct( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		ArrayList<String> errors = new ArrayList<>();
		
		String productId = request.getParameter( "productId" );
		String name = request.getParameter( "name" );
		String description = request.getParameter( "description" );
		String price = request.getParameter( "price" );
		
		Product product = new Product();
		
		try {
			product.setProductId( Integer.parseInt( productId ) );
		} catch( NumberFormatException e ) {
			errors.add( "Product ID is not a valid number" );
		} catch( Exception e ) {
			errors.add( e.getMessage() );
		}
		try {
			product.setName( name );
		} catch( Exception e ) {
			errors.add( e.getMessage() );
		}
		try {
			product.setDescription( description );
		} catch( Exception e ) {
			errors.add( e.getMessage() );
		}
		try {
			product.setPrice( price );
		} catch( Exception e ) {
			errors.add( e.getMessage() );
		}
		
		if( errors.size() <= 0 ) {
			try {
				shopService.updateProducts( product );
			} catch( Exception e ) {
				errors.add( e.getMessage() );
			}
		}
		
		if( errors.size() > 0 ) {
			request.setAttribute( "errors", errors );
			request.setAttribute( "product", product );
			request.getRequestDispatcher( "updateProduct.jsp" ).forward( request, response );
		} else {
			request.setAttribute( "products", shopService.getProducts() );
			request.getRequestDispatcher( "products.jsp" ).forward( request, response );
		}
	}
	
	private void deleteProduct( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		if( request.getParameter( "delete" ).equals( "Yes" ) ) {
			try {
				shopService.deleteProduct( Integer.parseInt( request.getParameter( "productId" ) ) );
			} catch( Exception e ) {}
		}
		
		request.setAttribute( "products", shopService.getProducts() );
		request.getRequestDispatcher( "products.jsp" ).forward( request, response );
	}
	
	private void deletePerson( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		if( request.getParameter( "delete" ).equals( "Yes" ) ) {
			try {
				shopService.deletePerson( request.getParameter( "userid" ) );
			} catch( Exception e ) {}
		}
		
		request.setAttribute( "users", shopService.getPersons() );
		request.getRequestDispatcher( "personoverview.jsp" ).forward( request, response );
	}
	
	private void checkPassword( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException, NoSuchAlgorithmException {
		Person person = shopService.getPerson( request.getParameter( "userid" ) );
		String message;
		
		if( person.isCorrectPassword( request.getParameter( "password" ) ) ) {
			message = "The password is correct.";
		} else {
			message = "The password is NOT correct.";
		}
		
		request.setAttribute( "message", message );
		request.getRequestDispatcher( "checkPassword.jsp" ).forward( request, response );
	}
}
