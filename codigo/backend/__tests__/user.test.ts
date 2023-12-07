import UserService from '../timeSeries/services/userService';
import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

describe('UserService', () => {
  afterAll(async () => {
    await prisma.$disconnect();
  });

  // Teste para garantir que todos os usuários possam ser obtidos
  it('deve obter todos os usuários', async () => {
    const users = await UserService.getAllUsers();
    expect(users).toBeDefined();
  });

  // Teste para garantir que um novo usuário pode ser criado com sucesso
  it('deve criar um novo usuário', async () => {
    const uniqueEmail = `testuser_${Date.now()}@example.com`;
    const newUser = await UserService.createUser('John Doe', uniqueEmail, 'password');
    expect(newUser).toBeDefined();
  });

  // Teste para garantir que um usuário pode ser obtido por ID
  it('deve obter usuário por ID', async () => {
    // Obtém todos os usuários do banco de dados
    const users = await prisma.user.findMany();

    // Seleciona o ID do primeiro usuário (se existir)
    const userId = users[0]?.id;

    // Obtém o usuário usando o ID
    const user = await UserService.getUserById(userId);
    expect(user).toBeDefined();
  });

  // Teste para verificar a disponibilidade de e-mail
  it('deve verificar a disponibilidade do e-mail', async () => {
    const email = 'testemail@example.com';
    const isEmailAvailable = await UserService.checkEmailAvailability(email);
    expect(isEmailAvailable).toBeDefined();
  });

  // Teste para atualizar as informações de um usuário
  it('deve atualizar as informações do usuário', async () => {
    // Assumindo que existe pelo menos um usuário no banco de dados
    const users = await prisma.user.findMany();
    const userId = users[0]?.id;

    // Certifique-se de que userId existe antes de chamar o updateUser
    if (!userId) {
      throw new Error('Nenhum usuário encontrado para atualizar.');
    }

    const user = await UserService.getUserById(userId);

    // Simula a atualização do usuário com uma nova senha
    const updatedUser = await UserService.updateUser(
      userId,
      'Novo Nome',
      'novoemail@example.com',
      'novasenha',
      'senha123'
    );

    expect(updatedUser).toBeDefined();
  });

  // Teste para excluir um usuário
  it('deve excluir um usuário', async () => {
    // Assumindo que existe pelo menos um usuário no banco de dados
    const users = await prisma.user.findMany();
    const userId = users[0]?.id;

    // Certifique-se de que userId existe antes de chamar o deleteUser
    if (!userId) {
      throw new Error('Nenhum usuário encontrado para excluir.');
    }

    await UserService.deleteUser(userId, 'senha123');

    // Verifique se o usuário foi excluído
    const deletedUser = await UserService.getUserById(userId);
    expect(deletedUser).toBeNull();
  });
});