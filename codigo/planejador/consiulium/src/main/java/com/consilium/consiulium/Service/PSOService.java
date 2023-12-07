package com.consilium.consiulium.Service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.consilium.consiulium.Dto.PSORequest;
import com.consilium.consiulium.Algorithm.PSO;
import com.consilium.consiulium.Algorithm.PSO.ResultContainer;

@Service
public class PSOService {

    public ResultContainer runPSO(int n, int m, double[] pedidos, double[][] tempoPedidos) {
        return PSO.runPSO(n, m, pedidos, tempoPedidos);
    }
}

