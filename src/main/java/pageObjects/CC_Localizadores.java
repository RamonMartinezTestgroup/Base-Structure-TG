package main.java.pageObjects;
//Dentro de esta clase se almacenaran todos los localizadores que se ocuparan.
public interface CC_Localizadores {

	//Login
	String inputUsuario = "//*[@id=\"user-name\"]"; //XPATH
	String inputContrasena = "//*[@id=\"password\"]"; //XPATH
	String buttonIniciarSesion = "//*[@id=\"login-button\"]"; //XPATH
	
	//Lista de Productos
	String labelPaginaProductos = "//*[@id=\"header_container\"]/div[2]/span";
	String buttonAgregarCarro = "//html/body/div/div/div/div[2]/div/div/div/div[0]/div[2]/div[2]/button";
	String buttonCarroCompras = "//*[@id=\"shopping_cart_container\"]/a";
	String labelNumeroCarrito = "//*[@id=\"shopping_cart_container\"]/a/span";
	String listProductos = "//html/body/div/div/div/div[2]/div/div/div/div/div/div/a/div";
}