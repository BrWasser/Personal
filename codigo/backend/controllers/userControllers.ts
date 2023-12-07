import { Request, Response } from 'express';
import UserService from '../timeSeries/services/userService';

const UserController = {
  /**
   * Obtém todos os usuários.
   */
  async getAllUsers(req: Request, res: Response) {
    try {
      const users = await UserService.getAllUsers();
      res.json(users);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Erro ao buscar usuários.' });
    }
  },

  /**
   * Obtém um usuário pelo ID.
   */
  async getUserById(req: Request, res: Response) {
    const userId = req.params.id;
    try {
      const user = await UserService.getUserById(userId);
      if (user) {
        res.json(user);
      } else {
        res.status(404).json({ error: 'Usuário não encontrado.' });
      }
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Erro ao buscar o usuário.' });
    }
  },

  /**
   * Cria um novo usuário.
   */
  async createUser(req: Request, res: Response) {
    const { name, email, password } = req.body;
    try {
      const newUser = await UserService.createUser(name, email, password);
      res.json(newUser);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Erro ao criar um usuário.' });
    }
  },

  /**
   * Atualiza um usuário.
   */
  async updateUser(req: Request, res: Response) {
    const userId = req.params.id;
    const { name, email, password, currentPassword } = req.body;
    try {
      const updatedUser = await UserService.updateUser(userId, name, email, password, currentPassword);
      res.json(updatedUser);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Erro ao atualizar o usuário.' });
    }
  },

  /**
   * deleta um usuário.
   */
  async deleteUser(req: Request, res: Response) {
    const userId = req.params.id;
    const { password } = req.body; // Adicione a senha fornecida no corpo da solicitação
    try {
      await UserService.deleteUser(userId, password);
      res.json({ message: 'Usuário excluído com sucesso.' });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Erro ao excluir o usuário.' });
    }
  },

  /**
   * verifica se o email já está sendo usado.
   */
  async checkEmailAvailability(req: Request, res: Response) {
    const { email } = req.body;
    try {
      const isEmailTaken = await UserService.checkEmailAvailability(email);
      res.json({ isEmailTaken });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Erro ao verificar a disponibilidade do e-mail.' });
    }
  },
};

export default UserController;