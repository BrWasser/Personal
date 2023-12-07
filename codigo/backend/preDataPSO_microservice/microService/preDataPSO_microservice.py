from flask import Flask, request, jsonify
import pandas as pd
import numpy as np
from math import radians, sin, cos, sqrt, atan2
import openrouteservice
import requests
from werkzeug.utils import secure_filename
import os

import asyncio
import aiohttp

def haversine_distance(coord1, coord2):
    R = 6371.0

    # Converte as coordenadas de graus para radianos
    lat1, lon1 = radians(coord1[1]), radians(coord1[0])
    lat2, lon2 = radians(coord2[1]), radians(coord2[0])

    # Diferença nas coordenadas
    dlon = lon2 - lon1
    dlat = lat2 - lat1

    # Fórmula de Haversine
    a = sin(dlat / 2)**2 + cos(lat1) * cos(lat2) * sin(dlon / 2)**2
    c = 2 * atan2(sqrt(a), sqrt(1 - a))

    # Distância em quilômetros
    distance = R * c

    return distance

async def matTempo(coordTec, coordServ):
    
    mat = []
    tecJa = 0
    #A requisição é feita por técnico
    while tecJa < len(coordTec.index):
        batch = 0
        batch_times = []
        tecRe = 0
        if len(coordTec.index) - tecJa >= 50:
            tecRe = 50 
        else: 
            tecRe = len(coordTec.index) - tecJa
        strCood = ""
        strDest = ''
        strSour = ''
        nBatch = 1
        for i in range(1,len(coordServ.index)):
            if len(coordServ.index)/i <= 50:
                nBatch = i
                break
            else:
                continue
        #Como são quase 10.000 serviços e o número máximo de pontos na URL da API é próximo de 100, dividimos em 100 requisições por técnico
        while batch < nBatch:
            for a in range(tecRe+int(len(coordServ.index)/nBatch)):
                #Recebe a localização do técnico do técnico
                if a < tecRe:
                    idR = tecJa + a
                    laCheck = coordTec.at[idR,'Latitude']/1000000 if coordTec.at[idR,'Latitude'] > 180 or coordTec.at[idR,'Latitude'] < -180 else coordTec.at[idR,'Latitude']
                    loCheck = coordTec.at[idR,'Longitude']/1000000 if coordTec.at[idR,'Longitude'] > 180 or coordTec.at[idR,'Longitude'] < -180 else coordTec.at[idR,'Longitude']
                    strCood += str(loCheck) + "," + str(laCheck) + ";" 
                    strSour += str(a) + ';'
                #Recebe a localização dos serviços
                else: 
                    idR = a + batch*int(len(coordServ.index)/nBatch) - tecRe
                    laCheck = coordServ.at[idR,'latitude']/1000000 if coordServ.at[idR,'latitude'] > 180 or coordServ.at[idR,'latitude'] < -180 else coordServ.at[idR,'latitude']
                    loCheck = coordServ.at[idR,'longitude']/1000000 if coordServ.at[idR,'longitude'] > 180 or coordServ.at[idR,'longitude'] < -180 else coordServ.at[idR,'longitude']
                    strDest += str(a) + ";"
                    strCood += str(loCheck) + "," + str(laCheck) + ";" 
            
            strDest = strDest[:-1]
            strCood = strCood[:-1]
            strSour = strSour[:-1]
            url = 'http://router.project-osrm.org/table/v1/driving/'+ strCood +'?sources=' + strSour + '&destinations=' + strDest
            strDest = ''
            strCood = ''
            strSour = ''
            #Requisisção asíncrona que envia as localizações e retorna um array com os tempos, que são convertidos para minutos
            try:
                async with aiohttp.ClientSession() as session:
                    async with session.get(url) as response:
                        res = await response.json()
                        res = res['durations']
                        batch_times.append(res)
            except Exception as e:
                e_type, e_object, e_traceback = sys.exc_info()

                e_filename = os.path.split(
                                    e_traceback.tb_frame.f_code.co_filename
                                )[1]

                e_message = str(e)

                e_line_number = e_traceback.tb_lineno

                print(f'exception type: {e_type}')

                print(f'exception filename: {e_filename}')

                print(f'exception line number: {e_line_number}')

                print(f'exception message: {e_message}')
                return jsonify({'error': str(e)})
            batch+=1

        #Verifica os serviços não checados pelas batches anteriores
        resto = len(coordServ.index) - int(len(coordServ.index)/nBatch)*nBatch
        if resto > 0:
            for a in range(tecRe+resto):
                if a < tecRe:
                    idR = tecJa + a
                    laCheck = coordTec.at[idR,'Latitude']/1000000 if coordTec.at[idR,'Latitude'] > 180 or coordTec.at[idR,'Latitude'] < -180 else coordTec.at[idR,'Latitude']
                    loCheck = coordTec.at[idR,'Longitude']/1000000 if coordTec.at[idR,'Longitude'] > 180 or coordTec.at[idR,'Longitude'] < -180 else coordTec.at[idR,'Longitude']
                    strCood += str(loCheck) + "," + str(laCheck) + ";" 
                    strSour += str(a) + ';'
                    #Recebe a localização dos serviços
                else: 
                    idR = len(coordServ.index) - (a - tecRe) 
                    laCheck = coordServ.at[idR,'latitude']/1000000 if coordServ.at[idR,'latitude'] > 180 or coordServ.at[idR,'latitude'] < -180 else coordServ.at[idR,'latitude']
                    loCheck = coordServ.at[idR,'longitude']/1000000 if coordServ.at[idR,'longitude'] > 180 or coordServ.at[idR,'longitude'] < -180 else coordServ.at[idR,'longitude']
                    strDest += str(a) + ";"
                    strCood += str(loCheck) + "," + str(laCheck) + ";" 
                
                strDest = strDest[:-1]
                strCood = strCood[:-1]
                strSour = strSour[:-1]
                url = 'http://router.project-osrm.org/table/v1/driving/'+ strCood +'?sources=' + strSour + '&destinations=' + strDest
                strDest = ''
                strCood = ''
                strSour = ''
                #Requisisção asíncrona que envia as localizações e retorna um array com os tempos, que são convertidos para minutos
                try:
                    async with aiohttp.ClientSession() as session:
                        async with session.get(url) as response:
                            res = await response.json()
                            res = res['durations']
                            batch_times.append(res)
                except Exception as e:
                    e_type, e_object, e_traceback = sys.exc_info()

                    e_filename = os.path.split(
                                        e_traceback.tb_frame.f_code.co_filename
                                    )[1]

                    e_message = str(e)

                    e_line_number = e_traceback.tb_lineno

                    print(f'exception type: {e_type}')

                    print(f'exception filename: {e_filename}')

                    print(f'exception line number: {e_line_number}')

                    print(f'exception message: {e_message}')
                    return jsonify({'error': str(e)})
                
        #Adiciona todas as requisições de um técnico na matriz, concatenando os resultados das requisições
        if len(batch_times) == 1:
            for i in batch_times:
                for j in i:
                    mat.append(list(np.array(j)/60))
        else:
            for i in range(len(batch_times[0])):
                mat_aux = []
                for j in batch_times:
                    mat_aux.append(np.array(j[i])/60)
                mat.append(list(np.concatenate(mat_aux)))
                
        tecJa += tecRe

    return mat



async def funcao(df_tecnicos, df_pedidos): 

    df_pedidos = df_pedidos[['latitude', 'longitude', 'setor', 'id_venda']]
    df_tecnicos = df_tecnicos[['Nome','Latitude', 'Longitude']]

    #quantidade de pedidos em cada setor
    qtd_pedidos_setores = []
    for setor in df_pedidos.setor.unique():
        qtd_pedidos_setores.append(len(df_pedidos.query(f"setor == '{setor}'")))

    matriz_tempo_deslocamento = await matTempo(df_tecnicos,df_pedidos)

    print("Matrz de tempos de deslocamento concluída")
    
    return [[df_pedidos.setor.unique().size], [qtd_pedidos_setores], [len(df_tecnicos)], [matriz_tempo_deslocamento]]

app = Flask(__name__)

UPLOAD_FOLDER = './uploads'
ALLOWED_EXTENSIONS = {'csv', 'txt', 'xlsx'}

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

def process_uploaded_file(file):
    if file and allowed_file(file.filename):
        filename = secure_filename(file.filename)
        filepath = os.path.join(app.config['UPLOAD_FOLDER'], filename)
        file.save(filepath)
        return pd.read_csv(filepath) if filename.endswith('.csv') else pd.read_csv(filepath)
    else:
        raise ValueError("Invalid file format or filename")

# Rota POST para o microserviço
@app.route('/calcular_distancias', methods=['POST'])
async def calcular_distancias():
    try:
        # Verifica se o pedido tem os arquivos esperados
        if 'df_tecnicos' not in request.files or 'df_pedidos' not in request.files:
            return jsonify({'error': 'Arquivos df_tecnicos e df_pedidos são necessários'})

        if not os.path.exists(app.config['UPLOAD_FOLDER']):
            os.makedirs(app.config['UPLOAD_FOLDER'])
            os.chmod(app.config['UPLOAD_FOLDER'], 0o777)

        file_tecnicos = request.files['df_tecnicos']
        file_pedidos = request.files['df_pedidos']

        # Processa os arquivos enviados e obtém os DataFrames
        df_tecnicos = process_uploaded_file(file_tecnicos)
        df_pedidos = process_uploaded_file(file_pedidos)

        # Executa a função
        resultado = await funcao(df_tecnicos, df_pedidos)

        # Retorna o resultado como JSON
        return jsonify(resultado)

    except Exception as e:
        return jsonify({'error': str(e)})

if __name__ == '__main__':
    app.run(debug=True)

app = Flask(__name__)