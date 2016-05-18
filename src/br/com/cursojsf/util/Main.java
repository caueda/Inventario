package br.com.cursojsf.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

	/**
	 * Configurar o MySQL
	 * mysql -u root -p
	 * mysql> create database iventario;
	 * mysql> grant all on inventario.* to 'aplicacao'@'localhost' identified by 'welcome1';
	 * @param args
	 */
	public static void main(String[] args) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("inventario");

		factory.close();
	}
}
