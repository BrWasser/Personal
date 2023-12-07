import DataService from '../timeSeries/services/dataService';
import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

describe('DataService', () => {
  afterAll(async () => {
    await prisma.$disconnect();
  });

  // Testar se é possível obter todos os dados
  it('deve obter todos os dados', async () => {
    const data = await DataService.getAllData();
    expect(data).toBeDefined();
  });

  // Testar se novos dados podem ser criados com sucesso
  it('deve criar novos dados', async () => {
    const novoArquivo = Buffer.from('dados de exemplo');
    const novoNomeArquivo = `arquivoteste_${Date.now()}.txt`;

    const novosDados = await DataService.createData(novoArquivo, novoNomeArquivo);
    expect(novosDados).toBeDefined();
  });

  // Testar se é possível obter dados por ID
  it('deve obter dados por ID', async () => {
    // Supondo que haja pelo menos uma entrada de dados no banco de dados
    const entradasDados = await prisma.data.findMany();
    const idDados = entradasDados[0]?.id;

    if (!idDados) {
      throw new Error('Nenhum dado encontrado para obter por ID.');
    }

    const dados = await DataService.getDataById(idDados);
    expect(dados).toBeDefined();
  });

  // Testar a atualização das informações dos dados
  it('deve atualizar as informações dos dados', async () => {
    // Supondo que haja pelo menos uma entrada de dados no banco de dados
    const entradasDados = await prisma.data.findMany();
    const idDados = entradasDados[0]?.id;

    if (!idDados) {
      throw new Error('Nenhum dado encontrado para atualizar.');
    }

    const arquivoAtualizado = Buffer.from('dados atualizados');
    const nomeArquivoAtualizado = `arquivo_atualizado_${Date.now()}.txt`;

    const dadosAtualizados = await DataService.updateData(idDados, arquivoAtualizado, nomeArquivoAtualizado);
    expect(dadosAtualizados).toBeDefined();
  });

  // Testar a exclusão de dados
  it('deve excluir dados', async () => {
    // Supondo que haja pelo menos uma entrada de dados no banco de dados
    const entradasDados = await prisma.data.findMany();
    const idDados = entradasDados[0]?.id;

    if (!idDados) {
      throw new Error('Nenhum dado encontrado para excluir.');
    }

    await DataService.deleteData(idDados);

    // Verificar se os dados foram excluídos
    const dadosExcluidos = await DataService.getDataById(idDados);
    expect(dadosExcluidos).toBeNull();
  });
});
