# 📌 EAMS - Sensor Data Module (Module 3)

## 🚀 Overview  
This module handles **Sensor Data Ingestion and Processing** in the EAMS system.  
It simulates IoT sensor inputs (temperature, pressure), stores readings, and triggers alerts when thresholds are exceeded.

---

## 🧱 Tech Stack  
- Java 17  
- Spring Boot  
- Spring Data JPA  
- MySQL  
- Lombok  
- Scheduler (@Scheduled)  

---

## ✨ Features Implemented  

### 📡 Sensor Data Ingestion  
- Accept sensor readings via API  
- Store temperature and pressure data  
- Associate readings with assets  

### ⏱ Timestamp Tracking  
- Each reading stored with timestamp  
- Enables monitoring and historical analysis  

### 🚨 Alert Trigger Integration  
- Checks sensor values against asset thresholds  
- Triggers alert logic when exceeded  
- Integrates with Alert Module  

### 🔄 Scheduler Simulation  
- Automatically generates mock sensor data  
- Runs at fixed intervals  
- Simulates real-world IoT behavior  

---

## 🧾 Validation & Error Handling  
- Asset existence validation  
- Clean exception handling  
- DTO-based request structure  

---

## 📂 Project Structure  

com.enterprise.eams  
│  
├── sensormodule  
│   ├── controller  
│   ├── service  
│   ├── repository  
│   ├── entity  
│   ├── dto  
│   ├── scheduler  
│  
├── common  

---

## 🔑 API Endpoints  

### 🟢 Send Sensor Data  
POST /api/sensors/send-data  

**Request Body**
{
  "assetId": 1,
  "temperature": 85,
  "pressure": 130
}

---

### 🔵 Get Sensor Data by Asset  
GET /api/sensors/asset/{id}  

---

## 📤 Sample Response  

{
  "id": 101,
  "temperature": 85,
  "pressure": 130,
  "timestamp": "2026-01-01T10:30:00"
}

---

## 🔗 Relationships  

- Many SensorData → One Asset  
- SensorData used to trigger Alerts  
- Connected to Asset Module for thresholds  

---

## ⚙️ Business Logic  

- Fetch asset thresholds  
- Compare incoming values:
  - If exceeded → status = CRITICAL  
  - Else → status = NORMAL  
- Call AlertService for further processing  

---

## 🔄 Scheduler Logic  

- Runs periodically using @Scheduled  
- Fetches all assets  
- Generates random temperature & pressure values  
- Calls sensor service internally  
- Helps simulate real-time monitoring  

---

## 🔐 Access Control  

Role     | Permissions  
---------|-------------  
MANAGER  | View all sensor data  
OPERATOR | View assigned asset data  

---

## 🧪 Testing Flow  

- Send manual sensor data via API  
- Verify data stored in DB  
- Trigger threshold breach → alert created  
- Scheduler auto-generates data  
- Validate repeated execution  

---

## 📌 Notes  

- Core module for real-time monitoring  
- Bridges Asset Module and Alert Module  
- Designed to simulate IoT systems  

---

## ✅ Module Status  

✔ Sensor Data Storage Implemented  
✔ Threshold Comparison Logic Done  
✔ Alert Integration Working  
✔ Scheduler Simulation Added  

---

## 👩‍💻 Author  
Developed as part of EAMS project (Module 3)
