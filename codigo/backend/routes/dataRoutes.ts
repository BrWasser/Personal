import express from 'express';
import { dataController } from '../controllers/dataControllers';
import multer from 'multer';

const router = express.Router();

// Configurando o multer com um armazenamento personalizado
const storage = multer.diskStorage({
  destination: 'public/',  // Especificando a pasta de destino
  filename: (req, file, cb) => {
    cb(null, file.originalname);  // Usando o nome original do arquivo
  },
});
const upload = multer({ storage: storage });

/**
 * Define uma rota GET para /.
 * Esta rota chama a função obterTodosDados do controlador de dados.
 */
router.get('/', dataController.getAllData);

/**
 * Define uma rota GET para /:id.
 * Esta rota chama a função obterDadosPorId do controlador de dados.
 */
router.get('/:id', dataController.getDataById);

/**
 * Define uma rota POST para /.
 * Esta rota usa o middleware multer para o upload de um arquivo e, em seguida, chama a função criarDados do controlador de dados.
 */
router.post('/', upload.single('file'), dataController.createData);

/**
 * Define uma rota DELETE para /:id.
 * Esta rota chama a função deletarDados do controlador de dados.
 */
router.delete('/:id', dataController.deleteData);

export default router;
