# 📌 EAMS - Alert Module (Module 4)

## 🚀 Overview  
This module handles **Alert Management** in the EAMS system.  
It monitors sensor data against asset thresholds and generates alerts when abnormal conditions occur. It also manages alert lifecycle and sends email notifications.

---

## 🧱 Tech Stack  
- Java 17  
- Spring Boot  
- Spring Data JPA  
- MySQL  
- Java Mail Sender  
- Lombok  

---

## ✨ Features Implemented  

### 🚨 Alert Generation  
- Automatically triggered when sensor values exceed thresholds  
- Integrated with Sensor Data Module  
- Supports different alert types (e.g., THRESHOLD_BREACH)

---

### 🔄 Alert Lifecycle Management  
- Alert Status:
  - ACTIVE  
  - RESOLVED  
- Updates existing alert if already active  
- Prevents duplicate alerts for same asset  

---

### 📧 Email Notification  
- Sends alert email when system enters CRITICAL state  
- Sends resolve email when system returns to NORMAL  
- Includes asset details and sensor values  
- Cooldown logic to prevent spam  

---

### ⚙️ Smart Alert Handling  
- Checks previous vs current asset status  
- Creates alert only on state transition  
- Updates existing alert instead of creating duplicates  

---

## 🧾 Validation & Error Handling  
- Asset existence validation  
- Null checks for assigned users  
- Clean exception handling  
- Controlled alert creation logic  

---

## 📂 Project Structure  

com.enterprise.eams  
│  
├── alertmodule  
│   ├── controller  
│   ├── service  
│   ├── repository  
│   ├── entity  
│   ├── dto  
│  
├── common  
│   └── email  

---

## 🔑 API Endpoints  

### 🔵 Get All Alerts  
GET /api/alerts  

---

### 🔵 Get Alerts by Asset  
GET /api/alerts/asset/{assetId}  

---

### 🟡 Update Alert (Resolve)  
PUT /api/alerts/{id}  

**Request Body**
{
  "status": "RESOLVED"
}

---

## 📤 Sample Response  

{
  "id": 10,
  "assetId": 1,
  "assetName": "Boiler Machine",
  "type": "THRESHOLD_BREACH",
  "message": "Threshold crossed for asset Boiler Machine",
  "status": "ACTIVE",
  "triggeredAt": "2026-01-01T10:30:00"
}

---

## 🔗 Relationships  

- One Asset → Many Alerts  
- Alert linked to Sensor Data indirectly  
- Integrated with Email Service  

---

## ⚙️ Business Logic  

- Compare sensor values with asset thresholds  
- Detect state transition:
  - NORMAL → CRITICAL → Create Alert  
  - CRITICAL → NORMAL → Resolve Alert  
- Update existing active alert if present  
- Send email notifications accordingly  

---

## 🔐 Access Control  

Role     | Permissions  
---------|-------------  
MANAGER  | View all alerts  
OPERATOR | View alerts of assigned assets  

---

## 🧪 Testing Flow  

- Send sensor data exceeding threshold  
- Verify alert created in DB  
- Check email received  
- Send normal data → alert resolved  
- Verify resolve email sent  
- Ensure no duplicate alerts created  

---

## 📌 Notes  

- Prevents alert spam using cooldown logic  
- Ensures only one active alert per asset  
- Core monitoring module for system reliability  

---

## ✅ Module Status  

✔ Alert Creation Logic Implemented  
✔ Alert Update & Resolution Done  
✔ Email Notification Integrated  
✔ Cooldown Logic Added  
✔ Ready for Dashboard Integration  

---

## 👩‍💻 Author  
Developed as part of EAMS project (Module 4)
