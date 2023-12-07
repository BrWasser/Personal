package com.consilium.consiulium;

// Importe as classes necessárias do Spring Boot
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.consilium.consiulium.Algorithm.PSO.ResultContainer;

import java.util.List;

import com.consilium.consiulium.Dto.PSORequest;
import com.consilium.consiulium.Algorithm.PSO;

@SpringBootApplication
public class ConsiuliumApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsiuliumApplication.class, args);
    }
}

// Adicione um controlador REST para processar solicitações
@RestController
class PSOController {

    // Mapeie a rota para aceitar solicitações POST JSON
    @PostMapping("/run-pso")
    public ResultContainer runPSO(@RequestBody PSORequest psoRequest) {
        // Extraia os parâmetros da solicitação e chame a função runPSO
        int n = psoRequest.getN();
        int m = psoRequest.getM();
        double[] pedidos = psoRequest.getPedidos();
        double[][] tempoPedidos = psoRequest.getTempoPedidos();

        return PSO.runPSO(n, m, pedidos, tempoPedidos);
    }
}