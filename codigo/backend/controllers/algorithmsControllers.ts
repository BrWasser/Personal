import { Request, Response, NextFunction } from 'express';
import axios, { AxiosResponse } from 'axios';
import FormData from 'form-data';
import { FileArray, UploadedFile } from 'express-fileupload';
import path from 'path';
import fs from 'fs';
const FormData = require('form-data');

class AlgorithmsController {
  /**
   * Executa o algoritmo PSO.
   * Recebe os arquivos dfTecnicos e dfPedidos no corpo da requisição.
   * Envia os arquivos para o serviço Python e retorna a resposta.
   */
  async runPSO(req: Request, res: Response, next: NextFunction): Promise<void> {
    try {

      // Verifica se os arquivos esperados estão presentes
      const files = req.files as FileArray | undefined;

      if (!files || !files['dfTecnicos'] || !files['dfPedidos']) {
        res.status(400).json({ error: 'Arquivos dfTecnicos e dfPedidos são necessários' });
        return;
      }

      const dfTecnicosFile = files['dfTecnicos'] as UploadedFile;
      const dfPedidosFile = files['dfPedidos'] as UploadedFile;

      const formData = new FormData();
      formData.append('df_tecnicos', dfTecnicosFile[0].buffer, { filename: dfTecnicosFile[0].originalname });
      formData.append('df_pedidos', dfPedidosFile[0].buffer, { filename: dfPedidosFile[0].originalname });

      const pythonServiceResponse = await axios.post('http://127.0.0.1:5000/calcular_distancias', formData);

      console.log(pythonServiceResponse.data);

      const setores = pythonServiceResponse.data[0][0];
      const qtdPedidosSetores = pythonServiceResponse.data[1][0];
      const numTecnicos = pythonServiceResponse.data[2][0];
      const matrizTempoDeslocamento= pythonServiceResponse.data.slice(3)[0][0];

      console.log("numero setores " + setores)
      console.log("numero tecnicos " + numTecnicos);
      console.log("numero qtdPedidosSetores " + qtdPedidosSetores);
      console.log("numero matrizTempoDeslocamentoLista " + matrizTempoDeslocamento);

      const data = {
        n: numTecnicos,
        m: setores,
        pedidos: qtdPedidosSetores,
        tempoPedidos: matrizTempoDeslocamento
      };

      const headers = {
        'Content-Type': 'application/json',
      };

      const javaServiceResponse: AxiosResponse = await axios.post('http://localhost:8080/run-pso', data, { headers });
      console.log(javaServiceResponse.data);

      res.json(javaServiceResponse.data);

    } catch (error) {
      next(error);
    }
  }
}

export default new AlgorithmsController();
