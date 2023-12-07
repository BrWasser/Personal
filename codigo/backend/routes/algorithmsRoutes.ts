/**
 * Este módulo define as rotas para os algoritmos.
 * Ele importa os módulos necessários e define uma rota POST para o algoritmo PSO.
 */

// Importa os módulos necessários
import express, { Router } from 'express';
import multer from 'multer';
import algorithmsController from '../controllers/algorithmsControllers';

// Cria um roteador express
const router: Router = express.Router();

// Configura o multer para o upload de arquivos
const upload = multer();

/**
 * Define uma rota POST para /pso.
 * Esta rota usa o middleware multer para o upload de dois arquivos e, em seguida, chama a função runPSO do controlador de algoritmos.
 */
router.post('/pso', upload.fields([{ name: 'dfTecnicos', maxCount: 1 }, { name: 'dfPedidos', maxCount: 1 }]), algorithmsController.runPSO);

// Exporta o roteador
export default router;
