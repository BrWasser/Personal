from flask import Flask, request, jsonify
import numpy as np
from sklearn.cluster import KMeans
from statsmodels.tsa.arima.model import ARIMA

app = Flask(__name__)

# Dados de exemplo para a série temporal
time_series_data = [70, 80, 100, 120, 150, 180, 200, 220, 250, 280]

@app.route('/predict_time_series', methods=['POST'])
def predict_time_series_api():
    try:
        # Aqui você pode ajustar o modelo ARIMA com os seus dados de série temporal
        model = ARIMA(time_series_data, order=(1, 1, 1))
        model_fit = model.fit()

        # Fazendo a previsão para o próximo valor na série temporal
        prediction = model_fit.forecast()[0]

        return jsonify({'next_prediction': prediction})

    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)