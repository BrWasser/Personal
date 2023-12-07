/**
 * Este é o arquivo principal do servidor para nossa aplicação.
 * Ele configura e inicializa o servidor express, incluindo middleware e rotas.
 * Também inicia o servidor ouvindo em uma porta especificada.
 */

// Importa os módulos necessários

import express from 'express';
import bodyParser from 'body-parser';
import cors from 'cors'; 

// Importa as rotas
import userRoutes from './routes/userRoutes';
import dataRoutes from './routes/dataRoutes';
import algorithmsRoutes from './routes/algorithmsRoutes';

// Cria uma aplicação express
const app = express();

const corsOptions = {
  origin: 'http://localhost:4000',
  optionsSeccessStatus: 200
}

app.use(cors(corsOptions));

/**
 * Usa o middleware body-parser para analisar corpos de solicitação JSON
 */
app.use(bodyParser.json());

/**
 * Usa o middleware cors para habilitar o compartilhamento de recursos de origem cruzada
 */
app.use(cors());

// Define a porta em que o servidor irá ouvir
const port = process.env.PORT || 3000;

/**
 * Usa o middleware rotasUsuarios para solicitações para /users
 */
app.use('/users', userRoutes);

/**
 * Usa o middleware rotasDados para solicitações para /dados
 */
app.use('/data', dataRoutes);

/**
 * Usa o middleware rotasAlgoritmos para solicitações para /algoritmos
 */
app.use('/algorithms', algorithmsRoutes);

/**
 * Inicia o servidor ouvindo na porta especificada
 */
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
