package br.com.cursojsf.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import br.com.cursojsf.ejb.login.LoginBean;
import br.com.cursojsf.entities.Usuario;


public class AbstractTest {
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		File inventarioProjectFolder = new File(System.getProperty("user.dir"));
		File classes = new File(inventarioProjectFolder.getAbsolutePath() + "/build/classes");
		Map properties = new HashMap();
		properties.put ("org.glassfish.ejb.embedded.glassfish.installation.root", "/home/caueda/Java/glassfish4/glassfish");  
//		properties.put(EJBContainer.MODULES, classes);
//		properties.put(EJBContainer.APP_NAME, "Inventario");
		EJBContainer ejbC = EJBContainer.createEJBContainer(properties);
		Context context = ejbC.getContext();
		String name = "java:global/Inventario/LoginBean";
		LoginBean bean = (LoginBean)context.lookup(name);
		Usuario usuario = bean.remoteLogin("aplicacao@test.com", "welcome1");
		System.out.println(usuario != null);
	}

}
