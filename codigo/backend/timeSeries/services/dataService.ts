import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

class DataService {
  async getAllData() {
    return prisma.data.findMany();
  }

  async getDataById(id: string) {
    return prisma.data.findUnique({
      where: { id },
    });
  }

  async createData(fileName: string) {
    const createdData = await prisma.data.create({
      data: {
        fileName,
      },
    });

    return createdData;
  }

  async deleteData(id: string) {
    return prisma.data.delete({
      where: { id },
    });
  }
}

export const dataService = new DataService();
