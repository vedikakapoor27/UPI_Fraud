# Real-Time UPI Fraud Detector

A rule-based real-time fraud detection system for UPI transactions built for the Fiserv Fintech Hackathon 2026.

---

## Problem Statement

UPI fraud transactions are increasing rapidly, making real-time fraud monitoring essential.

This project detects suspicious UPI transactions using a weighted rule-based fraud scoring engine and displays flagged transactions in a live dashboard.

The system helps fraud analysts identify suspicious activity before fraudulent transactions are completed.

---

## Features

- Real-time fraud risk scoring
- Weighted risk engine (0–100)
- 10 fraud detection rules
- Live fraud monitoring dashboard
- Transaction velocity checks
- Device fingerprint analysis
- CSV import/export support
- Risk tier classification
- Detailed rule explanations
- In-memory transaction tracking

---

## Tech Stack

### Frontend
- HTML
- CSS
- JavaScript

### Backend
- Java / Spring Boot

### Concepts Used
- Rule-based fraud detection
- Risk scoring
- Velocity checks
- Device fingerprinting
- Real-time transaction monitoring

---

## System Architecture

### Input Layer
Captures transaction details such as:
- Payer VPA
- Payee VPA
- Amount
- Timestamp
- Location
- Device ID
- Authentication method
- MCC

### Rule Engine Layer
Evaluates transactions against fraud detection rules and assigns weighted risk points.

### Data Layer
Uses in-memory tracking for:
- Velocity checks
- Device monitoring
- Transaction history

### Dashboard Layer
Displays:
- Risk scores
- Fraud alerts
- KPI metrics
- Flagged transactions
- CSV export functionality

---

## Fraud Detection Rules

| Rule | Description | Risk Points |
|------|-------------|-------------|
| Large Transaction | Amount > ₹10,000 | 30 |
| Off-Hours Activity | 10 PM – 6 AM | 20 |
| Velocity Breach | Multiple transactions in short time | 25 |
| Authentication Bypass | No authentication used | 20 |
| New Device Detection | Unknown device fingerprint | 12 |
| Rooted Device Detection | Root/Jailbreak detected | 15 |
| High-Risk MCC | Gambling / financial MCC | 16 |
| Unverified Merchant | Unknown merchant | 18 |
| High-Risk Location | Cross-border / unknown location | 14 |
| Collect Request Pattern | UPI collect scam pattern | 8 |

---

## Risk Classification

| Score Range | Risk Level |
|-------------|------------|
| 0–39 | LOW |
| 40–69 | MEDIUM |
| 70–100 | HIGH |

---

## Dashboard Features

- Live KPI monitoring
- Fraud transaction table
- Rule-by-rule analysis
- CSV import support
- CSV export functionality
- Real-time screening
- Transaction history tracking
- Filterable risk categories

---

## Sample Transaction

```json
{
  "payer_id": "9988776655",
  "payee_id": "MERCHANT121",
  "amount": 9500,
  "timestamp": "2026-02-10T02:30:45",
  "location": "Delhi",
  "device_id": "ABC123"
}

##Team Contributions
### Akshaya
- Contributed to frontend development and dashboard design
- Worked on real-time UI updates and transaction monitoring interface
- Implemented KPI cards, risk tables, and transaction analysis views
- Helped build CSV import/export and dashboard interaction features
- Contributed to frontend validation, responsiveness, and user experience
- Developed the transaction history view for tracking screened transactions
- Assisted in integrating fraud analysis results with the frontend dashboard
