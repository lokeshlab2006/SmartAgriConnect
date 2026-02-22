import { useState, useEffect } from 'react'
import './index.css'

function App() {
  const [view, setView] = useState('farmer') // 'farmer' or 'buyer'

  return (
    <div className="container">
      <header className="header">
        <div className="logo">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="url(#gradient)" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <linearGradient id="gradient" x1="0%" y1="0%" x2="100%" y2="100%">
              <stop offset="0%" stopColor="#10b981" />
              <stop offset="100%" stopColor="#3b82f6" />
            </linearGradient>
            <path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6" />
          </svg>
          SmartAgri Connect
        </div>
        <div className="nav-links">
          <span
            className={`nav-item ${view === 'farmer' ? 'active' : ''}`}
            onClick={() => setView('farmer')}
          >
            Farmer Dashboard
          </span>
          <span
            className={`nav-item ${view === 'buyer' ? 'active' : ''}`}
            onClick={() => setView('buyer')}
          >
            Buyer Marketplace
          </span>
        </div>
      </header>

      {view === 'farmer' ? <FarmerDashboard /> : <BuyerMarketplace />}
    </div>
  )
}

function FarmerDashboard() {
  const [crop, setCrop] = useState('')
  const [quantity, setQuantity] = useState('')
  const [price, setPrice] = useState('')
  const [prediction, setPrediction] = useState(null)
  const [loading, setLoading] = useState(false)
  const [marketPrices, setMarketPrices] = useState([])

  useEffect(() => {
    fetchMarketPrices()
  }, [])

  const fetchMarketPrices = async () => {
    try {
      const res = await fetch('http://localhost:8080/api/market/prices')
      if (res.ok) {
        const data = await res.json()
        setMarketPrices(data)
      }
    } catch (err) {
      console.error("Failed to fetch market prices:", err)
    }
  }

  const checkPricePrediction = async (e) => {
    e.preventDefault()
    if (!crop) return
    setLoading(true)
    try {
      // Calling Python AI service
      const res = await fetch('http://localhost:8000/predict', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ crop, region: 'Local' })
      })
      const data = await res.json()
      setPrediction(data)
    } catch (err) {
      console.error(err)
      // Fallback dummy data if python API fails
      setPrediction({
        current_price: 150.5,
        predicted_trend_7_days: [150.5, 155, 160, 158, 165, 170, 175],
        suggestion: "Hold"
      })
    }
    setLoading(false)
  }

  const handleListProduce = async (e) => {
    e.preventDefault()
    try {
      const res = await fetch('http://localhost:8080/api/marketplace/list', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          farmerName: 'Ramesh Kumar (Mock)',
          crop,
          quantityKg: parseFloat(quantity),
          expectedPricePerKg: parseFloat(price),
          location: 'Pune APMC (Mock)'
        })
      })

      if (res.ok) {
        alert('Produce Listed Successfully to Spring Boot Backend!')
        setCrop('')
        setQuantity('')
        setPrice('')
      } else {
        alert('Failed to list produce. Server returned an error.')
      }
    } catch (err) {
      console.error('List produce error:', err)
      alert('Failed to list produce. Check backend connection.')
    }
  }

  return (
    <>
      <div className="glass-panel" style={{ marginBottom: '24px' }}>
        <h2 className="card-title">Live Mandi Prices (from Backend)</h2>
        <div style={{ display: 'flex', gap: '16px', overflowX: 'auto', paddingBottom: '8px' }}>
          {marketPrices.length > 0 ? marketPrices.map(mp => (
            <div key={mp.id} style={{ background: 'rgba(255,255,255,0.05)', padding: '12px 16px', borderRadius: '8px', minWidth: '150px' }}>
              <div style={{ fontWeight: '600', color: 'var(--primary)' }}>{mp.crop}</div>
              <div style={{ fontSize: '0.85rem', color: 'var(--text-muted)', marginBottom: '4px' }}>{mp.market}</div>
              <div style={{ fontSize: '1.2rem', fontWeight: 'bold' }}>₹{(mp.currentPricePerKg || 0).toFixed(2)}</div>
              <div className={`trend-${mp.trend?.toLowerCase() || 'stable'}`} style={{ fontSize: '0.8rem', marginTop: '4px' }}>
                Trend: {mp.trend || 'STABLE'}
              </div>
            </div>
          )) : <div style={{ color: 'var(--text-muted)' }}>Connecting to market price server...</div>}
        </div>
      </div>

      <div className="grid-2">
        <div className="glass-panel">
          <h2 className="card-title">List Your Produce</h2>
          <form onSubmit={handleListProduce}>
            <label>Crop Name</label>
            <input type="text" value={crop} onChange={(e) => setCrop(e.target.value)} placeholder="e.g. Tomato, Wheat" required />

            <label>Quantity (Kg)</label>
            <input type="number" value={quantity} onChange={(e) => setQuantity(e.target.value)} placeholder="e.g. 500" required />

            <label>Expected Price (₹/Kg)</label>
            <input type="number" value={price} onChange={(e) => setPrice(e.target.value)} placeholder="e.g. 40" required />

            <button type="submit" className="btn" style={{ width: '100%' }}>List in Marketplace</button>
          </form>
        </div>

        <div className="glass-panel">
          <h2 className="card-title">AI Price Prediction</h2>
          <p style={{ color: 'var(--text-muted)', marginBottom: '16px' }}>Check future price trends before selling.</p>

          <div style={{ display: 'flex', gap: '12px', marginBottom: '24px' }}>
            <input
              type="text"
              value={crop}
              onChange={(e) => setCrop(e.target.value)}
              placeholder="Crop Name"
              style={{ marginBottom: 0 }}
            />
            <button className="btn btn-secondary" onClick={checkPricePrediction} disabled={loading || !crop}>
              {loading ? 'Analyzing...' : 'Predict'}
            </button>
          </div>

          {prediction && (
            <div style={{ background: 'rgba(0,0,0,0.2)', padding: '16px', borderRadius: '8px' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '16px' }}>
                <span style={{ fontSize: '1.2rem' }}>Current Market Rate:</span>
                <span style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>₹{prediction.current_price} / Kg</span>
              </div>

              <div style={{ marginBottom: '16px' }}>
                <div style={{ fontSize: '0.9rem', color: 'var(--text-muted)', marginBottom: '8px' }}>7-Day Forecast</div>
                <div style={{ display: 'flex', gap: '4px', height: '60px', alignItems: 'flex-end' }}>
                  {prediction.predicted_trend_7_days.map((p, i) => {
                    const max = Math.max(...prediction.predicted_trend_7_days);
                    const min = Math.min(...prediction.predicted_trend_7_days);
                    const range = max - min || 1;
                    const height = 20 + ((p - min) / range) * 80;
                    return (
                      <div key={i} style={{
                        flex: 1,
                        background: i === 6 ? 'var(--primary)' : 'rgba(16, 185, 129, 0.4)',
                        height: `${height}%`,
                        borderRadius: '4px 4px 0 0',
                        transition: 'height 1s ease'
                      }} title={`₹${p}`}></div>
                    )
                  })}
                </div>
              </div>

              <div style={{ padding: '12px', background: prediction.suggestion === 'Hold' ? 'rgba(59, 130, 246, 0.1)' : 'rgba(16, 185, 129, 0.1)', borderLeft: `4px solid ${prediction.suggestion === 'Hold' ? 'var(--secondary)' : 'var(--primary)'}`, borderRadius: '4px' }}>
                <strong>AI Suggestion:</strong> {prediction.suggestion}
              </div>
            </div>
          )}
        </div>
      </div>
    </>
  )
}

function BuyerMarketplace() {
  const [search, setSearch] = useState('')
  const [listings, setListings] = useState([])

  useEffect(() => {
    fetchListings()
  }, [])

  const fetchListings = async () => {
    try {
      const res = await fetch('http://localhost:8080/api/marketplace/listings')
      if (res.ok) {
        const data = await res.json()
        setListings(data)
      }
    } catch (err) {
      console.error("Failed to fetch listings:", err)
    }
  }

  const filtered = listings.filter(l => (l.crop || '').toLowerCase().includes(search.toLowerCase()))

  return (
    <div>
      <div className="glass-panel" style={{ marginBottom: '24px' }}>
        <div style={{ display: 'flex', gap: '16px', alignItems: 'center' }}>
          <input
            type="text"
            placeholder="Search produce..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            style={{ marginBottom: 0, maxWidth: '400px' }}
          />
          <button className="btn">Search</button>
        </div>
      </div>

      <div className="grid-2">
        {filtered.map(listing => (
          <div key={listing.id} className="glass-panel listing-card">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
              <div>
                <div className="badge">{listing.crop}</div>
                <h3 style={{ marginTop: '12px', fontSize: '1.2rem' }}>{listing.farmerName}</h3>
                <div style={{ color: 'var(--text-muted)', fontSize: '0.9rem' }}>{listing.location || 'Unknown Location'}</div>
              </div>
              <div style={{ textAlign: 'right' }}>
                <div style={{ fontSize: '1.5rem', fontWeight: 'bold', color: 'var(--primary)' }}>₹{listing.expectedPricePerKg} / Kg</div>
                <div style={{ color: 'var(--text-muted)', fontSize: '0.9rem' }}>{listing.quantityKg} Kg Available</div>
              </div>
            </div>
            <div style={{ marginTop: '16px', display: 'flex', gap: '12px' }}>
              <button className="btn" style={{ flex: 1 }}>Contact Farmer</button>
              <button className="btn btn-secondary" style={{ flex: 1 }}>Buy Now</button>
            </div>
          </div>
        ))}
        {filtered.length === 0 && (
          <div style={{ color: 'var(--text-muted)', padding: '24px' }}>No produce listings available from the backend yet. Go to the Farmer Dashboard to list some!</div>
        )}
      </div>
    </div>
  )
}

export default App
