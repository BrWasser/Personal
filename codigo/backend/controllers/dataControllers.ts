import { Request, Response } from 'express';
import { dataService } from '../timeSeries/services/dataService';

class DataController {
  /**
   * Obtém todos os dados.
   */
  async getAllData(req: Request, res: Response) {
    const data = await dataService.getAllData();
    res.json(data);
  }

  /**
   * Obtém os dados por ID.
   */
  async getDataById(req: Request, res: Response) {
    const { id } = req.params;
    const data = await dataService.getDataById(id);
    res.json(data);
  }

  /**
   * Cria novos dados.
   */
  async createData(req: Request, res: Response) {
    const fileName = req.file?.originalname;

    if (!fileName) {
      return res.status(400).json({ error: 'File name is missing' });
    }
    
    const data = await dataService.createData(fileName);
    res.json(data);
  }

  /**
   * Deleta os dados.
   */
  async deleteData(req: Request, res: Response) {
    const { id } = req.params;
    const data = await dataService.deleteData(id);
    res.json(data);
  }
}

export const dataController = new DataController();
