# 📌 EAMS - Asset Module (Module 2)

## 🚀 Overview  
This module handles **Asset Management** in the EAMS system.  
It enables Managers to create, update, assign, and monitor assets such as machines and equipment, along with defining threshold limits for sensor monitoring.

---

## 🧱 Tech Stack  
- Java 17  
- Spring Boot  
- Spring Data JPA  
- MySQL  
- Lombok  
- (Integrated with User Module)

---

## ✨ Features Implemented  

### 🏭 Asset Management  
- Create Asset  
- Get All Assets  
- Get Asset by ID  
- Update Asset  
- Delete Asset  

### 👥 Asset Assignment  
- Assign assets to Users (Operators)  
- Many-to-One relationship (Multiple assets → One user)  

### 📊 Threshold Configuration  
- Set temperature threshold  
- Set pressure threshold  
- Used for triggering alerts in Sensor Module  

### ⚙️ Status Tracking  
- Asset status:  
  - NORMAL  
  - CRITICAL  
- Automatically updated based on sensor data  

---

## 🧾 Validation & Error Handling  
- DTO-based request handling  
- Clean API responses  
- Proper exception handling (Asset not found, etc.)  

---

## 📂 Project Structure  

com.enterprise.eams  
│  
├── assetmodule  
│   ├── controller  
│   ├── service  
│   ├── repository  
│   ├── entity  
│   ├── dto  
│  
├── common  
│   └── (shared utilities, if any)  

---

## 🔑 API Endpoints  

### 🟢 Create Asset (Manager)
POST /api/assets  

**Request Body**
{
  "name": "Boiler Machine",
  "type": "Thermal",
  "location": "Plant A",
  "thresholdTemp": 80,
  "thresholdPressure": 120
}

---

### 🔵 Get All Assets
GET /api/assets  

---

### 🔵 Get Asset by ID
GET /api/assets/{id}  

---

### 🟡 Update Asset
PUT /api/assets/{id}  

---

### 🔴 Delete Asset
DELETE /api/assets/{id}  

---

## 📤 Sample Response  

{
  "id": 1,
  "name": "Boiler Machine",
  "type": "Thermal",
  "location": "Plant A",
  "thresholdTemp": 80,
  "thresholdPressure": 120,
  "status": "NORMAL"
}

---

## 🔗 Relationships  

- One User → Many Assets  
- One Asset → Many SensorData  
- One Asset → Many Alerts  

---

## ⚙️ Business Logic  

- Assets store threshold values for monitoring  
- Sensor module uses these thresholds to:  
  - Trigger alerts  
  - Update asset status  

---

## 🔐 Access Control  

Role     | Permissions  
---------|-------------  
MANAGER  | Full CRUD operations  
OPERATOR | View assigned assets only  

---

## 🧪 Testing Flow  

- Create asset  
- Assign asset to user  
- Fetch asset list  
- Update asset details  
- Delete asset  
- Verify integration with sensor data  

---

## 📌 Notes  

- Asset module is the **core linking module**  
- Used by:
  - Sensor Module  
  - Alert Module  
- Clean separation using DTOs and service layer  

---

## ✅ Module Status  

✔ Asset CRUD Completed  
✔ User Assignment Implemented  
✔ Threshold Logic Integrated  
✔ Ready for Sensor & Alert Modules  

---

## 👩‍💻 Author  
Developed as part of EAMS project (Module 2)
