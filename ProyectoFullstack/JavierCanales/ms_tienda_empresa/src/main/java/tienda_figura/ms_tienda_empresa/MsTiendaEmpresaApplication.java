package tienda_figura.ms_tienda_empresa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsTiendaEmpresaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsTiendaEmpresaApplication.class, args);
	}

}
