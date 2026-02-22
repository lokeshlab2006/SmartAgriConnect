from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
import random

app = FastAPI(title="SmartAgri Connect AI Service")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class PredictionRequest(BaseModel):
    crop: str
    region: str

@app.post("/predict")
async def predict_price(request: PredictionRequest):
    # Dummy ML model response for MVP
    base_price = 100 if request.crop.lower() == 'tomato' else 200
    predicted_prices = [base_price + random.uniform(-10, 15) for _ in range(7)]
    
    return {
        "crop": request.crop,
        "region": request.region,
        "current_price": round(predicted_prices[0], 2),
        "predicted_trend_7_days": [round(p, 2) for p in predicted_prices],
        "suggestion": "Hold" if predicted_prices[-1] > predicted_prices[0] else "Sell Now"
    }

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
