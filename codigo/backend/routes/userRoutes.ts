import { Router } from 'express';
import UserController from '../controllers/userControllers';

const router = Router();

// Rota para listar todos os usuários
router.get('/', UserController.getAllUsers);

// Rota para criar um novo usuário
router.post('/', UserController.createUser);

// Rota para verificar se o email já está sendo usado
router.post('/checkEmail', UserController.checkEmailAvailability);

// Rota para listar um usuário pelo ID
router.get('/:id', UserController.getUserById);

// Rota para atualizar um usuário
router.put('/:id', UserController.updateUser);

// Rota para excluir um usuário
router.delete('/:id', UserController.deleteUser);

export default router;