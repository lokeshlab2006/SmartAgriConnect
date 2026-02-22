# SmartAgri Connect 🌾

Welcome to **SmartAgri Connect**! This is a modern, real-time market intelligence platform designed to connect farmers directly with buyers while providing AI-based price trend predictions.

This project is divided into three main components:

1. **Frontend Web App** (`frontend-app`): Built with React and Vite. It serves the user interfaces for the Farmer Dashboard and Buyer Marketplace.
2. **Core Backend System** (`backend-core`): Built with Java Spring Boot. It acts as the primary data source for producing listings, market prices, and authentication interactions.
3. **AI Prediction Service** (`ai-service`): Built with Python and FastAPI. It provides predictive analytics on crop prices.

---

## 🚀 Getting Started Guide for Beginners

To run the complete SmartAgri Connect platform, you will need to open **three separate terminals**, one for each of the services, and start them individually.

Here is exactly what you need to do out of the box.

### Prerequisites

Make sure you have the following installed on your machine:

- **Node.js & npm** (To run the React frontend)
- **Python 3+** (To run the AI prediction service)
- **Java 17+** (To run the Spring Boot backend)

---

### Step 1: Start the Python AI Service 🐍

This service simulates machine learning predictions for future crop prices.

**In your first terminal window:**

1. Navigate into the AI Service folder:

   ```bash
   cd ai-service
   ```

2. Activate the Python virtual environment:
   **(On Windows):**

   ```bash
   .\venv\Scripts\activate
   ```

   *(Note: If you receive an "Execution Policy" error in PowerShell, run `Set-ExecutionPolicy Unrestricted -Scope CurrentUser` first, or use Command Prompt `cmd.exe` instead and run `.\venv\Scripts\activate.bat`)*
   **(On Mac/Linux):**

   ```bash
   source venv/bin/activate
   ```

3. Start the FastAPI server using Uvicorn:

   ```bash
   python app.py
   ```

> **✅ Success Indicator**: The terminal will display `Uvicorn running on http://0.0.0.0:8000`. Leave this terminal open.

---

### Step 2: Start the Java Spring Boot Backend Core ☕

This is the main brain of the app that holds the database and handles marketplace listings.

**In your second terminal window:**

1. Navigate into the Backend folder:

   ```bash
   cd backend-core
   ```

2. Start the Spring Boot application using the built-in Maven Wrapper:
   **(On Windows):**

   ```bash
   .\mvnw spring-boot:run
   ```

   **(On Mac/Linux):**

   ```bash
   ./mvnw spring-boot:run
   ```

*Note: The first time you run this, it will take a minute or two to download dependencies from the internet.*

> **✅ Success Indicator**: The terminal will display `Tomcat started on port 8080 (http)` followed by `Started BackendApplication`. Leave this terminal open.

---

### Step 3: Start the React Frontend App ⚛️

This is the beautiful user interface where farmers and buyers actually interact.

**In your third terminal window:**

1. Navigate into the Frontend folder:

   ```bash
   cd frontend-app
   ```

2. Install the JavaScript package dependencies (Only needed the first time):

   ```bash
   npm install
   ```

3. Start the Vite development server:

   ```bash
   npm run dev
   ```

> **✅ Success Indicator**: The terminal will display a local URL, typically `http://localhost:5173/` or `http://localhost:5174/`.

---

## 🎉 You're Done

Open your web browser and go to the link provided by Step 3 (e.g., `http://localhost:5173`).

You will now see the beautiful **SmartAgri Connect** app!
Because you started the Java and Python servers beforehand, the app will automatically communicate with them to feed you live mock market data and AI predictions.

---

## 🏗️ Technical Details & Entry Points (For Developers)

If you want to read or edit the code, here are the core entry points for each service:

### 1. Frontend (`frontend-app`)

- **Technology Stack**: React, Vite, Vanilla CSS.
- **Entry Point File**: `src/App.jsx`. This file contains the entire layout and routing logic for switching between the `FarmerDashboard` and `BuyerMarketplace`.
- **Styling**: All of the visual glass-morphism themes and colors are controlled entirely within `src/index.css`.

### 2. Backend API (`backend-core`)

- **Technology Stack**: Java 17, Spring Boot 3.2, H2 In-Memory Database, Maven.
- **Entry Point File**: `src/main/java/com/smartagri/backend/BackendApplication.java`.
- **Logic Folders**:
  - `controller/`: Look in `MarketController.java` to see how the initial mandi prices are hardcoded into the DB on startup. Look at `MarketplaceController.java` to see how listings are posted and gathered.
  - `model/`: The Data tables (`ProduceListing.java` & `MarketPrice.java`).
  - `repository/`: The Data access layer pointing to the H2 database.
- **Tests**: Found under `src/test/java/`. To run the unit tests, use the command `.\mvnw test`.

### 3. AI Service (`ai-service`)

- **Technology Stack**: Python, FastAPI.
- **Entry Point File**: `app.py`.
- **Logic**: It exposes a single POST endpoint `/predict` which takes a `{"crop": "name", "region": "name"}` JSON standard and mathematically calculates randomized baseline pricing trends to simulate AI behavior.
