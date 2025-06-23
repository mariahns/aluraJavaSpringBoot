package br.com.alura.screenmatch;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.screenmatch.principal.Principal;
import br.com.alura.screenmatch.principal.PrincipalOld;


@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//PrincipalOld principal = new PrincipalOld();
		Principal principal = new Principal();
		principal.exibeMenu();			

	}
}
