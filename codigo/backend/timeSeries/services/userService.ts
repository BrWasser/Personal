import { PrismaClient } from '@prisma/client';
import bcrypt from 'bcrypt';

const prisma = new PrismaClient();
const saltRounds = 5; // Número de rounds de hashing

const UserService = {
  getAllUsers() {
    return prisma.user.findMany();
  },

  async createUser(name: string, email: string, password: string) {

    // Hash da senha
    const hashedPassword = await bcrypt.hash(password, saltRounds);

    return prisma.user.create({
      data: {
        email,
        password: hashedPassword,
      },
    });
  },

  async getUserById(userId: string) {
    const user = await prisma.user.findUnique({
      where: {
        id: userId,
      },
    });
    return user;
  },

  async updateUser(userId: string, name: string, email: string, password: string, currentPassword: string) {
    const user = await prisma.user.findUnique({
      where: {
        id: userId,
      },
    });
  
    if (!user) {
      throw new Error("Usuário não encontrado");
    }
  
    // Verificar se a senha atual está correta
    const isPasswordValid = await bcrypt.compare(currentPassword, user.password);
    if (!isPasswordValid) {
      throw new Error("Senha atual incorreta");
    }
  
    // Hash da nova senha, se fornecida
    let hashedPassword = user.password; // Mantenha a senha atual se nenhuma nova senha for fornecida
    if (password) {
      hashedPassword = await bcrypt.hash(password, saltRounds);
    }
  
    return prisma.user.update({
      where: {
        id: userId,
      },
      data: {
        email,
        password: hashedPassword,
      },
    });
  }
  ,

  deleteUser(userId: string, password: string) {
    return prisma.user.findUnique({
      where: {
        id: userId,
      },
    }).then((user) => {
      if (!user) {
        throw new Error("Usuário não encontrado");
      }

      // Verifique se a senha fornecida corresponde à senha armazenada no banco de dados.
      return bcrypt.compare(password, user.password).then((passwordMatch: boolean) => { // Adicione a tipagem 'boolean' aqui
        if (passwordMatch) {
          // As senhas correspondem, exclua o usuário.
          return prisma.user.delete({
            where: {
              id: userId,
            },
          });
        } else {
          throw new Error("Senha incorreta");
        }
      });
    });
  },

  async checkEmailAvailability(email: string) {
    const existingUser = await prisma.user.findUnique({
      where: {
        email: email,
      },
    });
    return !!existingUser;
  },
};

export default UserService;