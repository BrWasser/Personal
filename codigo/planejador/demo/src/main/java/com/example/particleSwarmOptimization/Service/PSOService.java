package com.example.particleSwarmOptimization.Service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.example.particleSwarmOptimization.Algorithm.PSO;

@Service
public class PSOService {

    public List<List<Integer>> runPSO(int n, int m, double[] pedidos, double[][] tempoPedidos) {
        return PSO.runPSO(n, m, pedidos, tempoPedidos);
    }
}

